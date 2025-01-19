package se.iths.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.iths.utility.FileStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    User user;

    @BeforeEach
    public void setUp() {
        user = new User("Tan", 29, 177, 56, new SessionHandler(new FileStorage()));
    }

    @Test
    void getName() {
        String userName = user.getName();
        assertEquals("Tan", userName, "Name does not match");
    }
    @Test
    void getAge() {
        int userAge = user.getAge();
        assertEquals(29, userAge, "Age does not match");
    }
    @Test
    void getHeight() {
        double userHeight = user.getHeightInCm();
        assertEquals(177, userHeight, "Height does not match");
    }
    @Test
    void getWeight() {
        double userWeight = user.getWeightInKg();
        assertEquals(56, userWeight, "Weight does not match");
    }

    // TODO Allow user to set their stats. (age, weight, height)
    @Test
    void setName() {
        user.setName("Thai");
        String userName = user.getName();
        assertEquals("Thai", userName, "New name does not match");
    }
    @Test
    void setAge() {
        user.setAge(28);
        int userAge = user.getAge();
        assertEquals(28, userAge, "New age does not match");
    }
    @Test
    void setHeight() {
        user.setHeightInCm(177.8);
        double userHeight = user.getHeightInCm();
        assertEquals(177.8, userHeight, "New height does not match");
    }
    @Test
    void setWeight() {
        user.setWeightInKg(54.7);
        double userWeight = user.getWeightInKg();
        assertEquals(54.7, userWeight, "New weight does not match");
    }

    // TODO Don't allow a negative value to be set. (even if it bypasses the user-input)
    @Test
    void setAge_NegativeValue() {
        user.setAge(-1);
        int userAge = user.getAge();
        assertEquals(0, userAge, "New age does not match");
    }
    @Test
    void setHeight_NegativeValue() {
        user.setHeightInCm(-1);
        double userHeight = user.getHeightInCm();
        assertEquals(0, userHeight, "New age does not match");
    }
    @Test
    void setWeight_NegativeValue() {
        user.setWeightInKg(-1);
        double userWeight = user.getWeightInKg();
        assertEquals(0, userWeight, "New age does not match");
    }



    @Test
    void getSessionSize() {
        user.getSessionCollection().createSession(
                "Bloop",
                12.3,
                30934,
                LocalDate.of(2000, 4, 2));

        assertFalse(user.getSessionCollection().getSessionIDs().isEmpty(), "SessionCollection should not be empty");
    }

}
