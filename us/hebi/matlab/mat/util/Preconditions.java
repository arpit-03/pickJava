/*     */ package us.hebi.matlab.mat.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Preconditions
/*     */ {
/*     */   public static void checkArgument(boolean expression) {
/* 113 */     if (!expression) {
/* 114 */       throw new IllegalArgumentException();
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
/*     */   public static void checkArgument(boolean expression, Object errorMessage) {
/* 127 */     if (!expression) {
/* 128 */       throw new IllegalArgumentException(String.valueOf(errorMessage));
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void checkArgument(boolean expression, String errorMessageTemplate, Object... errorMessageArgs) {
/* 150 */     if (!expression) {
/* 151 */       throw new IllegalArgumentException(format(errorMessageTemplate, errorMessageArgs));
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
/*     */   public static void checkState(boolean expression) {
/* 163 */     if (!expression) {
/* 164 */       throw new IllegalStateException();
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
/*     */   public static void checkState(boolean expression, Object errorMessage) {
/* 178 */     if (!expression) {
/* 179 */       throw new IllegalStateException(String.valueOf(errorMessage));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void checkState(boolean expression, String errorMessageTemplate, Object... errorMessageArgs) {
/* 202 */     if (!expression) {
/* 203 */       throw new IllegalStateException(format(errorMessageTemplate, errorMessageArgs));
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
/*     */   public static <T> T checkNotNull(T reference) {
/* 215 */     if (reference == null) {
/* 216 */       throw new NullPointerException();
/*     */     }
/* 218 */     return reference;
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
/*     */   public static <T> T checkNotNull(T reference, Object errorMessage) {
/* 231 */     if (reference == null) {
/* 232 */       throw new NullPointerException(String.valueOf(errorMessage));
/*     */     }
/* 234 */     return reference;
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
/*     */ 
/*     */   
/*     */   public static <T> T checkNotNull(T reference, String errorMessageTemplate, Object... errorMessageArgs) {
/* 254 */     if (reference == null)
/*     */     {
/* 256 */       throw new NullPointerException(format(errorMessageTemplate, errorMessageArgs));
/*     */     }
/* 258 */     return reference;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int checkElementIndex(int index, int size) {
/* 298 */     return checkElementIndex(index, size, "index");
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
/*     */   public static int checkElementIndex(int index, int size, String desc) {
/* 315 */     if (index < 0 || index >= size) {
/* 316 */       throw new IndexOutOfBoundsException(badElementIndex(index, size, desc));
/*     */     }
/* 318 */     return index;
/*     */   }
/*     */   
/*     */   private static String badElementIndex(int index, int size, String desc) {
/* 322 */     if (index < 0)
/* 323 */       return format("%s (%s) must not be negative", new Object[] { desc, Integer.valueOf(index) }); 
/* 324 */     if (size < 0) {
/* 325 */       throw new IllegalArgumentException("negative size: " + size);
/*     */     }
/* 327 */     return format("%s (%s) must be less than size (%s)", new Object[] { desc, Integer.valueOf(index), Integer.valueOf(size) });
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
/*     */   public static int checkPositionIndex(int index, int size) {
/* 342 */     return checkPositionIndex(index, size, "index");
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
/*     */   public static int checkPositionIndex(int index, int size, String desc) {
/* 358 */     if (index < 0 || index > size) {
/* 359 */       throw new IndexOutOfBoundsException(badPositionIndex(index, size, desc));
/*     */     }
/* 361 */     return index;
/*     */   }
/*     */   
/*     */   private static String badPositionIndex(int index, int size, String desc) {
/* 365 */     if (index < 0)
/* 366 */       return format("%s (%s) must not be negative", new Object[] { desc, Integer.valueOf(index) }); 
/* 367 */     if (size < 0) {
/* 368 */       throw new IllegalArgumentException("negative size: " + size);
/*     */     }
/* 370 */     return format("%s (%s) must not be greater than size (%s)", new Object[] { desc, Integer.valueOf(index), Integer.valueOf(size) });
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
/*     */   public static void checkPositionIndexes(int start, int end, int size) {
/* 388 */     if (start < 0 || end < start || end > size) {
/* 389 */       throw new IndexOutOfBoundsException(badPositionIndexes(start, end, size));
/*     */     }
/*     */   }
/*     */   
/*     */   private static String badPositionIndexes(int start, int end, int size) {
/* 394 */     if (start < 0 || start > size) {
/* 395 */       return badPositionIndex(start, size, "start index");
/*     */     }
/* 397 */     if (end < 0 || end > size) {
/* 398 */       return badPositionIndex(end, size, "end index");
/*     */     }
/*     */     
/* 401 */     return format("end index (%s) must not be less than start index (%s)", new Object[] { Integer.valueOf(end), Integer.valueOf(start) });
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
/*     */   static String format(String template, Object... args) {
/* 416 */     template = String.valueOf(template);
/*     */ 
/*     */     
/* 419 */     StringBuilder builder = new StringBuilder(template.length() + 16 * args.length);
/* 420 */     int templateStart = 0;
/* 421 */     int i = 0;
/* 422 */     while (i < args.length) {
/* 423 */       int placeholderStart = template.indexOf("%s", templateStart);
/* 424 */       if (placeholderStart == -1) {
/*     */         break;
/*     */       }
/* 427 */       builder.append(template, templateStart, placeholderStart);
/* 428 */       builder.append(args[i++]);
/* 429 */       templateStart = placeholderStart + 2;
/*     */     } 
/* 431 */     builder.append(template.substring(templateStart));
/*     */ 
/*     */     
/* 434 */     if (i < args.length) {
/* 435 */       builder.append(" [");
/* 436 */       builder.append(args[i++]);
/* 437 */       while (i < args.length) {
/* 438 */         builder.append(", ");
/* 439 */         builder.append(args[i++]);
/*     */       } 
/* 441 */       builder.append(']');
/*     */     } 
/*     */     
/* 444 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/util/Preconditions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */