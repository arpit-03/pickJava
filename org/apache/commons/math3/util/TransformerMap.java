/*     */ package org.apache.commons.math3.util;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransformerMap
/*     */   implements NumberTransformer, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4605318041528645258L;
/*  41 */   private NumberTransformer defaultTransformer = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  46 */   private Map<Class<?>, NumberTransformer> map = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TransformerMap() {
/*  52 */     this.map = new HashMap<Class<?>, NumberTransformer>();
/*  53 */     this.defaultTransformer = new DefaultTransformer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsClass(Class<?> key) {
/*  62 */     return this.map.containsKey(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsTransformer(NumberTransformer value) {
/*  71 */     return this.map.containsValue(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NumberTransformer getTransformer(Class<?> key) {
/*  81 */     return this.map.get(key);
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
/*     */   public NumberTransformer putTransformer(Class<?> key, NumberTransformer transformer) {
/*  93 */     return this.map.put(key, transformer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NumberTransformer removeTransformer(Class<?> key) {
/* 103 */     return this.map.remove(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 110 */     this.map.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Class<?>> classes() {
/* 118 */     return this.map.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<NumberTransformer> transformers() {
/* 127 */     return this.map.values();
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
/*     */   public double transform(Object o) throws MathIllegalArgumentException {
/* 141 */     double value = Double.NaN;
/*     */     
/* 143 */     if (o instanceof Number || o instanceof String) {
/* 144 */       value = this.defaultTransformer.transform(o);
/*     */     } else {
/* 146 */       NumberTransformer trans = getTransformer(o.getClass());
/* 147 */       if (trans != null) {
/* 148 */         value = trans.transform(o);
/*     */       }
/*     */     } 
/*     */     
/* 152 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/* 158 */     if (this == other) {
/* 159 */       return true;
/*     */     }
/* 161 */     if (other instanceof TransformerMap) {
/* 162 */       TransformerMap rhs = (TransformerMap)other;
/* 163 */       if (!this.defaultTransformer.equals(rhs.defaultTransformer)) {
/* 164 */         return false;
/*     */       }
/* 166 */       if (this.map.size() != rhs.map.size()) {
/* 167 */         return false;
/*     */       }
/* 169 */       for (Map.Entry<Class<?>, NumberTransformer> entry : this.map.entrySet()) {
/* 170 */         if (!((NumberTransformer)entry.getValue()).equals(rhs.map.get(entry.getKey()))) {
/* 171 */           return false;
/*     */         }
/*     */       } 
/* 174 */       return true;
/*     */     } 
/* 176 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 182 */     int hash = this.defaultTransformer.hashCode();
/* 183 */     for (NumberTransformer t : this.map.values()) {
/* 184 */       hash = hash * 31 + t.hashCode();
/*     */     }
/* 186 */     return hash;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/TransformerMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */