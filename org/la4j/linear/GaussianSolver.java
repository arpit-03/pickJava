/*     */ package org.la4j.linear;
/*     */ 
/*     */ import org.la4j.Matrices;
/*     */ import org.la4j.Matrix;
/*     */ import org.la4j.Vector;
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
/*     */ public class GaussianSolver
/*     */   extends AbstractSolver
/*     */   implements LinearSystemSolver
/*     */ {
/*     */   private static final long serialVersionUID = 4071505L;
/*     */   private final Matrix aa;
/*     */   
/*     */   public GaussianSolver(Matrix a) {
/*  42 */     super(a);
/*     */     
/*  44 */     this.aa = a.copyOfColumns(unknowns() + 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector solve(Vector b) {
/*  49 */     ensureRHSIsCorrect(b);
/*     */ 
/*     */     
/*  52 */     this.aa.setColumn(unknowns(), b);
/*     */ 
/*     */     
/*  55 */     triangularizeWithPivoting(this.aa);
/*     */     
/*  57 */     if (Math.abs(this.aa.diagonalProduct()) < Matrices.EPS) {
/*  58 */       fail("This system is singular.");
/*     */     }
/*     */ 
/*     */     
/*  62 */     Vector x = b.blankOfLength(this.aa.columns() - 1);
/*  63 */     backSubstitution(this.aa, x);
/*     */     
/*  65 */     return x;
/*     */   }
/*     */ 
/*     */   
/*     */   private void triangularizeWithPivoting(Matrix matrix) {
/*  70 */     for (int i = 0; i + 1 < matrix.rows(); i++) {
/*     */       
/*  72 */       int maxIndex = i;
/*  73 */       double maxItem = Math.abs(matrix.get(i, i));
/*     */       
/*  75 */       for (int k = i + 1; k < matrix.rows(); k++) {
/*  76 */         double value = Math.abs(matrix.get(k, i));
/*  77 */         if (value > maxItem) {
/*  78 */           maxItem = value;
/*  79 */           maxIndex = k;
/*     */         } 
/*     */       } 
/*     */       
/*  83 */       if (maxItem == 0.0D) {
/*  84 */         throw new IllegalArgumentException("This system can't be solved.");
/*     */       }
/*     */       
/*  87 */       if (maxIndex > i) {
/*  88 */         matrix.swapRows(maxIndex, i);
/*     */       }
/*     */       
/*  91 */       for (int j = i + 1; j < matrix.rows(); j++) {
/*     */         
/*  93 */         double c = matrix.get(j, i) / matrix.get(i, i);
/*  94 */         matrix.set(j, i, 0.0D);
/*     */         
/*  96 */         for (int m = i + 1; m < matrix.columns(); m++) {
/*  97 */           matrix.updateAt(j, m, Matrices.asMinusFunction(matrix.get(i, m) * c));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void backSubstitution(Matrix matrix, Vector result) {
/* 105 */     for (int i = matrix.rows() - 1; i >= 0; i--) {
/*     */       
/* 107 */       double acc = 0.0D;
/* 108 */       for (int j = i + 1; j < matrix.columns() - 1; j++) {
/* 109 */         acc += result.get(j) * matrix.get(i, j);
/*     */       }
/*     */       
/* 112 */       result.set(i, (matrix.get(i, matrix.columns() - 1) - acc) / matrix.get(i, i));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean applicableTo(Matrix matrix) {
/* 119 */     return (matrix.rows() == matrix.columns());
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/linear/GaussianSolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */