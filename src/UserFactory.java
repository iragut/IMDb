package org.example;

import org.example.enums.AccountType;
import org.example.input.account.CredentialsInput;
import org.example.user.User;


public class UserFactory {
    public static User<?> factory(AccountType accountType, String name, CredentialsInput credentialsInput) {
        if (AccountType.Regular.equals(accountType)) {
            return new Regular<>(credentialsInput, name);
        } else if (AccountType.Contributor.equals(accountType)) {
            return new Contributor<>(credentialsInput, name);
        } else if (AccountType.Admin.equals(accountType)) {
            return new Admin<>(credentialsInput, name);
        } else {
            throw new IllegalArgumentException("Invalid account type");
        }
    }
}
