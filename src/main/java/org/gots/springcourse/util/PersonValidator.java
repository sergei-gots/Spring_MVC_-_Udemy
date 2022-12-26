package org.gots.springcourse.util;

import org.gots.springcourse.dao.PersonDAO;
import org.gots.springcourse.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class PersonValidator implements Validator
{

    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        //First we should perform downcast:
        Person person = (Person)target;
        String email = person.getEmail();
        Optional<Person> opt = personDAO.getPersonByEMail(email);
        if(opt.isPresent()) {
            //if entry with the entered e-mail already exist in DB
            if(opt.get().getId() != person.getId()) {
                //and if it's not the same entry we try updating
                //we will reject this try
                errors.rejectValue("email", "", "E-mail \"" + email + "\" is already in use.");
            }
        }
    }
}
