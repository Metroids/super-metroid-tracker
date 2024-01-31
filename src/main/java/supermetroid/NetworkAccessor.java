package supermetroid;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class NetworkAccessor {
  private Socket socket;
  private InputStream reader;
  private OutputStream writer;

  public NetworkAccessor() {
    /*
    NetworkAccessor a = new NetworkAccessor();
    a.printAscii("GAME_INFO");
    a.printBinary("CORE_READ WRAM;$998;2");
    a.close();
    */
    try {
      socket = new Socket();
      socket.connect(new InetSocketAddress("localhost", 48879));

      writer = socket.getOutputStream();
      reader = socket.getInputStream();
    } catch(Exception e) {
      e.printStackTrace();
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

  public void close() {
    try {
      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
