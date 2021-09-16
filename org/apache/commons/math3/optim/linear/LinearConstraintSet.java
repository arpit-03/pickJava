/*    */ package org.apache.commons.math3.optim.linear;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Set;
/*    */ import org.apache.commons.math3.optim.OptimizationData;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LinearConstraintSet
/*    */   implements OptimizationData
/*    */ {
/* 33 */   private final Set<LinearConstraint> linearConstraints = new LinkedHashSet<LinearConstraint>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LinearConstraintSet(LinearConstraint... constraints) {
/* 41 */     for (LinearConstraint c : constraints) {
/* 42 */       this.linearConstraints.add(c);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LinearConstraintSet(Collection<LinearConstraint> constraints) {
/* 52 */     this.linearConstraints.addAll(constraints);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection<LinearConstraint> getConstraints() {
/* 61 */     return Collections.unmodifiableSet(this.linearConstraints);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/linear/LinearConstraintSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */