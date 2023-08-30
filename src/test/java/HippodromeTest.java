import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {

    @Test
    void constructorWhenArgIsNullAndCheckExeptionMessage() {
        Throwable exception= assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    void constructorWhenArgsIsEmptyAndCheckExteptionMessage() {
        Throwable exception= assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<Horse>()));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void getHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            String name = nameRandom(3, 10);
            double speed = speedRandom(1.0, 4.0);

            horses.add(new Horse(name, speed));
        }

        Hippodrome hippodrome = new Hippodrome(horses);

        assertIterableEquals(horses, hippodrome.getHorses());
        assertArrayEquals(horses.toArray(), hippodrome.getHorses().toArray());
    }

    @Test
    void move() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Horse horse = Mockito.mock(Horse.class);
            horses.add(horse);
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        for (Horse horse : hippodrome.getHorses()) {
            Mockito.verify(horse).move();
        }
    }

    @Test
    void getWinner() {
        List<Horse> horses = new ArrayList<>();
        double max = 0.0;
        for (int i = 0; i < 10; i++) {
            String name = nameRandom(3, 10);
            double speed = speedRandom(1.0, 4.0);
            double distance = speedRandom(1.0, 10.0);

            if (distance > max) max = distance;

            horses.add(new Horse(name, speed, distance));
        }

        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(max, hippodrome.getWinner().getDistance());
    }

    private String nameRandom(int minLength, int maxLength) {
        int length = new Random().nextInt(maxLength - minLength + 1) + minLength;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char randomChar = (char) (new Random().nextInt(26) + 'a');
            sb.append(randomChar);
        }

        return sb.toString();
    }

    private double speedRandom(double minValue, double maxValue) {

        Random random = new Random();
        double number = minValue + (maxValue - minValue) * random.nextDouble();
        return Math.round(number * 10.0) / 10.0;
    }
}