# Spring MVC - Udemy course
To the Spring Framework Udemy Course by Neil Alishev

https://www.udemy.com/course/spring-alishev/learn/lecture/31011706
https://www.udemy.com/course/spring-alishev/learn/lecture/31011890

<h2>Lessons 42 & 43. "Prodigy validation. Spring Validator"</h2>

So we have been developing a <b>CRUD-Application for entity 'Person'</b>.
Within the implementation of the entity 'Person' in the class Person we use
validating @annotations for fields.
<br>Also we use @annotation <b>@Valid</b> with the <b>Person person</b>
argument within <b>Controller class PeopleController</b>
and have also argument <b>BindingResult bindingResult</b>
out there to accumulate error messages and verify if there are
any on our forms, our pages or our web-<b>representations</b>.
<br>The such validations we use can operate only on the level
of data getting from user form. So that some errors will remain unchecked
until they meet constraints of database. And such as errors will cause detailed
error reports that are not appropriate to be displayed to the end user of the
application. 
<p>E.g., we have created person with e-mail <b>test@email.com</b>
and try creating a new one with the same email. But
we remember we have <b>UNIQUE</b>-constraint set on field <b>email</b>
on <p>table 'Person'</p>. Because e-mail address is syntax correct
it will be successfully validated, but by trying inserting
this entry in table we will meet DB-constraint, and user
will get the next detailed message in browser:

    HTTP Status 500 – Internal Server Error
    Type Exception Report

    Message Request processing failed; nested exception is org.springframework.dao.DuplicateKeyException: PreparedStatementCallback; SQL [INSERT INTO person(name, age, email) VALUES (?,?,?)]; ERROR: duplicate key value violates unique constraint "person_email_key"

    Description The server encountered an unexpected condition that prevented it from fulfilling the request.

    Exception

    org.springframework.web.util.NestedServletException: Request processing failed; nested exception is org.springframework.dao.DuplicateKeyException: PreparedStatementCallback; SQL [INSERT INTO person(name, age, email) VALUES (?,?,?)]; ERROR: duplicate key value violates unique constraint "person_email_key"
    Detail: Key (email)=(test@mail.com) already exists.; nested exception is org.postgresql.util.PSQLException: ERROR: duplicate key value violates unique constraint "person_email_key"
    Detail: Key (email)=(test@mail.com) already exists.
    org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1014)
    org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:909)
    javax.servlet.http.HttpServlet.service(HttpServlet.java:681)
    ...

In order to validate values immediately within Database we can use
<div style="color:green"><b>Spring Interface Validator</b> </div>






