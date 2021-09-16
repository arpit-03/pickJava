/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import edu.mines.jtk.ogl.Gl;
/*     */ import java.awt.Color;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MaterialState
/*     */   implements State
/*     */ {
/*     */   public boolean hasAmbientFront() {
/*  41 */     return this._ambientFrontSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasAmbientBack() {
/*  49 */     return this._ambientBackSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getAmbientFront() {
/*  57 */     return toColor(this._ambientFront);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getAmbientBack() {
/*  65 */     return toColor(this._ambientBack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAmbient(Color ambient) {
/*  73 */     this._ambientFront = this._ambientBack = toArray(ambient);
/*  74 */     this._ambientFrontSet = this._ambientBackSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAmbientFront(Color ambient) {
/*  82 */     this._ambientFront = toArray(ambient);
/*  83 */     this._ambientFrontSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAmbientBack(Color ambient) {
/*  91 */     this._ambientBack = toArray(ambient);
/*  92 */     this._ambientBackSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetAmbient() {
/*  99 */     this._ambientFront = this._ambientBack = _ambientDefault;
/* 100 */     this._ambientFrontSet = this._ambientBackSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetAmbientFront() {
/* 107 */     this._ambientFront = _ambientDefault;
/* 108 */     this._ambientFrontSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetAmbientBack() {
/* 115 */     this._ambientBack = _ambientDefault;
/* 116 */     this._ambientBackSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasDiffuseFront() {
/* 124 */     return this._diffuseFrontSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasDiffuseBack() {
/* 132 */     return this._diffuseBackSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getDiffuseFront() {
/* 140 */     return toColor(this._diffuseFront);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getDiffuseBack() {
/* 148 */     return toColor(this._diffuseBack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDiffuse(Color diffuse) {
/* 156 */     this._diffuseFront = this._diffuseBack = toArray(diffuse);
/* 157 */     this._diffuseFrontSet = this._diffuseBackSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDiffuseFront(Color diffuse) {
/* 165 */     this._diffuseFront = toArray(diffuse);
/* 166 */     this._diffuseFrontSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDiffuseBack(Color diffuse) {
/* 174 */     this._diffuseBack = toArray(diffuse);
/* 175 */     this._diffuseBackSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetDiffuse() {
/* 182 */     this._diffuseFront = this._diffuseBack = _diffuseDefault;
/* 183 */     this._diffuseFrontSet = this._diffuseBackSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetDiffuseFront() {
/* 190 */     this._diffuseFront = _diffuseDefault;
/* 191 */     this._diffuseFrontSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetDiffuseBack() {
/* 198 */     this._diffuseBack = _diffuseDefault;
/* 199 */     this._diffuseBackSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSpecularFront() {
/* 207 */     return this._specularFrontSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSpecularBack() {
/* 215 */     return this._specularBackSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getSpecularFront() {
/* 223 */     return toColor(this._specularFront);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getSpecularBack() {
/* 231 */     return toColor(this._specularBack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpecular(Color specular) {
/* 239 */     this._specularFront = this._specularBack = toArray(specular);
/* 240 */     this._specularFrontSet = this._specularBackSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpecularFront(Color specular) {
/* 248 */     this._specularFront = toArray(specular);
/* 249 */     this._specularFrontSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpecularBack(Color specular) {
/* 257 */     this._specularBack = toArray(specular);
/* 258 */     this._specularBackSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetSpecular() {
/* 265 */     this._specularFront = this._specularBack = _specularDefault;
/* 266 */     this._specularFrontSet = this._specularBackSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetSpecularFront() {
/* 273 */     this._specularFront = _specularDefault;
/* 274 */     this._specularFrontSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetSpecularBack() {
/* 281 */     this._specularBack = _specularDefault;
/* 282 */     this._specularBackSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasEmissiveFront() {
/* 290 */     return this._emissiveFrontSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasEmissiveBack() {
/* 298 */     return this._emissiveBackSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getEmissiveFront() {
/* 306 */     return toColor(this._emissiveFront);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getEmissiveBack() {
/* 314 */     return toColor(this._emissiveBack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEmissive(Color emissive) {
/* 322 */     this._emissiveFront = this._emissiveBack = toArray(emissive);
/* 323 */     this._emissiveFrontSet = this._emissiveBackSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEmissiveFront(Color emissive) {
/* 331 */     this._emissiveFront = toArray(emissive);
/* 332 */     this._emissiveFrontSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEmissiveBack(Color emissive) {
/* 340 */     this._emissiveBack = toArray(emissive);
/* 341 */     this._emissiveBackSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetEmissive() {
/* 348 */     this._emissiveFront = this._emissiveBack = _emissiveDefault;
/* 349 */     this._emissiveFrontSet = this._emissiveBackSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetEmissiveFront() {
/* 356 */     this._emissiveFront = _emissiveDefault;
/* 357 */     this._emissiveFrontSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetEmissiveBack() {
/* 364 */     this._emissiveBack = _emissiveDefault;
/* 365 */     this._emissiveBackSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasShininessFront() {
/* 373 */     return this._shininessFrontSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasShininessBack() {
/* 381 */     return this._shininessBackSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getShininessFront() {
/* 389 */     return this._shininessFront;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getShininessBack() {
/* 397 */     return this._shininessBack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShininess(float shininess) {
/* 405 */     this._shininessFront = this._shininessBack = shininess;
/* 406 */     this._shininessFrontSet = this._shininessBackSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShininessFront(float shininess) {
/* 414 */     this._shininessFront = shininess;
/* 415 */     this._shininessFrontSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShininessBack(float shininess) {
/* 423 */     this._shininessBack = shininess;
/* 424 */     this._shininessBackSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetShininess() {
/* 431 */     this._shininessFront = this._shininessBack = _shininessDefault;
/* 432 */     this._shininessFrontSet = this._shininessBackSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetShininessFront() {
/* 439 */     this._shininessFront = _shininessDefault;
/* 440 */     this._shininessFrontSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetShininessBack() {
/* 447 */     this._shininessBack = _shininessDefault;
/* 448 */     this._shininessBackSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasColorMaterialFront() {
/* 456 */     return this._colorMaterialFrontSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasColorMaterialBack() {
/* 464 */     return this._colorMaterialBackSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColorMaterialFront() {
/* 472 */     return this._colorMaterialFront;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColorMaterialBack() {
/* 480 */     return this._colorMaterialBack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColorMaterial(int mode) {
/* 488 */     this._colorMaterialFront = this._colorMaterialBack = mode;
/* 489 */     this._colorMaterialFrontSet = this._colorMaterialBackSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColorMaterialFront(int mode) {
/* 497 */     this._colorMaterialFront = mode;
/* 498 */     this._colorMaterialFrontSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColorMaterialBack(int mode) {
/* 506 */     this._colorMaterialBack = mode;
/* 507 */     this._colorMaterialBackSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetColorMaterial() {
/* 514 */     this._colorMaterialFront = this._colorMaterialBack = _colorMaterialDefault;
/* 515 */     this._colorMaterialFrontSet = this._colorMaterialBackSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetColorMaterialFront() {
/* 522 */     this._colorMaterialFront = _colorMaterialDefault;
/* 523 */     this._colorMaterialFrontSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetColorMaterialBack() {
/* 530 */     this._colorMaterialBack = _colorMaterialDefault;
/* 531 */     this._colorMaterialBackSet = false;
/*     */   }
/*     */   
/*     */   public void apply() {
/* 535 */     Gl.glEnable(2896);
/* 536 */     if (this._ambientFrontSet && this._ambientBackSet && this._ambientFront == this._ambientBack) {
/* 537 */       Gl.glMaterialfv(1032, 4608, this._ambientFront, 0);
/*     */     } else {
/* 539 */       if (this._ambientFrontSet)
/* 540 */         Gl.glMaterialfv(1028, 4608, this._ambientFront, 0); 
/* 541 */       if (this._ambientBackSet)
/* 542 */         Gl.glMaterialfv(1029, 4608, this._ambientBack, 0); 
/*     */     } 
/* 544 */     if (this._diffuseFrontSet && this._diffuseBackSet && this._diffuseFront == this._diffuseBack) {
/* 545 */       Gl.glMaterialfv(1032, 4609, this._diffuseFront, 0);
/*     */     } else {
/* 547 */       if (this._diffuseFrontSet)
/* 548 */         Gl.glMaterialfv(1028, 4609, this._diffuseFront, 0); 
/* 549 */       if (this._diffuseBackSet)
/* 550 */         Gl.glMaterialfv(1029, 4609, this._diffuseBack, 0); 
/*     */     } 
/* 552 */     if (this._specularFrontSet && this._specularBackSet && this._specularFront == this._specularBack) {
/*     */ 
/*     */       
/* 555 */       Gl.glMaterialfv(1032, 4610, this._specularFront, 0);
/*     */     } else {
/* 557 */       if (this._specularFrontSet)
/* 558 */         Gl.glMaterialfv(1028, 4610, this._specularFront, 0); 
/* 559 */       if (this._specularBackSet)
/* 560 */         Gl.glMaterialfv(1029, 4610, this._specularBack, 0); 
/*     */     } 
/* 562 */     if (this._emissiveFrontSet && this._emissiveBackSet && this._emissiveFront == this._emissiveBack) {
/*     */ 
/*     */       
/* 565 */       Gl.glMaterialfv(1032, 5632, this._emissiveFront, 0);
/*     */     } else {
/* 567 */       if (this._emissiveFrontSet)
/* 568 */         Gl.glMaterialfv(1028, 5632, this._emissiveFront, 0); 
/* 569 */       if (this._emissiveBackSet)
/* 570 */         Gl.glMaterialfv(1029, 5632, this._emissiveBack, 0); 
/*     */     } 
/* 572 */     if (this._shininessFrontSet && this._shininessBackSet && this._shininessFront == this._shininessBack) {
/*     */ 
/*     */       
/* 575 */       Gl.glMaterialf(1032, 5633, this._shininessFront);
/*     */     } else {
/* 577 */       if (this._shininessFrontSet)
/* 578 */         Gl.glMaterialf(1028, 5633, this._shininessFront); 
/* 579 */       if (this._shininessBackSet)
/* 580 */         Gl.glMaterialf(1029, 5633, this._shininessBack); 
/*     */     } 
/* 582 */     if (this._colorMaterialFrontSet || this._colorMaterialBackSet)
/* 583 */       Gl.glEnable(2903); 
/* 584 */     if (this._colorMaterialFrontSet && this._colorMaterialBackSet && this._colorMaterialFront == this._colorMaterialBack) {
/*     */ 
/*     */       
/* 587 */       Gl.glColorMaterial(1032, this._colorMaterialFront);
/*     */     } else {
/* 589 */       if (this._colorMaterialFrontSet)
/* 590 */         Gl.glColorMaterial(1028, this._colorMaterialFront); 
/* 591 */       if (this._colorMaterialBackSet)
/* 592 */         Gl.glColorMaterial(1029, this._colorMaterialBack); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getAttributeBits() {
/* 597 */     return 8256;
/*     */   }
/*     */   
/* 600 */   private static float[] _ambientDefault = new float[] { 0.2F, 0.2F, 0.2F, 1.0F };
/* 601 */   private float[] _ambientFront = _ambientDefault;
/* 602 */   private float[] _ambientBack = _ambientDefault;
/*     */   
/*     */   private boolean _ambientFrontSet;
/*     */   private boolean _ambientBackSet;
/* 606 */   private static float[] _diffuseDefault = new float[] { 0.8F, 0.8F, 0.8F, 1.0F };
/* 607 */   private float[] _diffuseFront = _diffuseDefault;
/* 608 */   private float[] _diffuseBack = _diffuseDefault;
/*     */   
/*     */   private boolean _diffuseFrontSet;
/*     */   private boolean _diffuseBackSet;
/* 612 */   private static float[] _specularDefault = new float[] { 0.0F, 0.0F, 0.0F, 1.0F };
/* 613 */   private float[] _specularFront = _specularDefault;
/* 614 */   private float[] _specularBack = _specularDefault;
/*     */   
/*     */   private boolean _specularFrontSet;
/*     */   private boolean _specularBackSet;
/* 618 */   private static float[] _emissiveDefault = new float[] { 0.0F, 0.0F, 0.0F, 1.0F };
/* 619 */   private float[] _emissiveFront = _emissiveDefault;
/* 620 */   private float[] _emissiveBack = _emissiveDefault;
/*     */   
/*     */   private boolean _emissiveFrontSet;
/*     */   private boolean _emissiveBackSet;
/* 624 */   private static float _shininessDefault = 0.0F;
/* 625 */   private float _shininessFront = _shininessDefault;
/* 626 */   private float _shininessBack = _shininessDefault;
/*     */   
/*     */   private boolean _shininessFrontSet;
/*     */   private boolean _shininessBackSet;
/* 630 */   private static int _colorMaterialDefault = 5634;
/* 631 */   private int _colorMaterialFront = _colorMaterialDefault;
/* 632 */   private int _colorMaterialBack = _colorMaterialDefault;
/*     */   private boolean _colorMaterialFrontSet;
/*     */   private boolean _colorMaterialBackSet;
/*     */   
/*     */   private static float[] toArray(Color c) {
/* 637 */     float r = c.getRed() / 255.0F;
/* 638 */     float g = c.getGreen() / 255.0F;
/* 639 */     float b = c.getBlue() / 255.0F;
/* 640 */     float a = c.getAlpha() / 255.0F;
/* 641 */     return new float[] { r, g, b, a };
/*     */   }
/*     */   
/*     */   private static Color toColor(float[] a) {
/* 645 */     return new Color(a[0], a[1], a[2], a[3]);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/MaterialState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */