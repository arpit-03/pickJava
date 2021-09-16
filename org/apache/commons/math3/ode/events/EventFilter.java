/*     */ package org.apache.commons.math3.ode.events;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EventFilter
/*     */   implements EventHandler
/*     */ {
/*     */   private static final int HISTORY_SIZE = 100;
/*     */   private final EventHandler rawHandler;
/*     */   private final FilterType filter;
/*     */   private final Transformer[] transformers;
/*     */   private final double[] updates;
/*     */   private boolean forward;
/*     */   private double extremeT;
/*     */   
/*     */   public EventFilter(EventHandler rawHandler, FilterType filter) {
/*  83 */     this.rawHandler = rawHandler;
/*  84 */     this.filter = filter;
/*  85 */     this.transformers = new Transformer[100];
/*  86 */     this.updates = new double[100];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(double t0, double[] y0, double t) {
/*  93 */     this.rawHandler.init(t0, y0, t);
/*     */ 
/*     */     
/*  96 */     this.forward = (t >= t0);
/*  97 */     this.extremeT = this.forward ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
/*  98 */     Arrays.fill((Object[])this.transformers, Transformer.UNINITIALIZED);
/*  99 */     Arrays.fill(this.updates, this.extremeT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double g(double t, double[] y) {
/* 106 */     double rawG = this.rawHandler.g(t, y);
/*     */ 
/*     */     
/* 109 */     if (this.forward) {
/* 110 */       int last = this.transformers.length - 1;
/* 111 */       if (this.extremeT < t) {
/*     */ 
/*     */ 
/*     */         
/* 115 */         Transformer previous = this.transformers[last];
/* 116 */         Transformer next = this.filter.selectTransformer(previous, rawG, this.forward);
/* 117 */         if (next != previous) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 124 */           System.arraycopy(this.updates, 1, this.updates, 0, last);
/* 125 */           System.arraycopy(this.transformers, 1, this.transformers, 0, last);
/* 126 */           this.updates[last] = this.extremeT;
/* 127 */           this.transformers[last] = next;
/*     */         } 
/*     */         
/* 130 */         this.extremeT = t;
/*     */ 
/*     */         
/* 133 */         return next.transformed(rawG);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 139 */       for (int j = last; j > 0; j--) {
/* 140 */         if (this.updates[j] <= t)
/*     */         {
/* 142 */           return this.transformers[j].transformed(rawG);
/*     */         }
/*     */       } 
/*     */       
/* 146 */       return this.transformers[0].transformed(rawG);
/*     */     } 
/*     */ 
/*     */     
/* 150 */     if (t < this.extremeT) {
/*     */ 
/*     */ 
/*     */       
/* 154 */       Transformer previous = this.transformers[0];
/* 155 */       Transformer next = this.filter.selectTransformer(previous, rawG, this.forward);
/* 156 */       if (next != previous) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 163 */         System.arraycopy(this.updates, 0, this.updates, 1, this.updates.length - 1);
/* 164 */         System.arraycopy(this.transformers, 0, this.transformers, 1, this.transformers.length - 1);
/* 165 */         this.updates[0] = this.extremeT;
/* 166 */         this.transformers[0] = next;
/*     */       } 
/*     */       
/* 169 */       this.extremeT = t;
/*     */ 
/*     */       
/* 172 */       return next.transformed(rawG);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 178 */     for (int i = 0; i < this.updates.length - 1; i++) {
/* 179 */       if (t <= this.updates[i])
/*     */       {
/* 181 */         return this.transformers[i].transformed(rawG);
/*     */       }
/*     */     } 
/*     */     
/* 185 */     return this.transformers[this.updates.length - 1].transformed(rawG);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EventHandler.Action eventOccurred(double t, double[] y, boolean increasing) {
/* 195 */     return this.rawHandler.eventOccurred(t, y, this.filter.getTriggeredIncreasing());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetState(double t, double[] y) {
/* 201 */     this.rawHandler.resetState(t, y);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/events/EventFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */