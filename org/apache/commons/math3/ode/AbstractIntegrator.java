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
/*     */ import org.apache.commons.math3.analysis.solvers.BracketingNthOrderBrentSolver;
/*     */ import org.apache.commons.math3.analysis.solvers.UnivariateSolver;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NoBracketingException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.ode.events.EventHandler;
/*     */ import org.apache.commons.math3.ode.events.EventState;
/*     */ import org.apache.commons.math3.ode.sampling.AbstractStepInterpolator;
/*     */ import org.apache.commons.math3.ode.sampling.StepHandler;
/*     */ import org.apache.commons.math3.ode.sampling.StepInterpolator;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.Incrementor;
/*     */ import org.apache.commons.math3.util.IntegerSequence;
/*     */ import org.apache.commons.math3.util.Precision;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractIntegrator
/*     */   implements FirstOrderIntegrator
/*     */ {
/*     */   protected Collection<StepHandler> stepHandlers;
/*     */   protected double stepStart;
/*     */   protected double stepSize;
/*     */   protected boolean isLastStep;
/*     */   protected boolean resetOccurred;
/*     */   private Collection<EventState> eventsStates;
/*     */   private boolean statesInitialized;
/*     */   private final String name;
/*     */   private IntegerSequence.Incrementor evaluations;
/*     */   private transient ExpandableStatefulODE expandable;
/*     */   
/*     */   public AbstractIntegrator(String name) {
/*  84 */     this.name = name;
/*  85 */     this.stepHandlers = new ArrayList<StepHandler>();
/*  86 */     this.stepStart = Double.NaN;
/*  87 */     this.stepSize = Double.NaN;
/*  88 */     this.eventsStates = new ArrayList<EventState>();
/*  89 */     this.statesInitialized = false;
/*  90 */     this.evaluations = IntegerSequence.Incrementor.create().withMaximalCount(2147483647);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractIntegrator() {
/*  96 */     this(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 101 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addStepHandler(StepHandler handler) {
/* 106 */     this.stepHandlers.add(handler);
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<StepHandler> getStepHandlers() {
/* 111 */     return Collections.unmodifiableCollection(this.stepHandlers);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearStepHandlers() {
/* 116 */     this.stepHandlers.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEventHandler(EventHandler handler, double maxCheckInterval, double convergence, int maxIterationCount) {
/* 124 */     addEventHandler(handler, maxCheckInterval, convergence, maxIterationCount, (UnivariateSolver)new BracketingNthOrderBrentSolver(convergence, 5));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEventHandler(EventHandler handler, double maxCheckInterval, double convergence, int maxIterationCount, UnivariateSolver solver) {
/* 135 */     this.eventsStates.add(new EventState(handler, maxCheckInterval, convergence, maxIterationCount, solver));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<EventHandler> getEventHandlers() {
/* 141 */     List<EventHandler> list = new ArrayList<EventHandler>(this.eventsStates.size());
/* 142 */     for (EventState state : this.eventsStates) {
/* 143 */       list.add(state.getEventHandler());
/*     */     }
/* 145 */     return Collections.unmodifiableCollection(list);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearEventHandlers() {
/* 150 */     this.eventsStates.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public double getCurrentStepStart() {
/* 155 */     return this.stepStart;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getCurrentSignedStepsize() {
/* 160 */     return this.stepSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMaxEvaluations(int maxEvaluations) {
/* 165 */     this.evaluations = this.evaluations.withMaximalCount((maxEvaluations < 0) ? Integer.MAX_VALUE : maxEvaluations);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxEvaluations() {
/* 170 */     return this.evaluations.getMaximalCount();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEvaluations() {
/* 175 */     return this.evaluations.getCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initIntegration(double t0, double[] y0, double t) {
/* 185 */     this.evaluations = this.evaluations.withStart(0);
/*     */     
/* 187 */     for (EventState state : this.eventsStates) {
/* 188 */       state.setExpandable(this.expandable);
/* 189 */       state.getEventHandler().init(t0, y0, t);
/*     */     } 
/*     */     
/* 192 */     for (StepHandler handler : this.stepHandlers) {
/* 193 */       handler.init(t0, y0, t);
/*     */     }
/*     */     
/* 196 */     setStateInitialized(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setEquations(ExpandableStatefulODE equations) {
/* 204 */     this.expandable = equations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ExpandableStatefulODE getExpandable() {
/* 212 */     return this.expandable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected Incrementor getEvaluationsCounter() {
/* 222 */     return Incrementor.wrap(this.evaluations);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected IntegerSequence.Incrementor getCounter() {
/* 230 */     return this.evaluations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double integrate(FirstOrderDifferentialEquations equations, double t0, double[] y0, double t, double[] y) throws DimensionMismatchException, NumberIsTooSmallException, MaxCountExceededException, NoBracketingException {
/* 239 */     if (y0.length != equations.getDimension()) {
/* 240 */       throw new DimensionMismatchException(y0.length, equations.getDimension());
/*     */     }
/* 242 */     if (y.length != equations.getDimension()) {
/* 243 */       throw new DimensionMismatchException(y.length, equations.getDimension());
/*     */     }
/*     */ 
/*     */     
/* 247 */     ExpandableStatefulODE expandableODE = new ExpandableStatefulODE(equations);
/* 248 */     expandableODE.setTime(t0);
/* 249 */     expandableODE.setPrimaryState(y0);
/*     */ 
/*     */     
/* 252 */     integrate(expandableODE, t);
/*     */ 
/*     */     
/* 255 */     System.arraycopy(expandableODE.getPrimaryState(), 0, y, 0, y.length);
/* 256 */     return expandableODE.getTime();
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
/*     */   public abstract void integrate(ExpandableStatefulODE paramExpandableStatefulODE, double paramDouble) throws NumberIsTooSmallException, DimensionMismatchException, MaxCountExceededException, NoBracketingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void computeDerivatives(double t, double[] y, double[] yDot) throws MaxCountExceededException, DimensionMismatchException, NullPointerException {
/* 295 */     this.evaluations.increment();
/* 296 */     this.expandable.computeDerivatives(t, y, yDot);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setStateInitialized(boolean stateInitialized) {
/* 307 */     this.statesInitialized = stateInitialized;
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
/*     */   protected double acceptStep(AbstractStepInterpolator interpolator, double[] y, double[] yDot, double tEnd) throws MaxCountExceededException, DimensionMismatchException, NoBracketingException {
/* 327 */     double previousT = interpolator.getGlobalPreviousTime();
/* 328 */     double currentT = interpolator.getGlobalCurrentTime();
/*     */ 
/*     */     
/* 331 */     if (!this.statesInitialized) {
/* 332 */       for (EventState state : this.eventsStates) {
/* 333 */         state.reinitializeBegin((StepInterpolator)interpolator);
/*     */       }
/* 335 */       this.statesInitialized = true;
/*     */     } 
/*     */ 
/*     */     
/* 339 */     final int orderingSign = interpolator.isForward() ? 1 : -1;
/* 340 */     SortedSet<EventState> occurringEvents = new TreeSet<EventState>(new Comparator<EventState>()
/*     */         {
/*     */           public int compare(EventState es0, EventState es1)
/*     */           {
/* 344 */             return orderingSign * Double.compare(es0.getEventTime(), es1.getEventTime());
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 349 */     for (EventState state : this.eventsStates) {
/* 350 */       if (state.evaluateStep((StepInterpolator)interpolator))
/*     */       {
/* 352 */         occurringEvents.add(state);
/*     */       }
/*     */     } 
/*     */     
/* 356 */     while (!occurringEvents.isEmpty()) {
/*     */ 
/*     */       
/* 359 */       Iterator<EventState> iterator = occurringEvents.iterator();
/* 360 */       EventState currentEvent = iterator.next();
/* 361 */       iterator.remove();
/*     */ 
/*     */       
/* 364 */       double eventT = currentEvent.getEventTime();
/* 365 */       interpolator.setSoftPreviousTime(previousT);
/* 366 */       interpolator.setSoftCurrentTime(eventT);
/*     */ 
/*     */       
/* 369 */       interpolator.setInterpolatedTime(eventT);
/* 370 */       double[] eventYComplete = new double[y.length];
/* 371 */       this.expandable.getPrimaryMapper().insertEquationData(interpolator.getInterpolatedState(), eventYComplete);
/*     */       
/* 373 */       int i = 0;
/* 374 */       for (EquationsMapper secondary : this.expandable.getSecondaryMappers()) {
/* 375 */         secondary.insertEquationData(interpolator.getInterpolatedSecondaryState(i++), eventYComplete);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 380 */       for (EventState state : this.eventsStates) {
/* 381 */         state.stepAccepted(eventT, eventYComplete);
/* 382 */         this.isLastStep = (this.isLastStep || state.stop());
/*     */       } 
/*     */ 
/*     */       
/* 386 */       for (StepHandler handler : this.stepHandlers) {
/* 387 */         handler.handleStep((StepInterpolator)interpolator, this.isLastStep);
/*     */       }
/*     */       
/* 390 */       if (this.isLastStep) {
/*     */         
/* 392 */         System.arraycopy(eventYComplete, 0, y, 0, y.length);
/* 393 */         return eventT;
/*     */       } 
/*     */       
/* 396 */       boolean needReset = false;
/* 397 */       this.resetOccurred = false;
/* 398 */       needReset = currentEvent.reset(eventT, eventYComplete);
/* 399 */       if (needReset) {
/*     */ 
/*     */         
/* 402 */         interpolator.setInterpolatedTime(eventT);
/* 403 */         System.arraycopy(eventYComplete, 0, y, 0, y.length);
/* 404 */         computeDerivatives(eventT, y, yDot);
/* 405 */         this.resetOccurred = true;
/* 406 */         return eventT;
/*     */       } 
/*     */ 
/*     */       
/* 410 */       previousT = eventT;
/* 411 */       interpolator.setSoftPreviousTime(eventT);
/* 412 */       interpolator.setSoftCurrentTime(currentT);
/*     */ 
/*     */       
/* 415 */       if (currentEvent.evaluateStep((StepInterpolator)interpolator))
/*     */       {
/* 417 */         occurringEvents.add(currentEvent);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 423 */     interpolator.setInterpolatedTime(currentT);
/* 424 */     double[] currentY = new double[y.length];
/* 425 */     this.expandable.getPrimaryMapper().insertEquationData(interpolator.getInterpolatedState(), currentY);
/*     */     
/* 427 */     int index = 0;
/* 428 */     for (EquationsMapper secondary : this.expandable.getSecondaryMappers()) {
/* 429 */       secondary.insertEquationData(interpolator.getInterpolatedSecondaryState(index++), currentY);
/*     */     }
/*     */     
/* 432 */     for (EventState state : this.eventsStates) {
/* 433 */       state.stepAccepted(currentT, currentY);
/* 434 */       this.isLastStep = (this.isLastStep || state.stop());
/*     */     } 
/* 436 */     this.isLastStep = (this.isLastStep || Precision.equals(currentT, tEnd, 1));
/*     */ 
/*     */     
/* 439 */     for (StepHandler handler : this.stepHandlers) {
/* 440 */       handler.handleStep((StepInterpolator)interpolator, this.isLastStep);
/*     */     }
/*     */     
/* 443 */     return currentT;
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
/*     */   protected void sanityChecks(ExpandableStatefulODE equations, double t) throws NumberIsTooSmallException, DimensionMismatchException {
/* 457 */     double threshold = 1000.0D * FastMath.ulp(FastMath.max(FastMath.abs(equations.getTime()), FastMath.abs(t)));
/*     */     
/* 459 */     double dt = FastMath.abs(equations.getTime() - t);
/* 460 */     if (dt <= threshold)
/* 461 */       throw new NumberIsTooSmallException(LocalizedFormats.TOO_SMALL_INTEGRATION_INTERVAL, Double.valueOf(dt), Double.valueOf(threshold), false); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/AbstractIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */