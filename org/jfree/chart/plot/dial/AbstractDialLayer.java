/*     */ package org.jfree.chart.plot.dial;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.EventListener;
/*     */ import java.util.List;
/*     */ import javax.swing.event.EventListenerList;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractDialLayer
/*     */   implements DialLayer
/*     */ {
/*     */   private boolean visible = true;
/*  74 */   private transient EventListenerList listenerList = new EventListenerList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVisible() {
/*  87 */     return this.visible;
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
/*     */   public void setVisible(boolean visible) {
/* 100 */     this.visible = visible;
/* 101 */     notifyListeners(new DialLayerChangeEvent(this));
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
/*     */   public boolean equals(Object obj) {
/* 113 */     if (obj == this) {
/* 114 */       return true;
/*     */     }
/* 116 */     if (!(obj instanceof AbstractDialLayer)) {
/* 117 */       return false;
/*     */     }
/* 119 */     AbstractDialLayer that = (AbstractDialLayer)obj;
/* 120 */     return (this.visible == that.visible);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 130 */     int result = 23;
/* 131 */     result = HashUtilities.hashCode(result, this.visible);
/* 132 */     return result;
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 145 */     AbstractDialLayer clone = (AbstractDialLayer)super.clone();
/*     */     
/* 147 */     clone.listenerList = new EventListenerList();
/* 148 */     return clone;
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
/*     */   public void addChangeListener(DialLayerChangeListener listener) {
/* 160 */     this.listenerList.add(DialLayerChangeListener.class, listener);
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
/*     */   public void removeChangeListener(DialLayerChangeListener listener) {
/* 172 */     this.listenerList.remove(DialLayerChangeListener.class, listener);
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
/*     */   public boolean hasListener(EventListener listener) {
/* 186 */     List list = Arrays.asList(this.listenerList.getListenerList());
/* 187 */     return list.contains(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void notifyListeners(DialLayerChangeEvent event) {
/* 197 */     Object[] listeners = this.listenerList.getListenerList();
/* 198 */     for (int i = listeners.length - 2; i >= 0; i -= 2) {
/* 199 */       if (listeners[i] == DialLayerChangeListener.class) {
/* 200 */         ((DialLayerChangeListener)listeners[i + 1]).dialLayerChanged(event);
/*     */       }
/*     */     } 
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
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 216 */     stream.defaultReadObject();
/* 217 */     this.listenerList = new EventListenerList();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/dial/AbstractDialLayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */