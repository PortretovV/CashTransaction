package com.portretov.service;


import com.portretov.entity.BTransaction;

import javax.ejb.Local;

/**
 * Created by VP on 02.05.2017.
 */

@Local
public interface BTransactionService {
    void deleteRollback(BTransaction btransaction);
    BTransaction cashTransfer(BTransaction btransaction);
    BTransaction cashTransferWithException(BTransaction btransaction);
}
