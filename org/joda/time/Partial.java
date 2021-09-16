/*      */ package org.joda.time;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Locale;
/*      */ import org.joda.time.base.AbstractPartial;
/*      */ import org.joda.time.field.AbstractPartialFieldProperty;
/*      */ import org.joda.time.field.FieldUtils;
/*      */ import org.joda.time.format.DateTimeFormat;
/*      */ import org.joda.time.format.DateTimeFormatter;
/*      */ import org.joda.time.format.ISODateTimeFormat;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Partial
/*      */   extends AbstractPartial
/*      */   implements ReadablePartial, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 12324121189002L;
/*      */   private final Chronology iChronology;
/*      */   private final DateTimeFieldType[] iTypes;
/*      */   private final int[] iValues;
/*      */   private transient DateTimeFormatter[] iFormatter;
/*      */   
/*      */   public Partial() {
/*  103 */     this((Chronology)null);
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
/*      */   public Partial(Chronology paramChronology) {
/*  124 */     this.iChronology = DateTimeUtils.getChronology(paramChronology).withUTC();
/*  125 */     this.iTypes = new DateTimeFieldType[0];
/*  126 */     this.iValues = new int[0];
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
/*      */   public Partial(DateTimeFieldType paramDateTimeFieldType, int paramInt) {
/*  139 */     this(paramDateTimeFieldType, paramInt, (Chronology)null);
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
/*      */   public Partial(DateTimeFieldType paramDateTimeFieldType, int paramInt, Chronology paramChronology) {
/*  154 */     paramChronology = DateTimeUtils.getChronology(paramChronology).withUTC();
/*  155 */     this.iChronology = paramChronology;
/*  156 */     if (paramDateTimeFieldType == null) {
/*  157 */       throw new IllegalArgumentException("The field type must not be null");
/*      */     }
/*  159 */     this.iTypes = new DateTimeFieldType[] { paramDateTimeFieldType };
/*  160 */     this.iValues = new int[] { paramInt };
/*  161 */     paramChronology.validate(this, this.iValues);
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
/*      */   public Partial(DateTimeFieldType[] paramArrayOfDateTimeFieldType, int[] paramArrayOfint) {
/*  177 */     this(paramArrayOfDateTimeFieldType, paramArrayOfint, (Chronology)null);
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
/*      */   public Partial(DateTimeFieldType[] paramArrayOfDateTimeFieldType, int[] paramArrayOfint, Chronology paramChronology) {
/*  195 */     paramChronology = DateTimeUtils.getChronology(paramChronology).withUTC();
/*  196 */     this.iChronology = paramChronology;
/*  197 */     if (paramArrayOfDateTimeFieldType == null) {
/*  198 */       throw new IllegalArgumentException("Types array must not be null");
/*      */     }
/*  200 */     if (paramArrayOfint == null) {
/*  201 */       throw new IllegalArgumentException("Values array must not be null");
/*      */     }
/*  203 */     if (paramArrayOfint.length != paramArrayOfDateTimeFieldType.length) {
/*  204 */       throw new IllegalArgumentException("Values array must be the same length as the types array");
/*      */     }
/*  206 */     if (paramArrayOfDateTimeFieldType.length == 0) {
/*  207 */       this.iTypes = paramArrayOfDateTimeFieldType;
/*  208 */       this.iValues = paramArrayOfint;
/*      */       return;
/*      */     } 
/*  211 */     for (byte b1 = 0; b1 < paramArrayOfDateTimeFieldType.length; b1++) {
/*  212 */       if (paramArrayOfDateTimeFieldType[b1] == null) {
/*  213 */         throw new IllegalArgumentException("Types array must not contain null: index " + b1);
/*      */       }
/*      */     } 
/*  216 */     DurationField durationField = null;
/*  217 */     for (byte b2 = 0; b2 < paramArrayOfDateTimeFieldType.length; b2++) {
/*  218 */       DateTimeFieldType dateTimeFieldType = paramArrayOfDateTimeFieldType[b2];
/*  219 */       DurationField durationField1 = dateTimeFieldType.getDurationType().getField(this.iChronology);
/*  220 */       if (b2 > 0) {
/*  221 */         if (!durationField1.isSupported()) {
/*  222 */           if (durationField.isSupported()) {
/*  223 */             throw new IllegalArgumentException("Types array must be in order largest-smallest: " + paramArrayOfDateTimeFieldType[b2 - 1].getName() + " < " + dateTimeFieldType.getName());
/*      */           }
/*      */           
/*  226 */           throw new IllegalArgumentException("Types array must not contain duplicate unsupported: " + paramArrayOfDateTimeFieldType[b2 - 1].getName() + " and " + dateTimeFieldType.getName());
/*      */         } 
/*      */ 
/*      */         
/*  230 */         int i = durationField.compareTo(durationField1);
/*  231 */         if (i < 0) {
/*  232 */           throw new IllegalArgumentException("Types array must be in order largest-smallest: " + paramArrayOfDateTimeFieldType[b2 - 1].getName() + " < " + dateTimeFieldType.getName());
/*      */         }
/*  234 */         if (i == 0) {
/*  235 */           if (durationField.equals(durationField1)) {
/*  236 */             DurationFieldType durationFieldType1 = paramArrayOfDateTimeFieldType[b2 - 1].getRangeDurationType();
/*  237 */             DurationFieldType durationFieldType2 = dateTimeFieldType.getRangeDurationType();
/*  238 */             if (durationFieldType1 == null) {
/*  239 */               if (durationFieldType2 == null) {
/*  240 */                 throw new IllegalArgumentException("Types array must not contain duplicate: " + paramArrayOfDateTimeFieldType[b2 - 1].getName() + " and " + dateTimeFieldType.getName());
/*      */               }
/*      */             } else {
/*      */               
/*  244 */               if (durationFieldType2 == null) {
/*  245 */                 throw new IllegalArgumentException("Types array must be in order largest-smallest: " + paramArrayOfDateTimeFieldType[b2 - 1].getName() + " < " + dateTimeFieldType.getName());
/*      */               }
/*      */               
/*  248 */               DurationField durationField2 = durationFieldType1.getField(this.iChronology);
/*  249 */               DurationField durationField3 = durationFieldType2.getField(this.iChronology);
/*  250 */               if (durationField2.compareTo(durationField3) < 0) {
/*  251 */                 throw new IllegalArgumentException("Types array must be in order largest-smallest: " + paramArrayOfDateTimeFieldType[b2 - 1].getName() + " < " + dateTimeFieldType.getName());
/*      */               }
/*      */               
/*  254 */               if (durationField2.compareTo(durationField3) == 0) {
/*  255 */                 throw new IllegalArgumentException("Types array must not contain duplicate: " + paramArrayOfDateTimeFieldType[b2 - 1].getName() + " and " + dateTimeFieldType.getName());
/*      */               }
/*      */             }
/*      */           
/*      */           }
/*  260 */           else if (durationField.isSupported() && durationField.getType() != DurationFieldType.YEARS_TYPE) {
/*  261 */             throw new IllegalArgumentException("Types array must be in order largest-smallest, for year-based fields, years is defined as being largest: " + paramArrayOfDateTimeFieldType[b2 - 1].getName() + " < " + dateTimeFieldType.getName());
/*      */           } 
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  268 */       durationField = durationField1;
/*      */     } 
/*      */     
/*  271 */     this.iTypes = (DateTimeFieldType[])paramArrayOfDateTimeFieldType.clone();
/*  272 */     paramChronology.validate(this, paramArrayOfint);
/*  273 */     this.iValues = (int[])paramArrayOfint.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Partial(ReadablePartial paramReadablePartial) {
/*  284 */     if (paramReadablePartial == null) {
/*  285 */       throw new IllegalArgumentException("The partial must not be null");
/*      */     }
/*  287 */     this.iChronology = DateTimeUtils.getChronology(paramReadablePartial.getChronology()).withUTC();
/*  288 */     this.iTypes = new DateTimeFieldType[paramReadablePartial.size()];
/*  289 */     this.iValues = new int[paramReadablePartial.size()];
/*  290 */     for (byte b = 0; b < paramReadablePartial.size(); b++) {
/*  291 */       this.iTypes[b] = paramReadablePartial.getFieldType(b);
/*  292 */       this.iValues[b] = paramReadablePartial.getValue(b);
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
/*      */   Partial(Partial paramPartial, int[] paramArrayOfint) {
/*  306 */     this.iChronology = paramPartial.iChronology;
/*  307 */     this.iTypes = paramPartial.iTypes;
/*  308 */     this.iValues = paramArrayOfint;
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
/*      */   Partial(Chronology paramChronology, DateTimeFieldType[] paramArrayOfDateTimeFieldType, int[] paramArrayOfint) {
/*  322 */     this.iChronology = paramChronology;
/*  323 */     this.iTypes = paramArrayOfDateTimeFieldType;
/*  324 */     this.iValues = paramArrayOfint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int size() {
/*  334 */     return this.iTypes.length;
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
/*      */   public Chronology getChronology() {
/*  346 */     return this.iChronology;
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
/*      */   protected DateTimeField getField(int paramInt, Chronology paramChronology) {
/*  358 */     return this.iTypes[paramInt].getField(paramChronology);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFieldType getFieldType(int paramInt) {
/*  369 */     return this.iTypes[paramInt];
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
/*      */   public DateTimeFieldType[] getFieldTypes() {
/*  381 */     return (DateTimeFieldType[])this.iTypes.clone();
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
/*      */   public int getValue(int paramInt) {
/*  393 */     return this.iValues[paramInt];
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
/*      */   public int[] getValues() {
/*  406 */     return (int[])this.iValues.clone();
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
/*      */   public Partial withChronologyRetainFields(Chronology paramChronology) {
/*  425 */     paramChronology = DateTimeUtils.getChronology(paramChronology);
/*  426 */     paramChronology = paramChronology.withUTC();
/*  427 */     if (paramChronology == getChronology()) {
/*  428 */       return this;
/*      */     }
/*  430 */     Partial partial = new Partial(paramChronology, this.iTypes, this.iValues);
/*  431 */     paramChronology.validate(partial, this.iValues);
/*  432 */     return partial;
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
/*      */   public Partial with(DateTimeFieldType paramDateTimeFieldType, int paramInt) {
/*  452 */     if (paramDateTimeFieldType == null) {
/*  453 */       throw new IllegalArgumentException("The field type must not be null");
/*      */     }
/*  455 */     int i = indexOf(paramDateTimeFieldType);
/*  456 */     if (i == -1) {
/*  457 */       DateTimeFieldType[] arrayOfDateTimeFieldType = new DateTimeFieldType[this.iTypes.length + 1];
/*  458 */       int[] arrayOfInt1 = new int[arrayOfDateTimeFieldType.length];
/*      */ 
/*      */       
/*  461 */       byte b = 0;
/*  462 */       DurationField durationField = paramDateTimeFieldType.getDurationType().getField(this.iChronology);
/*  463 */       if (durationField.isSupported())
/*  464 */         for (; b < this.iTypes.length; b++) {
/*  465 */           DateTimeFieldType dateTimeFieldType = this.iTypes[b];
/*  466 */           DurationField durationField1 = dateTimeFieldType.getDurationType().getField(this.iChronology);
/*  467 */           if (durationField1.isSupported()) {
/*  468 */             int j = durationField.compareTo(durationField1);
/*  469 */             if (j > 0)
/*      */               break; 
/*  471 */             if (j == 0) {
/*  472 */               if (paramDateTimeFieldType.getRangeDurationType() == null) {
/*      */                 break;
/*      */               }
/*  475 */               if (dateTimeFieldType.getRangeDurationType() != null) {
/*      */ 
/*      */                 
/*  478 */                 DurationField durationField2 = paramDateTimeFieldType.getRangeDurationType().getField(this.iChronology);
/*  479 */                 DurationField durationField3 = dateTimeFieldType.getRangeDurationType().getField(this.iChronology);
/*  480 */                 if (durationField2.compareTo(durationField3) > 0) {
/*      */                   break;
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         }  
/*  487 */       System.arraycopy(this.iTypes, 0, arrayOfDateTimeFieldType, 0, b);
/*  488 */       System.arraycopy(this.iValues, 0, arrayOfInt1, 0, b);
/*  489 */       arrayOfDateTimeFieldType[b] = paramDateTimeFieldType;
/*  490 */       arrayOfInt1[b] = paramInt;
/*  491 */       System.arraycopy(this.iTypes, b, arrayOfDateTimeFieldType, b + 1, arrayOfDateTimeFieldType.length - b - 1);
/*  492 */       System.arraycopy(this.iValues, b, arrayOfInt1, b + 1, arrayOfInt1.length - b - 1);
/*      */ 
/*      */       
/*  495 */       Partial partial = new Partial(arrayOfDateTimeFieldType, arrayOfInt1, this.iChronology);
/*  496 */       this.iChronology.validate(partial, arrayOfInt1);
/*  497 */       return partial;
/*      */     } 
/*  499 */     if (paramInt == getValue(i)) {
/*  500 */       return this;
/*      */     }
/*  502 */     int[] arrayOfInt = getValues();
/*  503 */     arrayOfInt = getField(i).set(this, i, arrayOfInt, paramInt);
/*  504 */     return new Partial(this, arrayOfInt);
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
/*      */   public Partial without(DateTimeFieldType paramDateTimeFieldType) {
/*  516 */     int i = indexOf(paramDateTimeFieldType);
/*  517 */     if (i != -1) {
/*  518 */       DateTimeFieldType[] arrayOfDateTimeFieldType = new DateTimeFieldType[size() - 1];
/*  519 */       int[] arrayOfInt = new int[size() - 1];
/*  520 */       System.arraycopy(this.iTypes, 0, arrayOfDateTimeFieldType, 0, i);
/*  521 */       System.arraycopy(this.iTypes, i + 1, arrayOfDateTimeFieldType, i, arrayOfDateTimeFieldType.length - i);
/*  522 */       System.arraycopy(this.iValues, 0, arrayOfInt, 0, i);
/*  523 */       System.arraycopy(this.iValues, i + 1, arrayOfInt, i, arrayOfInt.length - i);
/*  524 */       Partial partial = new Partial(this.iChronology, arrayOfDateTimeFieldType, arrayOfInt);
/*  525 */       this.iChronology.validate(partial, arrayOfInt);
/*  526 */       return partial;
/*      */     } 
/*  528 */     return this;
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
/*      */   public Partial withField(DateTimeFieldType paramDateTimeFieldType, int paramInt) {
/*  547 */     int i = indexOfSupported(paramDateTimeFieldType);
/*  548 */     if (paramInt == getValue(i)) {
/*  549 */       return this;
/*      */     }
/*  551 */     int[] arrayOfInt = getValues();
/*  552 */     arrayOfInt = getField(i).set(this, i, arrayOfInt, paramInt);
/*  553 */     return new Partial(this, arrayOfInt);
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
/*      */   public Partial withFieldAdded(DurationFieldType paramDurationFieldType, int paramInt) {
/*  571 */     int i = indexOfSupported(paramDurationFieldType);
/*  572 */     if (paramInt == 0) {
/*  573 */       return this;
/*      */     }
/*  575 */     int[] arrayOfInt = getValues();
/*  576 */     arrayOfInt = getField(i).add(this, i, arrayOfInt, paramInt);
/*  577 */     return new Partial(this, arrayOfInt);
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
/*      */   public Partial withFieldAddWrapped(DurationFieldType paramDurationFieldType, int paramInt) {
/*  595 */     int i = indexOfSupported(paramDurationFieldType);
/*  596 */     if (paramInt == 0) {
/*  597 */       return this;
/*      */     }
/*  599 */     int[] arrayOfInt = getValues();
/*  600 */     arrayOfInt = getField(i).addWrapPartial(this, i, arrayOfInt, paramInt);
/*  601 */     return new Partial(this, arrayOfInt);
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
/*      */   public Partial withPeriodAdded(ReadablePeriod paramReadablePeriod, int paramInt) {
/*  620 */     if (paramReadablePeriod == null || paramInt == 0) {
/*  621 */       return this;
/*      */     }
/*  623 */     int[] arrayOfInt = getValues();
/*  624 */     for (byte b = 0; b < paramReadablePeriod.size(); b++) {
/*  625 */       DurationFieldType durationFieldType = paramReadablePeriod.getFieldType(b);
/*  626 */       int i = indexOf(durationFieldType);
/*  627 */       if (i >= 0) {
/*  628 */         arrayOfInt = getField(i).add(this, i, arrayOfInt, FieldUtils.safeMultiply(paramReadablePeriod.getValue(b), paramInt));
/*      */       }
/*      */     } 
/*      */     
/*  632 */     return new Partial(this, arrayOfInt);
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
/*      */   public Partial plus(ReadablePeriod paramReadablePeriod) {
/*  645 */     return withPeriodAdded(paramReadablePeriod, 1);
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
/*      */   public Partial minus(ReadablePeriod paramReadablePeriod) {
/*  658 */     return withPeriodAdded(paramReadablePeriod, -1);
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
/*      */   public Property property(DateTimeFieldType paramDateTimeFieldType) {
/*  673 */     return new Property(this, indexOfSupported(paramDateTimeFieldType));
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
/*      */   public boolean isMatch(ReadableInstant paramReadableInstant) {
/*  687 */     long l = DateTimeUtils.getInstantMillis(paramReadableInstant);
/*  688 */     Chronology chronology = DateTimeUtils.getInstantChronology(paramReadableInstant);
/*  689 */     for (byte b = 0; b < this.iTypes.length; b++) {
/*  690 */       int i = this.iTypes[b].getField(chronology).get(l);
/*  691 */       if (i != this.iValues[b]) {
/*  692 */         return false;
/*      */       }
/*      */     } 
/*  695 */     return true;
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
/*      */   public boolean isMatch(ReadablePartial paramReadablePartial) {
/*  711 */     if (paramReadablePartial == null) {
/*  712 */       throw new IllegalArgumentException("The partial must not be null");
/*      */     }
/*  714 */     for (byte b = 0; b < this.iTypes.length; b++) {
/*  715 */       int i = paramReadablePartial.get(this.iTypes[b]);
/*  716 */       if (i != this.iValues[b]) {
/*  717 */         return false;
/*      */       }
/*      */     } 
/*  720 */     return true;
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
/*      */   public DateTimeFormatter getFormatter() {
/*  736 */     DateTimeFormatter[] arrayOfDateTimeFormatter = this.iFormatter;
/*  737 */     if (arrayOfDateTimeFormatter == null) {
/*  738 */       if (size() == 0) {
/*  739 */         return null;
/*      */       }
/*  741 */       arrayOfDateTimeFormatter = new DateTimeFormatter[2];
/*      */       try {
/*  743 */         ArrayList arrayList = new ArrayList(Arrays.asList((Object[])this.iTypes));
/*  744 */         arrayOfDateTimeFormatter[0] = ISODateTimeFormat.forFields(arrayList, true, false);
/*  745 */         if (arrayList.size() == 0) {
/*  746 */           arrayOfDateTimeFormatter[1] = arrayOfDateTimeFormatter[0];
/*      */         }
/*  748 */       } catch (IllegalArgumentException illegalArgumentException) {}
/*      */ 
/*      */       
/*  751 */       this.iFormatter = arrayOfDateTimeFormatter;
/*      */     } 
/*  753 */     return arrayOfDateTimeFormatter[0];
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
/*      */   public String toString() {
/*  769 */     DateTimeFormatter[] arrayOfDateTimeFormatter = this.iFormatter;
/*  770 */     if (arrayOfDateTimeFormatter == null) {
/*  771 */       getFormatter();
/*  772 */       arrayOfDateTimeFormatter = this.iFormatter;
/*  773 */       if (arrayOfDateTimeFormatter == null) {
/*  774 */         return toStringList();
/*      */       }
/*      */     } 
/*  777 */     DateTimeFormatter dateTimeFormatter = arrayOfDateTimeFormatter[1];
/*  778 */     if (dateTimeFormatter == null) {
/*  779 */       return toStringList();
/*      */     }
/*  781 */     return dateTimeFormatter.print(this);
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
/*      */   public String toStringList() {
/*  794 */     int i = size();
/*  795 */     StringBuilder stringBuilder = new StringBuilder(20 * i);
/*  796 */     stringBuilder.append('[');
/*  797 */     for (byte b = 0; b < i; b++) {
/*  798 */       if (b > 0) {
/*  799 */         stringBuilder.append(',').append(' ');
/*      */       }
/*  801 */       stringBuilder.append(this.iTypes[b].getName());
/*  802 */       stringBuilder.append('=');
/*  803 */       stringBuilder.append(this.iValues[b]);
/*      */     } 
/*  805 */     stringBuilder.append(']');
/*  806 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString(String paramString) {
/*  817 */     if (paramString == null) {
/*  818 */       return toString();
/*      */     }
/*  820 */     return DateTimeFormat.forPattern(paramString).print(this);
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
/*      */   public String toString(String paramString, Locale paramLocale) {
/*  832 */     if (paramString == null) {
/*  833 */       return toString();
/*      */     }
/*  835 */     return DateTimeFormat.forPattern(paramString).withLocale(paramLocale).print(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Property
/*      */     extends AbstractPartialFieldProperty
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 53278362873888L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final Partial iPartial;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final int iFieldIndex;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Property(Partial param1Partial, int param1Int) {
/*  865 */       this.iPartial = param1Partial;
/*  866 */       this.iFieldIndex = param1Int;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DateTimeField getField() {
/*  875 */       return this.iPartial.getField(this.iFieldIndex);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected ReadablePartial getReadablePartial() {
/*  884 */       return this.iPartial;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Partial getPartial() {
/*  893 */       return this.iPartial;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int get() {
/*  902 */       return this.iPartial.getValue(this.iFieldIndex);
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
/*      */ 
/*      */     
/*      */     public Partial addToCopy(int param1Int) {
/*  924 */       int[] arrayOfInt = this.iPartial.getValues();
/*  925 */       arrayOfInt = getField().add(this.iPartial, this.iFieldIndex, arrayOfInt, param1Int);
/*  926 */       return new Partial(this.iPartial, arrayOfInt);
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
/*      */ 
/*      */     
/*      */     public Partial addWrapFieldToCopy(int param1Int) {
/*  948 */       int[] arrayOfInt = this.iPartial.getValues();
/*  949 */       arrayOfInt = getField().addWrapField(this.iPartial, this.iFieldIndex, arrayOfInt, param1Int);
/*  950 */       return new Partial(this.iPartial, arrayOfInt);
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
/*      */     public Partial setCopy(int param1Int) {
/*  965 */       int[] arrayOfInt = this.iPartial.getValues();
/*  966 */       arrayOfInt = getField().set(this.iPartial, this.iFieldIndex, arrayOfInt, param1Int);
/*  967 */       return new Partial(this.iPartial, arrayOfInt);
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
/*      */     public Partial setCopy(String param1String, Locale param1Locale) {
/*  982 */       int[] arrayOfInt = this.iPartial.getValues();
/*  983 */       arrayOfInt = getField().set(this.iPartial, this.iFieldIndex, arrayOfInt, param1String, param1Locale);
/*  984 */       return new Partial(this.iPartial, arrayOfInt);
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
/*      */     public Partial setCopy(String param1String) {
/*  998 */       return setCopy(param1String, (Locale)null);
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
/*      */     public Partial withMaximumValue() {
/* 1012 */       return setCopy(getMaximumValue());
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
/*      */     public Partial withMinimumValue() {
/* 1025 */       return setCopy(getMinimumValue());
/*      */     }
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/Partial.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */