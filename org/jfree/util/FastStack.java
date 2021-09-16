/*     */ package org.jfree.util;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.EmptyStackException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FastStack
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private Object[] contents;
/*     */   private int size;
/*     */   private int initialSize;
/*     */   
/*     */   public FastStack() {
/*  52 */     this.initialSize = 10;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FastStack(int size) {
/*  61 */     this.initialSize = Math.max(1, size);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  71 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*  80 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void push(Object o) {
/*  89 */     if (this.contents == null) {
/*  90 */       this.contents = new Object[this.initialSize];
/*  91 */       this.contents[0] = o;
/*  92 */       this.size = 1;
/*     */       
/*     */       return;
/*     */     } 
/*  96 */     int oldSize = this.size;
/*  97 */     this.size++;
/*  98 */     if (this.contents.length == this.size) {
/*     */       
/* 100 */       Object[] newContents = new Object[this.size + this.initialSize];
/*     */       
/* 102 */       System.arraycopy(this.contents, 0, newContents, 0, this.size);
/* 103 */       this.contents = newContents;
/*     */     } 
/* 105 */     this.contents[oldSize] = o;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object peek() {
/* 114 */     if (this.size == 0) {
/* 115 */       throw new EmptyStackException();
/*     */     }
/* 117 */     return this.contents[this.size - 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object pop() {
/* 126 */     if (this.size == 0) {
/* 127 */       throw new EmptyStackException();
/*     */     }
/* 129 */     this.size--;
/* 130 */     Object retval = this.contents[this.size];
/* 131 */     this.contents[this.size] = null;
/* 132 */     return retval;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 142 */       FastStack stack = (FastStack)super.clone();
/* 143 */       if (this.contents != null) {
/* 144 */         stack.contents = (Object[])this.contents.clone();
/*     */       }
/* 146 */       return stack;
/*     */     }
/* 148 */     catch (CloneNotSupportedException cne) {
/* 149 */       throw new IllegalStateException("Clone not supported? Why?");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 157 */     this.size = 0;
/* 158 */     if (this.contents != null) {
/* 159 */       Arrays.fill(this.contents, (Object)null);
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
/*     */   public Object get(int index) {
/* 171 */     if (index >= this.size) {
/* 172 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 174 */     return this.contents[index];
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/util/FastStack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */