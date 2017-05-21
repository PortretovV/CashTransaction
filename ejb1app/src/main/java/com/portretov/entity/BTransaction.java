package com.portretov.entity;

import javax.persistence.*;

/**
 * Created by VP on 02.05.2017.
 */
@Entity
@Table(name = "transaction", schema = "ejb3-transaction2")
public class BTransaction {
    private Integer idTransaction;
    private Double sumTransaction;
    private BankAccount senderAcc;
    private BankAccount reciverAcc;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTransaction")
    public Integer getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(Integer idTransaction) {
        this.idTransaction = idTransaction;
    }

    @Basic
    @Column(name = "sumTransaction")
    public Double getSumTransaction() {
        return sumTransaction;
    }

    public void setSumTransaction(Double sumTransaction) {
        this.sumTransaction = sumTransaction;
    }

    @ManyToOne
    @JoinColumn(name = "senderAcc", referencedColumnName = "accountNumber", nullable = false)
    public BankAccount getSenderAcc() {
        return senderAcc;
    }

    public void setSenderAcc(BankAccount bankAccountBySenderAcc) {
        this.senderAcc = bankAccountBySenderAcc;
    }

    @ManyToOne
    @JoinColumn(name = "reciverAcc", referencedColumnName = "accountNumber", nullable = false)
    public BankAccount getReciverAcc() {
        return reciverAcc;
    }

    public void setReciverAcc(BankAccount bankAccountByReciverAcc) {
        this.reciverAcc = bankAccountByReciverAcc;
    }
}
