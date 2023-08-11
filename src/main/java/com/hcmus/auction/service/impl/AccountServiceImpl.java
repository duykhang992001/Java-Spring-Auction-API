package com.hcmus.auction.service.impl;

import com.hcmus.auction.common.util.BcryptUtil;
import com.hcmus.auction.common.variable.ErrorMessage;
import com.hcmus.auction.exception.GenericException;
import com.hcmus.auction.model.dto.AccountDTO;
import com.hcmus.auction.model.entity.Account;
import com.hcmus.auction.model.entity.User;
import com.hcmus.auction.model.mapper.AccountMapper;
import com.hcmus.auction.repository.AccountRepository;
import com.hcmus.auction.service.definition.AccountService;
import com.hcmus.auction.service.definition.GenericService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

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

    @Override
    public boolean isExistedAccount(String email) {
        return accountRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public void addNewAccount(User user, String email, String password) {
        Account account = new Account();

        account.setUser(user);
        account.setIsActivated(false);
        account.setLastLoginTimestamp(null);
        account.setEmail(email);
        account.setRefreshToken(UUID.randomUUID().toString());
        account.setPassword(BcryptUtil.hashText(password));

        accountRepository.save(account);
    }

    @Override
    public void activateAccount(String userId) {
        Optional<Account> accountOptional = accountRepository.findById(userId);
        Account account = accountOptional.orElse(null);

        if (account == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_ACCOUNT.getMessage());

        account.setIsActivated(true);
        accountRepository.save(account);
    }
}
