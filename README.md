# Spring MVC - Udemy course
To the Spring Framework Udemy Course by Neil Alishev

https://www.udemy.com/course/spring-alishev/learn/lecture/31009314

<h2>Lesson 30. "Configuring DB with a <u>.properties</u> file "</h2>

The way we used to initialise a connection with the DB -
 a direct mentioning connection's data in a code like username, password and url
is inflexible and unsafe. E.g. if the code is located in a public 
repository on <i>GitHub</i> it is opened for everyone. 
In this lesson we transfer the connection data from a code
to a <i>.properties</i>-file.
Until now we have such code in the class <i>config.</i><b>SpringConfig</b>:

    @Bean
        public DataSource dataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.postgresql.Driver");
            dataSource.setUrl("jdbc:postgresql://localhost:5432/first_db");
            dataSource.setUsername("postgres");
            dataSource.setPassword("postgres");
            return dataSource;
        }

The place where such files are stored will be the folder <b>main/resources</b>.
Let's create a new directory <b>resources</b> within our project folder <b>name</b>
The created folder will be marked as a <i><b>resource folder</i></b>.
Also we can make folder marked as a <i><b>resources root</i></b> from the
context menu to the folder.
It will be suggested by IntelliJ Idea because of the name we gave to a folder.
Let now create in this folder <b><i>main/resources</b></i> 
the file <b>database.proprerties</b> with the next content.  
<b>!</b>The only thing to remember: if we launch our application under <b>Windows</b>-OS
we should have <b>another name</b> for the key <b>username</b> in order not to
interfere with the Windows corresponding internal variable with the same name </b>username</b>.
So <u>let's have</u> <b>username_value</b> instead of <u>username</u>

<b>datapbase.properties</b> file:
    
    driver=org.postgresql.Driver
    url=jdbc:postgresql://localhost:5432/first_db
    username_value=postgres  
    password=postgres

Usually we won't add this <b>database.properties</b> file to the
<i>Git</i>-repository because there is information that is considered to
be confidential. But I add it there in educational purposes.
Normally we have (and create now) the file <b>database.properties.origin</b>.
This file is going to store public information and will store
necessary keys described in <b>database.properties</b> but without
values. It is necessary in order to provide a person who will use the application with the information which will be needed
to connect with the database:

<b>datapbase.properties.origin</b> file:

    driver=
    url=
    username_value=
    password=

<h3>Modifying config.SpringConfig-class</h3>

The code we change will look like:
    
    ... 
    import org.springframework.context.annotation.PropertySource;
    import org.springframework.core.env.Environment;
    ...
    import java.util.Objects;
    ....
    @PropertySource("classpath:database.properties")
    ...
    public class SpringConfig implements WebMvcConfigurer {
        ...
        private final Environment environment;
     @Autowired
     public SpringConfig(ApplicationContext applicationContext, Environment environment) {
       ...
       this.environment = environment;
     }

    ...
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("driver")));
        dataSource.setUrl(environment.getProperty("url"));
        dataSource.setUsername(environment.getProperty("username_value"));
        dataSource.setPassword(environment.getProperty("password"));
        return dataSource;
    }
    ...
    }
   
<p> And when we consequently insert corresponding property-names as
a parameters for <b>environment.getProperty(String propertyName)</b>
we can observe how corresponding keys changing its color in
the <b>database.property</b>-file.
<p>The method <b>< T > T Objects.requireNotNull(T objRef)</b>
Checks that the specified object reference (in our case it is
a Driver instance) is not null. 
This method is designed primarily
for doing parameter validation in methods and constructors to prevent further execution
and if a Driver isn't created successfully <b>NullPointerException</b> will be thrown.

