/*     */ package edu.mines.jtk.util;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ArrayQueue<E>
/*     */ {
/*     */   private static final int MIN_LENGTH = 32;
/*     */   private int _length;
/*     */   private Object[] _array;
/*     */   private int _first;
/*     */   private int _size;
/*     */   
/*     */   public ArrayQueue() {
/* 185 */     this._length = 0;
/*     */     
/* 187 */     this._first = 0;
/* 188 */     this._size = 0; this._length = 32; this._array = new Object[this._length]; } public ArrayQueue(int capacity) { this._length = 0; this._first = 0; this._size = 0; if (capacity < 32)
/*     */       capacity = 32; 
/*     */     this._length = capacity;
/*     */     this._array = new Object[this._length]; }
/*     */   public void add(E e) { if (this._size == this._length)
/*     */       resize(this._size * 2); 
/*     */     int index = this._first + this._size;
/*     */     if (index >= this._length)
/*     */       index -= this._length; 
/*     */     this._array[index] = e;
/* 198 */     this._size++; } private void resize(int length) { if (length < 32) {
/* 199 */       length = 32;
/*     */     }
/*     */     
/* 202 */     if (length != this._length) {
/*     */ 
/*     */       
/* 205 */       Object[] array = new Object[length];
/*     */ 
/*     */       
/* 208 */       int n1 = this._length - this._first;
/* 209 */       if (n1 > this._size)
/* 210 */         n1 = this._size; 
/* 211 */       if (n1 > 0) {
/* 212 */         System.arraycopy(this._array, this._first, array, 0, n1);
/*     */       }
/*     */       
/* 215 */       int n2 = this._first + this._size - this._length;
/* 216 */       if (n2 > 0) {
/* 217 */         System.arraycopy(this._array, 0, array, n1, n2);
/*     */       }
/*     */       
/* 220 */       this._length = length;
/* 221 */       this._array = array;
/* 222 */       this._first = 0;
/*     */     }  }
/*     */ 
/*     */   
/*     */   public E first() {
/*     */     if (this._size == 0)
/*     */       throw new NoSuchElementException(); 
/*     */     E e = (E)this._array[this._first];
/*     */     return e;
/*     */   }
/*     */   
/*     */   public E remove() {
/*     */     if (this._size == 0)
/*     */       throw new NoSuchElementException(); 
/*     */     E e = (E)this._array[this._first];
/*     */     this._first++;
/*     */     if (this._first == this._length)
/*     */       this._first = 0; 
/*     */     this._size--;
/*     */     if (this._size * 3 < this._length)
/*     */       resize(this._size * 2); 
/*     */     return e;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*     */     return (this._size == 0);
/*     */   }
/*     */   
/*     */   public void ensureCapacity(int capacity) {
/*     */     if (capacity < this._length)
/*     */       capacity = this._length; 
/*     */     resize(capacity);
/*     */   }
/*     */   
/*     */   public int size() {
/*     */     return this._size;
/*     */   }
/*     */   
/*     */   public void trimToSize() {
/*     */     resize(this._size);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/ArrayQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */