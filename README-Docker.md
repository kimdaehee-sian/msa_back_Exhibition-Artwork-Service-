# Guidely Exhibition Service - Docker 가이드

이 문서는 Guidely Exhibition Service를 Docker로 실행하는 방법을 설명합니다.

## 🐳 사전 요구사항

- Docker Desktop (Windows/Mac) 또는 Docker Engine (Linux)
- Docker Compose
- 최소 4GB RAM (MySQL + Redis + 애플리케이션)

## 🚀 빠른 시작

### 1. Docker 이미지 빌드 및 실행

#### Linux/Mac 사용자
```bash
chmod +x docker-build.sh
./docker-build.sh
```

#### Windows 사용자
```cmd
docker-build.bat
```

### 2. 수동으로 실행하기

#### 1단계: Docker 이미지 빌드
```bash
docker build -t guidely-exhibition-service .
```

#### 2단계: 서비스 시작
```bash
docker-compose up -d
```

## 📋 서비스 구성

### 실행되는 서비스들

| 서비스 | 포트 | 설명 |
|--------|------|------|
| Exhibition Service | 8082 | 메인 애플리케이션 |
| MySQL | 3306 | 데이터베이스 |
| Redis | 6379 | 캐시 (선택사항) |

### 환경 변수

- `SPRING_PROFILES_ACTIVE=docker`: Docker 프로파일 사용
- `SPRING_DATASOURCE_URL`: MySQL 연결 URL
- `SPRING_DATASOURCE_USERNAME`: MySQL 사용자명
- `SPRING_DATASOURCE_PASSWORD`: MySQL 비밀번호

## 🔧 유용한 명령어

### 서비스 상태 확인
```bash
docker-compose ps
```

### 로그 확인
```bash
# 모든 서비스 로그
docker-compose logs -f

# 특정 서비스 로그
docker-compose logs -f exhibition-service
docker-compose logs -f mysql
```

### 서비스 중지
```bash
docker-compose down
```

### 서비스 재시작
```bash
docker-compose restart
```

### 이미지 재빌드 및 재시작
```bash
docker-compose up -d --build
```

## 🗄️ 데이터베이스

### MySQL 접속 정보
- **Host**: localhost
- **Port**: 3306
- **Database**: guidely_exhibition_db
- **Username**: guidely
- **Password**: guidely123
- **Root Password**: password

### 데이터 영속성
- MySQL 데이터는 `mysql_data` 볼륨에 저장됩니다
- 컨테이너를 삭제해도 데이터는 유지됩니다

## 🧪 API 테스트

서비스가 실행되면 다음 URL로 테스트할 수 있습니다:

- **Health Check**: http://localhost:8082/actuator/health
- **API Info**: http://localhost:8082/actuator/info
- **전시회 목록**: http://localhost:8082/exhibitions
- **작품 목록**: http://localhost:8082/artworks

## 🔍 문제 해결

### 일반적인 문제들

#### 1. 포트 충돌
```bash
# 사용 중인 포트 확인
netstat -an | grep 8082
netstat -an | grep 3306

# 다른 포트 사용
# docker-compose.yml에서 ports 섹션 수정
```

#### 2. 메모리 부족
```bash
# Docker Desktop 설정에서 메모리 증가
# 최소 4GB 권장
```

#### 3. 데이터베이스 연결 실패
```bash
# MySQL 컨테이너 상태 확인
docker-compose logs mysql

# MySQL 컨테이너 재시작
docker-compose restart mysql
```

### 로그 분석
```bash
# 애플리케이션 로그
docker-compose logs exhibition-service

# MySQL 로그
docker-compose logs mysql

# Redis 로그
docker-compose logs redis
```

## 🗂️ 파일 구조

```
exhibition-service/
├── Dockerfile                 # Docker 이미지 정의
├── docker-compose.yml         # 서비스 오케스트레이션
├── .dockerignore             # Docker 빌드 제외 파일
├── docker-build.sh           # Linux/Mac 빌드 스크립트
├── docker-build.bat          # Windows 빌드 스크립트
├── src/main/resources/
│   └── application-docker.yml # Docker 환경 설정
└── mysql/
    └── init/                 # MySQL 초기화 스크립트
        └── 01-init.sql
```

## 📚 추가 정보

- [Spring Boot Docker 가이드](https://spring.io/guides/gs/spring-boot-docker/)
- [Docker Compose 문서](https://docs.docker.com/compose/)
- [MySQL Docker 이미지](https://hub.docker.com/_/mysql)

## 🆘 지원

문제가 발생하면 다음을 확인하세요:

1. Docker 로그 확인
2. 서비스 상태 확인
3. 포트 충돌 여부 확인
4. 메모리 사용량 확인 