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
/*     */ 
/*     */ 
/*     */ public class RectangularCholeskyDecomposition
/*     */ {
/*     */   private final RealMatrix root;
/*     */   private int rank;
/*     */   
/*     */   public RectangularCholeskyDecomposition(RealMatrix matrix) throws NonPositiveDefiniteMatrixException {
/*  69 */     this(matrix, 0.0D);
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
/*     */   public RectangularCholeskyDecomposition(RealMatrix matrix, double small) throws NonPositiveDefiniteMatrixException {
/*  84 */     int order = matrix.getRowDimension();
/*  85 */     double[][] c = matrix.getData();
/*  86 */     double[][] b = new double[order][order];
/*     */     
/*  88 */     int[] index = new int[order];
/*  89 */     for (int i = 0; i < order; i++) {
/*  90 */       index[i] = i;
/*     */     }
/*     */     
/*  93 */     int r = 0; boolean loop;
/*  94 */     for (loop = true; loop; ) {
/*     */ 
/*     */       
/*  97 */       int swapR = r;
/*  98 */       for (int k = r + 1; k < order; k++) {
/*  99 */         int ii = index[k];
/* 100 */         int isr = index[swapR];
/* 101 */         if (c[ii][ii] > c[isr][isr]) {
/* 102 */           swapR = k;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 108 */       if (swapR != r) {
/* 109 */         int tmpIndex = index[r];
/* 110 */         index[r] = index[swapR];
/* 111 */         index[swapR] = tmpIndex;
/* 112 */         double[] tmpRow = b[r];
/* 113 */         b[r] = b[swapR];
/* 114 */         b[swapR] = tmpRow;
/*     */       } 
/*     */ 
/*     */       
/* 118 */       int ir = index[r];
/* 119 */       if (c[ir][ir] <= small) {
/*     */         
/* 121 */         if (r == 0) {
/* 122 */           throw new NonPositiveDefiniteMatrixException(c[ir][ir], ir, small);
/*     */         }
/*     */ 
/*     */         
/* 126 */         for (int n = r; n < order; n++) {
/* 127 */           if (c[index[n]][index[n]] < -small)
/*     */           {
/*     */             
/* 130 */             throw new NonPositiveDefiniteMatrixException(c[index[n]][index[n]], n, small);
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 136 */         loop = false;
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 141 */       double sqrt = FastMath.sqrt(c[ir][ir]);
/* 142 */       b[r][r] = sqrt;
/* 143 */       double inverse = 1.0D / sqrt;
/* 144 */       double inverse2 = 1.0D / c[ir][ir];
/* 145 */       for (int m = r + 1; m < order; m++) {
/* 146 */         int ii = index[m];
/* 147 */         double e = inverse * c[ii][ir];
/* 148 */         b[m][r] = e;
/* 149 */         c[ii][ii] = c[ii][ii] - c[ii][ir] * c[ii][ir] * inverse2;
/* 150 */         for (int n = r + 1; n < m; n++) {
/* 151 */           int ij = index[n];
/* 152 */           double f = c[ii][ij] - e * b[n][r];
/* 153 */           c[ii][ij] = f;
/* 154 */           c[ij][ii] = f;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 159 */       loop = (++r < order);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 164 */     this.rank = r;
/* 165 */     this.root = MatrixUtils.createRealMatrix(order, r);
/* 166 */     for (int j = 0; j < order; j++) {
/* 167 */       for (int k = 0; k < r; k++) {
/* 168 */         this.root.setEntry(index[j], k, b[j][k]);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getRootMatrix() {
/* 181 */     return this.root;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRank() {
/* 192 */     return this.rank;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/RectangularCholeskyDecomposition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */