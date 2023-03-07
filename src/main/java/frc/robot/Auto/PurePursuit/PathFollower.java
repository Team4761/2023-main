package frc.robot.Auto.PurePursuit;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.main.Constants;

// follow a path generated by pathgen
// turn into command later i guess
public class PathFollower {
    private double points[][];
    private double[] targetVelocities;

    private double time;
    private int closestIndex = 0;
    private double lookaheadIndex = 0;
    private double[] lookaheadPoint = {0, 0};

    private double output = 0;

    // lookahead radius
    // thing says pick a lookahead distance for a path around 12-25 based on how curvy the path is, with shorter lookahead distances for curvier paths
    private double CIRCLE = 2;

    public PathFollower(double[][] ps, double[] targetVels) {
        points = ps;
        targetVelocities = targetVels;
    }

    // calculate voltage for left and right side, input pose, leftvel and rightvel in meters, and time in seconds
    public double[] calculate(Pose2d pos, double leftVel, double rightVel, double t) {
        SmartDashboard.putNumber("closest", closestIndex + 1);
        SmartDashboard.putNumber("end index", points.length);

        double[] velocities = new double[2];
        
        // check all points forward from the last closest one for the new closest point
        for (int i = closestIndex+1; i<points.length; i++) {
            if (pos.getTranslation().getDistance(new Translation2d(points[i][0], points[i][1])) < pos.getTranslation().getDistance(new Translation2d(points[closestIndex][0], points[closestIndex][1]))) {
                closestIndex = i;
            }
        }

        
        // find lookahead
        for (int i = (int)lookaheadIndex; i<points.length-1; i++) {
            double[] intersect = intersection(points[i], points[i+1], pos, CIRCLE);
            if(intersect[0] != -1.0 && intersect[0]+i > lookaheadIndex) {
                lookaheadPoint[0] = intersect[1];
                lookaheadPoint[1] = intersect[2];
            }
        }

        double maxChange = (t-time) * Constants.DRIVETRAIN_MAX_ACCELERATION;
        
        double targetAccel = MathUtil.clamp(targetVelocities[closestIndex] - output, -maxChange, maxChange);
        
        double velocityToGo = output+targetAccel;
        output+=targetAccel;

        // do stuff w/ lookahead point
        /*double tanned = (lookaheadPoint[1]-pos.getY())/(lookaheadPoint[0]-pos.getX());
        double x = Math.abs(-tanned * lookaheadPoint[0] + lookaheadPoint[1] + tanned*pos.getX()-pos.getY()) / Math.sqrt(tanned*tanned + 1);
        double side = Math.signum(Math.sin(pos.getRotation().getRadians() * (lookaheadPoint[0]-pos.getX())) - Math.cos(pos.getRotation().getRadians()) * (lookaheadPoint[1]-pos.getY()));
        */
        
        // my x that i think makes sense
        double x = (lookaheadPoint[1]-pos.getY())*Math.cos(-pos.getRotation().getRadians())-(lookaheadPoint[0]-pos.getX())*Math.sin(-pos.getRotation().getRadians());
        if (x!=0) {
            double radius = (CIRCLE*CIRCLE)/(2*x);// *side;


            //get the wheel velocities using the stufff
            //To get the target velocity take the target velocity associated with the closest point, and constantly feed this value through the rate limiter to get the acceleration-limited target velocity.
            
            // W(angular velocity) = (left wheel speed - right wheel speed) / track width


            System.out.println("Lookahead: "+lookaheadPoint[0]+", "+lookaheadPoint[1]);
            System.out.println("Curve radius: "+radius);

            velocities[0] = velocityToGo * (radius+Constants.trackWidth/2)/(radius-Constants.trackWidth/2);
            velocities[1] = velocityToGo * (radius-Constants.trackWidth/2)/(radius+Constants.trackWidth/2);
        } else {
            velocities[0] = velocityToGo;
            velocities[1] = velocityToGo;

        }
        // FF = Kv * velocities[0] + Ka * targetAccel;
        // FB = Kp * (velocities[0] - actualVel);


        // units are in meters
        velocities[0] = - (0.4 * velocities[0] + 0.002 * targetAccel +  0.03 * (velocities[0] - leftVel));
        velocities[1] = 0.4 * velocities [1] + 0.002 * targetAccel +  0.03 * (velocities[1] - rightVel);
        
        velocities[0] += Math.signum(velocities[0])*0.6;
        velocities[1] += Math.signum(velocities[1])*0.6;

        time = t;
        return velocities;
    }

    // intersection of line and circle, with points of start of line, end of line, center of circle, radius of circle
    // returns the t value, x, y of the highest t value intersection, or -1.0 for the t value if no intersection
    private double[] intersection (double[] start, double[] end, Pose2d center, double radius) {

        double[] d = {end[0] - start[0], end[1] - start[1]}; // ( Direction vector of ray, from start to end )
        double[] f = {start[0] - center.getX(),  start[1] - center.getY()}; // ( Vector from center sphere to ray start )
        

        double a = d[0]*d[0] + d[1]*d[1];
        double b = 2* f[0]*d[0] + f[1]*d[1];
        double c = f[0]*f[0] + f[1]*f[1] - radius*radius;
        double discriminant = b*b - 4*a*c;

        double[] intersection = new double[3];
        if (discriminant < 0) {
            // no intersection
            intersection[0] = -1.0;
        }else{

            discriminant = Math.sqrt(discriminant);
            double t1 = (-b - discriminant)/(2*a);
            double t2 = (-b + discriminant)/(2*a);
            // if t1 valid
            if (t1 >= 0 && t1 <=1){
                intersection[0] = 1.0;
                intersection[1] = start[0] + t1 * d[0];
                intersection[2] = start[1] + t1 * d[1];

                // if t2 valid and more than t1
                if (t2 >= 0 && t2 <=1 && t2>t1){
                    //return t2 intersection
                    intersection[0] = 1.0;
                    intersection[1] = start[0] + t2 * d[0];
                    intersection[2] = start[1] + t2 * d[1];
                    return intersection;
                } else {
                    // return t1 if t2 not valid or not further along
                    return intersection;
                }
            }
            // if t1 invalid and t2 valid
            else if (t2 >= 0 && t2 <=1){
                //return t2 intersection
                intersection[0] = 1.0;
                intersection[1] = start[0] + t2 * d[0];
                intersection[2] = start[1] + t2 * d[1];
                return intersection;
            }
            else {
                //otherwise, no intersection
                intersection[0] = -1.0;
            }
        }
        return intersection;
    }


}
