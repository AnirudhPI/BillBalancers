package expenseservice

import (
	"context"

	"github.com/AnirudhPI/BillBalancers/proto"
)

type Microservice struct {
	proto.UnimplementedMicroserviceServer
}

func (ms *Microservice) SayHello(ctx context.Context, req *proto.HelloRequest) (*proto.HelloResponse, error) {
	name := req.GetName()
	greeting := name
	return &proto.HelloResponse{Greeting: greeting}, nil
}
