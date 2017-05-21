package ru.portretov.service;

import ru.portretov.entity.Transaction;
import javax.ejb.Local;

/**
 * Created by VP on 02.05.2017.
 */

@Local
public interface TransactionService {
    void deleteWithEJBException(Transaction transaction);
    Transaction cashTransfer(Transaction transaction);
    Transaction cashTransferWithRollback(Transaction transaction);
}
