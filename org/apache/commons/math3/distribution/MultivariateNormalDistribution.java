/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.linear.Array2DRowRealMatrix;
/*     */ import org.apache.commons.math3.linear.EigenDecomposition;
/*     */ import org.apache.commons.math3.linear.NonPositiveDefiniteMatrixException;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ import org.apache.commons.math3.linear.SingularMatrixException;
/*     */ import org.apache.commons.math3.random.RandomGenerator;
/*     */ import org.apache.commons.math3.random.Well19937c;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.MathArrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultivariateNormalDistribution
/*     */   extends AbstractMultivariateRealDistribution
/*     */ {
/*     */   private final double[] means;
/*     */   private final RealMatrix covarianceMatrix;
/*     */   private final RealMatrix covarianceMatrixInverse;
/*     */   private final double covarianceMatrixDeterminant;
/*     */   private final RealMatrix samplingMatrix;
/*     */   
/*     */   public MultivariateNormalDistribution(double[] means, double[][] covariances) throws SingularMatrixException, DimensionMismatchException, NonPositiveDefiniteMatrixException {
/*  82 */     this((RandomGenerator)new Well19937c(), means, covariances);
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
/*     */   public MultivariateNormalDistribution(RandomGenerator rng, double[] means, double[][] covariances) throws SingularMatrixException, DimensionMismatchException, NonPositiveDefiniteMatrixException {
/* 109 */     super(rng, means.length);
/*     */     
/* 111 */     int dim = means.length;
/*     */     
/* 113 */     if (covariances.length != dim) {
/* 114 */       throw new DimensionMismatchException(covariances.length, dim);
/*     */     }
/*     */     
/* 117 */     for (int i = 0; i < dim; i++) {
/* 118 */       if (dim != (covariances[i]).length) {
/* 119 */         throw new DimensionMismatchException((covariances[i]).length, dim);
/*     */       }
/*     */     } 
/*     */     
/* 123 */     this.means = MathArrays.copyOf(means);
/*     */     
/* 125 */     this.covarianceMatrix = (RealMatrix)new Array2DRowRealMatrix(covariances);
/*     */ 
/*     */     
/* 128 */     EigenDecomposition covMatDec = new EigenDecomposition(this.covarianceMatrix);
/*     */ 
/*     */     
/* 131 */     this.covarianceMatrixInverse = covMatDec.getSolver().getInverse();
/*     */     
/* 133 */     this.covarianceMatrixDeterminant = covMatDec.getDeterminant();
/*     */ 
/*     */     
/* 136 */     double[] covMatEigenvalues = covMatDec.getRealEigenvalues();
/*     */     
/* 138 */     for (int j = 0; j < covMatEigenvalues.length; j++) {
/* 139 */       if (covMatEigenvalues[j] < 0.0D) {
/* 140 */         throw new NonPositiveDefiniteMatrixException(covMatEigenvalues[j], j, 0.0D);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 145 */     Array2DRowRealMatrix covMatEigenvectors = new Array2DRowRealMatrix(dim, dim);
/* 146 */     for (int v = 0; v < dim; v++) {
/* 147 */       double[] evec = covMatDec.getEigenvector(v).toArray();
/* 148 */       covMatEigenvectors.setColumn(v, evec);
/*     */     } 
/*     */     
/* 151 */     RealMatrix tmpMatrix = covMatEigenvectors.transpose();
/*     */ 
/*     */     
/* 154 */     for (int row = 0; row < dim; row++) {
/* 155 */       double factor = FastMath.sqrt(covMatEigenvalues[row]);
/* 156 */       for (int col = 0; col < dim; col++) {
/* 157 */         tmpMatrix.multiplyEntry(row, col, factor);
/*     */       }
/*     */     } 
/*     */     
/* 161 */     this.samplingMatrix = covMatEigenvectors.multiply(tmpMatrix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getMeans() {
/* 170 */     return MathArrays.copyOf(this.means);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getCovariances() {
/* 179 */     return this.covarianceMatrix.copy();
/*     */   }
/*     */ 
/*     */   
/*     */   public double density(double[] vals) throws DimensionMismatchException {
/* 184 */     int dim = getDimension();
/* 185 */     if (vals.length != dim) {
/* 186 */       throw new DimensionMismatchException(vals.length, dim);
/*     */     }
/*     */     
/* 189 */     return FastMath.pow(6.283185307179586D, -0.5D * dim) * FastMath.pow(this.covarianceMatrixDeterminant, -0.5D) * getExponentTerm(vals);
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
/*     */   public double[] getStandardDeviations() {
/* 201 */     int dim = getDimension();
/* 202 */     double[] std = new double[dim];
/* 203 */     double[][] s = this.covarianceMatrix.getData();
/* 204 */     for (int i = 0; i < dim; i++) {
/* 205 */       std[i] = FastMath.sqrt(s[i][i]);
/*     */     }
/* 207 */     return std;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] sample() {
/* 213 */     int dim = getDimension();
/* 214 */     double[] normalVals = new double[dim];
/*     */     
/* 216 */     for (int i = 0; i < dim; i++) {
/* 217 */       normalVals[i] = this.random.nextGaussian();
/*     */     }
/*     */     
/* 220 */     double[] vals = this.samplingMatrix.operate(normalVals);
/*     */     
/* 222 */     for (int j = 0; j < dim; j++) {
/* 223 */       vals[j] = vals[j] + this.means[j];
/*     */     }
/*     */     
/* 226 */     return vals;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double getExponentTerm(double[] values) {
/* 236 */     double[] centered = new double[values.length];
/* 237 */     for (int i = 0; i < centered.length; i++) {
/* 238 */       centered[i] = values[i] - getMeans()[i];
/*     */     }
/* 240 */     double[] preMultiplied = this.covarianceMatrixInverse.preMultiply(centered);
/* 241 */     double sum = 0.0D;
/* 242 */     for (int j = 0; j < preMultiplied.length; j++) {
/* 243 */       sum += preMultiplied[j] * centered[j];
/*     */     }
/* 245 */     return FastMath.exp(-0.5D * sum);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/MultivariateNormalDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */