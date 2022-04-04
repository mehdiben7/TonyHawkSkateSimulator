package ca.thetonyhawks.tonyhawksimulator.tests;

import ca.thetonyhawks.tonyhawksimulator.model.AnimationModel;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimationModelTest {

    @Test
    public void testGetAngledPlaneAcceleration() {

        assertEquals(1.61, AnimationModel.getAngledPlaneAcceleration(9.8, 0.01, 10, 65));
        assertEquals(1.94, AnimationModel.getAngledPlaneAcceleration(2, 0, 75.6, 200));
        assertEquals(49.76, AnimationModel.getAngledPlaneAcceleration(50, 0.9, 89.69, 400));
        assertEquals(9.85, AnimationModel.getAngledPlaneAcceleration(10.50, 0, 69.69, 1));
    }

    @Test
    public void testGetFallDuration() {

        assertEquals(2.53, AnimationModel.getFallDuration(8, 2.5));
        assertEquals(4.35, AnimationModel.getFallDuration(236, 25));
        assertEquals(2.80, AnimationModel.getFallDuration(999.99, 254.66));
        assertEquals(10, AnimationModel.getFallDuration(0.5, 0.01));
        assertEquals(2.83, AnimationModel.getFallDuration(5, 1.25));
    }
}
