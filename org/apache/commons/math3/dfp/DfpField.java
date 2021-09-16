/*     */ package org.apache.commons.math3.dfp;public class DfpField implements Field<Dfp> { public static final int FLAG_INVALID = 1; public static final int FLAG_DIV_ZERO = 2; public static final int FLAG_OVERFLOW = 4; public static final int FLAG_UNDERFLOW = 8; public static final int FLAG_INEXACT = 16;
/*     */   private static String sqr2String;
/*     */   private static String sqr2ReciprocalString;
/*     */   private static String sqr3String;
/*     */   private static String sqr3ReciprocalString;
/*     */   private static String piString;
/*     */   private static String eString;
/*     */   private static String ln2String;
/*     */   private static String ln5String;
/*     */   private static String ln10String;
/*     */   private final int radixDigits;
/*     */   private final Dfp zero;
/*     */   private final Dfp one;
/*     */   private final Dfp two;
/*     */   private final Dfp sqr2;
/*     */   private final Dfp[] sqr2Split;
/*     */   private final Dfp sqr2Reciprocal;
/*     */   private final Dfp sqr3;
/*     */   private final Dfp sqr3Reciprocal;
/*     */   private final Dfp pi;
/*     */   private final Dfp[] piSplit;
/*     */   private final Dfp e;
/*     */   private final Dfp[] eSplit;
/*     */   private final Dfp ln2;
/*     */   private final Dfp[] ln2Split;
/*     */   private final Dfp ln5;
/*     */   private final Dfp[] ln5Split;
/*     */   private final Dfp ln10;
/*     */   private RoundingMode rMode;
/*     */   private int ieeeFlags;
/*     */   
/*  32 */   public enum RoundingMode { ROUND_DOWN,
/*     */ 
/*     */     
/*  35 */     ROUND_UP,
/*     */ 
/*     */     
/*  38 */     ROUND_HALF_UP,
/*     */ 
/*     */     
/*  41 */     ROUND_HALF_DOWN,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  46 */     ROUND_HALF_EVEN,
/*     */ 
/*     */     
/*  49 */     ROUND_HALF_ODD,
/*     */ 
/*     */     
/*  52 */     ROUND_CEIL,
/*     */ 
/*     */     
/*  55 */     ROUND_FLOOR; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DfpField(int decimalDigits) {
/* 176 */     this(decimalDigits, true);
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
/*     */   private DfpField(int decimalDigits, boolean computeConstants) {
/* 192 */     this.radixDigits = (decimalDigits < 13) ? 4 : ((decimalDigits + 3) / 4);
/* 193 */     this.rMode = RoundingMode.ROUND_HALF_EVEN;
/* 194 */     this.ieeeFlags = 0;
/* 195 */     this.zero = new Dfp(this, 0);
/* 196 */     this.one = new Dfp(this, 1);
/* 197 */     this.two = new Dfp(this, 2);
/*     */     
/* 199 */     if (computeConstants) {
/*     */       
/* 201 */       synchronized (DfpField.class)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 207 */         computeStringConstants((decimalDigits < 67) ? 200 : (3 * decimalDigits));
/*     */ 
/*     */         
/* 210 */         this.sqr2 = new Dfp(this, sqr2String);
/* 211 */         this.sqr2Split = split(sqr2String);
/* 212 */         this.sqr2Reciprocal = new Dfp(this, sqr2ReciprocalString);
/* 213 */         this.sqr3 = new Dfp(this, sqr3String);
/* 214 */         this.sqr3Reciprocal = new Dfp(this, sqr3ReciprocalString);
/* 215 */         this.pi = new Dfp(this, piString);
/* 216 */         this.piSplit = split(piString);
/* 217 */         this.e = new Dfp(this, eString);
/* 218 */         this.eSplit = split(eString);
/* 219 */         this.ln2 = new Dfp(this, ln2String);
/* 220 */         this.ln2Split = split(ln2String);
/* 221 */         this.ln5 = new Dfp(this, ln5String);
/* 222 */         this.ln5Split = split(ln5String);
/* 223 */         this.ln10 = new Dfp(this, ln10String);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 228 */       this.sqr2 = null;
/* 229 */       this.sqr2Split = null;
/* 230 */       this.sqr2Reciprocal = null;
/* 231 */       this.sqr3 = null;
/* 232 */       this.sqr3Reciprocal = null;
/* 233 */       this.pi = null;
/* 234 */       this.piSplit = null;
/* 235 */       this.e = null;
/* 236 */       this.eSplit = null;
/* 237 */       this.ln2 = null;
/* 238 */       this.ln2Split = null;
/* 239 */       this.ln5 = null;
/* 240 */       this.ln5Split = null;
/* 241 */       this.ln10 = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRadixDigits() {
/* 250 */     return this.radixDigits;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRoundingMode(RoundingMode mode) {
/* 261 */     this.rMode = mode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RoundingMode getRoundingMode() {
/* 268 */     return this.rMode;
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
/*     */   public int getIEEEFlags() {
/* 283 */     return this.ieeeFlags;
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
/*     */   public void clearIEEEFlags() {
/* 297 */     this.ieeeFlags = 0;
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
/*     */   public void setIEEEFlags(int flags) {
/* 312 */     this.ieeeFlags = flags & 0x1F;
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
/*     */   public void setIEEEFlagsBits(int bits) {
/* 330 */     this.ieeeFlags |= bits & 0x1F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp newDfp() {
/* 337 */     return new Dfp(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp newDfp(byte x) {
/* 345 */     return new Dfp(this, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp newDfp(int x) {
/* 353 */     return new Dfp(this, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp newDfp(long x) {
/* 361 */     return new Dfp(this, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp newDfp(double x) {
/* 369 */     return new Dfp(this, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp newDfp(Dfp d) {
/* 377 */     return new Dfp(d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp newDfp(String s) {
/* 385 */     return new Dfp(this, s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp newDfp(byte sign, byte nans) {
/* 395 */     return new Dfp(this, sign, nans);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp getZero() {
/* 402 */     return this.zero;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp getOne() {
/* 409 */     return this.one;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends FieldElement<Dfp>> getRuntimeClass() {
/* 414 */     return (Class)Dfp.class;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp getTwo() {
/* 421 */     return this.two;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp getSqr2() {
/* 428 */     return this.sqr2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp[] getSqr2Split() {
/* 435 */     return (Dfp[])this.sqr2Split.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp getSqr2Reciprocal() {
/* 442 */     return this.sqr2Reciprocal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp getSqr3() {
/* 449 */     return this.sqr3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp getSqr3Reciprocal() {
/* 456 */     return this.sqr3Reciprocal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp getPi() {
/* 463 */     return this.pi;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp[] getPiSplit() {
/* 470 */     return (Dfp[])this.piSplit.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp getE() {
/* 477 */     return this.e;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp[] getESplit() {
/* 484 */     return (Dfp[])this.eSplit.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp getLn2() {
/* 491 */     return this.ln2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp[] getLn2Split() {
/* 498 */     return (Dfp[])this.ln2Split.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp getLn5() {
/* 505 */     return this.ln5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp[] getLn5Split() {
/* 512 */     return (Dfp[])this.ln5Split.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dfp getLn10() {
/* 519 */     return this.ln10;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Dfp[] split(String a) {
/* 529 */     Dfp[] result = new Dfp[2];
/* 530 */     boolean leading = true;
/* 531 */     int sp = 0;
/* 532 */     int sig = 0;
/*     */     
/* 534 */     char[] buf = new char[a.length()];
/*     */     int i;
/* 536 */     for (i = 0; i < buf.length; i++) {
/* 537 */       buf[i] = a.charAt(i);
/*     */       
/* 539 */       if (buf[i] >= '1' && buf[i] <= '9') {
/* 540 */         leading = false;
/*     */       }
/*     */       
/* 543 */       if (buf[i] == '.') {
/* 544 */         sig += (400 - sig) % 4;
/* 545 */         leading = false;
/*     */       } 
/*     */       
/* 548 */       if (sig == this.radixDigits / 2 * 4) {
/* 549 */         sp = i;
/*     */         
/*     */         break;
/*     */       } 
/* 553 */       if (buf[i] >= '0' && buf[i] <= '9' && !leading) {
/* 554 */         sig++;
/*     */       }
/*     */     } 
/*     */     
/* 558 */     result[0] = new Dfp(this, new String(buf, 0, sp));
/*     */     
/* 560 */     for (i = 0; i < buf.length; i++) {
/* 561 */       buf[i] = a.charAt(i);
/* 562 */       if (buf[i] >= '0' && buf[i] <= '9' && i < sp) {
/* 563 */         buf[i] = '0';
/*     */       }
/*     */     } 
/*     */     
/* 567 */     result[1] = new Dfp(this, new String(buf));
/*     */     
/* 569 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void computeStringConstants(int highPrecisionDecimalDigits) {
/* 577 */     if (sqr2String == null || sqr2String.length() < highPrecisionDecimalDigits - 3) {
/*     */ 
/*     */       
/* 580 */       DfpField highPrecisionField = new DfpField(highPrecisionDecimalDigits, false);
/* 581 */       Dfp highPrecisionOne = new Dfp(highPrecisionField, 1);
/* 582 */       Dfp highPrecisionTwo = new Dfp(highPrecisionField, 2);
/* 583 */       Dfp highPrecisionThree = new Dfp(highPrecisionField, 3);
/*     */       
/* 585 */       Dfp highPrecisionSqr2 = highPrecisionTwo.sqrt();
/* 586 */       sqr2String = highPrecisionSqr2.toString();
/* 587 */       sqr2ReciprocalString = highPrecisionOne.divide(highPrecisionSqr2).toString();
/*     */       
/* 589 */       Dfp highPrecisionSqr3 = highPrecisionThree.sqrt();
/* 590 */       sqr3String = highPrecisionSqr3.toString();
/* 591 */       sqr3ReciprocalString = highPrecisionOne.divide(highPrecisionSqr3).toString();
/*     */       
/* 593 */       piString = computePi(highPrecisionOne, highPrecisionTwo, highPrecisionThree).toString();
/* 594 */       eString = computeExp(highPrecisionOne, highPrecisionOne).toString();
/* 595 */       ln2String = computeLn(highPrecisionTwo, highPrecisionOne, highPrecisionTwo).toString();
/* 596 */       ln5String = computeLn(new Dfp(highPrecisionField, 5), highPrecisionOne, highPrecisionTwo).toString();
/* 597 */       ln10String = computeLn(new Dfp(highPrecisionField, 10), highPrecisionOne, highPrecisionTwo).toString();
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
/*     */   private static Dfp computePi(Dfp one, Dfp two, Dfp three) {
/* 610 */     Dfp sqrt2 = two.sqrt();
/* 611 */     Dfp yk = sqrt2.subtract(one);
/* 612 */     Dfp four = two.add(two);
/* 613 */     Dfp two2kp3 = two;
/* 614 */     Dfp ak = two.multiply(three.subtract(two.multiply(sqrt2)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 622 */     for (int i = 1; i < 20; i++) {
/* 623 */       Dfp ykM1 = yk;
/*     */       
/* 625 */       Dfp y2 = yk.multiply(yk);
/* 626 */       Dfp oneMinusY4 = one.subtract(y2.multiply(y2));
/* 627 */       Dfp s = oneMinusY4.sqrt().sqrt();
/* 628 */       yk = one.subtract(s).divide(one.add(s));
/*     */       
/* 630 */       two2kp3 = two2kp3.multiply(four);
/*     */       
/* 632 */       Dfp p = one.add(yk);
/* 633 */       Dfp p2 = p.multiply(p);
/* 634 */       ak = ak.multiply(p2.multiply(p2)).subtract(two2kp3.multiply(yk).multiply(one.add(yk).add(yk.multiply(yk))));
/*     */       
/* 636 */       if (yk.equals(ykM1)) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 641 */     return one.divide(ak);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Dfp computeExp(Dfp a, Dfp one) {
/* 652 */     Dfp y = new Dfp(one);
/* 653 */     Dfp py = new Dfp(one);
/* 654 */     Dfp f = new Dfp(one);
/* 655 */     Dfp fi = new Dfp(one);
/* 656 */     Dfp x = new Dfp(one);
/*     */     
/* 658 */     for (int i = 0; i < 10000; i++) {
/* 659 */       x = x.multiply(a);
/* 660 */       y = y.add(x.divide(f));
/* 661 */       fi = fi.add(one);
/* 662 */       f = f.multiply(fi);
/* 663 */       if (y.equals(py)) {
/*     */         break;
/*     */       }
/* 666 */       py = new Dfp(y);
/*     */     } 
/*     */     
/* 669 */     return y;
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
/*     */ 
/*     */   
/*     */   public static Dfp computeLn(Dfp a, Dfp one, Dfp two) {
/* 735 */     int den = 1;
/* 736 */     Dfp x = a.add(new Dfp(a.getField(), -1)).divide(a.add(one));
/*     */     
/* 738 */     Dfp y = new Dfp(x);
/* 739 */     Dfp num = new Dfp(x);
/* 740 */     Dfp py = new Dfp(y);
/* 741 */     for (int i = 0; i < 10000; i++) {
/* 742 */       num = num.multiply(x);
/* 743 */       num = num.multiply(x);
/* 744 */       den += 2;
/* 745 */       Dfp t = num.divide(den);
/* 746 */       y = y.add(t);
/* 747 */       if (y.equals(py)) {
/*     */         break;
/*     */       }
/* 750 */       py = new Dfp(y);
/*     */     } 
/*     */     
/* 753 */     return y.multiply(two);
/*     */   } }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/dfp/DfpField.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */