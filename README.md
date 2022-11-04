# Spring_MVC_-_Udemy_course
To the Spring Framework Udemy Course by Neil Alishev

https://www.udemy.com/course/spring-alishev/learn/lecture/31009296

<h2>Lesson 22. "CRUD. REST. DAO-Pattern"</h2>

<h3>CRUD</h3>
It is a standard classification of functions manipulating data.
<br>These are 4 basic functions 
which are used in work with Data Bases:
<br> - <b>CREATE</b>
<br> - <b>READ</b>
<br> - <b>UPDATE</b>
<br> - <b>DELETE</b>
<br><br>The majority of web-applications are CRUD-Apps

<h3>REST</h3>
It is a Web-apps project pattern.
<br>It describes how with HTTP will a client interact with a server,
i.e. which HTTP-request will provide the CRUD model.
There are 4 operations corresponding 4 CRUD-functions.
These are:
<br> - <b>GET</b>
<br> - <b>POST</b>
<br> - <b>PATCH</b>
<br> - <b>DELETE</b>

<h3> CRUD - REST correspondence:</h3>
<br><b>GET</b>
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
/posts
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
<b>READ</b>
<br>
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
/posts/:id
<br>
<br><b>POST</b>
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
/posts
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
<b>CREATE</b>
<br>
<br><b>PATCH</b>
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
/posts/:id
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
<b>UPDATE</b>
<br>
<br><b>DELETE</b>
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
/posts/:id
&nbsp&nbsp&nbsp&nbsp&nbsp
<b>DELETE</b>
<br>
<br>

<h3>DAO</h3>
<b><u>Data Access Object</u></b>
The logic for interaction with DB is represented with a special class.
Such an approach is called the DAO projecting pattern.
Such classes will be created for each entity represented with DB.
A DAO-class usually represents SQL-code for interacting with a DB.
<br>
<b>Hybernate</b>  and <b>Spring Data JPA</b> provide much more abstract
pattern for access DB - <b>repository</b>. This will be considered later in this course.

