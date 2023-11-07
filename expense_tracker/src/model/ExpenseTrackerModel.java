package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExpenseTrackerModel {

  // encapsulation - data integrity
  private List<Transaction> transactions;

  public ExpenseTrackerModel() {
    transactions = new ArrayList<>();
  }

  /**
   * Removes the last transaction from the list of transactions.
   * 
   * @return boolean true if the last transaction was removed, false otherwise.
   */
  public boolean removeLastTransaction() {
    List<Transaction> transactions = new ArrayList<Transaction>(getTransactions());

    if (transactions.isEmpty()) {
      return false;
    } else {
      transactions.remove(transactions.size() - 1);
      setTransactions(transactions);
      return true;
    }

  }

  public void addTransaction(Transaction t) {
    // Perform input validation to guarantee that all transactions added are
    // non-null.
    if (t == null) {
      throw new IllegalArgumentException("The new transaction must be non-null.");
    }
    transactions.add(t);
  }

  public void removeTransaction(Transaction t) {
    transactions.remove(t);
  }

  public List<Transaction> getTransactions() {
    // encapsulation - data integrity
    return Collections.unmodifiableList(new ArrayList<>(transactions));
  }

  public void setTransactions(List<Transaction> newTransactionList) {
    // Perform input validation to guarantee that all transactions added are
    // non-null.
    if (newTransactionList == null) {
      throw new IllegalArgumentException("The new transaction list must be non-null.");
    }
    transactions = newTransactionList;
  }

}
