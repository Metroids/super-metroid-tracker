package supermetroid.memory

import com.sun.jna.Memory
import com.sun.jna.Pointer
import com.sun.jna.ptr.IntByReference

class WindowsProcessReader : MemoryReader() {
    private val snes9x: Snes9x = Snes9x.get()
    private val read = IntByReference()

    private val byteSize = 1
    private val byteMemory = Memory(byteSize.toLong())
    override fun readByte(offset: Int): Int {
        Snes9x.kernel32.ReadProcessMemory(
            snes9x.handle,
            Pointer.createConstant(snes9x.baseAddress + offset),
            byteMemory,
            byteSize,
            read
        )
        return byteMemory.getByte(0).toInt() and 255
    }

    private val shortSize = 2
    private val shortMemory = Memory(shortSize.toLong())
    override fun readShort(offset: Int): Int {
        Snes9x.kernel32.ReadProcessMemory(
            snes9x.handle,
            Pointer.createConstant(snes9x.baseAddress + offset),
            shortMemory,
            shortSize,
            read
        )
        return java.lang.Short.toUnsignedInt(shortMemory.getShort(0))
    }
}
