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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SinusoidalMap
/*    */   implements IChaoticFunction
/*    */ {
/*    */   public double Generate(double x) {
/* 42 */     return 2.3D * x * x * Math.sin(Math.PI * x);
/*    */   }
/*    */ 
/*    */   
/*    */   public double[] Generate(double initialState, int iterations) {
/* 47 */     double[] map = new double[iterations];
/* 48 */     map[0] = initialState;
/*    */     
/* 50 */     for (int i = 1; i < iterations; i++) {
/* 51 */       map[i] = Generate(map[i - 1]);
/*    */     }
/*    */     
/* 54 */     return map;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Functions/Chaotic/SinusoidalMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */