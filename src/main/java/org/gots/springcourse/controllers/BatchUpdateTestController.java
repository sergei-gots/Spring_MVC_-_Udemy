package org.gots.springcourse.controllers;

import org.gots.springcourse.dao.PersonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test-batch-update")
public class BatchUpdateTestController {
    private final PersonDAO personDAO;
    @Autowired
    public BatchUpdateTestController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String getIndex() {
        return "/test-batch-update/index";
    }
    @GetMapping("/1000-ordinary-requests")
    public String perform1000Requests(){

        personDAO.test1000UpdateRequests();
        return("redirect:/people");
    }
    @GetMapping("/1-batch-request")
    public String performBatchUpdate(){
        personDAO.testBatchUpdate();
        return ("redirect:/people");
    }
    @GetMapping("/delete-test-batch-data")
    public String performClearDBfromBatchTestData() {
        personDAO.deleteTestBatchData();
        return ("redirect:/people");
    }
}
