package guru.springframework.services.security.loginsuccessevent;

import guru.springframework.domain.User;
import guru.springframework.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessEventHandler implements ApplicationListener<LoginSuccessEvent> {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(LoginSuccessEvent event) {
        Authentication authentication = (Authentication) event.getSource();
        System.out.println("LoginEvent Success for: " + authentication.getPrincipal());
        updateUserAccount(authentication);
    }

    private void updateUserAccount(Authentication authentication) {
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        User user = userService.findByUserName(userDetails.getUsername());

        if(user != null){

            user.setFailedLoginAttempts(0);
            System.out.println("Good login, resetting failed attempts" + user);
            userService.saveOrUpdate(user);
        }


    }
}
