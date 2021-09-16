/*     */ package com.jmatio.common;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MatDataTypes
/*     */ {
/*     */   public static final int miUNKNOWN = 0;
/*     */   public static final int miINT8 = 1;
/*     */   public static final int miUINT8 = 2;
/*     */   public static final int miINT16 = 3;
/*     */   public static final int miUINT16 = 4;
/*     */   public static final int miINT32 = 5;
/*     */   public static final int miUINT32 = 6;
/*     */   public static final int miSINGLE = 7;
/*     */   public static final int miDOUBLE = 9;
/*     */   public static final int miINT64 = 12;
/*     */   public static final int miUINT64 = 13;
/*     */   public static final int miMATRIX = 14;
/*     */   public static final int miCOMPRESSED = 15;
/*     */   public static final int miUTF8 = 16;
/*     */   public static final int miUTF16 = 17;
/*     */   public static final int miUTF32 = 18;
/*     */   public static final int miSIZE_INT64 = 8;
/*     */   public static final int miSIZE_INT32 = 4;
/*     */   public static final int miSIZE_INT16 = 2;
/*     */   public static final int miSIZE_INT8 = 1;
/*     */   public static final int miSIZE_UINT64 = 8;
/*     */   public static final int miSIZE_UINT32 = 4;
/*     */   public static final int miSIZE_UINT16 = 2;
/*     */   public static final int miSIZE_UINT8 = 1;
/*     */   public static final int miSIZE_DOUBLE = 8;
/*     */   public static final int miSIZE_CHAR = 1;
/*     */   
/*     */   public static int sizeOf(int type) {
/*  47 */     switch (type) {
/*     */       
/*     */       case 1:
/*  50 */         return 1;
/*     */       case 2:
/*  52 */         return 1;
/*     */       case 3:
/*  54 */         return 2;
/*     */       case 4:
/*  56 */         return 2;
/*     */       case 5:
/*  58 */         return 4;
/*     */       case 6:
/*  60 */         return 4;
/*     */       case 12:
/*  62 */         return 8;
/*     */       case 13:
/*  64 */         return 8;
/*     */       case 9:
/*  66 */         return 8;
/*     */     } 
/*  68 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String typeToString(int type) {
/*  80 */     switch (type)
/*     */     
/*     */     { case 0:
/*  83 */         s = "unknown";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 133 */         return s;case 1: s = "int8"; return s;case 2: s = "uint8"; return s;case 3: s = "int16"; return s;case 4: s = "uint16"; return s;case 5: s = "int32"; return s;case 6: s = "uint32"; return s;case 7: s = "single"; return s;case 9: s = "double"; return s;case 12: s = "int64"; return s;case 13: s = "uint64"; return s;case 14: s = "matrix"; return s;case 15: s = "compressed"; return s;case 16: s = "uft8"; return s;case 17: s = "utf16"; return s;case 18: s = "utf32"; return s; }  String s = "unknown"; return s;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/common/MatDataTypes.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */