/*     */ package Catalano.Math;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SparseArray
/*     */   implements Iterable<SparseArray.Entry>
/*     */ {
/*     */   private List<Entry> array;
/*     */   
/*     */   public class Entry
/*     */   {
/*     */     public int i;
/*     */     public double x;
/*     */     
/*     */     public Entry(int i, double x) {
/*  58 */       this.i = i;
/*  59 */       this.x = x;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseArray() {
/*  69 */     this(10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SparseArray(int initialCapacity) {
/*  79 */     this.array = new ArrayList<>(initialCapacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*  87 */     return this.array.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  95 */     return this.array.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<Entry> iterator() {
/* 103 */     return this.array.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double get(int i) {
/* 112 */     for (Entry e : this.array) {
/* 113 */       if (e.i == i) {
/* 114 */         return e.x;
/*     */       }
/*     */     } 
/*     */     
/* 118 */     return 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean set(int i, double x) {
/* 128 */     if (x == 0.0D) {
/* 129 */       remove(i);
/* 130 */       return false;
/*     */     } 
/*     */     
/* 133 */     Iterator<Entry> it = this.array.iterator();
/* 134 */     for (int k = 0; it.hasNext(); k++) {
/* 135 */       Entry e = it.next();
/* 136 */       if (e.i == i) {
/* 137 */         e.x = x;
/* 138 */         return false;
/* 139 */       }  if (e.i > i) {
/* 140 */         this.array.add(k, new Entry(i, x));
/* 141 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 145 */     this.array.add(new Entry(i, x));
/* 146 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void append(int i, double x) {
/* 155 */     if (x != 0.0D) {
/* 156 */       this.array.add(new Entry(i, x));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(int i) {
/* 165 */     Iterator<Entry> it = this.array.iterator();
/* 166 */     while (it.hasNext()) {
/* 167 */       Entry e = it.next();
/* 168 */       if (e.i == i) {
/* 169 */         it.remove();
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/SparseArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */