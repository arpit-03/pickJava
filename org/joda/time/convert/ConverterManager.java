/*     */ package org.joda.time.convert;
/*     */ 
/*     */ import java.security.Permission;
/*     */ import org.joda.time.JodaTimePermission;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ConverterManager
/*     */ {
/*     */   private static ConverterManager INSTANCE;
/*     */   private ConverterSet iInstantConverters;
/*     */   private ConverterSet iPartialConverters;
/*     */   private ConverterSet iDurationConverters;
/*     */   private ConverterSet iPeriodConverters;
/*     */   private ConverterSet iIntervalConverters;
/*     */   
/*     */   public static ConverterManager getInstance() {
/*  89 */     if (INSTANCE == null) {
/*  90 */       INSTANCE = new ConverterManager();
/*     */     }
/*  92 */     return INSTANCE;
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
/*     */   protected ConverterManager() {
/* 107 */     this.iInstantConverters = new ConverterSet(new Converter[] { ReadableInstantConverter.INSTANCE, StringConverter.INSTANCE, CalendarConverter.INSTANCE, DateConverter.INSTANCE, LongConverter.INSTANCE, NullConverter.INSTANCE });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     this.iPartialConverters = new ConverterSet(new Converter[] { ReadablePartialConverter.INSTANCE, ReadableInstantConverter.INSTANCE, StringConverter.INSTANCE, CalendarConverter.INSTANCE, DateConverter.INSTANCE, LongConverter.INSTANCE, NullConverter.INSTANCE });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     this.iDurationConverters = new ConverterSet(new Converter[] { ReadableDurationConverter.INSTANCE, ReadableIntervalConverter.INSTANCE, StringConverter.INSTANCE, LongConverter.INSTANCE, NullConverter.INSTANCE });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     this.iPeriodConverters = new ConverterSet(new Converter[] { ReadableDurationConverter.INSTANCE, ReadablePeriodConverter.INSTANCE, ReadableIntervalConverter.INSTANCE, StringConverter.INSTANCE, NullConverter.INSTANCE });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 142 */     this.iIntervalConverters = new ConverterSet(new Converter[] { ReadableIntervalConverter.INSTANCE, StringConverter.INSTANCE, NullConverter.INSTANCE });
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
/*     */   public InstantConverter getInstantConverter(Object paramObject) {
/* 160 */     InstantConverter instantConverter = (InstantConverter)this.iInstantConverters.select((paramObject == null) ? null : paramObject.getClass());
/*     */     
/* 162 */     if (instantConverter != null) {
/* 163 */       return instantConverter;
/*     */     }
/* 165 */     throw new IllegalArgumentException("No instant converter found for type: " + ((paramObject == null) ? "null" : paramObject.getClass().getName()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InstantConverter[] getInstantConverters() {
/* 176 */     ConverterSet converterSet = this.iInstantConverters;
/* 177 */     InstantConverter[] arrayOfInstantConverter = new InstantConverter[converterSet.size()];
/* 178 */     converterSet.copyInto((Converter[])arrayOfInstantConverter);
/* 179 */     return arrayOfInstantConverter;
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
/*     */   public InstantConverter addInstantConverter(InstantConverter paramInstantConverter) throws SecurityException {
/* 196 */     checkAlterInstantConverters();
/* 197 */     if (paramInstantConverter == null) {
/* 198 */       return null;
/*     */     }
/* 200 */     InstantConverter[] arrayOfInstantConverter = new InstantConverter[1];
/* 201 */     this.iInstantConverters = this.iInstantConverters.add(paramInstantConverter, (Converter[])arrayOfInstantConverter);
/* 202 */     return arrayOfInstantConverter[0];
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
/*     */   public InstantConverter removeInstantConverter(InstantConverter paramInstantConverter) throws SecurityException {
/* 215 */     checkAlterInstantConverters();
/* 216 */     if (paramInstantConverter == null) {
/* 217 */       return null;
/*     */     }
/* 219 */     InstantConverter[] arrayOfInstantConverter = new InstantConverter[1];
/* 220 */     this.iInstantConverters = this.iInstantConverters.remove(paramInstantConverter, (Converter[])arrayOfInstantConverter);
/* 221 */     return arrayOfInstantConverter[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkAlterInstantConverters() throws SecurityException {
/* 230 */     SecurityManager securityManager = System.getSecurityManager();
/* 231 */     if (securityManager != null) {
/* 232 */       securityManager.checkPermission((Permission)new JodaTimePermission("ConverterManager.alterInstantConverters"));
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
/*     */   public PartialConverter getPartialConverter(Object paramObject) {
/* 247 */     PartialConverter partialConverter = (PartialConverter)this.iPartialConverters.select((paramObject == null) ? null : paramObject.getClass());
/*     */     
/* 249 */     if (partialConverter != null) {
/* 250 */       return partialConverter;
/*     */     }
/* 252 */     throw new IllegalArgumentException("No partial converter found for type: " + ((paramObject == null) ? "null" : paramObject.getClass().getName()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PartialConverter[] getPartialConverters() {
/* 263 */     ConverterSet converterSet = this.iPartialConverters;
/* 264 */     PartialConverter[] arrayOfPartialConverter = new PartialConverter[converterSet.size()];
/* 265 */     converterSet.copyInto((Converter[])arrayOfPartialConverter);
/* 266 */     return arrayOfPartialConverter;
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
/*     */   public PartialConverter addPartialConverter(PartialConverter paramPartialConverter) throws SecurityException {
/* 283 */     checkAlterPartialConverters();
/* 284 */     if (paramPartialConverter == null) {
/* 285 */       return null;
/*     */     }
/* 287 */     PartialConverter[] arrayOfPartialConverter = new PartialConverter[1];
/* 288 */     this.iPartialConverters = this.iPartialConverters.add(paramPartialConverter, (Converter[])arrayOfPartialConverter);
/* 289 */     return arrayOfPartialConverter[0];
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
/*     */   public PartialConverter removePartialConverter(PartialConverter paramPartialConverter) throws SecurityException {
/* 302 */     checkAlterPartialConverters();
/* 303 */     if (paramPartialConverter == null) {
/* 304 */       return null;
/*     */     }
/* 306 */     PartialConverter[] arrayOfPartialConverter = new PartialConverter[1];
/* 307 */     this.iPartialConverters = this.iPartialConverters.remove(paramPartialConverter, (Converter[])arrayOfPartialConverter);
/* 308 */     return arrayOfPartialConverter[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkAlterPartialConverters() throws SecurityException {
/* 317 */     SecurityManager securityManager = System.getSecurityManager();
/* 318 */     if (securityManager != null) {
/* 319 */       securityManager.checkPermission((Permission)new JodaTimePermission("ConverterManager.alterPartialConverters"));
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
/*     */   public DurationConverter getDurationConverter(Object paramObject) {
/* 334 */     DurationConverter durationConverter = (DurationConverter)this.iDurationConverters.select((paramObject == null) ? null : paramObject.getClass());
/*     */     
/* 336 */     if (durationConverter != null) {
/* 337 */       return durationConverter;
/*     */     }
/* 339 */     throw new IllegalArgumentException("No duration converter found for type: " + ((paramObject == null) ? "null" : paramObject.getClass().getName()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DurationConverter[] getDurationConverters() {
/* 350 */     ConverterSet converterSet = this.iDurationConverters;
/* 351 */     DurationConverter[] arrayOfDurationConverter = new DurationConverter[converterSet.size()];
/* 352 */     converterSet.copyInto((Converter[])arrayOfDurationConverter);
/* 353 */     return arrayOfDurationConverter;
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
/*     */   public DurationConverter addDurationConverter(DurationConverter paramDurationConverter) throws SecurityException {
/* 370 */     checkAlterDurationConverters();
/* 371 */     if (paramDurationConverter == null) {
/* 372 */       return null;
/*     */     }
/* 374 */     DurationConverter[] arrayOfDurationConverter = new DurationConverter[1];
/* 375 */     this.iDurationConverters = this.iDurationConverters.add(paramDurationConverter, (Converter[])arrayOfDurationConverter);
/* 376 */     return arrayOfDurationConverter[0];
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
/*     */   public DurationConverter removeDurationConverter(DurationConverter paramDurationConverter) throws SecurityException {
/* 389 */     checkAlterDurationConverters();
/* 390 */     if (paramDurationConverter == null) {
/* 391 */       return null;
/*     */     }
/* 393 */     DurationConverter[] arrayOfDurationConverter = new DurationConverter[1];
/* 394 */     this.iDurationConverters = this.iDurationConverters.remove(paramDurationConverter, (Converter[])arrayOfDurationConverter);
/* 395 */     return arrayOfDurationConverter[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkAlterDurationConverters() throws SecurityException {
/* 404 */     SecurityManager securityManager = System.getSecurityManager();
/* 405 */     if (securityManager != null) {
/* 406 */       securityManager.checkPermission((Permission)new JodaTimePermission("ConverterManager.alterDurationConverters"));
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
/*     */   public PeriodConverter getPeriodConverter(Object paramObject) {
/* 421 */     PeriodConverter periodConverter = (PeriodConverter)this.iPeriodConverters.select((paramObject == null) ? null : paramObject.getClass());
/*     */     
/* 423 */     if (periodConverter != null) {
/* 424 */       return periodConverter;
/*     */     }
/* 426 */     throw new IllegalArgumentException("No period converter found for type: " + ((paramObject == null) ? "null" : paramObject.getClass().getName()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PeriodConverter[] getPeriodConverters() {
/* 437 */     ConverterSet converterSet = this.iPeriodConverters;
/* 438 */     PeriodConverter[] arrayOfPeriodConverter = new PeriodConverter[converterSet.size()];
/* 439 */     converterSet.copyInto((Converter[])arrayOfPeriodConverter);
/* 440 */     return arrayOfPeriodConverter;
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
/*     */   public PeriodConverter addPeriodConverter(PeriodConverter paramPeriodConverter) throws SecurityException {
/* 457 */     checkAlterPeriodConverters();
/* 458 */     if (paramPeriodConverter == null) {
/* 459 */       return null;
/*     */     }
/* 461 */     PeriodConverter[] arrayOfPeriodConverter = new PeriodConverter[1];
/* 462 */     this.iPeriodConverters = this.iPeriodConverters.add(paramPeriodConverter, (Converter[])arrayOfPeriodConverter);
/* 463 */     return arrayOfPeriodConverter[0];
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
/*     */   public PeriodConverter removePeriodConverter(PeriodConverter paramPeriodConverter) throws SecurityException {
/* 476 */     checkAlterPeriodConverters();
/* 477 */     if (paramPeriodConverter == null) {
/* 478 */       return null;
/*     */     }
/* 480 */     PeriodConverter[] arrayOfPeriodConverter = new PeriodConverter[1];
/* 481 */     this.iPeriodConverters = this.iPeriodConverters.remove(paramPeriodConverter, (Converter[])arrayOfPeriodConverter);
/* 482 */     return arrayOfPeriodConverter[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkAlterPeriodConverters() throws SecurityException {
/* 491 */     SecurityManager securityManager = System.getSecurityManager();
/* 492 */     if (securityManager != null) {
/* 493 */       securityManager.checkPermission((Permission)new JodaTimePermission("ConverterManager.alterPeriodConverters"));
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
/*     */   public IntervalConverter getIntervalConverter(Object paramObject) {
/* 508 */     IntervalConverter intervalConverter = (IntervalConverter)this.iIntervalConverters.select((paramObject == null) ? null : paramObject.getClass());
/*     */     
/* 510 */     if (intervalConverter != null) {
/* 511 */       return intervalConverter;
/*     */     }
/* 513 */     throw new IllegalArgumentException("No interval converter found for type: " + ((paramObject == null) ? "null" : paramObject.getClass().getName()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntervalConverter[] getIntervalConverters() {
/* 524 */     ConverterSet converterSet = this.iIntervalConverters;
/* 525 */     IntervalConverter[] arrayOfIntervalConverter = new IntervalConverter[converterSet.size()];
/* 526 */     converterSet.copyInto((Converter[])arrayOfIntervalConverter);
/* 527 */     return arrayOfIntervalConverter;
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
/*     */   public IntervalConverter addIntervalConverter(IntervalConverter paramIntervalConverter) throws SecurityException {
/* 544 */     checkAlterIntervalConverters();
/* 545 */     if (paramIntervalConverter == null) {
/* 546 */       return null;
/*     */     }
/* 548 */     IntervalConverter[] arrayOfIntervalConverter = new IntervalConverter[1];
/* 549 */     this.iIntervalConverters = this.iIntervalConverters.add(paramIntervalConverter, (Converter[])arrayOfIntervalConverter);
/* 550 */     return arrayOfIntervalConverter[0];
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
/*     */   public IntervalConverter removeIntervalConverter(IntervalConverter paramIntervalConverter) throws SecurityException {
/* 563 */     checkAlterIntervalConverters();
/* 564 */     if (paramIntervalConverter == null) {
/* 565 */       return null;
/*     */     }
/* 567 */     IntervalConverter[] arrayOfIntervalConverter = new IntervalConverter[1];
/* 568 */     this.iIntervalConverters = this.iIntervalConverters.remove(paramIntervalConverter, (Converter[])arrayOfIntervalConverter);
/* 569 */     return arrayOfIntervalConverter[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkAlterIntervalConverters() throws SecurityException {
/* 578 */     SecurityManager securityManager = System.getSecurityManager();
/* 579 */     if (securityManager != null) {
/* 580 */       securityManager.checkPermission((Permission)new JodaTimePermission("ConverterManager.alterIntervalConverters"));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 589 */     return "ConverterManager[" + this.iInstantConverters.size() + " instant," + this.iPartialConverters.size() + " partial," + this.iDurationConverters.size() + " duration," + this.iPeriodConverters.size() + " period," + this.iIntervalConverters.size() + " interval]";
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/convert/ConverterManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */