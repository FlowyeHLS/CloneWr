package com.amicus.testapk;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * Created by sizik on 09.11.2026.
 */
@Dao
public interface UserDao {
    @Insert
    long insert(User user);

    @Query("SELECT*FROM mytable")
    List<User>getAll();

    @Query("DELETE FROM mytable")
    int clearAll();

    @Query("DELETE FROM sqlite_sequence WHERE name = 'mytable'")
    void reset();

    @Query("DELETE FROM mytable WHERE id=:id")
    int deleteById(int id);

    @Query("SELECT * FROM mytable " + "WHERE name LIKE '%' || :search || '%' " + "OR email LIKE '%' || :search || '%'")
    List<User> search(String search);
}
