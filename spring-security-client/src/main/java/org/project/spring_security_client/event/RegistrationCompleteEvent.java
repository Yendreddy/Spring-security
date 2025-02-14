package org.project.spring_security_client.event;

import lombok.Getter;
import lombok.Setter;
import org.project.spring_security_client.entity.User;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private User user;
    private String applicationUrl;

    public RegistrationCompleteEvent(User user, String applicationUrl) {
        super(user);
        this.applicationUrl = applicationUrl;
        this.user = user;
    }
}
