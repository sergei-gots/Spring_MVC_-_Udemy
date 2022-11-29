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

<h4>1. ALTER TABLE Person </h4>

<li>Add <u>COLUMN</u> <b>adm-flag</b> to the <u>TABLE</u> <b>Person</b>:

        ALTER TABLE Person ADD COLUMN adm_flag BOOLEAN;

So it will be <b>BOOLEAN</b> but with necessity to have a value: so that we don't have
to <b>TRUNCATE</b> our table before adding this column and for already existing rows on table
we will have <b><null></b>values for <u>COLUMN</u> <b>adm-flag</b>.
<br>
<br>

<h4>HTML Logic</h4>

<li>2. Consider <b>application logic</b>. It will determine how user will operate in web-browser:
    <ul>
    <li> <u>Page</u> <b>/admin</b> will print list of all the admins;
    <li> <u>Page</u> <b>/admin/select</b> will provide to a user option to select a person to be admin
    <li> <u>Address</u> <b>/admin/add</b> will be used to handle html-<b>PATCH</b>-request to make selected person be admin.
    </ul>

<h4>class controller.AdminController {}</h4>

<li>3. Now we will add <b>controller to handle http-requests</b>:


    @Component
    @RequestMapping("/admin")
    public class AdminController {
    
        @Autowired
        private final PersonDAO personDAO;
    
        public AdminController(PersonDAO personDAO) {
            this.personDAO = personDAO;
        }
    
        @GetMapping("/select")
        public String selectAdmin(Model model) {

            //Here we ADD an EMPTY PERSON object to BE FILLED WITH AN ID
            //with the choise on POPUP-LIST on the form
            model.addAttribute(("person"), new Person());

            //Here we ADD a LIST OF PEOPLE to BE SHOWN for choise
            //within the POPUP-LIST on the form
            model.addAttribute("people", personDAO.index());

            //All this data within model-instance will be 
            //passed to the view "/admin/select"
            return "/admin/select";
        }
    
        @PatchMapping("/add")
        public String makeAdmin(@ModelAttribute("person") Person person) {
            
            //With the DAO we modify person with ID got from the form
            //and make this person to be admin
            personDAO.makeAdmin(person.getId());
            
            //We make here redirect on the page which will show list of admins
            return "redirect:/admin";
        }
    
        @GetMapping("")
        public String index(Model model) {
            //under the adress "/admin" we catch with @GetMapping("")
            //we will get with the DAO list of admins
            //and pass them to be shown on the view "/admin/index.html"
            model.addAttribute("people", personDAO.indexAdmin());

            //Here we indicate that we will go to the /admin/index.html
            return "/admin/index";
        }
    }

After that we should write missing methods <b>makeAdmin(ind id)</b> 
and <b>indexAdmin</b> in <u>class</u> <b>dao.PersonDAO</b>.

<h4>Modifying DAO-class</h4>

<li> 4. Let's implement in <b>DAO</b>-class these new methods mentioned and used in <b>controller</b>-class:

    @Component
    public class PersonDAO {

        ...
        public void makeAdmin(int id) {
            jdbcTemplate.update( "UPDATE person SET adm_flag=TRUE WHERE id=?", id);
        }
    
        public List<Person> indexAdmin() {
            return jdbcTemplate.query("SELECT * FROM person WHERE adm_flag=true", new BeanPropertyRowMapper<>(Person.class));
        }
    }

<h4>Views</h4>
<li> 5. Let's add view: 
    <ul> 
    <li>Let's create folder <u>webapp/WEB-INF/views</u><b>/admin</b>
    <ul>
        <li>Let's create view <b>index.html</b> there;
        <li>Let's create view <b>select.html</b> 
    </ul>
    </ul>
<br>
<u>View</u> <b>index.html</b> will have the next <b>< body ></b>:

    <body>
    <h3>List of admins:</h3>
    <div th:each="person : ${people}">
        <a th:href="@{/people/{id}(id=${person.getId()})}"
           th:text="${person.getName() + ',  ' + person.getAge}">user</a>
    </div>
    <br>
    <hr>
    <a href="/admin/select">Add an admin</a>
    <br>
    <a href="/admin">Admins list</a>
    <br>
    <a href="/people">Back to all the persons list</a>
    </body>

<br>
<u>View</u> <b>select.html</b> will have the next <b>< body ></b>:

<body>

    <form th:method="PATCH" th:action="@{/admin/add}">
        <label for="person">Select person:</label>
        <select th:object="${person}" th:field="*{id}" id="person">
            <option th:each ="person : ${people}" th:value="${person.getId()}" th:text="${person.getName()}"/>
        </select>
        <br>
        <br>
            <input type="submit" value="Mark person as admin"/>
    </form>
    <br>
    <hr>
    <a href="/admin">Admins list</a>
    <br>
    <a href="/people">All the persons</a>
    </body>

So that it will have <b>< form ></b> with the <u>th:method</u> <b>"PATCH"</b>
which will cause <u>th:action</u> <b>"@{/admin/add}"</b> passing to <b>controller</b> 
by pressing on <u>submit</u>-button. And it has within form <b> < select >  < option > </b>-section
which will be decomposed with <b>th</b>ymeleaf to a standart <b> < select >  < option > </b>-PopUp List.

<p>
<br>
    So that our application is ready though it does not have a completeness of functionality
such as removing of an admin status from a person or such as stringency like adding an admin
from list containing only persons that aren't admin yet:) ok! 


</p>

