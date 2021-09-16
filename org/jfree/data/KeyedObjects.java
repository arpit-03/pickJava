/*     */ package org.jfree.data;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.util.PublicCloneable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KeyedObjects
/*     */   implements Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1321582394193530984L;
/*  69 */   private List data = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemCount() {
/*  78 */     return this.data.size();
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
/*     */   public Object getObject(int item) {
/*  91 */     Object result = null;
/*  92 */     KeyedObject kobj = this.data.get(item);
/*  93 */     if (kobj != null) {
/*  94 */       result = kobj.getObject();
/*     */     }
/*  96 */     return result;
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
/*     */   public Comparable getKey(int index) {
/* 111 */     Comparable result = null;
/* 112 */     KeyedObject item = this.data.get(index);
/* 113 */     if (item != null) {
/* 114 */       result = item.getKey();
/*     */     }
/* 116 */     return result;
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
/*     */   public int getIndex(Comparable key) {
/* 129 */     ParamChecks.nullNotPermitted(key, "key");
/* 130 */     int i = 0;
/* 131 */     Iterator<KeyedObject> iterator = this.data.iterator();
/* 132 */     while (iterator.hasNext()) {
/* 133 */       KeyedObject ko = iterator.next();
/* 134 */       if (ko.getKey().equals(key)) {
/* 135 */         return i;
/*     */       }
/* 137 */       i++;
/*     */     } 
/* 139 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getKeys() {
/* 148 */     List<Comparable> result = new ArrayList();
/* 149 */     Iterator<KeyedObject> iterator = this.data.iterator();
/* 150 */     while (iterator.hasNext()) {
/* 151 */       KeyedObject ko = iterator.next();
/* 152 */       result.add(ko.getKey());
/*     */     } 
/* 154 */     return result;
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
/*     */   public Object getObject(Comparable key) {
/* 168 */     int index = getIndex(key);
/* 169 */     if (index < 0) {
/* 170 */       throw new UnknownKeyException("The key (" + key + ") is not recognised.");
/*     */     }
/*     */     
/* 173 */     return getObject(index);
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
/*     */   public void addObject(Comparable key, Object object) {
/* 186 */     setObject(key, object);
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
/*     */   public void setObject(Comparable key, Object object) {
/* 200 */     int keyIndex = getIndex(key);
/* 201 */     if (keyIndex >= 0) {
/* 202 */       KeyedObject ko = this.data.get(keyIndex);
/* 203 */       ko.setObject(object);
/*     */     } else {
/*     */       
/* 206 */       KeyedObject ko = new KeyedObject(key, object);
/* 207 */       this.data.add(ko);
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
/*     */   
/*     */   public void insertValue(int position, Comparable key, Object value) {
/* 224 */     if (position < 0 || position > this.data.size()) {
/* 225 */       throw new IllegalArgumentException("'position' out of bounds.");
/*     */     }
/* 227 */     ParamChecks.nullNotPermitted(key, "key");
/* 228 */     int pos = getIndex(key);
/* 229 */     if (pos >= 0) {
/* 230 */       this.data.remove(pos);
/*     */     }
/* 232 */     KeyedObject item = new KeyedObject(key, value);
/* 233 */     if (position <= this.data.size()) {
/* 234 */       this.data.add(position, item);
/*     */     } else {
/*     */       
/* 237 */       this.data.add(item);
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
/*     */   public void removeValue(int index) {
/* 249 */     this.data.remove(index);
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
/*     */   public void removeValue(Comparable key) {
/* 263 */     int index = getIndex(key);
/* 264 */     if (index < 0) {
/* 265 */       throw new UnknownKeyException("The key (" + key.toString() + ") is not recognised.");
/*     */     }
/*     */     
/* 268 */     removeValue(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 277 */     this.data.clear();
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
/* 291 */     KeyedObjects clone = (KeyedObjects)super.clone();
/* 292 */     clone.data = new ArrayList();
/* 293 */     Iterator<KeyedObject> iterator = this.data.iterator();
/* 294 */     while (iterator.hasNext()) {
/* 295 */       KeyedObject ko = iterator.next();
/* 296 */       clone.data.add(ko.clone());
/*     */     } 
/* 298 */     return clone;
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
/*     */   public boolean equals(Object obj) {
/* 311 */     if (obj == this) {
/* 312 */       return true;
/*     */     }
/* 314 */     if (!(obj instanceof KeyedObjects)) {
/* 315 */       return false;
/*     */     }
/* 317 */     KeyedObjects that = (KeyedObjects)obj;
/* 318 */     int count = getItemCount();
/* 319 */     if (count != that.getItemCount()) {
/* 320 */       return false;
/*     */     }
/*     */     
/* 323 */     for (int i = 0; i < count; i++) {
/* 324 */       Comparable k1 = getKey(i);
/* 325 */       Comparable k2 = that.getKey(i);
/* 326 */       if (!k1.equals(k2)) {
/* 327 */         return false;
/*     */       }
/* 329 */       Object o1 = getObject(i);
/* 330 */       Object o2 = that.getObject(i);
/* 331 */       if (o1 == null) {
/* 332 */         if (o2 != null) {
/* 333 */           return false;
/*     */         
/*     */         }
/*     */       }
/* 337 */       else if (!o1.equals(o2)) {
/* 338 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 342 */     return true;
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
/* 353 */     return (this.data != null) ? this.data.hashCode() : 0;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/KeyedObjects.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */