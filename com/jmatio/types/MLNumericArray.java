/*     */ package com.jmatio.types;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MLNumericArray<T extends Number>
/*     */   extends MLArray
/*     */   implements GenericArrayCreator<T>, ByteStorageSupport<T>
/*     */ {
/*     */   private ByteBuffer real;
/*     */   private ByteBuffer imaginary;
/*     */   private byte[] bytes;
/*     */   
/*     */   public MLNumericArray(String name, int[] dims, int type, int attributes) {
/*  37 */     super(name, dims, type, attributes);
/*  38 */     allocate();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void allocate() {
/*  44 */     this.real = ByteBuffer.allocate(getSize() * getBytesAllocated());
/*  45 */     if (isComplex())
/*     */     {
/*  47 */       this.imaginary = ByteBuffer.allocate(getSize() * getBytesAllocated());
/*     */     }
/*  49 */     this.bytes = new byte[getBytesAllocated()];
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
/*     */   public MLNumericArray(String name, int type, T[] vals, int m) {
/*  64 */     this(name, new int[] { m, vals.length / m }, type, 0);
/*     */     
/*  66 */     for (int i = 0; i < vals.length; i++)
/*     */     {
/*  68 */       set(vals[i], i);
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
/*     */   public T getReal(int m, int n) {
/*  80 */     return getReal(getIndex(m, n));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getReal(int index) {
/*  89 */     return _get(this.real, index);
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
/*     */   public void setReal(T value, int m, int n) {
/* 101 */     setReal(value, getIndex(m, n));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReal(T value, int index) {
/* 111 */     _set(this.real, value, index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReal(T[] vector) {
/* 120 */     if (vector.length != getSize())
/*     */     {
/* 122 */       throw new IllegalArgumentException("Matrix dimensions do not match. " + getSize() + " not " + vector.length);
/*     */     }
/* 124 */     System.arraycopy(vector, 0, this.real, 0, vector.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setImaginary(T value, int m, int n) {
/* 135 */     setImaginary(value, getIndex(m, n));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setImaginary(T value, int index) {
/* 145 */     if (isComplex())
/*     */     {
/* 147 */       _set(this.imaginary, value, index);
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
/*     */   public T getImaginary(int m, int n) {
/* 159 */     return getImaginary(getIndex(m, n));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getImaginary(int index) {
/* 167 */     return _get(this.imaginary, index);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(T value, int m, int n) {
/* 197 */     if (isComplex())
/*     */     {
/* 199 */       throw new IllegalStateException("Cannot use this method for Complex matrices");
/*     */     }
/* 201 */     setReal(value, m, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(T value, int index) {
/* 211 */     if (isComplex())
/*     */     {
/* 213 */       throw new IllegalStateException("Cannot use this method for Complex matrices");
/*     */     }
/* 215 */     setReal(value, index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T get(int m, int n) {
/* 226 */     if (isComplex())
/*     */     {
/* 228 */       throw new IllegalStateException("Cannot use this method for Complex matrices");
/*     */     }
/* 230 */     return getReal(m, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T get(int index) {
/* 238 */     if (isComplex())
/*     */     {
/* 240 */       throw new IllegalStateException("Cannot use this method for Complex matrices");
/*     */     }
/* 242 */     return _get(this.real, index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(T[] vector) {
/* 249 */     if (isComplex())
/*     */     {
/* 251 */       throw new IllegalStateException("Cannot use this method for Complex matrices");
/*     */     }
/* 253 */     setReal(vector);
/*     */   }
/*     */   
/*     */   private int getByteOffset(int index) {
/* 257 */     return index * getBytesAllocated();
/*     */   }
/*     */ 
/*     */   
/*     */   protected T _get(ByteBuffer buffer, int index) {
/* 262 */     buffer.position(getByteOffset(index));
/* 263 */     buffer.get(this.bytes, 0, this.bytes.length);
/* 264 */     return buldFromBytes(this.bytes);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _set(ByteBuffer buffer, T value, int index) {
/* 269 */     buffer.position(getByteOffset(index));
/* 270 */     buffer.put(getByteArray(value));
/*     */   }
/*     */ 
/*     */   
/*     */   public void putImaginaryByteBuffer(ByteBuffer buff) {
/* 275 */     if (!isComplex())
/*     */     {
/* 277 */       throw new RuntimeException("Array is not complex");
/*     */     }
/* 279 */     this.imaginary.rewind();
/* 280 */     this.imaginary.put(buff);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer getImaginaryByteBuffer() {
/* 285 */     return this.imaginary;
/*     */   }
/*     */ 
/*     */   
/*     */   public void putRealByteBuffer(ByteBuffer buff) {
/* 290 */     this.real.rewind();
/* 291 */     this.real.put(buff);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer getRealByteBuffer() {
/* 296 */     return this.real;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String contentToString() {
/* 304 */     StringBuffer sb = new StringBuffer();
/* 305 */     sb.append(this.name + " = \n");
/*     */     
/* 307 */     if (getSize() > 1000) {
/*     */       
/* 309 */       sb.append("Cannot display variables with more than 1000 elements.");
/* 310 */       return sb.toString();
/*     */     } 
/* 312 */     for (int m = 0; m < getM(); m++) {
/*     */       
/* 314 */       sb.append("\t");
/* 315 */       for (int n = 0; n < getN(); n++) {
/*     */         
/* 317 */         sb.append(getReal(m, n));
/* 318 */         if (isComplex())
/*     */         {
/* 320 */           sb.append("+" + getImaginary(m, n));
/*     */         }
/* 322 */         sb.append("\t");
/*     */       } 
/* 324 */       sb.append("\n");
/*     */     } 
/* 326 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 334 */     if (o instanceof MLNumericArray) {
/*     */       
/* 336 */       boolean result = (directByteBufferEquals(this.real, ((MLNumericArray)o).real) && Arrays.equals(this.dims, ((MLNumericArray)o).dims));
/*     */       
/* 338 */       if (isComplex() && result)
/*     */       {
/* 340 */         result &= directByteBufferEquals(this.imaginary, ((MLNumericArray)o).imaginary);
/*     */       }
/* 342 */       return result;
/*     */     } 
/* 344 */     return super.equals(o);
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
/*     */   private static boolean directByteBufferEquals(ByteBuffer buffa, ByteBuffer buffb) {
/* 356 */     if (buffa == buffb)
/*     */     {
/* 358 */       return true;
/*     */     }
/*     */     
/* 361 */     if (buffa == null || buffb == null)
/*     */     {
/* 363 */       return false;
/*     */     }
/*     */     
/* 366 */     buffa.rewind();
/* 367 */     buffb.rewind();
/*     */     
/* 369 */     int length = buffa.remaining();
/*     */     
/* 371 */     if (buffb.remaining() != length)
/*     */     {
/* 373 */       return false;
/*     */     }
/*     */     
/* 376 */     for (int i = 0; i < length; i++) {
/*     */       
/* 378 */       if (buffa.get() != buffb.get())
/*     */       {
/* 380 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 384 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 389 */     if (this.real != null)
/*     */     {
/* 391 */       this.real.clear();
/*     */     }
/* 393 */     if (this.imaginary != null)
/*     */     {
/* 395 */       this.real.clear();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/types/MLNumericArray.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */