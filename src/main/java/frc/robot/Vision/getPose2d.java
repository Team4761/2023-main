// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Vision;

import java.util.List;

import org.photonvision.RobotPoseEstimator;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class getPose2d extends CommandBase {

  Pose2d prevPose;



  /** Creates a new getPose2d. */
  public getPose2d() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    AprilTagFieldLayout fieldLayout = visionVars.fieldLayout;
    RobotPoseEstimator poseEstimmator = visionVars.robotPoseEstimator;
    prevPose = new Pose2d();

    }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    visionVars.getEstimatedPose(prevPose);
    prevPose = visionVars.getEstimatedPose(prevPose).getFirst();



   
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
