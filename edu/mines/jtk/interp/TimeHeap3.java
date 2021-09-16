/*     */ package edu.mines.jtk.interp;
/*     */ 
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Check;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class TimeHeap3
/*     */ {
/*     */   private Type _type;
/*     */   private int _n1;
/*     */   private int _n2;
/*     */   private int _n3;
/*     */   private int _n;
/*     */   private int[][][] _imap;
/*     */   private Entry[] _e;
/*     */   
/*     */   public enum Type
/*     */   {
/*  46 */     MIN, MAX;
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
/*     */ 
/*     */   
/*     */   public static class Entry
/*     */   {
/*     */     public int i1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int i2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int i3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float time;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int mark;
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
/*     */ 
/*     */   
/*     */   public Type getType() {
/*     */     return this._type;
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
/*     */   
/*     */   public int getN1() {
/*     */     return this._n1;
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
/*     */   
/*     */   public int getN2() {
/*     */     return this._n2;
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
/*     */   
/*     */   public int getN3() {
/*     */     return this._n3;
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
/*     */   
/*     */   public void insert(int i1, int i2, int i3, float time) {
/*     */     insert(i1, i2, i3, time, 0);
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
/*     */   
/*     */   public TimeHeap3(Type type, int n1, int n2, int n3) {
/* 228 */     this._e = new Entry[1024]; this._type = type; this._n1 = n1; this._n2 = n2; this._n3 = n3; this._imap = new int[n3][n2][n1];
/*     */   } public void insert(int i1, int i2, int i3, float time, int mark) { int i = indexOf(i1, i2, i3); Check.argument((i < 0), "entry with indices (i1,i2) is not in the heap"); i = this._n; if (this._n == this._e.length)
/*     */       grow(this._n + 1);  Entry ei = this._e[i]; if (ei == null)
/*     */       ei = new Entry();  ei.i1 = i1; ei.i2 = i2; ei.i3 = i3;
/*     */     ei.time = time;
/*     */     ei.mark = mark;
/*     */     set(i, ei);
/*     */     siftUp(i);
/* 236 */     this._n++; } private int indexOf(int i1, int i2, int i3) { int i = this._imap[i3][i2][i1];
/* 237 */     if (i < 0 || i >= this._n)
/* 238 */       return -1; 
/* 239 */     Entry ei = this._e[i];
/* 240 */     if (ei.i1 != i1 || ei.i2 != i2 || ei.i3 != i3)
/* 241 */       return -1; 
/* 242 */     return i; }
/*     */   public void reduce(int i1, int i2, int i3, float time) { int i = indexOf(i1, i2, i3); Check.argument((i >= 0), "entry with indices (i1,i2) is in the heap"); Check.argument((time < (this._e[i]).time), "specified time less than time in heap");
/*     */     (this._e[i]).time = time;
/*     */     if (this._type == Type.MIN) {
/*     */       siftUp(i);
/*     */     } else {
/*     */       siftDown(i);
/* 249 */     }  } private void set(int i, Entry ei) { this._e[i] = ei;
/* 250 */     this._imap[ei.i3][ei.i2][ei.i1] = i; }
/*     */   public Entry remove() { Check.state((this._n > 0), "heap is not empty"); Entry e0 = this._e[0]; this._n--; if (this._n > 0) { set(0, this._e[this._n]); set(this._n, e0); siftDown(0); }
/*     */      return e0; }
/*     */   public boolean contains(int i1, int i2, int i3) { return (indexOf(i1, i2, i3) >= 0); }
/*     */   public void clear() { this._n = 0; }
/*     */   public int size() { return this._n; }
/*     */   public boolean isEmpty() { return (this._n == 0); }
/* 257 */   public void dump() { dump("", 0); } private void siftDown(int i) { Entry ei = this._e[i];
/* 258 */     float eit = ei.time;
/* 259 */     int m = this._n >>> 1;
/* 260 */     while (i < m) {
/* 261 */       int c = (i << 1) + 1;
/* 262 */       int r = c + 1;
/* 263 */       Entry ec = this._e[c];
/* 264 */       if (this._type == Type.MIN) {
/* 265 */         if (r < this._n && (this._e[r]).time < ec.time)
/* 266 */           ec = this._e[c = r]; 
/* 267 */         if (eit <= ec.time)
/*     */           break; 
/*     */       } else {
/* 270 */         if (r < this._n && (this._e[r]).time > ec.time)
/* 271 */           ec = this._e[c = r]; 
/* 272 */         if (eit >= ec.time)
/*     */           break; 
/*     */       } 
/* 275 */       set(i, ec);
/* 276 */       i = c;
/*     */     } 
/* 278 */     if (ei != this._e[i]) {
/* 279 */       set(i, ei);
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void siftUp(int i) {
/* 286 */     Entry ei = this._e[i];
/* 287 */     float eit = ei.time;
/* 288 */     while (i > 0) {
/* 289 */       int p = i - 1 >>> 1;
/* 290 */       Entry ep = this._e[p];
/* 291 */       if ((this._type == Type.MIN) ? (
/* 292 */         eit >= ep.time) : (
/*     */ 
/*     */         
/* 295 */         eit <= ep.time)) {
/*     */         break;
/*     */       }
/* 298 */       set(i, ep);
/* 299 */       i = p;
/*     */     } 
/* 301 */     if (ei != this._e[i]) {
/* 302 */       set(i, ei);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void grow(int minCapacity) {
/* 309 */     if (minCapacity < 0)
/* 310 */       throw new OutOfMemoryError(); 
/* 311 */     int oldCapacity = this._e.length;
/* 312 */     int newCapacity = oldCapacity * 2;
/* 313 */     if (newCapacity < 0)
/* 314 */       newCapacity = Integer.MAX_VALUE; 
/* 315 */     if (newCapacity < minCapacity)
/* 316 */       newCapacity = minCapacity; 
/* 317 */     Entry[] e = new Entry[newCapacity];
/* 318 */     System.arraycopy(this._e, 0, e, 0, oldCapacity);
/* 319 */     this._e = e;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void dump(String s, int i) {
/* 326 */     if (i < this._n) {
/* 327 */       s = s + "  ";
/* 328 */       Entry e = this._e[i];
/* 329 */       System.out.println(s + e.i1 + " " + e.i2 + " " + e.i3 + " " + e.time);
/* 330 */       dump(s, 2 * i + 1);
/* 331 */       dump(s, 2 * i + 2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 339 */     testHeap(new TimeHeap3(Type.MIN, 9, 11, 13));
/* 340 */     testHeap(new TimeHeap3(Type.MAX, 9, 11, 13));
/*     */   }
/*     */   
/*     */   private static void testHeap(TimeHeap3 heap) {
/* 344 */     int n1 = heap.getN1();
/* 345 */     int n2 = heap.getN2();
/* 346 */     int n3 = heap.getN3();
/* 347 */     int n = n1 * n2 * n3;
/* 348 */     float[] s = ArrayMath.randfloat(n);
/* 349 */     float[][][] t = ArrayMath.reshape(n1, n2, n3, s); int i3, j;
/* 350 */     for (i3 = 0, j = 0; i3 < n3; i3++) {
/* 351 */       for (int i2 = 0; i2 < n2; i2++) {
/* 352 */         for (int i1 = 0; i1 < n1; i1++, j++) {
/* 353 */           float ti = t[i3][i2][i1];
/* 354 */           heap.insert(i1, i2, i3, ti);
/* 355 */           s[j] = ti;
/*     */         } 
/*     */       } 
/*     */     } 
/* 359 */     for (i3 = 0, j = 0; i3 < n3; i3++) {
/* 360 */       for (int i2 = 0; i2 < n2; i2++) {
/* 361 */         for (int i1 = 0; i1 < n1; i1++, j++) {
/* 362 */           s[j] = s[j] - 0.5F;
/* 363 */           t[i3][i2][i1] = t[i3][i2][i1] - 0.5F;
/* 364 */           heap.reduce(i1, i2, i3, t[i3][i2][i1]);
/*     */         } 
/*     */       } 
/*     */     } 
/* 368 */     assert !heap.isEmpty();
/* 369 */     assert heap.size() == n;
/* 370 */     ArrayMath.quickSort(s);
/* 371 */     if (heap.getType() == Type.MAX)
/* 372 */       s = ArrayMath.reverse(s); 
/* 373 */     for (int i = 0; i < n; i++) {
/* 374 */       Entry e = heap.remove();
/* 375 */       float ti = e.time;
/* 376 */       assert ti == s[i];
/*     */     } 
/* 378 */     assert heap.isEmpty();
/* 379 */     assert heap.size() == 0;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/interp/TimeHeap3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */