/*     */ package us.hebi.matlab.mat.types;
/*     */ 
/*     */ import us.hebi.matlab.mat.util.Casts;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractMatrixBase
/*     */   extends AbstractArray
/*     */   implements Matrix
/*     */ {
/*     */   protected AbstractMatrixBase(int[] dims) {
/*  34 */     super(dims);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected long orLogical(long value) {
/*  40 */     if (isLogical())
/*  41 */       return ((value != 0L) ? 1L : 0L); 
/*  42 */     return value;
/*     */   }
/*     */   
/*     */   protected double orLogical(double value) {
/*  46 */     if (isLogical())
/*  47 */       return ((value != 0.0D) ? true : false); 
/*  48 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract long getLong(int paramInt);
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void setLong(int paramInt, long paramLong);
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract double getDouble(int paramInt);
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void setDouble(int paramInt, double paramDouble);
/*     */ 
/*     */   
/*     */   public abstract long getImaginaryLong(int paramInt);
/*     */ 
/*     */   
/*     */   public abstract void setImaginaryLong(int paramInt, long paramLong);
/*     */ 
/*     */   
/*     */   public abstract double getImaginaryDouble(int paramInt);
/*     */ 
/*     */   
/*     */   public abstract void setImaginaryDouble(int paramInt, double paramDouble);
/*     */ 
/*     */   
/*     */   public boolean getBoolean(int index) {
/*  81 */     return Casts.logical(getDouble(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getByte(int index) {
/*  86 */     return Casts.int8(getLong(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public short getShort(int index) {
/*  91 */     return Casts.int16(getLong(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt(int index) {
/*  96 */     return Casts.int32(getLong(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFloat(int index) {
/* 101 */     return Casts.single(getDouble(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBoolean(int index, boolean value) {
/* 106 */     setLong(index, Casts.int8(value));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setByte(int index, byte value) {
/* 111 */     setLong(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setShort(int index, short value) {
/* 116 */     setLong(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInt(int index, int value) {
/* 121 */     setLong(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFloat(int index, float value) {
/* 126 */     setDouble(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getImaginaryByte(int index) {
/* 131 */     return Casts.int8(getImaginaryLong(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public short getImaginaryShort(int index) {
/* 136 */     return Casts.int16(getImaginaryLong(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getImaginaryInt(int index) {
/* 141 */     return Casts.int32(getImaginaryLong(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public float getImaginaryFloat(int index) {
/* 146 */     return Casts.single(getImaginaryDouble(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setImaginaryByte(int index, byte value) {
/* 151 */     setImaginaryLong(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setImaginaryShort(int index, short value) {
/* 156 */     setImaginaryLong(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setImaginaryInt(int index, int value) {
/* 161 */     setImaginaryLong(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setImaginaryFloat(int index, float value) {
/* 166 */     setImaginaryDouble(index, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean getBoolean(int row, int col) {
/* 173 */     return getBoolean(getColumnMajorIndex(row, col));
/*     */   }
/*     */ 
/*     */   
/*     */   public final byte getByte(int row, int col) {
/* 178 */     return getByte(getColumnMajorIndex(row, col));
/*     */   }
/*     */ 
/*     */   
/*     */   public final short getShort(int row, int col) {
/* 183 */     return getShort(getColumnMajorIndex(row, col));
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getInt(int row, int col) {
/* 188 */     return getInt(getColumnMajorIndex(row, col));
/*     */   }
/*     */ 
/*     */   
/*     */   public final long getLong(int row, int col) {
/* 193 */     return getLong(getColumnMajorIndex(row, col));
/*     */   }
/*     */ 
/*     */   
/*     */   public final float getFloat(int row, int col) {
/* 198 */     return getFloat(getColumnMajorIndex(row, col));
/*     */   }
/*     */ 
/*     */   
/*     */   public final double getDouble(int row, int col) {
/* 203 */     return getDouble(getColumnMajorIndex(row, col));
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setBoolean(int row, int col, boolean value) {
/* 208 */     setBoolean(getColumnMajorIndex(row, col), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setByte(int row, int col, byte value) {
/* 213 */     setByte(getColumnMajorIndex(row, col), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setShort(int row, int col, short value) {
/* 218 */     setShort(getColumnMajorIndex(row, col), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setInt(int row, int col, int value) {
/* 223 */     setInt(getColumnMajorIndex(row, col), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setLong(int row, int col, long value) {
/* 228 */     setLong(getColumnMajorIndex(row, col), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setFloat(int row, int col, float value) {
/* 233 */     setFloat(getColumnMajorIndex(row, col), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setDouble(int row, int col, double value) {
/* 238 */     setDouble(getColumnMajorIndex(row, col), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean getBoolean(int[] indices) {
/* 243 */     return getBoolean(getColumnMajorIndex(indices));
/*     */   }
/*     */ 
/*     */   
/*     */   public final byte getByte(int[] indices) {
/* 248 */     return getByte(getColumnMajorIndex(indices));
/*     */   }
/*     */ 
/*     */   
/*     */   public final short getShort(int[] indices) {
/* 253 */     return getShort(getColumnMajorIndex(indices));
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getInt(int[] indices) {
/* 258 */     return getInt(getColumnMajorIndex(indices));
/*     */   }
/*     */ 
/*     */   
/*     */   public final long getLong(int[] indices) {
/* 263 */     return getLong(getColumnMajorIndex(indices));
/*     */   }
/*     */ 
/*     */   
/*     */   public final float getFloat(int[] indices) {
/* 268 */     return getFloat(getColumnMajorIndex(indices));
/*     */   }
/*     */ 
/*     */   
/*     */   public final double getDouble(int[] indices) {
/* 273 */     return getDouble(getColumnMajorIndex(indices));
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setBoolean(int[] indices, boolean value) {
/* 278 */     setBoolean(getColumnMajorIndex(indices), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setByte(int[] indices, byte value) {
/* 283 */     setByte(getColumnMajorIndex(indices), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setShort(int[] indices, short value) {
/* 288 */     setShort(getColumnMajorIndex(indices), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setInt(int[] indices, int value) {
/* 293 */     setInt(getColumnMajorIndex(indices), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setLong(int[] indices, long value) {
/* 298 */     setLong(getColumnMajorIndex(indices), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setFloat(int[] indices, float value) {
/* 303 */     setFloat(getColumnMajorIndex(indices), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setDouble(int[] indices, double value) {
/* 308 */     setDouble(getColumnMajorIndex(indices), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final byte getImaginaryByte(int row, int col) {
/* 313 */     return getImaginaryByte(getColumnMajorIndex(row, col));
/*     */   }
/*     */ 
/*     */   
/*     */   public final short getImaginaryShort(int row, int col) {
/* 318 */     return getImaginaryShort(getColumnMajorIndex(row, col));
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getImaginaryInt(int row, int col) {
/* 323 */     return getImaginaryInt(getColumnMajorIndex(row, col));
/*     */   }
/*     */ 
/*     */   
/*     */   public final long getImaginaryLong(int row, int col) {
/* 328 */     return getImaginaryLong(getColumnMajorIndex(row, col));
/*     */   }
/*     */ 
/*     */   
/*     */   public final float getImaginaryFloat(int row, int col) {
/* 333 */     return getImaginaryFloat(getColumnMajorIndex(row, col));
/*     */   }
/*     */ 
/*     */   
/*     */   public final double getImaginaryDouble(int row, int col) {
/* 338 */     return getImaginaryDouble(getColumnMajorIndex(row, col));
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setImaginaryByte(int row, int col, byte value) {
/* 343 */     setImaginaryByte(getColumnMajorIndex(row, col), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setImaginaryShort(int row, int col, short value) {
/* 348 */     setImaginaryShort(getColumnMajorIndex(row, col), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setImaginaryInt(int row, int col, int value) {
/* 353 */     setImaginaryInt(getColumnMajorIndex(row, col), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setImaginaryLong(int row, int col, long value) {
/* 358 */     setImaginaryLong(getColumnMajorIndex(row, col), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setImaginaryFloat(int row, int col, float value) {
/* 363 */     setImaginaryFloat(getColumnMajorIndex(row, col), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setImaginaryDouble(int row, int col, double value) {
/* 368 */     setImaginaryDouble(getColumnMajorIndex(row, col), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final byte getImaginaryByte(int[] indices) {
/* 373 */     return getImaginaryByte(getColumnMajorIndex(indices));
/*     */   }
/*     */ 
/*     */   
/*     */   public final short getImaginaryShort(int[] indices) {
/* 378 */     return getImaginaryShort(getColumnMajorIndex(indices));
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getImaginaryInt(int[] indices) {
/* 383 */     return getImaginaryInt(getColumnMajorIndex(indices));
/*     */   }
/*     */ 
/*     */   
/*     */   public final long getImaginaryLong(int[] indices) {
/* 388 */     return getImaginaryLong(getColumnMajorIndex(indices));
/*     */   }
/*     */ 
/*     */   
/*     */   public final float getImaginaryFloat(int[] indices) {
/* 393 */     return getImaginaryFloat(getColumnMajorIndex(indices));
/*     */   }
/*     */ 
/*     */   
/*     */   public final double getImaginaryDouble(int[] indices) {
/* 398 */     return getImaginaryDouble(getColumnMajorIndex(indices));
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setImaginaryByte(int[] indices, byte value) {
/* 403 */     setImaginaryByte(getColumnMajorIndex(indices), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setImaginaryShort(int[] indices, short value) {
/* 408 */     setImaginaryShort(getColumnMajorIndex(indices), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setImaginaryInt(int[] indices, int value) {
/* 413 */     setImaginaryInt(getColumnMajorIndex(indices), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setImaginaryLong(int[] indices, long value) {
/* 418 */     setImaginaryLong(getColumnMajorIndex(indices), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setImaginaryFloat(int[] indices, float value) {
/* 423 */     setImaginaryFloat(getColumnMajorIndex(indices), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setImaginaryDouble(int[] indices, double value) {
/* 428 */     setImaginaryDouble(getColumnMajorIndex(indices), value);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/types/AbstractMatrixBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */