package org.example.user;

import org.example.IMDB;
import org.example.entities.Actor;
import org.example.entities.Production;

public interface
StaffInterface {
    void addProductionSystem(Production p, IMDB imdb);
    void addActorSystem(Actor a, IMDB imdb);
    void removeProductionSystem(Production production, IMDB imdb);
    void removeActorSystem(Actor actor, IMDB imdb);
    void updateProduction(IMDB imdb);
    void updateActor(IMDB imdb);

}
