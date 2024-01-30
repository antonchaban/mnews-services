docker run --name mnews-db -e POSTGRES_PASSWORD=qwerty -p 5436:5432 -d --rm postgres

migrate -path . -database "postgres://postgres:qwerty@localhost:5436/postgres?sslmode=disable" up

docker run --rm -d -it -p 15672:15672 -p 5672:5672 rabbitmq:3.11.28-management
