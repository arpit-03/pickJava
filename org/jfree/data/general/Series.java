/*     */ package org.jfree.data.general;
/*     */ 
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.beans.PropertyChangeSupport;
/*     */ import java.beans.PropertyVetoException;
/*     */ import java.beans.VetoableChangeListener;
/*     */ import java.beans.VetoableChangeSupport;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.event.EventListenerList;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Series
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6906561437538683581L;
/*     */   private Comparable key;
/*     */   private String description;
/*     */   private EventListenerList listeners;
/*     */   private PropertyChangeSupport propertyChangeSupport;
/*     */   private VetoableChangeSupport vetoableChangeSupport;
/*     */   private boolean notify;
/*     */   
/*     */   protected Series(Comparable key) {
/* 110 */     this(key, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Series(Comparable key, String description) {
/* 120 */     ParamChecks.nullNotPermitted(key, "key");
/* 121 */     this.key = key;
/* 122 */     this.description = description;
/* 123 */     this.listeners = new EventListenerList();
/* 124 */     this.propertyChangeSupport = new PropertyChangeSupport(this);
/* 125 */     this.vetoableChangeSupport = new VetoableChangeSupport(this);
/* 126 */     this.notify = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Comparable getKey() {
/* 137 */     return this.key;
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
/*     */   public void setKey(Comparable key) {
/* 152 */     ParamChecks.nullNotPermitted(key, "key");
/* 153 */     Comparable old = this.key;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 158 */       this.vetoableChangeSupport.fireVetoableChange("Key", old, key);
/* 159 */       this.key = key;
/*     */ 
/*     */       
/* 162 */       this.propertyChangeSupport.firePropertyChange("Key", old, key);
/* 163 */     } catch (PropertyVetoException e) {
/* 164 */       throw new IllegalArgumentException(e.getMessage());
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
/*     */   public String getDescription() {
/* 176 */     return this.description;
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
/*     */   public void setDescription(String description) {
/* 188 */     String old = this.description;
/* 189 */     this.description = description;
/* 190 */     this.propertyChangeSupport.firePropertyChange("Description", old, description);
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
/*     */   public boolean getNotify() {
/* 203 */     return this.notify;
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
/*     */   public void setNotify(boolean notify) {
/* 215 */     if (this.notify != notify) {
/* 216 */       this.notify = notify;
/* 217 */       fireSeriesChanged();
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
/*     */   public boolean isEmpty() {
/* 230 */     return (getItemCount() == 0);
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
/*     */   public abstract int getItemCount();
/*     */ 
/*     */ 
/*     */ 
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
/* 259 */     Series clone = (Series)super.clone();
/* 260 */     clone.listeners = new EventListenerList();
/* 261 */     clone.propertyChangeSupport = new PropertyChangeSupport(clone);
/* 262 */     clone.vetoableChangeSupport = new VetoableChangeSupport(clone);
/* 263 */     return clone;
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
/* 275 */     if (obj == this) {
/* 276 */       return true;
/*     */     }
/* 278 */     if (!(obj instanceof Series)) {
/* 279 */       return false;
/*     */     }
/* 281 */     Series that = (Series)obj;
/* 282 */     if (!getKey().equals(that.getKey())) {
/* 283 */       return false;
/*     */     }
/* 285 */     if (!ObjectUtilities.equal(getDescription(), that.getDescription())) {
/* 286 */       return false;
/*     */     }
/* 288 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 299 */     int result = this.key.hashCode();
/*     */     
/* 301 */     result = 29 * result + ((this.description != null) ? this.description.hashCode() : 0);
/* 302 */     return result;
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
/*     */   public void addChangeListener(SeriesChangeListener listener) {
/* 315 */     this.listeners.add(SeriesChangeListener.class, listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeChangeListener(SeriesChangeListener listener) {
/* 325 */     this.listeners.remove(SeriesChangeListener.class, listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireSeriesChanged() {
/* 333 */     if (this.notify) {
/* 334 */       notifyListeners(new SeriesChangeEvent(this));
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
/*     */   protected void notifyListeners(SeriesChangeEvent event) {
/* 346 */     Object[] listenerList = this.listeners.getListenerList();
/* 347 */     for (int i = listenerList.length - 2; i >= 0; i -= 2) {
/* 348 */       if (listenerList[i] == SeriesChangeListener.class) {
/* 349 */         ((SeriesChangeListener)listenerList[i + 1]).seriesChanged(event);
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
/*     */   public void addPropertyChangeListener(PropertyChangeListener listener) {
/* 362 */     this.propertyChangeSupport.addPropertyChangeListener(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePropertyChangeListener(PropertyChangeListener listener) {
/* 371 */     this.propertyChangeSupport.removePropertyChangeListener(listener);
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
/*     */   protected void firePropertyChange(String property, Object oldValue, Object newValue) {
/* 383 */     this.propertyChangeSupport.firePropertyChange(property, oldValue, newValue);
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
/*     */   public void addVetoableChangeListener(VetoableChangeListener listener) {
/* 395 */     this.vetoableChangeSupport.addVetoableChangeListener(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeVetoableChangeListener(VetoableChangeListener listener) {
/* 406 */     this.vetoableChangeSupport.removeVetoableChangeListener(listener);
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
/*     */   protected void fireVetoableChange(String property, Object oldValue, Object newValue) throws PropertyVetoException {
/* 420 */     this.vetoableChangeSupport.fireVetoableChange(property, oldValue, newValue);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/general/Series.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */