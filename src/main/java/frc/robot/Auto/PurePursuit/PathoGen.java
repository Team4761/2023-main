package frc.robot.Auto.PurePursuit;

import java.util.ArrayList;

import frc.robot.main.Constants;

public class PathoGen {
	// in meters for now
	private final double SPACING = 0.1;
	// whhere (path max velocity, k / curvature at point)
	private final double k = 2;
	ArrayList<double[]> pointsTemp;
	private double[][] points;

	// distAtPoint[i] should be the distance along path that points[i] is
	private double[] distAtPoint;
	private double[] curveAtPoint;
	private double[] maxVelAtPoint;
	private double[] targetVelAtPoint;

    //1 get the points
	public PathoGen(double[][] p) {
		//2 make more points
		// for each line segment on the path (points-1)
		for (int i = 0; i<p.length-1; i++) {
			// vector = endpoint-startpoint
			double[] vector = {p[i+1][0]-p[i][0], p[i+1][1]-p[i][1]};
			double vector_magnitude = Math.sqrt(vector[0]*vector[0] + vector[1]*vector[1]);

			int num_points_that_fit = (int)Math.ceil(vector_magnitude / SPACING);

			//points = new double[num_points_that_fit+1][2];
			
			//normalized vector * spacing
			vector[0] = vector[0]/vector_magnitude*SPACING;
			vector[1] = vector[1]/vector_magnitude*SPACING;
			
			for(int j = 0; j<num_points_that_fit; j++) {
				//Add (start_point + vector * i) to new_points
				double[] nextPoint = {p[i][0]+vector[0]*j, p[i][1]+vector[1]*j};
				pointsTemp.add(nextPoint);
			}
		}
		//add the last point
		pointsTemp.add(p[p.length-1]);


		//3: smooth points
		//the paper says a value of b between 0.75 and 0.98 to work well, with a set to 1 - b and tolerance = 0.001, can tune b on a per-path basis
		points = smoother((double[][])pointsTemp.toArray(), 0.2, 0.8, 0.001);
		

		//4: store distance along path, target velocity, and curvature of path at each point
		// the distances
		distAtPoint = new double[points.length];
		distAtPoint[0] = 0;
		for(int i = 1; i<points.length; i++) {
			distAtPoint[i] = distAtPoint[i-1] + findDistance(points[i], points[i-1]);
		}
		// the curvatures
		curveAtPoint = new double[points.length];
		curveAtPoint[0] = 0;
		curveAtPoint[points.length-1] = 0;
		for(int i = 1; i < points.length-1; i++) {
			// where 1 is points[i], 2 is points[i-1], 3 is points [i+1]
			double xDiff1 = (points[i][0]-points[i-1][0]);
			if (xDiff1 == 0) xDiff1 = 0.0001;
			double k1 = 0.5*(points[i][0]*points[i][0] + points[i][1]*points[i][1] - points[i-1][0]*points[i-1][0] - points[i-1][1]*points[i-1][1])/xDiff1;
			double k2 = (points[i][1] - points[i-1][1])/xDiff1;
			double b = 0.5*(points[i-1][0]*points[i-1][0] - 2*points[i-1][0]*k1 + points[i-1][1]*points[i-1][1] - points[i+1][0]*points[i+1][0] + 2*points[i+1][0]*k1-points[i+1][1]*points[i+1][1]);
			double thing2 = points[i+1][0]*k2 - points[i+1][1] + points[i-1][1] - points[i-1][0]*k2;
			if (thing2 == 0) thing2 = 0.0001;
			b/=thing2;
			double a = k1-k2*b;
			double r = Math.sqrt((points[i][0]-a)*(points[i][0]-a)+(points[i][1]-b)*(points[i][1]-b));

			curveAtPoint[i] = 1/r;
		}

		// the velocities
		maxVelAtPoint = new double[points.length];
		for (int i = 0; i<points.length; i++) {
			if (curveAtPoint[i]==0) maxVelAtPoint[i] = Constants.DRIVETRAIN_MAX_VELOCITY;
			else maxVelAtPoint[i] = Math.min(Constants.DRIVETRAIN_MAX_VELOCITY, k/curveAtPoint[i]);
		}
		//the minimum of: the point’s current velocity, and the largest velocity the robot can reach by that point when starting at the last point
		// combine this and last loop?
		targetVelAtPoint = new double[points.length];
		targetVelAtPoint[0] = 0; // i don't know
		for (int i = 1; i<points.length-1; i++) {
			targetVelAtPoint[i] = Math.min(maxVelAtPoint[i], Math.sqrt(targetVelAtPoint[i-1]*targetVelAtPoint[i-1] + 2*Constants.DRIVETRAIN_MAX_ACCELERATION*( distAtPoint[i]-distAtPoint[i-1])));
		}
		targetVelAtPoint[points.length-1] = 0;
		for (int i = points.length-2; i>=0; i--) {
			targetVelAtPoint[i] = Math.min(targetVelAtPoint[i], Math.sqrt(targetVelAtPoint[i + 1]) + 2*Constants.DRIVETRAIN_MAX_ACCELERATION*distAtPoint[i+1]-distAtPoint[i]);
		}

		// DONE I THINK
	}

	public double[][] getPoints() {
		return points;
	}

	public double[] getTargetVelocities() {
		return targetVelAtPoint;
	}


	private double findDistance(double[] p1, double[] p2) {
		return Math.sqrt((p1[0]-p2[0])*(p1[0]-p2[0])+(p1[1]-p2[1])*(p1[1]-p2[1]));
	}


    // smooths the points
    private double[][] smoother(double[][] path, double weight_data, double weight_smooth, double tolerance)
	{

		//copy array
		double[][] newPath = doubleArrayCopy(path);

		double change = tolerance;
		while(change >= tolerance)
		{
			change = 0.0;
			for(int i=1; i<path.length-1; i++)
				for(int j=0; j<path[i].length; j++)
				{
					double aux = newPath[i][j];
					newPath[i][j] += weight_data * (path[i][j] - newPath[i][j]) + weight_smooth * (newPath[i-1][j] + newPath[i+1][j] - (2.0 * newPath[i][j]));
					change += Math.abs(aux - newPath[i][j]);	
				}					
		}

		return newPath;

	}
    private double[][] doubleArrayCopy(double[][] arr)
	{

		//size first dimension of array
		double[][] temp = new double[arr.length][arr[0].length];

		for(int i=0; i<arr.length; i++)
		{
			//Resize second dimension of array
			temp[i] = new double[arr[i].length];

			//Copy Contents
			for(int j=0; j<arr[i].length; j++)
				temp[i][j] = arr[i][j];
		}

		return temp;

	}
}
