/*    */ package Catalano.Math.Functions.Chaotic;
/*    */ 
/*    */ import Catalano.Math.Tools;
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
/*    */ public class IterativeMap
/*    */   implements IChaoticFunction
/*    */ {
/*    */   private double a;
/*    */   
/*    */   public IterativeMap() {
/* 42 */     this(0.7D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IterativeMap(double a) {
/* 50 */     this.a = a;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double Generate(double x) {
/* 56 */     double r = Math.sin(this.a * Math.PI / x);
/* 57 */     return Tools.Scale(-1.0D, 1.0D, 0.0D, 1.0D, r);
/*    */   }
/*    */ 
/*    */   
/*    */   public double[] Generate(double initialState, int iterations) {
/* 62 */     double[] map = new double[iterations];
/* 63 */     map[0] = initialState;
/*    */     
/* 65 */     for (int i = 1; i < iterations; i++) {
/* 66 */       map[i] = Generate(map[i - 1]);
/*    */     }
/*    */     
/* 69 */     return map;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Functions/Chaotic/IterativeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */