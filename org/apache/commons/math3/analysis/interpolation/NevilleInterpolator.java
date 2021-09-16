/*    */ package org.apache.commons.math3.analysis.interpolation;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*    */ import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionLagrangeForm;
/*    */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*    */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*    */ import org.apache.commons.math3.exception.NonMonotonicSequenceException;
/*    */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NevilleInterpolator
/*    */   implements UnivariateInterpolator, Serializable
/*    */ {
/*    */   static final long serialVersionUID = 3003707660147873733L;
/*    */   
/*    */   public PolynomialFunctionLagrangeForm interpolate(double[] x, double[] y) throws DimensionMismatchException, NumberIsTooSmallException, NonMonotonicSequenceException {
/* 58 */     return new PolynomialFunctionLagrangeForm(x, y);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/interpolation/NevilleInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */