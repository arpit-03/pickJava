/*     */ package us.hebi.matlab.mat.types;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import us.hebi.matlab.mat.util.IndentingAppendable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class StringHelper
/*     */ {
/*     */   static String toString(Collection<MatFile.Entry> entries) {
/*  38 */     StringBuilder builder = new StringBuilder();
/*  39 */     IndentingAppendable out = wrap(builder);
/*     */ 
/*     */     
/*  42 */     int longest = 0;
/*  43 */     for (MatFile.Entry entry : entries) {
/*  44 */       longest = Math.max(longest, entry.getName().length());
/*     */     }
/*     */ 
/*     */     
/*  48 */     boolean first = true;
/*  49 */     for (MatFile.Entry entry : entries) {
/*  50 */       if (!first) out.append("\n"); 
/*  51 */       first = false;
/*     */       
/*  53 */       appendName(entry.getName(), longest, out);
/*  54 */       out.append(" = ");
/*  55 */       append(entry.getValue(), out);
/*     */     } 
/*     */     
/*  58 */     return builder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   static String toString(Array array) {
/*  63 */     StringBuilder builder = new StringBuilder();
/*  64 */     append(array, wrap(builder));
/*  65 */     return builder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void append(Array array, IndentingAppendable out) {
/*  70 */     if (array.getNumElements() == 0) {
/*  71 */       if (array instanceof Cell) {
/*  72 */         out.append("{}"); return;
/*     */       } 
/*  74 */       if (array instanceof Matrix) {
/*  75 */         out.append("[]"); return;
/*     */       } 
/*  77 */       if (array instanceof Char) {
/*  78 */         out.append("''");
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/*  84 */     if (array instanceof Char) {
/*  85 */       Char c = (Char)array;
/*  86 */       if (c.getNumRows() == 1) {
/*  87 */         out.append("'").append(c.getString()).append("'");
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/*  93 */     if (array instanceof Matrix) {
/*  94 */       Matrix matrix = (Matrix)array;
/*  95 */       if (matrix.getNumElements() == 1) {
/*     */         
/*  97 */         if (matrix.isLogical()) {
/*  98 */           out.append(Boolean.valueOf(matrix.getBoolean(0)));
/*     */           
/*     */           return;
/*     */         } 
/* 102 */         out.append(Double.valueOf(matrix.getDouble(0)));
/* 103 */         if (matrix.isComplex()) {
/* 104 */           out.append("+").append(Double.valueOf(matrix.getImaginaryDouble(0))).append("j");
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 111 */     if (array instanceof Struct) {
/*     */ 
/*     */       
/* 114 */       String className = "struct";
/* 115 */       if (array instanceof ObjectStruct)
/* 116 */         className = getFullClassName((ObjectStruct)array); 
/* 117 */       out.append(getDimString(array)).append(className);
/*     */ 
/*     */       
/* 120 */       Struct struct = (Struct)array;
/* 121 */       int longestName = getLongestName(struct.getFieldNames());
/*     */ 
/*     */       
/* 124 */       out.indent();
/* 125 */       for (String field : struct.getFieldNames()) {
/* 126 */         out.append("\n");
/* 127 */         appendName(field, longestName, out);
/*     */ 
/*     */         
/* 130 */         if (struct.getNumElements() == 1) {
/* 131 */           out.append(": ");
/* 132 */           append(struct.get(field), out);
/*     */         } 
/*     */       } 
/*     */       
/* 136 */       out.unindent();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 141 */     if (array instanceof FunctionHandle) {
/* 142 */       out.append(getDimString(array)).append("function_handle");
/* 143 */       out.indent().append("\ncontent: ");
/* 144 */       append(((FunctionHandle)array).getContent(), out);
/* 145 */       out.unindent();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 150 */     if (array instanceof JavaObject) {
/* 151 */       JavaObject javaObj = (JavaObject)array;
/* 152 */       out.append(getDimString(array)).append(javaObj.getClassName()).append(" (Java)");
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 157 */     appendGenericString(array, out);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void appendGenericString(Array array, IndentingAppendable out) {
/* 162 */     out.append(getDimString(array)).append(array.getType());
/*     */   }
/*     */   
/*     */   private static String getDimString(Array array) {
/* 166 */     return Arrays.toString(array.getDimensions())
/* 167 */       .replaceAll(", ", "x")
/* 168 */       .replace("[", "")
/* 169 */       .replace("]", " ");
/*     */   }
/*     */   
/*     */   private static int getLongestName(List<String> names) {
/* 173 */     int longest = 0;
/* 174 */     for (String name : names) {
/* 175 */       longest = Math.max(longest, name.length());
/*     */     }
/* 177 */     return longest;
/*     */   }
/*     */   
/*     */   private static void appendName(String name, int longest, IndentingAppendable out) {
/* 181 */     out.append(name.isEmpty() ? "\"\"" : name);
/*     */   }
/*     */   
/*     */   private static IndentingAppendable wrap(Appendable appendable) {
/* 185 */     IndentingAppendable out = new IndentingAppendable(appendable);
/* 186 */     out.setIndentString("    ");
/* 187 */     return out;
/*     */   }
/*     */   
/*     */   private static String getFullClassName(ObjectStruct object) {
/* 191 */     if (!object.getPackageName().isEmpty())
/* 192 */       return String.valueOf(object.getPackageName()) + "." + object.getClassName(); 
/* 193 */     return object.getClassName();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/types/StringHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */