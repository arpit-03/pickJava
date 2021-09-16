/*     */ package org.apache.commons.math3.stat.correlation;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.linear.BlockRealMatrix;
/*     */ import org.apache.commons.math3.linear.MatrixUtils;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.Pair;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KendallsCorrelation
/*     */ {
/*     */   private final RealMatrix correlationMatrix;
/*     */   
/*     */   public KendallsCorrelation() {
/*  79 */     this.correlationMatrix = null;
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
/*     */   public KendallsCorrelation(double[][] data) {
/*  91 */     this(MatrixUtils.createRealMatrix(data));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KendallsCorrelation(RealMatrix matrix) {
/* 101 */     this.correlationMatrix = computeCorrelationMatrix(matrix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getCorrelationMatrix() {
/* 110 */     return this.correlationMatrix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix computeCorrelationMatrix(RealMatrix matrix) {
/* 121 */     int nVars = matrix.getColumnDimension();
/* 122 */     BlockRealMatrix blockRealMatrix = new BlockRealMatrix(nVars, nVars);
/* 123 */     for (int i = 0; i < nVars; i++) {
/* 124 */       for (int j = 0; j < i; j++) {
/* 125 */         double corr = correlation(matrix.getColumn(i), matrix.getColumn(j));
/* 126 */         blockRealMatrix.setEntry(i, j, corr);
/* 127 */         blockRealMatrix.setEntry(j, i, corr);
/*     */       } 
/* 129 */       blockRealMatrix.setEntry(i, i, 1.0D);
/*     */     } 
/* 131 */     return (RealMatrix)blockRealMatrix;
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
/*     */   public RealMatrix computeCorrelationMatrix(double[][] matrix) {
/* 143 */     return computeCorrelationMatrix((RealMatrix)new BlockRealMatrix(matrix));
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
/*     */   public double correlation(double[] xArray, double[] yArray) throws DimensionMismatchException {
/* 157 */     if (xArray.length != yArray.length) {
/* 158 */       throw new DimensionMismatchException(xArray.length, yArray.length);
/*     */     }
/*     */     
/* 161 */     int n = xArray.length;
/* 162 */     long numPairs = sum((n - 1));
/*     */ 
/*     */     
/* 165 */     Pair[] arrayOfPair1 = new Pair[n];
/* 166 */     for (int i = 0; i < n; i++) {
/* 167 */       arrayOfPair1[i] = new Pair(Double.valueOf(xArray[i]), Double.valueOf(yArray[i]));
/*     */     }
/*     */     
/* 170 */     Arrays.sort(arrayOfPair1, (Comparator)new Comparator<Pair<Double, Double>>()
/*     */         {
/*     */           public int compare(Pair<Double, Double> pair1, Pair<Double, Double> pair2) {
/* 173 */             int compareFirst = ((Double)pair1.getFirst()).compareTo((Double)pair2.getFirst());
/* 174 */             return (compareFirst != 0) ? compareFirst : ((Double)pair1.getSecond()).compareTo((Double)pair2.getSecond());
/*     */           }
/*     */         });
/*     */     
/* 178 */     long tiedXPairs = 0L;
/* 179 */     long tiedXYPairs = 0L;
/* 180 */     long consecutiveXTies = 1L;
/* 181 */     long consecutiveXYTies = 1L;
/* 182 */     Pair<Double, Double> prev = arrayOfPair1[0];
/* 183 */     for (int j = 1; j < n; j++) {
/* 184 */       Pair<Double, Double> curr = arrayOfPair1[j];
/* 185 */       if (((Double)curr.getFirst()).equals(prev.getFirst())) {
/* 186 */         consecutiveXTies++;
/* 187 */         if (((Double)curr.getSecond()).equals(prev.getSecond())) {
/* 188 */           consecutiveXYTies++;
/*     */         } else {
/* 190 */           tiedXYPairs += sum(consecutiveXYTies - 1L);
/* 191 */           consecutiveXYTies = 1L;
/*     */         } 
/*     */       } else {
/* 194 */         tiedXPairs += sum(consecutiveXTies - 1L);
/* 195 */         consecutiveXTies = 1L;
/* 196 */         tiedXYPairs += sum(consecutiveXYTies - 1L);
/* 197 */         consecutiveXYTies = 1L;
/*     */       } 
/* 199 */       prev = curr;
/*     */     } 
/* 201 */     tiedXPairs += sum(consecutiveXTies - 1L);
/* 202 */     tiedXYPairs += sum(consecutiveXYTies - 1L);
/*     */     
/* 204 */     long swaps = 0L;
/*     */     
/* 206 */     Pair[] arrayOfPair2 = new Pair[n]; int segmentSize;
/* 207 */     for (segmentSize = 1; segmentSize < n; segmentSize <<= 1) {
/* 208 */       int offset; for (offset = 0; offset < n; ) {
/* 209 */         int m = offset;
/* 210 */         int iEnd = FastMath.min(m + segmentSize, n);
/* 211 */         int i1 = iEnd;
/* 212 */         int jEnd = FastMath.min(i1 + segmentSize, n);
/*     */         
/* 214 */         int copyLocation = offset; for (;; offset += 2 * segmentSize) {
/* 215 */           if (m < iEnd || i1 < jEnd) {
/* 216 */             if (m < iEnd) {
/* 217 */               if (i1 < jEnd) {
/* 218 */                 if (((Double)arrayOfPair1[m].getSecond()).compareTo((Double)arrayOfPair1[i1].getSecond()) <= 0) {
/* 219 */                   arrayOfPair2[copyLocation] = arrayOfPair1[m];
/* 220 */                   m++;
/*     */                 } else {
/* 222 */                   arrayOfPair2[copyLocation] = arrayOfPair1[i1];
/* 223 */                   i1++;
/* 224 */                   swaps += (iEnd - m);
/*     */                 } 
/*     */               } else {
/* 227 */                 arrayOfPair2[copyLocation] = arrayOfPair1[m];
/* 228 */                 m++;
/*     */               } 
/*     */             } else {
/* 231 */               arrayOfPair2[copyLocation] = arrayOfPair1[i1];
/* 232 */               i1++;
/*     */             } 
/* 234 */             copyLocation++; continue;
/*     */           } 
/*     */         } 
/* 237 */       }  Pair[] arrayOfPair = arrayOfPair1;
/* 238 */       arrayOfPair1 = arrayOfPair2;
/* 239 */       arrayOfPair2 = arrayOfPair;
/*     */     } 
/*     */     
/* 242 */     long tiedYPairs = 0L;
/* 243 */     long consecutiveYTies = 1L;
/* 244 */     prev = arrayOfPair1[0];
/* 245 */     for (int k = 1; k < n; k++) {
/* 246 */       Pair<Double, Double> curr = arrayOfPair1[k];
/* 247 */       if (((Double)curr.getSecond()).equals(prev.getSecond())) {
/* 248 */         consecutiveYTies++;
/*     */       } else {
/* 250 */         tiedYPairs += sum(consecutiveYTies - 1L);
/* 251 */         consecutiveYTies = 1L;
/*     */       } 
/* 253 */       prev = curr;
/*     */     } 
/* 255 */     tiedYPairs += sum(consecutiveYTies - 1L);
/*     */     
/* 257 */     long concordantMinusDiscordant = numPairs - tiedXPairs - tiedYPairs + tiedXYPairs - 2L * swaps;
/* 258 */     double nonTiedPairsMultiplied = (numPairs - tiedXPairs) * (numPairs - tiedYPairs);
/* 259 */     return concordantMinusDiscordant / FastMath.sqrt(nonTiedPairsMultiplied);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long sum(long n) {
/* 270 */     return n * (n + 1L) / 2L;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/correlation/KendallsCorrelation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */