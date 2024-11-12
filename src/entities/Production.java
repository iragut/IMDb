package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.enums.Genre;
import org.example.input.production.Rating;
import org.example.user.User;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Production implements Comparable<Object>, SortedInterface{
    private String title;
    private String plot;
    private String type;
    private double averageRating;
    private List<String> directors;
    private List<String> actors;
    private List<Genre> genres;
    private List<Rating> ratings;
    private ImageIcon image;
    private String trailer;

    public abstract void displayInfo();

    public abstract void displayInfoUI(User<?> user);

    @Override
    public String getName() {
        return this.title;
    }

    @Override
    public int compareTo(@NotNull Object o) {
        return this.title.compareTo(((Production) o).title);
    }

    public int compareToByHighestRating(@NotNull Object o) {
        return Double.compare(((Production) o).getAverageRating(), this.getAverageRating());
    }
    public int compareToByHighestNumberOfRating(@NotNull Object o) {
        return Integer.compare(((Production) o).ratings.size(), this.ratings.size());
    }

    public int compareToByLowestNumberOfRating(@NotNull Object o) {
        return Integer.compare(this.ratings.size(), ((Production) o).ratings.size());
    }

    public Genre stringToGenre(String genre) {
        for (Genre g : Genre.values()) {
            if (g.toString().equals(genre)) {
                return g;
            }
        }
        return null;
    }

    public double getAverageRating() {
        double sum = 0;
        for (Rating rating : ratings) {
            sum += rating.getRating();
        }
        averageRating = sum / ratings.size();
        return averageRating;
    }
}
