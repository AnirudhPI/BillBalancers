package expenseservice

import (
	"context"
	"fmt"
	"log"
	"os"
	"path/filepath"

	expenses "github.com/AnirudhPI/BillBalancers/proto/expenses"
	groups "github.com/AnirudhPI/BillBalancers/proto/groups"
	"github.com/google/uuid"
	"github.com/joho/godotenv"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
)

type ExpenseService struct {
	groups.UnimplementedGroupServiceServer
	expenses.UnimplementedMicroserviceServer
	DB *gorm.DB
}
type Group struct {
	GroupId   string `gorm:"primaryKey"`
	GroupName string
}

type UserGroup struct {
	_id     string `gorm:"primaryKey"`
	GroupID string
	UserID  string
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
	ms.DB.AutoMigrate(&Group{})
	ms.DB.AutoMigrate(&UserGroup{})
}

func (ms *ExpenseService) CreateGroup(ctx context.Context, req *groups.GroupName) (*groups.Group, error) {
	groupName := req.GetGroupName()
	fmt.Println(groupName)

	groupID := uuid.New().String()

	group := Group{
		GroupId:   groupID,
		GroupName: groupName,
	}

	result := ms.DB.WithContext(ctx).Create(&group)
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
		_id := uuid.New().String()
		userGroupMapping := UserGroup{
			_id:     _id,
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
