package se.iths.core;

public class User {
    private String name;
    private int age;
    private double heightInCm;
    private double weightInKg;
    private SessionHandler sessionCollection;

    public SessionHandler getSessionCollection() {
        return sessionCollection;
    }


    public User(String name, int age, double heightInCm, double weightInKg, SessionHandler sessionCollection) {
        this.name = name;
        this.age = age;
        this.heightInCm = heightInCm;
        this.weightInKg = weightInKg;
        this.sessionCollection = sessionCollection;
    }

    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public double getHeightInCm() {
        return heightInCm;
    }
    public double getWeightInKg() {
        return weightInKg;
    }

    public void setWeightInKg(double weightInKg) {
        this.weightInKg = weightInKg;
        if (this.weightInKg < 0 )
            this.weightInKg = 0;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
        if (this.age < 0 )
            this.age = 0;
    }
    public void setHeightInCm(double heightInCm) {
        this.heightInCm = heightInCm;
        if (this.heightInCm < 0 )
            this.heightInCm = 0;
    }

}
