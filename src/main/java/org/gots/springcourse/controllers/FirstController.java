package org.gots.springcourse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/first")
public class FirstController {
    @GetMapping("/hello")
    public String helloPage(HttpServletRequest request,
                            Model model) {

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");

        String message = "Hello";
        if(name!=null) {
            message += ", " + name;
            if (surname != null) {
                message += " " + surname;
            }
        }
        else if(surname != null) {
                message += ", " + surname;
        }
        message+=":)";

        System.out.println(message);
        model.addAttribute("message", message);


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

    @GetMapping("/calculator")
    public String calculatorPage(@RequestParam(value="a", required=false) String a,
                                 @RequestParam(value="b", required = false) String b,
                                 @RequestParam(value="action", required = false) String action,
                                 Model model) {
        String view = "first/calculator";
        String info = """
            Please, use the next syntax to make this calculator work: 
            "http://localhost:8080/calculator?a=X&B=Y&action=ACTION\"
            where
            A and B are integer numbers;
            ACTION - one of the actions:
            multiplication
            addition
            subtraction
            division
            """;
        Integer intA, intB;
        int nA, nB, result;
        char operand;

        try {
            intA = Integer.parseInt(a);
            intB = Integer.parseInt(b);
        } catch(NumberFormatException e) {
            model.addAttribute("message", info);
            return view;
        }

        if(action == null || intA == null || intB == null)  {
            model.addAttribute("message", info);
            return view;
        }

        nA = intA.intValue();
        nB = intB.intValue();

        if(action.compareToIgnoreCase("addition")==0)               {     result = nA + nB; operand = '+'; }
        else if(action.compareToIgnoreCase("subtraction")==0)       {     result = nA - nB; operand = '-';  }
        else if(action.compareToIgnoreCase("multiplication")==0)    {     result = nA * nB; operand = '*';  }
        else if(action.compareToIgnoreCase("division")==0)          {
            if(nB != 0) {
                result = nA / nB; operand = '/';
            }
            else {
                model.addAttribute("message", "Division by zero");
                return view;
            }
        }
        else {
            model.addAttribute("message", info);
            return view;
        }

        model.addAttribute("message", intA.toString() + operand + intB + "=" + result);

        return view;

    }
}
