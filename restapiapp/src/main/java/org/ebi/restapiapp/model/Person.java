package org.ebi.restapiapp.model;

import javax.persistence.*;

@Entity
@Table(name="person")
public class Person {

    private long id;
    private String firstName;
    private String lastName;
    private int age;
    private String favouriteColor;

    public Person() {
    }
    public Person(String firstName, String lastName, int age, String favouriteColor) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.favouriteColor = favouriteColor;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "age", nullable = false)
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    @Column(name = "favourite_color", nullable = false)
    public String getFavouriteColor() {
        return favouriteColor;
    }
    public void setFavouriteColor(String favouriteColor) {
        this.favouriteColor = favouriteColor;
    }
}
