package guru.springframework.commands.validators;

import guru.springframework.commands.CustomerForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CustomerFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return CustomerForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        CustomerForm customerForm = (CustomerForm) o;

        if(!customerForm.getPasswordText().equals(customerForm.getPasswordTextConf())){
            errors.rejectValue("passwordText", "PasswordsDontMach.customerForm.passwordText", "Passwords don't match");
            errors.rejectValue("passwordTextConf", "PasswordsDontMach.customerForm.passwordTextConf", "Passwords don't match");
        }
    }
}
