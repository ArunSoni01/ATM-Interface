import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String, AccountHolder> accountHolders;
    private Map<String, Account> accounts;

    public Bank() {
        this.accountHolders = new HashMap<>();
        this.accounts = new HashMap<>();
    }

    public void addAccountHolder(String userId, String pin) {
        accountHolders.put(userId, new AccountHolder(userId, pin));
        accounts.put(userId, new Account());
    }

    public AccountHolder getAccountHolder(String userId) {
        return accountHolders.get(userId);
    }

    public Account getAccount(String userId) {
        return accounts.get(userId);
    }
}
