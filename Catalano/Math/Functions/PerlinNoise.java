/*     */ package Catalano.Math.Functions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PerlinNoise
/*     */ {
/*  32 */   private double initFrequency = 1.0D;
/*  33 */   private double initAmplitude = 1.0D;
/*  34 */   private double persistence = 0.65D;
/*  35 */   private int octaves = 4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PerlinNoise() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PerlinNoise(int octaves, double persistense) {
/*  48 */     this.octaves = octaves;
/*  49 */     this.persistence = persistense;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PerlinNoise(int octaves, double persistence, double initFrequency, double initAmplitude) {
/*  60 */     this.octaves = octaves;
/*  61 */     this.persistence = persistence;
/*  62 */     this.initFrequency = initFrequency;
/*  63 */     this.initAmplitude = initAmplitude;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getInitAmplitude() {
/*  71 */     return this.initAmplitude;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInitAmplitude(double initAmplitude) {
/*  79 */     this.initAmplitude = initAmplitude;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getInitFrequency() {
/*  87 */     return this.initFrequency;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInitFrequency(double initFrequency) {
/*  95 */     this.initFrequency = initFrequency;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOctaves() {
/* 103 */     return this.octaves;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOctaves(int octaves) {
/* 111 */     this.octaves = octaves;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getPersistence() {
/* 119 */     return this.persistence;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPersistence(double persistence) {
/* 127 */     this.persistence = persistence;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double Function1D(double x) {
/* 136 */     double frequency = this.initFrequency;
/* 137 */     double amplitude = this.initAmplitude;
/* 138 */     double sum = 0.0D;
/*     */ 
/*     */     
/* 141 */     for (int i = 0; i < this.octaves; i++) {
/*     */       
/* 143 */       sum += SmoothedNoise(x * frequency) * amplitude;
/*     */       
/* 145 */       frequency *= 2.0D;
/* 146 */       amplitude *= this.persistence;
/*     */     } 
/* 148 */     return sum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double Function2D(double x, double y) {
/* 158 */     double frequency = this.initFrequency;
/* 159 */     double amplitude = this.initAmplitude;
/* 160 */     double sum = 0.0D;
/*     */ 
/*     */     
/* 163 */     for (int i = 0; i < this.octaves; i++) {
/*     */       
/* 165 */       sum += SmoothedNoise(x * frequency, y * frequency) * amplitude;
/*     */       
/* 167 */       frequency *= 2.0D;
/* 168 */       amplitude *= this.persistence;
/*     */     } 
/* 170 */     return sum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double Noise(int x) {
/* 179 */     int n = x << 13 ^ x;
/*     */     
/* 181 */     return 1.0D - (n * (n * n * 15731 + 789221) + 1376312589 & Integer.MAX_VALUE) / 1.073741824E9D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double Noise(int x, int y) {
/* 191 */     int n = x + y * 57;
/* 192 */     n = n << 13 ^ n;
/*     */     
/* 194 */     return 1.0D - (n * (n * n * 15731 + 789221) + 1376312589 & Integer.MAX_VALUE) / 1.073741824E9D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double SmoothedNoise(double x) {
/* 203 */     int xInt = (int)x;
/* 204 */     double xFrac = x - xInt;
/*     */     
/* 206 */     return CosineInterpolate(Noise(xInt), Noise(xInt + 1), xFrac);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double SmoothedNoise(double x, double y) {
/* 216 */     int xInt = (int)x;
/* 217 */     int yInt = (int)y;
/* 218 */     double xFrac = x - xInt;
/* 219 */     double yFrac = y - yInt;
/*     */ 
/*     */     
/* 222 */     double x0y0 = Noise(xInt, yInt);
/* 223 */     double x1y0 = Noise(xInt + 1, yInt);
/* 224 */     double x0y1 = Noise(xInt, yInt + 1);
/* 225 */     double x1y1 = Noise(xInt + 1, yInt + 1);
/*     */ 
/*     */     
/* 228 */     double v1 = CosineInterpolate(x0y0, x1y0, xFrac);
/* 229 */     double v2 = CosineInterpolate(x0y1, x1y1, xFrac);
/*     */     
/* 231 */     return CosineInterpolate(v1, v2, yFrac);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double CosineInterpolate(double x1, double x2, double a) {
/* 242 */     double f = (1.0D - Math.cos(a * Math.PI)) * 0.5D;
/*     */     
/* 244 */     return x1 * (1.0D - f) + x2 * f;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Functions/PerlinNoise.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */