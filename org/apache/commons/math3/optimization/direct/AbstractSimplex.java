/*     */ package org.apache.commons.math3.optimization.direct;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import org.apache.commons.math3.analysis.MultivariateFunction;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.ZeroException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.optimization.OptimizationData;
/*     */ import org.apache.commons.math3.optimization.PointValuePair;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public abstract class AbstractSimplex
/*     */   implements OptimizationData
/*     */ {
/*     */   private PointValuePair[] simplex;
/*     */   private double[][] startConfiguration;
/*     */   private final int dimension;
/*     */   
/*     */   protected AbstractSimplex(int n) {
/*  66 */     this(n, 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractSimplex(int n, double sideLength) {
/*  77 */     this(createHypercubeSteps(n, sideLength));
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
/*     */   protected AbstractSimplex(double[] steps) {
/*  99 */     if (steps == null) {
/* 100 */       throw new NullArgumentException();
/*     */     }
/* 102 */     if (steps.length == 0) {
/* 103 */       throw new ZeroException();
/*     */     }
/* 105 */     this.dimension = steps.length;
/*     */ 
/*     */ 
/*     */     
/* 109 */     this.startConfiguration = new double[this.dimension][this.dimension];
/* 110 */     for (int i = 0; i < this.dimension; i++) {
/* 111 */       double[] vertexI = this.startConfiguration[i];
/* 112 */       for (int j = 0; j < i + 1; j++) {
/* 113 */         if (steps[j] == 0.0D) {
/* 114 */           throw new ZeroException(LocalizedFormats.EQUAL_VERTICES_IN_SIMPLEX, new Object[0]);
/*     */         }
/* 116 */         System.arraycopy(steps, 0, vertexI, 0, j + 1);
/*     */       } 
/*     */     } 
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
/*     */   protected AbstractSimplex(double[][] referenceSimplex) {
/* 134 */     if (referenceSimplex.length <= 0) {
/* 135 */       throw new NotStrictlyPositiveException(LocalizedFormats.SIMPLEX_NEED_ONE_POINT, Integer.valueOf(referenceSimplex.length));
/*     */     }
/*     */     
/* 138 */     this.dimension = referenceSimplex.length - 1;
/*     */ 
/*     */ 
/*     */     
/* 142 */     this.startConfiguration = new double[this.dimension][this.dimension];
/* 143 */     double[] ref0 = referenceSimplex[0];
/*     */ 
/*     */     
/* 146 */     for (int i = 0; i < referenceSimplex.length; i++) {
/* 147 */       double[] refI = referenceSimplex[i];
/*     */ 
/*     */       
/* 150 */       if (refI.length != this.dimension) {
/* 151 */         throw new DimensionMismatchException(refI.length, this.dimension);
/*     */       }
/* 153 */       for (int j = 0; j < i; j++) {
/* 154 */         double[] refJ = referenceSimplex[j];
/* 155 */         boolean allEquals = true;
/* 156 */         for (int k = 0; k < this.dimension; k++) {
/* 157 */           if (refI[k] != refJ[k]) {
/* 158 */             allEquals = false;
/*     */             break;
/*     */           } 
/*     */         } 
/* 162 */         if (allEquals) {
/* 163 */           throw new MathIllegalArgumentException(LocalizedFormats.EQUAL_VERTICES_IN_SIMPLEX, new Object[] { Integer.valueOf(i), Integer.valueOf(j) });
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 169 */       if (i > 0) {
/* 170 */         double[] confI = this.startConfiguration[i - 1];
/* 171 */         for (int k = 0; k < this.dimension; k++) {
/* 172 */           confI[k] = refI[k] - ref0[k];
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDimension() {
/* 184 */     return this.dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 195 */     return this.simplex.length;
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
/*     */   public abstract void iterate(MultivariateFunction paramMultivariateFunction, Comparator<PointValuePair> paramComparator);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void build(double[] startPoint) {
/* 218 */     if (this.dimension != startPoint.length) {
/* 219 */       throw new DimensionMismatchException(this.dimension, startPoint.length);
/*     */     }
/*     */ 
/*     */     
/* 223 */     this.simplex = new PointValuePair[this.dimension + 1];
/* 224 */     this.simplex[0] = new PointValuePair(startPoint, Double.NaN);
/*     */ 
/*     */     
/* 227 */     for (int i = 0; i < this.dimension; i++) {
/* 228 */       double[] confI = this.startConfiguration[i];
/* 229 */       double[] vertexI = new double[this.dimension];
/* 230 */       for (int k = 0; k < this.dimension; k++) {
/* 231 */         vertexI[k] = startPoint[k] + confI[k];
/*     */       }
/* 233 */       this.simplex[i + 1] = new PointValuePair(vertexI, Double.NaN);
/*     */     } 
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
/*     */   public void evaluate(MultivariateFunction evaluationFunction, Comparator<PointValuePair> comparator) {
/* 248 */     for (int i = 0; i < this.simplex.length; i++) {
/* 249 */       PointValuePair vertex = this.simplex[i];
/* 250 */       double[] point = vertex.getPointRef();
/* 251 */       if (Double.isNaN(((Double)vertex.getValue()).doubleValue())) {
/* 252 */         this.simplex[i] = new PointValuePair(point, evaluationFunction.value(point), false);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 257 */     Arrays.sort(this.simplex, comparator);
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
/*     */   protected void replaceWorstPoint(PointValuePair pointValuePair, Comparator<PointValuePair> comparator) {
/* 269 */     for (int i = 0; i < this.dimension; i++) {
/* 270 */       if (comparator.compare(this.simplex[i], pointValuePair) > 0) {
/* 271 */         PointValuePair tmp = this.simplex[i];
/* 272 */         this.simplex[i] = pointValuePair;
/* 273 */         pointValuePair = tmp;
/*     */       } 
/*     */     } 
/* 276 */     this.simplex[this.dimension] = pointValuePair;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PointValuePair[] getPoints() {
/* 285 */     PointValuePair[] copy = new PointValuePair[this.simplex.length];
/* 286 */     System.arraycopy(this.simplex, 0, copy, 0, this.simplex.length);
/* 287 */     return copy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PointValuePair getPoint(int index) {
/* 297 */     if (index < 0 || index >= this.simplex.length)
/*     */     {
/* 299 */       throw new OutOfRangeException(Integer.valueOf(index), Integer.valueOf(0), Integer.valueOf(this.simplex.length - 1));
/*     */     }
/* 301 */     return this.simplex[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setPoint(int index, PointValuePair point) {
/* 312 */     if (index < 0 || index >= this.simplex.length)
/*     */     {
/* 314 */       throw new OutOfRangeException(Integer.valueOf(index), Integer.valueOf(0), Integer.valueOf(this.simplex.length - 1));
/*     */     }
/* 316 */     this.simplex[index] = point;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setPoints(PointValuePair[] points) {
/* 326 */     if (points.length != this.simplex.length) {
/* 327 */       throw new DimensionMismatchException(points.length, this.simplex.length);
/*     */     }
/* 329 */     this.simplex = points;
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
/*     */   private static double[] createHypercubeSteps(int n, double sideLength) {
/* 341 */     double[] steps = new double[n];
/* 342 */     for (int i = 0; i < n; i++) {
/* 343 */       steps[i] = sideLength;
/*     */     }
/* 345 */     return steps;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/direct/AbstractSimplex.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */