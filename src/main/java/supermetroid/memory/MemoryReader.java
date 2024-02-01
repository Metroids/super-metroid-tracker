package supermetroid.memory;

public abstract class MemoryReader {
  public abstract byte readByte(int offset);
  public byte[] readBytes(int offset, byte[] bytes) {
    for (var i = 0; i < bytes.length; i++) {
      bytes[i] = readByte(offset + i);
    }
    return bytes;
  }
  public abstract int readShort(int offset);
  public void close() {}
}
