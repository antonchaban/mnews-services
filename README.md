docker run --name mnews-db -e POSTGRES_PASSWORD=qwerty -p 5436:5432 -d --rm postgres

migrate -path . -database "postgres://postgres:qwerty@localhost:5436/postgres?sslmode=disable" up