package com.hcmus.auction.service.impl;

import com.hcmus.auction.common.util.MailUtil;
import com.hcmus.auction.common.util.OtpUtil;
import com.hcmus.auction.common.variable.ErrorMessage;
import com.hcmus.auction.exception.GenericException;
import com.hcmus.auction.service.definition.AuthService;
import com.hcmus.auction.service.impl.email.EmailDetail;
import com.hcmus.auction.service.impl.email.EmailServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
        EmailDetail emailDetail = new EmailDetail(email, EMAIL_BODY, EMAIL_OBJECT);

        otpHistoryService.addNewOtpForRegistration(otpCode, userId);
        emailService.sendEmail(emailDetail);
        return userId;
    }

    @Override
    public void verifyRegistrationOtpCode(String userId, String otpCode) {
        if (userService.getById(userId) == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_USER.getMessage());
        otpHistoryService.verifyRegistrationOtpCode(userId, otpCode);
        userService.activateAccount(userId);
    }
}
