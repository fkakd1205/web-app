# DBCP 연결
#### DBCP 연결 이유와 연결 방법

### 개발 환경
* OS : Windows 10 64bit
* TOOL : Eclipse ver.4.12.0 / Tomcat ver.9.0 / MySQL ver.8.0
* Language : Java / HTML5 / CSS3 / JavaScript

### 라이브러리
* tomcat-dbcp
* mysql-connector-java-8.0.15

<br />

## 1. MySQL 구동 오류
* MySQL 구동 중, 일정 시간(8시간) 동안 아무런 요청이 없으면 강제로 Connection을 닫아 버린다.
* 이는 MySQL 시스템 변수 'wait_timeout'의 디폴트 값이 28000초(8시간)으로 설정되어 있기 때문이다.
* 따라서 8시간 후 request 했을 때, 다음과 같은 오류가 발생한다.

<pre><code>
com.mysql.cj.jdbc.exceptions.CommunicationsException: The last packet successfully received from the server was 52,356,421 milliseconds ago.  
The last packet sent successfully to the server was 52,356,428 milliseconds ago. is longer than the server configured value of 'wait_timeout'. 
You should consider either expiring and/or testing connection validity before use in your application, increasing the server configured values for client timeouts, 
or using the Connector/J connection property 'autoReconnect=true' to avoid this problem …
</code></pre>

<br />

* 이미 접속이 끊긴 커넥션이기 때문에 이를 사용하려고 할 때 문제가 발생한다.
* 오류 로그를 보면 'wait_timeout'을 늘리거나 'autoReconnect=true'를 설정하여 이 문제를 해결하라고 한다.

#### :heavy_check_mark: wait_timeout
* 시간을 지정해두면, 지정된 시간만큼 구동이 가능하다.
* 하지만 지정된 시간이 초과되면 커넥션을 종료시킨다.
* 시간을 무한정 늘릴 수 없기 때문에 오류의 해결책으로 보기 어렵다.

#### :heavy_check_mark: autoReconnect=true
* 쿼리 수행 다음 DB 세션에 문제가 있으면 단순히 SQLException을 리턴한 다음 재접속을 시도할 수 있도록 한다.
* 따라서 최초 문제 발생시에는 에러를 발생시키며, 새로고침(F5)하면 재연결 된다.
* 정상적인 서비스 운영을 위해 SQLException이 리턴되어 재접속을 요청하는 것도 문제가 있다.

<br />
<br />

## 2. DBCP의 validationQuery
* DBCP의 파라미터 중 validationQuery를 사용하면 위 문제를 해결할 수 있다.
* DBCP는 Database Connection Pool의 약자로 데이터 베이스와 연결을 맺고 있는 Connection 객체를 관리하는 역할을 한다.
* 즉, DBCP도 JDBC와 유사한 역할을 한다.

#### :heavy_check_mark: validationQuery
* DBCP의 옵션 중 하나로 커넥션의 유효성을 검사한다.
* 단순 검증을 위한 옵션이기 때문에 자원 소비를 최소화하기 위한 간단한 쿼리를 사용해야 한다.
    * MySQL : "select 1"
    * Oracle : "select 1 from dual"

<br />
<br />

## 3. JDBC와 DBCP의 차이
### JDBC
1. 페이지 요청(DBMS 연결 요청)이 들어왔을 때, DB Connection을 호출
2. DB 접속을 위해 JDBC 드라이버를 로드
3. DB 서버 정보(서버 URL, 사용자 아이디, 비밀번호)를 입력하여 DB 연결
4. DB Connection 객체 생성
5. 쿼리 수행 후 결과 값 반환
6. DB Connection close()로 Connection을 닫음

### DBCP 
1. 페이지 요청(DBMS 연결 요청)이 들어왔을 때, DB Connection Pool에는 이미 DB Connection 객체가 저장되어 있다. (설정한만큼 Connection이 생성, default=0)
2. DBCP에서 DB Connection 객체를 가져다 사용
3. 쿼리 수행 후 결과 값 반환
4. DB Connection close()로 Connection을 닫는 것이 아니라 Connection Pool로 반환

<br />

* 다수의 사용자가 동시에 이용할 수 있는 사이트에서 JDBC 방식과 같이 DB에 접근하면, 매번 DB연결을 위해 JDBC드라이버를 로드하고 DB Connection 객체를 생성하는 행위를 한다. (2, 3, 4번 반복) 이는 사용자가 많아질수록 서버의 부하가 일어날 가능성이 높아진다.   
* DBCP는 매번 JDBC 드라이버를 로드하고 DB Connection 객체를 생성하지 않아도 커넥션을 가져다 사용할 수 있기 때문에 빠른 처리 속도를 보이며 DB 서버가 감당할 수 없는 접속 요청은 사전에 차단이 가능하다.     
* DBCP의 옵션 중 하나인 validationQuery를 통해 커넥션의 유효성을 검사할 수 있기 때문에, 일정 시간(8시간) 후 Connection을 닫아 버리는 오류를 해결할 수 있다.   

<br />
<br />

## 4. DBCP 연결 방법
### 1️⃣ DBCP 연결 방법
* Tomcat v.6.0 이상은 commons-dbcp-1.4jar, commons-pool-1.6.jar, commons-collections-3.2.1-bin.jar가 tomcat-dbcp.jar 파일 하나로 통합.
* Tomcat 설치 폴더 lib에 tomcat-dbcp.jar파일이 존재. (따로 설치 x)
* tomcat-dbcp.jar파일 복사해서 자신의 프로젝트 폴터 WebContent > WEB-INF > lib에 넣기
<img src="https://user-images.githubusercontent.com/35367660/120632864-68fca300-c4a4-11eb-811d-77b0daa0f60e.png" width="500">

<br />

### 2️⃣ DBCP 연결 방법
* Server > server.xml의 &lt;GlobalNamingResources&gt;&lt;/GlobalNamingResources&gt; 안에 아래 코드 추가
* Server > context.xml을 복사해서, 내 프로젝트 폴더 WebContent > META-INF에 넣고, 
그 context.xml의 &lt;Context&gt;&lt;/Context&gt; 안에 아래 코드 추가

~~~
<Resource auth="Container" driverClassName="com.mysql.cj.jdbc.Driver"
  		factory="org.apache.tomcat.dbcp.dbcp2.BasicDataSourceFactory"
  		name="jdbc/*DB*" password=“*PW*"
  		validationQuery="select 1"
  		testOnBorrow="true"       // testOnBorrow : 풀에서 커넥션을 가져오기 전 객체의 유효성 검사 여부
  		testWhileIdle="true"        // testWhileIdle : 놀고 있는 커넥션의 제거 여부를 검사할 때, 해당 커넥션의 유효성 테스트 여부
  		timeBetweenEvictionRunsMillis="3600000"       // timeBetweenEvictionRunsMillis : 유효한 커넥션인지 확인하기 위한 주기 설정(1시간=3600000)
  		type="javax.sql.DataSource"
  		url="jdbc:mysql://*IP주소*:*포트번호*/*DB*?serverTimezone=Asia/Seoul&amp;autoReconnect=true"
  		username=“*MySQL유저아이디*"/>
~~~

※ '*'로 묶은 것들은 본인의 설정에 맞게 작성   
※ name=“jdbc/DB”와 url=“jdbc:.../DB?...”의 DB는 동일한 이름   
※ DBCP 옵션 설정 - [BasicDataSource Configuration Parameters](http://commons.apache.org/proper/commons-dbcp/configuration.html)

<br />

### 3️⃣ DBCP 연결 방법
* 내 프로젝트 폴더 > WebContent > WEB-INF > web.xml의 &lt;web-app&gt;&lt;/web-app&gt; 안에 아래 코드 추가

~~~
<resource-ref>
     <description>*DBCP test*</description>
     <res-ref-name>jdbc/*DB*</res-ref-name>
     <res-type>javax.sql.DataSource</res-type>
     <res-auth>Container</res-auth>
  </resource-ref>
~~~

* &lt;desription&gt; - 리소스에 대한 설명을 지정
* &lt;res-ref-name&gt; - 사용하고자 하는 리소스의 이름을 지정
* &lt;res-type&gt; - 사용하고자 하는 리소스의 타입을 지정
* &lt;res-auth&gt; - 리소스에 대한 권한  

※ DB는 context.xml, server.xml에 작성한 DB와 동일한 값을 넣어야 함.

<br />

### 4️⃣ DBCP 연결 방법
* DBCP 연결 코드 (기존 JDBC 연결 코드, web.xml에 JDBC 연결 부분 주석처리 or 삭제하기)
* 기존 DBConnectionManager 파일에서 JDBC연결 코드 주석 or 삭제 처리하고 아래 DBCP 연결코드 넣기

~~~java
ServletContext context = event.getServletContext();
Context initContext;
DataSource dataSource = null;
		
// DBCP 연결
try {
     initContext = new InitialContext();
     
     // JNDI 이용을 위한 객체 생성
     Context envContext = (Context)initContext.lookup("java:comp/env");
     
     // lookup() 등록된 naming서비스로부터 자원을 찾고자 할 때 사용하는 메소드
     // context 객체를 통해 이름으로 Resource 획득
     dataSource = (DataSource)envContext.lookup("jdbc/DB");
     
     System.out.println("Load dbcp Driver");
} catch (NamingException e1) {
     e1.printStackTrace();
}

try {
     conn = dataSource.getConnection();
			
     if(conn != null) {
          context.setAttribute("DBConnection", conn);
          System.out.println("Succeed DB connection");
     }
} catch (SQLException e) {
     e.printStackTrace();
     System.out.println("Fail DB connection");
}
~~~

<br />

## 참고 자료
* [DBCP 옵션](http://commons.apache.org/proper/commons-dbcp/configuration.html)
* [JDBC와 DBCP의 차이점](https://shxrecord.tistory.com/127)
* [DBCP 설정 참고](https://m.blog.naver.com/PostView.nhn?blogId=undersky03&logNo=66480172&proxyReferer=https:%2F%2Fwww.google.com%2F)
* [DBCP 연결 코드 참고](https://devbox.tistory.com/entry/JSP-%EC%BB%A4%EB%84%A5%EC%85%98-%ED%92%80-1)
* [Commons DBCP](https://d2.naver.com/helloworld/5102792)
* [DBCP 옵션&설정](https://opentutorials.org/module/3569/21223)


