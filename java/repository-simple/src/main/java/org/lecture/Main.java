package org.lecture;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        try {
            AccountRepository repo = new AccountRepository();

            String newId = UUID.randomUUID().toString();
            Account newAccount = new Account(
                    newId, "test2", "test2", "test2@test.com", new Timestamp(Instant.now().toEpochMilli()).toString());
            repo.create(newAccount);

            List<Account> accounts = repo.all();
            System.out.println("all accounts: " + accounts);

            Account account = repo.get(accounts.get(0).id);
            System.out.println("single account: " + account);

            newAccount.username = "test3";
            repo.update(newAccount);

            Account updatedAccount = repo.get(newId);
            System.out.println("new account after update: " + updatedAccount);

            repo.delete(newId);

            accounts = repo.all();
            System.out.println("all accounts at the end: " + accounts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
