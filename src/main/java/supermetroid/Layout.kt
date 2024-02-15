package supermetroid

import supermetroid.trackable.BeamTrackable
import supermetroid.trackable.BooleanTrackable
import supermetroid.trackable.Trackable
import java.awt.Point

object Layout {
    private fun shortMajors() = mutableMapOf(
        Point(0, 0) to BeamTrackable(),
        Point(1, 0) to BooleanTrackable.MORPH,
        Point(2, 0) to BooleanTrackable.BOMBS,
        Point(3, 0) to BooleanTrackable.SPRING_BALL,
        Point(4, 0) to BooleanTrackable.VARIA,

        Point(0, 1) to BooleanTrackable.HI_JUMP,
        Point(1, 1) to BooleanTrackable.SPEED_BOOSTER,
        Point(2, 1) to BooleanTrackable.SPACE_JUMP,
        Point(3, 1) to BooleanTrackable.SCREW_ATTACK,
        Point(4, 1) to BooleanTrackable.GRAVITY
    )

    fun bountyBlaster(): Map<Point, Trackable> {
        val trackables = shortMajors()
        val objectives = ObjectiveReader()
        for (i in 0..4) trackables[Point(i, 2)] = objectives.createObjectiveTrackable(i)
        for (i in 0..2) trackables[Point(i + 1, 3)] = objectives.createObjectiveTrackable(i + 5)

        return trackables
    }

    fun objectives(): Map<Point, Trackable> {
        val trackables = shortMajors()
        val objectives = ObjectiveReader()

        var i = 0
        while (i < objectives.total) {
            val x = i % 5
            val y = (i / 5) + 2
            trackables[Point(x, y)] = objectives.createObjectiveTrackable(i)
            i++
        }

        return trackables
    }

    fun standard() = mutableMapOf(
        Point(0, 0) to BooleanTrackable.CHARGE,
        Point(1, 0) to BooleanTrackable.ICE,
        Point(2, 0) to BooleanTrackable.WAVE,
        Point(3, 0) to BooleanTrackable.SPAZER,
        Point(4, 0) to BooleanTrackable.PLASMA,

        Point(0, 1) to BooleanTrackable.MORPH,
        Point(1, 1) to BooleanTrackable.VARIA,
        Point(2, 1) to BooleanTrackable.SPRING_BALL,
        Point(3, 1) to BooleanTrackable.HI_JUMP,
        Point(4, 1) to BooleanTrackable.SPACE_JUMP,

        Point(0, 2) to BooleanTrackable.BOMBS,
        Point(1, 2) to BooleanTrackable.GRAVITY,
        Point(2, 2) to BooleanTrackable.RIDLEY,
        Point(3, 2) to BooleanTrackable.SPEED_BOOSTER,
        Point(4, 2) to BooleanTrackable.SCREW_ATTACK,

        Point(0, 3) to BooleanTrackable.CROCOMIRE,
        Point(1, 3) to BooleanTrackable.KRAID,
        Point(2, 3) to BooleanTrackable.PHANTOON,
        Point(3, 3) to BooleanTrackable.DRAYGON,
        Point(4, 3) to BooleanTrackable.SHAKTOOL
    )
}
