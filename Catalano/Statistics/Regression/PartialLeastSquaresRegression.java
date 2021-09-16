/*     */ package Catalano.Statistics.Regression;
/*     */ 
/*     */ import Catalano.Math.Matrix;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PartialLeastSquaresRegression
/*     */ {
/*     */   private double tolerance;
/*     */   private int factors;
/*     */   private double[][] P;
/*     */   private double[][] Q;
/*     */   private double[][] T;
/*     */   private double[][] U;
/*     */   private double[][] W;
/*     */   private double[][] coeff;
/*     */   private double[] B;
/*     */   
/*     */   public double[][] getScoreMatrixX() {
/*  49 */     return this.T;
/*  50 */   } public double[][] getScoreMatrixY() { return this.U; }
/*  51 */   public double[][] getLoadingMatrixX() { return this.P; }
/*  52 */   public double[][] getLoadingMatrixY() { return this.Q; }
/*  53 */   public double[][] getW() { return this.W; }
/*  54 */   public double[] getB() { return this.B; } public double[][] getCoefficients() {
/*  55 */     return this.coeff;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getTolerance() {
/*  62 */     return this.tolerance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTolerance(double tolerance) {
/*  70 */     this.tolerance = tolerance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PartialLeastSquaresRegression(int factors) {
/*  78 */     this(factors, 1.0E-14D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PartialLeastSquaresRegression(int factors, double tolerance) {
/*  87 */     this.tolerance = tolerance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Compute(double[][] X, double[][] Y) {
/*  97 */     int rows = X.length;
/*  98 */     int xcols = (X[0]).length;
/*  99 */     int ycols = (Y[0]).length;
/*     */     
/* 101 */     double[][] E = Matrix.Copy(X);
/* 102 */     double[][] F = Matrix.Copy(Y);
/*     */     
/* 104 */     this.T = new double[rows][this.factors];
/* 105 */     this.U = new double[rows][this.factors];
/* 106 */     this.P = new double[xcols][this.factors];
/* 107 */     this.Q = new double[ycols][this.factors];
/* 108 */     this.W = new double[xcols][this.factors];
/* 109 */     this.B = new double[this.factors];
/*     */     
/* 111 */     double[] varX = new double[this.factors];
/* 112 */     double[] varY = new double[this.factors];
/*     */ 
/*     */     
/* 115 */     for (int factor = 0; factor < this.factors; factor++) {
/*     */ 
/*     */       
/* 118 */       double[] t = Matrix.getColumn(E, largest(E));
/*     */ 
/*     */       
/* 121 */       double[] u = Matrix.getColumn(F, largest(F));
/*     */ 
/*     */       
/* 124 */       double[] w = new double[xcols];
/* 125 */       double[] c = new double[ycols];
/*     */       
/* 127 */       double norm_t = Euclidean(t);
/*     */ 
/*     */       
/* 130 */       while (norm_t > this.tolerance) {
/*     */ 
/*     */         
/* 133 */         double[] t0 = Arrays.copyOf(t, t.length);
/*     */ 
/*     */ 
/*     */         
/* 137 */         w = new double[xcols];
/* 138 */         for (int i1 = 0; i1 < w.length; i1++) {
/* 139 */           for (int i6 = 0; i6 < u.length; i6++) {
/* 140 */             w[i1] = w[i1] + E[i6][i1] * u[i6];
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 145 */         double Ew = Euclidean(w); int i2;
/* 146 */         for (i2 = 0; i2 < w.length; i2++) {
/* 147 */           w[i2] = w[i2] / Ew;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 152 */         t = new double[rows];
/* 153 */         for (i2 = 0; i2 < t.length; i2++) {
/* 154 */           for (int i6 = 0; i6 < w.length; i6++) {
/* 155 */             t[i2] = t[i2] + E[i2][i6] * w[i6];
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 160 */         double Et = Euclidean(t);
/* 161 */         for (int i4 = 0; i4 < t.length; i4++) {
/* 162 */           t[i4] = t[i4] / Et;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 167 */         c = new double[ycols];
/* 168 */         for (int i3 = 0; i3 < c.length; i3++) {
/* 169 */           for (int i6 = 0; i6 < t.length; i6++) {
/* 170 */             c[i3] = c[i3] + F[i6][i3] * t[i6];
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 175 */         double Ec = Euclidean(c); int i5;
/* 176 */         for (i5 = 0; i5 < c.length; ) { c[i5] = c[i5] / Ec; i5++; }
/*     */ 
/*     */ 
/*     */         
/* 180 */         u = new double[rows];
/* 181 */         for (i5 = 0; i5 < u.length; i5++) {
/* 182 */           for (int i6 = 0; i6 < c.length; i6++) {
/* 183 */             u[i5] = u[i5] + F[i5][i6] * c[i6];
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 188 */         norm_t = 0.0D;
/* 189 */         for (i5 = 0; i5 < t.length; i5++) {
/* 190 */           double d = t0[i5] - t[i5];
/* 191 */           norm_t += d * d;
/*     */         } 
/* 193 */         norm_t = Math.sqrt(norm_t);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 198 */       double b = 0.0D;
/* 199 */       for (int k = 0; k < t.length; k++) {
/* 200 */         b += t[k] * u[k];
/*     */       }
/*     */ 
/*     */       
/* 204 */       double[] p = new double[xcols];
/* 205 */       for (int j = 0; j < p.length; j++) {
/* 206 */         for (int i1 = 0; i1 < rows; i1++) {
/* 207 */           p[j] = p[j] + E[i1][j] * t[i1];
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 212 */       for (int m = 0; m < t.length; m++) {
/*     */         int i1;
/* 214 */         for (i1 = 0; i1 < p.length; i1++) {
/* 215 */           E[m][i1] = E[m][i1] - t[m] * p[i1];
/*     */         }
/*     */         
/* 218 */         for (i1 = 0; i1 < c.length; i1++) {
/* 219 */           F[m][i1] = F[m][i1] - b * t[m] * c[i1];
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 224 */       varY[factor] = b * b;
/* 225 */       double d1 = 0.0D; int n;
/* 226 */       for (n = 0; n < p.length; n++) {
/* 227 */         d1 += p[n] * p[n];
/*     */       }
/* 229 */       varX[factor] = d1;
/*     */ 
/*     */       
/* 232 */       for (n = 0; n < t.length; ) { this.T[n][factor] = t[n]; n++; }
/* 233 */        for (n = 0; n < p.length; ) { this.P[n][factor] = p[n]; n++; }
/* 234 */        for (n = 0; n < u.length; ) { this.U[n][factor] = u[n]; n++; }
/* 235 */        for (n = 0; n < c.length; ) { this.Q[n][factor] = c[n]; n++; }
/* 236 */        for (n = 0; n < w.length; ) { this.W[n][factor] = w[n]; n++; }
/* 237 */        this.B[factor] = b;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 242 */     double[][] temp = new double[this.B.length][this.B.length];
/* 243 */     for (int i = 0; i < this.B.length; i++) {
/* 244 */       temp[i][i] = this.B[i];
/*     */     }
/* 246 */     double[][] mat = Matrix.PseudoInverse(Matrix.Transpose(this.P));
/* 247 */     mat = Matrix.Multiply(mat, temp);
/* 248 */     mat = Matrix.Multiply(mat, Matrix.Transpose(this.Q));
/*     */     
/* 250 */     this.coeff = mat;
/*     */   }
/*     */   
/*     */   private double Euclidean(double[] v) {
/* 254 */     double r = 0.0D;
/* 255 */     for (int i = 0; i < v.length; i++) {
/* 256 */       r += Math.pow(v[i], 2.0D);
/*     */     }
/* 258 */     return Math.sqrt(r);
/*     */   }
/*     */   
/*     */   private int largest(double[][] matrix) {
/* 262 */     int index = 0;
/* 263 */     double max = 0.0D;
/* 264 */     for (int i = 0; i < (matrix[0]).length; i++) {
/* 265 */       double sum = 0.0D;
/* 266 */       for (int j = 0; j < matrix.length; j++) {
/* 267 */         sum += matrix[j][i] * matrix[j][i];
/*     */       }
/* 269 */       if (sum > max) {
/* 270 */         max = sum;
/* 271 */         index = i;
/*     */       } 
/*     */     } 
/* 274 */     return index;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Regression/PartialLeastSquaresRegression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */