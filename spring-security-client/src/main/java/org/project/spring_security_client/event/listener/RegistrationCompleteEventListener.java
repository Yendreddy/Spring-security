package org.project.spring_security_client.event.listener;

import lombok.extern.slf4j.Slf4j;
import org.project.spring_security_client.entity.User;
import org.project.spring_security_client.event.RegistrationCompleteEvent;
import org.project.spring_security_client.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
         //Create the verification token for the user with link
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token, user);
        //Send mail to user
        String url = event.getApplicationUrl() + "/verifyRegistration?token=" +token;

        //sendVerificationEmail()
        log.info("Click to verify your account: {}", url);
    }
}
