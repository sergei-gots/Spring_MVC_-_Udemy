package org.gots.springcourse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("/hello-world")
    public String sayHello() {
        //Our representation, or VIEW will respond with "Hello World"
        //Our view will be named hello_world.html.
        return "hello_world";
    }
}
