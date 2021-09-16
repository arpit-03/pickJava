/*    */ package org.apache.commons.math3.ode;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ class ParameterConfiguration
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 2247518849090889379L;
/*    */   private String parameterName;
/*    */   private double hP;
/*    */   
/*    */   ParameterConfiguration(String parameterName, double hP) {
/* 42 */     this.parameterName = parameterName;
/* 43 */     this.hP = hP;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getParameterName() {
/* 50 */     return this.parameterName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getHP() {
/* 57 */     return this.hP;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setHP(double hParam) {
/* 64 */     this.hP = hParam;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/ParameterConfiguration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */