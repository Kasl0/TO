package command;

import model.Account;
import model.Transaction;

public class AddTransactionCommand implements Command {

    private Transaction transactionToAdd;
    private Account account;

    public void execute() {
        account.addTransaction(transactionToAdd);
    }

    public String getName() {
        return "New transaction: " + transactionToAdd.toString();
    }

    public void undo() {
        account.removeTransaction(transactionToAdd);
    }

    public void redo() {
        account.addTransaction(transactionToAdd);
    }

    public AddTransactionCommand(Transaction transactionToAdd, Account account) {
        this.transactionToAdd = transactionToAdd;
        this.account = account;
    }
}
