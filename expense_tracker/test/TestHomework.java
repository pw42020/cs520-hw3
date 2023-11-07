
// package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import model.Filter.TransactionFilter;
import model.Filter.AmountFilter;
import model.Filter.CategoryFilter;

import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import model.Transaction;
import view.ExpenseTrackerView;

public class TestHomework {

    private ExpenseTrackerModel model;
    private ExpenseTrackerView view;
    private ExpenseTrackerController controller;

    @Before
    public void setup() {
        model = new ExpenseTrackerModel();
        view = new ExpenseTrackerView();
        controller = new ExpenseTrackerController(model, view);
    }

    public double getTotalCost() {
        double totalCost = 0.0;
        List<Transaction> allTransactions = model.getTransactions(); // Using the model's getTransactions method
        for (Transaction transaction : allTransactions) {
            totalCost += transaction.getAmount();
        }
        return totalCost;
    }

    /**
     * This test checks that the addTransaction method works correctly.
     * Uses an amount of 50 and a category of food to check.
     */
    @Test
    public void testAddTransactionTwo() {
        boolean returnValue = controller.addTransaction(50.00, "food");

        if (returnValue) {
            // Post-condition: List of transactions contains one transaction
            assertEquals(1, model.getTransactions().size());

            // Check the contents of the list
            assertEquals(50.00, getTotalCost(), 0.01);
        } else {
            // failed
            assertTrue(false);
        }
    }

    /**
     * Invalid input handling test
     */
    @Test
    public void testInvalidInputHandling() {
        boolean returnValue = controller.addTransaction(-50.00, "food");

        if (!returnValue) {
            // Post-condition: List of transactions contains one transaction
            assertEquals(0, model.getTransactions().size());

            assertEquals(0, getTotalCost(), 0.01);
        } else {
            // failed
            assertTrue(false);
        }
    }

    /**
     * Checks valid amount filtering
     */
    @Test
    public void testAmountFilter() {
        double AMOUNT_CHOSEN = 50.0;
        boolean returnValue = controller.addTransaction(50, "food");
        returnValue &= controller.addTransaction(25, "entertainment");
        returnValue &= controller.addTransaction(10, "other");
        returnValue &= controller.addTransaction(100, "other");
        returnValue &= controller.addTransaction(75, "food");

        assertTrue(returnValue); // ensuring all transactions were added correctly

        AmountFilter filter = new AmountFilter(AMOUNT_CHOSEN);
        List<Transaction> filteredTransactions = filter.filter(model.getTransactions());
        assertEquals(filteredTransactions.size(), 1);
        for (Transaction transaction : filteredTransactions) {
            assertTrue(transaction.getAmount() == AMOUNT_CHOSEN);
        }
    }

    /**
     * Checks valid category filtering
     */
    @Test
    public void testCategoryFilter() {
        String CATEGORY_CHOSEN = "food";
        boolean returnValue = controller.addTransaction(50, "food");
        returnValue &= controller.addTransaction(25, "entertainment");
        returnValue &= controller.addTransaction(10, "other");
        returnValue &= controller.addTransaction(100, "other");
        returnValue &= controller.addTransaction(75, "food");

        assertTrue(returnValue); // ensuring all transactions were added correctly

        CategoryFilter filter = new CategoryFilter(CATEGORY_CHOSEN);
        List<Transaction> filteredTransactions = filter.filter(model.getTransactions());
        assertEquals(filteredTransactions.size(), 2);
        for (Transaction transaction : filteredTransactions) {
            assertTrue(transaction.getCategory().equals(CATEGORY_CHOSEN));
        }
    }

    /**
     * Checks that program will not allow undo when
     * transactions list is empty
     */
    @Test
    public void testUndoEmptyTransactionsList() {
        boolean returnValue = controller.undoLastTransaction();
        assertTrue(!returnValue);
    }

    /**
     * Checks that program will allow undo when
     * transactions list is not empty
     */
    @Test
    public void testUndoNonEmptyTransactionsList() {
        boolean returnValue = controller.addTransaction(50, "food");
        assertTrue(returnValue);
        returnValue = controller.undoLastTransaction();
        assertTrue(returnValue);
    }
}
