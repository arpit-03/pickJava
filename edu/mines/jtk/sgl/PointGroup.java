/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import edu.mines.jtk.ogl.Gl;
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
/*     */ public class PointGroup
/*     */   extends Group
/*     */ {
/*     */   private static final int X = 0;
/*     */   private static final int Y = 1;
/*     */   private static final int Z = 2;
/*     */   private static final int R = 0;
/*     */   private static final int G = 1;
/*     */   private static final int B = 2;
/*     */   private static final int MIN_POINT_PER_NODE = 2048;
/*     */   private float _size;
/*     */   
/*     */   public PointGroup(float[] xyz) {
/*  37 */     this(xyz, (float[])null);
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
/*     */   public PointGroup(float[] xyz, float[] rgb) {
/*  52 */     buildTree(xyz, rgb);
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
/*     */   public PointGroup(float size, float[] xyz) {
/*  64 */     this(size, xyz, null);
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
/*     */   public PointGroup(float size, float[] xyz, float[] rgb) {
/*  80 */     this._size = size;
/*  81 */     buildTree(xyz, rgb);
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
/*     */   private void buildTree(float[] xyz, float[] rgb) {
/*  99 */     BoundingBoxTree bbt = new BoundingBoxTree(2048, xyz);
/* 100 */     buildTree(this, bbt.getRoot(), xyz, rgb);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildTree(Group parent, BoundingBoxTree.Node bbtNode, float[] xyz, float[] rgb) {
/* 106 */     if (bbtNode.isLeaf()) {
/*     */       PointNode pn;
/* 108 */       if (this._size > 0.0F) {
/* 109 */         pn = new PointNode(bbtNode, this._size, xyz, rgb);
/*     */       } else {
/* 111 */         pn = new PointNode(bbtNode, xyz, rgb);
/*     */       } 
/* 113 */       parent.addChild(pn);
/*     */     } else {
/* 115 */       Group group = new Group();
/* 116 */       parent.addChild(group);
/* 117 */       buildTree(group, bbtNode.getLeft(), xyz, rgb);
/* 118 */       buildTree(group, bbtNode.getRight(), xyz, rgb);
/*     */     } 
/*     */   }
/*     */   
/*     */   private class PointNode extends Node {
/*     */     private BoundingSphere _bs;
/*     */     private int _np;
/*     */     private FloatBuffer _vb;
/*     */     private FloatBuffer _nb;
/*     */     private FloatBuffer _cb;
/*     */     
/*     */     public PointNode(BoundingBoxTree.Node bbtNode, float[] xyz, float[] rgb) {
/* 130 */       BoundingBox bb = bbtNode.getBoundingBox();
/* 131 */       this._bs = new BoundingSphere(bb);
/* 132 */       this._np = bbtNode.getSize();
/* 133 */       int np = this._np;
/* 134 */       int nv = np;
/* 135 */       int nc = np;
/* 136 */       int[] index = bbtNode.getIndices();
/* 137 */       this._vb = Direct.newFloatBuffer(3 * nv);
/* 138 */       this._cb = (rgb != null) ? Direct.newFloatBuffer(3 * nc) : null;
/* 139 */       for (int ip = 0, iv = 0, ic = 0; ip < np; ip++) {
/* 140 */         int i = 3 * index[ip];
/* 141 */         this._vb.put(iv++, xyz[i + 0]);
/* 142 */         this._vb.put(iv++, xyz[i + 1]);
/* 143 */         this._vb.put(iv++, xyz[i + 2]);
/* 144 */         if (this._cb != null) {
/* 145 */           this._cb.put(ic++, rgb[i + 0]);
/* 146 */           this._cb.put(ic++, rgb[i + 1]);
/* 147 */           this._cb.put(ic++, rgb[i + 2]);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public PointNode(BoundingBoxTree.Node bbtNode, float size, float[] xyz, float[] rgb) {
/* 155 */       BoundingBox bb = bbtNode.getBoundingBox();
/* 156 */       this._bs = new BoundingSphere(bb);
/* 157 */       this._np = bbtNode.getSize();
/* 158 */       int np = this._np;
/* 159 */       int nv = np;
/* 160 */       int nn = np;
/* 161 */       int nc = np;
/* 162 */       int[] index = bbtNode.getIndices();
/* 163 */       this._vb = Direct.newFloatBuffer(72 * nv);
/* 164 */       this._nb = Direct.newFloatBuffer(72 * nn);
/* 165 */       this._cb = (rgb != null) ? Direct.newFloatBuffer(72 * nc) : null;
/* 166 */       float d = 0.5F * size;
/* 167 */       for (int ip = 0, iv = 0, in = 0, ic = 0; ip < np; ip++) {
/* 168 */         int i = 3 * index[ip];
/* 169 */         float xi = xyz[i + 0];
/* 170 */         float yi = xyz[i + 1];
/* 171 */         float zi = xyz[i + 2];
/*     */         
/* 173 */         this._vb.put(iv++, xi - d); this._vb.put(iv++, yi - d); this._vb.put(iv++, zi - d);
/* 174 */         this._vb.put(iv++, xi - d); this._vb.put(iv++, yi - d); this._vb.put(iv++, zi + d);
/* 175 */         this._vb.put(iv++, xi - d); this._vb.put(iv++, yi + d); this._vb.put(iv++, zi + d);
/* 176 */         this._vb.put(iv++, xi - d); this._vb.put(iv++, yi + d); this._vb.put(iv++, zi - d);
/*     */         
/* 178 */         this._vb.put(iv++, xi - d); this._vb.put(iv++, yi - d); this._vb.put(iv++, zi - d);
/* 179 */         this._vb.put(iv++, xi + d); this._vb.put(iv++, yi - d); this._vb.put(iv++, zi - d);
/* 180 */         this._vb.put(iv++, xi + d); this._vb.put(iv++, yi - d); this._vb.put(iv++, zi + d);
/* 181 */         this._vb.put(iv++, xi - d); this._vb.put(iv++, yi - d); this._vb.put(iv++, zi + d);
/*     */         
/* 183 */         this._vb.put(iv++, xi - d); this._vb.put(iv++, yi - d); this._vb.put(iv++, zi - d);
/* 184 */         this._vb.put(iv++, xi - d); this._vb.put(iv++, yi + d); this._vb.put(iv++, zi - d);
/* 185 */         this._vb.put(iv++, xi + d); this._vb.put(iv++, yi + d); this._vb.put(iv++, zi - d);
/* 186 */         this._vb.put(iv++, xi + d); this._vb.put(iv++, yi - d); this._vb.put(iv++, zi - d);
/*     */         
/* 188 */         this._vb.put(iv++, xi + d); this._vb.put(iv++, yi - d); this._vb.put(iv++, zi - d);
/* 189 */         this._vb.put(iv++, xi + d); this._vb.put(iv++, yi + d); this._vb.put(iv++, zi - d);
/* 190 */         this._vb.put(iv++, xi + d); this._vb.put(iv++, yi + d); this._vb.put(iv++, zi + d);
/* 191 */         this._vb.put(iv++, xi + d); this._vb.put(iv++, yi - d); this._vb.put(iv++, zi + d);
/*     */         
/* 193 */         this._vb.put(iv++, xi - d); this._vb.put(iv++, yi + d); this._vb.put(iv++, zi - d);
/* 194 */         this._vb.put(iv++, xi - d); this._vb.put(iv++, yi + d); this._vb.put(iv++, zi + d);
/* 195 */         this._vb.put(iv++, xi + d); this._vb.put(iv++, yi + d); this._vb.put(iv++, zi + d);
/* 196 */         this._vb.put(iv++, xi + d); this._vb.put(iv++, yi + d); this._vb.put(iv++, zi - d);
/*     */         
/* 198 */         this._vb.put(iv++, xi - d); this._vb.put(iv++, yi - d); this._vb.put(iv++, zi + d);
/* 199 */         this._vb.put(iv++, xi + d); this._vb.put(iv++, yi - d); this._vb.put(iv++, zi + d);
/* 200 */         this._vb.put(iv++, xi + d); this._vb.put(iv++, yi + d); this._vb.put(iv++, zi + d);
/* 201 */         this._vb.put(iv++, xi - d); this._vb.put(iv++, yi + d); this._vb.put(iv++, zi + d);
/*     */         
/* 203 */         this._nb.put(in++, -1.0F); this._nb.put(in++, 0.0F); this._nb.put(in++, 0.0F);
/* 204 */         this._nb.put(in++, -1.0F); this._nb.put(in++, 0.0F); this._nb.put(in++, 0.0F);
/* 205 */         this._nb.put(in++, -1.0F); this._nb.put(in++, 0.0F); this._nb.put(in++, 0.0F);
/* 206 */         this._nb.put(in++, -1.0F); this._nb.put(in++, 0.0F); this._nb.put(in++, 0.0F);
/*     */         
/* 208 */         this._nb.put(in++, 0.0F); this._nb.put(in++, -1.0F); this._nb.put(in++, 0.0F);
/* 209 */         this._nb.put(in++, 0.0F); this._nb.put(in++, -1.0F); this._nb.put(in++, 0.0F);
/* 210 */         this._nb.put(in++, 0.0F); this._nb.put(in++, -1.0F); this._nb.put(in++, 0.0F);
/* 211 */         this._nb.put(in++, 0.0F); this._nb.put(in++, -1.0F); this._nb.put(in++, 0.0F);
/*     */         
/* 213 */         this._nb.put(in++, 0.0F); this._nb.put(in++, 0.0F); this._nb.put(in++, -1.0F);
/* 214 */         this._nb.put(in++, 0.0F); this._nb.put(in++, 0.0F); this._nb.put(in++, -1.0F);
/* 215 */         this._nb.put(in++, 0.0F); this._nb.put(in++, 0.0F); this._nb.put(in++, -1.0F);
/* 216 */         this._nb.put(in++, 0.0F); this._nb.put(in++, 0.0F); this._nb.put(in++, -1.0F);
/*     */         
/* 218 */         this._nb.put(in++, 1.0F); this._nb.put(in++, 0.0F); this._nb.put(in++, 0.0F);
/* 219 */         this._nb.put(in++, 1.0F); this._nb.put(in++, 0.0F); this._nb.put(in++, 0.0F);
/* 220 */         this._nb.put(in++, 1.0F); this._nb.put(in++, 0.0F); this._nb.put(in++, 0.0F);
/* 221 */         this._nb.put(in++, 1.0F); this._nb.put(in++, 0.0F); this._nb.put(in++, 0.0F);
/*     */         
/* 223 */         this._nb.put(in++, 0.0F); this._nb.put(in++, 1.0F); this._nb.put(in++, 0.0F);
/* 224 */         this._nb.put(in++, 0.0F); this._nb.put(in++, 1.0F); this._nb.put(in++, 0.0F);
/* 225 */         this._nb.put(in++, 0.0F); this._nb.put(in++, 1.0F); this._nb.put(in++, 0.0F);
/* 226 */         this._nb.put(in++, 0.0F); this._nb.put(in++, 1.0F); this._nb.put(in++, 0.0F);
/*     */         
/* 228 */         this._nb.put(in++, 0.0F); this._nb.put(in++, 0.0F); this._nb.put(in++, 1.0F);
/* 229 */         this._nb.put(in++, 0.0F); this._nb.put(in++, 0.0F); this._nb.put(in++, 1.0F);
/* 230 */         this._nb.put(in++, 0.0F); this._nb.put(in++, 0.0F); this._nb.put(in++, 1.0F);
/* 231 */         this._nb.put(in++, 0.0F); this._nb.put(in++, 0.0F); this._nb.put(in++, 1.0F);
/*     */         
/* 233 */         if (this._cb != null) {
/* 234 */           float ri = rgb[i + 0];
/* 235 */           float gi = rgb[i + 1];
/* 236 */           float bi = rgb[i + 2];
/* 237 */           for (int j = 0; j < 24; j++) {
/* 238 */             this._cb.put(ic++, ri);
/* 239 */             this._cb.put(ic++, gi);
/* 240 */             this._cb.put(ic++, bi);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     protected BoundingSphere computeBoundingSphere(boolean finite) {
/* 247 */       return this._bs;
/*     */     }
/*     */     
/*     */     protected void draw(DrawContext dc) {
/* 251 */       Gl.glEnableClientState(32884);
/* 252 */       Gl.glVertexPointer(3, 5126, 0, this._vb);
/* 253 */       if (this._nb != null) {
/* 254 */         Gl.glEnableClientState(32885);
/* 255 */         Gl.glNormalPointer(5126, 0, this._nb);
/*     */       } 
/* 257 */       if (this._cb != null) {
/* 258 */         Gl.glEnableClientState(32886);
/* 259 */         Gl.glColorPointer(3, 5126, 0, this._cb);
/*     */       } 
/* 261 */       if (PointGroup.this._size > 0.0F) {
/* 262 */         Gl.glDrawArrays(7, 0, 24 * this._np);
/*     */       } else {
/* 264 */         Gl.glDrawArrays(0, 0, this._np);
/*     */       } 
/* 266 */       if (this._cb != null)
/* 267 */         Gl.glDisableClientState(32886); 
/* 268 */       if (this._nb != null)
/* 269 */         Gl.glDisableClientState(32885); 
/* 270 */       Gl.glDisableClientState(32884);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/PointGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */