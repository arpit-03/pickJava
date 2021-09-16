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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DfpMath
/*     */ {
/*     */   private static final String POW_TRAP = "pow";
/*     */   
/*     */   protected static Dfp[] split(DfpField field, String a) {
/*  45 */     Dfp[] result = new Dfp[2];
/*     */     
/*  47 */     boolean leading = true;
/*  48 */     int sp = 0;
/*  49 */     int sig = 0;
/*     */     
/*  51 */     char[] buf = new char[a.length()];
/*     */     int i;
/*  53 */     for (i = 0; i < buf.length; i++) {
/*  54 */       buf[i] = a.charAt(i);
/*     */       
/*  56 */       if (buf[i] >= '1' && buf[i] <= '9') {
/*  57 */         leading = false;
/*     */       }
/*     */       
/*  60 */       if (buf[i] == '.') {
/*  61 */         sig += (400 - sig) % 4;
/*  62 */         leading = false;
/*     */       } 
/*     */       
/*  65 */       if (sig == field.getRadixDigits() / 2 * 4) {
/*  66 */         sp = i;
/*     */         
/*     */         break;
/*     */       } 
/*  70 */       if (buf[i] >= '0' && buf[i] <= '9' && !leading) {
/*  71 */         sig++;
/*     */       }
/*     */     } 
/*     */     
/*  75 */     result[0] = field.newDfp(new String(buf, 0, sp));
/*     */     
/*  77 */     for (i = 0; i < buf.length; i++) {
/*  78 */       buf[i] = a.charAt(i);
/*  79 */       if (buf[i] >= '0' && buf[i] <= '9' && i < sp) {
/*  80 */         buf[i] = '0';
/*     */       }
/*     */     } 
/*     */     
/*  84 */     result[1] = field.newDfp(new String(buf));
/*     */     
/*  86 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Dfp[] split(Dfp a) {
/*  94 */     Dfp[] result = new Dfp[2];
/*  95 */     Dfp shift = a.multiply(a.power10K(a.getRadixDigits() / 2));
/*  96 */     result[0] = a.add(shift).subtract(shift);
/*  97 */     result[1] = a.subtract(result[0]);
/*  98 */     return result;
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
/*     */   protected static Dfp[] splitMult(Dfp[] a, Dfp[] b) {
/* 110 */     Dfp[] result = new Dfp[2];
/*     */     
/* 112 */     result[1] = a[0].getZero();
/* 113 */     result[0] = a[0].multiply(b[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     if (result[0].classify() == 1 || result[0].equals(result[1])) {
/* 120 */       return result;
/*     */     }
/*     */     
/* 123 */     result[1] = a[0].multiply(b[1]).add(a[1].multiply(b[0])).add(a[1].multiply(b[1]));
/*     */     
/* 125 */     return result;
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
/*     */   protected static Dfp[] splitDiv(Dfp[] a, Dfp[] b) {
/* 138 */     Dfp[] result = new Dfp[2];
/*     */     
/* 140 */     result[0] = a[0].divide(b[0]);
/* 141 */     result[1] = a[1].multiply(b[0]).subtract(a[0].multiply(b[1]));
/* 142 */     result[1] = result[1].divide(b[0].multiply(b[0]).add(b[0].multiply(b[1])));
/*     */     
/* 144 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Dfp splitPow(Dfp[] base, int a) {
/* 153 */     boolean invert = false;
/*     */     
/* 155 */     Dfp[] r = new Dfp[2];
/*     */     
/* 157 */     Dfp[] result = new Dfp[2];
/* 158 */     result[0] = base[0].getOne();
/* 159 */     result[1] = base[0].getZero();
/*     */     
/* 161 */     if (a == 0)
/*     */     {
/* 163 */       return result[0].add(result[1]);
/*     */     }
/*     */     
/* 166 */     if (a < 0) {
/*     */       
/* 168 */       invert = true;
/* 169 */       a = -a;
/*     */     } 
/*     */     
/*     */     do {
/*     */       int prevtrial;
/* 174 */       r[0] = new Dfp(base[0]);
/* 175 */       r[1] = new Dfp(base[1]);
/* 176 */       int trial = 1;
/*     */ 
/*     */       
/*     */       while (true) {
/* 180 */         prevtrial = trial;
/* 181 */         trial *= 2;
/* 182 */         if (trial > a) {
/*     */           break;
/*     */         }
/* 185 */         r = splitMult(r, r);
/*     */       } 
/*     */       
/* 188 */       trial = prevtrial;
/*     */       
/* 190 */       a -= trial;
/* 191 */       result = splitMult(result, r);
/*     */     }
/* 193 */     while (a >= 1);
/*     */     
/* 195 */     result[0] = result[0].add(result[1]);
/*     */     
/* 197 */     if (invert) {
/* 198 */       result[0] = base[0].getOne().divide(result[0]);
/*     */     }
/*     */     
/* 201 */     return result[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Dfp pow(Dfp base, int a) {
/* 212 */     boolean invert = false;
/*     */     
/* 214 */     Dfp result = base.getOne();
/*     */     
/* 216 */     if (a == 0)
/*     */     {
/* 218 */       return result;
/*     */     }
/*     */     
/* 221 */     if (a < 0) {
/* 222 */       invert = true;
/* 223 */       a = -a;
/*     */     } 
/*     */     do {
/*     */       Dfp prevr;
/*     */       int prevtrial;
/* 228 */       Dfp r = new Dfp(base);
/*     */       
/* 230 */       int trial = 1;
/*     */ 
/*     */       
/*     */       do {
/* 234 */         prevr = new Dfp(r);
/* 235 */         prevtrial = trial;
/* 236 */         r = r.multiply(r);
/* 237 */         trial *= 2;
/* 238 */       } while (a > trial);
/*     */       
/* 240 */       r = prevr;
/* 241 */       trial = prevtrial;
/*     */       
/* 243 */       a -= trial;
/* 244 */       result = result.multiply(r);
/*     */     }
/* 246 */     while (a >= 1);
/*     */     
/* 248 */     if (invert) {
/* 249 */       result = base.getOne().divide(result);
/*     */     }
/*     */     
/* 252 */     return base.newInstance(result);
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
/*     */   public static Dfp exp(Dfp a) {
/* 265 */     Dfp inta = a.rint();
/* 266 */     Dfp fraca = a.subtract(inta);
/*     */     
/* 268 */     int ia = inta.intValue();
/* 269 */     if (ia > 2147483646)
/*     */     {
/* 271 */       return a.newInstance((byte)1, (byte)1);
/*     */     }
/*     */     
/* 274 */     if (ia < -2147483646)
/*     */     {
/* 276 */       return a.newInstance();
/*     */     }
/*     */     
/* 279 */     Dfp einta = splitPow(a.getField().getESplit(), ia);
/* 280 */     Dfp efraca = expInternal(fraca);
/*     */     
/* 282 */     return einta.multiply(efraca);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Dfp expInternal(Dfp a) {
/* 291 */     Dfp y = a.getOne();
/* 292 */     Dfp x = a.getOne();
/* 293 */     Dfp fact = a.getOne();
/* 294 */     Dfp py = new Dfp(y);
/*     */     
/* 296 */     for (int i = 1; i < 90; i++) {
/* 297 */       x = x.multiply(a);
/* 298 */       fact = fact.divide(i);
/* 299 */       y = y.add(x.multiply(fact));
/* 300 */       if (y.equals(py)) {
/*     */         break;
/*     */       }
/* 303 */       py = new Dfp(y);
/*     */     } 
/*     */     
/* 306 */     return y;
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
/*     */   public static Dfp log(Dfp a) {
/* 320 */     int p2 = 0;
/*     */ 
/*     */     
/* 323 */     if (a.equals(a.getZero()) || a.lessThan(a.getZero()) || a.isNaN()) {
/*     */       
/* 325 */       a.getField().setIEEEFlagsBits(1);
/* 326 */       return a.dotrap(1, "ln", a, a.newInstance((byte)1, (byte)3));
/*     */     } 
/*     */     
/* 329 */     if (a.classify() == 1) {
/* 330 */       return a;
/*     */     }
/*     */     
/* 333 */     Dfp x = new Dfp(a);
/* 334 */     int lr = x.log10K();
/*     */     
/* 336 */     x = x.divide(pow(a.newInstance(10000), lr));
/* 337 */     int ix = x.floor().intValue();
/*     */     
/* 339 */     while (ix > 2) {
/* 340 */       ix >>= 1;
/* 341 */       p2++;
/*     */     } 
/*     */ 
/*     */     
/* 345 */     Dfp[] spx = split(x);
/* 346 */     Dfp[] spy = new Dfp[2];
/* 347 */     spy[0] = pow(a.getTwo(), p2);
/* 348 */     spx[0] = spx[0].divide(spy[0]);
/* 349 */     spx[1] = spx[1].divide(spy[0]);
/*     */     
/* 351 */     spy[0] = a.newInstance("1.33333");
/* 352 */     while (spx[0].add(spx[1]).greaterThan(spy[0])) {
/* 353 */       spx[0] = spx[0].divide(2);
/* 354 */       spx[1] = spx[1].divide(2);
/* 355 */       p2++;
/*     */     } 
/*     */ 
/*     */     
/* 359 */     Dfp[] spz = logInternal(spx);
/*     */     
/* 361 */     spx[0] = a.newInstance(p2 + 4 * lr);
/* 362 */     spx[1] = a.getZero();
/* 363 */     spy = splitMult(a.getField().getLn2Split(), spx);
/*     */     
/* 365 */     spz[0] = spz[0].add(spy[0]);
/* 366 */     spz[1] = spz[1].add(spy[1]);
/*     */     
/* 368 */     spx[0] = a.newInstance(4 * lr);
/* 369 */     spx[1] = a.getZero();
/* 370 */     spy = splitMult(a.getField().getLn5Split(), spx);
/*     */     
/* 372 */     spz[0] = spz[0].add(spy[0]);
/* 373 */     spz[1] = spz[1].add(spy[1]);
/*     */     
/* 375 */     return a.newInstance(spz[0].add(spz[1]));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Dfp[] logInternal(Dfp[] a) {
/* 439 */     Dfp t = a[0].divide(4).add(a[1].divide(4));
/* 440 */     Dfp x = t.add(a[0].newInstance("-0.25")).divide(t.add(a[0].newInstance("0.25")));
/*     */     
/* 442 */     Dfp y = new Dfp(x);
/* 443 */     Dfp num = new Dfp(x);
/* 444 */     Dfp py = new Dfp(y);
/* 445 */     int den = 1;
/* 446 */     for (int i = 0; i < 10000; i++) {
/* 447 */       num = num.multiply(x);
/* 448 */       num = num.multiply(x);
/* 449 */       den += 2;
/* 450 */       t = num.divide(den);
/* 451 */       y = y.add(t);
/* 452 */       if (y.equals(py)) {
/*     */         break;
/*     */       }
/* 455 */       py = new Dfp(y);
/*     */     } 
/*     */     
/* 458 */     y = y.multiply(a[0].getTwo());
/*     */     
/* 460 */     return split(y);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Dfp pow(Dfp x, Dfp y) {
/*     */     Dfp r;
/* 507 */     if (x.getField().getRadixDigits() != y.getField().getRadixDigits()) {
/* 508 */       x.getField().setIEEEFlagsBits(1);
/* 509 */       Dfp result = x.newInstance(x.getZero());
/* 510 */       result.nans = 3;
/* 511 */       return x.dotrap(1, "pow", x, result);
/*     */     } 
/*     */     
/* 514 */     Dfp zero = x.getZero();
/* 515 */     Dfp one = x.getOne();
/* 516 */     Dfp two = x.getTwo();
/* 517 */     boolean invert = false;
/*     */ 
/*     */ 
/*     */     
/* 521 */     if (y.equals(zero)) {
/* 522 */       return x.newInstance(one);
/*     */     }
/*     */     
/* 525 */     if (y.equals(one)) {
/* 526 */       if (x.isNaN()) {
/*     */         
/* 528 */         x.getField().setIEEEFlagsBits(1);
/* 529 */         return x.dotrap(1, "pow", x, x);
/*     */       } 
/* 531 */       return x;
/*     */     } 
/*     */     
/* 534 */     if (x.isNaN() || y.isNaN()) {
/*     */       
/* 536 */       x.getField().setIEEEFlagsBits(1);
/* 537 */       return x.dotrap(1, "pow", x, x.newInstance((byte)1, (byte)3));
/*     */     } 
/*     */ 
/*     */     
/* 541 */     if (x.equals(zero)) {
/* 542 */       if (Dfp.copysign(one, x).greaterThan(zero)) {
/*     */         
/* 544 */         if (y.greaterThan(zero)) {
/* 545 */           return x.newInstance(zero);
/*     */         }
/* 547 */         return x.newInstance(x.newInstance((byte)1, (byte)1));
/*     */       } 
/*     */ 
/*     */       
/* 551 */       if (y.classify() == 0 && y.rint().equals(y) && !y.remainder(two).equals(zero)) {
/*     */         
/* 553 */         if (y.greaterThan(zero)) {
/* 554 */           return x.newInstance(zero.negate());
/*     */         }
/* 556 */         return x.newInstance(x.newInstance((byte)-1, (byte)1));
/*     */       } 
/*     */ 
/*     */       
/* 560 */       if (y.greaterThan(zero)) {
/* 561 */         return x.newInstance(zero);
/*     */       }
/* 563 */       return x.newInstance(x.newInstance((byte)1, (byte)1));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 569 */     if (x.lessThan(zero)) {
/*     */       
/* 571 */       x = x.negate();
/* 572 */       invert = true;
/*     */     } 
/*     */     
/* 575 */     if (x.greaterThan(one) && y.classify() == 1) {
/* 576 */       if (y.greaterThan(zero)) {
/* 577 */         return y;
/*     */       }
/* 579 */       return x.newInstance(zero);
/*     */     } 
/*     */ 
/*     */     
/* 583 */     if (x.lessThan(one) && y.classify() == 1) {
/* 584 */       if (y.greaterThan(zero)) {
/* 585 */         return x.newInstance(zero);
/*     */       }
/* 587 */       return x.newInstance(Dfp.copysign(y, one));
/*     */     } 
/*     */ 
/*     */     
/* 591 */     if (x.equals(one) && y.classify() == 1) {
/* 592 */       x.getField().setIEEEFlagsBits(1);
/* 593 */       return x.dotrap(1, "pow", x, x.newInstance((byte)1, (byte)3));
/*     */     } 
/*     */     
/* 596 */     if (x.classify() == 1) {
/*     */       
/* 598 */       if (invert) {
/*     */         
/* 600 */         if (y.classify() == 0 && y.rint().equals(y) && !y.remainder(two).equals(zero)) {
/*     */           
/* 602 */           if (y.greaterThan(zero)) {
/* 603 */             return x.newInstance(x.newInstance((byte)-1, (byte)1));
/*     */           }
/* 605 */           return x.newInstance(zero.negate());
/*     */         } 
/*     */ 
/*     */         
/* 609 */         if (y.greaterThan(zero)) {
/* 610 */           return x.newInstance(x.newInstance((byte)1, (byte)1));
/*     */         }
/* 612 */         return x.newInstance(zero);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 617 */       if (y.greaterThan(zero)) {
/* 618 */         return x;
/*     */       }
/* 620 */       return x.newInstance(zero);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 625 */     if (invert && !y.rint().equals(y)) {
/* 626 */       x.getField().setIEEEFlagsBits(1);
/* 627 */       return x.dotrap(1, "pow", x, x.newInstance((byte)1, (byte)3));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 633 */     if (y.lessThan(x.newInstance(100000000)) && y.greaterThan(x.newInstance(-100000000))) {
/* 634 */       Dfp u = y.rint();
/* 635 */       int ui = u.intValue();
/*     */       
/* 637 */       Dfp v = y.subtract(u);
/*     */       
/* 639 */       if (v.unequal(zero)) {
/* 640 */         Dfp a = v.multiply(log(x));
/* 641 */         Dfp b = a.divide(x.getField().getLn2()).rint();
/*     */         
/* 643 */         Dfp c = a.subtract(b.multiply(x.getField().getLn2()));
/* 644 */         r = splitPow(split(x), ui);
/* 645 */         r = r.multiply(pow(two, b.intValue()));
/* 646 */         r = r.multiply(exp(c));
/*     */       } else {
/* 648 */         r = splitPow(split(x), ui);
/*     */       } 
/*     */     } else {
/*     */       
/* 652 */       r = exp(log(x).multiply(y));
/*     */     } 
/*     */     
/* 655 */     if (invert && y.rint().equals(y) && !y.remainder(two).equals(zero))
/*     */     {
/* 657 */       r = r.negate();
/*     */     }
/*     */     
/* 660 */     return x.newInstance(r);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Dfp sinInternal(Dfp[] a) {
/* 671 */     Dfp c = a[0].add(a[1]);
/* 672 */     Dfp y = c;
/* 673 */     c = c.multiply(c);
/* 674 */     Dfp x = y;
/* 675 */     Dfp fact = a[0].getOne();
/* 676 */     Dfp py = new Dfp(y);
/*     */     
/* 678 */     for (int i = 3; i < 90; i += 2) {
/* 679 */       x = x.multiply(c);
/* 680 */       x = x.negate();
/*     */       
/* 682 */       fact = fact.divide((i - 1) * i);
/* 683 */       y = y.add(x.multiply(fact));
/* 684 */       if (y.equals(py)) {
/*     */         break;
/*     */       }
/* 687 */       py = new Dfp(y);
/*     */     } 
/*     */     
/* 690 */     return y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Dfp cosInternal(Dfp[] a) {
/* 700 */     Dfp one = a[0].getOne();
/*     */ 
/*     */     
/* 703 */     Dfp x = one;
/* 704 */     Dfp y = one;
/* 705 */     Dfp c = a[0].add(a[1]);
/* 706 */     c = c.multiply(c);
/*     */     
/* 708 */     Dfp fact = one;
/* 709 */     Dfp py = new Dfp(y);
/*     */     
/* 711 */     for (int i = 2; i < 90; i += 2) {
/* 712 */       x = x.multiply(c);
/* 713 */       x = x.negate();
/*     */       
/* 715 */       fact = fact.divide((i - 1) * i);
/*     */       
/* 717 */       y = y.add(x.multiply(fact));
/* 718 */       if (y.equals(py)) {
/*     */         break;
/*     */       }
/* 721 */       py = new Dfp(y);
/*     */     } 
/*     */     
/* 724 */     return y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Dfp sin(Dfp a) {
/* 733 */     Dfp y, pi = a.getField().getPi();
/* 734 */     Dfp zero = a.getField().getZero();
/* 735 */     boolean neg = false;
/*     */ 
/*     */     
/* 738 */     Dfp x = a.remainder(pi.multiply(2));
/*     */ 
/*     */ 
/*     */     
/* 742 */     if (x.lessThan(zero)) {
/* 743 */       x = x.negate();
/* 744 */       neg = true;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 751 */     if (x.greaterThan(pi.divide(2))) {
/* 752 */       x = pi.subtract(x);
/*     */     }
/*     */ 
/*     */     
/* 756 */     if (x.lessThan(pi.divide(4))) {
/* 757 */       y = sinInternal(split(x));
/*     */     } else {
/* 759 */       Dfp[] c = new Dfp[2];
/* 760 */       Dfp[] piSplit = a.getField().getPiSplit();
/* 761 */       c[0] = piSplit[0].divide(2).subtract(x);
/* 762 */       c[1] = piSplit[1].divide(2);
/* 763 */       y = cosInternal(c);
/*     */     } 
/*     */     
/* 766 */     if (neg) {
/* 767 */       y = y.negate();
/*     */     }
/*     */     
/* 770 */     return a.newInstance(y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Dfp cos(Dfp a) {
/* 779 */     Dfp y, pi = a.getField().getPi();
/* 780 */     Dfp zero = a.getField().getZero();
/* 781 */     boolean neg = false;
/*     */ 
/*     */     
/* 784 */     Dfp x = a.remainder(pi.multiply(2));
/*     */ 
/*     */ 
/*     */     
/* 788 */     if (x.lessThan(zero)) {
/* 789 */       x = x.negate();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 796 */     if (x.greaterThan(pi.divide(2))) {
/* 797 */       x = pi.subtract(x);
/* 798 */       neg = true;
/*     */     } 
/*     */ 
/*     */     
/* 802 */     if (x.lessThan(pi.divide(4))) {
/* 803 */       Dfp[] c = new Dfp[2];
/* 804 */       c[0] = x;
/* 805 */       c[1] = zero;
/*     */       
/* 807 */       y = cosInternal(c);
/*     */     } else {
/* 809 */       Dfp[] c = new Dfp[2];
/* 810 */       Dfp[] piSplit = a.getField().getPiSplit();
/* 811 */       c[0] = piSplit[0].divide(2).subtract(x);
/* 812 */       c[1] = piSplit[1].divide(2);
/* 813 */       y = sinInternal(c);
/*     */     } 
/*     */     
/* 816 */     if (neg) {
/* 817 */       y = y.negate();
/*     */     }
/*     */     
/* 820 */     return a.newInstance(y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Dfp tan(Dfp a) {
/* 829 */     return sin(a).divide(cos(a));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Dfp atanInternal(Dfp a) {
/* 838 */     Dfp y = new Dfp(a);
/* 839 */     Dfp x = new Dfp(y);
/* 840 */     Dfp py = new Dfp(y);
/*     */     
/* 842 */     for (int i = 3; i < 90; i += 2) {
/* 843 */       x = x.multiply(a);
/* 844 */       x = x.multiply(a);
/* 845 */       x = x.negate();
/* 846 */       y = y.add(x.divide(i));
/* 847 */       if (y.equals(py)) {
/*     */         break;
/*     */       }
/* 850 */       py = new Dfp(y);
/*     */     } 
/*     */     
/* 853 */     return y;
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
/*     */   public static Dfp atan(Dfp a) {
/* 871 */     Dfp zero = a.getField().getZero();
/* 872 */     Dfp one = a.getField().getOne();
/* 873 */     Dfp[] sqr2Split = a.getField().getSqr2Split();
/* 874 */     Dfp[] piSplit = a.getField().getPiSplit();
/* 875 */     boolean recp = false;
/* 876 */     boolean neg = false;
/* 877 */     boolean sub = false;
/*     */     
/* 879 */     Dfp ty = sqr2Split[0].subtract(one).add(sqr2Split[1]);
/*     */     
/* 881 */     Dfp x = new Dfp(a);
/* 882 */     if (x.lessThan(zero)) {
/* 883 */       neg = true;
/* 884 */       x = x.negate();
/*     */     } 
/*     */     
/* 887 */     if (x.greaterThan(one)) {
/* 888 */       recp = true;
/* 889 */       x = one.divide(x);
/*     */     } 
/*     */     
/* 892 */     if (x.greaterThan(ty)) {
/* 893 */       Dfp[] sty = new Dfp[2];
/* 894 */       sub = true;
/*     */       
/* 896 */       sty[0] = sqr2Split[0].subtract(one);
/* 897 */       sty[1] = sqr2Split[1];
/*     */       
/* 899 */       Dfp[] xs = split(x);
/*     */       
/* 901 */       Dfp[] ds = splitMult(xs, sty);
/* 902 */       ds[0] = ds[0].add(one);
/*     */       
/* 904 */       xs[0] = xs[0].subtract(sty[0]);
/* 905 */       xs[1] = xs[1].subtract(sty[1]);
/*     */       
/* 907 */       xs = splitDiv(xs, ds);
/* 908 */       x = xs[0].add(xs[1]);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 913 */     Dfp y = atanInternal(x);
/*     */     
/* 915 */     if (sub) {
/* 916 */       y = y.add(piSplit[0].divide(8)).add(piSplit[1].divide(8));
/*     */     }
/*     */     
/* 919 */     if (recp) {
/* 920 */       y = piSplit[0].divide(2).subtract(y).add(piSplit[1].divide(2));
/*     */     }
/*     */     
/* 923 */     if (neg) {
/* 924 */       y = y.negate();
/*     */     }
/*     */     
/* 927 */     return a.newInstance(y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Dfp asin(Dfp a) {
/* 936 */     return atan(a.divide(a.getOne().subtract(a.multiply(a)).sqrt()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Dfp acos(Dfp a) {
/* 945 */     boolean negative = false;
/*     */     
/* 947 */     if (a.lessThan(a.getZero())) {
/* 948 */       negative = true;
/*     */     }
/*     */     
/* 951 */     a = Dfp.copysign(a, a.getOne());
/*     */     
/* 953 */     Dfp result = atan(a.getOne().subtract(a.multiply(a)).sqrt().divide(a));
/*     */     
/* 955 */     if (negative) {
/* 956 */       result = a.getField().getPi().subtract(result);
/*     */     }
/*     */     
/* 959 */     return a.newInstance(result);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/dfp/DfpMath.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */