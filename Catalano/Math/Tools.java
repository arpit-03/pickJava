/*     */ package Catalano.Math;
/*     */ 
/*     */ import Catalano.Core.DoubleRange;
/*     */ import Catalano.Core.FloatRange;
/*     */ import Catalano.Core.IntRange;
/*     */ import Catalano.Math.Random.Random;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Tools
/*     */ {
/*  42 */   private static Random random = new Random();
/*     */   
/*     */   public static Random Random() {
/*  45 */     return random;
/*     */   }
/*     */   
/*     */   public static void SetupGenerator(int seed) {
/*  49 */     random = new Random(seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Square(double x) {
/*  58 */     return x * x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Sinc(double x) {
/*  67 */     return Math.sin(Math.PI * x) / Math.PI * x;
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
/*     */   public static float Angle(float x, float y) {
/*  82 */     if (y >= 0.0F) {
/*  83 */       if (x >= 0.0F)
/*  84 */         return (float)Math.atan((y / x)); 
/*  85 */       return (float)(Math.PI - Math.atan((-y / x)));
/*     */     } 
/*     */     
/*  88 */     if (x >= 0.0F)
/*  89 */       return (float)(6.283185307179586D - Math.atan((-y / x))); 
/*  90 */     return (float)(Math.PI + Math.atan((y / x)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Angle(double x, double y) {
/* 101 */     if (y >= 0.0D) {
/* 102 */       if (x >= 0.0D)
/* 103 */         return Math.atan(y / x); 
/* 104 */       return Math.PI - Math.atan(-y / x);
/*     */     } 
/*     */     
/* 107 */     if (x >= 0.0D)
/* 108 */       return 6.283185307179586D - Math.atan(-y / x); 
/* 109 */     return Math.PI + Math.atan(y / x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Clamp(double x, DoubleRange range) {
/* 120 */     return Clamp(x, range.getMin(), range.getMax());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int Clamp(int x, IntRange range) {
/* 130 */     return Clamp(x, range.getMin(), range.getMax());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float Clamp(float x, FloatRange range) {
/* 140 */     return Clamp(x, range.getMin(), range.getMax());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Clamp(double x, double min, double max) {
/* 151 */     if (x < min)
/* 152 */       return min; 
/* 153 */     if (x > max)
/* 154 */       return max; 
/* 155 */     return x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int Clamp(int x, int min, int max) {
/* 166 */     if (x < min)
/* 167 */       return min; 
/* 168 */     if (x > max)
/* 169 */       return max; 
/* 170 */     return x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float Clamp(float x, float min, float max) {
/* 181 */     if (x < min)
/* 182 */       return min; 
/* 183 */     if (x > max)
/* 184 */       return max; 
/* 185 */     return x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void Clamp(double[] values, List<DoubleRange> ranges) {
/* 194 */     for (int i = 0; i < values.length; i++) {
/* 195 */       DoubleRange range = ranges.get(i);
/* 196 */       values[i] = (values[i] < range.getMin()) ? range.getMin() : values[i];
/* 197 */       values[i] = (values[i] > range.getMax()) ? range.getMax() : values[i];
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void Clamp(double[] values, DoubleRange range) {
/* 207 */     Clamp(values, range.getMin(), range.getMax());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void Clamp(double[] values, double min, double max) {
/* 218 */     for (int i = 0; i < values.length; i++) {
/* 219 */       values[i] = (values[i] < min) ? min : values[i];
/* 220 */       values[i] = (values[i] > max) ? max : values[i];
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
/*     */   public static int DigitalRoot(int n) {
/* 234 */     return 1 + (n - 1) % 9;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int GreatestCommonDivisor(int a, int b) {
/* 245 */     int x = a - b * (int)Math.floor((a / b));
/* 246 */     while (x != 0) {
/* 247 */       a = b;
/* 248 */       b = x;
/* 249 */       x = a - b * (int)Math.floor((a / b));
/*     */     } 
/* 251 */     return b;
/*     */   }
/*     */   
/*     */   public static boolean isNumeric(String str) {
/* 255 */     if (str.length() == 0) {
/* 256 */       return false;
/*     */     }
/* 258 */     char[] chars = str.toCharArray();
/* 259 */     int sz = chars.length;
/* 260 */     boolean hasExp = false;
/* 261 */     boolean hasDecPoint = false;
/* 262 */     boolean allowSigns = false;
/* 263 */     boolean foundDigit = false;
/*     */     
/* 265 */     int start = (chars[0] == '-') ? 1 : 0;
/* 266 */     if (sz > start + 1 && 
/* 267 */       chars[start] == '0' && chars[start + 1] == 'x') {
/* 268 */       int j = start + 2;
/* 269 */       if (j == sz) {
/* 270 */         return false;
/*     */       }
/*     */       
/* 273 */       for (; j < chars.length; j++) {
/* 274 */         if ((chars[j] < '0' || chars[j] > '9') && (
/* 275 */           chars[j] < 'a' || chars[j] > 'f') && (
/* 276 */           chars[j] < 'A' || chars[j] > 'F')) {
/* 277 */           return false;
/*     */         }
/*     */       } 
/* 280 */       return true;
/*     */     } 
/*     */     
/* 283 */     sz--;
/*     */     
/* 285 */     int i = start;
/*     */ 
/*     */     
/* 288 */     while (i < sz || (i < sz + 1 && allowSigns && !foundDigit)) {
/* 289 */       if (chars[i] >= '0' && chars[i] <= '9') {
/* 290 */         foundDigit = true;
/* 291 */         allowSigns = false;
/*     */       }
/* 293 */       else if (chars[i] == '.') {
/* 294 */         if (hasDecPoint || hasExp)
/*     */         {
/* 296 */           return false;
/*     */         }
/* 298 */         hasDecPoint = true;
/* 299 */       } else if (chars[i] == 'e' || chars[i] == 'E') {
/*     */         
/* 301 */         if (hasExp)
/*     */         {
/* 303 */           return false;
/*     */         }
/* 305 */         if (!foundDigit) {
/* 306 */           return false;
/*     */         }
/* 308 */         hasExp = true;
/* 309 */         allowSigns = true;
/* 310 */       } else if (chars[i] == '+' || chars[i] == '-') {
/* 311 */         if (!allowSigns) {
/* 312 */           return false;
/*     */         }
/* 314 */         allowSigns = false;
/* 315 */         foundDigit = false;
/*     */       } else {
/* 317 */         return false;
/*     */       } 
/* 319 */       i++;
/*     */     } 
/* 321 */     if (i < chars.length) {
/* 322 */       if (chars[i] >= '0' && chars[i] <= '9')
/*     */       {
/* 324 */         return true;
/*     */       }
/* 326 */       if (chars[i] == 'e' || chars[i] == 'E')
/*     */       {
/* 328 */         return false;
/*     */       }
/* 330 */       if (chars[i] == '.') {
/* 331 */         if (hasDecPoint || hasExp)
/*     */         {
/* 333 */           return false;
/*     */         }
/*     */         
/* 336 */         return foundDigit;
/*     */       } 
/* 338 */       if (!allowSigns && (
/* 339 */         chars[i] == 'd' || 
/* 340 */         chars[i] == 'D' || 
/* 341 */         chars[i] == 'f' || 
/* 342 */         chars[i] == 'F')) {
/* 343 */         return foundDigit;
/*     */       }
/* 345 */       if (chars[i] == 'l' || 
/* 346 */         chars[i] == 'L')
/*     */       {
/* 348 */         return (foundDigit && !hasExp);
/*     */       }
/*     */       
/* 351 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 355 */     return (!allowSigns && foundDigit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isPowerOf2(int x) {
/* 364 */     return (x > 0) ? (((x & x - 1) == 0)) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int Mod(int x, int m) {
/* 374 */     if (m < 0) m = -m; 
/* 375 */     int r = x % m;
/* 376 */     return (r < 0) ? (r + m) : r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int NextPowerOf2(int x) {
/* 385 */     x--;
/* 386 */     x |= x >> 1;
/* 387 */     x |= x >> 2;
/* 388 */     x |= x >> 4;
/* 389 */     x |= x >> 8;
/* 390 */     x |= x >> 16;
/* 391 */     return ++x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void Permutate(int[] x) {
/* 401 */     random.permutate(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int PreviousPowerOf2(int x) {
/* 410 */     return NextPowerOf2(x + 1) / 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized double RandomNextDouble() {
/* 420 */     return random.nextDouble();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized double RandomLaplacian() {
/* 428 */     return RandomLaplacian(0.0D, 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized double RandomLaplacian(double mean, double std) {
/* 438 */     double u = random.nextDouble() - 0.5D;
/* 439 */     double sigma = std / 1.4142135623730951D;
/* 440 */     return mean - sigma * Math.signum(u) * Math.log(1.0D - 2.0D * Math.abs(u));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int Scale(IntRange from, IntRange to, int x) {
/* 451 */     if (from.length() == 0.0D) return 0; 
/* 452 */     return (int)(to.length() * (x - from.getMin()) / from.length() + to.getMin());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Scale(DoubleRange from, DoubleRange to, int x) {
/* 463 */     if (from.length() == 0.0D) return 0.0D; 
/* 464 */     return to.length() * (x - from.getMin()) / from.length() + to.getMin();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Scale(DoubleRange from, DoubleRange to, double x) {
/* 475 */     if (from.length() == 0.0D) return 0.0D; 
/* 476 */     return to.length() * (x - from.getMin()) / from.length() + to.getMin();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float Scale(FloatRange from, FloatRange to, int x) {
/* 487 */     if (from.length() == 0.0F) return 0.0F; 
/* 488 */     return to.length() * (x - from.getMin()) / from.length() + to.getMin();
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
/*     */   public static double Scale(double fromMin, double fromMax, double toMin, double toMax, double x) {
/* 501 */     if (fromMax - fromMin == 0.0D) return 0.0D; 
/* 502 */     return (toMax - toMin) * (x - fromMin) / (fromMax - fromMin) + toMin;
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
/*     */   public static float Scale(float fromMin, float fromMax, float toMin, float toMax, float x) {
/* 515 */     if (fromMax - fromMin == 0.0F) return 0.0F; 
/* 516 */     return (toMax - toMin) * (x - fromMin) / (fromMax - fromMin) + toMin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Sum(double[] data) {
/* 525 */     double sum = 0.0D;
/* 526 */     for (int i = 0; i < data.length; i++) {
/* 527 */       sum += data[i];
/*     */     }
/* 529 */     return sum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int Sum(int[] data) {
/* 538 */     int sum = 0;
/* 539 */     for (int i = 0; i < data.length; i++) {
/* 540 */       sum += data[i];
/*     */     }
/* 542 */     return sum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float Sum(float[] data) {
/* 551 */     float sum = 0.0F;
/* 552 */     for (int i = 0; i < data.length; i++) {
/* 553 */       sum += data[i];
/*     */     }
/* 555 */     return sum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Log(double a, double b) {
/* 565 */     return Math.log(a) / Math.log(b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double TruncatedPower(double value, double degree) {
/* 575 */     double x = Math.pow(value, degree);
/* 576 */     return (x > 0.0D) ? x : 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] Unique(int[] values) {
/* 585 */     HashSet<Integer> lst = new HashSet<>();
/* 586 */     for (int i = 0; i < values.length; i++) {
/* 587 */       lst.add(Integer.valueOf(values[i]));
/*     */     }
/*     */     
/* 590 */     int[] v = new int[lst.size()];
/* 591 */     Iterator<Integer> it = lst.iterator();
/* 592 */     for (int j = 0; j < v.length; j++) {
/* 593 */       v[j] = ((Integer)it.next()).intValue();
/*     */     }
/*     */     
/* 596 */     return v;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Hypotenuse(double a, double b) {
/* 606 */     double r = 0.0D;
/* 607 */     double absA = Math.abs(a);
/* 608 */     double absB = Math.abs(b);
/*     */     
/* 610 */     if (absA > absB) {
/*     */       
/* 612 */       r = b / a;
/* 613 */       r = absA * Math.sqrt(1.0D + r * r);
/*     */     }
/* 615 */     else if (b != 0.0D) {
/*     */       
/* 617 */       r = a / b;
/* 618 */       r = absB * Math.sqrt(1.0D + r * r);
/*     */     } 
/*     */     
/* 621 */     return r;
/*     */   }
/*     */   
/*     */   public static int Log2(int x) {
/* 625 */     if (x <= 65536) {
/*     */       
/* 627 */       if (x <= 256) {
/*     */         
/* 629 */         if (x <= 16) {
/*     */           
/* 631 */           if (x <= 4) {
/*     */             
/* 633 */             if (x <= 2) {
/*     */               
/* 635 */               if (x <= 1)
/* 636 */                 return 0; 
/* 637 */               return 1;
/*     */             } 
/* 639 */             return 2;
/*     */           } 
/* 641 */           if (x <= 8)
/* 642 */             return 3; 
/* 643 */           return 4;
/*     */         } 
/* 645 */         if (x <= 64) {
/*     */           
/* 647 */           if (x <= 32)
/* 648 */             return 5; 
/* 649 */           return 6;
/*     */         } 
/* 651 */         if (x <= 128)
/* 652 */           return 7; 
/* 653 */         return 8;
/*     */       } 
/* 655 */       if (x <= 4096) {
/*     */         
/* 657 */         if (x <= 1024) {
/*     */           
/* 659 */           if (x <= 512)
/* 660 */             return 9; 
/* 661 */           return 10;
/*     */         } 
/* 663 */         if (x <= 2048)
/* 664 */           return 11; 
/* 665 */         return 12;
/*     */       } 
/* 667 */       if (x <= 16384) {
/*     */         
/* 669 */         if (x <= 8192)
/* 670 */           return 13; 
/* 671 */         return 14;
/*     */       } 
/* 673 */       if (x <= 32768)
/* 674 */         return 15; 
/* 675 */       return 16;
/*     */     } 
/*     */     
/* 678 */     if (x <= 16777216) {
/*     */       
/* 680 */       if (x <= 1048576) {
/*     */         
/* 682 */         if (x <= 262144) {
/*     */           
/* 684 */           if (x <= 131072)
/* 685 */             return 17; 
/* 686 */           return 18;
/*     */         } 
/* 688 */         if (x <= 524288)
/* 689 */           return 19; 
/* 690 */         return 20;
/*     */       } 
/* 692 */       if (x <= 4194304) {
/*     */         
/* 694 */         if (x <= 2097152)
/* 695 */           return 21; 
/* 696 */         return 22;
/*     */       } 
/* 698 */       if (x <= 8388608)
/* 699 */         return 23; 
/* 700 */       return 24;
/*     */     } 
/* 702 */     if (x <= 268435456) {
/*     */       
/* 704 */       if (x <= 67108864) {
/*     */         
/* 706 */         if (x <= 33554432)
/* 707 */           return 25; 
/* 708 */         return 26;
/*     */       } 
/* 710 */       if (x <= 134217728)
/* 711 */         return 27; 
/* 712 */       return 28;
/*     */     } 
/* 714 */     if (x <= 1073741824) {
/*     */       
/* 716 */       if (x <= 536870912)
/* 717 */         return 29; 
/* 718 */       return 30;
/*     */     } 
/* 720 */     return 31;
/*     */   }
/*     */   
/*     */   public static int Pow2(int power) {
/* 724 */     return (power >= 0 && power <= 30) ? (1 << power) : 0;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Tools.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */