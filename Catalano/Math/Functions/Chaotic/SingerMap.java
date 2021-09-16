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
/*    */ public class SingerMap
/*    */   implements IChaoticFunction
/*    */ {
/*    */   private double u;
/*    */   
/*    */   public SingerMap() {
/* 39 */     this(1.07D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SingerMap(double u) {
/* 47 */     this.u = u;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double Generate(double x) {
/* 53 */     return this.u * (7.86D * x - 23.31D * x * x + 28.75D * x * x * x - 13.302875D * x * x * x * x);
/*    */   }
/*    */ 
/*    */   
/*    */   public double[] Generate(double initialState, int iterations) {
/* 58 */     double[] map = new double[iterations];
/* 59 */     map[0] = initialState;
/*    */     
/* 61 */     for (int i = 1; i < iterations; i++) {
/* 62 */       map[i] = Generate(map[i - 1]);
/*    */     }
/*    */     
/* 65 */     return map;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Functions/Chaotic/SingerMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */