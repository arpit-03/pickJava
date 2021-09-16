/*     */ package org.jfree.chart.panel;
/*     */ 
/*     */ import javax.swing.event.EventListenerList;
/*     */ import org.jfree.chart.event.OverlayChangeEvent;
/*     */ import org.jfree.chart.event.OverlayChangeListener;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AbstractOverlay
/*     */ {
/*  65 */   private transient EventListenerList changeListeners = new EventListenerList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChangeListener(OverlayChangeListener listener) {
/*  76 */     ParamChecks.nullNotPermitted(listener, "listener");
/*  77 */     this.changeListeners.add(OverlayChangeListener.class, listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeChangeListener(OverlayChangeListener listener) {
/*  88 */     ParamChecks.nullNotPermitted(listener, "listener");
/*  89 */     this.changeListeners.remove(OverlayChangeListener.class, listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireOverlayChanged() {
/*  98 */     OverlayChangeEvent event = new OverlayChangeEvent(this);
/*  99 */     notifyListeners(event);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void notifyListeners(OverlayChangeEvent event) {
/* 109 */     Object[] listeners = this.changeListeners.getListenerList();
/* 110 */     for (int i = listeners.length - 2; i >= 0; i -= 2) {
/* 111 */       if (listeners[i] == OverlayChangeListener.class)
/* 112 */         ((OverlayChangeListener)listeners[i + 1]).overlayChanged(event); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/panel/AbstractOverlay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */