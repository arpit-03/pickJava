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
/*    */ public class LogisticMap
/*    */   implements IChaoticFunction
/*    */ {
/*    */   private double r;
/*    */   
/*    */   public LogisticMap() {
/* 40 */     this(3.6D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LogisticMap(double r) {
/* 48 */     this.r = r;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Generate(double x) {
/* 53 */     return this.r * x * (1.0D - x);
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


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Functions/Chaotic/LogisticMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */