package ca.thetonyhawks.tonyhawksimulator.tests;

import ca.thetonyhawks.tonyhawksimulator.model.AnimationModel;
import ca.thetonyhawks.tonyhawksimulator.model.planes.AngledSkaterPlane;
import org.junit.Before;
import org.junit.Test;

import java.math.RoundingMode;
import java.text.DecimalFormatSymbols;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimationModelTest {
    @Before
    public void setUp() {
        AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.setRoundingMode(RoundingMode.HALF_UP);

        DecimalFormatSymbols dfs = AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.getDecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.setDecimalFormatSymbols(dfs);
    }

    @Test
    public void testGetAngledPlaneAcceleration() {

        assertEquals(1.61, AngledSkaterPlane.getAngledPlaneAcceleration(9.8, 0.01, 10, 65));
        assertEquals(1.94, AngledSkaterPlane.getAngledPlaneAcceleration(2, 0, 75.6, 200));
        assertEquals(49.76, AngledSkaterPlane.getAngledPlaneAcceleration(50, 0.9, 89.69, 400));
        assertEquals(9.85, AngledSkaterPlane.getAngledPlaneAcceleration(10.50, 0, 69.69, 1));
    }

    @Test
    public void testGetFallDuration() {

        assertEquals(2.53, AnimationModel.getFallDuration(8, 2.5));
        assertEquals(4.35, AnimationModel.getFallDuration(236, 25));
        assertEquals(2.80, AnimationModel.getFallDuration(999.99, 254.66));
        assertEquals(10, AnimationModel.getFallDuration(0.5, 0.01));
        assertEquals(2.83, AnimationModel.getFallDuration(5, 1.25));
        assertEquals(1.79, AnimationModel.getFallDuration(8, 5));
        assertEquals(4.16, AnimationModel.getFallDuration(8.75, 1.01));
        assertEquals(4.35, AnimationModel.getFallDuration(99.28, 10.5));
        assertEquals(14.14, AnimationModel.getFallDuration(0.10, 0.001));
    }

}
