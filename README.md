# 게시판 프로젝트
### View - React
- React-hook-form, yup등을 이용한 입력값 검증으로 편하고 빠르게 form 제작
- React-router 로그인 페이지 및 게시판 수정, 삭제 작성 페이지 라우팅
- Proxy 활용으로 임시 Cors 에러 제거

### BackEnd - Java Spring Boot
- JPA및 QueryDsl으로 IDE에서 에러 발견 및 동적 쿼리 검색기능 구현
- Spring Security 도입으로 Rest Api Jwt 로그인 구현 (2023/04/22)기준 완료
- 페이지네이션 적용

### 발견한 문제점과 수정사항
- 댓글이 달린 게시물 삭제 안되는 버그 발견 (수정완료)
- 배포시 React에 있는 CSP로 인한 요청 url이 자동으로 https로 변환됨(https ssl 적용으로 수정완료)
- 검색시, 제목은 제대로 검색이 되는데, 제목+내용이나 제목+작성자+내용으로 검색이 제대로 안되는 문제 발견 - (JPA BooleanExpression 로직 문제, 로직수정으로 지금은 정상작동)(티스토리에 관련글 작성예정)
- 이메일 체크 기능(2023.04.25기준 구현완료)
- 글 작성시 255byte가 넘어가면 에러 발생(데이터베이스 설정문제 수정완료)
### 추가 예정 사항

- 이메일 인증
- Swagger를 통한 자동 문서화 및 DTO별 기능 정리

# 스크린샷
![스크린샷 2023-04-21 오후 7 19 23](https://user-images.githubusercontent.com/69139476/233612440-cd0eb2ef-9c1b-4a55-9c3b-a7403f0d7d5f.png)
![스크린샷 2023-04-21 오후 7 19 26](https://user-images.githubusercontent.com/69139476/233612454-55f4ec85-737e-41dd-9e77-be758c510130.png)
![스크린샷 2023-04-21 오후 7 19 32](https://user-images.githubusercontent.com/69139476/233612463-c638075a-4005-4f17-8beb-61a81b569965.png)
![스크린샷 2023-04-21 오후 7 19 38](https://user-images.githubusercontent.com/69139476/233612475-aade69dc-bcba-4819-a160-517abf4f5f8c.png)
