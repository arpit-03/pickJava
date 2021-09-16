/*     */ package Catalano.Math.Functions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Gaussian
/*     */ {
/*  41 */   private double sigma = 1.0D;
/*  42 */   private double sqrSigma = 1.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Gaussian(double sigma) {
/*  49 */     setSigma(sigma);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSigma() {
/*  57 */     return this.sigma;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSigma(double sigma) {
/*  67 */     this.sigma = Math.max(1.0E-8D, sigma);
/*  68 */     this.sqrSigma = sigma * sigma;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double Function1D(double x) {
/*  77 */     return Math.exp(x * x / -2.0D * this.sqrSigma) / Math.sqrt(6.283185307179586D) * this.sigma;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double Function2D(double x, double y) {
/*  87 */     return Math.exp(-(x * x + y * y) / 2.0D * this.sqrSigma) / 6.283185307179586D * this.sqrSigma;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] Kernel1D(int size) {
/*  96 */     if (size % 2 == 0 || size < 3 || size > 101) {
/*     */       try {
/*  98 */         throw new Exception("Wrong size");
/*  99 */       } catch (Exception e) {
/* 100 */         e.printStackTrace();
/*     */       } 
/*     */     }
/* 103 */     int r = size / 2;
/*     */     
/* 105 */     double[] kernel = new double[size];
/*     */ 
/*     */     
/* 108 */     for (int x = -r, i = 0; i < size; x++, i++)
/*     */     {
/* 110 */       kernel[i] = Function1D(x);
/*     */     }
/*     */     
/* 113 */     return kernel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] Kernel2D(int size) {
/* 122 */     if (size % 2 == 0 || size < 3 || size > 101) {
/*     */       try {
/* 124 */         throw new Exception("Wrong size");
/* 125 */       } catch (Exception e) {
/* 126 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/* 130 */     int r = size / 2;
/* 131 */     double[][] kernel = new double[size][size];
/*     */ 
/*     */     
/* 134 */     double sum = 0.0D;
/* 135 */     for (int y = -r, j = 0; j < size; y++, j++) {
/*     */       
/* 137 */       for (int x = -r, k = 0; k < size; x++, k++) {
/*     */         
/* 139 */         kernel[j][k] = Function2D(x, y);
/* 140 */         sum += kernel[j][k];
/*     */       } 
/*     */     } 
/*     */     
/* 144 */     for (int i = 0; i < kernel.length; i++) {
/* 145 */       for (int k = 0; k < (kernel[0]).length; k++) {
/* 146 */         kernel[i][k] = kernel[i][k] / sum;
/*     */       }
/*     */     } 
/*     */     
/* 150 */     return kernel;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Functions/Gaussian.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */