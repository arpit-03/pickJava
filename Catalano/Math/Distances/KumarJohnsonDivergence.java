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
/*    */ public class KumarJohnsonDivergence
/*    */   implements IDivergence<double[]>
/*    */ {
/*    */   public double Compute(double[] u, double[] v) {
/* 38 */     return Distance.KumarJohnsonDivergence(u, v);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Distances/KumarJohnsonDivergence.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */