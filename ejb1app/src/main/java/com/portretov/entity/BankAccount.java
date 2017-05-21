package com.portretov.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by VP on 02.05.2017.
 */
@Entity
@Table(name = "bank_account", schema = "ejb3-transaction2")
@NamedQueries({
        @NamedQuery(name = "BankAccount.findAll", query = "select b from BankAccount b"),
        @NamedQuery(name = "BankAccount.findByNumber", query = "select b from BankAccount b where b.accountNumber = :accountNumber")
})
public class BankAccount {
    private String accountNumber;
    private String inn;
    private String bik;
    private String bankName;
    private String userInfo;
    private String userAddress;
    private Double sum;
    private Collection<BTransaction> transactions1;
    private Collection<BTransaction> transactions2;

    @Id
    @Column(name = "accountNumber")
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Basic
    @Column(name = "INN")
    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    @Basic
    @Column(name = "BIK")
    public String getBik() {
        return bik;
    }

    public void setBik(String bik) {
        this.bik = bik;
    }

    @Basic
    @Column(name = "bankName")
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Basic
    @Column(name = "userInfo")
    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    @Basic
    @Column(name = "userAddress")
    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    @Basic
    @Column(name = "sum")
    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    @OneToMany(mappedBy = "senderAcc",fetch = FetchType.EAGER)
    public Collection<BTransaction> getTransactions1() {
        return transactions1;
    }

    public void setTransactions1(Collection<BTransaction> transactionsByAccountNumber) {
        this.transactions1 = transactionsByAccountNumber;
    }

    @OneToMany(mappedBy = "reciverAcc",fetch = FetchType.EAGER)
    public Collection<BTransaction> getTransactions2() {
        return transactions2;
    }

    public void setTransactions2(Collection<BTransaction> transactionsByAccountNumber_0) {
        this.transactions2 = transactionsByAccountNumber_0;
    }
}
