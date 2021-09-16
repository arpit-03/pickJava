/*     */ package org.joda.time.chrono;
/*     */ 
/*     */ import org.joda.time.DateTimeField;
/*     */ import org.joda.time.DateTimeFieldType;
/*     */ import org.joda.time.DateTimeUtils;
/*     */ import org.joda.time.DurationField;
/*     */ import org.joda.time.ReadablePartial;
/*     */ import org.joda.time.field.FieldUtils;
/*     */ import org.joda.time.field.ImpreciseDateTimeField;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class BasicMonthOfYearDateTimeField
/*     */   extends ImpreciseDateTimeField
/*     */ {
/*     */   private static final long serialVersionUID = -8258715387168736L;
/*     */   private static final int MIN = 1;
/*     */   private final BasicChronology iChronology;
/*     */   private final int iMax;
/*     */   private final int iLeapMonth;
/*     */   
/*     */   BasicMonthOfYearDateTimeField(BasicChronology paramBasicChronology, int paramInt) {
/*  52 */     super(DateTimeFieldType.monthOfYear(), paramBasicChronology.getAverageMillisPerMonth());
/*  53 */     this.iChronology = paramBasicChronology;
/*  54 */     this.iMax = this.iChronology.getMaxMonth();
/*  55 */     this.iLeapMonth = paramInt;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLenient() {
/*  60 */     return false;
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
/*     */   public int get(long paramLong) {
/*  73 */     return this.iChronology.getMonthOfYear(paramLong);
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
/*     */   public long add(long paramLong, int paramInt) {
/*  93 */     if (paramInt == 0) {
/*  94 */       return paramLong;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  99 */     long l1 = this.iChronology.getMillisOfDay(paramLong);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 104 */     int i = this.iChronology.getYear(paramLong);
/* 105 */     int j = this.iChronology.getMonthOfYear(paramLong, i);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     int k = i;
/*     */     
/* 113 */     int m = j - 1 + paramInt;
/* 114 */     if (j > 0 && m < 0) {
/* 115 */       k++;
/* 116 */       paramInt -= this.iMax;
/* 117 */       m = j - 1 + paramInt;
/*     */     } 
/* 119 */     if (m >= 0) {
/* 120 */       k += m / this.iMax;
/* 121 */       m = m % this.iMax + 1;
/*     */     } else {
/* 123 */       k = k + m / this.iMax - 1;
/* 124 */       m = Math.abs(m);
/* 125 */       int i2 = m % this.iMax;
/*     */       
/* 127 */       if (i2 == 0) {
/* 128 */         i2 = this.iMax;
/*     */       }
/* 130 */       m = this.iMax - i2 + 1;
/*     */       
/* 132 */       if (m == 1) {
/* 133 */         k++;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 142 */     int n = this.iChronology.getDayOfMonth(paramLong, i, j);
/* 143 */     int i1 = this.iChronology.getDaysInYearMonth(k, m);
/* 144 */     if (n > i1) {
/* 145 */       n = i1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 150 */     long l2 = this.iChronology.getYearMonthDayMillis(k, m, n);
/*     */     
/* 152 */     return l2 + l1;
/*     */   }
/*     */   
/*     */   public long add(long paramLong1, long paramLong2) {
/*     */     long l2;
/* 157 */     int i = (int)paramLong2;
/* 158 */     if (i == paramLong2) {
/* 159 */       return add(paramLong1, i);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 164 */     long l1 = this.iChronology.getMillisOfDay(paramLong1);
/*     */     
/* 166 */     int j = this.iChronology.getYear(paramLong1);
/* 167 */     int k = this.iChronology.getMonthOfYear(paramLong1, j);
/*     */ 
/*     */     
/* 170 */     long l3 = (k - 1) + paramLong2;
/* 171 */     if (l3 >= 0L) {
/* 172 */       l2 = j + l3 / this.iMax;
/* 173 */       l3 = l3 % this.iMax + 1L;
/*     */     } else {
/* 175 */       l2 = j + l3 / this.iMax - 1L;
/* 176 */       l3 = Math.abs(l3);
/* 177 */       int i3 = (int)(l3 % this.iMax);
/* 178 */       if (i3 == 0) {
/* 179 */         i3 = this.iMax;
/*     */       }
/* 181 */       l3 = (this.iMax - i3 + 1);
/* 182 */       if (l3 == 1L) {
/* 183 */         l2++;
/*     */       }
/*     */     } 
/*     */     
/* 187 */     if (l2 < this.iChronology.getMinYear() || l2 > this.iChronology.getMaxYear())
/*     */     {
/*     */       
/* 190 */       throw new IllegalArgumentException("Magnitude of add amount is too large: " + paramLong2);
/*     */     }
/*     */ 
/*     */     
/* 194 */     int m = (int)l2;
/* 195 */     int n = (int)l3;
/*     */     
/* 197 */     int i1 = this.iChronology.getDayOfMonth(paramLong1, j, k);
/* 198 */     int i2 = this.iChronology.getDaysInYearMonth(m, n);
/* 199 */     if (i1 > i2) {
/* 200 */       i1 = i2;
/*     */     }
/*     */     
/* 203 */     long l4 = this.iChronology.getYearMonthDayMillis(m, n, i1);
/*     */     
/* 205 */     return l4 + l1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] add(ReadablePartial paramReadablePartial, int paramInt1, int[] paramArrayOfint, int paramInt2) {
/* 212 */     if (paramInt2 == 0) {
/* 213 */       return paramArrayOfint;
/*     */     }
/* 215 */     if (paramReadablePartial.size() > 0 && paramReadablePartial.getFieldType(0).equals(DateTimeFieldType.monthOfYear()) && paramInt1 == 0) {
/*     */       
/* 217 */       int i = paramArrayOfint[0] - 1;
/* 218 */       int j = (i + paramInt2 % 12 + 12) % 12 + 1;
/* 219 */       return set(paramReadablePartial, 0, paramArrayOfint, j);
/*     */     } 
/* 221 */     if (DateTimeUtils.isContiguous(paramReadablePartial)) {
/* 222 */       long l = 0L; byte b; int i;
/* 223 */       for (b = 0, i = paramReadablePartial.size(); b < i; b++) {
/* 224 */         l = paramReadablePartial.getFieldType(b).getField(this.iChronology).set(l, paramArrayOfint[b]);
/*     */       }
/* 226 */       l = add(l, paramInt2);
/* 227 */       return this.iChronology.get(paramReadablePartial, l);
/*     */     } 
/* 229 */     return super.add(paramReadablePartial, paramInt1, paramArrayOfint, paramInt2);
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
/*     */   public long addWrapField(long paramLong, int paramInt) {
/* 244 */     return set(paramLong, FieldUtils.getWrappedValue(get(paramLong), paramInt, 1, this.iMax));
/*     */   }
/*     */ 
/*     */   
/*     */   public long getDifferenceAsLong(long paramLong1, long paramLong2) {
/* 249 */     if (paramLong1 < paramLong2) {
/* 250 */       return -getDifference(paramLong2, paramLong1);
/*     */     }
/*     */     
/* 253 */     int i = this.iChronology.getYear(paramLong1);
/* 254 */     int j = this.iChronology.getMonthOfYear(paramLong1, i);
/* 255 */     int k = this.iChronology.getYear(paramLong2);
/* 256 */     int m = this.iChronology.getMonthOfYear(paramLong2, k);
/*     */     
/* 258 */     long l1 = (i - k) * this.iMax + j - m;
/*     */ 
/*     */ 
/*     */     
/* 262 */     int n = this.iChronology.getDayOfMonth(paramLong1, i, j);
/*     */     
/* 264 */     if (n == this.iChronology.getDaysInYearMonth(i, j)) {
/*     */       
/* 266 */       int i1 = this.iChronology.getDayOfMonth(paramLong2, k, m);
/*     */       
/* 268 */       if (i1 > n)
/*     */       {
/*     */ 
/*     */         
/* 272 */         paramLong2 = this.iChronology.dayOfMonth().set(paramLong2, n);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 277 */     long l2 = paramLong1 - this.iChronology.getYearMonthMillis(i, j);
/*     */     
/* 279 */     long l3 = paramLong2 - this.iChronology.getYearMonthMillis(k, m);
/*     */ 
/*     */     
/* 282 */     if (l2 < l3) {
/* 283 */       l1--;
/*     */     }
/*     */     
/* 286 */     return l1;
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
/*     */   public long set(long paramLong, int paramInt) {
/* 304 */     FieldUtils.verifyValueBounds((DateTimeField)this, paramInt, 1, this.iMax);
/*     */     
/* 306 */     int i = this.iChronology.getYear(paramLong);
/*     */     
/* 308 */     int j = this.iChronology.getDayOfMonth(paramLong, i);
/* 309 */     int k = this.iChronology.getDaysInYearMonth(i, paramInt);
/* 310 */     if (j > k)
/*     */     {
/* 312 */       j = k;
/*     */     }
/*     */     
/* 315 */     return this.iChronology.getYearMonthDayMillis(i, paramInt, j) + this.iChronology.getMillisOfDay(paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DurationField getRangeDurationField() {
/* 321 */     return this.iChronology.years();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLeap(long paramLong) {
/* 326 */     int i = this.iChronology.getYear(paramLong);
/* 327 */     if (this.iChronology.isLeapYear(i)) {
/* 328 */       return (this.iChronology.getMonthOfYear(paramLong, i) == this.iLeapMonth);
/*     */     }
/* 330 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLeapAmount(long paramLong) {
/* 335 */     return isLeap(paramLong) ? 1 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public DurationField getLeapDurationField() {
/* 340 */     return this.iChronology.days();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMinimumValue() {
/* 345 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaximumValue() {
/* 350 */     return this.iMax;
/*     */   }
/*     */ 
/*     */   
/*     */   public long roundFloor(long paramLong) {
/* 355 */     int i = this.iChronology.getYear(paramLong);
/* 356 */     int j = this.iChronology.getMonthOfYear(paramLong, i);
/* 357 */     return this.iChronology.getYearMonthMillis(i, j);
/*     */   }
/*     */ 
/*     */   
/*     */   public long remainder(long paramLong) {
/* 362 */     return paramLong - roundFloor(paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() {
/* 370 */     return this.iChronology.monthOfYear();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/chrono/BasicMonthOfYearDateTimeField.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */