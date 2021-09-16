/*    */ package us.hebi.matlab.mat.format;
/*    */ 
/*    */ import java.nio.charset.Charset;
/*    */ import java.nio.charset.UnsupportedCharsetException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Charsets
/*    */ {
/* 37 */   public static final Charset US_ASCII = Charset.forName("US-ASCII");
/* 38 */   public static final Charset UTF_8 = Charset.forName("UTF-8");
/* 39 */   public static final Charset UTF_16BE = Charset.forName("UTF-16BE");
/* 40 */   public static final Charset UTF_16LE = Charset.forName("UTF-16LE");
/*    */ 
/*    */   
/* 43 */   public static final Charset UTF_32BE = forNameOrNull("UTF-32BE");
/* 44 */   public static final Charset UTF_32LE = forNameOrNull("UTF-32LE");
/*    */   
/*    */   private static Charset forNameOrNull(String name) {
/*    */     try {
/* 48 */       return Charset.forName(name);
/* 49 */     } catch (UnsupportedCharsetException uce) {
/* 50 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/Charsets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */