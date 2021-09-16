/*      */ package org.joda.time.field;
/*      */ 
/*      */ import java.util.Locale;
/*      */ import org.joda.time.DateTimeField;
/*      */ import org.joda.time.DateTimeFieldType;
/*      */ import org.joda.time.DurationField;
/*      */ import org.joda.time.IllegalFieldValueException;
/*      */ import org.joda.time.ReadablePartial;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class BaseDateTimeField
/*      */   extends DateTimeField
/*      */ {
/*      */   private final DateTimeFieldType iType;
/*      */   
/*      */   protected BaseDateTimeField(DateTimeFieldType paramDateTimeFieldType) {
/*   51 */     if (paramDateTimeFieldType == null) {
/*   52 */       throw new IllegalArgumentException("The type must not be null");
/*      */     }
/*   54 */     this.iType = paramDateTimeFieldType;
/*      */   }
/*      */   
/*      */   public final DateTimeFieldType getType() {
/*   58 */     return this.iType;
/*      */   }
/*      */   
/*      */   public final String getName() {
/*   62 */     return this.iType.getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isSupported() {
/*   69 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int get(long paramLong);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAsText(long paramLong, Locale paramLocale) {
/*   94 */     return getAsText(get(paramLong), paramLocale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getAsText(long paramLong) {
/*  106 */     return getAsText(paramLong, (Locale)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAsText(ReadablePartial paramReadablePartial, int paramInt, Locale paramLocale) {
/*  121 */     return getAsText(paramInt, paramLocale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getAsText(ReadablePartial paramReadablePartial, Locale paramLocale) {
/*  136 */     return getAsText(paramReadablePartial, paramReadablePartial.get(getType()), paramLocale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAsText(int paramInt, Locale paramLocale) {
/*  153 */     return Integer.toString(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAsShortText(long paramLong, Locale paramLocale) {
/*  168 */     return getAsShortText(get(paramLong), paramLocale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getAsShortText(long paramLong) {
/*  180 */     return getAsShortText(paramLong, (Locale)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAsShortText(ReadablePartial paramReadablePartial, int paramInt, Locale paramLocale) {
/*  195 */     return getAsShortText(paramInt, paramLocale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getAsShortText(ReadablePartial paramReadablePartial, Locale paramLocale) {
/*  210 */     return getAsShortText(paramReadablePartial, paramReadablePartial.get(getType()), paramLocale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAsShortText(int paramInt, Locale paramLocale) {
/*  227 */     return getAsText(paramInt, paramLocale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long add(long paramLong, int paramInt) {
/*  253 */     return getDurationField().add(paramLong, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long add(long paramLong1, long paramLong2) {
/*  267 */     return getDurationField().add(paramLong1, paramLong2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] add(ReadablePartial paramReadablePartial, int paramInt1, int[] paramArrayOfint, int paramInt2) {
/*  298 */     if (paramInt2 == 0) {
/*  299 */       return paramArrayOfint;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  304 */     DateTimeField dateTimeField = null;
/*      */     
/*  306 */     while (paramInt2 > 0) {
/*  307 */       int i = getMaximumValue(paramReadablePartial, paramArrayOfint);
/*  308 */       long l = (paramArrayOfint[paramInt1] + paramInt2);
/*  309 */       if (l <= i) {
/*  310 */         paramArrayOfint[paramInt1] = (int)l;
/*      */         break;
/*      */       } 
/*  313 */       if (dateTimeField == null) {
/*  314 */         if (paramInt1 == 0) {
/*  315 */           throw new IllegalArgumentException("Maximum value exceeded for add");
/*      */         }
/*  317 */         dateTimeField = paramReadablePartial.getField(paramInt1 - 1);
/*      */         
/*  319 */         if (getRangeDurationField().getType() != dateTimeField.getDurationField().getType()) {
/*  320 */           throw new IllegalArgumentException("Fields invalid for add");
/*      */         }
/*      */       } 
/*  323 */       paramInt2 -= i + 1 - paramArrayOfint[paramInt1];
/*  324 */       paramArrayOfint = dateTimeField.add(paramReadablePartial, paramInt1 - 1, paramArrayOfint, 1);
/*  325 */       paramArrayOfint[paramInt1] = getMinimumValue(paramReadablePartial, paramArrayOfint);
/*      */     } 
/*  327 */     while (paramInt2 < 0) {
/*  328 */       int i = getMinimumValue(paramReadablePartial, paramArrayOfint);
/*  329 */       long l = (paramArrayOfint[paramInt1] + paramInt2);
/*  330 */       if (l >= i) {
/*  331 */         paramArrayOfint[paramInt1] = (int)l;
/*      */         break;
/*      */       } 
/*  334 */       if (dateTimeField == null) {
/*  335 */         if (paramInt1 == 0) {
/*  336 */           throw new IllegalArgumentException("Maximum value exceeded for add");
/*      */         }
/*  338 */         dateTimeField = paramReadablePartial.getField(paramInt1 - 1);
/*  339 */         if (getRangeDurationField().getType() != dateTimeField.getDurationField().getType()) {
/*  340 */           throw new IllegalArgumentException("Fields invalid for add");
/*      */         }
/*      */       } 
/*  343 */       paramInt2 -= i - 1 - paramArrayOfint[paramInt1];
/*  344 */       paramArrayOfint = dateTimeField.add(paramReadablePartial, paramInt1 - 1, paramArrayOfint, -1);
/*  345 */       paramArrayOfint[paramInt1] = getMaximumValue(paramReadablePartial, paramArrayOfint);
/*      */     } 
/*      */     
/*  348 */     return set(paramReadablePartial, paramInt1, paramArrayOfint, paramArrayOfint[paramInt1]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] addWrapPartial(ReadablePartial paramReadablePartial, int paramInt1, int[] paramArrayOfint, int paramInt2) {
/*  379 */     if (paramInt2 == 0) {
/*  380 */       return paramArrayOfint;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  385 */     DateTimeField dateTimeField = null;
/*      */     
/*  387 */     while (paramInt2 > 0) {
/*  388 */       int i = getMaximumValue(paramReadablePartial, paramArrayOfint);
/*  389 */       long l = (paramArrayOfint[paramInt1] + paramInt2);
/*  390 */       if (l <= i) {
/*  391 */         paramArrayOfint[paramInt1] = (int)l;
/*      */         break;
/*      */       } 
/*  394 */       if (dateTimeField == null) {
/*  395 */         if (paramInt1 == 0) {
/*  396 */           paramInt2 -= i + 1 - paramArrayOfint[paramInt1];
/*  397 */           paramArrayOfint[paramInt1] = getMinimumValue(paramReadablePartial, paramArrayOfint);
/*      */           continue;
/*      */         } 
/*  400 */         dateTimeField = paramReadablePartial.getField(paramInt1 - 1);
/*      */         
/*  402 */         if (getRangeDurationField().getType() != dateTimeField.getDurationField().getType()) {
/*  403 */           throw new IllegalArgumentException("Fields invalid for add");
/*      */         }
/*      */       } 
/*  406 */       paramInt2 -= i + 1 - paramArrayOfint[paramInt1];
/*  407 */       paramArrayOfint = dateTimeField.addWrapPartial(paramReadablePartial, paramInt1 - 1, paramArrayOfint, 1);
/*  408 */       paramArrayOfint[paramInt1] = getMinimumValue(paramReadablePartial, paramArrayOfint);
/*      */     } 
/*  410 */     while (paramInt2 < 0) {
/*  411 */       int i = getMinimumValue(paramReadablePartial, paramArrayOfint);
/*  412 */       long l = (paramArrayOfint[paramInt1] + paramInt2);
/*  413 */       if (l >= i) {
/*  414 */         paramArrayOfint[paramInt1] = (int)l;
/*      */         break;
/*      */       } 
/*  417 */       if (dateTimeField == null) {
/*  418 */         if (paramInt1 == 0) {
/*  419 */           paramInt2 -= i - 1 - paramArrayOfint[paramInt1];
/*  420 */           paramArrayOfint[paramInt1] = getMaximumValue(paramReadablePartial, paramArrayOfint);
/*      */           continue;
/*      */         } 
/*  423 */         dateTimeField = paramReadablePartial.getField(paramInt1 - 1);
/*  424 */         if (getRangeDurationField().getType() != dateTimeField.getDurationField().getType()) {
/*  425 */           throw new IllegalArgumentException("Fields invalid for add");
/*      */         }
/*      */       } 
/*  428 */       paramInt2 -= i - 1 - paramArrayOfint[paramInt1];
/*  429 */       paramArrayOfint = dateTimeField.addWrapPartial(paramReadablePartial, paramInt1 - 1, paramArrayOfint, -1);
/*  430 */       paramArrayOfint[paramInt1] = getMaximumValue(paramReadablePartial, paramArrayOfint);
/*      */     } 
/*      */     
/*  433 */     return set(paramReadablePartial, paramInt1, paramArrayOfint, paramArrayOfint[paramInt1]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long addWrapField(long paramLong, int paramInt) {
/*  461 */     int i = get(paramLong);
/*  462 */     int j = FieldUtils.getWrappedValue(i, paramInt, getMinimumValue(paramLong), getMaximumValue(paramLong));
/*      */     
/*  464 */     return set(paramLong, j);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] addWrapField(ReadablePartial paramReadablePartial, int paramInt1, int[] paramArrayOfint, int paramInt2) {
/*  495 */     int i = paramArrayOfint[paramInt1];
/*  496 */     int j = FieldUtils.getWrappedValue(i, paramInt2, getMinimumValue(paramReadablePartial), getMaximumValue(paramReadablePartial));
/*      */     
/*  498 */     return set(paramReadablePartial, paramInt1, paramArrayOfint, j);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDifference(long paramLong1, long paramLong2) {
/*  522 */     return getDurationField().getDifference(paramLong1, paramLong2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getDifferenceAsLong(long paramLong1, long paramLong2) {
/*  545 */     return getDurationField().getDifferenceAsLong(paramLong1, paramLong2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract long set(long paramLong, int paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] set(ReadablePartial paramReadablePartial, int paramInt1, int[] paramArrayOfint, int paramInt2) {
/*  585 */     FieldUtils.verifyValueBounds(this, paramInt2, getMinimumValue(paramReadablePartial, paramArrayOfint), getMaximumValue(paramReadablePartial, paramArrayOfint));
/*  586 */     paramArrayOfint[paramInt1] = paramInt2;
/*      */ 
/*      */     
/*  589 */     for (int i = paramInt1 + 1; i < paramReadablePartial.size(); i++) {
/*  590 */       DateTimeField dateTimeField = paramReadablePartial.getField(i);
/*  591 */       if (paramArrayOfint[i] > dateTimeField.getMaximumValue(paramReadablePartial, paramArrayOfint)) {
/*  592 */         paramArrayOfint[i] = dateTimeField.getMaximumValue(paramReadablePartial, paramArrayOfint);
/*      */       }
/*  594 */       if (paramArrayOfint[i] < dateTimeField.getMinimumValue(paramReadablePartial, paramArrayOfint)) {
/*  595 */         paramArrayOfint[i] = dateTimeField.getMinimumValue(paramReadablePartial, paramArrayOfint);
/*      */       }
/*      */     } 
/*  598 */     return paramArrayOfint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long set(long paramLong, String paramString, Locale paramLocale) {
/*  618 */     int i = convertText(paramString, paramLocale);
/*  619 */     return set(paramLong, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final long set(long paramLong, String paramString) {
/*  635 */     return set(paramLong, paramString, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] set(ReadablePartial paramReadablePartial, int paramInt, int[] paramArrayOfint, String paramString, Locale paramLocale) {
/*  654 */     int i = convertText(paramString, paramLocale);
/*  655 */     return set(paramReadablePartial, paramInt, paramArrayOfint, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int convertText(String paramString, Locale paramLocale) {
/*      */     try {
/*  668 */       return Integer.parseInt(paramString);
/*  669 */     } catch (NumberFormatException numberFormatException) {
/*  670 */       throw new IllegalFieldValueException(getType(), paramString);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract DurationField getDurationField();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract DurationField getRangeDurationField();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isLeap(long paramLong) {
/*  704 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLeapAmount(long paramLong) {
/*  716 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DurationField getLeapDurationField() {
/*  726 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int getMinimumValue();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinimumValue(long paramLong) {
/*  746 */     return getMinimumValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinimumValue(ReadablePartial paramReadablePartial) {
/*  758 */     return getMinimumValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinimumValue(ReadablePartial paramReadablePartial, int[] paramArrayOfint) {
/*  772 */     return getMinimumValue(paramReadablePartial);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int getMaximumValue();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaximumValue(long paramLong) {
/*  792 */     return getMaximumValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaximumValue(ReadablePartial paramReadablePartial) {
/*  804 */     return getMaximumValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaximumValue(ReadablePartial paramReadablePartial, int[] paramArrayOfint) {
/*  818 */     return getMaximumValue(paramReadablePartial);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaximumTextLength(Locale paramLocale) {
/*  829 */     int i = getMaximumValue();
/*  830 */     if (i >= 0) {
/*  831 */       if (i < 10)
/*  832 */         return 1; 
/*  833 */       if (i < 100)
/*  834 */         return 2; 
/*  835 */       if (i < 1000) {
/*  836 */         return 3;
/*      */       }
/*      */     } 
/*  839 */     return Integer.toString(i).length();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaximumShortTextLength(Locale paramLocale) {
/*  850 */     return getMaximumTextLength(paramLocale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract long roundFloor(long paramLong);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long roundCeiling(long paramLong) {
/*  886 */     long l = roundFloor(paramLong);
/*  887 */     if (l != paramLong) {
/*  888 */       paramLong = add(l, 1);
/*      */     }
/*  890 */     return paramLong;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long roundHalfFloor(long paramLong) {
/*  903 */     long l1 = roundFloor(paramLong);
/*  904 */     long l2 = roundCeiling(paramLong);
/*      */     
/*  906 */     long l3 = paramLong - l1;
/*  907 */     long l4 = l2 - paramLong;
/*      */     
/*  909 */     if (l3 <= l4)
/*      */     {
/*  911 */       return l1;
/*      */     }
/*  913 */     return l2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long roundHalfCeiling(long paramLong) {
/*  927 */     long l1 = roundFloor(paramLong);
/*  928 */     long l2 = roundCeiling(paramLong);
/*      */     
/*  930 */     long l3 = paramLong - l1;
/*  931 */     long l4 = l2 - paramLong;
/*      */     
/*  933 */     if (l4 <= l3)
/*      */     {
/*  935 */       return l2;
/*      */     }
/*  937 */     return l1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long roundHalfEven(long paramLong) {
/*  955 */     long l1 = roundFloor(paramLong);
/*  956 */     long l2 = roundCeiling(paramLong);
/*      */     
/*  958 */     long l3 = paramLong - l1;
/*  959 */     long l4 = l2 - paramLong;
/*      */     
/*  961 */     if (l3 < l4)
/*      */     {
/*  963 */       return l1; } 
/*  964 */     if (l4 < l3)
/*      */     {
/*  966 */       return l2;
/*      */     }
/*      */ 
/*      */     
/*  970 */     if ((get(l2) & 0x1) == 0) {
/*  971 */       return l2;
/*      */     }
/*  973 */     return l1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long remainder(long paramLong) {
/*  994 */     return paramLong - roundFloor(paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1003 */     return "DateTimeField[" + getName() + ']';
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/field/BaseDateTimeField.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */