/*     */ package us.hebi.matlab.mat.format;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import us.hebi.matlab.mat.types.AbstractStructBase;
/*     */ import us.hebi.matlab.mat.types.Array;
/*     */ import us.hebi.matlab.mat.types.MatlabType;
/*     */ import us.hebi.matlab.mat.types.Matrix;
/*     */ import us.hebi.matlab.mat.types.ObjectStruct;
/*     */ import us.hebi.matlab.mat.types.Opaque;
/*     */ import us.hebi.matlab.mat.types.Sink;
/*     */ import us.hebi.matlab.mat.types.Struct;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class McosReference
/*     */   extends AbstractStructBase
/*     */   implements ObjectStruct, Opaque, Mat5Serializable
/*     */ {
/*     */   final String objectType;
/*     */   final String className;
/*     */   final Array content;
/*     */   final int[] objectIds;
/*     */   final int classId;
/*     */   final McosObject[] objects;
/*     */   
/*     */   static McosReference parseMcosReference(Array content) {
/*  48 */     if (!isMcosReference(content))
/*  49 */       throw new IllegalArgumentException("Not a valid reference"); 
/*  50 */     return parseOpaque("MCOS", "N/A", content);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isMcosReference(Array content) {
/*  58 */     if (content.getType() != MatlabType.UInt32) {
/*  59 */       return false;
/*     */     }
/*     */     
/*  62 */     if (content.getNumRows() < 5 || content.getNumCols() != 1) {
/*  63 */       return false;
/*     */     }
/*     */     
/*  66 */     Matrix data = (Matrix)content;
/*  67 */     return (data.getInt(0) == -587202560 && data.getInt(1) == 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static McosReference parseOpaque(String objectType, String className, Array content) {
/*  76 */     if ("FileWrapper__".equals(className)) {
/*  77 */       throw new IllegalArgumentException("Only for references. Not for FileWrapper__ class");
/*     */     }
/*     */     
/*  80 */     if (!"MCOS".equals(objectType) || !isMcosReference(content)) {
/*  81 */       throw new IllegalArgumentException("Unexpected MCOS object reference data type: " + content);
/*     */     }
/*     */     
/*  84 */     Matrix data = (Matrix)content;
/*  85 */     int[] dims = new int[2];
/*  86 */     dims[0] = data.getInt(2);
/*  87 */     dims[1] = data.getInt(3);
/*     */ 
/*     */     
/*  90 */     int numElements = getNumElements(dims);
/*  91 */     if (numElements != data.getNumRows() - 5) {
/*  92 */       throw new IllegalArgumentException("Different number of references than dimensions");
/*     */     }
/*     */     
/*  95 */     int[] objectIds = new int[numElements];
/*  96 */     for (int i = 0; i < objectIds.length; i++) {
/*  97 */       objectIds[i] = data.getInt(i + 4);
/*     */     }
/*     */ 
/*     */     
/* 101 */     int classId = data.getInt(data.getNumRows() - 1);
/* 102 */     return new McosReference(dims, objectType, className, content, objectIds, classId);
/*     */   }
/*     */ 
/*     */   
/*     */   private McosReference(int[] dims, String objectType, String className, Array content, int[] objectIds, int classId) {
/* 107 */     super(dims);
/*     */ 
/*     */     
/* 110 */     this.content = content;
/* 111 */     this.className = className;
/* 112 */     this.objectType = objectType;
/*     */ 
/*     */     
/* 115 */     this.objectIds = objectIds;
/* 116 */     this.classId = classId;
/* 117 */     this.objects = new McosObject[objectIds.length];
/* 118 */     Arrays.fill((Object[])this.objects, McosObject.EMPTY);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setReferences(List<McosObject> objects) {
/* 123 */     for (int i = 0; i < this.objectIds.length; i++) {
/* 124 */       this.objects[i] = objects.get(this.objectIds[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getFieldNames() {
/* 130 */     return isEmpty() ? Collections.<String>emptyList() : this.objects[0].getFieldNames();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Array> T get(String field, int index) {
/* 136 */     return (T)this.objects[index].get(field);
/*     */   }
/*     */ 
/*     */   
/*     */   public Struct set(String field, int index, Array value) {
/* 141 */     this.objects[index].set(field, value);
/* 142 */     return (Struct)this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Array[] remove(String field) {
/* 147 */     throw new IllegalStateException("Can't remove fields from Reference Objects.");
/*     */   }
/*     */ 
/*     */   
/*     */   public MatlabType getType() {
/* 152 */     return MatlabType.Object;
/*     */   }
/*     */   
/*     */   public String getObjectType() {
/* 156 */     return this.objectType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPackageName() {
/* 161 */     return isEmpty() ? "" : this.objects[0].getPackageName();
/*     */   }
/*     */   
/*     */   public String getClassName() {
/* 165 */     return isEmpty() ? this.className : this.objects[0].getClassName();
/*     */   }
/*     */   
/*     */   public Array getContent() {
/* 169 */     return this.content;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMat5Size(String name) {
/* 174 */     return Mat5WriteUtil.computeOpaqueSize(name, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeMat5(String name, boolean isGlobal, Sink sink) throws IOException {
/* 179 */     Mat5WriteUtil.writeOpaque(name, isGlobal, this, sink);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 184 */     this.content.close();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 190 */     return String.valueOf(super.toString()) + " for '" + getClassName() + "'";
/*     */   }
/*     */   
/*     */   private boolean isEmpty() {
/* 194 */     return !(this.objects.length != 0 && this.objects[0] != McosObject.EMPTY);
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
/*     */   protected int subHashCode() {
/* 207 */     return Compat.hash(new Object[] { this.objectType, this.className, this.content, Integer.valueOf(Arrays.hashCode(this.objectIds)), Integer.valueOf(this.classId), Integer.valueOf(Arrays.hashCode((Object[])this.objects)) });
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean subEqualsGuaranteedSameClass(Object otherGuaranteedSameClass) {
/* 212 */     McosReference other = (McosReference)otherGuaranteedSameClass;
/* 213 */     return (other.objectType.equals(this.objectType) && 
/* 214 */       other.className.equals(this.className) && 
/* 215 */       other.classId == this.classId && 
/* 216 */       other.content.equals(this.content) && 
/* 217 */       Arrays.equals(other.objectIds, this.objectIds) && 
/* 218 */       Arrays.equals((Object[])other.objects, (Object[])this.objects));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/McosReference.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */