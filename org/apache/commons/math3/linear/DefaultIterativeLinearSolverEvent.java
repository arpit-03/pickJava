/*     */ package org.apache.commons.math3.linear;
/*     */ 
/*     */ import org.apache.commons.math3.exception.MathUnsupportedOperationException;
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
/*     */ public class DefaultIterativeLinearSolverEvent
/*     */   extends IterativeLinearSolverEvent
/*     */ {
/*     */   private static final long serialVersionUID = 20120129L;
/*     */   private final RealVector b;
/*     */   private final RealVector r;
/*     */   private final double rnorm;
/*     */   private final RealVector x;
/*     */   
/*     */   public DefaultIterativeLinearSolverEvent(Object source, int iterations, RealVector x, RealVector b, RealVector r, double rnorm) {
/*  65 */     super(source, iterations);
/*  66 */     this.x = x;
/*  67 */     this.b = b;
/*  68 */     this.r = r;
/*  69 */     this.rnorm = rnorm;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultIterativeLinearSolverEvent(Object source, int iterations, RealVector x, RealVector b, double rnorm) {
/*  92 */     super(source, iterations);
/*  93 */     this.x = x;
/*  94 */     this.b = b;
/*  95 */     this.r = null;
/*  96 */     this.rnorm = rnorm;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNormOfResidual() {
/* 102 */     return this.rnorm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealVector getResidual() {
/* 113 */     if (this.r != null) {
/* 114 */       return this.r;
/*     */     }
/* 116 */     throw new MathUnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RealVector getRightHandSideVector() {
/* 122 */     return this.b;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RealVector getSolution() {
/* 128 */     return this.x;
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
/*     */   public boolean providesResidual() {
/* 141 */     return (this.r != null);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/DefaultIterativeLinearSolverEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */