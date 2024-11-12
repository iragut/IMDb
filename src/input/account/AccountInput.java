package org.example.input.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entities.Actor;
import org.example.entities.Production;
import org.example.entities.SortedInterface;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountInput {
    private String username;
    private Integer experience;
    private Information information;
    private String userType;
    private List<String> productionsContribution;
    private List<String> actorsContribution;
    private List<String> favoriteProductions;
    private List<String> favoriteActors;
    private List<String> notifications;

    public Actor getActor(List<Actor> actors, String name){
        for (Actor actor : actors){
            if (actor.getName().equals(name))
                return actor;
        }
        return null;
    }

    public Production getProduction(List<Production> productions, String name){
        for (Production production : productions){
            if (production.getTitle().equals(name))
                return production;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> SortedSet<T> getFavoriteCombine(List<Actor> actors, List<Production> productions){
        TreeSet<T> favoriteCombine = (TreeSet<T>) new TreeSet<>(Comparator.comparing(SortedInterface::getName));
        if (favoriteProductions != null){
            for (String productionName : favoriteProductions){
                Production production = getProduction(productions, productionName);
                if (production != null)
                    favoriteCombine.add((T) production);

            }
        }
        if (favoriteActors != null){
            for (String actorName : favoriteActors){
                Actor actor = getActor(actors, actorName);
                if (actor != null)
                    favoriteCombine.add((T) actor);
            }
        }
        return favoriteCombine;
    }

    @SuppressWarnings("unchecked")
    public <T> SortedSet<T> getContributionCombine(List<Actor> actors, List<Production> productions){
        TreeSet<T> contributionCombine = (TreeSet<T>) new TreeSet<>(Comparator.comparing(SortedInterface::getName));
        if (productionsContribution != null){
            for (String productionName : productionsContribution){
                Production production = getProduction(productions, productionName);
                if (production != null)
                    contributionCombine.add((T) production);
            }
        }
        if (actorsContribution != null){
            for (String actorName : actorsContribution){
                Actor actor = getActor(actors, actorName);
                if (actor != null)
                    contributionCombine.add((T) actor);
            }
        }
        return contributionCombine;
    }

    @SuppressWarnings("unchecked")
    public <T> SortedSet<T> addContribution(SortedSet<T> contribution, Object obj){
        contribution.add((T) obj);
        return contribution;
    }

    @SuppressWarnings("unchecked")
    public <T> SortedSet<T> addFavorite(SortedSet<T> favorite, Object obj){
        favorite.add((T) obj);
        return favorite;
    }
}
