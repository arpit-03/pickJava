/*    */ package us.hebi.matlab.mat.format;
/*    */ 
/*    */ import java.util.Arrays;
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
/*    */ class Compat
/*    */ {
/*    */   public static int hash(Object... values) {
/* 34 */     return Arrays.hashCode(values);
/*    */   }
/*    */   
/*    */   public static boolean equals(Object a, Object b) {
/* 38 */     return !(a != b && (a == null || !a.equals(b)));
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/Compat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */