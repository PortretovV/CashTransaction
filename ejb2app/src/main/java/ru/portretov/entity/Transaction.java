package ru.portretov.entity;

import javax.persistence.*;

/**
 * Created by VP on 02.05.2017.
 */
@Entity
@Table(name = "transaction", schema = "ejb3-transaction1")
public class Transaction {
    private Integer idTransaction;
    private Double sumTransaction;
    private CreditCard senderCard;
    private CreditCard reciverCard;

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
    @JoinColumn(name = "senderCard", referencedColumnName = "cardNumber")
    public CreditCard getSenderCard() {
        return senderCard;
    }

    public void setSenderCard(CreditCard creditCardBySenderCard) {
        this.senderCard = creditCardBySenderCard;
    }

    @ManyToOne
    @JoinColumn(name = "reciverCard", referencedColumnName = "cardNumber")
    public CreditCard getReciverCard() {
        return reciverCard;
    }

    public void setReciverCard(CreditCard creditCardByReciverCard) {
        this.reciverCard = creditCardByReciverCard;
    }
}
