## 📁 Project Structure

```
cinema-system/
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   │   └── com/example/cinema_system/
    │   │   │       ├── config/
    │   │   │       ├── controller/
    │   │   │       ├── dto/
    │   │   │       ├── entity/
    │   │   │       ├── exeption/
    │   │   │       ├── logger/
    │   │   │       ├── mapper/
    │   │   │       ├── repository/
    │   │   │       ├── service/
    │   │   │       ├── security/    
    │   │   │       └── util/
    │   │   └── resources/
    │   │       ├── db/migration    
    │   │       ├── static/
    │   │       │   └── sql/
    │   │       └── temlates/
    ├── pom.xml
    └── README.md
```
## 🔹 Rebuild and run the full stack

```bash
docker compose up --build
```

### 🐘 Useful Commands for Database Debugging (PostgreSQL + Docker)

#### 🔹 Connect to PostgreSQL container

```bash
docker exec -it cinema-system-postgres-1 psql -U postgres -d cinema_system
```

#### 🔹 Common psql commands

| Command                         | Description                                   |
| ------------------------------- | --------------------------------------------- |
| `\l`                            | List all databases                            |
| `\c cinema_system`              | Connect to `cinema_system` database           |
| `\dt`                           | Show all tables in the current database       |
| `\d users`                      | Show structure (columns) of the `users` table |
| `SELECT * FROM users LIMIT 20;` | View first 20 rows from the `users` table     |
| `\q`                            | Quit the psql console                         |


