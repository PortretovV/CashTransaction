package com.portretov.ejb;

import com.portretov.entity.BTransaction;
import com.portretov.entity.BankAccount;
import com.portretov.service.BankAccountService;
import com.portretov.service.BTransactionService;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 * Created by VP on 03.05.2017.
 */

@Stateful
@SessionScoped
public class TransactionEJBImpl implements TransactionBankAccountEJB, Serializable {

    @EJB
    private BankAccountService bankService;
    @EJB
    private BTransactionService bTransactionService;

//    private List<BankAccount> bankAccountList;
//    private BankAccount bankAccount;
//    private BTransaction transaction;
//    private String recieveNumber;
//
//
//    public List<BankAccount> getBankAccountList() {
//        bankAccountList = bankService.findAll();
//        return bankAccountList;
//    }
//
//    public BankAccount getBankAccount() {
//        return bankAccount;
//    }
//
//    public void setBankAccount(BankAccount bankAccount) {
//        this.bankAccount = bankAccount;
//    }
//
//    public BTransaction getTransaction() {
//        return transaction;
//    }
//
//    public void setTransaction(BTransaction transaction) {
//        this.transaction = transaction;
//    }
//
//    public String getRecieveNumber() {
//        return recieveNumber;
//    }
//
//    public void setRecieveNumber(String recieveNumber) {
//        this.recieveNumber = recieveNumber;
//    }

    public void deleteRollback(BTransaction bTransaction){
        bTransactionService.deleteRollback(bTransaction);
    }


    public BTransaction saveTransaction(String recieveNumber, BTransaction bTransaction){
        if(recieveNumber != null){
            BankAccount bankAccount = bankService.findByAccountNumber(recieveNumber);
            bTransaction.setReciverAcc(bankAccount);
            bTransactionService.cashTransfer(bTransaction);
        }
        return bTransaction;
    }

    public BTransaction saveWithExceptionTransaction(String recieveNumber, BTransaction bTransaction){
        if(recieveNumber != null){
            BankAccount bankAccount = bankService.findByAccountNumber(recieveNumber);
            bTransaction.setReciverAcc(bankAccount);
            bTransactionService.cashTransferWithException(bTransaction);
        }
        return bTransaction;
    }

}
