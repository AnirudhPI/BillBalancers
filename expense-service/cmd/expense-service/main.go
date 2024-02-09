package main

import (
	"fmt"
	"log"
	"net"

	expenseservice "github.com/AnirudhPI/BillBalancers/internal/expense-service"
	"github.com/AnirudhPI/BillBalancers/proto"
	"google.golang.org/grpc"
)

func main() {
	server := grpc.NewServer()
	ms := &expenseservice.Microservice{}
	proto.RegisterMicroserviceServer(server, ms)
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
