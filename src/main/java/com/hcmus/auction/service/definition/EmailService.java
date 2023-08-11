package com.hcmus.auction.service.definition;

import com.hcmus.auction.service.impl.email.EmailDetail;

public interface EmailService {
    void sendEmail(EmailDetail emailDetail);
}
