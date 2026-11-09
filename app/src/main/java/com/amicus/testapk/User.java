package com.amicus.testapk;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by sizik on 09.11.2026.
 */
@Entity(tableName = "mytable")
public class User {
    @PrimaryKey(autoGenerate = true)
    int id;
    String name;
    String email;
    String phone;
}

