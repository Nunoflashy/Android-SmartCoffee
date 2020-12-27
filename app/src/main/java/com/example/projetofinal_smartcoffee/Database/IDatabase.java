package com.example.projetofinal_smartcoffee.Database;

import java.util.List;

public interface IDatabase {
    void open();
    void close();
    void delete();
    String getName();
    String getPath();
    <T> List<T> getAll();
}
