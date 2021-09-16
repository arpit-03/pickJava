/*     */ package us.hebi.matlab.mat.format;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import us.hebi.matlab.mat.types.Array;
/*     */ import us.hebi.matlab.mat.types.Cell;
/*     */ import us.hebi.matlab.mat.types.Char;
/*     */ import us.hebi.matlab.mat.types.MatlabType;
/*     */ import us.hebi.matlab.mat.types.Matrix;
/*     */ import us.hebi.matlab.mat.types.Struct;
/*     */ import us.hebi.matlab.mat.util.Preconditions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class McosFileWrapper
/*     */   extends MatOpaque
/*     */ {
/*     */   private final ByteOrder order;
/*     */   int version;
/*     */   String[] strings;
/*     */   int[] segmentIndices;
/*     */   List<ClassInfo> classInfo;
/*     */   List<ObjectInfo> objectInfo;
/*     */   List<Property[]> segment2Properties;
/*     */   List<Property[]> segment4Properties;
/*     */   McosRegistry mcosRegistry;
/*     */   
/*     */   McosFileWrapper(String objectType, String className, Array content, ByteOrder order) {
/*  52 */     super(objectType, className, content);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 407 */     this.version = -1;
/* 408 */     this.strings = null;
/* 409 */     this.segmentIndices = null;
/* 410 */     this.classInfo = null;
/* 411 */     this.objectInfo = null;
/* 412 */     this.segment2Properties = null;
/* 413 */     this.segment4Properties = null;
/* 414 */     this.mcosRegistry = null;
/*     */     if (!"MCOS".equals(objectType))
/*     */       throw new IllegalArgumentException("Expected MCOS object type. Found " + objectType); 
/*     */     if (!"FileWrapper__".equals(className))
/*     */       throw new IllegalArgumentException("Expected FileWrapper__ class. Found " + className); 
/*     */     this.order = order;
/*     */   }
/*     */   
/*     */   public Cell getContent() {
/*     */     return (Cell)super.getContent();
/*     */   }
/*     */   
/*     */   List<McosObject> parseObjects(McosRegistry mcosRegistry) throws IOException {
/*     */     this.mcosRegistry = (McosRegistry)Preconditions.checkNotNull(mcosRegistry);
/*     */     Matrix mcos = (Matrix)getContent().get(0);
/*     */     if (mcos.getType() != MatlabType.UInt8)
/*     */       throw Mat5Reader.readError("Unexpected MCOS data type. Expected: %s, Found: %s", new Object[] { MatlabType.UInt8, mcos.getType() }); 
/*     */     ByteBuffer buffer = Mat5.exportBytes(mcos);
/*     */     buffer.order(this.order);
/*     */     this.version = buffer.getInt();
/*     */     if (this.version != 2 && this.version != 3)
/*     */       throw Mat5Reader.readError("MAT file's MCOS data has an unknown version. Expected: 2 or 3, Found %d", new Object[] { Integer.valueOf(this.version) }); 
/*     */     int numStrings = buffer.getInt();
/*     */     this.segmentIndices = new int[6];
/*     */     for (int i = 0; i < this.segmentIndices.length; i++)
/*     */       this.segmentIndices[i] = buffer.getInt(); 
/*     */     if (this.segmentIndices[5] != buffer.limit())
/*     */       throw Mat5Reader.readError("Unexpected end of segment 5. Expected: %d, Found: %d", new Object[] { Integer.valueOf(buffer.limit()), Integer.valueOf(this.segmentIndices[5]) }); 
/*     */     checkUnknown(buffer.getLong(), 0L);
/*     */     this.strings = parseStrings(buffer, numStrings);
/*     */     if ((buffer.position() + 7 & 0xFFFFFFF8) != this.segmentIndices[0])
/*     */       throw new IllegalStateException("Data from the strings section was not all read!"); 
/*     */     this.classInfo = parseSegment1(buffer);
/*     */     this.objectInfo = parseSegment3(buffer);
/*     */     this.segment2Properties = parseSegment2(buffer);
/*     */     this.segment4Properties = parseSegment4(buffer);
/*     */     return createObjects();
/*     */   }
/*     */   
/*     */   private List<McosObject> createObjects() {
/*     */     Cell sharedProperties = (Cell)getContent().get(getContent().getNumElements() - 1);
/*     */     List<McosObject> objects = new ArrayList<>(this.objectInfo.size());
/*     */     for (int i = 0; i < this.objectInfo.size(); i++) {
/*     */       ObjectInfo objInfo = this.objectInfo.get(i);
/*     */       ClassInfo info = this.classInfo.get(objInfo.classId - 1);
/*     */       McosObject object = new McosObject(info.packageName, info.className);
/*     */       Struct shared = (Struct)sharedProperties.get(objInfo.classId);
/*     */       for (String name : shared.getFieldNames())
/*     */         object.set(name, shared.get(name)); 
/*     */       if (objInfo.segment2PropertiesIndex > 0) {
/*     */         byte b;
/*     */         int j;
/*     */         Property[] arrayOfProperty;
/*     */         for (j = (arrayOfProperty = this.segment2Properties.get(objInfo.segment2PropertiesIndex - 1)).length, b = 0; b < j; ) {
/*     */           Property property = arrayOfProperty[b];
/*     */           object.set(property.name, property.value);
/*     */           b++;
/*     */         } 
/*     */       } 
/*     */       if (objInfo.segment4PropertiesIndex > 0) {
/*     */         byte b;
/*     */         int j;
/*     */         Property[] arrayOfProperty;
/*     */         for (j = (arrayOfProperty = this.segment4Properties.get(objInfo.segment4PropertiesIndex - 1)).length, b = 0; b < j; ) {
/*     */           Property property = arrayOfProperty[b];
/*     */           object.set(property.name, property.value);
/*     */           b++;
/*     */         } 
/*     */       } 
/*     */       objects.add(object);
/*     */     } 
/*     */     return objects;
/*     */   }
/*     */   
/*     */   private static String[] parseStrings(ByteBuffer buffer, int numStrings) {
/*     */     String[] strings = new String[numStrings];
/*     */     StringBuilder sb = new StringBuilder();
/*     */     for (int i = 0; i < strings.length; i++) {
/*     */       sb.setLength(0);
/*     */       char next = (char)buffer.get();
/*     */       while (next != '\000') {
/*     */         sb.append(next);
/*     */         next = (char)buffer.get();
/*     */       } 
/*     */       strings[i] = sb.toString();
/*     */     } 
/*     */     return strings;
/*     */   }
/*     */   
/*     */   private String getString(int index) {
/*     */     return (index > 0) ? this.strings[index - 1] : "";
/*     */   }
/*     */   
/*     */   private List<ClassInfo> parseSegment1(ByteBuffer buffer) {
/*     */     buffer.position(this.segmentIndices[0]);
/*     */     checkUnknown(buffer.getLong(), 0L);
/*     */     checkUnknown(buffer.getLong(), 0L);
/*     */     List<ClassInfo> classNames = new ArrayList<>();
/*     */     while (buffer.position() < this.segmentIndices[1]) {
/*     */       String packageName = getString(buffer.getInt());
/*     */       String className = getString(buffer.getInt());
/*     */       checkUnknown(buffer.getLong(), 0L);
/*     */       classNames.add(new ClassInfo(packageName, className));
/*     */     } 
/*     */     if (buffer.position() != this.segmentIndices[1])
/*     */       throw new IllegalStateException("Data from the class section was not all read!"); 
/*     */     return classNames;
/*     */   }
/*     */   
/*     */   private List<Property[]> parseSegment2(ByteBuffer buffer) {
/*     */     return parseProperties(buffer, this.segmentIndices[1], this.segmentIndices[2]);
/*     */   }
/*     */   
/*     */   private List<Property[]> parseProperties(ByteBuffer buffer, int start, int end) {
/*     */     if (start == end)
/*     */       return (List)Collections.emptyList(); 
/*     */     buffer.position(start);
/*     */     checkUnknown(buffer.getLong(), 0L);
/*     */     List<Property[]> perClassProperties = (List)new ArrayList<>();
/*     */     while (buffer.position() < end) {
/*     */       int numProps = buffer.getInt();
/*     */       Property[] properties = new Property[numProps];
/*     */       for (int i = 0; i < numProps; i++) {
/*     */         Char char_;
/*     */         Array value;
/*     */         Matrix matrix;
/*     */         McosReference mcosReference;
/*     */         String name = getString(buffer.getInt());
/*     */         int flag = buffer.getInt();
/*     */         int heapIndex = buffer.getInt();
/*     */         switch (flag) {
/*     */           case 0:
/*     */             char_ = Mat5.newString(getString(heapIndex));
/*     */             break;
/*     */           case 1:
/*     */             value = getContent().get(heapIndex + 2, 0);
/*     */             break;
/*     */           case 2:
/*     */             matrix = Mat5.newLogicalScalar((heapIndex != 0));
/*     */             break;
/*     */           default:
/*     */             throw new IllegalArgumentException("Unexpected flag value: " + flag);
/*     */         } 
/*     */         if (McosReference.isMcosReference((Array)matrix))
/*     */           mcosReference = this.mcosRegistry.register(McosReference.parseMcosReference((Array)matrix)); 
/*     */         properties[i] = new Property(name, (Array)mcosReference);
/*     */       } 
/*     */       perClassProperties.add(properties);
/*     */       if ((numProps * 3 + 1) % 2 != 0)
/*     */         checkUnknown(buffer.getInt(), 0L); 
/*     */     } 
/*     */     if (buffer.position() != end)
/*     */       throw new IllegalStateException("Data from the class section was not all read!"); 
/*     */     return perClassProperties;
/*     */   }
/*     */   
/*     */   private List<ObjectInfo> parseSegment3(ByteBuffer buffer) {
/*     */     buffer.position(this.segmentIndices[2]);
/*     */     checkUnknown(buffer.getLong(), 0L);
/*     */     checkUnknown(buffer.getLong(), 0L);
/*     */     checkUnknown(buffer.getLong(), 0L);
/*     */     List<ObjectInfo> objectInfo = new ArrayList<>();
/*     */     while (buffer.position() < this.segmentIndices[3]) {
/*     */       int classId = buffer.getInt();
/*     */       checkUnknown(buffer.getInt(), 0L);
/*     */       checkUnknown(buffer.getInt(), 0L);
/*     */       int segment2PropsIndex = buffer.getInt();
/*     */       int segment4PropsIndex = buffer.getInt();
/*     */       int supposedlyObjectId = buffer.getInt();
/*     */       objectInfo.add(new ObjectInfo(supposedlyObjectId, classId, segment2PropsIndex, segment4PropsIndex));
/*     */     } 
/*     */     if (buffer.position() != this.segmentIndices[3])
/*     */       throw new IllegalStateException("Data from the class section was not all read!"); 
/*     */     return objectInfo;
/*     */   }
/*     */   
/*     */   private List<Property[]> parseSegment4(ByteBuffer buffer) {
/*     */     return parseProperties(buffer, this.segmentIndices[3], this.segmentIndices[4]);
/*     */   }
/*     */   
/*     */   private List<Object> parseSegment5(ByteBuffer buffer) {
/*     */     if (this.segmentIndices[4] == this.segmentIndices[5])
/*     */       return Collections.emptyList(); 
/*     */     byte[] bytes = new byte[this.segmentIndices[5] - this.segmentIndices[4]];
/*     */     buffer.position(this.segmentIndices[4]);
/*     */     buffer.get(bytes);
/*     */     System.out.println("\nSegment 5:\n" + Arrays.toString(bytes));
/*     */     throw new IllegalStateException("Segment 5 has data!");
/*     */   }
/*     */   
/*     */   private static void checkUnknown(long value, long expected) {
/*     */     if (value != expected)
/*     */       throw new IllegalStateException("MAT file's MCOS data has different byte values for unknown fields!  Aborting!"); 
/*     */   }
/*     */   
/*     */   private static class ClassInfo {
/*     */     final String packageName;
/*     */     final String className;
/*     */     
/*     */     ClassInfo(String packageName, String className) {
/*     */       this.packageName = packageName;
/*     */       this.className = className;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ObjectInfo {
/*     */     final int objectId;
/*     */     final int classId;
/*     */     final int segment2PropertiesIndex;
/*     */     final int segment4PropertiesIndex;
/*     */     
/*     */     ObjectInfo(int objectId, int classId, int segment2PropertiesIndex, int segment4PropertiesIndex) {
/*     */       this.objectId = objectId;
/*     */       this.classId = classId;
/*     */       this.segment2PropertiesIndex = segment2PropertiesIndex;
/*     */       this.segment4PropertiesIndex = segment4PropertiesIndex;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Property {
/*     */     final String name;
/*     */     final Array value;
/*     */     
/*     */     Property(String name, Array value) {
/*     */       this.name = name;
/*     */       this.value = value;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/McosFileWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */