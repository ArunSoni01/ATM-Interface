public class BankTransaction {
    public static boolean transfer(Account fromAccount, Account toAccount, double amount) {
        if (fromAccount.withdraw(amount)) {
            toAccount.deposit(amount);
            fromAccount.addTransaction("Transferred: $" + amount + " to account");
            toAccount.addTransaction("Received: $" + amount + " from account");
            return true;
        }
        return false;
    }
}
