# Spring MVC - Udemy course
To the Spring Framework Udemy Course by Neil Alishev

https://www.udemy.com/course/spring-alishev/learn/lecture/31011706
https://www.udemy.com/course/spring-alishev/learn/lecture/31011890

<h2>Lessons 43. "Prodigy validation: Pattern Validation"</h2>

Here we will validate our inputs with <b>regular expressions regExp</b>.
E.g. we will have an additional field in <b>table 'Person'</b> named <b>address</b>.
This address per se must meet some logical structure. Let is structure will be like:

    <C>ountry<,> <C>ity<,> <Index: 5 digits>

e.g.
    
    Germany, Berlin, 10785

Let's add <u>field</u> <b>address</b> to <u>entity class</u> <b>models.Person</b>
    
    public class Person {
        ...
        private String address;
        
        public Person(..., String address) {
            ...
            this.address = address;
        }
        ...
        public String getAddress() {
            return address;
        }
    
        public void setAddress(String address) {
            this.address = address;
        }
        ...    
    }

Let's modify <u>entity table</u> <b>Person</b> in database:

    TRUNCATE TABLE Person;
    
So now the table is empty, we can change structure of the table:

    ALTER TABLE Person ADD COLUMN address varchar(100) NOT NULL;

Then we modify appropriately <u>class</u> <b>dao.PersonDAO</b>:

    ...    
    @Component
    public class PersonDAO {
       ...
        public void save(Person person) {
            jdbcTemplate.update("INSERT INTO person(name, age, email, address) VALUES (?,?,?,?)",
                ...,
                person.getAddress());
        }

        public void update(int id, Person updatedPerson) {
            jdbcTemplate.update("UPDATE person SET name=?, age=?, email=?, address=? WHERE id=?",
                ...
                updatedPerson.getAddress(), id);
        }
    }

And now we also modify <u>views</u> <b>edit.html</b> and <b>new.html</b> by adding out there a field section:

    ...
    <form th:method="..." th:action="@{/people}" th:object="${person}">
    
            ...
        <br>
            <label for="address">Enter address:</label>
            <input type="text" th:field="*{address}" id="address"/>
            <div style="color:red" th:if="${#fields.hasErrors('address')}" th:errors="*{address}">Adress Error</div>
        <br>    
            ...
    </form>

Also let's edit <u>view</u> <b>show.html</b>:

    <body>
        ...
        <p th:text="${'Address: ' + person.getAddress()}">VALUE</p>
        ...  
    
    </body>

Finally we will introduce <u>pattern validation</u> <b>@Pattern</b> 
in the <u>entity class</u> <b>models.Person</b> which will check the input value for address
on correspondence to our address structure mentioned above. Once againg, there should be presented
name of <b>Country</b> beginning with capital letter, then name of <b>City</b>, again, beginning with 
capital letter, and <b>index</b> consisting of 5 digits. All the parts separated by comma and space: '<b>, '</b>.
A good table sheet with many of regular expressions items we can find on  
http://rexegg.com/regex-quickstart.html.
So let's modify our <u>entity class</u> <b>models.Person</b> by adding <u>annotation</u> <b>@Pattern</b>
to the <u>field</u> <b>address</b>:

    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{5}",
    message = """
    Your address should be in the next format: "Country, City, Postal Code (5 digits)" .
    Country and City should begin with a capital letter
    """)
    String address;

At all writing regular expressions is such a kind of art:).
Ok. So now our application is prepared to work with the <u>field</u> <b>address</b> of the <u>entity</u> <b>Person</b>.
Let's try creating a new person:

    Enter name:      Sergei
    Enter age:       0
    Enter e-mail:    test@mail.com
    Enter address:   russia, moscow, 105000
    
<div style="color:red">Your address should be in the next format: Country, City, Postal Code (5 digits). 
Country and City should begin with a capital letter</div>

and if we try typing the address
    
    Enter address:   Russia, Moscow,10500

we will get the same message because there is not space-symbol between city and postal code.
If we try entering

    Enter address: Germany, Berlin, 10785

We will have this entry successfully added to the database:)




