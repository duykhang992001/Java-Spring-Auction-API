package com.hcmus.auction.model.mapper;

import com.hcmus.auction.model.dto.OtpHistoryDTO;
import com.hcmus.auction.model.entity.OtpHistory;
import com.hcmus.auction.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class OtpHistoryMapper implements GenericMapper<OtpHistory, OtpHistoryDTO> {
    @Override
    public OtpHistory toEntity(OtpHistoryDTO otpHistoryDTO) {
        OtpHistory otpHistory = new OtpHistory();
        User user = new User();

        user.setId(otpHistoryDTO.getUserId());
        otpHistory.setId(otpHistoryDTO.getId());
        otpHistory.setValue(otpHistoryDTO.getValue());
        otpHistory.setEndTimestamp(otpHistoryDTO.getEndTimestamp());
        otpHistory.setStartTimestamp(otpHistoryDTO.getStartTimestamp());
        otpHistory.setIsUsed(otpHistoryDTO.getIsUsed());
        otpHistory.setIsUsedForSignUp(otpHistoryDTO.getIsUsedForSignUp());
        otpHistory.setUser(user);

        return otpHistory;
    }

    @Override
    public OtpHistoryDTO toDTO(OtpHistory object) {
        return null;
    }
}
