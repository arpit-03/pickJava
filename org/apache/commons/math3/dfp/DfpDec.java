/*     */ package org.apache.commons.math3.dfp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DfpDec
/*     */   extends Dfp
/*     */ {
/*     */   protected DfpDec(DfpField factory) {
/*  32 */     super(factory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DfpDec(DfpField factory, byte x) {
/*  40 */     super(factory, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DfpDec(DfpField factory, int x) {
/*  48 */     super(factory, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DfpDec(DfpField factory, long x) {
/*  56 */     super(factory, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DfpDec(DfpField factory, double x) {
/*  64 */     super(factory, x);
/*  65 */     round(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DfpDec(Dfp d) {
/*  72 */     super(d);
/*  73 */     round(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DfpDec(DfpField factory, String s) {
/*  81 */     super(factory, s);
/*  82 */     round(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DfpDec(DfpField factory, byte sign, byte nans) {
/*  92 */     super(factory, sign, nans);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp newInstance() {
/*  98 */     return new DfpDec(getField());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp newInstance(byte x) {
/* 104 */     return new DfpDec(getField(), x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp newInstance(int x) {
/* 110 */     return new DfpDec(getField(), x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp newInstance(long x) {
/* 116 */     return new DfpDec(getField(), x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp newInstance(double x) {
/* 122 */     return new DfpDec(getField(), x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp newInstance(Dfp d) {
/* 130 */     if (getField().getRadixDigits() != d.getField().getRadixDigits()) {
/* 131 */       getField().setIEEEFlagsBits(1);
/* 132 */       Dfp result = newInstance(getZero());
/* 133 */       result.nans = 3;
/* 134 */       return dotrap(1, "newInstance", d, result);
/*     */     } 
/*     */     
/* 137 */     return new DfpDec(d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp newInstance(String s) {
/* 144 */     return new DfpDec(getField(), s);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp newInstance(byte sign, byte nans) {
/* 150 */     return new DfpDec(getField(), sign, nans);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getDecimalDigits() {
/* 159 */     return getRadixDigits() * 4 - 3;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int round(int in) {
/*     */     int n;
/*     */     boolean inc;
/* 166 */     int msb = this.mant[this.mant.length - 1];
/* 167 */     if (msb == 0)
/*     */     {
/* 169 */       return 0;
/*     */     }
/*     */     
/* 172 */     int cmaxdigits = this.mant.length * 4;
/* 173 */     int lsbthreshold = 1000;
/* 174 */     while (lsbthreshold > msb) {
/* 175 */       lsbthreshold /= 10;
/* 176 */       cmaxdigits--;
/*     */     } 
/*     */ 
/*     */     
/* 180 */     int digits = getDecimalDigits();
/* 181 */     int lsbshift = cmaxdigits - digits;
/* 182 */     int lsd = lsbshift / 4;
/*     */     
/* 184 */     lsbthreshold = 1;
/* 185 */     for (int i = 0; i < lsbshift % 4; i++) {
/* 186 */       lsbthreshold *= 10;
/*     */     }
/*     */     
/* 189 */     int lsb = this.mant[lsd];
/*     */     
/* 191 */     if (lsbthreshold <= 1 && digits == 4 * this.mant.length - 3) {
/* 192 */       return super.round(in);
/*     */     }
/*     */     
/* 195 */     int discarded = in;
/*     */     
/* 197 */     if (lsbthreshold == 1) {
/*     */       
/* 199 */       n = this.mant[lsd - 1] / 1000 % 10;
/* 200 */       this.mant[lsd - 1] = this.mant[lsd - 1] % 1000;
/* 201 */       discarded |= this.mant[lsd - 1];
/*     */     } else {
/* 203 */       n = lsb * 10 / lsbthreshold % 10;
/* 204 */       discarded |= lsb % lsbthreshold / 10;
/*     */     } 
/*     */     
/* 207 */     for (int j = 0; j < lsd; j++) {
/* 208 */       discarded |= this.mant[j];
/* 209 */       this.mant[j] = 0;
/*     */     } 
/*     */     
/* 212 */     this.mant[lsd] = lsb / lsbthreshold * lsbthreshold;
/*     */ 
/*     */     
/* 215 */     switch (getField().getRoundingMode()) {
/*     */       case ROUND_DOWN:
/* 217 */         inc = false;
/*     */         break;
/*     */       
/*     */       case ROUND_UP:
/* 221 */         inc = (n != 0 || discarded != 0);
/*     */         break;
/*     */       
/*     */       case ROUND_HALF_UP:
/* 225 */         inc = (n >= 5);
/*     */         break;
/*     */       
/*     */       case ROUND_HALF_DOWN:
/* 229 */         inc = (n > 5);
/*     */         break;
/*     */       
/*     */       case ROUND_HALF_EVEN:
/* 233 */         inc = (n > 5 || (n == 5 && discarded != 0) || (n == 5 && discarded == 0 && (lsb / lsbthreshold & 0x1) == 1));
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case ROUND_HALF_ODD:
/* 239 */         inc = (n > 5 || (n == 5 && discarded != 0) || (n == 5 && discarded == 0 && (lsb / lsbthreshold & 0x1) == 0));
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case ROUND_CEIL:
/* 245 */         inc = (this.sign == 1 && (n != 0 || discarded != 0));
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/* 250 */         inc = (this.sign == -1 && (n != 0 || discarded != 0));
/*     */         break;
/*     */     } 
/*     */     
/* 254 */     if (inc) {
/*     */       
/* 256 */       int rh = lsbthreshold;
/* 257 */       for (int k = lsd; k < this.mant.length; k++) {
/* 258 */         int r = this.mant[k] + rh;
/* 259 */         rh = r / 10000;
/* 260 */         this.mant[k] = r % 10000;
/*     */       } 
/*     */       
/* 263 */       if (rh != 0) {
/* 264 */         shiftRight();
/* 265 */         this.mant[this.mant.length - 1] = rh;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 270 */     if (this.exp < -32767) {
/*     */       
/* 272 */       getField().setIEEEFlagsBits(8);
/* 273 */       return 8;
/*     */     } 
/*     */     
/* 276 */     if (this.exp > 32768) {
/*     */       
/* 278 */       getField().setIEEEFlagsBits(4);
/* 279 */       return 4;
/*     */     } 
/*     */     
/* 282 */     if (n != 0 || discarded != 0) {
/*     */       
/* 284 */       getField().setIEEEFlagsBits(16);
/* 285 */       return 16;
/*     */     } 
/* 287 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp nextAfter(Dfp x) {
/*     */     Dfp result;
/* 294 */     String trapName = "nextAfter";
/*     */ 
/*     */     
/* 297 */     if (getField().getRadixDigits() != x.getField().getRadixDigits()) {
/* 298 */       getField().setIEEEFlagsBits(1);
/* 299 */       Dfp dfp = newInstance(getZero());
/* 300 */       dfp.nans = 3;
/* 301 */       return dotrap(1, "nextAfter", x, dfp);
/*     */     } 
/*     */     
/* 304 */     boolean up = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 309 */     if (lessThan(x)) {
/* 310 */       up = true;
/*     */     }
/*     */     
/* 313 */     if (equals(x)) {
/* 314 */       return newInstance(x);
/*     */     }
/*     */     
/* 317 */     if (lessThan(getZero())) {
/* 318 */       up = !up;
/*     */     }
/*     */     
/* 321 */     if (up) {
/* 322 */       Dfp inc = power10(intLog10() - getDecimalDigits() + 1);
/* 323 */       inc = copysign(inc, this);
/*     */       
/* 325 */       if (equals(getZero())) {
/* 326 */         inc = power10K(-32767 - this.mant.length - 1);
/*     */       }
/*     */       
/* 329 */       if (inc.equals(getZero())) {
/* 330 */         result = copysign(newInstance(getZero()), this);
/*     */       } else {
/* 332 */         result = add(inc);
/*     */       } 
/*     */     } else {
/* 335 */       Dfp inc = power10(intLog10());
/* 336 */       inc = copysign(inc, this);
/*     */       
/* 338 */       if (equals(inc)) {
/* 339 */         inc = inc.divide(power10(getDecimalDigits()));
/*     */       } else {
/* 341 */         inc = inc.divide(power10(getDecimalDigits() - 1));
/*     */       } 
/*     */       
/* 344 */       if (equals(getZero())) {
/* 345 */         inc = power10K(-32767 - this.mant.length - 1);
/*     */       }
/*     */       
/* 348 */       if (inc.equals(getZero())) {
/* 349 */         result = copysign(newInstance(getZero()), this);
/*     */       } else {
/* 351 */         result = subtract(inc);
/*     */       } 
/*     */     } 
/*     */     
/* 355 */     if (result.classify() == 1 && classify() != 1) {
/* 356 */       getField().setIEEEFlagsBits(16);
/* 357 */       result = dotrap(16, "nextAfter", x, result);
/*     */     } 
/*     */     
/* 360 */     if (result.equals(getZero()) && !equals(getZero())) {
/* 361 */       getField().setIEEEFlagsBits(16);
/* 362 */       result = dotrap(16, "nextAfter", x, result);
/*     */     } 
/*     */     
/* 365 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/dfp/DfpDec.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */