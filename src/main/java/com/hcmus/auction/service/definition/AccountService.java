package com.hcmus.auction.service.definition;

import com.hcmus.auction.common.variable.response.LoginResponse;
import com.hcmus.auction.model.entity.User;

public interface AccountService {
    void updateEmail(String userId, String email);
    boolean isExistedAccount(String email);
    void addNewAccount(User user, String email, String password);
    void activateAccount(String userId);
    void changePassword(String userId, String oldPassword, String newPassword);
    String getUserByEmail(String email);
    void resetPassword(String userId, String password);
    LoginResponse login(String email, String password);
}
