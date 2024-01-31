package supermetroid.layout;

import supermetroid.trackable.BooleanTrackable;
import supermetroid.trackable.Trackable;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Standard {
  public Map<Point, Trackable> trackables() throws Exception {
    Map<Point, Trackable> trackables = new HashMap<>();
    trackables.put(new Point(0, 0), BooleanTrackable.CHARGE);
    trackables.put(new Point(1, 0), BooleanTrackable.ICE);
    trackables.put(new Point(2, 0), BooleanTrackable.WAVE);
    trackables.put(new Point(3, 0), BooleanTrackable.SPAZER);
    trackables.put(new Point(4, 0), BooleanTrackable.PLASMA);

    trackables.put(new Point(0, 1), BooleanTrackable.MORPH);
    trackables.put(new Point(1, 1), BooleanTrackable.VARIA);
    trackables.put(new Point(2, 1), BooleanTrackable.SPRING_BALL);
    trackables.put(new Point(3, 1), BooleanTrackable.HI_JUMP);
    trackables.put(new Point(4, 1), BooleanTrackable.SPACE_JUMP);

    trackables.put(new Point(0, 2), BooleanTrackable.BOMBS);
    trackables.put(new Point(1, 2), BooleanTrackable.GRAVITY);
    trackables.put(new Point(2, 2), BooleanTrackable.RIDLEY);
    trackables.put(new Point(3, 2), BooleanTrackable.SPEED_BOOSTER);
    trackables.put(new Point(4, 2), BooleanTrackable.SCREW_ATTACK);

    trackables.put(new Point(0, 3), BooleanTrackable.CROCOMIRE);
    trackables.put(new Point(1, 3), BooleanTrackable.KRAID);
    trackables.put(new Point(2, 3), BooleanTrackable.PHANTOON);
    trackables.put(new Point(3, 3), BooleanTrackable.DRAYGON);
    trackables.put(new Point(4, 3), BooleanTrackable.SHAKTOOL);
    return trackables;
  }
}
