/*     */ package org.apache.commons.math3.ode;
/*     */ 
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NoBracketingException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.linear.Array2DRowRealMatrix;
/*     */ import org.apache.commons.math3.ode.nonstiff.AdaptiveStepsizeIntegrator;
/*     */ import org.apache.commons.math3.ode.nonstiff.DormandPrince853Integrator;
/*     */ import org.apache.commons.math3.ode.sampling.StepHandler;
/*     */ import org.apache.commons.math3.ode.sampling.StepInterpolator;
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
/*     */ public abstract class MultistepIntegrator
/*     */   extends AdaptiveStepsizeIntegrator
/*     */ {
/*     */   protected double[] scaled;
/*     */   protected Array2DRowRealMatrix nordsieck;
/*     */   private FirstOrderIntegrator starter;
/*     */   private final int nSteps;
/*     */   private double exp;
/*     */   private double safety;
/*     */   private double minReduction;
/*     */   private double maxGrowth;
/*     */   
/*     */   protected MultistepIntegrator(String name, int nSteps, int order, double minStep, double maxStep, double scalAbsoluteTolerance, double scalRelativeTolerance) throws NumberIsTooSmallException {
/* 118 */     super(name, minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
/*     */     
/* 120 */     if (nSteps < 2) {
/* 121 */       throw new NumberIsTooSmallException(LocalizedFormats.INTEGRATION_METHOD_NEEDS_AT_LEAST_TWO_PREVIOUS_POINTS, Integer.valueOf(nSteps), Integer.valueOf(2), true);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 126 */     this.starter = (FirstOrderIntegrator)new DormandPrince853Integrator(minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
/*     */ 
/*     */     
/* 129 */     this.nSteps = nSteps;
/*     */     
/* 131 */     this.exp = -1.0D / order;
/*     */ 
/*     */     
/* 134 */     setSafety(0.9D);
/* 135 */     setMinReduction(0.2D);
/* 136 */     setMaxGrowth(FastMath.pow(2.0D, -this.exp));
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
/*     */ 
/*     */   
/*     */   protected MultistepIntegrator(String name, int nSteps, int order, double minStep, double maxStep, double[] vecAbsoluteTolerance, double[] vecRelativeTolerance) {
/* 164 */     super(name, minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
/* 165 */     this.starter = (FirstOrderIntegrator)new DormandPrince853Integrator(minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
/*     */ 
/*     */     
/* 168 */     this.nSteps = nSteps;
/*     */     
/* 170 */     this.exp = -1.0D / order;
/*     */ 
/*     */     
/* 173 */     setSafety(0.9D);
/* 174 */     setMinReduction(0.2D);
/* 175 */     setMaxGrowth(FastMath.pow(2.0D, -this.exp));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ODEIntegrator getStarterIntegrator() {
/* 184 */     return this.starter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStarterIntegrator(FirstOrderIntegrator starterIntegrator) {
/* 195 */     this.starter = starterIntegrator;
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
/*     */ 
/*     */   
/*     */   protected void start(double t0, double[] y0, double t) throws DimensionMismatchException, NumberIsTooSmallException, MaxCountExceededException, NoBracketingException {
/* 223 */     this.starter.clearEventHandlers();
/* 224 */     this.starter.clearStepHandlers();
/*     */ 
/*     */     
/* 227 */     this.starter.addStepHandler(new NordsieckInitializer((this.nSteps + 3) / 2, y0.length));
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 232 */       if (this.starter instanceof AbstractIntegrator) {
/* 233 */         ((AbstractIntegrator)this.starter).integrate(getExpandable(), t);
/*     */       } else {
/* 235 */         this.starter.integrate(new FirstOrderDifferentialEquations()
/*     */             {
/*     */               public int getDimension()
/*     */               {
/* 239 */                 return MultistepIntegrator.this.getExpandable().getTotalDimension();
/*     */               }
/*     */ 
/*     */               
/*     */               public void computeDerivatives(double t, double[] y, double[] yDot) {
/* 244 */                 MultistepIntegrator.this.getExpandable().computeDerivatives(t, y, yDot);
/*     */               }
/*     */             }t0, y0, t, new double[y0.length]);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 251 */       throw new MathIllegalStateException(LocalizedFormats.MULTISTEP_STARTER_STOPPED_EARLY, new Object[0]);
/*     */     }
/* 253 */     catch (InitializationCompletedMarkerException icme) {
/*     */ 
/*     */ 
/*     */       
/* 257 */       getCounter().increment(this.starter.getEvaluations());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 262 */       this.starter.clearStepHandlers();
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Array2DRowRealMatrix initializeHighOrderDerivatives(double paramDouble, double[] paramArrayOfdouble, double[][] paramArrayOfdouble1, double[][] paramArrayOfdouble2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMinReduction() {
/* 282 */     return this.minReduction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMinReduction(double minReduction) {
/* 289 */     this.minReduction = minReduction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMaxGrowth() {
/* 296 */     return this.maxGrowth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxGrowth(double maxGrowth) {
/* 303 */     this.maxGrowth = maxGrowth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSafety() {
/* 310 */     return this.safety;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSafety(double safety) {
/* 317 */     this.safety = safety;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNSteps() {
/* 324 */     return this.nSteps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double computeStepGrowShrinkFactor(double error) {
/* 332 */     return FastMath.min(this.maxGrowth, FastMath.max(this.minReduction, this.safety * FastMath.pow(error, this.exp)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static interface NordsieckTransformer
/*     */   {
/*     */     Array2DRowRealMatrix initializeHighOrderDerivatives(double param1Double, double[] param1ArrayOfdouble, double[][] param1ArrayOfdouble1, double[][] param1ArrayOfdouble2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class NordsieckInitializer
/*     */     implements StepHandler
/*     */   {
/*     */     private int count;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final double[] t;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final double[][] y;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final double[][] yDot;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     NordsieckInitializer(int nbStartPoints, int n) {
/* 373 */       this.count = 0;
/* 374 */       this.t = new double[nbStartPoints];
/* 375 */       this.y = new double[nbStartPoints][n];
/* 376 */       this.yDot = new double[nbStartPoints][n];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void handleStep(StepInterpolator interpolator, boolean isLast) throws MaxCountExceededException {
/* 383 */       double prev = interpolator.getPreviousTime();
/* 384 */       double curr = interpolator.getCurrentTime();
/*     */       
/* 386 */       if (this.count == 0) {
/*     */         
/* 388 */         interpolator.setInterpolatedTime(prev);
/* 389 */         this.t[0] = prev;
/* 390 */         ExpandableStatefulODE expandableStatefulODE = MultistepIntegrator.this.getExpandable();
/* 391 */         EquationsMapper equationsMapper = expandableStatefulODE.getPrimaryMapper();
/* 392 */         equationsMapper.insertEquationData(interpolator.getInterpolatedState(), this.y[this.count]);
/* 393 */         equationsMapper.insertEquationData(interpolator.getInterpolatedDerivatives(), this.yDot[this.count]);
/* 394 */         int i = 0;
/* 395 */         for (EquationsMapper secondary : expandableStatefulODE.getSecondaryMappers()) {
/* 396 */           secondary.insertEquationData(interpolator.getInterpolatedSecondaryState(i), this.y[this.count]);
/* 397 */           secondary.insertEquationData(interpolator.getInterpolatedSecondaryDerivatives(i), this.yDot[this.count]);
/* 398 */           i++;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 403 */       this.count++;
/* 404 */       interpolator.setInterpolatedTime(curr);
/* 405 */       this.t[this.count] = curr;
/*     */       
/* 407 */       ExpandableStatefulODE expandable = MultistepIntegrator.this.getExpandable();
/* 408 */       EquationsMapper primary = expandable.getPrimaryMapper();
/* 409 */       primary.insertEquationData(interpolator.getInterpolatedState(), this.y[this.count]);
/* 410 */       primary.insertEquationData(interpolator.getInterpolatedDerivatives(), this.yDot[this.count]);
/* 411 */       int index = 0;
/* 412 */       for (EquationsMapper secondary : expandable.getSecondaryMappers()) {
/* 413 */         secondary.insertEquationData(interpolator.getInterpolatedSecondaryState(index), this.y[this.count]);
/* 414 */         secondary.insertEquationData(interpolator.getInterpolatedSecondaryDerivatives(index), this.yDot[this.count]);
/* 415 */         index++;
/*     */       } 
/*     */       
/* 418 */       if (this.count == this.t.length - 1) {
/*     */ 
/*     */         
/* 421 */         MultistepIntegrator.this.stepStart = this.t[0];
/* 422 */         MultistepIntegrator.this.stepSize = (this.t[this.t.length - 1] - this.t[0]) / (this.t.length - 1);
/*     */ 
/*     */         
/* 425 */         MultistepIntegrator.this.scaled = (double[])this.yDot[0].clone();
/* 426 */         for (int j = 0; j < MultistepIntegrator.this.scaled.length; j++) {
/* 427 */           MultistepIntegrator.this.scaled[j] = MultistepIntegrator.this.scaled[j] * MultistepIntegrator.this.stepSize;
/*     */         }
/*     */ 
/*     */         
/* 431 */         MultistepIntegrator.this.nordsieck = MultistepIntegrator.this.initializeHighOrderDerivatives(MultistepIntegrator.this.stepSize, this.t, this.y, this.yDot);
/*     */ 
/*     */         
/* 434 */         throw new MultistepIntegrator.InitializationCompletedMarkerException();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void init(double t0, double[] y0, double time) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class InitializationCompletedMarkerException
/*     */     extends RuntimeException
/*     */   {
/*     */     private static final long serialVersionUID = -1914085471038046418L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     InitializationCompletedMarkerException() {
/* 456 */       super((Throwable)null);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/MultistepIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */