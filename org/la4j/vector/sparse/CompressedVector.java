/*     */ package org.la4j.vector.sparse;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import org.la4j.Vector;
/*     */ import org.la4j.Vectors;
/*     */ import org.la4j.iterator.VectorIterator;
/*     */ import org.la4j.vector.SparseVector;
/*     */ import org.la4j.vector.VectorFactory;
/*     */ import org.la4j.vector.functor.VectorFunction;
/*     */ import org.la4j.vector.functor.VectorProcedure;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CompressedVector
/*     */   extends SparseVector
/*     */ {
/*     */   private static final byte VECTOR_TAG = 16;
/*     */   private static final int MINIMUM_SIZE = 32;
/*     */   private double[] values;
/*     */   private int[] indices;
/*     */   
/*     */   public static CompressedVector zero(int length) {
/*  67 */     return new CompressedVector(length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CompressedVector zero(int length, int capacity) {
/*  75 */     return new CompressedVector(length, capacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CompressedVector random(int length, double density, Random random) {
/*  83 */     if (density < 0.0D || density > 1.0D) {
/*  84 */       throw new IllegalArgumentException("The density value should be between 0 and 1.0");
/*     */     }
/*     */     
/*  87 */     int cardinality = (int)(length * density);
/*  88 */     double[] values = new double[cardinality];
/*  89 */     int[] indices = new int[cardinality];
/*     */     
/*  91 */     for (int i = 0; i < cardinality; i++) {
/*  92 */       values[i] = random.nextDouble();
/*  93 */       indices[i] = random.nextInt(length);
/*     */     } 
/*     */     
/*  96 */     Arrays.sort(indices);
/*     */     
/*  98 */     return new CompressedVector(length, cardinality, values, indices);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CompressedVector fromArray(double[] array) {
/* 106 */     int length = array.length;
/* 107 */     CompressedVector result = zero(length);
/*     */     
/* 109 */     for (int i = 0; i < length; i++) {
/* 110 */       if (array[i] != 0.0D) {
/* 111 */         result.set(i, array[i]);
/*     */       }
/*     */     } 
/*     */     
/* 115 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CompressedVector fromBinary(byte[] array) {
/* 126 */     ByteBuffer buffer = ByteBuffer.wrap(array);
/*     */     
/* 128 */     if (buffer.get() != 16) {
/* 129 */       throw new IllegalArgumentException("Can not decode CompressedVector from the given byte array.");
/*     */     }
/*     */     
/* 132 */     int length = buffer.getInt();
/* 133 */     int cardinality = buffer.getInt();
/* 134 */     double[] values = new double[cardinality];
/* 135 */     int[] indices = new int[cardinality];
/*     */     
/* 137 */     for (int i = 0; i < cardinality; i++) {
/* 138 */       indices[i] = buffer.getInt();
/* 139 */       values[i] = buffer.getDouble();
/*     */     } 
/*     */     
/* 142 */     return new CompressedVector(length, cardinality, values, indices);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CompressedVector fromCSV(String csv) {
/* 153 */     return (CompressedVector)Vector.fromCSV(csv).to(Vectors.COMPRESSED);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CompressedVector fromMatrixMarket(String mm) {
/* 164 */     return (CompressedVector)Vector.fromMatrixMarket(mm).to(Vectors.COMPRESSED);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CompressedVector fromCollection(Collection<? extends Number> list) {
/* 175 */     return (CompressedVector)Vector.fromCollection(list).to(Vectors.COMPRESSED);
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
/*     */   public static CompressedVector fromMap(Map<Integer, ? extends Number> map, int length) {
/* 189 */     int cardinality = map.size();
/* 190 */     int[] indices = new int[cardinality];
/* 191 */     double[] values = new double[cardinality];
/* 192 */     int i = 0;
/* 193 */     for (Map.Entry<Integer, ? extends Number> entry : map.entrySet()) {
/* 194 */       int index = ((Integer)entry.getKey()).intValue();
/* 195 */       if (index < 0 || index >= length) {
/* 196 */         throw new IllegalArgumentException("Check your map: Index must be 0..n-1");
/*     */       }
/* 198 */       indices[i] = index;
/* 199 */       values[i] = ((Number)entry.getValue()).doubleValue();
/* 200 */       i++;
/*     */     } 
/* 202 */     return new CompressedVector(length, cardinality, values, indices);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompressedVector() {
/* 209 */     this(0);
/*     */   }
/*     */   
/*     */   public CompressedVector(int length) {
/* 213 */     this(length, 0);
/*     */   }
/*     */   
/*     */   public CompressedVector(int length, int capacity) {
/* 217 */     super(length);
/* 218 */     int alignedSize = align(length, capacity);
/* 219 */     this.values = new double[alignedSize];
/* 220 */     this.indices = new int[alignedSize];
/*     */   }
/*     */   
/*     */   public CompressedVector(int length, int cardinality, double[] values, int[] indices) {
/* 224 */     super(length, cardinality);
/* 225 */     this.values = values;
/* 226 */     this.indices = indices;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getOrElse(int i, double defaultValue) {
/* 231 */     ensureIndexIsInBounds(i);
/* 232 */     int k = searchForIndex(i);
/*     */     
/* 234 */     if (k < this.cardinality && this.indices[k] == i) {
/* 235 */       return this.values[k];
/*     */     }
/*     */     
/* 238 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(int i, double value) {
/* 243 */     ensureIndexIsInBounds(i);
/* 244 */     int k = searchForIndex(i);
/*     */     
/* 246 */     if (k < this.cardinality && this.indices[k] == i) {
/*     */       
/* 248 */       if (value != 0.0D) {
/* 249 */         this.values[k] = value;
/*     */       } else {
/* 251 */         remove(k);
/*     */       } 
/*     */     } else {
/* 254 */       insert(k, i, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAll(double value) {
/* 260 */     if (value == 0.0D) {
/* 261 */       this.cardinality = 0;
/*     */     } else {
/* 263 */       if (this.values.length < this.length) {
/* 264 */         this.values = new double[this.length];
/* 265 */         this.indices = new int[this.length];
/*     */       } 
/*     */       
/* 268 */       for (int i = 0; i < this.length; i++) {
/* 269 */         this.indices[i] = i;
/* 270 */         this.values[i] = value;
/*     */       } 
/*     */       
/* 273 */       this.cardinality = this.length;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void swapElements(int i, int j) {
/* 279 */     if (i == j) {
/*     */       return;
/*     */     }
/*     */     
/* 283 */     int ii = searchForIndex(i);
/* 284 */     int jj = searchForIndex(j);
/*     */     
/* 286 */     boolean iiNotZero = (ii < this.cardinality && i == this.indices[ii]);
/* 287 */     boolean jjNotZero = (jj < this.cardinality && j == this.indices[jj]);
/*     */     
/* 289 */     if (iiNotZero && jjNotZero) {
/*     */       
/* 291 */       double sd = this.values[ii];
/* 292 */       this.values[ii] = this.values[jj];
/* 293 */       this.values[jj] = sd;
/*     */     } else {
/*     */       
/* 296 */       double notZero = this.values[iiNotZero ? ii : jj];
/*     */       
/* 298 */       int leftIndex = (ii < jj) ? ii : jj;
/* 299 */       int rightIndex = (ii > jj) ? ii : jj;
/*     */       
/* 301 */       if (((iiNotZero && leftIndex == ii) || (jjNotZero && leftIndex == jj)) && ii != jj) {
/*     */ 
/*     */         
/* 304 */         System.arraycopy(this.values, leftIndex + 1, this.values, leftIndex, this.cardinality - leftIndex);
/*     */         
/* 306 */         System.arraycopy(this.values, rightIndex - 1, this.values, rightIndex, this.cardinality - rightIndex);
/*     */ 
/*     */         
/* 309 */         this.values[rightIndex - 1] = notZero;
/*     */         
/* 311 */         System.arraycopy(this.indices, leftIndex + 1, this.indices, leftIndex, this.cardinality - leftIndex);
/*     */         
/* 313 */         System.arraycopy(this.indices, rightIndex - 1, this.indices, rightIndex, this.cardinality - rightIndex);
/*     */ 
/*     */         
/* 316 */         this.indices[rightIndex - 1] = jjNotZero ? i : j;
/*     */       }
/* 318 */       else if ((iiNotZero && rightIndex == ii) || (jjNotZero && rightIndex == jj)) {
/*     */ 
/*     */         
/* 321 */         System.arraycopy(this.values, rightIndex + 1, this.values, rightIndex, this.cardinality - rightIndex);
/*     */         
/* 323 */         System.arraycopy(this.values, leftIndex, this.values, leftIndex + 1, this.cardinality - leftIndex);
/*     */ 
/*     */         
/* 326 */         this.values[leftIndex] = notZero;
/*     */         
/* 328 */         System.arraycopy(this.indices, rightIndex + 1, this.indices, rightIndex, this.cardinality - rightIndex);
/*     */         
/* 330 */         System.arraycopy(this.indices, leftIndex, this.indices, leftIndex + 1, this.cardinality - leftIndex);
/*     */ 
/*     */         
/* 333 */         this.indices[leftIndex] = jjNotZero ? i : j;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector copyOfLength(int length) {
/* 340 */     ensureLengthIsCorrect(length);
/*     */     
/* 342 */     int $cardinality = (length >= this.length) ? this.cardinality : searchForIndex(length);
/*     */     
/* 344 */     int capacity = align(length, $cardinality);
/*     */     
/* 346 */     double[] $values = new double[capacity];
/* 347 */     int[] $indices = new int[capacity];
/*     */     
/* 349 */     System.arraycopy(this.values, 0, $values, 0, $cardinality);
/* 350 */     System.arraycopy(this.indices, 0, $indices, 0, $cardinality);
/*     */     
/* 352 */     return (Vector)new CompressedVector(length, $cardinality, $values, $indices);
/*     */   }
/*     */ 
/*     */   
/*     */   public void each(VectorProcedure procedure) {
/* 357 */     int k = 0;
/* 358 */     for (int i = 0; i < this.length; i++) {
/* 359 */       if (k < this.cardinality && this.indices[k] == i) {
/* 360 */         procedure.apply(i, this.values[k++]);
/*     */       } else {
/* 362 */         procedure.apply(i, 0.0D);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void eachNonZero(VectorProcedure procedure) {
/* 369 */     for (int i = 0; i < this.cardinality; i++) {
/* 370 */       procedure.apply(this.indices[i], this.values[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateAt(int i, VectorFunction function) {
/* 376 */     int k = searchForIndex(i);
/*     */     
/* 378 */     if (k < this.cardinality && this.indices[k] == i) {
/* 379 */       double value = function.evaluate(i, this.values[k]);
/*     */ 
/*     */       
/* 382 */       if (value != 0.0D) {
/* 383 */         this.values[k] = value;
/*     */       } else {
/* 385 */         remove(k);
/*     */       } 
/*     */     } else {
/* 388 */       insert(k, i, function.evaluate(i, 0.0D));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean nonZeroAt(int i) {
/* 394 */     int k = searchForIndex(i);
/* 395 */     return (k < this.cardinality && this.indices[k] == i);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Vector> T to(VectorFactory<T> factory) {
/* 400 */     if (factory.outputClass == CompressedVector.class) {
/* 401 */       return (T)factory.outputClass.cast(this);
/*     */     }
/*     */     
/* 404 */     return (T)super.to(factory);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector blankOfLength(int length) {
/* 409 */     return (Vector)zero(length);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] toBinary() {
/* 414 */     int size = 9 + 8 * this.cardinality + 8 * this.cardinality;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 420 */     ByteBuffer buffer = ByteBuffer.allocate(size);
/*     */     
/* 422 */     buffer.put((byte)16);
/* 423 */     buffer.putInt(this.length);
/* 424 */     buffer.putInt(this.cardinality);
/*     */     
/* 426 */     for (int i = 0; i < this.cardinality; i++) {
/* 427 */       buffer.putInt(this.indices[i]);
/* 428 */       buffer.putDouble(this.values[i]);
/*     */     } 
/*     */     
/* 431 */     return buffer.array();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int searchForIndex(int i) {
/* 442 */     if (this.cardinality == 0 || i > this.indices[this.cardinality - 1]) {
/* 443 */       return this.cardinality;
/*     */     }
/*     */     
/* 446 */     int left = 0;
/* 447 */     int right = this.cardinality;
/*     */     
/* 449 */     while (left < right) {
/* 450 */       int p = (left + right) / 2;
/* 451 */       if (this.indices[p] > i) {
/* 452 */         right = p; continue;
/* 453 */       }  if (this.indices[p] < i) {
/* 454 */         left = p + 1; continue;
/*     */       } 
/* 456 */       return p;
/*     */     } 
/*     */ 
/*     */     
/* 460 */     return left;
/*     */   }
/*     */ 
/*     */   
/*     */   private void insert(int k, int i, double value) {
/* 465 */     if (value == 0.0D) {
/*     */       return;
/*     */     }
/*     */     
/* 469 */     if (this.values.length < this.cardinality + 1) {
/* 470 */       growUp();
/*     */     }
/*     */     
/* 473 */     if (this.cardinality - k > 0) {
/* 474 */       System.arraycopy(this.values, k, this.values, k + 1, this.cardinality - k);
/* 475 */       System.arraycopy(this.indices, k, this.indices, k + 1, this.cardinality - k);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 483 */     this.values[k] = value;
/* 484 */     this.indices[k] = i;
/*     */     
/* 486 */     this.cardinality++;
/*     */   }
/*     */ 
/*     */   
/*     */   private void remove(int k) {
/* 491 */     this.cardinality--;
/*     */     
/* 493 */     if (this.cardinality - k > 0) {
/* 494 */       System.arraycopy(this.values, k + 1, this.values, k, this.cardinality - k);
/* 495 */       System.arraycopy(this.indices, k + 1, this.indices, k, this.cardinality - k);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void growUp() {
/* 505 */     if (this.values.length == this.length)
/*     */     {
/* 507 */       throw new IllegalStateException("This vector can't grow up.");
/*     */     }
/*     */     
/* 510 */     int capacity = Math.min(this.length, this.cardinality * 3 / 2 + 1);
/*     */     
/* 512 */     double[] $values = new double[capacity];
/* 513 */     int[] $indices = new int[capacity];
/*     */     
/* 515 */     System.arraycopy(this.values, 0, $values, 0, this.cardinality);
/* 516 */     System.arraycopy(this.indices, 0, $indices, 0, this.cardinality);
/*     */     
/* 518 */     this.values = $values;
/* 519 */     this.indices = $indices;
/*     */   }
/*     */   
/*     */   private int align(int length, int capacity) {
/* 523 */     if (capacity < 0) {
/* 524 */       fail("Cardinality should be positive: " + capacity + ".");
/*     */     }
/* 526 */     if (capacity > length) {
/* 527 */       fail("Cardinality should be less then or equal to capacity: " + capacity + ".");
/*     */     }
/* 529 */     return Math.min(length, (capacity / 32 + 1) * 32);
/*     */   }
/*     */ 
/*     */   
/*     */   public VectorIterator nonZeroIterator() {
/* 534 */     return new VectorIterator(this.length) {
/*     */         private boolean currentIsRemoved = false;
/* 536 */         private int k = -1;
/* 537 */         private int removedIndex = -1;
/*     */ 
/*     */         
/*     */         public int index() {
/* 541 */           return this.currentIsRemoved ? this.removedIndex : CompressedVector.this.indices[this.k];
/*     */         }
/*     */ 
/*     */         
/*     */         public double get() {
/* 546 */           return this.currentIsRemoved ? 0.0D : CompressedVector.this.values[this.k];
/*     */         }
/*     */ 
/*     */         
/*     */         public void set(double value) {
/* 551 */           if (value == 0.0D && !this.currentIsRemoved) {
/* 552 */             this.currentIsRemoved = true;
/* 553 */             this.removedIndex = CompressedVector.this.indices[this.k];
/* 554 */             CompressedVector.this.remove(this.k--);
/* 555 */           } else if (value != 0.0D && !this.currentIsRemoved) {
/* 556 */             CompressedVector.this.values[this.k] = value;
/*     */           } else {
/* 558 */             this.currentIsRemoved = false;
/* 559 */             CompressedVector.this.insert(++this.k, this.removedIndex, value);
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/* 565 */           return (this.k + 1 < CompressedVector.this.cardinality);
/*     */         }
/*     */ 
/*     */         
/*     */         public Double next() {
/* 570 */           this.currentIsRemoved = false;
/* 571 */           return Double.valueOf(CompressedVector.this.values[++this.k]);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public VectorIterator iterator() {
/* 578 */     return new VectorIterator(this.length) {
/* 579 */         private int k = 0;
/* 580 */         private int i = -1;
/*     */         
/*     */         private boolean currentNonZero = false;
/*     */         
/*     */         public int index() {
/* 585 */           return this.i;
/*     */         }
/*     */ 
/*     */         
/*     */         public double get() {
/* 590 */           return this.currentNonZero ? CompressedVector.this.values[this.k] : 0.0D;
/*     */         }
/*     */ 
/*     */         
/*     */         public void set(double value) {
/* 595 */           if (this.currentNonZero) {
/* 596 */             if (value == 0.0D) {
/* 597 */               CompressedVector.this.remove(this.k);
/* 598 */               this.currentNonZero = false;
/*     */             } else {
/* 600 */               CompressedVector.this.values[this.k] = value;
/*     */             } 
/*     */           } else {
/* 603 */             CompressedVector.this.insert(this.k, this.i, value);
/* 604 */             this.currentNonZero = true;
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/* 610 */           return (this.i + 1 < this.length);
/*     */         }
/*     */ 
/*     */         
/*     */         public Double next() {
/* 615 */           if (this.currentNonZero) {
/* 616 */             this.k++;
/*     */           }
/*     */           
/* 619 */           this.i++;
/* 620 */           this.currentNonZero = (this.k < CompressedVector.this.cardinality && CompressedVector.this.indices[this.k] == this.i);
/*     */           
/* 622 */           return Double.valueOf(get());
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/vector/sparse/CompressedVector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */