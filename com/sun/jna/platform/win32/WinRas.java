/*      */ package com.sun.jna.platform.win32;
/*      */ 
/*      */ import com.sun.jna.Memory;
/*      */ import com.sun.jna.Native;
/*      */ import com.sun.jna.Pointer;
/*      */ import com.sun.jna.Structure;
/*      */ import com.sun.jna.Union;
/*      */ import com.sun.jna.win32.StdCallLibrary;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
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
/*      */ public interface WinRas
/*      */ {
/*      */   public static final int ERROR_BUFFER_TOO_SMALL = 603;
/*      */   public static final int ERROR_CANNOT_FIND_PHONEBOOK_ENTRY = 623;
/*      */   public static final int MAX_PATH = 260;
/*      */   public static final int UNLEN = 256;
/*      */   public static final int PWLEN = 256;
/*      */   public static final int DNLEN = 15;
/*      */   public static final int RAS_MaxEntryName = 256;
/*      */   public static final int RAS_MaxPhoneNumber = 128;
/*      */   public static final int RAS_MaxCallbackNumber = 128;
/*      */   public static final int RAS_MaxDeviceType = 16;
/*      */   public static final int RAS_MaxDeviceName = 128;
/*      */   public static final int RAS_MaxDnsSuffix = 256;
/*      */   public static final int RAS_MaxAreaCode = 10;
/*      */   public static final int RAS_MaxX25Address = 200;
/*      */   public static final int RAS_MaxIpAddress = 15;
/*      */   public static final int RAS_MaxFacilities = 200;
/*      */   public static final int RAS_MaxUserData = 200;
/*      */   public static final int RAS_MaxPadType = 32;
/*      */   public static final int RASCS_Connected = 8192;
/*      */   public static final int RASCS_Disconnected = 8193;
/*      */   public static final int RASCM_UserName = 1;
/*      */   public static final int RASCM_Password = 2;
/*      */   public static final int RASCM_Domain = 4;
/*      */   public static final int RASTUNNELENDPOINT_IPv4 = 1;
/*      */   public static final int RASTUNNELENDPOINT_IPv6 = 2;
/*      */   public static final String RASDT_Modem = "modem";
/*      */   
/*      */   public static class RASEAPINFO
/*      */     extends Structure
/*      */   {
/*      */     public int dwSizeofEapInfo;
/*      */     public Pointer pbEapInfo;
/*      */     
/*      */     public RASEAPINFO() {}
/*      */     
/*      */     public RASEAPINFO(Pointer memory) {
/*   89 */       super(memory);
/*   90 */       read();
/*      */     }
/*      */     
/*      */     public RASEAPINFO(byte[] data) {
/*   94 */       this.pbEapInfo = (Pointer)new Memory(data.length);
/*   95 */       this.pbEapInfo.write(0L, data, 0, data.length);
/*   96 */       this.dwSizeofEapInfo = data.length;
/*   97 */       allocateMemory();
/*      */     }
/*      */     
/*      */     public RASEAPINFO(String s) {
/*  101 */       this(Native.toByteArray(s));
/*      */     }
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
/*      */     protected List<String> getFieldOrder() {
/*  115 */       return Arrays.asList(new String[] { "dwSizeofEapInfo", "pbEapInfo" });
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public byte[] getData() {
/*  123 */       return (this.pbEapInfo == null) ? null : this.pbEapInfo.getByteArray(0L, this.dwSizeofEapInfo);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class RASDEVSPECIFICINFO
/*      */     extends Structure
/*      */   {
/*      */     public int dwSize;
/*      */     public Pointer pbDevSpecificInfo;
/*      */     
/*      */     public RASDEVSPECIFICINFO() {}
/*      */     
/*      */     public RASDEVSPECIFICINFO(Pointer memory) {
/*  137 */       super(memory);
/*  138 */       read();
/*      */     }
/*      */     
/*      */     public RASDEVSPECIFICINFO(byte[] data) {
/*  142 */       this.pbDevSpecificInfo = (Pointer)new Memory(data.length);
/*  143 */       this.pbDevSpecificInfo.write(0L, data, 0, data.length);
/*  144 */       this.dwSize = data.length;
/*  145 */       allocateMemory();
/*      */     }
/*      */     
/*      */     public RASDEVSPECIFICINFO(String s) {
/*  149 */       this(Native.toByteArray(s));
/*      */     }
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
/*      */     protected List<String> getFieldOrder() {
/*  163 */       return Arrays.asList(new String[] { "dwSize", "pbDevSpecificInfo" });
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public byte[] getData() {
/*  171 */       return (this.pbDevSpecificInfo == null) ? null : this.pbDevSpecificInfo.getByteArray(0L, this.dwSize);
/*      */     } }
/*      */   
/*      */   public static class RASDIALEXTENSIONS extends Structure { public int dwSize;
/*      */     public int dwfOptions;
/*      */     public WinDef.HWND hwndParent;
/*      */     public BaseTSD.ULONG_PTR reserved;
/*      */     public BaseTSD.ULONG_PTR reserved1;
/*      */     public WinRas.RASEAPINFO RasEapInfo;
/*      */     public WinDef.BOOL fSkipPppAuth;
/*      */     public WinRas.RASDEVSPECIFICINFO RasDevSpecificInfo;
/*      */     
/*      */     public RASDIALEXTENSIONS() {
/*  184 */       this.dwSize = size();
/*      */     }
/*      */     
/*      */     public RASDIALEXTENSIONS(Pointer memory) {
/*  188 */       super(memory);
/*  189 */       read();
/*      */     }
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
/*      */     public static class ByReference
/*      */       extends RASDIALEXTENSIONS
/*      */       implements Structure.ByReference {}
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
/*      */     protected List<String> getFieldOrder() {
/*  233 */       return Arrays.asList(new String[] { "dwSize", "dwfOptions", "hwndParent", "reserved", "reserved1", "RasEapInfo", "fSkipPppAuth", "RasDevSpecificInfo" });
/*      */     } }
/*      */ 
/*      */   
/*      */   public static class RASDIALPARAMS
/*      */     extends Structure
/*      */   {
/*      */     public int dwSize;
/*      */     
/*      */     public RASDIALPARAMS() {
/*  243 */       this.dwSize = size();
/*      */     }
/*      */     
/*      */     public RASDIALPARAMS(Pointer memory) {
/*  247 */       super(memory);
/*  248 */       read();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends RASDIALPARAMS
/*      */       implements Structure.ByReference {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  262 */     public char[] szEntryName = new char[257];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  267 */     public char[] szPhoneNumber = new char[129];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  273 */     public char[] szCallbackNumber = new char[129];
/*      */ 
/*      */ 
/*      */     
/*  277 */     public char[] szUserName = new char[257];
/*      */ 
/*      */ 
/*      */     
/*  281 */     public char[] szPassword = new char[257];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  286 */     public char[] szDomain = new char[16];
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  290 */       return Arrays.asList(new String[] { "dwSize", "szEntryName", "szPhoneNumber", "szCallbackNumber", "szUserName", "szPassword", "szDomain" });
/*      */     }
/*      */   }
/*      */   
/*      */   public static class RASCONN
/*      */     extends Structure {
/*      */     public int dwSize;
/*      */     public WinNT.HANDLE hrasconn;
/*      */     
/*      */     public RASCONN() {
/*  300 */       this.dwSize = size();
/*      */     }
/*      */     
/*      */     public RASCONN(Pointer memory) {
/*  304 */       super(memory);
/*  305 */       read();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends RASCONN
/*      */       implements Structure.ByReference {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  323 */     public char[] szEntryName = new char[257];
/*      */ 
/*      */ 
/*      */     
/*  327 */     public char[] szDeviceType = new char[17];
/*      */ 
/*      */ 
/*      */     
/*  331 */     public char[] szDeviceName = new char[129];
/*      */ 
/*      */ 
/*      */     
/*  335 */     public char[] szPhonebook = new char[260];
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwSubEntry;
/*      */ 
/*      */ 
/*      */     
/*      */     public Guid.GUID guidEntry;
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwFlags;
/*      */ 
/*      */ 
/*      */     
/*      */     public WinNT.LUID luid;
/*      */ 
/*      */ 
/*      */     
/*      */     public Guid.GUID guidCorrelationId;
/*      */ 
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  360 */       return Arrays.asList(new String[] { "dwSize", "hrasconn", "szEntryName", "szDeviceType", "szDeviceName", "szPhonebook", "dwSubEntry", "guidEntry", "dwFlags", "luid", "guidCorrelationId" });
/*      */     }
/*      */   }
/*      */   
/*      */   public static class RAS_STATS extends Structure {
/*      */     public int dwSize;
/*      */     public int dwBytesXmited;
/*      */     public int dwBytesRcved;
/*      */     
/*      */     public RAS_STATS() {
/*  370 */       this.dwSize = size();
/*      */     }
/*      */     public int dwFramesXmited; public int dwFramesRcved; public int dwCrcErr; public int dwTimeoutErr; public int dwAlignmentErr; public int dwHardwareOverrunErr;
/*      */     public RAS_STATS(Pointer memory) {
/*  374 */       super(memory);
/*  375 */       read();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwFramingErr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwBufferOverrunErr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwCompressionRatioIn;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwCompressionRatioOut;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwBps;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwConnectDuration;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  441 */       return Arrays.asList(new String[] { "dwSize", "dwBytesXmited", "dwBytesRcved", "dwFramesXmited", "dwFramesRcved", "dwCrcErr", "dwTimeoutErr", "dwAlignmentErr", "dwHardwareOverrunErr", "dwFramingErr", "dwBufferOverrunErr", "dwCompressionRatioIn", "dwCompressionRatioOut", "dwBps", "dwConnectDuration" });
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class RASIPV4ADDR
/*      */     extends Structure
/*      */   {
/*      */     public RASIPV4ADDR() {}
/*      */ 
/*      */     
/*      */     public RASIPV4ADDR(Pointer memory) {
/*  454 */       super(memory);
/*  455 */       read();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  461 */     public byte[] addr = new byte[8];
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  465 */       return Arrays.asList(new String[] { "addr" });
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class RASIPV6ADDR
/*      */     extends Structure
/*      */   {
/*      */     public RASIPV6ADDR() {}
/*      */ 
/*      */     
/*      */     public RASIPV6ADDR(Pointer memory) {
/*  478 */       super(memory);
/*  479 */       read();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  485 */     public byte[] addr = new byte[16];
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  489 */       return Arrays.asList(new String[] { "addr" });
/*      */     }
/*      */   }
/*      */   
/*      */   public static class RASPPPIP
/*      */     extends Structure {
/*      */     public int dwSize;
/*      */     public int dwError;
/*      */     
/*      */     public RASPPPIP() {
/*  499 */       this.dwSize = size();
/*      */     }
/*      */     
/*      */     public RASPPPIP(Pointer memory) {
/*  503 */       super(memory);
/*  504 */       read();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends RASPPPIP
/*      */       implements Structure.ByReference {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  523 */     public char[] szIpAddress = new char[16];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  530 */     public char[] szServerIpAddress = new char[16];
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwOptions;
/*      */ 
/*      */     
/*      */     public int dwServerOptions;
/*      */ 
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  542 */       return Arrays.asList(new String[] { "dwSize", "dwError", "szIpAddress", "szServerIpAddress", "dwOptions", "dwServerOptions" });
/*      */     }
/*      */   }
/*      */   
/*      */   public static class RASTUNNELENDPOINT
/*      */     extends Structure
/*      */   {
/*      */     public int dwType;
/*      */     public UNION u;
/*      */     
/*      */     public RASTUNNELENDPOINT() {}
/*      */     
/*      */     public RASTUNNELENDPOINT(Pointer memory) {
/*  555 */       super(memory);
/*  556 */       read();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public static class UNION
/*      */       extends Union
/*      */     {
/*      */       public WinRas.RASIPV4ADDR ipv4;
/*      */ 
/*      */       
/*      */       public WinRas.RASIPV6ADDR ipv6;
/*      */ 
/*      */ 
/*      */       
/*      */       public static class ByReference
/*      */         extends UNION
/*      */         implements Structure.ByReference {}
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  579 */       return Arrays.asList(new String[] { "dwType", "u" });
/*      */     }
/*      */ 
/*      */     
/*      */     public void read() {
/*  584 */       super.read();
/*      */       
/*  586 */       switch (this.dwType) {
/*      */         case 1:
/*  588 */           this.u.setType(WinRas.RASIPV4ADDR.class);
/*      */           break;
/*      */         case 2:
/*  591 */           this.u.setType(WinRas.RASIPV6ADDR.class);
/*      */           break;
/*      */         default:
/*  594 */           this.u.setType(WinRas.RASIPV4ADDR.class);
/*      */           break;
/*      */       } 
/*      */       
/*  598 */       this.u.read();
/*      */     }
/*      */   }
/*      */   
/*      */   public static class RASCONNSTATUS extends Structure {
/*      */     public int dwSize;
/*      */     public int rasconnstate;
/*      */     public int dwError;
/*      */     
/*      */     public RASCONNSTATUS() {
/*  608 */       this.dwSize = size();
/*      */     }
/*      */     
/*      */     public RASCONNSTATUS(Pointer memory) {
/*  612 */       super(memory);
/*  613 */       read();
/*      */     }
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
/*  634 */     public char[] szDeviceType = new char[17];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  640 */     public char[] szDeviceName = new char[129];
/*      */ 
/*      */ 
/*      */     
/*  644 */     public char[] szPhoneNumber = new char[129];
/*      */ 
/*      */ 
/*      */     
/*      */     public WinRas.RASTUNNELENDPOINT localEndPoint;
/*      */ 
/*      */ 
/*      */     
/*      */     public WinRas.RASTUNNELENDPOINT remoteEndPoint;
/*      */ 
/*      */     
/*      */     public int rasconnsubstate;
/*      */ 
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  660 */       return Arrays.asList(new String[] { "dwSize", "rasconnstate", "dwError", "szDeviceType", "szDeviceName", "szPhoneNumber", "localEndPoint", "remoteEndPoint", "rasconnsubstate" });
/*      */     }
/*      */   }
/*      */   
/*      */   public static class RASCREDENTIALS
/*      */     extends Structure {
/*      */     public int dwSize;
/*      */     public int dwMask;
/*      */     
/*      */     public RASCREDENTIALS() {
/*  670 */       this.dwSize = size();
/*      */     }
/*      */     
/*      */     public RASCREDENTIALS(Pointer memory) {
/*  674 */       super(memory);
/*  675 */       read();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends RASCREDENTIALS
/*      */       implements Structure.ByReference {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  692 */     public char[] szUserName = new char[257];
/*      */ 
/*      */ 
/*      */     
/*  696 */     public char[] szPassword = new char[257];
/*      */ 
/*      */ 
/*      */     
/*  700 */     public char[] szDomain = new char[16];
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  704 */       return Arrays.asList(new String[] { "dwSize", "dwMask", "szUserName", "szPassword", "szDomain" });
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class RASIPADDR
/*      */     extends Structure
/*      */   {
/*      */     public RASIPADDR() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public RASIPADDR(Pointer memory) {
/*  718 */       super(memory);
/*  719 */       read();
/*      */     }
/*      */     
/*  722 */     public byte[] addr = new byte[4];
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  726 */       return Arrays.asList(new String[] { "addr" });
/*      */     }
/*      */   }
/*      */   
/*      */   public static class RASENTRY extends Structure {
/*      */     public int dwSize;
/*      */     public int dwfOptions;
/*      */     public int dwCountryID;
/*      */     public int dwCountryCode;
/*      */     
/*      */     public RASENTRY() {
/*  737 */       this.dwSize = size();
/*      */     }
/*      */     
/*      */     public RASENTRY(Pointer memory) {
/*  741 */       super(memory);
/*  742 */       read();
/*      */     }
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
/*      */     public static class ByReference
/*      */       extends RASENTRY
/*      */       implements Structure.ByReference {}
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
/*  775 */     public char[] szAreaCode = new char[11];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  780 */     public char[] szLocalPhoneNumber = new char[129];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwAlternateOffset;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinRas.RASIPADDR ipaddr;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinRas.RASIPADDR ipaddrDns;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinRas.RASIPADDR ipaddrDnsAlt;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinRas.RASIPADDR ipaddrWins;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinRas.RASIPADDR ipaddrWinsAlt;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwFrameSize;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwfNetProtocols;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwFramingProtocol;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  830 */     public char[] szScript = new char[260];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  835 */     public char[] szAutodialDll = new char[260];
/*      */ 
/*      */ 
/*      */     
/*  839 */     public char[] szAutodialFunc = new char[260];
/*      */ 
/*      */ 
/*      */     
/*  843 */     public char[] szDeviceType = new char[17];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  848 */     public char[] szDeviceName = new char[129];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  853 */     public char[] szX25PadType = new char[33];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  858 */     public char[] szX25Address = new char[201];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  863 */     public char[] szX25Facilities = new char[201];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  868 */     public char[] szX25UserData = new char[201];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwChannels;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwReserved1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwReserved2;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwSubEntries;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwDialMode;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwDialExtraPercent;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwDialExtraSampleSeconds;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwHangUpExtraPercent;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwHangUpExtraSampleSeconds;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwIdleDisconnectSeconds;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwType;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwEncryptionType;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwCustomAuthKey;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Guid.GUID guidId;
/*      */ 
/*      */ 
/*      */     
/*  942 */     public char[] szCustomDialDll = new char[260];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwVpnStrategy;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwfOptions2;
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwfOptions3;
/*      */ 
/*      */ 
/*      */     
/*  960 */     public char[] szDnsSuffix = new char[256];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwTcpWindowSize;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  970 */     public char[] szPrerequisitePbk = new char[260];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  977 */     public char[] szPrerequisiteEntry = new char[257];
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwRedialCount;
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwRedialPause;
/*      */ 
/*      */ 
/*      */     
/*      */     public WinRas.RASIPV6ADDR ipv6addrDns;
/*      */ 
/*      */ 
/*      */     
/*      */     public WinRas.RASIPV6ADDR ipv6addrDnsAlt;
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwIPv4InterfaceMetric;
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwIPv6InterfaceMetric;
/*      */ 
/*      */ 
/*      */     
/*      */     public WinRas.RASIPV6ADDR ipv6addr;
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwIPv6PrefixLength;
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwNetworkOutageTime;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 1019 */       return Arrays.asList(new String[] { "dwSize", "dwfOptions", "dwCountryID", "dwCountryCode", "szAreaCode", "szLocalPhoneNumber", "dwAlternateOffset", "ipaddr", "ipaddrDns", "ipaddrDnsAlt", "ipaddrWins", "ipaddrWinsAlt", "dwFrameSize", "dwfNetProtocols", "dwFramingProtocol", "szScript", "szAutodialDll", "szAutodialFunc", "szDeviceType", "szDeviceName", "szX25PadType", "szX25Address", "szX25Facilities", "szX25UserData", "dwChannels", "dwReserved1", "dwReserved2", "dwSubEntries", "dwDialMode", "dwDialExtraPercent", "dwDialExtraSampleSeconds", "dwHangUpExtraPercent", "dwHangUpExtraSampleSeconds", "dwIdleDisconnectSeconds", "dwType", "dwEncryptionType", "dwCustomAuthKey", "guidId", "szCustomDialDll", "dwVpnStrategy", "dwfOptions2", "dwfOptions3", "szDnsSuffix", "dwTcpWindowSize", "szPrerequisitePbk", "szPrerequisiteEntry", "dwRedialCount", "dwRedialPause", "ipv6addrDns", "ipv6addrDnsAlt", "dwIPv4InterfaceMetric", "dwIPv6InterfaceMetric", "ipv6addr", "dwIPv6PrefixLength", "dwNetworkOutageTime" });
/*      */     }
/*      */   }
/*      */   
/*      */   public static interface RasDialFunc2 extends StdCallLibrary.StdCallCallback {
/*      */     int dialNotification(int param1Int1, int param1Int2, WinNT.HANDLE param1HANDLE, int param1Int3, int param1Int4, int param1Int5, int param1Int6);
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/WinRas.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */