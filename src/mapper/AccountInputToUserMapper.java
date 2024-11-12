package org.example.mapper;


import org.example.Admin;
import org.example.Contributor;
import org.example.Regular;
import org.example.entities.Actor;
import org.example.entities.Production;
import org.example.user.User;
import org.example.enums.AccountType;
import org.example.input.account.AccountInput;

import java.util.ArrayList;
import java.util.List;

public class AccountInputToUserMapper {
    public static User<String> mapAccountInputToUser(final AccountInput accountInput, List<Actor> actors, List<Production> productions) {
        if (accountInput.getUserType().equals(AccountType.Admin.name())) {
            final Admin<String> adminUser = new Admin<>();
            adminUser.setExperience(0);
            adminUser.setRequests(new ArrayList<>());
            adminUser.setUserType(AccountType.Admin);
            adminUser.setUsername(accountInput.getUsername());
            adminUser.setFavorites(accountInput.getFavoriteCombine(actors, productions));
            adminUser.setContributions(accountInput.getContributionCombine(actors, productions));
            adminUser.setInformation(accountInput.getInformation());
            if (accountInput.getNotifications() == null)
                adminUser.setNotifications(new ArrayList<>());
            else
                adminUser.setNotifications(accountInput.getNotifications());
            return adminUser;
        }
        if (accountInput.getUserType().equals(AccountType.Contributor.name())) {
            final Contributor<String> contributorUser = new Contributor<>();
            contributorUser.setExperience(accountInput.getExperience());
            contributorUser.setRequests(new ArrayList<>());
            contributorUser.setUserType(AccountType.Contributor);
            contributorUser.setUsername(accountInput.getUsername());
            contributorUser.setFavorites(accountInput.getFavoriteCombine(actors, productions));
            contributorUser.setContributions(accountInput.getContributionCombine(actors, productions));
            contributorUser.setInformation(accountInput.getInformation());
            if (accountInput.getNotifications() == null)
                contributorUser.setNotifications(new ArrayList<>());
            else
                contributorUser.setNotifications(accountInput.getNotifications());
            return contributorUser;
        }
        final Regular<String> regularUser = new Regular<>();
        regularUser.setExperience(accountInput.getExperience());
        regularUser.setUserType(AccountType.Regular);
        regularUser.setNotifications(new ArrayList<>());
        regularUser.setUsername(accountInput.getUsername());
        regularUser.setFavorites(accountInput.getFavoriteCombine(actors, productions));
        regularUser.setInformation(accountInput.getInformation());
        if (accountInput.getNotifications() == null)
            regularUser.setNotifications(new ArrayList<>());
        else
            regularUser.setNotifications(accountInput.getNotifications());
        return regularUser;
    }
}
