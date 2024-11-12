package org.example.mapper;

import org.example.entities.Movie;
import org.example.entities.Production;
import org.example.entities.Series;
import org.example.input.production.ProductionInput;

public class ProductionInputToProductionMapper {
    public static Production mapProductionInputToProduction(final ProductionInput productionInput){
        if (productionInput.getType().equals("Movie")){
            Movie movie = new Movie();
            movie.setTitle(productionInput.getTitle());
            movie.setType(productionInput.getType());
            movie.setDirectors(productionInput.getDirectors());
            movie.setActors(productionInput.getActors());
            movie.setGenres(productionInput.getGenres());
            movie.setRatings(productionInput.getRatings());
            movie.setPlot(productionInput.getPlot());
            movie.setAverageRating(productionInput.getAverageRating());
            movie.setDuration(productionInput.getDuration());
            movie.setReleaseYear(productionInput.getReleaseYear());
            return movie;
        }
        Series series = new Series();
        series.setTitle(productionInput.getTitle());
        series.setType(productionInput.getType());
        series.setDirectors(productionInput.getDirectors());
        series.setActors(productionInput.getActors());
        series.setGenres(productionInput.getGenres());
        series.setRatings(productionInput.getRatings());
        series.setPlot(productionInput.getPlot());
        series.setAverageRating(productionInput.getAverageRating());
        series.setReleaseYear(productionInput.getReleaseYear());
        series.setNumSeasons(productionInput.getNumSeasons());
        series.setSeasons(productionInput.getSeasons());
        return series;
    }
}
