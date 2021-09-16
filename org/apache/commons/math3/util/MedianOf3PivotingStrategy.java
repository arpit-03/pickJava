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
/*    */ public class MedianOf3PivotingStrategy
/*    */   implements PivotingStrategyInterface, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 20140713L;
/*    */   
/*    */   public int pivotIndex(double[] work, int begin, int end) throws MathIllegalArgumentException {
/* 41 */     MathArrays.verifyValues(work, begin, end - begin);
/* 42 */     int inclusiveEnd = end - 1;
/* 43 */     int middle = begin + (inclusiveEnd - begin) / 2;
/* 44 */     double wBegin = work[begin];
/* 45 */     double wMiddle = work[middle];
/* 46 */     double wEnd = work[inclusiveEnd];
/*    */     
/* 48 */     if (wBegin < wMiddle) {
/* 49 */       if (wMiddle < wEnd) {
/* 50 */         return middle;
/*    */       }
/* 52 */       return (wBegin < wEnd) ? inclusiveEnd : begin;
/*    */     } 
/*    */     
/* 55 */     if (wBegin < wEnd) {
/* 56 */       return begin;
/*    */     }
/* 58 */     return (wMiddle < wEnd) ? inclusiveEnd : middle;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/MedianOf3PivotingStrategy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */