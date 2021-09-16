/*     */ package org.jfree.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ObjectUtilities
/*     */ {
/*     */   public static final String THREAD_CONTEXT = "ThreadContext";
/*     */   public static final String CLASS_CONTEXT = "ClassContext";
/*  83 */   private static String classLoaderSource = "ThreadContext";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ClassLoader classLoader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getClassLoaderSource() {
/* 102 */     return classLoaderSource;
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
/*     */   public static void setClassLoaderSource(String classLoaderSource) {
/* 116 */     ObjectUtilities.classLoaderSource = classLoaderSource;
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
/*     */   public static boolean equal(Object o1, Object o2) {
/* 128 */     if (o1 == o2) {
/* 129 */       return true;
/*     */     }
/* 131 */     if (o1 != null) {
/* 132 */       return o1.equals(o2);
/*     */     }
/*     */     
/* 135 */     return false;
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
/*     */   public static int hashCode(Object object) {
/* 148 */     int result = 0;
/* 149 */     if (object != null) {
/* 150 */       result = object.hashCode();
/*     */     }
/* 152 */     return result;
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
/*     */   public static Object clone(Object object) throws CloneNotSupportedException {
/* 165 */     if (object == null) {
/* 166 */       throw new IllegalArgumentException("Null 'object' argument.");
/*     */     }
/* 168 */     if (object instanceof PublicCloneable) {
/* 169 */       PublicCloneable pc = (PublicCloneable)object;
/* 170 */       return pc.clone();
/*     */     } 
/*     */     
/*     */     try {
/* 174 */       Method method = object.getClass().getMethod("clone", (Class[])null);
/*     */       
/* 176 */       if (Modifier.isPublic(method.getModifiers())) {
/* 177 */         return method.invoke(object, (Object[])null);
/*     */       }
/*     */     }
/* 180 */     catch (NoSuchMethodException e) {
/* 181 */       Log.warn("Object without clone() method is impossible.");
/*     */     }
/* 183 */     catch (IllegalAccessException e) {
/* 184 */       Log.warn("Object.clone(): unable to call method.");
/*     */     }
/* 186 */     catch (InvocationTargetException e) {
/* 187 */       Log.warn("Object without clone() method is impossible.");
/*     */     } 
/*     */     
/* 190 */     throw new CloneNotSupportedException("Failed to clone.");
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
/*     */   public static Collection deepClone(Collection collection) throws CloneNotSupportedException {
/* 206 */     if (collection == null) {
/* 207 */       throw new IllegalArgumentException("Null 'collection' argument.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     Collection<Object> result = (Collection)clone(collection);
/* 214 */     result.clear();
/* 215 */     Iterator iterator = collection.iterator();
/* 216 */     while (iterator.hasNext()) {
/* 217 */       Object item = iterator.next();
/* 218 */       if (item != null) {
/* 219 */         result.add(clone(item));
/*     */         continue;
/*     */       } 
/* 222 */       result.add(null);
/*     */     } 
/*     */     
/* 225 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void setClassLoader(ClassLoader classLoader) {
/* 235 */     ObjectUtilities.classLoader = classLoader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ClassLoader getClassLoader() {
/* 244 */     return classLoader;
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
/*     */   public static ClassLoader getClassLoader(Class c) {
/*     */     String localClassLoaderSource;
/* 259 */     synchronized (ObjectUtilities.class) {
/*     */       
/* 261 */       if (classLoader != null) {
/* 262 */         return classLoader;
/*     */       }
/* 264 */       localClassLoaderSource = classLoaderSource;
/*     */     } 
/*     */     
/* 267 */     if ("ThreadContext".equals(localClassLoaderSource)) {
/* 268 */       ClassLoader threadLoader = Thread.currentThread().getContextClassLoader();
/* 269 */       if (threadLoader != null) {
/* 270 */         return threadLoader;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 275 */     ClassLoader applicationCL = c.getClassLoader();
/* 276 */     if (applicationCL == null) {
/* 277 */       return ClassLoader.getSystemClassLoader();
/*     */     }
/*     */     
/* 280 */     return applicationCL;
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
/*     */   public static URL getResource(String name, Class c) {
/* 293 */     ClassLoader cl = getClassLoader(c);
/* 294 */     if (cl == null) {
/* 295 */       return null;
/*     */     }
/* 297 */     return cl.getResource(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static URL getResourceRelative(String name, Class c) {
/* 308 */     ClassLoader cl = getClassLoader(c);
/* 309 */     String cname = convertName(name, c);
/* 310 */     if (cl == null) {
/* 311 */       return null;
/*     */     }
/* 313 */     return cl.getResource(cname);
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
/*     */   private static String convertName(String name, Class<?> c) {
/* 327 */     if (name.startsWith("/"))
/*     */     {
/* 329 */       return name.substring(1);
/*     */     }
/*     */ 
/*     */     
/* 333 */     while (c.isArray()) {
/* 334 */       c = c.getComponentType();
/*     */     }
/*     */     
/* 337 */     String baseName = c.getName();
/* 338 */     int index = baseName.lastIndexOf('.');
/* 339 */     if (index == -1) {
/* 340 */       return name;
/*     */     }
/*     */     
/* 343 */     String pkgName = baseName.substring(0, index);
/* 344 */     return pkgName.replace('.', '/') + "/" + name;
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
/*     */   public static InputStream getResourceAsStream(String name, Class context) {
/* 357 */     URL url = getResource(name, context);
/* 358 */     if (url == null) {
/* 359 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 363 */       return url.openStream();
/*     */     }
/* 365 */     catch (IOException e) {
/* 366 */       return null;
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
/*     */   public static InputStream getResourceRelativeAsStream(String name, Class context) {
/* 380 */     URL url = getResourceRelative(name, context);
/* 381 */     if (url == null) {
/* 382 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 386 */       return url.openStream();
/*     */     }
/* 388 */     catch (IOException e) {
/* 389 */       return null;
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
/*     */   public static Object loadAndInstantiate(String className, Class source) {
/*     */     try {
/* 404 */       ClassLoader loader = getClassLoader(source);
/* 405 */       Class<?> c = loader.loadClass(className);
/* 406 */       return c.newInstance();
/*     */     }
/* 408 */     catch (Exception e) {
/* 409 */       return null;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object loadAndInstantiate(String className, Class source, Class type) {
/*     */     try {
/* 428 */       ClassLoader loader = getClassLoader(source);
/* 429 */       Class<?> c = loader.loadClass(className);
/* 430 */       if (type.isAssignableFrom(c)) {
/* 431 */         return c.newInstance();
/*     */       }
/*     */     }
/* 434 */     catch (Exception e) {
/* 435 */       return null;
/*     */     } 
/* 437 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isJDK14() {
/*     */     try {
/* 448 */       ClassLoader loader = getClassLoader(ObjectUtilities.class);
/* 449 */       if (loader != null) {
/*     */         try {
/* 451 */           loader.loadClass("java.util.RandomAccess");
/* 452 */           return true;
/*     */         }
/* 454 */         catch (ClassNotFoundException e) {
/* 455 */           return false;
/*     */         }
/* 457 */         catch (Exception e) {}
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 462 */     catch (Exception e) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 470 */       String version = System.getProperty("java.vm.specification.version");
/*     */       
/* 472 */       if (version == null) {
/* 473 */         return false;
/*     */       }
/*     */       
/* 476 */       String[] versions = parseVersions(version);
/* 477 */       String[] target = { "1", "4" };
/* 478 */       return (ArrayUtilities.compareVersionArrays((Comparable[])versions, (Comparable[])target) >= 0);
/*     */     }
/* 480 */     catch (Exception e) {
/* 481 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static String[] parseVersions(String version) {
/* 487 */     if (version == null)
/*     */     {
/* 489 */       return new String[0];
/*     */     }
/*     */     
/* 492 */     ArrayList<String> versions = new ArrayList();
/* 493 */     StringTokenizer strtok = new StringTokenizer(version, ".");
/* 494 */     while (strtok.hasMoreTokens())
/*     */     {
/* 496 */       versions.add(strtok.nextToken());
/*     */     }
/* 498 */     return versions.<String>toArray(new String[versions.size()]);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/util/ObjectUtilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */