/*     */ package org.jfree.ui;
/*     */ 
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Layer
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -1470104570733183430L;
/*  59 */   public static final Layer FOREGROUND = new Layer("Layer.FOREGROUND");
/*     */ 
/*     */   
/*  62 */   public static final Layer BACKGROUND = new Layer("Layer.BACKGROUND");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String name;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Layer(String name) {
/*  73 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  82 */     return this.name;
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
/*     */   public boolean equals(Object o) {
/*  95 */     if (this == o) {
/*  96 */       return true;
/*     */     }
/*  98 */     if (!(o instanceof Layer)) {
/*  99 */       return false;
/*     */     }
/*     */     
/* 102 */     Layer layer = (Layer)o;
/* 103 */     if (!this.name.equals(layer.name)) {
/* 104 */       return false;
/*     */     }
/*     */     
/* 107 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 117 */     return this.name.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() throws ObjectStreamException {
/* 128 */     Layer result = null;
/* 129 */     if (equals(FOREGROUND)) {
/* 130 */       result = FOREGROUND;
/*     */     }
/* 132 */     else if (equals(BACKGROUND)) {
/* 133 */       result = BACKGROUND;
/*     */     } 
/* 135 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/Layer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */