/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.Structure;
/*     */ import com.sun.jna.win32.W32APITypeMapper;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Winnetwk
/*     */ {
/*     */   public class RESOURCESCOPE
/*     */   {
/*     */     public static final int RESOURCE_CONNECTED = 1;
/*     */     public static final int RESOURCE_GLOBALNET = 2;
/*     */     public static final int RESOURCE_REMEMBERED = 3;
/*     */     public static final int RESOURCE_RECENT = 4;
/*     */     public static final int RESOURCE_CONTEXT = 5;
/*     */   }
/*     */   
/*     */   public class RESOURCETYPE
/*     */   {
/*     */     public static final int RESOURCETYPE_ANY = 0;
/*     */     public static final int RESOURCETYPE_DISK = 1;
/*     */     public static final int RESOURCETYPE_PRINT = 2;
/*     */     public static final int RESOURCETYPE_RESERVED = 8;
/*     */     public static final int RESOURCETYPE_UNKNOWN = -1;
/*     */   }
/*     */   
/*     */   public class RESOURCEDISPLAYTYPE
/*     */   {
/*     */     public static final int RESOURCEDISPLAYTYPE_GENERIC = 0;
/*     */     public static final int RESOURCEDISPLAYTYPE_DOMAIN = 1;
/*     */     public static final int RESOURCEDISPLAYTYPE_SERVER = 2;
/*     */     public static final int RESOURCEDISPLAYTYPE_SHARE = 3;
/*     */     public static final int RESOURCEDISPLAYTYPE_FILE = 4;
/*     */   }
/*     */   
/*     */   public class RESOURCEUSAGE
/*     */   {
/*     */     public static final int RESOURCEUSAGE_CONNECTABLE = 1;
/*     */     public static final int RESOURCEUSAGE_CONTAINER = 2;
/*     */     public static final int RESOURCEUSAGE_NOLOCALDEVICE = 4;
/*     */     public static final int RESOURCEUSAGE_SIBLING = 8;
/*     */     public static final int RESOURCEUSAGE_ATTACHED = 16;
/*     */     public static final int RESOURCEUSAGE_ALL = 19;
/*     */   }
/*     */   
/*     */   public class ConnectFlag
/*     */   {
/*     */     public static final int CONNECT_UPDATE_PROFILE = 1;
/*     */     public static final int CONNECT_INTERACTIVE = 8;
/*     */     public static final int CONNECT_PROMPT = 16;
/*     */     public static final int CONNECT_REDIRECT = 128;
/*     */     public static final int CONNECT_LOCALDRIVE = 256;
/*     */     public static final int CONNECT_COMMANDLINE = 2048;
/*     */     public static final int CONNECT_CMD_SAVECRED = 4096;
/*     */   }
/*     */   
/*     */   public static class NETRESOURCE
/*     */     extends Structure
/*     */   {
/*     */     public static class ByReference
/*     */       extends NETRESOURCE
/*     */       implements Structure.ByReference
/*     */     {
/*     */       public ByReference() {}
/*     */       
/*     */       public ByReference(Pointer memory) {
/* 292 */         super(memory);
/*     */       }
/*     */     }
/*     */     
/* 296 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "dwScope", "dwType", "dwDisplayType", "dwUsage", "lpLocalName", "lpRemoteName", "lpComment", "lpProvider" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int dwScope;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int dwType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int dwDisplayType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int dwUsage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String lpLocalName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String lpRemoteName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String lpComment;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String lpProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public NETRESOURCE() {
/* 360 */       super(W32APITypeMapper.DEFAULT);
/*     */     }
/*     */     
/*     */     public NETRESOURCE(Pointer address) {
/* 364 */       super(address, 0, W32APITypeMapper.DEFAULT);
/* 365 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 370 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 377 */   public static int UNIVERSAL_NAME_INFO_LEVEL = 1;
/* 378 */   public static int REMOTE_NAME_INFO_LEVEL = 2;
/*     */ 
/*     */ 
/*     */   
/*     */   public static class UNIVERSAL_NAME_INFO
/*     */     extends Structure
/*     */   {
/*     */     public static class ByReference
/*     */       extends Winnetwk.REMOTE_NAME_INFO
/*     */       implements Structure.ByReference
/*     */     {
/*     */       public ByReference() {}
/*     */ 
/*     */       
/*     */       public ByReference(Pointer memory) {
/* 393 */         super(memory);
/*     */       }
/*     */     }
/*     */     
/* 397 */     public static final List<String> FIELDS = createFieldsOrder("lpUniversalName");
/*     */ 
/*     */     
/*     */     public String lpUniversalName;
/*     */ 
/*     */ 
/*     */     
/*     */     public UNIVERSAL_NAME_INFO() {
/* 405 */       super(W32APITypeMapper.DEFAULT);
/*     */     }
/*     */     
/*     */     public UNIVERSAL_NAME_INFO(Pointer address) {
/* 409 */       super(address, 0, W32APITypeMapper.DEFAULT);
/* 410 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 415 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class REMOTE_NAME_INFO
/*     */     extends Structure
/*     */   {
/*     */     public static class ByReference
/*     */       extends REMOTE_NAME_INFO
/*     */       implements Structure.ByReference
/*     */     {
/*     */       public ByReference() {}
/*     */ 
/*     */ 
/*     */       
/*     */       public ByReference(Pointer memory) {
/* 434 */         super(memory);
/*     */       }
/*     */     }
/*     */     
/* 438 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "lpUniversalName", "lpConnectionName", "lpRemainingPath" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String lpUniversalName;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String lpConnectionName;
/*     */ 
/*     */ 
/*     */     
/*     */     public String lpRemainingPath;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public REMOTE_NAME_INFO() {
/* 458 */       super(W32APITypeMapper.DEFAULT);
/*     */     }
/*     */     
/*     */     public REMOTE_NAME_INFO(Pointer address) {
/* 462 */       super(address, 0, W32APITypeMapper.DEFAULT);
/* 463 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 468 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/Winnetwk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */