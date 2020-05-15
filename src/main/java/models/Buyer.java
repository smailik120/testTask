package models;

public class Buyer {
    private String name;
    private String lastName;

    public Buyer(String name, String surname) {
        this.name = name;
        this.lastName = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return lastName;
    }


}
