package com.smd.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smd.api.entity.AccountEntity;
import com.smd.api.repository.AccountRepository;

import java.util.List;

@Service
@Transactional
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    public List<AccountEntity> getAccount() {
        return accountRepository.findAll();
    }
}
