package ru.osipov.webPractice.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.osipov.webPractice.DAO.PersonDao;
import ru.osipov.webPractice.models.Person;

@Component
public class PersonValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (person.getAge() == 0) {
            errors.rejectValue("age","", "Age should be greater than 0");
        };
    }
}
