package org.gots.springcourse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/first")
public class FirstController {
    @GetMapping("/hello")
    public String helloPage(HttpServletRequest request) {       //Here we will use an instance of HttpServletRequest
                                                                //to access request parameters
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        System.out.println("Hello, " +  name + " " + surname + ":)");

        return "first/hello";
    }

    @GetMapping("/goodbye")
    //Here we will use parameter-annotations @RequestParam  to access request parameters:
    public String goodByePage(@RequestParam(value="name", required=false) String name,
                                  @RequestParam(value = "surname", required=false) String surname) {

        //In case if these annotated parameters are missing in the HTTP-request
        //The Error 404 will be appeared.
        //To avoid this we can use the field required=false

        System.out.println("Goodbye, " +  name + " " + surname + ":)");
        return "first/goodbye";
    }

}
