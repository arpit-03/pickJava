/*     */ package Catalano.Core.Structs;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BinaryHeap<E extends Comparable<E>>
/*     */ {
/*  35 */   private int count = 0;
/*  36 */   List<E> heap = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int count() {
/*  43 */     return this.count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*  51 */     return this.heap.size();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BinaryHeap() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public BinaryHeap(Comparable[] keys) {
/*     */     byte b;
/*     */     int i;
/*     */     Comparable[] arrayOfComparable;
/*  64 */     for (i = (arrayOfComparable = keys).length, b = 0; b < i; ) { Comparable comparable = arrayOfComparable[b];
/*  65 */       this.heap.add((E)comparable); b++; }
/*     */     
/*  67 */     for (int k = this.heap.size() / 2 - 1; k >= 0; k--) {
/*  68 */       downHeap(k, this.heap.get(k));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(E node) {
/*  77 */     this.heap.add(null);
/*  78 */     int k = this.heap.size() - 1;
/*  79 */     upHeap(k, node);
/*  80 */     this.count++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E remove() {
/*  88 */     Comparable comparable1 = (Comparable)this.heap.get(0);
/*  89 */     Comparable comparable2 = (Comparable)this.heap.remove(this.heap.size() - 1);
/*  90 */     downHeap(0, (E)comparable2);
/*  91 */     this.count--;
/*  92 */     return (E)comparable1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(E item) {
/* 100 */     this.heap.remove(item);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E min() {
/* 108 */     return this.heap.get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 116 */     return this.heap.isEmpty();
/*     */   }
/*     */   
/*     */   private void upHeap(int k, E node) {
/* 120 */     while (k > 0) {
/* 121 */       int parent = (k - 1) / 2;
/* 122 */       Comparable comparable = (Comparable)this.heap.get(parent);
/* 123 */       if (node.compareTo(comparable) >= 0) {
/*     */         break;
/*     */       }
/* 126 */       this.heap.set(k, (E)comparable);
/* 127 */       k = parent;
/*     */     } 
/* 129 */     this.heap.set(k, node);
/*     */   }
/*     */   
/*     */   private void downHeap(int k, E node) {
/* 133 */     if (this.heap.isEmpty()) {
/*     */       return;
/*     */     }
/* 136 */     while (k < this.heap.size() / 2) {
/* 137 */       int child = 2 * k + 1;
/* 138 */       if (child < this.heap.size() - 1 && ((Comparable<Comparable>)this.heap.get(child)).compareTo((Comparable)this.heap.get(child + 1)) > 0) {
/* 139 */         child++;
/*     */       }
/* 141 */       if (node.compareTo((Comparable)this.heap.get(child)) < 0) {
/*     */         break;
/*     */       }
/* 144 */       this.heap.set(k, this.heap.get(child));
/* 145 */       k = child;
/*     */     } 
/* 147 */     this.heap.set(k, node);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Core/Structs/BinaryHeap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */