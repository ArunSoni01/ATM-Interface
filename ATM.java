import java.util.Scanner;

public class ATM {
    private Bank bank;
    private AccountHolder currentAccountHolder;
    private Scanner scanner;

    public ATM(Bank bank) {
        this.bank = bank;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        if (authenticateUser()) {
            showMenu();
        } else {
            System.out.println("Authentication failed.");
        }
    }

    private boolean authenticateUser() {
        System.out.println("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.println("Enter PIN: ");
        String pin = scanner.nextLine();

        AccountHolder accountHolder = bank.getAccountHolder(userId);
        if (accountHolder != null && accountHolder.getPin().equals(pin)) {
            currentAccountHolder = accountHolder;
            return true;
        }
        return false;
    }

    private void showMenu() {
        while (true) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Show Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (choice) {
                case 1:
                    showTransactionHistory();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showTransactionHistory() {
        Account account = bank.getAccount(currentAccountHolder.getUserId());
        System.out.println("\nTransaction History:");
        for (String transaction : account.getTransactionHistory()) {
            System.out.println(transaction);
        }
    }

    private void withdraw() {
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        Account account = bank.getAccount(currentAccountHolder.getUserId());
        if (account.withdraw(amount)) {
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    private void deposit() {
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        Account account = bank.getAccount(currentAccountHolder.getUserId());
        account.deposit(amount);
        System.out.println("Deposit successful.");
    }

    private void transfer() {
        System.out.print("Enter recipient user ID: ");
        String recipientUserId = scanner.nextLine();
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();
        Account fromAccount = bank.getAccount(currentAccountHolder.getUserId());
        Account toAccount = bank.getAccount(recipientUserId);

        if (toAccount != null && BankTransaction.transfer(fromAccount, toAccount, amount)) {
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Transfer failed. Check recipient user ID or balance.");
        }
    }

    public static void main(String[] args) {
        Bank bank = new Bank();
        bank.addAccountHolder("user1", "1234");
        bank.addAccountHolder("user2", "5678");

        ATM atm = new ATM(bank);
        atm.start();
    }
}
