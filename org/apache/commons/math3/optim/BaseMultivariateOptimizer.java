/*     */ package org.apache.commons.math3.optim;
/*     */ 
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BaseMultivariateOptimizer<PAIR>
/*     */   extends BaseOptimizer<PAIR>
/*     */ {
/*     */   private double[] start;
/*     */   private double[] lowerBound;
/*     */   private double[] upperBound;
/*     */   
/*     */   protected BaseMultivariateOptimizer(ConvergenceChecker<PAIR> checker) {
/*  47 */     super(checker);
/*     */   }
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
/*     */   public PAIR optimize(OptimizationData... optData) {
/*  65 */     return super.optimize(optData);
/*     */   }
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
/*     */   protected void parseOptimizationData(OptimizationData... optData) {
/*  81 */     super.parseOptimizationData(optData);
/*     */ 
/*     */ 
/*     */     
/*  85 */     for (OptimizationData data : optData) {
/*  86 */       if (data instanceof InitialGuess) {
/*  87 */         this.start = ((InitialGuess)data).getInitialGuess();
/*     */       
/*     */       }
/*  90 */       else if (data instanceof SimpleBounds) {
/*  91 */         SimpleBounds bounds = (SimpleBounds)data;
/*  92 */         this.lowerBound = bounds.getLower();
/*  93 */         this.upperBound = bounds.getUpper();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  99 */     checkParameters();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getStartPoint() {
/* 108 */     return (this.start == null) ? null : (double[])this.start.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getLowerBound() {
/* 114 */     return (this.lowerBound == null) ? null : (double[])this.lowerBound.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getUpperBound() {
/* 120 */     return (this.upperBound == null) ? null : (double[])this.upperBound.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkParameters() {
/* 127 */     if (this.start != null) {
/* 128 */       int dim = this.start.length;
/* 129 */       if (this.lowerBound != null) {
/* 130 */         if (this.lowerBound.length != dim) {
/* 131 */           throw new DimensionMismatchException(this.lowerBound.length, dim);
/*     */         }
/* 133 */         for (int i = 0; i < dim; i++) {
/* 134 */           double v = this.start[i];
/* 135 */           double lo = this.lowerBound[i];
/* 136 */           if (v < lo) {
/* 137 */             throw new NumberIsTooSmallException(Double.valueOf(v), Double.valueOf(lo), true);
/*     */           }
/*     */         } 
/*     */       } 
/* 141 */       if (this.upperBound != null) {
/* 142 */         if (this.upperBound.length != dim) {
/* 143 */           throw new DimensionMismatchException(this.upperBound.length, dim);
/*     */         }
/* 145 */         for (int i = 0; i < dim; i++) {
/* 146 */           double v = this.start[i];
/* 147 */           double hi = this.upperBound[i];
/* 148 */           if (v > hi)
/* 149 */             throw new NumberIsTooLargeException(Double.valueOf(v), Double.valueOf(hi), true); 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/BaseMultivariateOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */