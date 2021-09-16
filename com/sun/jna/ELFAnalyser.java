/*     */ package com.sun.jna;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.math.BigInteger;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
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
/*     */ class ELFAnalyser
/*     */ {
/*  29 */   private static final byte[] ELF_MAGIC = new byte[] { Byte.MAX_VALUE, 69, 76, 70 };
/*     */   
/*     */   private static final int EF_ARM_ABI_FLOAT_HARD = 1024;
/*     */   
/*     */   private static final int EF_ARM_ABI_FLOAT_SOFT = 512;
/*     */   
/*     */   private static final int EI_DATA_BIG_ENDIAN = 2;
/*     */   
/*     */   private static final int E_MACHINE_ARM = 40;
/*     */   
/*     */   private static final int EI_CLASS_64BIT = 2;
/*     */   
/*     */   private final String filename;
/*     */ 
/*     */   
/*     */   public static ELFAnalyser analyse(String filename) throws IOException {
/*  45 */     ELFAnalyser res = new ELFAnalyser(filename);
/*  46 */     res.runDetection();
/*  47 */     return res;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean ELF = false;
/*     */   
/*     */   private boolean _64Bit = false;
/*     */   
/*     */   private boolean bigEndian = false;
/*     */   
/*     */   private boolean armHardFloatFlag = false;
/*     */   private boolean armSoftFloatFlag = false;
/*     */   private boolean armEabiAapcsVfp = false;
/*     */   private boolean arm = false;
/*     */   
/*     */   public boolean isELF() {
/*  63 */     return this.ELF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean is64Bit() {
/*  71 */     return this._64Bit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBigEndian() {
/*  79 */     return this.bigEndian;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFilename() {
/*  86 */     return this.filename;
/*     */   }
/*     */   
/*     */   public boolean isArmHardFloat() {
/*  90 */     return (isArmEabiAapcsVfp() || isArmHardFloatFlag());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isArmEabiAapcsVfp() {
/*  98 */     return this.armEabiAapcsVfp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isArmHardFloatFlag() {
/* 106 */     return this.armHardFloatFlag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isArmSoftFloatFlag() {
/* 114 */     return this.armSoftFloatFlag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isArm() {
/* 122 */     return this.arm;
/*     */   }
/*     */   
/*     */   private ELFAnalyser(String filename) {
/* 126 */     this.filename = filename;
/*     */   }
/*     */   
/*     */   private void runDetection() throws IOException {
/* 130 */     RandomAccessFile raf = new RandomAccessFile(this.filename, "r");
/*     */ 
/*     */     
/*     */     try {
/* 134 */       if (raf.length() > 4L) {
/* 135 */         byte[] magic = new byte[4];
/* 136 */         raf.seek(0L);
/* 137 */         raf.read(magic);
/* 138 */         if (Arrays.equals(magic, ELF_MAGIC)) {
/* 139 */           this.ELF = true;
/*     */         }
/*     */       } 
/* 142 */       if (!this.ELF) {
/*     */         return;
/*     */       }
/* 145 */       raf.seek(4L);
/*     */ 
/*     */       
/* 148 */       byte sizeIndicator = raf.readByte();
/* 149 */       byte endianessIndicator = raf.readByte();
/* 150 */       this._64Bit = (sizeIndicator == 2);
/* 151 */       this.bigEndian = (endianessIndicator == 2);
/* 152 */       raf.seek(0L);
/*     */       
/* 154 */       ByteBuffer headerData = ByteBuffer.allocate(this._64Bit ? 64 : 52);
/* 155 */       raf.getChannel().read(headerData, 0L);
/*     */       
/* 157 */       headerData.order(this.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
/*     */ 
/*     */       
/* 160 */       this.arm = (headerData.get(18) == 40);
/*     */       
/* 162 */       if (this.arm) {
/*     */         
/* 164 */         int flags = headerData.getInt(this._64Bit ? 48 : 36);
/* 165 */         this.armHardFloatFlag = ((flags & 0x400) == 1024);
/* 166 */         this.armSoftFloatFlag = ((flags & 0x200) == 512);
/*     */         
/* 168 */         parseEabiAapcsVfp(headerData, raf);
/*     */       } 
/*     */     } finally {
/*     */       try {
/* 172 */         raf.close();
/* 173 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseEabiAapcsVfp(ByteBuffer headerData, RandomAccessFile raf) throws IOException {
/* 180 */     ELFSectionHeaders sectionHeaders = new ELFSectionHeaders(this._64Bit, this.bigEndian, headerData, raf);
/*     */     
/* 182 */     for (ELFSectionHeaderEntry eshe : sectionHeaders.getEntries()) {
/* 183 */       if (".ARM.attributes".equals(eshe.getName())) {
/* 184 */         ByteBuffer armAttributesBuffer = ByteBuffer.allocate(eshe.getSize());
/* 185 */         armAttributesBuffer.order(this.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
/* 186 */         raf.getChannel().read(armAttributesBuffer, eshe.getOffset());
/* 187 */         armAttributesBuffer.rewind();
/* 188 */         Map<Integer, Map<ArmAeabiAttributesTag, Object>> armAttributes = parseArmAttributes(armAttributesBuffer);
/* 189 */         Map<ArmAeabiAttributesTag, Object> fileAttributes = armAttributes.get(Integer.valueOf(1));
/* 190 */         if (fileAttributes == null) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 200 */         Object abiVFPargValue = fileAttributes.get(ArmAeabiAttributesTag.ABI_VFP_args);
/* 201 */         if (abiVFPargValue instanceof Integer && ((Integer)abiVFPargValue).equals(Integer.valueOf(1))) {
/* 202 */           this.armEabiAapcsVfp = true; continue;
/* 203 */         }  if (abiVFPargValue instanceof BigInteger && ((BigInteger)abiVFPargValue).intValue() == 1)
/* 204 */           this.armEabiAapcsVfp = true; 
/*     */       } 
/*     */     } 
/*     */   } static class ELFSectionHeaders { public ELFSectionHeaders(boolean _64bit, boolean bigEndian, ByteBuffer headerData, RandomAccessFile raf) throws IOException {
/*     */       long shoff;
/*     */       int shentsize, shnum;
/*     */       short shstrndx;
/* 211 */       this.entries = new ArrayList<ELFAnalyser.ELFSectionHeaderEntry>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 218 */       if (_64bit) {
/* 219 */         shoff = headerData.getLong(40);
/* 220 */         shentsize = headerData.getShort(58);
/* 221 */         shnum = headerData.getShort(60);
/* 222 */         shstrndx = headerData.getShort(62);
/*     */       } else {
/* 224 */         shoff = headerData.getInt(32);
/* 225 */         shentsize = headerData.getShort(46);
/* 226 */         shnum = headerData.getShort(48);
/* 227 */         shstrndx = headerData.getShort(50);
/*     */       } 
/*     */       
/* 230 */       int tableLength = shnum * shentsize;
/*     */       
/* 232 */       ByteBuffer data = ByteBuffer.allocate(tableLength);
/* 233 */       data.order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
/* 234 */       raf.getChannel().read(data, shoff);
/*     */       
/* 236 */       for (int i = 0; i < shnum; i++) {
/* 237 */         data.position(i * shentsize);
/* 238 */         ByteBuffer header = data.slice();
/* 239 */         header.order(data.order());
/* 240 */         header.limit(shentsize);
/* 241 */         this.entries.add(new ELFAnalyser.ELFSectionHeaderEntry(_64bit, header));
/*     */       } 
/*     */       
/* 244 */       ELFAnalyser.ELFSectionHeaderEntry stringTable = this.entries.get(shstrndx);
/* 245 */       ByteBuffer stringBuffer = ByteBuffer.allocate(stringTable.getSize());
/* 246 */       stringBuffer.order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
/* 247 */       raf.getChannel().read(stringBuffer, stringTable.getOffset());
/* 248 */       stringBuffer.rewind();
/*     */       
/* 250 */       ByteArrayOutputStream baos = new ByteArrayOutputStream(20);
/* 251 */       for (ELFAnalyser.ELFSectionHeaderEntry eshe : this.entries) {
/* 252 */         baos.reset();
/*     */         
/* 254 */         stringBuffer.position(eshe.getNameOffset());
/*     */         
/* 256 */         while (stringBuffer.position() < stringBuffer.limit()) {
/* 257 */           byte b = stringBuffer.get();
/* 258 */           if (b == 0) {
/*     */             break;
/*     */           }
/* 261 */           baos.write(b);
/*     */         } 
/*     */ 
/*     */         
/* 265 */         eshe.setName(baos.toString("ASCII"));
/*     */       } 
/*     */     }
/*     */     private final List<ELFAnalyser.ELFSectionHeaderEntry> entries;
/*     */     public List<ELFAnalyser.ELFSectionHeaderEntry> getEntries() {
/* 270 */       return this.entries;
/*     */     } }
/*     */ 
/*     */   
/*     */   static class ELFSectionHeaderEntry {
/*     */     private final int nameOffset;
/*     */     private String name;
/*     */     private final int type;
/*     */     private final int flags;
/*     */     private final int offset;
/*     */     private final int size;
/*     */     
/*     */     public ELFSectionHeaderEntry(boolean _64bit, ByteBuffer sectionHeaderData) {
/* 283 */       this.nameOffset = sectionHeaderData.getInt(0);
/* 284 */       this.type = sectionHeaderData.getInt(4);
/* 285 */       this.flags = (int)(_64bit ? sectionHeaderData.getLong(8) : sectionHeaderData.getInt(8));
/* 286 */       this.offset = (int)(_64bit ? sectionHeaderData.getLong(24) : sectionHeaderData.getInt(16));
/* 287 */       this.size = (int)(_64bit ? sectionHeaderData.getLong(32) : sectionHeaderData.getInt(20));
/*     */     }
/*     */     
/*     */     public String getName() {
/* 291 */       return this.name;
/*     */     }
/*     */     
/*     */     public void setName(String name) {
/* 295 */       this.name = name;
/*     */     }
/*     */     
/*     */     public int getNameOffset() {
/* 299 */       return this.nameOffset;
/*     */     }
/*     */     
/*     */     public int getType() {
/* 303 */       return this.type;
/*     */     }
/*     */     
/*     */     public int getFlags() {
/* 307 */       return this.flags;
/*     */     }
/*     */     
/*     */     public int getOffset() {
/* 311 */       return this.offset;
/*     */     }
/*     */     
/*     */     public int getSize() {
/* 315 */       return this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 320 */       return "ELFSectionHeaderEntry{nameIdx=" + this.nameOffset + ", name=" + this.name + ", type=" + this.type + ", flags=" + this.flags + ", offset=" + this.offset + ", size=" + this.size + '}';
/*     */     } }
/*     */   
/*     */   static class ArmAeabiAttributesTag { private final int value;
/*     */     private final String name;
/*     */     private final ParameterType parameterType;
/*     */     
/* 327 */     public enum ParameterType { UINT32, NTBS, ULEB128; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ArmAeabiAttributesTag(int value, String name, ParameterType parameterType) {
/* 335 */       this.value = value;
/* 336 */       this.name = name;
/* 337 */       this.parameterType = parameterType;
/*     */     }
/*     */     
/*     */     public int getValue() {
/* 341 */       return this.value;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 345 */       return this.name;
/*     */     }
/*     */     
/*     */     public ParameterType getParameterType() {
/* 349 */       return this.parameterType;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 354 */       return this.name + " (" + this.value + ")";
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 359 */       int hash = 7;
/* 360 */       hash = 67 * hash + this.value;
/* 361 */       return hash;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 366 */       if (this == obj) {
/* 367 */         return true;
/*     */       }
/* 369 */       if (obj == null) {
/* 370 */         return false;
/*     */       }
/* 372 */       if (getClass() != obj.getClass()) {
/* 373 */         return false;
/*     */       }
/* 375 */       ArmAeabiAttributesTag other = (ArmAeabiAttributesTag)obj;
/* 376 */       if (this.value != other.value) {
/* 377 */         return false;
/*     */       }
/* 379 */       return true;
/*     */     }
/*     */     
/* 382 */     private static final List<ArmAeabiAttributesTag> tags = new LinkedList<ArmAeabiAttributesTag>();
/* 383 */     private static final Map<Integer, ArmAeabiAttributesTag> valueMap = new HashMap<Integer, ArmAeabiAttributesTag>();
/* 384 */     private static final Map<String, ArmAeabiAttributesTag> nameMap = new HashMap<String, ArmAeabiAttributesTag>();
/*     */ 
/*     */     
/* 387 */     public static final ArmAeabiAttributesTag File = addTag(1, "File", ParameterType.UINT32);
/* 388 */     public static final ArmAeabiAttributesTag Section = addTag(2, "Section", ParameterType.UINT32);
/* 389 */     public static final ArmAeabiAttributesTag Symbol = addTag(3, "Symbol", ParameterType.UINT32);
/* 390 */     public static final ArmAeabiAttributesTag CPU_raw_name = addTag(4, "CPU_raw_name", ParameterType.NTBS);
/* 391 */     public static final ArmAeabiAttributesTag CPU_name = addTag(5, "CPU_name", ParameterType.NTBS);
/* 392 */     public static final ArmAeabiAttributesTag CPU_arch = addTag(6, "CPU_arch", ParameterType.ULEB128);
/* 393 */     public static final ArmAeabiAttributesTag CPU_arch_profile = addTag(7, "CPU_arch_profile", ParameterType.ULEB128);
/* 394 */     public static final ArmAeabiAttributesTag ARM_ISA_use = addTag(8, "ARM_ISA_use", ParameterType.ULEB128);
/* 395 */     public static final ArmAeabiAttributesTag THUMB_ISA_use = addTag(9, "THUMB_ISA_use", ParameterType.ULEB128);
/* 396 */     public static final ArmAeabiAttributesTag FP_arch = addTag(10, "FP_arch", ParameterType.ULEB128);
/* 397 */     public static final ArmAeabiAttributesTag WMMX_arch = addTag(11, "WMMX_arch", ParameterType.ULEB128);
/* 398 */     public static final ArmAeabiAttributesTag Advanced_SIMD_arch = addTag(12, "Advanced_SIMD_arch", ParameterType.ULEB128);
/* 399 */     public static final ArmAeabiAttributesTag PCS_config = addTag(13, "PCS_config", ParameterType.ULEB128);
/* 400 */     public static final ArmAeabiAttributesTag ABI_PCS_R9_use = addTag(14, "ABI_PCS_R9_use", ParameterType.ULEB128);
/* 401 */     public static final ArmAeabiAttributesTag ABI_PCS_RW_data = addTag(15, "ABI_PCS_RW_data", ParameterType.ULEB128);
/* 402 */     public static final ArmAeabiAttributesTag ABI_PCS_RO_data = addTag(16, "ABI_PCS_RO_data", ParameterType.ULEB128);
/* 403 */     public static final ArmAeabiAttributesTag ABI_PCS_GOT_use = addTag(17, "ABI_PCS_GOT_use", ParameterType.ULEB128);
/* 404 */     public static final ArmAeabiAttributesTag ABI_PCS_wchar_t = addTag(18, "ABI_PCS_wchar_t", ParameterType.ULEB128);
/* 405 */     public static final ArmAeabiAttributesTag ABI_FP_rounding = addTag(19, "ABI_FP_rounding", ParameterType.ULEB128);
/* 406 */     public static final ArmAeabiAttributesTag ABI_FP_denormal = addTag(20, "ABI_FP_denormal", ParameterType.ULEB128);
/* 407 */     public static final ArmAeabiAttributesTag ABI_FP_exceptions = addTag(21, "ABI_FP_exceptions", ParameterType.ULEB128);
/* 408 */     public static final ArmAeabiAttributesTag ABI_FP_user_exceptions = addTag(22, "ABI_FP_user_exceptions", ParameterType.ULEB128);
/* 409 */     public static final ArmAeabiAttributesTag ABI_FP_number_model = addTag(23, "ABI_FP_number_model", ParameterType.ULEB128);
/* 410 */     public static final ArmAeabiAttributesTag ABI_align_needed = addTag(24, "ABI_align_needed", ParameterType.ULEB128);
/* 411 */     public static final ArmAeabiAttributesTag ABI_align8_preserved = addTag(25, "ABI_align8_preserved", ParameterType.ULEB128);
/* 412 */     public static final ArmAeabiAttributesTag ABI_enum_size = addTag(26, "ABI_enum_size", ParameterType.ULEB128);
/* 413 */     public static final ArmAeabiAttributesTag ABI_HardFP_use = addTag(27, "ABI_HardFP_use", ParameterType.ULEB128);
/* 414 */     public static final ArmAeabiAttributesTag ABI_VFP_args = addTag(28, "ABI_VFP_args", ParameterType.ULEB128);
/* 415 */     public static final ArmAeabiAttributesTag ABI_WMMX_args = addTag(29, "ABI_WMMX_args", ParameterType.ULEB128);
/* 416 */     public static final ArmAeabiAttributesTag ABI_optimization_goals = addTag(30, "ABI_optimization_goals", ParameterType.ULEB128);
/* 417 */     public static final ArmAeabiAttributesTag ABI_FP_optimization_goals = addTag(31, "ABI_FP_optimization_goals", ParameterType.ULEB128);
/* 418 */     public static final ArmAeabiAttributesTag compatibility = addTag(32, "compatibility", ParameterType.NTBS);
/* 419 */     public static final ArmAeabiAttributesTag CPU_unaligned_access = addTag(34, "CPU_unaligned_access", ParameterType.ULEB128);
/* 420 */     public static final ArmAeabiAttributesTag FP_HP_extension = addTag(36, "FP_HP_extension", ParameterType.ULEB128);
/* 421 */     public static final ArmAeabiAttributesTag ABI_FP_16bit_format = addTag(38, "ABI_FP_16bit_format", ParameterType.ULEB128);
/* 422 */     public static final ArmAeabiAttributesTag MPextension_use = addTag(42, "MPextension_use", ParameterType.ULEB128);
/* 423 */     public static final ArmAeabiAttributesTag DIV_use = addTag(44, "DIV_use", ParameterType.ULEB128);
/* 424 */     public static final ArmAeabiAttributesTag nodefaults = addTag(64, "nodefaults", ParameterType.ULEB128);
/* 425 */     public static final ArmAeabiAttributesTag also_compatible_with = addTag(65, "also_compatible_with", ParameterType.NTBS);
/* 426 */     public static final ArmAeabiAttributesTag conformance = addTag(67, "conformance", ParameterType.NTBS);
/* 427 */     public static final ArmAeabiAttributesTag T2EE_use = addTag(66, "T2EE_use", ParameterType.ULEB128);
/* 428 */     public static final ArmAeabiAttributesTag Virtualization_use = addTag(68, "Virtualization_use", ParameterType.ULEB128);
/* 429 */     public static final ArmAeabiAttributesTag MPextension_use2 = addTag(70, "MPextension_use", ParameterType.ULEB128);
/*     */     
/*     */     private static ArmAeabiAttributesTag addTag(int value, String name, ParameterType type) {
/* 432 */       ArmAeabiAttributesTag tag = new ArmAeabiAttributesTag(value, name, type);
/*     */       
/* 434 */       if (!valueMap.containsKey(Integer.valueOf(tag.getValue()))) {
/* 435 */         valueMap.put(Integer.valueOf(tag.getValue()), tag);
/*     */       }
/* 437 */       if (!nameMap.containsKey(tag.getName())) {
/* 438 */         nameMap.put(tag.getName(), tag);
/*     */       }
/* 440 */       tags.add(tag);
/* 441 */       return tag;
/*     */     }
/*     */     
/*     */     public static List<ArmAeabiAttributesTag> getTags() {
/* 445 */       return Collections.unmodifiableList(tags);
/*     */     }
/*     */     
/*     */     public static ArmAeabiAttributesTag getByName(String name) {
/* 449 */       return nameMap.get(name);
/*     */     }
/*     */     
/*     */     public static ArmAeabiAttributesTag getByValue(int value) {
/* 453 */       if (valueMap.containsKey(Integer.valueOf(value))) {
/* 454 */         return valueMap.get(Integer.valueOf(value));
/*     */       }
/* 456 */       ArmAeabiAttributesTag pseudoTag = new ArmAeabiAttributesTag(value, "Unknown " + value, getParameterType(value));
/* 457 */       return pseudoTag;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static ParameterType getParameterType(int value) {
/* 463 */       ArmAeabiAttributesTag tag = getByValue(value);
/* 464 */       if (tag == null) {
/* 465 */         if (value % 2 == 0) {
/* 466 */           return ParameterType.ULEB128;
/*     */         }
/* 468 */         return ParameterType.NTBS;
/*     */       } 
/*     */       
/* 471 */       return tag.getParameterType();
/*     */     } }
/*     */ 
/*     */   
/*     */   public enum ParameterType { UINT32, NTBS, ULEB128; }
/*     */   
/*     */   private static Map<Integer, Map<ArmAeabiAttributesTag, Object>> parseArmAttributes(ByteBuffer bb) {
/* 478 */     byte format = bb.get();
/* 479 */     if (format != 65)
/*     */     {
/*     */       
/* 482 */       return Collections.EMPTY_MAP;
/*     */     }
/* 484 */     while (bb.position() < bb.limit()) {
/* 485 */       int posSectionStart = bb.position();
/* 486 */       int sectionLength = bb.getInt();
/* 487 */       if (sectionLength <= 0) {
/*     */         break;
/*     */       }
/*     */       
/* 491 */       String vendorName = readNTBS(bb, null);
/* 492 */       if ("aeabi".equals(vendorName)) {
/* 493 */         return parseAEABI(bb);
/*     */       }
/* 495 */       bb.position(posSectionStart + sectionLength);
/*     */     } 
/* 497 */     return Collections.EMPTY_MAP;
/*     */   }
/*     */   
/*     */   private static Map<Integer, Map<ArmAeabiAttributesTag, Object>> parseAEABI(ByteBuffer buffer) {
/* 501 */     Map<Integer, Map<ArmAeabiAttributesTag, Object>> data = new HashMap<Integer, Map<ArmAeabiAttributesTag, Object>>();
/* 502 */     while (buffer.position() < buffer.limit()) {
/* 503 */       int pos = buffer.position();
/* 504 */       int subsectionTag = readULEB128(buffer).intValue();
/* 505 */       int length = buffer.getInt();
/* 506 */       if (subsectionTag == 1) {
/* 507 */         data.put(Integer.valueOf(subsectionTag), parseFileAttribute(buffer));
/*     */       }
/* 509 */       buffer.position(pos + length);
/*     */     } 
/* 511 */     return data;
/*     */   }
/*     */   
/*     */   private static Map<ArmAeabiAttributesTag, Object> parseFileAttribute(ByteBuffer bb) {
/* 515 */     Map<ArmAeabiAttributesTag, Object> result = new HashMap<ArmAeabiAttributesTag, Object>();
/* 516 */     while (bb.position() < bb.limit()) {
/* 517 */       int tagValue = readULEB128(bb).intValue();
/* 518 */       ArmAeabiAttributesTag tag = ArmAeabiAttributesTag.getByValue(tagValue);
/* 519 */       switch (tag.getParameterType()) {
/*     */         case UINT32:
/* 521 */           result.put(tag, Integer.valueOf(bb.getInt()));
/*     */         
/*     */         case NTBS:
/* 524 */           result.put(tag, readNTBS(bb, null));
/*     */         
/*     */         case ULEB128:
/* 527 */           result.put(tag, readULEB128(bb));
/*     */       } 
/*     */     
/*     */     } 
/* 531 */     return result;
/*     */   }
/*     */   private static String readNTBS(ByteBuffer buffer, Integer position) {
/*     */     byte currentByte;
/* 535 */     if (position != null) {
/* 536 */       buffer.position(position.intValue());
/*     */     }
/* 538 */     int startingPos = buffer.position();
/*     */     
/*     */     do {
/* 541 */       currentByte = buffer.get();
/* 542 */     } while (currentByte != 0 && buffer.position() <= buffer.limit());
/* 543 */     int terminatingPosition = buffer.position();
/* 544 */     byte[] data = new byte[terminatingPosition - startingPos - 1];
/* 545 */     buffer.position(startingPos);
/* 546 */     buffer.get(data);
/* 547 */     buffer.position(buffer.position() + 1);
/*     */     try {
/* 549 */       return new String(data, "ASCII");
/* 550 */     } catch (UnsupportedEncodingException ex) {
/* 551 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static BigInteger readULEB128(ByteBuffer buffer) {
/* 556 */     BigInteger result = BigInteger.ZERO;
/* 557 */     int shift = 0;
/*     */     while (true) {
/* 559 */       byte b = buffer.get();
/* 560 */       result = result.or(BigInteger.valueOf((b & Byte.MAX_VALUE)).shiftLeft(shift));
/* 561 */       if ((b & 0x80) == 0) {
/*     */         break;
/*     */       }
/* 564 */       shift += 7;
/*     */     } 
/* 566 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/ELFAnalyser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */