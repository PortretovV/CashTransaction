package com.portretov.service;

import com.portretov.entity.BankAccount;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by VP on 02.05.2017.
 */

@Local
public interface BankAccountService {
    BankAccount save(BankAccount account);
    void delete(BankAccount account);
    List<BankAccount> findAll();
    BankAccount findByAccountNumber(String number);
}
