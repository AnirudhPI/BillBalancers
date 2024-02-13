package expenseservice

import (
	"context"
	"fmt"

	"github.com/AnirudhPI/BillBalancers/proto"
)

type Microservice struct {
	proto.UnimplementedMicroserviceServer
}

func (ms *Microservice) SayHello(ctx context.Context, req *proto.HelloRequest) (*proto.HelloResponse, error) {
	name := req.GetName()
	return &proto.HelloResponse{Greeting: name}, nil
}

func (ms *Microservice) CreateGroup(ctx context.Context, req *proto.GroupName) (*proto.Group, error) {
	name := req.GetGroupName()
	fmt.Println(name)
	return &proto.Group{groupName: name}, nil
}
