package frc.robot.field;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import frc.robot.Vision.TagPositions;

import javax.swing.*;
import java.awt.*;

import static frc.robot.field.Constants.CONE_POLE_DIAMETER;
import static frc.robot.field.Constants.ZONE3_BIN3_TOP_Y;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class FieldFrame extends JPanel {

    private final int height;
    private final int width;

    public FieldFrame() {
        setLayout(null);
        setBackground(Color.GRAY);
        height = toGraph(ZONE3_BIN3_TOP_Y * 2);
        width = toGraph(Field.MIDPOINT_X * 1.5);
        setSize(width, height);
        setBackground(Color.WHITE);

        addAprilTags(1, TagPositions.APRIL_TAG_1, TagPositions.APRIL_TAG_2, TagPositions.APRIL_TAG_3, TagPositions.APRIL_TAG_4);
        addAprilTags(5, TagPositions.APRIL_TAG_5, TagPositions.APRIL_TAG_6, TagPositions.APRIL_TAG_7, TagPositions.APRIL_TAG_8);

        addZone(Field.ZONE_8, 100);
        addZone(Field.ZONE_7, 200);
        addZone(Field.ZONE_6, 50);
        addZone(Field.ZONE_1, 100);
        addZone(Field.ZONE_2, 200);
        addZone(Field.ZONE_3, 50);

        addFieldArea("CS", new Color(200, 225, 155), Field.CHARGING_STATION_ON_TOP);
        addFieldArea("", new Color(155, 125, 200), Field.CHARGING_STATION_ALL);

        addFieldArea("CS", new Color(200, 225, 155), Field.CHARGING_STATION_OTHER_ON_TOP);
        addFieldArea("", new Color(155, 125, 200), Field.CHARGING_STATION_OTHER_ALL);

        JLabel centerLine = new JLabel();
        centerLine.setBackground(Color.BLACK);
        centerLine.setOpaque(true);
        centerLine.setBounds(new Rectangle(toGraph(Field.MIDPOINT_X - 1), 0, 3, height));
        add(centerLine);

        addItems(6, Field.ItemInlineWithZone6, Field.ItemBelowCenterOfZone7, Field.ItemAboveCenterOfZone7, Field.ItemInlineWithZone8);
        addItems(1, Field.ItemInlineWithZone1, Field.ItemBelowCenterOfZone2, Field.ItemAboveCenterOfZone2, Field.ItemInLineWithZone3);

        addStartPoses(1, Field.STARTING_POSE_1, Field.STARTING_POSE_2, Field.STARTING_POSE_3);
        addStartPoses(6, Field.STARTING_POSE_6, Field.STARTING_POSE_7, Field.STARTING_POSE_8);
    }

    private void addZone(Zone zone, int baseValue) {
        addPoles(zone.poleTopShelfAboveCenter, zone.poleMidShelfAboveCenter, zone.poleTopShelfBelowCenter, zone.poleMidShelfBelowCenter);

        addFieldArea("TSB", new Color(155, 155, baseValue), zone.topShelfBelowCenter);
        addFieldArea("TSM", new Color(155, 155, baseValue), zone.topShelfMid);
        addFieldArea("TSU", new Color(155, 155, baseValue), zone.topShelfAboveCenter);

        addFieldArea("MSB", new Color(baseValue, 155, 155), zone.midShelfBelowCenter);
        addFieldArea("MSM", new Color(baseValue, 155, 155), zone.midShelfMid);
        addFieldArea("MSU", new Color(baseValue, 155, 155), zone.midShelfAboveCenter);

        addFieldArea("LSB", new Color(155, baseValue, 155), zone.bottomShelfBelowCenter);
        addFieldArea("LSM", new Color(155, baseValue, 155), zone.bottomShelfMid);
        addFieldArea("LSU", new Color(155, baseValue, 155), zone.bottomShelfAboveCenter);
    }

    private void addFieldArea(String text, Color color, Field_Area area) {
        addLabelToGraph(
            text,
            area.getBottomLeft().getX(),
            area.getTopLeft().getY(),
            toGraph(area.getWidth()),
            toGraph(area.getHeight()),
            color
        );
    }

    private int toGraphY(double area) {
        return height - toGraph(area) - 100;
    }

    private void addItems(int firstNumber, Translation2d ... items) {
        for (Translation2d item : items) {
            addLabelToGraph(
                Integer.toString(firstNumber++),
                item.getX() - 2,
                item.getY() - 2,
                toGraph(4),
                toGraph(4),
                Color.lightGray
            );
        }
    }

    private void addStartPoses(int firstNumber, Pose2d ... poses) {
        var width = 26.0;
        var height = 20.0;
        for (Pose2d pose : poses) {
            boolean facingRight = pose.getRotation().getDegrees() == 0;
            var delta = facingRight ? -width : 0;
            var text = "SP" + firstNumber++;
            if (facingRight) {
                text += " >";
            } else {
                text = "< " + text;
            }
            addLabelToGraph(
                text,
                pose.getX() + delta,
                pose.getY() + height/2,
                toGraph(width),
                toGraph(height),
                Color.lightGray
            );
        }
    }

    private void addAprilTags(int firstNumber, Pose3d ... poses) {
        var width = 11;
        var height = 8.5;
        for (Pose3d pose : poses) {
            boolean facingRight = pose.getRotation().getZ() == 0;
            var delta = facingRight ? -width : 0;
            var text = "" + firstNumber++;
            addLabelToGraph(
                text,
                pose.getX() + delta,
                pose.getY() + height/2,
                toGraph(width),
                toGraph(height),
                Color.gray
            );
        }
    }

    private void addPoles(Translation3d ... poles) {
        var width = CONE_POLE_DIAMETER;
        var height = width;
        for (Translation3d pole : poles) {
            addLabelToGraph("P", pole.getX() - width / 2.0, pole.getY() - height / 2.0, 4, 4, Color.green);
        }
    }

    private static int toGraph(double d) {
        return (int) d * 2;
    }

    private JLabel addLabelToGraph(String text, double x, double y, int width, int height, Color lightGray) {
        JLabel label = new JLabel(text);
        label.setBounds(
            new Rectangle(
                toGraph(x),
                toGraphY(y),
                width,
                height
            )
        );
        label.setBorder(BorderFactory.createLineBorder(Color.black));
        label.setBackground(lightGray);
        label.setOpaque(true);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);
        return label;
    }

    public static void main(String[] args) {
        JFrame app = new JFrame();
        app.setDefaultCloseOperation(EXIT_ON_CLOSE);
        app.setSize(toGraph(Field.MIDPOINT_X * 2), toGraph(ZONE3_BIN3_TOP_Y * 2));
        app.setLocation(200, 200);

        var panel = new FieldFrame();
        app.setContentPane(panel);
        panel.setLayout(null);
        app.setLayout(null);
        app.setVisible(true);
    }
}
