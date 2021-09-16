/*     */ package org.apache.commons.math3.optimization.univariate;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.optimization.ConvergenceChecker;
/*     */ import org.apache.commons.math3.optimization.GoalType;
/*     */ import org.apache.commons.math3.random.RandomGenerator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class UnivariateMultiStartOptimizer<FUNC extends UnivariateFunction>
/*     */   implements BaseUnivariateOptimizer<FUNC>
/*     */ {
/*     */   private final BaseUnivariateOptimizer<FUNC> optimizer;
/*     */   private int maxEvaluations;
/*     */   private int totalEvaluations;
/*     */   private int starts;
/*     */   private RandomGenerator generator;
/*     */   private UnivariatePointValuePair[] optima;
/*     */   
/*     */   public UnivariateMultiStartOptimizer(BaseUnivariateOptimizer<FUNC> optimizer, int starts, RandomGenerator generator) {
/*  76 */     if (optimizer == null || generator == null)
/*     */     {
/*  78 */       throw new NullArgumentException();
/*     */     }
/*  80 */     if (starts < 1) {
/*  81 */       throw new NotStrictlyPositiveException(Integer.valueOf(starts));
/*     */     }
/*     */     
/*  84 */     this.optimizer = optimizer;
/*  85 */     this.starts = starts;
/*  86 */     this.generator = generator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConvergenceChecker<UnivariatePointValuePair> getConvergenceChecker() {
/*  93 */     return this.optimizer.getConvergenceChecker();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxEvaluations() {
/*  98 */     return this.maxEvaluations;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEvaluations() {
/* 103 */     return this.totalEvaluations;
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
/*     */   public UnivariatePointValuePair[] getOptima() {
/* 134 */     if (this.optima == null) {
/* 135 */       throw new MathIllegalStateException(LocalizedFormats.NO_OPTIMUM_COMPUTED_YET, new Object[0]);
/*     */     }
/* 137 */     return (UnivariatePointValuePair[])this.optima.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnivariatePointValuePair optimize(int maxEval, FUNC f, GoalType goal, double min, double max) {
/* 144 */     return optimize(maxEval, f, goal, min, max, min + 0.5D * (max - min));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnivariatePointValuePair optimize(int maxEval, FUNC f, GoalType goal, double min, double max, double startValue) {
/* 152 */     RuntimeException lastException = null;
/* 153 */     this.optima = new UnivariatePointValuePair[this.starts];
/* 154 */     this.totalEvaluations = 0;
/*     */ 
/*     */     
/* 157 */     for (int i = 0; i < this.starts; i++) {
/*     */       
/*     */       try {
/* 160 */         double s = (i == 0) ? startValue : (min + this.generator.nextDouble() * (max - min));
/* 161 */         this.optima[i] = this.optimizer.optimize(maxEval - this.totalEvaluations, f, goal, min, max, s);
/* 162 */       } catch (RuntimeException mue) {
/* 163 */         lastException = mue;
/* 164 */         this.optima[i] = null;
/*     */       } 
/*     */ 
/*     */       
/* 168 */       this.totalEvaluations += this.optimizer.getEvaluations();
/*     */     } 
/*     */     
/* 171 */     sortPairs(goal);
/*     */     
/* 173 */     if (this.optima[0] == null) {
/* 174 */       throw lastException;
/*     */     }
/*     */ 
/*     */     
/* 178 */     return this.optima[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sortPairs(final GoalType goal) {
/* 187 */     Arrays.sort(this.optima, new Comparator<UnivariatePointValuePair>()
/*     */         {
/*     */           public int compare(UnivariatePointValuePair o1, UnivariatePointValuePair o2)
/*     */           {
/* 191 */             if (o1 == null)
/* 192 */               return (o2 == null) ? 0 : 1; 
/* 193 */             if (o2 == null) {
/* 194 */               return -1;
/*     */             }
/* 196 */             double v1 = o1.getValue();
/* 197 */             double v2 = o2.getValue();
/* 198 */             return (goal == GoalType.MINIMIZE) ? Double.compare(v1, v2) : Double.compare(v2, v1);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/univariate/UnivariateMultiStartOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */