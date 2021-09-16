/*     */ package org.apache.commons.math3.util;
/*     */ 
/*     */ import java.util.NoSuchElementException;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultidimensionalCounter
/*     */   implements Iterable<Integer>
/*     */ {
/*     */   private final int dimension;
/*     */   private final int[] uniCounterOffset;
/*     */   private final int[] size;
/*     */   private final int totalSize;
/*     */   private final int last;
/*     */   
/*     */   public class Iterator
/*     */     implements java.util.Iterator<Integer>
/*     */   {
/*  75 */     private final int[] counter = new int[MultidimensionalCounter.this.dimension];
/*     */ 
/*     */ 
/*     */     
/*  79 */     private int count = -1;
/*     */ 
/*     */ 
/*     */     
/*  83 */     private final int maxCount = MultidimensionalCounter.this.totalSize - 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Iterator() {
/*  90 */       this.counter[MultidimensionalCounter.this.last] = -1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/*  97 */       return (this.count < this.maxCount);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Integer next() {
/* 107 */       if (!hasNext()) {
/* 108 */         throw new NoSuchElementException();
/*     */       }
/*     */       
/* 111 */       for (int i = MultidimensionalCounter.this.last; i >= 0; i--) {
/* 112 */         if (this.counter[i] == MultidimensionalCounter.this.size[i] - 1) {
/* 113 */           this.counter[i] = 0;
/*     */         } else {
/* 115 */           this.counter[i] = this.counter[i] + 1;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 120 */       return Integer.valueOf(++this.count);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getCount() {
/* 129 */       return this.count;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int[] getCounts() {
/* 137 */       return MathArrays.copyOf(this.counter);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getCount(int dim) {
/* 152 */       return this.counter[dim];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void remove() {
/* 159 */       throw new UnsupportedOperationException();
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
/*     */   public MultidimensionalCounter(int... size) throws NotStrictlyPositiveException {
/* 171 */     this.dimension = size.length;
/* 172 */     this.size = MathArrays.copyOf(size);
/*     */     
/* 174 */     this.uniCounterOffset = new int[this.dimension];
/*     */     
/* 176 */     this.last = this.dimension - 1;
/* 177 */     int tS = size[this.last];
/* 178 */     for (int i = 0; i < this.last; i++) {
/* 179 */       int count = 1;
/* 180 */       for (int j = i + 1; j < this.dimension; j++) {
/* 181 */         count *= size[j];
/*     */       }
/* 183 */       this.uniCounterOffset[i] = count;
/* 184 */       tS *= size[i];
/*     */     } 
/* 186 */     this.uniCounterOffset[this.last] = 0;
/*     */     
/* 188 */     if (tS <= 0) {
/* 189 */       throw new NotStrictlyPositiveException(Integer.valueOf(tS));
/*     */     }
/*     */     
/* 192 */     this.totalSize = tS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator iterator() {
/* 201 */     return new Iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDimension() {
/* 210 */     return this.dimension;
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
/*     */   public int[] getCounts(int index) throws OutOfRangeException {
/* 222 */     if (index < 0 || index >= this.totalSize)
/*     */     {
/* 224 */       throw new OutOfRangeException(Integer.valueOf(index), Integer.valueOf(0), Integer.valueOf(this.totalSize));
/*     */     }
/*     */     
/* 227 */     int[] indices = new int[this.dimension];
/*     */     
/* 229 */     int count = 0;
/* 230 */     for (int i = 0; i < this.last; i++) {
/* 231 */       int idx = 0;
/* 232 */       int offset = this.uniCounterOffset[i];
/* 233 */       while (count <= index) {
/* 234 */         count += offset;
/* 235 */         idx++;
/*     */       } 
/* 237 */       idx--;
/* 238 */       count -= offset;
/* 239 */       indices[i] = idx;
/*     */     } 
/*     */     
/* 242 */     indices[this.last] = index - count;
/*     */     
/* 244 */     return indices;
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
/*     */   public int getCount(int... c) throws OutOfRangeException, DimensionMismatchException {
/* 260 */     if (c.length != this.dimension) {
/* 261 */       throw new DimensionMismatchException(c.length, this.dimension);
/*     */     }
/* 263 */     int count = 0;
/* 264 */     for (int i = 0; i < this.dimension; i++) {
/* 265 */       int index = c[i];
/* 266 */       if (index < 0 || index >= this.size[i])
/*     */       {
/* 268 */         throw new OutOfRangeException(Integer.valueOf(index), Integer.valueOf(0), Integer.valueOf(this.size[i] - 1));
/*     */       }
/* 270 */       count += this.uniCounterOffset[i] * c[i];
/*     */     } 
/* 272 */     return count + c[this.last];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 281 */     return this.totalSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getSizes() {
/* 289 */     return MathArrays.copyOf(this.size);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 297 */     StringBuilder sb = new StringBuilder();
/* 298 */     for (int i = 0; i < this.dimension; i++) {
/* 299 */       sb.append("[").append(getCount(new int[] { i })).append("]");
/*     */     } 
/* 301 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/MultidimensionalCounter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */