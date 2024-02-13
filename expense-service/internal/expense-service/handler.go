package expenseservice

import (
	"context"
	"fmt"

	expenses "github.com/AnirudhPI/BillBalancers/proto/expenses"
	groups "github.com/AnirudhPI/BillBalancers/proto/groups"
)

type ExpenseService struct {
	groups.UnimplementedGroupServiceServer
	expenses.UnimplementedMicroserviceServer
}

func (ms *ExpenseService) CreateGroup(ctx context.Context, req *groups.GroupName) (*groups.Group, error) {
	name := req.GetGroupName()
	fmt.Println(name)
	return &groups.Group{GroupName: name}, nil
}
