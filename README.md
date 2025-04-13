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
- **커뮤니티 API 개발**
    - 게시판 CRUD 및 조건 기반 검색(QueryDSL) 구현
- **데이터 저장 구조 설계 및 복합 환경 구성**
    - MongoDB + MySQL 혼합 구조 설계
- **Elasticsearch 기반 검색 기능 개발 중**
    - 정교한 필터링과 자동완성 기능 포함 예정

---

### 🛠 사용한 기술 스택

- `Java`, `Spring Boot`, `JPA`, `QueryDSL`, `RabbitMQ`, `Python`, `Elasticsearch`
- `Vue.js`
- `MongoDB`, `MySQL`, `Redis`
- `Docker`, `AWS`, `Git/GitHub`, `GitHub Actions`
- `Swagger`

---

## 📌 초기 기획 및 설계

- **API 설계 및 데이터베이스 모델링** → ERD(Entity-Relationship Diagram) 작성
- SW 아키텍처 다이어그램

---

## 🔧 개발 단계

### 🏗 백엔드 기능 구현

### 📌 콘텐츠 데이터 크롤링 및 저장

- **Steam Web API** 호출 + 크롤링 자동화
- **Python 크롤러 컨테이너화** (Docker-Compose)
- MongoDB Unique Index 적용 → appId 중복 방지

### 📌 상세 조회 및 비동기 저장

- 사용자 요청 시 **즉시 응답 → RabbitMQ로 저장 요청 비동기 처리**
- Spring AMQP + RabbitMQ를 통한 이벤트 처리 구조 구현
- 외부 API 호출과 저장을 분리해 응답 속도 개선

### 📌 커뮤니티 API

- **게시판 CRUD**: 게시글/댓글 작성, 조회, 수정, 삭제
- **QueryDSL을 통한 조건별 검색 구현**:
    - 작성자, 키워드, 날짜 등 기준으로 필터링 가능

### 📌 검색 기능 (진행 중)

- Elasticsearch 기반 검색 서버 구성 중
- 향후 리뷰, 태그, 가격, 장르 기반 정렬 및 추천 시스템 확장 예정

---

## ☁️ CI/CD 및 배포 환경

- **Docker 환경 구성**
    - 백엔드, MongoDB, MySQL, Redis, 크롤러 컨테이너 관리
- **Docker-Compose를 통한 로컬 개발 환경 통합**
- **GitHub Actions 기반 CI 구성**
    - 코드 변경 시 자동 빌드/테스트, 린팅 적용
- **AWS 기반 배포 예정**

---

### 🛠 이슈 및 트러블슈팅

### **MongoDB 사용 결정**

- **문제:** RDBMS만 사용해왔으나, 크롤링 데이터 구조가 다양하여 스키마 통합이 어려웠음.
- **해결:** 조회가 많고 업데이트가 적은 데이터 특성을 고려해 **NoSQL(MongoDB) 도입**, 대량 데이터 저장과 분산 처리가 용이함.

### **MongoDB 엔티티 빌더 패턴 사용 문제**

- **문제:** 크롤링 데이터의 필드 수가 많고 일부 필드는 Null 값이 많아, 빌더 패턴 사용 시 필수 인자 누락 위험 존재.
- **해결:** **정적 팩토리 메소드**를 활용하여 객체 생성 로직을 개선, 필수 필드 누락 방지.

### 게임 상세정보 조회 : 즉시응답 + 비동기 저장 (RabbitMQ 메시지 큐)

- **문제:** 외부 API에서 데이터를 받은 후 DB에 저장하고 응답하는 구조라, 전체 응답 시간이 길어짐
- **해결:** RabbitMQ를 통해 저장 작업을 비동기 처리하고, API 응답은 즉시 반환하도록 구조 개선
