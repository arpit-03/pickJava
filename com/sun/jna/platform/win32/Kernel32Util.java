/*      */ package com.sun.jna.platform.win32;
/*      */ 
/*      */ import com.sun.jna.LastErrorException;
/*      */ import com.sun.jna.Memory;
/*      */ import com.sun.jna.Native;
/*      */ import com.sun.jna.Pointer;
/*      */ import com.sun.jna.Structure;
/*      */ import com.sun.jna.ptr.IntByReference;
/*      */ import com.sun.jna.ptr.PointerByReference;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.nio.ByteOrder;
/*      */ import java.util.ArrayList;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.TreeMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class Kernel32Util
/*      */   implements WinDef
/*      */ {
/*      */   public static final String VOLUME_GUID_PATH_PREFIX = "\\\\?\\Volume{";
/*      */   public static final String VOLUME_GUID_PATH_SUFFIX = "}\\";
/*      */   
/*      */   public static String getComputerName() {
/*   60 */     char[] buffer = new char[WinBase.MAX_COMPUTERNAME_LENGTH + 1];
/*   61 */     IntByReference lpnSize = new IntByReference(buffer.length);
/*   62 */     if (!Kernel32.INSTANCE.GetComputerName(buffer, lpnSize)) {
/*   63 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*   65 */     return Native.toString(buffer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void freeLocalMemory(Pointer ptr) {
/*   75 */     Pointer res = Kernel32.INSTANCE.LocalFree(ptr);
/*   76 */     if (res != null) {
/*   77 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void freeGlobalMemory(Pointer ptr) {
/*   88 */     Pointer res = Kernel32.INSTANCE.GlobalFree(ptr);
/*   89 */     if (res != null) {
/*   90 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void closeHandleRefs(WinNT.HANDLEByReference... refs) {
/*  105 */     Win32Exception err = null;
/*  106 */     for (WinNT.HANDLEByReference r : refs) {
/*      */       try {
/*  108 */         closeHandleRef(r);
/*  109 */       } catch (Win32Exception e) {
/*  110 */         if (err == null) {
/*  111 */           err = e;
/*      */         } else {
/*  113 */           err.addSuppressedReflected((Throwable)e);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  118 */     if (err != null) {
/*  119 */       throw err;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void closeHandleRef(WinNT.HANDLEByReference ref) {
/*  129 */     closeHandle((ref == null) ? null : ref.getValue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void closeHandles(WinNT.HANDLE... handles) {
/*  143 */     Win32Exception err = null;
/*  144 */     for (WinNT.HANDLE h : handles) {
/*      */       try {
/*  146 */         closeHandle(h);
/*  147 */       } catch (Win32Exception e) {
/*  148 */         if (err == null) {
/*  149 */           err = e;
/*      */         } else {
/*  151 */           err.addSuppressedReflected((Throwable)e);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  156 */     if (err != null) {
/*  157 */       throw err;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void closeHandle(WinNT.HANDLE h) {
/*  169 */     if (h == null) {
/*      */       return;
/*      */     }
/*      */     
/*  173 */     if (!Kernel32.INSTANCE.CloseHandle(h)) {
/*  174 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String formatMessage(int code) {
/*  186 */     PointerByReference buffer = new PointerByReference();
/*  187 */     int nLen = Kernel32.INSTANCE.FormatMessage(4864, null, code, 0, buffer, 0, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  195 */     if (nLen == 0) {
/*  196 */       throw new LastErrorException(Native.getLastError());
/*      */     }
/*      */     
/*  199 */     Pointer ptr = buffer.getValue();
/*      */     try {
/*  201 */       String s = ptr.getWideString(0L);
/*  202 */       return s.trim();
/*      */     } finally {
/*  204 */       freeLocalMemory(ptr);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String formatMessage(WinNT.HRESULT code) {
/*  216 */     return formatMessage(code.intValue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static String formatMessageFromHR(WinNT.HRESULT code) {
/*  226 */     return formatMessage(code.intValue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String formatMessageFromLastErrorCode(int code) {
/*  237 */     return formatMessageFromHR(W32Errors.HRESULT_FROM_WIN32(code));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getLastErrorMessage() {
/*  245 */     return formatMessageFromLastErrorCode(Kernel32.INSTANCE
/*  246 */         .GetLastError());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getTempPath() {
/*  255 */     WinDef.DWORD nBufferLength = new WinDef.DWORD(260L);
/*  256 */     char[] buffer = new char[nBufferLength.intValue()];
/*  257 */     if (Kernel32.INSTANCE.GetTempPath(nBufferLength, buffer).intValue() == 0) {
/*  258 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*  260 */     return Native.toString(buffer);
/*      */   }
/*      */   
/*      */   public static void deleteFile(String filename) {
/*  264 */     if (!Kernel32.INSTANCE.DeleteFile(filename)) {
/*  265 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static List<String> getLogicalDriveStrings() {
/*  275 */     WinDef.DWORD dwSize = Kernel32.INSTANCE.GetLogicalDriveStrings(new WinDef.DWORD(0L), null);
/*  276 */     if (dwSize.intValue() <= 0) {
/*  277 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */     
/*  280 */     char[] buf = new char[dwSize.intValue()];
/*  281 */     dwSize = Kernel32.INSTANCE.GetLogicalDriveStrings(dwSize, buf);
/*  282 */     int bufSize = dwSize.intValue();
/*  283 */     if (bufSize <= 0) {
/*  284 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */     
/*  287 */     return Native.toStringList(buf, 0, bufSize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getFileAttributes(String fileName) {
/*  298 */     int fileAttributes = Kernel32.INSTANCE.GetFileAttributes(fileName);
/*  299 */     if (fileAttributes == -1) {
/*  300 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*  302 */     return fileAttributes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getFileType(String fileName) throws FileNotFoundException {
/*  312 */     File f = new File(fileName);
/*  313 */     if (!f.exists()) {
/*  314 */       throw new FileNotFoundException(fileName);
/*      */     }
/*      */     
/*  317 */     WinNT.HANDLE hFile = null;
/*  318 */     Win32Exception err = null; try {
/*      */       int rc;
/*  320 */       hFile = Kernel32.INSTANCE.CreateFile(fileName, -2147483648, 1, new WinBase.SECURITY_ATTRIBUTES(), 3, 128, (new WinNT.HANDLEByReference())
/*      */ 
/*      */           
/*  323 */           .getValue());
/*      */       
/*  325 */       if (WinBase.INVALID_HANDLE_VALUE.equals(hFile)) {
/*  326 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */       }
/*      */       
/*  329 */       int type = Kernel32.INSTANCE.GetFileType(hFile);
/*  330 */       switch (type) {
/*      */         case 0:
/*  332 */           rc = Kernel32.INSTANCE.GetLastError();
/*  333 */           switch (rc) {
/*      */             case 0:
/*      */               break;
/*      */           } 
/*  337 */           throw new Win32Exception(rc);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  342 */       return type;
/*      */     }
/*  344 */     catch (Win32Exception e) {
/*  345 */       err = e;
/*  346 */       throw err;
/*      */     } finally {
/*      */       try {
/*  349 */         closeHandle(hFile);
/*  350 */       } catch (Win32Exception e) {
/*  351 */         if (err == null) {
/*  352 */           err = e;
/*      */         } else {
/*  354 */           err.addSuppressedReflected((Throwable)e);
/*      */         } 
/*      */       } 
/*      */       
/*  358 */       if (err != null) {
/*  359 */         throw err;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getDriveType(String rootName) {
/*  369 */     return Kernel32.INSTANCE.GetDriveType(rootName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getEnvironmentVariable(String name) {
/*  381 */     int size = Kernel32.INSTANCE.GetEnvironmentVariable(name, null, 0);
/*  382 */     if (size == 0)
/*  383 */       return null; 
/*  384 */     if (size < 0) {
/*  385 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */     
/*  388 */     char[] buffer = new char[size];
/*  389 */     size = Kernel32.INSTANCE.GetEnvironmentVariable(name, buffer, buffer.length);
/*      */     
/*  391 */     if (size <= 0) {
/*  392 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*  394 */     return Native.toString(buffer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Map<String, String> getEnvironmentVariables() {
/*  406 */     Pointer lpszEnvironmentBlock = Kernel32.INSTANCE.GetEnvironmentStrings();
/*  407 */     if (lpszEnvironmentBlock == null) {
/*  408 */       throw new LastErrorException(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */     
/*      */     try {
/*  412 */       return getEnvironmentVariables(lpszEnvironmentBlock, 0L);
/*      */     } finally {
/*  414 */       if (!Kernel32.INSTANCE.FreeEnvironmentStrings(lpszEnvironmentBlock)) {
/*  415 */         throw new LastErrorException(Kernel32.INSTANCE.GetLastError());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Map<String, String> getEnvironmentVariables(Pointer lpszEnvironmentBlock, long offset) {
/*  432 */     if (lpszEnvironmentBlock == null) {
/*  433 */       return null;
/*      */     }
/*      */     
/*  436 */     Map<String, String> vars = new TreeMap<String, String>();
/*  437 */     boolean asWideChars = isWideCharEnvironmentStringBlock(lpszEnvironmentBlock, offset);
/*  438 */     long stepFactor = asWideChars ? 2L : 1L;
/*  439 */     long curOffset = offset; while (true) {
/*  440 */       String nvp = readEnvironmentStringBlockEntry(lpszEnvironmentBlock, curOffset, asWideChars);
/*  441 */       int len = nvp.length();
/*  442 */       if (len == 0) {
/*      */         break;
/*      */       }
/*      */       
/*  446 */       int pos = nvp.indexOf('=');
/*  447 */       if (pos < 0) {
/*  448 */         throw new IllegalArgumentException("Missing variable value separator in " + nvp);
/*      */       }
/*      */       
/*  451 */       String name = nvp.substring(0, pos), value = nvp.substring(pos + 1);
/*  452 */       vars.put(name, value);
/*      */       
/*  454 */       curOffset += (len + 1) * stepFactor;
/*      */     } 
/*      */     
/*  457 */     return vars;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String readEnvironmentStringBlockEntry(Pointer lpszEnvironmentBlock, long offset, boolean asWideChars) {
/*  473 */     long endOffset = findEnvironmentStringBlockEntryEnd(lpszEnvironmentBlock, offset, asWideChars);
/*  474 */     int dataLen = (int)(endOffset - offset);
/*  475 */     if (dataLen == 0) {
/*  476 */       return "";
/*      */     }
/*      */     
/*  479 */     int charsLen = asWideChars ? (dataLen / 2) : dataLen;
/*  480 */     char[] chars = new char[charsLen];
/*  481 */     long curOffset = offset, stepSize = asWideChars ? 2L : 1L;
/*  482 */     ByteOrder byteOrder = ByteOrder.nativeOrder();
/*  483 */     for (int index = 0; index < chars.length; index++, curOffset += stepSize) {
/*  484 */       byte b = lpszEnvironmentBlock.getByte(curOffset);
/*  485 */       if (asWideChars) {
/*  486 */         byte x = lpszEnvironmentBlock.getByte(curOffset + 1L);
/*  487 */         if (ByteOrder.LITTLE_ENDIAN.equals(byteOrder)) {
/*  488 */           chars[index] = (char)(x << 8 & 0xFF00 | b & 0xFF);
/*      */         } else {
/*  490 */           chars[index] = (char)(b << 8 & 0xFF00 | x & 0xFF);
/*      */         } 
/*      */       } else {
/*  493 */         chars[index] = (char)(b & 0xFF);
/*      */       } 
/*      */     } 
/*      */     
/*  497 */     return new String(chars);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long findEnvironmentStringBlockEntryEnd(Pointer lpszEnvironmentBlock, long offset, boolean asWideChars) {
/*      */     long curOffset;
/*      */     long stepSize;
/*  513 */     for (curOffset = offset, stepSize = asWideChars ? 2L : 1L;; curOffset += stepSize) {
/*  514 */       byte b = lpszEnvironmentBlock.getByte(curOffset);
/*  515 */       if (b == 0) {
/*  516 */         return curOffset;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isWideCharEnvironmentStringBlock(Pointer lpszEnvironmentBlock, long offset) {
/*  549 */     byte b0 = lpszEnvironmentBlock.getByte(offset);
/*  550 */     byte b1 = lpszEnvironmentBlock.getByte(offset + 1L);
/*  551 */     ByteOrder byteOrder = ByteOrder.nativeOrder();
/*  552 */     if (ByteOrder.LITTLE_ENDIAN.equals(byteOrder)) {
/*  553 */       return isWideCharEnvironmentStringBlock(b1);
/*      */     }
/*  555 */     return isWideCharEnvironmentStringBlock(b0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isWideCharEnvironmentStringBlock(byte charsetIndicator) {
/*  561 */     if (charsetIndicator != 0) {
/*  562 */       return false;
/*      */     }
/*  564 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int getPrivateProfileInt(String appName, String keyName, int defaultValue, String fileName) {
/*  590 */     return Kernel32.INSTANCE.GetPrivateProfileInt(appName, keyName, defaultValue, fileName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String getPrivateProfileString(String lpAppName, String lpKeyName, String lpDefault, String lpFileName) {
/*  645 */     char[] buffer = new char[1024];
/*  646 */     Kernel32.INSTANCE.GetPrivateProfileString(lpAppName, lpKeyName, lpDefault, buffer, new WinDef.DWORD(buffer.length), lpFileName);
/*      */     
/*  648 */     return Native.toString(buffer);
/*      */   }
/*      */ 
/*      */   
/*      */   public static final void writePrivateProfileString(String appName, String keyName, String string, String fileName) {
/*  653 */     if (!Kernel32.INSTANCE.WritePrivateProfileString(appName, keyName, string, fileName))
/*      */     {
/*  655 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final WinNT.SYSTEM_LOGICAL_PROCESSOR_INFORMATION[] getLogicalProcessorInformation() {
/*      */     Memory memory;
/*  666 */     int sizePerStruct = (new WinNT.SYSTEM_LOGICAL_PROCESSOR_INFORMATION()).size();
/*  667 */     WinDef.DWORDByReference bufferSize = new WinDef.DWORDByReference(new WinDef.DWORD(sizePerStruct));
/*      */ 
/*      */     
/*      */     while (true) {
/*  671 */       memory = new Memory(bufferSize.getValue().intValue());
/*  672 */       if (!Kernel32.INSTANCE.GetLogicalProcessorInformation((Pointer)memory, bufferSize)) {
/*      */         
/*  674 */         int err = Kernel32.INSTANCE.GetLastError();
/*  675 */         if (err != 122)
/*  676 */           throw new Win32Exception(err); 
/*      */         continue;
/*      */       } 
/*      */       break;
/*      */     } 
/*  681 */     WinNT.SYSTEM_LOGICAL_PROCESSOR_INFORMATION firstInformation = new WinNT.SYSTEM_LOGICAL_PROCESSOR_INFORMATION((Pointer)memory);
/*      */     
/*  683 */     int returnedStructCount = bufferSize.getValue().intValue() / sizePerStruct;
/*      */     
/*  685 */     return (WinNT.SYSTEM_LOGICAL_PROCESSOR_INFORMATION[])firstInformation
/*  686 */       .toArray((Structure[])new WinNT.SYSTEM_LOGICAL_PROCESSOR_INFORMATION[returnedStructCount]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String[] getPrivateProfileSection(String appName, String fileName) {
/*  707 */     char[] buffer = new char[32768];
/*  708 */     if (Kernel32.INSTANCE.GetPrivateProfileSection(appName, buffer, new WinDef.DWORD(buffer.length), fileName).intValue() == 0) {
/*  709 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*  711 */     return (new String(buffer)).split("\000");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String[] getPrivateProfileSectionNames(String fileName) {
/*  726 */     char[] buffer = new char[65536];
/*  727 */     if (Kernel32.INSTANCE.GetPrivateProfileSectionNames(buffer, new WinDef.DWORD(buffer.length), fileName).intValue() == 0) {
/*  728 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*  730 */     return (new String(buffer)).split("\000");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writePrivateProfileSection(String appName, String[] strings, String fileName) {
/*  743 */     StringBuilder buffer = new StringBuilder();
/*  744 */     for (String string : strings)
/*  745 */       buffer.append(string).append(false); 
/*  746 */     buffer.append(false);
/*  747 */     if (!Kernel32.INSTANCE.WritePrivateProfileSection(appName, buffer.toString(), fileName)) {
/*  748 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final List<String> queryDosDevice(String lpszDeviceName, int maxTargetSize) {
/*  760 */     char[] lpTargetPath = new char[maxTargetSize];
/*  761 */     int dwSize = Kernel32.INSTANCE.QueryDosDevice(lpszDeviceName, lpTargetPath, lpTargetPath.length);
/*  762 */     if (dwSize == 0) {
/*  763 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */     
/*  766 */     return Native.toStringList(lpTargetPath, 0, dwSize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final List<String> getVolumePathNamesForVolumeName(String lpszVolumeName) {
/*  776 */     char[] lpszVolumePathNames = new char[261];
/*  777 */     IntByReference lpcchReturnLength = new IntByReference();
/*      */     
/*  779 */     if (!Kernel32.INSTANCE.GetVolumePathNamesForVolumeName(lpszVolumeName, lpszVolumePathNames, lpszVolumePathNames.length, lpcchReturnLength)) {
/*  780 */       int hr = Kernel32.INSTANCE.GetLastError();
/*  781 */       if (hr != 234) {
/*  782 */         throw new Win32Exception(hr);
/*      */       }
/*      */       
/*  785 */       int required = lpcchReturnLength.getValue();
/*  786 */       lpszVolumePathNames = new char[required];
/*      */       
/*  788 */       if (!Kernel32.INSTANCE.GetVolumePathNamesForVolumeName(lpszVolumeName, lpszVolumePathNames, lpszVolumePathNames.length, lpcchReturnLength)) {
/*  789 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */       }
/*      */     } 
/*      */     
/*  793 */     int bufSize = lpcchReturnLength.getValue();
/*  794 */     return Native.toStringList(lpszVolumePathNames, 0, bufSize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String extractVolumeGUID(String volumeGUIDPath) {
/*  814 */     if (volumeGUIDPath == null || volumeGUIDPath
/*  815 */       .length() <= "\\\\?\\Volume{".length() + "}\\".length() || 
/*  816 */       !volumeGUIDPath.startsWith("\\\\?\\Volume{") || 
/*  817 */       !volumeGUIDPath.endsWith("}\\")) {
/*  818 */       throw new IllegalArgumentException("Bad volume GUID path format: " + volumeGUIDPath);
/*      */     }
/*      */     
/*  821 */     return volumeGUIDPath.substring("\\\\?\\Volume{".length(), volumeGUIDPath.length() - "}\\".length());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String QueryFullProcessImageName(WinNT.HANDLE hProcess, int dwFlags) {
/*  838 */     char[] path = new char[260];
/*  839 */     IntByReference lpdwSize = new IntByReference(path.length);
/*  840 */     if (Kernel32.INSTANCE.QueryFullProcessImageName(hProcess, 0, path, lpdwSize))
/*  841 */       return (new String(path)).substring(0, lpdwSize.getValue()); 
/*  842 */     throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] getResource(String path, String type, String name) {
/*  859 */     WinDef.HMODULE target = Kernel32.INSTANCE.LoadLibraryEx(path, null, 2);
/*      */     
/*  861 */     if (target == null) {
/*  862 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */     
/*  865 */     Win32Exception err = null;
/*  866 */     Pointer start = null;
/*  867 */     int length = 0;
/*  868 */     byte[] results = null; try {
/*      */       Memory memory1, memory2;
/*  870 */       Pointer t = null;
/*      */       try {
/*  872 */         t = new Pointer(Long.parseLong(type));
/*  873 */       } catch (NumberFormatException e) {
/*  874 */         memory1 = new Memory((Native.WCHAR_SIZE * (type.length() + 1)));
/*  875 */         memory1.setWideString(0L, type);
/*      */       } 
/*      */       
/*  878 */       Pointer n = null;
/*      */       try {
/*  880 */         n = new Pointer(Long.parseLong(name));
/*  881 */       } catch (NumberFormatException e) {
/*  882 */         memory2 = new Memory((Native.WCHAR_SIZE * (name.length() + 1)));
/*  883 */         memory2.setWideString(0L, name);
/*      */       } 
/*      */       
/*  886 */       WinDef.HRSRC hrsrc = Kernel32.INSTANCE.FindResource(target, (Pointer)memory2, (Pointer)memory1);
/*  887 */       if (hrsrc == null) {
/*  888 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */       }
/*      */ 
/*      */       
/*  892 */       WinNT.HANDLE loaded = Kernel32.INSTANCE.LoadResource(target, hrsrc);
/*  893 */       if (loaded == null) {
/*  894 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */       }
/*      */       
/*  897 */       length = Kernel32.INSTANCE.SizeofResource(target, hrsrc);
/*  898 */       if (length == 0) {
/*  899 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  904 */       start = Kernel32.INSTANCE.LockResource(loaded);
/*  905 */       if (start == null) {
/*  906 */         throw new IllegalStateException("LockResource returned null.");
/*      */       }
/*      */       
/*  909 */       results = start.getByteArray(0L, length);
/*  910 */     } catch (Win32Exception we) {
/*  911 */       err = we;
/*      */     } finally {
/*      */       
/*  914 */       if (target != null && 
/*  915 */         !Kernel32.INSTANCE.FreeLibrary(target)) {
/*  916 */         Win32Exception we = new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*  917 */         if (err != null) {
/*  918 */           we.addSuppressedReflected((Throwable)err);
/*      */         }
/*  920 */         throw we;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  925 */     if (err != null) {
/*  926 */       throw err;
/*      */     }
/*      */     
/*  929 */     return results;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Map<String, List<String>> getResourceNames(String path) {
/*  942 */     WinDef.HMODULE target = Kernel32.INSTANCE.LoadLibraryEx(path, null, 2);
/*      */     
/*  944 */     if (target == null) {
/*  945 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */     
/*  948 */     final List<String> types = new ArrayList<String>();
/*  949 */     final Map<String, List<String>> result = new LinkedHashMap<String, List<String>>();
/*      */     
/*  951 */     WinBase.EnumResTypeProc ertp = new WinBase.EnumResTypeProc()
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public boolean invoke(WinDef.HMODULE module, Pointer type, Pointer lParam)
/*      */         {
/*  959 */           if (Pointer.nativeValue(type) <= 65535L) {
/*  960 */             types.add(Pointer.nativeValue(type) + "");
/*      */           } else {
/*  962 */             types.add(type.getWideString(0L));
/*      */           } 
/*  964 */           return true;
/*      */         }
/*      */       };
/*      */     
/*  968 */     WinBase.EnumResNameProc ernp = new WinBase.EnumResNameProc()
/*      */       {
/*      */         public boolean invoke(WinDef.HMODULE module, Pointer type, Pointer name, Pointer lParam)
/*      */         {
/*  972 */           String typeName = "";
/*      */           
/*  974 */           if (Pointer.nativeValue(type) <= 65535L) {
/*  975 */             typeName = Pointer.nativeValue(type) + "";
/*      */           } else {
/*  977 */             typeName = type.getWideString(0L);
/*      */           } 
/*      */           
/*  980 */           if (Pointer.nativeValue(name) < 65535L) {
/*  981 */             ((List<String>)result.get(typeName)).add(Pointer.nativeValue(name) + "");
/*      */           } else {
/*  983 */             ((List<String>)result.get(typeName)).add(name.getWideString(0L));
/*      */           } 
/*      */           
/*  986 */           return true;
/*      */         }
/*      */       };
/*      */ 
/*      */     
/*  991 */     Win32Exception err = null;
/*      */     try {
/*  993 */       if (!Kernel32.INSTANCE.EnumResourceTypes(target, ertp, null)) {
/*  994 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */       }
/*      */       
/*  997 */       for (String typeName : types) {
/*  998 */         Memory memory; result.put(typeName, new ArrayList<String>());
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1003 */         Pointer pointer = null;
/*      */         try {
/* 1005 */           pointer = new Pointer(Long.parseLong(typeName));
/* 1006 */         } catch (NumberFormatException e) {
/* 1007 */           memory = new Memory((Native.WCHAR_SIZE * (typeName.length() + 1)));
/* 1008 */           memory.setWideString(0L, typeName);
/*      */         } 
/*      */         
/* 1011 */         boolean callResult = Kernel32.INSTANCE.EnumResourceNames(target, (Pointer)memory, ernp, null);
/*      */         
/* 1013 */         if (!callResult) {
/* 1014 */           throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */         }
/*      */       } 
/* 1017 */     } catch (Win32Exception e) {
/* 1018 */       err = e;
/*      */     }
/*      */     finally {
/*      */       
/* 1022 */       if (target != null && 
/* 1023 */         !Kernel32.INSTANCE.FreeLibrary(target)) {
/* 1024 */         Win32Exception we = new Win32Exception(Kernel32.INSTANCE.GetLastError());
/* 1025 */         if (err != null) {
/* 1026 */           we.addSuppressedReflected((Throwable)err);
/*      */         }
/* 1028 */         throw we;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1033 */     if (err != null) {
/* 1034 */       throw err;
/*      */     }
/* 1036 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static List<Tlhelp32.MODULEENTRY32W> getModules(int processID) {
/* 1047 */     WinNT.HANDLE snapshot = Kernel32.INSTANCE.CreateToolhelp32Snapshot(Tlhelp32.TH32CS_SNAPMODULE, new WinDef.DWORD(processID));
/* 1048 */     if (snapshot == null) {
/* 1049 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */     
/* 1052 */     Win32Exception we = null;
/*      */     try {
/* 1054 */       Tlhelp32.MODULEENTRY32W first = new Tlhelp32.MODULEENTRY32W();
/*      */       
/* 1056 */       if (!Kernel32.INSTANCE.Module32FirstW(snapshot, first)) {
/* 1057 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */       }
/*      */       
/* 1060 */       List<Tlhelp32.MODULEENTRY32W> modules = new ArrayList<Tlhelp32.MODULEENTRY32W>();
/* 1061 */       modules.add(first);
/*      */       
/* 1063 */       Tlhelp32.MODULEENTRY32W next = new Tlhelp32.MODULEENTRY32W();
/* 1064 */       while (Kernel32.INSTANCE.Module32NextW(snapshot, next)) {
/* 1065 */         modules.add(next);
/* 1066 */         next = new Tlhelp32.MODULEENTRY32W();
/*      */       } 
/*      */       
/* 1069 */       int lastError = Kernel32.INSTANCE.GetLastError();
/*      */ 
/*      */ 
/*      */       
/* 1073 */       if (lastError != 0 && lastError != 18) {
/* 1074 */         throw new Win32Exception(lastError);
/*      */       }
/*      */       
/* 1077 */       return modules;
/* 1078 */     } catch (Win32Exception e) {
/* 1079 */       we = e;
/* 1080 */       throw we;
/*      */     } finally {
/*      */       try {
/* 1083 */         closeHandle(snapshot);
/* 1084 */       } catch (Win32Exception e) {
/* 1085 */         if (we == null) {
/* 1086 */           we = e;
/*      */         } else {
/* 1088 */           we.addSuppressedReflected((Throwable)e);
/*      */         } 
/*      */       } 
/*      */       
/* 1092 */       if (we != null)
/* 1093 */         throw we; 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/Kernel32Util.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */