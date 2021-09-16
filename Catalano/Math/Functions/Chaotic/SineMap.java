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
/*    */ public class SineMap
/*    */   implements IChaoticFunction
/*    */ {
/*    */   private double u;
/*    */   
/*    */   public SineMap() {
/* 40 */     this(1.0D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SineMap(double u) {
/* 48 */     this.u = u;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double Generate(double x) {
/* 54 */     return this.u * Math.sin(Math.PI * x);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] Generate(double initialState, int iterations) {
/* 60 */     double[] map = new double[iterations];
/* 61 */     map[0] = initialState;
/*    */     
/* 63 */     for (int i = 1; i < iterations; i++) {
/* 64 */       map[i] = Generate(map[i - 1]);
/*    */     }
/*    */     
/* 67 */     return map;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Functions/Chaotic/SineMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */