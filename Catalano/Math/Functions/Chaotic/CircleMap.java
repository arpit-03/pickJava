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
/*    */ public class CircleMap
/*    */   implements IChaoticFunction
/*    */ {
/*    */   private double a;
/*    */   private double b;
/*    */   
/*    */   public CircleMap() {
/* 40 */     this(0.5D, 0.2D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CircleMap(double a, double b) {
/* 49 */     this.a = a;
/* 50 */     this.b = b;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double Generate(double x) {
/* 56 */     double r = x + this.b - this.a / 6.283185307179586D * Math.sin(6.283185307179586D * x);
/* 57 */     return r % 1.0D;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] Generate(double initialState, int iterations) {
/* 63 */     double[] map = new double[iterations];
/* 64 */     map[0] = initialState;
/*    */     
/* 66 */     for (int i = 1; i < iterations; i++) {
/* 67 */       map[i] = Generate(map[i - 1]);
/*    */     }
/*    */     
/* 70 */     return map;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Functions/Chaotic/CircleMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */