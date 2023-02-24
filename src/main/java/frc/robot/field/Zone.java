package frc.robot.field;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;

import static frc.robot.field.Constants.*;

/**
 * A zone is a set of places we can score by placing cones or cubes
 */
public class Zone {
    /**
     * The top shelf is the highest shelf.  Below center is a Y value less than the center bin.
     */
    public final Field_Area topShelfBelowCenter;

    /**
     * The top shelf is the highest shelf.  Mid is the middle bin of the zone.
     */
    public final Field_Area topShelfMid;

    /**
     * The top shelf is the highest shelf.  Above center is a Y value greater than the center bin.
     */
    public final Field_Area topShelfAboveCenter;

    /**
     * The mid shelf is the shelf below the higher shelf.  Below center is a Y value less than the center bin.
     */
    public final Field_Area midShelfBelowCenter;

    /**
     * The mid shelf is the shelf below the higher shelf.  Mid is the middle bin of the zone.
     */
    public final Field_Area midShelfMid;

    /**
     * The mid shelf is the shelf below the higher shelf.  Above center is a Y value greater than the center bin.
     */
    public final Field_Area midShelfAboveCenter;

    /**
     * The bottom shelf is on the floor.  Below center is a Y value less than the center bin.
     */
    public final Field_Area bottomShelfBelowCenter;

    /**
     * The bottom shelf is on the floor.  Mid is the middle bin of the zone.
     */
    public final Field_Area bottomShelfMid;

    /**
     * The bottom shelf is on the floor.  Above center is a Y value greater than the center bin.
     */
    public final Field_Area bottomShelfAboveCenter;

    /**
     * The taller pole below the Y value in the center bin (closer to origin)
     */
    public final Translation3d poleTopShelfBelowCenter;

    /**
     * The taller pole above the Y value in the center bin (further away from origin)
     */
    public final Translation3d poleTopShelfAboveCenter;

    /**
     * The shorter pole below the Y value in the center bin (closer to origin)
     */
    public final Translation3d poleMidShelfBelowCenter;

    /**
     * The shorter pole above the Y value in the center bin (further away from origin)
     */
    public final Translation3d poleMidShelfAboveCenter;

    public Zone(
            Translation2d startingPoint,
            Translation2d topShelfBelowCenterUr,
            Translation2d topShelfMidUr,
            Translation2d topShelfAboveCenterUr,
            Translation2d midShelfBelowCenterUr,
            Translation2d midShelfMidUr,
            Translation2d midShelfAboveCenterUr,
            Translation2d bottomShelfBelowCenterUr,
            Translation2d bottomShelfMidUr,
            Translation2d bottomShelfAboveCenterUr
    ) {
        this.topShelfBelowCenter = new Field_Area(startingPoint, topShelfBelowCenterUr);
        this.topShelfMid = new Field_Area(topShelfBelowCenter.getTopLeft(), topShelfMidUr);
        this.topShelfAboveCenter = new Field_Area(topShelfMid.getTopLeft(), topShelfAboveCenterUr);
        this.midShelfBelowCenter = new Field_Area(topShelfBelowCenter.getBottomRight(), midShelfBelowCenterUr);
        this.midShelfMid = new Field_Area(midShelfBelowCenter.getTopLeft(), midShelfMidUr);
        this.midShelfAboveCenter = new Field_Area(midShelfMid.getTopLeft(), midShelfAboveCenterUr);
        this.bottomShelfBelowCenter = new Field_Area(midShelfBelowCenter.getBottomRight(), bottomShelfBelowCenterUr);
        this.bottomShelfMid = new Field_Area(bottomShelfBelowCenter.getTopLeft(), bottomShelfMidUr);
        this.bottomShelfAboveCenter = new Field_Area(bottomShelfMid.getTopLeft(), bottomShelfAboveCenterUr);
        this.poleTopShelfBelowCenter =
            new Translation3d(
                bottomShelfBelowCenter.getCenterRight().getX() - CONE_HIGHER_X_OFFSET_FROM_ZONE_FRONT,
                bottomShelfBelowCenter.getTopLeft().getY() - CONE_Y_OFFSET_FROM_CENTER_WALL,
                CONE_HEIGHT_HIGHER);
        this.poleTopShelfAboveCenter =
            new Translation3d(
                poleTopShelfBelowCenter.getX(),
                bottomShelfAboveCenter.getBottomLeft().getY() + CONE_Y_OFFSET_FROM_CENTER_WALL,
                CONE_HEIGHT_HIGHER);
        this.poleMidShelfBelowCenter =
            new Translation3d(
                bottomShelfBelowCenter.getCenterRight().getX() - CONE_LOWER_X_OFFSET_FROM_ZONE_FRONT,
                poleTopShelfBelowCenter.getY(),
                CONE_HEIGHT_LOWER);
        this.poleMidShelfAboveCenter =
            new Translation3d(
                bottomShelfBelowCenter.getCenterRight().getX() - CONE_LOWER_X_OFFSET_FROM_ZONE_FRONT,
                poleTopShelfAboveCenter.getY(),
                CONE_HEIGHT_LOWER);
    }

    public Zone(
            Field_Area topShelfBelowCenter,
            Field_Area topShelfMid,
            Field_Area topShelfAboveCenter,
            Field_Area midShelfBelowCenter,
            Field_Area midShelfMid,
            Field_Area midShelfAboveCenter,
            Field_Area bottomShelfBelowCenter,
            Field_Area bottomShelfMid,
            Field_Area bottomShelfAboveCenter,
            Translation3d poleTopShelfBelowCenter,
            Translation3d poleTopShelfAboveCenter,
            Translation3d poleMidShelfBelowCenter,
            Translation3d poleMidShelfAboveCenter) {
        this.topShelfBelowCenter = topShelfBelowCenter;
        this.topShelfMid = topShelfMid;
        this.topShelfAboveCenter = topShelfAboveCenter;
        this.midShelfBelowCenter = midShelfBelowCenter;
        this.midShelfMid = midShelfMid;
        this.midShelfAboveCenter = midShelfAboveCenter;
        this.bottomShelfBelowCenter = bottomShelfBelowCenter;
        this.bottomShelfMid = bottomShelfMid;
        this.bottomShelfAboveCenter = bottomShelfAboveCenter;
        this.poleTopShelfBelowCenter = poleTopShelfBelowCenter;
        this.poleTopShelfAboveCenter = poleTopShelfAboveCenter;
        this.poleMidShelfBelowCenter = poleMidShelfBelowCenter;
        this.poleMidShelfAboveCenter = poleMidShelfAboveCenter;
    }
}
