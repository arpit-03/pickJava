/*    */ package org.apache.commons.math3.complex;
/*    */ 
/*    */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*    */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*    */ import org.apache.commons.math3.util.FastMath;
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
/*    */ public class ComplexUtils
/*    */ {
/*    */   public static Complex polar2Complex(double r, double theta) throws MathIllegalArgumentException {
/* 62 */     if (r < 0.0D) {
/* 63 */       throw new MathIllegalArgumentException(LocalizedFormats.NEGATIVE_COMPLEX_MODULE, new Object[] { Double.valueOf(r) });
/*    */     }
/*    */     
/* 66 */     return new Complex(r * FastMath.cos(theta), r * FastMath.sin(theta));
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
/*    */   public static Complex[] convertToComplex(double[] real) {
/* 79 */     Complex[] c = new Complex[real.length];
/* 80 */     for (int i = 0; i < real.length; i++) {
/* 81 */       c[i] = new Complex(real[i], 0.0D);
/*    */     }
/*    */     
/* 84 */     return c;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/complex/ComplexUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */