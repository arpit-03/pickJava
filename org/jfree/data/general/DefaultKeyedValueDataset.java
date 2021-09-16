/*     */ package org.jfree.data.general;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.jfree.data.DefaultKeyedValue;
/*     */ import org.jfree.data.KeyedValue;
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
/*     */ public class DefaultKeyedValueDataset
/*     */   extends AbstractDataset
/*     */   implements KeyedValueDataset, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8149484339560406750L;
/*     */   private KeyedValue data;
/*     */   
/*     */   public DefaultKeyedValueDataset() {
/*  66 */     this(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultKeyedValueDataset(Comparable key, Number value) {
/*  76 */     this((KeyedValue)new DefaultKeyedValue(key, value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultKeyedValueDataset(KeyedValue data) {
/*  86 */     this.data = data;
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
/*  97 */     Comparable result = null;
/*  98 */     if (this.data != null) {
/*  99 */       result = this.data.getKey();
/*     */     }
/* 101 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Number getValue() {
/* 111 */     Number result = null;
/* 112 */     if (this.data != null) {
/* 113 */       result = this.data.getValue();
/*     */     }
/* 115 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateValue(Number value) {
/* 124 */     if (this.data == null) {
/* 125 */       throw new RuntimeException("updateValue: can't update null.");
/*     */     }
/* 127 */     setValue(this.data.getKey(), value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(Comparable key, Number value) {
/* 138 */     this.data = (KeyedValue)new DefaultKeyedValue(key, value);
/* 139 */     notifyListeners(new DatasetChangeEvent(this, this));
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
/* 151 */     if (obj == this) {
/* 152 */       return true;
/*     */     }
/* 154 */     if (!(obj instanceof KeyedValueDataset)) {
/* 155 */       return false;
/*     */     }
/* 157 */     KeyedValueDataset that = (KeyedValueDataset)obj;
/* 158 */     if (this.data == null) {
/* 159 */       if (that.getKey() != null || that.getValue() != null) {
/* 160 */         return false;
/*     */       }
/* 162 */       return true;
/*     */     } 
/* 164 */     if (!ObjectUtilities.equal(this.data.getKey(), that.getKey())) {
/* 165 */       return false;
/*     */     }
/* 167 */     if (!ObjectUtilities.equal(this.data.getValue(), that.getValue())) {
/* 168 */       return false;
/*     */     }
/* 170 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 180 */     return (this.data != null) ? this.data.hashCode() : 0;
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 194 */     DefaultKeyedValueDataset clone = (DefaultKeyedValueDataset)super.clone();
/* 195 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/general/DefaultKeyedValueDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */