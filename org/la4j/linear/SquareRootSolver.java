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
/*     */ public class SquareRootSolver
/*     */   extends AbstractSolver
/*     */   implements LinearSystemSolver
/*     */ {
/*     */   private static final long serialVersionUID = 4071505L;
/*     */   
/*     */   public SquareRootSolver(Matrix a) {
/*  38 */     super(a);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector solve(Vector b) {
/*  43 */     ensureRHSIsCorrect(b);
/*     */     
/*  45 */     Matrix s = this.a.blank();
/*  46 */     Matrix d = this.a.blank();
/*     */     
/*  48 */     Vector x = b.blankOfLength(unknowns());
/*  49 */     Vector y = b.blankOfLength(unknowns());
/*  50 */     Vector z = b.blankOfLength(unknowns());
/*     */     int i;
/*  52 */     for (i = 0; i < this.a.rows(); i++) {
/*     */       
/*  54 */       double dd = 0.0D;
/*  55 */       for (int l = 0; l < i; l++) {
/*  56 */         double sli = s.get(l, i);
/*  57 */         dd += sli * sli * d.get(l, l);
/*     */       } 
/*     */       
/*  60 */       d.set(i, i, Math.signum(this.a.get(i, i) - dd));
/*  61 */       s.set(i, i, Math.sqrt(Math.abs(this.a.get(i, i) - dd)));
/*     */       
/*  63 */       if (s.get(i, i) == 0.0D)
/*     */       {
/*  65 */         fail("This matrix is singular. We can't solve it.");
/*     */       }
/*     */       
/*  68 */       for (int j = i + 1; j < this.a.columns(); j++) {
/*     */         
/*  70 */         double acc = 0.0D;
/*  71 */         for (int m = 0; m < i; m++) {
/*  72 */           double sli = s.get(m, i);
/*  73 */           double slj = s.get(m, j);
/*  74 */           acc += sli * slj * d.get(m, m);
/*     */         } 
/*     */         
/*  77 */         s.set(i, j, (this.a.get(i, j) - acc) / s.get(i, i) * d.get(i, i));
/*     */       } 
/*     */       
/*  80 */       double zz = 0.0D;
/*  81 */       for (int k = 0; k < i; k++) {
/*  82 */         zz += z.get(k) * s.get(k, i);
/*     */       }
/*     */       
/*  85 */       z.set(i, (b.get(i) - zz) / s.get(i, i));
/*  86 */       y.set(i, z.get(i) / d.get(i, i));
/*     */     } 
/*     */     
/*  89 */     for (i = this.a.rows() - 1; i >= 0; i--) {
/*     */       
/*  91 */       double acc = 0.0D;
/*  92 */       for (int l = i + 1; l < this.a.columns(); l++) {
/*  93 */         acc += x.get(l) * s.get(i, l);
/*     */       }
/*     */       
/*  96 */       x.set(i, (y.get(i) - acc) / s.get(i, i));
/*     */     } 
/*     */     
/*  99 */     return x;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean applicableTo(Matrix matrix) {
/* 104 */     return matrix.is(Matrices.SYMMETRIC_MATRIX);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/linear/SquareRootSolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */