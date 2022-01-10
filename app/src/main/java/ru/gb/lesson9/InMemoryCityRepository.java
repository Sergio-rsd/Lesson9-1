package ru.gb.lesson9;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCityRepository implements Repo {

    private List<City> cities = new ArrayList<>();

    private int counter = 0;

    private static InMemoryCityRepository repository;

    private InMemoryCityRepository()
    {
        create("London", 11_000_000);
        create("Paris", 8_000_000);
        create("Prague", 4_000_000);
        create("Tokyo", 20_000_000);
        create("Melbourne", 5_000_000);
        create("Mexico", 15_000_000);
    }
    public static Repo getInstance()
    {
        if(repository == null)
            repository = new InMemoryCityRepository();
        return repository;
    }

    @Override
    public int create(String name, int population) {
        City city = new City(++counter, name, population);
        cities.add(city);
        return city.getId();
    }

    @Override
    public List<City> getAll() {
        return cities;
    }

    @Override
    public void update(City city) {
        for(int i = 0; i < cities.size(); i++)
        {
            if(city.getId() == cities.get(i).getId())
                cities.set(i, city);
        }
    }

    @Override
    public void delete(City city) {
        delete(city.getId());
    }

    @Override
    public void delete(int id) {
        for(int i = 0; i < cities.size(); i++)
        {
            if(id == cities.get(i).getId())
                cities.remove(i);
        }

    }
}
