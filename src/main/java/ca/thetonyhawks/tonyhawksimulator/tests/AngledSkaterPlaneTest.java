package ca.thetonyhawks.tonyhawksimulator.tests;

import ca.thetonyhawks.tonyhawksimulator.model.planes.AngledSkaterPlane;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AngledSkaterPlaneTest {
    @Test
    public void testGetHeight() {
        assertEquals(0.87, AngledSkaterPlane.getHeight(1_000.0, 0.05));
        assertEquals(8.66, AngledSkaterPlane.getHeight(10.0, 59.99));
        assertEquals(8.78, AngledSkaterPlane.getHeight(25.69, 19.996));
        assertEquals(0, AngledSkaterPlane.getHeight(0, 45.75));
        assertEquals(-8.85, AngledSkaterPlane.getHeight(26.0, -19.91));
        assertEquals(259.05, AngledSkaterPlane.getHeight(260.0, 94.9));

    }
}
