/*     */ package org.apache.commons.math3.random;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Arrays;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.commons.math3.exception.MathInternalError;
/*     */ import org.apache.commons.math3.exception.MathParseException;
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SobolSequenceGenerator
/*     */   implements RandomVectorGenerator
/*     */ {
/*     */   private static final int BITS = 52;
/*  62 */   private static final double SCALE = FastMath.pow(2.0D, 52);
/*     */ 
/*     */   
/*     */   private static final int MAX_DIMENSION = 1000;
/*     */ 
/*     */   
/*     */   private static final String RESOURCE_NAME = "/assets/org/apache/commons/math3/random/new-joe-kuo-6.1000";
/*     */ 
/*     */   
/*     */   private static final String FILE_CHARSET = "US-ASCII";
/*     */ 
/*     */   
/*     */   private final int dimension;
/*     */ 
/*     */   
/*  77 */   private int count = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final long[][] direction;
/*     */ 
/*     */ 
/*     */   
/*     */   private final long[] x;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SobolSequenceGenerator(int dimension) throws OutOfRangeException {
/*  92 */     if (dimension < 1 || dimension > 1000) {
/*  93 */       throw new OutOfRangeException(Integer.valueOf(dimension), Integer.valueOf(1), Integer.valueOf(1000));
/*     */     }
/*     */ 
/*     */     
/*  97 */     InputStream is = getClass().getResourceAsStream("/assets/org/apache/commons/math3/random/new-joe-kuo-6.1000");
/*  98 */     if (is == null) {
/*  99 */       throw new MathInternalError();
/*     */     }
/*     */     
/* 102 */     this.dimension = dimension;
/*     */ 
/*     */     
/* 105 */     this.direction = new long[dimension][53];
/* 106 */     this.x = new long[dimension];
/*     */     
/*     */     try {
/* 109 */       initFromStream(is);
/* 110 */     } catch (IOException e) {
/*     */       
/* 112 */       throw new MathInternalError();
/* 113 */     } catch (MathParseException e) {
/*     */       
/* 115 */       throw new MathInternalError();
/*     */     } finally {
/*     */       try {
/* 118 */         is.close();
/* 119 */       } catch (IOException iOException) {}
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SobolSequenceGenerator(int dimension, InputStream is) throws NotStrictlyPositiveException, MathParseException, IOException {
/* 159 */     if (dimension < 1) {
/* 160 */       throw new NotStrictlyPositiveException(Integer.valueOf(dimension));
/*     */     }
/*     */     
/* 163 */     this.dimension = dimension;
/*     */ 
/*     */     
/* 166 */     this.direction = new long[dimension][53];
/* 167 */     this.x = new long[dimension];
/*     */ 
/*     */     
/* 170 */     int lastDimension = initFromStream(is);
/* 171 */     if (lastDimension < dimension) {
/* 172 */       throw new OutOfRangeException(Integer.valueOf(dimension), Integer.valueOf(1), Integer.valueOf(lastDimension));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int initFromStream(InputStream is) throws MathParseException, IOException {
/* 190 */     for (int i = 1; i <= 52; i++) {
/* 191 */       this.direction[0][i] = 1L << 52 - i;
/*     */     }
/*     */     
/* 194 */     Charset charset = Charset.forName("US-ASCII");
/* 195 */     BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset));
/* 196 */     int dim = -1;
/*     */ 
/*     */     
/*     */     try {
/* 200 */       reader.readLine();
/*     */       
/* 202 */       int lineNumber = 2;
/* 203 */       int index = 1;
/* 204 */       String line = null;
/* 205 */       while ((line = reader.readLine()) != null) {
/* 206 */         StringTokenizer st = new StringTokenizer(line, " ");
/*     */         try {
/* 208 */           dim = Integer.parseInt(st.nextToken());
/* 209 */           if (dim >= 2 && dim <= this.dimension) {
/* 210 */             int s = Integer.parseInt(st.nextToken());
/* 211 */             int a = Integer.parseInt(st.nextToken());
/* 212 */             int[] m = new int[s + 1];
/* 213 */             for (int j = 1; j <= s; j++) {
/* 214 */               m[j] = Integer.parseInt(st.nextToken());
/*     */             }
/* 216 */             initDirectionVector(index++, a, m);
/*     */           } 
/*     */           
/* 219 */           if (dim > this.dimension) {
/* 220 */             return dim;
/*     */           }
/* 222 */         } catch (NoSuchElementException e) {
/* 223 */           throw new MathParseException(line, lineNumber);
/* 224 */         } catch (NumberFormatException e) {
/* 225 */           throw new MathParseException(line, lineNumber);
/*     */         } 
/* 227 */         lineNumber++;
/*     */       } 
/*     */     } finally {
/* 230 */       reader.close();
/*     */     } 
/*     */     
/* 233 */     return dim;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initDirectionVector(int d, int a, int[] m) {
/* 244 */     int s = m.length - 1; int i;
/* 245 */     for (i = 1; i <= s; i++) {
/* 246 */       this.direction[d][i] = m[i] << 52 - i;
/*     */     }
/* 248 */     for (i = s + 1; i <= 52; i++) {
/* 249 */       this.direction[d][i] = this.direction[d][i - s] ^ this.direction[d][i - s] >> s;
/* 250 */       for (int k = 1; k <= s - 1; k++) {
/* 251 */         this.direction[d][i] = this.direction[d][i] ^ (a >> s - 1 - k & 0x1) * this.direction[d][i - k];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public double[] nextVector() {
/* 258 */     double[] v = new double[this.dimension];
/* 259 */     if (this.count == 0) {
/* 260 */       this.count++;
/* 261 */       return v;
/*     */     } 
/*     */ 
/*     */     
/* 265 */     int c = 1;
/* 266 */     int value = this.count - 1;
/* 267 */     while ((value & 0x1) == 1) {
/* 268 */       value >>= 1;
/* 269 */       c++;
/*     */     } 
/*     */     
/* 272 */     for (int i = 0; i < this.dimension; i++) {
/* 273 */       this.x[i] = this.x[i] ^ this.direction[i][c];
/* 274 */       v[i] = this.x[i] / SCALE;
/*     */     } 
/* 276 */     this.count++;
/* 277 */     return v;
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
/*     */   public double[] skipTo(int index) throws NotPositiveException {
/* 290 */     if (index == 0) {
/*     */       
/* 292 */       Arrays.fill(this.x, 0L);
/*     */     } else {
/* 294 */       int i = index - 1;
/* 295 */       long grayCode = (i ^ i >> 1);
/* 296 */       for (int j = 0; j < this.dimension; j++) {
/* 297 */         long result = 0L;
/* 298 */         for (int k = 1; k <= 52; k++) {
/* 299 */           long shift = grayCode >> k - 1;
/* 300 */           if (shift == 0L) {
/*     */             break;
/*     */           }
/*     */ 
/*     */           
/* 305 */           long ik = shift & 0x1L;
/* 306 */           result ^= ik * this.direction[j][k];
/*     */         } 
/* 308 */         this.x[j] = result;
/*     */       } 
/*     */     } 
/* 311 */     this.count = index;
/* 312 */     return nextVector();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNextIndex() {
/* 322 */     return this.count;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/random/SobolSequenceGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */