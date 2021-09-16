/*     */ package com.boreholeseismic.dsp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Filter
/*     */ {
/*     */   private double[] output;
/*     */   double[] initFilter;
/*     */   
/*     */   public Filter(double[] b, double[] a, double[] x, double[] z) {
/*  28 */     double Npts = x.length;
/*  29 */     this.initFilter = z;
/*  30 */     double L = 1.0D;
/*  31 */     double nb = b.length;
/*  32 */     double na = a.length;
/*  33 */     double nfilt = Math.max(nb, na);
/*  34 */     double nfact = 3.0D * (nfilt - 1.0D);
/*     */ 
/*     */     
/*  37 */     this.output = ffOneChan(b, a, x, nfact, L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double[] ffOneChan(double[] b, double[] a, double[] xc, double nfact, double L) {
/*  44 */     double[] xt = scalarAdd(scalarMultiply(matrixSubPart(xc, nfact, 1.0D), -1.0D), 2.0D * xc[0]);
/*     */     
/*  46 */     filterObject one = FilterResults(b, a, xt, scalarMultiply(this.initFilter, xt[0]));
/*  47 */     filterObject two = FilterResults(b, a, xc, one.zOut);
/*  48 */     double[] yc2 = two.yOut;
/*  49 */     xt = scalarAdd(scalarMultiply(matrixSubPart(xc, (xc.length - 1), xc.length - nfact), -1.0D), 2.0D * xc[xc.length - 1]);
/*  50 */     filterObject three = FilterResults(b, a, xt, two.zOut);
/*  51 */     double[] yc3 = three.yOut;
/*     */     
/*  53 */     filterObject four = FilterResults(b, a, reverseMatrix(yc3), scalarMultiply(this.initFilter, yc3[yc3.length - 1]));
/*  54 */     filterObject five = FilterResults(b, a, reverseMatrix(yc2), four.zOut);
/*     */     
/*  56 */     xc = reverseMatrix(five.yOut);
/*     */     
/*  58 */     return xc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private filterObject FilterResults(double[] b, double[] a, double[] x, double[] zIn) {
/*  65 */     int n = a.length;
/*     */     
/*  67 */     double[] z = new double[n];
/*     */     
/*  69 */     for (int i = 0; i < zIn.length; i++) {
/*  70 */       z[i] = zIn[i];
/*     */     }
/*  72 */     double divisor = a[0];
/*     */     
/*  74 */     for (int k = 0; k < b.length; k++) {
/*     */       
/*  76 */       b[k] = b[k] / divisor;
/*  77 */       a[k] = a[k] / divisor;
/*     */     } 
/*     */     
/*  80 */     double[] yOut = new double[x.length];
/*     */     
/*  82 */     for (int j = 0; j < x.length; j++) {
/*     */       
/*  84 */       yOut[j] = b[0] * x[j] + z[0];
/*     */       
/*  86 */       for (int m = 1; m < n; m++) {
/*  87 */         z[m - 1] = b[m] * x[j] + z[m] - a[m] * yOut[j];
/*     */       }
/*     */     } 
/*     */     
/*  91 */     double[] zOut = new double[z.length - 1];
/*     */     
/*  93 */     for (int l = 0; l < zOut.length; l++) {
/*  94 */       zOut[l] = z[l];
/*     */     }
/*     */     
/*  97 */     return new filterObject(yOut, zOut);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double[] reverseMatrix(double[] filt2) {
/* 104 */     double[] output = new double[filt2.length];
/*     */     
/* 106 */     for (int i = 0; i < filt2.length; i++) {
/* 107 */       output[i] = filt2[filt2.length - i - 1];
/*     */     }
/* 109 */     return output;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double[][] matrixSub(double[][] matrix1, double[][] matrix2) {
/* 142 */     double[][] output = new double[matrix1.length][(matrix1[0]).length];
/*     */     
/* 144 */     for (int i = 0; i < matrix1.length; i++) {
/* 145 */       for (int j = 0; j < (matrix1[0]).length; j++)
/* 146 */         output[i][j] = matrix1[i][j] - matrix2[i][j]; 
/*     */     } 
/* 148 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private double[][] horCat(double[] smallerMatrix, double[][] bigMatrix) {
/* 154 */     double[][] output = new double[smallerMatrix.length][(bigMatrix[0]).length + 1];
/*     */     
/* 156 */     for (int i = 0; i < output.length; i++) {
/* 157 */       for (int j = 0; j < (output[0]).length; j++) {
/*     */         
/* 159 */         if (j == 0) {
/* 160 */           output[i][j] = smallerMatrix[i];
/*     */         } else {
/* 162 */           output[i][j] = bigMatrix[i][j - 1];
/*     */         } 
/*     */       } 
/*     */     } 
/* 166 */     return output;
/*     */   }
/*     */ 
/*     */   
/*     */   private double[][] vertCat(double[][] Matrix1, double[][] Matrix2) {
/* 171 */     double[][] output = new double[Matrix1.length + Matrix2.length][(Matrix1[0]).length];
/*     */     int i;
/* 173 */     for (i = 0; i < Matrix1.length; i++) {
/* 174 */       for (int j = 0; j < Matrix1.length; j++)
/* 175 */         output[i][j] = Matrix1[i][j]; 
/*     */     } 
/* 177 */     for (i = 0; i < Matrix2.length; i++) {
/* 178 */       for (int j = 0; j < Matrix2.length; j++)
/* 179 */         output[i + Matrix1.length][j] = Matrix2[i][j]; 
/*     */     } 
/* 181 */     return output;
/*     */   }
/*     */ 
/*     */   
/*     */   private double[][] zeroMatrix(double i, double d) {
/* 186 */     double[][] output = new double[(int)i][(int)d];
/*     */     
/* 188 */     return output;
/*     */   }
/*     */ 
/*     */   
/*     */   private double[] matrixAdd(double[] matrix1, double[] matrix2) {
/* 193 */     double[] output = new double[matrix1.length];
/*     */     
/* 195 */     for (int i = 0; i < matrix1.length; i++) {
/* 196 */       output[i] = matrix1[i] + matrix2[i];
/*     */     }
/* 198 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private double[] scalarMultiply(double[] inputArray, double factor) {
/* 204 */     double[] outputArray = new double[inputArray.length];
/*     */     
/* 206 */     for (int j = 0; j < inputArray.length; j++) {
/* 207 */       outputArray[j] = inputArray[j] * factor;
/*     */     }
/* 209 */     return outputArray;
/*     */   }
/*     */ 
/*     */   
/*     */   private double[] scalarAdd(double[] inputArray, double factor) {
/* 214 */     for (int j = 0; j < inputArray.length; j++) {
/* 215 */       inputArray[j] = inputArray[j] + factor;
/*     */     }
/* 217 */     return inputArray;
/*     */   }
/*     */ 
/*     */   
/*     */   private double[] matrixSubPart(double[] a, double start, double end) {
/* 222 */     double[] output = new double[(int)(Math.abs(start - end) + 1.0D)];
/*     */     
/* 224 */     if (end > start) {
/* 225 */       for (int i = (int)start; i <= (int)end; i++)
/* 226 */         output[i - (int)start] = a[i]; 
/*     */     } else {
/* 228 */       for (int i = (int)start; i >= (int)end; i--)
/* 229 */         output[(int)start - i] = a[i]; 
/*     */     } 
/* 231 */     return output;
/*     */   }
/*     */   
/*     */   public static double[] FilterResults(double[] b, double[] a, double[] x) {
/* 235 */     double[] filter = null;
/* 236 */     double[] a1 = getRealArrayScalarDiv(a, a[0]);
/* 237 */     double[] b1 = getRealArrayScalarDiv(b, a[0]);
/* 238 */     int sx = x.length;
/* 239 */     filter = new double[sx];
/* 240 */     filter[0] = b1[0] * x[0];
/* 241 */     for (int i = 1; i < sx; i++) {
/* 242 */       filter[i] = 0.0D;
/* 243 */       for (int j = 0; j <= i; j++) {
/* 244 */         int k = i - j;
/* 245 */         if (j > 0) {
/* 246 */           if (k < b1.length && j < x.length) {
/* 247 */             filter[i] = filter[i] + b1[k] * x[j];
/*     */           }
/* 249 */           if (k < filter.length && j < a1.length) {
/* 250 */             filter[i] = filter[i] - a1[j] * filter[k];
/*     */           }
/*     */         }
/* 253 */         else if (k < b1.length && j < x.length) {
/* 254 */           filter[i] = filter[i] + b1[k] * x[j];
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 259 */     return filter;
/*     */   }
/*     */ 
/*     */   
/*     */   public static double[] getRealArrayScalarDiv(double[] dDividend, double dDivisor) {
/* 264 */     if (dDividend == null) {
/* 265 */       throw new IllegalArgumentException("The array must be defined or diferent to null");
/*     */     }
/* 267 */     if (dDividend.length == 0) {
/* 268 */       throw new IllegalArgumentException("The size array must be greater than Zero");
/*     */     }
/* 270 */     double[] dQuotient = new double[dDividend.length];
/*     */     
/* 272 */     for (int i = 0; i < dDividend.length; i++) {
/* 273 */       if (dDivisor != 0.0D) {
/* 274 */         dQuotient[i] = dDividend[i] / dDivisor;
/*     */       } else {
/* 276 */         if (dDividend[i] > 0.0D) {
/* 277 */           dQuotient[i] = Double.POSITIVE_INFINITY;
/*     */         }
/* 279 */         if (dDividend[i] == 0.0D) {
/* 280 */           dQuotient[i] = Double.NaN;
/*     */         }
/* 282 */         if (dDividend[i] < 0.0D) {
/* 283 */           dQuotient[i] = Double.NEGATIVE_INFINITY;
/*     */         }
/*     */       } 
/*     */     } 
/* 287 */     return dQuotient;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getFilterOutput() {
/* 293 */     return this.output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] createArray(double start, double interval, double end) {
/*     */     double[] output;
/* 301 */     if (end > start) {
/*     */       
/* 303 */       output = new double[(int)(end - start + 1.0D)];
/* 304 */       for (int i = (int)start; i < end; i++) {
/* 305 */         output[(int)(i - start)] = i;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 310 */       output = new double[(int)(start - end + 1.0D)];
/* 311 */       for (int i = (int)end; i < start; i--) {
/* 312 */         output[(int)(i - end)] = i;
/*     */       }
/*     */     } 
/* 315 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] createOnesArray(double start, double end) {
/* 322 */     double[] output = new double[(int)(end - start + 1.0D)];
/*     */     
/* 324 */     for (int i = 0; i < output.length; i++) {
/* 325 */       output[i] = 1.0D;
/*     */     }
/* 327 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] eye(int matrixSize) {
/* 333 */     double[][] output = new double[matrixSize][matrixSize];
/*     */     
/* 335 */     for (int i = 0; i < output.length; i++) {
/* 336 */       output[i][i] = 1.0D;
/*     */     }
/* 338 */     return output;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/dsp/Filter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */