package supermetroid

class State {
    var beams: Int = 0
    var charge = 0
    var equipmentA = 0
    var equipmentB = 0
    var brinstarBosses = 0
    var norfairBosses = 0
    var wreckedShipBosses = 0
    var maridiaBosses = 0

    var maxHealth = 0
    var maxReserveHealth = 0
    var maxMissiles = 0
    var maxSuperMissiles = 0
    var maxPowerBombs = 0

    var game = 0
    var events = 0

    var objectives = IntArray(5)

    fun update(
        current: State,
        majorTracker: MajorTracker,
        minorTracker: MinorTracker,
        first: Boolean
    ) {
        var stateChanged = first
        stateChanged = stateChanged || beams != current.beams
        stateChanged = stateChanged || charge != current.charge
        stateChanged = stateChanged || equipmentA != current.equipmentA
        stateChanged = stateChanged || equipmentB != current.equipmentB
        stateChanged = stateChanged || brinstarBosses != current.brinstarBosses
        stateChanged = stateChanged || norfairBosses != current.norfairBosses
        stateChanged = stateChanged || wreckedShipBosses != current.wreckedShipBosses
        stateChanged = stateChanged || maridiaBosses != current.maridiaBosses
        stateChanged = stateChanged || events != current.events
        for (i in objectives.indices) {
            stateChanged = stateChanged || objectives[i] != current.objectives[i]
        }

        if (maxHealth != current.maxHealth || first) updateMinor(minorTracker, Count.E_TANKS)
        if (maxReserveHealth != current.maxReserveHealth || first) updateMinor(minorTracker, Count.RESERVE_TANKS)
        if (maxMissiles != current.maxMissiles || first) updateMinor(minorTracker, Count.MISSILES)
        if (maxSuperMissiles != current.maxSuperMissiles || first) updateMinor(minorTracker, Count.SUPER_MISSILES)
        if (maxPowerBombs != current.maxPowerBombs || first) updateMinor(minorTracker, Count.POWER_BOMBS)

        if (stateChanged) {
            majorTracker.updateState(this)
        }
    }

    private fun updateMinor(minorTracker: MinorTracker, count: Count) {
        minorTracker.setIconStatus(count, count.getCount(this))
    }
}
