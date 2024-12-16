package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public abstract class DaoS<T> {

    public abstract List<T> getAll() throws SQLException;

    public abstract void insert(T t) throws SQLException;

    public abstract void update(T t) throws SQLException;

    public abstract void delete(T t) throws SQLException;
    
    public abstract void deleteById(int id) throws SQLException;

    public abstract T getById(int id) throws SQLException;
    

}