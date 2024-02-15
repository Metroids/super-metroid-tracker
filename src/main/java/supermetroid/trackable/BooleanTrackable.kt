package supermetroid.trackable

import supermetroid.State
import supermetroid.readResourceToIcon
import java.util.function.Function
import javax.swing.ImageIcon

class BooleanTrackable(image: String, private val booleanMemoryReader: Function<State, Boolean>) : Trackable() {
    private var on: ImageIcon = readResourceToIcon("/on/$image.png")
    private var off: ImageIcon = readResourceToIcon("/off/$image.png")
    private var current = false

    override fun setIcon(state: State) {
        if (current != booleanMemoryReader.apply(state)) {
            current = booleanMemoryReader.apply(state)
            label.icon = if (current) on else off
        }
    }

    override fun setDefault() {
        label.icon = off
    }

    companion object {
        val CHARGE: BooleanTrackable = BooleanTrackable("charge") { state: State -> (state.charge and 0x10) > 0 }
        val ICE: BooleanTrackable = BooleanTrackable("ice") { state: State -> (state.beams and 0x2) > 0 }
        val WAVE: BooleanTrackable = BooleanTrackable("wave") { state: State -> (state.beams and 0x1) > 0 }
        val SPAZER: BooleanTrackable = BooleanTrackable("spazer") { state: State -> (state.beams and 0x4) > 0 }
        val PLASMA: BooleanTrackable = BooleanTrackable("plasma") { state: State -> (state.beams and 0x8) > 0 }

        val MORPH: BooleanTrackable = BooleanTrackable("morph") { state: State -> (state.equipmentA and 0x4) > 0 }
        val VARIA: BooleanTrackable = BooleanTrackable("varia") { state: State -> (state.equipmentA and 0x1) > 0 }
        val SPRING_BALL: BooleanTrackable = BooleanTrackable("spring-ball") { state: State -> (state.equipmentA and 0x2) > 0 }
        val HI_JUMP: BooleanTrackable = BooleanTrackable("hi-jump") { state: State -> (state.equipmentB and 0x1) > 0 }
        val SPACE_JUMP: BooleanTrackable = BooleanTrackable("space-jump") { state: State -> (state.equipmentB and 0x2) > 0 }
        val BOMBS: BooleanTrackable = BooleanTrackable("bombs") { state: State -> (state.equipmentB and 0x10) > 0 }
        val GRAVITY: BooleanTrackable = BooleanTrackable("gravity") { state: State -> (state.equipmentA and 0x20) > 0 }
        val SPEED_BOOSTER: BooleanTrackable = BooleanTrackable("speed-booster") { state: State -> (state.equipmentB and 0x20) > 0 }
        val SCREW_ATTACK: BooleanTrackable = BooleanTrackable("screw-attack") { state: State -> (state.equipmentA and 0x8) > 0 }

        val PHANTOON: BooleanTrackable = BooleanTrackable("phantoon") { state: State -> (state.wreckedShipBosses and 0x1) > 0 }
        val RIDLEY: BooleanTrackable = BooleanTrackable("ridley") { state: State -> (state.norfairBosses and 0x1) > 0 }
        val KRAID: BooleanTrackable = BooleanTrackable("kraid") { state: State -> (state.brinstarBosses and 0x1) > 0 }
        val DRAYGON: BooleanTrackable = BooleanTrackable("draygon") { state: State -> (state.maridiaBosses and 0x1) > 0 }

        val CROCOMIRE: BooleanTrackable = BooleanTrackable("crocomire") { state: State -> (state.norfairBosses and 0x2) > 0 }
        val SHAKTOOL: BooleanTrackable = BooleanTrackable("shaktool") { state: State -> (state.events and 0x20) > 0 }
    }
}
