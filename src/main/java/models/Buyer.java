package models;

public class Buyer {
    private String name;
    private String lastName;

    public Buyer(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }


}
