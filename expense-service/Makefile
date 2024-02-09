GOCMD=go
GOBUILD=$(GOCMD) build
GOCLEAN=$(GOCMD) clean
GOTEST=$(GOCMD) test

BINARY_NAME=expense-service

build:
	$(GOBUILD) -o $(BINARY_NAME) -v ./cmd/expense-service

clean:
	$(GOCLEAN)
	rm -f $(BINARY_NAME)

run:
	$(GOBUILD) -o $(BINARY_NAME) -v ./cmd/expense-service
	./$(BINARY_NAME)

deps:
	$(GOGET) -v ./...

protoc:
	sh ./scripts/generate_proto.sh