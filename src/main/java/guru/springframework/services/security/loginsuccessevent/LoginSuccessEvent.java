package guru.springframework.services.security.loginsuccessevent;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class LoginSuccessEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public LoginSuccessEvent(Object source) {
        super(source);
    }
}
