/*      */ package org.joda.time.tz;
/*      */ 
/*      */ import java.io.DataInput;
/*      */ import java.io.DataInputStream;
/*      */ import java.io.DataOutput;
/*      */ import java.io.DataOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.text.DateFormatSymbols;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import org.joda.time.Chronology;
/*      */ import org.joda.time.DateTime;
/*      */ import org.joda.time.DateTimeUtils;
/*      */ import org.joda.time.DateTimeZone;
/*      */ import org.joda.time.Period;
/*      */ import org.joda.time.PeriodType;
/*      */ import org.joda.time.chrono.ISOChronology;
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
/*      */ public class DateTimeZoneBuilder
/*      */ {
/*      */   private final ArrayList<RuleSet> iRuleSets;
/*      */   
/*      */   public static DateTimeZone readFrom(InputStream paramInputStream, String paramString) throws IOException {
/*   95 */     if (paramInputStream instanceof DataInput) {
/*   96 */       return readFrom((DataInput)paramInputStream, paramString);
/*      */     }
/*   98 */     return readFrom(new DataInputStream(paramInputStream), paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DateTimeZone readFrom(DataInput paramDataInput, String paramString) throws IOException {
/*      */     DateTimeZone dateTimeZone;
/*  110 */     switch (paramDataInput.readUnsignedByte()) {
/*      */       case 70:
/*  112 */         dateTimeZone = new FixedDateTimeZone(paramString, paramDataInput.readUTF(), (int)readMillis(paramDataInput), (int)readMillis(paramDataInput));
/*      */         
/*  114 */         if (dateTimeZone.equals(DateTimeZone.UTC)) {
/*  115 */           dateTimeZone = DateTimeZone.UTC;
/*      */         }
/*  117 */         return dateTimeZone;
/*      */       case 67:
/*  119 */         return CachedDateTimeZone.forZone(PrecalculatedZone.readFrom(paramDataInput, paramString));
/*      */       case 80:
/*  121 */         return PrecalculatedZone.readFrom(paramDataInput, paramString);
/*      */     } 
/*  123 */     throw new IOException("Invalid encoding");
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
/*      */   static void writeMillis(DataOutput paramDataOutput, long paramLong) throws IOException {
/*  140 */     if (paramLong % 1800000L == 0L) {
/*      */       
/*  142 */       long l = paramLong / 1800000L;
/*  143 */       if (l << 58L >> 58L == l) {
/*      */         
/*  145 */         paramDataOutput.writeByte((int)(l & 0x3FL));
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*  150 */     if (paramLong % 60000L == 0L) {
/*      */       
/*  152 */       long l = paramLong / 60000L;
/*  153 */       if (l << 34L >> 34L == l) {
/*      */         
/*  155 */         paramDataOutput.writeInt(0x40000000 | (int)(l & 0x3FFFFFFFL));
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*  160 */     if (paramLong % 1000L == 0L) {
/*      */       
/*  162 */       long l = paramLong / 1000L;
/*  163 */       if (l << 26L >> 26L == l) {
/*      */         
/*  165 */         paramDataOutput.writeByte(0x80 | (int)(l >> 32L & 0x3FL));
/*  166 */         paramDataOutput.writeInt((int)(l & 0xFFFFFFFFFFFFFFFFL));
/*      */ 
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  175 */     paramDataOutput.writeByte((paramLong < 0L) ? 255 : 192);
/*  176 */     paramDataOutput.writeLong(paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static long readMillis(DataInput paramDataInput) throws IOException {
/*      */     long l;
/*  183 */     int i = paramDataInput.readUnsignedByte();
/*  184 */     switch (i >> 6) {
/*      */       
/*      */       default:
/*  187 */         i = i << 26 >> 26;
/*  188 */         return i * 1800000L;
/*      */ 
/*      */       
/*      */       case 1:
/*  192 */         i = i << 26 >> 2;
/*  193 */         i |= paramDataInput.readUnsignedByte() << 16;
/*  194 */         i |= paramDataInput.readUnsignedByte() << 8;
/*  195 */         i |= paramDataInput.readUnsignedByte();
/*  196 */         return i * 60000L;
/*      */ 
/*      */       
/*      */       case 2:
/*  200 */         l = i << 58L >> 26L;
/*  201 */         l |= (paramDataInput.readUnsignedByte() << 24);
/*  202 */         l |= (paramDataInput.readUnsignedByte() << 16);
/*  203 */         l |= (paramDataInput.readUnsignedByte() << 8);
/*  204 */         l |= paramDataInput.readUnsignedByte();
/*  205 */         return l * 1000L;
/*      */       case 3:
/*      */         break;
/*      */     } 
/*  209 */     return paramDataInput.readLong();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static DateTimeZone buildFixedZone(String paramString1, String paramString2, int paramInt1, int paramInt2) {
/*  215 */     if ("UTC".equals(paramString1) && paramString1.equals(paramString2) && paramInt1 == 0 && paramInt2 == 0)
/*      */     {
/*  217 */       return DateTimeZone.UTC;
/*      */     }
/*  219 */     return new FixedDateTimeZone(paramString1, paramString2, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeZoneBuilder() {
/*  226 */     this.iRuleSets = new ArrayList<RuleSet>(10);
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
/*      */   public DateTimeZoneBuilder addCutover(int paramInt1, char paramChar, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, int paramInt5) {
/*  252 */     if (this.iRuleSets.size() > 0) {
/*  253 */       OfYear ofYear = new OfYear(paramChar, paramInt2, paramInt3, paramInt4, paramBoolean, paramInt5);
/*      */       
/*  255 */       RuleSet ruleSet = this.iRuleSets.get(this.iRuleSets.size() - 1);
/*  256 */       ruleSet.setUpperLimit(paramInt1, ofYear);
/*      */     } 
/*  258 */     this.iRuleSets.add(new RuleSet());
/*  259 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeZoneBuilder setStandardOffset(int paramInt) {
/*  268 */     getLastRuleSet().setStandardOffset(paramInt);
/*  269 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeZoneBuilder setFixedSavings(String paramString, int paramInt) {
/*  276 */     getLastRuleSet().setFixedSavings(paramString, paramInt);
/*  277 */     return this;
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
/*      */   
/*      */   public DateTimeZoneBuilder addRecurringSavings(String paramString, int paramInt1, int paramInt2, int paramInt3, char paramChar, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean, int paramInt7) {
/*  309 */     if (paramInt2 <= paramInt3) {
/*  310 */       OfYear ofYear = new OfYear(paramChar, paramInt4, paramInt5, paramInt6, paramBoolean, paramInt7);
/*      */       
/*  312 */       Recurrence recurrence = new Recurrence(ofYear, paramString, paramInt1);
/*  313 */       Rule rule = new Rule(recurrence, paramInt2, paramInt3);
/*  314 */       getLastRuleSet().addRule(rule);
/*      */     } 
/*  316 */     return this;
/*      */   }
/*      */   
/*      */   private RuleSet getLastRuleSet() {
/*  320 */     if (this.iRuleSets.size() == 0) {
/*  321 */       addCutover(-2147483648, 'w', 1, 1, 0, false, 0);
/*      */     }
/*  323 */     return this.iRuleSets.get(this.iRuleSets.size() - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeZone toDateTimeZone(String paramString, boolean paramBoolean) {
/*  333 */     if (paramString == null) {
/*  334 */       throw new IllegalArgumentException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  339 */     ArrayList<Transition> arrayList = new ArrayList();
/*      */ 
/*      */ 
/*      */     
/*  343 */     DSTZone dSTZone = null;
/*      */     
/*  345 */     long l = Long.MIN_VALUE;
/*  346 */     int i = 0;
/*      */     
/*  348 */     int j = this.iRuleSets.size();
/*  349 */     for (byte b = 0; b < j; b++) {
/*  350 */       RuleSet ruleSet = this.iRuleSets.get(b);
/*  351 */       Transition transition = ruleSet.firstTransition(l);
/*  352 */       if (transition != null) {
/*      */ 
/*      */         
/*  355 */         addTransition(arrayList, transition);
/*  356 */         l = transition.getMillis();
/*  357 */         i = transition.getSaveMillis();
/*      */ 
/*      */         
/*  360 */         ruleSet = new RuleSet(ruleSet);
/*      */         
/*  362 */         while ((transition = ruleSet.nextTransition(l, i)) != null && (
/*  363 */           !addTransition(arrayList, transition) || dSTZone == null)) {
/*      */ 
/*      */ 
/*      */           
/*  367 */           l = transition.getMillis();
/*  368 */           i = transition.getSaveMillis();
/*  369 */           if (dSTZone == null && b == j - 1) {
/*  370 */             dSTZone = ruleSet.buildTailZone(paramString);
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  377 */         l = ruleSet.getUpperLimit(i);
/*      */       } 
/*      */     } 
/*      */     
/*  381 */     if (arrayList.size() == 0) {
/*  382 */       if (dSTZone != null)
/*      */       {
/*  384 */         return dSTZone;
/*      */       }
/*  386 */       return buildFixedZone(paramString, "UTC", 0, 0);
/*      */     } 
/*  388 */     if (arrayList.size() == 1 && dSTZone == null) {
/*  389 */       Transition transition = arrayList.get(0);
/*  390 */       return buildFixedZone(paramString, transition.getNameKey(), transition.getWallOffset(), transition.getStandardOffset());
/*      */     } 
/*      */ 
/*      */     
/*  394 */     PrecalculatedZone precalculatedZone = PrecalculatedZone.create(paramString, paramBoolean, arrayList, dSTZone);
/*  395 */     if (precalculatedZone.isCachable()) {
/*  396 */       return CachedDateTimeZone.forZone(precalculatedZone);
/*      */     }
/*  398 */     return precalculatedZone;
/*      */   }
/*      */   
/*      */   private boolean addTransition(ArrayList<Transition> paramArrayList, Transition paramTransition) {
/*  402 */     int i = paramArrayList.size();
/*  403 */     if (i == 0) {
/*  404 */       paramArrayList.add(paramTransition);
/*  405 */       return true;
/*      */     } 
/*      */     
/*  408 */     Transition transition = paramArrayList.get(i - 1);
/*  409 */     if (!paramTransition.isTransitionFrom(transition)) {
/*  410 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  415 */     int j = 0;
/*  416 */     if (i >= 2) {
/*  417 */       j = ((Transition)paramArrayList.get(i - 2)).getWallOffset();
/*      */     }
/*  419 */     int k = transition.getWallOffset();
/*      */     
/*  421 */     long l1 = transition.getMillis() + j;
/*  422 */     long l2 = paramTransition.getMillis() + k;
/*      */     
/*  424 */     if (l2 != l1) {
/*  425 */       paramArrayList.add(paramTransition);
/*  426 */       return true;
/*      */     } 
/*      */     
/*  429 */     paramArrayList.remove(i - 1);
/*  430 */     return addTransition(paramArrayList, paramTransition);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeTo(String paramString, OutputStream paramOutputStream) throws IOException {
/*  441 */     if (paramOutputStream instanceof DataOutput) {
/*  442 */       writeTo(paramString, (DataOutput)paramOutputStream);
/*      */     } else {
/*  444 */       DataOutputStream dataOutputStream = new DataOutputStream(paramOutputStream);
/*  445 */       writeTo(paramString, dataOutputStream);
/*  446 */       dataOutputStream.flush();
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
/*      */   public void writeTo(String paramString, DataOutput paramDataOutput) throws IOException {
/*  459 */     DateTimeZone dateTimeZone = toDateTimeZone(paramString, false);
/*      */     
/*  461 */     if (dateTimeZone instanceof FixedDateTimeZone) {
/*  462 */       paramDataOutput.writeByte(70);
/*  463 */       paramDataOutput.writeUTF(dateTimeZone.getNameKey(0L));
/*  464 */       writeMillis(paramDataOutput, dateTimeZone.getOffset(0L));
/*  465 */       writeMillis(paramDataOutput, dateTimeZone.getStandardOffset(0L));
/*      */     } else {
/*  467 */       if (dateTimeZone instanceof CachedDateTimeZone) {
/*  468 */         paramDataOutput.writeByte(67);
/*  469 */         dateTimeZone = ((CachedDateTimeZone)dateTimeZone).getUncachedZone();
/*      */       } else {
/*  471 */         paramDataOutput.writeByte(80);
/*      */       } 
/*  473 */       ((PrecalculatedZone)dateTimeZone).writeTo(paramDataOutput);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static final class OfYear
/*      */   {
/*      */     final char iMode;
/*      */     
/*      */     static OfYear readFrom(DataInput param1DataInput) throws IOException {
/*  482 */       return new OfYear((char)param1DataInput.readUnsignedByte(), param1DataInput.readUnsignedByte(), param1DataInput.readByte(), param1DataInput.readUnsignedByte(), param1DataInput.readBoolean(), (int)DateTimeZoneBuilder.readMillis(param1DataInput));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final int iMonthOfYear;
/*      */ 
/*      */     
/*      */     final int iDayOfMonth;
/*      */ 
/*      */     
/*      */     final int iDayOfWeek;
/*      */ 
/*      */     
/*      */     final boolean iAdvance;
/*      */ 
/*      */     
/*      */     final int iMillisOfDay;
/*      */ 
/*      */ 
/*      */     
/*      */     OfYear(char param1Char, int param1Int1, int param1Int2, int param1Int3, boolean param1Boolean, int param1Int4) {
/*  505 */       if (param1Char != 'u' && param1Char != 'w' && param1Char != 's') {
/*  506 */         throw new IllegalArgumentException("Unknown mode: " + param1Char);
/*      */       }
/*      */       
/*  509 */       this.iMode = param1Char;
/*  510 */       this.iMonthOfYear = param1Int1;
/*  511 */       this.iDayOfMonth = param1Int2;
/*  512 */       this.iDayOfWeek = param1Int3;
/*  513 */       this.iAdvance = param1Boolean;
/*  514 */       this.iMillisOfDay = param1Int4;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long setInstant(int param1Int1, int param1Int2, int param1Int3) {
/*      */       boolean bool;
/*  522 */       if (this.iMode == 'w') {
/*  523 */         bool = param1Int2 + param1Int3;
/*  524 */       } else if (this.iMode == 's') {
/*  525 */         bool = param1Int2;
/*      */       } else {
/*  527 */         bool = false;
/*      */       } 
/*      */       
/*  530 */       ISOChronology iSOChronology = ISOChronology.getInstanceUTC();
/*  531 */       long l = iSOChronology.year().set(0L, param1Int1);
/*  532 */       l = iSOChronology.monthOfYear().set(l, this.iMonthOfYear);
/*  533 */       l = iSOChronology.millisOfDay().set(l, this.iMillisOfDay);
/*  534 */       l = setDayOfMonth((Chronology)iSOChronology, l);
/*      */       
/*  536 */       if (this.iDayOfWeek != 0) {
/*  537 */         l = setDayOfWeek((Chronology)iSOChronology, l);
/*      */       }
/*      */ 
/*      */       
/*  541 */       return l - bool;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long next(long param1Long, int param1Int1, int param1Int2) {
/*      */       boolean bool;
/*  549 */       if (this.iMode == 'w') {
/*  550 */         bool = param1Int1 + param1Int2;
/*  551 */       } else if (this.iMode == 's') {
/*  552 */         bool = param1Int1;
/*      */       } else {
/*  554 */         bool = false;
/*      */       } 
/*      */ 
/*      */       
/*  558 */       param1Long += bool;
/*      */       
/*  560 */       ISOChronology iSOChronology = ISOChronology.getInstanceUTC();
/*  561 */       long l = iSOChronology.monthOfYear().set(param1Long, this.iMonthOfYear);
/*      */       
/*  563 */       l = iSOChronology.millisOfDay().set(l, 0);
/*  564 */       l = iSOChronology.millisOfDay().add(l, this.iMillisOfDay);
/*  565 */       l = setDayOfMonthNext((Chronology)iSOChronology, l);
/*      */       
/*  567 */       if (this.iDayOfWeek == 0) {
/*  568 */         if (l <= param1Long) {
/*  569 */           l = iSOChronology.year().add(l, 1);
/*  570 */           l = setDayOfMonthNext((Chronology)iSOChronology, l);
/*      */         } 
/*      */       } else {
/*  573 */         l = setDayOfWeek((Chronology)iSOChronology, l);
/*  574 */         if (l <= param1Long) {
/*  575 */           l = iSOChronology.year().add(l, 1);
/*  576 */           l = iSOChronology.monthOfYear().set(l, this.iMonthOfYear);
/*  577 */           l = setDayOfMonthNext((Chronology)iSOChronology, l);
/*  578 */           l = setDayOfWeek((Chronology)iSOChronology, l);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  583 */       return l - bool;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long previous(long param1Long, int param1Int1, int param1Int2) {
/*      */       boolean bool;
/*  591 */       if (this.iMode == 'w') {
/*  592 */         bool = param1Int1 + param1Int2;
/*  593 */       } else if (this.iMode == 's') {
/*  594 */         bool = param1Int1;
/*      */       } else {
/*  596 */         bool = false;
/*      */       } 
/*      */ 
/*      */       
/*  600 */       param1Long += bool;
/*      */       
/*  602 */       ISOChronology iSOChronology = ISOChronology.getInstanceUTC();
/*  603 */       long l = iSOChronology.monthOfYear().set(param1Long, this.iMonthOfYear);
/*      */       
/*  605 */       l = iSOChronology.millisOfDay().set(l, 0);
/*  606 */       l = iSOChronology.millisOfDay().add(l, this.iMillisOfDay);
/*  607 */       l = setDayOfMonthPrevious((Chronology)iSOChronology, l);
/*      */       
/*  609 */       if (this.iDayOfWeek == 0) {
/*  610 */         if (l >= param1Long) {
/*  611 */           l = iSOChronology.year().add(l, -1);
/*  612 */           l = setDayOfMonthPrevious((Chronology)iSOChronology, l);
/*      */         } 
/*      */       } else {
/*  615 */         l = setDayOfWeek((Chronology)iSOChronology, l);
/*  616 */         if (l >= param1Long) {
/*  617 */           l = iSOChronology.year().add(l, -1);
/*  618 */           l = iSOChronology.monthOfYear().set(l, this.iMonthOfYear);
/*  619 */           l = setDayOfMonthPrevious((Chronology)iSOChronology, l);
/*  620 */           l = setDayOfWeek((Chronology)iSOChronology, l);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  625 */       return l - bool;
/*      */     }
/*      */     
/*      */     public boolean equals(Object param1Object) {
/*  629 */       if (this == param1Object) {
/*  630 */         return true;
/*      */       }
/*  632 */       if (param1Object instanceof OfYear) {
/*  633 */         OfYear ofYear = (OfYear)param1Object;
/*  634 */         return (this.iMode == ofYear.iMode && this.iMonthOfYear == ofYear.iMonthOfYear && this.iDayOfMonth == ofYear.iDayOfMonth && this.iDayOfWeek == ofYear.iDayOfWeek && this.iAdvance == ofYear.iAdvance && this.iMillisOfDay == ofYear.iMillisOfDay);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  642 */       return false;
/*      */     }
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
/*      */     public void writeTo(DataOutput param1DataOutput) throws IOException {
/*  659 */       param1DataOutput.writeByte(this.iMode);
/*  660 */       param1DataOutput.writeByte(this.iMonthOfYear);
/*  661 */       param1DataOutput.writeByte(this.iDayOfMonth);
/*  662 */       param1DataOutput.writeByte(this.iDayOfWeek);
/*  663 */       param1DataOutput.writeBoolean(this.iAdvance);
/*  664 */       DateTimeZoneBuilder.writeMillis(param1DataOutput, this.iMillisOfDay);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private long setDayOfMonthNext(Chronology param1Chronology, long param1Long) {
/*      */       try {
/*  672 */         param1Long = setDayOfMonth(param1Chronology, param1Long);
/*  673 */       } catch (IllegalArgumentException illegalArgumentException) {
/*  674 */         if (this.iMonthOfYear == 2 && this.iDayOfMonth == 29) {
/*  675 */           while (!param1Chronology.year().isLeap(param1Long)) {
/*  676 */             param1Long = param1Chronology.year().add(param1Long, 1);
/*      */           }
/*  678 */           param1Long = setDayOfMonth(param1Chronology, param1Long);
/*      */         } else {
/*  680 */           throw illegalArgumentException;
/*      */         } 
/*      */       } 
/*  683 */       return param1Long;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private long setDayOfMonthPrevious(Chronology param1Chronology, long param1Long) {
/*      */       try {
/*  691 */         param1Long = setDayOfMonth(param1Chronology, param1Long);
/*  692 */       } catch (IllegalArgumentException illegalArgumentException) {
/*  693 */         if (this.iMonthOfYear == 2 && this.iDayOfMonth == 29) {
/*  694 */           while (!param1Chronology.year().isLeap(param1Long)) {
/*  695 */             param1Long = param1Chronology.year().add(param1Long, -1);
/*      */           }
/*  697 */           param1Long = setDayOfMonth(param1Chronology, param1Long);
/*      */         } else {
/*  699 */           throw illegalArgumentException;
/*      */         } 
/*      */       } 
/*  702 */       return param1Long;
/*      */     }
/*      */     
/*      */     private long setDayOfMonth(Chronology param1Chronology, long param1Long) {
/*  706 */       if (this.iDayOfMonth >= 0) {
/*  707 */         param1Long = param1Chronology.dayOfMonth().set(param1Long, this.iDayOfMonth);
/*      */       } else {
/*  709 */         param1Long = param1Chronology.dayOfMonth().set(param1Long, 1);
/*  710 */         param1Long = param1Chronology.monthOfYear().add(param1Long, 1);
/*  711 */         param1Long = param1Chronology.dayOfMonth().add(param1Long, this.iDayOfMonth);
/*      */       } 
/*  713 */       return param1Long;
/*      */     }
/*      */     
/*      */     private long setDayOfWeek(Chronology param1Chronology, long param1Long) {
/*  717 */       int i = param1Chronology.dayOfWeek().get(param1Long);
/*  718 */       int j = this.iDayOfWeek - i;
/*  719 */       if (j != 0) {
/*  720 */         if (this.iAdvance) {
/*  721 */           if (j < 0) {
/*  722 */             j += 7;
/*      */           }
/*      */         }
/*  725 */         else if (j > 0) {
/*  726 */           j -= 7;
/*      */         } 
/*      */         
/*  729 */         param1Long = param1Chronology.dayOfWeek().add(param1Long, j);
/*      */       } 
/*  731 */       return param1Long;
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class Recurrence
/*      */   {
/*      */     final DateTimeZoneBuilder.OfYear iOfYear;
/*      */     
/*      */     static Recurrence readFrom(DataInput param1DataInput) throws IOException {
/*  740 */       return new Recurrence(DateTimeZoneBuilder.OfYear.readFrom(param1DataInput), param1DataInput.readUTF(), (int)DateTimeZoneBuilder.readMillis(param1DataInput));
/*      */     }
/*      */ 
/*      */     
/*      */     final String iNameKey;
/*      */     final int iSaveMillis;
/*      */     
/*      */     Recurrence(DateTimeZoneBuilder.OfYear param1OfYear, String param1String, int param1Int) {
/*  748 */       this.iOfYear = param1OfYear;
/*  749 */       this.iNameKey = param1String;
/*  750 */       this.iSaveMillis = param1Int;
/*      */     }
/*      */     
/*      */     public DateTimeZoneBuilder.OfYear getOfYear() {
/*  754 */       return this.iOfYear;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long next(long param1Long, int param1Int1, int param1Int2) {
/*  761 */       return this.iOfYear.next(param1Long, param1Int1, param1Int2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long previous(long param1Long, int param1Int1, int param1Int2) {
/*  768 */       return this.iOfYear.previous(param1Long, param1Int1, param1Int2);
/*      */     }
/*      */     
/*      */     public String getNameKey() {
/*  772 */       return this.iNameKey;
/*      */     }
/*      */     
/*      */     public int getSaveMillis() {
/*  776 */       return this.iSaveMillis;
/*      */     }
/*      */     
/*      */     public boolean equals(Object param1Object) {
/*  780 */       if (this == param1Object) {
/*  781 */         return true;
/*      */       }
/*  783 */       if (param1Object instanceof Recurrence) {
/*  784 */         Recurrence recurrence = (Recurrence)param1Object;
/*  785 */         return (this.iSaveMillis == recurrence.iSaveMillis && this.iNameKey.equals(recurrence.iNameKey) && this.iOfYear.equals(recurrence.iOfYear));
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  790 */       return false;
/*      */     }
/*      */     
/*      */     public void writeTo(DataOutput param1DataOutput) throws IOException {
/*  794 */       this.iOfYear.writeTo(param1DataOutput);
/*  795 */       param1DataOutput.writeUTF(this.iNameKey);
/*  796 */       DateTimeZoneBuilder.writeMillis(param1DataOutput, this.iSaveMillis);
/*      */     }
/*      */     
/*      */     Recurrence rename(String param1String) {
/*  800 */       return new Recurrence(this.iOfYear, param1String, this.iSaveMillis);
/*      */     }
/*      */     
/*      */     Recurrence renameAppend(String param1String) {
/*  804 */       return rename((this.iNameKey + param1String).intern());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class Rule
/*      */   {
/*      */     final DateTimeZoneBuilder.Recurrence iRecurrence;
/*      */     
/*      */     final int iFromYear;
/*      */     final int iToYear;
/*      */     
/*      */     Rule(DateTimeZoneBuilder.Recurrence param1Recurrence, int param1Int1, int param1Int2) {
/*  817 */       this.iRecurrence = param1Recurrence;
/*  818 */       this.iFromYear = param1Int1;
/*  819 */       this.iToYear = param1Int2;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getFromYear() {
/*  824 */       return this.iFromYear;
/*      */     }
/*      */     
/*      */     public int getToYear() {
/*  828 */       return this.iToYear;
/*      */     }
/*      */ 
/*      */     
/*      */     public DateTimeZoneBuilder.OfYear getOfYear() {
/*  833 */       return this.iRecurrence.getOfYear();
/*      */     }
/*      */     
/*      */     public String getNameKey() {
/*  837 */       return this.iRecurrence.getNameKey();
/*      */     }
/*      */     
/*      */     public int getSaveMillis() {
/*  841 */       return this.iRecurrence.getSaveMillis();
/*      */     }
/*      */     public long next(long param1Long, int param1Int1, int param1Int2) {
/*      */       int j;
/*  845 */       ISOChronology iSOChronology = ISOChronology.getInstanceUTC();
/*      */       
/*  847 */       int i = param1Int1 + param1Int2;
/*  848 */       long l1 = param1Long;
/*      */ 
/*      */       
/*  851 */       if (param1Long == Long.MIN_VALUE) {
/*  852 */         j = Integer.MIN_VALUE;
/*      */       } else {
/*  854 */         j = iSOChronology.year().get(param1Long + i);
/*      */       } 
/*      */       
/*  857 */       if (j < this.iFromYear) {
/*      */         
/*  859 */         l1 = iSOChronology.year().set(0L, this.iFromYear) - i;
/*      */ 
/*      */         
/*  862 */         l1--;
/*      */       } 
/*      */       
/*  865 */       long l2 = this.iRecurrence.next(l1, param1Int1, param1Int2);
/*      */       
/*  867 */       if (l2 > param1Long) {
/*  868 */         j = iSOChronology.year().get(l2 + i);
/*  869 */         if (j > this.iToYear)
/*      */         {
/*  871 */           l2 = param1Long;
/*      */         }
/*      */       } 
/*      */       
/*  875 */       return l2;
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class Transition {
/*      */     private final long iMillis;
/*      */     private final String iNameKey;
/*      */     private final int iWallOffset;
/*      */     private final int iStandardOffset;
/*      */     
/*      */     Transition(long param1Long, Transition param1Transition) {
/*  886 */       this.iMillis = param1Long;
/*  887 */       this.iNameKey = param1Transition.iNameKey;
/*  888 */       this.iWallOffset = param1Transition.iWallOffset;
/*  889 */       this.iStandardOffset = param1Transition.iStandardOffset;
/*      */     }
/*      */     
/*      */     Transition(long param1Long, DateTimeZoneBuilder.Rule param1Rule, int param1Int) {
/*  893 */       this.iMillis = param1Long;
/*  894 */       this.iNameKey = param1Rule.getNameKey();
/*  895 */       this.iWallOffset = param1Int + param1Rule.getSaveMillis();
/*  896 */       this.iStandardOffset = param1Int;
/*      */     }
/*      */ 
/*      */     
/*      */     Transition(long param1Long, String param1String, int param1Int1, int param1Int2) {
/*  901 */       this.iMillis = param1Long;
/*  902 */       this.iNameKey = param1String;
/*  903 */       this.iWallOffset = param1Int1;
/*  904 */       this.iStandardOffset = param1Int2;
/*      */     }
/*      */     
/*      */     public long getMillis() {
/*  908 */       return this.iMillis;
/*      */     }
/*      */     
/*      */     public String getNameKey() {
/*  912 */       return this.iNameKey;
/*      */     }
/*      */     
/*      */     public int getWallOffset() {
/*  916 */       return this.iWallOffset;
/*      */     }
/*      */     
/*      */     public int getStandardOffset() {
/*  920 */       return this.iStandardOffset;
/*      */     }
/*      */     
/*      */     public int getSaveMillis() {
/*  924 */       return this.iWallOffset - this.iStandardOffset;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isTransitionFrom(Transition param1Transition) {
/*  931 */       if (param1Transition == null) {
/*  932 */         return true;
/*      */       }
/*  934 */       return (this.iMillis > param1Transition.iMillis && (this.iWallOffset != param1Transition.iWallOffset || !this.iNameKey.equals(param1Transition.iNameKey)));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class RuleSet
/*      */   {
/*      */     private static final int YEAR_LIMIT;
/*      */     private int iStandardOffset;
/*      */     private ArrayList<DateTimeZoneBuilder.Rule> iRules;
/*      */     private String iInitialNameKey;
/*      */     private int iInitialSaveMillis;
/*      */     private int iUpperYear;
/*      */     private DateTimeZoneBuilder.OfYear iUpperOfYear;
/*      */     
/*      */     static {
/*  950 */       long l = DateTimeUtils.currentTimeMillis();
/*  951 */       YEAR_LIMIT = ISOChronology.getInstanceUTC().year().get(l) + 100;
/*      */     }
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
/*      */     RuleSet() {
/*  966 */       this.iRules = new ArrayList<DateTimeZoneBuilder.Rule>(10);
/*  967 */       this.iUpperYear = Integer.MAX_VALUE;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     RuleSet(RuleSet param1RuleSet) {
/*  974 */       this.iStandardOffset = param1RuleSet.iStandardOffset;
/*  975 */       this.iRules = new ArrayList<DateTimeZoneBuilder.Rule>(param1RuleSet.iRules);
/*  976 */       this.iInitialNameKey = param1RuleSet.iInitialNameKey;
/*  977 */       this.iInitialSaveMillis = param1RuleSet.iInitialSaveMillis;
/*  978 */       this.iUpperYear = param1RuleSet.iUpperYear;
/*  979 */       this.iUpperOfYear = param1RuleSet.iUpperOfYear;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getStandardOffset() {
/*  984 */       return this.iStandardOffset;
/*      */     }
/*      */     
/*      */     public void setStandardOffset(int param1Int) {
/*  988 */       this.iStandardOffset = param1Int;
/*      */     }
/*      */     
/*      */     public void setFixedSavings(String param1String, int param1Int) {
/*  992 */       this.iInitialNameKey = param1String;
/*  993 */       this.iInitialSaveMillis = param1Int;
/*      */     }
/*      */     
/*      */     public void addRule(DateTimeZoneBuilder.Rule param1Rule) {
/*  997 */       if (!this.iRules.contains(param1Rule)) {
/*  998 */         this.iRules.add(param1Rule);
/*      */       }
/*      */     }
/*      */     
/*      */     public void setUpperLimit(int param1Int, DateTimeZoneBuilder.OfYear param1OfYear) {
/* 1003 */       this.iUpperYear = param1Int;
/* 1004 */       this.iUpperOfYear = param1OfYear;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DateTimeZoneBuilder.Transition firstTransition(long param1Long) {
/* 1014 */       if (this.iInitialNameKey != null)
/*      */       {
/* 1016 */         return new DateTimeZoneBuilder.Transition(param1Long, this.iInitialNameKey, this.iStandardOffset + this.iInitialSaveMillis, this.iStandardOffset);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1021 */       ArrayList<DateTimeZoneBuilder.Rule> arrayList = new ArrayList<DateTimeZoneBuilder.Rule>(this.iRules);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1027 */       long l = Long.MIN_VALUE;
/* 1028 */       int i = 0;
/* 1029 */       DateTimeZoneBuilder.Transition transition1 = null;
/*      */       
/*      */       DateTimeZoneBuilder.Transition transition2;
/* 1032 */       while ((transition2 = nextTransition(l, i)) != null) {
/* 1033 */         l = transition2.getMillis();
/*      */         
/* 1035 */         if (l == param1Long) {
/* 1036 */           transition1 = new DateTimeZoneBuilder.Transition(param1Long, transition2);
/*      */           
/*      */           break;
/*      */         } 
/* 1040 */         if (l > param1Long) {
/* 1041 */           if (transition1 == null)
/*      */           {
/*      */ 
/*      */             
/* 1045 */             for (DateTimeZoneBuilder.Rule rule : arrayList) {
/* 1046 */               if (rule.getSaveMillis() == 0) {
/* 1047 */                 transition1 = new DateTimeZoneBuilder.Transition(param1Long, rule, this.iStandardOffset);
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           }
/* 1052 */           if (transition1 == null)
/*      */           {
/*      */ 
/*      */             
/* 1056 */             transition1 = new DateTimeZoneBuilder.Transition(param1Long, transition2.getNameKey(), this.iStandardOffset, this.iStandardOffset);
/*      */           }
/*      */ 
/*      */           
/*      */           break;
/*      */         } 
/*      */ 
/*      */         
/* 1064 */         transition1 = new DateTimeZoneBuilder.Transition(param1Long, transition2);
/*      */         
/* 1066 */         i = transition2.getSaveMillis();
/*      */       } 
/*      */       
/* 1069 */       this.iRules = arrayList;
/* 1070 */       return transition1;
/*      */     }
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
/*      */     public DateTimeZoneBuilder.Transition nextTransition(long param1Long, int param1Int) {
/* 1085 */       ISOChronology iSOChronology = ISOChronology.getInstanceUTC();
/*      */ 
/*      */       
/* 1088 */       DateTimeZoneBuilder.Rule rule = null;
/* 1089 */       long l = Long.MAX_VALUE;
/*      */       
/* 1091 */       Iterator<DateTimeZoneBuilder.Rule> iterator = this.iRules.iterator();
/* 1092 */       while (iterator.hasNext()) {
/* 1093 */         DateTimeZoneBuilder.Rule rule1 = iterator.next();
/* 1094 */         long l1 = rule1.next(param1Long, this.iStandardOffset, param1Int);
/* 1095 */         if (l1 <= param1Long) {
/* 1096 */           iterator.remove();
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/* 1101 */         if (l1 <= l) {
/*      */           
/* 1103 */           rule = rule1;
/* 1104 */           l = l1;
/*      */         } 
/*      */       } 
/*      */       
/* 1108 */       if (rule == null) {
/* 1109 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1113 */       if (iSOChronology.year().get(l) >= YEAR_LIMIT) {
/* 1114 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1118 */       if (this.iUpperYear < Integer.MAX_VALUE) {
/* 1119 */         long l1 = this.iUpperOfYear.setInstant(this.iUpperYear, this.iStandardOffset, param1Int);
/*      */         
/* 1121 */         if (l >= l1)
/*      */         {
/* 1123 */           return null;
/*      */         }
/*      */       } 
/*      */       
/* 1127 */       return new DateTimeZoneBuilder.Transition(l, rule, this.iStandardOffset);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long getUpperLimit(int param1Int) {
/* 1134 */       if (this.iUpperYear == Integer.MAX_VALUE) {
/* 1135 */         return Long.MAX_VALUE;
/*      */       }
/* 1137 */       return this.iUpperOfYear.setInstant(this.iUpperYear, this.iStandardOffset, param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DateTimeZoneBuilder.DSTZone buildTailZone(String param1String) {
/* 1144 */       if (this.iRules.size() == 2) {
/* 1145 */         DateTimeZoneBuilder.Rule rule1 = this.iRules.get(0);
/* 1146 */         DateTimeZoneBuilder.Rule rule2 = this.iRules.get(1);
/* 1147 */         if (rule1.getToYear() == Integer.MAX_VALUE && rule2.getToYear() == Integer.MAX_VALUE)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1157 */           return new DateTimeZoneBuilder.DSTZone(param1String, this.iStandardOffset, rule1.iRecurrence, rule2.iRecurrence);
/*      */         }
/*      */       } 
/*      */       
/* 1161 */       return null;
/*      */     } }
/*      */   
/*      */   private static final class DSTZone extends DateTimeZone {
/*      */     private static final long serialVersionUID = 6941492635554961361L;
/*      */     final int iStandardOffset;
/*      */     
/*      */     static DSTZone readFrom(DataInput param1DataInput, String param1String) throws IOException {
/* 1169 */       return new DSTZone(param1String, (int)DateTimeZoneBuilder.readMillis(param1DataInput), DateTimeZoneBuilder.Recurrence.readFrom(param1DataInput), DateTimeZoneBuilder.Recurrence.readFrom(param1DataInput));
/*      */     }
/*      */ 
/*      */     
/*      */     final DateTimeZoneBuilder.Recurrence iStartRecurrence;
/*      */     
/*      */     final DateTimeZoneBuilder.Recurrence iEndRecurrence;
/*      */ 
/*      */     
/*      */     DSTZone(String param1String, int param1Int, DateTimeZoneBuilder.Recurrence param1Recurrence1, DateTimeZoneBuilder.Recurrence param1Recurrence2) {
/* 1179 */       super(param1String);
/* 1180 */       this.iStandardOffset = param1Int;
/* 1181 */       this.iStartRecurrence = param1Recurrence1;
/* 1182 */       this.iEndRecurrence = param1Recurrence2;
/*      */     }
/*      */     
/*      */     public String getNameKey(long param1Long) {
/* 1186 */       return findMatchingRecurrence(param1Long).getNameKey();
/*      */     }
/*      */     
/*      */     public int getOffset(long param1Long) {
/* 1190 */       return this.iStandardOffset + findMatchingRecurrence(param1Long).getSaveMillis();
/*      */     }
/*      */     
/*      */     public int getStandardOffset(long param1Long) {
/* 1194 */       return this.iStandardOffset;
/*      */     }
/*      */     
/*      */     public boolean isFixed() {
/* 1198 */       return false;
/*      */     }
/*      */     public long nextTransition(long param1Long) {
/*      */       long l1, l2;
/* 1202 */       int i = this.iStandardOffset;
/* 1203 */       DateTimeZoneBuilder.Recurrence recurrence1 = this.iStartRecurrence;
/* 1204 */       DateTimeZoneBuilder.Recurrence recurrence2 = this.iEndRecurrence;
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1209 */         l1 = recurrence1.next(param1Long, i, recurrence2.getSaveMillis());
/*      */         
/* 1211 */         if (param1Long > 0L && l1 < 0L)
/*      */         {
/* 1213 */           l1 = param1Long;
/*      */         }
/* 1215 */       } catch (IllegalArgumentException illegalArgumentException) {
/*      */         
/* 1217 */         l1 = param1Long;
/* 1218 */       } catch (ArithmeticException arithmeticException) {
/*      */         
/* 1220 */         l1 = param1Long;
/*      */       } 
/*      */       
/*      */       try {
/* 1224 */         l2 = recurrence2.next(param1Long, i, recurrence1.getSaveMillis());
/*      */         
/* 1226 */         if (param1Long > 0L && l2 < 0L)
/*      */         {
/* 1228 */           l2 = param1Long;
/*      */         }
/* 1230 */       } catch (IllegalArgumentException illegalArgumentException) {
/*      */         
/* 1232 */         l2 = param1Long;
/* 1233 */       } catch (ArithmeticException arithmeticException) {
/*      */         
/* 1235 */         l2 = param1Long;
/*      */       } 
/*      */       
/* 1238 */       return (l1 > l2) ? l2 : l1;
/*      */     }
/*      */ 
/*      */     
/*      */     public long previousTransition(long param1Long) {
/*      */       long l1, l2;
/* 1244 */       param1Long++;
/*      */       
/* 1246 */       int i = this.iStandardOffset;
/* 1247 */       DateTimeZoneBuilder.Recurrence recurrence1 = this.iStartRecurrence;
/* 1248 */       DateTimeZoneBuilder.Recurrence recurrence2 = this.iEndRecurrence;
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1253 */         l1 = recurrence1.previous(param1Long, i, recurrence2.getSaveMillis());
/*      */         
/* 1255 */         if (param1Long < 0L && l1 > 0L)
/*      */         {
/* 1257 */           l1 = param1Long;
/*      */         }
/* 1259 */       } catch (IllegalArgumentException illegalArgumentException) {
/*      */         
/* 1261 */         l1 = param1Long;
/* 1262 */       } catch (ArithmeticException arithmeticException) {
/*      */         
/* 1264 */         l1 = param1Long;
/*      */       } 
/*      */       
/*      */       try {
/* 1268 */         l2 = recurrence2.previous(param1Long, i, recurrence1.getSaveMillis());
/*      */         
/* 1270 */         if (param1Long < 0L && l2 > 0L)
/*      */         {
/* 1272 */           l2 = param1Long;
/*      */         }
/* 1274 */       } catch (IllegalArgumentException illegalArgumentException) {
/*      */         
/* 1276 */         l2 = param1Long;
/* 1277 */       } catch (ArithmeticException arithmeticException) {
/*      */         
/* 1279 */         l2 = param1Long;
/*      */       } 
/*      */       
/* 1282 */       return ((l1 > l2) ? l1 : l2) - 1L;
/*      */     }
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 1286 */       if (this == param1Object) {
/* 1287 */         return true;
/*      */       }
/* 1289 */       if (param1Object instanceof DSTZone) {
/* 1290 */         DSTZone dSTZone = (DSTZone)param1Object;
/* 1291 */         return (getID().equals(dSTZone.getID()) && this.iStandardOffset == dSTZone.iStandardOffset && this.iStartRecurrence.equals(dSTZone.iStartRecurrence) && this.iEndRecurrence.equals(dSTZone.iEndRecurrence));
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1297 */       return false;
/*      */     }
/*      */     
/*      */     public void writeTo(DataOutput param1DataOutput) throws IOException {
/* 1301 */       DateTimeZoneBuilder.writeMillis(param1DataOutput, this.iStandardOffset);
/* 1302 */       this.iStartRecurrence.writeTo(param1DataOutput);
/* 1303 */       this.iEndRecurrence.writeTo(param1DataOutput);
/*      */     }
/*      */     private DateTimeZoneBuilder.Recurrence findMatchingRecurrence(long param1Long) {
/*      */       long l1, l2;
/* 1307 */       int i = this.iStandardOffset;
/* 1308 */       DateTimeZoneBuilder.Recurrence recurrence1 = this.iStartRecurrence;
/* 1309 */       DateTimeZoneBuilder.Recurrence recurrence2 = this.iEndRecurrence;
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1314 */         l1 = recurrence1.next(param1Long, i, recurrence2.getSaveMillis());
/*      */       }
/* 1316 */       catch (IllegalArgumentException illegalArgumentException) {
/*      */         
/* 1318 */         l1 = param1Long;
/* 1319 */       } catch (ArithmeticException arithmeticException) {
/*      */         
/* 1321 */         l1 = param1Long;
/*      */       } 
/*      */       
/*      */       try {
/* 1325 */         l2 = recurrence2.next(param1Long, i, recurrence1.getSaveMillis());
/*      */       }
/* 1327 */       catch (IllegalArgumentException illegalArgumentException) {
/*      */         
/* 1329 */         l2 = param1Long;
/* 1330 */       } catch (ArithmeticException arithmeticException) {
/*      */         
/* 1332 */         l2 = param1Long;
/*      */       } 
/*      */       
/* 1335 */       return (l1 > l2) ? recurrence1 : recurrence2;
/*      */     } }
/*      */   
/*      */   private static final class PrecalculatedZone extends DateTimeZone {
/*      */     private static final long serialVersionUID = 7811976468055766265L;
/*      */     private final long[] iTransitions;
/*      */     private final int[] iWallOffsets;
/*      */     
/*      */     static PrecalculatedZone readFrom(DataInput param1DataInput, String param1String) throws IOException {
/* 1344 */       int i = param1DataInput.readUnsignedShort();
/* 1345 */       String[] arrayOfString1 = new String[i]; int j;
/* 1346 */       for (j = 0; j < i; j++) {
/* 1347 */         arrayOfString1[j] = param1DataInput.readUTF();
/*      */       }
/*      */       
/* 1350 */       j = param1DataInput.readInt();
/* 1351 */       long[] arrayOfLong = new long[j];
/* 1352 */       int[] arrayOfInt1 = new int[j];
/* 1353 */       int[] arrayOfInt2 = new int[j];
/* 1354 */       String[] arrayOfString2 = new String[j];
/*      */       
/* 1356 */       for (byte b = 0; b < j; b++) {
/* 1357 */         arrayOfLong[b] = DateTimeZoneBuilder.readMillis(param1DataInput);
/* 1358 */         arrayOfInt1[b] = (int)DateTimeZoneBuilder.readMillis(param1DataInput);
/* 1359 */         arrayOfInt2[b] = (int)DateTimeZoneBuilder.readMillis(param1DataInput);
/*      */         try {
/*      */           int k;
/* 1362 */           if (i < 256) {
/* 1363 */             k = param1DataInput.readUnsignedByte();
/*      */           } else {
/* 1365 */             k = param1DataInput.readUnsignedShort();
/*      */           } 
/* 1367 */           arrayOfString2[b] = arrayOfString1[k];
/* 1368 */         } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 1369 */           throw new IOException("Invalid encoding");
/*      */         } 
/*      */       } 
/*      */       
/* 1373 */       DateTimeZoneBuilder.DSTZone dSTZone = null;
/* 1374 */       if (param1DataInput.readBoolean()) {
/* 1375 */         dSTZone = DateTimeZoneBuilder.DSTZone.readFrom(param1DataInput, param1String);
/*      */       }
/*      */       
/* 1378 */       return new PrecalculatedZone(param1String, arrayOfLong, arrayOfInt1, arrayOfInt2, arrayOfString2, dSTZone);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private final int[] iStandardOffsets;
/*      */ 
/*      */     
/*      */     private final String[] iNameKeys;
/*      */     
/*      */     private final DateTimeZoneBuilder.DSTZone iTailZone;
/*      */ 
/*      */     
/*      */     static PrecalculatedZone create(String param1String, boolean param1Boolean, ArrayList<DateTimeZoneBuilder.Transition> param1ArrayList, DateTimeZoneBuilder.DSTZone param1DSTZone) {
/* 1392 */       int i = param1ArrayList.size();
/* 1393 */       if (i == 0) {
/* 1394 */         throw new IllegalArgumentException();
/*      */       }
/*      */       
/* 1397 */       long[] arrayOfLong = new long[i];
/* 1398 */       int[] arrayOfInt1 = new int[i];
/* 1399 */       int[] arrayOfInt2 = new int[i];
/* 1400 */       String[] arrayOfString1 = new String[i];
/*      */       
/* 1402 */       DateTimeZoneBuilder.Transition transition = null;
/* 1403 */       for (byte b1 = 0; b1 < i; b1++) {
/* 1404 */         DateTimeZoneBuilder.Transition transition1 = param1ArrayList.get(b1);
/*      */         
/* 1406 */         if (!transition1.isTransitionFrom(transition)) {
/* 1407 */           throw new IllegalArgumentException(param1String);
/*      */         }
/*      */         
/* 1410 */         arrayOfLong[b1] = transition1.getMillis();
/* 1411 */         arrayOfInt1[b1] = transition1.getWallOffset();
/* 1412 */         arrayOfInt2[b1] = transition1.getStandardOffset();
/* 1413 */         arrayOfString1[b1] = transition1.getNameKey();
/*      */         
/* 1415 */         transition = transition1;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1420 */       String[] arrayOfString2 = new String[5];
/* 1421 */       String[][] arrayOfString = (new DateFormatSymbols(Locale.ENGLISH)).getZoneStrings();
/* 1422 */       for (byte b2 = 0; b2 < arrayOfString.length; b2++) {
/* 1423 */         String[] arrayOfString3 = arrayOfString[b2];
/* 1424 */         if (arrayOfString3 != null && arrayOfString3.length == 5 && param1String.equals(arrayOfString3[0])) {
/* 1425 */           arrayOfString2 = arrayOfString3;
/*      */         }
/*      */       } 
/*      */       
/* 1429 */       ISOChronology iSOChronology = ISOChronology.getInstanceUTC();
/*      */       
/* 1431 */       for (byte b3 = 0; b3 < arrayOfString1.length - 1; b3++) {
/* 1432 */         String str1 = arrayOfString1[b3];
/* 1433 */         String str2 = arrayOfString1[b3 + 1];
/* 1434 */         long l1 = arrayOfInt1[b3];
/* 1435 */         long l2 = arrayOfInt1[b3 + 1];
/* 1436 */         long l3 = arrayOfInt2[b3];
/* 1437 */         long l4 = arrayOfInt2[b3 + 1];
/* 1438 */         Period period = new Period(arrayOfLong[b3], arrayOfLong[b3 + 1], PeriodType.yearMonthDay(), (Chronology)iSOChronology);
/* 1439 */         if (l1 != l2 && l3 == l4 && str1.equals(str2) && period.getYears() == 0 && period.getMonths() > 4 && period.getMonths() < 8 && str1.equals(arrayOfString2[2]) && str1.equals(arrayOfString2[4])) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1446 */           if (ZoneInfoLogger.verbose()) {
/* 1447 */             System.out.println("Fixing duplicate name key - " + str2);
/* 1448 */             System.out.println("     - " + new DateTime(arrayOfLong[b3], (Chronology)iSOChronology) + " - " + new DateTime(arrayOfLong[b3 + 1], (Chronology)iSOChronology));
/*      */           } 
/*      */           
/* 1451 */           if (l1 > l2) {
/* 1452 */             arrayOfString1[b3] = (str1 + "-Summer").intern();
/* 1453 */           } else if (l1 < l2) {
/* 1454 */             arrayOfString1[b3 + 1] = (str2 + "-Summer").intern();
/* 1455 */             b3++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1460 */       if (param1DSTZone != null && 
/* 1461 */         param1DSTZone.iStartRecurrence.getNameKey().equals(param1DSTZone.iEndRecurrence.getNameKey())) {
/*      */         
/* 1463 */         if (ZoneInfoLogger.verbose()) {
/* 1464 */           System.out.println("Fixing duplicate recurrent name key - " + param1DSTZone.iStartRecurrence.getNameKey());
/*      */         }
/*      */         
/* 1467 */         if (param1DSTZone.iStartRecurrence.getSaveMillis() > 0) {
/* 1468 */           param1DSTZone = new DateTimeZoneBuilder.DSTZone(param1DSTZone.getID(), param1DSTZone.iStandardOffset, param1DSTZone.iStartRecurrence.renameAppend("-Summer"), param1DSTZone.iEndRecurrence);
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/* 1474 */           param1DSTZone = new DateTimeZoneBuilder.DSTZone(param1DSTZone.getID(), param1DSTZone.iStandardOffset, param1DSTZone.iStartRecurrence, param1DSTZone.iEndRecurrence.renameAppend("-Summer"));
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1483 */       return new PrecalculatedZone(param1Boolean ? param1String : "", arrayOfLong, arrayOfInt1, arrayOfInt2, arrayOfString1, param1DSTZone);
/*      */     }
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
/*      */     private PrecalculatedZone(String param1String, long[] param1ArrayOflong, int[] param1ArrayOfint1, int[] param1ArrayOfint2, String[] param1ArrayOfString, DateTimeZoneBuilder.DSTZone param1DSTZone) {
/* 1503 */       super(param1String);
/* 1504 */       this.iTransitions = param1ArrayOflong;
/* 1505 */       this.iWallOffsets = param1ArrayOfint1;
/* 1506 */       this.iStandardOffsets = param1ArrayOfint2;
/* 1507 */       this.iNameKeys = param1ArrayOfString;
/* 1508 */       this.iTailZone = param1DSTZone;
/*      */     }
/*      */     
/*      */     public String getNameKey(long param1Long) {
/* 1512 */       long[] arrayOfLong = this.iTransitions;
/* 1513 */       int i = Arrays.binarySearch(arrayOfLong, param1Long);
/* 1514 */       if (i >= 0) {
/* 1515 */         return this.iNameKeys[i];
/*      */       }
/* 1517 */       i ^= 0xFFFFFFFF;
/* 1518 */       if (i < arrayOfLong.length) {
/* 1519 */         if (i > 0) {
/* 1520 */           return this.iNameKeys[i - 1];
/*      */         }
/* 1522 */         return "UTC";
/*      */       } 
/* 1524 */       if (this.iTailZone == null) {
/* 1525 */         return this.iNameKeys[i - 1];
/*      */       }
/* 1527 */       return this.iTailZone.getNameKey(param1Long);
/*      */     }
/*      */     
/*      */     public int getOffset(long param1Long) {
/* 1531 */       long[] arrayOfLong = this.iTransitions;
/* 1532 */       int i = Arrays.binarySearch(arrayOfLong, param1Long);
/* 1533 */       if (i >= 0) {
/* 1534 */         return this.iWallOffsets[i];
/*      */       }
/* 1536 */       i ^= 0xFFFFFFFF;
/* 1537 */       if (i < arrayOfLong.length) {
/* 1538 */         if (i > 0) {
/* 1539 */           return this.iWallOffsets[i - 1];
/*      */         }
/* 1541 */         return 0;
/*      */       } 
/* 1543 */       if (this.iTailZone == null) {
/* 1544 */         return this.iWallOffsets[i - 1];
/*      */       }
/* 1546 */       return this.iTailZone.getOffset(param1Long);
/*      */     }
/*      */     
/*      */     public int getStandardOffset(long param1Long) {
/* 1550 */       long[] arrayOfLong = this.iTransitions;
/* 1551 */       int i = Arrays.binarySearch(arrayOfLong, param1Long);
/* 1552 */       if (i >= 0) {
/* 1553 */         return this.iStandardOffsets[i];
/*      */       }
/* 1555 */       i ^= 0xFFFFFFFF;
/* 1556 */       if (i < arrayOfLong.length) {
/* 1557 */         if (i > 0) {
/* 1558 */           return this.iStandardOffsets[i - 1];
/*      */         }
/* 1560 */         return 0;
/*      */       } 
/* 1562 */       if (this.iTailZone == null) {
/* 1563 */         return this.iStandardOffsets[i - 1];
/*      */       }
/* 1565 */       return this.iTailZone.getStandardOffset(param1Long);
/*      */     }
/*      */     
/*      */     public boolean isFixed() {
/* 1569 */       return false;
/*      */     }
/*      */     
/*      */     public long nextTransition(long param1Long) {
/* 1573 */       long[] arrayOfLong = this.iTransitions;
/* 1574 */       int i = Arrays.binarySearch(arrayOfLong, param1Long);
/* 1575 */       i = (i >= 0) ? (i + 1) : (i ^ 0xFFFFFFFF);
/* 1576 */       if (i < arrayOfLong.length) {
/* 1577 */         return arrayOfLong[i];
/*      */       }
/* 1579 */       if (this.iTailZone == null) {
/* 1580 */         return param1Long;
/*      */       }
/* 1582 */       long l = arrayOfLong[arrayOfLong.length - 1];
/* 1583 */       if (param1Long < l) {
/* 1584 */         param1Long = l;
/*      */       }
/* 1586 */       return this.iTailZone.nextTransition(param1Long);
/*      */     }
/*      */     
/*      */     public long previousTransition(long param1Long) {
/* 1590 */       long[] arrayOfLong = this.iTransitions;
/* 1591 */       int i = Arrays.binarySearch(arrayOfLong, param1Long);
/* 1592 */       if (i >= 0) {
/* 1593 */         if (param1Long > Long.MIN_VALUE) {
/* 1594 */           return param1Long - 1L;
/*      */         }
/* 1596 */         return param1Long;
/*      */       } 
/* 1598 */       i ^= 0xFFFFFFFF;
/* 1599 */       if (i < arrayOfLong.length) {
/* 1600 */         if (i > 0) {
/* 1601 */           long l1 = arrayOfLong[i - 1];
/* 1602 */           if (l1 > Long.MIN_VALUE) {
/* 1603 */             return l1 - 1L;
/*      */           }
/*      */         } 
/* 1606 */         return param1Long;
/*      */       } 
/* 1608 */       if (this.iTailZone != null) {
/* 1609 */         long l1 = this.iTailZone.previousTransition(param1Long);
/* 1610 */         if (l1 < param1Long) {
/* 1611 */           return l1;
/*      */         }
/*      */       } 
/* 1614 */       long l = arrayOfLong[i - 1];
/* 1615 */       if (l > Long.MIN_VALUE) {
/* 1616 */         return l - 1L;
/*      */       }
/* 1618 */       return param1Long;
/*      */     }
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 1622 */       if (this == param1Object) {
/* 1623 */         return true;
/*      */       }
/* 1625 */       if (param1Object instanceof PrecalculatedZone) {
/* 1626 */         PrecalculatedZone precalculatedZone = (PrecalculatedZone)param1Object;
/* 1627 */         return (getID().equals(precalculatedZone.getID()) && Arrays.equals(this.iTransitions, precalculatedZone.iTransitions) && Arrays.equals((Object[])this.iNameKeys, (Object[])precalculatedZone.iNameKeys) && Arrays.equals(this.iWallOffsets, precalculatedZone.iWallOffsets) && Arrays.equals(this.iStandardOffsets, precalculatedZone.iStandardOffsets) && ((this.iTailZone == null) ? (null == precalculatedZone.iTailZone) : this.iTailZone.equals(precalculatedZone.iTailZone)));
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1637 */       return false;
/*      */     }
/*      */     
/*      */     public void writeTo(DataOutput param1DataOutput) throws IOException {
/* 1641 */       int i = this.iTransitions.length;
/*      */ 
/*      */       
/* 1644 */       HashSet<String> hashSet = new HashSet(); int j;
/* 1645 */       for (j = 0; j < i; j++) {
/* 1646 */         hashSet.add(this.iNameKeys[j]);
/*      */       }
/*      */       
/* 1649 */       j = hashSet.size();
/* 1650 */       if (j > 65535) {
/* 1651 */         throw new UnsupportedOperationException("String pool is too large");
/*      */       }
/* 1653 */       String[] arrayOfString = new String[j];
/* 1654 */       Iterator<String> iterator = hashSet.iterator(); byte b;
/* 1655 */       for (b = 0; iterator.hasNext(); b++) {
/* 1656 */         arrayOfString[b] = iterator.next();
/*      */       }
/*      */ 
/*      */       
/* 1660 */       param1DataOutput.writeShort(j);
/* 1661 */       for (b = 0; b < j; b++) {
/* 1662 */         param1DataOutput.writeUTF(arrayOfString[b]);
/*      */       }
/*      */       
/* 1665 */       param1DataOutput.writeInt(i);
/*      */       
/* 1667 */       for (b = 0; b < i; b++) {
/* 1668 */         DateTimeZoneBuilder.writeMillis(param1DataOutput, this.iTransitions[b]);
/* 1669 */         DateTimeZoneBuilder.writeMillis(param1DataOutput, this.iWallOffsets[b]);
/* 1670 */         DateTimeZoneBuilder.writeMillis(param1DataOutput, this.iStandardOffsets[b]);
/*      */ 
/*      */         
/* 1673 */         String str = this.iNameKeys[b];
/* 1674 */         for (byte b1 = 0; b1 < j; b1++) {
/* 1675 */           if (arrayOfString[b1].equals(str)) {
/* 1676 */             if (j < 256) {
/* 1677 */               param1DataOutput.writeByte(b1); break;
/*      */             } 
/* 1679 */             param1DataOutput.writeShort(b1);
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1686 */       param1DataOutput.writeBoolean((this.iTailZone != null));
/* 1687 */       if (this.iTailZone != null) {
/* 1688 */         this.iTailZone.writeTo(param1DataOutput);
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean isCachable() {
/* 1693 */       if (this.iTailZone != null) {
/* 1694 */         return true;
/*      */       }
/* 1696 */       long[] arrayOfLong = this.iTransitions;
/* 1697 */       if (arrayOfLong.length <= 1) {
/* 1698 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1703 */       double d = 0.0D;
/* 1704 */       byte b1 = 0;
/*      */       
/* 1706 */       for (byte b2 = 1; b2 < arrayOfLong.length; b2++) {
/* 1707 */         long l = arrayOfLong[b2] - arrayOfLong[b2 - 1];
/* 1708 */         if (l < 63158400000L) {
/* 1709 */           d += l;
/* 1710 */           b1++;
/*      */         } 
/*      */       } 
/*      */       
/* 1714 */       if (b1 > 0) {
/* 1715 */         double d1 = d / b1;
/* 1716 */         d1 /= 8.64E7D;
/* 1717 */         if (d1 >= 25.0D)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1724 */           return true;
/*      */         }
/*      */       } 
/*      */       
/* 1728 */       return false;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/tz/DateTimeZoneBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */