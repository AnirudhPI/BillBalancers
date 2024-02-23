package main

import (
	"fmt"
	"log"
	"net"

	expenseservice "github.com/AnirudhPI/BillBalancers/internal/expense-service"
	expense "github.com/AnirudhPI/BillBalancers/proto/expenses"
	groups "github.com/AnirudhPI/BillBalancers/proto/groups"
	_ "github.com/go-sql-driver/mysql"
	"google.golang.org/grpc"
)

func main() {

	server := grpc.NewServer()
	ms := &expenseservice.ExpenseService{}
	ms.ConnectToDB()
	groups.RegisterGroupServiceServer(server, ms)
	expense.RegisterExpenseServiceServer(server, ms)
	address := ":50051"
	lis, err := net.Listen("tcp", address)
	if err != nil {
		log.Fatalf("Failed to listen: %v", err)
	}

	fmt.Printf("Server listening on %s\n", address)

	if err := server.Serve(lis); err != nil {
		log.Fatalf("Failed to serve: %v", err)
	}

}
