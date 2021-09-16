/*     */ package org.joda.time.convert;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ConverterSet
/*     */ {
/*     */   private final Converter[] iConverters;
/*     */   private Entry[] iSelectEntries;
/*     */   
/*     */   ConverterSet(Converter[] paramArrayOfConverter) {
/*  35 */     this.iConverters = paramArrayOfConverter;
/*  36 */     this.iSelectEntries = new Entry[16];
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
/*     */   Converter select(Class<?> paramClass) throws IllegalStateException {
/*  49 */     Entry[] arrayOfEntry1 = this.iSelectEntries;
/*  50 */     int i = arrayOfEntry1.length;
/*  51 */     byte b1 = (paramClass == null) ? 0 : (paramClass.hashCode() & i - 1);
/*     */     
/*     */     Entry entry;
/*     */     
/*  55 */     while ((entry = arrayOfEntry1[b1]) != null) {
/*  56 */       if (entry.iType == paramClass) {
/*  57 */         return entry.iConverter;
/*     */       }
/*  59 */       if (++b1 >= i) {
/*  60 */         b1 = 0;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  66 */     Converter converter = selectSlow(this, paramClass);
/*  67 */     entry = new Entry(paramClass, converter);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     arrayOfEntry1 = (Entry[])arrayOfEntry1.clone();
/*     */ 
/*     */     
/*  82 */     arrayOfEntry1[b1] = entry;
/*     */     
/*     */     int j;
/*  85 */     for (j = 0; j < i; j++) {
/*  86 */       if (arrayOfEntry1[j] == null) {
/*     */         
/*  88 */         this.iSelectEntries = arrayOfEntry1;
/*  89 */         return converter;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  95 */     j = i << 1;
/*  96 */     Entry[] arrayOfEntry2 = new Entry[j];
/*  97 */     for (byte b2 = 0; b2 < i; b2++) {
/*  98 */       entry = arrayOfEntry1[b2];
/*  99 */       paramClass = entry.iType;
/* 100 */       b1 = (paramClass == null) ? 0 : (paramClass.hashCode() & j - 1);
/* 101 */       while (arrayOfEntry2[b1] != null) {
/* 102 */         if (++b1 >= j) {
/* 103 */           b1 = 0;
/*     */         }
/*     */       } 
/* 106 */       arrayOfEntry2[b1] = entry;
/*     */     } 
/*     */ 
/*     */     
/* 110 */     this.iSelectEntries = arrayOfEntry2;
/* 111 */     return converter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int size() {
/* 118 */     return this.iConverters.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void copyInto(Converter[] paramArrayOfConverter) {
/* 125 */     System.arraycopy(this.iConverters, 0, paramArrayOfConverter, 0, this.iConverters.length);
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
/*     */   ConverterSet add(Converter paramConverter, Converter[] paramArrayOfConverter) {
/* 139 */     Converter[] arrayOfConverter1 = this.iConverters;
/* 140 */     int i = arrayOfConverter1.length;
/*     */     
/* 142 */     for (byte b = 0; b < i; b++) {
/* 143 */       Converter converter = arrayOfConverter1[b];
/* 144 */       if (paramConverter.equals(converter)) {
/*     */         
/* 146 */         if (paramArrayOfConverter != null) {
/* 147 */           paramArrayOfConverter[0] = null;
/*     */         }
/* 149 */         return this;
/*     */       } 
/*     */       
/* 152 */       if (paramConverter.getSupportedType() == converter.getSupportedType()) {
/*     */         
/* 154 */         Converter[] arrayOfConverter = new Converter[i];
/*     */         
/* 156 */         for (byte b1 = 0; b1 < i; b1++) {
/* 157 */           if (b1 != b) {
/* 158 */             arrayOfConverter[b1] = arrayOfConverter1[b1];
/*     */           } else {
/* 160 */             arrayOfConverter[b1] = paramConverter;
/*     */           } 
/*     */         } 
/*     */         
/* 164 */         if (paramArrayOfConverter != null) {
/* 165 */           paramArrayOfConverter[0] = converter;
/*     */         }
/* 167 */         return new ConverterSet(arrayOfConverter);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 172 */     Converter[] arrayOfConverter2 = new Converter[i + 1];
/* 173 */     System.arraycopy(arrayOfConverter1, 0, arrayOfConverter2, 0, i);
/* 174 */     arrayOfConverter2[i] = paramConverter;
/*     */     
/* 176 */     if (paramArrayOfConverter != null) {
/* 177 */       paramArrayOfConverter[0] = null;
/*     */     }
/* 179 */     return new ConverterSet(arrayOfConverter2);
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
/*     */   ConverterSet remove(Converter paramConverter, Converter[] paramArrayOfConverter) {
/* 191 */     Converter[] arrayOfConverter = this.iConverters;
/* 192 */     int i = arrayOfConverter.length;
/*     */     
/* 194 */     for (byte b = 0; b < i; b++) {
/* 195 */       if (paramConverter.equals(arrayOfConverter[b])) {
/* 196 */         return remove(b, paramArrayOfConverter);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 201 */     if (paramArrayOfConverter != null) {
/* 202 */       paramArrayOfConverter[0] = null;
/*     */     }
/* 204 */     return this;
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
/*     */   ConverterSet remove(int paramInt, Converter[] paramArrayOfConverter) {
/* 216 */     Converter[] arrayOfConverter1 = this.iConverters;
/* 217 */     int i = arrayOfConverter1.length;
/* 218 */     if (paramInt >= i) {
/* 219 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */     
/* 222 */     if (paramArrayOfConverter != null) {
/* 223 */       paramArrayOfConverter[0] = arrayOfConverter1[paramInt];
/*     */     }
/*     */     
/* 226 */     Converter[] arrayOfConverter2 = new Converter[i - 1];
/*     */     
/* 228 */     byte b1 = 0;
/* 229 */     for (byte b2 = 0; b2 < i; b2++) {
/* 230 */       if (b2 != paramInt) {
/* 231 */         arrayOfConverter2[b1++] = arrayOfConverter1[b2];
/*     */       }
/*     */     } 
/*     */     
/* 235 */     return new ConverterSet(arrayOfConverter2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Converter selectSlow(ConverterSet paramConverterSet, Class<?> paramClass) {
/* 243 */     Converter[] arrayOfConverter = paramConverterSet.iConverters;
/* 244 */     int i = arrayOfConverter.length;
/*     */     
/*     */     int j;
/* 247 */     for (j = i; --j >= 0; ) {
/* 248 */       Converter converter = arrayOfConverter[j];
/* 249 */       Class<?> clazz = converter.getSupportedType();
/*     */       
/* 251 */       if (clazz == paramClass)
/*     */       {
/* 253 */         return converter;
/*     */       }
/*     */       
/* 256 */       if (clazz == null || (paramClass != null && !clazz.isAssignableFrom(paramClass))) {
/*     */         
/* 258 */         paramConverterSet = paramConverterSet.remove(j, (Converter[])null);
/* 259 */         arrayOfConverter = paramConverterSet.iConverters;
/* 260 */         i = arrayOfConverter.length;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 266 */     if (paramClass == null || i == 0) {
/* 267 */       return null;
/*     */     }
/* 269 */     if (i == 1)
/*     */     {
/* 271 */       return arrayOfConverter[0];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 277 */     for (j = i; --j >= 0; ) {
/* 278 */       Converter converter = arrayOfConverter[j];
/* 279 */       Class<?> clazz = converter.getSupportedType();
/* 280 */       for (int k = i; --k >= 0;) {
/* 281 */         if (k != j && arrayOfConverter[k].getSupportedType().isAssignableFrom(clazz)) {
/*     */           
/* 283 */           paramConverterSet = paramConverterSet.remove(k, (Converter[])null);
/* 284 */           arrayOfConverter = paramConverterSet.iConverters;
/* 285 */           i = arrayOfConverter.length;
/* 286 */           j = i - 1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 293 */     if (i == 1)
/*     */     {
/* 295 */       return arrayOfConverter[0];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 301 */     StringBuilder stringBuilder = new StringBuilder();
/* 302 */     stringBuilder.append("Unable to find best converter for type \"");
/* 303 */     stringBuilder.append(paramClass.getName());
/* 304 */     stringBuilder.append("\" from remaining set: ");
/* 305 */     for (byte b = 0; b < i; b++) {
/* 306 */       Converter converter = arrayOfConverter[b];
/* 307 */       Class<?> clazz = converter.getSupportedType();
/*     */       
/* 309 */       stringBuilder.append(converter.getClass().getName());
/* 310 */       stringBuilder.append('[');
/* 311 */       stringBuilder.append((clazz == null) ? null : clazz.getName());
/* 312 */       stringBuilder.append("], ");
/*     */     } 
/*     */     
/* 315 */     throw new IllegalStateException(stringBuilder.toString());
/*     */   }
/*     */   
/*     */   static class Entry {
/*     */     final Class<?> iType;
/*     */     final Converter iConverter;
/*     */     
/*     */     Entry(Class<?> param1Class, Converter param1Converter) {
/* 323 */       this.iType = param1Class;
/* 324 */       this.iConverter = param1Converter;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/convert/ConverterSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */