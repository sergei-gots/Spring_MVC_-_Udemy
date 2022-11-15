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

We remove all stuff inside all the methods and all the variables of class -
they are no more necessary. 

Introduce a field for <b>JDBC-Template</b>:

    private final JdbcTemplate jdbcTemplate;

and initialize it in a constructor  within autowiring mechanism:

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

Now we can rewrite our methods executing queries.
But first we should implement realisation for an <u><i>interface</i> org.springframework.jdbc.core.<b>RowMapper</b></u>:

    import org.gots.springcourse.models.Person;
    import org.springframework.jdbc.core.RowMapper;

    import java.sql.ResultSet;
    import java.sql.SQLException;

    public class PersonMapper implements RowMapper<Person> {

            @Override
            public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));

                return person;
            }
    }

<p>After what recode our <b>PersonDao</b> query methods:</p>

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new PersonMapper());
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id=?",
                new Object[] {id} ,
                new int[] { Types.INTEGER },
                new PersonMapper().stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person VALUES (?,?,?,?)", 1, person.getName(), person.getAge(), person.getEmail());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person SET name=?, age=?, email=? WHERE id=?",
                updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }


We use our <b>dao.PersonMapper</b> to put values from corresponding columns of result set into
corresponding fields of an object of the class <b>Person</b>.
And furthermore, because we have such names for class fields that
are the same for columns in the table, we can use 

    BeanPropertyRowMapper<>(Person.class)

instead of using our <b>PersonMapper</b> and short our code
again. Let's recode methods <u>List< Person ></u><b> index</b><u>()</u> 
and <u>Person</u><b> show</b><u>(ind id</u>) once again:

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id=?",
                new Object[] {id} ,
                new int[] { java.sql.Types.INTEGER },
                new BeanPropertyRowMapper<>(Person.class) ).stream().findAny().orElse(null);
    }

<p> I will not remove the <b>PersonMapper</b>-class from a lesson's branch
but with pleasure mark this class with the anntotation <b>@Deprecated</b>:)