package org.example.input.production;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entities.Episode;
import org.example.enums.Genre;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductionInput {
    private String title;
    private String type;
    private List<String> directors;
    private List<String> actors;
    private List<Genre> genres;
    private List<Rating> ratings;
    private String plot;
    private double averageRating;
    private String duration;
    private int releaseYear;
    private int numSeasons;
    private Map<String, List<Episode>> seasons;

}
