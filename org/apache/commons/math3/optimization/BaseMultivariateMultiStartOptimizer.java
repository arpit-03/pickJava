/*     */ package org.apache.commons.math3.optimization;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import org.apache.commons.math3.analysis.MultivariateFunction;
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
/*     */ 
/*     */ @Deprecated
/*     */ public class BaseMultivariateMultiStartOptimizer<FUNC extends MultivariateFunction>
/*     */   implements BaseMultivariateOptimizer<FUNC>
/*     */ {
/*     */   private final BaseMultivariateOptimizer<FUNC> optimizer;
/*     */   private int maxEvaluations;
/*     */   private int totalEvaluations;
/*     */   private int starts;
/*     */   private RandomVectorGenerator generator;
/*     */   private PointValuePair[] optima;
/*     */   
/*     */   protected BaseMultivariateMultiStartOptimizer(BaseMultivariateOptimizer<FUNC> optimizer, int starts, RandomVectorGenerator generator) {
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
/*     */   public PointValuePair[] getOptima() {
/* 114 */     if (this.optima == null) {
/* 115 */       throw new MathIllegalStateException(LocalizedFormats.NO_OPTIMUM_COMPUTED_YET, new Object[0]);
/*     */     }
/* 117 */     return (PointValuePair[])this.optima.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxEvaluations() {
/* 122 */     return this.maxEvaluations;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEvaluations() {
/* 127 */     return this.totalEvaluations;
/*     */   }
/*     */ 
/*     */   
/*     */   public ConvergenceChecker<PointValuePair> getConvergenceChecker() {
/* 132 */     return this.optimizer.getConvergenceChecker();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PointValuePair optimize(int maxEval, FUNC f, GoalType goal, double[] startPoint) {
/* 141 */     this.maxEvaluations = maxEval;
/* 142 */     RuntimeException lastException = null;
/* 143 */     this.optima = new PointValuePair[this.starts];
/* 144 */     this.totalEvaluations = 0;
/*     */ 
/*     */     
/* 147 */     for (int i = 0; i < this.starts; i++) {
/*     */       
/*     */       try {
/* 150 */         this.optima[i] = this.optimizer.optimize(maxEval - this.totalEvaluations, f, goal, (i == 0) ? startPoint : this.generator.nextVector());
/*     */       }
/* 152 */       catch (RuntimeException mue) {
/* 153 */         lastException = mue;
/* 154 */         this.optima[i] = null;
/*     */       } 
/*     */ 
/*     */       
/* 158 */       this.totalEvaluations += this.optimizer.getEvaluations();
/*     */     } 
/*     */     
/* 161 */     sortPairs(goal);
/*     */     
/* 163 */     if (this.optima[0] == null) {
/* 164 */       throw lastException;
/*     */     }
/*     */ 
/*     */     
/* 168 */     return this.optima[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sortPairs(final GoalType goal) {
/* 177 */     Arrays.sort(this.optima, new Comparator<PointValuePair>()
/*     */         {
/*     */           public int compare(PointValuePair o1, PointValuePair o2)
/*     */           {
/* 181 */             if (o1 == null)
/* 182 */               return (o2 == null) ? 0 : 1; 
/* 183 */             if (o2 == null) {
/* 184 */               return -1;
/*     */             }
/* 186 */             double v1 = ((Double)o1.getValue()).doubleValue();
/* 187 */             double v2 = ((Double)o2.getValue()).doubleValue();
/* 188 */             return (goal == GoalType.MINIMIZE) ? Double.compare(v1, v2) : Double.compare(v2, v1);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/BaseMultivariateMultiStartOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */