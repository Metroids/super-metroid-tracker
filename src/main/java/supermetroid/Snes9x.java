package supermetroid;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.*;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.W32APIOptions;

public class Snes9x {
  public WinNT.HANDLE handle;
  public Psapi.MODULEINFO module;
  public Pointer pointer;
  public boolean sixtyfourBit;
  public long baseAddress;

  static int ALL_PERMISSIONS = 0x001F0FFF;
  static Kernel32 kernel32 = Native.load(Kernel32.class, W32APIOptions.UNICODE_OPTIONS);

  public static Snes9x get() {
    Snes9x snes9x = new Snes9x();

    Tlhelp32.PROCESSENTRY32.ByReference processEntry = new Tlhelp32.PROCESSENTRY32.ByReference();

    WinNT.HANDLE snapshot = kernel32.CreateToolhelp32Snapshot(Tlhelp32.TH32CS_SNAPPROCESS, new WinDef.DWORD());
    WinDef.DWORD pid = null;
    try  {
      while (kernel32.Process32Next(snapshot, processEntry)) {
        if (Native.toString(processEntry.szExeFile).toLowerCase().contains("snes9x")) {
          System.out.println("Found " + Native.toString(processEntry.szExeFile));
          pid = processEntry.th32ProcessID;
          break;
        }
      }
    } finally {
      kernel32.CloseHandle(snapshot);
    }

    snes9x.handle = kernel32.OpenProcess(ALL_PERMISSIONS, true, pid.intValue());

    WinDef.HMODULE[] modules = new WinDef.HMODULE[25];
    IntByReference lpcbNeededs = new IntByReference();
    Psapi.INSTANCE.EnumProcessModules(snes9x.handle, modules, 100, lpcbNeededs);

    version: for (var i = 0; i < modules.length; i++) {
      var lpmodinfo = new Psapi.MODULEINFO();

      if (modules[i] != null) {
        Psapi.INSTANCE.GetModuleInformation(snes9x.handle, modules[i], lpmodinfo, lpmodinfo.size());

        switch (lpmodinfo.SizeOfImage) {
          // snes9x 1.60 (x64)
          case 12836864:
            snes9x.sixtyfourBit = true;
            snes9x.module = lpmodinfo;
            snes9x.pointer = Pointer.createConstant(0x1408D8BE8l);
            break version;

          // snes9x 1.60
          case 9027584:
            snes9x.module = lpmodinfo;
            snes9x.pointer = Pointer.createConstant(0x94DB54l);
            break version;

          // supermetroid.Snes9x-rr 1.60 (x64)
          case 13565952:
            snes9x.sixtyfourBit = true;
            snes9x.module = lpmodinfo;
            snes9x.pointer = Pointer.createConstant(0x140925118l);
            break version;

          // supermetroid.Snes9x-rr 1.60
          case 9646080:
            snes9x.module = lpmodinfo;
            snes9x.pointer = Pointer.createConstant(0x97EE04l);
            break version;
        }
      }
    }

    var size = 8;
    Memory output = new Memory(size);
    IntByReference bytesRead = new IntByReference();
    kernel32.ReadProcessMemory(snes9x.handle, snes9x.pointer, output, size, bytesRead);
    snes9x.baseAddress = snes9x.sixtyfourBit ? output.getLong(0) : output.getInt(0);

    return snes9x;
  }
}
