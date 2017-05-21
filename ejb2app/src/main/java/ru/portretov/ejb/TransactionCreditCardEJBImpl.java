package ru.portretov.ejb;

import ru.portretov.entity.CreditCard;
import ru.portretov.entity.Transaction;
import ru.portretov.service.CreditCardService;
import ru.portretov.service.TransactionService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import java.io.Serializable;

/**
 * Created by VP on 04.05.2017.
 */

@Stateful
@ConversationScoped
public class TransactionCreditCardEJBImpl implements TransactionCreditCardEJB, Serializable{
    @EJB
    CreditCardService cardService;
    @EJB
    TransactionService transactionService;
    @Inject
    private Conversation conversation;

    @PostConstruct
    private void init(){
        if(conversation.isTransient())
            conversation.begin();
    }

    @PreDestroy
    private void destroy(){
        if(!conversation.isTransient())
        conversation.end();
    }

    public void deleteRollback(Transaction transaction){
        transactionService.deleteWithEJBException(transaction);
    }

    public Transaction saveTransaction(String recieveNumber, Transaction transaction){
        if(recieveNumber != null){
            CreditCard creditCard = cardService.findByCardNumber(recieveNumber);
            if(creditCard != null){
                transaction.setReciverCard(creditCard);
                transactionService.cashTransfer(transaction);
            }
        }
        return transaction;
    }

    public Transaction saveTransactionWithRollback(String recieveNumber, Transaction transaction){
        if(recieveNumber != null){
            CreditCard creditCard = cardService.findByCardNumber(recieveNumber);
            if(creditCard != null){
                transaction.setReciverCard(creditCard);
                transactionService.cashTransferWithRollback(transaction);
            }
        }
        return transaction;
    }
}
