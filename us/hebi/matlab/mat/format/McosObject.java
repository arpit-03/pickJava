/*     */ package us.hebi.matlab.mat.format;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import us.hebi.matlab.mat.types.Array;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class McosObject
/*     */ {
/*     */   private final String packageName;
/*     */   private final String className;
/*     */   private final List<String> fieldNames;
/*     */   private final Map<String, Array> properties;
/*     */   
/*     */   String getPackageName() {
/*     */     return this.packageName;
/*     */   }
/*     */   
/*     */   String getClassName() {
/*     */     return this.className;
/*     */   }
/*     */   
/*     */   McosObject(String packageName, String className) {
/*  70 */     this.fieldNames = new ArrayList<>(8);
/*  71 */     this.properties = new HashMap<>(16);
/*     */     this.packageName = packageName;
/*  73 */     this.className = className; } static final McosObject EMPTY = new McosObject("", "")
/*     */     {
/*     */       public void set(String name, Array value) {
/*  76 */         throw new IllegalStateException("Can't set empty reference.");
/*     */       }
/*     */     };
/*     */   List<String> getFieldNames() {
/*     */     return this.fieldNames;
/*     */   }
/*  82 */   public String toString() { return "'" + getClassName() + "' class"; }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  87 */     return Compat.hash(new Object[] { this.packageName, this.className, this.fieldNames, this.properties }); }
/*     */   void set(String name, Array value) { if (this.properties.put(name, value) == null)
/*     */       this.fieldNames.add(name);  } Array get(String name) {
/*     */     return this.properties.get(name);
/*     */   } public boolean equals(Object obj) {
/*  92 */     if (this == obj)
/*  93 */       return true; 
/*  94 */     if (obj instanceof McosObject) {
/*  95 */       McosObject other = (McosObject)obj;
/*  96 */       return (other.packageName.equals(this.packageName) && 
/*  97 */         other.className.equals(this.className) && 
/*  98 */         other.fieldNames.equals(this.fieldNames) && 
/*  99 */         other.properties.equals(this.properties));
/*     */     } 
/* 101 */     return false;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/McosObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */