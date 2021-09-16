/*     */ package org.apache.http.osgi.impl;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class PropertiesUtils
/*     */ {
/*  40 */   private static final Map<Class<?>, PropertyConverter<?>> CONVERTERS_REGISTRY = new HashMap<Class<?>, PropertyConverter<?>>();
/*     */ 
/*     */   
/*     */   static {
/*  44 */     register(new BooleanPropertyConverter(), new Class[] { boolean.class, Boolean.class });
/*  45 */     register(new StringPropertyConverter(), new Class[] { String.class });
/*  46 */     register(new StringArrayPropertyConverter(), new Class[] { String[].class });
/*  47 */     register(new IntegerPropertyConverter(), new Class[] { int.class, Integer.class });
/*  48 */     register(new LongPropertyConverter(), new Class[] { long.class, Long.class });
/*  49 */     register(new DoublePropertyConverter(), new Class[] { double.class, Double.class });
/*     */   }
/*     */   
/*     */   private static void register(PropertyConverter<?> converter, Class<?>... targetTypes) {
/*  53 */     for (Class<?> targetType : targetTypes) {
/*  54 */       CONVERTERS_REGISTRY.put(targetType, converter);
/*     */     }
/*     */   }
/*     */   
/*     */   public static <T> T to(Object propValue, Class<T> targetType, T defaultValue) {
/*  59 */     Object v = propValue;
/*  60 */     if (v == null) {
/*  61 */       return defaultValue;
/*     */     }
/*     */     
/*  64 */     if (!targetType.isArray()) {
/*  65 */       v = toObject(v);
/*     */     }
/*     */     
/*  68 */     if (targetType.isInstance(v)) {
/*  69 */       return targetType.cast(v);
/*     */     }
/*     */     
/*  72 */     if (CONVERTERS_REGISTRY.containsKey(targetType)) {
/*     */       
/*  74 */       PropertyConverter<T> converter = (PropertyConverter<T>)CONVERTERS_REGISTRY.get(targetType);
/*     */       try {
/*  76 */         return converter.to(v);
/*  77 */       } catch (Exception ignore) {}
/*     */     } 
/*     */ 
/*     */     
/*  81 */     return defaultValue;
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
/*     */   private static Object toObject(Object propValue) {
/*  94 */     if (propValue.getClass().isArray()) {
/*  95 */       Object[] prop = (Object[])propValue;
/*  96 */       return (prop.length > 0) ? prop[0] : null;
/*     */     } 
/*     */     
/*  99 */     if (propValue instanceof Collection) {
/* 100 */       Collection<?> prop = (Collection)propValue;
/* 101 */       return prop.isEmpty() ? null : prop.iterator().next();
/*     */     } 
/*     */     
/* 104 */     return propValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static interface PropertyConverter<T>
/*     */   {
/*     */     T to(Object param1Object);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class BooleanPropertyConverter
/*     */     implements PropertyConverter<Boolean>
/*     */   {
/*     */     private BooleanPropertyConverter() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public Boolean to(Object propValue) {
/* 124 */       return Boolean.valueOf(String.valueOf(propValue));
/*     */     }
/*     */   }
/*     */   
/*     */   private static class StringPropertyConverter
/*     */     implements PropertyConverter<String> {
/*     */     private StringPropertyConverter() {}
/*     */     
/*     */     public String to(Object propValue) {
/* 133 */       return String.valueOf(propValue);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class StringArrayPropertyConverter
/*     */     implements PropertyConverter<String[]> {
/*     */     private StringArrayPropertyConverter() {}
/*     */     
/*     */     public String[] to(Object propValue) {
/* 142 */       if (propValue instanceof String)
/*     */       {
/* 144 */         return new String[] { (String)propValue };
/*     */       }
/*     */       
/* 147 */       if (propValue.getClass().isArray()) {
/*     */         
/* 149 */         Object[] valueArray = (Object[])propValue;
/* 150 */         List<String> values = new ArrayList<String>(valueArray.length);
/* 151 */         for (Object value : valueArray) {
/* 152 */           if (value != null) {
/* 153 */             values.add(value.toString());
/*     */           }
/*     */         } 
/* 156 */         return values.<String>toArray(new String[values.size()]);
/*     */       } 
/*     */ 
/*     */       
/* 160 */       if (propValue instanceof Collection) {
/*     */         
/* 162 */         Collection<?> valueCollection = (Collection)propValue;
/* 163 */         List<String> valueList = new ArrayList<String>(valueCollection.size());
/* 164 */         for (Object value : valueCollection) {
/* 165 */           if (value != null) {
/* 166 */             valueList.add(value.toString());
/*     */           }
/*     */         } 
/* 169 */         return valueList.<String>toArray(new String[valueList.size()]);
/*     */       } 
/*     */ 
/*     */       
/* 173 */       throw new IllegalArgumentException();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class IntegerPropertyConverter
/*     */     implements PropertyConverter<Integer> {
/*     */     private IntegerPropertyConverter() {}
/*     */     
/*     */     public Integer to(Object propValue) {
/* 182 */       return Integer.valueOf(String.valueOf(propValue));
/*     */     }
/*     */   }
/*     */   
/*     */   private static class LongPropertyConverter
/*     */     implements PropertyConverter<Long> {
/*     */     private LongPropertyConverter() {}
/*     */     
/*     */     public Long to(Object propValue) {
/* 191 */       return Long.valueOf(String.valueOf(propValue));
/*     */     }
/*     */   }
/*     */   
/*     */   private static class DoublePropertyConverter
/*     */     implements PropertyConverter<Double> {
/*     */     private DoublePropertyConverter() {}
/*     */     
/*     */     public Double to(Object propValue) {
/* 200 */       return Double.valueOf(String.valueOf(propValue));
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/osgi/impl/PropertiesUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */