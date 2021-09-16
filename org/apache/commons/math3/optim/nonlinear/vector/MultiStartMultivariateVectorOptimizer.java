/*     */ package org.apache.commons.math3.optim.nonlinear.vector;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.linear.ArrayRealVector;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ import org.apache.commons.math3.linear.RealVector;
/*     */ import org.apache.commons.math3.optim.BaseMultiStartMultivariateOptimizer;
/*     */ import org.apache.commons.math3.optim.PointVectorValuePair;
/*     */ import org.apache.commons.math3.random.RandomVectorGenerator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class MultiStartMultivariateVectorOptimizer
/*     */   extends BaseMultiStartMultivariateOptimizer<PointVectorValuePair>
/*     */ {
/*     */   private final MultivariateVectorOptimizer optimizer;
/*  47 */   private final List<PointVectorValuePair> optima = new ArrayList<PointVectorValuePair>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MultiStartMultivariateVectorOptimizer(MultivariateVectorOptimizer optimizer, int starts, RandomVectorGenerator generator) throws NullArgumentException, NotStrictlyPositiveException {
/*  66 */     super(optimizer, starts, generator);
/*  67 */     this.optimizer = optimizer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PointVectorValuePair[] getOptima() {
/*  75 */     Collections.sort(this.optima, getPairComparator());
/*  76 */     return this.optima.<PointVectorValuePair>toArray(new PointVectorValuePair[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void store(PointVectorValuePair optimum) {
/*  84 */     this.optima.add(optimum);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void clear() {
/*  92 */     this.optima.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Comparator<PointVectorValuePair> getPairComparator() {
/*  99 */     return new Comparator<PointVectorValuePair>()
/*     */       {
/* 101 */         private final RealVector target = (RealVector)new ArrayRealVector(MultiStartMultivariateVectorOptimizer.this.optimizer.getTarget(), false);
/*     */         
/* 103 */         private final RealMatrix weight = MultiStartMultivariateVectorOptimizer.this.optimizer.getWeight();
/*     */ 
/*     */ 
/*     */         
/*     */         public int compare(PointVectorValuePair o1, PointVectorValuePair o2) {
/* 108 */           if (o1 == null)
/* 109 */             return (o2 == null) ? 0 : 1; 
/* 110 */           if (o2 == null) {
/* 111 */             return -1;
/*     */           }
/* 113 */           return Double.compare(weightedResidual(o1), weightedResidual(o2));
/*     */         }
/*     */ 
/*     */         
/*     */         private double weightedResidual(PointVectorValuePair pv) {
/* 118 */           ArrayRealVector arrayRealVector = new ArrayRealVector(pv.getValueRef(), false);
/* 119 */           RealVector r = this.target.subtract((RealVector)arrayRealVector);
/* 120 */           return r.dotProduct(this.weight.operate(r));
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/nonlinear/vector/MultiStartMultivariateVectorOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */