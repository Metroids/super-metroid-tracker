package supermetroid;

import supermetroid.memory.MemoryReader;
import supermetroid.memory.Snes9x;

public class StateReader {
  private MemoryReader reader;

  public StateReader(MemoryReader reader) {
    this.reader = reader;
  }

  private byte[] equipment = new byte[6];
  private byte[] bosses = new byte[4];
  public void readState(State state) {
    reader.readBytes(0x09A4, equipment);
    state.equipmentA = equipment[0];
    state.equipmentB = equipment[1];
    state.beams = equipment[4];
    state.charge = equipment[5];

    reader.readBytes(0xD829, bosses);
    state.brinstarBosses = bosses[0];
    state.norfairBosses = bosses[1];
    state.wreckedShipBosses = bosses[2];
    state.maridiaBosses = bosses[3];

    state.maxHealth = reader.readShort(0x09C4);
    state.maxReserveHealth = reader.readShort(0x09D4);
    state.maxMissiles = reader.readShort(0x09C8);
    state.maxSuperMissiles = reader.readShort(0x09CC);
    state.maxPowerBombs = reader.readShort(0x09D0);

    state.game = reader.readByte(0x0998);
    state.events = reader.readByte(0xD821);
    state.objectives = reader.readBytes(0xD834, state.objectives);
  }
}
