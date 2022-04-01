package ca.thetonyhawks.tonyhawksimulator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimationModelTest { // TODO So now we have a problem with the test class

    @Test
    void getAngledPlaneAcceleration() {
        assertEquals(1.61, AnimationModel.getAngledPlaneAcceleration(9.8, 0.01, 10, 65));
        assertEquals(1.94, AnimationModel.getAngledPlaneAcceleration(2, 0, 75.6, 200));
        assertEquals(49.76, AnimationModel.getAngledPlaneAcceleration(50, 0.9, 89.69, 400));
        assertEquals(9.85, AnimationModel.getAngledPlaneAcceleration(10.50, 0, 69.69, 1));
    }
}