import ru.portretov.ejb.TransactionCreditCardEJB;
import ru.portretov.entity.CreditCard;
import ru.portretov.entity.Transaction;
import ru.portretov.service.CreditCardService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

/**
 * Created by VP on 02.05.2017.
 */

@ManagedBean(name = "cardTransaction")
@SessionScoped
public class CardTransactionBean {

    @EJB
    CreditCardService cardService;
    @EJB
    TransactionCreditCardEJB transactionEJB;

    private List<CreditCard> creditCardList;
    private CreditCard creditCard;
    private Transaction transaction;
    private String recieveNumber;

    public List<CreditCard> getCreditCardList() {
        creditCardList = cardService.findAll();
        return creditCardList;
    }


    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public String getRecieveNumber() {
        return recieveNumber;
    }

    public void setRecieveNumber(String recieveNumber) {
        this.recieveNumber = recieveNumber;
    }

    public String deleteRollback(Transaction transaction){
        transactionEJB.deleteRollback(transaction);
        return "card_transaction";
    }

    public String createTransaction(CreditCard card){
        this.recieveNumber = "";
        this.creditCard = card;
        this.transaction = new Transaction();
        transaction.setSenderCard(card);
        return "createCardTransaction";
    }

    public String saveTransaction(){
        transactionEJB.saveTransaction(recieveNumber, transaction);
        return "card_transaction";
    }

    public String saveTransactionWithRollback(){
        transactionEJB.saveTransactionWithRollback(recieveNumber, transaction);
        return "card_transaction";
    }

    public String goToCardTransaction(){
        return "card_transaction";
    }
}
