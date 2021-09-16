/*    */ package Catalano.Math.Distances;
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
/*    */ public class MinkowskiDistance
/*    */   implements IDistance<double[]>
/*    */ {
/* 33 */   private double p = 1.0D;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getOrder() {
/* 40 */     return this.p;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setOrder(double p) {
/* 48 */     if (p == 0.0D)
/* 49 */       throw new IllegalArgumentException("P must be different from 0."); 
/* 50 */     this.p = p;
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
/*    */   
/*    */   public MinkowskiDistance(double p) {
/* 64 */     setOrder(p);
/*    */   }
/*    */ 
/*    */   
/*    */   public double Compute(double[] u, double[] v) {
/* 69 */     return Distance.Minkowski(u, v, this.p);
/*    */   }
/*    */   
/*    */   public MinkowskiDistance() {}
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Distances/MinkowskiDistance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */