package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public abstract class Dao<T> {

    public abstract List<T> getAll() throws SQLException;

    public abstract int insert(T t) throws SQLException;

    public abstract int update(T t) throws SQLException;

    public abstract boolean delete(int id) throws SQLException;

    public abstract T getById(int id) throws SQLException;
    

}