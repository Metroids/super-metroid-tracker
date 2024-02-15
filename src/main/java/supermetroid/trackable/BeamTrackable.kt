package supermetroid.trackable

import supermetroid.State
import supermetroid.readResourceToIcon
import java.util.*
import javax.swing.ImageIcon

class BeamTrackable : Trackable() {
    private var current: String? = null
    private val beams: MutableMap<String, ImageIcon> = HashMap()

    init {
        for (combinations in 0 until 32) {
            val c = if ((combinations and 0x1) > 0) "a" else "i"
            val w = if ((combinations and 0x2) > 0) "a" else "i"
            val i = if ((combinations and 0x4) > 0) "a" else "i"
            val s = if ((combinations and 0x8) > 0) "a" else "i"
            val p = if ((combinations and 0x10) > 0) "a" else "i"

            val key = "$c$w$i$s$p"
            beams[key] = readResourceToIcon("/beams/cwisp-$key.png")
        }
    }

    override fun setIcon(state: State) {
        val c = if ((state.charge and 0x10) > 0) "a" else "i"
        val w = if ((state.beams and 0x1) > 0) "a" else "i"
        val i = if ((state.beams and 0x2) > 0) "a" else "i"
        val s = if ((state.beams and 0x4) > 0) "a" else "i"
        val p = if ((state.beams and 0x8) > 0) "a" else "i"

        val key = "$c$w$i$s$p"

        if (key != current) {
            current = key
            label.setIcon(beams[key])
        }
    }

    override fun setDefault() = label.setIcon(beams["iiiii"])
}
