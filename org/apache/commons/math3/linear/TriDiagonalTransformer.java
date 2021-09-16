/*     */ package org.apache.commons.math3.linear;
/*     */ 
/*     */ import java.util.Arrays;
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
/*     */ class TriDiagonalTransformer
/*     */ {
/*     */   private final double[][] householderVectors;
/*     */   private final double[] main;
/*     */   private final double[] secondary;
/*     */   private RealMatrix cachedQ;
/*     */   private RealMatrix cachedQt;
/*     */   private RealMatrix cachedT;
/*     */   
/*     */   TriDiagonalTransformer(RealMatrix matrix) {
/*  62 */     if (!matrix.isSquare()) {
/*  63 */       throw new NonSquareMatrixException(matrix.getRowDimension(), matrix.getColumnDimension());
/*     */     }
/*     */ 
/*     */     
/*  67 */     int m = matrix.getRowDimension();
/*  68 */     this.householderVectors = matrix.getData();
/*  69 */     this.main = new double[m];
/*  70 */     this.secondary = new double[m - 1];
/*  71 */     this.cachedQ = null;
/*  72 */     this.cachedQt = null;
/*  73 */     this.cachedT = null;
/*     */ 
/*     */     
/*  76 */     transform();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getQ() {
/*  85 */     if (this.cachedQ == null) {
/*  86 */       this.cachedQ = getQT().transpose();
/*     */     }
/*  88 */     return this.cachedQ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getQT() {
/*  97 */     if (this.cachedQt == null) {
/*  98 */       int m = this.householderVectors.length;
/*  99 */       double[][] qta = new double[m][m];
/*     */ 
/*     */       
/* 102 */       for (int k = m - 1; k >= 1; k--) {
/* 103 */         double[] hK = this.householderVectors[k - 1];
/* 104 */         qta[k][k] = 1.0D;
/* 105 */         if (hK[k] != 0.0D) {
/* 106 */           double inv = 1.0D / this.secondary[k - 1] * hK[k];
/* 107 */           double beta = 1.0D / this.secondary[k - 1];
/* 108 */           qta[k][k] = 1.0D + beta * hK[k];
/* 109 */           for (int i = k + 1; i < m; i++) {
/* 110 */             qta[k][i] = beta * hK[i];
/*     */           }
/* 112 */           for (int j = k + 1; j < m; j++) {
/* 113 */             beta = 0.0D; int n;
/* 114 */             for (n = k + 1; n < m; n++) {
/* 115 */               beta += qta[j][n] * hK[n];
/*     */             }
/* 117 */             beta *= inv;
/* 118 */             qta[j][k] = beta * hK[k];
/* 119 */             for (n = k + 1; n < m; n++) {
/* 120 */               qta[j][n] = qta[j][n] + beta * hK[n];
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/* 125 */       qta[0][0] = 1.0D;
/* 126 */       this.cachedQt = MatrixUtils.createRealMatrix(qta);
/*     */     } 
/*     */ 
/*     */     
/* 130 */     return this.cachedQt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getT() {
/* 138 */     if (this.cachedT == null) {
/* 139 */       int m = this.main.length;
/* 140 */       double[][] ta = new double[m][m];
/* 141 */       for (int i = 0; i < m; i++) {
/* 142 */         ta[i][i] = this.main[i];
/* 143 */         if (i > 0) {
/* 144 */           ta[i][i - 1] = this.secondary[i - 1];
/*     */         }
/* 146 */         if (i < this.main.length - 1) {
/* 147 */           ta[i][i + 1] = this.secondary[i];
/*     */         }
/*     */       } 
/* 150 */       this.cachedT = MatrixUtils.createRealMatrix(ta);
/*     */     } 
/*     */ 
/*     */     
/* 154 */     return this.cachedT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   double[][] getHouseholderVectorsRef() {
/* 164 */     return this.householderVectors;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   double[] getMainDiagonalRef() {
/* 174 */     return this.main;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   double[] getSecondaryDiagonalRef() {
/* 184 */     return this.secondary;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void transform() {
/* 192 */     int m = this.householderVectors.length;
/* 193 */     double[] z = new double[m];
/* 194 */     for (int k = 0; k < m - 1; k++) {
/*     */ 
/*     */       
/* 197 */       double[] hK = this.householderVectors[k];
/* 198 */       this.main[k] = hK[k];
/* 199 */       double xNormSqr = 0.0D;
/* 200 */       for (int j = k + 1; j < m; j++) {
/* 201 */         double c = hK[j];
/* 202 */         xNormSqr += c * c;
/*     */       } 
/* 204 */       double a = (hK[k + 1] > 0.0D) ? -FastMath.sqrt(xNormSqr) : FastMath.sqrt(xNormSqr);
/* 205 */       this.secondary[k] = a;
/* 206 */       if (a != 0.0D) {
/*     */ 
/*     */         
/* 209 */         hK[k + 1] = hK[k + 1] - a;
/* 210 */         double beta = -1.0D / a * hK[k + 1];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 216 */         Arrays.fill(z, k + 1, m, 0.0D);
/* 217 */         for (int i = k + 1; i < m; i++) {
/* 218 */           double[] hI = this.householderVectors[i];
/* 219 */           double hKI = hK[i];
/* 220 */           double zI = hI[i] * hKI;
/* 221 */           for (int i1 = i + 1; i1 < m; i1++) {
/* 222 */             double hIJ = hI[i1];
/* 223 */             zI += hIJ * hK[i1];
/* 224 */             z[i1] = z[i1] + hIJ * hKI;
/*     */           } 
/* 226 */           z[i] = beta * (z[i] + zI);
/*     */         } 
/*     */ 
/*     */         
/* 230 */         double gamma = 0.0D; int n;
/* 231 */         for (n = k + 1; n < m; n++) {
/* 232 */           gamma += z[n] * hK[n];
/*     */         }
/* 234 */         gamma *= beta / 2.0D;
/*     */ 
/*     */         
/* 237 */         for (n = k + 1; n < m; n++) {
/* 238 */           z[n] = z[n] - gamma * hK[n];
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 243 */         for (n = k + 1; n < m; n++) {
/* 244 */           double[] hI = this.householderVectors[n];
/* 245 */           for (int i1 = n; i1 < m; i1++) {
/* 246 */             hI[i1] = hI[i1] - hK[n] * z[i1] + z[n] * hK[i1];
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 251 */     this.main[m - 1] = this.householderVectors[m - 1][m - 1];
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/TriDiagonalTransformer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */