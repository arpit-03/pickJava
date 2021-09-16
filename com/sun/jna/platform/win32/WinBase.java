/*      */ package com.sun.jna.platform.win32;
/*      */ 
/*      */ import com.sun.jna.Callback;
/*      */ import com.sun.jna.Native;
/*      */ import com.sun.jna.Platform;
/*      */ import com.sun.jna.Pointer;
/*      */ import com.sun.jna.Structure;
/*      */ import com.sun.jna.Union;
/*      */ import com.sun.jna.ptr.ByteByReference;
/*      */ import com.sun.jna.win32.StdCallLibrary;
/*      */ import com.sun.jna.win32.W32APITypeMapper;
/*      */ import java.text.DateFormat;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
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
/*      */ public interface WinBase
/*      */   extends WinDef, BaseTSD
/*      */ {
/*   52 */   public static final WinNT.HANDLE INVALID_HANDLE_VALUE = new WinNT.HANDLE(
/*   53 */       Pointer.createConstant((Pointer.SIZE == 8) ? -1L : 4294967295L)); public static final int WAIT_FAILED = -1; public static final int WAIT_OBJECT_0 = 0; public static final int WAIT_ABANDONED = 128; public static final int WAIT_ABANDONED_0 = 128; public static final int LOGON32_LOGON_INTERACTIVE = 2; public static final int LOGON32_LOGON_NETWORK = 3; public static final int LOGON32_LOGON_BATCH = 4; public static final int LOGON32_LOGON_SERVICE = 5; public static final int LOGON32_LOGON_UNLOCK = 7; public static final int LOGON32_LOGON_NETWORK_CLEARTEXT = 8; public static final int LOGON32_LOGON_NEW_CREDENTIALS = 9; public static final int LOGON32_PROVIDER_DEFAULT = 0; public static final int LOGON32_PROVIDER_WINNT35 = 1; public static final int LOGON32_PROVIDER_WINNT40 = 2; public static final int LOGON32_PROVIDER_WINNT50 = 3; public static final int HANDLE_FLAG_INHERIT = 1; public static final int HANDLE_FLAG_PROTECT_FROM_CLOSE = 2; public static final int STARTF_USESHOWWINDOW = 1; public static final int STARTF_USESIZE = 2; public static final int STARTF_USEPOSITION = 4; public static final int STARTF_USECOUNTCHARS = 8; public static final int STARTF_USEFILLATTRIBUTE = 16; public static final int STARTF_RUNFULLSCREEN = 32; public static final int STARTF_FORCEONFEEDBACK = 64; public static final int STARTF_FORCEOFFFEEDBACK = 128; public static final int STARTF_USESTDHANDLES = 256; public static final int DEBUG_PROCESS = 1; public static final int DEBUG_ONLY_THIS_PROCESS = 2; public static final int CREATE_SUSPENDED = 4; public static final int DETACHED_PROCESS = 8; public static final int CREATE_NEW_CONSOLE = 16; public static final int CREATE_NEW_PROCESS_GROUP = 512; public static final int CREATE_UNICODE_ENVIRONMENT = 1024; public static final int CREATE_SEPARATE_WOW_VDM = 2048; public static final int CREATE_SHARED_WOW_VDM = 4096; public static final int CREATE_FORCEDOS = 8192; public static final int INHERIT_PARENT_AFFINITY = 65536; public static final int CREATE_PROTECTED_PROCESS = 262144; public static final int EXTENDED_STARTUPINFO_PRESENT = 524288; public static final int CREATE_BREAKAWAY_FROM_JOB = 16777216; public static final int CREATE_PRESERVE_CODE_AUTHZ_LEVEL = 33554432; public static final int CREATE_DEFAULT_ERROR_MODE = 67108864; public static final int CREATE_NO_WINDOW = 134217728; public static final int FILE_ENCRYPTABLE = 0; public static final int FILE_IS_ENCRYPTED = 1; public static final int FILE_SYSTEM_ATTR = 2; public static final int FILE_ROOT_DIR = 3; public static final int FILE_SYSTEM_DIR = 4; public static final int FILE_UNKNOWN = 5; public static final int FILE_SYSTEM_NOT_SUPPORT = 6; public static final int FILE_USER_DISALLOWED = 7; public static final int FILE_READ_ONLY = 8; public static final int FILE_DIR_DISALOWED = 9; public static final int CREATE_FOR_IMPORT = 1; public static final int CREATE_FOR_DIR = 2; public static final int OVERWRITE_HIDDEN = 4; public static final int INVALID_FILE_SIZE = -1; public static final int INVALID_SET_FILE_POINTER = -1; public static final int INVALID_FILE_ATTRIBUTES = -1; public static final int STILL_ACTIVE = 259; public static final int FileBasicInfo = 0; public static final int FileStandardInfo = 1; public static final int FileNameInfo = 2; public static final int FileRenameInfo = 3; public static final int FileDispositionInfo = 4; public static final int FileAllocationInfo = 5; public static final int FileEndOfFileInfo = 6; public static final int FileStreamInfo = 7; public static final int FileCompressionInfo = 8; public static final int FileAttributeTagInfo = 9; public static final int FileIdBothDirectoryInfo = 10; public static final int FileIdBothDirectoryRestartInfo = 11; public static final int FileIoPriorityHintInfo = 12; public static final int FileRemoteProtocolInfo = 13; public static final int FileFullDirectoryInfo = 14; public static final int FileFullDirectoryRestartInfo = 15;
/*      */   public static final int FileStorageInfo = 16;
/*      */   public static final int FileAlignmentInfo = 17;
/*      */   public static final int FileIdInfo = 18;
/*      */   public static final int FileIdExtdDirectoryInfo = 19;
/*      */   public static final int FileIdExtdDirectoryRestartInfo = 20;
/*      */   public static final int FindExInfoStandard = 0;
/*      */   public static final int FindExInfoBasic = 1;
/*      */   public static final int FindExInfoMaxInfoLevel = 2;
/*      */   public static final int FindExSearchNameMatch = 0;
/*      */   public static final int FindExSearchLimitToDirectories = 1;
/*      */   public static final int FindExSearchLimitToDevices = 2;
/*   65 */   public static final int MAX_COMPUTERNAME_LENGTH = Platform.isMac() ? 15 : 31;
/*      */   
/*      */   public static final int LMEM_FIXED = 0;
/*      */   
/*      */   public static final int LMEM_MOVEABLE = 2;
/*      */   
/*      */   public static final int LMEM_NOCOMPACT = 16;
/*      */   
/*      */   public static final int LMEM_NODISCARD = 32;
/*      */   
/*      */   public static final int LMEM_ZEROINIT = 64;
/*      */   
/*      */   public static final int LMEM_MODIFY = 128;
/*      */   
/*      */   public static final int LMEM_DISCARDABLE = 3840;
/*      */   
/*      */   public static final int LMEM_VALID_FLAGS = 3954;
/*      */   
/*      */   public static final int LMEM_INVALID_HANDLE = 32768;
/*      */   
/*      */   public static final int LHND = 66;
/*      */   
/*      */   public static final int LPTR = 64;
/*      */   
/*      */   public static final int LMEM_DISCARDED = 16384;
/*      */   
/*      */   public static final int LMEM_LOCKCOUNT = 255;
/*      */   
/*      */   public static final int FORMAT_MESSAGE_ALLOCATE_BUFFER = 256;
/*      */   
/*      */   public static final int FORMAT_MESSAGE_IGNORE_INSERTS = 512;
/*      */   
/*      */   public static final int FORMAT_MESSAGE_FROM_STRING = 1024;
/*      */   
/*      */   public static final int FORMAT_MESSAGE_FROM_HMODULE = 2048;
/*      */   
/*      */   public static final int FORMAT_MESSAGE_FROM_SYSTEM = 4096;
/*      */   
/*      */   public static final int FORMAT_MESSAGE_ARGUMENT_ARRAY = 8192;
/*      */   
/*      */   public static final int DRIVE_UNKNOWN = 0;
/*      */   
/*      */   public static final int DRIVE_NO_ROOT_DIR = 1;
/*      */   
/*      */   public static final int DRIVE_REMOVABLE = 2;
/*      */   
/*      */   public static final int DRIVE_FIXED = 3;
/*      */   
/*      */   public static final int DRIVE_REMOTE = 4;
/*      */   
/*      */   public static final int DRIVE_CDROM = 5;
/*      */   
/*      */   public static final int DRIVE_RAMDISK = 6;
/*      */   
/*      */   public static final int INFINITE = -1;
/*      */   
/*      */   public static final int MOVEFILE_COPY_ALLOWED = 2;
/*      */   
/*      */   public static final int MOVEFILE_CREATE_HARDLINK = 16;
/*      */   
/*      */   public static final int MOVEFILE_DELAY_UNTIL_REBOOT = 4;
/*      */   
/*      */   public static final int MOVEFILE_FAIL_IF_NOT_TRACKABLE = 32;
/*      */   
/*      */   public static final int MOVEFILE_REPLACE_EXISTING = 1;
/*      */   
/*      */   public static final int MOVEFILE_WRITE_THROUGH = 8;
/*      */   
/*      */   public static final int PIPE_CLIENT_END = 0;
/*      */   
/*      */   public static final int PIPE_SERVER_END = 1;
/*      */   
/*      */   public static final int PIPE_ACCESS_DUPLEX = 3;
/*      */   
/*      */   public static final int PIPE_ACCESS_INBOUND = 1;
/*      */   
/*      */   public static final int PIPE_ACCESS_OUTBOUND = 2;
/*      */   
/*      */   public static final int PIPE_TYPE_BYTE = 0;
/*      */   
/*      */   public static final int PIPE_TYPE_MESSAGE = 4;
/*      */   
/*      */   public static final int PIPE_READMODE_BYTE = 0;
/*      */   
/*      */   public static final int PIPE_READMODE_MESSAGE = 2;
/*      */   
/*      */   public static final int PIPE_WAIT = 0;
/*      */   
/*      */   public static final int PIPE_NOWAIT = 1;
/*      */   
/*      */   public static final int PIPE_ACCEPT_REMOTE_CLIENTS = 0;
/*      */   
/*      */   public static final int PIPE_REJECT_REMOTE_CLIENTS = 8;
/*      */   
/*      */   public static final int PIPE_UNLIMITED_INSTANCES = 255;
/*      */   
/*      */   public static final int NMPWAIT_USE_DEFAULT_WAIT = 0;
/*      */   
/*      */   public static final int NMPWAIT_NOWAIT = 1;
/*      */   
/*      */   public static final int NMPWAIT_WAIT_FOREVER = -1;
/*      */   
/*      */   public static final int NOPARITY = 0;
/*      */   
/*      */   public static final int ODDPARITY = 1;
/*      */   
/*      */   public static final int EVENPARITY = 2;
/*      */   
/*      */   public static final int MARKPARITY = 3;
/*      */   
/*      */   public static final int SPACEPARITY = 4;
/*      */   
/*      */   public static final int ONESTOPBIT = 0;
/*      */   
/*      */   public static final int ONE5STOPBITS = 1;
/*      */   
/*      */   public static final int TWOSTOPBITS = 2;
/*      */   
/*      */   public static final int CBR_110 = 110;
/*      */   
/*      */   public static final int CBR_300 = 300;
/*      */   
/*      */   public static final int CBR_600 = 600;
/*      */   
/*      */   public static final int CBR_1200 = 1200;
/*      */   
/*      */   public static final int CBR_2400 = 2400;
/*      */   
/*      */   public static final int CBR_4800 = 4800;
/*      */   
/*      */   public static final int CBR_9600 = 9600;
/*      */   
/*      */   public static final int CBR_14400 = 14400;
/*      */   
/*      */   public static final int CBR_19200 = 19200;
/*      */   
/*      */   public static final int CBR_38400 = 38400;
/*      */   public static final int CBR_56000 = 56000;
/*      */   public static final int CBR_128000 = 128000;
/*      */   public static final int CBR_256000 = 256000;
/*      */   public static final int DTR_CONTROL_DISABLE = 0;
/*      */   public static final int DTR_CONTROL_ENABLE = 1;
/*      */   public static final int DTR_CONTROL_HANDSHAKE = 2;
/*      */   public static final int RTS_CONTROL_DISABLE = 0;
/*      */   public static final int RTS_CONTROL_ENABLE = 1;
/*      */   public static final int RTS_CONTROL_HANDSHAKE = 2;
/*      */   public static final int RTS_CONTROL_TOGGLE = 3;
/*      */   public static final int ES_AWAYMODE_REQUIRED = 64;
/*      */   public static final int ES_CONTINUOUS = -2147483648;
/*      */   public static final int ES_DISPLAY_REQUIRED = 2;
/*      */   public static final int ES_SYSTEM_REQUIRED = 1;
/*      */   public static final int ES_USER_PRESENT = 4;
/*      */   
/*      */   public static class FILE_BASIC_INFO
/*      */     extends Structure
/*      */   {
/*      */     public WinNT.LARGE_INTEGER CreationTime;
/*      */     public WinNT.LARGE_INTEGER LastAccessTime;
/*      */     public WinNT.LARGE_INTEGER LastWriteTime;
/*      */     public WinNT.LARGE_INTEGER ChangeTime;
/*      */     public int FileAttributes;
/*      */     
/*      */     public static class ByReference
/*      */       extends FILE_BASIC_INFO
/*      */       implements Structure.ByReference
/*      */     {
/*      */       public ByReference(Pointer memory) {
/*  232 */         super(memory);
/*      */       }
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
/*      */       public ByReference() {}
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
/*      */     public static int sizeOf() {
/*  266 */       return Native.getNativeSize(FILE_BASIC_INFO.class, null);
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  271 */       return Arrays.asList(new String[] { "CreationTime", "LastAccessTime", "LastWriteTime", "ChangeTime", "FileAttributes" });
/*      */     }
/*      */ 
/*      */     
/*      */     public FILE_BASIC_INFO() {}
/*      */ 
/*      */     
/*      */     public FILE_BASIC_INFO(Pointer memory) {
/*  279 */       super(memory);
/*  280 */       read();
/*      */       
/*  282 */       this.CreationTime = new WinNT.LARGE_INTEGER(this.CreationTime.getValue());
/*  283 */       this.LastAccessTime = new WinNT.LARGE_INTEGER(this.LastAccessTime.getValue());
/*  284 */       this.LastWriteTime = new WinNT.LARGE_INTEGER(this.LastWriteTime.getValue());
/*  285 */       this.ChangeTime = new WinNT.LARGE_INTEGER(this.ChangeTime.getValue());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FILE_BASIC_INFO(WinBase.FILETIME CreationTime, WinBase.FILETIME LastAccessTime, WinBase.FILETIME LastWriteTime, WinBase.FILETIME ChangeTime, int FileAttributes) {
/*  293 */       this.CreationTime = new WinNT.LARGE_INTEGER(CreationTime.toTime());
/*  294 */       this.LastAccessTime = new WinNT.LARGE_INTEGER(LastAccessTime.toTime());
/*  295 */       this.LastWriteTime = new WinNT.LARGE_INTEGER(LastWriteTime.toTime());
/*  296 */       this.ChangeTime = new WinNT.LARGE_INTEGER(ChangeTime.toTime());
/*  297 */       this.FileAttributes = FileAttributes;
/*  298 */       write();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FILE_BASIC_INFO(WinNT.LARGE_INTEGER CreationTime, WinNT.LARGE_INTEGER LastAccessTime, WinNT.LARGE_INTEGER LastWriteTime, WinNT.LARGE_INTEGER ChangeTime, int FileAttributes) {
/*  306 */       this.CreationTime = CreationTime;
/*  307 */       this.LastAccessTime = LastAccessTime;
/*  308 */       this.LastWriteTime = LastWriteTime;
/*  309 */       this.ChangeTime = ChangeTime;
/*  310 */       this.FileAttributes = FileAttributes;
/*  311 */       write();
/*      */     } }
/*      */   
/*      */   public static class FILE_STANDARD_INFO extends Structure {
/*      */     public WinNT.LARGE_INTEGER AllocationSize;
/*      */     public WinNT.LARGE_INTEGER EndOfFile;
/*      */     public int NumberOfLinks;
/*      */     public boolean DeletePending;
/*      */     public boolean Directory;
/*      */     
/*      */     public static class ByReference extends FILE_STANDARD_INFO implements Structure.ByReference {
/*      */       public ByReference() {}
/*      */       
/*      */       public ByReference(Pointer memory) {
/*  325 */         super(memory);
/*      */       }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static int sizeOf() {
/*  356 */       return Native.getNativeSize(FILE_STANDARD_INFO.class, null);
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  361 */       return Arrays.asList(new String[] { "AllocationSize", "EndOfFile", "NumberOfLinks", "DeletePending", "Directory" });
/*      */     }
/*      */ 
/*      */     
/*      */     public FILE_STANDARD_INFO() {}
/*      */ 
/*      */     
/*      */     public FILE_STANDARD_INFO(Pointer memory) {
/*  369 */       super(memory);
/*  370 */       read();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FILE_STANDARD_INFO(WinNT.LARGE_INTEGER AllocationSize, WinNT.LARGE_INTEGER EndOfFile, int NumberOfLinks, boolean DeletePending, boolean Directory) {
/*  378 */       this.AllocationSize = AllocationSize;
/*  379 */       this.EndOfFile = EndOfFile;
/*  380 */       this.NumberOfLinks = NumberOfLinks;
/*  381 */       this.DeletePending = DeletePending;
/*  382 */       this.Directory = Directory;
/*  383 */       write();
/*      */     }
/*      */   }
/*      */   
/*      */   public static class FILE_DISPOSITION_INFO
/*      */     extends Structure {
/*      */     public boolean DeleteFile;
/*      */     
/*      */     public static class ByReference
/*      */       extends FILE_DISPOSITION_INFO
/*      */       implements Structure.ByReference {
/*      */       public ByReference() {}
/*      */       
/*      */       public ByReference(Pointer memory) {
/*  397 */         super(memory);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static int sizeOf() {
/*  409 */       return Native.getNativeSize(FILE_DISPOSITION_INFO.class, null);
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  414 */       return Arrays.asList(new String[] { "DeleteFile" });
/*      */     }
/*      */ 
/*      */     
/*      */     public FILE_DISPOSITION_INFO() {}
/*      */ 
/*      */     
/*      */     public FILE_DISPOSITION_INFO(Pointer memory) {
/*  422 */       super(memory);
/*  423 */       read();
/*      */     }
/*      */     
/*      */     public FILE_DISPOSITION_INFO(boolean DeleteFile) {
/*  427 */       this.DeleteFile = DeleteFile;
/*  428 */       write();
/*      */     } }
/*      */   
/*      */   public static class FILE_COMPRESSION_INFO extends Structure {
/*      */     public WinNT.LARGE_INTEGER CompressedFileSize;
/*      */     public short CompressionFormat;
/*      */     public byte CompressionUnitShift;
/*      */     public byte ChunkShift;
/*      */     public byte ClusterShift;
/*      */     
/*      */     public static class ByReference extends FILE_COMPRESSION_INFO implements Structure.ByReference {
/*      */       public ByReference() {}
/*      */       
/*      */       public ByReference(Pointer memory) {
/*  442 */         super(memory);
/*      */       }
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
/*  474 */     public byte[] Reserved = new byte[3];
/*      */ 
/*      */     
/*      */     public static int sizeOf() {
/*  478 */       return Native.getNativeSize(FILE_COMPRESSION_INFO.class, null);
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  483 */       return Arrays.asList(new String[] { "CompressedFileSize", "CompressionFormat", "CompressionUnitShift", "ChunkShift", "ClusterShift", "Reserved" });
/*      */     }
/*      */     
/*      */     public FILE_COMPRESSION_INFO() {
/*  487 */       super(W32APITypeMapper.DEFAULT);
/*      */     }
/*      */     
/*      */     public FILE_COMPRESSION_INFO(Pointer memory) {
/*  491 */       super(memory, 0, W32APITypeMapper.DEFAULT);
/*  492 */       read();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FILE_COMPRESSION_INFO(WinNT.LARGE_INTEGER CompressedFileSize, short CompressionFormat, byte CompressionUnitShift, byte ChunkShift, byte ClusterShift) {
/*  500 */       this.CompressedFileSize = CompressedFileSize;
/*  501 */       this.CompressionFormat = CompressionFormat;
/*  502 */       this.CompressionUnitShift = CompressionUnitShift;
/*  503 */       this.ChunkShift = ChunkShift;
/*  504 */       this.ClusterShift = ClusterShift;
/*  505 */       this.Reserved = new byte[3];
/*  506 */       write();
/*      */     }
/*      */   }
/*      */   
/*      */   public static class FILE_ATTRIBUTE_TAG_INFO
/*      */     extends Structure {
/*      */     public int FileAttributes;
/*      */     public int ReparseTag;
/*      */     
/*      */     public static class ByReference
/*      */       extends FILE_ATTRIBUTE_TAG_INFO implements Structure.ByReference {
/*      */       public ByReference() {}
/*      */       
/*      */       public ByReference(Pointer memory) {
/*  520 */         super(memory);
/*      */       }
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
/*      */     public static int sizeOf() {
/*  536 */       return Native.getNativeSize(FILE_ATTRIBUTE_TAG_INFO.class, null);
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  541 */       return Arrays.asList(new String[] { "FileAttributes", "ReparseTag" });
/*      */     }
/*      */ 
/*      */     
/*      */     public FILE_ATTRIBUTE_TAG_INFO() {}
/*      */ 
/*      */     
/*      */     public FILE_ATTRIBUTE_TAG_INFO(Pointer memory) {
/*  549 */       super(memory);
/*  550 */       read();
/*      */     }
/*      */ 
/*      */     
/*      */     public FILE_ATTRIBUTE_TAG_INFO(int FileAttributes, int ReparseTag) {
/*  555 */       this.FileAttributes = FileAttributes;
/*  556 */       this.ReparseTag = ReparseTag;
/*  557 */       write();
/*      */     }
/*      */   }
/*      */   
/*      */   public static class FILE_ID_INFO
/*      */     extends Structure
/*      */   {
/*      */     public long VolumeSerialNumber;
/*      */     public FILE_ID_128 FileId;
/*      */     
/*      */     public static class ByReference
/*      */       extends FILE_ID_INFO
/*      */       implements Structure.ByReference {
/*      */       public ByReference() {}
/*      */       
/*      */       public ByReference(Pointer memory) {
/*  573 */         super(memory);
/*      */       }
/*      */     }
/*      */     
/*      */     public static class FILE_ID_128 extends Structure {
/*  578 */       public WinDef.BYTE[] Identifier = new WinDef.BYTE[16];
/*      */ 
/*      */       
/*      */       protected List<String> getFieldOrder() {
/*  582 */         return Arrays.asList(new String[] { "Identifier" });
/*      */       }
/*      */ 
/*      */       
/*      */       public FILE_ID_128() {}
/*      */ 
/*      */       
/*      */       public FILE_ID_128(Pointer memory) {
/*  590 */         super(memory);
/*  591 */         read();
/*      */       }
/*      */       
/*      */       public FILE_ID_128(WinDef.BYTE[] Identifier) {
/*  595 */         this.Identifier = Identifier;
/*  596 */         write();
/*      */       }
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
/*      */     public static int sizeOf() {
/*  612 */       return Native.getNativeSize(FILE_ID_INFO.class, null);
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  617 */       return Arrays.asList(new String[] { "VolumeSerialNumber", "FileId" });
/*      */     }
/*      */ 
/*      */     
/*      */     public FILE_ID_INFO() {}
/*      */ 
/*      */     
/*      */     public FILE_ID_INFO(Pointer memory) {
/*  625 */       super(memory);
/*  626 */       read();
/*      */     }
/*      */ 
/*      */     
/*      */     public FILE_ID_INFO(long VolumeSerialNumber, FILE_ID_128 FileId) {
/*  631 */       this.VolumeSerialNumber = VolumeSerialNumber;
/*  632 */       this.FileId = FileId;
/*  633 */       write();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class WIN32_FIND_DATA
/*      */     extends Structure
/*      */   {
/*      */     public int dwFileAttributes;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinBase.FILETIME ftCreationTime;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinBase.FILETIME ftLastAccessTime;
/*      */ 
/*      */ 
/*      */     
/*      */     public WinBase.FILETIME ftLastWriteTime;
/*      */ 
/*      */ 
/*      */     
/*      */     public int nFileSizeHigh;
/*      */ 
/*      */ 
/*      */     
/*      */     public int nFileSizeLow;
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwReserved0;
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwReserved1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends WIN32_FIND_DATA
/*      */       implements Structure.ByReference
/*      */     {
/*      */       public ByReference() {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public ByReference(Pointer memory) {
/*  689 */         super(memory);
/*      */       }
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
/*      */ 
/*      */ 
/*      */     
/*  766 */     public char[] cFileName = new char[260];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  774 */     public char[] cAlternateFileName = new char[14];
/*      */     
/*      */     public static int sizeOf() {
/*  777 */       return Native.getNativeSize(WIN32_FIND_DATA.class, null);
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  782 */       return Arrays.asList(new String[] { "dwFileAttributes", "ftCreationTime", "ftLastAccessTime", "ftLastWriteTime", "nFileSizeHigh", "nFileSizeLow", "dwReserved0", "dwReserved1", "cFileName", "cAlternateFileName" });
/*      */     }
/*      */     
/*      */     public WIN32_FIND_DATA() {
/*  786 */       super(W32APITypeMapper.DEFAULT);
/*      */     }
/*      */     
/*      */     public WIN32_FIND_DATA(Pointer memory) {
/*  790 */       super(memory, 0, W32APITypeMapper.DEFAULT);
/*  791 */       read();
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
/*      */     public WIN32_FIND_DATA(int dwFileAttributes, WinBase.FILETIME ftCreationTime, WinBase.FILETIME ftLastAccessTime, WinBase.FILETIME ftLastWriteTime, int nFileSizeHigh, int nFileSizeLow, int dwReserved0, int dwReserved1, char[] cFileName, char[] cAlternateFileName) {
/*  804 */       this.dwFileAttributes = dwFileAttributes;
/*  805 */       this.ftCreationTime = ftCreationTime;
/*  806 */       this.ftLastAccessTime = ftLastAccessTime;
/*  807 */       this.ftLastWriteTime = ftLastWriteTime;
/*  808 */       this.nFileSizeHigh = nFileSizeHigh;
/*  809 */       this.nFileSizeLow = nFileSizeLow;
/*  810 */       this.dwReserved0 = dwReserved0;
/*  811 */       this.cFileName = cFileName;
/*  812 */       this.cAlternateFileName = cAlternateFileName;
/*  813 */       write();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getFileName() {
/*  820 */       return Native.toString(this.cFileName);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getAlternateFileName() {
/*  827 */       return Native.toString(this.cAlternateFileName);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class FILETIME
/*      */     extends Structure
/*      */   {
/*      */     public int dwLowDateTime;
/*      */     
/*      */     public int dwHighDateTime;
/*      */     
/*      */     private static final long EPOCH_DIFF = 11644473600000L;
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  843 */       return Arrays.asList(new String[] { "dwLowDateTime", "dwHighDateTime" });
/*      */     }
/*      */     
/*      */     public static class ByReference
/*      */       extends FILETIME implements Structure.ByReference {
/*      */       public ByReference() {}
/*      */       
/*      */       public ByReference(Pointer memory) {
/*  851 */         super(memory);
/*      */       }
/*      */     }
/*      */     
/*      */     public FILETIME(Date date) {
/*  856 */       long rawValue = dateToFileTime(date);
/*  857 */       this.dwHighDateTime = (int)(rawValue >> 32L & 0xFFFFFFFFL);
/*  858 */       this.dwLowDateTime = (int)(rawValue & 0xFFFFFFFFL);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FILETIME(WinNT.LARGE_INTEGER ft) {
/*  866 */       this.dwHighDateTime = ft.getHigh().intValue();
/*  867 */       this.dwLowDateTime = ft.getLow().intValue();
/*      */     }
/*      */ 
/*      */     
/*      */     public FILETIME() {}
/*      */     
/*      */     public FILETIME(Pointer memory) {
/*  874 */       super(memory);
/*  875 */       read();
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
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static Date filetimeToDate(int high, int low) {
/*  901 */       long filetime = high << 32L | low & 0xFFFFFFFFL;
/*  902 */       long ms_since_16010101 = filetime / 10000L;
/*  903 */       long ms_since_19700101 = ms_since_16010101 - 11644473600000L;
/*  904 */       return new Date(ms_since_19700101);
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
/*      */     public static long dateToFileTime(Date date) {
/*  916 */       long ms_since_19700101 = date.getTime();
/*  917 */       long ms_since_16010101 = ms_since_19700101 + 11644473600000L;
/*  918 */       return ms_since_16010101 * 1000L * 10L;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Date toDate() {
/*  926 */       return filetimeToDate(this.dwHighDateTime, this.dwLowDateTime);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long toTime() {
/*  936 */       return toDate().getTime();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public long toLong() {
/*  948 */       return toDate().getTime();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORDLONG toDWordLong() {
/*  959 */       return new WinDef.DWORDLONG(this.dwHighDateTime << 32L | this.dwLowDateTime & 0xFFFFFFFFL);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  964 */       return super.toString() + ": " + toDate().toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class SYSTEMTIME
/*      */     extends Structure
/*      */   {
/*      */     public short wYear;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public short wMonth;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public short wDayOfWeek;
/*      */ 
/*      */ 
/*      */     
/*      */     public short wDay;
/*      */ 
/*      */ 
/*      */     
/*      */     public short wHour;
/*      */ 
/*      */ 
/*      */     
/*      */     public short wMinute;
/*      */ 
/*      */ 
/*      */     
/*      */     public short wSecond;
/*      */ 
/*      */ 
/*      */     
/*      */     public short wMilliseconds;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SYSTEMTIME() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SYSTEMTIME(Date date) {
/* 1016 */       this(date.getTime());
/*      */     }
/*      */     
/*      */     public SYSTEMTIME(long timestamp) {
/* 1020 */       Calendar cal = Calendar.getInstance();
/* 1021 */       cal.setTimeInMillis(timestamp);
/* 1022 */       fromCalendar(cal);
/*      */     }
/*      */     
/*      */     public SYSTEMTIME(Calendar cal) {
/* 1026 */       fromCalendar(cal);
/*      */     }
/*      */     
/*      */     public void fromCalendar(Calendar cal) {
/* 1030 */       this.wYear = (short)cal.get(1);
/* 1031 */       this.wMonth = (short)(1 + cal.get(2) - 0);
/* 1032 */       this.wDay = (short)cal.get(5);
/* 1033 */       this.wHour = (short)cal.get(11);
/* 1034 */       this.wMinute = (short)cal.get(12);
/* 1035 */       this.wSecond = (short)cal.get(13);
/* 1036 */       this.wMilliseconds = (short)cal.get(14);
/* 1037 */       this.wDayOfWeek = (short)(cal.get(7) - 1);
/*      */     }
/*      */     
/*      */     public Calendar toCalendar() {
/* 1041 */       Calendar cal = Calendar.getInstance();
/* 1042 */       cal.set(1, this.wYear);
/* 1043 */       cal.set(2, 0 + this.wMonth - 1);
/* 1044 */       cal.set(5, this.wDay);
/* 1045 */       cal.set(11, this.wHour);
/* 1046 */       cal.set(12, this.wMinute);
/* 1047 */       cal.set(13, this.wSecond);
/* 1048 */       cal.set(14, this.wMilliseconds);
/* 1049 */       return cal;
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 1054 */       return Arrays.asList(new String[] { "wYear", "wMonth", "wDayOfWeek", "wDay", "wHour", "wMinute", "wSecond", "wMilliseconds" });
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1060 */       if (this.wYear == 0 && this.wMonth == 0 && this.wDay == 0 && this.wHour == 0 && this.wMinute == 0 && this.wSecond == 0 && this.wMilliseconds == 0)
/*      */       {
/*      */         
/* 1063 */         return super.toString();
/*      */       }
/*      */       
/* 1066 */       DateFormat dtf = DateFormat.getDateTimeInstance();
/* 1067 */       Calendar cal = toCalendar();
/* 1068 */       return dtf.format(cal.getTime());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class TIME_ZONE_INFORMATION
/*      */     extends Structure
/*      */   {
/*      */     public WinDef.LONG Bias;
/*      */     
/*      */     public String StandardName;
/*      */     public WinBase.SYSTEMTIME StandardDate;
/*      */     public WinDef.LONG StandardBias;
/*      */     public String DaylightName;
/*      */     public WinBase.SYSTEMTIME DaylightDate;
/*      */     public WinDef.LONG DaylightBias;
/*      */     
/*      */     public TIME_ZONE_INFORMATION() {
/* 1086 */       super(W32APITypeMapper.DEFAULT);
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 1091 */       return Arrays.asList(new String[] { "Bias", "StandardName", "StandardDate", "StandardBias", "DaylightName", "DaylightDate", "DaylightBias" });
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
/*      */   public static class OVERLAPPED
/*      */     extends Structure
/*      */   {
/*      */     public BaseTSD.ULONG_PTR Internal;
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
/*      */     public BaseTSD.ULONG_PTR InternalHigh;
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
/*      */     public int Offset;
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
/*      */     public int OffsetHigh;
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
/*      */     public WinNT.HANDLE hEvent;
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
/* 1184 */       return Arrays.asList(new String[] { "Internal", "InternalHigh", "Offset", "OffsetHigh", "hEvent" });
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class SYSTEM_INFO
/*      */     extends Structure
/*      */   {
/*      */     public UNION processorArchitecture;
/*      */     
/*      */     public WinDef.DWORD dwPageSize;
/*      */     
/*      */     public Pointer lpMinimumApplicationAddress;
/*      */     
/*      */     public Pointer lpMaximumApplicationAddress;
/*      */     
/*      */     public BaseTSD.DWORD_PTR dwActiveProcessorMask;
/*      */     
/*      */     public WinDef.DWORD dwNumberOfProcessors;
/*      */     
/*      */     public WinDef.DWORD dwProcessorType;
/*      */     
/*      */     public WinDef.DWORD dwAllocationGranularity;
/*      */     public WinDef.WORD wProcessorLevel;
/*      */     public WinDef.WORD wProcessorRevision;
/*      */     
/*      */     public static class PI
/*      */       extends Structure
/*      */     {
/*      */       public WinDef.WORD wProcessorArchitecture;
/*      */       public WinDef.WORD wReserved;
/*      */       
/*      */       public static class ByReference
/*      */         extends PI
/*      */         implements Structure.ByReference {}
/*      */       
/*      */       protected List<String> getFieldOrder() {
/* 1221 */         return Arrays.asList(new String[] { "wProcessorArchitecture", "wReserved" });
/*      */       }
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
/*      */     public static class UNION
/*      */       extends Union
/*      */     {
/*      */       public WinDef.DWORD dwOemID;
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
/*      */       public WinBase.SYSTEM_INFO.PI pi;
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
/*      */       public static class ByReference
/*      */         extends UNION
/*      */         implements Structure.ByReference {}
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
/*      */     protected List<String> getFieldOrder() {
/* 1296 */       return Arrays.asList(new String[] { "processorArchitecture", "dwPageSize", "lpMinimumApplicationAddress", "lpMaximumApplicationAddress", "dwActiveProcessorMask", "dwNumberOfProcessors", "dwProcessorType", "dwAllocationGranularity", "wProcessorLevel", "wProcessorRevision" });
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class MEMORYSTATUSEX
/*      */     extends Structure
/*      */   {
/*      */     public WinDef.DWORD dwLength;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD dwMemoryLoad;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORDLONG ullTotalPhys;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORDLONG ullAvailPhys;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORDLONG ullTotalPageFile;
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORDLONG ullAvailPageFile;
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORDLONG ullTotalVirtual;
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORDLONG ullAvailVirtual;
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORDLONG ullAvailExtendedVirtual;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 1349 */       return Arrays.asList(new String[] { "dwLength", "dwMemoryLoad", "ullTotalPhys", "ullAvailPhys", "ullTotalPageFile", "ullAvailPageFile", "ullTotalVirtual", "ullAvailVirtual", "ullAvailExtendedVirtual" });
/*      */     }
/*      */     
/*      */     public MEMORYSTATUSEX() {
/* 1353 */       this.dwLength = new WinDef.DWORD(size());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class SECURITY_ATTRIBUTES
/*      */     extends Structure
/*      */   {
/*      */     public WinDef.DWORD dwLength;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Pointer lpSecurityDescriptor;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean bInheritHandle;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 1383 */       return Arrays.asList(new String[] { "dwLength", "lpSecurityDescriptor", "bInheritHandle" });
/*      */     }
/*      */     
/*      */     public SECURITY_ATTRIBUTES() {
/* 1387 */       this.dwLength = new WinDef.DWORD(size());
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
/*      */   public static class STARTUPINFO
/*      */     extends Structure
/*      */   {
/*      */     public WinDef.DWORD cb;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String lpReserved;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String lpDesktop;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String lpTitle;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD dwX;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD dwY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD dwXSize;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD dwYSize;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD dwXCountChars;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD dwYCountChars;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD dwFillAttribute;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwFlags;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.WORD wShowWindow;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.WORD cbReserved2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ByteByReference lpReserved2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinNT.HANDLE hStdInput;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinNT.HANDLE hStdOutput;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinNT.HANDLE hStdError;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 1560 */       return Arrays.asList(new String[] { "cb", "lpReserved", "lpDesktop", "lpTitle", "dwX", "dwY", "dwXSize", "dwYSize", "dwXCountChars", "dwYCountChars", "dwFillAttribute", "dwFlags", "wShowWindow", "cbReserved2", "lpReserved2", "hStdInput", "hStdOutput", "hStdError" });
/*      */     }
/*      */     
/*      */     public STARTUPINFO() {
/* 1564 */       super(W32APITypeMapper.DEFAULT);
/* 1565 */       this.cb = new WinDef.DWORD(size());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class PROCESS_INFORMATION
/*      */     extends Structure
/*      */   {
/*      */     public WinNT.HANDLE hProcess;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinNT.HANDLE hThread;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD dwProcessId;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD dwThreadId;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 1608 */       return Arrays.asList(new String[] { "hProcess", "hThread", "dwProcessId", "dwThreadId" });
/*      */     }
/*      */     
/*      */     public static class ByReference
/*      */       extends PROCESS_INFORMATION implements Structure.ByReference {
/*      */       public ByReference() {}
/*      */       
/*      */       public ByReference(Pointer memory) {
/* 1616 */         super(memory);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public PROCESS_INFORMATION() {}
/*      */     
/*      */     public PROCESS_INFORMATION(Pointer memory) {
/* 1624 */       super(memory);
/* 1625 */       read();
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
/*      */   public static interface THREAD_START_ROUTINE
/*      */     extends StdCallLibrary.StdCallCallback
/*      */   {
/*      */     WinDef.DWORD apply(WinDef.LPVOID param1LPVOID);
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
/*      */   public static class FOREIGN_THREAD_START_ROUTINE
/*      */     extends Structure
/*      */   {
/*      */     WinDef.LPVOID foreignLocation;
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
/*      */     protected List<String> getFieldOrder() {
/* 1701 */       return Arrays.asList(new String[] { "foreignLocation" });
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
/*      */   public static interface COMPUTER_NAME_FORMAT
/*      */   {
/*      */     public static final int ComputerNameNetBIOS = 0;
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
/*      */     public static final int ComputerNameDnsHostname = 1;
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
/*      */     public static final int ComputerNameDnsDomain = 2;
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
/*      */     public static final int ComputerNameDnsFullyQualified = 3;
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
/*      */     public static final int ComputerNamePhysicalNetBIOS = 4;
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
/*      */     public static final int ComputerNamePhysicalDnsHostname = 5;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final int ComputerNamePhysicalDnsDomain = 6;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final int ComputerNamePhysicalDnsFullyQualified = 7;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final int ComputerNameMax = 8;
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
/*      */   public static interface FE_EXPORT_FUNC
/*      */     extends StdCallLibrary.StdCallCallback
/*      */   {
/*      */     WinDef.DWORD callback(Pointer param1Pointer1, Pointer param1Pointer2, WinDef.ULONG param1ULONG);
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
/*      */   public static interface FE_IMPORT_FUNC
/*      */     extends StdCallLibrary.StdCallCallback
/*      */   {
/*      */     WinDef.DWORD callback(Pointer param1Pointer1, Pointer param1Pointer2, WinDef.ULONGByReference param1ULONGByReference);
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
/*      */   public static class COMMTIMEOUTS
/*      */     extends Structure
/*      */   {
/* 1847 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "ReadIntervalTimeout", "ReadTotalTimeoutMultiplier", "ReadTotalTimeoutConstant", "WriteTotalTimeoutMultiplier", "WriteTotalTimeoutConstant" });
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
/*      */     public WinDef.DWORD ReadIntervalTimeout;
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
/*      */     public WinDef.DWORD ReadTotalTimeoutMultiplier;
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
/*      */     public WinDef.DWORD ReadTotalTimeoutConstant;
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
/*      */     public WinDef.DWORD WriteTotalTimeoutMultiplier;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD WriteTotalTimeoutConstant;
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
/* 1913 */       return FIELDS;
/*      */     } }
/*      */   public static class DCB extends Structure { public WinDef.DWORD DCBlength; public WinDef.DWORD BaudRate; public DCBControllBits controllBits; public WinDef.WORD wReserved; public WinDef.WORD XonLim;
/*      */     public WinDef.WORD XoffLim;
/*      */     public WinDef.BYTE ByteSize;
/*      */     public WinDef.BYTE Parity;
/*      */     public WinDef.BYTE StopBits;
/*      */     public char XonChar;
/*      */     public char XoffChar;
/*      */     public char ErrorChar;
/*      */     public char EofChar;
/*      */     public char EvtChar;
/*      */     public WinDef.WORD wReserved1;
/*      */     
/*      */     public static class DCBControllBits extends WinDef.DWORD { private static final long serialVersionUID = 8574966619718078579L;
/*      */       
/*      */       public String toString() {
/* 1930 */         StringBuilder stringBuilder = new StringBuilder();
/* 1931 */         stringBuilder.append('<');
/* 1932 */         stringBuilder.append("fBinary:1=");
/* 1933 */         stringBuilder.append(getfBinary() ? 49 : 48);
/* 1934 */         stringBuilder.append(", fParity:1=");
/* 1935 */         stringBuilder.append(getfParity() ? 49 : 48);
/* 1936 */         stringBuilder.append(", fOutxCtsFlow:1=");
/* 1937 */         stringBuilder.append(getfOutxCtsFlow() ? 49 : 48);
/* 1938 */         stringBuilder.append(", fOutxDsrFlow:1=");
/* 1939 */         stringBuilder.append(getfOutxDsrFlow() ? 49 : 48);
/* 1940 */         stringBuilder.append(", fDtrControl:2=");
/* 1941 */         stringBuilder.append(getfDtrControl());
/* 1942 */         stringBuilder.append(", fDsrSensitivity:1=");
/* 1943 */         stringBuilder.append(getfDsrSensitivity() ? 49 : 48);
/* 1944 */         stringBuilder.append(", fTXContinueOnXoff:1=");
/* 1945 */         stringBuilder.append(getfTXContinueOnXoff() ? 49 : 48);
/* 1946 */         stringBuilder.append(", fOutX:1=");
/* 1947 */         stringBuilder.append(getfOutX() ? 49 : 48);
/* 1948 */         stringBuilder.append(", fInX:1=");
/* 1949 */         stringBuilder.append(getfInX() ? 49 : 48);
/* 1950 */         stringBuilder.append(", fErrorChar:1=");
/* 1951 */         stringBuilder.append(getfErrorChar() ? 49 : 48);
/* 1952 */         stringBuilder.append(", fNull:1=");
/* 1953 */         stringBuilder.append(getfNull() ? 49 : 48);
/* 1954 */         stringBuilder.append(", fRtsControl:2=");
/* 1955 */         stringBuilder.append(getfRtsControl());
/* 1956 */         stringBuilder.append(", fAbortOnError:1=");
/* 1957 */         stringBuilder.append(getfAbortOnError() ? 49 : 48);
/* 1958 */         stringBuilder.append(", fDummy2:17=");
/* 1959 */         stringBuilder.append(getfDummy2());
/* 1960 */         stringBuilder.append('>');
/* 1961 */         return stringBuilder.toString();
/*      */       }
/*      */       
/*      */       public boolean getfAbortOnError() {
/* 1965 */         return ((intValue() & 0x4000) != 0);
/*      */       }
/*      */       
/*      */       public boolean getfBinary() {
/* 1969 */         return ((intValue() & 0x1) != 0);
/*      */       }
/*      */       
/*      */       public boolean getfDsrSensitivity() {
/* 1973 */         return ((intValue() & 0x40) != 0);
/*      */       }
/*      */       
/*      */       public int getfDtrControl() {
/* 1977 */         return intValue() >>> 4 & 0x3;
/*      */       }
/*      */       
/*      */       public boolean getfErrorChar() {
/* 1981 */         return ((intValue() & 0x400) != 0);
/*      */       }
/*      */       
/*      */       public boolean getfInX() {
/* 1985 */         return ((intValue() & 0x200) != 0);
/*      */       }
/*      */       
/*      */       public boolean getfNull() {
/* 1989 */         return ((intValue() & 0x800) != 0);
/*      */       }
/*      */       
/*      */       public boolean getfOutX() {
/* 1993 */         return ((intValue() & 0x100) != 0);
/*      */       }
/*      */       
/*      */       public boolean getfOutxCtsFlow() {
/* 1997 */         return ((intValue() & 0x4) != 0);
/*      */       }
/*      */       
/*      */       public boolean getfOutxDsrFlow() {
/* 2001 */         return ((intValue() & 0x8) != 0);
/*      */       }
/*      */       
/*      */       public boolean getfParity() {
/* 2005 */         return ((intValue() & 0x2) != 0);
/*      */       }
/*      */       
/*      */       public int getfRtsControl() {
/* 2009 */         return intValue() >>> 12 & 0x3;
/*      */       }
/*      */       
/*      */       public int getfDummy2() {
/* 2013 */         return intValue() >>> 15 & 0x1FFFF;
/*      */       }
/*      */       
/*      */       public boolean getfTXContinueOnXoff() {
/* 2017 */         return ((intValue() & 0x80) != 0);
/*      */       }
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
/*      */       public void setfAbortOnError(boolean fAbortOnError) {
/* 2030 */         int tmp = leftShiftMask(fAbortOnError ? 1 : 0, (byte)14, 1, intValue());
/* 2031 */         setValue(tmp);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setfBinary(boolean fBinary) {
/* 2042 */         int tmp = leftShiftMask(fBinary ? 1 : 0, (byte)0, 1, intValue());
/* 2043 */         setValue(tmp);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setfDsrSensitivity(boolean fDsrSensitivity) {
/* 2055 */         int tmp = leftShiftMask(fDsrSensitivity ? 1 : 0, (byte)6, 1, intValue());
/* 2056 */         setValue(tmp);
/*      */       }
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
/*      */       public void setfDtrControl(int fOutxDsrFlow) {
/* 2070 */         int tmp = leftShiftMask(fOutxDsrFlow, (byte)4, 3, intValue());
/* 2071 */         setValue(tmp);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setfErrorChar(boolean fErrorChar) {
/* 2083 */         int tmp = leftShiftMask(fErrorChar ? 1 : 0, (byte)10, 1, intValue());
/* 2084 */         setValue(tmp);
/*      */       }
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
/*      */       public void setfInX(boolean fInX) {
/* 2097 */         int tmp = leftShiftMask(fInX ? 1 : 0, (byte)9, 1, intValue());
/* 2098 */         setValue(tmp);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setfNull(boolean fNull) {
/* 2107 */         int tmp = leftShiftMask(fNull ? 1 : 0, (byte)11, 1, intValue());
/* 2108 */         setValue(tmp);
/*      */       }
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
/*      */       public void setfOutX(boolean fOutX) {
/* 2121 */         int tmp = leftShiftMask(fOutX ? 1 : 0, (byte)8, 1, intValue());
/* 2122 */         setValue(tmp);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setfOutxCtsFlow(boolean fOutxCtsFlow) {
/* 2134 */         int tmp = leftShiftMask(fOutxCtsFlow ? 1 : 0, (byte)2, 1, intValue());
/* 2135 */         setValue(tmp);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setfOutxDsrFlow(boolean fOutxDsrFlow) {
/* 2147 */         int tmp = leftShiftMask(fOutxDsrFlow ? 1 : 0, (byte)3, 1, intValue());
/* 2148 */         setValue(tmp);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setfParity(boolean fParity) {
/* 2158 */         int tmp = leftShiftMask(fParity ? 1 : 0, (byte)1, 1, intValue());
/* 2159 */         setValue(tmp);
/*      */       }
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
/*      */       public void setfRtsControl(int fRtsControl) {
/* 2174 */         int tmp = leftShiftMask(fRtsControl, (byte)12, 3, intValue());
/* 2175 */         setValue(tmp);
/*      */       }
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
/*      */       public void setfTXContinueOnXoff(boolean fTXContinueOnXoff) {
/* 2189 */         int tmp = leftShiftMask(fTXContinueOnXoff ? 1 : 0, (byte)7, 1, intValue());
/* 2190 */         setValue(tmp);
/*      */       }
/*      */ 
/*      */       
/*      */       private static int leftShiftMask(int valuetoset, byte shift, int mask, int storage) {
/* 2195 */         int tmp = storage;
/* 2196 */         tmp &= mask << shift ^ 0xFFFFFFFF;
/* 2197 */         tmp |= (valuetoset & mask) << shift;
/* 2198 */         return tmp;
/*      */       } }
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
/*      */     public DCB() {
/* 2317 */       this.DCBlength = new WinDef.DWORD(size());
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 2322 */       return Arrays.asList(new String[] { "DCBlength", "BaudRate", "controllBits", "wReserved", "XonLim", "XoffLim", "ByteSize", "Parity", "StopBits", "XonChar", "XoffChar", "ErrorChar", "EofChar", "EvtChar", "wReserved1" });
/*      */     } }
/*      */ 
/*      */   
/*      */   public static interface EnumResTypeProc extends Callback {
/*      */     boolean invoke(WinDef.HMODULE param1HMODULE, Pointer param1Pointer1, Pointer param1Pointer2);
/*      */   }
/*      */   
/*      */   public static interface EnumResNameProc extends Callback {
/*      */     boolean invoke(WinDef.HMODULE param1HMODULE, Pointer param1Pointer1, Pointer param1Pointer2, Pointer param1Pointer3);
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/WinBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */