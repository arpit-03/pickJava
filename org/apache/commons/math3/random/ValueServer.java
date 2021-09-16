/*     */ package org.apache.commons.math3.random;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.ZeroException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ValueServer
/*     */ {
/*     */   public static final int DIGEST_MODE = 0;
/*     */   public static final int REPLAY_MODE = 1;
/*     */   public static final int UNIFORM_MODE = 2;
/*     */   public static final int EXPONENTIAL_MODE = 3;
/*     */   public static final int GAUSSIAN_MODE = 4;
/*     */   public static final int CONSTANT_MODE = 5;
/*  72 */   private int mode = 5;
/*     */ 
/*     */   
/*  75 */   private URL valuesFileURL = null;
/*     */ 
/*     */   
/*  78 */   private double mu = 0.0D;
/*     */ 
/*     */   
/*  81 */   private double sigma = 0.0D;
/*     */ 
/*     */   
/*  84 */   private EmpiricalDistribution empiricalDistribution = null;
/*     */ 
/*     */   
/*  87 */   private BufferedReader filePointer = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private final RandomDataGenerator randomData;
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueServer() {
/*  96 */     this.randomData = new RandomDataGenerator();
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
/*     */   @Deprecated
/*     */   public ValueServer(RandomDataImpl randomData) {
/* 109 */     this.randomData = randomData.getDelegate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueServer(RandomGenerator generator) {
/* 120 */     this.randomData = new RandomDataGenerator(generator);
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
/*     */   public double getNext() throws IOException, MathIllegalStateException, MathIllegalArgumentException {
/* 133 */     switch (this.mode) { case 0:
/* 134 */         return getNextDigest();
/* 135 */       case 1: return getNextReplay();
/* 136 */       case 2: return getNextUniform();
/* 137 */       case 3: return getNextExponential();
/* 138 */       case 4: return getNextGaussian();
/* 139 */       case 5: return this.mu; }
/* 140 */      throw new MathIllegalStateException(LocalizedFormats.UNKNOWN_MODE, new Object[] { Integer.valueOf(this.mode), "DIGEST_MODE", Integer.valueOf(0), "REPLAY_MODE", Integer.valueOf(1), "UNIFORM_MODE", Integer.valueOf(2), "EXPONENTIAL_MODE", Integer.valueOf(3), "GAUSSIAN_MODE", Integer.valueOf(4), "CONSTANT_MODE", Integer.valueOf(5) });
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
/*     */   public void fill(double[] values) throws IOException, MathIllegalStateException, MathIllegalArgumentException {
/* 159 */     for (int i = 0; i < values.length; i++) {
/* 160 */       values[i] = getNext();
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
/*     */   public double[] fill(int length) throws IOException, MathIllegalStateException, MathIllegalArgumentException {
/* 176 */     double[] out = new double[length];
/* 177 */     for (int i = 0; i < length; i++) {
/* 178 */       out[i] = getNext();
/*     */     }
/* 180 */     return out;
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
/*     */   public void computeDistribution() throws IOException, ZeroException, NullArgumentException {
/* 198 */     computeDistribution(1000);
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
/*     */   public void computeDistribution(int binCount) throws NullArgumentException, IOException, ZeroException {
/* 218 */     this.empiricalDistribution = new EmpiricalDistribution(binCount, this.randomData.getRandomGenerator());
/* 219 */     this.empiricalDistribution.load(this.valuesFileURL);
/* 220 */     this.mu = this.empiricalDistribution.getSampleStats().getMean();
/* 221 */     this.sigma = this.empiricalDistribution.getSampleStats().getStandardDeviation();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMode() {
/* 231 */     return this.mode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMode(int mode) {
/* 240 */     this.mode = mode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URL getValuesFileURL() {
/* 250 */     return this.valuesFileURL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValuesFileURL(String url) throws MalformedURLException {
/* 261 */     this.valuesFileURL = new URL(url);
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
/*     */   public void setValuesFileURL(URL url) {
/* 273 */     this.valuesFileURL = url;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EmpiricalDistribution getEmpiricalDistribution() {
/* 282 */     return this.empiricalDistribution;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetReplayFile() throws IOException {
/* 292 */     if (this.filePointer != null) {
/*     */       try {
/* 294 */         this.filePointer.close();
/* 295 */         this.filePointer = null;
/* 296 */       } catch (IOException iOException) {}
/*     */     }
/*     */ 
/*     */     
/* 300 */     this.filePointer = new BufferedReader(new InputStreamReader(this.valuesFileURL.openStream(), "UTF-8"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeReplayFile() throws IOException {
/* 309 */     if (this.filePointer != null) {
/* 310 */       this.filePointer.close();
/* 311 */       this.filePointer = null;
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
/*     */   public double getMu() {
/* 324 */     return this.mu;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMu(double mu) {
/* 335 */     this.mu = mu;
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
/*     */   public double getSigma() {
/* 348 */     return this.sigma;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSigma(double sigma) {
/* 357 */     this.sigma = sigma;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reSeed(long seed) {
/* 367 */     this.randomData.reSeed(seed);
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
/*     */   private double getNextDigest() throws MathIllegalStateException {
/* 384 */     if (this.empiricalDistribution == null || this.empiricalDistribution.getBinStats().size() == 0)
/*     */     {
/* 386 */       throw new MathIllegalStateException(LocalizedFormats.DIGEST_NOT_INITIALIZED, new Object[0]);
/*     */     }
/* 388 */     return this.empiricalDistribution.getNextValue();
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
/*     */   private double getNextReplay() throws IOException, MathIllegalStateException {
/* 411 */     String str = null;
/* 412 */     if (this.filePointer == null) {
/* 413 */       resetReplayFile();
/*     */     }
/* 415 */     if ((str = this.filePointer.readLine()) == null) {
/*     */       
/* 417 */       closeReplayFile();
/* 418 */       resetReplayFile();
/* 419 */       if ((str = this.filePointer.readLine()) == null) {
/* 420 */         throw new MathIllegalStateException(LocalizedFormats.URL_CONTAINS_NO_DATA, new Object[] { this.valuesFileURL });
/*     */       }
/*     */     } 
/*     */     
/* 424 */     return Double.parseDouble(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double getNextUniform() throws MathIllegalArgumentException {
/* 434 */     return this.randomData.nextUniform(0.0D, 2.0D * this.mu);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double getNextExponential() throws MathIllegalArgumentException {
/* 444 */     return this.randomData.nextExponential(this.mu);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double getNextGaussian() throws MathIllegalArgumentException {
/* 455 */     return this.randomData.nextGaussian(this.mu, this.sigma);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/random/ValueServer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */