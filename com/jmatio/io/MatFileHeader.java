/*    */ package com.jmatio.io;
/*    */ 
/*    */ import java.util.Date;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MatFileHeader
/*    */ {
/* 15 */   private static String DEFAULT_DESCRIPTIVE_TEXT = "MATLAB 5.0 MAT-file, Platform: " + System.getProperty("os.name") + ", CREATED on: ";
/*    */ 
/*    */   
/* 18 */   private static int DEFAULT_VERSION = 256;
/* 19 */   private static byte[] DEFAULT_ENDIAN_INDICATOR = new byte[] { 77, 73 };
/*    */ 
/*    */ 
/*    */   
/*    */   private int version;
/*    */ 
/*    */   
/*    */   private String description;
/*    */ 
/*    */   
/*    */   private byte[] endianIndicator;
/*    */ 
/*    */ 
/*    */   
/*    */   public MatFileHeader(String description, int version, byte[] endianIndicator) {
/* 34 */     this.description = description;
/* 35 */     this.version = version;
/* 36 */     this.endianIndicator = endianIndicator;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 46 */     return this.description;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] getEndianIndicator() {
/* 56 */     return this.endianIndicator;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getVersion() {
/* 65 */     return this.version;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static MatFileHeader createHeader() {
/* 81 */     return new MatFileHeader(DEFAULT_DESCRIPTIVE_TEXT + (new Date()).toString(), DEFAULT_VERSION, DEFAULT_ENDIAN_INDICATOR);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 91 */     StringBuffer sb = new StringBuffer();
/* 92 */     sb.append("[");
/* 93 */     sb.append("desriptive text: " + this.description);
/* 94 */     sb.append(", version: " + this.version);
/* 95 */     sb.append(", endianIndicator: " + new String(this.endianIndicator));
/* 96 */     sb.append("]");
/*    */     
/* 98 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/io/MatFileHeader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */