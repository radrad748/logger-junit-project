
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class HorseTest {


    @Test
    void constructorWhenNameIsNullAndCheckExceptionMessage() {
        Throwable exception= assertThrows(IllegalArgumentException.class, () -> new Horse(null, 2.1));
        assertEquals("Name cannot be null.", exception.getMessage());
    }


    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void constructorWhenNameIsNotCorrectAndExceptionMessage(String name) {
        Throwable exception= assertThrows(IllegalArgumentException.class, () -> new Horse(name, 2.1));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    void constructorWhenSpeedIsNotCorrectAndExceptionMessage() {
        Throwable exception= assertThrows(IllegalArgumentException.class, () -> new Horse("Bucephalus", -2.1));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void constructorWhenDistanceIsNotCorrectAndExceptionMessage() {
        Throwable exception= assertThrows(IllegalArgumentException.class, () -> new Horse("Bucephalus", 2.1, -5.0));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }


    @Test
    void getName() {
        Horse horse = new Horse("Bucephalus", 2.4);
        assertEquals("Bucephalus", horse.getName());
    }

    @Test
    void getSpeed() {
        Horse horse = new Horse("Bucephalus", 2.4);
        assertEquals(2.4, horse.getSpeed());
    }

    @Test
    void getDistanceWhenConstructorThreeArgs() {
        Horse horse = new Horse("Bucephalus", 2.4, 5.1);
        assertEquals(5.1, horse.getDistance());
    }

    @Test
    void getDistanceWhenConstructorTwoArgs() {
        Horse horse1 = new Horse("Zephyr", 2.7);
        assertEquals(0, horse1.getDistance());
    }

    @Test
    void methodMoveCallsGetRandomDouble() {
        try (MockedStatic<Horse> staticHorse = Mockito.mockStatic(Horse.class)) {

            Horse horse = new Horse("Bucephalus", 2.4);
            horse.move();

            staticHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {5.0, 4.2, 6.3})
    void getRandomDoubleChangeCorrectDistance(double random) {
        try (MockedStatic<Horse> staticHorse = Mockito.mockStatic(Horse.class)) {
            staticHorse.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);

            Horse horse = new Horse("Bucephalus", 2.4);
            horse.move();

            assertEquals(horse.getSpeed() * Horse.getRandomDouble(0.2, 0.9), horse.getDistance());
        }
    }

}