package ru.gb.lesson9;

import java.util.List;

public interface Repo {
    // CRUD
    // create read update delete
    int create(String name, int population);
    List<City> getAll();
    void update(City city);
    void delete(City city);
    void delete(int id);
}
