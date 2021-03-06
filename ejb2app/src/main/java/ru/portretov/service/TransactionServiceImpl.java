package ru.portretov.service;

import ru.portretov.entity.CreditCard;
import ru.portretov.entity.Transaction;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.*;

/**
 * Created by VP on 02.05.2017.
 */

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TransactionServiceImpl implements TransactionService {

    @PersistenceContext(unitName = "TRANSACTION1", type = PersistenceContextType.TRANSACTION)
    private EntityManager em;
    @Resource
    SessionContext ctx;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    private Transaction transfer(Transaction transaction){
        CreditCard sender = transaction.getSenderCard();
        CreditCard receiver = transaction.getReciverCard();

        double newSenderSum = sender.getDeposit() - transaction.getSumTransaction();
        double newReceiverSum = receiver.getDeposit() + transaction.getSumTransaction();
        sender.setDeposit(newSenderSum);
        receiver.setDeposit(newReceiverSum);
        em.merge(sender);
        em.merge(receiver);

        transaction = em.merge(transaction);
        return transaction;
    }

    @Override
    public Transaction cashTransfer(Transaction transaction){
            transaction = save(transaction);
            return transfer(transaction);
    }

    @Override
    public Transaction cashTransferWithRollback(Transaction transaction) {
        transaction = saveWithRollback(transaction);
        return transfer(transaction);
    }

//    @Override
//    public Transaction cashTransferWithException(Transaction transaction) throws EJBException{
//        CreditCard sender = transaction.getSenderCard();
//        CreditCard receiver = transaction.getReciverCard();
//
//        double newSenderSum = sender.getDeposit() - transaction.getSumTransaction();
//        double newReceiverSum = receiver.getDeposit() + transaction.getSumTransaction();
//
//        sender.setDeposit(newSenderSum);
//        receiver.setDeposit(newReceiverSum);
//
//        em.merge(sender);
//        em.merge(receiver);
//
//        transaction = em.merge(transaction);
//        if(transaction.getSumTransaction()> sender.getDeposit()){
//            throw new EJBException("Должен вызваться откат");
//        }
//        return transaction;
//    }

    private Transaction save(Transaction transaction) {
        if(transaction.getIdTransaction() == null){
            em.persist(transaction);
            return transaction;
        }
        else if (transaction.getIdTransaction()>0){
            em.merge(transaction);
            return transaction;
        }
        return null;
    }


    /*
7.Провести эксперимент: закончить транзакцию откатом в сессионном фасаде для
первой базы данных и убедиться, что обновления отменены.
     */
    private Transaction saveWithRollback(Transaction transaction) {//Происходит откат данных
        if(transaction.getIdTransaction() == null){
            em.persist(transaction);
            ctx.setRollbackOnly();
            return transaction;
        }
        else if (transaction.getIdTransaction()>0){
            em.merge(transaction);
            ctx.setRollbackOnly();
            return transaction;
        }
        return null;
    }



    /*
10.Провести эксперимент: закончить транзакцию откатом, выбросив системное
исключение EJBException в сессионном фасаде для первой базы данных должно
выполняться в контексте новой транзакции и после
обновления источника данных, и убедиться, что обновления отменены.
     */

    @Override
    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)//Выкидывает исключение и откатывает
    public void deleteWithEJBException(Transaction transaction) {
        em.merge(transaction);
        em.remove(em.contains(transaction) ? transaction : em.merge(transaction));
        if(transaction != null){
            throw new EJBException("Working in new context transaction");
        }
    }


}
