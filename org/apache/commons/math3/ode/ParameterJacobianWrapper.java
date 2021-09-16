/*    */ package org.apache.commons.math3.ode;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class ParameterJacobianWrapper
/*    */   implements ParameterJacobianProvider
/*    */ {
/*    */   private final FirstOrderDifferentialEquations fode;
/*    */   private final ParameterizedODE pode;
/*    */   private final Map<String, Double> hParam;
/*    */   
/*    */   ParameterJacobianWrapper(FirstOrderDifferentialEquations fode, ParameterizedODE pode, ParameterConfiguration[] paramsAndSteps) {
/* 52 */     this.fode = fode;
/* 53 */     this.pode = pode;
/* 54 */     this.hParam = new HashMap<String, Double>();
/*    */ 
/*    */     
/* 57 */     for (ParameterConfiguration param : paramsAndSteps) {
/* 58 */       String name = param.getParameterName();
/* 59 */       if (pode.isSupported(name)) {
/* 60 */         this.hParam.put(name, Double.valueOf(param.getHP()));
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getParametersNames() {
/* 67 */     return this.pode.getParametersNames();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSupported(String name) {
/* 72 */     return this.pode.isSupported(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void computeParameterJacobian(double t, double[] y, double[] yDot, String paramName, double[] dFdP) throws DimensionMismatchException, MaxCountExceededException {
/* 80 */     int n = this.fode.getDimension();
/* 81 */     if (this.pode.isSupported(paramName)) {
/* 82 */       double[] tmpDot = new double[n];
/*    */ 
/*    */       
/* 85 */       double p = this.pode.getParameter(paramName);
/* 86 */       double hP = ((Double)this.hParam.get(paramName)).doubleValue();
/* 87 */       this.pode.setParameter(paramName, p + hP);
/* 88 */       this.fode.computeDerivatives(t, y, tmpDot);
/* 89 */       for (int i = 0; i < n; i++) {
/* 90 */         dFdP[i] = (tmpDot[i] - yDot[i]) / hP;
/*    */       }
/* 92 */       this.pode.setParameter(paramName, p);
/*    */     } else {
/* 94 */       Arrays.fill(dFdP, 0, n, 0.0D);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/ParameterJacobianWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */