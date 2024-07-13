# 🍡 경단 Spring 서버 레포지토리

> 경단 프로젝트의 전반적인 서버 구성 및 배포, 인가에 관련된 기술적 개요

경단 Spring 서버는 클라이언트 요청 처리와 인증, 인가를 담당하는 백엔드 서버입니다.

## 📋 목차

1. [기술 스택](#-기술-스택)
2. [보안 및 인가](#-보안-및-인가)
3. [배포](#-배포)
4. [서버 구성](#-서버-구성)

## 🔧 기술 스택

경단 Spring 서버는 다음과 같은 기술 스택을 사용합니다:

- **Java**: 메인 프로그래밍 언어
- **Spring Boot**: 애플리케이션 프레임워크
- **Spring Security**: 인증 및 인가 처리
- **JWT (JSON Web Tokens)**: 토큰 기반 인증
- **Spring Data JPA**: 데이터 액세스 레이어
- **QueryDSL**: 타입 안전한 쿼리 작성 도구
- **Hibernate**: ORM(Object-Relational Mapping) 프레임워크
- **PostgreSQL**: 데이터베이스 관리 시스템(AWS RDS에 배포)
- **Gradle**: 빌드 관리 도구
- **Docker**: 컨테이너화 도구

## 🔒 보안 및 인가

경단 Spring 서버는 다음과 같은 보안 및 인가 메커니즘을 사용합니다:

- **[카카오톡 보안 인증](https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api)**: 카카오톡에서 토큰을 받아 사용자를 인증하고, 우리 서버에서 유효성 검사 후 JWT 토큰 생성
  - **인증 과정**: 카카오톡 토큰을 사용하여 사용자 인증 → 서버에서 실제 유저 확인 및 검증 → JWT 액세스 토큰 및 리프레시 토큰 생성
  - **역할 기반 접근 제어**: ADMIN, ROLE 등의 역할 확인
- **OAuth 2.0**: 권한 부여 프레임워크를 사용한 안전한 자원 접근

## 🚀 배포

경단 Spring 서버는 GitHub Actions를 사용하여 자동화된 배포를 진행합니다. 백엔드는 Docker를 사용하여 컨테이너화된 이미지를 Amazon ECR에 올리고, Amazon ECS를 통해 Gyeongdan 클러스터에 배포합니다. 프론트엔드는 Vercel을 사용하여 배포합니다.


### 백엔드 배포 절차
![스크린샷 2024-07-14 오전 2 19 05](https://github.com/user-attachments/assets/f961f9e8-3894-4ef3-ba42-83b16f4931ce)

1. **GitHub Actions**: staging 브랜치에 코드가 푸시되면, 배포 시작을 Slack 채널에 알림
2. **Docker 이미지 생성**: Docker로 이미지 빌드
3. ECR 업로드: 생성된 Docker 이미지를 Amazon ECR에 업로드

### 프론트엔드 배포 절차
![스크린샷 2024-07-14 오전 2 18 40](https://github.com/user-attachments/assets/8dd794d8-7f99-4636-8ff3-bcdb03c3abf4)
1. Vercel: staging 브랜치에 코드가 머지되면, Vercel을 통해 자동 배포

### 데이터베이스 배포
1. PostgreSQL: 데이터베이스로 사용
2. AWS RDS: PostgreSQL 데이터베이스를 AWS RDS에 배포
   - AWS RDS 콘솔에서 PostgreSQL 인스턴스를 생성하고 설정
   - Spring Boot 애플리케이션에서 데이터베이스 연결 설정

### 지속적 통합 및 배포(CI/CD)
- GitHub Actions: 코드 푸시에 대한 자동 빌드 및 테스트
- Slack 알림: 배포 시작 및 완료 시 Slack 채널에 알림
- Docker: 컨테이너 이미지 생성 및 관리
- Amazon ECR: Docker 이미지 저장소
- Amazon ECS: 컨테이너 오케스트레이션 및 배포 자동화
- Vercel: 프론트엔드 자동 배포

## 🔧 서버 구성
경단 프로젝트는 클라이언트, Spring 서버, FastAPI 서버로 구성됩니다.

![스크린샷 2024-07-14 오전 3 12 23](https://github.com/user-attachments/assets/51a00b46-dc10-4659-aadc-b09c4da981b0)

- 클라이언트 (NextJS): 사용자의 요청을 Spring 서버에 전달하고, Spring 서버의 응답을 받아 처리합니다.
- Spring 서버: 클라이언트 요청을 처리하고, 인증 및 인가를 담당합니다. FastAPI 서버와 협력하여 예측, 추천 시스템, 기사 재생성 등의 작업을 수행합니다.
- FastAPI 서버: 스케줄링 작업을 수행하며, PostgreSQL에 있는 정보를 기반으로 외부 기사나 Google Custom Search Engine(CSE) 등을 호출하여 예측, 추천 시스템, 기사 재생성 작업을 수행합니다.

  
