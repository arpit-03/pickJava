/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ import com.sun.jna.Native;
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.Structure;
/*     */ import com.sun.jna.ptr.IntByReference;
/*     */ import com.sun.jna.win32.StdCallLibrary;
/*     */ import com.sun.jna.win32.W32APIOptions;
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
/*     */ public interface SetupApi
/*     */   extends StdCallLibrary
/*     */ {
/*  43 */   public static final SetupApi INSTANCE = (SetupApi)Native.loadLibrary("setupapi", SetupApi.class, W32APIOptions.DEFAULT_OPTIONS);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   public static final Guid.GUID GUID_DEVINTERFACE_DISK = new Guid.GUID("53F56307-B6BF-11D0-94F2-00A0C91EFB8B");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public static final Guid.GUID GUID_DEVINTERFACE_COMPORT = new Guid.GUID("86E0D1E0-8089-11D0-9CE4-08003E301F73");
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
/*     */   public static final int DIGCF_DEFAULT = 1;
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
/*     */   public static final int DIGCF_PRESENT = 2;
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
/*     */   public static final int DIGCF_ALLCLASSES = 4;
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
/*     */   public static final int DIGCF_PROFILE = 8;
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
/*     */   public static final int DIGCF_DEVICEINTERFACE = 16;
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
/*     */   public static final int SPDRP_REMOVAL_POLICY = 31;
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
/*     */   public static final int CM_DEVCAP_REMOVABLE = 4;
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
/*     */   public static final int DICS_FLAG_GLOBAL = 1;
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
/*     */   public static final int DICS_FLAG_CONFIGSPECIFIC = 2;
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
/*     */   public static final int DICS_FLAG_CONFIGGENERAL = 4;
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
/*     */   public static final int DIREG_DEV = 1;
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
/*     */   public static final int DIREG_DRV = 2;
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
/*     */   public static final int DIREG_BOTH = 4;
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
/*     */   public static final int SPDRP_DEVICEDESC = 0;
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
/*     */   WinNT.HANDLE SetupDiGetClassDevs(Guid.GUID paramGUID, Pointer paramPointer1, Pointer paramPointer2, int paramInt);
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
/*     */   boolean SetupDiDestroyDeviceInfoList(WinNT.HANDLE paramHANDLE);
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
/*     */   boolean SetupDiEnumDeviceInterfaces(WinNT.HANDLE paramHANDLE, Pointer paramPointer, Guid.GUID paramGUID, int paramInt, SP_DEVICE_INTERFACE_DATA paramSP_DEVICE_INTERFACE_DATA);
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
/*     */   boolean SetupDiGetDeviceInterfaceDetail(WinNT.HANDLE paramHANDLE, SP_DEVICE_INTERFACE_DATA paramSP_DEVICE_INTERFACE_DATA, Pointer paramPointer, int paramInt, IntByReference paramIntByReference, SP_DEVINFO_DATA paramSP_DEVINFO_DATA);
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
/*     */   boolean SetupDiGetDeviceRegistryProperty(WinNT.HANDLE paramHANDLE, SP_DEVINFO_DATA paramSP_DEVINFO_DATA, int paramInt1, IntByReference paramIntByReference1, Pointer paramPointer, int paramInt2, IntByReference paramIntByReference2);
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
/*     */   WinReg.HKEY SetupDiOpenDevRegKey(WinNT.HANDLE paramHANDLE, SP_DEVINFO_DATA paramSP_DEVINFO_DATA, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
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
/*     */   boolean SetupDiEnumDeviceInfo(WinNT.HANDLE paramHANDLE, int paramInt, SP_DEVINFO_DATA paramSP_DEVINFO_DATA);
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
/*     */   public static class SP_DEVICE_INTERFACE_DATA
/*     */     extends Structure
/*     */   {
/*     */     public static class ByReference
/*     */       extends SetupApi.SP_DEVINFO_DATA
/*     */       implements Structure.ByReference
/*     */     {
/*     */       public ByReference() {}
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
/*     */       public ByReference(Pointer memory) {
/* 394 */         super(memory);
/*     */       }
/*     */     }
/*     */     
/* 398 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "cbSize", "InterfaceClassGuid", "Flags", "Reserved" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int cbSize;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Guid.GUID InterfaceClassGuid;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int Flags;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Pointer Reserved;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SP_DEVICE_INTERFACE_DATA() {
/* 424 */       this.cbSize = size();
/*     */     }
/*     */     
/*     */     public SP_DEVICE_INTERFACE_DATA(Pointer memory) {
/* 428 */       super(memory);
/* 429 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 434 */       return FIELDS;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class SP_DEVINFO_DATA
/*     */     extends Structure
/*     */   {
/*     */     public static class ByReference
/*     */       extends SP_DEVINFO_DATA
/*     */       implements Structure.ByReference
/*     */     {
/*     */       public ByReference() {}
/*     */       
/*     */       public ByReference(Pointer memory) {
/* 448 */         super(memory);
/*     */       }
/*     */     }
/*     */     
/* 452 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "cbSize", "InterfaceClassGuid", "DevInst", "Reserved" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int cbSize;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Guid.GUID InterfaceClassGuid;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int DevInst;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Pointer Reserved;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SP_DEVINFO_DATA() {
/* 479 */       this.cbSize = size();
/*     */     }
/*     */     
/*     */     public SP_DEVINFO_DATA(Pointer memory) {
/* 483 */       super(memory);
/* 484 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 489 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/SetupApi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */