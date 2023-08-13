package com.hcmus.auction.service.impl;

import com.hcmus.auction.common.util.BcryptUtil;
import com.hcmus.auction.common.util.TimeUtil;
import com.hcmus.auction.common.variable.ErrorMessage;
import com.hcmus.auction.exception.GenericException;
import com.hcmus.auction.model.dto.OtpHistoryDTO;
import com.hcmus.auction.model.entity.OtpHistory;
import com.hcmus.auction.model.mapper.OtpHistoryMapper;
import com.hcmus.auction.repository.OtpHistoryRepository;
import com.hcmus.auction.service.definition.OtpHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class OtpHistoryServiceImpl implements OtpHistoryService {
    private final OtpHistoryRepository otpHistoryRepository;
    private final OtpHistoryMapper otpHistoryMapper;

    @Override
    public OtpHistory getLastOtpRecordByUserIdAndType(String userId, Boolean isUsedForSignUp) {
        final String SORT_BY = "startTimestamp";
        Pageable pageable = PageRequest.of(0, 1, Sort.by(SORT_BY).descending());
        Page<OtpHistory> otpHistoryPage = otpHistoryRepository.findAllByUserIdAndIsUsedForSignUp(userId, isUsedForSignUp, pageable);
        return otpHistoryPage.isEmpty() ? null : otpHistoryPage.getContent().get(0);
    }

    @Override
    public void addNewOtp(String value, String userId, Boolean isUsedForSignUp) {
        OtpHistoryDTO otpHistoryDTO = new OtpHistoryDTO();
        Integer currentTimestamp = TimeUtil.getCurrentTimestamp();
        final Integer DIFF_5_MINUTES_IN_SECOND = 5 * 60;

        otpHistoryDTO.setId(UUID.randomUUID().toString());
        otpHistoryDTO.setUserId(userId);
        otpHistoryDTO.setValue(value);
        otpHistoryDTO.setStartTimestamp(currentTimestamp);
        otpHistoryDTO.setEndTimestamp(currentTimestamp + DIFF_5_MINUTES_IN_SECOND);
        otpHistoryDTO.setIsUsed(false);
        otpHistoryDTO.setIsUsedForSignUp(isUsedForSignUp);

        otpHistoryRepository.save(otpHistoryMapper.toEntity(otpHistoryDTO));
    }

    @Override
    public String verifyOtpCode(String userId, String otpCode, Boolean isUsedForSignUp) {
        Integer currentTimestamp = TimeUtil.getCurrentTimestamp();
        OtpHistory otpHistory = this.getLastOtpRecordByUserIdAndType(userId, isUsedForSignUp);

        if (otpHistory != null) {
            if (otpHistory.getIsUsed())
                throw new GenericException(ErrorMessage.DO_NOT_HAVE_OTP_RECORD.getMessage());
            if (!otpHistory.getValue().equals(otpCode))
                throw new GenericException(ErrorMessage.INVALID_OTP.getMessage());
            if (currentTimestamp > otpHistory.getEndTimestamp())
                throw new GenericException(ErrorMessage.OTP_EXPIRED.getMessage());
            otpHistory.setIsUsed(true);
            otpHistoryRepository.save(otpHistory);
            return BcryptUtil.hashText(otpHistory.getEndTimestamp() + otpCode);
        } else
            throw new GenericException(ErrorMessage.DO_NOT_HAVE_OTP_RECORD.getMessage());
    }

    @Override
    public boolean isValidOtpToken(String token, String userId) {
        final Boolean IS_USED_FOR_SIGN_UP = false;
        OtpHistory otpHistory = this.getLastOtpRecordByUserIdAndType(userId, IS_USED_FOR_SIGN_UP);
        return BcryptUtil.isSameText(otpHistory.getEndTimestamp() + otpHistory.getValue(), token);
    }
}
