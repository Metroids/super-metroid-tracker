package supermetroid.layout;

import supermetroid.ObjectiveReader;
import supermetroid.trackable.BeamTrackable;
import supermetroid.trackable.BooleanTrackable;
import supermetroid.trackable.Trackable;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class BountyBlaster {
  public static Map<Point, Trackable> trackables() throws Exception {
    Map<Point, Trackable> trackables = new HashMap<>();
    trackables.put(new Point(0, 0), new BeamTrackable());
    trackables.put(new Point(1, 0), BooleanTrackable.MORPH);
    trackables.put(new Point(2, 0), BooleanTrackable.BOMBS);
    trackables.put(new Point(3, 0), BooleanTrackable.SPRING_BALL);
    trackables.put(new Point(4, 0), BooleanTrackable.VARIA);

    trackables.put(new Point(0, 1), BooleanTrackable.HI_JUMP);
    trackables.put(new Point(1, 1), BooleanTrackable.SPEED_BOOSTER);
    trackables.put(new Point(2, 1), BooleanTrackable.SPACE_JUMP);
    trackables.put(new Point(3, 1), BooleanTrackable.SCREW_ATTACK);
    trackables.put(new Point(4, 1), BooleanTrackable.GRAVITY);

    var objectives = new ObjectiveReader();
    for (int i = 0; i < 5; i++) trackables.put(new Point(i, 2), objectives.createObjectiveTrackable(i));
    for (int i = 0; i < 3; i++) trackables.put(new Point(i + 1, 3), objectives.createObjectiveTrackable(i + 5));

    return trackables;
  }
}
