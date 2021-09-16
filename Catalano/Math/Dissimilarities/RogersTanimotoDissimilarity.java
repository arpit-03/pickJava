/*    */ package Catalano.Math.Dissimilarities;
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
/*    */ public class RogersTanimotoDissimilarity
/*    */   implements IDissimilarity<int[]>
/*    */ {
/*    */   public double Compute(int[] u, int[] v) {
/* 38 */     return Dissimilarity.RogersTanimoto(u, v);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Dissimilarities/RogersTanimotoDissimilarity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */