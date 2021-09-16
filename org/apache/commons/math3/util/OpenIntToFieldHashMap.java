/*     */ package org.apache.commons.math3.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.NoSuchElementException;
/*     */ import org.apache.commons.math3.Field;
/*     */ import org.apache.commons.math3.FieldElement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OpenIntToFieldHashMap<T extends FieldElement<T>>
/*     */   implements Serializable
/*     */ {
/*     */   protected static final byte FREE = 0;
/*     */   protected static final byte FULL = 1;
/*     */   protected static final byte REMOVED = 2;
/*     */   private static final long serialVersionUID = -9179080286849120720L;
/*     */   private static final float LOAD_FACTOR = 0.5F;
/*     */   private static final int DEFAULT_EXPECTED_SIZE = 16;
/*     */   private static final int RESIZE_MULTIPLIER = 2;
/*     */   private static final int PERTURB_SHIFT = 5;
/*     */   private final Field<T> field;
/*     */   private int[] keys;
/*     */   private T[] values;
/*     */   private byte[] states;
/*     */   private final T missingEntries;
/*     */   private int size;
/*     */   private int mask;
/*     */   private transient int count;
/*     */   
/*     */   public OpenIntToFieldHashMap(Field<T> field) {
/*  99 */     this(field, 16, (T)field.getZero());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenIntToFieldHashMap(Field<T> field, T missingEntries) {
/* 108 */     this(field, 16, missingEntries);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenIntToFieldHashMap(Field<T> field, int expectedSize) {
/* 117 */     this(field, expectedSize, (T)field.getZero());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenIntToFieldHashMap(Field<T> field, int expectedSize, T missingEntries) {
/* 128 */     this.field = field;
/* 129 */     int capacity = computeCapacity(expectedSize);
/* 130 */     this.keys = new int[capacity];
/* 131 */     this.values = buildArray(capacity);
/* 132 */     this.states = new byte[capacity];
/* 133 */     this.missingEntries = missingEntries;
/* 134 */     this.mask = capacity - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenIntToFieldHashMap(OpenIntToFieldHashMap<T> source) {
/* 142 */     this.field = source.field;
/* 143 */     int length = source.keys.length;
/* 144 */     this.keys = new int[length];
/* 145 */     System.arraycopy(source.keys, 0, this.keys, 0, length);
/* 146 */     this.values = buildArray(length);
/* 147 */     System.arraycopy(source.values, 0, this.values, 0, length);
/* 148 */     this.states = new byte[length];
/* 149 */     System.arraycopy(source.states, 0, this.states, 0, length);
/* 150 */     this.missingEntries = source.missingEntries;
/* 151 */     this.size = source.size;
/* 152 */     this.mask = source.mask;
/* 153 */     this.count = source.count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int computeCapacity(int expectedSize) {
/* 162 */     if (expectedSize == 0) {
/* 163 */       return 1;
/*     */     }
/* 165 */     int capacity = (int)FastMath.ceil((expectedSize / 0.5F));
/* 166 */     int powerOfTwo = Integer.highestOneBit(capacity);
/* 167 */     if (powerOfTwo == capacity) {
/* 168 */       return capacity;
/*     */     }
/* 170 */     return nextPowerOfTwo(capacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int nextPowerOfTwo(int i) {
/* 179 */     return Integer.highestOneBit(i) << 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T get(int key) {
/* 189 */     int hash = hashOf(key);
/* 190 */     int index = hash & this.mask;
/* 191 */     if (containsKey(key, index)) {
/* 192 */       return this.values[index];
/*     */     }
/*     */     
/* 195 */     if (this.states[index] == 0) {
/* 196 */       return this.missingEntries;
/*     */     }
/*     */     
/* 199 */     int j = index; int perturb;
/* 200 */     for (perturb = perturb(hash); this.states[index] != 0; perturb >>= 5) {
/* 201 */       j = probe(perturb, j);
/* 202 */       index = j & this.mask;
/* 203 */       if (containsKey(key, index)) {
/* 204 */         return this.values[index];
/*     */       }
/*     */     } 
/*     */     
/* 208 */     return this.missingEntries;
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
/* 219 */     int hash = hashOf(key);
/* 220 */     int index = hash & this.mask;
/* 221 */     if (containsKey(key, index)) {
/* 222 */       return true;
/*     */     }
/*     */     
/* 225 */     if (this.states[index] == 0) {
/* 226 */       return false;
/*     */     }
/*     */     
/* 229 */     int j = index; int perturb;
/* 230 */     for (perturb = perturb(hash); this.states[index] != 0; perturb >>= 5) {
/* 231 */       j = probe(perturb, j);
/* 232 */       index = j & this.mask;
/* 233 */       if (containsKey(key, index)) {
/* 234 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 238 */     return false;
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
/* 250 */     return new Iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int perturb(int hash) {
/* 259 */     return hash & Integer.MAX_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int findInsertionIndex(int key) {
/* 268 */     return findInsertionIndex(this.keys, this.states, key, this.mask);
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
/* 281 */     int hash = hashOf(key);
/* 282 */     int index = hash & mask;
/* 283 */     if (states[index] == 0)
/* 284 */       return index; 
/* 285 */     if (states[index] == 1 && keys[index] == key) {
/* 286 */       return changeIndexSign(index);
/*     */     }
/*     */     
/* 289 */     int perturb = perturb(hash);
/* 290 */     int j = index;
/* 291 */     if (states[index] == 1) {
/*     */       do {
/* 293 */         j = probe(perturb, j);
/* 294 */         index = j & mask;
/* 295 */         perturb >>= 5;
/*     */       }
/* 297 */       while (states[index] == 1 && keys[index] != key);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 303 */     if (states[index] == 0)
/* 304 */       return index; 
/* 305 */     if (states[index] == 1)
/*     */     {
/*     */       
/* 308 */       return changeIndexSign(index);
/*     */     }
/*     */     
/* 311 */     int firstRemoved = index;
/*     */     while (true) {
/* 313 */       j = probe(perturb, j);
/* 314 */       index = j & mask;
/*     */       
/* 316 */       if (states[index] == 0)
/* 317 */         return firstRemoved; 
/* 318 */       if (states[index] == 1 && keys[index] == key) {
/* 319 */         return changeIndexSign(index);
/*     */       }
/*     */       
/* 322 */       perturb >>= 5;
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
/* 335 */     return (j << 2) + j + perturb + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int changeIndexSign(int index) {
/* 344 */     return -index - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 352 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T remove(int key) {
/* 363 */     int hash = hashOf(key);
/* 364 */     int index = hash & this.mask;
/* 365 */     if (containsKey(key, index)) {
/* 366 */       return doRemove(index);
/*     */     }
/*     */     
/* 369 */     if (this.states[index] == 0) {
/* 370 */       return this.missingEntries;
/*     */     }
/*     */     
/* 373 */     int j = index; int perturb;
/* 374 */     for (perturb = perturb(hash); this.states[index] != 0; perturb >>= 5) {
/* 375 */       j = probe(perturb, j);
/* 376 */       index = j & this.mask;
/* 377 */       if (containsKey(key, index)) {
/* 378 */         return doRemove(index);
/*     */       }
/*     */     } 
/*     */     
/* 382 */     return this.missingEntries;
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
/* 394 */     return ((key != 0 || this.states[index] == 1) && this.keys[index] == key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private T doRemove(int index) {
/* 403 */     this.keys[index] = 0;
/* 404 */     this.states[index] = 2;
/* 405 */     T previous = this.values[index];
/* 406 */     this.values[index] = this.missingEntries;
/* 407 */     this.size--;
/* 408 */     this.count++;
/* 409 */     return previous;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T put(int key, T value) {
/* 419 */     int index = findInsertionIndex(key);
/* 420 */     T previous = this.missingEntries;
/* 421 */     boolean newMapping = true;
/* 422 */     if (index < 0) {
/* 423 */       index = changeIndexSign(index);
/* 424 */       previous = this.values[index];
/* 425 */       newMapping = false;
/*     */     } 
/* 427 */     this.keys[index] = key;
/* 428 */     this.states[index] = 1;
/* 429 */     this.values[index] = value;
/* 430 */     if (newMapping) {
/* 431 */       this.size++;
/* 432 */       if (shouldGrowTable()) {
/* 433 */         growTable();
/*     */       }
/* 435 */       this.count++;
/*     */     } 
/* 437 */     return previous;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void growTable() {
/* 446 */     int oldLength = this.states.length;
/* 447 */     int[] oldKeys = this.keys;
/* 448 */     T[] oldValues = this.values;
/* 449 */     byte[] oldStates = this.states;
/*     */     
/* 451 */     int newLength = 2 * oldLength;
/* 452 */     int[] newKeys = new int[newLength];
/* 453 */     T[] newValues = buildArray(newLength);
/* 454 */     byte[] newStates = new byte[newLength];
/* 455 */     int newMask = newLength - 1;
/* 456 */     for (int i = 0; i < oldLength; i++) {
/* 457 */       if (oldStates[i] == 1) {
/* 458 */         int key = oldKeys[i];
/* 459 */         int index = findInsertionIndex(newKeys, newStates, key, newMask);
/* 460 */         newKeys[index] = key;
/* 461 */         newValues[index] = oldValues[i];
/* 462 */         newStates[index] = 1;
/*     */       } 
/*     */     } 
/*     */     
/* 466 */     this.mask = newMask;
/* 467 */     this.keys = newKeys;
/* 468 */     this.values = newValues;
/* 469 */     this.states = newStates;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean shouldGrowTable() {
/* 478 */     return (this.size > (this.mask + 1) * 0.5F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int hashOf(int key) {
/* 487 */     int h = key ^ key >>> 20 ^ key >>> 12;
/* 488 */     return h ^ h >>> 7 ^ h >>> 4;
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
/* 510 */     private final int referenceCount = OpenIntToFieldHashMap.this.count;
/*     */     
/*     */     private int current;
/* 513 */     private int next = -1; private Iterator() {
/*     */       try {
/* 515 */         advance();
/* 516 */       } catch (NoSuchElementException noSuchElementException) {}
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
/* 527 */       return (this.next >= 0);
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
/* 538 */       if (this.referenceCount != OpenIntToFieldHashMap.this.count) {
/* 539 */         throw new ConcurrentModificationException();
/*     */       }
/* 541 */       if (this.current < 0) {
/* 542 */         throw new NoSuchElementException();
/*     */       }
/* 544 */       return OpenIntToFieldHashMap.this.keys[this.current];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public T value() throws ConcurrentModificationException, NoSuchElementException {
/* 555 */       if (this.referenceCount != OpenIntToFieldHashMap.this.count) {
/* 556 */         throw new ConcurrentModificationException();
/*     */       }
/* 558 */       if (this.current < 0) {
/* 559 */         throw new NoSuchElementException();
/*     */       }
/* 561 */       return (T)OpenIntToFieldHashMap.this.values[this.current];
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
/* 572 */       if (this.referenceCount != OpenIntToFieldHashMap.this.count) {
/* 573 */         throw new ConcurrentModificationException();
/*     */       }
/*     */ 
/*     */       
/* 577 */       this.current = this.next;
/*     */       
/*     */       while (true) {
/*     */         try {
/* 581 */           if (OpenIntToFieldHashMap.this.states[++this.next] != 1)
/*     */             continue; 
/*     */           break;
/* 584 */         } catch (ArrayIndexOutOfBoundsException e) {
/* 585 */           this.next = -2;
/* 586 */           if (this.current < 0) {
/* 587 */             throw new NoSuchElementException();
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
/* 604 */     stream.defaultReadObject();
/* 605 */     this.count = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private T[] buildArray(int length) {
/* 614 */     return (T[])Array.newInstance(this.field.getRuntimeClass(), length);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/OpenIntToFieldHashMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */