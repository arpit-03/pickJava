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
/*    */ public class PiecewiseMap
/*    */   implements IChaoticFunction
/*    */ {
/*    */   private double p;
/*    */   
/*    */   public PiecewiseMap() {
/* 40 */     this(0.4D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PiecewiseMap(double p) {
/* 48 */     this.p = p;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double Generate(double x) {
/* 54 */     if (x >= 0.0D && x < this.p) {
/* 55 */       return x / this.p;
/*    */     }
/* 57 */     if (x >= this.p && x < 0.5D) {
/* 58 */       return (x - this.p) / (0.5D - this.p);
/*    */     }
/* 60 */     if (x >= 0.5D && x < 1.0D - this.p) {
/* 61 */       return (1.0D - this.p - x) / (0.5D - this.p);
/*    */     }
/*    */     
/* 64 */     return (1.0D - x) / this.p;
/*    */   }
/*    */ 
/*    */   
/*    */   public double[] Generate(double initialState, int iterations) {
/* 69 */     double[] map = new double[iterations];
/* 70 */     map[0] = initialState;
/*    */     
/* 72 */     for (int i = 1; i < iterations; i++) {
/* 73 */       map[i] = Generate(map[i - 1]);
/*    */     }
/*    */     
/* 76 */     return map;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Functions/Chaotic/PiecewiseMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */