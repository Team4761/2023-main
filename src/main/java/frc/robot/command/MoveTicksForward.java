package frc.robot.command;

public class MoveTicksForward extends MoveCommand {
    private Distances start;
    private double ticks;

    private int count = 0;
    private double speed;

    public MoveTicksForward(double ticks, double speed) {
        super(speed, 0);

        this.ticks = ticks;
        this.speed = 2;
        start = Distances.fromPlaceholder();
    }

    @Override
    public boolean isFinished() {
        var rightNow = Distances.fromPlaceholder();
        Distances delta = getDelta(rightNow);

        var avg = delta.average();
        if (ticks - avg <= 0) {
            System.out.println("Stopping after " + ticks + " " + delta + " " + delta.average());
            return true;
        }
        return false;
    }

    private Distances getDelta(Distances rightNow) {
        var delta = new Distances(
                Math.abs(rightNow.frontLeft - start.frontLeft),
                Math.abs(rightNow.frontRight - start.frontRight),
                Math.abs(rightNow.backLeft - start.backLeft),
                Math.abs(rightNow.backRight - start.frontRight)
        );
        return delta;
    }
}
