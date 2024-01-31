package supermetroid;

import supermetroid.layout.Objectives;
import supermetroid.trackable.Trackable;

import java.awt.*;
import java.util.Map;

public class Main {
  public static void main(String... args) throws Exception {
    Map<Point, Trackable> trackers = Objectives.trackables();

    boolean showMajor = (args.length == 0) || (args.length == 1 && "majors".equals(args[0]));
    MajorTracker majorTracker = new MajorTracker(trackers);
    majorTracker.setVisible(showMajor);
    majorTracker.setLocation(10, 10);
    Rectangle bounds = majorTracker.getBounds();

    boolean showMinor = (args.length == 0) || (args.length == 1 && "majors".equals(args[0]));
    MinorTracker minorTracker = new MinorTracker();
    minorTracker.setVisible(showMinor);
    minorTracker.setLocation(10, showMajor ? (int) (bounds.getY() + bounds.getHeight() + 10) : 10);

    new Thread(new TrackerThread(majorTracker, minorTracker)).start();
  }
}
