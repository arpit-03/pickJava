/*     */ package org.jfree.chart.annotations;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.EventListener;
/*     */ import java.util.List;
/*     */ import javax.swing.event.EventListenerList;
/*     */ import org.jfree.chart.event.AnnotationChangeEvent;
/*     */ import org.jfree.chart.event.AnnotationChangeListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractAnnotation
/*     */   implements Annotation, Cloneable, Serializable
/*     */ {
/*     */   private transient EventListenerList listenerList;
/*     */   private boolean notify = true;
/*     */   
/*     */   protected AbstractAnnotation() {
/*  78 */     this.listenerList = new EventListenerList();
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
/*     */   public void addChangeListener(AnnotationChangeListener listener) {
/*  91 */     this.listenerList.add(AnnotationChangeListener.class, listener);
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
/*     */   public void removeChangeListener(AnnotationChangeListener listener) {
/* 104 */     this.listenerList.remove(AnnotationChangeListener.class, listener);
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
/*     */   public boolean hasListener(EventListener listener) {
/* 120 */     List list = Arrays.asList(this.listenerList.getListenerList());
/* 121 */     return list.contains(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fireAnnotationChanged() {
/* 130 */     if (this.notify) {
/* 131 */       notifyListeners(new AnnotationChangeEvent(this, this));
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
/*     */   protected void notifyListeners(AnnotationChangeEvent event) {
/* 146 */     Object[] listeners = this.listenerList.getListenerList();
/* 147 */     for (int i = listeners.length - 2; i >= 0; i -= 2) {
/* 148 */       if (listeners[i] == AnnotationChangeListener.class) {
/* 149 */         ((AnnotationChangeListener)listeners[i + 1]).annotationChanged(event);
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
/*     */   public boolean getNotify() {
/* 165 */     return this.notify;
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
/*     */   public void setNotify(boolean flag) {
/* 177 */     this.notify = flag;
/* 178 */     if (this.notify) {
/* 179 */       fireAnnotationChanged();
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
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/* 195 */     AbstractAnnotation clone = (AbstractAnnotation)super.clone();
/* 196 */     clone.listenerList = new EventListenerList();
/* 197 */     return clone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 208 */     stream.defaultWriteObject();
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
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 221 */     stream.defaultReadObject();
/* 222 */     this.listenerList = new EventListenerList();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/annotations/AbstractAnnotation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */