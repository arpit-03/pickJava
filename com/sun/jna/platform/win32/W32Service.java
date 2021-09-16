/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ import com.sun.jna.Memory;
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.ptr.IntByReference;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class W32Service
/*     */ {
/*  50 */   Winsvc.SC_HANDLE _handle = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public W32Service(Winsvc.SC_HANDLE handle) {
/*  59 */     this._handle = handle;
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
/*     */   private void addShutdownPrivilegeToProcess() {
/*  75 */     WinNT.HANDLEByReference hToken = new WinNT.HANDLEByReference();
/*  76 */     WinNT.LUID luid = new WinNT.LUID();
/*  77 */     Advapi32.INSTANCE.OpenProcessToken(Kernel32.INSTANCE.GetCurrentProcess(), 32, hToken);
/*     */     
/*  79 */     Advapi32.INSTANCE.LookupPrivilegeValue("", "SeShutdownPrivilege", luid);
/*  80 */     WinNT.TOKEN_PRIVILEGES tp = new WinNT.TOKEN_PRIVILEGES(1);
/*  81 */     tp.Privileges[0] = new WinNT.LUID_AND_ATTRIBUTES(luid, new WinDef.DWORD(2L));
/*  82 */     Advapi32.INSTANCE.AdjustTokenPrivileges(hToken.getValue(), false, tp, tp.size(), null, new IntByReference());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFailureActions(List<Winsvc.SC_ACTION> actions, int resetPeriod, String rebootMsg, String command) {
/*  93 */     Winsvc.SERVICE_FAILURE_ACTIONS.ByReference actionStruct = new Winsvc.SERVICE_FAILURE_ACTIONS.ByReference();
/*  94 */     actionStruct.dwResetPeriod = resetPeriod;
/*  95 */     actionStruct.lpRebootMsg = rebootMsg;
/*  96 */     actionStruct.lpCommand = command;
/*  97 */     actionStruct.cActions = actions.size();
/*     */     
/*  99 */     actionStruct.lpsaActions = new Winsvc.SC_ACTION.ByReference();
/* 100 */     Winsvc.SC_ACTION[] actionArray = (Winsvc.SC_ACTION[])actionStruct.lpsaActions.toArray(actions.size());
/* 101 */     boolean hasShutdownPrivilege = false;
/* 102 */     int i = 0;
/* 103 */     for (Winsvc.SC_ACTION action : actions) {
/* 104 */       if (!hasShutdownPrivilege && action.type == 2) {
/* 105 */         addShutdownPrivilegeToProcess();
/* 106 */         hasShutdownPrivilege = true;
/*     */       } 
/* 108 */       (actionArray[i]).type = action.type;
/* 109 */       (actionArray[i]).delay = action.delay;
/* 110 */       i++;
/*     */     } 
/*     */     
/* 113 */     if (!Advapi32.INSTANCE.ChangeServiceConfig2(this._handle, 2, actionStruct))
/*     */     {
/* 115 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*     */     }
/*     */   }
/*     */   
/*     */   private Pointer queryServiceConfig2(int type) {
/* 120 */     IntByReference bufferSize = new IntByReference();
/* 121 */     Advapi32.INSTANCE.QueryServiceConfig2(this._handle, type, Pointer.NULL, 0, bufferSize);
/*     */     
/* 123 */     Memory memory = new Memory(bufferSize.getValue());
/*     */     
/* 125 */     if (!Advapi32.INSTANCE.QueryServiceConfig2(this._handle, type, (Pointer)memory, bufferSize.getValue(), new IntByReference()))
/*     */     {
/* 127 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*     */     }
/*     */     
/* 130 */     return (Pointer)memory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Winsvc.SERVICE_FAILURE_ACTIONS getFailureActions() {
/* 139 */     Pointer buffer = queryServiceConfig2(2);
/* 140 */     Winsvc.SERVICE_FAILURE_ACTIONS result = new Winsvc.SERVICE_FAILURE_ACTIONS(buffer);
/* 141 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFailureActionsFlag(boolean flagValue) {
/* 150 */     Winsvc.SERVICE_FAILURE_ACTIONS_FLAG flag = new Winsvc.SERVICE_FAILURE_ACTIONS_FLAG();
/* 151 */     flag.fFailureActionsOnNonCrashFailures = flagValue ? 1 : 0;
/*     */     
/* 153 */     if (!Advapi32.INSTANCE.ChangeServiceConfig2(this._handle, 4, flag))
/*     */     {
/* 155 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getFailureActionsFlag() {
/* 165 */     Pointer buffer = queryServiceConfig2(4);
/* 166 */     Winsvc.SERVICE_FAILURE_ACTIONS_FLAG result = new Winsvc.SERVICE_FAILURE_ACTIONS_FLAG(buffer);
/* 167 */     return (result.fFailureActionsOnNonCrashFailures != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Winsvc.SERVICE_STATUS_PROCESS queryStatus() {
/* 176 */     IntByReference size = new IntByReference();
/*     */     
/* 178 */     Advapi32.INSTANCE.QueryServiceStatusEx(this._handle, 0, null, 0, size);
/*     */ 
/*     */     
/* 181 */     Winsvc.SERVICE_STATUS_PROCESS status = new Winsvc.SERVICE_STATUS_PROCESS(size.getValue());
/* 182 */     if (!Advapi32.INSTANCE.QueryServiceStatusEx(this._handle, 0, status, status
/* 183 */         .size(), size)) {
/* 184 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*     */     }
/*     */     
/* 187 */     return status;
/*     */   }
/*     */   
/*     */   public void startService() {
/* 191 */     waitForNonPendingState();
/*     */     
/* 193 */     if ((queryStatus()).dwCurrentState == 4) {
/*     */       return;
/*     */     }
/* 196 */     if (!Advapi32.INSTANCE.StartService(this._handle, 0, null)) {
/* 197 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*     */     }
/* 199 */     waitForNonPendingState();
/* 200 */     if ((queryStatus()).dwCurrentState != 4) {
/* 201 */       throw new RuntimeException("Unable to start the service");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopService() {
/* 209 */     waitForNonPendingState();
/*     */     
/* 211 */     if ((queryStatus()).dwCurrentState == 1) {
/*     */       return;
/*     */     }
/* 214 */     if (!Advapi32.INSTANCE.ControlService(this._handle, 1, new Winsvc.SERVICE_STATUS()))
/*     */     {
/* 216 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*     */     }
/* 218 */     waitForNonPendingState();
/* 219 */     if ((queryStatus()).dwCurrentState != 1) {
/* 220 */       throw new RuntimeException("Unable to stop the service");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void continueService() {
/* 228 */     waitForNonPendingState();
/*     */     
/* 230 */     if ((queryStatus()).dwCurrentState == 4) {
/*     */       return;
/*     */     }
/* 233 */     if (!Advapi32.INSTANCE.ControlService(this._handle, 3, new Winsvc.SERVICE_STATUS()))
/*     */     {
/* 235 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*     */     }
/* 237 */     waitForNonPendingState();
/* 238 */     if ((queryStatus()).dwCurrentState != 4) {
/* 239 */       throw new RuntimeException("Unable to continue the service");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pauseService() {
/* 247 */     waitForNonPendingState();
/*     */     
/* 249 */     if ((queryStatus()).dwCurrentState == 7) {
/*     */       return;
/*     */     }
/* 252 */     if (!Advapi32.INSTANCE.ControlService(this._handle, 2, new Winsvc.SERVICE_STATUS()))
/*     */     {
/* 254 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*     */     }
/* 256 */     waitForNonPendingState();
/* 257 */     if ((queryStatus()).dwCurrentState != 7) {
/* 258 */       throw new RuntimeException("Unable to pause the service");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void waitForNonPendingState() {
/* 267 */     Winsvc.SERVICE_STATUS_PROCESS status = queryStatus();
/*     */     
/* 269 */     int previousCheckPoint = status.dwCheckPoint;
/* 270 */     int checkpointStartTickCount = Kernel32.INSTANCE.GetTickCount();
/*     */     
/* 272 */     while (isPendingState(status.dwCurrentState)) {
/*     */ 
/*     */       
/* 275 */       if (status.dwCheckPoint != previousCheckPoint) {
/* 276 */         previousCheckPoint = status.dwCheckPoint;
/* 277 */         checkpointStartTickCount = Kernel32.INSTANCE.GetTickCount();
/*     */       } 
/*     */ 
/*     */       
/* 281 */       if (Kernel32.INSTANCE.GetTickCount() - checkpointStartTickCount > status.dwWaitHint) {
/* 282 */         throw new RuntimeException("Timeout waiting for service to change to a non-pending state.");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 289 */       int dwWaitTime = status.dwWaitHint / 10;
/*     */       
/* 291 */       if (dwWaitTime < 1000) {
/* 292 */         dwWaitTime = 1000;
/* 293 */       } else if (dwWaitTime > 10000) {
/* 294 */         dwWaitTime = 10000;
/*     */       } 
/*     */       try {
/* 297 */         Thread.sleep(dwWaitTime);
/* 298 */       } catch (InterruptedException e) {
/* 299 */         throw new RuntimeException(e);
/*     */       } 
/*     */       
/* 302 */       status = queryStatus();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isPendingState(int state) {
/* 307 */     switch (state) {
/*     */       case 2:
/*     */       case 3:
/*     */       case 5:
/*     */       case 6:
/* 312 */         return true;
/*     */     } 
/* 314 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Winsvc.SC_HANDLE getHandle() {
/* 325 */     return this._handle;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/W32Service.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */