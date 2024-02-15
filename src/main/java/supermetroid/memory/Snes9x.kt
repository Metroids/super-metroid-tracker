package supermetroid.memory

import com.sun.jna.Memory
import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.platform.win32.*
import com.sun.jna.ptr.IntByReference
import com.sun.jna.win32.W32APIOptions

class Snes9x {
    var handle: WinNT.HANDLE? = null
    var module: Psapi.MODULEINFO? = null
    var pointer: Pointer? = null
    var sixtyfourBit: Boolean = false
    var baseAddress: Long = 0

    companion object {
        var ALL_PERMISSIONS: Int = 0x001F0FFF
        var kernel32: Kernel32 = Native.load(Kernel32::class.java, W32APIOptions.UNICODE_OPTIONS)

        fun get(): Snes9x {
            val snes9x = Snes9x()

            val processEntry = Tlhelp32.PROCESSENTRY32.ByReference()
            val snapshot = kernel32.CreateToolhelp32Snapshot(Tlhelp32.TH32CS_SNAPPROCESS, WinDef.DWORD())
            var pid: WinDef.DWORD? = null
            try {
                while (kernel32.Process32Next(snapshot, processEntry)) {
                    val executableName = Native.toString(processEntry.szExeFile).lowercase()
                    if (executableName.contains("snes9x")) {
                        println("Found $executableName")
                        pid = processEntry.th32ProcessID
                        break
                    }
                }
            } finally {
                kernel32.CloseHandle(snapshot)
            }

            snes9x.handle = kernel32.OpenProcess(ALL_PERMISSIONS, true, pid!!.toInt())

            val modules = arrayOfNulls<WinDef.HMODULE>(25)
            val lpcbNeededs = IntByReference()
            Psapi.INSTANCE.EnumProcessModules(snes9x.handle, modules, 100, lpcbNeededs)

            version@ for (i in modules.indices) {
                val lpmodinfo = Psapi.MODULEINFO()

                if (modules[i] != null) {
                    Psapi.INSTANCE.GetModuleInformation(snes9x.handle, modules[i], lpmodinfo, lpmodinfo.size())

                    when (lpmodinfo.SizeOfImage) {
                        12836864 -> {
                            snes9x.sixtyfourBit = true
                            snes9x.module = lpmodinfo
                            snes9x.pointer = Pointer.createConstant(0x1408D8BE8L)
                            break@version
                        }

                        9027584 -> {
                            snes9x.module = lpmodinfo
                            snes9x.pointer = Pointer.createConstant(0x94DB54L)
                            break@version
                        }

                        13565952 -> {
                            snes9x.sixtyfourBit = true
                            snes9x.module = lpmodinfo
                            snes9x.pointer = Pointer.createConstant(0x140925118L)
                            break@version
                        }

                        9646080 -> {
                            snes9x.module = lpmodinfo
                            snes9x.pointer = Pointer.createConstant(0x97EE04L)
                            break@version
                        }
                    }
                }
            }

            val size = 8
            val output = Memory(size.toLong())
            val bytesRead = IntByReference()
            kernel32.ReadProcessMemory(snes9x.handle, snes9x.pointer, output, size, bytesRead)
            snes9x.baseAddress = if (snes9x.sixtyfourBit) output.getLong(0) else output.getInt(0).toLong()
            return snes9x
        }
    }
}
