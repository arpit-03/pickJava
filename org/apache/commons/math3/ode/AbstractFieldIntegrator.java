/*     */ package org.apache.commons.math3.ode;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeSet;
/*     */ import org.apache.commons.math3.Field;
/*     */ import org.apache.commons.math3.RealFieldElement;
/*     */ import org.apache.commons.math3.analysis.solvers.BracketedRealFieldUnivariateSolver;
/*     */ import org.apache.commons.math3.analysis.solvers.FieldBracketingNthOrderBrentSolver;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NoBracketingException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.ode.events.FieldEventHandler;
/*     */ import org.apache.commons.math3.ode.events.FieldEventState;
/*     */ import org.apache.commons.math3.ode.sampling.AbstractFieldStepInterpolator;
/*     */ import org.apache.commons.math3.ode.sampling.FieldStepHandler;
/*     */ import org.apache.commons.math3.ode.sampling.FieldStepInterpolator;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.IntegerSequence;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractFieldIntegrator<T extends RealFieldElement<T>>
/*     */   implements FirstOrderFieldIntegrator<T>
/*     */ {
/*     */   private static final double DEFAULT_RELATIVE_ACCURACY = 1.0E-14D;
/*     */   private static final double DEFAULT_FUNCTION_VALUE_ACCURACY = 1.0E-15D;
/*     */   private Collection<FieldStepHandler<T>> stepHandlers;
/*     */   private FieldODEStateAndDerivative<T> stepStart;
/*     */   private T stepSize;
/*     */   private boolean isLastStep;
/*     */   private boolean resetOccurred;
/*     */   private final Field<T> field;
/*     */   private Collection<FieldEventState<T>> eventsStates;
/*     */   private boolean statesInitialized;
/*     */   private final String name;
/*     */   private IntegerSequence.Incrementor evaluations;
/*     */   private transient FieldExpandableODE<T> equations;
/*     */   
/*     */   protected AbstractFieldIntegrator(Field<T> field, String name) {
/*  96 */     this.field = field;
/*  97 */     this.name = name;
/*  98 */     this.stepHandlers = new ArrayList<FieldStepHandler<T>>();
/*  99 */     this.stepStart = null;
/* 100 */     this.stepSize = null;
/* 101 */     this.eventsStates = new ArrayList<FieldEventState<T>>();
/* 102 */     this.statesInitialized = false;
/* 103 */     this.evaluations = IntegerSequence.Incrementor.create().withMaximalCount(2147483647);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Field<T> getField() {
/* 110 */     return this.field;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 115 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addStepHandler(FieldStepHandler<T> handler) {
/* 120 */     this.stepHandlers.add(handler);
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<FieldStepHandler<T>> getStepHandlers() {
/* 125 */     return Collections.unmodifiableCollection(this.stepHandlers);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearStepHandlers() {
/* 130 */     this.stepHandlers.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEventHandler(FieldEventHandler<T> handler, double maxCheckInterval, double convergence, int maxIterationCount) {
/* 138 */     addEventHandler(handler, maxCheckInterval, convergence, maxIterationCount, (BracketedRealFieldUnivariateSolver<T>)new FieldBracketingNthOrderBrentSolver((RealFieldElement)((RealFieldElement)this.field.getZero()).add(1.0E-14D), (RealFieldElement)((RealFieldElement)this.field.getZero()).add(convergence), (RealFieldElement)((RealFieldElement)this.field.getZero()).add(1.0E-15D), 5));
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
/*     */   public void addEventHandler(FieldEventHandler<T> handler, double maxCheckInterval, double convergence, int maxIterationCount, BracketedRealFieldUnivariateSolver<T> solver) {
/* 152 */     this.eventsStates.add(new FieldEventState(handler, maxCheckInterval, (RealFieldElement)((RealFieldElement)this.field.getZero()).add(convergence), maxIterationCount, solver));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<FieldEventHandler<T>> getEventHandlers() {
/* 158 */     List<FieldEventHandler<T>> list = new ArrayList<FieldEventHandler<T>>(this.eventsStates.size());
/* 159 */     for (FieldEventState<T> state : this.eventsStates) {
/* 160 */       list.add(state.getEventHandler());
/*     */     }
/* 162 */     return Collections.unmodifiableCollection(list);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearEventHandlers() {
/* 167 */     this.eventsStates.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public FieldODEStateAndDerivative<T> getCurrentStepStart() {
/* 172 */     return this.stepStart;
/*     */   }
/*     */ 
/*     */   
/*     */   public T getCurrentSignedStepsize() {
/* 177 */     return this.stepSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMaxEvaluations(int maxEvaluations) {
/* 182 */     this.evaluations = this.evaluations.withMaximalCount((maxEvaluations < 0) ? Integer.MAX_VALUE : maxEvaluations);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxEvaluations() {
/* 187 */     return this.evaluations.getMaximalCount();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEvaluations() {
/* 192 */     return this.evaluations.getCount();
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
/*     */   protected FieldODEStateAndDerivative<T> initIntegration(FieldExpandableODE<T> eqn, T t0, T[] y0, T t) {
/* 205 */     this.equations = eqn;
/* 206 */     this.evaluations = this.evaluations.withStart(0);
/*     */ 
/*     */     
/* 209 */     eqn.init(t0, y0, t);
/*     */ 
/*     */     
/* 212 */     T[] y0Dot = computeDerivatives(t0, y0);
/* 213 */     FieldODEStateAndDerivative<T> state0 = new FieldODEStateAndDerivative<T>(t0, y0, y0Dot);
/*     */ 
/*     */     
/* 216 */     for (FieldEventState<T> state : this.eventsStates) {
/* 217 */       state.getEventHandler().init(state0, (RealFieldElement)t);
/*     */     }
/*     */ 
/*     */     
/* 221 */     for (FieldStepHandler<T> handler : this.stepHandlers) {
/* 222 */       handler.init(state0, (RealFieldElement)t);
/*     */     }
/*     */     
/* 225 */     setStateInitialized(false);
/*     */     
/* 227 */     return state0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FieldExpandableODE<T> getEquations() {
/* 235 */     return this.equations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected IntegerSequence.Incrementor getEvaluationsCounter() {
/* 242 */     return this.evaluations;
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
/*     */   public T[] computeDerivatives(T t, T[] y) throws DimensionMismatchException, MaxCountExceededException, NullPointerException {
/* 257 */     this.evaluations.increment();
/* 258 */     return this.equations.computeDerivatives(t, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setStateInitialized(boolean stateInitialized) {
/* 268 */     this.statesInitialized = stateInitialized;
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
/*     */   protected FieldODEStateAndDerivative<T> acceptStep(AbstractFieldStepInterpolator<T> interpolator, T tEnd) throws MaxCountExceededException, DimensionMismatchException, NoBracketingException {
/* 284 */     FieldODEStateAndDerivative<T> previousState = interpolator.getGlobalPreviousState();
/* 285 */     FieldODEStateAndDerivative<T> currentState = interpolator.getGlobalCurrentState();
/*     */ 
/*     */     
/* 288 */     if (!this.statesInitialized) {
/* 289 */       for (FieldEventState<T> state : this.eventsStates) {
/* 290 */         state.reinitializeBegin((FieldStepInterpolator)interpolator);
/*     */       }
/* 292 */       this.statesInitialized = true;
/*     */     } 
/*     */ 
/*     */     
/* 296 */     final int orderingSign = interpolator.isForward() ? 1 : -1;
/* 297 */     SortedSet<FieldEventState<T>> occurringEvents = new TreeSet<FieldEventState<T>>((Comparator)new Comparator<FieldEventState<FieldEventState<T>>>()
/*     */         {
/*     */           public int compare(FieldEventState<T> es0, FieldEventState<T> es1)
/*     */           {
/* 301 */             return orderingSign * Double.compare(es0.getEventTime().getReal(), es1.getEventTime().getReal());
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 306 */     for (FieldEventState<T> state : this.eventsStates) {
/* 307 */       if (state.evaluateStep((FieldStepInterpolator)interpolator))
/*     */       {
/* 309 */         occurringEvents.add(state);
/*     */       }
/*     */     } 
/*     */     
/* 313 */     AbstractFieldStepInterpolator<T> restricted = interpolator;
/* 314 */     while (!occurringEvents.isEmpty()) {
/*     */ 
/*     */       
/* 317 */       Iterator<FieldEventState<T>> iterator = occurringEvents.iterator();
/* 318 */       FieldEventState<T> currentEvent = iterator.next();
/* 319 */       iterator.remove();
/*     */ 
/*     */       
/* 322 */       FieldODEStateAndDerivative<T> eventState = restricted.getInterpolatedState(currentEvent.getEventTime());
/*     */ 
/*     */       
/* 325 */       restricted = restricted.restrictStep(previousState, eventState);
/*     */ 
/*     */       
/* 328 */       for (FieldEventState<T> state : this.eventsStates) {
/* 329 */         state.stepAccepted(eventState);
/* 330 */         this.isLastStep = (this.isLastStep || state.stop());
/*     */       } 
/*     */ 
/*     */       
/* 334 */       for (FieldStepHandler<T> handler : this.stepHandlers) {
/* 335 */         handler.handleStep((FieldStepInterpolator)restricted, this.isLastStep);
/*     */       }
/*     */       
/* 338 */       if (this.isLastStep)
/*     */       {
/* 340 */         return eventState;
/*     */       }
/*     */       
/* 343 */       FieldODEState<T> newState = null;
/* 344 */       this.resetOccurred = false;
/* 345 */       for (FieldEventState<T> state : this.eventsStates) {
/* 346 */         newState = state.reset(eventState);
/* 347 */         if (newState != null) {
/*     */ 
/*     */           
/* 350 */           T[] y = this.equations.getMapper().mapState(newState);
/* 351 */           T[] yDot = computeDerivatives(newState.getTime(), y);
/* 352 */           this.resetOccurred = true;
/* 353 */           return this.equations.getMapper().mapStateAndDerivative(newState.getTime(), y, yDot);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 358 */       previousState = eventState;
/* 359 */       restricted = restricted.restrictStep(eventState, currentState);
/*     */ 
/*     */       
/* 362 */       if (currentEvent.evaluateStep((FieldStepInterpolator)restricted))
/*     */       {
/* 364 */         occurringEvents.add(currentEvent);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 370 */     for (FieldEventState<T> state : this.eventsStates) {
/* 371 */       state.stepAccepted(currentState);
/* 372 */       this.isLastStep = (this.isLastStep || state.stop());
/*     */     } 
/* 374 */     this.isLastStep = (this.isLastStep || ((RealFieldElement)((RealFieldElement)currentState.getTime().subtract(tEnd)).abs()).getReal() <= FastMath.ulp(tEnd.getReal()));
/*     */ 
/*     */     
/* 377 */     for (FieldStepHandler<T> handler : this.stepHandlers) {
/* 378 */       handler.handleStep((FieldStepInterpolator)restricted, this.isLastStep);
/*     */     }
/*     */     
/* 381 */     return currentState;
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
/*     */   protected void sanityChecks(FieldODEState<T> eqn, T t) throws NumberIsTooSmallException, DimensionMismatchException {
/* 395 */     double threshold = 1000.0D * FastMath.ulp(FastMath.max(FastMath.abs(eqn.getTime().getReal()), FastMath.abs(t.getReal())));
/*     */     
/* 397 */     double dt = ((RealFieldElement)((RealFieldElement)eqn.getTime().subtract(t)).abs()).getReal();
/* 398 */     if (dt <= threshold) {
/* 399 */       throw new NumberIsTooSmallException(LocalizedFormats.TOO_SMALL_INTEGRATION_INTERVAL, Double.valueOf(dt), Double.valueOf(threshold), false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean resetOccurred() {
/* 409 */     return this.resetOccurred;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setStepSize(T stepSize) {
/* 416 */     this.stepSize = stepSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected T getStepSize() {
/* 423 */     return this.stepSize;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setStepStart(FieldODEStateAndDerivative<T> stepStart) {
/* 429 */     this.stepStart = stepStart;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FieldODEStateAndDerivative<T> getStepStart() {
/* 436 */     return this.stepStart;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setIsLastStep(boolean isLastStep) {
/* 443 */     this.isLastStep = isLastStep;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isLastStep() {
/* 450 */     return this.isLastStep;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/AbstractFieldIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */