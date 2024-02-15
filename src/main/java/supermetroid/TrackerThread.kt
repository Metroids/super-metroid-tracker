package supermetroid

import supermetroid.memory.MemoryReader

class TrackerThread(
    private val memoryReader: MemoryReader,
    private val majorTracker: MajorTracker,
    private val minorTracker: MinorTracker
) : Runnable {
    private val pollTimeMilliseconds = 1000L

    override fun run() {
        val reader = StateReader(memoryReader)
        var current = State()
        var next = State()
        var swap: State

        reader.readState(current)
        var first = true
        while (true) {
            if (majorTracker.reset) {
                majorTracker.reset = false
                current = State()
                next = State()
                next.update(current, majorTracker, minorTracker, true)
            }

            reader.readState(next)
            val normalGameplay = (next.game == 0x08)
            val zebesOnFire = (next.events and 0x40) > 0

            // Only change the state if we're in normal gameplay.
            if (normalGameplay) {
                next.update(current, majorTracker, minorTracker, first)

                swap = current
                current = next
                next = swap
                first = false
            }

            try {Thread.sleep(pollTimeMilliseconds)} catch (e: Exception) {}
        }
    }
}