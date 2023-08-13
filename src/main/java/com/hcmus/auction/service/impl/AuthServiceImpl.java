package com.hcmus.auction.service.impl;

import com.hcmus.auction.common.util.MailUtil;
import com.hcmus.auction.common.util.OtpUtil;
import com.hcmus.auction.common.variable.ErrorMessage;
import com.hcmus.auction.common.variable.response.LoginResponse;
import com.hcmus.auction.exception.GenericException;
import com.hcmus.auction.service.definition.AuthService;
import com.hcmus.auction.service.impl.email.EmailDetail;
import com.hcmus.auction.service.impl.email.EmailServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserServiceImpl userService;
    private final EmailServiceImpl emailService;
    private final OtpHistoryServiceImpl otpHistoryService;

    @Override
    public String signUpNewAccount(String email, String password, String name, String address) {
        if (userService.isExistedAccount(email))
            throw new GenericException(ErrorMessage.EXISTED_ACCOUNT.getMessage());
        String userId = userService.addNewUser(email, password, name, address);

        String otpCode = OtpUtil.createOtpCode();
        final String EMAIL_BODY = MailUtil.getEmailBodyWhenReceivingOtp(name, otpCode);
        final String EMAIL_OBJECT = "Solar Banking: Please verify your email address";
        final Boolean IS_USED_FOR_SIGN_UP = true;
        EmailDetail emailDetail = new EmailDetail(email, EMAIL_BODY, EMAIL_OBJECT);

        otpHistoryService.addNewOtp(otpCode, userId, IS_USED_FOR_SIGN_UP);
        emailService.sendEmail(emailDetail);
        return userId;
    }

    @Override
    public void verifyRegistrationOtpCode(String userId, String otpCode) {
        final Boolean IS_USED_FOR_SIGN_UP = true;
        if (userService.getById(userId) == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_USER.getMessage());
        otpHistoryService.verifyOtpCode(userId, otpCode, IS_USED_FOR_SIGN_UP);
        userService.activateAccount(userId);
    }

    @Override
    public String sendForgotPasswordOtpViaEmail(String email) {
        List<String> userInfo = userService.getUserByEmail(email);
        String userId = userInfo.get(0);
        String name = userInfo.get(1);

        String otpCode = OtpUtil.createOtpCode();
        final String EMAIL_BODY = MailUtil.getEmailBodyWhenReceivingOtp(name, otpCode);
        final String EMAIL_OBJECT = "Solar Banking: Please verify your email address";
        final Boolean IS_USED_FOR_SIGN_UP = false;
        EmailDetail emailDetail = new EmailDetail(email, EMAIL_BODY, EMAIL_OBJECT);

        otpHistoryService.addNewOtp(otpCode, userId, IS_USED_FOR_SIGN_UP);
        emailService.sendEmail(emailDetail);
        return userId;
    }

    @Override
    public String verifyForgotPasswordOtpCode(String userId, String otpCode) {
        final Boolean IS_USED_FOR_SIGN_UP = false;
        if (userService.getById(userId) == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_USER.getMessage());
        return otpHistoryService.verifyOtpCode(userId, otpCode, IS_USED_FOR_SIGN_UP);
    }

    @Override
    public void resetPassword(String token, String userId, String password) {
        if (userService.getById(userId) == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_USER.getMessage());
        if (!otpHistoryService.isValidOtpToken(token, userId))
            throw new GenericException(ErrorMessage.INVALID_OTP_TOKEN.getMessage());
        userService.resetPassword(userId, password);
    }

    @Override
    public LoginResponse login(String email, String password) {
        return userService.login(email, password);
    }
}
