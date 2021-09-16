/*     */ package org.jfree.data;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
/*     */ import org.jfree.chart.util.ParamChecks;
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
/*     */ public class KeyedValueComparator
/*     */   implements Comparator, Serializable
/*     */ {
/*     */   private KeyedValueComparatorType type;
/*     */   private SortOrder order;
/*     */   
/*     */   public KeyedValueComparator(KeyedValueComparatorType type, SortOrder order) {
/*  73 */     ParamChecks.nullNotPermitted(type, "type");
/*  74 */     ParamChecks.nullNotPermitted(order, "order");
/*  75 */     this.type = type;
/*  76 */     this.order = order;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyedValueComparatorType getType() {
/*  85 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SortOrder getOrder() {
/*  94 */     return this.order;
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
/*     */   public int compare(Object o1, Object o2) {
/*     */     int result;
/* 109 */     if (o2 == null) {
/* 110 */       return -1;
/*     */     }
/* 112 */     if (o1 == null) {
/* 113 */       return 1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 118 */     KeyedValue kv1 = (KeyedValue)o1;
/* 119 */     KeyedValue kv2 = (KeyedValue)o2;
/*     */     
/* 121 */     if (this.type == KeyedValueComparatorType.BY_KEY) {
/* 122 */       if (this.order.equals(SortOrder.ASCENDING)) {
/* 123 */         result = kv1.getKey().compareTo(kv2.getKey());
/*     */       }
/* 125 */       else if (this.order.equals(SortOrder.DESCENDING)) {
/* 126 */         result = kv2.getKey().compareTo(kv1.getKey());
/*     */       } else {
/*     */         
/* 129 */         throw new IllegalArgumentException("Unrecognised sort order.");
/*     */       }
/*     */     
/* 132 */     } else if (this.type == KeyedValueComparatorType.BY_VALUE) {
/* 133 */       Number n1 = kv1.getValue();
/* 134 */       Number n2 = kv2.getValue();
/* 135 */       if (n2 == null) {
/* 136 */         return -1;
/*     */       }
/* 138 */       if (n1 == null) {
/* 139 */         return 1;
/*     */       }
/* 141 */       double d1 = n1.doubleValue();
/* 142 */       double d2 = n2.doubleValue();
/* 143 */       if (this.order.equals(SortOrder.ASCENDING)) {
/* 144 */         if (d1 > d2) {
/* 145 */           result = 1;
/*     */         }
/* 147 */         else if (d1 < d2) {
/* 148 */           result = -1;
/*     */         } else {
/*     */           
/* 151 */           result = 0;
/*     */         }
/*     */       
/* 154 */       } else if (this.order.equals(SortOrder.DESCENDING)) {
/* 155 */         if (d1 > d2) {
/* 156 */           result = -1;
/*     */         }
/* 158 */         else if (d1 < d2) {
/* 159 */           result = 1;
/*     */         } else {
/*     */           
/* 162 */           result = 0;
/*     */         } 
/*     */       } else {
/*     */         
/* 166 */         throw new IllegalArgumentException("Unrecognised sort order.");
/*     */       } 
/*     */     } else {
/*     */       
/* 170 */       throw new IllegalArgumentException("Unrecognised type.");
/*     */     } 
/*     */     
/* 173 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/KeyedValueComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */