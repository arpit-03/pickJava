/*      */ package com.sun.jna.platform.win32;
/*      */ 
/*      */ import com.sun.jna.Memory;
/*      */ import com.sun.jna.Pointer;
/*      */ import com.sun.jna.StringArray;
/*      */ import com.sun.jna.Structure;
/*      */ import com.sun.jna.Union;
/*      */ import com.sun.jna.win32.W32APITypeMapper;
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
/*      */ public interface Winevt
/*      */ {
/*      */   public static final int EVT_VARIANT_TYPE_ARRAY = 128;
/*      */   public static final int EVT_VARIANT_TYPE_MASK = 127;
/*      */   public static final int EVT_READ_ACCESS = 1;
/*      */   public static final int EVT_WRITE_ACCESS = 2;
/*      */   public static final int EVT_ALL_ACCESS = 7;
/*      */   public static final int EVT_CLEAR_ACCESS = 4;
/*      */   
/*      */   public enum EVT_VARIANT_TYPE
/*      */   {
/*   49 */     EvtVarTypeNull(""),
/*      */ 
/*      */     
/*   52 */     EvtVarTypeString("String"),
/*      */ 
/*      */     
/*   55 */     EvtVarTypeAnsiString("AnsiString"),
/*      */ 
/*      */     
/*   58 */     EvtVarTypeSByte("SByte"),
/*      */ 
/*      */     
/*   61 */     EvtVarTypeByte("Byte"),
/*      */ 
/*      */     
/*   64 */     EvtVarTypeInt16("Int16"),
/*      */ 
/*      */     
/*   67 */     EvtVarTypeUInt16("UInt16"),
/*      */ 
/*      */     
/*   70 */     EvtVarTypeInt32("Int32"),
/*      */ 
/*      */     
/*   73 */     EvtVarTypeUInt32("UInt32"),
/*      */ 
/*      */     
/*   76 */     EvtVarTypeInt64("Int64"),
/*      */ 
/*      */     
/*   79 */     EvtVarTypeUInt64("UInt64"),
/*      */ 
/*      */     
/*   82 */     EvtVarTypeSingle("Single"),
/*      */ 
/*      */     
/*   85 */     EvtVarTypeDouble("Double"),
/*      */ 
/*      */     
/*   88 */     EvtVarTypeBoolean("Boolean"),
/*      */ 
/*      */     
/*   91 */     EvtVarTypeBinary("Binary"),
/*      */ 
/*      */     
/*   94 */     EvtVarTypeGuid("Guid"),
/*      */ 
/*      */     
/*   97 */     EvtVarTypeSizeT("SizeT"),
/*      */ 
/*      */     
/*  100 */     EvtVarTypeFileTime("FileTime"),
/*      */ 
/*      */     
/*  103 */     EvtVarTypeSysTime("SysTime"),
/*      */ 
/*      */     
/*  106 */     EvtVarTypeSid("Sid"),
/*      */ 
/*      */     
/*  109 */     EvtVarTypeHexInt32("Int32"),
/*      */ 
/*      */     
/*  112 */     EvtVarTypeHexInt64("Int64"),
/*      */ 
/*      */     
/*  115 */     EvtVarTypeEvtHandle("EvtHandle"),
/*      */ 
/*      */     
/*  118 */     EvtVarTypeEvtXml("Xml");
/*      */     
/*      */     private final String field;
/*      */     
/*      */     EVT_VARIANT_TYPE(String field) {
/*  123 */       this.field = field;
/*      */     }
/*      */     
/*      */     public String getField() {
/*  127 */       return this.field.isEmpty() ? "" : (this.field + "Val");
/*      */     }
/*      */     
/*      */     public String getArrField() {
/*  131 */       return this.field.isEmpty() ? "" : (this.field + "Arr");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class EVT_VARIANT
/*      */     extends Structure
/*      */   {
/*      */     public field1_union field1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int Count;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int Type;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Object holder;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static class field1_union
/*      */       extends Union
/*      */     {
/*      */       public byte byteValue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public short shortValue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int intValue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public long longValue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public float floatValue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public double doubleVal;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Pointer pointerValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public EVT_VARIANT() {
/*  218 */       super(W32APITypeMapper.DEFAULT);
/*      */     }
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  222 */       return Arrays.asList(new String[] { "field1", "Count", "Type" });
/*      */     }
/*      */     
/*      */     public EVT_VARIANT(Pointer peer) {
/*  226 */       super(peer, 0, W32APITypeMapper.DEFAULT);
/*      */     }
/*      */     
/*      */     public void use(Pointer m) {
/*  230 */       useMemory(m, 0);
/*      */     }
/*      */     
/*      */     public static class ByReference extends EVT_VARIANT implements Structure.ByReference {
/*      */       public ByReference(Pointer p) {
/*  235 */         super(p);
/*      */       }
/*      */       
/*      */       public ByReference() {}
/*      */     }
/*      */     
/*      */     public static class ByValue
/*      */       extends EVT_VARIANT
/*      */       implements Structure.ByValue {
/*      */       public ByValue(Pointer p) {
/*  245 */         super(p);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public ByValue() {}
/*      */     }
/*      */ 
/*      */     
/*      */     private int getBaseType() {
/*  255 */       return this.Type & 0x7F;
/*      */     }
/*      */     
/*      */     public boolean isArray() {
/*  259 */       return ((this.Type & 0x80) == 128);
/*      */     }
/*      */     
/*      */     public Winevt.EVT_VARIANT_TYPE getVariantType() {
/*  263 */       return Winevt.EVT_VARIANT_TYPE.values()[getBaseType()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setValue(Winevt.EVT_VARIANT_TYPE type, Object value) {
/*  274 */       allocateMemory();
/*  275 */       if (type == null) {
/*  276 */         throw new IllegalArgumentException("setValue must not be called with type set to NULL");
/*      */       }
/*  278 */       this.holder = null;
/*  279 */       if (value == null || type == Winevt.EVT_VARIANT_TYPE.EvtVarTypeNull) {
/*  280 */         this.Type = Winevt.EVT_VARIANT_TYPE.EvtVarTypeNull.ordinal();
/*  281 */         this.Count = 0;
/*  282 */         this.field1.writeField("pointerValue", Pointer.NULL);
/*      */       } else {
/*  284 */         switch (type) {
/*      */           case EvtVarTypeAnsiString:
/*  286 */             if (value.getClass().isArray() && value.getClass().getComponentType() == String.class) {
/*  287 */               this.Type = type.ordinal() | 0x80;
/*  288 */               StringArray sa = new StringArray((String[])value, false);
/*  289 */               this.holder = sa;
/*  290 */               this.Count = ((String[])value).length;
/*  291 */               this.field1.writeField("pointerValue", sa); break;
/*  292 */             }  if (value.getClass() == String.class) {
/*  293 */               this.Type = type.ordinal();
/*  294 */               Memory mem = new Memory((((String)value).length() + 1));
/*  295 */               mem.setString(0L, (String)value);
/*  296 */               this.holder = mem;
/*  297 */               this.Count = 0;
/*  298 */               this.field1.writeField("pointerValue", mem); break;
/*      */             } 
/*  300 */             throw new IllegalArgumentException(type.name() + " must be set from String/String[]");
/*      */ 
/*      */           
/*      */           case EvtVarTypeBoolean:
/*  304 */             if (value.getClass().isArray() && value.getClass().getComponentType() == WinDef.BOOL.class) {
/*  305 */               this.Type = type.ordinal() | 0x80;
/*  306 */               Memory mem = new Memory((((WinDef.BOOL[])value).length * 4));
/*  307 */               for (int i = 0; i < ((WinDef.BOOL[])value).length; i++) {
/*  308 */                 mem.setInt((i * 4), ((WinDef.BOOL[])value)[i].intValue());
/*      */               }
/*  310 */               this.holder = mem;
/*  311 */               this.Count = 0;
/*  312 */               this.field1.writeField("pointerValue", mem); break;
/*  313 */             }  if (value.getClass() == WinDef.BOOL.class) {
/*  314 */               this.Type = type.ordinal();
/*  315 */               this.Count = 0;
/*  316 */               this.field1.writeField("intValue", Integer.valueOf(((WinDef.BOOL)value).intValue())); break;
/*      */             } 
/*  318 */             throw new IllegalArgumentException(type.name() + " must be set from BOOL/BOOL[]");
/*      */ 
/*      */           
/*      */           case EvtVarTypeString:
/*      */           case EvtVarTypeEvtXml:
/*  323 */             if (value.getClass().isArray() && value.getClass().getComponentType() == String.class) {
/*  324 */               this.Type = type.ordinal() | 0x80;
/*  325 */               StringArray sa = new StringArray((String[])value, true);
/*  326 */               this.holder = sa;
/*  327 */               this.Count = ((String[])value).length;
/*  328 */               this.field1.writeField("pointerValue", sa); break;
/*  329 */             }  if (value.getClass() == String.class) {
/*  330 */               this.Type = type.ordinal();
/*  331 */               Memory mem = new Memory(((((String)value).length() + 1) * 2));
/*  332 */               mem.setWideString(0L, (String)value);
/*  333 */               this.holder = mem;
/*  334 */               this.Count = 0;
/*  335 */               this.field1.writeField("pointerValue", mem); break;
/*      */             } 
/*  337 */             throw new IllegalArgumentException(type.name() + " must be set from String/String[]");
/*      */ 
/*      */           
/*      */           case EvtVarTypeSByte:
/*      */           case EvtVarTypeByte:
/*  342 */             if (value.getClass().isArray() && value.getClass().getComponentType() == byte.class) {
/*  343 */               this.Type = type.ordinal() | 0x80;
/*  344 */               Memory mem = new Memory((((byte[])value).length * 1));
/*  345 */               mem.write(0L, (byte[])value, 0, ((byte[])value).length);
/*  346 */               this.holder = mem;
/*  347 */               this.Count = 0;
/*  348 */               this.field1.writeField("pointerValue", mem); break;
/*  349 */             }  if (value.getClass() == byte.class) {
/*  350 */               this.Type = type.ordinal();
/*  351 */               this.Count = 0;
/*  352 */               this.field1.writeField("byteValue", value); break;
/*      */             } 
/*  354 */             throw new IllegalArgumentException(type.name() + " must be set from byte/byte[]");
/*      */ 
/*      */           
/*      */           case EvtVarTypeInt16:
/*      */           case EvtVarTypeUInt16:
/*  359 */             if (value.getClass().isArray() && value.getClass().getComponentType() == short.class) {
/*  360 */               this.Type = type.ordinal() | 0x80;
/*  361 */               Memory mem = new Memory((((short[])value).length * 2));
/*  362 */               mem.write(0L, (short[])value, 0, ((short[])value).length);
/*  363 */               this.holder = mem;
/*  364 */               this.Count = 0;
/*  365 */               this.field1.writeField("pointerValue", mem); break;
/*  366 */             }  if (value.getClass() == short.class) {
/*  367 */               this.Type = type.ordinal();
/*  368 */               this.Count = 0;
/*  369 */               this.field1.writeField("shortValue", value); break;
/*      */             } 
/*  371 */             throw new IllegalArgumentException(type.name() + " must be set from short/short[]");
/*      */ 
/*      */           
/*      */           case EvtVarTypeHexInt32:
/*      */           case EvtVarTypeInt32:
/*      */           case EvtVarTypeUInt32:
/*  377 */             if (value.getClass().isArray() && value.getClass().getComponentType() == int.class) {
/*  378 */               this.Type = type.ordinal() | 0x80;
/*  379 */               Memory mem = new Memory((((int[])value).length * 4));
/*  380 */               mem.write(0L, (int[])value, 0, ((int[])value).length);
/*  381 */               this.holder = mem;
/*  382 */               this.Count = 0;
/*  383 */               this.field1.writeField("pointerValue", mem); break;
/*  384 */             }  if (value.getClass() == int.class) {
/*  385 */               this.Type = type.ordinal();
/*  386 */               this.Count = 0;
/*  387 */               this.field1.writeField("intValue", value); break;
/*      */             } 
/*  389 */             throw new IllegalArgumentException(type.name() + " must be set from int/int[]");
/*      */ 
/*      */           
/*      */           case EvtVarTypeHexInt64:
/*      */           case EvtVarTypeInt64:
/*      */           case EvtVarTypeUInt64:
/*  395 */             if (value.getClass().isArray() && value.getClass().getComponentType() == long.class) {
/*  396 */               this.Type = type.ordinal() | 0x80;
/*  397 */               Memory mem = new Memory((((long[])value).length * 4));
/*  398 */               mem.write(0L, (long[])value, 0, ((long[])value).length);
/*  399 */               this.holder = mem;
/*  400 */               this.Count = 0;
/*  401 */               this.field1.writeField("pointerValue", mem); break;
/*  402 */             }  if (value.getClass() == long.class) {
/*  403 */               this.Type = type.ordinal();
/*  404 */               this.Count = 0;
/*  405 */               this.field1.writeField("longValue", value); break;
/*      */             } 
/*  407 */             throw new IllegalArgumentException(type.name() + " must be set from long/long[]");
/*      */ 
/*      */           
/*      */           case EvtVarTypeSingle:
/*  411 */             if (value.getClass().isArray() && value.getClass().getComponentType() == float.class) {
/*  412 */               this.Type = type.ordinal() | 0x80;
/*  413 */               Memory mem = new Memory((((float[])value).length * 4));
/*  414 */               mem.write(0L, (float[])value, 0, ((float[])value).length);
/*  415 */               this.holder = mem;
/*  416 */               this.Count = 0;
/*  417 */               this.field1.writeField("pointerValue", mem); break;
/*  418 */             }  if (value.getClass() == float.class) {
/*  419 */               this.Type = type.ordinal();
/*  420 */               this.Count = 0;
/*  421 */               this.field1.writeField("floatValue", value); break;
/*      */             } 
/*  423 */             throw new IllegalArgumentException(type.name() + " must be set from float/float[]");
/*      */ 
/*      */           
/*      */           case EvtVarTypeDouble:
/*  427 */             if (value.getClass().isArray() && value.getClass().getComponentType() == double.class) {
/*  428 */               this.Type = type.ordinal() | 0x80;
/*  429 */               Memory mem = new Memory((((double[])value).length * 4));
/*  430 */               mem.write(0L, (double[])value, 0, ((double[])value).length);
/*  431 */               this.holder = mem;
/*  432 */               this.Count = 0;
/*  433 */               this.field1.writeField("pointerValue", mem); break;
/*  434 */             }  if (value.getClass() == double.class) {
/*  435 */               this.Type = type.ordinal();
/*  436 */               this.Count = 0;
/*  437 */               this.field1.writeField("doubleVal", value); break;
/*      */             } 
/*  439 */             throw new IllegalArgumentException(type.name() + " must be set from double/double[]");
/*      */ 
/*      */           
/*      */           case EvtVarTypeBinary:
/*  443 */             if (value.getClass().isArray() && value.getClass().getComponentType() == byte.class) {
/*  444 */               this.Type = type.ordinal();
/*  445 */               Memory mem = new Memory((((byte[])value).length * 1));
/*  446 */               mem.write(0L, (byte[])value, 0, ((byte[])value).length);
/*  447 */               this.holder = mem;
/*  448 */               this.Count = 0;
/*  449 */               this.field1.writeField("pointerValue", mem); break;
/*      */             } 
/*  451 */             throw new IllegalArgumentException(type.name() + " must be set from byte[]");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           default:
/*  461 */             throw new IllegalStateException(String.format("NOT IMPLEMENTED: getValue(%s) (Array: %b, Count: %d)", new Object[] { type, Boolean.valueOf(isArray()), Integer.valueOf(this.Count) }));
/*      */         } 
/*      */       } 
/*  464 */       write();
/*      */     }
/*      */     public Object getValue() {
/*      */       WinBase.FILETIME fILETIME;
/*      */       WinBase.SYSTEMTIME sYSTEMTIME;
/*      */       Guid.GUID gUID;
/*      */       WinNT.PSID result;
/*  471 */       Winevt.EVT_VARIANT_TYPE type = getVariantType();
/*  472 */       switch (type) {
/*      */         case EvtVarTypeAnsiString:
/*  474 */           return isArray() ? this.field1.getPointer().getPointer(0L).getStringArray(0L, this.Count) : this.field1.getPointer().getPointer(0L).getString(0L);
/*      */         case EvtVarTypeBoolean:
/*  476 */           if (isArray()) {
/*  477 */             int[] rawValue = this.field1.getPointer().getPointer(0L).getIntArray(0L, this.Count);
/*  478 */             WinDef.BOOL[] arrayOfBOOL = new WinDef.BOOL[rawValue.length];
/*  479 */             for (int i = 0; i < arrayOfBOOL.length; i++) {
/*  480 */               arrayOfBOOL[i] = new WinDef.BOOL(rawValue[i]);
/*      */             }
/*  482 */             return arrayOfBOOL;
/*      */           } 
/*  484 */           return new WinDef.BOOL(this.field1.getPointer().getInt(0L));
/*      */         
/*      */         case EvtVarTypeString:
/*      */         case EvtVarTypeEvtXml:
/*  488 */           return isArray() ? this.field1.getPointer().getPointer(0L).getWideStringArray(0L, this.Count) : this.field1.getPointer().getPointer(0L).getWideString(0L);
/*      */         case EvtVarTypeFileTime:
/*  490 */           if (isArray()) {
/*  491 */             WinBase.FILETIME resultFirst = (WinBase.FILETIME)Structure.newInstance(WinBase.FILETIME.class, this.field1.getPointer().getPointer(0L));
/*  492 */             resultFirst.read();
/*  493 */             return resultFirst.toArray(this.Count);
/*      */           } 
/*  495 */           fILETIME = new WinBase.FILETIME(this.field1.getPointer());
/*  496 */           fILETIME.read();
/*  497 */           return fILETIME;
/*      */         
/*      */         case EvtVarTypeSysTime:
/*  500 */           if (isArray()) {
/*  501 */             WinBase.SYSTEMTIME resultFirst = (WinBase.SYSTEMTIME)Structure.newInstance(WinBase.SYSTEMTIME.class, this.field1.getPointer().getPointer(0L));
/*  502 */             resultFirst.read();
/*  503 */             return resultFirst.toArray(this.Count);
/*      */           } 
/*  505 */           sYSTEMTIME = (WinBase.SYSTEMTIME)Structure.newInstance(WinBase.SYSTEMTIME.class, this.field1.getPointer().getPointer(0L));
/*  506 */           sYSTEMTIME.read();
/*  507 */           return sYSTEMTIME;
/*      */         
/*      */         case EvtVarTypeSByte:
/*      */         case EvtVarTypeByte:
/*  511 */           return isArray() ? this.field1.getPointer().getPointer(0L).getByteArray(0L, this.Count) : Byte.valueOf(this.field1.getPointer().getByte(0L));
/*      */         case EvtVarTypeInt16:
/*      */         case EvtVarTypeUInt16:
/*  514 */           return isArray() ? this.field1.getPointer().getPointer(0L).getShortArray(0L, this.Count) : Short.valueOf(this.field1.getPointer().getShort(0L));
/*      */         case EvtVarTypeHexInt32:
/*      */         case EvtVarTypeInt32:
/*      */         case EvtVarTypeUInt32:
/*  518 */           return isArray() ? this.field1.getPointer().getPointer(0L).getIntArray(0L, this.Count) : Integer.valueOf(this.field1.getPointer().getInt(0L));
/*      */         case EvtVarTypeHexInt64:
/*      */         case EvtVarTypeInt64:
/*      */         case EvtVarTypeUInt64:
/*  522 */           return isArray() ? this.field1.getPointer().getPointer(0L).getLongArray(0L, this.Count) : Long.valueOf(this.field1.getPointer().getLong(0L));
/*      */         case EvtVarTypeSingle:
/*  524 */           return isArray() ? this.field1.getPointer().getPointer(0L).getFloatArray(0L, this.Count) : Float.valueOf(this.field1.getPointer().getFloat(0L));
/*      */         case EvtVarTypeDouble:
/*  526 */           return isArray() ? this.field1.getPointer().getPointer(0L).getDoubleArray(0L, this.Count) : Double.valueOf(this.field1.getPointer().getDouble(0L));
/*      */         case EvtVarTypeBinary:
/*  528 */           assert !isArray();
/*  529 */           return this.field1.getPointer().getPointer(0L).getByteArray(0L, this.Count);
/*      */         case EvtVarTypeNull:
/*  531 */           return null;
/*      */         case EvtVarTypeGuid:
/*  533 */           if (isArray()) {
/*  534 */             Guid.GUID resultFirst = (Guid.GUID)Structure.newInstance(Guid.GUID.class, this.field1.getPointer().getPointer(0L));
/*  535 */             resultFirst.read();
/*  536 */             return resultFirst.toArray(this.Count);
/*      */           } 
/*  538 */           gUID = (Guid.GUID)Structure.newInstance(Guid.GUID.class, this.field1.getPointer().getPointer(0L));
/*  539 */           gUID.read();
/*  540 */           return gUID;
/*      */         
/*      */         case EvtVarTypeSid:
/*  543 */           if (isArray()) {
/*  544 */             WinNT.PSID resultFirst = (WinNT.PSID)Structure.newInstance(WinNT.PSID.class, this.field1.getPointer().getPointer(0L));
/*  545 */             resultFirst.read();
/*  546 */             return resultFirst.toArray(this.Count);
/*      */           } 
/*  548 */           result = (WinNT.PSID)Structure.newInstance(WinNT.PSID.class, this.field1.getPointer().getPointer(0L));
/*  549 */           result.read();
/*  550 */           return result;
/*      */         
/*      */         case EvtVarTypeSizeT:
/*  553 */           if (isArray()) {
/*  554 */             long[] rawValue = this.field1.getPointer().getPointer(0L).getLongArray(0L, this.Count);
/*  555 */             BaseTSD.SIZE_T[] arrayOfSIZE_T = new BaseTSD.SIZE_T[rawValue.length];
/*  556 */             for (int i = 0; i < arrayOfSIZE_T.length; i++) {
/*  557 */               arrayOfSIZE_T[i] = new BaseTSD.SIZE_T(rawValue[i]);
/*      */             }
/*  559 */             return arrayOfSIZE_T;
/*      */           } 
/*  561 */           return new BaseTSD.SIZE_T(this.field1.getPointer().getLong(0L));
/*      */         
/*      */         case EvtVarTypeEvtHandle:
/*  564 */           if (isArray()) {
/*  565 */             Pointer[] rawValue = this.field1.getPointer().getPointer(0L).getPointerArray(0L, this.Count);
/*  566 */             WinNT.HANDLE[] arrayOfHANDLE = new WinNT.HANDLE[rawValue.length];
/*  567 */             for (int i = 0; i < arrayOfHANDLE.length; i++) {
/*  568 */               arrayOfHANDLE[i] = new WinNT.HANDLE(rawValue[i]);
/*      */             }
/*  570 */             return arrayOfHANDLE;
/*      */           } 
/*  572 */           return new WinNT.HANDLE(this.field1.getPointer().getPointer(0L));
/*      */       } 
/*      */       
/*  575 */       throw new IllegalStateException(String.format("NOT IMPLEMENTED: getValue(%s) (Array: %b, Count: %d)", new Object[] { type, Boolean.valueOf(isArray()), Integer.valueOf(this.Count) }));
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
/*      */   public static class EVT_RPC_LOGIN
/*      */     extends Structure
/*      */   {
/*      */     public String Server;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String User;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String Domain;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String Password;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int Flags;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public EVT_RPC_LOGIN() {
/*  630 */       super(W32APITypeMapper.UNICODE);
/*      */     }
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  634 */       return Arrays.asList(new String[] { "Server", "User", "Domain", "Password", "Flags" });
/*      */     }
/*      */     
/*      */     public EVT_RPC_LOGIN(String Server, String User, String Domain, String Password, int Flags) {
/*  638 */       super(W32APITypeMapper.UNICODE);
/*  639 */       this.Server = Server;
/*  640 */       this.User = User;
/*  641 */       this.Domain = Domain;
/*  642 */       this.Password = Password;
/*  643 */       this.Flags = Flags;
/*      */     }
/*      */     
/*      */     public EVT_RPC_LOGIN(Pointer peer) {
/*  647 */       super(peer, 0, W32APITypeMapper.UNICODE);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */       extends EVT_RPC_LOGIN
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static class ByValue
/*      */       extends EVT_RPC_LOGIN
/*      */       implements Structure.ByValue {}
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class EVT_HANDLE
/*      */     extends WinNT.HANDLE
/*      */   {
/*      */     public EVT_HANDLE() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public EVT_HANDLE(Pointer p) {
/* 1705 */       super(p);
/*      */     }
/*      */   }
/*      */   
/*      */   public static interface EVT_EVENT_PROPERTY_ID {
/*      */     public static final int EvtEventQueryIDs = 0;
/*      */     public static final int EvtEventPath = 1;
/*      */     public static final int EvtEventPropertyIdEND = 2;
/*      */   }
/*      */   
/*      */   public static interface EVT_QUERY_PROPERTY_ID {
/*      */     public static final int EvtQueryNames = 0;
/*      */     public static final int EvtQueryStatuses = 1;
/*      */     public static final int EvtQueryPropertyIdEND = 2;
/*      */   }
/*      */   
/*      */   public static interface EVT_EVENT_METADATA_PROPERTY_ID {
/*      */     public static final int EventMetadataEventID = 0;
/*      */     public static final int EventMetadataEventVersion = 1;
/*      */     public static final int EventMetadataEventChannel = 2;
/*      */     public static final int EventMetadataEventLevel = 3;
/*      */     public static final int EventMetadataEventOpcode = 4;
/*      */     public static final int EventMetadataEventTask = 5;
/*      */     public static final int EventMetadataEventKeyword = 6;
/*      */     public static final int EventMetadataEventMessageID = 7;
/*      */     public static final int EventMetadataEventTemplate = 8;
/*      */     public static final int EvtEventMetadataPropertyIdEND = 9;
/*      */   }
/*      */   
/*      */   public static interface EVT_PUBLISHER_METADATA_PROPERTY_ID {
/*      */     public static final int EvtPublisherMetadataPublisherGuid = 0;
/*      */     public static final int EvtPublisherMetadataResourceFilePath = 1;
/*      */     public static final int EvtPublisherMetadataParameterFilePath = 2;
/*      */     public static final int EvtPublisherMetadataMessageFilePath = 3;
/*      */     public static final int EvtPublisherMetadataHelpLink = 4;
/*      */     public static final int EvtPublisherMetadataPublisherMessageID = 5;
/*      */     public static final int EvtPublisherMetadataChannelReferences = 6;
/*      */     public static final int EvtPublisherMetadataChannelReferencePath = 7;
/*      */     public static final int EvtPublisherMetadataChannelReferenceIndex = 8;
/*      */     public static final int EvtPublisherMetadataChannelReferenceID = 9;
/*      */     public static final int EvtPublisherMetadataChannelReferenceFlags = 10;
/*      */     public static final int EvtPublisherMetadataChannelReferenceMessageID = 11;
/*      */     public static final int EvtPublisherMetadataLevels = 12;
/*      */     public static final int EvtPublisherMetadataLevelName = 13;
/*      */     public static final int EvtPublisherMetadataLevelValue = 14;
/*      */     public static final int EvtPublisherMetadataLevelMessageID = 15;
/*      */     public static final int EvtPublisherMetadataTasks = 16;
/*      */     public static final int EvtPublisherMetadataTaskName = 17;
/*      */     public static final int EvtPublisherMetadataTaskEventGuid = 18;
/*      */     public static final int EvtPublisherMetadataTaskValue = 19;
/*      */     public static final int EvtPublisherMetadataTaskMessageID = 20;
/*      */     public static final int EvtPublisherMetadataOpcodes = 21;
/*      */     public static final int EvtPublisherMetadataOpcodeName = 22;
/*      */     public static final int EvtPublisherMetadataOpcodeValue = 23;
/*      */     public static final int EvtPublisherMetadataOpcodeMessageID = 24;
/*      */     public static final int EvtPublisherMetadataKeywords = 25;
/*      */     public static final int EvtPublisherMetadataKeywordName = 26;
/*      */     public static final int EvtPublisherMetadataKeywordValue = 27;
/*      */     public static final int EvtPublisherMetadataKeywordMessageID = 28;
/*      */     public static final int EvtPublisherMetadataPropertyIdEND = 29;
/*      */   }
/*      */   
/*      */   public static interface EVT_CHANNEL_REFERENCE_FLAGS {
/*      */     public static final int EvtChannelReferenceImported = 1;
/*      */   }
/*      */   
/*      */   public static interface EVT_CHANNEL_SID_TYPE {
/*      */     public static final int EvtChannelSidTypeNone = 0;
/*      */     public static final int EvtChannelSidTypePublishing = 1;
/*      */   }
/*      */   
/*      */   public static interface EVT_CHANNEL_CLOCK_TYPE {
/*      */     public static final int EvtChannelClockTypeSystemTime = 0;
/*      */     public static final int EvtChannelClockTypeQPC = 1;
/*      */   }
/*      */   
/*      */   public static interface EVT_CHANNEL_ISOLATION_TYPE {
/*      */     public static final int EvtChannelIsolationTypeApplication = 0;
/*      */     public static final int EvtChannelIsolationTypeSystem = 1;
/*      */     public static final int EvtChannelIsolationTypeCustom = 2;
/*      */   }
/*      */   
/*      */   public static interface EVT_CHANNEL_TYPE {
/*      */     public static final int EvtChannelTypeAdmin = 0;
/*      */     public static final int EvtChannelTypeOperational = 1;
/*      */     public static final int EvtChannelTypeAnalytic = 2;
/*      */     public static final int EvtChannelTypeDebug = 3;
/*      */   }
/*      */   
/*      */   public static interface EVT_CHANNEL_CONFIG_PROPERTY_ID {
/*      */     public static final int EvtChannelConfigEnabled = 0;
/*      */     public static final int EvtChannelConfigIsolation = 1;
/*      */     public static final int EvtChannelConfigType = 2;
/*      */     public static final int EvtChannelConfigOwningPublisher = 3;
/*      */     public static final int EvtChannelConfigClassicEventlog = 4;
/*      */     public static final int EvtChannelConfigAccess = 5;
/*      */     public static final int EvtChannelLoggingConfigRetention = 6;
/*      */     public static final int EvtChannelLoggingConfigAutoBackup = 7;
/*      */     public static final int EvtChannelLoggingConfigMaxSize = 8;
/*      */     public static final int EvtChannelLoggingConfigLogFilePath = 9;
/*      */     public static final int EvtChannelPublishingConfigLevel = 10;
/*      */     public static final int EvtChannelPublishingConfigKeywords = 11;
/*      */     public static final int EvtChannelPublishingConfigControlGuid = 12;
/*      */     public static final int EvtChannelPublishingConfigBufferSize = 13;
/*      */     public static final int EvtChannelPublishingConfigMinBuffers = 14;
/*      */     public static final int EvtChannelPublishingConfigMaxBuffers = 15;
/*      */     public static final int EvtChannelPublishingConfigLatency = 16;
/*      */     public static final int EvtChannelPublishingConfigClockType = 17;
/*      */     public static final int EvtChannelPublishingConfigSidType = 18;
/*      */     public static final int EvtChannelPublisherList = 19;
/*      */     public static final int EvtChannelPublishingConfigFileMax = 20;
/*      */     public static final int EvtChannelConfigPropertyIdEND = 21;
/*      */   }
/*      */   
/*      */   public static interface EVT_EXPORTLOG_FLAGS {
/*      */     public static final int EvtExportLogChannelPath = 1;
/*      */     public static final int EvtExportLogFilePath = 2;
/*      */     public static final int EvtExportLogTolerateQueryErrors = 4096;
/*      */     public static final int EvtExportLogOverwrite = 8192;
/*      */   }
/*      */   
/*      */   public static interface EVT_LOG_PROPERTY_ID {
/*      */     public static final int EvtLogCreationTime = 0;
/*      */     public static final int EvtLogLastAccessTime = 1;
/*      */     public static final int EvtLogLastWriteTime = 2;
/*      */     public static final int EvtLogFileSize = 3;
/*      */     public static final int EvtLogAttributes = 4;
/*      */     public static final int EvtLogNumberOfLogRecords = 5;
/*      */     public static final int EvtLogOldestRecordNumber = 6;
/*      */     public static final int EvtLogFull = 7;
/*      */   }
/*      */   
/*      */   public static interface EVT_OPEN_LOG_FLAGS {
/*      */     public static final int EvtOpenChannelPath = 1;
/*      */     public static final int EvtOpenFilePath = 2;
/*      */   }
/*      */   
/*      */   public static interface EVT_FORMAT_MESSAGE_FLAGS {
/*      */     public static final int EvtFormatMessageEvent = 1;
/*      */     public static final int EvtFormatMessageLevel = 2;
/*      */     public static final int EvtFormatMessageTask = 3;
/*      */     public static final int EvtFormatMessageOpcode = 4;
/*      */     public static final int EvtFormatMessageKeyword = 5;
/*      */     public static final int EvtFormatMessageChannel = 6;
/*      */     public static final int EvtFormatMessageProvider = 7;
/*      */     public static final int EvtFormatMessageId = 8;
/*      */     public static final int EvtFormatMessageXml = 9;
/*      */   }
/*      */   
/*      */   public static interface EVT_RENDER_FLAGS {
/*      */     public static final int EvtRenderEventValues = 0;
/*      */     public static final int EvtRenderEventXml = 1;
/*      */     public static final int EvtRenderBookmark = 2;
/*      */   }
/*      */   
/*      */   public static interface EVT_RENDER_CONTEXT_FLAGS {
/*      */     public static final int EvtRenderContextValues = 0;
/*      */     public static final int EvtRenderContextSystem = 1;
/*      */     public static final int EvtRenderContextUser = 2;
/*      */   }
/*      */   
/*      */   public static interface EVT_SYSTEM_PROPERTY_ID {
/*      */     public static final int EvtSystemProviderName = 0;
/*      */     public static final int EvtSystemProviderGuid = 1;
/*      */     public static final int EvtSystemEventID = 2;
/*      */     public static final int EvtSystemQualifiers = 3;
/*      */     public static final int EvtSystemLevel = 4;
/*      */     public static final int EvtSystemTask = 5;
/*      */     public static final int EvtSystemOpcode = 6;
/*      */     public static final int EvtSystemKeywords = 7;
/*      */     public static final int EvtSystemTimeCreated = 8;
/*      */     public static final int EvtSystemEventRecordId = 9;
/*      */     public static final int EvtSystemActivityID = 10;
/*      */     public static final int EvtSystemRelatedActivityID = 11;
/*      */     public static final int EvtSystemProcessID = 12;
/*      */     public static final int EvtSystemThreadID = 13;
/*      */     public static final int EvtSystemChannel = 14;
/*      */     public static final int EvtSystemComputer = 15;
/*      */     public static final int EvtSystemUserID = 16;
/*      */     public static final int EvtSystemVersion = 17;
/*      */     public static final int EvtSystemPropertyIdEND = 18;
/*      */   }
/*      */   
/*      */   public static interface EVT_SUBSCRIBE_NOTIFY_ACTION {
/*      */     public static final int EvtSubscribeActionError = 0;
/*      */     public static final int EvtSubscribeActionDeliver = 1;
/*      */   }
/*      */   
/*      */   public static interface EVT_SUBSCRIBE_FLAGS {
/*      */     public static final int EvtSubscribeToFutureEvents = 1;
/*      */     public static final int EvtSubscribeStartAtOldestRecord = 2;
/*      */     public static final int EvtSubscribeStartAfterBookmark = 3;
/*      */     public static final int EvtSubscribeOriginMask = 3;
/*      */     public static final int EvtSubscribeTolerateQueryErrors = 4096;
/*      */     public static final int EvtSubscribeStrict = 65536;
/*      */   }
/*      */   
/*      */   public static interface EVT_SEEK_FLAGS {
/*      */     public static final int EvtSeekRelativeToFirst = 1;
/*      */     public static final int EvtSeekRelativeToLast = 2;
/*      */     public static final int EvtSeekRelativeToCurrent = 3;
/*      */     public static final int EvtSeekRelativeToBookmark = 4;
/*      */     public static final int EvtSeekOriginMask = 7;
/*      */     public static final int EvtSeekStrict = 65536;
/*      */   }
/*      */   
/*      */   public static interface EVT_QUERY_FLAGS {
/*      */     public static final int EvtQueryChannelPath = 1;
/*      */     public static final int EvtQueryFilePath = 2;
/*      */     public static final int EvtQueryForwardDirection = 256;
/*      */     public static final int EvtQueryReverseDirection = 512;
/*      */     public static final int EvtQueryTolerateQueryErrors = 4096;
/*      */   }
/*      */   
/*      */   public static interface EVT_RPC_LOGIN_FLAGS {
/*      */     public static final int EvtRpcLoginAuthDefault = 0;
/*      */     public static final int EvtRpcLoginAuthNegotiate = 1;
/*      */     public static final int EvtRpcLoginAuthKerberos = 2;
/*      */     public static final int EvtRpcLoginAuthNTLM = 3;
/*      */   }
/*      */   
/*      */   public static interface EVT_LOGIN_CLASS {
/*      */     public static final int EvtRpcLogin = 1;
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/Winevt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */