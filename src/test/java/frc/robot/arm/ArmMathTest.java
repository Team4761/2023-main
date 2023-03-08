package frc.robot.arm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArmMathTest {
    @Test
    public void testArmConsistency() {
        ArmMath math = new ArmMath();
        for (int x = 0; x < 44; x += 2) {
            for (int z = 0; z < 50; z += 2) {
                var theta1 = math.arm1Theta(x, z);
                var theta2 = math.arm2Theta(x, z);
                if (!Double.isNaN(theta1)) {
                    System.out.println("(" + x + "," + z + ") = (" + theta1 + "," + theta2 + ")");
                    var comp = math.getPoint(theta1, theta2);
                    Assertions.assertTrue(Math.abs(comp.getX() - x) < .1);
                    Assertions.assertTrue(Math.abs(comp.getY() - z) < .1);
                }
            }
        }
    }
}
