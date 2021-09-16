/*    */ package Catalano.Math.Dissimilarities;
/*    */ 
/*    */ import Catalano.Math.Distances.IDistance;
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
/*    */ public class DiceDissimilarity
/*    */   implements IDistance<int[]>
/*    */ {
/*    */   public double Compute(int[] u, int[] v) {
/* 40 */     return Dissimilarity.Dice(u, v);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Dissimilarities/DiceDissimilarity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */