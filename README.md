# Spring MVC - Udemy course
To the Spring Framework Udemy Course by Neil Alishev

https://www.udemy.com/course/spring-alishev/learn/lecture/31009312

<h2>Lesson 27. "JDBC API. SQL-Injections. Class PreparedStatement"</h2>

<h3>Once again about CRUD-Applications:)</h3>
We've been working on creaitng our CRUD-Application since the Lesson 22.
We will memorise that <b>CRUD</b> means <b>CREATE READ UPDATE </b> and <b>DELETE</b>. 
<br>Ok, let's go on:)

<h3>SQL-Injection</h3>
SQL-injection is one of the most popular instruments to hack websites and applications 
working with databases.

    NB:
    Instrument/Tool means an implement designed to perform a specific function
    in a valid and reliable manner. 
    An Instrument is used for data collection, storage, analysis, management 
    or monitoring, while a Tool is used to measure, manipulate or fabricate 
    any type of material.
<p> As rows from an HTML-form have being concatenated in a SQL-request directly, 
    a hacker can build such a string which will harm to a website or app.
    E.g. if our email-value to be inserted in SQL-string is:

        test@mail.ru'); DROP TABLE Person; --
<p> it will trasnform in the next resulting SQL-string:

        INSERT INTO Person VALUES(1,'test',25,'test@mail.ru'); DROP TABLE Person; --')
<p>that will cause deleting the table 'Person' from a database completely. The "<b>--</b>" comments 
the following content. We should mention that it will affect database if we don't have
an <b>@Email</b>-annotation to validate an e-mail in our class Person.
If we try to continue operating with our database we will get the next errorr during
java-code execution:
        
        ERROR: relation "person" does not exist

So we need to protect our applications from various sql-injections. Such a protection
is implemented in the class <b>PreparedStatement</b>.

<h3>class PreparedStatement</h3>
Class <b>PreparedStatement</b> is used for all the data that is received from a user.
SQL-statement will be compiled once and won't be changed. User data can be inserted only
in specified places within statement anc can affect its logic.

Instead of using <i>java.sql.</i><b>Statement</b>

    Statement statement = connection.createStatement();
    String SQL = "INSERT INTO Person VALUES(id + ",'" + name + "'," + age + ",'" +
        email + "')";
    statement.executeUpdate(SQL);

We will use <i>java.sql.</i><b>PreparedStatement</b>:

    PreparedStatement preparedStatement = 
        connection.prepareStatement("INSERT INTO Person VALUES(?,?,?,?");
    
    preparedStatement.setint(1, ++PEOPLE_COUNT);
    preparedStatement.setString(2, person.getName());
    preparedStatement.setint(3, person.getAge());
    preparedStatement.setString(4, person.getEmail());

    prepredStatement.executeUpdate();

Now if we try to input the SQL-injection described above we will get a new entry but
with an email representing our tricky expression from the SQL-injection.
<ul>Class <b>PreparedStatement</b><br>
<li><u>more comfortable</u>: there is no need to concatenate multiple fragments of a string;</li>
<li><u>protects from SQL injections</u></li>
<li><u>faster than class <b>Statement</b></u> especially if threre are a lot of queries</li>
</ul>

<h4>Why is the class <b>PreparedStatement</b> is much faster than the class <b>Statement</b></h4>
<ul>
<li>With the class <b>PreparedStatement</b> SQL-query will be compiled only once, then 
with the class <b>Statement</b> each query will be compiled. Remember that <b>SQL</b> is
actually just a <u>language</u> and its code should be compiled first.</li>
<li><b>PreparedStatement</b>'s query could be <b>cached by database</b>!)</li>
</ul>

<h3>Workout java-code</h3>

Improve the method <b>void save(Person person)</b> in class <i>dao.</i><b>PersonDAO</b>:

    public void save(Person person) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO person VALUES(?,?,?,?)");
            preparedStatement.setInt(1, ++PEOPLE_COUNT);
            preparedStatement.setString(2, person.getName());
            preparedStatement.setInt(3, person.getAge());
            preparedStatement.setString(4,person.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

Improve the method <b>void show(int id)</b> in class <i>dao.</i><b>PersonDAO</b>.
Also we will introduce an auxiliary method <b>void setData(Person person, ResultSet resultSet) throws SQLException</b>
which will also use within the already recoded to interact with database method 
<b>List<People> index()</b>:

    private void setData(Person person, ResultSet resultSet) throws SQLException {
        person.setId(resultSet.getInt("id"));
        person.setName(resultSet.getString("name"));
        person.setAge(resultSet.getInt("age"));
        person.setEmail(resultSet.getString("email"));
    }

    public Person show(int id) {
        Person person = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM person WHERE id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                person = new Person();
                setData(person, resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

<p> Recode the method <b>void updatePerson(int id, Person updatedPerson)</b></p>:

    public void update(int id, Person updatedPerson) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE person SET name=?, age=?, email=? WHERE id=?"
                );

            preparedStatement.setString(1, updatedPerson.getName());
            preparedStatement.setInt(2, updatedPerson.getAge());
            preparedStatement.setString(3, updatedPerson.getEmail());
            preparedStatement.setInt(4, id);
            
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

Note that if there are more than 1 row in the table <b>Person</b> with the <b>id</b> equal our id
then for all these rows their fields <b>name, age</b> and <b>email</b> will be updated with the
values from our statement.

<p>Let's recode the method <b>void delete(int id)</b>:

    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM person WHERE id=?"
                );
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   <p>Note that if there are more than 1 row in the table <b>Person</b> with the <b>id</b> equal our id
then for all these rows will be deleted.</p> 

<h4>Temporary fix-patch for ID</h4>
Let's get the maximum value of ID from the database at the launch of our application.
We will do it in the static part of the class <b>PersonDAO</b> just after initialisation of
the connection:

    static {
        ...
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT MAX(id) as max_id FROM person";
            ResultSet resultSet =  statement.executeQuery(SQL);
            resultSet.next();
            PEOPLE_COUNT =  resultSet.getInt("max_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

So now we can be sure that we wouldn't have the rows created with the id which is already 
represented in the table. But remember, it is true by the condition that there isn't any concurrent application
working simulteanously with this table aside.

    
