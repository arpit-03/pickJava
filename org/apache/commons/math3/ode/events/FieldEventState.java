/*     */ package org.apache.commons.math3.ode.events;
/*     */ 
/*     */ import org.apache.commons.math3.RealFieldElement;
/*     */ import org.apache.commons.math3.analysis.RealFieldUnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.solvers.AllowedSolution;
/*     */ import org.apache.commons.math3.analysis.solvers.BracketedRealFieldUnivariateSolver;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NoBracketingException;
/*     */ import org.apache.commons.math3.ode.FieldODEState;
/*     */ import org.apache.commons.math3.ode.FieldODEStateAndDerivative;
/*     */ import org.apache.commons.math3.ode.sampling.FieldStepInterpolator;
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
/*     */ public class FieldEventState<T extends RealFieldElement<T>>
/*     */ {
/*     */   private final FieldEventHandler<T> handler;
/*     */   private final double maxCheckInterval;
/*     */   private final T convergence;
/*     */   private final int maxIterationCount;
/*     */   private T t0;
/*     */   private T g0;
/*     */   private boolean g0Positive;
/*     */   private boolean pendingEvent;
/*     */   private T pendingEventTime;
/*     */   private T previousEventTime;
/*     */   private boolean forward;
/*     */   private boolean increasing;
/*     */   private Action nextAction;
/*     */   private final BracketedRealFieldUnivariateSolver<T> solver;
/*     */   
/*     */   public FieldEventState(FieldEventHandler<T> handler, double maxCheckInterval, T convergence, int maxIterationCount, BracketedRealFieldUnivariateSolver<T> solver) {
/* 103 */     this.handler = handler;
/* 104 */     this.maxCheckInterval = maxCheckInterval;
/* 105 */     this.convergence = (T)convergence.abs();
/* 106 */     this.maxIterationCount = maxIterationCount;
/* 107 */     this.solver = solver;
/*     */ 
/*     */     
/* 110 */     this.t0 = null;
/* 111 */     this.g0 = null;
/* 112 */     this.g0Positive = true;
/* 113 */     this.pendingEvent = false;
/* 114 */     this.pendingEventTime = null;
/* 115 */     this.previousEventTime = null;
/* 116 */     this.increasing = true;
/* 117 */     this.nextAction = Action.CONTINUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldEventHandler<T> getEventHandler() {
/* 125 */     return this.handler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMaxCheckInterval() {
/* 132 */     return this.maxCheckInterval;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getConvergence() {
/* 139 */     return this.convergence;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxIterationCount() {
/* 146 */     return this.maxIterationCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reinitializeBegin(FieldStepInterpolator<T> interpolator) throws MaxCountExceededException {
/* 157 */     FieldODEStateAndDerivative<T> s0 = interpolator.getPreviousState();
/* 158 */     this.t0 = (T)s0.getTime();
/* 159 */     this.g0 = this.handler.g(s0);
/* 160 */     if (this.g0.getReal() == 0.0D) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 174 */       double epsilon = FastMath.max(this.solver.getAbsoluteAccuracy().getReal(), FastMath.abs(((RealFieldElement)this.solver.getRelativeAccuracy().multiply(this.t0)).getReal()));
/*     */       
/* 176 */       RealFieldElement realFieldElement = (RealFieldElement)this.t0.add(0.5D * epsilon);
/* 177 */       this.g0 = this.handler.g(interpolator.getInterpolatedState(realFieldElement));
/*     */     } 
/* 179 */     this.g0Positive = (this.g0.getReal() >= 0.0D);
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
/*     */   public boolean evaluateStep(final FieldStepInterpolator<T> interpolator) throws MaxCountExceededException, NoBracketingException {
/* 194 */     this.forward = interpolator.isForward();
/* 195 */     FieldODEStateAndDerivative<T> s1 = interpolator.getCurrentState();
/* 196 */     RealFieldElement realFieldElement1 = s1.getTime();
/* 197 */     RealFieldElement realFieldElement2 = (RealFieldElement)realFieldElement1.subtract(this.t0);
/* 198 */     if (((RealFieldElement)((RealFieldElement)realFieldElement2.abs()).subtract(this.convergence)).getReal() < 0.0D)
/*     */     {
/* 200 */       return false;
/*     */     }
/* 202 */     int n = FastMath.max(1, (int)FastMath.ceil(FastMath.abs(realFieldElement2.getReal()) / this.maxCheckInterval));
/* 203 */     RealFieldElement realFieldElement3 = (RealFieldElement)realFieldElement2.divide(n);
/*     */     
/* 205 */     RealFieldUnivariateFunction<T> f = new RealFieldUnivariateFunction<T>()
/*     */       {
/*     */         public T value(T t) {
/* 208 */           return FieldEventState.this.handler.g(interpolator.getInterpolatedState((RealFieldElement)t));
/*     */         }
/*     */       };
/*     */     
/* 212 */     T ta = this.t0;
/* 213 */     T ga = this.g0;
/* 214 */     for (int i = 0; i < n; i++) {
/*     */ 
/*     */       
/* 217 */       RealFieldElement realFieldElement = (i == n - 1) ? realFieldElement1 : (RealFieldElement)this.t0.add(realFieldElement3.multiply(i + 1));
/* 218 */       T gb = this.handler.g(interpolator.getInterpolatedState(realFieldElement));
/*     */ 
/*     */       
/* 221 */       if ((this.g0Positive ^ ((gb.getReal() >= 0.0D) ? 1 : 0)) != 0) {
/*     */ 
/*     */ 
/*     */         
/* 225 */         this.increasing = (((RealFieldElement)gb.subtract(ga)).getReal() >= 0.0D);
/*     */ 
/*     */         
/* 228 */         RealFieldElement realFieldElement4 = this.forward ? this.solver.solve(this.maxIterationCount, f, (RealFieldElement)ta, realFieldElement, AllowedSolution.RIGHT_SIDE) : this.solver.solve(this.maxIterationCount, f, realFieldElement, (RealFieldElement)ta, AllowedSolution.LEFT_SIDE);
/*     */ 
/*     */ 
/*     */         
/* 232 */         if (this.previousEventTime != null && ((RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement4.subtract(ta)).abs()).subtract(this.convergence)).getReal() <= 0.0D && ((RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement4.subtract(this.previousEventTime)).abs()).subtract(this.convergence)).getReal() <= 0.0D)
/*     */         { RealFieldElement realFieldElement5;
/*     */ 
/*     */           
/*     */           RealFieldElement realFieldElement6;
/*     */ 
/*     */           
/*     */           do {
/* 240 */             realFieldElement5 = this.forward ? (RealFieldElement)ta.add(this.convergence) : (RealFieldElement)ta.subtract(this.convergence);
/* 241 */             realFieldElement6 = f.value(realFieldElement5);
/* 242 */           } while ((this.g0Positive ^ ((realFieldElement6.getReal() >= 0.0D) ? 1 : 0)) != 0 && (this.forward ^ ((((RealFieldElement)realFieldElement5.subtract(realFieldElement)).getReal() >= 0.0D) ? 1 : 0)) != 0);
/*     */           
/* 244 */           if ((this.forward ^ ((((RealFieldElement)realFieldElement5.subtract(realFieldElement)).getReal() >= 0.0D) ? 1 : 0)) != 0) {
/*     */             
/* 246 */             i--;
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 251 */             this.pendingEventTime = (T)realFieldElement4;
/* 252 */             this.pendingEvent = true;
/* 253 */             return true;
/*     */           }  }
/* 255 */         else { if (this.previousEventTime == null || ((RealFieldElement)((RealFieldElement)((RealFieldElement)this.previousEventTime.subtract(realFieldElement4)).abs()).subtract(this.convergence)).getReal() > 0.0D) {
/*     */             
/* 257 */             this.pendingEventTime = (T)realFieldElement4;
/* 258 */             this.pendingEvent = true;
/* 259 */             return true;
/*     */           } 
/*     */           
/* 262 */           RealFieldElement realFieldElement5 = realFieldElement;
/* 263 */           ga = gb; }
/*     */ 
/*     */       
/*     */       } else {
/*     */         
/* 268 */         RealFieldElement realFieldElement4 = realFieldElement;
/* 269 */         ga = gb;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 275 */     this.pendingEvent = false;
/* 276 */     this.pendingEventTime = null;
/* 277 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getEventTime() {
/* 286 */     return this.pendingEvent ? this.pendingEventTime : (T)((RealFieldElement)this.t0.getField().getZero()).add(this.forward ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stepAccepted(FieldODEStateAndDerivative<T> state) {
/* 296 */     this.t0 = (T)state.getTime();
/* 297 */     this.g0 = this.handler.g(state);
/*     */     
/* 299 */     if (this.pendingEvent && ((RealFieldElement)((RealFieldElement)((RealFieldElement)this.pendingEventTime.subtract(state.getTime())).abs()).subtract(this.convergence)).getReal() <= 0.0D) {
/*     */       
/* 301 */       this.previousEventTime = (T)state.getTime();
/* 302 */       this.g0Positive = this.increasing;
/* 303 */       this.nextAction = this.handler.eventOccurred(state, ((this.increasing ^ this.forward) == 0));
/*     */     } else {
/* 305 */       this.g0Positive = (this.g0.getReal() >= 0.0D);
/* 306 */       this.nextAction = Action.CONTINUE;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean stop() {
/* 315 */     return (this.nextAction == Action.STOP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldODEState<T> reset(FieldODEStateAndDerivative<T> state) {
/*     */     FieldODEState<T> newState;
/* 325 */     if (!this.pendingEvent || ((RealFieldElement)((RealFieldElement)((RealFieldElement)this.pendingEventTime.subtract(state.getTime())).abs()).subtract(this.convergence)).getReal() > 0.0D) {
/* 326 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 330 */     if (this.nextAction == Action.RESET_STATE) {
/* 331 */       newState = this.handler.resetState(state);
/* 332 */     } else if (this.nextAction == Action.RESET_DERIVATIVES) {
/* 333 */       FieldODEStateAndDerivative<T> fieldODEStateAndDerivative = state;
/*     */     } else {
/* 335 */       newState = null;
/*     */     } 
/* 337 */     this.pendingEvent = false;
/* 338 */     this.pendingEventTime = null;
/*     */     
/* 340 */     return newState;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/events/FieldEventState.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */