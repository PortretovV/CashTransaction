package com.portretov.ejb;

import com.portretov.entity.BTransaction;

import javax.ejb.Local;

/**
 * Created by VP on 03.05.2017.
 */

@Local
public interface TransactionBankAccountEJB {
    void deleteRollback(BTransaction bTransaction);
    BTransaction saveTransaction(String recieveNumber, BTransaction bTransaction);
    BTransaction saveWithExceptionTransaction(String recieveNumber, BTransaction bTransaction);
}
