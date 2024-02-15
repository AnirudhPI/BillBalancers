package expenseservice

import (
	"context"
	"database/sql"
	"fmt"
	"log"
	"os"
	"path/filepath"

	expenses "github.com/AnirudhPI/BillBalancers/proto/expenses"
	groups "github.com/AnirudhPI/BillBalancers/proto/groups"
	"github.com/joho/godotenv"
)

type ExpenseService struct {
	groups.UnimplementedGroupServiceServer
	expenses.UnimplementedMicroserviceServer
}

func loadEnv() {
	path, err := os.Getwd()
	if err != nil {
		log.Println(err)
	}
	godotenv.Load(filepath.Join(path, ".env"))
}

func connectToDB() {
	loadEnv()
	db, err := sql.Open("mysql", os.Getenv("DSN"))
	if err != nil {
		log.Fatalf("failed to connect: %v", err)
	}
	defer db.Close()
	if err := db.Ping(); err != nil {
		log.Fatalf("failed to ping: %v", err)
	}

	log.Println("Successfully connected to DB!")
}

func (ms *ExpenseService) CreateGroup(ctx context.Context, req *groups.GroupName) (*groups.Group, error) {
	name := req.GetGroupName()
	fmt.Println(name)
	return &groups.Group{GroupName: name}, nil
}
