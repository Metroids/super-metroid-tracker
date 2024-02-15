package supermetroid

import java.util.function.Function

enum class Count(var col: Int, private val count: Function<State, Int>) {
    E_TANKS(0, { s: State -> (s.maxHealth - 99) / 100 }),
    RESERVE_TANKS(1, { s: State -> s.maxReserveHealth / 100 }),
    MISSILES(2, { s: State -> s.maxMissiles }),
    SUPER_MISSILES(3, { s: State -> s.maxSuperMissiles }),
    POWER_BOMBS(4, { s: State -> s.maxPowerBombs });

    var row: Int = 4

    fun getCount(state: State) = count.apply(state)
}
