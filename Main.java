import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

class BankAccount {
    private String accountId;
    private double balance;

    public BankAccount(String accountId, double initialBalance) {
        this.accountId = accountId;
        this.balance = initialBalance;
    }

    public String getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (isValidWithdrawal(amount)) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidWithdrawal(double amount) {
        return amount > 0 && amount <= balance;
    }
}

class Transaction {
    private String type;
    private double amount;
    private String accountId;

    public Transaction(String accountId, String type, double amount) {
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Account ID: " + accountId + " | " + type + ": $" + amount;
    }
}

class TransactionHistory {
    private Map<String, List<Transaction>> transactions;

    public TransactionHistory() {
        this.transactions = new HashMap<>();
    }

    public void addTransaction(String accountId, String type, double amount) {
        transactions.computeIfAbsent(accountId, k -> new ArrayList<>())
                .add(new Transaction(accountId, type, amount));
    }

    public void displayHistory(String accountId) {
        System.out.println("\nTransaction History for Account ID " + accountId + ":");
        transactions.getOrDefault(accountId, Collections.emptyList())
                .forEach(System.out::println);
    }
}

class EnhancedATM {
    private Map<String, BankAccount> userAccounts;
    private Map<String, String> accountPasswords;
    private Scanner scanner;
    private TransactionHistory transactionHistory;

    public EnhancedATM() {
        this.userAccounts = new HashMap<>();
        this.accountPasswords = new HashMap<>();
        this.scanner = new Scanner(System.in);
        this.transactionHistory = new TransactionHistory();
    }

    public void addUserAccount(String accountId, String password, double initialBalance) {
        userAccounts.put(accountId, new BankAccount(accountId, initialBalance));
        accountPasswords.put(accountId, password);
    }

    public boolean authenticateUser(String accountId, String password) {
        return accountPasswords.containsKey(accountId) && accountPasswords.get(accountId).equals(password);
    }

    public void displayMenu() {
        System.out.println("ATM Menu:");
        System.out.println("1. Withdraw");
        System.out.println("2. Deposit");
        System.out.println("3. Check Balance");
        System.out.println("4. Transaction History");
        System.out.println("5. Exit");
    }

    public void processOption(String accountId, int option) {
        switch (option) {
            case 1:
                handleWithdrawal(accountId);
                break;
            case 2:
                handleDeposit(accountId);
                break;
            case 3:
                displayBalance(accountId);
                break;
            case 4:
                displayTransactionHistory(accountId);
                break;
            case 5:
                exitATM();
                break;
            default:
                System.out.println("Invalid option. Please choose a valid option.");
        }
    }

    private void handleWithdrawal(String accountId) {
        System.out.print("Enter withdrawal amount: ");
        double withdrawAmount = scanner.nextDouble();
        if (userAccounts.containsKey(accountId) && userAccounts.get(accountId).withdraw(withdrawAmount)) {
            System.out
                    .println("Withdrawal successful. Remaining balance: $" + userAccounts.get(accountId).getBalance());
            transactionHistory.addTransaction(accountId, "Withdrawal", withdrawAmount);
        } else {
            System.out.println("Insufficient funds or invalid amount for withdrawal.");
        }
    }

    private void handleDeposit(String accountId) {
        System.out.print("Enter deposit amount: ");
        double depositAmount = scanner.nextDouble();
        if (userAccounts.containsKey(accountId)) {
            userAccounts.get(accountId).deposit(depositAmount);
            System.out.println("Deposit successful. Updated balance: $" + userAccounts.get(accountId).getBalance());
            transactionHistory.addTransaction(accountId, "Deposit", depositAmount);
        } else {
            System.out.println("Invalid account ID.");
        }
    }

    private void displayBalance(String accountId) {
        if (userAccounts.containsKey(accountId)) {
            System.out.println(
                    "Current Balance for Account ID " + accountId + ": $" + userAccounts.get(accountId).getBalance());
        } else {
            System.out.println("Invalid account ID.");
        }
    }

    private void displayTransactionHistory(String accountId) {
        transactionHistory.displayHistory(accountId);
    }

    private void exitATM() {
        System.out.println("Exiting ATM. Thank you!");
        System.exit(0);
    }
}

public class Main {
    public static void main(String[] args) {
        EnhancedATM enhancedATM = new EnhancedATM();

        enhancedATM.addUserAccount("123456", "password123", 1000);
        enhancedATM.addUserAccount("789012", "securepass", 1500);
        while (true) {
            System.out.print("Enter Account ID: ");
            String accountId = new Scanner(System.in).nextLine();
            System.out.print("Enter Password: ");
            String password = new Scanner(System.in).nextLine();

            if (enhancedATM.authenticateUser(accountId, password)) {
                System.out.println("Authentication successful. Welcome!");
                while (true) {
                    enhancedATM.displayMenu();
                    System.out.print("Choose an option (1-5): ");
                    int userOption = new Scanner(System.in).nextInt();
                    enhancedATM.processOption(accountId, userOption);
                }
            } else {
                System.out.println("Authentication failed. Invalid Account ID or Password.");
            }
        }
    }
}
