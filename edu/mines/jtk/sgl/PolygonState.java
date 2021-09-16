/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import edu.mines.jtk.ogl.Gl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PolygonState
/*     */   implements State
/*     */ {
/*     */   public boolean hasCullFace() {
/*  37 */     return this._cullFaceSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCullFace() {
/*  45 */     return this._cullFace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCullFace(int mode) {
/*  53 */     this._cullFace = mode;
/*  54 */     this._cullFaceSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetCullFace() {
/*  61 */     this._cullFace = _cullFaceDefault;
/*  62 */     this._cullFaceSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasFrontFace() {
/*  70 */     return this._frontFaceSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFrontFace() {
/*  78 */     return this._frontFace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFrontFace(int mode) {
/*  86 */     this._frontFace = mode;
/*  87 */     this._frontFaceSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetFrontFace() {
/*  94 */     this._frontFace = _frontFaceDefault;
/*  95 */     this._frontFaceSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasPolygonModeFront() {
/* 103 */     return this._polygonModeFrontSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasPolygonModeBack() {
/* 111 */     return this._polygonModeBackSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPolygonModeFront() {
/* 119 */     return this._polygonModeFront;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPolygonModeBack() {
/* 127 */     return this._polygonModeBack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPolygonMode(int mode) {
/* 135 */     this._polygonModeFront = this._polygonModeBack = mode;
/* 136 */     this._polygonModeFrontSet = this._polygonModeBackSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPolygonModeFront(int mode) {
/* 144 */     this._polygonModeFront = mode;
/* 145 */     this._polygonModeFrontSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPolygonModeBack(int mode) {
/* 153 */     this._polygonModeBack = mode;
/* 154 */     this._polygonModeBackSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetPolygonMode() {
/* 161 */     this._polygonModeFront = this._polygonModeBack = _polygonModeDefault;
/* 162 */     this._polygonModeFrontSet = this._polygonModeBackSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetPolygonModeFront() {
/* 169 */     this._polygonModeFront = _polygonModeDefault;
/* 170 */     this._polygonModeFrontSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetPolygonModeBack() {
/* 177 */     this._polygonModeBack = _polygonModeDefault;
/* 178 */     this._polygonModeBackSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasPolygonOffset() {
/* 186 */     return this._polygonOffsetSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPolygonOffsetFactor() {
/* 194 */     return this._polygonOffsetFactor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPolygonOffsetUnits() {
/* 202 */     return this._polygonOffsetUnits;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPolygonOffset(float factor, float units) {
/* 211 */     this._polygonOffsetFactor = factor;
/* 212 */     this._polygonOffsetUnits = units;
/* 213 */     this._polygonOffsetSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetPolygonOffset() {
/* 220 */     this._polygonOffsetFactor = _polygonOffsetFactorDefault;
/* 221 */     this._polygonOffsetUnits = _polygonOffsetUnitsDefault;
/* 222 */     this._polygonOffsetSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasPolygonOffsetFill() {
/* 230 */     return this._polygonOffsetFillSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getPolygonOffsetFill() {
/* 238 */     return this._polygonOffsetFill;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPolygonOffsetFill(boolean fill) {
/* 246 */     this._polygonOffsetFill = fill;
/* 247 */     this._polygonOffsetFillSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetPolygonOffsetFill() {
/* 254 */     this._polygonOffsetFill = _polygonOffsetFillDefault;
/* 255 */     this._polygonOffsetFillSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasPolygonOffsetLine() {
/* 263 */     return this._polygonOffsetLineSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getPolygonOffsetLine() {
/* 271 */     return this._polygonOffsetLine;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPolygonOffsetLine(boolean line) {
/* 279 */     this._polygonOffsetLine = line;
/* 280 */     this._polygonOffsetLineSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetPolygonOffsetLine() {
/* 287 */     this._polygonOffsetLine = _polygonOffsetLineDefault;
/* 288 */     this._polygonOffsetLineSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasPolygonOffsetPoint() {
/* 296 */     return this._polygonOffsetPointSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getPolygonOffsetPoint() {
/* 304 */     return this._polygonOffsetPoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPolygonOffsetPoint(boolean point) {
/* 312 */     this._polygonOffsetPoint = point;
/* 313 */     this._polygonOffsetPointSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasPolygonStipple() {
/* 321 */     return this._polygonStippleSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getPolygonStipple() {
/* 329 */     return (byte[])this._polygonStipple.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPolygonStipple(byte[] mask) {
/* 337 */     this._polygonStipple = (byte[])mask.clone();
/* 338 */     this._polygonStippleSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetPolygonStipple() {
/* 345 */     this._polygonStipple = _polygonStippleDefault;
/* 346 */     this._polygonStippleSet = false;
/*     */   }
/*     */   
/*     */   public void apply() {
/* 350 */     if (this._cullFaceSet) {
/* 351 */       Gl.glEnable(2884);
/* 352 */       Gl.glCullFace(this._cullFace);
/*     */     } 
/* 354 */     if (this._frontFaceSet) {
/* 355 */       Gl.glEnable(2886);
/* 356 */       Gl.glFrontFace(this._frontFace);
/*     */     } 
/* 358 */     if (this._polygonModeFrontSet && this._polygonModeBackSet && this._polygonModeFront == this._polygonModeBack) {
/*     */ 
/*     */       
/* 361 */       Gl.glPolygonMode(1032, this._polygonModeFront);
/*     */     } else {
/* 363 */       if (this._polygonModeFrontSet)
/* 364 */         Gl.glPolygonMode(1028, this._polygonModeFront); 
/* 365 */       if (this._polygonModeBackSet)
/* 366 */         Gl.glPolygonMode(1029, this._polygonModeBack); 
/*     */     } 
/* 368 */     if (this._polygonOffsetSet)
/* 369 */       Gl.glPolygonOffset(this._polygonOffsetFactor, this._polygonOffsetUnits); 
/* 370 */     if (this._polygonOffsetFillSet) {
/* 371 */       if (this._polygonOffsetFill) {
/* 372 */         Gl.glEnable(32823);
/*     */       } else {
/* 374 */         Gl.glDisable(32823);
/*     */       } 
/*     */     }
/* 377 */     if (this._polygonOffsetLineSet) {
/* 378 */       if (this._polygonOffsetLine) {
/* 379 */         Gl.glEnable(10754);
/*     */       } else {
/* 381 */         Gl.glDisable(10754);
/*     */       } 
/*     */     }
/* 384 */     if (this._polygonOffsetPointSet) {
/* 385 */       if (this._polygonOffsetPoint) {
/* 386 */         Gl.glEnable(10753);
/*     */       } else {
/* 388 */         Gl.glDisable(10753);
/*     */       } 
/*     */     }
/* 391 */     if (this._polygonStippleSet) {
/* 392 */       Gl.glEnable(2882);
/* 393 */       Gl.glPolygonStipple(this._polygonStipple, 0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getAttributeBits() {
/* 398 */     return 8200;
/*     */   }
/*     */   
/* 401 */   private static int _cullFaceDefault = 1029;
/* 402 */   private int _cullFace = _cullFaceDefault;
/*     */   
/*     */   private boolean _cullFaceSet;
/* 405 */   private static int _frontFaceDefault = 2305;
/* 406 */   private int _frontFace = _frontFaceDefault;
/*     */   
/*     */   private boolean _frontFaceSet;
/* 409 */   private static int _polygonModeDefault = 6914;
/* 410 */   private int _polygonModeFront = _polygonModeDefault;
/* 411 */   private int _polygonModeBack = _polygonModeDefault;
/*     */   
/*     */   private boolean _polygonModeFrontSet;
/*     */   private boolean _polygonModeBackSet;
/* 415 */   private static float _polygonOffsetFactorDefault = 0.0F;
/* 416 */   private static float _polygonOffsetUnitsDefault = 0.0F;
/* 417 */   private float _polygonOffsetFactor = _polygonOffsetFactorDefault;
/* 418 */   private float _polygonOffsetUnits = _polygonOffsetUnitsDefault;
/*     */   
/*     */   private boolean _polygonOffsetSet;
/*     */   private static boolean _polygonOffsetFillDefault = false;
/* 422 */   private boolean _polygonOffsetFill = _polygonOffsetFillDefault;
/*     */   
/*     */   private boolean _polygonOffsetFillSet;
/*     */   private static boolean _polygonOffsetLineDefault = false;
/* 426 */   private boolean _polygonOffsetLine = _polygonOffsetLineDefault;
/*     */   
/*     */   private boolean _polygonOffsetLineSet;
/*     */   private static boolean _polygonOffsetPointDefault = false;
/* 430 */   private boolean _polygonOffsetPoint = _polygonOffsetPointDefault;
/*     */   
/*     */   private boolean _polygonOffsetPointSet;
/* 433 */   private static byte ONES = -1;
/* 434 */   private static byte[] _polygonStippleDefault = new byte[] { ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES, ONES };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 468 */   private byte[] _polygonStipple = _polygonStippleDefault;
/*     */   private boolean _polygonStippleSet;
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/PolygonState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */