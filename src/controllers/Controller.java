package controllers;

import java.util.List;

public abstract class Controller<T> {

    public abstract void add(T entity);

    public abstract void update(int id, T updatedEntity);

    public abstract void delete(int id);

    public abstract List<T> getAll();
}
