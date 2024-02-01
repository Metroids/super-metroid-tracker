package supermetroid;

import supermetroid.memory.MemoryReader;
import supermetroid.memory.Snes9x;
import supermetroid.memory.WindowsProcessReader;

public class TrackerThread implements Runnable {
    static int POLL_TIME_MILLISECONDS = 1000;
    private MajorTracker majorTracker;
    private MinorTracker minorTracker;
    private MemoryReader memoryReader;

    public TrackerThread(MemoryReader memoryReader, MajorTracker majorTracker, MinorTracker minorTracker) {
        this.memoryReader = memoryReader;
        this.majorTracker = majorTracker;
        this.minorTracker = minorTracker;
    }

    @Override
    public void run() {
        StateReader reader = new StateReader(memoryReader);
        State current = new State();
        State next = new State();
        State swap;

        reader.readState(current);
        boolean first = true;
        while (true) {
            if (majorTracker.reset) {
                majorTracker.reset = false;
                current = new State();
                next = new State();
                next.update(current, majorTracker, minorTracker, true, false);
            }

            reader.readState(next);
            boolean normalGameplay = (next.game == 0x08);
            boolean zebesOnFire = (next.events & 0x40) > 0;

            // Only change the state if we're in normal gameplay.
            if (normalGameplay) {
                next.update(current, majorTracker, minorTracker, first, zebesOnFire);

                swap = current;
                current = next;
                next = swap;
                first = false;
            }

            try {
                Thread.sleep(POLL_TIME_MILLISECONDS);
            } catch(Exception e) {
            }
        }
    }
}