package guru.springframework.services.security.loginfailureevent;

import guru.springframework.domain.User;
import guru.springframework.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class LoginFailureEventHandler implements ApplicationListener<LoginFailureEvent> {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(LoginFailureEvent event) {

        Authentication authentication = (Authentication) event.getSource();
        System.out.println("LoginEvent Failure for: " + authentication.getPrincipal());
        updateUserAccount(authentication);
    }

    private void updateUserAccount(Authentication authentication) {
        User user = userService.findByUserName((String) authentication.getPrincipal());

        if(user != null){
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);

            if(user.getFailedLoginAttempts() > 5){
                user.setEnabled(false);
            }
            System.out.println("Valid User name, updating failed attempts" + user);
            userService.saveOrUpdate(user);
        }
    }
}
