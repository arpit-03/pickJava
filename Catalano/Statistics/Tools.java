/*     */ package Catalano.Statistics;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Tools
/*     */ {
/*     */   public static double AlphaTrimmedMean(double[] values, float alpha) {
/*  43 */     int lower = (int)(values.length * alpha);
/*  44 */     int upper = values.length - lower;
/*     */     
/*  46 */     double sum = 0.0D;
/*  47 */     for (int i = lower; i < upper; i++) {
/*  48 */       sum += values[i];
/*     */     }
/*     */     
/*  51 */     return sum / (upper - lower);
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
/*     */   public static double AlphaTrimmedMean(double[] values, int n) {
/*  63 */     int upper = values.length - n;
/*     */     
/*  65 */     double sum = 0.0D;
/*  66 */     for (int i = n; i < upper; i++) {
/*  67 */       sum += values[i];
/*     */     }
/*  69 */     return sum / (upper - n);
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
/*     */   public static double AlphaTrimmedMean(int[] values, float alpha) {
/*  81 */     int lower = (int)(values.length * alpha);
/*  82 */     int upper = values.length - lower;
/*     */     
/*  84 */     double sum = 0.0D;
/*  85 */     for (int i = lower; i < upper; i++) {
/*  86 */       sum += values[i];
/*     */     }
/*     */     
/*  89 */     return sum / (upper - lower);
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
/*     */   public static double AlphaTrimmedMean(int[] values, int n) {
/* 101 */     int upper = values.length - n;
/*     */     
/* 103 */     double sum = 0.0D;
/* 104 */     for (int i = n; i < upper; i++) {
/* 105 */       sum += values[i];
/*     */     }
/* 107 */     return sum / (upper - n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double CoefficientOfVariation(double[] x) {
/* 117 */     double mean = Mean(x);
/* 118 */     double std = Math.sqrt(Variance(x, mean));
/* 119 */     return std / mean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[][] Correlation(double[][] data) {
/* 128 */     double[][] co = new double[(data[0]).length][(data[0]).length];
/* 129 */     for (int i = 0; i < co.length; i++) {
/* 130 */       for (int j = 0; j < (co[0]).length; j++) {
/* 131 */         if (i == j) {
/* 132 */           co[i][j] = 1.0D;
/*     */         } else {
/*     */           
/* 135 */           double[] colX = Matrix.getColumn(data, i);
/* 136 */           double[] colY = Matrix.getColumn(data, j);
/* 137 */           co[i][j] = Correlations.PearsonCorrelation(colX, colY);
/*     */         } 
/*     */       } 
/*     */     } 
/* 141 */     return co;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Covariance(double[] x, double[] y) {
/* 151 */     if (x.length != y.length) {
/* 152 */       throw new IllegalArgumentException("The size of both matrix needs be equal");
/*     */     }
/* 154 */     double meanX = 0.0D, meanY = 0.0D;
/* 155 */     for (int i = 0; i < x.length; i++) {
/* 156 */       meanX += x[i];
/* 157 */       meanY += y[i];
/*     */     } 
/* 159 */     meanX /= x.length;
/* 160 */     meanY /= y.length;
/*     */     
/* 162 */     return Covariance(x, y, meanX, meanY);
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
/*     */   public static double Covariance(double[] x, double[] y, double meanX, double meanY) {
/* 174 */     double result = 0.0D;
/* 175 */     for (int i = 0; i < x.length; i++) {
/* 176 */       result += (x[i] - meanX) * (y[i] - meanY);
/*     */     }
/*     */     
/* 179 */     return result / (x.length - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[][] Covariance(double[][] matrix) {
/* 188 */     double[] means = new double[(matrix[0]).length]; int i;
/* 189 */     for (i = 0; i < matrix.length; i++) {
/* 190 */       for (int j = 0; j < (matrix[0]).length; j++) {
/* 191 */         means[j] = means[j] + matrix[i][j];
/*     */       }
/*     */     } 
/*     */     
/* 195 */     for (i = 0; i < means.length; i++) {
/* 196 */       means[i] = means[i] / means.length;
/*     */     }
/*     */     
/* 199 */     return Covariance(matrix, means);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[][] Covariance(double[][] matrix, double[] means) {
/* 209 */     double[][] cov = new double[means.length][means.length];
/*     */     
/* 211 */     for (int i = 0; i < cov.length; i++) {
/* 212 */       for (int j = 0; j < (cov[0]).length; j++) {
/* 213 */         cov[i][j] = Covariance(Matrix.getColumn(matrix, i), Matrix.getColumn(matrix, j), means[i], means[j]);
/*     */       }
/*     */     } 
/*     */     
/* 217 */     return cov;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Fisher(double n) {
/* 228 */     if (n <= -1.0D || n >= 1.0D) {
/* 229 */       throw new IllegalArgumentException("Fisher works with number between -1 < x < 1");
/*     */     }
/* 231 */     double r = (1.0D + n) / (1.0D - n);
/* 232 */     return 0.5D * Math.log(r);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Inclination(double[] x, double[] y) {
/* 242 */     if (x.length != y.length) {
/* 243 */       throw new IllegalArgumentException("The size of both matrix needs be equal");
/*     */     }
/* 245 */     double meanX = 0.0D, meanY = 0.0D;
/* 246 */     for (int i = 0; i < x.length; i++) {
/* 247 */       meanX += x[i];
/* 248 */       meanY += y[i];
/*     */     } 
/*     */     
/* 251 */     meanX /= x.length;
/* 252 */     meanY /= y.length;
/*     */     
/* 254 */     double num = 0.0D, den = 0.0D;
/* 255 */     for (int j = 0; j < x.length; j++) {
/* 256 */       num += (x[j] - meanX) * (y[j] - meanY);
/* 257 */       den += Math.pow(x[j] - meanX, 2.0D);
/*     */     } 
/*     */     
/* 260 */     return num / den;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double InverseFisher(double n) {
/* 269 */     if (n <= -1.0D || n >= 1.0D) {
/* 270 */       throw new IllegalArgumentException("Fisher works with number between -1 < x < 1");
/*     */     }
/* 272 */     double r = (Math.pow(Math.E, 2.0D * n) - 1.0D) / (Math.pow(Math.E, 2.0D * n) + 1.0D);
/* 273 */     return r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Interception(double[] x, double[] y) {
/* 283 */     if (x.length != y.length) {
/* 284 */       throw new IllegalArgumentException("The size of both matrix needs be equal");
/*     */     }
/* 286 */     double meanX = 0.0D, meanY = 0.0D;
/* 287 */     for (int i = 0; i < x.length; i++) {
/* 288 */       meanX += x[i];
/* 289 */       meanY += y[i];
/*     */     } 
/*     */     
/* 292 */     meanX /= x.length;
/* 293 */     meanY /= y.length;
/*     */     
/* 295 */     double b = Inclination(x, y);
/* 296 */     double a = meanY - b * meanX;
/* 297 */     return a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Max(double[] x) {
/* 306 */     double m = x[0];
/* 307 */     for (int i = 1; i < x.length; i++) {
/* 308 */       if (x[i] > m) m = x[i]; 
/*     */     } 
/* 310 */     return m;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Mean(double[] x) {
/* 319 */     double r = 0.0D;
/* 320 */     for (int i = 0; i < x.length; i++) {
/* 321 */       r += x[i];
/*     */     }
/*     */     
/* 324 */     return r / x.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] Mean(double[][] data) {
/* 334 */     double[] means = new double[(data[0]).length]; int i;
/* 335 */     for (i = 0; i < data.length; i++) {
/* 336 */       for (int j = 0; j < (data[0]).length; j++) {
/* 337 */         means[j] = means[j] + data[i][j];
/*     */       }
/*     */     } 
/*     */     
/* 341 */     for (i = 0; i < means.length; i++) {
/* 342 */       means[i] = means[i] / data.length;
/*     */     }
/* 344 */     return means;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Min(double[] x) {
/* 354 */     double m = x[0];
/* 355 */     for (int i = 1; i < x.length; i++) {
/* 356 */       if (x[i] < m) m = x[i]; 
/*     */     } 
/* 358 */     return m;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Mode(double[] values) {
/* 367 */     Arrays.sort(values);
/* 368 */     double v = values[0];
/* 369 */     int index = 0, x = 0, rep = 0;
/* 370 */     for (int i = 1; i < values.length; i++) {
/* 371 */       if (values[i] == v) {
/* 372 */         x++;
/* 373 */         if (x > rep) {
/* 374 */           rep = x;
/* 375 */           index = i;
/*     */         } 
/* 377 */         v = values[i];
/* 378 */         x = 0;
/*     */       } else {
/*     */         
/* 381 */         if (x > rep) {
/* 382 */           rep = x;
/* 383 */           index = i;
/*     */         } 
/* 385 */         v = values[i];
/* 386 */         x = 0;
/*     */       } 
/*     */     } 
/* 389 */     return values[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int Mode(int[] values) {
/* 398 */     Arrays.sort(values);
/* 399 */     int v = values[0];
/* 400 */     int index = 0, x = 0, rep = 0;
/* 401 */     for (int i = 1; i < values.length; i++) {
/* 402 */       if (values[i] == v) {
/* 403 */         x++;
/* 404 */         if (x > rep) {
/* 405 */           rep = x;
/* 406 */           index = i;
/*     */         } 
/* 408 */         v = values[i];
/* 409 */         x = 0;
/*     */       } else {
/*     */         
/* 412 */         if (x > rep) {
/* 413 */           rep = x;
/* 414 */           index = i;
/*     */         } 
/* 416 */         v = values[i];
/* 417 */         x = 0;
/*     */       } 
/*     */     } 
/* 420 */     return values[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double GeometricMean(double[] x) {
/* 430 */     double r = 1.0D;
/* 431 */     for (int i = 0; i < x.length; i++) {
/* 432 */       r *= x[i];
/*     */     }
/*     */     
/* 435 */     return Math.pow(r, 1.0D / x.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double HarmonicMean(double[] x) {
/* 445 */     double r = 0.0D;
/* 446 */     for (int i = 0; i < x.length; i++) {
/* 447 */       r += 1.0D / x[i];
/*     */     }
/*     */     
/* 450 */     return x.length / r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double ContraHarmonicMean(double[] x, int order) {
/* 461 */     double r1 = 0.0D, r2 = 0.0D;
/* 462 */     for (int i = 0; i < x.length; i++) {
/* 463 */       r1 += Math.pow(x[i], (order + 1));
/* 464 */       r2 += Math.pow(x[i], order);
/*     */     } 
/*     */     
/* 467 */     return r1 / r2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Sum(double[] x) {
/* 476 */     double sum = 0.0D;
/* 477 */     for (int i = 0; i < x.length; i++) {
/* 478 */       sum += x[i];
/*     */     }
/* 480 */     return sum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Variance(double[] x) {
/* 489 */     return Variance(x, Mean(x));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Variance(double[] x, double mean) {
/* 499 */     double sum = 0.0D;
/* 500 */     for (int i = 0; i < x.length; i++) {
/* 501 */       sum += Math.pow(x[i] - mean, 2.0D);
/*     */     }
/* 503 */     double var = sum / (x.length - 1.0D);
/* 504 */     return var;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double StandartDeviation(double[] x) {
/* 513 */     return Math.sqrt(Variance(x));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double StandartDeviation(double[] x, double mean) {
/* 523 */     return Math.sqrt(Variance(x, mean));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] StandartDeviation(double[][] data) {
/* 532 */     double[] means = Mean(data);
/* 533 */     return StandartDeviation(data, means);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] StandartDeviation(double[][] data, double[] means) {
/* 544 */     double[] std = new double[means.length];
/*     */     int i;
/* 546 */     for (i = 0; i < data.length; i++) {
/* 547 */       for (int j = 0; j < (data[0]).length; j++) {
/* 548 */         std[j] = std[j] + Math.pow(data[i][j] - means[j], 2.0D);
/*     */       }
/*     */     } 
/*     */     
/* 552 */     for (i = 0; i < std.length; i++) {
/* 553 */       std[i] = Math.sqrt(std[i] / (data.length - 1.0D));
/*     */     }
/*     */     
/* 556 */     return std;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Tools.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */