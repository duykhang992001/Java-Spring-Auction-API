package com.hcmus.auction.service.impl;

import com.hcmus.auction.model.dto.AccountDTO;
import com.hcmus.auction.model.entity.Account;
import com.hcmus.auction.model.mapper.AccountMapper;
import com.hcmus.auction.repository.AccountRepository;
import com.hcmus.auction.service.definition.GenericService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements GenericService<AccountDTO, String> {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public AccountDTO getById(String accountId) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        return accountOptional.map(accountMapper::toDTO).orElse(null);
    }
}
