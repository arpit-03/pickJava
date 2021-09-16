/*     */ package org.jfree.data.general;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.DefaultKeyedValues;
/*     */ import org.jfree.data.KeyedValues;
/*     */ import org.jfree.util.PublicCloneable;
/*     */ import org.jfree.util.SortOrder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultPieDataset
/*     */   extends AbstractDataset
/*     */   implements PieDataset, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2904745139106540618L;
/*     */   private DefaultKeyedValues data;
/*     */   
/*     */   public DefaultPieDataset() {
/*  87 */     this.data = new DefaultKeyedValues();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultPieDataset(KeyedValues data) {
/*  97 */     ParamChecks.nullNotPermitted(data, "data");
/*  98 */     this.data = new DefaultKeyedValues();
/*  99 */     for (int i = 0; i < data.getItemCount(); i++) {
/* 100 */       this.data.addValue(data.getKey(i), data.getValue(i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemCount() {
/* 111 */     return this.data.getItemCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getKeys() {
/* 122 */     return Collections.unmodifiableList(this.data.getKeys());
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
/*     */   public Comparable getKey(int item) {
/* 138 */     return this.data.getKey(item);
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
/*     */   public int getIndex(Comparable key) {
/* 153 */     return this.data.getIndex(key);
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
/*     */   public Number getValue(int item) {
/* 165 */     Number result = null;
/* 166 */     if (getItemCount() > item) {
/* 167 */       result = this.data.getValue(item);
/*     */     }
/* 169 */     return result;
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
/*     */   public Number getValue(Comparable key) {
/* 183 */     ParamChecks.nullNotPermitted(key, "key");
/* 184 */     return this.data.getValue(key);
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
/*     */   public void setValue(Comparable key, Number value) {
/* 198 */     this.data.setValue(key, value);
/* 199 */     fireDatasetChanged();
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
/*     */   public void setValue(Comparable key, double value) {
/* 213 */     setValue(key, new Double(value));
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
/*     */   public void insertValue(int position, Comparable key, double value) {
/* 230 */     insertValue(position, key, new Double(value));
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
/*     */   public void insertValue(int position, Comparable key, Number value) {
/* 247 */     this.data.insertValue(position, key, value);
/* 248 */     fireDatasetChanged();
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
/*     */   public void remove(Comparable key) {
/* 261 */     this.data.removeValue(key);
/* 262 */     fireDatasetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 272 */     if (getItemCount() > 0) {
/* 273 */       this.data.clear();
/* 274 */       fireDatasetChanged();
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
/*     */   public void sortByKeys(SortOrder order) {
/* 287 */     this.data.sortByKeys(order);
/* 288 */     fireDatasetChanged();
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
/*     */   public void sortByValues(SortOrder order) {
/* 300 */     this.data.sortByValues(order);
/* 301 */     fireDatasetChanged();
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
/* 313 */     if (obj == this) {
/* 314 */       return true;
/*     */     }
/*     */     
/* 317 */     if (!(obj instanceof PieDataset)) {
/* 318 */       return false;
/*     */     }
/* 320 */     PieDataset that = (PieDataset)obj;
/* 321 */     int count = getItemCount();
/* 322 */     if (that.getItemCount() != count) {
/* 323 */       return false;
/*     */     }
/*     */     
/* 326 */     for (int i = 0; i < count; i++) {
/* 327 */       Comparable k1 = getKey(i);
/* 328 */       Comparable k2 = that.getKey(i);
/* 329 */       if (!k1.equals(k2)) {
/* 330 */         return false;
/*     */       }
/*     */       
/* 333 */       Number v1 = getValue(i);
/* 334 */       Number v2 = that.getValue(i);
/* 335 */       if (v1 == null) {
/* 336 */         if (v2 != null) {
/* 337 */           return false;
/*     */         
/*     */         }
/*     */       }
/* 341 */       else if (!v1.equals(v2)) {
/* 342 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 346 */     return true;
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
/* 357 */     return this.data.hashCode();
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
/* 370 */     DefaultPieDataset clone = (DefaultPieDataset)super.clone();
/* 371 */     clone.data = (DefaultKeyedValues)this.data.clone();
/* 372 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/general/DefaultPieDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */