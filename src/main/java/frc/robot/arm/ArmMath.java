package frc.robot.arm;

import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.main.Constants;

public class ArmMath {
    double armLength1 = Constants.ARM_LENGTH_BOTTOM;
    double armLength2 = Constants.ARM_LENGTH_TOP;

    //x and y relative to arm1's joint
    public double arm1Theta(double x, double y) {
        double d = Math.sqrt(x * x + y * y);
        double thetaC = Math.acos((armLength2 * armLength2 - d * d - armLength1 * armLength1) / (-2 * armLength1 * d));
        if (x < 0)
            return Math.atan(y / x) + thetaC;
        if (x == 0)
            return Math.PI / 2 + thetaC;
        if (x > 0)
            return Math.atan(-x / y) + thetaC + Math.PI / 2;
        return Math.PI / 4;
    }

    public double arm2Theta(double x, double y) {
        double d = Math.sqrt(x * x + y * y);
        return Math.acos((d * d - armLength1 * armLength1 - armLength2 * armLength2) / (-2 * armLength1 * armLength2));
    }

    //chatGPT did this lol. It works, returns both values
    public double[] getThetas(double x, double y) {  //change to 2d array or make if states to return best solution
        double[][] angles = new double[2][2];
        double c2 = (Math.pow(x, 2) + Math.pow(y, 2) - Math.pow(armLength1, 2) - Math.pow(armLength2, 2)) / (2 * armLength1 * armLength2);
        double s2 = Math.sqrt(1 - Math.pow(c2, 2));

        // Calculate both possible values of the second joint angle
        angles[0][1] = Math.atan2(s2, c2);
        angles[1][1] = Math.atan2(-s2, c2);

        // Calculate both possible values of the first joint angle
        double k1_0 = armLength1 + armLength2 * c2;
        double k2_0 = armLength2 * s2;
        angles[0][0] = Math.atan2(y, x) - Math.atan2(k2_0, k1_0);

        double k1_1 = armLength1 + armLength2 * c2;
        double k2_1 = -armLength2 * s2;
        angles[1][0] = Math.atan2(y, x) - Math.atan2(k2_1, k1_1);

        return angles[0];
    }

    //thing to fit given parameters
    //inches
    final private double robotLength = 30.0;//robot's length (parallel to arms)
    final private double jointIn = 3.0;//distance joint is in from front
    final private double robotHeight = 40.0;//robot's height //maybe not actual height
    final private double jointHeight = 8.0;//joint height from robot
    final private double maxX = 48.0;//max x distance from robot
    final private double maxY = 78.0;//max y distance from robot
    final private double maxD = Math.sqrt(armLength1 * armLength1 + armLength2 * armLength2);
    //also max and mins distances to be configured

    //<not sure if translation2d is implemented correctly>
    public Translation2d inPoint(Translation2d testPoint) {
        if (inBounds(new Translation2d(testPoint.getX(), testPoint.getY())))
            return testPoint;
        else {
            //loop for nearest point
            double minDistance = 100;
            //use latest point instead
            Translation2d sudoPoint = new Translation2d(1000, 1000);
            //modify these numbers in accordance to joystick sensitivity (initial and end should be 2x max distance change)
            for (int i = -5; i < 5; i += 2) {
                for (int j = -5; j < 5; j += 2) {
                    Translation2d jp = new Translation2d(testPoint.getX() + i / 5.0, testPoint.getY() + j / 5.0);
                    if (testPoint.getDistance(jp) < minDistance) {
                        sudoPoint = jp;
                        minDistance = testPoint.getDistance(jp);
                    }
                }
            }
            if (sudoPoint.getX() < 100 && sudoPoint.getY() < 100 && minDistance < 10)
                return sudoPoint;
        }
        return null;
    }

    public boolean inBounds(Translation2d testPoint)//,Translation2d testPoint)
    {
        //only for default joint configuration
        Translation2d jLock1 = new Translation2d(-Math.sqrt(armLength1 * armLength1 - jointHeight * jointHeight), -jointHeight);
        Translation2d origin = new Translation2d();
        if (testPoint.getDistance(jLock1) >= armLength1 && testPoint.getDistance(origin) <= maxD && testPoint.getDistance(origin) >= Math.abs(armLength1 - armLength2) && testPoint.getX() <= maxX + jointIn && testPoint.getX() >= -(maxX + robotLength - jointIn) && testPoint.getY() <= robotHeight + maxY && testPoint.getY() >= -(robotHeight + jointHeight)) {
            if (testPoint.getY() <= -jointHeight) {
                if (testPoint.getX() >= jointIn || testPoint.getX() <= -robotLength + jointIn)
                    return true;
            } else
                return true;
        }
        return false;
    }

    //gets point from two thetas of two arms
    public Translation2d getPoint(double theta1, double theta2) {
        return new Translation2d(
                armLength1 * Math.cos(theta1) + armLength2 * Math.cos(theta1 + theta2),
                armLength1 * Math.sin(theta1) + armLength2 * Math.sin(theta1 + theta2));  //Checked 2/18, added theta1 - theta 2
    }

    public boolean comparePoint(Translation2d wantedPoint, Translation2d currentPoint, double variation) {
        return Math.abs(wantedPoint.getX() - currentPoint.getX()) < variation && Math.abs(wantedPoint.getY() - currentPoint.getY()) < variation;
    }
}
