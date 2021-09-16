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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IntersectionDistance
/*    */   implements IDistance<double[]>
/*    */ {
/*    */   public double Compute(double[] u, double[] v) {
/* 38 */     double r = 0.0D;
/* 39 */     for (int i = 0; i < u.length; i++) {
/* 40 */       r += Math.min(u[i], v[i]);
/*    */     }
/*    */     
/* 43 */     return r;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Distances/IntersectionDistance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */