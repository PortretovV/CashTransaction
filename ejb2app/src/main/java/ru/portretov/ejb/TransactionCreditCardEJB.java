package ru.portretov.ejb;

import ru.portretov.entity.Transaction;
import javax.ejb.Local;

/**
 * Created by VP on 04.05.2017.
 */
@Local
public interface TransactionCreditCardEJB {
    void deleteRollback(Transaction transaction);
    Transaction saveTransaction(String recieveNumber, Transaction transaction);
    Transaction saveTransactionWithRollback(String recieveNumber, Transaction transaction);
}
