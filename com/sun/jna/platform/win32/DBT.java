/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ import com.sun.jna.Native;
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.Structure;
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
/*     */ public interface DBT
/*     */ {
/*     */   public static final int DBT_NO_DISK_SPACE = 71;
/*     */   public static final int DBT_LOW_DISK_SPACE = 72;
/*     */   public static final int DBT_CONFIGMGPRIVATE = 32767;
/*     */   public static final int DBT_DEVICEARRIVAL = 32768;
/*     */   public static final int DBT_DEVICEQUERYREMOVE = 32769;
/*     */   public static final int DBT_DEVICEQUERYREMOVEFAILED = 32770;
/*     */   public static final int DBT_DEVICEREMOVEPENDING = 32771;
/*     */   public static final int DBT_DEVICEREMOVECOMPLETE = 32772;
/*     */   public static final int DBT_DEVNODES_CHANGED = 7;
/*     */   public static final int DBT_DEVICETYPESPECIFIC = 32773;
/*     */   public static final int DBT_CUSTOMEVENT = 32774;
/*  77 */   public static final Guid.GUID GUID_DEVINTERFACE_USB_DEVICE = new Guid.GUID("{A5DCBF10-6530-11D2-901F-00C04FB951ED}");
/*     */ 
/*     */ 
/*     */   
/*  81 */   public static final Guid.GUID GUID_DEVINTERFACE_HID = new Guid.GUID("{4D1E55B2-F16F-11CF-88CB-001111000030}");
/*     */ 
/*     */   
/*  84 */   public static final Guid.GUID GUID_DEVINTERFACE_VOLUME = new Guid.GUID("{53F5630D-B6BF-11D0-94F2-00A0C91EFB8B}");
/*     */   
/*     */   public static final int DBT_DEVTYP_OEM = 0;
/*  87 */   public static final Guid.GUID GUID_DEVINTERFACE_KEYBOARD = new Guid.GUID("{884b96c3-56ef-11d1-bc8c-00a0c91405dd}"); public static final int DBT_DEVTYP_DEVNODE = 1; public static final int DBT_DEVTYP_VOLUME = 2;
/*     */   public static final int DBT_DEVTYP_PORT = 3;
/*     */   public static final int DBT_DEVTYP_NET = 4;
/*  90 */   public static final Guid.GUID GUID_DEVINTERFACE_MOUSE = new Guid.GUID("{378DE44C-56EF-11D1-BC8C-00A0C91405DD}");
/*     */   public static final int DBT_DEVTYP_DEVICEINTERFACE = 5;
/*     */   public static final int DBT_DEVTYP_HANDLE = 6;
/*     */   public static final int DBTF_MEDIA = 1;
/*     */   public static final int DBTF_NET = 2;
/*     */   
/*  96 */   public static class DEV_BROADCAST_HDR extends Structure { public static final List<String> FIELDS = createFieldsOrder(new String[] { "dbch_size", "dbch_devicetype", "dbch_reserved" });
/*     */ 
/*     */     
/*  99 */     public int dbch_size = size();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int dbch_devicetype;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int dbch_reserved;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DEV_BROADCAST_HDR() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DEV_BROADCAST_HDR(long pointer) {
/* 121 */       this(new Pointer(pointer));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DEV_BROADCAST_HDR(Pointer memory) {
/* 131 */       super(memory);
/* 132 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 137 */       return FIELDS;
/*     */     } }
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
/*     */   public static class DEV_BROADCAST_OEM
/*     */     extends Structure
/*     */   {
/* 166 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "dbco_size", "dbco_devicetype", "dbco_reserved", "dbco_identifier", "dbco_suppfunc" });
/*     */ 
/*     */ 
/*     */     
/* 170 */     public int dbco_size = size();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int dbco_devicetype;
/*     */ 
/*     */ 
/*     */     
/*     */     public int dbco_reserved;
/*     */ 
/*     */ 
/*     */     
/*     */     public int dbco_identifier;
/*     */ 
/*     */ 
/*     */     
/*     */     public int dbco_suppfunc;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DEV_BROADCAST_OEM() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DEV_BROADCAST_OEM(Pointer memory) {
/* 198 */       super(memory);
/* 199 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 204 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class DEV_BROADCAST_DEVNODE
/*     */     extends Structure
/*     */   {
/* 212 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "dbcd_size", "dbcd_devicetype", "dbcd_reserved", "dbcd_devnode" });
/*     */ 
/*     */     
/* 215 */     public int dbcd_size = size();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int dbcd_devicetype;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int dbcd_reserved;
/*     */ 
/*     */ 
/*     */     
/*     */     public int dbcd_devnode;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DEV_BROADCAST_DEVNODE() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DEV_BROADCAST_DEVNODE(Pointer memory) {
/* 240 */       super(memory);
/* 241 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 246 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class DEV_BROADCAST_VOLUME
/*     */     extends Structure
/*     */   {
/* 254 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "dbcv_size", "dbcv_devicetype", "dbcv_reserved", "dbcv_unitmask", "dbcv_flags" });
/*     */ 
/*     */ 
/*     */     
/* 258 */     public int dbcv_size = size();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int dbcv_devicetype;
/*     */ 
/*     */ 
/*     */     
/*     */     public int dbcv_reserved;
/*     */ 
/*     */ 
/*     */     
/*     */     public int dbcv_unitmask;
/*     */ 
/*     */ 
/*     */     
/*     */     public short dbcv_flags;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DEV_BROADCAST_VOLUME() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DEV_BROADCAST_VOLUME(Pointer memory) {
/* 286 */       super(memory);
/* 287 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 292 */       return FIELDS;
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
/*     */   public static class DEV_BROADCAST_PORT
/*     */     extends Structure
/*     */   {
/* 306 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "dbcp_size", "dbcp_devicetype", "dbcp_reserved", "dbcp_name" });
/*     */ 
/*     */     
/* 309 */     public int dbcp_size = size();
/*     */ 
/*     */     
/*     */     public int dbcp_devicetype;
/*     */ 
/*     */     
/*     */     public int dbcp_reserved;
/*     */ 
/*     */     
/* 318 */     public char[] dbcp_name = new char[1];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DEV_BROADCAST_PORT() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DEV_BROADCAST_PORT(Pointer memory) {
/* 334 */       super(memory);
/* 335 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 340 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class DEV_BROADCAST_NET
/*     */     extends Structure
/*     */   {
/* 348 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "dbcn_size", "dbcn_devicetype", "dbcn_reserved", "dbcn_resource", "dbcn_flags" });
/*     */ 
/*     */ 
/*     */     
/* 352 */     public int dbcn_size = size();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int dbcn_devicetype;
/*     */ 
/*     */ 
/*     */     
/*     */     public int dbcn_reserved;
/*     */ 
/*     */ 
/*     */     
/*     */     public int dbcn_resource;
/*     */ 
/*     */ 
/*     */     
/*     */     public int dbcn_flags;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DEV_BROADCAST_NET() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DEV_BROADCAST_NET(Pointer memory) {
/* 380 */       super(memory);
/* 381 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 386 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class DEV_BROADCAST_DEVICEINTERFACE
/*     */     extends Structure
/*     */   {
/* 394 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "dbcc_size", "dbcc_devicetype", "dbcc_reserved", "dbcc_classguid", "dbcc_name" });
/*     */ 
/*     */ 
/*     */     
/*     */     public int dbcc_size;
/*     */ 
/*     */     
/*     */     public int dbcc_devicetype;
/*     */ 
/*     */     
/*     */     public int dbcc_reserved;
/*     */ 
/*     */     
/*     */     public Guid.GUID dbcc_classguid;
/*     */ 
/*     */     
/* 410 */     public char[] dbcc_name = new char[1];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DEV_BROADCAST_DEVICEINTERFACE() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DEV_BROADCAST_DEVICEINTERFACE(long pointer) {
/* 426 */       this(new Pointer(pointer));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DEV_BROADCAST_DEVICEINTERFACE(Pointer memory) {
/* 436 */       super(memory);
/* 437 */       this.dbcc_size = ((Integer)readField("dbcc_size")).intValue();
/*     */       
/* 439 */       int len = 1 + this.dbcc_size - size();
/* 440 */       this.dbcc_name = new char[len];
/* 441 */       read();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getDbcc_name() {
/* 450 */       return Native.toString(this.dbcc_name);
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 455 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class DEV_BROADCAST_HANDLE
/*     */     extends Structure
/*     */   {
/* 463 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "dbch_size", "dbch_devicetype", "dbch_reserved", "dbch_handle", "dbch_hdevnotify", "dbch_eventguid", "dbch_nameoffset", "dbch_data" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 468 */     public int dbch_size = size();
/*     */ 
/*     */ 
/*     */     
/*     */     public int dbch_devicetype;
/*     */ 
/*     */ 
/*     */     
/*     */     public int dbch_reserved;
/*     */ 
/*     */ 
/*     */     
/*     */     public WinNT.HANDLE dbch_handle;
/*     */ 
/*     */ 
/*     */     
/*     */     public WinUser.HDEVNOTIFY dbch_hdevnotify;
/*     */ 
/*     */ 
/*     */     
/*     */     public Guid.GUID dbch_eventguid;
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.LONG dbch_nameoffset;
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] dbch_data;
/*     */ 
/*     */ 
/*     */     
/*     */     public DEV_BROADCAST_HANDLE() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public DEV_BROADCAST_HANDLE(Pointer memory) {
/* 505 */       super(memory);
/* 506 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 511 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/DBT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */