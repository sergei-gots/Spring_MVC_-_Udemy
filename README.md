# Spring MVC - Udemy course
To the Spring Framework Udemy Course by Neil Alishev

https://www.udemy.com/course/spring-alishev/learn/lecture/31011922

<h2>Lessons 44. "Pop-up lists: Tags < Select>  & < Option> "</h2>

Tag <b>< Select></b> resides within a <b>< form></b>.
Within our app we will use <b>xmlns="http://www.thymeleaf.org"</b> to simplify
interaction between form, DAO and entities.

Here we will validate our inputs with <b>regular expressions regExp</b>.
E.g. we will have an additional field in <b>table 'Person'</b> named <b>address</b>.
This address per se must meet some logical structure. Let is structure will be like:
What are we going to do:

<li>1. We will pass <b>from Controller</b>  empty object of <b>Person</b> named <b>person</b> 
<b>to View, or to Form</b>. Also we will pass <b>list of all the persons</b> named <b>people</b>.
Form will represent each person from the list <b>people</b> within <b>PopUp List</b> having  <b>id=person</b>.
For user will be displayed <b>th:text</b> - it is defined to be <b>field 'name'</b> of a person.
As user's choice will be returned <b>th:object=${person}</b> - a new object of person with the  
<b>th:value = ${person.getId()}</b> for <b>th:field="*{id}"</b> of a person:

    <label for="person">Select person:</label>
     <select th:object="${person}" th:field="*{id}" id="person">
        <option th:each ="person : ${people}" th:value="${person.getId()}" th:text="${person.getName()}"/>
     </select>

<li>2. In the acceptor Controller's method we will have <b>@ModelAttribute</b> which <b>will create</b> 
<b>person-instance</b> containing selected in the PopUp list value of <b>id</b>.
</li>    

<h3>Task</h3>

Let's assume some people listed in the table 'Person' have some featured trait. E.g . Some people
can have a feature 'being admin'. It could be either true or false, or even undefined.
<br>To handle this feature let's add a new specific <b>Controller</b> class <b>controllers.AdminController</b>:

<li>1. Add <u>COLUMN</u> <b>adm-flag</b> to the <u>TABLE</u> <b>Person</b>:

        ALTER TABLE Person ADD COLUMN adm_flag BOOLEAN;

So it will be <b>BOOLEAN</b> but with necessity to have a value: so that we don't have
to <b>TRUNCATE</b> our table before adding this column and for already existing rows on table
we will have <b><null></b>values for <u>COLUMN</u> <b>adm-flag</b>.



