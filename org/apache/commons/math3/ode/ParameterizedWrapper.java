/*    */ package org.apache.commons.math3.ode;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*    */ import org.apache.commons.math3.exception.MaxCountExceededException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class ParameterizedWrapper
/*    */   implements ParameterizedODE
/*    */ {
/*    */   private final FirstOrderDifferentialEquations fode;
/*    */   
/*    */   ParameterizedWrapper(FirstOrderDifferentialEquations ode) {
/* 39 */     this.fode = ode;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDimension() {
/* 46 */     return this.fode.getDimension();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void computeDerivatives(double t, double[] y, double[] yDot) throws MaxCountExceededException, DimensionMismatchException {
/* 58 */     this.fode.computeDerivatives(t, y, yDot);
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getParametersNames() {
/* 63 */     return new ArrayList<String>();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSupported(String name) {
/* 68 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double getParameter(String name) throws UnknownParameterException {
/* 74 */     if (!isSupported(name)) {
/* 75 */       throw new UnknownParameterException(name);
/*    */     }
/* 77 */     return Double.NaN;
/*    */   }
/*    */   
/*    */   public void setParameter(String name, double value) {}
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/ParameterizedWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */