package org.project.spring_security_client.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.project.spring_security_client.entity.User;
import org.project.spring_security_client.entity.VerificationToken;
import org.project.spring_security_client.event.RegistrationCompleteEvent;
import org.project.spring_security_client.model.PasswordModel;
import org.project.spring_security_client.model.UserModel;
import org.project.spring_security_client.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel, final HttpServletRequest request){

        User user = userService.registerUser(userModel);
        publisher.publishEvent(new RegistrationCompleteEvent(user,applicationUrl(request)));
        return "Success";
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token){
        String result = userService.validateVerificationToken(token);
        if(result.equalsIgnoreCase("valid")){
            return "User verified successfully !";
        }
        return "Bad user";
    }

    @GetMapping("/resendVerifyToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest request){
        VerificationToken verificationToken =
                userService.generateNewVerificationToken(oldToken);
        User user = verificationToken.getUser();
        resendVerificationTokenMail(user, applicationUrl(request), verificationToken);
        return "Verification link sent";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest request){
        User user = userService.findUserByEmail(passwordModel.getEmail());
        String url = "";
        if(user != null){
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);
            url = passwordResetTokenMail(user,applicationUrl(request), token);
        }
        return url;
    }

    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token, @RequestBody PasswordModel passwordModel){
        String result = userService.validatePasswordResetToken(token);
        if(!result.equalsIgnoreCase("valid")){
            return "invalid token";
        }
        Optional<User> user = userService.getUserByPasswordResetToken(token);
        if(user.isPresent()){
            userService.changePassword(user.get(),passwordModel.getNewPassword());
            return "password reset successful";
        }else {
            return "invalid token";
        }
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody PasswordModel passwordModel){
        User user = userService.findUserByEmail(passwordModel.getEmail());
        if(!userService.checkIfValidOldPassword(user,passwordModel.getOldPassword())){
            return "invalid old password";
        }
        userService.changePassword(user, passwordModel.getNewPassword());
        return "password changed successfully";
    }

    private String passwordResetTokenMail(User user, String applicationUrl, String token) {
        String url = applicationUrl + "/savePassword?token=" +token;

        log.info("Click the link below to reset the password: {}", url);
        return url;
    }

    private void resendVerificationTokenMail(User user, String applicationUrl, VerificationToken verificationToken ) {
        String url = applicationUrl + "/verifyRegistration?token=" +verificationToken.getToken();

        //sendVerificationEmail()
        log.info("Click to verify your account: {}", url);
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://"+
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }
}
