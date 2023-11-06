package command;

import model.Account;
import model.Transaction;

import java.util.ArrayList;

public class RemoveTransactionsCommand implements Command {

    private ArrayList<Transaction> transactionsToRemove;
    private Account account;

    public void execute() {
        for (Transaction transaction : transactionsToRemove) {
            account.removeTransaction(transaction);
        }
    }

    public String getName() {
        return transactionsToRemove.size() + " transactions removed";
    }

    public void undo() {
        for (Transaction transaction : transactionsToRemove) {
            account.addTransaction(transaction);
        }
    }

    public void redo() {
        for (Transaction transaction : transactionsToRemove) {
            account.removeTransaction(transaction);
        }
    }

    public RemoveTransactionsCommand(ArrayList<Transaction> transactionsToRemove, Account account) {
        this.transactionsToRemove = transactionsToRemove;
        this.account = account;
    }
}
