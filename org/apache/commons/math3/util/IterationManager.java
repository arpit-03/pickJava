/*     */ package org.apache.commons.math3.util;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IterationManager
/*     */ {
/*     */   private IntegerSequence.Incrementor iterations;
/*     */   private final Collection<IterationListener> listeners;
/*     */   
/*     */   public IterationManager(int maxIterations) {
/*  45 */     this.iterations = IntegerSequence.Incrementor.create().withMaximalCount(maxIterations);
/*  46 */     this.listeners = new CopyOnWriteArrayList<IterationListener>();
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
/*     */   @Deprecated
/*     */   public IterationManager(int maxIterations, Incrementor.MaxCountExceededCallback callBack) {
/*  63 */     this(maxIterations, new IntegerSequence.Incrementor.MaxCountExceededCallback(callBack)
/*     */         {
/*     */           public void trigger(int maximalCount) throws MaxCountExceededException {
/*  66 */             callBack.trigger(maximalCount);
/*     */           }
/*     */         });
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
/*     */   public IterationManager(int maxIterations, IntegerSequence.Incrementor.MaxCountExceededCallback callBack) {
/*  82 */     this.iterations = IntegerSequence.Incrementor.create().withMaximalCount(maxIterations).withCallback(callBack);
/*  83 */     this.listeners = new CopyOnWriteArrayList<IterationListener>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addIterationListener(IterationListener listener) {
/*  92 */     this.listeners.add(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireInitializationEvent(IterationEvent e) {
/* 102 */     for (IterationListener l : this.listeners) {
/* 103 */       l.initializationPerformed(e);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireIterationPerformedEvent(IterationEvent e) {
/* 114 */     for (IterationListener l : this.listeners) {
/* 115 */       l.iterationPerformed(e);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireIterationStartedEvent(IterationEvent e) {
/* 126 */     for (IterationListener l : this.listeners) {
/* 127 */       l.iterationStarted(e);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireTerminationEvent(IterationEvent e) {
/* 138 */     for (IterationListener l : this.listeners) {
/* 139 */       l.terminationPerformed(e);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIterations() {
/* 150 */     return this.iterations.getCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxIterations() {
/* 159 */     return this.iterations.getMaximalCount();
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
/*     */   public void incrementIterationCount() throws MaxCountExceededException {
/* 172 */     this.iterations.increment();
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
/*     */   public void removeIterationListener(IterationListener listener) {
/* 184 */     this.listeners.remove(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetIterationCount() {
/* 192 */     this.iterations = this.iterations.withStart(0);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/IterationManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */