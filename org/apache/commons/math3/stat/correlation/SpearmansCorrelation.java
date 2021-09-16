/*     */ package org.apache.commons.math3.stat.correlation;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.linear.BlockRealMatrix;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ import org.apache.commons.math3.stat.ranking.NaNStrategy;
/*     */ import org.apache.commons.math3.stat.ranking.NaturalRanking;
/*     */ import org.apache.commons.math3.stat.ranking.RankingAlgorithm;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpearmansCorrelation
/*     */ {
/*     */   private final RealMatrix data;
/*     */   private final RankingAlgorithm rankingAlgorithm;
/*     */   private final PearsonsCorrelation rankCorrelation;
/*     */   
/*     */   public SpearmansCorrelation() {
/*  60 */     this((RankingAlgorithm)new NaturalRanking());
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
/*     */   public SpearmansCorrelation(RankingAlgorithm rankingAlgorithm) {
/*  73 */     this.data = null;
/*  74 */     this.rankingAlgorithm = rankingAlgorithm;
/*  75 */     this.rankCorrelation = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpearmansCorrelation(RealMatrix dataMatrix) {
/*  85 */     this(dataMatrix, (RankingAlgorithm)new NaturalRanking());
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
/*     */   public SpearmansCorrelation(RealMatrix dataMatrix, RankingAlgorithm rankingAlgorithm) {
/* 100 */     this.rankingAlgorithm = rankingAlgorithm;
/* 101 */     this.data = rankTransform(dataMatrix);
/* 102 */     this.rankCorrelation = new PearsonsCorrelation(this.data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getCorrelationMatrix() {
/* 112 */     return this.rankCorrelation.getCorrelationMatrix();
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
/*     */   public PearsonsCorrelation getRankCorrelation() {
/* 130 */     return this.rankCorrelation;
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
/* 141 */     RealMatrix matrixCopy = rankTransform(matrix);
/* 142 */     return (new PearsonsCorrelation()).computeCorrelationMatrix(matrixCopy);
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
/* 154 */     return computeCorrelationMatrix((RealMatrix)new BlockRealMatrix(matrix));
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
/*     */   public double correlation(double[] xArray, double[] yArray) {
/* 167 */     if (xArray.length != yArray.length)
/* 168 */       throw new DimensionMismatchException(xArray.length, yArray.length); 
/* 169 */     if (xArray.length < 2) {
/* 170 */       throw new MathIllegalArgumentException(LocalizedFormats.INSUFFICIENT_DIMENSION, new Object[] { Integer.valueOf(xArray.length), Integer.valueOf(2) });
/*     */     }
/*     */     
/* 173 */     double[] x = xArray;
/* 174 */     double[] y = yArray;
/* 175 */     if (this.rankingAlgorithm instanceof NaturalRanking && NaNStrategy.REMOVED == ((NaturalRanking)this.rankingAlgorithm).getNanStrategy()) {
/*     */       
/* 177 */       Set<Integer> nanPositions = new HashSet<Integer>();
/*     */       
/* 179 */       nanPositions.addAll(getNaNPositions(xArray));
/* 180 */       nanPositions.addAll(getNaNPositions(yArray));
/*     */       
/* 182 */       x = removeValues(xArray, nanPositions);
/* 183 */       y = removeValues(yArray, nanPositions);
/*     */     } 
/* 185 */     return (new PearsonsCorrelation()).correlation(this.rankingAlgorithm.rank(x), this.rankingAlgorithm.rank(y));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RealMatrix rankTransform(RealMatrix matrix) {
/*     */     BlockRealMatrix blockRealMatrix;
/* 197 */     RealMatrix realMatrix1, transformed = null;
/*     */     
/* 199 */     if (this.rankingAlgorithm instanceof NaturalRanking && ((NaturalRanking)this.rankingAlgorithm).getNanStrategy() == NaNStrategy.REMOVED) {
/*     */       
/* 201 */       Set<Integer> nanPositions = new HashSet<Integer>(); int j;
/* 202 */       for (j = 0; j < matrix.getColumnDimension(); j++) {
/* 203 */         nanPositions.addAll(getNaNPositions(matrix.getColumn(j)));
/*     */       }
/*     */ 
/*     */       
/* 207 */       if (!nanPositions.isEmpty()) {
/* 208 */         blockRealMatrix = new BlockRealMatrix(matrix.getRowDimension() - nanPositions.size(), matrix.getColumnDimension());
/*     */         
/* 210 */         for (j = 0; j < blockRealMatrix.getColumnDimension(); j++) {
/* 211 */           blockRealMatrix.setColumn(j, removeValues(matrix.getColumn(j), nanPositions));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 216 */     if (blockRealMatrix == null) {
/* 217 */       realMatrix1 = matrix.copy();
/*     */     }
/*     */     
/* 220 */     for (int i = 0; i < realMatrix1.getColumnDimension(); i++) {
/* 221 */       realMatrix1.setColumn(i, this.rankingAlgorithm.rank(realMatrix1.getColumn(i)));
/*     */     }
/*     */     
/* 224 */     return realMatrix1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Integer> getNaNPositions(double[] input) {
/* 234 */     List<Integer> positions = new ArrayList<Integer>();
/* 235 */     for (int i = 0; i < input.length; i++) {
/* 236 */       if (Double.isNaN(input[i])) {
/* 237 */         positions.add(Integer.valueOf(i));
/*     */       }
/*     */     } 
/* 240 */     return positions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double[] removeValues(double[] input, Set<Integer> indices) {
/* 251 */     if (indices.isEmpty()) {
/* 252 */       return input;
/*     */     }
/* 254 */     double[] result = new double[input.length - indices.size()];
/* 255 */     for (int i = 0, j = 0; i < input.length; i++) {
/* 256 */       if (!indices.contains(Integer.valueOf(i))) {
/* 257 */         result[j++] = input[i];
/*     */       }
/*     */     } 
/* 260 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/correlation/SpearmansCorrelation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */