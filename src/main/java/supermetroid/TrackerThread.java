package supermetroid;

public class TrackerThread implements Runnable {
    static int POLL_TIME_MILLISECONDS = 1000;
    private MajorTracker majorTracker;
    private MinorTracker minorTracker;
    public TrackerThread(MajorTracker majorTracker, MinorTracker minorTracker) {
        this.majorTracker = majorTracker;
        this.minorTracker = minorTracker;
    }

    @Override
    public void run() {
        var snes9x = Snes9x.get();

        StateReader reader = new StateReader();
        State current = new State();
        State next = new State();
        State swap;

        reader.readState(snes9x, current);
        boolean first = true;
        while (true) {
            if (majorTracker.reset) {
                majorTracker.reset = false;
                current = new State();
                next = new State();
                next.update(current, majorTracker, minorTracker, true, false);
            }

            reader.readState(snes9x, next);
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