package supermetroid.memory;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public class WindowsProcessReader extends MemoryReader {
  private final Snes9x snes9x;
  private final int BYTE_READ_SIZE = 1;
  private final Memory BYTE_MEMORY = new Memory(BYTE_READ_SIZE);
  private final int USHORT_READ_SIZE = 2;
  private final Memory USHORT_MEMORY = new Memory(USHORT_READ_SIZE);
  private final IntByReference READ = new IntByReference();

  public WindowsProcessReader() {
    snes9x = Snes9x.get();
  }

  @Override
  public byte readByte(int offset) {
    Snes9x.kernel32.ReadProcessMemory(snes9x.handle, Pointer.createConstant(snes9x.baseAddress + offset), BYTE_MEMORY, BYTE_READ_SIZE, READ);
    return BYTE_MEMORY.getByte(0);
  }

  @Override
  public int readShort(int offset) {
    Snes9x.kernel32.ReadProcessMemory(snes9x.handle, Pointer.createConstant(snes9x.baseAddress + offset), USHORT_MEMORY, USHORT_READ_SIZE, READ);
    return Short.toUnsignedInt(USHORT_MEMORY.getShort(0));
  }
}
