package supermetroid.memory

import java.io.InputStream
import java.io.OutputStream
import java.net.InetSocketAddress
import java.net.Socket
import java.nio.charset.StandardCharsets

class NetworkReader : MemoryReader() {
    private var socket: Socket = Socket()
    private var reader: InputStream
    private var writer: OutputStream
    private val singleByte = IntArray(1)
    private val twoBytes = IntArray(2)

    init {
        socket.connect(InetSocketAddress("localhost", 48879))
        writer = socket.getOutputStream()
        reader = socket.getInputStream()
    }

    override fun readByte(offset: Int) = readBytes(offset, singleByte)[0]
    override fun readBytes(offset: Int, bytes: IntArray): IntArray {
        val command = "CORE_READ WRAM;$%x;%d\n".format(offset, bytes.size)
        writer.write(command.toByteArray(StandardCharsets.US_ASCII))
        reader.readNBytes(5)

        for (i in bytes.indices) {
            bytes[i] = (reader.read() and 255)
        }

        return bytes
    }

    override fun readShort(offset: Int): Int {
        readBytes(offset, twoBytes)
        return ((twoBytes[1] and 255) shl 8) + (twoBytes[0] and 255)
    }

    override fun close() {
        try {reader.close()} catch (e: Exception) {}
        try {writer.close()} catch (e: Exception) {}
        try {socket.close()} catch (e: Exception) {}
    }

    fun print(command: String) {
        writer.write((command + "\n").toByteArray(StandardCharsets.US_ASCII))

        val b = StringBuilder()
        while (b.lastIndexOf("\n\n") == -1) {
            b.append(reader.read().toChar())
        }
        println(b)
        println()
    }
}
