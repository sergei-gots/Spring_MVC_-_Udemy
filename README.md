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
    
    @GetMapping("/{id}/edit") //Request for update

    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));
        return "/people/edit";
    }

    @PatchMapping("/id")    //Update request
    public String update(Model model, @PathVariable("id") int id) {
        personDAO.update(id, personDAO.show(id));
        return  "redirect:people";
    }

</code>
Implementation in the <b>PersonDAO</b>:
<code>
    
    public void update(int id, Person person) {
        Person personToBeUpdated = show(id);
        personToBeUpdated.setName(person.getName());
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
</br>

<h3>HTTP "DELETE" method</h3>
Delete request: DELETE /people/:id

<h3>Request handling in SPRING</h3>
is provided with <b>Filter</b>-object.
In SPRING Boot it will be made with a line in a config. file.