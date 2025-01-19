package se.iths.core;

public class User {
    private String name;
    private int age;
    private double height;
    private double weight;
    private SessionHandler sessionCollection;

    public SessionHandler getSessionCollection() {
        return sessionCollection;
    }


    public User(String name, int age, double height, double weight , SessionHandler sessionCollection) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.sessionCollection = sessionCollection;
    }

    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public double getHeight() {
        return height;
    }
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setHeight(double height) {
        this.height = height;
    }

}
