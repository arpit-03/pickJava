/*      */ package org.json;
/*      */ 
/*      */ import java.io.Closeable;
/*      */ import java.io.IOException;
/*      */ import java.io.StringWriter;
/*      */ import java.io.Writer;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.util.Collection;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Set;
/*      */ import java.util.regex.Pattern;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JSONObject
/*      */ {
/*      */   private static final class Null
/*      */   {
/*      */     private Null() {}
/*      */     
/*      */     protected final Object clone() {
/*  119 */       return this;
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
/*      */     public boolean equals(Object object) {
/*  132 */       return (object == null || object == this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  141 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  151 */       return "null";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  159 */   static final Pattern NUMBER_PATTERN = Pattern.compile("-?(?:0|[1-9]\\d*)(?:\\.\\d+)?(?:[eE][+-]?\\d+)?");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Map<String, Object> map;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  172 */   public static final Object NULL = new Null();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject() {
/*  184 */     this.map = new HashMap<String, Object>();
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
/*      */   public JSONObject(JSONObject jo, String[] names) {
/*  198 */     this(names.length);
/*  199 */     for (int i = 0; i < names.length; i++) {
/*      */       try {
/*  201 */         putOnce(names[i], jo.opt(names[i]));
/*  202 */       } catch (Exception exception) {}
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
/*      */   
/*      */   public JSONObject(JSONTokener x) throws JSONException {
/*  217 */     this();
/*      */ 
/*      */ 
/*      */     
/*  221 */     if (x.nextClean() != '{') {
/*  222 */       throw x.syntaxError("A JSONObject text must begin with '{'");
/*      */     }
/*      */     while (true) {
/*  225 */       char c = x.nextClean();
/*  226 */       switch (c) {
/*      */         case '\000':
/*  228 */           throw x.syntaxError("A JSONObject text must end with '}'");
/*      */         case '}':
/*      */           return;
/*      */       } 
/*  232 */       x.back();
/*  233 */       String key = x.nextValue().toString();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  238 */       c = x.nextClean();
/*  239 */       if (c != ':') {
/*  240 */         throw x.syntaxError("Expected a ':' after a key");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  245 */       if (key != null) {
/*      */         
/*  247 */         if (opt(key) != null)
/*      */         {
/*  249 */           throw x.syntaxError("Duplicate key \"" + key + "\"");
/*      */         }
/*      */         
/*  252 */         Object value = x.nextValue();
/*  253 */         if (value != null) {
/*  254 */           put(key, value);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  260 */       switch (x.nextClean()) {
/*      */         case ',':
/*      */         case ';':
/*  263 */           if (x.nextClean() == '}') {
/*      */             return;
/*      */           }
/*  266 */           x.back(); continue;
/*      */         case '}':
/*      */           return;
/*      */       }  break;
/*      */     } 
/*  271 */     throw x.syntaxError("Expected a ',' or '}'");
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
/*      */   public JSONObject(Map<?, ?> m) {
/*  288 */     if (m == null) {
/*  289 */       this.map = new HashMap<String, Object>();
/*      */     } else {
/*  291 */       this.map = new HashMap<String, Object>(m.size());
/*  292 */       for (Map.Entry<?, ?> e : m.entrySet()) {
/*  293 */         if (e.getKey() == null) {
/*  294 */           throw new NullPointerException("Null key.");
/*      */         }
/*  296 */         Object value = e.getValue();
/*  297 */         if (value != null) {
/*  298 */           this.map.put(String.valueOf(e.getKey()), wrap(value));
/*      */         }
/*      */       } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject(Object bean) {
/*  363 */     this();
/*  364 */     populateMap(bean);
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
/*      */   public JSONObject(Object object, String[] names) {
/*  382 */     this(names.length);
/*  383 */     Class<?> c = object.getClass();
/*  384 */     for (int i = 0; i < names.length; i++) {
/*  385 */       String name = names[i];
/*      */       try {
/*  387 */         putOpt(name, c.getField(name).get(object));
/*  388 */       } catch (Exception exception) {}
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject(String source) throws JSONException {
/*  406 */     this(new JSONTokener(source));
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
/*      */   public JSONObject(String baseName, Locale locale) throws JSONException {
/*  420 */     this();
/*  421 */     ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale, 
/*  422 */         Thread.currentThread().getContextClassLoader());
/*      */ 
/*      */ 
/*      */     
/*  426 */     Enumeration<String> keys = bundle.getKeys();
/*  427 */     while (keys.hasMoreElements()) {
/*  428 */       Object key = keys.nextElement();
/*  429 */       if (key != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  435 */         String[] path = ((String)key).split("\\.");
/*  436 */         int last = path.length - 1;
/*  437 */         JSONObject target = this;
/*  438 */         for (int i = 0; i < last; i++) {
/*  439 */           String segment = path[i];
/*  440 */           JSONObject nextTarget = target.optJSONObject(segment);
/*  441 */           if (nextTarget == null) {
/*  442 */             nextTarget = new JSONObject();
/*  443 */             target.put(segment, nextTarget);
/*      */           } 
/*  445 */           target = nextTarget;
/*      */         } 
/*  447 */         target.put(path[last], bundle.getString((String)key));
/*      */       } 
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
/*      */   protected JSONObject(int initialCapacity) {
/*  460 */     this.map = new HashMap<String, Object>(initialCapacity);
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
/*      */   public JSONObject accumulate(String key, Object value) throws JSONException {
/*  485 */     testValidity(value);
/*  486 */     Object object = opt(key);
/*  487 */     if (object == null) {
/*  488 */       put(key, 
/*  489 */           (value instanceof JSONArray) ? (new JSONArray()).put(value) : 
/*  490 */           value);
/*  491 */     } else if (object instanceof JSONArray) {
/*  492 */       ((JSONArray)object).put(value);
/*      */     } else {
/*  494 */       put(key, (new JSONArray()).put(object).put(value));
/*      */     } 
/*  496 */     return this;
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
/*      */   public JSONObject append(String key, Object value) throws JSONException {
/*  517 */     testValidity(value);
/*  518 */     Object object = opt(key);
/*  519 */     if (object == null) {
/*  520 */       put(key, (new JSONArray()).put(value));
/*  521 */     } else if (object instanceof JSONArray) {
/*  522 */       put(key, ((JSONArray)object).put(value));
/*      */     } else {
/*  524 */       throw new JSONException("JSONObject[" + key + "] is not a JSONArray.");
/*      */     } 
/*      */     
/*  527 */     return this;
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
/*      */   public static String doubleToString(double d) {
/*  539 */     if (Double.isInfinite(d) || Double.isNaN(d)) {
/*  540 */       return "null";
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  545 */     String string = Double.toString(d);
/*  546 */     if (string.indexOf('.') > 0 && string.indexOf('e') < 0 && string
/*  547 */       .indexOf('E') < 0) {
/*  548 */       while (string.endsWith("0")) {
/*  549 */         string = string.substring(0, string.length() - 1);
/*      */       }
/*  551 */       if (string.endsWith(".")) {
/*  552 */         string = string.substring(0, string.length() - 1);
/*      */       }
/*      */     } 
/*  555 */     return string;
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
/*      */   public Object get(String key) throws JSONException {
/*  568 */     if (key == null) {
/*  569 */       throw new JSONException("Null key.");
/*      */     }
/*  571 */     Object object = opt(key);
/*  572 */     if (object == null) {
/*  573 */       throw new JSONException("JSONObject[" + quote(key) + "] not found.");
/*      */     }
/*  575 */     return object;
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
/*      */   public <E extends Enum<E>> E getEnum(Class<E> clazz, String key) throws JSONException {
/*  593 */     E val = optEnum(clazz, key);
/*  594 */     if (val == null)
/*      */     {
/*      */ 
/*      */       
/*  598 */       throw new JSONException("JSONObject[" + quote(key) + "] is not an enum of type " + 
/*  599 */           quote(clazz.getSimpleName()) + ".");
/*      */     }
/*      */     
/*  602 */     return val;
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
/*      */   public boolean getBoolean(String key) throws JSONException {
/*  616 */     Object object = get(key);
/*  617 */     if (object.equals(Boolean.FALSE) || (object instanceof String && ((String)object)
/*      */       
/*  619 */       .equalsIgnoreCase("false")))
/*  620 */       return false; 
/*  621 */     if (object.equals(Boolean.TRUE) || (object instanceof String && ((String)object)
/*      */       
/*  623 */       .equalsIgnoreCase("true"))) {
/*  624 */       return true;
/*      */     }
/*  626 */     throw new JSONException("JSONObject[" + quote(key) + "] is not a Boolean.");
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
/*      */   public BigInteger getBigInteger(String key) throws JSONException {
/*  641 */     Object object = get(key);
/*  642 */     BigInteger ret = objectToBigInteger(object, null);
/*  643 */     if (ret != null) {
/*  644 */       return ret;
/*      */     }
/*  646 */     throw new JSONException("JSONObject[" + quote(key) + "] could not be converted to BigInteger (" + object + ").");
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
/*      */   public BigDecimal getBigDecimal(String key) throws JSONException {
/*  664 */     Object object = get(key);
/*  665 */     BigDecimal ret = objectToBigDecimal(object, null);
/*  666 */     if (ret != null) {
/*  667 */       return ret;
/*      */     }
/*  669 */     throw new JSONException("JSONObject[" + quote(key) + "] could not be converted to BigDecimal (" + object + ").");
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
/*      */   public double getDouble(String key) throws JSONException {
/*  684 */     return getNumber(key).doubleValue();
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
/*      */   public float getFloat(String key) throws JSONException {
/*  698 */     return getNumber(key).floatValue();
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
/*      */   public Number getNumber(String key) throws JSONException {
/*  712 */     Object object = get(key);
/*      */     try {
/*  714 */       if (object instanceof Number) {
/*  715 */         return (Number)object;
/*      */       }
/*  717 */       return stringToNumber(object.toString());
/*  718 */     } catch (Exception e) {
/*  719 */       throw new JSONException("JSONObject[" + quote(key) + "] is not a number.", e);
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
/*      */ 
/*      */   
/*      */   public int getInt(String key) throws JSONException {
/*  735 */     return getNumber(key).intValue();
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
/*      */   public JSONArray getJSONArray(String key) throws JSONException {
/*  748 */     Object object = get(key);
/*  749 */     if (object instanceof JSONArray) {
/*  750 */       return (JSONArray)object;
/*      */     }
/*  752 */     throw new JSONException("JSONObject[" + quote(key) + "] is not a JSONArray.");
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
/*      */   public JSONObject getJSONObject(String key) throws JSONException {
/*  766 */     Object object = get(key);
/*  767 */     if (object instanceof JSONObject) {
/*  768 */       return (JSONObject)object;
/*      */     }
/*  770 */     throw new JSONException("JSONObject[" + quote(key) + "] is not a JSONObject.");
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
/*      */   public long getLong(String key) throws JSONException {
/*  785 */     return getNumber(key).longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String[] getNames(JSONObject jo) {
/*  796 */     if (jo.isEmpty()) {
/*  797 */       return null;
/*      */     }
/*  799 */     return jo.keySet().<String>toArray(new String[jo.length()]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String[] getNames(Object object) {
/*  810 */     if (object == null) {
/*  811 */       return null;
/*      */     }
/*  813 */     Class<?> klass = object.getClass();
/*  814 */     Field[] fields = klass.getFields();
/*  815 */     int length = fields.length;
/*  816 */     if (length == 0) {
/*  817 */       return null;
/*      */     }
/*  819 */     String[] names = new String[length];
/*  820 */     for (int i = 0; i < length; i++) {
/*  821 */       names[i] = fields[i].getName();
/*      */     }
/*  823 */     return names;
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
/*      */   public String getString(String key) throws JSONException {
/*  836 */     Object object = get(key);
/*  837 */     if (object instanceof String) {
/*  838 */       return (String)object;
/*      */     }
/*  840 */     throw new JSONException("JSONObject[" + quote(key) + "] not a string.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean has(String key) {
/*  851 */     return this.map.containsKey(key);
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
/*      */   public JSONObject increment(String key) throws JSONException {
/*  867 */     Object value = opt(key);
/*  868 */     if (value == null) {
/*  869 */       put(key, 1);
/*  870 */     } else if (value instanceof BigInteger) {
/*  871 */       put(key, ((BigInteger)value).add(BigInteger.ONE));
/*  872 */     } else if (value instanceof BigDecimal) {
/*  873 */       put(key, ((BigDecimal)value).add(BigDecimal.ONE));
/*  874 */     } else if (value instanceof Integer) {
/*  875 */       put(key, ((Integer)value).intValue() + 1);
/*  876 */     } else if (value instanceof Long) {
/*  877 */       put(key, ((Long)value).longValue() + 1L);
/*  878 */     } else if (value instanceof Double) {
/*  879 */       put(key, ((Double)value).doubleValue() + 1.0D);
/*  880 */     } else if (value instanceof Float) {
/*  881 */       put(key, ((Float)value).floatValue() + 1.0F);
/*      */     } else {
/*  883 */       throw new JSONException("Unable to increment [" + quote(key) + "].");
/*      */     } 
/*  885 */     return this;
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
/*      */   public boolean isNull(String key) {
/*  898 */     return NULL.equals(opt(key));
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
/*      */   public Iterator<String> keys() {
/*  910 */     return keySet().iterator();
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
/*      */   public Set<String> keySet() {
/*  922 */     return this.map.keySet();
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
/*      */   protected Set<Map.Entry<String, Object>> entrySet() {
/*  938 */     return this.map.entrySet();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int length() {
/*  947 */     return this.map.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  956 */     return this.map.isEmpty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray names() {
/*  967 */     if (this.map.isEmpty()) {
/*  968 */       return null;
/*      */     }
/*  970 */     return new JSONArray(this.map.keySet());
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
/*      */   public static String numberToString(Number number) throws JSONException {
/*  983 */     if (number == null) {
/*  984 */       throw new JSONException("Null pointer");
/*      */     }
/*  986 */     testValidity(number);
/*      */ 
/*      */ 
/*      */     
/*  990 */     String string = number.toString();
/*  991 */     if (string.indexOf('.') > 0 && string.indexOf('e') < 0 && string
/*  992 */       .indexOf('E') < 0) {
/*  993 */       while (string.endsWith("0")) {
/*  994 */         string = string.substring(0, string.length() - 1);
/*      */       }
/*  996 */       if (string.endsWith(".")) {
/*  997 */         string = string.substring(0, string.length() - 1);
/*      */       }
/*      */     } 
/* 1000 */     return string;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object opt(String key) {
/* 1011 */     return (key == null) ? null : this.map.get(key);
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
/*      */   public <E extends Enum<E>> E optEnum(Class<E> clazz, String key) {
/* 1026 */     return optEnum(clazz, key, null);
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
/*      */   public <E extends Enum<E>> E optEnum(Class<E> clazz, String key, E defaultValue) {
/*      */     try {
/* 1045 */       Object val = opt(key);
/* 1046 */       if (NULL.equals(val)) {
/* 1047 */         return defaultValue;
/*      */       }
/* 1049 */       if (clazz.isAssignableFrom(val.getClass()))
/*      */       {
/*      */         
/* 1052 */         return (E)val;
/*      */       }
/*      */       
/* 1055 */       return Enum.valueOf(clazz, val.toString());
/* 1056 */     } catch (IllegalArgumentException e) {
/* 1057 */       return defaultValue;
/* 1058 */     } catch (NullPointerException e) {
/* 1059 */       return defaultValue;
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
/*      */   public boolean optBoolean(String key) {
/* 1072 */     return optBoolean(key, false);
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
/*      */   public boolean optBoolean(String key, boolean defaultValue) {
/* 1087 */     Object val = opt(key);
/* 1088 */     if (NULL.equals(val)) {
/* 1089 */       return defaultValue;
/*      */     }
/* 1091 */     if (val instanceof Boolean) {
/* 1092 */       return ((Boolean)val).booleanValue();
/*      */     }
/*      */     
/*      */     try {
/* 1096 */       return getBoolean(key);
/* 1097 */     } catch (Exception e) {
/* 1098 */       return defaultValue;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal optBigDecimal(String key, BigDecimal defaultValue) {
/* 1117 */     Object val = opt(key);
/* 1118 */     return objectToBigDecimal(val, defaultValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static BigDecimal objectToBigDecimal(Object val, BigDecimal defaultValue) {
/* 1128 */     if (NULL.equals(val)) {
/* 1129 */       return defaultValue;
/*      */     }
/* 1131 */     if (val instanceof BigDecimal) {
/* 1132 */       return (BigDecimal)val;
/*      */     }
/* 1134 */     if (val instanceof BigInteger) {
/* 1135 */       return new BigDecimal((BigInteger)val);
/*      */     }
/* 1137 */     if (val instanceof Double || val instanceof Float) {
/* 1138 */       double d = ((Number)val).doubleValue();
/* 1139 */       if (Double.isNaN(d)) {
/* 1140 */         return defaultValue;
/*      */       }
/* 1142 */       return new BigDecimal(((Number)val).doubleValue());
/*      */     } 
/* 1144 */     if (val instanceof Long || val instanceof Integer || val instanceof Short || val instanceof Byte)
/*      */     {
/* 1146 */       return new BigDecimal(((Number)val).longValue());
/*      */     }
/*      */     
/*      */     try {
/* 1150 */       return new BigDecimal(val.toString());
/* 1151 */     } catch (Exception e) {
/* 1152 */       return defaultValue;
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
/*      */ 
/*      */   
/*      */   public BigInteger optBigInteger(String key, BigInteger defaultValue) {
/* 1168 */     Object val = opt(key);
/* 1169 */     return objectToBigInteger(val, defaultValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static BigInteger objectToBigInteger(Object val, BigInteger defaultValue) {
/* 1179 */     if (NULL.equals(val)) {
/* 1180 */       return defaultValue;
/*      */     }
/* 1182 */     if (val instanceof BigInteger) {
/* 1183 */       return (BigInteger)val;
/*      */     }
/* 1185 */     if (val instanceof BigDecimal) {
/* 1186 */       return ((BigDecimal)val).toBigInteger();
/*      */     }
/* 1188 */     if (val instanceof Double || val instanceof Float) {
/* 1189 */       double d = ((Number)val).doubleValue();
/* 1190 */       if (Double.isNaN(d)) {
/* 1191 */         return defaultValue;
/*      */       }
/* 1193 */       return (new BigDecimal(d)).toBigInteger();
/*      */     } 
/* 1195 */     if (val instanceof Long || val instanceof Integer || val instanceof Short || val instanceof Byte)
/*      */     {
/* 1197 */       return BigInteger.valueOf(((Number)val).longValue());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1206 */       String valStr = val.toString();
/* 1207 */       if (isDecimalNotation(valStr)) {
/* 1208 */         return (new BigDecimal(valStr)).toBigInteger();
/*      */       }
/* 1210 */       return new BigInteger(valStr);
/* 1211 */     } catch (Exception e) {
/* 1212 */       return defaultValue;
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
/*      */   public double optDouble(String key) {
/* 1226 */     return optDouble(key, Double.NaN);
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
/*      */   public double optDouble(String key, double defaultValue) {
/* 1241 */     Number val = optNumber(key);
/* 1242 */     if (val == null) {
/* 1243 */       return defaultValue;
/*      */     }
/* 1245 */     double doubleValue = val.doubleValue();
/*      */ 
/*      */ 
/*      */     
/* 1249 */     return doubleValue;
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
/*      */   public float optFloat(String key) {
/* 1262 */     return optFloat(key, Float.NaN);
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
/*      */   public float optFloat(String key, float defaultValue) {
/* 1277 */     Number val = optNumber(key);
/* 1278 */     if (val == null) {
/* 1279 */       return defaultValue;
/*      */     }
/* 1281 */     float floatValue = val.floatValue();
/*      */ 
/*      */ 
/*      */     
/* 1285 */     return floatValue;
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
/*      */   public int optInt(String key) {
/* 1298 */     return optInt(key, 0);
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
/*      */   public int optInt(String key, int defaultValue) {
/* 1313 */     Number val = optNumber(key, null);
/* 1314 */     if (val == null) {
/* 1315 */       return defaultValue;
/*      */     }
/* 1317 */     return val.intValue();
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
/*      */   public JSONArray optJSONArray(String key) {
/* 1329 */     Object o = opt(key);
/* 1330 */     return (o instanceof JSONArray) ? (JSONArray)o : null;
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
/*      */   public JSONObject optJSONObject(String key) {
/* 1342 */     Object object = opt(key);
/* 1343 */     return (object instanceof JSONObject) ? (JSONObject)object : null;
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
/*      */   public long optLong(String key) {
/* 1356 */     return optLong(key, 0L);
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
/*      */   public long optLong(String key, long defaultValue) {
/* 1371 */     Number val = optNumber(key, null);
/* 1372 */     if (val == null) {
/* 1373 */       return defaultValue;
/*      */     }
/*      */     
/* 1376 */     return val.longValue();
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
/*      */   public Number optNumber(String key) {
/* 1390 */     return optNumber(key, null);
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
/*      */   public Number optNumber(String key, Number defaultValue) {
/* 1406 */     Object val = opt(key);
/* 1407 */     if (NULL.equals(val)) {
/* 1408 */       return defaultValue;
/*      */     }
/* 1410 */     if (val instanceof Number) {
/* 1411 */       return (Number)val;
/*      */     }
/*      */     
/*      */     try {
/* 1415 */       return stringToNumber(val.toString());
/* 1416 */     } catch (Exception e) {
/* 1417 */       return defaultValue;
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
/*      */   public String optString(String key) {
/* 1431 */     return optString(key, "");
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
/*      */   public String optString(String key, String defaultValue) {
/* 1445 */     Object object = opt(key);
/* 1446 */     return NULL.equals(object) ? defaultValue : object.toString();
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
/*      */   private void populateMap(Object bean) {
/* 1459 */     Class<?> klass = bean.getClass();
/*      */ 
/*      */ 
/*      */     
/* 1463 */     boolean includeSuperClass = (klass.getClassLoader() != null);
/*      */     
/* 1465 */     Method[] methods = includeSuperClass ? klass.getMethods() : klass.getDeclaredMethods();
/* 1466 */     for (Method method : methods) {
/* 1467 */       int modifiers = method.getModifiers();
/* 1468 */       if (Modifier.isPublic(modifiers) && 
/* 1469 */         !Modifier.isStatic(modifiers) && (method
/* 1470 */         .getParameterTypes()).length == 0 && 
/* 1471 */         !method.isBridge() && method
/* 1472 */         .getReturnType() != void.class && 
/* 1473 */         isValidMethodName(method.getName())) {
/* 1474 */         String key = getKeyNameFromMethod(method);
/* 1475 */         if (key != null && !key.isEmpty()) {
/*      */           
/* 1477 */           try { Object result = method.invoke(bean, new Object[0]);
/* 1478 */             if (result != null) {
/* 1479 */               this.map.put(key, wrap(result));
/*      */ 
/*      */ 
/*      */               
/* 1483 */               if (result instanceof Closeable) {
/*      */                 try {
/* 1485 */                   ((Closeable)result).close();
/* 1486 */                 } catch (IOException iOException) {}
/*      */               }
/*      */             }
/*      */              }
/* 1490 */           catch (IllegalAccessException illegalAccessException) {  }
/* 1491 */           catch (IllegalArgumentException illegalArgumentException) {  }
/* 1492 */           catch (InvocationTargetException invocationTargetException) {}
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isValidMethodName(String name) {
/* 1500 */     return (!"getClass".equals(name) && !"getDeclaringClass".equals(name));
/*      */   }
/*      */   private String getKeyNameFromMethod(Method method) {
/*      */     String key;
/* 1504 */     int ignoreDepth = getAnnotationDepth(method, (Class)JSONPropertyIgnore.class);
/* 1505 */     if (ignoreDepth > 0) {
/* 1506 */       int forcedNameDepth = getAnnotationDepth(method, (Class)JSONPropertyName.class);
/* 1507 */       if (forcedNameDepth < 0 || ignoreDepth <= forcedNameDepth)
/*      */       {
/*      */         
/* 1510 */         return null;
/*      */       }
/*      */     } 
/* 1513 */     JSONPropertyName annotation = getAnnotation(method, JSONPropertyName.class);
/* 1514 */     if (annotation != null && annotation.value() != null && !annotation.value().isEmpty()) {
/* 1515 */       return annotation.value();
/*      */     }
/*      */     
/* 1518 */     String name = method.getName();
/* 1519 */     if (name.startsWith("get") && name.length() > 3) {
/* 1520 */       key = name.substring(3);
/* 1521 */     } else if (name.startsWith("is") && name.length() > 2) {
/* 1522 */       key = name.substring(2);
/*      */     } else {
/* 1524 */       return null;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1529 */     if (Character.isLowerCase(key.charAt(0))) {
/* 1530 */       return null;
/*      */     }
/* 1532 */     if (key.length() == 1) {
/* 1533 */       key = key.toLowerCase(Locale.ROOT);
/* 1534 */     } else if (!Character.isUpperCase(key.charAt(1))) {
/* 1535 */       key = key.substring(0, 1).toLowerCase(Locale.ROOT) + key.substring(1);
/*      */     } 
/* 1537 */     return key;
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
/*      */   private static <A extends Annotation> A getAnnotation(Method m, Class<A> annotationClass) {
/* 1556 */     if (m == null || annotationClass == null) {
/* 1557 */       return null;
/*      */     }
/*      */     
/* 1560 */     if (m.isAnnotationPresent(annotationClass)) {
/* 1561 */       return m.getAnnotation(annotationClass);
/*      */     }
/*      */ 
/*      */     
/* 1565 */     Class<?> c = m.getDeclaringClass();
/* 1566 */     if (c.getSuperclass() == null) {
/* 1567 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1571 */     for (Class<?> i : c.getInterfaces()) {
/*      */       try {
/* 1573 */         Method im = i.getMethod(m.getName(), m.getParameterTypes());
/* 1574 */         return getAnnotation(im, annotationClass);
/* 1575 */       } catch (SecurityException ex) {
/*      */       
/* 1577 */       } catch (NoSuchMethodException ex) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1583 */       return getAnnotation(c
/* 1584 */           .getSuperclass().getMethod(m.getName(), m.getParameterTypes()), annotationClass);
/*      */     }
/* 1586 */     catch (SecurityException ex) {
/* 1587 */       return null;
/* 1588 */     } catch (NoSuchMethodException ex) {
/* 1589 */       return null;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getAnnotationDepth(Method m, Class<? extends Annotation> annotationClass) {
/* 1609 */     if (m == null || annotationClass == null) {
/* 1610 */       return -1;
/*      */     }
/*      */     
/* 1613 */     if (m.isAnnotationPresent(annotationClass)) {
/* 1614 */       return 1;
/*      */     }
/*      */ 
/*      */     
/* 1618 */     Class<?> c = m.getDeclaringClass();
/* 1619 */     if (c.getSuperclass() == null) {
/* 1620 */       return -1;
/*      */     }
/*      */ 
/*      */     
/* 1624 */     for (Class<?> i : c.getInterfaces()) {
/*      */       try {
/* 1626 */         Method im = i.getMethod(m.getName(), m.getParameterTypes());
/* 1627 */         int d = getAnnotationDepth(im, annotationClass);
/* 1628 */         if (d > 0)
/*      */         {
/* 1630 */           return d + 1;
/*      */         }
/* 1632 */       } catch (SecurityException ex) {
/*      */       
/* 1634 */       } catch (NoSuchMethodException ex) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1640 */       int d = getAnnotationDepth(c
/* 1641 */           .getSuperclass().getMethod(m.getName(), m.getParameterTypes()), annotationClass);
/*      */       
/* 1643 */       if (d > 0)
/*      */       {
/* 1645 */         return d + 1;
/*      */       }
/* 1647 */       return -1;
/* 1648 */     } catch (SecurityException ex) {
/* 1649 */       return -1;
/* 1650 */     } catch (NoSuchMethodException ex) {
/* 1651 */       return -1;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject put(String key, boolean value) throws JSONException {
/* 1669 */     return put(key, value ? Boolean.TRUE : Boolean.FALSE);
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
/*      */   public JSONObject put(String key, Collection<?> value) throws JSONException {
/* 1687 */     return put(key, new JSONArray(value));
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
/*      */   public JSONObject put(String key, double value) throws JSONException {
/* 1704 */     return put(key, Double.valueOf(value));
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
/*      */   public JSONObject put(String key, float value) throws JSONException {
/* 1721 */     return put(key, Float.valueOf(value));
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
/*      */   public JSONObject put(String key, int value) throws JSONException {
/* 1738 */     return put(key, Integer.valueOf(value));
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
/*      */   public JSONObject put(String key, long value) throws JSONException {
/* 1755 */     return put(key, Long.valueOf(value));
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
/*      */   public JSONObject put(String key, Map<?, ?> value) throws JSONException {
/* 1773 */     return put(key, new JSONObject(value));
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
/*      */   public JSONObject put(String key, Object value) throws JSONException {
/* 1793 */     if (key == null) {
/* 1794 */       throw new NullPointerException("Null key.");
/*      */     }
/* 1796 */     if (value != null) {
/* 1797 */       testValidity(value);
/* 1798 */       this.map.put(key, value);
/*      */     } else {
/* 1800 */       remove(key);
/*      */     } 
/* 1802 */     return this;
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
/*      */   public JSONObject putOnce(String key, Object value) throws JSONException {
/* 1819 */     if (key != null && value != null) {
/* 1820 */       if (opt(key) != null) {
/* 1821 */         throw new JSONException("Duplicate key \"" + key + "\"");
/*      */       }
/* 1823 */       return put(key, value);
/*      */     } 
/* 1825 */     return this;
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
/*      */   public JSONObject putOpt(String key, Object value) throws JSONException {
/* 1843 */     if (key != null && value != null) {
/* 1844 */       return put(key, value);
/*      */     }
/* 1846 */     return this;
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
/*      */   public Object query(String jsonPointer) {
/* 1869 */     return query(new JSONPointer(jsonPointer));
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
/*      */   public Object query(JSONPointer jsonPointer) {
/* 1891 */     return jsonPointer.queryFrom(this);
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
/*      */   public Object optQuery(String jsonPointer) {
/* 1903 */     return optQuery(new JSONPointer(jsonPointer));
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
/*      */   public Object optQuery(JSONPointer jsonPointer) {
/*      */     try {
/* 1916 */       return jsonPointer.queryFrom(this);
/* 1917 */     } catch (JSONPointerException e) {
/* 1918 */       return null;
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
/*      */ 
/*      */   
/*      */   public static String quote(String string) {
/* 1934 */     StringWriter sw = new StringWriter();
/* 1935 */     synchronized (sw.getBuffer()) {
/*      */       
/* 1937 */       return quote(string, sw).toString();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Writer quote(String string, Writer w) throws IOException {
/* 1946 */     if (string == null || string.isEmpty()) {
/* 1947 */       w.write("\"\"");
/* 1948 */       return w;
/*      */     } 
/*      */ 
/*      */     
/* 1952 */     char c = Character.MIN_VALUE;
/*      */ 
/*      */     
/* 1955 */     int len = string.length();
/*      */     
/* 1957 */     w.write(34);
/* 1958 */     for (int i = 0; i < len; i++) {
/* 1959 */       char b = c;
/* 1960 */       c = string.charAt(i);
/* 1961 */       switch (c) {
/*      */         case '"':
/*      */         case '\\':
/* 1964 */           w.write(92);
/* 1965 */           w.write(c);
/*      */           break;
/*      */         case '/':
/* 1968 */           if (b == '<') {
/* 1969 */             w.write(92);
/*      */           }
/* 1971 */           w.write(c);
/*      */           break;
/*      */         case '\b':
/* 1974 */           w.write("\\b");
/*      */           break;
/*      */         case '\t':
/* 1977 */           w.write("\\t");
/*      */           break;
/*      */         case '\n':
/* 1980 */           w.write("\\n");
/*      */           break;
/*      */         case '\f':
/* 1983 */           w.write("\\f");
/*      */           break;
/*      */         case '\r':
/* 1986 */           w.write("\\r");
/*      */           break;
/*      */         default:
/* 1989 */           if (c < ' ' || (c >= '' && c < ' ') || (c >= ' ' && c < '℀')) {
/*      */             
/* 1991 */             w.write("\\u");
/* 1992 */             String hhhh = Integer.toHexString(c);
/* 1993 */             w.write("0000", 0, 4 - hhhh.length());
/* 1994 */             w.write(hhhh); break;
/*      */           } 
/* 1996 */           w.write(c);
/*      */           break;
/*      */       } 
/*      */     } 
/* 2000 */     w.write(34);
/* 2001 */     return w;
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
/*      */   public Object remove(String key) {
/* 2013 */     return this.map.remove(key);
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
/*      */   public boolean similar(Object other) {
/*      */     try {
/* 2026 */       if (!(other instanceof JSONObject)) {
/* 2027 */         return false;
/*      */       }
/* 2029 */       if (!keySet().equals(((JSONObject)other).keySet())) {
/* 2030 */         return false;
/*      */       }
/* 2032 */       for (Map.Entry<String, ?> entry : entrySet()) {
/* 2033 */         String name = entry.getKey();
/* 2034 */         Object valueThis = entry.getValue();
/* 2035 */         Object valueOther = ((JSONObject)other).get(name);
/* 2036 */         if (valueThis == valueOther) {
/*      */           continue;
/*      */         }
/* 2039 */         if (valueThis == null) {
/* 2040 */           return false;
/*      */         }
/* 2042 */         if (valueThis instanceof JSONObject) {
/* 2043 */           if (!((JSONObject)valueThis).similar(valueOther))
/* 2044 */             return false;  continue;
/*      */         } 
/* 2046 */         if (valueThis instanceof JSONArray) {
/* 2047 */           if (!((JSONArray)valueThis).similar(valueOther))
/* 2048 */             return false;  continue;
/*      */         } 
/* 2050 */         if (!valueThis.equals(valueOther)) {
/* 2051 */           return false;
/*      */         }
/*      */       } 
/* 2054 */       return true;
/* 2055 */     } catch (Throwable exception) {
/* 2056 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static boolean isDecimalNotation(String val) {
/* 2067 */     return (val.indexOf('.') > -1 || val.indexOf('e') > -1 || val
/* 2068 */       .indexOf('E') > -1 || "-0".equals(val));
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
/*      */   protected static Number stringToNumber(String val) throws NumberFormatException {
/* 2082 */     char initial = val.charAt(0);
/* 2083 */     if ((initial >= '0' && initial <= '9') || initial == '-') {
/*      */       
/* 2085 */       if (isDecimalNotation(val)) {
/*      */ 
/*      */         
/* 2088 */         if (val.length() > 14) {
/* 2089 */           return new BigDecimal(val);
/*      */         }
/* 2091 */         Double d = Double.valueOf(val);
/* 2092 */         if (d.isInfinite() || d.isNaN())
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 2097 */           return new BigDecimal(val);
/*      */         }
/* 2099 */         return d;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2123 */       BigInteger bi = new BigInteger(val);
/* 2124 */       if (bi.bitLength() <= 31) {
/* 2125 */         return Integer.valueOf(bi.intValue());
/*      */       }
/* 2127 */       if (bi.bitLength() <= 63) {
/* 2128 */         return Long.valueOf(bi.longValue());
/*      */       }
/* 2130 */       return bi;
/*      */     } 
/* 2132 */     throw new NumberFormatException("val [" + val + "] is not a valid number.");
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
/*      */   public static Object stringToValue(String string) {
/* 2148 */     if ("".equals(string)) {
/* 2149 */       return string;
/*      */     }
/*      */ 
/*      */     
/* 2153 */     if ("true".equalsIgnoreCase(string)) {
/* 2154 */       return Boolean.TRUE;
/*      */     }
/* 2156 */     if ("false".equalsIgnoreCase(string)) {
/* 2157 */       return Boolean.FALSE;
/*      */     }
/* 2159 */     if ("null".equalsIgnoreCase(string)) {
/* 2160 */       return NULL;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2168 */     char initial = string.charAt(0);
/* 2169 */     if ((initial >= '0' && initial <= '9') || initial == '-') {
/*      */       
/*      */       try {
/*      */ 
/*      */         
/* 2174 */         if (isDecimalNotation(string)) {
/* 2175 */           Double d = Double.valueOf(string);
/* 2176 */           if (!d.isInfinite() && !d.isNaN()) {
/* 2177 */             return d;
/*      */           }
/*      */         } else {
/* 2180 */           Long myLong = Long.valueOf(string);
/* 2181 */           if (string.equals(myLong.toString())) {
/* 2182 */             if (myLong.longValue() == myLong.intValue()) {
/* 2183 */               return Integer.valueOf(myLong.intValue());
/*      */             }
/* 2185 */             return myLong;
/*      */           } 
/*      */         } 
/* 2188 */       } catch (Exception exception) {}
/*      */     }
/*      */     
/* 2191 */     return string;
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
/*      */   public static void testValidity(Object o) throws JSONException {
/* 2203 */     if (o != null) {
/* 2204 */       if (o instanceof Double) {
/* 2205 */         if (((Double)o).isInfinite() || ((Double)o).isNaN()) {
/* 2206 */           throw new JSONException("JSON does not allow non-finite numbers.");
/*      */         }
/*      */       }
/* 2209 */       else if (o instanceof Float && ((
/* 2210 */         (Float)o).isInfinite() || ((Float)o).isNaN())) {
/* 2211 */         throw new JSONException("JSON does not allow non-finite numbers.");
/*      */       } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray toJSONArray(JSONArray names) throws JSONException {
/* 2230 */     if (names == null || names.isEmpty()) {
/* 2231 */       return null;
/*      */     }
/* 2233 */     JSONArray ja = new JSONArray();
/* 2234 */     for (int i = 0; i < names.length(); i++) {
/* 2235 */       ja.put(opt(names.getString(i)));
/*      */     }
/* 2237 */     return ja;
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
/*      */   public String toString() {
/*      */     try {
/* 2256 */       return toString(0);
/* 2257 */     } catch (Exception e) {
/* 2258 */       return null;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString(int indentFactor) throws JSONException {
/* 2289 */     StringWriter w = new StringWriter();
/* 2290 */     synchronized (w.getBuffer()) {
/* 2291 */       return write(w, indentFactor, 0).toString();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String valueToString(Object value) throws JSONException {
/* 2324 */     return JSONWriter.valueToString(value);
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
/*      */   public static Object wrap(Object object) {
/*      */     try {
/* 2341 */       if (object == null) {
/* 2342 */         return NULL;
/*      */       }
/* 2344 */       if (object instanceof JSONObject || object instanceof JSONArray || NULL
/* 2345 */         .equals(object) || object instanceof JSONString || object instanceof Byte || object instanceof Character || object instanceof Short || object instanceof Integer || object instanceof Long || object instanceof Boolean || object instanceof Float || object instanceof Double || object instanceof String || object instanceof BigInteger || object instanceof BigDecimal || object instanceof Enum)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2352 */         return object;
/*      */       }
/*      */       
/* 2355 */       if (object instanceof Collection) {
/* 2356 */         Collection<?> coll = (Collection)object;
/* 2357 */         return new JSONArray(coll);
/*      */       } 
/* 2359 */       if (object.getClass().isArray()) {
/* 2360 */         return new JSONArray(object);
/*      */       }
/* 2362 */       if (object instanceof Map) {
/* 2363 */         Map<?, ?> map = (Map<?, ?>)object;
/* 2364 */         return new JSONObject(map);
/*      */       } 
/* 2366 */       Package objectPackage = object.getClass().getPackage();
/*      */       
/* 2368 */       String objectPackageName = (objectPackage != null) ? objectPackage.getName() : "";
/* 2369 */       if (objectPackageName.startsWith("java.") || objectPackageName
/* 2370 */         .startsWith("javax.") || object
/* 2371 */         .getClass().getClassLoader() == null) {
/* 2372 */         return object.toString();
/*      */       }
/* 2374 */       return new JSONObject(object);
/* 2375 */     } catch (Exception exception) {
/* 2376 */       return null;
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
/*      */   
/*      */   public Writer write(Writer writer) throws JSONException {
/* 2391 */     return write(writer, 0, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   static final Writer writeValue(Writer writer, Object value, int indentFactor, int indent) throws JSONException, IOException {
/* 2396 */     if (value == null || value.equals(null)) {
/* 2397 */       writer.write("null");
/* 2398 */     } else if (value instanceof JSONString) {
/*      */       Object o;
/*      */       try {
/* 2401 */         o = ((JSONString)value).toJSONString();
/* 2402 */       } catch (Exception e) {
/* 2403 */         throw new JSONException(e);
/*      */       } 
/* 2405 */       writer.write((o != null) ? o.toString() : quote(value.toString()));
/* 2406 */     } else if (value instanceof Number) {
/*      */       
/* 2408 */       String numberAsString = numberToString((Number)value);
/* 2409 */       if (NUMBER_PATTERN.matcher(numberAsString).matches()) {
/* 2410 */         writer.write(numberAsString);
/*      */       }
/*      */       else {
/*      */         
/* 2414 */         quote(numberAsString, writer);
/*      */       } 
/* 2416 */     } else if (value instanceof Boolean) {
/* 2417 */       writer.write(value.toString());
/* 2418 */     } else if (value instanceof Enum) {
/* 2419 */       writer.write(quote(((Enum)value).name()));
/* 2420 */     } else if (value instanceof JSONObject) {
/* 2421 */       ((JSONObject)value).write(writer, indentFactor, indent);
/* 2422 */     } else if (value instanceof JSONArray) {
/* 2423 */       ((JSONArray)value).write(writer, indentFactor, indent);
/* 2424 */     } else if (value instanceof Map) {
/* 2425 */       Map<?, ?> map = (Map<?, ?>)value;
/* 2426 */       (new JSONObject(map)).write(writer, indentFactor, indent);
/* 2427 */     } else if (value instanceof Collection) {
/* 2428 */       Collection<?> coll = (Collection)value;
/* 2429 */       (new JSONArray(coll)).write(writer, indentFactor, indent);
/* 2430 */     } else if (value.getClass().isArray()) {
/* 2431 */       (new JSONArray(value)).write(writer, indentFactor, indent);
/*      */     } else {
/* 2433 */       quote(value.toString(), writer);
/*      */     } 
/* 2435 */     return writer;
/*      */   }
/*      */   
/*      */   static final void indent(Writer writer, int indent) throws IOException {
/* 2439 */     for (int i = 0; i < indent; i++) {
/* 2440 */       writer.write(32);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Writer write(Writer writer, int indentFactor, int indent) throws JSONException {
/*      */     try {
/* 2473 */       boolean commanate = false;
/* 2474 */       int length = length();
/* 2475 */       writer.write(123);
/*      */       
/* 2477 */       if (length == 1) {
/* 2478 */         Map.Entry<String, ?> entry = entrySet().iterator().next();
/* 2479 */         String key = entry.getKey();
/* 2480 */         writer.write(quote(key));
/* 2481 */         writer.write(58);
/* 2482 */         if (indentFactor > 0) {
/* 2483 */           writer.write(32);
/*      */         }
/*      */         try {
/* 2486 */           writeValue(writer, entry.getValue(), indentFactor, indent);
/* 2487 */         } catch (Exception e) {
/* 2488 */           throw new JSONException("Unable to write JSONObject value for key: " + key, e);
/*      */         } 
/* 2490 */       } else if (length != 0) {
/* 2491 */         int newindent = indent + indentFactor;
/* 2492 */         for (Map.Entry<String, ?> entry : entrySet()) {
/* 2493 */           if (commanate) {
/* 2494 */             writer.write(44);
/*      */           }
/* 2496 */           if (indentFactor > 0) {
/* 2497 */             writer.write(10);
/*      */           }
/* 2499 */           indent(writer, newindent);
/* 2500 */           String key = entry.getKey();
/* 2501 */           writer.write(quote(key));
/* 2502 */           writer.write(58);
/* 2503 */           if (indentFactor > 0) {
/* 2504 */             writer.write(32);
/*      */           }
/*      */           try {
/* 2507 */             writeValue(writer, entry.getValue(), indentFactor, newindent);
/* 2508 */           } catch (Exception e) {
/* 2509 */             throw new JSONException("Unable to write JSONObject value for key: " + key, e);
/*      */           } 
/* 2511 */           commanate = true;
/*      */         } 
/* 2513 */         if (indentFactor > 0) {
/* 2514 */           writer.write(10);
/*      */         }
/* 2516 */         indent(writer, indent);
/*      */       } 
/* 2518 */       writer.write(125);
/* 2519 */       return writer;
/* 2520 */     } catch (IOException exception) {
/* 2521 */       throw new JSONException(exception);
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
/*      */   public Map<String, Object> toMap() {
/* 2535 */     Map<String, Object> results = new HashMap<String, Object>();
/* 2536 */     for (Map.Entry<String, Object> entry : entrySet()) {
/*      */       Object value;
/* 2538 */       if (entry.getValue() == null || NULL.equals(entry.getValue())) {
/* 2539 */         value = null;
/* 2540 */       } else if (entry.getValue() instanceof JSONObject) {
/* 2541 */         value = ((JSONObject)entry.getValue()).toMap();
/* 2542 */       } else if (entry.getValue() instanceof JSONArray) {
/* 2543 */         value = ((JSONArray)entry.getValue()).toList();
/*      */       } else {
/* 2545 */         value = entry.getValue();
/*      */       } 
/* 2547 */       results.put(entry.getKey(), value);
/*      */     } 
/* 2549 */     return results;
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/json/JSONObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */