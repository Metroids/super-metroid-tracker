package supermetroid.memory

abstract class MemoryReader {
    abstract fun readByte(offset: Int): Int
    open fun readBytes(offset: Int, bytes: IntArray): IntArray {
        for (i in bytes.indices) {
            bytes[i] = readByte(offset + i)
        }
        return bytes
    }

    abstract fun readShort(offset: Int): Int
    open fun close() {}
}
