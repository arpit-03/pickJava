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
/*     */ 
/*     */ class TimeHeap2
/*     */ {
/*     */   private Type _type;
/*     */   private int _n1;
/*     */   private int _n2;
/*     */   private int _n;
/*     */   private int[][] _imap;
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
/*     */   public void insert(int i1, int i2, float time) {
/*     */     insert(i1, i2, time, 0);
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
/*     */   public void insert(int i1, int i2, float time, int mark) {
/*     */     int i = indexOf(i1, i2);
/*     */     Check.argument((i < 0), "entry with indices (i1,i2) is not in the heap");
/*     */     i = this._n;
/*     */     if (this._n == this._e.length) {
/*     */       grow(this._n + 1);
/*     */     }
/*     */     Entry ei = this._e[i];
/*     */     if (ei == null) {
/*     */       ei = new Entry();
/*     */     }
/*     */     ei.i1 = i1;
/*     */     ei.i2 = i2;
/*     */     ei.time = time;
/*     */     ei.mark = mark;
/*     */     set(i, ei);
/*     */     siftUp(i);
/*     */     this._n++;
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
/*     */   public TimeHeap2(Type type, int n1, int n2) {
/* 213 */     this._e = new Entry[1024]; this._type = type; this._n1 = n1; this._n2 = n2; this._imap = new int[n2][n1];
/*     */   } public void reduce(int i1, int i2, float time) { int i = indexOf(i1, i2); Check.argument((i >= 0), "entry with indices (i1,i2) is in the heap");
/*     */     Check.argument((time < (this._e[i]).time), "specified time less than time in heap");
/*     */     (this._e[i]).time = time;
/*     */     if (this._type == Type.MIN) {
/*     */       siftUp(i);
/*     */     } else {
/*     */       siftDown(i);
/* 221 */     }  } private int indexOf(int i1, int i2) { int i = this._imap[i2][i1];
/* 222 */     if (i < 0 || i >= this._n)
/* 223 */       return -1; 
/* 224 */     Entry ei = this._e[i];
/* 225 */     if (ei.i1 != i1 || ei.i2 != i2)
/* 226 */       return -1; 
/* 227 */     return i; }
/*     */   public Entry remove() { Check.state((this._n > 0), "heap is not empty"); Entry e0 = this._e[0]; this._n--;
/*     */     if (this._n > 0) {
/*     */       set(0, this._e[this._n]);
/*     */       set(this._n, e0);
/*     */       siftDown(0);
/*     */     } 
/* 234 */     return e0; } private void set(int i, Entry ei) { this._e[i] = ei;
/* 235 */     this._imap[ei.i2][ei.i1] = i; }
/*     */   public boolean contains(int i1, int i2) { return (indexOf(i1, i2) >= 0); }
/*     */   public void clear() { this._n = 0; }
/*     */   public int size() { return this._n; }
/*     */   public boolean isEmpty() { return (this._n == 0); } public void dump() {
/*     */     dump("", 0);
/*     */   } private void siftDown(int i) {
/* 242 */     Entry ei = this._e[i];
/* 243 */     float eit = ei.time;
/* 244 */     int m = this._n >>> 1;
/* 245 */     while (i < m) {
/* 246 */       int c = (i << 1) + 1;
/* 247 */       int r = c + 1;
/* 248 */       Entry ec = this._e[c];
/* 249 */       if (this._type == Type.MIN) {
/* 250 */         if (r < this._n && (this._e[r]).time < ec.time)
/* 251 */           ec = this._e[c = r]; 
/* 252 */         if (eit <= ec.time)
/*     */           break; 
/*     */       } else {
/* 255 */         if (r < this._n && (this._e[r]).time > ec.time)
/* 256 */           ec = this._e[c = r]; 
/* 257 */         if (eit >= ec.time)
/*     */           break; 
/*     */       } 
/* 260 */       set(i, ec);
/* 261 */       i = c;
/*     */     } 
/* 263 */     if (ei != this._e[i]) {
/* 264 */       set(i, ei);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void siftUp(int i) {
/* 271 */     Entry ei = this._e[i];
/* 272 */     float eit = ei.time;
/* 273 */     while (i > 0) {
/* 274 */       int p = i - 1 >>> 1;
/* 275 */       Entry ep = this._e[p];
/* 276 */       if ((this._type == Type.MIN) ? (
/* 277 */         eit >= ep.time) : (
/*     */ 
/*     */         
/* 280 */         eit <= ep.time)) {
/*     */         break;
/*     */       }
/* 283 */       set(i, ep);
/* 284 */       i = p;
/*     */     } 
/* 286 */     if (ei != this._e[i]) {
/* 287 */       set(i, ei);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void grow(int minCapacity) {
/* 294 */     if (minCapacity < 0)
/* 295 */       throw new OutOfMemoryError(); 
/* 296 */     int oldCapacity = this._e.length;
/* 297 */     int newCapacity = oldCapacity * 2;
/* 298 */     if (newCapacity < 0)
/* 299 */       newCapacity = Integer.MAX_VALUE; 
/* 300 */     if (newCapacity < minCapacity)
/* 301 */       newCapacity = minCapacity; 
/* 302 */     Entry[] e = new Entry[newCapacity];
/* 303 */     System.arraycopy(this._e, 0, e, 0, oldCapacity);
/* 304 */     this._e = e;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void dump(String s, int i) {
/* 311 */     if (i < this._n) {
/* 312 */       s = s + "  ";
/* 313 */       Entry e = this._e[i];
/* 314 */       System.out.println(s + e.i1 + " " + e.i2 + " " + e.time);
/* 315 */       dump(s, 2 * i + 1);
/* 316 */       dump(s, 2 * i + 2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 324 */     testHeap(new TimeHeap2(Type.MIN, 9, 11));
/* 325 */     testHeap(new TimeHeap2(Type.MAX, 9, 11));
/*     */   }
/*     */   
/*     */   private static void testHeap(TimeHeap2 heap) {
/* 329 */     int n1 = heap.getN1();
/* 330 */     int n2 = heap.getN2();
/* 331 */     int n = n1 * n2;
/* 332 */     float[] s = ArrayMath.randfloat(n);
/* 333 */     float[][] t = ArrayMath.reshape(n1, n2, s); int i2, j;
/* 334 */     for (i2 = 0, j = 0; i2 < n2; i2++) {
/* 335 */       for (int i1 = 0; i1 < n1; i1++, j++) {
/* 336 */         float ti = t[i2][i1];
/* 337 */         heap.insert(i1, i2, ti);
/* 338 */         s[j] = ti;
/*     */       } 
/*     */     } 
/* 341 */     for (i2 = 0, j = 0; i2 < n2; i2++) {
/* 342 */       for (int i1 = 0; i1 < n1; i1++, j++) {
/* 343 */         s[j] = s[j] - 0.5F;
/* 344 */         t[i2][i1] = t[i2][i1] - 0.5F;
/* 345 */         heap.reduce(i1, i2, t[i2][i1]);
/*     */       } 
/*     */     } 
/* 348 */     assert !heap.isEmpty();
/* 349 */     assert heap.size() == n;
/* 350 */     ArrayMath.quickSort(s);
/* 351 */     if (heap.getType() == Type.MAX)
/* 352 */       s = ArrayMath.reverse(s); 
/* 353 */     for (int i = 0; i < n; i++) {
/* 354 */       Entry e = heap.remove();
/* 355 */       float ti = e.time;
/* 356 */       assert ti == s[i];
/*     */     } 
/* 358 */     assert heap.isEmpty();
/* 359 */     assert heap.size() == 0;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/interp/TimeHeap2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */