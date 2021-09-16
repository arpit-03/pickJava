/*    */ package org.apache.commons.math3.fraction;
/*    */ 
/*    */ import org.apache.commons.math3.exception.ConvergenceException;
/*    */ import org.apache.commons.math3.exception.util.Localizable;
/*    */ import org.apache.commons.math3.exception.util.LocalizedFormats;
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
/*    */ public class FractionConversionException
/*    */   extends ConvergenceException
/*    */ {
/*    */   private static final long serialVersionUID = -4661812640132576263L;
/*    */   
/*    */   public FractionConversionException(double value, int maxIterations) {
/* 41 */     super((Localizable)LocalizedFormats.FAILED_FRACTION_CONVERSION, new Object[] { Double.valueOf(value), Integer.valueOf(maxIterations) });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FractionConversionException(double value, long p, long q) {
/* 52 */     super((Localizable)LocalizedFormats.FRACTION_CONVERSION_OVERFLOW, new Object[] { Double.valueOf(value), Long.valueOf(p), Long.valueOf(q) });
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fraction/FractionConversionException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */