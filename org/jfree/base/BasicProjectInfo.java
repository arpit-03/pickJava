/*     */ package org.jfree.base;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicProjectInfo
/*     */   extends Library
/*     */ {
/*     */   private String copyright;
/*     */   private List libraries;
/*     */   private List optionalLibraries;
/*     */   
/*     */   private static class OptionalLibraryHolder
/*     */   {
/*     */     private String libraryClass;
/*     */     private transient Library library;
/*     */     
/*     */     public OptionalLibraryHolder(String libraryClass) {
/*  66 */       if (libraryClass == null) {
/*  67 */         throw new NullPointerException("LibraryClass must not be null.");
/*     */       }
/*  69 */       this.libraryClass = libraryClass;
/*     */     }
/*     */     
/*     */     public OptionalLibraryHolder(Library library) {
/*  73 */       if (library == null) {
/*  74 */         throw new NullPointerException("Library must not be null.");
/*     */       }
/*  76 */       this.library = library;
/*  77 */       this.libraryClass = library.getClass().getName();
/*     */     }
/*     */     
/*     */     public String getLibraryClass() {
/*  81 */       return this.libraryClass;
/*     */     }
/*     */     
/*     */     public Library getLibrary() {
/*  85 */       if (this.library == null) {
/*  86 */         this.library = loadLibrary(this.libraryClass);
/*     */       }
/*  88 */       return this.library;
/*     */     }
/*     */     
/*     */     protected Library loadLibrary(String classname) {
/*  92 */       if (classname == null) {
/*  93 */         return null;
/*     */       }
/*     */       
/*     */       try {
/*  97 */         Class<?> c = ObjectUtilities.getClassLoader(getClass()).loadClass(classname);
/*     */         try {
/*  99 */           Method m = c.getMethod("getInstance", (Class[])null);
/* 100 */           return (Library)m.invoke(null, (Object[])null);
/*     */         }
/* 102 */         catch (Exception e) {
/*     */ 
/*     */           
/* 105 */           return (Library)c.newInstance();
/*     */         } 
/* 107 */       } catch (Exception e) {
/*     */ 
/*     */         
/* 110 */         return null;
/*     */       } 
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
/*     */   public BasicProjectInfo() {
/* 128 */     this.libraries = new ArrayList();
/* 129 */     this.optionalLibraries = new ArrayList();
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
/*     */   public BasicProjectInfo(String name, String version, String licence, String info) {
/* 142 */     this();
/* 143 */     setName(name);
/* 144 */     setVersion(version);
/* 145 */     setLicenceName(licence);
/* 146 */     setInfo(info);
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
/*     */   public BasicProjectInfo(String name, String version, String info, String copyright, String licenceName) {
/* 161 */     this(name, version, licenceName, info);
/* 162 */     setCopyright(copyright);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCopyright() {
/* 171 */     return this.copyright;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCopyright(String copyright) {
/* 180 */     this.copyright = copyright;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInfo(String info) {
/* 189 */     super.setInfo(info);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLicenceName(String licence) {
/* 198 */     super.setLicenceName(licence);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setName(String name) {
/* 207 */     super.setName(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVersion(String version) {
/* 216 */     super.setVersion(version);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Library[] getLibraries() {
/* 225 */     return (Library[])this.libraries
/* 226 */       .toArray((Object[])new Library[this.libraries.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addLibrary(Library library) {
/* 235 */     if (library == null) {
/* 236 */       throw new NullPointerException();
/*     */     }
/* 238 */     this.libraries.add(library);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Library[] getOptionalLibraries() {
/* 247 */     ArrayList<Library> libraries = new ArrayList();
/* 248 */     for (int i = 0; i < this.optionalLibraries.size(); i++) {
/*     */       
/* 250 */       OptionalLibraryHolder holder = this.optionalLibraries.get(i);
/* 251 */       Library l = holder.getLibrary();
/* 252 */       if (l != null) {
/* 253 */         libraries.add(l);
/*     */       }
/*     */     } 
/* 256 */     return libraries.<Library>toArray(new Library[libraries.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addOptionalLibrary(String libraryClass) {
/* 267 */     if (libraryClass == null) {
/* 268 */       throw new NullPointerException("Library classname must be given.");
/*     */     }
/* 270 */     this.optionalLibraries
/* 271 */       .add(new OptionalLibraryHolder(libraryClass));
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
/*     */   public void addOptionalLibrary(Library library) {
/* 283 */     if (library == null) {
/* 284 */       throw new NullPointerException("Library must be given.");
/*     */     }
/* 286 */     this.optionalLibraries.add(new OptionalLibraryHolder(library));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/base/BasicProjectInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */