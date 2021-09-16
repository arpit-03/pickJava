/*     */ package org.jfree.chart.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.util.ObjectUtilities;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StandardEntityCollection
/*     */   implements EntityCollection, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5384773031184897047L;
/*  79 */   private List entities = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEntityCount() {
/*  89 */     return this.entities.size();
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
/*     */   public ChartEntity getEntity(int index) {
/* 103 */     return this.entities.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 111 */     this.entities.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(ChartEntity entity) {
/* 121 */     ParamChecks.nullNotPermitted(entity, "entity");
/* 122 */     this.entities.add(entity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAll(EntityCollection collection) {
/* 133 */     this.entities.addAll(collection.getEntities());
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
/*     */   public ChartEntity getEntity(double x, double y) {
/* 147 */     int entityCount = this.entities.size();
/* 148 */     for (int i = entityCount - 1; i >= 0; i--) {
/* 149 */       ChartEntity entity = this.entities.get(i);
/* 150 */       if (entity.getArea().contains(x, y)) {
/* 151 */         return entity;
/*     */       }
/*     */     } 
/* 154 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection getEntities() {
/* 164 */     return Collections.unmodifiableCollection(this.entities);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator iterator() {
/* 174 */     return this.entities.iterator();
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
/* 186 */     if (obj == this) {
/* 187 */       return true;
/*     */     }
/* 189 */     if (obj instanceof StandardEntityCollection) {
/* 190 */       StandardEntityCollection that = (StandardEntityCollection)obj;
/* 191 */       return ObjectUtilities.equal(this.entities, that.entities);
/*     */     } 
/* 193 */     return false;
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
/* 206 */     StandardEntityCollection clone = (StandardEntityCollection)super.clone();
/* 207 */     clone.entities = new ArrayList(this.entities.size());
/* 208 */     for (int i = 0; i < this.entities.size(); i++) {
/* 209 */       ChartEntity entity = this.entities.get(i);
/* 210 */       clone.entities.add(entity.clone());
/*     */     } 
/* 212 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/entity/StandardEntityCollection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */