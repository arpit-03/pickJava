/*     */ package org.apache.http.osgi.impl;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.AbstractList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WeakList<T>
/*     */   extends AbstractList<T>
/*     */ {
/*  46 */   private final List<WeakReference<T>> innerList = new ArrayList<WeakReference<T>>();
/*     */ 
/*     */ 
/*     */   
/*     */   public T get(int index) {
/*  51 */     return ((WeakReference<T>)this.innerList.get(index)).get();
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  56 */     checkReferences();
/*  57 */     return this.innerList.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(T t) {
/*  62 */     return this.innerList.add(new WeakReference<T>(t));
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/*  67 */     this.innerList.clear();
/*     */   }
/*     */   
/*     */   private void checkReferences() {
/*  71 */     ListIterator<WeakReference<T>> references = this.innerList.listIterator();
/*  72 */     while (references.hasNext()) {
/*  73 */       WeakReference<T> reference = references.next();
/*  74 */       if (reference.get() == null) {
/*  75 */         references.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<T> iterator() {
/*  82 */     return new WeakIterator<T>(this.innerList.iterator());
/*     */   }
/*     */   
/*     */   private class WeakIterator<T>
/*     */     implements Iterator<T>
/*     */   {
/*     */     private final Iterator<WeakReference<T>> innerIterator;
/*     */     private WeakReference<T> next;
/*     */     
/*     */     public WeakIterator(Iterator<WeakReference<T>> innerIterator) {
/*  92 */       this.innerIterator = innerIterator;
/*  93 */       fetchNext();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/*  98 */       return (this.next != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public T next() {
/* 103 */       if (this.next != null) {
/* 104 */         T result = this.next.get();
/* 105 */         fetchNext();
/* 106 */         return result;
/*     */       } 
/* 108 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     private void fetchNext() {
/* 113 */       while (this.innerIterator.hasNext()) {
/* 114 */         WeakReference<T> ref = this.innerIterator.next();
/* 115 */         T obj = ref.get();
/* 116 */         if (obj != null) {
/* 117 */           this.next = ref;
/*     */           return;
/*     */         } 
/*     */       } 
/* 121 */       this.next = null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 126 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/osgi/impl/WeakList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */