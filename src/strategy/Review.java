package org.example.strategy;

public class Review implements ExperienceStrategy {
    @Override
    public int calculateExperience() {
        return 4;
    }
}
