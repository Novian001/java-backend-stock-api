# Java Backend Stock API

Spring Boot 3.2 REST API untuk manajemen stok produk.

## Stack
- **Java**: 17 LTS (Spring Boot 3.2)
- **Database**: PostgreSQL
- **Build**: Gradle
- **Container**: Docker (multi-stage)

## Endpoints
- `GET  /api/products` — semua produk
- `GET  /api/products/{id}` — detail produk
- `POST /api/products` — buat produk baru
- `PATCH /api/products/{id}/stock?change=N` — update stok

## Build Lokal
```bash
./gradlew build
```

## Docker
```bash
docker build -t java-backend-stock-api .
docker run -p 8080:8080 java-backend-stock-api
```

## CI/CD
GitHub Actions otomatis build + test + push image ke `ghcr.io`.