/*     */ package edu.mines.jtk.util;
/*     */ 
/*     */ import java.util.Formatter;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.logging.Logger;
/*     */ import java.util.regex.MatchResult;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Localize
/*     */ {
/*  50 */   private static final Logger LOG = Logger.getLogger(Localize.class.getName());
/*  51 */   private static final Pattern s_tokens = Pattern.compile("[$][{](.+?)[}]");
/*     */   
/*  53 */   private static final Map<String, ResourceBundle> s_rb = new HashMap<>();
/*     */ 
/*     */   
/*     */   private final ResourceBundle resourceBundle;
/*     */ 
/*     */   
/*     */   private final Locale locale;
/*     */ 
/*     */   
/*     */   private final Class<?> clazz;
/*     */   
/*     */   private final String resourceBundleName;
/*     */ 
/*     */   
/*     */   public Localize(Class<?> clazz) {
/*  68 */     this(clazz, null);
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
/*     */   public Localize(Class<?> clazz, String resourceBundleName) {
/*  81 */     this(clazz, resourceBundleName, null);
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
/*     */   public Localize(Class<?> clazz, String resourceBundleName, Locale locale) {
/*  95 */     this.clazz = clazz;
/*  96 */     this.resourceBundleName = resourceBundleName;
/*  97 */     this.resourceBundle = getResourceBundle(clazz, resourceBundleName, locale);
/*  98 */     this.locale = locale;
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
/*     */   public String format(String key, Object... args) {
/*     */     String format;
/* 111 */     if (key == null) {
/* 112 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 116 */       if (this.resourceBundle != null) {
/* 117 */         format = this.resourceBundle.getString(key);
/*     */       } else {
/* 119 */         format = key;
/*     */       } 
/* 121 */     } catch (MissingResourceException mre) {
/* 122 */       format = key;
/*     */     } 
/* 124 */     Formatter formatter = new Formatter(this.locale);
/* 125 */     String s = formatter.format(format, args).out().toString().trim();
/* 126 */     formatter.close();
/* 127 */     return s;
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
/*     */   public static ResourceBundle getResourceBundle(Class<?> clazz, String resourceBundleName, Locale locale) {
/* 143 */     if (locale == null) {
/* 144 */       locale = Locale.getDefault();
/*     */     }
/* 146 */     String key = clazz.getName() + ";" + resourceBundleName + ";" + locale.toString();
/* 147 */     synchronized (s_rb) {
/*     */       
/* 149 */       ResourceBundle resourceBundle = s_rb.get(key);
/* 150 */       if (resourceBundle == null) {
/* 151 */         if (resourceBundleName == null) {
/* 152 */           String cn = clazz.getName();
/* 153 */           if (cn == null) {
/* 154 */             Class<?> c2 = clazz.getEnclosingClass();
/* 155 */             if (c2 != null) {
/* 156 */               cn = c2.getName();
/*     */             }
/*     */           } 
/* 159 */           if (cn == null) {
/* 160 */             throw new IllegalArgumentException("Specify top-level class.  This class does not have a canonical name: " + clazz
/* 161 */                 .getName());
/*     */           }
/* 163 */           resourceBundleName = cn;
/*     */         } else {
/* 165 */           resourceBundleName = clazz.getPackage().getName() + "." + resourceBundleName;
/*     */         } 
/*     */         try {
/* 168 */           resourceBundle = ResourceBundle.getBundle(resourceBundleName, locale, clazz.getClassLoader());
/* 169 */           s_rb.put(key, resourceBundle);
/* 170 */         } catch (MissingResourceException missingResourceException) {}
/*     */       } 
/*     */ 
/*     */       
/* 174 */       return s_rb.get(key);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getMessage(Throwable throwable) {
/* 185 */     if (throwable.getCause() == null) {
/* 186 */       String localizedMessage = throwable.getLocalizedMessage();
/* 187 */       if (localizedMessage != null) {
/* 188 */         return localizedMessage;
/*     */       }
/* 190 */       String message = throwable.getMessage();
/* 191 */       if (message != null) {
/* 192 */         return message;
/*     */       }
/* 194 */       return throwable.toString();
/*     */     } 
/* 196 */     String causeToString = throwable.getCause().toString();
/* 197 */     String localized = throwable.getLocalizedMessage();
/* 198 */     if (localized == null || localized.equals(causeToString)) {
/* 199 */       return getMessage(throwable.getCause());
/*     */     }
/* 201 */     return localized;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 206 */     return "Localize{, locale=" + this.locale + ", clazz=" + this.clazz + ", resourceBundleName='" + this.resourceBundleName + '\'' + '}';
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
/*     */   public static String filter(String message, ResourceBundle catalog) {
/* 218 */     if (catalog == null) {
/* 219 */       return message;
/*     */     }
/*     */     
/*     */     try {
/* 223 */       message = catalog.getString(message);
/* 224 */     } catch (MissingResourceException missingResourceException) {}
/*     */ 
/*     */     
/* 227 */     Matcher matcher = s_tokens.matcher(message);
/* 228 */     int numberMatches = 0;
/* 229 */     for (; matcher.find(); numberMatches++);
/*     */ 
/*     */     
/* 232 */     for (int match = numberMatches; match > 0; match--) {
/* 233 */       matcher.reset();
/* 234 */       for (int i = 0; i < match; i++) {
/* 235 */         matcher.find();
/*     */       }
/* 237 */       MatchResult mr = matcher.toMatchResult();
/*     */       try {
/* 239 */         String key = mr.group(1);
/* 240 */         String replacement = catalog.getString(key);
/* 241 */         int start = mr.start();
/* 242 */         int end = mr.end();
/*     */         
/* 244 */         message = message.substring(0, start) + replacement + message.substring(end);
/* 245 */       } catch (MissingResourceException missingResourceException) {}
/*     */     } 
/* 247 */     return message;
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
/*     */   @Deprecated
/*     */   public static String filter(String message, Class<?> resourceClass) {
/* 266 */     ClassLoader cl = resourceClass.getClassLoader();
/* 267 */     if (cl == null) {
/* 268 */       LOG.warning("Could not get ClassLoader from " + resourceClass.getName());
/* 269 */       cl = ClassLoader.getSystemClassLoader();
/*     */     } 
/* 271 */     Locale currentLocale = Locale.getDefault();
/* 272 */     String name = resourceClass.getName();
/*     */     
/* 274 */     ResourceBundle catalog = ResourceBundle.getBundle(name, currentLocale, cl);
/* 275 */     if (catalog == null) {
/* 276 */       LOG.warning("Could not get ResourceBundle " + name + " for " + currentLocale + " from " + cl);
/*     */     }
/*     */     
/* 279 */     return filter(message, catalog);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String timeWords(long seconds) {
/* 287 */     if (seconds == 0L) {
/* 288 */       return filter("0 ${seconds}", Localize.class);
/*     */     }
/* 290 */     String result = "";
/* 291 */     long minutes = seconds / 60L;
/* 292 */     long hours = minutes / 60L;
/* 293 */     long days = hours / 24L;
/* 294 */     seconds %= 60L;
/* 295 */     minutes %= 60L;
/* 296 */     hours %= 24L;
/*     */     
/* 298 */     if (hours >= 12L) days++; 
/* 299 */     hours = minutes = seconds = 0L;
/*     */     
/* 301 */     if (minutes >= 30L) {
/* 302 */       hours++;
/* 303 */       days += hours / 24L;
/* 304 */       hours %= 24L;
/*     */     } 
/* 306 */     minutes = seconds = 0L;
/* 307 */     if (minutes >= 10L || hours > 0L) {
/* 308 */       if (seconds >= 30L) {
/* 309 */         minutes++;
/* 310 */         hours += minutes / 60L;
/* 311 */         minutes %= 60L;
/*     */       } 
/* 313 */       seconds = 0L;
/*     */     } 
/* 315 */     if (seconds != 0L)
/* 316 */       result = " " + seconds + " ${second" + ((seconds > 1L) ? "s}" : "}") + result; 
/* 317 */     if (minutes != 0L)
/* 318 */       result = " " + minutes + " ${minute" + ((minutes > 1L) ? "s}" : "}") + result; 
/* 319 */     if (hours != 0L)
/* 320 */       result = " " + hours + " ${hour" + ((hours > 1L) ? "s}" : "}") + result; 
/* 321 */     if (days != 0L) {
/* 322 */       result = " " + days + " ${day" + ((days > 1L) ? "s}" : "}") + result;
/*     */     }
/* 324 */     return filter(result.trim(), Localize.class);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/Localize.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */