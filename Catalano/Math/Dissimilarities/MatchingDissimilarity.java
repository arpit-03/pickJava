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
/*    */ public class MatchingDissimilarity
/*    */   implements IDissimilarity<int[]>
/*    */ {
/*    */   public double Compute(int[] u, int[] v) {
/* 38 */     return Dissimilarity.Matching(u, v);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Dissimilarities/MatchingDissimilarity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */