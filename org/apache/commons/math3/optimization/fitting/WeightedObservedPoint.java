/*    */ package org.apache.commons.math3.optimization.fitting;
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
/*    */ 
/*    */ @Deprecated
/*    */ public class WeightedObservedPoint
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 5306874947404636157L;
/*    */   private final double weight;
/*    */   private final double x;
/*    */   private final double y;
/*    */   
/*    */   public WeightedObservedPoint(double weight, double x, double y) {
/* 49 */     this.weight = weight;
/* 50 */     this.x = x;
/* 51 */     this.y = y;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getWeight() {
/* 58 */     return this.weight;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getX() {
/* 65 */     return this.x;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getY() {
/* 72 */     return this.y;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/fitting/WeightedObservedPoint.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */