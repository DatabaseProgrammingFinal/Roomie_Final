# 🏡 기숙사 물품 대여 플랫폼

---

## ID/PW for test
- **ID** : user01 
- **PW** : admin1234!

---

## 📋 프로젝트 개요
**기숙사 물품 대여 서비스**는 기숙사 생활 중 급히 필요한 소량 물품을 손쉽게 대여/공유할 수 있는 사용자 친화적 플랫폼입니다. 같은 건물 내에서 물품을 빠르게 전달받을 수 있는 환경을 조성하며, 공유 경제와 소속감, 친밀감을 동시에 강화합니다.

---

## 👥 팀 소개
- **수업명**: 데이터베이스프로그래밍 02분반  
- **팀명**: 10팀  
- **팀원**:  
  - 20210759 김수현  
  - 20220765 박주희  
  - 20220803 정여진  

---

## 💡 개발 배경
- **소량 물품 대여 서비스**의 필요성: A4 용지, 간단한 가전제품 등 기숙사 내에서 급히 필요한 물품을 신속히 구할 수 있는 방법이 제한적.  
- **공유 경제 활성화**: 자원을 효율적으로 공유하며, 사용하지 않는 물품의 가치를 높임.  
- **거리적 이점 활용**: 같은 건물 내에서 요청자와 제공자 간 물품 이동이 용이.  
- **소속감과 친밀감 증대**: 기숙사 내에서의 협력을 통해 공동체 의식을 강화.

---

## 🎯 서비스 목적 및 특징
- **사용자 친화적 인터페이스 제공**: 기숙사생 누구나 쉽게 사용할 수 있는 간단한 UI/UX 설계.  
- **포인트 시스템**: 물품 제공자에게 상점 부여, 연체 시 요청자에게 벌점 부여로 공정성 강화.  
- **데이터 기반 운영**: 물품 대여 및 반납 데이터를 바탕으로 투명한 관리와 분석 가능.  
- **공유 경제 활성화**: 사용하지 않는 물품을 대여함으로써 기숙사 내 물품 활용도를 극대화.

---

## 🛠️ 기술 스택
### **백엔드**
- ![Java](https://img.shields.io/badge/Java-007396?style=flat-square&logo=java&logoColor=white)
- ![Servlet](https://img.shields.io/badge/Servlet-4D4D4D?style=flat-square&logo=java&logoColor=white)

### **데이터베이스**
- ![Oracle](https://img.shields.io/badge/Oracle-F80000?style=flat-square&logo=oracle&logoColor=white)

### **디자인**
- ![Figma](https://img.shields.io/badge/Figma-F24E1E?style=flat-square&logo=figma&logoColor=white)

### **협업 도구**
- ![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=github&logoColor=white)
- ![Notion](https://img.shields.io/badge/Notion-000000?style=flat-square&logo=notion&logoColor=white)
- ![Discord](https://img.shields.io/badge/Discord-5865F2?style=flat-square&logo=discord&logoColor=white)

---

## 📦 설치 및 실행 방식
1. **필요한 환경**
   - Java 8 이상
   - Oracle Database
   - Apache Tomcat 9.x

2. **설치**
   - **Git**을 사용해 레포지토리를 클론:
     ```bash
     git clone https://github.com/your-repo-url
     ```
   - 데이터베이스 초기화: 제공된 SQL 스크립트 실행.
   - `context.properties` 파일에 DB 연결 정보 추가.

3. **실행**
   - IDE (Eclipse)에서 프로젝트 열기.
   - Tomcat 서버 설정 후 애플리케이션 실행.
   - 브라우저에서 `http://localhost:8080` 접속.

---

## 📱 화면 구성 및 API 주소
### 1. 사용자 등록/로그인
- **화면**: 사용자 등록 및 로그인 페이지  
- **API**: `/users/register`, `/users/login`

### 2. 물품 대여 게시글
- **화면**: 게시글 작성/검색/상세보기 페이지  
- **API**: `/posts/create`, `/posts/list`, `/posts/view`

### 3. 쪽지
- **화면**: 쪽지함, 쪽지 작성 페이지  
- **API**: `/messages/send`, `/messages/inbox`

### 4. 대여 확정
- **화면**: 대여 확정 및 반납 업데이트 페이지  
- **API**: `/rentals/confirm`, `/rentals/update`

---

## 🔑 주요 기능
1. **사용자 등록 및 로그인**
   - 기숙사 정보와 닉네임 입력.
   - ID 및 닉네임 중복 방지.

2. **물품 대여 게시글**
   - 요청글 및 제공글 작성, 검색, 상세보기.
   - 리스트 형식으로 게시글 확인.

3. **대여 확정 시스템**
   - 제공자와 요청자 매칭.
   - 대여 장소 및 일정 업데이트.
   - 반납 상태 및 연체 벌점 기록.

4. **포인트 시스템**
   - 반납 시 제공자에게 상점 부여.
   - 연체 시 요청자에게 벌점 부과.

5. **쪽지 기능**
   - 게시글 작성자와 쪽지 주고받기.
   - 쪽지함에서 대화 확인.

---

## 🧪 사용한 라이브러리
- `javax.servlet.http.HttpSession`  
- `javax.servlet.http.HttpServletRequest` & `javax.servlet.http.HttpServletResponse`  
- `org.slf4j.Logger` & `org.slf4j.LoggerFactory`  
- `com.fasterxml.jackson.databind.ObjectMapper`

---

## 💾 커밋 컨벤션
커밋 메시지는 다음과 같은 컨벤션을 따릅니다:
- **`build`**: 외부 의존성 설치나 빌드 시스템에 영향을 주는 변경사항
- **`ci`**: CI 환경설정과 스크립트에 변화가 있는 경우
- **`docs`**: 문서, 주석 관련
- **`feat`**: 새로운 기능 추가
- **`fix`**: 버그 픽스
- **`refactor`**: 코드 리팩토링 관련
- **`chore`**: 버그 픽스 또는 기능 추가에 해당되지 않는 '자잘한' 변경사항 (ex. 파일명 변경, 코드 정리 등)
- **`test`**: 테스트 관련 수정
- **`style`**: UI 변경사항  

---

## 🔗 협업 및 프로젝트 관리 툴
- **깃허브**: 코드 관리 및 이슈 트래킹.  
- **노션**: 문서 관리 및 프로젝트 일정 관리.  
- **디스코드**: 팀 간 소통 및 실시간 협업.  

---

## 🏆 향후 계획
- 모바일 친화적 반응형 웹 디자인 추가.
- 대여 통계 및 리포트 기능 개발.
- 다양한 물품 카테고리 추가.

---
