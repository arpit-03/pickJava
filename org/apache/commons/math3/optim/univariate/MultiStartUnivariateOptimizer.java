/*     */ package org.apache.commons.math3.optim.univariate;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.TooManyEvaluationsException;
/*     */ import org.apache.commons.math3.exception.TooManyIterationsException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.optim.MaxEval;
/*     */ import org.apache.commons.math3.optim.OptimizationData;
/*     */ import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
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
/*     */ public class MultiStartUnivariateOptimizer
/*     */   extends UnivariateOptimizer
/*     */ {
/*     */   private final UnivariateOptimizer optimizer;
/*     */   private int totalEvaluations;
/*     */   private int starts;
/*     */   private RandomGenerator generator;
/*     */   private UnivariatePointValuePair[] optima;
/*     */   private OptimizationData[] optimData;
/*  58 */   private int maxEvalIndex = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   private int searchIntervalIndex = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MultiStartUnivariateOptimizer(UnivariateOptimizer optimizer, int starts, RandomGenerator generator) {
/*  78 */     super(optimizer.getConvergenceChecker());
/*     */     
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
/*     */   public int getEvaluations() {
/*  92 */     return this.totalEvaluations;
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
/*     */   public UnivariatePointValuePair[] getOptima() {
/* 118 */     if (this.optima == null) {
/* 119 */       throw new MathIllegalStateException(LocalizedFormats.NO_OPTIMUM_COMPUTED_YET, new Object[0]);
/*     */     }
/* 121 */     return (UnivariatePointValuePair[])this.optima.clone();
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
/*     */   public UnivariatePointValuePair optimize(OptimizationData... optData) {
/* 133 */     this.optimData = optData;
/*     */     
/* 135 */     return super.optimize(optData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected UnivariatePointValuePair doOptimize() {
/* 146 */     for (int i = 0; i < this.optimData.length; i++) {
/* 147 */       if (this.optimData[i] instanceof MaxEval) {
/* 148 */         this.optimData[i] = null;
/* 149 */         this.maxEvalIndex = i;
/*     */       
/*     */       }
/* 152 */       else if (this.optimData[i] instanceof SearchInterval) {
/* 153 */         this.optimData[i] = null;
/* 154 */         this.searchIntervalIndex = i;
/*     */       } 
/*     */     } 
/*     */     
/* 158 */     if (this.maxEvalIndex == -1) {
/* 159 */       throw new MathIllegalStateException();
/*     */     }
/* 161 */     if (this.searchIntervalIndex == -1) {
/* 162 */       throw new MathIllegalStateException();
/*     */     }
/*     */     
/* 165 */     RuntimeException lastException = null;
/* 166 */     this.optima = new UnivariatePointValuePair[this.starts];
/* 167 */     this.totalEvaluations = 0;
/*     */     
/* 169 */     int maxEval = getMaxEvaluations();
/* 170 */     double min = getMin();
/* 171 */     double max = getMax();
/* 172 */     double startValue = getStartValue();
/*     */ 
/*     */     
/* 175 */     for (int j = 0; j < this.starts; j++) {
/*     */ 
/*     */       
/*     */       try {
/* 179 */         this.optimData[this.maxEvalIndex] = (OptimizationData)new MaxEval(maxEval - this.totalEvaluations);
/*     */         
/* 181 */         double s = (j == 0) ? startValue : (min + this.generator.nextDouble() * (max - min));
/*     */ 
/*     */         
/* 184 */         this.optimData[this.searchIntervalIndex] = new SearchInterval(min, max, s);
/*     */         
/* 186 */         this.optima[j] = this.optimizer.optimize(this.optimData);
/* 187 */       } catch (RuntimeException mue) {
/* 188 */         lastException = mue;
/* 189 */         this.optima[j] = null;
/*     */       } 
/*     */ 
/*     */       
/* 193 */       this.totalEvaluations += this.optimizer.getEvaluations();
/*     */     } 
/*     */     
/* 196 */     sortPairs(getGoalType());
/*     */     
/* 198 */     if (this.optima[0] == null) {
/* 199 */       throw lastException;
/*     */     }
/*     */ 
/*     */     
/* 203 */     return this.optima[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sortPairs(final GoalType goal) {
/* 212 */     Arrays.sort(this.optima, new Comparator<UnivariatePointValuePair>()
/*     */         {
/*     */           public int compare(UnivariatePointValuePair o1, UnivariatePointValuePair o2)
/*     */           {
/* 216 */             if (o1 == null)
/* 217 */               return (o2 == null) ? 0 : 1; 
/* 218 */             if (o2 == null) {
/* 219 */               return -1;
/*     */             }
/* 221 */             double v1 = o1.getValue();
/* 222 */             double v2 = o2.getValue();
/* 223 */             return (goal == GoalType.MINIMIZE) ? Double.compare(v1, v2) : Double.compare(v2, v1);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/univariate/MultiStartUnivariateOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */