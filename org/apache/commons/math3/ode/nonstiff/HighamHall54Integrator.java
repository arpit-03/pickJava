/*     */ package org.apache.commons.math3.ode.nonstiff;
/*     */ 
/*     */ import org.apache.commons.math3.util.FastMath;
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
/*     */ public class HighamHall54Integrator
/*     */   extends EmbeddedRungeKuttaIntegrator
/*     */ {
/*     */   private static final String METHOD_NAME = "Higham-Hall 5(4)";
/*  42 */   private static final double[] STATIC_C = new double[] { 0.2222222222222222D, 0.3333333333333333D, 0.5D, 0.6D, 1.0D, 1.0D };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   private static final double[][] STATIC_A = new double[][] { { 0.2222222222222222D }, { 0.08333333333333333D, 0.25D }, { 0.125D, 0.0D, 0.375D }, { 0.182D, -0.27D, 0.624D, 0.064D }, { -0.55D, 1.35D, 2.4D, -7.2D, 5.0D }, { 0.08333333333333333D, 0.0D, 0.84375D, -1.3333333333333333D, 1.3020833333333333D, 0.10416666666666667D } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   private static final double[] STATIC_B = new double[] { 0.08333333333333333D, 0.0D, 0.84375D, -1.3333333333333333D, 1.3020833333333333D, 0.10416666666666667D, 0.0D };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   private static final double[] STATIC_E = new double[] { -0.05D, 0.0D, 0.50625D, -1.2D, 0.78125D, 0.0625D, -0.1D };
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
/*     */   public HighamHall54Integrator(double minStep, double maxStep, double scalAbsoluteTolerance, double scalRelativeTolerance) {
/*  80 */     super("Higham-Hall 5(4)", false, STATIC_C, STATIC_A, STATIC_B, new HighamHall54StepInterpolator(), minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
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
/*     */   public HighamHall54Integrator(double minStep, double maxStep, double[] vecAbsoluteTolerance, double[] vecRelativeTolerance) {
/*  98 */     super("Higham-Hall 5(4)", false, STATIC_C, STATIC_A, STATIC_B, new HighamHall54StepInterpolator(), minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOrder() {
/* 105 */     return 5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double estimateError(double[][] yDotK, double[] y0, double[] y1, double h) {
/* 114 */     double error = 0.0D;
/*     */     
/* 116 */     for (int j = 0; j < this.mainSetDimension; j++) {
/* 117 */       double errSum = STATIC_E[0] * yDotK[0][j];
/* 118 */       for (int l = 1; l < STATIC_E.length; l++) {
/* 119 */         errSum += STATIC_E[l] * yDotK[l][j];
/*     */       }
/*     */       
/* 122 */       double yScale = FastMath.max(FastMath.abs(y0[j]), FastMath.abs(y1[j]));
/* 123 */       double tol = (this.vecAbsoluteTolerance == null) ? (this.scalAbsoluteTolerance + this.scalRelativeTolerance * yScale) : (this.vecAbsoluteTolerance[j] + this.vecRelativeTolerance[j] * yScale);
/*     */ 
/*     */       
/* 126 */       double ratio = h * errSum / tol;
/* 127 */       error += ratio * ratio;
/*     */     } 
/*     */ 
/*     */     
/* 131 */     return FastMath.sqrt(error / this.mainSetDimension);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/HighamHall54Integrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */