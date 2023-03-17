package frc.robot.command;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Drivetrain.DrivetrainSubsystem;
import frc.robot.arm.ArmMath;
import frc.robot.arm.ArmSubsystem;
import frc.robot.intake.IntakeSubsystem;
import frc.robot.leds.LEDSubsystem;
import frc.robot.leds.UpdateLED;
import frc.robot.controller.XboxControl;
import frc.robot.main.Constants;
import frc.robot.main.Robot;

public class ArmControl extends CommandBase {
    public XboxControl xbox;
    public int port;
    
    ArmMath armMath = new ArmMath();

    // for delay on arm movement
    // 0 neutral, 1 intake, 2 mid, 3 shelf, 4 top
    private int position = 0;

    public ArmControl(int port) {
        xbox = new XboxControl(port);
        this.port = port;

        ArmSubsystem armSubsystem = ArmSubsystem.getInstance();
        xbox.leftBumper().onTrue(getNeutralSequence());
        xbox.a().onTrue(new SequentialCommandGroup(getNeutralSequence(), new MoveArmAngles(Constants.INTAKE_POSITION), Commands.runOnce(this::setPosition1)));
        xbox.b().onTrue(new SequentialCommandGroup(getNeutralSequence(), new MoveArmDelayBottom(Constants.MID_RUNG_POSITION, 0.2), Commands.runOnce(this::setPosition2)));
        xbox.x().onTrue(new SequentialCommandGroup(getNeutralSequence(), new MoveArmDelayBottom(Constants.SHELF_POSITION, 0.2), Commands.runOnce(this::setPosition3)));
        xbox.y().onTrue(new SequentialCommandGroup(getNeutralSequence(), /*new MoveArmAngles(Constants.MID_RUNG_POSITION).withTimeout(0.8), */Commands.runOnce(this::setPosition4), new MoveArmDelayBottom(Constants.TOP_RUNG_POSITION, 1.2)));

        // For the Wii U button board, right stick is actually the start button
        xbox.leftStick().onTrue(Commands.runOnce(this::onPressDisablePidButton, armSubsystem));

        xbox.leftStick().onTrue(Commands.runOnce(this::onPressDisablePidButton, armSubsystem));
        xbox.rightStick().onTrue(Commands.runOnce(this::onPressEnablePidButton, armSubsystem));

        xbox.rightBumper().whileTrue(Commands.runOnce(this::onPressRightBumper, DrivetrainSubsystem.getInstance()));
        xbox.rightBumper().onFalse(Commands.runOnce(this::onPressRightBumperRelease, DrivetrainSubsystem.getInstance()));

        //xbox.leftTrigger().onTrue(Commands.runOnce(this::onPressTrigger, armSubsystem));
        //xbox.rightTrigger().onTrue(Commands.runOnce(this::onPressTrigger, armSubsystem));
        

        xbox.povUp().onTrue(Commands.runOnce(this::setLEDCone, LEDSubsystem.getInstance()));
        xbox.povDown().onTrue(Commands.runOnce(this::setLEDCube, LEDSubsystem.getInstance()));

        
        IntakeSubsystem intakeSubsystem = IntakeSubsystem.getInstance();
        xbox.leftTrigger().onTrue(Commands.runOnce(this::inTake, intakeSubsystem));
        xbox.rightTrigger().onTrue(Commands.runOnce(this::outTake, intakeSubsystem));
        xbox.leftTrigger().onFalse(Commands.runOnce(this::disableIntake, intakeSubsystem));
        xbox.rightTrigger().onFalse(Commands.runOnce(this::disableIntake, intakeSubsystem));
    }

    // allows for delays when travelling from certain positions back to neutral
    private SequentialCommandGroup getNeutralSequence() {
        switch(position) {
            case 0:
            return new SequentialCommandGroup(new MoveArmAngles(Constants.NEUTRAL_POSITION), Commands.runOnce(this::setPosition0));

            case 1:
            return new SequentialCommandGroup(new MoveArmAngles(Constants.NEUTRAL_POSITION), Commands.runOnce(this::setPosition0));
            
            case 2:
            return new SequentialCommandGroup(new MoveArmDelayTop(Constants.NEUTRAL_POSITION, 0.2), Commands.runOnce(this::setPosition0));

            case 3:
            return new SequentialCommandGroup(new MoveArmDelayTop(Constants.NEUTRAL_POSITION, 0.3), Commands.runOnce(this::setPosition0));

            case 4:
            return new SequentialCommandGroup(new MoveArmDelayTop(Constants.INBETWEEN_POSITION, 1.5), new MoveArmAngles(Constants.NEUTRAL_POSITION), Commands.runOnce(this::setPosition0));

            default:
            return new SequentialCommandGroup();
        }
    }
    private void setPosition0() {
        this.position = 0;
    }
    private void setPosition1() {
        this.position = 1;
    }
    private void setPosition2() {
        this.position = 2;
    }
    private void setPosition3() {
        this.position = 3;
    }
    private void setPosition4() {
        this.position = 4;
    }

    private void setLEDCone() {
        UpdateLED.displayCone();
    }
    private void setLEDCube() {
        UpdateLED.displayCube();
    }
    
    private void inTake() {
        IntakeSubsystem.getInstance().setSpeed(0.6);
    }
    private void outTake() {
        IntakeSubsystem.getInstance().setSpeed(-0.12);
    }
    private void disableIntake() {
        IntakeSubsystem.getInstance().setSpeed(0.15);
    }

    private void onPressRightBumper() {
        Robot.arms.disablePID();
        Robot.arms.setTop(-.01);
        Robot.arms.setBottom(-.01);
    }

    public void onPressTrigger() {
        Robot.arms.enablePID();
    }

    private void onPressRightBumperRelease() {
        moveToSetPoint(Constants.NEUTRAL_POSITION);
    }

    private static void moveToSetPoint(Translation2d midRungPosition) {
        Robot.arms.enablePID();
        Robot.arms.movePID();
        Robot.arms.moveToSetRotation(midRungPosition);
    }

    private void onPressDisablePidButton() {
        Robot.arms.disablePID();
    }
    private void onPressEnablePidButton() {
        Robot.arms.enablePID();
    }

    @Override
    public void execute() {
        if (!Robot.arms.isPidEnabled()) {
            manualControl();
        }
        Robot.arms.debug();

    }

    public void manualControl() {
        boolean armBoundsChecker = Robot.m_shuffleboard.armsBoundChecker();
        if (!armBoundsChecker ||
            armMath.inBounds(
                armMath.getPoint(
                    ArmSubsystem.getInstance().getTopRotation() + xbox.getRightY()*0.05,
                    ArmSubsystem.getInstance().getBottomRotation() + xbox.getLeftY()*0.05)
            )
        ) {
            if (xbox.getLeftY() != 0) {
                //Robot.arms.setTop(getTopArmSpeed() * (xbox.getLeftY() > 0 ? 1 : -1));
            }
            if (xbox.getRightY() != 0) {
                //Robot.arms.setBottom(getBottomArmSpeed() * (xbox.getRightY() > 0 ? 1 : -1));
            }
        }
    }

    // TODO: map this to a button?
    public void emergencyStop() {
        Robot.arms.emergencyStop();
    }

    public void reinitController(int port) {
        xbox = new XboxControl(port);
        this.port = port;
        System.out.println("Changed Controller Port To " + port);
    }

    private double getTopArmSpeed() {
        return Robot.m_shuffleboard.getManualTopArmSpeed();
    }

    private double getBottomArmSpeed() {
        return Robot.m_shuffleboard.getManualBottomArmSpeed();
    }}
