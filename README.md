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

### 1. 클라이언트 보안 및 인가

- **[카카오톡 보안 인증](https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api)**: 카카오톡에서 토큰을 받아 사용자를 인증하고, 우리 서버에서 유효성 검사 후 JWT 토큰을 생성합니다.
  - **인증 과정**: 
    1. 카카오톡 토큰을 사용하여 사용자 인증
    2. 서버에서 실제 유저 확인 및 검증
    3. JWT 액세스 토큰 및 리프레시 토큰 생성
  - **역할 기반 접근 제어**: ADMIN, ROLE 등의 역할 확인
- **OAuth 2.0**: 권한 부여 프레임워크를 사용한 안전한 자원 접근

### 2. HTTPS 설정
<img src="https://github.com/user-attachments/assets/571c820e-bda7-467e-a8a3-1425bd9aa40c" alt="보안 다이어그램" width="500"/>

- **배포 구성**: Application Load Balancer를 활용한 배포
- **도메인 및 인증서 설정**:
  - **Route 53을 통해 도메인 구매**: AWS Route 53을 통해 도메인을 구매하고 관리합니다.
  - **ACM 인증서 적용**: AWS Certificate Manager(ACM)를 사용하여 SSL/TLS 인증서를 생성하고 관리합니다.
  - **HTTPS 설정**: 
    - ACM에서 생성한 인증서를 Application Load Balancer(ELB)에 적용하여 HTTPS를 통해 안전하게 통신할 수 있도록 합니다.
    - 클라이언트는 Route 53에서 설정된 도메인을 통해 Application Load Balancer와 통신합니다.
    - 이를 통해 모든 트래픽은 HTTPS를 통해 암호화되어 전송됩니다.






## 🚀 배포

경단의 2개의 백엔드 서버와 1개의 NextJS 서버는 GitHub Actions를 사용하여 자동화된 배포를 진행합니다. 

### ELB를 통한 무중단 배포
- **롤링 업데이트**: 롤링 업데이트는 애플리케이션의 새로운 버전을 순차적으로 배포하여 무중단 배포를 가능하게 합니다. 새로운 인스턴스를 생성하고, 기존 인스턴스와 교체하여 서비스 중단 없이 배포를 완료합니다.
- **동적 포트 매핑**: 동적 포트 매핑을 통해 새로 배포된 애플리케이션 인스턴스가 사용 중인 포트를 자동으로 할당받아 충돌 없이 동작할 수 있습니다. 이를 통해 새로운 인스턴스가 정상적으로 실행되고 있는지 확인할 수 있습니다.

**경단 무중단 배포 프로세스:**
1. **코드 푸시 및 빌드**: GitHub Actions를 통해 코드가 푸시되면 자동으로 빌드 및 테스트가 수행
2. **Docker 이미지 생성 및 배포**: 성공적으로 빌드된 애플리케이션은 Docker 이미지를 생성하고, Amazon ECR(Amazon Elastic Container Registry)에 푸시
3. **Amazon ECS 및 ELB 설정**:
   - Amazon ECS(Amazon Elastic Container Service)를 사용하여 새로운 Docker 컨테이너 인스턴스를 시작
   - ELB(Elastic Load Balancer)는 새로운 인스턴스의 상태를 모니터링하고, 헬스체크(health check)를 통해 인스턴스가 정상적으로 동작하는지 확인
4. **포트 매핑 및 트래픽 전환**:
   - 동적 포트 매핑을 통해 새로운 인스턴스가 기존 인스턴스와 포트 충돌 없이 실행
   - 새로운 인스턴스가 정상적으로 동작하면, ELB는 트래픽을 기존 인스턴스에서 새로운 인스턴스로 점진적으로 전환
5. **기존 인스턴스 종료**:
   - 새로운 인스턴스가 안정적으로 서비스 중인 경우, 기존 인스턴스는 점진적으로 종료
   - 이 과정은 서비스 중단 없이 완료

### 지속적 통합 및 배포(CI/CD)

- **GitHub Actions**: GitHub Actions 워크플로우를 통해 코드 변경 시마다 자동으로 빌드, 테스트, 배포 수행
- **Slack 알림**: 배포 시작 및 완료 시 Slack 채널에 알림을 보내어 팀원들에게 배포 상태를 실시간으로 공유
- **Docker**: 애플리케이션을 컨테이너로 패키징하여 일관된 환경에서 실행
- **Amazon ECR**: 생성된 이미지를 안전하게 저장하고 배포 시 사용합
- **Amazon ECS**: 여러 컨테이너를 관리하고 확장
- **Amazon ELB**: ELB를 통해 헬스체크를 통해 인스턴스의 상태를 모니터링하고, 무중단 배포
- **Route 53**: ELB와 함께 사용하여 트래픽을 라우팅
- **Vercel**: 프론트엔드 main 브랜치 코드 변경 시 자동으로 배포되어 최신 상태를 유지(프론트만 해당)

코드 변경 후 자동으로 배포가 이루어지며, 서비스 중단 없이 최신 버전의 애플리케이션을 사용자에게 제공



### 백엔드 배포 절차

![스크린샷 2024-07-14 오전 3 21 12](https://github.com/user-attachments/assets/346b35f9-2a26-4833-95c7-28bb4ad847b4)


1. **GitHub Actions**: staging 브랜치에 코드가 푸시되면, 배포 시작을 Slack 채널에 알림
2. **Docker 이미지 생성**: Docker로 이미지 빌드
3. ECR 업로드: 생성된 Docker 이미지를 Amazon ECR에 업로드

### 프론트엔드 배포 절차

![스크린샷 2024-07-14 오전 3 20 48](https://github.com/user-attachments/assets/3edad8c5-4be3-4350-95b3-f3f8e230a077)

1. Vercel: staging 브랜치에 코드가 머지되면, Vercel을 통해 자동 배포

### 데이터베이스 배포
1. PostgreSQL: 데이터베이스로 사용
2. AWS RDS: PostgreSQL 데이터베이스를 AWS RDS에 배포
   - AWS RDS 콘솔에서 PostgreSQL 인스턴스를 생성하고 설정
   - Spring Boot 애플리케이션에서 데이터베이스 연결 설정

## 🔧 서버 구성
경단 프로젝트는 클라이언트, Spring 서버, FastAPI 서버로 구성됩니다.

<img width="847" alt="Screenshot 2024-07-18 at 5 17 12 PM" src="https://github.com/user-attachments/assets/613f1574-c794-4713-b2ce-2e4ff585404e">


- 클라이언트 (NextJS): 사용자의 요청을 Spring 서버에 전달하고, Spring 서버의 응답을 받아 처리합니다.
- Spring 서버: 클라이언트 요청을 처리하고, 인증 및 인가를 담당합니다. FastAPI 서버와 협력하여 예측, 추천 시스템, 기사 재생성 등의 작업을 수행합니다.
- FastAPI 서버: 주로 스케줄링된 작업을 수행하며, PostgreSQL에 있는 정보를 기반으로 외부 기사나 Google Custom Search Engine(CSE) 등을 호출하여 예측, 추천 시스템, 기사 재생성 작업을 수행합니다.

