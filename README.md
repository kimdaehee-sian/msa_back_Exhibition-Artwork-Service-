# Exhibition-Artwork-Service

### 서비스명: Exhibition-Artwork-Service
**역할**: 전시회 및 작품 정보 관리  
**포트**: 8080

---

## 📋 주요 기능

### 🎨 전시회 관리
- `GET /api/exhibitions` – 전시회 목록 조회
- `GET /api/exhibitions/{id}` – 전시회 상세 조회

### 🖼️ 작품 관리
- `GET /api/artworks` – 작품 목록 조회
- `GET /api/artworks/{id}` – 작품 상세 조회
- `GET /api/artworks/exhibition/{exhibitionId}` – 전시회별 작품 조회

### 🔧 관리자 기능
- `POST /api/admin/exhibitions` – 전시회 생성
- `PATCH /api/admin/exhibitions/{id}` – 전시회 수정
- `DELETE /api/admin/exhibitions/{id}` – 전시회 삭제
- `GET /api/admin/exhibitions/summaries` – 전시회 요약 목록
- `GET /api/admin/exhibitions/dropdown` – 전시회 드롭다운 옵션

- `POST /api/admin/artworks` – 작품 생성
- `PATCH /api/admin/artworks/{id}` – 작품 수정
- `DELETE /api/admin/artworks/{id}` – 작품 삭제
- `GET /api/admin/artworks/summaries` – 작품 요약 목록

---

## 🏃‍♂️ 실행 방법

### 로컬 실행
```bash
./gradlew bootRun
```

### Docker 실행
```bash
docker build -t exhibition-artwork-service .
docker run -p 8080:8080 exhibition-artwork-service
```

### 빌드
```bash
./gradlew clean build -x test
```

---

## 🌐 API 테스트

### Swagger UI
- **로컬**: http://localhost:8080/swagger-ui/index.html
- **Azure**: https://guidely-exhibition-artwork-services-dmeagqebfud4e7hh.koreacentral-01.azurewebsites.net/swagger-ui/index.html

### OpenAPI 문서
- **로컬**: http://localhost:8080/v3/api-docs
- **Azure**: https://guidely-exhibition-artwork-services-dmeagqebfud4e7hh.koreacentral-01.azurewebsites.net/v3/api-docs

### 헬스 체크
- **로컬**: http://localhost:8080/actuator/health
- **Azure**: https://guidely-exhibition-artwork-services-dmeagqebfud4e7hh.koreacentral-01.azurewebsites.net/actuator/health

---

## 🗄️ 데이터베이스 정보

### 운영 환경 (Azure)
- **DB**: Azure MySQL
- **Host**: dbguidely-exhibition-artwork-services.mysql.database.azure.com
- **Database**: exhibition_db
- **Port**: 3306

### 로컬 개발 환경
- **DB**: MySQL (profile: mysql)
- **Host**: localhost:3306
- **Database**: exhibition_db

### Entity 구조

#### Exhibition (전시회)
```java
@Entity
public class Exhibition {
    private Long id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;
    private List<Artwork> artworks;
}
```

#### Artwork (작품)
```java
@Entity
public class Artwork {
    private Long id;
    private String title;
    private String artist;
    private String era;
    private String description;
    private String imageUrl;
    private Long exhibitionId;
    private LocalDateTime createdAt;
}
```

---

## 🛠️ 기술 스택

- **Spring Boot**: 3.5.5
- **Java**: 17
- **Gradle**: Build Tool
- **Spring Data JPA**: ORM
- **MySQL**: Database
- **SpringDoc OpenAPI**: API 문서화
- **Lombok**: 코드 간소화
- **Spring Boot Actuator**: 모니터링

---

## 📁 프로젝트 구조

```
src/main/java/com/guidely/exhibitionservice/
├── ExhibitionServiceApplication.java     # 메인 애플리케이션
├── config/
│   ├── JpaConfig.java                   # JPA 설정
│   ├── SwaggerConfig.java               # Swagger 설정
│   └── WebConfig.java                   # CORS 설정
├── controller/
│   ├── ExhibitionController.java        # 전시회 API
│   ├── ArtworkController.java           # 작품 API
│   ├── AdminExhibitionController.java   # 전시회 관리 API
│   └── AdminArtworkController.java      # 작품 관리 API
├── service/
│   ├── ExhibitionService.java           # 전시회 비즈니스 로직
│   └── ArtworkService.java              # 작품 비즈니스 로직
├── repository/
│   ├── ExhibitionRepository.java        # 전시회 데이터 접근
│   └── ArtworkRepository.java           # 작품 데이터 접근
├── entity/
│   ├── Exhibition.java                  # 전시회 엔티티
│   └── Artwork.java                     # 작품 엔티티
├── dto/
│   ├── ExhibitionResponse.java          # 전시회 응답 DTO
│   ├── ExhibitionCreateRequest.java     # 전시회 생성 요청
│   ├── ExhibitionUpdateRequest.java     # 전시회 수정 요청
│   ├── ArtworkResponse.java             # 작품 응답 DTO
│   ├── ArtworkCreateRequest.java        # 작품 생성 요청
│   └── ArtworkUpdateRequest.java        # 작품 수정 요청
└── exception/
    ├── GlobalExceptionHandler.java      # 전역 예외 처리
    ├── ExhibitionNotFoundException.java # 전시회 없음 예외
    ├── ArtworkNotFoundException.java    # 작품 없음 예외
    └── DuplicateTitleException.java     # 제목 중복 예외
```

---

## 🚀 배포 정보

### Azure App Service
- **URL**: https://guidely-exhibition-artwork-services-dmeagqebfud4e7hh.koreacentral-01.azurewebsites.net
- **리전**: Korea Central
- **CI/CD**: GitHub Actions

### 환경변수 (다른 서비스에서 사용)
```bash
VITE_EXHIBITION_ARTWORK_API_URL=https://guidely-exhibition-artwork-services-dmeagqebfud4e7hh.koreacentral-01.azurewebsites.net
```

---

## 👥 담당자

**김대희**  
- **담당 기능**: 전시회 및 작품 관리 시스템 전체
- **GitHub**: kimdaehee-sian
- **주요 작업**: 
  - Exhibition/Artwork CRUD API 개발
  - 관리자 기능 구현
  - Azure 배포 및 CI/CD 설정
  - Swagger UI 설정 및 CORS 해결

---

## 🔗 연관 서비스

- **Frontend**: 전시회/작품 정보를 표시하는 웹 인터페이스
- **Statistics Service**: 작품 통계 데이터 제공
- **User Service**: 사용자 정보 연동 (예정)
- **Engagement Service**: 좋아요/평점 기능 연동 (예정)

---

## 📊 모니터링

### Actuator Endpoints
- `/actuator/health` - 애플리케이션 상태
- `/actuator/info` - 애플리케이션 정보

### 로깅
- 애플리케이션 로그 레벨: INFO
- Spring Web 로그 레벨: WARN
- JPA 쿼리 로그: false (운영환경) 
