package supermetroid;

import java.util.function.Function;

public enum Count {
  E_TANKS(0, s -> (s.maxHealth - 99) / 100),
  RESERVE_TANKS(1, s -> s.maxReserveHealth / 100),
  MISSILES(2, s -> (int) s.maxMissiles),
  SUPER_MISSILES(3, s -> (int) s.maxSuperMissiles),
  POWER_BOMBS(4, s -> (int) s.maxPowerBombs);

  public int row = 4;
  public int col;
  private Function<State, Integer> count;
  Count(int col, Function<State, Integer> count) {
    this.col = col;
    this.count = count;
  }

  public int getCount(State state) {
    return count.apply(state);
  }
}
