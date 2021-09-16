/*     */ package us.hebi.matlab.mat.format;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import us.hebi.matlab.mat.types.AbstractStruct;
/*     */ import us.hebi.matlab.mat.types.Array;
/*     */ import us.hebi.matlab.mat.types.MatlabType;
/*     */ import us.hebi.matlab.mat.types.Sink;
/*     */ import us.hebi.matlab.mat.util.Preconditions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MatStruct
/*     */   extends AbstractStruct
/*     */   implements Mat5Serializable
/*     */ {
/*     */   private static final byte NULL_TERMINATOR = 0;
/*     */   private static final int NULL_TERMINATOR_LENGTH = 1;
/*     */   
/*     */   MatStruct(int[] dims) {
/*  46 */     super(dims);
/*     */   }
/*     */   
/*     */   MatStruct(int[] dims, String[] names, Array[][] values) {
/*  50 */     super(dims);
/*  51 */     Preconditions.checkArgument((getNumDimensions() == 2), "Structures are limited to two dimensions");
/*  52 */     int numElements = getNumElements();
/*  53 */     for (int field = 0; field < names.length; field++) {
/*  54 */       for (int i = 0; i < numElements; i++) {
/*  55 */         set(names[field], i, values[field][i]);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Array getEmptyValue() {
/*  62 */     return Mat5.EMPTY_MATRIX;
/*     */   }
/*     */   
/*     */   protected String getClassName() {
/*  66 */     return "";
/*     */   }
/*     */   
/*     */   protected int getLongestFieldName() {
/*  70 */     int length = 0;
/*  71 */     for (String name : getFieldNames()) {
/*  72 */       length = Math.max(length, Mat5WriteUtil.getLimitedNameLength(name));
/*     */     }
/*  74 */     return length + 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMat5Size(String name) {
/*  79 */     List<String> fieldNames = getFieldNames();
/*  80 */     int numElements = getNumElements();
/*  81 */     int numFields = fieldNames.size();
/*     */ 
/*     */     
/*  84 */     int size = 8;
/*  85 */     size += Mat5WriteUtil.computeArrayHeaderSize(name, (Array)this);
/*     */ 
/*     */     
/*  88 */     if (getType() == MatlabType.Object) {
/*  89 */       String objectClassName = getClassName();
/*  90 */       size += Mat5Type.Int8.computeSerializedSize(objectClassName.length());
/*     */     } 
/*     */ 
/*     */     
/*  94 */     size += Mat5Type.Int32.computeSerializedSize(1);
/*     */ 
/*     */     
/*  97 */     int numChars = getLongestFieldName() * numFields;
/*  98 */     size += Mat5Type.Int8.computeSerializedSize(numChars);
/*     */ 
/*     */     
/* 101 */     for (int i = 0; i < numElements; i++) {
/* 102 */       for (int field = 0; field < numFields; field++) {
/* 103 */         size += Mat5WriteUtil.computeArraySize(get(fieldNames.get(field), i));
/*     */       }
/*     */     } 
/*     */     
/* 107 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeMat5(String name, boolean isGlobal, Sink sink) throws IOException {
/* 112 */     List<String> fieldNames = getFieldNames();
/* 113 */     int numElements = getNumElements();
/* 114 */     int numFields = fieldNames.size();
/*     */ 
/*     */     
/* 117 */     Mat5WriteUtil.writeMatrixTag(name, this, sink);
/* 118 */     Mat5WriteUtil.writeArrayHeader(name, isGlobal, (Array)this, sink);
/*     */ 
/*     */     
/* 121 */     if (getType() == MatlabType.Object) {
/* 122 */       String objectClassName = getClassName();
/* 123 */       Mat5Type.Int8.writeBytesWithTag(objectClassName.getBytes(Charsets.US_ASCII), sink);
/*     */     } 
/*     */ 
/*     */     
/* 127 */     int longestName = getLongestFieldName();
/* 128 */     int numChars = longestName * numFields;
/* 129 */     Mat5Type.Int32.writeIntsWithTag(new int[] { longestName }, sink);
/*     */ 
/*     */     
/* 132 */     byte[] ascii = new byte[numChars];
/* 133 */     Arrays.fill(ascii, (byte)0); int i;
/* 134 */     for (i = 0; i < numFields; i++) {
/* 135 */       String fieldName = Mat5WriteUtil.getLimitedName(getFieldNames().get(i));
/* 136 */       byte[] bytes = fieldName.getBytes(Charsets.US_ASCII);
/* 137 */       System.arraycopy(bytes, 0, ascii, i * longestName, bytes.length);
/*     */     } 
/* 139 */     Mat5Type.Int8.writeBytesWithTag(ascii, sink);
/*     */ 
/*     */     
/* 142 */     Preconditions.checkArgument((getNumDimensions() == 2), "Structures are limited to two dimensions");
/* 143 */     for (i = 0; i < numElements; i++) {
/* 144 */       for (int field = 0; field < numFields; field++)
/* 145 */         Mat5WriteUtil.writeNestedArray(get(fieldNames.get(field), i), sink); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/MatStruct.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */