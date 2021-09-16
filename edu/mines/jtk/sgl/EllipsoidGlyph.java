/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import edu.mines.jtk.ogl.Gl;
/*     */ import edu.mines.jtk.ogl.GlDisplayList;
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Direct;
/*     */ import java.nio.FloatBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EllipsoidGlyph
/*     */ {
/*     */   private float[] _m;
/*     */   private int _nv;
/*     */   private float[] _xyz;
/*     */   private GlDisplayList _displayList;
/*     */   
/*     */   public EllipsoidGlyph() {
/*  49 */     this(4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EllipsoidGlyph(int m) {
/*  57 */     makeTransformMatrix();
/*  58 */     makeUnitSphere(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int countVertices() {
/*  66 */     return this._nv;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getVertices() {
/*  75 */     return this._xyz;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw() {
/*  82 */     if (this._displayList == null) {
/*  83 */       FloatBuffer xyz = Direct.newFloatBuffer(3 * this._nv);
/*  84 */       xyz.put(this._xyz); xyz.rewind();
/*  85 */       this._displayList = new GlDisplayList();
/*  86 */       Gl.glEnableClientState(32884);
/*  87 */       Gl.glEnableClientState(32885);
/*  88 */       Gl.glNewList(this._displayList.list(), 4864);
/*  89 */       Gl.glVertexPointer(3, 5126, 0, xyz);
/*  90 */       Gl.glNormalPointer(5126, 0, xyz);
/*  91 */       Gl.glDrawArrays(4, 0, this._nv);
/*  92 */       Gl.glEndList();
/*  93 */       Gl.glDisableClientState(32885);
/*  94 */       Gl.glDisableClientState(32884);
/*     */     } 
/*  96 */     Gl.glCallList(this._displayList.list());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(float cx, float cy, float cz, float r) {
/* 107 */     draw(cx, cy, cz, r, r, r);
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
/*     */   public void draw(float cx, float cy, float cz, float dx, float dy, float dz) {
/* 124 */     Gl.glPushMatrix();
/* 125 */     Gl.glTranslatef(cx, cy, cz);
/* 126 */     Gl.glScalef(dx, dy, dz);
/* 127 */     draw();
/* 128 */     Gl.glPopMatrix();
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
/*     */   public void draw(float cx, float cy, float cz, float ux, float uy, float uz, float vx, float vy, float vz, float wx, float wy, float wz) {
/* 158 */     if ((ux * (vy * wz - vz * wy) + uy * (vz * wx - vx * wz) + uz * (vx * wy - vy * wx)) < 0.0D) {
/* 159 */       ux = -ux; uy = -uy; uz = -uz;
/* 160 */       vx = -vx; vy = -vy; vz = -vz;
/* 161 */       wx = -wx; wy = -wy; wz = -wz;
/*     */     } 
/*     */ 
/*     */     
/* 165 */     this._m[0] = ux; this._m[4] = vx; this._m[8] = wx; this._m[12] = cx;
/* 166 */     this._m[1] = uy; this._m[5] = vy; this._m[9] = wy; this._m[13] = cy;
/* 167 */     this._m[2] = uz; this._m[6] = vz; this._m[10] = wz; this._m[14] = cz;
/*     */ 
/*     */     
/* 170 */     Gl.glPushMatrix();
/* 171 */     Gl.glMultMatrixf(this._m, 0);
/* 172 */     draw();
/* 173 */     Gl.glPopMatrix();
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
/*     */   private void makeTransformMatrix() {
/* 185 */     this._m = new float[16];
/* 186 */     this._m[15] = 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void makeUnitSphere(int m) {
/* 194 */     this._nv = 24;
/* 195 */     for (int i = 0; i < m; i++)
/* 196 */       this._nv *= 4; 
/* 197 */     int n = this._nv * 3;
/* 198 */     this._xyz = new float[n];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 204 */     float xm = -1.0F, x0 = 0.0F, xp = 1.0F;
/* 205 */     float ym = -1.0F, y0 = 0.0F, yp = 1.0F;
/* 206 */     float zm = -1.0F, z0 = 0.0F, zp = 1.0F;
/* 207 */     n = 0;
/* 208 */     n = addTri(xp, y0, z0, x0, yp, z0, x0, y0, zp, m, n);
/* 209 */     n = addTri(xm, y0, z0, x0, y0, zp, x0, yp, z0, m, n);
/* 210 */     n = addTri(xp, y0, z0, x0, y0, zp, x0, ym, z0, m, n);
/* 211 */     n = addTri(xm, y0, z0, x0, ym, z0, x0, y0, zp, m, n);
/* 212 */     n = addTri(xp, y0, z0, x0, y0, zm, x0, yp, z0, m, n);
/* 213 */     n = addTri(xm, y0, z0, x0, yp, z0, x0, y0, zm, m, n);
/* 214 */     n = addTri(xp, y0, z0, x0, ym, z0, x0, y0, zm, m, n);
/* 215 */     n = addTri(xm, y0, z0, x0, y0, zm, x0, ym, z0, m, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int addTri(float xa, float ya, float za, float xb, float yb, float zb, float xc, float yc, float zc, int m, int n) {
/* 224 */     if (m == 0) {
/*     */ 
/*     */       
/* 227 */       this._xyz[n++] = xa; this._xyz[n++] = ya; this._xyz[n++] = za;
/* 228 */       this._xyz[n++] = xb; this._xyz[n++] = yb; this._xyz[n++] = zb;
/* 229 */       this._xyz[n++] = xc; this._xyz[n++] = yc; this._xyz[n++] = zc;
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 236 */       float xab = 0.5F * (xa + xb), yab = 0.5F * (ya + yb), zab = 0.5F * (za + zb);
/* 237 */       float xbc = 0.5F * (xb + xc), ybc = 0.5F * (yb + yc), zbc = 0.5F * (zb + zc);
/* 238 */       float xca = 0.5F * (xc + xa), yca = 0.5F * (yc + ya), zca = 0.5F * (zc + za);
/*     */ 
/*     */       
/* 241 */       float dab = ArrayMath.sqrt(xab * xab + yab * yab + zab * zab);
/* 242 */       float dbc = ArrayMath.sqrt(xbc * xbc + ybc * ybc + zbc * zbc);
/* 243 */       float dca = ArrayMath.sqrt(xca * xca + yca * yca + zca * zca);
/*     */ 
/*     */       
/* 246 */       float sab = 1.0F / dab;
/* 247 */       float sbc = 1.0F / dbc;
/* 248 */       float sca = 1.0F / dca;
/* 249 */       xab *= sab; yab *= sab; zab *= sab;
/* 250 */       xbc *= sbc; ybc *= sbc; zbc *= sbc;
/* 251 */       xca *= sca; yca *= sca; zca *= sca;
/*     */ 
/*     */       
/* 254 */       m--;
/* 255 */       n = addTri(xa, ya, za, xab, yab, zab, xca, yca, zca, m, n);
/* 256 */       n = addTri(xb, yb, zb, xbc, ybc, zbc, xab, yab, zab, m, n);
/* 257 */       n = addTri(xc, yc, zc, xca, yca, zca, xbc, ybc, zbc, m, n);
/* 258 */       n = addTri(xab, yab, zab, xbc, ybc, zbc, xca, yca, zca, m, n);
/*     */     } 
/*     */     
/* 261 */     return n;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/EllipsoidGlyph.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */