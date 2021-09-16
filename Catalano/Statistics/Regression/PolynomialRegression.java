/*     */ package Catalano.Statistics.Regression;
/*     */ 
/*     */ import Catalano.Math.Decompositions.QRDecomposition;
/*     */ import Catalano.Statistics.Correlations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PolynomialRegression
/*     */   implements ISimpleRegression
/*     */ {
/*     */   private double[] x;
/*     */   private double[] y;
/*     */   private double[][] beta;
/*  37 */   private int degree = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDegree() {
/*  44 */     return this.degree;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDegree(int degree) {
/*  52 */     this.degree = Math.max(degree, 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolynomialRegression(double[] x, double[] y) {
/*  61 */     this(x, y, 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolynomialRegression(double[] x, double[] y, int degree) {
/*  71 */     this.x = x;
/*  72 */     this.y = y;
/*  73 */     this.degree = degree;
/*     */ 
/*     */     
/*  76 */     double[][] m = new double[x.length][degree + 1];
/*  77 */     for (int i = 0; i < x.length; i++) {
/*  78 */       for (int j = 0; j <= degree; j++) {
/*  79 */         m[i][j] = Math.pow(x[i], j);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  84 */     double[][] Y = CreateMatrix(y, y.length);
/*     */ 
/*     */     
/*  87 */     QRDecomposition qr = new QRDecomposition(m);
/*  88 */     this.beta = qr.solve(Y);
/*     */   }
/*     */ 
/*     */   
/*     */   private double[][] CreateMatrix(double[] v, int n) {
/*  93 */     int m = v.length;
/*  94 */     n = (m != 0) ? (v.length / m) : 0;
/*  95 */     if (m * n != v.length) {
/*  96 */       throw new IllegalArgumentException("Array length must be a multiple of m.");
/*     */     }
/*  98 */     double[][] A = new double[m][n];
/*  99 */     for (int i = 0; i < m; i++) {
/* 100 */       for (int j = 0; j < n; j++) {
/* 101 */         A[i][j] = v[i + j * m];
/*     */       }
/*     */     } 
/* 104 */     return A;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double Regression(double x) {
/* 110 */     double r = this.beta[0][0];
/* 111 */     double xx = x;
/* 112 */     for (int i = 1; i < this.beta.length; i++) {
/* 113 */       r += this.beta[i][0] * xx;
/* 114 */       xx *= x;
/*     */     } 
/*     */     
/* 117 */     return r;
/*     */   }
/*     */ 
/*     */   
/*     */   public double[] Regression(double[] x) {
/* 122 */     double[] result = new double[x.length];
/* 123 */     for (int i = 0; i < result.length; i++) {
/* 124 */       result[i] = this.beta[0][0];
/* 125 */       double xx = x[i];
/* 126 */       for (int j = 1; j < this.beta.length; j++) {
/* 127 */         result[i] = result[i] + this.beta[j][0] * xx;
/* 128 */         xx *= x[i];
/*     */       } 
/*     */     } 
/* 131 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public double CoefficientOfDetermination() {
/* 136 */     double[] r = Regression(this.x);
/* 137 */     return Math.pow(Correlations.PearsonCorrelation(r, this.y), 2.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 143 */     String equation = "y = ";
/*     */     
/* 145 */     int p = this.beta.length - 1;
/* 146 */     for (int i = 0; i < this.beta.length; i++) {
/* 147 */       if (p != 0) {
/* 148 */         equation = String.valueOf(equation) + String.format("%.4f", new Object[] { Double.valueOf(this.beta[p][0]) }) + "x^" + p + " ";
/*     */       } else {
/* 150 */         equation = String.valueOf(equation) + String.format("%.4f", new Object[] { Double.valueOf(this.beta[p][0]) });
/* 151 */       }  p--;
/*     */     } 
/*     */     
/* 154 */     return equation;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Regression/PolynomialRegression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */