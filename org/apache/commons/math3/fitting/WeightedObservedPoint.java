/*    */ package org.apache.commons.math3.fitting;
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
/*    */ public class WeightedObservedPoint
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 5306874947404636157L;
/*    */   private final double weight;
/*    */   private final double x;
/*    */   private final double y;
/*    */   
/*    */   public WeightedObservedPoint(double weight, double x, double y) {
/* 45 */     this.weight = weight;
/* 46 */     this.x = x;
/* 47 */     this.y = y;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getWeight() {
/* 56 */     return this.weight;
/*    */   }
/*    */ 
/*    */ 
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
/*    */ 
/*    */   
/*    */   public double getY() {
/* 74 */     return this.y;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fitting/WeightedObservedPoint.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */