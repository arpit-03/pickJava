/*     */ package org.apache.commons.math3.linear;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.function.Sqrt;
/*     */ import org.apache.commons.math3.util.MathArrays;
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
/*     */ public class JacobiPreconditioner
/*     */   extends RealLinearOperator
/*     */ {
/*     */   private final ArrayRealVector diag;
/*     */   
/*     */   public JacobiPreconditioner(double[] diag, boolean deep) {
/*  43 */     this.diag = new ArrayRealVector(diag, deep);
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
/*     */   public static JacobiPreconditioner create(RealLinearOperator a) throws NonSquareOperatorException {
/*  61 */     int n = a.getColumnDimension();
/*  62 */     if (a.getRowDimension() != n) {
/*  63 */       throw new NonSquareOperatorException(a.getRowDimension(), n);
/*     */     }
/*  65 */     double[] diag = new double[n];
/*  66 */     if (a instanceof AbstractRealMatrix) {
/*  67 */       AbstractRealMatrix m = (AbstractRealMatrix)a;
/*  68 */       for (int i = 0; i < n; i++) {
/*  69 */         diag[i] = m.getEntry(i, i);
/*     */       }
/*     */     } else {
/*  72 */       ArrayRealVector x = new ArrayRealVector(n);
/*  73 */       for (int i = 0; i < n; i++) {
/*  74 */         x.set(0.0D);
/*  75 */         x.setEntry(i, 1.0D);
/*  76 */         diag[i] = a.operate(x).getEntry(i);
/*     */       } 
/*     */     } 
/*  79 */     return new JacobiPreconditioner(diag, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnDimension() {
/*  85 */     return this.diag.getDimension();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRowDimension() {
/*  91 */     return this.diag.getDimension();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealVector operate(RealVector x) {
/*  98 */     return new ArrayRealVector(MathArrays.ebeDivide(x.toArray(), this.diag.toArray()), false);
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
/*     */   public RealLinearOperator sqrt() {
/* 112 */     final RealVector sqrtDiag = this.diag.map((UnivariateFunction)new Sqrt());
/* 113 */     return new RealLinearOperator()
/*     */       {
/*     */         public RealVector operate(RealVector x)
/*     */         {
/* 117 */           return new ArrayRealVector(MathArrays.ebeDivide(x.toArray(), sqrtDiag.toArray()), false);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public int getRowDimension() {
/* 125 */           return sqrtDiag.getDimension();
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public int getColumnDimension() {
/* 131 */           return sqrtDiag.getDimension();
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/JacobiPreconditioner.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */