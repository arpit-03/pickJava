/*     */ package org.apache.commons.math3.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.ConcurrentModificationException;
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
/*     */ public class OpenIntToDoubleHashMap
/*     */   implements Serializable
/*     */ {
/*     */   protected static final byte FREE = 0;
/*     */   protected static final byte FULL = 1;
/*     */   protected static final byte REMOVED = 2;
/*     */   private static final long serialVersionUID = -3646337053166149105L;
/*     */   private static final float LOAD_FACTOR = 0.5F;
/*     */   private static final int DEFAULT_EXPECTED_SIZE = 16;
/*     */   private static final int RESIZE_MULTIPLIER = 2;
/*     */   private static final int PERTURB_SHIFT = 5;
/*     */   private int[] keys;
/*     */   private double[] values;
/*     */   private byte[] states;
/*     */   private final double missingEntries;
/*     */   private int size;
/*     */   private int mask;
/*     */   private transient int count;
/*     */   
/*     */   public OpenIntToDoubleHashMap() {
/*  91 */     this(16, Double.NaN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenIntToDoubleHashMap(double missingEntries) {
/*  99 */     this(16, missingEntries);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenIntToDoubleHashMap(int expectedSize) {
/* 107 */     this(expectedSize, Double.NaN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenIntToDoubleHashMap(int expectedSize, double missingEntries) {
/* 117 */     int capacity = computeCapacity(expectedSize);
/* 118 */     this.keys = new int[capacity];
/* 119 */     this.values = new double[capacity];
/* 120 */     this.states = new byte[capacity];
/* 121 */     this.missingEntries = missingEntries;
/* 122 */     this.mask = capacity - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenIntToDoubleHashMap(OpenIntToDoubleHashMap source) {
/* 130 */     int length = source.keys.length;
/* 131 */     this.keys = new int[length];
/* 132 */     System.arraycopy(source.keys, 0, this.keys, 0, length);
/* 133 */     this.values = new double[length];
/* 134 */     System.arraycopy(source.values, 0, this.values, 0, length);
/* 135 */     this.states = new byte[length];
/* 136 */     System.arraycopy(source.states, 0, this.states, 0, length);
/* 137 */     this.missingEntries = source.missingEntries;
/* 138 */     this.size = source.size;
/* 139 */     this.mask = source.mask;
/* 140 */     this.count = source.count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int computeCapacity(int expectedSize) {
/* 149 */     if (expectedSize == 0) {
/* 150 */       return 1;
/*     */     }
/* 152 */     int capacity = (int)FastMath.ceil((expectedSize / 0.5F));
/* 153 */     int powerOfTwo = Integer.highestOneBit(capacity);
/* 154 */     if (powerOfTwo == capacity) {
/* 155 */       return capacity;
/*     */     }
/* 157 */     return nextPowerOfTwo(capacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int nextPowerOfTwo(int i) {
/* 166 */     return Integer.highestOneBit(i) << 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double get(int key) {
/* 176 */     int hash = hashOf(key);
/* 177 */     int index = hash & this.mask;
/* 178 */     if (containsKey(key, index)) {
/* 179 */       return this.values[index];
/*     */     }
/*     */     
/* 182 */     if (this.states[index] == 0) {
/* 183 */       return this.missingEntries;
/*     */     }
/*     */     
/* 186 */     int j = index; int perturb;
/* 187 */     for (perturb = perturb(hash); this.states[index] != 0; perturb >>= 5) {
/* 188 */       j = probe(perturb, j);
/* 189 */       index = j & this.mask;
/* 190 */       if (containsKey(key, index)) {
/* 191 */         return this.values[index];
/*     */       }
/*     */     } 
/*     */     
/* 195 */     return this.missingEntries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsKey(int key) {
/* 206 */     int hash = hashOf(key);
/* 207 */     int index = hash & this.mask;
/* 208 */     if (containsKey(key, index)) {
/* 209 */       return true;
/*     */     }
/*     */     
/* 212 */     if (this.states[index] == 0) {
/* 213 */       return false;
/*     */     }
/*     */     
/* 216 */     int j = index; int perturb;
/* 217 */     for (perturb = perturb(hash); this.states[index] != 0; perturb >>= 5) {
/* 218 */       j = probe(perturb, j);
/* 219 */       index = j & this.mask;
/* 220 */       if (containsKey(key, index)) {
/* 221 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 225 */     return false;
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
/*     */   public Iterator iterator() {
/* 237 */     return new Iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int perturb(int hash) {
/* 246 */     return hash & Integer.MAX_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int findInsertionIndex(int key) {
/* 255 */     return findInsertionIndex(this.keys, this.states, key, this.mask);
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
/*     */   private static int findInsertionIndex(int[] keys, byte[] states, int key, int mask) {
/* 268 */     int hash = hashOf(key);
/* 269 */     int index = hash & mask;
/* 270 */     if (states[index] == 0)
/* 271 */       return index; 
/* 272 */     if (states[index] == 1 && keys[index] == key) {
/* 273 */       return changeIndexSign(index);
/*     */     }
/*     */     
/* 276 */     int perturb = perturb(hash);
/* 277 */     int j = index;
/* 278 */     if (states[index] == 1) {
/*     */       do {
/* 280 */         j = probe(perturb, j);
/* 281 */         index = j & mask;
/* 282 */         perturb >>= 5;
/*     */       }
/* 284 */       while (states[index] == 1 && keys[index] != key);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 290 */     if (states[index] == 0)
/* 291 */       return index; 
/* 292 */     if (states[index] == 1)
/*     */     {
/*     */       
/* 295 */       return changeIndexSign(index);
/*     */     }
/*     */     
/* 298 */     int firstRemoved = index;
/*     */     while (true) {
/* 300 */       j = probe(perturb, j);
/* 301 */       index = j & mask;
/*     */       
/* 303 */       if (states[index] == 0)
/* 304 */         return firstRemoved; 
/* 305 */       if (states[index] == 1 && keys[index] == key) {
/* 306 */         return changeIndexSign(index);
/*     */       }
/*     */       
/* 309 */       perturb >>= 5;
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
/*     */   
/*     */   private static int probe(int perturb, int j) {
/* 322 */     return (j << 2) + j + perturb + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int changeIndexSign(int index) {
/* 331 */     return -index - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 339 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double remove(int key) {
/* 350 */     int hash = hashOf(key);
/* 351 */     int index = hash & this.mask;
/* 352 */     if (containsKey(key, index)) {
/* 353 */       return doRemove(index);
/*     */     }
/*     */     
/* 356 */     if (this.states[index] == 0) {
/* 357 */       return this.missingEntries;
/*     */     }
/*     */     
/* 360 */     int j = index; int perturb;
/* 361 */     for (perturb = perturb(hash); this.states[index] != 0; perturb >>= 5) {
/* 362 */       j = probe(perturb, j);
/* 363 */       index = j & this.mask;
/* 364 */       if (containsKey(key, index)) {
/* 365 */         return doRemove(index);
/*     */       }
/*     */     } 
/*     */     
/* 369 */     return this.missingEntries;
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
/*     */   private boolean containsKey(int key, int index) {
/* 381 */     return ((key != 0 || this.states[index] == 1) && this.keys[index] == key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double doRemove(int index) {
/* 390 */     this.keys[index] = 0;
/* 391 */     this.states[index] = 2;
/* 392 */     double previous = this.values[index];
/* 393 */     this.values[index] = this.missingEntries;
/* 394 */     this.size--;
/* 395 */     this.count++;
/* 396 */     return previous;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double put(int key, double value) {
/* 406 */     int index = findInsertionIndex(key);
/* 407 */     double previous = this.missingEntries;
/* 408 */     boolean newMapping = true;
/* 409 */     if (index < 0) {
/* 410 */       index = changeIndexSign(index);
/* 411 */       previous = this.values[index];
/* 412 */       newMapping = false;
/*     */     } 
/* 414 */     this.keys[index] = key;
/* 415 */     this.states[index] = 1;
/* 416 */     this.values[index] = value;
/* 417 */     if (newMapping) {
/* 418 */       this.size++;
/* 419 */       if (shouldGrowTable()) {
/* 420 */         growTable();
/*     */       }
/* 422 */       this.count++;
/*     */     } 
/* 424 */     return previous;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void growTable() {
/* 433 */     int oldLength = this.states.length;
/* 434 */     int[] oldKeys = this.keys;
/* 435 */     double[] oldValues = this.values;
/* 436 */     byte[] oldStates = this.states;
/*     */     
/* 438 */     int newLength = 2 * oldLength;
/* 439 */     int[] newKeys = new int[newLength];
/* 440 */     double[] newValues = new double[newLength];
/* 441 */     byte[] newStates = new byte[newLength];
/* 442 */     int newMask = newLength - 1;
/* 443 */     for (int i = 0; i < oldLength; i++) {
/* 444 */       if (oldStates[i] == 1) {
/* 445 */         int key = oldKeys[i];
/* 446 */         int index = findInsertionIndex(newKeys, newStates, key, newMask);
/* 447 */         newKeys[index] = key;
/* 448 */         newValues[index] = oldValues[i];
/* 449 */         newStates[index] = 1;
/*     */       } 
/*     */     } 
/*     */     
/* 453 */     this.mask = newMask;
/* 454 */     this.keys = newKeys;
/* 455 */     this.values = newValues;
/* 456 */     this.states = newStates;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean shouldGrowTable() {
/* 465 */     return (this.size > (this.mask + 1) * 0.5F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int hashOf(int key) {
/* 474 */     int h = key ^ key >>> 20 ^ key >>> 12;
/* 475 */     return h ^ h >>> 7 ^ h >>> 4;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public class Iterator
/*     */   {
/* 497 */     private final int referenceCount = OpenIntToDoubleHashMap.this.count;
/*     */     
/*     */     private int current;
/* 500 */     private int next = -1; private Iterator() {
/*     */       try {
/* 502 */         advance();
/* 503 */       } catch (NoSuchElementException noSuchElementException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 514 */       return (this.next >= 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int key() throws ConcurrentModificationException, NoSuchElementException {
/* 525 */       if (this.referenceCount != OpenIntToDoubleHashMap.this.count) {
/* 526 */         throw new ConcurrentModificationException();
/*     */       }
/* 528 */       if (this.current < 0) {
/* 529 */         throw new NoSuchElementException();
/*     */       }
/* 531 */       return OpenIntToDoubleHashMap.this.keys[this.current];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double value() throws ConcurrentModificationException, NoSuchElementException {
/* 542 */       if (this.referenceCount != OpenIntToDoubleHashMap.this.count) {
/* 543 */         throw new ConcurrentModificationException();
/*     */       }
/* 545 */       if (this.current < 0) {
/* 546 */         throw new NoSuchElementException();
/*     */       }
/* 548 */       return OpenIntToDoubleHashMap.this.values[this.current];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void advance() throws ConcurrentModificationException, NoSuchElementException {
/* 559 */       if (this.referenceCount != OpenIntToDoubleHashMap.this.count) {
/* 560 */         throw new ConcurrentModificationException();
/*     */       }
/*     */ 
/*     */       
/* 564 */       this.current = this.next;
/*     */       
/*     */       while (true) {
/*     */         try {
/* 568 */           if (OpenIntToDoubleHashMap.this.states[++this.next] != 1)
/*     */             continue; 
/*     */           break;
/* 571 */         } catch (ArrayIndexOutOfBoundsException e) {
/* 572 */           this.next = -2;
/* 573 */           if (this.current < 0) {
/* 574 */             throw new NoSuchElementException();
/*     */           }
/*     */         } 
/*     */         return;
/*     */       } 
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
/*     */   
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 591 */     stream.defaultReadObject();
/* 592 */     this.count = 0;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/OpenIntToDoubleHashMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */