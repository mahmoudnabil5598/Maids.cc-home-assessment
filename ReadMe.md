# Home assessment for Maids.cc

## Features

* Borrow Books
* Return books
* Create Users (Patron - Authors)

## H2 Database Configuration

```properties
spring.datasource.url=jdbc:h2:mem:demodb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

## Server Configuration

```properties
server.port=8000
server.servlet.context-path=/api/
```

## Authentication

   ```bash
  api/authenticate
```

## H2-Console

   ```bash
 localhost:8000/api/h2-console/login.jsp
```

## Swagger

   ```bash
http://localhost:8000/api/swagger-ui/index.html
```
