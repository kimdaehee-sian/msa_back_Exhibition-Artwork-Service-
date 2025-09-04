# 🐳 Docker Hub 푸시 및 배포 가이드

이 문서는 Guidely Exhibition Service를 Docker Hub에 푸시하고 배포하는 방법을 설명합니다.

## 📋 사전 준비사항

1. **Docker Hub 계정**: https://hub.docker.com 에서 계정 생성
2. **Docker 로그인**: `docker login` 명령어로 로그인
3. **Docker Desktop**: 실행 중이어야 함

## 🔐 Docker Hub 로그인

```bash
docker login
# Username: yerak213
# Password: [Docker Hub 비밀번호 입력]
```

## 🚀 Docker Hub에 푸시하기

### 1. 자동 스크립트 사용 (권장)

#### Windows 사용자
```cmd
# 최신 버전 푸시
docker-push.bat

# 특정 버전 푸시
docker-push.bat v1.0.0
```

#### Linux/Mac 사용자
```bash
# 실행 권한 부여
chmod +x docker-push.sh

# 최신 버전 푸시
./docker-push.sh

# 특정 버전 푸시
./docker-push.sh v1.0.0
```

### 2. 수동으로 푸시하기

```bash
# 1. 이미지 빌드 및 태깅
docker build -t yerak213/guidely-exhibition-service:latest .
docker build -t yerak213/guidely-exhibition-service:v1.0.0 .

# 2. Docker Hub에 푸시
docker push yerak213/guidely-exhibition-service:latest
docker push yerak213/guidely-exhibition-service:v1.0.0
```

## 🏷️ 버전 태깅 전략

### 권장 태깅 방식

```bash
# 메이저.마이너.패치
v1.0.0    # 첫 번째 정식 릴리즈
v1.0.1    # 버그 수정
v1.1.0    # 새로운 기능 추가
v2.0.0    # 호환성 깨짐이 있는 큰 변경

# 개발 버전
v1.0.0-alpha.1    # 알파 버전
v1.0.0-beta.1     # 베타 버전
v1.0.0-rc.1       # 릴리즈 후보
```

### 태깅 예시

```bash
# 개발 버전
./docker-push.sh v1.0.0-alpha.1

# 정식 릴리즈
./docker-push.sh v1.0.0

# 최신 버전 (latest)
./docker-push.sh
```

## 📥 Docker Hub에서 이미지 가져오기

### 1. 이미지 다운로드

```bash
# 최신 버전
docker pull yerak213/guidely-exhibition-service:latest

# 특정 버전
docker pull yerak213/guidely-exhibition-service:v1.0.0
```

### 2. docker-compose로 실행

```bash
# Docker Hub 이미지 사용
docker-compose -f docker-compose-hub.yml up -d
```

### 3. 직접 실행

```bash
# 최신 버전 실행
docker run -p 8082:8082 yerak213/guidely-exhibition-service:latest

# 특정 버전 실행
docker run -p 8082:8082 yerak213/guidely-exhibition-service:v1.0.0
```

## 🔍 Docker Hub에서 확인하기

### 이미지 URL
- **Repository**: https://hub.docker.com/r/yerak213/guidely-exhibition-service
- **Tags**: https://hub.docker.com/r/yerak213/guidely-exhibition-service/tags

### 이미지 정보 확인

```bash
# 로컬 이미지 목록
docker images yerak213/guidely-exhibition-service

# 이미지 상세 정보
docker inspect yerak213/guidely-exhibition-service:latest

# 이미지 히스토리
docker history yerak213/guidely-exhibition-service:latest
```

## 📊 이미지 관리

### 로컬 이미지 정리

```bash
# 사용하지 않는 이미지 삭제
docker image prune

# 모든 이미지 삭제 (주의!)
docker image prune -a

# 특정 이미지 삭제
docker rmi yerak213/guidely-exhibition-service:v1.0.0
```

### 태그 관리

```bash
# 새로운 태그 추가
docker tag yerak213/guidely-exhibition-service:latest yerak213/guidely-exhibition-service:stable

# 태그 삭제
docker rmi yerak213/guidely-exhibition-service:stable
```

## 🔄 CI/CD 파이프라인 연동

### GitHub Actions 예시

```yaml
name: Build and Push to Docker Hub

on:
  push:
    tags:
      - 'v*'

jobs:
  build-and-push:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      
      - name: Login to Docker Hub
        uses: docker/login-action@v2
        with:
          username: ${{ secrets.DOCKER_USERNAME }}
          password: ${{ secrets.DOCKER_PASSWORD }}
      
      - name: Build and push
        uses: docker/build-push-action@v4
        with:
          push: true
          tags: |
            yerak213/guidely-exhibition-service:latest
            yerak213/guidely-exhibition-service:${{ github.ref_name }}
```

## 🚨 주의사항

### 1. 보안
- **민감한 정보**: 환경 변수나 비밀번호를 이미지에 포함하지 마세요
- **최소 권한**: 필요한 최소한의 권한만 부여하세요

### 2. 이미지 크기
- **멀티스테이지 빌드**: 사용하여 최종 이미지 크기 최소화
- **불필요한 파일**: .dockerignore로 제외

### 3. 버전 관리
- **latest 태그**: 항상 최신 안정 버전을 가리키도록 유지
- **의존성**: 이미지 간 의존성 관리 주의

## 📚 유용한 명령어 모음

```bash
# Docker Hub 로그인 상태 확인
docker info | grep Username

# 이미지 빌드
docker build -t yerak213/guidely-exhibition-service:latest .

# 이미지 푸시
docker push yerak213/guidely-exhibition-service:latest

# 이미지 풀
docker pull yerak213/guidely-exhibition-service:latest

# 이미지 실행
docker run -p 8082:8082 yerak213/guidely-exhibition-service:latest

# 컨테이너 로그 확인
docker logs <container_id>

# 컨테이너 상태 확인
docker ps -a
```

## 🆘 문제 해결

### 일반적인 문제들

#### 1. 인증 실패
```bash
# Docker Hub 재로그인
docker logout
docker login
```

#### 2. 푸시 권한 없음
- Docker Hub에서 저장소 생성 확인
- 사용자명과 저장소명 일치 확인

#### 3. 이미지 크기 제한
- Docker Hub 무료 계정: 1개 저장소, 6개월 미사용 시 삭제
- 유료 계정: 더 많은 저장소와 저장 기간

## 📞 지원

문제가 발생하면 다음을 확인하세요:

1. **Docker 로그인 상태**: `docker info | grep Username`
2. **네트워크 연결**: 인터넷 연결 상태
3. **저장소 권한**: Docker Hub 저장소 접근 권한
4. **이미지 크기**: Docker Hub 계정 제한 확인 