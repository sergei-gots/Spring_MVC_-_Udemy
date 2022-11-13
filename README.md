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
