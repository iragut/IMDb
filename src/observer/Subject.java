package org.example.observer;

import org.example.observer.Observer;
public interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(Observer observer, String notification);
}
