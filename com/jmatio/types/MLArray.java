/*     */ package com.jmatio.types;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MLArray
/*     */ {
/*     */   public static final int mxUNKNOWN_CLASS = 0;
/*     */   public static final int mxCELL_CLASS = 1;
/*     */   public static final int mxSTRUCT_CLASS = 2;
/*     */   public static final int mxOBJECT_CLASS = 3;
/*     */   public static final int mxCHAR_CLASS = 4;
/*     */   public static final int mxSPARSE_CLASS = 5;
/*     */   public static final int mxDOUBLE_CLASS = 6;
/*     */   public static final int mxSINGLE_CLASS = 7;
/*     */   public static final int mxINT8_CLASS = 8;
/*     */   public static final int mxUINT8_CLASS = 9;
/*     */   public static final int mxINT16_CLASS = 10;
/*     */   public static final int mxUINT16_CLASS = 11;
/*     */   public static final int mxINT32_CLASS = 12;
/*     */   public static final int mxUINT32_CLASS = 13;
/*     */   public static final int mxINT64_CLASS = 14;
/*     */   public static final int mxUINT64_CLASS = 15;
/*     */   public static final int mxFUNCTION_CLASS = 16;
/*     */   public static final int mxOPAQUE_CLASS = 17;
/*     */   public static final int mtFLAG_COMPLEX = 2048;
/*     */   public static final int mtFLAG_GLOBAL = 1024;
/*     */   public static final int mtFLAG_LOGICAL = 512;
/*     */   public static final int mtFLAG_TYPE = 255;
/*     */   protected int[] dims;
/*     */   public String name;
/*     */   protected int attributes;
/*     */   protected int type;
/*     */   
/*     */   public MLArray(String name, int[] dims, int type, int attributes) {
/*  38 */     this.dims = new int[dims.length];
/*  39 */     System.arraycopy(dims, 0, this.dims, 0, dims.length);
/*     */ 
/*     */     
/*  42 */     if (name != null && !name.equals("")) {
/*     */       
/*  44 */       this.name = name;
/*     */     }
/*     */     else {
/*     */       
/*  48 */       this.name = "@";
/*     */     } 
/*     */ 
/*     */     
/*  52 */     this.type = type;
/*  53 */     this.attributes = attributes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  63 */     return this.name;
/*     */   }
/*     */   
/*     */   public int getFlags() {
/*  67 */     int flags = this.type & 0xFF | this.attributes & 0xFFFFFF00;
/*     */     
/*  69 */     return flags;
/*     */   }
/*     */   
/*     */   public byte[] getNameToByteArray() {
/*  73 */     return this.name.getBytes();
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getDimensions() {
/*  78 */     int[] ai = null;
/*  79 */     if (this.dims != null) {
/*     */       
/*  81 */       ai = new int[this.dims.length];
/*  82 */       System.arraycopy(this.dims, 0, ai, 0, this.dims.length);
/*     */     } 
/*  84 */     return ai;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getM() {
/*  89 */     int i = 0;
/*  90 */     if (this.dims != null)
/*     */     {
/*  92 */       i = this.dims[0];
/*     */     }
/*  94 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getN() {
/*  99 */     int i = 0;
/* 100 */     if (this.dims != null)
/*     */     {
/* 102 */       if (this.dims.length > 2) {
/*     */         
/* 104 */         i = 1;
/* 105 */         for (int j = 1; j < this.dims.length; j++)
/*     */         {
/* 107 */           i *= this.dims[j];
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 112 */         i = this.dims[1];
/*     */       } 
/*     */     }
/* 115 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNDimensions() {
/* 120 */     int i = 0;
/* 121 */     if (this.dims != null)
/*     */     {
/* 123 */       i = this.dims.length;
/*     */     }
/* 125 */     return i;
/*     */   }
/*     */   
/*     */   public int getSize() {
/* 129 */     return getM() * getN();
/*     */   }
/*     */   
/*     */   public int getType() {
/* 133 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 138 */     return (getN() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String typeToString(int type) {
/* 144 */     switch (type)
/*     */     
/*     */     { case 0:
/* 147 */         s = "unknown";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 204 */         return s;case 1: s = "cell"; return s;case 2: s = "struct"; return s;case 4: s = "char"; return s;case 5: s = "sparse"; return s;case 6: s = "double"; return s;case 7: s = "single"; return s;case 8: s = "int8"; return s;case 9: s = "uint8"; return s;case 10: s = "int16"; return s;case 11: s = "uint16"; return s;case 12: s = "int32"; return s;case 13: s = "uint32"; return s;case 14: s = "int64"; return s;case 15: s = "uint64"; return s;case 16: s = "function_handle"; return s;case 17: s = "opaque"; return s;case 3: s = "object"; return s; }  String s = "unknown"; return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCell() {
/* 209 */     return (this.type == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isChar() {
/* 214 */     return (this.type == 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isComplex() {
/* 219 */     return ((this.attributes & 0x800) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSparse() {
/* 224 */     return (this.type == 5);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isStruct() {
/* 229 */     return (this.type == 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDouble() {
/* 234 */     return (this.type == 6);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSingle() {
/* 239 */     return (this.type == 7);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInt8() {
/* 244 */     return (this.type == 8);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUint8() {
/* 249 */     return (this.type == 9);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInt16() {
/* 254 */     return (this.type == 10);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUint16() {
/* 259 */     return (this.type == 11);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInt32() {
/* 264 */     return (this.type == 12);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUint32() {
/* 269 */     return (this.type == 13);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInt64() {
/* 274 */     return (this.type == 14);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUint64() {
/* 279 */     return (this.type == 15);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isObject() {
/* 284 */     return (this.type == 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpaque() {
/* 289 */     return (this.type == 17);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLogical() {
/* 294 */     return ((this.attributes & 0x200) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFunctionObject() {
/* 299 */     return (this.type == 16);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUnknown() {
/* 304 */     return (this.type == 0);
/*     */   }
/*     */   
/*     */   protected int getIndex(int m, int n) {
/* 308 */     return m + n * getM();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 313 */     StringBuffer sb = new StringBuffer();
/* 314 */     if (this.dims != null) {
/*     */       
/* 316 */       sb.append('[');
/* 317 */       if (this.dims.length > 3) {
/*     */         
/* 319 */         sb.append(this.dims.length);
/* 320 */         sb.append('D');
/*     */       }
/*     */       else {
/*     */         
/* 324 */         sb.append(this.dims[0]);
/* 325 */         sb.append('x');
/* 326 */         sb.append(this.dims[1]);
/* 327 */         if (this.dims.length == 3) {
/*     */           
/* 329 */           sb.append('x');
/* 330 */           sb.append(this.dims[2]);
/*     */         } 
/*     */       } 
/* 333 */       sb.append("  ");
/* 334 */       sb.append(typeToString(this.type));
/* 335 */       sb.append(" array");
/* 336 */       if (isSparse()) {
/*     */         
/* 338 */         sb.append(" (sparse");
/* 339 */         if (isComplex())
/*     */         {
/* 341 */           sb.append(" complex");
/*     */         }
/* 343 */         sb.append(")");
/*     */       }
/* 345 */       else if (isComplex()) {
/*     */         
/* 347 */         sb.append(" (complex)");
/*     */       } 
/* 349 */       sb.append(']');
/*     */     }
/*     */     else {
/*     */       
/* 353 */       sb.append("[invalid]");
/*     */     } 
/* 355 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String contentToString() {
/* 360 */     return "content cannot be displayed";
/*     */   }
/*     */   
/*     */   public void dispose() {}
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/types/MLArray.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */