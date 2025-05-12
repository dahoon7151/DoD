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

### 🛠 이슈 및 트러블슈팅

### **MongoDB 엔티티 빌더 패턴 사용 문제**

- **문제:** 크롤링 데이터의 필드 수가 많고 일부 필드는 `Null` 값이 많아, 빌더 패턴 사용 시 필수 인자 누락 위험 존재.
- **해결:** **정적 팩토리 메소드**를 활용하여 객체 생성 로직을 개선, 필수 필드 누락 방지.

### 게임 상세정보 조회 : 즉시응답 + 비동기 저장 (RabbitMQ 메시지 큐)

- **문제:** 외부 API에서 데이터를 받은 후 DB에 저장하고 응답하는 구조라, 전체 응답 시간이 길어짐
- **해결:** RabbitMQ를 통해 저장 작업을 비동기 처리하고, API 응답은 즉시 반환하도록 구조 개선

### 수정·삭제 로직의 정합성 설계

- **문제:** JPA 조회-수정 방식에서 **삭제 후 덮어쓰기** 및 **Lost Update** 가능성 존재
- **해결:**
    - **QueryDSL 기반 단일 쿼리**로 수정·삭제를 처리하여 **영속성 컨텍스트를 우회**하여 DB 상태만을 기준으로 원자적으로 반영되도록 설계
    - **한 트랜잭션 내에서는 하나의 단일 쿼리만 수행**되도록 하고, 트랜잭션 간 일시적인 조회 불일치(약한 정합성)는 허용 가능한 수준으로 판단하여 허용

### Soft Delete 적용 및 정합성 + 성능 최적화

- **문제:** Soft Delete를 도입함에 따라, JPA 기본 메서드와 QueryDSL 모두에서 `deleted` 필드를 조건에 포함해야 했고, 이에 따라 **쿼리 정합성 유지 및 성능 저하** 가능성 존재
- **해결:**
    - JPA에서는 `@SQLRestriction("deleted = false")`를 통해 Soft Delete 필터링을 자동화
    - QueryDSL에서는 직접 `deleted.isFalse()` 조건을 명시하여 정합성 유지
    - `deleted`는 **boolean 타입으로 카디널리티가 낮아**, 단독 인덱싱시 성능 효율이 떨어지기 때문에   `id + deleted` 조합으로 **복합 인덱스**를 설정
