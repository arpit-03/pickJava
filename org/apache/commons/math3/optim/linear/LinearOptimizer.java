/*     */ package org.apache.commons.math3.optim.linear;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import org.apache.commons.math3.exception.TooManyIterationsException;
/*     */ import org.apache.commons.math3.optim.OptimizationData;
/*     */ import org.apache.commons.math3.optim.PointValuePair;
/*     */ import org.apache.commons.math3.optim.nonlinear.scalar.MultivariateOptimizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class LinearOptimizer
/*     */   extends MultivariateOptimizer
/*     */ {
/*     */   private LinearObjectiveFunction function;
/*     */   private Collection<LinearConstraint> linearConstraints;
/*     */   private boolean nonNegative;
/*     */   
/*     */   protected LinearOptimizer() {
/*  51 */     super(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isRestrictedToNonNegative() {
/*  58 */     return this.nonNegative;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected LinearObjectiveFunction getFunction() {
/*  65 */     return this.function;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Collection<LinearConstraint> getConstraints() {
/*  72 */     return Collections.unmodifiableCollection(this.linearConstraints);
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
/*     */   public PointValuePair optimize(OptimizationData... optData) throws TooManyIterationsException {
/*  94 */     return super.optimize(optData);
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
/*     */   protected void parseOptimizationData(OptimizationData... optData) {
/* 112 */     super.parseOptimizationData(optData);
/*     */ 
/*     */ 
/*     */     
/* 116 */     for (OptimizationData data : optData) {
/* 117 */       if (data instanceof LinearObjectiveFunction) {
/* 118 */         this.function = (LinearObjectiveFunction)data;
/*     */       
/*     */       }
/* 121 */       else if (data instanceof LinearConstraintSet) {
/* 122 */         this.linearConstraints = ((LinearConstraintSet)data).getConstraints();
/*     */       
/*     */       }
/* 125 */       else if (data instanceof NonNegativeConstraint) {
/* 126 */         this.nonNegative = ((NonNegativeConstraint)data).isRestrictedToNonNegative();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/linear/LinearOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */