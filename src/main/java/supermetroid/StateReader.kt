package supermetroid

import supermetroid.memory.MemoryReader

class StateReader(private val reader: MemoryReader) {
    private val equipment = IntArray(6)
    private val bosses = IntArray(4)

    fun readState(state: State) {
        reader.readBytes(0x09A4, equipment)
        state.equipmentA = equipment[0]
        state.equipmentB = equipment[1]
        state.beams = equipment[4]
        state.charge = equipment[5]

        reader.readBytes(0xD829, bosses)
        state.brinstarBosses = bosses[0]
        state.norfairBosses = bosses[1]
        state.wreckedShipBosses = bosses[2]
        state.maridiaBosses = bosses[3]

        state.maxHealth = reader.readShort(0x09C4)
        state.maxReserveHealth = reader.readShort(0x09D4)
        state.maxMissiles = reader.readShort(0x09C8)
        state.maxSuperMissiles = reader.readShort(0x09CC)
        state.maxPowerBombs = reader.readShort(0x09D0)

        state.game = reader.readByte(0x0998)
        state.events = reader.readByte(0xD821)
        state.objectives = reader.readBytes(0xD834, state.objectives)
    }
}
