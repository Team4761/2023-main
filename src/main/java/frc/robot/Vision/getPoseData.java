// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Vision;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class getPoseData extends CommandBase {

  Pose3d prevPose;



  /** Creates a new getPose2d. */
  public getPoseData() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    //  AprilTagFieldLayout fieldLayout = visionVars.fieldLayout;
    // RobotPoseEstimator poseEstimmator = visionVars.robotPoseEstimator;
    prevPose = new Pose3d();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {


    visionVarsAndMethods.getEstimatedPose();
    prevPose = visionVarsAndMethods.getEstimatedPose().getFirst();

    SmartDashboard.putNumber("pose x", prevPose.getX());
    SmartDashboard.putNumber("pose y", prevPose.getY());
    SmartDashboard.putNumber("best tag x", visionVarsAndMethods.getBestTagTransform().getFirst());
    SmartDashboard.putNumber("best tag y", visionVarsAndMethods.getBestTagTransform().getSecond());
    SmartDashboard.putNumber("best tag ID", visionVarsAndMethods.getBestTagID());
    SmartDashboard.putNumber("best tag pose ambi", visionVarsAndMethods.getBestTagPoseAmbi());
    SmartDashboard.putBoolean("is tag", visionVarsAndMethods.getIsTarget());

  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }


}