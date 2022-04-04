package ca.thetonyhawks.tonyhawksimulator.tests;


import ca.thetonyhawks.tonyhawksimulator.model.Skater;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SkaterTest {
    @Test
    public void testGetKineticEnergy() {

        assertEquals(140, Skater.getKineticEnergy(70, 2));
        assertEquals(215.62, Skater.getKineticEnergy(69, 2.5));
        assertEquals(4648.86, Skater.getKineticEnergy(89, 10.221));
        assertEquals(141.02, Skater.getKineticEnergy(50, 2.375));
    }
}
