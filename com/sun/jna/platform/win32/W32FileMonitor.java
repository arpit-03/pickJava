/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ import com.sun.jna.platform.FileMonitor;
/*     */ import com.sun.jna.ptr.IntByReference;
/*     */ import com.sun.jna.ptr.PointerByReference;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class W32FileMonitor
/*     */   extends FileMonitor
/*     */ {
/*     */   private static final int BUFFER_SIZE = 4096;
/*     */   private Thread watcher;
/*     */   private WinNT.HANDLE port;
/*     */   
/*     */   private class FileInfo
/*     */   {
/*     */     public final File file;
/*     */     public final WinNT.HANDLE handle;
/*     */     public final int notifyMask;
/*     */     public final boolean recursive;
/*  49 */     public final WinNT.FILE_NOTIFY_INFORMATION info = new WinNT.FILE_NOTIFY_INFORMATION(4096);
/*  50 */     public final IntByReference infoLength = new IntByReference();
/*  51 */     public final WinBase.OVERLAPPED overlapped = new WinBase.OVERLAPPED();
/*     */     public FileInfo(File f, WinNT.HANDLE h, int mask, boolean recurse) {
/*  53 */       this.file = f;
/*  54 */       this.handle = h;
/*  55 */       this.notifyMask = mask;
/*  56 */       this.recursive = recurse;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  61 */   private final Map<File, FileInfo> fileMap = new HashMap<File, FileInfo>();
/*  62 */   private final Map<WinNT.HANDLE, FileInfo> handleMap = new HashMap<WinNT.HANDLE, FileInfo>(); private boolean disposing = false;
/*     */   private static int watcherThreadID;
/*     */   
/*     */   private void handleChanges(FileInfo finfo) throws IOException {
/*  66 */     Kernel32 klib = Kernel32.INSTANCE;
/*  67 */     WinNT.FILE_NOTIFY_INFORMATION fni = finfo.info;
/*     */     
/*  69 */     fni.read();
/*     */     do {
/*  71 */       FileMonitor.FileEvent event = null;
/*  72 */       File file = new File(finfo.file, fni.getFilename());
/*  73 */       switch (fni.Action) {
/*     */         case 0:
/*     */           break;
/*     */         case 3:
/*  77 */           event = new FileMonitor.FileEvent(this, file, 4);
/*     */           break;
/*     */         case 1:
/*  80 */           event = new FileMonitor.FileEvent(this, file, 1);
/*     */           break;
/*     */         case 2:
/*  83 */           event = new FileMonitor.FileEvent(this, file, 2);
/*     */           break;
/*     */         case 4:
/*  86 */           event = new FileMonitor.FileEvent(this, file, 16);
/*     */           break;
/*     */         case 5:
/*  89 */           event = new FileMonitor.FileEvent(this, file, 32);
/*     */           break;
/*     */         
/*     */         default:
/*  93 */           System.err.println("Unrecognized file action '" + fni.Action + "'");
/*     */           break;
/*     */       } 
/*  96 */       if (event != null) {
/*  97 */         notify(event);
/*     */       }
/*     */       
/* 100 */       fni = fni.next();
/* 101 */     } while (fni != null);
/*     */ 
/*     */     
/* 104 */     if (!finfo.file.exists()) {
/* 105 */       unwatch(finfo.file);
/*     */       
/*     */       return;
/*     */     } 
/* 109 */     if (!klib.ReadDirectoryChangesW(finfo.handle, finfo.info, finfo.info
/* 110 */         .size(), finfo.recursive, finfo.notifyMask, finfo.infoLength, finfo.overlapped, null))
/*     */     {
/* 112 */       if (!this.disposing) {
/* 113 */         int err = klib.GetLastError();
/* 114 */         throw new IOException("ReadDirectoryChangesW failed on " + finfo.file + ": '" + 
/*     */             
/* 116 */             Kernel32Util.formatMessageFromLastErrorCode(err) + "' (" + err + ")");
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private FileInfo waitForChange() {
/* 123 */     IntByReference rcount = new IntByReference();
/* 124 */     BaseTSD.ULONG_PTRByReference rkey = new BaseTSD.ULONG_PTRByReference();
/* 125 */     PointerByReference roverlap = new PointerByReference();
/* 126 */     if (!Kernel32.INSTANCE.GetQueuedCompletionStatus(this.port, rcount, rkey, roverlap, -1)) {
/* 127 */       return null;
/*     */     }
/* 129 */     synchronized (this) {
/* 130 */       return this.handleMap.get(new WinNT.HANDLE(rkey.getValue().toPointer()));
/*     */     } 
/*     */   }
/*     */   
/*     */   private int convertMask(int mask) {
/* 135 */     int result = 0;
/* 136 */     if ((mask & 0x1) != 0) {
/* 137 */       result |= 0x40;
/*     */     }
/* 139 */     if ((mask & 0x2) != 0) {
/* 140 */       result |= 0x3;
/*     */     }
/* 142 */     if ((mask & 0x4) != 0) {
/* 143 */       result |= 0x10;
/*     */     }
/* 145 */     if ((mask & 0x30) != 0) {
/* 146 */       result |= 0x3;
/*     */     }
/* 148 */     if ((mask & 0x40) != 0) {
/* 149 */       result |= 0x8;
/*     */     }
/* 151 */     if ((mask & 0x8) != 0) {
/* 152 */       result |= 0x20;
/*     */     }
/* 154 */     if ((mask & 0x80) != 0) {
/* 155 */       result |= 0x4;
/*     */     }
/* 157 */     if ((mask & 0x100) != 0) {
/* 158 */       result |= 0x100;
/*     */     }
/* 160 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized void watch(File file, int eventMask, boolean recursive) throws IOException {
/* 167 */     File dir = file;
/* 168 */     if (!dir.isDirectory()) {
/* 169 */       recursive = false;
/* 170 */       dir = file.getParentFile();
/*     */     } 
/* 172 */     while (dir != null && !dir.exists()) {
/* 173 */       recursive = true;
/* 174 */       dir = dir.getParentFile();
/*     */     } 
/* 176 */     if (dir == null) {
/* 177 */       throw new FileNotFoundException("No ancestor found for " + file);
/*     */     }
/* 179 */     Kernel32 klib = Kernel32.INSTANCE;
/* 180 */     int mask = 7;
/*     */     
/* 182 */     int flags = 1107296256;
/*     */     
/* 184 */     WinNT.HANDLE handle = klib.CreateFile(file.getAbsolutePath(), 1, mask, null, 3, flags, null);
/*     */ 
/*     */ 
/*     */     
/* 188 */     if (WinBase.INVALID_HANDLE_VALUE.equals(handle)) {
/* 189 */       throw new IOException("Unable to open " + file + " (" + klib
/* 190 */           .GetLastError() + ")");
/*     */     }
/* 192 */     int notifyMask = convertMask(eventMask);
/* 193 */     FileInfo finfo = new FileInfo(file, handle, notifyMask, recursive);
/* 194 */     this.fileMap.put(file, finfo);
/* 195 */     this.handleMap.put(handle, finfo);
/*     */     
/* 197 */     this.port = klib.CreateIoCompletionPort(handle, this.port, handle.getPointer(), 0);
/* 198 */     if (WinBase.INVALID_HANDLE_VALUE.equals(this.port)) {
/* 199 */       throw new IOException("Unable to create/use I/O Completion port for " + file + " (" + klib
/*     */           
/* 201 */           .GetLastError() + ")");
/*     */     }
/*     */ 
/*     */     
/* 205 */     if (!klib.ReadDirectoryChangesW(handle, finfo.info, finfo.info.size(), recursive, notifyMask, finfo.infoLength, finfo.overlapped, null)) {
/*     */ 
/*     */       
/* 208 */       int err = klib.GetLastError();
/* 209 */       throw new IOException("ReadDirectoryChangesW failed on " + finfo.file + ", handle " + handle + ": '" + 
/*     */           
/* 211 */           Kernel32Util.formatMessageFromLastErrorCode(err) + "' (" + err + ")");
/*     */     } 
/*     */     
/* 214 */     if (this.watcher == null) {
/* 215 */       this.watcher = new Thread("W32 File Monitor-" + watcherThreadID++)
/*     */         {
/*     */           public void run()
/*     */           {
/*     */             while (true) {
/* 220 */               W32FileMonitor.FileInfo finfo = W32FileMonitor.this.waitForChange();
/* 221 */               if (finfo == null) {
/* 222 */                 synchronized (W32FileMonitor.this) {
/* 223 */                   if (W32FileMonitor.this.fileMap.isEmpty()) {
/* 224 */                     W32FileMonitor.this.watcher = null;
/*     */                     
/*     */                     break;
/*     */                   } 
/*     */                 } 
/*     */                 continue;
/*     */               } 
/*     */               try {
/* 232 */                 W32FileMonitor.this.handleChanges(finfo);
/*     */               }
/* 234 */               catch (IOException e) {
/*     */                 
/* 236 */                 e.printStackTrace();
/*     */               } 
/*     */             } 
/*     */           }
/*     */         };
/* 241 */       this.watcher.setDaemon(true);
/* 242 */       this.watcher.start();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected synchronized void unwatch(File file) {
/* 248 */     FileInfo finfo = this.fileMap.remove(file);
/* 249 */     if (finfo != null) {
/* 250 */       this.handleMap.remove(finfo.handle);
/* 251 */       Kernel32 klib = Kernel32.INSTANCE;
/*     */       
/* 253 */       klib.CloseHandle(finfo.handle);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void dispose() {
/* 259 */     this.disposing = true;
/*     */ 
/*     */     
/* 262 */     int i = 0;
/* 263 */     for (Object[] keys = this.fileMap.keySet().toArray(); !this.fileMap.isEmpty();) {
/* 264 */       unwatch((File)keys[i++]);
/*     */     }
/*     */     
/* 267 */     Kernel32 klib = Kernel32.INSTANCE;
/* 268 */     klib.PostQueuedCompletionStatus(this.port, 0, null, null);
/* 269 */     klib.CloseHandle(this.port);
/* 270 */     this.port = null;
/* 271 */     this.watcher = null;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/W32FileMonitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */