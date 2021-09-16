/*     */ package org.apache.commons.math3.ode.events;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.solvers.AllowedSolution;
/*     */ import org.apache.commons.math3.analysis.solvers.BracketedUnivariateSolver;
/*     */ import org.apache.commons.math3.analysis.solvers.PegasusSolver;
/*     */ import org.apache.commons.math3.analysis.solvers.UnivariateSolver;
/*     */ import org.apache.commons.math3.analysis.solvers.UnivariateSolverUtils;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NoBracketingException;
/*     */ import org.apache.commons.math3.ode.EquationsMapper;
/*     */ import org.apache.commons.math3.ode.ExpandableStatefulODE;
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
/*     */ public class EventState
/*     */ {
/*     */   private final EventHandler handler;
/*     */   private final double maxCheckInterval;
/*     */   private final double convergence;
/*     */   private final int maxIterationCount;
/*     */   private ExpandableStatefulODE expandable;
/*     */   private double t0;
/*     */   private double g0;
/*     */   private boolean g0Positive;
/*     */   private boolean pendingEvent;
/*     */   private double pendingEventTime;
/*     */   private double previousEventTime;
/*     */   private boolean forward;
/*     */   private boolean increasing;
/*     */   private EventHandler.Action nextAction;
/*     */   private final UnivariateSolver solver;
/*     */   
/*     */   public EventState(EventHandler handler, double maxCheckInterval, double convergence, int maxIterationCount, UnivariateSolver solver) {
/* 107 */     this.handler = handler;
/* 108 */     this.maxCheckInterval = maxCheckInterval;
/* 109 */     this.convergence = FastMath.abs(convergence);
/* 110 */     this.maxIterationCount = maxIterationCount;
/* 111 */     this.solver = solver;
/*     */ 
/*     */     
/* 114 */     this.expandable = null;
/* 115 */     this.t0 = Double.NaN;
/* 116 */     this.g0 = Double.NaN;
/* 117 */     this.g0Positive = true;
/* 118 */     this.pendingEvent = false;
/* 119 */     this.pendingEventTime = Double.NaN;
/* 120 */     this.previousEventTime = Double.NaN;
/* 121 */     this.increasing = true;
/* 122 */     this.nextAction = EventHandler.Action.CONTINUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EventHandler getEventHandler() {
/* 130 */     return this.handler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExpandable(ExpandableStatefulODE expandable) {
/* 137 */     this.expandable = expandable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMaxCheckInterval() {
/* 144 */     return this.maxCheckInterval;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getConvergence() {
/* 151 */     return this.convergence;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxIterationCount() {
/* 158 */     return this.maxIterationCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reinitializeBegin(StepInterpolator interpolator) throws MaxCountExceededException {
/* 169 */     this.t0 = interpolator.getPreviousTime();
/* 170 */     interpolator.setInterpolatedTime(this.t0);
/* 171 */     this.g0 = this.handler.g(this.t0, getCompleteState(interpolator));
/* 172 */     if (this.g0 == 0.0D) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 186 */       double epsilon = FastMath.max(this.solver.getAbsoluteAccuracy(), FastMath.abs(this.solver.getRelativeAccuracy() * this.t0));
/*     */       
/* 188 */       double tStart = this.t0 + 0.5D * epsilon;
/* 189 */       interpolator.setInterpolatedTime(tStart);
/* 190 */       this.g0 = this.handler.g(tStart, getCompleteState(interpolator));
/*     */     } 
/* 192 */     this.g0Positive = (this.g0 >= 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double[] getCompleteState(StepInterpolator interpolator) {
/* 202 */     double[] complete = new double[this.expandable.getTotalDimension()];
/*     */     
/* 204 */     this.expandable.getPrimaryMapper().insertEquationData(interpolator.getInterpolatedState(), complete);
/*     */     
/* 206 */     int index = 0;
/* 207 */     for (EquationsMapper secondary : this.expandable.getSecondaryMappers()) {
/* 208 */       secondary.insertEquationData(interpolator.getInterpolatedSecondaryState(index++), complete);
/*     */     }
/*     */ 
/*     */     
/* 212 */     return complete;
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
/*     */   public boolean evaluateStep(final StepInterpolator interpolator) throws MaxCountExceededException, NoBracketingException {
/*     */     try {
/* 228 */       this.forward = interpolator.isForward();
/* 229 */       double t1 = interpolator.getCurrentTime();
/* 230 */       double dt = t1 - this.t0;
/* 231 */       if (FastMath.abs(dt) < this.convergence)
/*     */       {
/* 233 */         return false;
/*     */       }
/* 235 */       int n = FastMath.max(1, (int)FastMath.ceil(FastMath.abs(dt) / this.maxCheckInterval));
/* 236 */       double h = dt / n;
/*     */       
/* 238 */       UnivariateFunction f = new UnivariateFunction()
/*     */         {
/*     */           public double value(double t) throws EventState.LocalMaxCountExceededException {
/*     */             try {
/* 242 */               interpolator.setInterpolatedTime(t);
/* 243 */               return EventState.this.handler.g(t, EventState.this.getCompleteState(interpolator));
/* 244 */             } catch (MaxCountExceededException mcee) {
/* 245 */               throw new EventState.LocalMaxCountExceededException(mcee);
/*     */             } 
/*     */           }
/*     */         };
/*     */       
/* 250 */       double ta = this.t0;
/* 251 */       double ga = this.g0;
/* 252 */       for (int i = 0; i < n; i++) {
/*     */ 
/*     */         
/* 255 */         double tb = (i == n - 1) ? t1 : (this.t0 + (i + 1) * h);
/* 256 */         interpolator.setInterpolatedTime(tb);
/* 257 */         double gb = this.handler.g(tb, getCompleteState(interpolator));
/*     */ 
/*     */         
/* 260 */         if ((this.g0Positive ^ ((gb >= 0.0D) ? 1 : 0)) != 0) {
/*     */           double root;
/*     */ 
/*     */           
/* 264 */           this.increasing = (gb >= ga);
/*     */ 
/*     */ 
/*     */           
/* 268 */           if (this.solver instanceof BracketedUnivariateSolver) {
/*     */             
/* 270 */             BracketedUnivariateSolver<UnivariateFunction> bracketing = (BracketedUnivariateSolver<UnivariateFunction>)this.solver;
/*     */             
/* 272 */             root = this.forward ? bracketing.solve(this.maxIterationCount, f, ta, tb, AllowedSolution.RIGHT_SIDE) : bracketing.solve(this.maxIterationCount, f, tb, ta, AllowedSolution.LEFT_SIDE);
/*     */           }
/*     */           else {
/*     */             
/* 276 */             double baseRoot = this.forward ? this.solver.solve(this.maxIterationCount, f, ta, tb) : this.solver.solve(this.maxIterationCount, f, tb, ta);
/*     */ 
/*     */             
/* 279 */             int remainingEval = this.maxIterationCount - this.solver.getEvaluations();
/* 280 */             PegasusSolver pegasusSolver = new PegasusSolver(this.solver.getRelativeAccuracy(), this.solver.getAbsoluteAccuracy());
/*     */             
/* 282 */             root = this.forward ? UnivariateSolverUtils.forceSide(remainingEval, f, (BracketedUnivariateSolver)pegasusSolver, baseRoot, ta, tb, AllowedSolution.RIGHT_SIDE) : UnivariateSolverUtils.forceSide(remainingEval, f, (BracketedUnivariateSolver)pegasusSolver, baseRoot, tb, ta, AllowedSolution.LEFT_SIDE);
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 289 */           if (!Double.isNaN(this.previousEventTime) && FastMath.abs(root - ta) <= this.convergence && FastMath.abs(root - this.previousEventTime) <= this.convergence)
/*     */           
/*     */           { 
/*     */             do {
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 297 */               ta = this.forward ? (ta + this.convergence) : (ta - this.convergence);
/* 298 */               ga = f.value(ta);
/* 299 */             } while ((this.g0Positive ^ ((ga >= 0.0D) ? 1 : 0)) != 0 && (this.forward ^ ((ta >= tb) ? 1 : 0)) != 0);
/*     */             
/* 301 */             if ((this.forward ^ ((ta >= tb) ? 1 : 0)) != 0) {
/*     */               
/* 303 */               i--;
/*     */             
/*     */             }
/*     */             else {
/*     */               
/* 308 */               this.pendingEventTime = root;
/* 309 */               this.pendingEvent = true;
/* 310 */               return true;
/*     */             }  }
/* 312 */           else { if (Double.isNaN(this.previousEventTime) || FastMath.abs(this.previousEventTime - root) > this.convergence) {
/*     */               
/* 314 */               this.pendingEventTime = root;
/* 315 */               this.pendingEvent = true;
/* 316 */               return true;
/*     */             } 
/*     */             
/* 319 */             ta = tb;
/* 320 */             ga = gb; }
/*     */ 
/*     */         
/*     */         } else {
/*     */           
/* 325 */           ta = tb;
/* 326 */           ga = gb;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 332 */       this.pendingEvent = false;
/* 333 */       this.pendingEventTime = Double.NaN;
/* 334 */       return false;
/*     */     }
/* 336 */     catch (LocalMaxCountExceededException lmcee) {
/* 337 */       throw lmcee.getException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getEventTime() {
/* 347 */     return this.pendingEvent ? this.pendingEventTime : (this.forward ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY);
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
/*     */   public void stepAccepted(double t, double[] y) {
/* 360 */     this.t0 = t;
/* 361 */     this.g0 = this.handler.g(t, y);
/*     */     
/* 363 */     if (this.pendingEvent && FastMath.abs(this.pendingEventTime - t) <= this.convergence) {
/*     */       
/* 365 */       this.previousEventTime = t;
/* 366 */       this.g0Positive = this.increasing;
/* 367 */       this.nextAction = this.handler.eventOccurred(t, y, ((this.increasing ^ this.forward) == 0));
/*     */     } else {
/* 369 */       this.g0Positive = (this.g0 >= 0.0D);
/* 370 */       this.nextAction = EventHandler.Action.CONTINUE;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean stop() {
/* 379 */     return (this.nextAction == EventHandler.Action.STOP);
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
/*     */   public boolean reset(double t, double[] y) {
/* 391 */     if (!this.pendingEvent || FastMath.abs(this.pendingEventTime - t) > this.convergence) {
/* 392 */       return false;
/*     */     }
/*     */     
/* 395 */     if (this.nextAction == EventHandler.Action.RESET_STATE) {
/* 396 */       this.handler.resetState(t, y);
/*     */     }
/* 398 */     this.pendingEvent = false;
/* 399 */     this.pendingEventTime = Double.NaN;
/*     */     
/* 401 */     return (this.nextAction == EventHandler.Action.RESET_STATE || this.nextAction == EventHandler.Action.RESET_DERIVATIVES);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class LocalMaxCountExceededException
/*     */     extends RuntimeException
/*     */   {
/*     */     private static final long serialVersionUID = 20120901L;
/*     */ 
/*     */ 
/*     */     
/*     */     private final MaxCountExceededException wrapped;
/*     */ 
/*     */ 
/*     */     
/*     */     LocalMaxCountExceededException(MaxCountExceededException exception) {
/* 419 */       this.wrapped = exception;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MaxCountExceededException getException() {
/* 426 */       return this.wrapped;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/events/EventState.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */