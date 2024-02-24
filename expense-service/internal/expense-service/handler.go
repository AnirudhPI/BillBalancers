package expenseservice

import (
	"context"
	"fmt"
	"log"
	"os"
	"path/filepath"

	expenses "github.com/AnirudhPI/BillBalancers/proto/expenses"
	groups "github.com/AnirudhPI/BillBalancers/proto/groups"
	"github.com/golang-jwt/jwt"
	"github.com/google/uuid"
	"github.com/joho/godotenv"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/status"
	"google.golang.org/protobuf/types/known/emptypb"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
)

type ExpenseService struct {
	groups.UnimplementedGroupServiceServer
	expenses.UnimplementedExpenseServiceServer
	DB *gorm.DB
}
type User struct {
	ID        string `gorm:"primaryKey"`
	FirstName string
	LastName  string
	Email     string
}

type Group struct {
	GroupID         string `gorm:"primaryKey"`
	GroupName       string
	CreatedByUserID string `gorm:"type:TEXT;size:255"`
	CreatedByUser   User   `gorm:"foreignKey:CreatedByUserID;references:ID"`
}

type UserGroup struct {
	GroupID string `gorm:"primaryKey"`
	UserID  string `gorm:"primaryKey"`
}

type Expense struct {
	ExpenseID    string `gorm:"primaryKey"`
	GroupID      string `gorm:"type:TEXT;size:255"`
	UserID       string `gorm:"type:TEXT;size:255"`
	Description  string
	TotalExpense float32
	Group        Group `gorm:"foreignKey:GroupID"`
	User         User  `gorm:"foreignKey:UserID"`
}

type ExpenseChange struct {
	ExpenseID    string `gorm:"primaryKey"`
	Description  string
	TotalExpense float32
	Expense      Expense `gorm:"foreignKey:ExpenseID"`
}

type UserExpense struct {
	UserID    string `gorm:"primaryKey"`
	ExpenseID string `gorm:"primaryKey"`
	Share     float32
	User      User    `gorm:"foreignKey:UserID"`
	Expense   Expense `gorm:"foreignKey:ExpenseID"`
}

func loadEnv() {
	path, err := os.Getwd()
	if err != nil {
		log.Println(err)
	}
	godotenv.Load(filepath.Join(path, ".env"))
}

func (ms *ExpenseService) ConnectToDB() {
	loadEnv()
	dsn := os.Getenv("DSN")
	db, err := gorm.Open(mysql.Open(dsn), &gorm.Config{})
	if err != nil {
		log.Fatalf("failed to connect: %v", err)
	}

	log.Println("Successfully connected to DB!")
	ms.DB = db
	ms.DB.AutoMigrate(&User{})
	ms.DB.AutoMigrate(&Group{})
	ms.DB.AutoMigrate(&UserGroup{})
	ms.DB.AutoMigrate(&Expense{})
	ms.DB.AutoMigrate(&UserExpense{})
}

func parseJWTToken(jwtToken string) (string, error) {
	token, err := jwt.Parse(jwtToken, func(token *jwt.Token) (interface{}, error) {
		if _, ok := token.Method.(*jwt.SigningMethodHMAC); !ok {
			return nil, fmt.Errorf("unexpected Signing Method: %v", token.Header)
		}
		return []byte(os.Getenv("JWT_SECRET")), nil
	})

	if err != nil {
		return "", fmt.Errorf("error parsing JWT token: %v", err)
	}

	if claims, ok := token.Claims.(jwt.MapClaims); ok && token.Valid {
		email, ok := claims["email"].(string)
		if !ok {
			return "", fmt.Errorf("email claim not found in JWT token")
		}
		return email, nil
	}

	return "", fmt.Errorf("invalid JWT token")
}
func (ms *ExpenseService) CreateGroup(ctx context.Context, req *groups.GroupName) (*groups.Group, error) {
	groupName := req.GetGroupName()

	groupID := uuid.New().String()
	userID := req.GetUserID()
	group := Group{
		GroupID:         groupID,
		GroupName:       groupName,
		CreatedByUserID: userID,
	}

	result := ms.DB.WithContext(ctx).Create(&group)
	if result.Error != nil {
		log.Printf("Failed to insert new group into database: %v", result.Error)
		return nil, fmt.Errorf("failed to create new group: %v", result.Error)
	}

	userGroup := UserGroup{
		GroupID: groupID,
		UserID:  userID,
	}
	result = ms.DB.WithContext(ctx).Create(&userGroup)
	if result.Error != nil {
		log.Printf("Failed to insert new group into database: %v", result.Error)
		return nil, fmt.Errorf("failed to create new group: %v", result.Error)
	}
	return &groups.Group{GroupId: groupID, GroupName: groupName}, nil
}

func (ms *ExpenseService) AddUsersToGroup(ctx context.Context, req *groups.GroupData) (*groups.Group, error) {

	groupID := req.GetGroupID()
	uuidList := req.GetUuid()
	for _, value := range uuidList {
		userGroupMapping := UserGroup{
			GroupID: groupID,
			UserID:  value,
		}
		result := ms.DB.WithContext(ctx).Create(&userGroupMapping)
		if result.Error != nil {
			log.Printf("Failed to insert new group into database: %v", result.Error)
			return nil, fmt.Errorf("failed to create new group: %v", result.Error)
		}
	}
	return &groups.Group{GroupId: groupID}, nil
}

func (ms *ExpenseService) GetGroupMembers(ctx context.Context, req *groups.GroupDetails) (*groups.GroupMembers, error) {
	groupID := req.GetGroupID()
	var userGroups []UserGroup
	result := ms.DB.WithContext(ctx).Where("group_id = ?", groupID).Find(&userGroups)
	if result.Error != nil {
		return nil, fmt.Errorf("error fetching group members: %v", result.Error)
	}
	userIDs := make([]string, 0, len(userGroups))
	for _, ug := range userGroups {
		userIDs = append(userIDs, ug.UserID)
	}
	fmt.Println(userIDs)
	return &groups.GroupMembers{FirstName: "HI", LastName: "hey", Email: "something"}, nil
}

func (ms *ExpenseService) AddExpense(ctx context.Context, req *expenses.Expense) (*emptypb.Empty, error) {

	expenseID := uuid.New().String()
	expense := Expense{
		ExpenseID:    expenseID,
		GroupID:      req.GetGroupID(),
		UserID:       req.GetUserID(),
		Description:  req.GetDescription(),
		TotalExpense: req.GetTotalExpense(),
	}

	result := ms.DB.WithContext(ctx).Create(&expense)
	if result.Error != nil {
		log.Printf("Failed to insert new expense into database: %v", result.Error)
		return nil, fmt.Errorf("failed to create new expense: %v", result.Error)
	}
	return &emptypb.Empty{}, nil

}

func (ms *ExpenseService) FetchExpense(ctx context.Context, req *expenses.ExpenseID) (*expenses.UserExpense, error) {
	expenseID := req.GetExpenseID()

	var expense Expense
	result := ms.DB.WithContext(ctx).Where("expense_id = ?", expenseID).First(&expense)
	if result.Error != nil {
		if result.Error == gorm.ErrRecordNotFound {
			return nil, status.Errorf(codes.NotFound, "expense with ID %s not found", expenseID)
		}
		return nil, status.Errorf(codes.Internal, "failed to fetch expense: %v", result.Error)
	}

	userExpense := &expenses.UserExpense{
		Description:  expense.Description,
		TotalExpense: float32(expense.TotalExpense),
	}

	return userExpense, nil
}

func (ms *ExpenseService) UpdateExpense(ctx context.Context, req *expenses.ExpenseChange) (*emptypb.Empty, error) {
	expenseID := req.GetExpenseID()

	expenseChange := ExpenseChange{
		ExpenseID:    expenseID,
		Description:  req.GetDescription(),
		TotalExpense: req.GetTotalExpense(),
	}

	result := ms.DB.WithContext(ctx).Model(&Expense{}).Where("expense_id = ?", expenseID).Updates(expenseChange)
	if result.Error != nil {
		log.Printf("Failed to update expense with ID %s: %v", expenseID, result.Error)
		return nil, status.Errorf(codes.Internal, "failed to update expense: %v", result.Error)
	}

	if result.RowsAffected == 0 {
		return nil, status.Errorf(codes.NotFound, "expense with ID %s not found", expenseID)
	}

	return &emptypb.Empty{}, nil
}

func (ms *ExpenseService) DeleteExpense(ctx context.Context, req *expenses.ExpenseID) (*emptypb.Empty, error) {
	expenseID := req.GetExpenseID()

	result := ms.DB.WithContext(ctx).Where("expense_id = ?", expenseID).Delete(&Expense{})

	if result.Error != nil {
		log.Printf("Failed to delete expense with ID %s: %v", expenseID, result.Error)
		return nil, status.Errorf(codes.Internal, "failed to delete expense: %v", result.Error)
	}

	if result.RowsAffected == 0 {
		return nil, status.Errorf(codes.NotFound, "expense with ID %s not found", expenseID)
	}

	return &emptypb.Empty{}, nil
}
