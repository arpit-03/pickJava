/*     */ package org.apache.commons.math3.linear;
/*     */ 
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.Precision;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class HessenbergTransformer
/*     */ {
/*     */   private final double[][] householderVectors;
/*     */   private final double[] ort;
/*     */   private RealMatrix cachedP;
/*     */   private RealMatrix cachedPt;
/*     */   private RealMatrix cachedH;
/*     */   
/*     */   HessenbergTransformer(RealMatrix matrix) {
/*  60 */     if (!matrix.isSquare()) {
/*  61 */       throw new NonSquareMatrixException(matrix.getRowDimension(), matrix.getColumnDimension());
/*     */     }
/*     */ 
/*     */     
/*  65 */     int m = matrix.getRowDimension();
/*  66 */     this.householderVectors = matrix.getData();
/*  67 */     this.ort = new double[m];
/*  68 */     this.cachedP = null;
/*  69 */     this.cachedPt = null;
/*  70 */     this.cachedH = null;
/*     */ 
/*     */     
/*  73 */     transform();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getP() {
/*  83 */     if (this.cachedP == null) {
/*  84 */       int n = this.householderVectors.length;
/*  85 */       int high = n - 1;
/*  86 */       double[][] pa = new double[n][n];
/*     */       
/*  88 */       for (int i = 0; i < n; i++) {
/*  89 */         for (int j = 0; j < n; j++) {
/*  90 */           pa[i][j] = (i == j) ? 1.0D : 0.0D;
/*     */         }
/*     */       } 
/*     */       
/*  94 */       for (int m = high - 1; m >= 1; m--) {
/*  95 */         if (this.householderVectors[m][m - 1] != 0.0D) {
/*  96 */           for (int k = m + 1; k <= high; k++) {
/*  97 */             this.ort[k] = this.householderVectors[k][m - 1];
/*     */           }
/*     */           
/* 100 */           for (int j = m; j <= high; j++) {
/* 101 */             double g = 0.0D;
/*     */             int i1;
/* 103 */             for (i1 = m; i1 <= high; i1++) {
/* 104 */               g += this.ort[i1] * pa[i1][j];
/*     */             }
/*     */ 
/*     */             
/* 108 */             g = g / this.ort[m] / this.householderVectors[m][m - 1];
/*     */             
/* 110 */             for (i1 = m; i1 <= high; i1++) {
/* 111 */               pa[i1][j] = pa[i1][j] + g * this.ort[i1];
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 117 */       this.cachedP = MatrixUtils.createRealMatrix(pa);
/*     */     } 
/* 119 */     return this.cachedP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getPT() {
/* 129 */     if (this.cachedPt == null) {
/* 130 */       this.cachedPt = getP().transpose();
/*     */     }
/*     */ 
/*     */     
/* 134 */     return this.cachedPt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getH() {
/* 143 */     if (this.cachedH == null) {
/* 144 */       int m = this.householderVectors.length;
/* 145 */       double[][] h = new double[m][m];
/* 146 */       for (int i = 0; i < m; i++) {
/* 147 */         if (i > 0)
/*     */         {
/* 149 */           h[i][i - 1] = this.householderVectors[i][i - 1];
/*     */         }
/*     */ 
/*     */         
/* 153 */         for (int j = i; j < m; j++) {
/* 154 */           h[i][j] = this.householderVectors[i][j];
/*     */         }
/*     */       } 
/* 157 */       this.cachedH = MatrixUtils.createRealMatrix(h);
/*     */     } 
/*     */ 
/*     */     
/* 161 */     return this.cachedH;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   double[][] getHouseholderVectorsRef() {
/* 172 */     return this.householderVectors;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void transform() {
/* 180 */     int n = this.householderVectors.length;
/* 181 */     int high = n - 1;
/*     */     
/* 183 */     for (int m = 1; m <= high - 1; m++) {
/*     */       
/* 185 */       double scale = 0.0D;
/* 186 */       for (int i = m; i <= high; i++) {
/* 187 */         scale += FastMath.abs(this.householderVectors[i][m - 1]);
/*     */       }
/*     */       
/* 190 */       if (!Precision.equals(scale, 0.0D)) {
/*     */         
/* 192 */         double h = 0.0D;
/* 193 */         for (int k = high; k >= m; k--) {
/* 194 */           this.ort[k] = this.householderVectors[k][m - 1] / scale;
/* 195 */           h += this.ort[k] * this.ort[k];
/*     */         } 
/* 197 */         double g = (this.ort[m] > 0.0D) ? -FastMath.sqrt(h) : FastMath.sqrt(h);
/*     */         
/* 199 */         h -= this.ort[m] * g;
/* 200 */         this.ort[m] = this.ort[m] - g;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 205 */         for (int j = m; j < n; j++) {
/* 206 */           double f = 0.0D; int i2;
/* 207 */           for (i2 = high; i2 >= m; i2--) {
/* 208 */             f += this.ort[i2] * this.householderVectors[i2][j];
/*     */           }
/* 210 */           f /= h;
/* 211 */           for (i2 = m; i2 <= high; i2++) {
/* 212 */             this.householderVectors[i2][j] = this.householderVectors[i2][j] - f * this.ort[i2];
/*     */           }
/*     */         } 
/*     */         
/* 216 */         for (int i1 = 0; i1 <= high; i1++) {
/* 217 */           double f = 0.0D; int i2;
/* 218 */           for (i2 = high; i2 >= m; i2--) {
/* 219 */             f += this.ort[i2] * this.householderVectors[i1][i2];
/*     */           }
/* 221 */           f /= h;
/* 222 */           for (i2 = m; i2 <= high; i2++) {
/* 223 */             this.householderVectors[i1][i2] = this.householderVectors[i1][i2] - f * this.ort[i2];
/*     */           }
/*     */         } 
/*     */         
/* 227 */         this.ort[m] = scale * this.ort[m];
/* 228 */         this.householderVectors[m][m - 1] = scale * g;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/HessenbergTransformer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */