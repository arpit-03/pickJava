/*     */ package org.apache.commons.math3.optimization;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import org.apache.commons.math3.analysis.MultivariateVectorFunction;
/*     */ import org.apache.commons.math3.exception.ConvergenceException;
/*     */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.random.RandomVectorGenerator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class BaseMultivariateVectorMultiStartOptimizer<FUNC extends MultivariateVectorFunction>
/*     */   implements BaseMultivariateVectorOptimizer<FUNC>
/*     */ {
/*     */   private final BaseMultivariateVectorOptimizer<FUNC> optimizer;
/*     */   private int maxEvaluations;
/*     */   private int totalEvaluations;
/*     */   private int starts;
/*     */   private RandomVectorGenerator generator;
/*     */   private PointVectorValuePair[] optima;
/*     */   
/*     */   protected BaseMultivariateVectorMultiStartOptimizer(BaseMultivariateVectorOptimizer<FUNC> optimizer, int starts, RandomVectorGenerator generator) {
/*  74 */     if (optimizer == null || generator == null)
/*     */     {
/*  76 */       throw new NullArgumentException();
/*     */     }
/*  78 */     if (starts < 1) {
/*  79 */       throw new NotStrictlyPositiveException(Integer.valueOf(starts));
/*     */     }
/*     */     
/*  82 */     this.optimizer = optimizer;
/*  83 */     this.starts = starts;
/*  84 */     this.generator = generator;
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
/*     */   public PointVectorValuePair[] getOptima() {
/* 115 */     if (this.optima == null) {
/* 116 */       throw new MathIllegalStateException(LocalizedFormats.NO_OPTIMUM_COMPUTED_YET, new Object[0]);
/*     */     }
/* 118 */     return (PointVectorValuePair[])this.optima.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxEvaluations() {
/* 123 */     return this.maxEvaluations;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEvaluations() {
/* 128 */     return this.totalEvaluations;
/*     */   }
/*     */ 
/*     */   
/*     */   public ConvergenceChecker<PointVectorValuePair> getConvergenceChecker() {
/* 133 */     return this.optimizer.getConvergenceChecker();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PointVectorValuePair optimize(int maxEval, FUNC f, double[] target, double[] weights, double[] startPoint) {
/* 142 */     this.maxEvaluations = maxEval;
/* 143 */     RuntimeException lastException = null;
/* 144 */     this.optima = new PointVectorValuePair[this.starts];
/* 145 */     this.totalEvaluations = 0;
/*     */ 
/*     */     
/* 148 */     for (int i = 0; i < this.starts; i++) {
/*     */ 
/*     */       
/*     */       try {
/* 152 */         this.optima[i] = this.optimizer.optimize(maxEval - this.totalEvaluations, f, target, weights, (i == 0) ? startPoint : this.generator.nextVector());
/*     */       }
/* 154 */       catch (ConvergenceException oe) {
/* 155 */         this.optima[i] = null;
/* 156 */       } catch (RuntimeException mue) {
/* 157 */         lastException = mue;
/* 158 */         this.optima[i] = null;
/*     */       } 
/*     */ 
/*     */       
/* 162 */       this.totalEvaluations += this.optimizer.getEvaluations();
/*     */     } 
/*     */     
/* 165 */     sortPairs(target, weights);
/*     */     
/* 167 */     if (this.optima[0] == null) {
/* 168 */       throw lastException;
/*     */     }
/*     */ 
/*     */     
/* 172 */     return this.optima[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sortPairs(final double[] target, final double[] weights) {
/* 183 */     Arrays.sort(this.optima, new Comparator<PointVectorValuePair>()
/*     */         {
/*     */           public int compare(PointVectorValuePair o1, PointVectorValuePair o2)
/*     */           {
/* 187 */             if (o1 == null)
/* 188 */               return (o2 == null) ? 0 : 1; 
/* 189 */             if (o2 == null) {
/* 190 */               return -1;
/*     */             }
/* 192 */             return Double.compare(weightedResidual(o1), weightedResidual(o2));
/*     */           }
/*     */           private double weightedResidual(PointVectorValuePair pv) {
/* 195 */             double[] value = pv.getValueRef();
/* 196 */             double sum = 0.0D;
/* 197 */             for (int i = 0; i < value.length; i++) {
/* 198 */               double ri = value[i] - target[i];
/* 199 */               sum += weights[i] * ri * ri;
/*     */             } 
/* 201 */             return sum;
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/BaseMultivariateVectorMultiStartOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */