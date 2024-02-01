package supermetroid.memory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class NetworkReader extends MemoryReader {
  private Socket socket;
  private InputStream reader;
  private OutputStream writer;
  private byte[] singleByte = new byte[1];
  private byte[] twoBytes = new byte[2];

  public NetworkReader() {
    try {
      socket = new Socket();
      socket.connect(new InetSocketAddress("localhost", 48879));

      writer = socket.getOutputStream();
      reader = socket.getInputStream();
    } catch(Exception e) {
      System.out.println("Error connecting to the snes9x port.");
      e.printStackTrace();
    }
  }

  @Override
  public byte readByte(int offset) {
    return readBytes(offset, singleByte)[0];
  }

  @Override
  public byte[] readBytes(int offset, byte[] bytes) {
    var command = String.format("CORE_READ WRAM;$%x;%d\n", offset, bytes.length).getBytes(StandardCharsets.US_ASCII);
    try {
      writer.write(command);
      reader.readNBytes(5);
      for (var i = 0; i < bytes.length; i++) {
        bytes[i] = (byte) (reader.read() & 255);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return bytes;
  }

  @Override
  public int readShort(int offset) {
    readBytes(offset, twoBytes);
    return ((twoBytes[1] & 255) << 8) + (twoBytes[0] & 255);
  }

  @Override
  public void close() {
    try {
      reader.close();
      writer.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        socket.close();
      } catch (Exception e) {

      }
    }
  }

  public void printAscii(String command) {
    try {
      writer.write((command + "\n").getBytes(StandardCharsets.US_ASCII));

      StringBuilder b = new StringBuilder();
      while (b.lastIndexOf("\n\n") == -1) {
        b.append((char) reader.read());
      }
      System.out.println(b);
      System.out.println();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void printBinary(String command) {
    try {
      writer.write((command + "\n").getBytes(StandardCharsets.US_ASCII));

      // First byte empty;
      reader.read();
      byte[] rawLength = reader.readNBytes(4);
      int length = (rawLength[0] & 255);
      length = (length << 8) + (rawLength[1] & 255);
      length = (length << 8) + (rawLength[2] & 255);
      length = (length << 8) + (rawLength[3] & 255);
      System.out.print("[" + length + "]");

      byte[] data = reader.readNBytes(length);

      for (byte d: data) {
        System.out.print(" " + Integer.toBinaryString(d & 255));
      }
      System.out.println();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
