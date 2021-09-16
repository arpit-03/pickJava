/*     */ package org.apache.commons.math3.linear;
/*     */ 
/*     */ import org.apache.commons.math3.util.FastMath;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RRQRDecomposition
/*     */   extends QRDecomposition
/*     */ {
/*     */   private int[] p;
/*     */   private RealMatrix cachedP;
/*     */   
/*     */   public RRQRDecomposition(RealMatrix matrix) {
/*  68 */     this(matrix, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RRQRDecomposition(RealMatrix matrix, double threshold) {
/*  79 */     super(matrix, threshold);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decompose(double[][] qrt) {
/*  87 */     this.p = new int[qrt.length];
/*  88 */     for (int i = 0; i < this.p.length; i++) {
/*  89 */       this.p[i] = i;
/*     */     }
/*  91 */     super.decompose(qrt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void performHouseholderReflection(int minor, double[][] qrt) {
/* 101 */     double l2NormSquaredMax = 0.0D;
/*     */     
/* 103 */     int l2NormSquaredMaxIndex = minor;
/* 104 */     for (int i = minor; i < qrt.length; i++) {
/* 105 */       double l2NormSquared = 0.0D;
/* 106 */       for (int j = 0; j < (qrt[i]).length; j++) {
/* 107 */         l2NormSquared += qrt[i][j] * qrt[i][j];
/*     */       }
/* 109 */       if (l2NormSquared > l2NormSquaredMax) {
/* 110 */         l2NormSquaredMax = l2NormSquared;
/* 111 */         l2NormSquaredMaxIndex = i;
/*     */       } 
/*     */     } 
/*     */     
/* 115 */     if (l2NormSquaredMaxIndex != minor) {
/* 116 */       double[] tmp1 = qrt[minor];
/* 117 */       qrt[minor] = qrt[l2NormSquaredMaxIndex];
/* 118 */       qrt[l2NormSquaredMaxIndex] = tmp1;
/* 119 */       int tmp2 = this.p[minor];
/* 120 */       this.p[minor] = this.p[l2NormSquaredMaxIndex];
/* 121 */       this.p[l2NormSquaredMaxIndex] = tmp2;
/*     */     } 
/*     */     
/* 124 */     super.performHouseholderReflection(minor, qrt);
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
/*     */   public RealMatrix getP() {
/* 137 */     if (this.cachedP == null) {
/* 138 */       int n = this.p.length;
/* 139 */       this.cachedP = MatrixUtils.createRealMatrix(n, n);
/* 140 */       for (int i = 0; i < n; i++) {
/* 141 */         this.cachedP.setEntry(this.p[i], i, 1.0D);
/*     */       }
/*     */     } 
/* 144 */     return this.cachedP;
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
/*     */   public int getRank(double dropThreshold) {
/* 167 */     RealMatrix r = getR();
/* 168 */     int rows = r.getRowDimension();
/* 169 */     int columns = r.getColumnDimension();
/* 170 */     int rank = 1;
/* 171 */     double lastNorm = r.getFrobeniusNorm();
/* 172 */     double rNorm = lastNorm;
/* 173 */     while (rank < FastMath.min(rows, columns)) {
/* 174 */       double thisNorm = r.getSubMatrix(rank, rows - 1, rank, columns - 1).getFrobeniusNorm();
/* 175 */       if (thisNorm == 0.0D || thisNorm / lastNorm * rNorm < dropThreshold) {
/*     */         break;
/*     */       }
/* 178 */       lastNorm = thisNorm;
/* 179 */       rank++;
/*     */     } 
/* 181 */     return rank;
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
/*     */   public DecompositionSolver getSolver() {
/* 198 */     return new Solver(super.getSolver(), getP());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Solver
/*     */     implements DecompositionSolver
/*     */   {
/*     */     private final DecompositionSolver upper;
/*     */ 
/*     */ 
/*     */     
/*     */     private RealMatrix p;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Solver(DecompositionSolver upper, RealMatrix p) {
/* 217 */       this.upper = upper;
/* 218 */       this.p = p;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isNonSingular() {
/* 223 */       return this.upper.isNonSingular();
/*     */     }
/*     */ 
/*     */     
/*     */     public RealVector solve(RealVector b) {
/* 228 */       return this.p.operate(this.upper.solve(b));
/*     */     }
/*     */ 
/*     */     
/*     */     public RealMatrix solve(RealMatrix b) {
/* 233 */       return this.p.multiply(this.upper.solve(b));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public RealMatrix getInverse() {
/* 241 */       return solve(MatrixUtils.createRealIdentityMatrix(this.p.getRowDimension()));
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/RRQRDecomposition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */