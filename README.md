# 🎮 DoD (Devil of Data)

**"누구나 게임 평론가가 될 수 있는 데이터 기반 게임 커뮤니티"**

게임 데이터를 수집하고 시각화하며, 유저가 직접 평가하고 토론할 수 있는 플랫폼입니다.  
Steam API 및 크롤링을 통해 수집한 데이터를 기반으로 다양한 기능을 제공합니다.
이후 게임 이외의 분야도 추가할 예정입니다.

---

## 🧠 주요 목표

- 개발 역량 향상을 위한 실전형 토이 프로젝트
- 실용적인 아키텍처 설계와 새로운 기술 도입 (e.g. 메시지 큐)
- 개인화된 게임 수집 기능 (티어리스트, Topster 등)
- 유저 기반 리뷰, 커뮤니티 기능 제공

---

## ⚙️ 기술 스택

### 📦 백엔드
- Java 17, Spring Boot
- Spring Security + JWT
- JPA + QueryDSL
- MySQL (유저, 리뷰, 커뮤니티)
- MongoDB (게임 데이터)
- Redis (캐시, 실시간 랭킹)
- RabbitMQ (비동기 처리 예정)

### 🌐 프론트엔드
- Vue.js 3 (Composition API)
- Axios
- Vue Router, Pinia

### 🚀 인프라 & 배포
- AWS EC2 + RDS + S3 + CloudFront
- Nginx + Docker + GitHub Actions (CI/CD)

---

## 📌 주요 기능

### 🎮 게임
- Steam 기반 게임 정보 조회
- 검색, 정렬, 필터 기능 지원
- 인기 게임 추천

### 📝 유저 리뷰
- 게임에 대한 별점 및 리뷰 작성
- 리뷰 좋아요 및 신고 기능
- 평론가 레벨 시스템 도입 예정

### 🧩 티어리스트
- 유저별 티어리스트 생성 및 공유
- 게임별 배치 및 코멘트 추가
- 공개 여부 설정 + 좋아요 기능

### 🖼️ Topster (후순위)
- 이미지 기반 게임 수집
- 공유 가능한 개인 Topster 캔버스

### 👤 유저
- 회원가입 / 로그인 / 로그아웃
- JWT 기반 인증 (Access + Refresh Token)
- 회원 탈퇴

---

## 🔄 API 설계 (Swagger 제공 예정)

- `/api/games`: 게임 목록 / 상세
- `/api/reviews`: 리뷰 등록 / 조회 / 삭제
- `/api/tiers`: 티어리스트 생성 / 조회 / 좋아요
- `/api/users`: 회원 관련 API

---

## ✨ 목표

- 실전 기반 API 설계 및 DB 설계
- 메시지 큐(RabbitMQ) 기반 비동기 처리 실험
- 확장 가능한 서비스 구조 학습
