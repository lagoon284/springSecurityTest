1. application.properties 설정법

  A. In_Memory 방식, 영구적 대이터 아님. 프로젝트 스탑 시 휘발됨.
  접속 url : localhost:8080/h2-console
  spring.datasource.url= jdbc:h2:mem:test
  spring.datasource.username= sa
  spring.datasource.password=

  B. Embeded 방식, 로컬베이스로 영구적 데이터
  접속 url 동일.
  spring.datasource.url= jdbc:h2:~/test (경로나 DB 이름 수정시 바뀔 수 있음. 임의지정 가능)
  spring.datasource.username= sa (임의지정 가능)
  spring.datasource.password= (임의지정 가능)

2. jpa hiber nate auto ddl 정책 설정.

  DB auto DDL 설정 (none, create, create-drop, update, validate)
  none : 아무것도 하지않는다.
  create : 테이블이 없을 경우, 테이블 생성.
  create-drop : 테이블이 없을 경우, 테이블 생성. 프로젝트 종료할때 테이블 드롭.
  update : 이미 테이블이 존재하는 상황에서 속성이 추가될 경우 기존테이블의 데이터에 변화없이 새로운 column만 추가한다.
  validate : DDL 실행 없이 엔티티클래스와 테이블이 정상적으로 매핑되는지만 검사한다. 만약 테이블이 없거나 엔티티에 매핑되는 컬럼이 존재하지 않은면 예외발생.

  spring.jpa.hibernate.ddl-auto= (위 설정 참조)
