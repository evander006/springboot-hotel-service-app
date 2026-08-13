# Hotel Property View API

RESTful Spring Boot application for managing hotels: create, list, search, amenities, and histograms.

Base URL: `http://localhost:8092/property-view`

## Tech stack

- Java 21
- Spring Boot 4
- Spring Data JPA
- H2 (in-memory)
- Maven
- Springdoc OpenAPI (Swagger UI)
- Validation / Lombok
- Liquidbase

## Requirements

- JDK 21+
- Maven 3.9+ (or use the included `mvnw` wrapper)

## Run

```bash
mvn spring-boot:run
```

Or with the wrapper:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

App starts on port **8092**.

## Useful links

| Resource | URL |
|----------|-----|
| Swagger UI | http://localhost:8092/swagger-ui.html |
| OpenAPI JSON | http://localhost:8092/v3/api-docs |
| H2 Console | http://localhost:8092/h2-console |

### H2 Console login

- **JDBC URL:** `jdbc:h2:mem:hoteldb`
- **User:** `sa`
- **Password:** `mysecretpassword`

> H2 is in-memory: data is cleared when the app stops.

## API endpoints

All endpoints use the `/property-view` prefix.

| Method | Path | Description |
|--------|------|-------------|
| `GET` | `/hotels` | List hotels (short summary) |
| `GET` | `/hotels/{id}` | Hotel details |
| `GET` | `/search` | Search by `name`, `brand`, `city`, `country`, `amenities` |
| `POST` | `/hotels` | Create hotel (`description` and `checkOut` are optional) |
| `POST` | `/hotels/{id}/amenities` | Add amenities to a hotel |
| `GET` | `/histogram/{param}` | Counts by `brand`, `city`, `country`, or `amenities` |

### Examples

**Create hotel**

```http
POST http://localhost:8092/property-view/hotels
Content-Type: application/json

{
  "name": "DoubleTree by Hilton Minsk",
  "description": "Luxury hotel in Minsk",
  "brand": "Hilton",
  "address": {
    "houseNumber": 9,
    "street": "Pobediteley Avenue",
    "city": "Minsk",
    "country": "Belarus",
    "postCode": "220004"
  },
  "contacts": {
    "phone": "+375 17 309-80-00",
    "email": "doubletreeminsk.info@hilton.com"
  },
  "arrivalTime": {
    "checkIn": "14:00",
    "checkOut": "12:00"
  }
}
```

**Search**

```http
GET http://localhost:8092/property-view/search?city=minsk
```

**Histogram**

```http
GET http://localhost:8092/property-view/histogram/city
```

## Project structure

```
src/main/java/.../springboothotelserviceapp
├── api/           # Controllers + DTOs
├── service/       # Business logic
├── repository/    # JPA repositories
├── domain/        # Entities + embeddables + mapper
└── config/        # OpenAPI configuration
```

## Manual testing

Ready-to-run requests (happy path + error cases) are in:

```
http/hotel-api.http
```

Open the file in IntelliJ IDEA and run requests with the green play button.

## Build

```bash
mvn clean package
```
