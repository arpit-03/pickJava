/*     */ package org.joda.time.tz;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.TreeMap;
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTime;
/*     */ import org.joda.time.DateTimeField;
/*     */ import org.joda.time.DateTimeZone;
/*     */ import org.joda.time.LocalDate;
/*     */ import org.joda.time.MutableDateTime;
/*     */ import org.joda.time.ReadWritableInstant;
/*     */ import org.joda.time.chrono.ISOChronology;
/*     */ import org.joda.time.chrono.LenientChronology;
/*     */ import org.joda.time.format.DateTimeFormatter;
/*     */ import org.joda.time.format.ISODateTimeFormat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ZoneInfoCompiler
/*     */ {
/*     */   static DateTimeOfYear cStartOfYear;
/*     */   static Chronology cLenientISO;
/*     */   private Map<String, RuleSet> iRuleSets;
/*     */   private List<Zone> iZones;
/*     */   private List<String> iGoodLinks;
/*     */   private List<String> iBackLinks;
/*     */   
/*     */   public static void main(String[] paramArrayOfString) throws Exception {
/*  81 */     if (paramArrayOfString.length == 0) {
/*  82 */       printUsage();
/*     */       
/*     */       return;
/*     */     } 
/*  86 */     File file1 = null;
/*  87 */     File file2 = null;
/*  88 */     boolean bool = false;
/*     */     
/*     */     byte b1;
/*  91 */     for (b1 = 0; b1 < paramArrayOfString.length; b1++) {
/*     */       try {
/*  93 */         if ("-src".equals(paramArrayOfString[b1]))
/*  94 */         { file1 = new File(paramArrayOfString[++b1]); }
/*  95 */         else if ("-dst".equals(paramArrayOfString[b1]))
/*  96 */         { file2 = new File(paramArrayOfString[++b1]); }
/*  97 */         else if ("-verbose".equals(paramArrayOfString[b1]))
/*  98 */         { bool = true; }
/*  99 */         else { if ("-?".equals(paramArrayOfString[b1])) {
/* 100 */             printUsage();
/*     */             return;
/*     */           } 
/*     */           break; }
/*     */       
/* 105 */       } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/* 106 */         printUsage();
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 111 */     if (b1 >= paramArrayOfString.length) {
/* 112 */       printUsage();
/*     */       
/*     */       return;
/*     */     } 
/* 116 */     File[] arrayOfFile = new File[paramArrayOfString.length - b1];
/* 117 */     for (byte b2 = 0; b1 < paramArrayOfString.length; b1++, b2++) {
/* 118 */       arrayOfFile[b2] = (file1 == null) ? new File(paramArrayOfString[b1]) : new File(file1, paramArrayOfString[b1]);
/*     */     }
/*     */     
/* 121 */     ZoneInfoLogger.set(bool);
/* 122 */     ZoneInfoCompiler zoneInfoCompiler = new ZoneInfoCompiler();
/* 123 */     zoneInfoCompiler.compile(file2, arrayOfFile);
/*     */   }
/*     */   
/*     */   private static void printUsage() {
/* 127 */     System.out.println("Usage: java org.joda.time.tz.ZoneInfoCompiler <options> <source files>");
/* 128 */     System.out.println("where possible options include:");
/* 129 */     System.out.println("  -src <directory>    Specify where to read source files");
/* 130 */     System.out.println("  -dst <directory>    Specify where to write generated files");
/* 131 */     System.out.println("  -verbose            Output verbosely (default false)");
/*     */   }
/*     */   
/*     */   static DateTimeOfYear getStartOfYear() {
/* 135 */     if (cStartOfYear == null) {
/* 136 */       cStartOfYear = new DateTimeOfYear();
/*     */     }
/* 138 */     return cStartOfYear;
/*     */   }
/*     */   
/*     */   static Chronology getLenientISOChronology() {
/* 142 */     if (cLenientISO == null) {
/* 143 */       cLenientISO = (Chronology)LenientChronology.getInstance((Chronology)ISOChronology.getInstanceUTC());
/*     */     }
/* 145 */     return cLenientISO;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void writeZoneInfoMap(DataOutputStream paramDataOutputStream, Map<String, DateTimeZone> paramMap) throws IOException {
/* 153 */     HashMap<Object, Object> hashMap = new HashMap<Object, Object>(paramMap.size());
/* 154 */     TreeMap<Object, Object> treeMap = new TreeMap<Object, Object>();
/*     */     
/* 156 */     short s = 0;
/* 157 */     for (Map.Entry<String, DateTimeZone> entry : paramMap.entrySet()) {
/* 158 */       String str = (String)entry.getKey();
/* 159 */       if (!hashMap.containsKey(str)) {
/* 160 */         Short short_ = Short.valueOf(s);
/* 161 */         hashMap.put(str, short_);
/* 162 */         treeMap.put(short_, str);
/* 163 */         s = (short)(s + 1); if (s == 0) {
/* 164 */           throw new InternalError("Too many time zone ids");
/*     */         }
/*     */       } 
/* 167 */       str = ((DateTimeZone)entry.getValue()).getID();
/* 168 */       if (!hashMap.containsKey(str)) {
/* 169 */         Short short_ = Short.valueOf(s);
/* 170 */         hashMap.put(str, short_);
/* 171 */         treeMap.put(short_, str);
/* 172 */         s = (short)(s + 1); if (s == 0) {
/* 173 */           throw new InternalError("Too many time zone ids");
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 179 */     paramDataOutputStream.writeShort(treeMap.size());
/* 180 */     for (String str : treeMap.values()) {
/* 181 */       paramDataOutputStream.writeUTF(str);
/*     */     }
/*     */ 
/*     */     
/* 185 */     paramDataOutputStream.writeShort(paramMap.size());
/* 186 */     for (Map.Entry<String, DateTimeZone> entry : paramMap.entrySet()) {
/* 187 */       String str = (String)entry.getKey();
/* 188 */       paramDataOutputStream.writeShort(((Short)hashMap.get(str)).shortValue());
/* 189 */       str = ((DateTimeZone)entry.getValue()).getID();
/* 190 */       paramDataOutputStream.writeShort(((Short)hashMap.get(str)).shortValue());
/*     */     } 
/*     */   }
/*     */   
/*     */   static int parseYear(String paramString, int paramInt) {
/* 195 */     paramString = paramString.toLowerCase();
/* 196 */     if (paramString.equals("minimum") || paramString.equals("min"))
/* 197 */       return Integer.MIN_VALUE; 
/* 198 */     if (paramString.equals("maximum") || paramString.equals("max"))
/* 199 */       return Integer.MAX_VALUE; 
/* 200 */     if (paramString.equals("only")) {
/* 201 */       return paramInt;
/*     */     }
/* 203 */     return Integer.parseInt(paramString);
/*     */   }
/*     */   
/*     */   static int parseMonth(String paramString) {
/* 207 */     DateTimeField dateTimeField = ISOChronology.getInstanceUTC().monthOfYear();
/* 208 */     return dateTimeField.get(dateTimeField.set(0L, paramString, Locale.ENGLISH));
/*     */   }
/*     */   
/*     */   static int parseDayOfWeek(String paramString) {
/* 212 */     DateTimeField dateTimeField = ISOChronology.getInstanceUTC().dayOfWeek();
/* 213 */     return dateTimeField.get(dateTimeField.set(0L, paramString, Locale.ENGLISH));
/*     */   }
/*     */   
/*     */   static String parseOptional(String paramString) {
/* 217 */     return paramString.equals("-") ? null : paramString;
/*     */   }
/*     */   
/*     */   static int parseTime(String paramString) {
/* 221 */     DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.hourMinuteSecondFraction();
/* 222 */     MutableDateTime mutableDateTime = new MutableDateTime(0L, getLenientISOChronology());
/* 223 */     boolean bool = false;
/* 224 */     if (paramString.startsWith("-")) {
/* 225 */       bool = true;
/*     */     }
/* 227 */     int i = dateTimeFormatter.parseInto((ReadWritableInstant)mutableDateTime, paramString, bool);
/* 228 */     if (i == (bool ^ 0xFFFFFFFF)) {
/* 229 */       throw new IllegalArgumentException(paramString);
/*     */     }
/* 231 */     int j = (int)mutableDateTime.getMillis();
/* 232 */     if (bool == true) {
/* 233 */       j = -j;
/*     */     }
/* 235 */     return j;
/*     */   }
/*     */   
/*     */   static char parseZoneChar(char paramChar) {
/* 239 */     switch (paramChar) {
/*     */       case 'S':
/*     */       case 's':
/* 242 */         return 's';
/*     */       case 'G': case 'U': case 'Z': case 'g': case 'u':
/*     */       case 'z':
/* 245 */         return 'u';
/*     */     } 
/*     */     
/* 248 */     return 'w';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean test(String paramString, DateTimeZone paramDateTimeZone) {
/* 256 */     if (!paramString.equals(paramDateTimeZone.getID())) {
/* 257 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 262 */     long l1 = ISOChronology.getInstanceUTC().year().set(0L, 1850);
/* 263 */     long l2 = ISOChronology.getInstanceUTC().year().set(0L, 2050);
/*     */     
/* 265 */     int i = paramDateTimeZone.getOffset(l1);
/* 266 */     String str = paramDateTimeZone.getNameKey(l1);
/*     */     
/* 268 */     ArrayList<Long> arrayList = new ArrayList();
/*     */     
/*     */     while (true) {
/* 271 */       long l = paramDateTimeZone.nextTransition(l1);
/* 272 */       if (l == l1 || l > l2) {
/*     */         break;
/*     */       }
/*     */       
/* 276 */       l1 = l;
/*     */       
/* 278 */       int k = paramDateTimeZone.getOffset(l1);
/* 279 */       String str1 = paramDateTimeZone.getNameKey(l1);
/*     */       
/* 281 */       if (i == k && str.equals(str1)) {
/*     */         
/* 283 */         System.out.println("*d* Error in " + paramDateTimeZone.getID() + " " + new DateTime(l1, (Chronology)ISOChronology.getInstanceUTC()));
/*     */ 
/*     */         
/* 286 */         return false;
/*     */       } 
/*     */       
/* 289 */       if (str1 == null || (str1.length() < 3 && !"??".equals(str1))) {
/* 290 */         System.out.println("*s* Error in " + paramDateTimeZone.getID() + " " + new DateTime(l1, (Chronology)ISOChronology.getInstanceUTC()) + ", nameKey=" + str1);
/*     */ 
/*     */ 
/*     */         
/* 294 */         return false;
/*     */       } 
/*     */       
/* 297 */       arrayList.add(Long.valueOf(l1));
/*     */       
/* 299 */       i = k;
/* 300 */       str = str1;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 305 */     l1 = ISOChronology.getInstanceUTC().year().set(0L, 2050);
/* 306 */     l2 = ISOChronology.getInstanceUTC().year().set(0L, 1850);
/*     */     
/* 308 */     for (int j = arrayList.size(); --j >= 0; ) {
/* 309 */       long l3 = paramDateTimeZone.previousTransition(l1);
/* 310 */       if (l3 == l1 || l3 < l2) {
/*     */         break;
/*     */       }
/*     */       
/* 314 */       l1 = l3;
/*     */       
/* 316 */       long l4 = ((Long)arrayList.get(j)).longValue();
/*     */       
/* 318 */       if (l4 - 1L != l1) {
/* 319 */         System.out.println("*r* Error in " + paramDateTimeZone.getID() + " " + new DateTime(l1, (Chronology)ISOChronology.getInstanceUTC()) + " != " + new DateTime(l4 - 1L, (Chronology)ISOChronology.getInstanceUTC()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 325 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 329 */     return true;
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
/*     */   public ZoneInfoCompiler() {
/* 345 */     this.iRuleSets = new HashMap<String, RuleSet>();
/* 346 */     this.iZones = new ArrayList<Zone>();
/* 347 */     this.iGoodLinks = new ArrayList<String>();
/* 348 */     this.iBackLinks = new ArrayList<String>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, DateTimeZone> compile(File paramFile, File[] paramArrayOfFile) throws IOException {
/* 358 */     if (paramArrayOfFile != null) {
/* 359 */       for (byte b1 = 0; b1 < paramArrayOfFile.length; b1++) {
/* 360 */         BufferedReader bufferedReader = new BufferedReader(new FileReader(paramArrayOfFile[b1]));
/* 361 */         parseDataFile(bufferedReader, "backward".equals(paramArrayOfFile[b1].getName()));
/* 362 */         bufferedReader.close();
/*     */       } 
/*     */     }
/*     */     
/* 366 */     if (paramFile != null) {
/* 367 */       if (!paramFile.exists() && !paramFile.mkdirs()) {
/* 368 */         throw new IOException("Destination directory doesn't exist and cannot be created: " + paramFile);
/*     */       }
/* 370 */       if (!paramFile.isDirectory()) {
/* 371 */         throw new IOException("Destination is not a directory: " + paramFile);
/*     */       }
/*     */     } 
/*     */     
/* 375 */     TreeMap<Object, Object> treeMap1 = new TreeMap<Object, Object>();
/* 376 */     TreeMap<Object, Object> treeMap2 = new TreeMap<Object, Object>();
/*     */     
/* 378 */     System.out.println("Writing zoneinfo files");
/*     */     byte b;
/* 380 */     for (b = 0; b < this.iZones.size(); b++) {
/* 381 */       Zone zone = this.iZones.get(b);
/* 382 */       DateTimeZoneBuilder dateTimeZoneBuilder = new DateTimeZoneBuilder();
/* 383 */       zone.addToBuilder(dateTimeZoneBuilder, this.iRuleSets);
/* 384 */       DateTimeZone dateTimeZone = dateTimeZoneBuilder.toDateTimeZone(zone.iName, true);
/* 385 */       if (test(dateTimeZone.getID(), dateTimeZone)) {
/* 386 */         treeMap1.put(dateTimeZone.getID(), dateTimeZone);
/* 387 */         treeMap2.put(dateTimeZone.getID(), zone);
/* 388 */         if (paramFile != null) {
/* 389 */           writeZone(paramFile, dateTimeZoneBuilder, dateTimeZone);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 395 */     for (b = 0; b < this.iGoodLinks.size(); b += 2) {
/* 396 */       String str1 = this.iGoodLinks.get(b);
/* 397 */       String str2 = this.iGoodLinks.get(b + 1);
/* 398 */       Zone zone = (Zone)treeMap2.get(str1);
/* 399 */       if (zone == null) {
/* 400 */         System.out.println("Cannot find source zone '" + str1 + "' to link alias '" + str2 + "' to");
/*     */       } else {
/* 402 */         DateTimeZoneBuilder dateTimeZoneBuilder = new DateTimeZoneBuilder();
/* 403 */         zone.addToBuilder(dateTimeZoneBuilder, this.iRuleSets);
/* 404 */         DateTimeZone dateTimeZone = dateTimeZoneBuilder.toDateTimeZone(str2, true);
/* 405 */         if (test(dateTimeZone.getID(), dateTimeZone)) {
/* 406 */           treeMap1.put(dateTimeZone.getID(), dateTimeZone);
/* 407 */           if (paramFile != null) {
/* 408 */             writeZone(paramFile, dateTimeZoneBuilder, dateTimeZone);
/*     */           }
/*     */         } 
/* 411 */         treeMap1.put(dateTimeZone.getID(), dateTimeZone);
/* 412 */         if (ZoneInfoLogger.verbose()) {
/* 413 */           System.out.println("Good link: " + str2 + " -> " + str1 + " revived");
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 419 */     for (b = 0; b < 2; b++) {
/* 420 */       for (byte b1 = 0; b1 < this.iBackLinks.size(); b1 += 2) {
/* 421 */         String str1 = this.iBackLinks.get(b1);
/* 422 */         String str2 = this.iBackLinks.get(b1 + 1);
/* 423 */         DateTimeZone dateTimeZone = (DateTimeZone)treeMap1.get(str1);
/* 424 */         if (dateTimeZone == null) {
/* 425 */           if (b > 0) {
/* 426 */             System.out.println("Cannot find time zone '" + str1 + "' to link alias '" + str2 + "' to");
/*     */           }
/*     */         } else {
/* 429 */           treeMap1.put(str2, dateTimeZone);
/* 430 */           if (ZoneInfoLogger.verbose()) {
/* 431 */             System.out.println("Back link: " + str2 + " -> " + dateTimeZone.getID());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 438 */     if (paramFile != null) {
/* 439 */       System.out.println("Writing ZoneInfoMap");
/* 440 */       File file = new File(paramFile, "ZoneInfoMap");
/* 441 */       if (!file.getParentFile().exists()) {
/* 442 */         file.getParentFile().mkdirs();
/*     */       }
/*     */       
/* 445 */       FileOutputStream fileOutputStream = new FileOutputStream(file);
/* 446 */       DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
/*     */       
/*     */       try {
/* 449 */         TreeMap<String, Object> treeMap = new TreeMap<String, Object>(String.CASE_INSENSITIVE_ORDER);
/* 450 */         treeMap.putAll(treeMap1);
/* 451 */         writeZoneInfoMap(dataOutputStream, (Map)treeMap);
/*     */       } finally {
/* 453 */         dataOutputStream.close();
/*     */       } 
/*     */     } 
/*     */     
/* 457 */     return (Map)treeMap1;
/*     */   }
/*     */   
/*     */   private void writeZone(File paramFile, DateTimeZoneBuilder paramDateTimeZoneBuilder, DateTimeZone paramDateTimeZone) throws IOException {
/* 461 */     if (ZoneInfoLogger.verbose()) {
/* 462 */       System.out.println("Writing " + paramDateTimeZone.getID());
/*     */     }
/* 464 */     File file = new File(paramFile, paramDateTimeZone.getID());
/* 465 */     if (!file.getParentFile().exists()) {
/* 466 */       file.getParentFile().mkdirs();
/*     */     }
/* 468 */     FileOutputStream fileOutputStream = new FileOutputStream(file);
/*     */     try {
/* 470 */       paramDateTimeZoneBuilder.writeTo(paramDateTimeZone.getID(), fileOutputStream);
/*     */     } finally {
/* 472 */       fileOutputStream.close();
/*     */     } 
/*     */ 
/*     */     
/* 476 */     FileInputStream fileInputStream = new FileInputStream(file);
/* 477 */     DateTimeZone dateTimeZone = DateTimeZoneBuilder.readFrom(fileInputStream, paramDateTimeZone.getID());
/* 478 */     fileInputStream.close();
/*     */     
/* 480 */     if (!paramDateTimeZone.equals(dateTimeZone)) {
/* 481 */       System.out.println("*e* Error in " + paramDateTimeZone.getID() + ": Didn't read properly from file");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void parseDataFile(BufferedReader paramBufferedReader, boolean paramBoolean) throws IOException {
/* 487 */     Zone zone = null;
/*     */     String str;
/* 489 */     while ((str = paramBufferedReader.readLine()) != null) {
/* 490 */       String str1 = str.trim();
/* 491 */       if (str1.length() == 0 || str1.charAt(0) == '#') {
/*     */         continue;
/*     */       }
/*     */       
/* 495 */       int i = str.indexOf('#');
/* 496 */       if (i >= 0) {
/* 497 */         str = str.substring(0, i);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 502 */       StringTokenizer stringTokenizer = new StringTokenizer(str, " \t");
/*     */       
/* 504 */       if (Character.isWhitespace(str.charAt(0)) && stringTokenizer.hasMoreTokens()) {
/* 505 */         if (zone != null)
/*     */         {
/* 507 */           zone.chain(stringTokenizer);
/*     */         }
/*     */         continue;
/*     */       } 
/* 511 */       if (zone != null) {
/* 512 */         this.iZones.add(zone);
/*     */       }
/* 514 */       zone = null;
/*     */ 
/*     */       
/* 517 */       if (stringTokenizer.hasMoreTokens()) {
/* 518 */         String str2 = stringTokenizer.nextToken();
/* 519 */         if (str2.equalsIgnoreCase("Rule")) {
/* 520 */           Rule rule = new Rule(stringTokenizer);
/* 521 */           RuleSet ruleSet = this.iRuleSets.get(rule.iName);
/* 522 */           if (ruleSet == null) {
/* 523 */             ruleSet = new RuleSet(rule);
/* 524 */             this.iRuleSets.put(rule.iName, ruleSet); continue;
/*     */           } 
/* 526 */           ruleSet.addRule(rule); continue;
/*     */         } 
/* 528 */         if (str2.equalsIgnoreCase("Zone")) {
/* 529 */           if (stringTokenizer.countTokens() < 4) {
/* 530 */             throw new IllegalArgumentException("Attempting to create a Zone from an incomplete tokenizer");
/*     */           }
/* 532 */           zone = new Zone(stringTokenizer); continue;
/* 533 */         }  if (str2.equalsIgnoreCase("Link")) {
/* 534 */           String str3 = stringTokenizer.nextToken();
/* 535 */           String str4 = stringTokenizer.nextToken();
/*     */ 
/*     */ 
/*     */           
/* 539 */           if (paramBoolean || str4.equals("US/Pacific-New") || str4.startsWith("Etc/") || str4.equals("GMT")) {
/* 540 */             this.iBackLinks.add(str3);
/* 541 */             this.iBackLinks.add(str4); continue;
/*     */           } 
/* 543 */           this.iGoodLinks.add(str3);
/* 544 */           this.iGoodLinks.add(str4);
/*     */           continue;
/*     */         } 
/* 547 */         System.out.println("Unknown line: " + str);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 552 */     if (zone != null)
/* 553 */       this.iZones.add(zone); 
/*     */   }
/*     */   
/*     */   static class DateTimeOfYear
/*     */   {
/*     */     public final int iMonthOfYear;
/*     */     public final int iDayOfMonth;
/*     */     public final int iDayOfWeek;
/*     */     public final boolean iAdvanceDayOfWeek;
/*     */     public final int iMillisOfDay;
/*     */     public final char iZoneChar;
/*     */     
/*     */     DateTimeOfYear() {
/* 566 */       this.iMonthOfYear = 1;
/* 567 */       this.iDayOfMonth = 1;
/* 568 */       this.iDayOfWeek = 0;
/* 569 */       this.iAdvanceDayOfWeek = false;
/* 570 */       this.iMillisOfDay = 0;
/* 571 */       this.iZoneChar = 'w';
/*     */     }
/*     */     
/*     */     DateTimeOfYear(StringTokenizer param1StringTokenizer) {
/* 575 */       int i = 1;
/* 576 */       int j = 1;
/* 577 */       int k = 0;
/* 578 */       int m = 0;
/* 579 */       boolean bool = false;
/* 580 */       char c = 'w';
/*     */       
/* 582 */       if (param1StringTokenizer.hasMoreTokens()) {
/* 583 */         i = ZoneInfoCompiler.parseMonth(param1StringTokenizer.nextToken());
/*     */         
/* 585 */         if (param1StringTokenizer.hasMoreTokens()) {
/* 586 */           String str = param1StringTokenizer.nextToken();
/* 587 */           if (str.startsWith("last")) {
/* 588 */             j = -1;
/* 589 */             k = ZoneInfoCompiler.parseDayOfWeek(str.substring(4));
/* 590 */             bool = false;
/*     */           } else {
/*     */             try {
/* 593 */               j = Integer.parseInt(str);
/* 594 */               k = 0;
/* 595 */               bool = false;
/* 596 */             } catch (NumberFormatException numberFormatException) {
/* 597 */               int n = str.indexOf(">=");
/* 598 */               if (n > 0) {
/* 599 */                 j = Integer.parseInt(str.substring(n + 2));
/* 600 */                 k = ZoneInfoCompiler.parseDayOfWeek(str.substring(0, n));
/* 601 */                 bool = true;
/*     */               } else {
/* 603 */                 n = str.indexOf("<=");
/* 604 */                 if (n > 0) {
/* 605 */                   j = Integer.parseInt(str.substring(n + 2));
/* 606 */                   k = ZoneInfoCompiler.parseDayOfWeek(str.substring(0, n));
/* 607 */                   bool = false;
/*     */                 } else {
/* 609 */                   throw new IllegalArgumentException(str);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 615 */           if (param1StringTokenizer.hasMoreTokens()) {
/* 616 */             str = param1StringTokenizer.nextToken();
/* 617 */             c = ZoneInfoCompiler.parseZoneChar(str.charAt(str.length() - 1));
/* 618 */             if (str.equals("24:00")) {
/*     */               
/* 620 */               if (i == 12 && j == 31) {
/* 621 */                 m = ZoneInfoCompiler.parseTime("23:59:59.999");
/*     */               } else {
/* 623 */                 LocalDate localDate = (j == -1) ? (new LocalDate(2001, i, 1)).plusMonths(1) : (new LocalDate(2001, i, j)).plusDays(1);
/*     */ 
/*     */                 
/* 626 */                 bool = (j != -1 && k != 0) ? true : false;
/* 627 */                 i = localDate.getMonthOfYear();
/* 628 */                 j = localDate.getDayOfMonth();
/* 629 */                 if (k != 0) {
/* 630 */                   k = (k - 1 + 1) % 7 + 1;
/*     */                 }
/*     */               } 
/*     */             } else {
/* 634 */               m = ZoneInfoCompiler.parseTime(str);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 640 */       this.iMonthOfYear = i;
/* 641 */       this.iDayOfMonth = j;
/* 642 */       this.iDayOfWeek = k;
/* 643 */       this.iAdvanceDayOfWeek = bool;
/* 644 */       this.iMillisOfDay = m;
/* 645 */       this.iZoneChar = c;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void addRecurring(DateTimeZoneBuilder param1DateTimeZoneBuilder, String param1String, int param1Int1, int param1Int2, int param1Int3) {
/* 654 */       param1DateTimeZoneBuilder.addRecurringSavings(param1String, param1Int1, param1Int2, param1Int3, this.iZoneChar, this.iMonthOfYear, this.iDayOfMonth, this.iDayOfWeek, this.iAdvanceDayOfWeek, this.iMillisOfDay);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void addCutover(DateTimeZoneBuilder param1DateTimeZoneBuilder, int param1Int) {
/* 668 */       param1DateTimeZoneBuilder.addCutover(param1Int, this.iZoneChar, this.iMonthOfYear, this.iDayOfMonth, this.iDayOfWeek, this.iAdvanceDayOfWeek, this.iMillisOfDay);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 678 */       return "MonthOfYear: " + this.iMonthOfYear + "\n" + "DayOfMonth: " + this.iDayOfMonth + "\n" + "DayOfWeek: " + this.iDayOfWeek + "\n" + "AdvanceDayOfWeek: " + this.iAdvanceDayOfWeek + "\n" + "MillisOfDay: " + this.iMillisOfDay + "\n" + "ZoneChar: " + this.iZoneChar + "\n";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class Rule
/*     */   {
/*     */     public final String iName;
/*     */     
/*     */     public final int iFromYear;
/*     */     
/*     */     public final int iToYear;
/*     */     
/*     */     public final String iType;
/*     */     
/*     */     public final ZoneInfoCompiler.DateTimeOfYear iDateTimeOfYear;
/*     */     public final int iSaveMillis;
/*     */     public final String iLetterS;
/*     */     
/*     */     Rule(StringTokenizer param1StringTokenizer) {
/* 698 */       if (param1StringTokenizer.countTokens() < 6) {
/* 699 */         throw new IllegalArgumentException("Attempting to create a Rule from an incomplete tokenizer");
/*     */       }
/* 701 */       this.iName = param1StringTokenizer.nextToken().intern();
/* 702 */       this.iFromYear = ZoneInfoCompiler.parseYear(param1StringTokenizer.nextToken(), 0);
/* 703 */       this.iToYear = ZoneInfoCompiler.parseYear(param1StringTokenizer.nextToken(), this.iFromYear);
/* 704 */       if (this.iToYear < this.iFromYear) {
/* 705 */         throw new IllegalArgumentException();
/*     */       }
/* 707 */       this.iType = ZoneInfoCompiler.parseOptional(param1StringTokenizer.nextToken());
/* 708 */       this.iDateTimeOfYear = new ZoneInfoCompiler.DateTimeOfYear(param1StringTokenizer);
/* 709 */       this.iSaveMillis = ZoneInfoCompiler.parseTime(param1StringTokenizer.nextToken());
/* 710 */       this.iLetterS = ZoneInfoCompiler.parseOptional(param1StringTokenizer.nextToken());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void addRecurring(DateTimeZoneBuilder param1DateTimeZoneBuilder, String param1String) {
/* 717 */       String str = formatName(param1String);
/* 718 */       this.iDateTimeOfYear.addRecurring(param1DateTimeZoneBuilder, str, this.iSaveMillis, this.iFromYear, this.iToYear);
/*     */     }
/*     */     
/*     */     private String formatName(String param1String) {
/*     */       String str3;
/* 723 */       int i = param1String.indexOf('/');
/* 724 */       if (i > 0) {
/* 725 */         if (this.iSaveMillis == 0)
/*     */         {
/* 727 */           return param1String.substring(0, i).intern();
/*     */         }
/* 729 */         return param1String.substring(i + 1).intern();
/*     */       } 
/*     */       
/* 732 */       i = param1String.indexOf("%s");
/* 733 */       if (i < 0) {
/* 734 */         return param1String;
/*     */       }
/* 736 */       String str1 = param1String.substring(0, i);
/* 737 */       String str2 = param1String.substring(i + 2);
/*     */       
/* 739 */       if (this.iLetterS == null) {
/* 740 */         str3 = str1.concat(str2);
/*     */       } else {
/* 742 */         str3 = str1 + this.iLetterS + str2;
/*     */       } 
/* 744 */       return str3.intern();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 748 */       return "[Rule]\nName: " + this.iName + "\n" + "FromYear: " + this.iFromYear + "\n" + "ToYear: " + this.iToYear + "\n" + "Type: " + this.iType + "\n" + this.iDateTimeOfYear + "SaveMillis: " + this.iSaveMillis + "\n" + "LetterS: " + this.iLetterS + "\n";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class RuleSet
/*     */   {
/*     */     private List<ZoneInfoCompiler.Rule> iRules;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     RuleSet(ZoneInfoCompiler.Rule param1Rule) {
/* 764 */       this.iRules = new ArrayList<ZoneInfoCompiler.Rule>();
/* 765 */       this.iRules.add(param1Rule);
/*     */     }
/*     */     
/*     */     void addRule(ZoneInfoCompiler.Rule param1Rule) {
/* 769 */       if (!param1Rule.iName.equals(((ZoneInfoCompiler.Rule)this.iRules.get(0)).iName)) {
/* 770 */         throw new IllegalArgumentException("Rule name mismatch");
/*     */       }
/* 772 */       this.iRules.add(param1Rule);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void addRecurring(DateTimeZoneBuilder param1DateTimeZoneBuilder, String param1String) {
/* 779 */       for (byte b = 0; b < this.iRules.size(); b++) {
/* 780 */         ZoneInfoCompiler.Rule rule = this.iRules.get(b);
/* 781 */         rule.addRecurring(param1DateTimeZoneBuilder, param1String);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Zone
/*     */   {
/*     */     public final String iName;
/*     */     public final int iOffsetMillis;
/*     */     public final String iRules;
/*     */     public final String iFormat;
/*     */     public final int iUntilYear;
/*     */     public final ZoneInfoCompiler.DateTimeOfYear iUntilDateTimeOfYear;
/*     */     private Zone iNext;
/*     */     
/*     */     Zone(StringTokenizer param1StringTokenizer) {
/* 797 */       this(param1StringTokenizer.nextToken(), param1StringTokenizer);
/*     */     }
/*     */     
/*     */     private Zone(String param1String, StringTokenizer param1StringTokenizer) {
/* 801 */       this.iName = param1String.intern();
/* 802 */       this.iOffsetMillis = ZoneInfoCompiler.parseTime(param1StringTokenizer.nextToken());
/* 803 */       this.iRules = ZoneInfoCompiler.parseOptional(param1StringTokenizer.nextToken());
/* 804 */       this.iFormat = param1StringTokenizer.nextToken().intern();
/*     */       
/* 806 */       int i = Integer.MAX_VALUE;
/* 807 */       ZoneInfoCompiler.DateTimeOfYear dateTimeOfYear = ZoneInfoCompiler.getStartOfYear();
/*     */       
/* 809 */       if (param1StringTokenizer.hasMoreTokens()) {
/* 810 */         i = Integer.parseInt(param1StringTokenizer.nextToken());
/* 811 */         if (param1StringTokenizer.hasMoreTokens()) {
/* 812 */           dateTimeOfYear = new ZoneInfoCompiler.DateTimeOfYear(param1StringTokenizer);
/*     */         }
/*     */       } 
/*     */       
/* 816 */       this.iUntilYear = i;
/* 817 */       this.iUntilDateTimeOfYear = dateTimeOfYear;
/*     */     }
/*     */     
/*     */     void chain(StringTokenizer param1StringTokenizer) {
/* 821 */       if (this.iNext != null) {
/* 822 */         this.iNext.chain(param1StringTokenizer);
/*     */       } else {
/* 824 */         this.iNext = new Zone(this.iName, param1StringTokenizer);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void addToBuilder(DateTimeZoneBuilder param1DateTimeZoneBuilder, Map<String, ZoneInfoCompiler.RuleSet> param1Map) {
/* 840 */       addToBuilder(this, param1DateTimeZoneBuilder, param1Map);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static void addToBuilder(Zone param1Zone, DateTimeZoneBuilder param1DateTimeZoneBuilder, Map<String, ZoneInfoCompiler.RuleSet> param1Map) {
/* 847 */       for (; param1Zone != null; param1Zone = param1Zone.iNext) {
/* 848 */         param1DateTimeZoneBuilder.setStandardOffset(param1Zone.iOffsetMillis);
/*     */         
/* 850 */         if (param1Zone.iRules == null) {
/* 851 */           param1DateTimeZoneBuilder.setFixedSavings(param1Zone.iFormat, 0);
/*     */         } else {
/*     */           
/*     */           try {
/* 855 */             int i = ZoneInfoCompiler.parseTime(param1Zone.iRules);
/* 856 */             param1DateTimeZoneBuilder.setFixedSavings(param1Zone.iFormat, i);
/*     */           }
/* 858 */           catch (Exception exception) {
/* 859 */             ZoneInfoCompiler.RuleSet ruleSet = param1Map.get(param1Zone.iRules);
/* 860 */             if (ruleSet == null) {
/* 861 */               throw new IllegalArgumentException("Rules not found: " + param1Zone.iRules);
/*     */             }
/*     */             
/* 864 */             ruleSet.addRecurring(param1DateTimeZoneBuilder, param1Zone.iFormat);
/*     */           } 
/*     */         } 
/*     */         
/* 868 */         if (param1Zone.iUntilYear == Integer.MAX_VALUE) {
/*     */           break;
/*     */         }
/*     */         
/* 872 */         param1Zone.iUntilDateTimeOfYear.addCutover(param1DateTimeZoneBuilder, param1Zone.iUntilYear);
/*     */       } 
/*     */     }
/*     */     
/*     */     public String toString() {
/* 877 */       String str = "[Zone]\nName: " + this.iName + "\n" + "OffsetMillis: " + this.iOffsetMillis + "\n" + "Rules: " + this.iRules + "\n" + "Format: " + this.iFormat + "\n" + "UntilYear: " + this.iUntilYear + "\n" + this.iUntilDateTimeOfYear;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 886 */       if (this.iNext == null) {
/* 887 */         return str;
/*     */       }
/*     */       
/* 890 */       return str + "...\n" + this.iNext.toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/tz/ZoneInfoCompiler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */