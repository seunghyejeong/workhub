IntelliJ 공모전 refactor 



[worktodo]

# 230215

	1. DBconnecttest class 생성 
	:: 완료 
	2. maven repository 추가
	:: 진행중  (java.mysql.* 가 아무리해도 import가 안돼)
	3. dbclass 입력
	:: 성공 
	4. root-context 입력 
	5. test 파일 생성

# 230216

	1. user test 
		@test
			instert
				- db 연결
				- sql query문 작성
				- prestatement로 insert 
				- query문 업데이트
				- result 반환 
			delete
			update
			select
			private deleteall
	2. userdto 생성 
			
	2, logincontroller
	3. registercontroller

[ 기본 ]
Eclipse		Workspace	Project
IntelliJ	Project		Module

<button type="button" class="navyBtn" onClick="location.href='PAGENAME.html'">

# 230216

	1. userdao/userdaoimpl 완료
	2.board는 mysql과 mapping 시키는 단계 
			::강의듣고 진행

# 230220 

	1. qna/notice/user 모두 db mapping & test 끝내기

# 230220 todo

1. pom.xml 업데이트 ㅇㅇ
2. web.xml 업데이트 ㅇㅇ 
3. root-context 업데이트 ㅇㅇ 
4. mybatis-config 생성 ㅇㅇ 
5. boardDTO생성  ㅇㅇ 
   create table board(
   bnum int auto_increment primary keyß
   title	varchar(20 not null)
   content	text
   writer	varchar(30) not null
   view_cnt	int default 0
   comment_cnt	int default 0
   register_date	datetime
   readchecked boolean
   ); 



[설정]

- db Auto 로 하면 한줄씩 enter 할 때마다 commit , rollback을 못함.
  : 그래서 manual로 바꿔주는게 좋음.

- setting > editor > general > Auto import -> Add unambiguous imports on the fly checked
  : 자동 import 

- setting > tools > databases > query execution > 

  1) Execute / ctrl+enter : smallest subbquery or statement  == 한줄씩 실행하기 
  2) Execute2 / (단축어개별설정) : whole sciprts 				== 모든 문장 실행하기 

- 오타 확인 잘하기 (변수명, 컬럼명)

- mapper는 만들 때 마다 그때그때 만들어주기.

- mapper property는  *Mapper.xml 하면 알아서 잡힘.

- register_date 등  now()로 mapper 가능한 타입은 따로 선언해주지 않고 바로 now() insert 가능하다 

- null 허용이라도 값이 있어야하는 경우에는 생성자로 선언해주어야함.

- @test에서 뭐가 잘못된지 보려면 method.print 한 후 system.out.print 로 console 찍기 

  

  

  

[단축어]

- import		: 	alt + enter 
- 자동 줄 정렬 	: 	ctl+alt+l
- 줄이동 		:	shift + alt + 방향키 
- 한줄 삭제 	:	ctl+x
- db commit		:	ctl+enter 
- 블럭 전체이동 : 	ctl+shift + 방향키 
- multicouser	:	ctl * 2 + 방향키 
- 문자열치환	:	ctrl+shift+r 


# 게시판 게시글==List type

# 두개이상 매개변수 == map new Hashmap()으로 선언해주면

css, javascript, img 등의 정적 자료들은 resources/static/**에 넣어야 한다.

- 기본생성자를 만드는 이유 : mybatis 를 위해서. . 값이 무엇이 들어갈지 지정해주지 않고 select 할 때 지정해 주는것이 가능해짐.
- 생성자를 만드는것은 insert를 위함.




[issue]

# 230215

	2-1) repository도 모두 지우고 maven reload project를 해도 안되어 inteillJ 재설치
		- 이미 auto import도 안되던 상태라 어딘가 충돌이 나고 있다고 생각함.
	2-2) setting -> build -> maven -> repositories -> update를 눌러봄.
	2-3) 동작 잘함.............. ㅜ 
	2-4) tomcat lib가 자꾸 빠져서 maven dependency 추가해줬음.
	
	        <!-- https://mvnrepository.com/artifact/com.mysql/mysql-connector-j -->
		<dependency>
	        <groupId>com.mysql</groupId>
	        <artifactId>mysql-connector-j</artifactId>
	        <version>8.0.32</version>
	    </dependency>
	
		<!-- https://mvnrepository.com/artifact/org.apache.tomcat/tomcat-servlet-api -->
		<dependency>
			<groupId>org.apache.tomcat</groupId>
			<artifactId>tomcat-servlet-api</artifactId>
			<version>9.0.52</version>
		</dependency>
		
	    <!-- https://mvnrepository.com/artifact/org.springframework/spring-jdbc -->
	    <dependency>
	        <groupId>org.springframework</groupId>
	        <artifactId>spring-jdbc</artifactId>
	        <version>${org.springframework-version}</version>
	    </dependency>
	
	    <dependency>
	        <groupId>junit</groupId>
	        <artifactId>junit</artifactId>
	        <version>4.7</version>
	    </dependency>
	
	    <!-- https://mvnrepository.com/artifact/org.springframework/spring-test -->
	    <dependency>
	        <groupId>org.springframework</groupId>
	        <artifactId>spring-test</artifactId>
	        <version>${org.springframework-version}</version>
	    </dependency>
	
	--- DB 연결하며 필요했던 repository from pom.xml -----

# 230216	

	1. userdto에 생성할 것들 
		: 기본생성자 , toString, hashCode, Constrocor
	2. executeUpdate
		: insert delete update 
	3. executeQuery
		insert 반환하는 정보하나만 매개변수로 넘긴다
		타입은사용자 정보를 반환하기 때문에 User type


​	




#

	int insert(BoardDto dto) throws Exception;
	   
	    User selectUser(int userIdx) throws Exception ;


​		

    public int insert(BoardDto dto) throws Exception {
        return session.insert(namespace+"insert", dto);
    } // int insert(String statement, Object parameter)
    
        @Override
    public int insertUser(User user) throws Exception {
        return session.insert(namespace + "insert",user);
    }


	<insert id="insert" parameterType="BoardDto">
	INSERT INTO board
		(title, content, writer)
	VALUES
		(#{title}, #{content}, #{writer})
	</insert>
	
	    <insert id="insert" parameterType="user">
	    insert
	    into user
	    values (#{username}, #{useremail})
	</insert>
	
	    @Test
	public void insertTest() throws Exception {
	    boardDao.deleteAll();
	    BoardDto boardDto = new BoardDto("no title", "no content", "asdf");
	    assertTrue(boardDao.insert(boardDto)==1);
	
	    boardDto = new BoardDto("no title", "no content", "asdf");
	    assertTrue(boardDao.insert(boardDto)==1);
	    assertTrue(boardDao.count()==2);
	
	    boardDao.deleteAll();
	    boardDto = new BoardDto("no title", "no content", "asdf");
	    assertTrue(boardDao.insert(boardDto)==1);
	    assertTrue(boardDao.count()==1);
	}


# mapper추가 

	1. mapper.xml 생성
	2. allias 생성


# spring MVC  

	1. Controller생성 (@Controller )	
	2. DB테이블생성			 
		계층간의 데이터를 주고 받기 위해 사용되는 객체
		auto_increment 사용할 때 팁 
			: 만약 private boardnum int ; 로 작성시 null값일 경우 변환 에러가 난다
			그래서 private Integer 로 선언해주면 유효한 값이 아닐지라도 null값으로 변환을 해주기 때문에 int 보다는 Integer로 DTO를 작성하는 편이 낫다.
	3. mapper XML & DTO 작성 											   			 --DB Connect query문 작성 
					(persistence layer)
	4.  DAO 인터페이스 생성					-- DaoImpl 소환 
	-보통 예외 발생시 throws Exception한다.
	- try{
			commit
	}catch {
			rollback
	}
						((((((
							busniess layer(@Service) 추가 가능
						 DB와 연결되는 것이 아닌 DAO와 연결되며, 
						method가 업무적인 용어로 생성된다
						보통 Transaction을 담당한다 (service와 Daop impl의 수행이 한번에 이루어져야함.)
						.))))))
	5. DAO 인터페이스 구현 및 test (@Repository  db connection layer)	-- Sqlsession 소환 
		- connection은 한 dao당 하나의 connection 생성 가능
		- 




#select 
	id
	parameterType		(조건절에 해당하는 type)
	resultType               (Query 문 실행 결과가 담길 result type명
#delete  
	id
	parameterType
#insert  
	parameterType


#auto_increment는 생성자를 만들지 않는다(자동증가이기때문에 생성자 필요없음.)
#mybatis-config 파일에 미리  mapper을 생성하면 오류난다.

#mybatis-config.xml
<?xml version="1.0" encoding="UTF-8"?>

<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <typeAliases>
        <typeAlias alias="user" type="com.opencloudplatform.edm.domain.User"/>
        <typeAlias alias="qnaboard" type="com.opencloudplatform.edm.domain.QnABoard"/>
    </typeAliases>
</configuration>


<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns:mvc="http://www.springframework.org/schema/mvc"
	   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	   xmlns="http://www.springframework.org/schema/beans"
	   xmlns:context="http://www.springframework.org/schema/context"
	   xmlns:tx="http://www.springframework.org/schema/tx"
	   xsi:schemaLocation="http://www.springframework.org/schema/mvc https://www.springframework.org/schema/mvc/spring-mvc.xsd
		http://www.springframework.org/schema/beans https://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd">
	

	<!-- Root Context: defines shared resources visible to all other web components -->
	<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
		<property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
		<property name="url" value="jdbc:mysql://localhost:3306/edmdb?useUnicode=true&amp;characterEncoding=utf8"></property>
		<property name="username" value="root"></property>
		<property name="password" value="1234"></property>
	</bean>
	
	<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
		<property name="dataSource" ref="dataSource"/>
		<property name="configLocation"  value="classpath:mybatis-config.xml"/>
		<property name="mapperLocations" value="classpath:mapper/*Mapper.xml"/>
						*Mapper로 해놓으면 모든 mapper가 mapping 됨.
	</bean>
	
	<bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
		<constructor-arg ref="sqlSessionFactory"/>
	</bean>
	
	<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource"/>
	</bean>
	<tx:annotation-driven/>
	
	<context:component-scan base-package="com.opencloudplatform.edm"/>

</beans>

# db에 저장된 index값 가져오기

	Integer idx = qnaBoardDao.selectAll().get(0).getIdx();
	-> 먼저 selectAll을 해준 후 0부터 시작하는 idx를 포함한 idx값중에 idx를 가져온다.


# DaoImpl

	@Repository
	 @Autowired
	SqlSession sqlSession;
	private static String namespace = "com.opencloudplatform.edm.dao.qnaBoardMapper.";

#Test
	@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class QnABoardDaoImplTest {
    @Autowired
    QnABoardDao qnaBoardDao;
    @Test
	public ~


public handler(int totalPostCnt, int nowPage, int pageSize){
	totalPageCnt = totalPostCnt/pageSize
	beginPage = nowPage / naviSize * navisize + 1
	endPage = bebinPage+naviSize -1 
}


#
// T selectOne(String statement, Object parameter)
// List<E> selectList(String statement)
// int insert(String statement, Object parameter)\





@GetMapping("/list")
public String list(HttpServletRequest request, @RequestParam("boardType") String boardType) {
    if(!loginCheck(request))
        return "redirect:/login/login?toURL="+request.getRequestURL(); 

    if (boardType.equals("notice")) {
        return "noticeBoard";
    } else if (boardType.equals("qna")) {
        return "qnaBoard";
    } else {
        return "boardList";
    }

}

<a href="<c:url value='/list'><c:param name='boardType' value='notice'/></c:url>">Notice Board</a>
<a href="<c:url value='/list'><c:param name='boardType' value='qna'/></c:url>">Q&A Board</a>