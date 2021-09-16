/*     */ package org.apache.commons.math3.optim.nonlinear.scalar;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.optim.BaseMultiStartMultivariateOptimizer;
/*     */ import org.apache.commons.math3.optim.PointValuePair;
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
/*     */ public class MultiStartMultivariateOptimizer
/*     */   extends BaseMultiStartMultivariateOptimizer<PointValuePair>
/*     */ {
/*     */   private final MultivariateOptimizer optimizer;
/*  43 */   private final List<PointValuePair> optima = new ArrayList<PointValuePair>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MultiStartMultivariateOptimizer(MultivariateOptimizer optimizer, int starts, RandomVectorGenerator generator) throws NullArgumentException, NotStrictlyPositiveException {
/*  62 */     super(optimizer, starts, generator);
/*  63 */     this.optimizer = optimizer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PointValuePair[] getOptima() {
/*  71 */     Collections.sort(this.optima, getPairComparator());
/*  72 */     return this.optima.<PointValuePair>toArray(new PointValuePair[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void store(PointValuePair optimum) {
/*  80 */     this.optima.add(optimum);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void clear() {
/*  88 */     this.optima.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Comparator<PointValuePair> getPairComparator() {
/*  95 */     return new Comparator<PointValuePair>()
/*     */       {
/*     */         public int compare(PointValuePair o1, PointValuePair o2)
/*     */         {
/*  99 */           if (o1 == null)
/* 100 */             return (o2 == null) ? 0 : 1; 
/* 101 */           if (o2 == null) {
/* 102 */             return -1;
/*     */           }
/* 104 */           double v1 = ((Double)o1.getValue()).doubleValue();
/* 105 */           double v2 = ((Double)o2.getValue()).doubleValue();
/* 106 */           return (MultiStartMultivariateOptimizer.this.optimizer.getGoalType() == GoalType.MINIMIZE) ? Double.compare(v1, v2) : Double.compare(v2, v1);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/nonlinear/scalar/MultiStartMultivariateOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */