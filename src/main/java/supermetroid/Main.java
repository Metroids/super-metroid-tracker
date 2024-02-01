package supermetroid;

import supermetroid.memory.MemoryReader;
import supermetroid.memory.NetworkReader;
import supermetroid.memory.WindowsProcessReader;
import supermetroid.trackable.Trackable;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

public class Main {
  public static void main(String... args) throws Exception {
    Map<Point, Trackable> trackers = new HashMap<>();
    MemoryReader memoryReader;

    if (args.length != 2) {
      memoryReader = new WindowsProcessReader();
      trackers = Layout.standard();
    } else {
      memoryReader = ("network".equals(args[0]) ? new NetworkReader() : new WindowsProcessReader());

      if ("bounty-blaster".equals(args[1])) trackers = Layout.bountyBlaster();
      if ("objectives".equals(args[1])) trackers = Layout.objectives();
      if (trackers.isEmpty()) trackers = Layout.standard();
    }

    MajorTracker majorTracker = new MajorTracker(trackers);
    majorTracker.setLocation(10, 10);
    majorTracker.setVisible(true);
    majorTracker.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent we) {
        memoryReader.close();
      }
    });
    Rectangle bounds = majorTracker.getBounds();

    MinorTracker minorTracker = new MinorTracker();
    minorTracker.setVisible(true);
    minorTracker.setLocation(10, (int) (bounds.getY() + bounds.getHeight() + 10));

    new Thread(new TrackerThread(memoryReader, majorTracker, minorTracker)).start();
  }
}
