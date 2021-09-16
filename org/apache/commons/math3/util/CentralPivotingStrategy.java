/*    */ package org.apache.commons.math3.util;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
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
/*    */ public class CentralPivotingStrategy
/*    */   implements PivotingStrategyInterface, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 20140713L;
/*    */   
/*    */   public int pivotIndex(double[] work, int begin, int end) throws MathIllegalArgumentException {
/* 42 */     MathArrays.verifyValues(work, begin, end - begin);
/* 43 */     return begin + (end - begin) / 2;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/CentralPivotingStrategy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */