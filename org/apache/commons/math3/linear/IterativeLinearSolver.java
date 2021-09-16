/*     */ package org.apache.commons.math3.linear;
/*     */ 
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.util.IterationManager;
/*     */ import org.apache.commons.math3.util.MathUtils;
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
/*     */ public abstract class IterativeLinearSolver
/*     */ {
/*     */   private final IterationManager manager;
/*     */   
/*     */   public IterativeLinearSolver(int maxIterations) {
/*  44 */     this.manager = new IterationManager(maxIterations);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IterativeLinearSolver(IterationManager manager) throws NullArgumentException {
/*  55 */     MathUtils.checkNotNull(manager);
/*  56 */     this.manager = manager;
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
/*     */   protected static void checkParameters(RealLinearOperator a, RealVector b, RealVector x0) throws NullArgumentException, NonSquareOperatorException, DimensionMismatchException {
/*  77 */     MathUtils.checkNotNull(a);
/*  78 */     MathUtils.checkNotNull(b);
/*  79 */     MathUtils.checkNotNull(x0);
/*  80 */     if (a.getRowDimension() != a.getColumnDimension()) {
/*  81 */       throw new NonSquareOperatorException(a.getRowDimension(), a.getColumnDimension());
/*     */     }
/*     */     
/*  84 */     if (b.getDimension() != a.getRowDimension()) {
/*  85 */       throw new DimensionMismatchException(b.getDimension(), a.getRowDimension());
/*     */     }
/*     */     
/*  88 */     if (x0.getDimension() != a.getColumnDimension()) {
/*  89 */       throw new DimensionMismatchException(x0.getDimension(), a.getColumnDimension());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IterationManager getIterationManager() {
/* 100 */     return this.manager;
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
/*     */   public RealVector solve(RealLinearOperator a, RealVector b) throws NullArgumentException, NonSquareOperatorException, DimensionMismatchException, MaxCountExceededException {
/* 122 */     MathUtils.checkNotNull(a);
/* 123 */     RealVector x = new ArrayRealVector(a.getColumnDimension());
/* 124 */     x.set(0.0D);
/* 125 */     return solveInPlace(a, b, x);
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
/*     */   public RealVector solve(RealLinearOperator a, RealVector b, RealVector x0) throws NullArgumentException, NonSquareOperatorException, DimensionMismatchException, MaxCountExceededException {
/* 148 */     MathUtils.checkNotNull(x0);
/* 149 */     return solveInPlace(a, b, x0.copy());
/*     */   }
/*     */   
/*     */   public abstract RealVector solveInPlace(RealLinearOperator paramRealLinearOperator, RealVector paramRealVector1, RealVector paramRealVector2) throws NullArgumentException, NonSquareOperatorException, DimensionMismatchException, MaxCountExceededException;
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/IterativeLinearSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */