# 🎮 DoD (Devil of Data)

**"누구나 게임 평론가가 될 수 있는 데이터 기반 게임 커뮤니티"**

게임 데이터를 수집하고 시각화하며, 유저가 직접 평가하고 토론할 수 있는 플랫폼입니다.  
Steam API 및 크롤링을 통해 수집한 데이터를 기반으로 다양한 기능을 제공합니다.
이후 게임 이외의 분야도 추가할 예정입니다.

📅 **2024.08 ~ 현재 진행 중**

👤 **개인 프로젝트**

🔗 [GitHub Repository](https://github.com/dahoon7151/DoD)

### 🔧 프로젝트에서 맡은 작업

- **콘텐츠 크롤링 자동화 및 API 연동**
    - Steam Web API와 Python 크롤러를 통해 메타데이터 수집
    - Docker-Compose 기반 자동화 및 파이프라인 구성
- **게임/콘텐츠 상세 정보 API 구현**
    - 외부 API 연동 + 메시지 큐(RabbitMQ) 활용한 비동기 저장 처리
- **리뷰 API 개발**
    - 게임 리뷰 CRUD 및 QueryDSL 기반 정렬/필터링 추가
- **데이터 저장 구조 설계 및 복합 환경 구성**
    - MongoDB + MySQL 혼합 구조 설계
- **Elasticsearch 기반 게임 검색 기능**

---

### 🛠 사용한 기술 스택

- `Java`, `Spring Boot`, `JPA`, `QueryDSL`, `RabbitMQ`, `Python`, `Elasticsearch`
- `Vue.js`
- `MongoDB`, `MySQL`, `Redis`
- `Docker`, `AWS`, `Git/GitHub`, `GitHub Actions`
- `Swagger`

---

## 🔧 개발 단계

### 🏗 백엔드 기능 구현

### 📌 콘텐츠 데이터 크롤링 및 저장

- **Steam Web API** 호출 + 크롤링 자동화
- **Python 크롤러 컨테이너화** (Docker-Compose)
- MongoDB Unique Index 적용 → `appId` 중복 방지

### 📌 게임 정보 조회

- 게임 요약 정보 → **Elasticsearch 검색용 Index**
- 상세 정보 요청 시 외부 API 요청 후 **즉시 응답 → RabbitMQ로 저장 요청 비동기 처리**
- Spring AMQP + RabbitMQ를 통한 이벤트 처리 구조 구현
- 외부 API 호출과 저장을 분리해 응답 속도 개선

### 📌커뮤니티 API

- **리뷰 CRUD**: 게시글/댓글 작성, 조회, 수정, 삭제
- **QueryDSL을 통한 조건별 검색**
- **좋아요, 조회수** 정합성 유지

---

## ☁️ CI/CD 및 배포 환경

- **Docker 환경 구성**
    - 백엔드, MongoDB, MySQL, Redis, 크롤러 컨테이너 관리
- **Docker-Compose를 통한 로컬 개발 환경 통합**
- **GitHub Actions 기반 CI 구성**
    - 코드 변경 시 자동 빌드/테스트, 린팅 적용
- **AWS 기반 배포 예정**

---
