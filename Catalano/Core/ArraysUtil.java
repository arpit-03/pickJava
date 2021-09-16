/*     */ package Catalano.Core;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ArraysUtil
/*     */ {
/*     */   public static int[] Argsort(final double[] array, final boolean ascending) {
/*  61 */     Integer[] indexes = new Integer[array.length];
/*  62 */     for (int i = 0; i < indexes.length; i++) {
/*  63 */       indexes[i] = Integer.valueOf(i);
/*     */     }
/*  65 */     Arrays.sort(indexes, new Comparator<Integer>()
/*     */         {
/*     */           public int compare(Integer i1, Integer i2) {
/*  68 */             return (ascending ? 1 : -1) * Double.compare(array[i1.intValue()], array[i2.intValue()]);
/*     */           }
/*     */         });
/*  71 */     return asArray(indexes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] Argsort(final int[] array, final boolean ascending) {
/*  81 */     Integer[] indexes = new Integer[array.length];
/*  82 */     for (int i = 0; i < indexes.length; i++) {
/*  83 */       indexes[i] = Integer.valueOf(i);
/*     */     }
/*  85 */     Arrays.sort(indexes, new Comparator<Integer>()
/*     */         {
/*     */           public int compare(Integer i1, Integer i2) {
/*  88 */             return (ascending ? 1 : -1) * Integer.compare(array[i1.intValue()], array[i2.intValue()]);
/*     */           }
/*     */         });
/*  91 */     return asArray(indexes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] Argsort(final float[] array, final boolean ascending) {
/* 101 */     Integer[] indexes = new Integer[array.length];
/* 102 */     for (int i = 0; i < indexes.length; i++) {
/* 103 */       indexes[i] = Integer.valueOf(i);
/*     */     }
/* 105 */     Arrays.sort(indexes, new Comparator<Integer>()
/*     */         {
/*     */           public int compare(Integer i1, Integer i2) {
/* 108 */             return (ascending ? 1 : -1) * Float.compare(array[i1.intValue()], array[i2.intValue()]);
/*     */           }
/*     */         });
/* 111 */     return asArray(indexes);
/*     */   }
/*     */   
/*     */   public static boolean Contains(int[] array, int element) {
/* 115 */     for (int i = 0; i < array.length; i++) {
/* 116 */       if (array[i] == element)
/* 117 */         return true; 
/*     */     } 
/* 119 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] Concatenate(int[] array, int[] array2) {
/* 129 */     int[] all = new int[array.length + array2.length];
/* 130 */     int idx = 0;
/*     */     
/*     */     int i;
/* 133 */     for (i = 0; i < array.length; i++) {
/* 134 */       all[idx++] = array[i];
/*     */     }
/*     */     
/* 137 */     for (i = 0; i < array2.length; i++) {
/* 138 */       all[idx++] = array2[i];
/*     */     }
/* 140 */     return all;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] Concatenate(double[] array, double[] array2) {
/* 150 */     double[] all = new double[array.length + array2.length];
/* 151 */     int idx = 0;
/*     */     
/*     */     int i;
/* 154 */     for (i = 0; i < array.length; i++) {
/* 155 */       all[idx++] = array[i];
/*     */     }
/*     */     
/* 158 */     for (i = 0; i < array2.length; i++) {
/* 159 */       all[idx++] = array2[i];
/*     */     }
/* 161 */     return all;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] Concatenate(float[] array, float[] array2) {
/* 171 */     float[] all = new float[array.length + array2.length];
/* 172 */     int idx = 0;
/*     */     
/*     */     int i;
/* 175 */     for (i = 0; i < array.length; i++) {
/* 176 */       all[idx++] = array[i];
/*     */     }
/*     */     
/* 179 */     for (i = 0; i < array2.length; i++) {
/* 180 */       all[idx++] = array2[i];
/*     */     }
/* 182 */     return all;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] ConcatenateInt(List<int[]> arrays) {
/* 192 */     int size = 0;
/* 193 */     for (int i = 0; i < arrays.size(); i++) {
/* 194 */       size += ((int[])arrays.get(i)).length;
/*     */     }
/*     */     
/* 197 */     int[] all = new int[size];
/* 198 */     int idx = 0;
/*     */     
/* 200 */     for (int j = 0; j < arrays.size(); j++) {
/* 201 */       int[] v = arrays.get(j);
/* 202 */       for (int k = 0; k < v.length; k++) {
/* 203 */         all[idx++] = v[j];
/*     */       }
/*     */     } 
/*     */     
/* 207 */     return all;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] ConcatenateDouble(List<double[]> arrays) {
/* 217 */     int size = 0;
/* 218 */     for (int i = 0; i < arrays.size(); i++) {
/* 219 */       size += ((double[])arrays.get(i)).length;
/*     */     }
/*     */     
/* 222 */     double[] all = new double[size];
/* 223 */     int idx = 0;
/*     */     
/* 225 */     for (int j = 0; j < arrays.size(); j++) {
/* 226 */       double[] v = arrays.get(j);
/* 227 */       for (int k = 0; k < v.length; k++) {
/* 228 */         all[idx++] = v[j];
/*     */       }
/*     */     } 
/*     */     
/* 232 */     return all;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] ConcatenateFloat(List<float[]> arrays) {
/* 242 */     int size = 0;
/* 243 */     for (int i = 0; i < arrays.size(); i++) {
/* 244 */       size += ((float[])arrays.get(i)).length;
/*     */     }
/*     */     
/* 247 */     float[] all = new float[size];
/* 248 */     int idx = 0;
/*     */     
/* 250 */     for (int j = 0; j < arrays.size(); j++) {
/* 251 */       float[] v = arrays.get(j);
/* 252 */       for (int k = 0; k < v.length; k++) {
/* 253 */         all[idx++] = v[j];
/*     */       }
/*     */     } 
/*     */     
/* 257 */     return all;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends Number> int[] asArray(Number... array) {
/* 267 */     int[] b = new int[array.length];
/* 268 */     for (int i = 0; i < b.length; i++) {
/* 269 */       b[i] = array[i].intValue();
/*     */     }
/* 271 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void Shuffle(double[] array) {
/* 279 */     Shuffle(array, 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void Shuffle(double[] array, long seed) {
/* 288 */     Random random = new Random();
/* 289 */     if (seed != 0L) random.setSeed(seed);
/*     */     
/* 291 */     for (int i = array.length - 1; i > 0; i--) {
/*     */       
/* 293 */       int index = random.nextInt(i + 1);
/* 294 */       double temp = array[index];
/* 295 */       array[index] = array[i];
/* 296 */       array[i] = temp;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void Shuffle(int[] array) {
/* 305 */     Shuffle(array, 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void Shuffle(int[] array, long seed) {
/* 315 */     Random random = new Random();
/* 316 */     if (seed != 0L) random.setSeed(seed);
/*     */     
/* 318 */     for (int i = array.length - 1; i > 0; i--) {
/*     */       
/* 320 */       int index = random.nextInt(i + 1);
/* 321 */       int temp = array[index];
/* 322 */       array[index] = array[i];
/* 323 */       array[i] = temp;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void Shuffle(float[] array) {
/* 332 */     Shuffle(array, 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void Shuffle(float[] array, long seed) {
/* 341 */     Random random = new Random();
/* 342 */     if (seed != 0L) random.setSeed(seed);
/*     */     
/* 344 */     for (int i = array.length - 1; i > 0; i--) {
/*     */       
/* 346 */       int index = random.nextInt(i + 1);
/* 347 */       float temp = array[index];
/* 348 */       array[index] = array[i];
/* 349 */       array[i] = temp;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> void Shuffle(Object[] array) {
/* 358 */     Shuffle(array, 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> void Shuffle(Object[] array, long seed) {
/* 367 */     Random random = new Random();
/* 368 */     if (seed != 0L) random.setSeed(seed);
/*     */     
/* 370 */     for (int i = array.length - 1; i > 0; i--) {
/*     */       
/* 372 */       int index = random.nextInt(i + 1);
/* 373 */       T temp = (T)array[index];
/* 374 */       array[index] = array[i];
/* 375 */       array[i] = temp;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] toFloat(int[] array) {
/* 385 */     float[] n = new float[array.length];
/* 386 */     for (int i = 0; i < array.length; i++) {
/* 387 */       n[i] = array[i];
/*     */     }
/* 389 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[][] toFloat(int[][] array) {
/* 398 */     float[][] n = new float[array.length][(array[0]).length];
/* 399 */     for (int i = 0; i < array.length; i++) {
/* 400 */       for (int j = 0; j < (array[0]).length; j++) {
/* 401 */         n[i][j] = array[i][j];
/*     */       }
/*     */     } 
/* 404 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] toFloat(double[] array) {
/* 413 */     float[] n = new float[array.length];
/* 414 */     for (int i = 0; i < array.length; i++) {
/* 415 */       n[i] = (float)array[i];
/*     */     }
/* 417 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[][] toFloat(double[][] array) {
/* 426 */     float[][] n = new float[array.length][(array[0]).length];
/* 427 */     for (int i = 0; i < array.length; i++) {
/* 428 */       for (int j = 0; j < (array[0]).length; j++) {
/* 429 */         n[i][j] = (float)array[i][j];
/*     */       }
/*     */     } 
/* 432 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] toFloat(List<Float> ints) {
/* 442 */     float[] n = new float[ints.size()];
/* 443 */     for (int i = 0; i < n.length; i++) {
/* 444 */       n[i] = ((Float)ints.get(i)).floatValue();
/*     */     }
/*     */     
/* 447 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] toInt(double[] array) {
/* 457 */     int[] n = new int[array.length];
/* 458 */     for (int i = 0; i < array.length; i++) {
/* 459 */       n[i] = (int)array[i];
/*     */     }
/* 461 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[][] toInt(double[][] array) {
/* 470 */     int[][] n = new int[array.length][(array[0]).length];
/* 471 */     for (int i = 0; i < array.length; i++) {
/* 472 */       for (int j = 0; j < (array[0]).length; j++) {
/* 473 */         n[i][j] = (int)array[i][j];
/*     */       }
/*     */     } 
/* 476 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] toInt(List<Integer> ints) {
/* 486 */     int[] n = new int[ints.size()];
/* 487 */     for (int i = 0; i < n.length; i++) {
/* 488 */       n[i] = ((Integer)ints.get(i)).intValue();
/*     */     }
/*     */     
/* 491 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] toInt(float[] array) {
/* 501 */     int[] n = new int[array.length];
/* 502 */     for (int i = 0; i < array.length; i++) {
/* 503 */       n[i] = (int)array[i];
/*     */     }
/* 505 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[][] toInt(float[][] array) {
/* 514 */     int[][] n = new int[array.length][(array[0]).length];
/* 515 */     for (int i = 0; i < array.length; i++) {
/* 516 */       for (int j = 0; j < (array[0]).length; j++) {
/* 517 */         n[i][j] = (int)array[i][j];
/*     */       }
/*     */     } 
/* 520 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] toDouble(int[] array) {
/* 529 */     double[] n = new double[array.length];
/* 530 */     for (int i = 0; i < array.length; i++) {
/* 531 */       n[i] = array[i];
/*     */     }
/* 533 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[][] toDouble(int[][] array) {
/* 542 */     double[][] n = new double[array.length][(array[0]).length];
/* 543 */     for (int i = 0; i < array.length; i++) {
/* 544 */       for (int j = 0; j < (array[0]).length; j++) {
/* 545 */         n[i][j] = array[i][j];
/*     */       }
/*     */     } 
/* 548 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] toDouble(float[] array) {
/* 557 */     double[] n = new double[array.length];
/* 558 */     for (int i = 0; i < array.length; i++) {
/* 559 */       n[i] = array[i];
/*     */     }
/* 561 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[][] toDouble(float[][] array) {
/* 570 */     double[][] n = new double[array.length][(array[0]).length];
/* 571 */     for (int i = 0; i < array.length; i++) {
/* 572 */       for (int j = 0; j < (array[0]).length; j++) {
/* 573 */         n[i][j] = array[i][j];
/*     */       }
/*     */     } 
/* 576 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] toDouble(List<Double> ints) {
/* 586 */     double[] n = new double[ints.size()];
/* 587 */     for (int i = 0; i < n.length; i++) {
/* 588 */       n[i] = ((Double)ints.get(i)).doubleValue();
/*     */     }
/*     */     
/* 591 */     return n;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Core/ArraysUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */