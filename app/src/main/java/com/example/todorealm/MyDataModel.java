package com.example.todorealm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MyDataModel extends RealmObject {
    @PrimaryKey
    private long id;
    private String name;
    private int age;

    // Required public no-argument constructor for Realm
    public MyDataModel() {
    }

    // Custom constructor
    public MyDataModel(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
