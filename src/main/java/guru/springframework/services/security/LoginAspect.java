package guru.springframework.services.security;

import guru.springframework.services.security.loginfailureevent.LoginFailureEvent;
import guru.springframework.services.security.loginfailureevent.LoginFailureEventPublisher;
import guru.springframework.services.security.loginsuccessevent.LoginSuccessEvent;
import guru.springframework.services.security.loginsuccessevent.LoginSuccessEventPublisher;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginAspect {

    private LoginFailureEventPublisher failureEventPublisher;
    private LoginSuccessEventPublisher successEventPublisher;


    @Autowired
    public void setFailureEventPublisher(LoginFailureEventPublisher failureEventPublisher) {
        this.failureEventPublisher = failureEventPublisher;
    }

    @Autowired
    public void setSuccessEventPublisher(LoginSuccessEventPublisher successEventPublisher) {
        this.successEventPublisher = successEventPublisher;
    }

    @Pointcut("execution(* org.springframework.security.authentication.AuthenticationProvider.authenticate(..))")
    public void doAuthenticate(){

    }

    @Before("guru.springframework.services.security.LoginAspect.doAuthenticate() && args(authentication)")
    public void logBefore(Authentication authentication){
        System.out.println("This is before Authenticate Method: authentication: " + authentication.isAuthenticated());
    }

    @AfterReturning(value = "guru.springframework.services.security.LoginAspect.doAuthenticate()", returning = "authentication")
    public void logAfterAuthenticate(Authentication authentication){
        System.out.println("This is after Authenticate Method: authentication: " + authentication.isAuthenticated());

        successEventPublisher.publish(new LoginSuccessEvent(authentication));

    }

    @AfterThrowing(value = "guru.springframework.services.security.LoginAspect.doAuthenticate() && args(authentication)")
    public void logAuthenticationException(Authentication authentication){
        String userDetails = (String) authentication.getPrincipal();
        System.out.println("Login failed for user: " + userDetails);

        failureEventPublisher.publish(new LoginFailureEvent(authentication));
    }
}
