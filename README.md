# Spring MVC - Udemy course
To the Spring Framework Udemy Course by Neil Alishev

https://www.udemy.com/course/spring-alishev/learn/lecture/31009300

<h2>Lesson 23. "@ModelAttribute annotation. Form Template (Thymeleaf).
<br>CRUD-Application, part II"</h2>

<h3>@ModelAttribute annotation</h3>

<code>
@GetMapping("/new")
    <br>public String newPerson(Model model) {
    
        model.addAttribute("person", new Person());
        return "people/new";
    }
</code>
can be simplified to:
<br><br>
<code>
@GetMapping("/new")
    <br>public String newPerson(@ModelAttribute("person") Person person) {

        return "people/new";
    }
</code>

<h3>redirect</h3>
We can redirect browser to a specified address with
the key-word "<b>redirect:</b>" as the prefix in the returning
string of a Controller-method. E.g.:

<code>
   @PostMapping()
    public String create(@ModelAttribute("person") Person person) {
        
        personDAO.save(person);
        return "redirect:/people";
    }
<code/>

If this key-word isn't applied here, the next Exception will be 
thrown:

<code>java.io.FileNotFoundException: Could not open ServletContext resource [/WEB-INF/views//people.html]
<code/>

see https://www.youtube.com/watch?v=lesNd-lqUiM for all the details.

<h3>How to use Cyrillic?</h3>

To handle cyrillic text correctly the next code was added:

<code>
    
    @Configuration
    ...
    public class SpringConfig {

    ...

    @Bean
    public SpringResourceTemplateResolver templateResolver() {

    ...

    templateResolver.setCharacterEncoding("UTF-8");

    ...

    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

    ...

    resolver.setCharacterEncoding("UTF-8");

    ...

    }
    }
<code/>

<code>

    import javax.servlet.FilterRegistration;
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
        addCharacterEncodingFilterUTF_8(servletContext);
    }

    private void addCharacterEncodingFilterUTF_8(ServletContext servletContext) {

        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);

        FilterRegistration.Dynamic filterRegistration = servletContext.addFilter("encodingFilter", filter);
        filterRegistration.addMappingForUrlPatterns(null, true, "/*");

    }
    ...
    }
<code/>
