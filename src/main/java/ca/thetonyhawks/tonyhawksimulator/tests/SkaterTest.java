package ca.thetonyhawks.tonyhawksimulator.tests;

import ca.thetonyhawks.tonyhawksimulator.Skater;
import org.junit.Test;

import java.math.RoundingMode;
import java.text.DecimalFormatSymbols;

import static ca.thetonyhawks.tonyhawksimulator.AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SkaterTest {
    @Test
    public void setUp() {

    }
    @Test
    public void testGetKineticEnergy() {
        TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.setRoundingMode(RoundingMode.HALF_UP);

        DecimalFormatSymbols dfs = TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.getDecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.setDecimalFormatSymbols(dfs);
        assertEquals(140, Skater.getKineticEnergy(70, 2));
        assertEquals(215.63, Skater.getKineticEnergy(69, 2.5));
        assertEquals(4648.86, Skater.getKineticEnergy(89, 10.221));
        assertEquals(141.02, Skater.getKineticEnergy(50, 2.375));
    }
}
