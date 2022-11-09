# Spring MVC - Udemy course
To the Spring Framework Udemy Course by Neil Alishev

https://www.udemy.com/course/spring-alishev/learn/lecture/31009306

<h2>Lesson 25. "Form Validation. @Validation</h2>

<h3>Form Validation Engine</h3>

https://mvnrepository.com/artifact/org.hibernate.validator/hibernate-validator

Dependency to be added in <u>pom.xml</u>:
<code>

    <!-- https://mvnrepository.com/artifact/org.hibernate.validator/hibernate-validator -->
        <dependency>
            <groupId>org.hibernate.validator</groupId>
            <artifactId>hibernate-validator</artifactId>
            <version>8.0.0.Final</version>
        </dependency>

<text>
    Modifications in the model class <b>Person</b>:<text/>

<code>
    
    import javax.validation.constraints.Email;
    import javax.validation.constraints.Min;
    import javax.validation.constraints.NotEmpty;
    import javax.validation.constraints.Size;

    public class Person {
        ...
        @NotEmpty(message = "Name should not be empty")
        @Size(min = 2, max = 30, message = "Name should have between 2 and 30 characters")
        private String name;

        @Min(value=0, message = "Age should be greater than or equal to 0")
        private int age;

        @NotEmpty(message = "E-mail should not be empty")
        @Email(message = "E-mail should be valid")
        private String email;
    
<code/>
<text>Modifications in the <b>PeopleController</b>:
<code>
        
    import javax.validation.Valid;
    ...
    import org.springframework.validation.BindingResult;
    ...
    public class PeopleController {
    ...
        @PostMapping()
        public String create(@ModelAttribute("person") @Valid Person person,
                             BindingResult bindingResult) {

            if(bindingResult.hasErrors()) {
                return "/people/new";
            }
            ...
        }
    
        @PatchMapping("/{id}")
        public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                             @PathVariable("id") int id) {
            if(bindingResult.hasErrors()) {
                return "/people/edit";
            }
            ...
        }
    }

<code/>
<p>Modifications in the views.
<br><b><u>new.html</u></b> and <b><u>edit.html:</u></b>
<code>
        
    <body>
        <form th:method="..." th:action="@{/people}" th:object="${person}">
            ...
            <div style="color:red" th:if="${#fields.hasErrors('name')}" th:errors="*{name}">Name Error</div>
        <br>
            ...
            <div style="color:red" th:if="${#fields.hasErrors('age')}" th:errors="*{age}">Age Error</div>
        <br>
            ...
            <div style="color:red" th:if="${#fields.hasErrors('email')}" th:errors="*{email}">EMail Error</div>
        <br>
    </form>
</body>
</p>



