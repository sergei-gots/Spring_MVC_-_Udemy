# Spring MVC - Udemy course
To the Spring Framework Udemy Course by Neil Alishev

https://www.udemy.com/course/spring-alishev/learn/lecture/31009308

<h2>Lesson 26. "JDBC API. Data Bases"</h2>

With the page <b>Database</b> we make connection with our database <b>first_db</b>
located on our PostgreS-DB Server.
<br>
<br>DB-name will be <b>first_db@localhost</b>
<br>Host will be <b>localhost</b> and port is <b>5432</b>
<br>URL for this db will be <b>jdbc:postgresql://localhost:5432/first_db</b>

<p>We can test connection by clicking <u>Test Connection</u>-button 
<p>Later we can get this <u>DB-connection Propery Page</u> with "<b>Shift+Enter</b>

<h3>Creating a Table</h3>
To operate our database we use a </u>Console</u>-window. To jump
to the console window we can use <b>Ctrl+Shift+F10</b> being
focused on our database on the page </u>Database</u>
<p>To create a table we will use <b>SQL-code</b>b> and <b>DDL-command</b>
"<b><u>CREATE</u></b>" in the console window:
<p><code>
    create table Person(

        id int,
        name varchar,
        age int,
        email varchar
    )
</code>

After having this code written we use a mnemonic button <u>Execute</u> above the console window.
<p>As a response we will get a report in the <u>Services</u>-window:
<p></p><code>
[2022-11-13 20:38:25] Connected
    first_db.public> create table Person(

                id int,
                name varchar,
                age int,
                email varchar
        ) 
    [2022-11-13 20:38:25] completed in 59 ms
</code>

We can see the created table on the page <u>Database</u>:

    first_db@localhost
        first_db
            public
                person
                    id
                    name
                    age
                    email

<h4>Add some data to the Table:</h4>
First we should clear our console window from the already executed commands.
Ok. To add some data, or in our case - data describing several Persons to
the table "<u>Person</u>" we will use <b>DDL-command</b> "<b><u>INSERT INTO</u></b>":

    insert into person values (1, 'Tom', 18, 'tom@yahoo.com')

and then press the button <u>Execute</u>. 
We will have the output on the page <u>Services/Output</u> like that:

    [2022-11-14 02:11:07] Connected
    first_db.public> set search_path = "public"
    [2022-11-14 02:11:07] completed in 6 ms
    first_db.public> insert into person values (1, 'Tom', 18, 'tom@yahoo.com')
    [2022-11-14 02:11:07] 1 row affected in 18 ms

Now we can observe the input data:

    select * from person

and we will get the result as a table view on the page <u>Services/<b>first_db.public.person</b></u> with the only row
representing the data we've just input.
<br>Also we will have on the page <u>Services/Output</u> the report like that:

    first_db.public> select * from person
    [2022-11-14 02:13:44] 1 row retrieved starting from 1 in 912 ms (execution: 44 ms, fetching: 868 ms)

<br>Let's add another data to our table. To separate command expressions in 
SQL we will use <b>semicolon</b>-signs <b>;</b>:

    insert into person values(2, 'Bob', 28, 'bob@gmail.com');
    insert into person values(3, 'Bob1', 38, 'bob@yahoo.com');
    insert into person values(4, 'Bob2', 45, 'bob2@gmail.com')

To execute our command set we can use not only the button <b><u>Execute</b></u>
but also <b><u>Ctrl+Shift</u></b>

<h3>Adding Java-Code to interact with the Database</h3>
<h4>Maven Dependency for PostgreSQL</h4>
We need to have <b>PostgreSQUL JDBC</b> driver.
<b>JDBC</b> means <b>Java™ database connectivity</b>
<p>To add the dependency we will go to maven repository and get
a dependency from <a href="https://mvnrepository.com/artifact/org.postgresql/postgresql">href="https://mvnrepository.com/artifact/org.postgresql/postgresql</a>
</p>
<p> We will add in the <u>pom.xml</u> a PostgreSQL JDBC dependency like that:</p>

<!-- https://mvnrepository.com/artifact/org.postgresql/postgresql -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.5.0</version>
    </dependency>
<p>To load maven changes we can use <b><u>Ctrl+Shift+O</u></b>

<h4>Modifying class dao.PersonDAO</h4>

In this lesson we will work out the methods <b>List<Person> index()</b> and
<b>void update(Person person)</b>. It will allow us to view list of people
in our database under <a href="http://localhost:8080/people"> http://localhost:8080/people </a> 
and <a href="http://localhost:8080/people/new"/> http://localhost:8080/people/new </a>.

<p>In our code we will use the next imports from the package <u><b>java.sql</b></u>:
    
    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.sql.Statement;
</p>

<p>First, we comment all the rest methods which are all work with a table prototype.
<p>Next, we introduce <b>static final</b> variables which are necessary to connect
our database. These values are typically should be stored in a <i>.properties</i>-file:

    @Component
    public class PersonDAO {
    
    //URL consists of 3 parts: <driver-name>://<host:port>/<db-name>:
    private static final String URL="jdbc:postgresql://localhost:5432/first_db";
    private static final String username="postgres";
    private static final String password="postgres";

<p> Then we will have a <b>static</b> variable <i>Connection</i> and static part 
to setup this connection with a database:


    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

<p>Here is the code for our methods to show list of people from database and create a new 
person entry:


    public List<Person> index() {
        List<Person> people = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM Person";
            ResultSet resultSet = statement.executeQuery(SQL);

            while(resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));

                people.add(person);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return people;
    }

    public void save(Person person) {
        try {
            Statement statement = connection.createStatement();
            String SQL = "INSERT INTO Person VALUES ("
                    + (++PEOPLE_COUNT) +
                    ",'" + person.getName() +
                    "'," + person.getAge() +
                    ",'" + person.getEmail() + "')";
            System.out.println("SQL = " + SQL);
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

<p> Now we can add a new Person to our database and even after restarting our app
and server we will get the added entry from the table because it is a table located in a real database on a real
database-server;)