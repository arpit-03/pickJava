/*    */ package us.hebi.matlab.mat.util;
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
/*    */ public class ByteConverters
/*    */ {
/*    */   public static ByteConverter getSafest() {
/* 30 */     return heapConverter;
/*    */   }
/*    */   
/*    */   public static ByteConverter getFastest() {
/* 34 */     return getFastest(true);
/*    */   }
/*    */   
/*    */   public static ByteConverter getFastest(boolean checkBounds) {
/* 38 */     UnsafeAccess.requireUnsafe();
/* 39 */     if (UnsafeAccess.isAvailable())
/* 40 */       return checkBounds ? unsafeConverterWithBoundsCheck : rawUnsafeConverter; 
/* 41 */     return heapConverter;
/*    */   }
/*    */ 
/*    */   
/* 45 */   private static final ByteConverter heapConverter = new HeapByteConverter();
/*    */   static {
/* 47 */     if (UnsafeAccess.isAvailable()) {
/* 48 */       UnsafeByteConverter unsafeConverter = new UnsafeByteConverter();
/* 49 */       rawUnsafeConverter = unsafeConverter;
/* 50 */       unsafeConverterWithBoundsCheck = new ArrayBoundsCheck(unsafeConverter);
/*    */     } else {
/* 52 */       rawUnsafeConverter = null;
/* 53 */       unsafeConverterWithBoundsCheck = null;
/*    */     } 
/*    */   }
/*    */   
/*    */   private static final ByteConverter rawUnsafeConverter;
/*    */   private static final ByteConverter unsafeConverterWithBoundsCheck;
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/util/ByteConverters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */