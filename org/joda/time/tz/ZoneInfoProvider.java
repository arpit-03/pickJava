/*     */ package org.joda.time.tz;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.joda.time.DateTimeZone;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ZoneInfoProvider
/*     */   implements Provider
/*     */ {
/*     */   private final File iFileDir;
/*     */   private final String iResourcePath;
/*     */   private final ClassLoader iLoader;
/*     */   private final Map<String, Object> iZoneInfoMap;
/*     */   private final Set<String> iZoneInfoKeys;
/*     */   
/*     */   public ZoneInfoProvider(File paramFile) throws IOException {
/*  62 */     if (paramFile == null) {
/*  63 */       throw new IllegalArgumentException("No file directory provided");
/*     */     }
/*  65 */     if (!paramFile.exists()) {
/*  66 */       throw new IOException("File directory doesn't exist: " + paramFile);
/*     */     }
/*  68 */     if (!paramFile.isDirectory()) {
/*  69 */       throw new IOException("File doesn't refer to a directory: " + paramFile);
/*     */     }
/*     */     
/*  72 */     this.iFileDir = paramFile;
/*  73 */     this.iResourcePath = null;
/*  74 */     this.iLoader = null;
/*     */     
/*  76 */     this.iZoneInfoMap = loadZoneInfoMap(openResource("ZoneInfoMap"));
/*  77 */     this.iZoneInfoKeys = Collections.unmodifiableSortedSet(new TreeSet<String>(this.iZoneInfoMap.keySet()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZoneInfoProvider(String paramString) throws IOException {
/*  88 */     this(paramString, null, false);
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
/*     */   public ZoneInfoProvider(String paramString, ClassLoader paramClassLoader) throws IOException {
/* 102 */     this(paramString, paramClassLoader, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ZoneInfoProvider(String paramString, ClassLoader paramClassLoader, boolean paramBoolean) throws IOException {
/* 113 */     if (paramString == null) {
/* 114 */       throw new IllegalArgumentException("No resource path provided");
/*     */     }
/* 116 */     if (!paramString.endsWith("/")) {
/* 117 */       paramString = paramString + '/';
/*     */     }
/*     */     
/* 120 */     this.iFileDir = null;
/* 121 */     this.iResourcePath = paramString;
/*     */     
/* 123 */     if (paramClassLoader == null && !paramBoolean) {
/* 124 */       paramClassLoader = getClass().getClassLoader();
/*     */     }
/*     */     
/* 127 */     this.iLoader = paramClassLoader;
/*     */     
/* 129 */     this.iZoneInfoMap = loadZoneInfoMap(openResource("ZoneInfoMap"));
/* 130 */     this.iZoneInfoKeys = Collections.unmodifiableSortedSet(new TreeSet<String>(this.iZoneInfoMap.keySet()));
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
/*     */   public DateTimeZone getZone(String paramString) {
/* 142 */     if (paramString == null) {
/* 143 */       return null;
/*     */     }
/*     */     
/* 146 */     Object object = this.iZoneInfoMap.get(paramString);
/* 147 */     if (object == null) {
/* 148 */       return null;
/*     */     }
/*     */     
/* 151 */     if (object instanceof SoftReference) {
/*     */       
/* 153 */       SoftReference<DateTimeZone> softReference = (SoftReference)object;
/* 154 */       DateTimeZone dateTimeZone = softReference.get();
/* 155 */       if (dateTimeZone != null) {
/* 156 */         return dateTimeZone;
/*     */       }
/*     */       
/* 159 */       return loadZoneData(paramString);
/* 160 */     }  if (paramString.equals(object))
/*     */     {
/* 162 */       return loadZoneData(paramString);
/*     */     }
/*     */ 
/*     */     
/* 166 */     return getZone((String)object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getAvailableIDs() {
/* 175 */     return this.iZoneInfoKeys;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uncaughtException(Exception paramException) {
/* 184 */     paramException.printStackTrace();
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
/*     */   private InputStream openResource(String paramString) throws IOException {
/*     */     InputStream inputStream;
/* 197 */     if (this.iFileDir != null) {
/* 198 */       inputStream = new FileInputStream(new File(this.iFileDir, paramString));
/*     */     } else {
/* 200 */       final String path = this.iResourcePath.concat(paramString);
/* 201 */       inputStream = AccessController.<InputStream>doPrivileged(new PrivilegedAction<InputStream>() {
/*     */             public InputStream run() {
/* 203 */               if (ZoneInfoProvider.this.iLoader != null) {
/* 204 */                 return ZoneInfoProvider.this.iLoader.getResourceAsStream(path);
/*     */               }
/* 206 */               return ClassLoader.getSystemResourceAsStream(path);
/*     */             }
/*     */           });
/*     */       
/* 210 */       if (inputStream == null) {
/* 211 */         StringBuilder stringBuilder = (new StringBuilder(40)).append("Resource not found: \"").append(str).append("\" ClassLoader: ").append((this.iLoader != null) ? this.iLoader.toString() : "system");
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 216 */         throw new IOException(stringBuilder.toString());
/*     */       } 
/*     */     } 
/* 219 */     return inputStream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DateTimeZone loadZoneData(String paramString) {
/* 229 */     InputStream inputStream = null;
/*     */     try {
/* 231 */       inputStream = openResource(paramString);
/* 232 */       DateTimeZone dateTimeZone = DateTimeZoneBuilder.readFrom(inputStream, paramString);
/* 233 */       this.iZoneInfoMap.put(paramString, new SoftReference<DateTimeZone>(dateTimeZone));
/* 234 */       return dateTimeZone;
/* 235 */     } catch (IOException iOException) {
/* 236 */       uncaughtException(iOException);
/* 237 */       this.iZoneInfoMap.remove(paramString);
/* 238 */       return null;
/*     */     } finally {
/*     */       try {
/* 241 */         if (inputStream != null) {
/* 242 */           inputStream.close();
/*     */         }
/* 244 */       } catch (IOException iOException) {}
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
/*     */   private static Map<String, Object> loadZoneInfoMap(InputStream paramInputStream) throws IOException {
/* 257 */     ConcurrentHashMap<Object, Object> concurrentHashMap = new ConcurrentHashMap<Object, Object>();
/* 258 */     DataInputStream dataInputStream = new DataInputStream(paramInputStream);
/*     */     try {
/* 260 */       readZoneInfoMap(dataInputStream, (Map)concurrentHashMap);
/*     */     } finally {
/*     */       try {
/* 263 */         dataInputStream.close();
/* 264 */       } catch (IOException iOException) {}
/*     */     } 
/*     */     
/* 267 */     concurrentHashMap.put("UTC", new SoftReference<DateTimeZone>(DateTimeZone.UTC));
/* 268 */     return (Map)concurrentHashMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void readZoneInfoMap(DataInputStream paramDataInputStream, Map<String, Object> paramMap) throws IOException {
/* 279 */     int i = paramDataInputStream.readUnsignedShort();
/* 280 */     String[] arrayOfString = new String[i]; byte b;
/* 281 */     for (b = 0; b < i; b++) {
/* 282 */       arrayOfString[b] = paramDataInputStream.readUTF().intern();
/*     */     }
/*     */ 
/*     */     
/* 286 */     i = paramDataInputStream.readUnsignedShort();
/* 287 */     for (b = 0; b < i; b++) {
/*     */       try {
/* 289 */         paramMap.put(arrayOfString[paramDataInputStream.readUnsignedShort()], arrayOfString[paramDataInputStream.readUnsignedShort()]);
/* 290 */       } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 291 */         throw new IOException("Corrupt zone info map");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/tz/ZoneInfoProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */