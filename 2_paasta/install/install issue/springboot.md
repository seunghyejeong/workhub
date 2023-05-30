# boot install issue
    - version
        + spring boot 3.0.1은  java 17과 호환된다. 그러므로 2.7.7version을 설치하여 java 11과 연동되도록 프로젝트를 생성해준다.
    - `'url' attribute is not specified and no embedded datasource could be configured`
        + DB에 연결이 되어있냐 묻는 오류 
    ```shell
    spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
    spring.datasource.url=jdbc:oracle:this:@localhost:1521/xe
    spring.datasource.username=admin
    spring.datasource.passowrd=1234
    ```
    SpringBoot는 어플리케이션이 시작될 때 필요한 기본 설정들을 자동으로 설정하게 되어있는데, 그중에 DataSource 설정이 자동구성 될 때 필요한 데이터베이스 정보가 설정되지 않아 발생하는 문제다.



프로젝트가 생성될 때 appliction.properties 파일이 자동 생성되었으나 빈파일로 되어있을 것인데, 여기에 사용자가 원하는 DB 설정을 넣고, 맞는 드라이버와 라이브러리 설치, JDBC 설정을 해야한다는 의미다.



만약 당장 JDBC설정이 필요없고 어떤 DB를 사용할지 결정하지 않았다면 다음의 소스를 참조하면 된다.



스프링부터 메인 클래스에 어노테이션을 추가한다.



@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}