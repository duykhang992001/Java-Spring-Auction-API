package com.hcmus.auction.model.mapper;

import com.hcmus.auction.model.dto.AccountDTO;
import com.hcmus.auction.model.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper implements GenericMapper<Account, AccountDTO> {
    @Override
    public Account toEntity(AccountDTO accountDTO) {
        return null;
    }

    @Override
    public AccountDTO toDTO(Account account) {
        UserMapper userMapper = new UserMapper();
        AccountDTO accountDTO = new AccountDTO();

        accountDTO.setEmail(account.getEmail());
        accountDTO.setIsActivated(account.getIsActivated());
        accountDTO.setUser(userMapper.toDTO(account.getUser()));

        return accountDTO;
    }
}
