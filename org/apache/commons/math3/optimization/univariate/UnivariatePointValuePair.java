/*    */ package org.apache.commons.math3.optimization.univariate;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ @Deprecated
/*    */ public class UnivariatePointValuePair
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1003888396256744753L;
/*    */   private final double point;
/*    */   private final double value;
/*    */   
/*    */   public UnivariatePointValuePair(double point, double value) {
/* 47 */     this.point = point;
/* 48 */     this.value = value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getPoint() {
/* 57 */     return this.point;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getValue() {
/* 66 */     return this.value;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/univariate/UnivariatePointValuePair.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */