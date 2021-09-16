/*     */ package org.jfree.data.statistics;
/*     */ 
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Regression
/*     */ {
/*     */   public static double[] getOLSRegression(double[][] data) {
/*  68 */     int n = data.length;
/*  69 */     if (n < 2) {
/*  70 */       throw new IllegalArgumentException("Not enough data.");
/*     */     }
/*     */     
/*  73 */     double sumX = 0.0D;
/*  74 */     double sumY = 0.0D;
/*  75 */     double sumXX = 0.0D;
/*  76 */     double sumXY = 0.0D;
/*  77 */     for (int i = 0; i < n; i++) {
/*  78 */       double x = data[i][0];
/*  79 */       double y = data[i][1];
/*  80 */       sumX += x;
/*  81 */       sumY += y;
/*  82 */       double xx = x * x;
/*  83 */       sumXX += xx;
/*  84 */       double xy = x * y;
/*  85 */       sumXY += xy;
/*     */     } 
/*  87 */     double sxx = sumXX - sumX * sumX / n;
/*  88 */     double sxy = sumXY - sumX * sumY / n;
/*  89 */     double xbar = sumX / n;
/*  90 */     double ybar = sumY / n;
/*     */     
/*  92 */     double[] result = new double[2];
/*  93 */     result[1] = sxy / sxx;
/*  94 */     result[0] = ybar - result[1] * xbar;
/*     */     
/*  96 */     return result;
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
/*     */   public static double[] getOLSRegression(XYDataset data, int series) {
/* 112 */     int n = data.getItemCount(series);
/* 113 */     if (n < 2) {
/* 114 */       throw new IllegalArgumentException("Not enough data.");
/*     */     }
/*     */     
/* 117 */     double sumX = 0.0D;
/* 118 */     double sumY = 0.0D;
/* 119 */     double sumXX = 0.0D;
/* 120 */     double sumXY = 0.0D;
/* 121 */     for (int i = 0; i < n; i++) {
/* 122 */       double x = data.getXValue(series, i);
/* 123 */       double y = data.getYValue(series, i);
/* 124 */       sumX += x;
/* 125 */       sumY += y;
/* 126 */       double xx = x * x;
/* 127 */       sumXX += xx;
/* 128 */       double xy = x * y;
/* 129 */       sumXY += xy;
/*     */     } 
/* 131 */     double sxx = sumXX - sumX * sumX / n;
/* 132 */     double sxy = sumXY - sumX * sumY / n;
/* 133 */     double xbar = sumX / n;
/* 134 */     double ybar = sumY / n;
/*     */     
/* 136 */     double[] result = new double[2];
/* 137 */     result[1] = sxy / sxx;
/* 138 */     result[0] = ybar - result[1] * xbar;
/*     */     
/* 140 */     return result;
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
/*     */   public static double[] getPowerRegression(double[][] data) {
/* 155 */     int n = data.length;
/* 156 */     if (n < 2) {
/* 157 */       throw new IllegalArgumentException("Not enough data.");
/*     */     }
/*     */     
/* 160 */     double sumX = 0.0D;
/* 161 */     double sumY = 0.0D;
/* 162 */     double sumXX = 0.0D;
/* 163 */     double sumXY = 0.0D;
/* 164 */     for (int i = 0; i < n; i++) {
/* 165 */       double x = Math.log(data[i][0]);
/* 166 */       double y = Math.log(data[i][1]);
/* 167 */       sumX += x;
/* 168 */       sumY += y;
/* 169 */       double xx = x * x;
/* 170 */       sumXX += xx;
/* 171 */       double xy = x * y;
/* 172 */       sumXY += xy;
/*     */     } 
/* 174 */     double sxx = sumXX - sumX * sumX / n;
/* 175 */     double sxy = sumXY - sumX * sumY / n;
/* 176 */     double xbar = sumX / n;
/* 177 */     double ybar = sumY / n;
/*     */     
/* 179 */     double[] result = new double[2];
/* 180 */     result[1] = sxy / sxx;
/* 181 */     result[0] = Math.pow(Math.exp(1.0D), ybar - result[1] * xbar);
/*     */     
/* 183 */     return result;
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
/*     */   public static double[] getPowerRegression(XYDataset data, int series) {
/* 199 */     int n = data.getItemCount(series);
/* 200 */     if (n < 2) {
/* 201 */       throw new IllegalArgumentException("Not enough data.");
/*     */     }
/*     */     
/* 204 */     double sumX = 0.0D;
/* 205 */     double sumY = 0.0D;
/* 206 */     double sumXX = 0.0D;
/* 207 */     double sumXY = 0.0D;
/* 208 */     for (int i = 0; i < n; i++) {
/* 209 */       double x = Math.log(data.getXValue(series, i));
/* 210 */       double y = Math.log(data.getYValue(series, i));
/* 211 */       sumX += x;
/* 212 */       sumY += y;
/* 213 */       double xx = x * x;
/* 214 */       sumXX += xx;
/* 215 */       double xy = x * y;
/* 216 */       sumXY += xy;
/*     */     } 
/* 218 */     double sxx = sumXX - sumX * sumX / n;
/* 219 */     double sxy = sumXY - sumX * sumY / n;
/* 220 */     double xbar = sumX / n;
/* 221 */     double ybar = sumY / n;
/*     */     
/* 223 */     double[] result = new double[2];
/* 224 */     result[1] = sxy / sxx;
/* 225 */     result[0] = Math.pow(Math.exp(1.0D), ybar - result[1] * xbar);
/*     */     
/* 227 */     return result;
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
/*     */   public static double[] getPolynomialRegression(XYDataset dataset, int series, int order) {
/* 252 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/* 253 */     int itemCount = dataset.getItemCount(series);
/* 254 */     if (itemCount < order + 1) {
/* 255 */       throw new IllegalArgumentException("Not enough data.");
/*     */     }
/* 257 */     int validItems = 0;
/* 258 */     double[][] data = new double[2][itemCount];
/* 259 */     for (int item = 0; item < itemCount; item++) {
/* 260 */       double x = dataset.getXValue(series, item);
/* 261 */       double y = dataset.getYValue(series, item);
/* 262 */       if (!Double.isNaN(x) && !Double.isNaN(y)) {
/* 263 */         data[0][validItems] = x;
/* 264 */         data[1][validItems] = y;
/* 265 */         validItems++;
/*     */       } 
/*     */     } 
/* 268 */     if (validItems < order + 1) {
/* 269 */       throw new IllegalArgumentException("Not enough data.");
/*     */     }
/* 271 */     int equations = order + 1;
/* 272 */     int coefficients = order + 2;
/* 273 */     double[] result = new double[equations + 1];
/* 274 */     double[][] matrix = new double[equations][coefficients];
/* 275 */     double sumX = 0.0D;
/* 276 */     double sumY = 0.0D;
/*     */     
/* 278 */     for (int i = 0; i < validItems; i++) {
/* 279 */       sumX += data[0][i];
/* 280 */       sumY += data[1][i];
/* 281 */       for (int k = 0; k < equations; k++) {
/* 282 */         for (int coe = 0; coe < coefficients - 1; coe++) {
/* 283 */           matrix[k][coe] = matrix[k][coe] + Math.pow(data[0][i], (k + coe));
/*     */         }
/* 285 */         matrix[k][coefficients - 1] = matrix[k][coefficients - 1] + data[1][i] * 
/* 286 */           Math.pow(data[0][i], k);
/*     */       } 
/*     */     } 
/* 289 */     double[][] subMatrix = calculateSubMatrix(matrix); int eq;
/* 290 */     for (eq = 1; eq < equations; eq++) {
/* 291 */       matrix[eq][0] = 0.0D;
/* 292 */       for (int coe = 1; coe < coefficients; coe++) {
/* 293 */         matrix[eq][coe] = subMatrix[eq - 1][coe - 1];
/*     */       }
/*     */     } 
/* 296 */     for (eq = equations - 1; eq > -1; eq--) {
/* 297 */       double value = matrix[eq][coefficients - 1];
/* 298 */       for (int coe = eq; coe < coefficients - 1; coe++) {
/* 299 */         value -= matrix[eq][coe] * result[coe];
/*     */       }
/* 301 */       result[eq] = value / matrix[eq][eq];
/*     */     } 
/* 303 */     double meanY = sumY / validItems;
/* 304 */     double yObsSquare = 0.0D;
/* 305 */     double yRegSquare = 0.0D;
/* 306 */     for (int j = 0; j < validItems; j++) {
/* 307 */       double yCalc = 0.0D;
/* 308 */       for (int k = 0; k < equations; k++) {
/* 309 */         yCalc += result[k] * Math.pow(data[0][j], k);
/*     */       }
/* 311 */       yRegSquare += Math.pow(yCalc - meanY, 2.0D);
/* 312 */       yObsSquare += Math.pow(data[1][j] - meanY, 2.0D);
/*     */     } 
/* 314 */     double rSquare = yRegSquare / yObsSquare;
/* 315 */     result[equations] = rSquare;
/* 316 */     return result;
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
/*     */   private static double[][] calculateSubMatrix(double[][] matrix) {
/* 330 */     int equations = matrix.length;
/* 331 */     int coefficients = (matrix[0]).length;
/* 332 */     double[][] result = new double[equations - 1][coefficients - 1];
/* 333 */     for (int eq = 1; eq < equations; eq++) {
/* 334 */       double factor = matrix[0][0] / matrix[eq][0];
/* 335 */       for (int coe = 1; coe < coefficients; coe++) {
/* 336 */         result[eq - 1][coe - 1] = matrix[0][coe] - matrix[eq][coe] * factor;
/*     */       }
/*     */     } 
/*     */     
/* 340 */     if (equations == 1) {
/* 341 */       return result;
/*     */     }
/*     */     
/* 344 */     if (result[0][0] == 0.0D) {
/* 345 */       boolean found = false;
/* 346 */       for (int j = 0; j < result.length; j++) {
/* 347 */         if (result[j][0] != 0.0D) {
/* 348 */           found = true;
/* 349 */           double[] temp = result[0];
/* 350 */           System.arraycopy(result[j], 0, result[0], 0, (result[j]).length);
/*     */           
/* 352 */           System.arraycopy(temp, 0, result[j], 0, temp.length);
/*     */           break;
/*     */         } 
/*     */       } 
/* 356 */       if (!found)
/*     */       {
/* 358 */         return new double[equations - 1][coefficients - 1];
/*     */       }
/*     */     } 
/* 361 */     double[][] subMatrix = calculateSubMatrix(result);
/* 362 */     for (int i = 1; i < equations - 1; i++) {
/* 363 */       result[i][0] = 0.0D;
/* 364 */       for (int coe = 1; coe < coefficients - 1; coe++) {
/* 365 */         result[i][coe] = subMatrix[i - 1][coe - 1];
/*     */       }
/*     */     } 
/* 368 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/statistics/Regression.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */