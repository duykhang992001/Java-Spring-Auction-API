package com.hcmus.auction.service.impl;

import com.hcmus.auction.common.variable.ErrorMessage;
import com.hcmus.auction.exception.GenericException;
import com.hcmus.auction.model.dto.AccountDTO;
import com.hcmus.auction.model.entity.Account;
import com.hcmus.auction.model.mapper.AccountMapper;
import com.hcmus.auction.repository.AccountRepository;
import com.hcmus.auction.service.definition.AccountService;
import com.hcmus.auction.service.definition.GenericService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements GenericService<AccountDTO, String>, AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public AccountDTO getById(String accountId) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        return accountOptional.map(accountMapper::toDTO).orElse(null);
    }

    @Override
    public void updateEmail(String userId, String email) {
        Optional<Account> accountOptional = accountRepository.findById(userId);
        Account account = accountOptional.orElse(null);

        if (account == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_ACCOUNT.getMessage());

        if (!email.equals(account.getEmail())) {
            Optional<Account> existedAccountOptional = accountRepository.findByEmail(email);
            Account existedAccount = existedAccountOptional.orElse(null);

            if (existedAccount != null)
                throw new GenericException(ErrorMessage.EXISTED_EMAIL.getMessage());

            account.setEmail(email);
            accountRepository.save(account);
        }
    }
}
