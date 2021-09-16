/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ import com.sun.jna.ptr.IntByReference;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.FutureTask;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public final class User32Util
/*     */ {
/*     */   public static final int registerWindowMessage(String lpString) {
/*  58 */     int messageId = User32.INSTANCE.RegisterWindowMessage(lpString);
/*  59 */     if (messageId == 0)
/*  60 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError()); 
/*  61 */     return messageId;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final WinDef.HWND createWindow(String className, String windowName, int style, int x, int y, int width, int height, WinDef.HWND parent, WinDef.HMENU menu, WinDef.HINSTANCE instance, WinDef.LPVOID param) {
/*  66 */     return createWindowEx(0, className, windowName, style, x, y, width, height, parent, menu, instance, param);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final WinDef.HWND createWindowEx(int exStyle, String className, String windowName, int style, int x, int y, int width, int height, WinDef.HWND parent, WinDef.HMENU menu, WinDef.HINSTANCE instance, WinDef.LPVOID param) {
/*  72 */     WinDef.HWND hWnd = User32.INSTANCE.CreateWindowEx(exStyle, className, windowName, style, x, y, width, height, parent, menu, instance, param);
/*  73 */     if (hWnd == null)
/*  74 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError()); 
/*  75 */     return hWnd;
/*     */   }
/*     */   
/*     */   public static final void destroyWindow(WinDef.HWND hWnd) {
/*  79 */     if (!User32.INSTANCE.DestroyWindow(hWnd))
/*  80 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError()); 
/*     */   }
/*     */   
/*     */   public static final List<WinUser.RAWINPUTDEVICELIST> GetRawInputDeviceList() {
/*  84 */     IntByReference puiNumDevices = new IntByReference(0);
/*  85 */     WinUser.RAWINPUTDEVICELIST placeholder = new WinUser.RAWINPUTDEVICELIST();
/*  86 */     int cbSize = placeholder.sizeof();
/*     */     
/*  88 */     int returnValue = User32.INSTANCE.GetRawInputDeviceList(null, puiNumDevices, cbSize);
/*  89 */     if (returnValue != 0) {
/*  90 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*     */     }
/*     */     
/*  93 */     int deviceCount = puiNumDevices.getValue();
/*  94 */     WinUser.RAWINPUTDEVICELIST[] records = (WinUser.RAWINPUTDEVICELIST[])placeholder.toArray(deviceCount);
/*  95 */     returnValue = User32.INSTANCE.GetRawInputDeviceList(records, puiNumDevices, cbSize);
/*  96 */     if (returnValue == -1) {
/*  97 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*     */     }
/*     */     
/* 100 */     if (returnValue != records.length) {
/* 101 */       throw new IllegalStateException("Mismatched allocated (" + records.length + ") vs. received devices count (" + returnValue + ")");
/*     */     }
/*     */     
/* 104 */     return Arrays.asList(records);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class MessageLoopThread
/*     */     extends Thread
/*     */   {
/*     */     public class Handler
/*     */       implements InvocationHandler
/*     */     {
/*     */       private final Object delegate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public Handler(Object delegate) {
/* 127 */         this.delegate = delegate;
/*     */       }
/*     */       
/*     */       public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
/*     */         try {
/* 132 */           return User32Util.MessageLoopThread.this.runOnThread(new Callable() {
/*     */                 public Object call() throws Exception {
/* 134 */                   return method.invoke(User32Util.MessageLoopThread.Handler.this.delegate, args);
/*     */                 }
/*     */               });
/* 137 */         } catch (InvocationTargetException ex) {
/* 138 */           Throwable cause = ex.getCause();
/* 139 */           if (cause instanceof Exception) {
/* 140 */             StackTraceElement[] hiddenStack = cause.getStackTrace();
/* 141 */             cause.fillInStackTrace();
/* 142 */             StackTraceElement[] currentStack = cause.getStackTrace();
/* 143 */             StackTraceElement[] fullStack = new StackTraceElement[currentStack.length + hiddenStack.length];
/* 144 */             System.arraycopy(hiddenStack, 0, fullStack, 0, hiddenStack.length);
/* 145 */             System.arraycopy(currentStack, 0, fullStack, hiddenStack.length, currentStack.length);
/* 146 */             cause.setStackTrace(fullStack);
/* 147 */             throw (Exception)cause;
/*     */           } 
/* 149 */           throw ex;
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 155 */     private volatile int nativeThreadId = 0;
/* 156 */     private volatile long javaThreadId = 0L;
/* 157 */     private final List<FutureTask> workQueue = Collections.synchronizedList(new ArrayList<FutureTask>());
/* 158 */     private static long messageLoopId = 0L;
/*     */     
/*     */     public MessageLoopThread() {
/* 161 */       setName("JNA User32 MessageLoop " + ++messageLoopId);
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 166 */       WinUser.MSG msg = new WinUser.MSG();
/*     */ 
/*     */       
/* 169 */       User32.INSTANCE.PeekMessage(msg, null, 0, 0, 0);
/* 170 */       this.javaThreadId = Thread.currentThread().getId();
/* 171 */       this.nativeThreadId = Kernel32.INSTANCE.GetCurrentThreadId();
/*     */       
/*     */       int getMessageReturn;
/* 174 */       while ((getMessageReturn = User32.INSTANCE.GetMessage(msg, null, 0, 0)) != 0) {
/* 175 */         if (getMessageReturn != -1) {
/*     */           
/* 177 */           while (!this.workQueue.isEmpty()) {
/*     */             try {
/* 179 */               FutureTask ft = this.workQueue.remove(0);
/* 180 */               ft.run();
/* 181 */             } catch (IndexOutOfBoundsException ex) {
/*     */               break;
/*     */             } 
/*     */           } 
/* 185 */           User32.INSTANCE.TranslateMessage(msg);
/* 186 */           User32.INSTANCE.DispatchMessage(msg);
/*     */           continue;
/*     */         } 
/* 189 */         if (getMessageFailed()) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 195 */       while (!this.workQueue.isEmpty()) {
/* 196 */         ((FutureTask)this.workQueue.remove(0)).cancel(false);
/*     */       }
/*     */     }
/*     */     
/*     */     public <V> Future<V> runAsync(Callable<V> command) {
/* 201 */       while (this.nativeThreadId == 0) {
/*     */         try {
/* 203 */           Thread.sleep(20L);
/* 204 */         } catch (InterruptedException ex) {
/* 205 */           Logger.getLogger(MessageLoopThread.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */         } 
/*     */       } 
/* 208 */       FutureTask<V> futureTask = new FutureTask<V>(command);
/* 209 */       this.workQueue.add(futureTask);
/* 210 */       User32.INSTANCE.PostThreadMessage(this.nativeThreadId, 1024, null, null);
/* 211 */       return futureTask;
/*     */     }
/*     */     
/*     */     public <V> V runOnThread(Callable<V> callable) throws Exception {
/* 215 */       while (this.javaThreadId == 0L) {
/*     */         try {
/* 217 */           Thread.sleep(20L);
/* 218 */         } catch (InterruptedException ex) {
/* 219 */           Logger.getLogger(MessageLoopThread.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */         } 
/*     */       } 
/*     */       
/* 223 */       if (this.javaThreadId == Thread.currentThread().getId()) {
/* 224 */         return callable.call();
/*     */       }
/*     */       
/* 227 */       Future<V> ft = runAsync(callable);
/*     */       try {
/* 229 */         return ft.get();
/* 230 */       } catch (InterruptedException ex) {
/* 231 */         throw ex;
/* 232 */       } catch (ExecutionException ex) {
/* 233 */         Throwable cause = ex.getCause();
/* 234 */         if (cause instanceof Exception) {
/* 235 */           throw (Exception)cause;
/*     */         }
/* 237 */         throw ex;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void exit() {
/* 244 */       User32.INSTANCE.PostThreadMessage(this.nativeThreadId, 18, null, null);
/*     */     }
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
/*     */     protected boolean getMessageFailed() {
/* 263 */       int lastError = Kernel32.INSTANCE.GetLastError();
/* 264 */       Logger.getLogger("com.sun.jna.platform.win32.User32Util.MessageLoopThread")
/* 265 */         .log(Level.WARNING, "Message loop was interrupted by an error. [lastError: {0}]", 
/*     */           
/* 267 */           Integer.valueOf(lastError));
/* 268 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/User32Util.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */