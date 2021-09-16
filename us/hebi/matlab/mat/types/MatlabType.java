/*    */ package us.hebi.matlab.mat.types;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum MatlabType
/*    */ {
/* 28 */   Cell(1, "cell"),
/* 29 */   Structure(2, "struct"),
/* 30 */   Object(3, "object"),
/* 31 */   Character(4, "char"),
/* 32 */   Sparse(5, "sparse"),
/* 33 */   Double(6, "double"),
/* 34 */   Single(7, "single"),
/* 35 */   Int8(8, "int8"),
/* 36 */   UInt8(9, "uint8"),
/* 37 */   Int16(10, "int16"),
/* 38 */   UInt16(11, "uint16"),
/* 39 */   Int32(12, "int32"),
/* 40 */   UInt32(13, "uint32"),
/* 41 */   Int64(14, "int64"),
/* 42 */   UInt64(15, "uint64"),
/*    */   
/* 44 */   Function(
/* 45 */     16, "function_handle"),
/* 46 */   Opaque(17, "opaque"); private final byte id; private final String name;
/*    */   private static final MatlabType[] lookup;
/*    */   
/*    */   public String toString() {
/* 50 */     return this.name;
/*    */   }
/*    */   
/*    */   public static MatlabType fromId(int id) {
/* 54 */     if (id > 0 && id < lookup.length) {
/* 55 */       MatlabType type = lookup[id];
/* 56 */       if (type != null)
/* 57 */         return type; 
/*    */     } 
/* 59 */     throw new IllegalArgumentException("Unknown array type for id: " + id);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte id() {
/* 66 */     return this.id;
/*    */   }
/*    */   
/*    */   MatlabType(int id, String name) {
/* 70 */     this.id = (byte)id;
/* 71 */     this.name = name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 81 */     int highestId = 0; byte b; int i; MatlabType[] arrayOfMatlabType;
/* 82 */     for (i = (arrayOfMatlabType = values()).length, b = 0; b < i; ) { MatlabType type = arrayOfMatlabType[b];
/* 83 */       highestId = Math.max(highestId, type.id);
/*    */       
/*    */       b++; }
/*    */     
/* 87 */     lookup = new MatlabType[highestId + 1];
/* 88 */     for (i = (arrayOfMatlabType = values()).length, b = 0; b < i; ) { MatlabType type = arrayOfMatlabType[b];
/* 89 */       lookup[type.id()] = type;
/*    */       b++; }
/*    */   
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/types/MatlabType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */