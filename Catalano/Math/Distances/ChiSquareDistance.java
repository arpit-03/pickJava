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
/*    */ public class ChiSquareDistance
/*    */   implements IDistance<double[]>
/*    */ {
/*    */   public double Compute(double[] u, double[] v) {
/* 38 */     double r = 0.0D;
/* 39 */     for (int i = 0; i < u.length; i++) {
/* 40 */       double t = u[i] + v[i];
/* 41 */       if (t != 0.0D) {
/* 42 */         r += Math.pow(u[i] - v[i], 2.0D) / t;
/*    */       }
/*    */     } 
/* 45 */     return 0.5D * r;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Distances/ChiSquareDistance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */