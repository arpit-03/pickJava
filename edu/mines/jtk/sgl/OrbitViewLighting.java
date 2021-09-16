/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import edu.mines.jtk.ogl.Gl;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OrbitViewLighting
/*     */ {
/*     */   public enum LightSourceType
/*     */   {
/*  55 */     DIRECTIONAL,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  60 */     POSITIONAL;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OrbitViewLighting() {
/* 393 */     this._lightSet = new boolean[3];
/*     */     
/* 395 */     this._lightTypes = new LightSourceType[3];
/* 396 */     this._ambients = new float[3][4];
/* 397 */     this._speculars = new float[3][4];
/* 398 */     this._diffuses = new float[3][4];
/* 399 */     this._positions = new float[3][3];
/*     */     for (int i = 0; i < 3; i++) {
/*     */       this._positions[i] = _posDefault;
/*     */       this._ambients[i] = _ambientDefault;
/*     */       this._diffuses[i] = _diffuseDefault;
/*     */       this._speculars[i] = _specularDefault;
/*     */       this._lightTypes[i] = LightSourceType.DIRECTIONAL;
/*     */       this._lightSet[i] = false;
/*     */     } 
/*     */     setLight(0, true);
/*     */   }
/*     */   
/*     */   public void toggleLight(int i) {
/*     */     this._lightSet[i] = !this._lightSet[i];
/*     */   }
/*     */   
/*     */   public void toggleLight() {
/*     */     toggleLight(0);
/*     */   }
/*     */   
/*     */   public void setLight(int i, boolean isOn) {
/*     */     this._lightSet[i] = isOn;
/*     */   }
/*     */   
/*     */   public boolean isLightOn() {
/*     */     return isLightOn(0);
/*     */   }
/*     */   
/*     */   public boolean isLightOn(int i) {
/*     */     return this._lightSet[i];
/*     */   }
/*     */   
/*     */   public void setAmbient(float[] rgba) {
/*     */     setAmbient(0, rgba);
/*     */   }
/*     */   
/*     */   public void setAmbient(int i, float[] rgba) {
/*     */     this._ambients[i] = rgba;
/*     */   }
/*     */   
/*     */   public float[] getAmbient() {
/*     */     return getAmbient(0);
/*     */   }
/*     */   
/*     */   public float[] getAmbient(int i) {
/*     */     return this._ambients[i];
/*     */   }
/*     */   
/*     */   public void setSpecular(float[] rgba) {
/*     */     setSpecular(0, rgba);
/*     */   }
/*     */   
/*     */   public void setSpecular(int i, float[] rgba) {
/*     */     this._speculars[i] = rgba;
/*     */   }
/*     */   
/*     */   public float[] getSpecular() {
/*     */     return getSpecular(0);
/*     */   }
/*     */   
/*     */   public float[] getSpecular(int i) {
/*     */     return this._speculars[i];
/*     */   }
/*     */   
/*     */   public void setDiffuse(float[] rgba) {
/*     */     setDiffuse(0, rgba);
/*     */   }
/*     */   
/*     */   public void setDiffuse(int i, float[] rgba) {
/*     */     this._diffuses[i] = rgba;
/*     */   }
/*     */   
/*     */   public float[] getDiffuse() {
/*     */     return getDiffuse(0);
/*     */   }
/*     */   
/*     */   public float[] getDiffuse(int i) {
/*     */     return this._diffuses[i];
/*     */   }
/*     */   
/*     */   public void setAmbientAndDiffuse(float[] rgba) {
/*     */     setAmbientAndDiffuse(0, rgba);
/*     */   }
/*     */   
/*     */   public void setAmbientAndDiffuse(int i, float[] rgba) {
/*     */     setAmbient(i, rgba);
/*     */     setDiffuse(i, rgba);
/*     */   }
/*     */   
/*     */   public void setPosition(float lx, float ly, float lz) {
/*     */     setPosition(0, lx, ly, lz);
/*     */   }
/*     */   
/*     */   public void setPosition(float[] pos) {
/*     */     setPosition(0, pos);
/*     */   }
/*     */   
/*     */   public float[] getPosition() {
/*     */     return getPosition(0);
/*     */   }
/*     */   
/*     */   public void setPosition(int i, float lx, float ly, float lz) {
/*     */     setPosition(i, new float[] { lx, ly, lz });
/*     */   }
/*     */   
/*     */   public void setPosition(int i, float[] pos) {
/*     */     this._positions[i] = pos;
/*     */   }
/*     */   
/*     */   public float[] getPosition(int i) {
/*     */     return this._positions[i];
/*     */   }
/*     */   
/*     */   public void setLightSourceType(LightSourceType type) {
/*     */     setLightSourceType(0, type);
/*     */   }
/*     */   
/*     */   public void setLightSourceType(int i, LightSourceType type) {
/*     */     this._lightTypes[i] = type;
/*     */   }
/*     */   
/*     */   public LightSourceType getLightSourceType() {
/*     */     return getLightSourceType(0);
/*     */   }
/*     */   
/*     */   public LightSourceType getLightSourceType(int i) {
/*     */     return this._lightTypes[i];
/*     */   }
/*     */   
/*     */   public void draw() {
/*     */     for (int i = 0; i < _sources.length; i++) {
/*     */       if (this._lightSet[i]) {
/*     */         float w = (this._lightTypes[i] == LightSourceType.DIRECTIONAL) ? 0.0F : 1.0F;
/*     */         float[] pos = { this._positions[i][0], this._positions[i][1], this._positions[i][2], w };
/*     */         Gl.glLightfv(_sources[i], 4611, pos, 0);
/*     */         Gl.glLightfv(_sources[i], 4608, this._ambients[i], 0);
/*     */         Gl.glLightfv(_sources[i], 4609, this._diffuses[i], 0);
/*     */         Gl.glLightfv(_sources[i], 4610, this._speculars[i], 0);
/*     */         Gl.glEnable(_sources[i]);
/*     */       } else {
/*     */         Gl.glDisable(_sources[i]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/*     */     if (this == o)
/*     */       return true; 
/*     */     if (o == null || getClass() != o.getClass())
/*     */       return false; 
/*     */     OrbitViewLighting that = (OrbitViewLighting)o;
/*     */     if (!Arrays.equals(this._lightSet, that._lightSet))
/*     */       return false; 
/*     */     if (!Arrays.equals((Object[])this._lightTypes, (Object[])that._lightTypes))
/*     */       return false; 
/*     */     if (!Arrays.deepEquals((Object[])this._ambients, (Object[])that._ambients))
/*     */       return false; 
/*     */     if (!Arrays.deepEquals((Object[])this._speculars, (Object[])that._speculars))
/*     */       return false; 
/*     */     if (!Arrays.deepEquals((Object[])this._diffuses, (Object[])that._diffuses))
/*     */       return false; 
/*     */     return Arrays.deepEquals((Object[])this._positions, (Object[])that._positions);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*     */     int result = Arrays.hashCode(this._lightSet);
/*     */     result = 31 * result + Arrays.hashCode((Object[])this._lightTypes);
/*     */     result = 31 * result + Arrays.deepHashCode((Object[])this._ambients);
/*     */     result = 31 * result + Arrays.deepHashCode((Object[])this._speculars);
/*     */     result = 31 * result + Arrays.deepHashCode((Object[])this._diffuses);
/*     */     result = 31 * result + Arrays.deepHashCode((Object[])this._positions);
/*     */     return result;
/*     */   }
/*     */   
/*     */   private static final int[] _sources = new int[] { 16384, 16385, 16386 };
/*     */   private static final float[] _ambientDefault = new float[] { 0.0F, 0.0F, 0.0F, 1.0F };
/*     */   private static final float[] _specularDefault = new float[] { 1.0F, 1.0F, 1.0F, 1.0F };
/*     */   private static final float[] _diffuseDefault = new float[] { 1.0F, 1.0F, 1.0F, 1.0F };
/*     */   private static final float[] _posDefault = new float[] { -0.1F, -0.1F, 1.0F };
/*     */   private boolean[] _lightSet;
/*     */   private LightSourceType[] _lightTypes;
/*     */   private float[][] _ambients;
/*     */   private float[][] _speculars;
/*     */   private float[][] _diffuses;
/*     */   private float[][] _positions;
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/OrbitViewLighting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */