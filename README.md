# Spring MVC - Udemy course
To the Spring Framework Udemy Course by Neil Alishev

https://www.udemy.com/course/spring-alishev/learn/lecture/31009302

<h2>Lesson 24. "PATCH (UPDATE) and DELETE requests.
<br>CRUD-Application, part III"</h2>

<h3>HTML5 vs HTTP</h3>
Values of the <u>HTML-Methods</u> are:
<br>
<a href=html.com/attributes/form-method/>html.com/attributes/form-method</a>
<br>
<br>- <b>GET</b>
<br>- <b>POST</b>
<br>
<br><u>HTTP Methods</u> are:
<br>
<br>- <b>GET</b>
<br>- <b>POST</b>
<br>- <b>PATCH</b>
<br>- <b>DELETE</b>
<br>- PUT
<br>- HEAD
<br>- CONNECT
<br>- OPTIONS
<br> ...

<h4>How to use all the HTTP methodw with HTML</h4>

<b>PATCH, DELETE, PUT</b>-HTTP-requests 
<br>are expressed with HTML-<b>POST</b> request
<br>having a <b>hidden field <u>_method</u></b>:
<code>
    
    < form method="post" action="/people/1">
        < input type="hidden" name="_method" value="patch">=$0
        ...
    < /form>
</code>



<h3>HTTP "UPDATE" method</h3>
Request to update:   <b>GET /people/:id/edit</b>
<br>Update request:      <b>PATCH /people/:id</b>
<br>Implementation in the <b>Controller</b>:
<code>

    /** Request to edit **/
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));
        return "/people/edit";
    }
    
    /** Request to update that has been edited  **/
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        personDAO.update(id, person);
        return  "redirect:/people";
    }

</code>

Implementation in the <b>PersonDAO</b>:
<code>
    
    public void update(int id, Person editedPerson) {
        Person personToBeUpdated = show(id);
        personToBeUpdated.setName(editedPerson.getName());
    }
</code>

HTML "/people/edit.html" will have:
    <code>

    < form th:method="PATCH" th:action="@{/people/{id}(id=${person.getId()})}" th:object="${person}">
        < label for="name">Enter name: </label>
        < input type="text" th:field="*{name}" id="name"/>
        < br>
        < input type="submit" value="Update"/>
    < /form>
    
</code>

After we run our app and go e.g. to the "http://localhost:8080/people/2/edit"
we will get the HTML-page with the next body:

<code>
    < body>

    < form method="post" action="/people/2">
    
    < input type="hidden" name="_method" value="PATCH">
    < label for="name">Enter name: </label>
    < input type="text" id="name" name="name" value="Bob">
    < br>
    < input type="submit" value="Update!">
    < /form>

    < /body>
</code>

and will have such visualisation:
<br>
<code>
<body>
<form method="post" action="/people/2"><input type="hidden" name="_method" value="PATCH">
    <label for="name">Enter name: </label>
    <input type="text" id="name" name="name" value="Bob">
    <br>
    <input type="submit" value="Update!">
</form>
</body>
</code>

So we have a <b>hidden field "<u>_method</u>"</b> in our
form and in order to read <b>hidden fields</b> we will
add an <b>HTTP-filter to</b> our <b>Servlet</b>.
To do this, we add the next code to the class <b>MySpringDispatcherServletInitializer</b>:
<code>

    import org.springframework.web.filter.HiddenHttpMethodFilter;
    ...
    import javax.servlet.ServletContext;
    import javax.servlet.ServletException;
    
    ...    
    public class MySpringMVCDispatcherServletInitializer extends
        AbstractAnnotationConfigDispatcherServletInitializer {
    ...
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        
        super.onStartup(servletContext);

        ...
        addHiddenHttpMethodFilter(servletContext);
    }

     private void addHiddenHttpMethodFilter(ServletContext servletContext) {
        servletContext.addFilter(
                        "hiddenHttpMethodFilter",
                        new HiddenHttpMethodFilter()).
                addMappingForUrlPatterns(null ,true, "/*");
    }
    }
</code>

Remember, we have this class <b>MySpringMvcDispatcherServletInitializer</b> in order to replace the
config.file "<b>web.xml</b>". Later, when we will use <b>Spring Boot</b> this configuration
for filtering hidden fields will take only one line:), ok.

<h3>HTTP "DELETE" method</h3>
Delete request: 
<code>DELETE /people/:id</code>

<br>Implementation in the <b>Controller</b>:
<br>
<code>

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
</code>
Implementation in the <b>PersonDAO</b>:
<code>

    public void delete(int id) {
        people.removeIf(p -> p.getId()==id);
    }
</code>
HTML "/people/edit.html" will have the next code:
    <code>

    <form th:method="DELETE" th:action="@{/people/{id}/(id=${person.getId()})}">
        <input type="submit" value="Delete"/>
    </form>
</code>

After we run our app and go e.g. to the "http://localhost:8080/people/2/edit"
we will get the HTML-page with the next body:
<code>
        
    < body>
        ...
        < form method="post" action="/people/1/"><input type="hidden" name="_method" value="DELETE"/>
        < input type="submit" value="Delete"/>
        < /form>
        ...
        < /body>
</code>
and will have as a visualisation just a button "Delete":
<br>
<code>
<br>
<form method="post" action="/people/1/"><input type="hidden" name="_method" value="DELETE"/>
        <input type="submit" value="Delete"/>
    </form>
</code>

