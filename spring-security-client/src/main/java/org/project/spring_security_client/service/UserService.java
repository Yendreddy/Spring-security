package org.project.spring_security_client.service;

import org.project.spring_security_client.entity.User;
import org.project.spring_security_client.entity.VerificationToken;
import org.project.spring_security_client.model.UserModel;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface UserService {

     void createPasswordResetTokenForUser(User user, String token);

    User registerUser(UserModel userModel);

    void saveVerificationTokenForUser(String token, User user);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);

    User findUserByEmail(String email);

    String validatePasswordResetToken(String token);

    Optional<User> getUserByPasswordResetToken(String token);

    void changePassword(User user, String newPassword);

    boolean checkIfValidOldPassword(User user, String oldPassword);
}
