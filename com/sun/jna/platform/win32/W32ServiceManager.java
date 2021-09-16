/*     */ package com.sun.jna.platform.win32;
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
/*     */ public class W32ServiceManager
/*     */ {
/*  34 */   Winsvc.SC_HANDLE _handle = null;
/*  35 */   String _machineName = null;
/*  36 */   String _databaseName = null;
/*     */ 
/*     */   
/*     */   public W32ServiceManager() {}
/*     */   
/*     */   public W32ServiceManager(String machineName, String databaseName) {
/*  42 */     this._machineName = machineName;
/*  43 */     this._databaseName = databaseName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void open(int permissions) {
/*  52 */     close();
/*     */     
/*  54 */     this._handle = Advapi32.INSTANCE.OpenSCManager(this._machineName, this._databaseName, permissions);
/*     */ 
/*     */     
/*  57 */     if (this._handle == null) {
/*  58 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/*  66 */     if (this._handle != null) {
/*  67 */       if (!Advapi32.INSTANCE.CloseServiceHandle(this._handle)) {
/*  68 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*     */       }
/*  70 */       this._handle = null;
/*     */     } 
/*     */   }
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
/*     */   public W32Service openService(String serviceName, int permissions) {
/*  84 */     Winsvc.SC_HANDLE serviceHandle = Advapi32.INSTANCE.OpenService(this._handle, serviceName, permissions);
/*     */ 
/*     */     
/*  87 */     if (serviceHandle == null) {
/*  88 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*     */     }
/*     */     
/*  91 */     return new W32Service(serviceHandle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Winsvc.SC_HANDLE getHandle() {
/* 100 */     return this._handle;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/W32ServiceManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */