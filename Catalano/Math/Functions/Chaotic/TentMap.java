/*    */ package Catalano.Math.Functions.Chaotic;
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
/*    */ public class TentMap
/*    */   implements IChaoticFunction
/*    */ {
/*    */   private double u;
/*    */   private double threshold;
/*    */   
/*    */   public TentMap() {
/* 41 */     this(1.5D, 0.5D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TentMap(double u, double threshold) {
/* 50 */     this.u = u;
/* 51 */     this.threshold = threshold;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double Generate(double x) {
/* 57 */     if (x < this.threshold) {
/* 58 */       return this.u * x;
/*    */     }
/* 60 */     return this.u * (1.0D - x);
/*    */   }
/*    */ 
/*    */   
/*    */   public double[] Generate(double initialState, int iterations) {
/* 65 */     double[] map = new double[iterations];
/* 66 */     map[0] = initialState;
/*    */     
/* 68 */     for (int i = 1; i < iterations; i++) {
/* 69 */       map[i] = Generate(map[i - 1]);
/*    */     }
/*    */     
/* 72 */     return map;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Functions/Chaotic/TentMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */