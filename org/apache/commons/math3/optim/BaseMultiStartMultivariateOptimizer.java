/*     */ package org.apache.commons.math3.optim;
/*     */ 
/*     */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.TooManyEvaluationsException;
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
/*     */ public abstract class BaseMultiStartMultivariateOptimizer<PAIR>
/*     */   extends BaseMultivariateOptimizer<PAIR>
/*     */ {
/*     */   private final BaseMultivariateOptimizer<PAIR> optimizer;
/*     */   private int totalEvaluations;
/*     */   private int starts;
/*     */   private RandomVectorGenerator generator;
/*     */   private OptimizationData[] optimData;
/*  53 */   private int maxEvalIndex = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   private int initialGuessIndex = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseMultiStartMultivariateOptimizer(BaseMultivariateOptimizer<PAIR> optimizer, int starts, RandomVectorGenerator generator) {
/*  81 */     super(optimizer.getConvergenceChecker());
/*     */     
/*  83 */     if (starts < 1) {
/*  84 */       throw new NotStrictlyPositiveException(Integer.valueOf(starts));
/*     */     }
/*     */     
/*  87 */     this.optimizer = optimizer;
/*  88 */     this.starts = starts;
/*  89 */     this.generator = generator;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEvaluations() {
/*  95 */     return this.totalEvaluations;
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
/*     */   public abstract PAIR[] getOptima();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PAIR optimize(OptimizationData... optData) {
/* 132 */     this.optimData = optData;
/*     */     
/* 134 */     return super.optimize(optData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PAIR doOptimize() {
/* 145 */     for (int i = 0; i < this.optimData.length; i++) {
/* 146 */       if (this.optimData[i] instanceof MaxEval) {
/* 147 */         this.optimData[i] = null;
/* 148 */         this.maxEvalIndex = i;
/*     */       } 
/* 150 */       if (this.optimData[i] instanceof InitialGuess) {
/* 151 */         this.optimData[i] = null;
/* 152 */         this.initialGuessIndex = i;
/*     */       } 
/*     */     } 
/*     */     
/* 156 */     if (this.maxEvalIndex == -1) {
/* 157 */       throw new MathIllegalStateException();
/*     */     }
/* 159 */     if (this.initialGuessIndex == -1) {
/* 160 */       throw new MathIllegalStateException();
/*     */     }
/*     */     
/* 163 */     RuntimeException lastException = null;
/* 164 */     this.totalEvaluations = 0;
/* 165 */     clear();
/*     */     
/* 167 */     int maxEval = getMaxEvaluations();
/* 168 */     double[] min = getLowerBound();
/* 169 */     double[] max = getUpperBound();
/* 170 */     double[] startPoint = getStartPoint();
/*     */ 
/*     */     
/* 173 */     for (int j = 0; j < this.starts; j++) {
/*     */ 
/*     */       
/*     */       try {
/* 177 */         this.optimData[this.maxEvalIndex] = new MaxEval(maxEval - this.totalEvaluations);
/*     */         
/* 179 */         double[] s = null;
/* 180 */         if (j == 0) {
/* 181 */           s = startPoint;
/*     */         } else {
/* 183 */           int attempts = 0;
/* 184 */           while (s == null) {
/* 185 */             if (attempts++ >= getMaxEvaluations()) {
/* 186 */               throw new TooManyEvaluationsException(Integer.valueOf(getMaxEvaluations()));
/*     */             }
/* 188 */             s = this.generator.nextVector();
/* 189 */             for (int k = 0; s != null && k < s.length; k++) {
/* 190 */               if ((min != null && s[k] < min[k]) || (max != null && s[k] > max[k]))
/*     */               {
/* 192 */                 s = null;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/* 197 */         this.optimData[this.initialGuessIndex] = new InitialGuess(s);
/*     */         
/* 199 */         PAIR result = this.optimizer.optimize(this.optimData);
/* 200 */         store(result);
/* 201 */       } catch (RuntimeException mue) {
/* 202 */         lastException = mue;
/*     */       } 
/*     */ 
/*     */       
/* 206 */       this.totalEvaluations += this.optimizer.getEvaluations();
/*     */     } 
/*     */     
/* 209 */     PAIR[] optima = getOptima();
/* 210 */     if (optima.length == 0)
/*     */     {
/* 212 */       throw lastException;
/*     */     }
/*     */ 
/*     */     
/* 216 */     return optima[0];
/*     */   }
/*     */   
/*     */   protected abstract void store(PAIR paramPAIR);
/*     */   
/*     */   protected abstract void clear();
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/BaseMultiStartMultivariateOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */