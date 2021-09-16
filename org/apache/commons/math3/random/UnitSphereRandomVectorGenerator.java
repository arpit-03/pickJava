/*    */ package org.apache.commons.math3.random;
/*    */ 
/*    */ import org.apache.commons.math3.util.FastMath;
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
/*    */ public class UnitSphereRandomVectorGenerator
/*    */   implements RandomVectorGenerator
/*    */ {
/*    */   private final RandomGenerator rand;
/*    */   private final int dimension;
/*    */   
/*    */   public UnitSphereRandomVectorGenerator(int dimension, RandomGenerator rand) {
/* 46 */     this.dimension = dimension;
/* 47 */     this.rand = rand;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public UnitSphereRandomVectorGenerator(int dimension) {
/* 56 */     this(dimension, new MersenneTwister());
/*    */   }
/*    */ 
/*    */   
/*    */   public double[] nextVector() {
/* 61 */     double[] v = new double[this.dimension];
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 66 */     double normSq = 0.0D;
/* 67 */     for (int i = 0; i < this.dimension; i++) {
/* 68 */       double comp = this.rand.nextGaussian();
/* 69 */       v[i] = comp;
/* 70 */       normSq += comp * comp;
/*    */     } 
/*    */     
/* 73 */     double f = 1.0D / FastMath.sqrt(normSq);
/* 74 */     for (int j = 0; j < this.dimension; j++) {
/* 75 */       v[j] = v[j] * f;
/*    */     }
/*    */     
/* 78 */     return v;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/random/UnitSphereRandomVectorGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */