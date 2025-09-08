# Exhibition Service

Guidely MSA의 두 번째 서비스로, 전시회 및 작품 정보 관리 기능을 제공합니다.

## 🚀 기능

### Exhibition (전시회)
- 전시회 목록 조회 (`GET /exhibitions`)
- 전시회 상세 조회 (`GET /exhibitions/{id}`)
- 상태별 전시회 조회 (`GET /exhibitions/status/{status}`)
- 제목으로 전시회 검색 (`GET /exhibitions/search?title={title}`)

### Artwork (작품)
- 작품 목록 조회 (`GET /artworks`)
- 작품 상세 조회 (`GET /artworks/{id}`)
- 작품 생성 (`POST /artworks`) - 관리자용
- 작품 정보 수정 (`PATCH /artworks/{id}`)
- 전시회별 작품 조회 (`GET /artworks/exhibition/{exhibitionId}`)
- 제목으로 작품 검색 (`GET /artworks/search/title?title={title}`)
- 작가로 작품 검색 (`GET /artworks/search/artist?artist={artist}`)
- 인기 작품 조회 (`GET /artworks/top/likes`)

## 🛠 기술 스택

- **Spring Boot**: 3.5.5
- **Java**: 17
- **Gradle**: Groovy
- **JPA/Hibernate**: 데이터베이스 연동
- **H2 Database**: 개발/테스트용 인메모리 DB
- **MySQL Driver**: 운영 환경용 DB
- **Lombok**: 코드 간결화
- **Spring Boot Actuator**: 모니터링

## 📁 프로젝트 구조

```
src/main/java/com/guidely/exhibitionservice/
├── ExhibitionServiceApplication.java    # 메인 애플리케이션 클래스
├── config/
│   └── JpaConfig.java                  # JPA 설정
├── controller/
│   ├── ExhibitionController.java       # 전시회 REST API 컨트롤러
│   └── ArtworkController.java          # 작품 REST API 컨트롤러
├── service/
│   ├── ExhibitionService.java          # 전시회 비즈니스 로직
│   └── ArtworkService.java             # 작품 비즈니스 로직
├── repository/
│   ├── ExhibitionRepository.java       # 전시회 데이터 접근 계층
│   └── ArtworkRepository.java          # 작품 데이터 접근 계층
├── entity/
│   ├── Exhibition.java                 # 전시회 엔티티
│   └── Artwork.java                    # 작품 엔티티
├── dto/
│   ├── ExhibitionResponse.java         # 전시회 응답 DTO
│   ├── ArtworkResponse.java            # 작품 응답 DTO
│   ├── ArtworkCreateRequest.java       # 작품 생성 요청 DTO
│   └── ArtworkUpdateRequest.java       # 작품 수정 요청 DTO
└── exception/
    ├── ExhibitionNotFoundException.java # 전시회 없음 예외
    └── ArtworkNotFoundException.java    # 작품 없음 예외
```

## 🏃‍♂️ 실행 방법

### 1. 프로젝트 빌드
```bash
./gradlew build
```

### 2. 애플리케이션 실행
```bash
./gradlew bootRun
```

### 3. 개발 서버 접속
- 애플리케이션: http://localhost:8083
- H2 콘솔: http://localhost:8083/h2-console
- Actuator: http://localhost:8083/actuator

## 📋 API 명세

### 전시회 API

#### 모든 전시회 조회
```http
GET /exhibitions
```

#### 전시회 상세 조회
```http
GET /exhibitions/{id}
```

#### 상태별 전시회 조회
```http
GET /exhibitions/status/{status}
```

#### 제목으로 전시회 검색
```http
GET /exhibitions/search?title={title}
```

### 작품 API

#### 모든 작품 조회
```http
GET /artworks
```

#### 작품 상세 조회
```http
GET /artworks/{id}
```

#### 작품 생성 (관리자)
```http
POST /artworks
Content-Type: application/json

{
  "title": "작품 제목",
  "description": "작품 설명",
  "artist": "작가명",
  "creationYear": 2023,
  "medium": "oil",
  "style": "impressionism",
  "imageUrl": "https://example.com/image.jpg",
  "keywords": "키워드1,키워드2,키워드3",
  "exhibitionId": 1
}
```

#### 작품 정보 수정
```http
PATCH /artworks/{id}
Content-Type: application/json

{
  "title": "수정된 제목",
  "description": "수정된 설명"
}
```

#### 전시회별 작품 조회
```http
GET /artworks/exhibition/{exhibitionId}
```

#### 제목으로 작품 검색
```http
GET /artworks/search/title?title={title}
```

#### 작가로 작품 검색
```http
GET /artworks/search/artist?artist={artist}
```

#### 인기 작품 조회
```http
GET /artworks/top/likes
```

## 🗄 데이터베이스

### 개발 환경
- H2 인메모리 데이터베이스 사용
- 애플리케이션 시작 시 테이블 자동 생성
- 애플리케이션 종료 시 데이터 삭제

### 운영 환경
- MySQL 데이터베이스 사용
- `application.yml`에서 주석 처리된 설정을 활성화하여 사용

## 🔧 설정

### 포트 변경
`application.yml`에서 `server.port` 값을 수정하여 포트를 변경할 수 있습니다.

### 데이터베이스 변경
1. `application.yml`에서 H2 설정을 주석 처리
2. MySQL 설정의 주석을 해제
3. 데이터베이스 연결 정보 수정

## 📊 모니터링

Spring Boot Actuator를 통해 다음 엔드포인트를 제공합니다:
- `/actuator/health`: 애플리케이션 상태 확인
- `/actuator/info`: 애플리케이션 정보
- `/actuator/metrics`: 메트릭 정보

## 🧪 테스트

```bash
./gradlew test
```

## 📝 로그

애플리케이션 로그는 다음과 같이 설정되어 있습니다:
- SQL 쿼리 로그 출력
- 요청/응답 로그 출력
- 에러 로그 상세 출력

## 🔗 다른 서비스와의 연동

- **User Service**: 사용자 정보 참조
- **Engagement Service**: 좋아요/싫어요 카운트 업데이트
- **Statistics Service**: 인기 작품 통계 제공
- **Conversation Service**: 작품 정보 기반 대화 
