package supermetroid;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public class StateReader {
  private final int BYTE_READ_SIZE = 1;
  private final Memory BYTE_MEMORY = new Memory(BYTE_READ_SIZE);
  private final int USHORT_READ_SIZE = 2;
  private final Memory USHORT_MEMORY = new Memory(USHORT_READ_SIZE);
  private final IntByReference READ = new IntByReference();

  public void readState(Snes9x snes9x, State state) {
    state.beams = readByte(snes9x, 0x09A8);
    state.charge = readByte(snes9x, 0x09A9);
    state.equipmentA = readByte(snes9x, 0x09A4);
    state.equipmentB = readByte(snes9x, 0x09A5);
    state.brinstarBosses = readByte(snes9x, 0xD829);
    state.norfairBosses = readByte(snes9x, 0xD82A);
    state.wreckedShipBosses = readByte(snes9x, 0xD82B);
    state.maridiaBosses = readByte(snes9x, 0xD82C);

    state.maxHealth = readShort(snes9x, 0x09C4);
    state.maxReserveHealth = readShort(snes9x, 0x09D4);
    state.maxMissiles = readShort(snes9x, 0x09C8);
    state.maxSuperMissiles = readShort(snes9x, 0x09CC);
    state.maxPowerBombs = readShort(snes9x, 0x09D0);

    state.game = readByte(snes9x, 0x0998);
    state.events = readByte(snes9x, 0xD821);

    for (int i = 0; i < 5; i++) {
      state.objectives[i] = readByte(snes9x, 0xd834 + i);
    }
  }

  public byte readByte(Snes9x snes9x, int offset) {
    Snes9x.kernel32.ReadProcessMemory(snes9x.handle, Pointer.createConstant(snes9x.baseAddress + offset), BYTE_MEMORY, BYTE_READ_SIZE, READ);
    return BYTE_MEMORY.getByte(0);
  }

  public int readShort(Snes9x snes9x, int offset) {
    Snes9x.kernel32.ReadProcessMemory(snes9x.handle, Pointer.createConstant(snes9x.baseAddress + offset), USHORT_MEMORY, USHORT_READ_SIZE, READ);
    return Short.toUnsignedInt(USHORT_MEMORY.getShort(0));
  }
}
