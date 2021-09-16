/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ import com.sun.jna.Native;
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.Structure;
/*     */ import com.sun.jna.Union;
/*     */ import com.sun.jna.win32.W32APITypeMapper;
/*     */ import java.util.Arrays;
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
/*     */ public interface Ntifs
/*     */   extends WinDef, BaseTSD
/*     */ {
/*     */   public static final int MAXIMUM_REPARSE_DATA_BUFFER_SIZE = 16384;
/*     */   public static final int REPARSE_BUFFER_HEADER_SIZE = 8;
/*     */   public static final int SYMLINK_FLAG_RELATIVE = 1;
/*     */   
/*     */   public static class SymbolicLinkReparseBuffer
/*     */     extends Structure
/*     */   {
/*     */     public static class ByReference
/*     */       extends SymbolicLinkReparseBuffer
/*     */       implements Structure.ByReference
/*     */     {
/*     */       public ByReference() {}
/*     */       
/*     */       public ByReference(Pointer memory) {
/*  57 */         super(memory);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  65 */     public short SubstituteNameOffset = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  71 */     public short SubstituteNameLength = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     public short PrintNameOffset = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  83 */     public short PrintNameLength = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  91 */     public int Flags = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     public char[] PathBuffer = new char[8192];
/*     */     
/*     */     public static int sizeOf() {
/* 105 */       return Native.getNativeSize(Ntifs.MountPointReparseBuffer.class, null);
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 110 */       return Arrays.asList(new String[] { "SubstituteNameOffset", "SubstituteNameLength", "PrintNameOffset", "PrintNameLength", "Flags", "PathBuffer" });
/*     */     }
/*     */     
/*     */     public SymbolicLinkReparseBuffer() {
/* 114 */       super(W32APITypeMapper.UNICODE);
/*     */     }
/*     */     
/*     */     public SymbolicLinkReparseBuffer(Pointer memory) {
/* 118 */       super(memory, 0, W32APITypeMapper.UNICODE);
/* 119 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     public SymbolicLinkReparseBuffer(String substituteName, String printName, int Flags) {
/* 124 */       String bothNames = substituteName + printName;
/* 125 */       this.PathBuffer = bothNames.toCharArray();
/* 126 */       this.SubstituteNameOffset = 0;
/* 127 */       this.SubstituteNameLength = (short)(substituteName.length() * 2);
/* 128 */       this.PrintNameOffset = (short)(substituteName.length() * 2);
/* 129 */       this.PrintNameLength = (short)(printName.length() * 2);
/* 130 */       this.Flags = Flags;
/* 131 */       write();
/*     */     }
/*     */ 
/*     */     
/*     */     public SymbolicLinkReparseBuffer(short SubstituteNameOffset, short SubstituteNameLength, short PrintNameOffset, short PrintNameLength, int Flags, String PathBuffer) {
/* 136 */       this.SubstituteNameOffset = SubstituteNameOffset;
/* 137 */       this.SubstituteNameLength = SubstituteNameLength;
/* 138 */       this.PrintNameOffset = PrintNameOffset;
/* 139 */       this.PrintNameLength = PrintNameLength;
/* 140 */       this.Flags = Flags;
/* 141 */       this.PathBuffer = PathBuffer.toCharArray();
/* 142 */       write();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getPrintName() {
/* 149 */       return String.copyValueOf(this.PathBuffer, this.PrintNameOffset / 2, this.PrintNameLength / 2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getSubstituteName() {
/* 156 */       return String.copyValueOf(this.PathBuffer, this.SubstituteNameOffset / 2, this.SubstituteNameLength / 2);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class MountPointReparseBuffer
/*     */     extends Structure {
/*     */     public static class ByReference
/*     */       extends MountPointReparseBuffer implements Structure.ByReference {
/*     */       public ByReference() {}
/*     */       
/*     */       public ByReference(Pointer memory) {
/* 167 */         super(memory);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 175 */     public short SubstituteNameOffset = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 181 */     public short SubstituteNameLength = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 187 */     public short PrintNameOffset = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 193 */     public short PrintNameLength = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 204 */     public char[] PathBuffer = new char[8192];
/*     */     
/*     */     public static int sizeOf() {
/* 207 */       return Native.getNativeSize(MountPointReparseBuffer.class, null);
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 212 */       return Arrays.asList(new String[] { "SubstituteNameOffset", "SubstituteNameLength", "PrintNameOffset", "PrintNameLength", "PathBuffer" });
/*     */     }
/*     */     
/*     */     public MountPointReparseBuffer() {
/* 216 */       super(W32APITypeMapper.UNICODE);
/*     */     }
/*     */     
/*     */     public MountPointReparseBuffer(Pointer memory) {
/* 220 */       super(memory, 0, W32APITypeMapper.UNICODE);
/* 221 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     public MountPointReparseBuffer(String substituteName, String printName) {
/* 226 */       String bothNames = substituteName + printName;
/* 227 */       this.PathBuffer = bothNames.toCharArray();
/* 228 */       this.SubstituteNameOffset = 0;
/* 229 */       this.SubstituteNameLength = (short)substituteName.length();
/* 230 */       this.PrintNameOffset = (short)(substituteName.length() * 2);
/* 231 */       this.PrintNameLength = (short)(printName.length() * 2);
/* 232 */       write();
/*     */     }
/*     */ 
/*     */     
/*     */     public MountPointReparseBuffer(short SubstituteNameOffset, short SubstituteNameLength, short PrintNameOffset, short PrintNameLength, String PathBuffer) {
/* 237 */       this.SubstituteNameOffset = SubstituteNameOffset;
/* 238 */       this.SubstituteNameLength = SubstituteNameLength;
/* 239 */       this.PrintNameOffset = PrintNameOffset;
/* 240 */       this.PrintNameLength = PrintNameLength;
/* 241 */       this.PathBuffer = PathBuffer.toCharArray();
/* 242 */       write();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class GenericReparseBuffer
/*     */     extends Structure {
/*     */     public static class ByReference
/*     */       extends GenericReparseBuffer implements Structure.ByReference {
/*     */       public ByReference() {}
/*     */       
/*     */       public ByReference(Pointer memory) {
/* 253 */         super(memory);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 261 */     public byte[] DataBuffer = new byte[16384];
/*     */     
/*     */     public static int sizeOf() {
/* 264 */       return Native.getNativeSize(GenericReparseBuffer.class, null);
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 269 */       return Arrays.asList(new String[] { "DataBuffer" });
/*     */     }
/*     */ 
/*     */     
/*     */     public GenericReparseBuffer() {}
/*     */ 
/*     */     
/*     */     public GenericReparseBuffer(Pointer memory) {
/* 277 */       super(memory);
/* 278 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     public GenericReparseBuffer(String DataBuffer) {
/* 283 */       this.DataBuffer = DataBuffer.getBytes();
/* 284 */       write();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class REPARSE_DATA_BUFFER
/*     */     extends Structure
/*     */   {
/*     */     public static class ByReference
/*     */       extends REPARSE_DATA_BUFFER
/*     */       implements Structure.ByReference
/*     */     {
/*     */       public ByReference() {}
/*     */       
/*     */       public ByReference(Pointer memory) {
/* 299 */         super(memory);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 306 */     public int ReparseTag = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 311 */     public short ReparseDataLength = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 319 */     public short Reserved = 0;
/*     */     
/*     */     public REPARSE_UNION u;
/*     */ 
/*     */     
/*     */     public static class REPARSE_UNION
/*     */       extends Union
/*     */     {
/*     */       public Ntifs.SymbolicLinkReparseBuffer symLinkReparseBuffer;
/*     */       public Ntifs.MountPointReparseBuffer mountPointReparseBuffer;
/*     */       
/*     */       public REPARSE_UNION(Pointer memory) {
/* 331 */         super(memory);
/*     */       }
/*     */       
/*     */       public Ntifs.GenericReparseBuffer genericReparseBuffer;
/*     */       
/*     */       public static class ByReference extends REPARSE_UNION implements Structure.ByReference {}
/*     */       
/*     */       public REPARSE_UNION() {}
/*     */     }
/*     */     
/*     */     public static int sizeOf() {
/* 342 */       return Native.getNativeSize(REPARSE_DATA_BUFFER.class, null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getSize() {
/* 349 */       return 8 + this.ReparseDataLength;
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 354 */       return Arrays.asList(new String[] { "ReparseTag", "ReparseDataLength", "Reserved", "u" });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public REPARSE_DATA_BUFFER() {}
/*     */ 
/*     */     
/*     */     public REPARSE_DATA_BUFFER(int ReparseTag, short Reserved) {
/* 363 */       this.ReparseTag = ReparseTag;
/* 364 */       this.Reserved = Reserved;
/* 365 */       this.ReparseDataLength = 0;
/* 366 */       write();
/*     */     }
/*     */ 
/*     */     
/*     */     public REPARSE_DATA_BUFFER(int ReparseTag, short Reserved, Ntifs.SymbolicLinkReparseBuffer symLinkReparseBuffer) {
/* 371 */       this.ReparseTag = ReparseTag;
/* 372 */       this.Reserved = Reserved;
/* 373 */       this.ReparseDataLength = (short)symLinkReparseBuffer.size();
/* 374 */       this.u.setType(Ntifs.SymbolicLinkReparseBuffer.class);
/* 375 */       this.u.symLinkReparseBuffer = symLinkReparseBuffer;
/* 376 */       write();
/*     */     }
/*     */     
/*     */     public REPARSE_DATA_BUFFER(Pointer memory) {
/* 380 */       super(memory);
/* 381 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     public void read() {
/* 386 */       super.read();
/*     */       
/* 388 */       switch (this.ReparseTag) {
/*     */         default:
/* 390 */           this.u.setType(Ntifs.GenericReparseBuffer.class);
/*     */           break;
/*     */         case -1610612733:
/* 393 */           this.u.setType(Ntifs.MountPointReparseBuffer.class);
/*     */           break;
/*     */         case -1610612724:
/* 396 */           this.u.setType(Ntifs.SymbolicLinkReparseBuffer.class);
/*     */           break;
/*     */       } 
/* 399 */       this.u.read();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/Ntifs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */