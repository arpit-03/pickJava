/*     */ package org.la4j.vector.dense;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import org.la4j.Vector;
/*     */ import org.la4j.Vectors;
/*     */ import org.la4j.vector.DenseVector;
/*     */ import org.la4j.vector.VectorFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicVector
/*     */   extends DenseVector
/*     */ {
/*     */   private static final byte VECTOR_TAG = 0;
/*     */   private double[] self;
/*     */   
/*     */   public static BasicVector zero(int length) {
/*  58 */     return new BasicVector(length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BasicVector constant(int length, double value) {
/*  66 */     double[] array = new double[length];
/*  67 */     Arrays.fill(array, value);
/*     */     
/*  69 */     return new BasicVector(array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BasicVector unit(int length) {
/*  76 */     return constant(length, 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BasicVector random(int length, Random random) {
/*  84 */     double[] array = new double[length];
/*  85 */     for (int i = 0; i < length; i++) {
/*  86 */       array[i] = random.nextDouble();
/*     */     }
/*     */     
/*  89 */     return new BasicVector(array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BasicVector fromArray(double[] array) {
/*  97 */     return new BasicVector(array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BasicVector fromBinary(byte[] array) {
/* 108 */     ByteBuffer buffer = ByteBuffer.wrap(array);
/*     */     
/* 110 */     if (buffer.get() != 0) {
/* 111 */       throw new IllegalArgumentException("Can not decode BasicVector from the given byte array.");
/*     */     }
/*     */     
/* 114 */     double[] values = new double[buffer.getInt()];
/* 115 */     for (int i = 0; i < values.length; i++) {
/* 116 */       values[i] = buffer.getDouble();
/*     */     }
/*     */     
/* 119 */     return new BasicVector(values);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BasicVector fromCSV(String csv) {
/* 130 */     return (BasicVector)Vector.fromCSV(csv).to(Vectors.BASIC);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BasicVector fromMatrixMarket(String mm) {
/* 141 */     return (BasicVector)Vector.fromMatrixMarket(mm).to(Vectors.BASIC);
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
/*     */   public static BasicVector fromCollection(Collection<? extends Number> list) {
/* 153 */     double[] self = new double[list.size()];
/* 154 */     int i = 0;
/* 155 */     for (Number x : list) {
/* 156 */       self[i] = x.doubleValue();
/* 157 */       i++;
/*     */     } 
/* 159 */     return fromArray(self);
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
/*     */   public static BasicVector fromMap(Map<Integer, ? extends Number> map, int length) {
/* 172 */     return (BasicVector)Vector.fromMap(map, length).to(Vectors.BASIC);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicVector() {
/* 178 */     this(0);
/*     */   }
/*     */   
/*     */   public BasicVector(int length) {
/* 182 */     this(new double[length]);
/*     */   }
/*     */   
/*     */   public BasicVector(double[] array) {
/* 186 */     super(array.length);
/* 187 */     this.self = array;
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(int i) {
/* 192 */     return this.self[i];
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(int i, double value) {
/* 197 */     this.self[i] = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public void swapElements(int i, int j) {
/* 202 */     if (i != j) {
/* 203 */       double d = this.self[i];
/* 204 */       this.self[i] = this.self[j];
/* 205 */       this.self[j] = d;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector copyOfLength(int length) {
/* 211 */     ensureLengthIsCorrect(length);
/*     */     
/* 213 */     double[] $self = new double[length];
/* 214 */     System.arraycopy(this.self, 0, $self, 0, Math.min($self.length, this.self.length));
/*     */     
/* 216 */     return (Vector)new BasicVector($self);
/*     */   }
/*     */ 
/*     */   
/*     */   public double[] toArray() {
/* 221 */     double[] result = new double[this.length];
/* 222 */     System.arraycopy(this.self, 0, result, 0, this.length);
/* 223 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Vector> T to(VectorFactory<T> factory) {
/* 228 */     if (factory.outputClass == BasicVector.class) {
/* 229 */       return (T)factory.outputClass.cast(this);
/*     */     }
/*     */     
/* 232 */     return (T)super.to(factory);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector blankOfLength(int length) {
/* 237 */     return (Vector)zero(length);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] toBinary() {
/* 242 */     int size = 5 + 8 * this.length;
/*     */ 
/*     */ 
/*     */     
/* 246 */     ByteBuffer buffer = ByteBuffer.allocate(size);
/*     */     
/* 248 */     buffer.put((byte)0);
/* 249 */     buffer.putInt(this.length);
/* 250 */     for (double value : this.self) {
/* 251 */       buffer.putDouble(value);
/*     */     }
/*     */     
/* 254 */     return buffer.array();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/vector/dense/BasicVector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */