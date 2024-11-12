package org.example.observer;

import java.util.ArrayList;
import java.util.List;

public class NotificationSubject implements Subject {
    private List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Observer observer, String notification) {
        observer.update(notification);
    }
}
