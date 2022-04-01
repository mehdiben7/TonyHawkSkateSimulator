package ca.thetonyhawks.tonyhawksimulator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkaterTest {

    @Test
    void getKineticEnergy() {
        assertEquals(140, Skater.getKineticEnergy(70, 2));
        assertEquals(215.63, Skater.getKineticEnergy(69, 2.5));
        assertEquals(4648.86, Skater.getKineticEnergy(89, 10.221));
        assertEquals(141.02, Skater.getKineticEnergy(50, 2.375));
    }

    @Test
    void getPotentialGravitationalEnergy() {
        // TODO Test the method
    }
}