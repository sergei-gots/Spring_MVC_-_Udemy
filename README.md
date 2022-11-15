# Spring MVC - Udemy course
To the Spring Framework Udemy Course by Neil Alishev

https://www.udemy.com/course/spring-alishev/learn/lecture/31009314

<h2>Lesson 28. "Spring JDBC Template"</h2>

<b>Spring Jdbc Template</b> is a wrapper upon <b>JDBC API</b>.

The <b>JDBC API</b> has some disadvantages in use:
<li>a big amount of superfluous code: e.g. we have to create <b>Connection</b>, <b>Statement</b>
and <b>ResultSet</b>
<li>Duplicated pieces of code. In our case we can see it in implementation of the methods
<b>void PersonDAO.show(int id)</b> and <b>vod PersonDAO.index() </b>
<li>Necessity to catch and handle <b>SQLException</b> everywhere
<p>High level of abstraction implemented in <b>Spring Jdbc Template</b> allows to reduce
the amount of source code up to 20 times(!). The code itself becomes simpler and more comfortable to be red.
</p>

<h3>Maven Dependency "spring-jdbc" </h3>
To do that we just copy any <b>SpringFramework</b>-dependency in our <u>pom.xml</u>-file and
change its <b>artifactId</b> to <b>spring-jdbc</b>. So we will have a new dependency:

    <!-- https://mvnrepository.com/artifact/org.springframework/spring-jdbc -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-jdbc</artifactId>
        <version>${spring.version}</version>
    </dependency>

This dependency gives access to the class <b>JDBCTemplate</b>. 

<h3>Creating Beans for DataSource and JDBCTemplate</b></h3>
We will add two methods creating Beans to the class <i>config.</i><b>SpringConfig</b>:

    ...
    import org.springframework.jdbc.core.JdbcTemplate;
    import org.springframework.jdbc.datasource.DriverManagerDataSource;
    ...
    import javax.sql.DataSource;
    ...
    public class SpringConfig implements WebMvcConfigurer {
    ...
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/first_db");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
    }

<p>We see we transferred the piece of code for initialisation connection with database
from the static part of the class <i>dao.</i><b>PersonDAO</b>. Now we can recode it.

<h3>Recoding PersonDAO</h3>

