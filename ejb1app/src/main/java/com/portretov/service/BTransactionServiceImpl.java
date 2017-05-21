package com.portretov.service;

import com.portretov.entity.BTransaction;
import com.portretov.entity.BankAccount;
import com.portretov.exception.TransactionException;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by VP on 02.05.2017.
 */

@Stateless

public class BTransactionServiceImpl implements BTransactionService {

    @PersistenceContext(unitName = "XA-TRANSACTION2")
    private EntityManager em;
    @Resource
    private SessionContext ctx;


    private BTransaction save(BTransaction bTransaction) {
        if(bTransaction.getIdTransaction() == null){
            em.persist(bTransaction);
            return bTransaction;
        }
        else if (bTransaction.getIdTransaction()>0){
            em.merge(bTransaction);
            return bTransaction;
        }
        return null;
    }

      /*
8. Провести эксперимент: закончить транзакцию откатом, выбросив системное
исключение EJBException в сессионном фасаде для второй базы данных после
обновления источника данных, и убедиться, что обновления отменены.
     */
    @Override
    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)//Выкидывает исключение и откатывает
    public void deleteRollback(BTransaction bTransaction) {
        em.merge(bTransaction);
        em.remove(em.contains(bTransaction) ? bTransaction : em.merge(bTransaction));
        if(!ctx.getRollbackOnly()){
            throw new TransactionException("Call my com.portretov.exception.TransactionException, changes don't save");
        }

    }

    @Override
    public BTransaction cashTransfer(BTransaction bTransaction) {
        bTransaction = save(bTransaction);
        if(bTransaction != null){
            BankAccount sender = bTransaction.getSenderAcc();
            BankAccount receiver = bTransaction.getReciverAcc();

            double newSenderSum = sender.getSum() - bTransaction.getSumTransaction();
            double newReceiverSum = receiver.getSum() + bTransaction.getSumTransaction();
            sender.setSum(newSenderSum);
            receiver.setSum(newReceiverSum);
            em.merge(sender);
            em.merge(receiver);

            bTransaction = em.merge(bTransaction);
        }
        return bTransaction;
    }


    /*
9.Провести эксперимент: закончить транзакцию откатом в сессионном фасаде для
второй базы данных, должно выполняться вне контекста транзакции, продемонстрировать
результат эксперимента, и убедиться, что обновления отменены.
     */
    @Override
    @TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED) //Кидает исключение на save, с анатацией REQ выполняет все действия, но потом откатывает
    public BTransaction cashTransferWithException(BTransaction bTransaction) {
        bTransaction = save(bTransaction);
        if(bTransaction != null){
            BankAccount sender = bTransaction.getSenderAcc();
            BankAccount receiver = bTransaction.getReciverAcc();

            double newSenderSum = sender.getSum() - bTransaction.getSumTransaction();
            double newReceiverSum = receiver.getSum() + bTransaction.getSumTransaction();
            sender.setSum(newSenderSum);
            receiver.setSum(newReceiverSum);
            em.merge(sender);
            em.merge(receiver);
            if(bTransaction != null){
                ctx.setRollbackOnly();
            }

            bTransaction = em.merge(bTransaction);
        }
        return bTransaction;
    }
}
