/*     */ package org.jfree.base;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Library
/*     */ {
/*     */   private String name;
/*     */   private String version;
/*     */   private String licenceName;
/*     */   private String info;
/*     */   
/*     */   public Library(String name, String version, String licence, String info) {
/*  80 */     this.name = name;
/*  81 */     this.version = version;
/*  82 */     this.licenceName = licence;
/*  83 */     this.info = info;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Library() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  99 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 108 */     return this.version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLicenceName() {
/* 117 */     return this.licenceName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInfo() {
/* 126 */     return this.info;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setInfo(String info) {
/* 135 */     this.info = info;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setLicenceName(String licenceName) {
/* 144 */     this.licenceName = licenceName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setName(String name) {
/* 153 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setVersion(String version) {
/* 162 */     this.version = version;
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
/*     */   public boolean equals(Object o) {
/* 174 */     if (this == o)
/*     */     {
/* 176 */       return true;
/*     */     }
/* 178 */     if (o == null || getClass() != o.getClass())
/*     */     {
/* 180 */       return false;
/*     */     }
/*     */     
/* 183 */     Library library = (Library)o;
/*     */     
/* 185 */     if ((this.name != null) ? !this.name.equals(library.name) : (library.name != null))
/*     */     {
/* 187 */       return false;
/*     */     }
/*     */     
/* 190 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 200 */     return (this.name != null) ? this.name.hashCode() : 0;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/base/Library.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */