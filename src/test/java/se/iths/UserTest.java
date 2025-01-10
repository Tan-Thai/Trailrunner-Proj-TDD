package se.iths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTest {
    User user;

    @BeforeEach
    public void setUp() {
        user = new User("Tan", 177, 56, 29);
    }

    @Test
    void getName() {
        String userName = user.getName();
        assertEquals("Tan", userName, "Name does not match");
    }

    @Test
    void setName() {
        user.setName("Thai");
        String userName = user.getName();
        assertEquals("Thai", userName, "New name does not match");
    }

    @Test
    void getHeight() {
        double userHeight = user.getHeight();
        assertEquals(177, userHeight, "Height does not match");
    }

    @Test
    void setHeight() {
        user.setHeight(177.8);
        double userHeight = user.getHeight();
        assertEquals(177.8, userHeight, "New height does not match");
    }

    @Test
    void getAge() {
        int userAge = user.getAge();
        assertEquals(29, userAge, "Age does not match");
    }

    @Test
    void setAge() {
        user.setAge(28);
        int userAge = user.getAge();
        assertEquals(28, userAge, "New age does not match");
    }

    @Test
    void getWeight() {
        double userWeight = user.getWeight();
        assertEquals(56, userWeight, "Weight does not match");
    }

    @Test
    void setWeight() {
        user.setWeight(54.7);
        double userWeight = user.getWeight();
        assertEquals(54.7, userWeight, "New weight does not match");
    }

}
