/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import edu.mines.jtk.dsp.Sampling;
/*     */ import edu.mines.jtk.ogl.Gl;
/*     */ import edu.mines.jtk.util.Direct;
/*     */ import java.awt.Color;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TriangleGroup
/*     */   extends Group
/*     */   implements Selectable
/*     */ {
/*     */   private static final int I = 0;
/*     */   private static final int J = 1;
/*     */   private static final int K = 2;
/*     */   private static final int X = 0;
/*     */   private static final int Y = 1;
/*     */   private static final int Z = 2;
/*     */   private static final int U = 0;
/*     */   private static final int V = 1;
/*     */   private static final int W = 2;
/*     */   private static final int R = 0;
/*     */   private static final int G = 1;
/*     */   private static final int B = 2;
/*     */   private static final int MIN_TRI_PER_NODE = 1024;
/*     */   
/*     */   public TriangleGroup(boolean vn, float[] xyz) {
/*  64 */     this(vn, xyz, (float[])null);
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
/*     */   public TriangleGroup(boolean vn, float[] xyz, float[] rgb) {
/*  89 */     int[] ijk = indexVertices(!vn, xyz);
/*  90 */     float[] uvw = computeNormals(ijk, xyz);
/*  91 */     buildTree(ijk, xyz, uvw, rgb);
/*  92 */     setDefaultStates();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TriangleGroup(boolean vn, Sampling sx, Sampling sy, float[][] z) {
/* 103 */     this(vn, makeVertices(sx, sy, z));
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
/*     */   public TriangleGroup(boolean vn, Sampling sx, Sampling sy, float[][] z, float[][] r, float[][] g, float[][] b) {
/* 120 */     this(vn, makeVertices(sx, sy, z), makeColors(r, g, b));
/*     */   }
/*     */   private static float[] makeVertices(Sampling sx, Sampling sy, float[][] z) {
/* 123 */     int nx = sx.getCount() - 1;
/* 124 */     int ny = sy.getCount() - 1;
/* 125 */     float[] xyz = new float[18 * nx * ny];
/* 126 */     for (int ix = 0, i = 0; ix < nx; ix++) {
/* 127 */       float x0 = (float)sx.getValue(ix);
/* 128 */       float x1 = (float)sx.getValue(ix + 1);
/* 129 */       for (int iy = 0; iy < ny; iy++) {
/* 130 */         float y0 = (float)sy.getValue(iy);
/* 131 */         float y1 = (float)sy.getValue(iy + 1);
/* 132 */         xyz[i++] = x0; xyz[i++] = y0; xyz[i++] = z[ix][iy];
/* 133 */         xyz[i++] = x0; xyz[i++] = y1; xyz[i++] = z[ix][iy + 1];
/* 134 */         xyz[i++] = x1; xyz[i++] = y0; xyz[i++] = z[ix + 1][iy];
/* 135 */         xyz[i++] = x1; xyz[i++] = y0; xyz[i++] = z[ix + 1][iy];
/* 136 */         xyz[i++] = x0; xyz[i++] = y1; xyz[i++] = z[ix][iy + 1];
/* 137 */         xyz[i++] = x1; xyz[i++] = y1; xyz[i++] = z[ix + 1][iy + 1];
/*     */       } 
/*     */     } 
/* 140 */     return xyz;
/*     */   }
/*     */   private static float[] makeColors(float[][] r, float[][] g, float[][] b) {
/* 143 */     int nx = r.length - 1;
/* 144 */     int ny = (r[0]).length - 1;
/* 145 */     float[] rgb = new float[18 * nx * ny];
/* 146 */     for (int ix = 0, i = 0; ix < nx; ix++) {
/* 147 */       for (int iy = 0; iy < ny; iy++) {
/* 148 */         rgb[i++] = r[ix][iy];
/* 149 */         rgb[i++] = g[ix][iy];
/* 150 */         rgb[i++] = b[ix][iy];
/* 151 */         rgb[i++] = r[ix][iy + 1];
/* 152 */         rgb[i++] = g[ix][iy + 1];
/* 153 */         rgb[i++] = b[ix][iy + 1];
/* 154 */         rgb[i++] = r[ix + 1][iy];
/* 155 */         rgb[i++] = g[ix + 1][iy];
/* 156 */         rgb[i++] = b[ix + 1][iy];
/* 157 */         rgb[i++] = r[ix + 1][iy];
/* 158 */         rgb[i++] = g[ix + 1][iy];
/* 159 */         rgb[i++] = b[ix + 1][iy];
/* 160 */         rgb[i++] = r[ix][iy + 1];
/* 161 */         rgb[i++] = g[ix][iy + 1];
/* 162 */         rgb[i++] = b[ix][iy + 1];
/* 163 */         rgb[i++] = r[ix + 1][iy + 1];
/* 164 */         rgb[i++] = g[ix + 1][iy + 1];
/* 165 */         rgb[i++] = b[ix + 1][iy + 1];
/*     */       } 
/*     */     } 
/* 168 */     return rgb;
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
/*     */   public TriangleGroup(int[] ijk, float[] xyz) {
/* 188 */     this(ijk, xyz, (float[])null);
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
/*     */   public TriangleGroup(float[] xyz, float[] uvw) {
/* 203 */     this(indexVertices(true, xyz), xyz, uvw, (float[])null);
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
/*     */   public TriangleGroup(int[] ijk, float[] xyz, float[] uvw) {
/* 223 */     this(ijk, xyz, uvw, (float[])null);
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
/*     */   public TriangleGroup(float[] xyz, float[] uvw, float[] rgb) {
/* 243 */     this(indexVertices(true, xyz), xyz, uvw, rgb);
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
/*     */   public TriangleGroup(int[] ijk, float[] xyz, float[] uvw, float[] rgb) {
/* 269 */     if (uvw == null)
/* 270 */       uvw = computeNormals(ijk, xyz); 
/* 271 */     buildTree(ijk, xyz, uvw, rgb);
/* 272 */     setDefaultStates();
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
/*     */   public static int[] indexVertices(boolean sequential, float[] xyz) {
/* 308 */     int nv = xyz.length / 3;
/* 309 */     int nt = nv / 3;
/*     */ 
/*     */     
/* 312 */     int[] ijk = new int[nv];
/*     */ 
/*     */ 
/*     */     
/* 316 */     if (sequential) {
/* 317 */       for (int iv = 0; iv < nv; iv++)
/* 318 */         ijk[iv] = iv; 
/*     */     } else {
/* 320 */       HashMap<Vertex, Integer> vimap = new HashMap<>(nv);
/* 321 */       for (int it = 0; it < nt; it++) {
/* 322 */         for (int iv = 0, jv = 3 * it, kv = 3 * jv; iv < 3; iv++, jv++, kv += 3) {
/* 323 */           Vertex v = new Vertex(xyz[kv + 0], xyz[kv + 1], xyz[kv + 2]);
/* 324 */           Integer i = vimap.get(v);
/* 325 */           if (i == null) {
/* 326 */             i = Integer.valueOf(jv);
/* 327 */             vimap.put(v, i);
/*     */           } 
/* 329 */           ijk[jv] = i.intValue();
/*     */         } 
/*     */       } 
/*     */     } 
/* 333 */     return ijk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor(Color color) {
/* 343 */     StateSet states = getStates();
/* 344 */     ColorState cs = (ColorState)states.find(ColorState.class);
/* 345 */     if (cs == null)
/* 346 */       cs = new ColorState(); 
/* 347 */     cs.setColor(color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void selectedChanged() {
/* 354 */     System.out.println("TriangleGroup: " + this + " selected=" + isSelected());
/* 355 */     dirtyDraw();
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
/*     */   private void buildTree(int[] ijk, float[] xyz, float[] uvw, float[] rgb) {
/* 373 */     float[] c = computeCenters(ijk, xyz);
/* 374 */     BoundingBoxTree bbt = new BoundingBoxTree(1024, c);
/* 375 */     buildTree(this, bbt.getRoot(), ijk, xyz, uvw, rgb);
/*     */   }
/*     */ 
/*     */   
/*     */   private void buildTree(Group parent, BoundingBoxTree.Node bbtNode, int[] ijk, float[] xyz, float[] uvw, float[] rgb) {
/* 380 */     if (bbtNode.isLeaf()) {
/* 381 */       TriangleNode tn = new TriangleNode(bbtNode, ijk, xyz, uvw, rgb);
/* 382 */       parent.addChild(tn);
/*     */     } else {
/* 384 */       Group group = new Group();
/* 385 */       parent.addChild(group);
/* 386 */       buildTree(group, bbtNode.getLeft(), ijk, xyz, uvw, rgb);
/* 387 */       buildTree(group, bbtNode.getRight(), ijk, xyz, uvw, rgb);
/*     */     } 
/*     */   }
/*     */   
/*     */   private class TriangleNode extends Node {
/*     */     private BoundingSphere _bs;
/*     */     private int _nt;
/*     */     private FloatBuffer _vb;
/*     */     private FloatBuffer _nb;
/*     */     private FloatBuffer _cb;
/*     */     
/*     */     public TriangleNode(BoundingBoxTree.Node bbtNode, int[] ijk, float[] xyz, float[] uvw, float[] rgb) {
/* 399 */       BoundingBox bb = bbtNode.getBoundingBox();
/* 400 */       this._bs = new BoundingSphere(bb);
/* 401 */       this._nt = bbtNode.getSize();
/* 402 */       int nt = this._nt;
/* 403 */       int nv = 3 * nt;
/* 404 */       int nn = 3 * nt;
/* 405 */       int nc = 3 * nt;
/* 406 */       int[] index = bbtNode.getIndices();
/* 407 */       this._vb = Direct.newFloatBuffer(3 * nv);
/* 408 */       this._nb = (uvw != null) ? Direct.newFloatBuffer(3 * nn) : null;
/* 409 */       this._cb = (rgb != null) ? Direct.newFloatBuffer(3 * nc) : null;
/* 410 */       for (int it = 0, iv = 0, in = 0, ic = 0; it < nt; it++) {
/* 411 */         int jt = 3 * index[it];
/* 412 */         int i = 3 * ijk[jt + 0];
/* 413 */         int j = 3 * ijk[jt + 1];
/* 414 */         int k = 3 * ijk[jt + 2];
/* 415 */         this._vb.put(iv++, xyz[i + 0]);
/* 416 */         this._vb.put(iv++, xyz[i + 1]);
/* 417 */         this._vb.put(iv++, xyz[i + 2]);
/* 418 */         this._vb.put(iv++, xyz[j + 0]);
/* 419 */         this._vb.put(iv++, xyz[j + 1]);
/* 420 */         this._vb.put(iv++, xyz[j + 2]);
/* 421 */         this._vb.put(iv++, xyz[k + 0]);
/* 422 */         this._vb.put(iv++, xyz[k + 1]);
/* 423 */         this._vb.put(iv++, xyz[k + 2]);
/* 424 */         if (this._nb != null) {
/* 425 */           this._nb.put(in++, uvw[i + 0]);
/* 426 */           this._nb.put(in++, uvw[i + 1]);
/* 427 */           this._nb.put(in++, uvw[i + 2]);
/* 428 */           this._nb.put(in++, uvw[j + 0]);
/* 429 */           this._nb.put(in++, uvw[j + 1]);
/* 430 */           this._nb.put(in++, uvw[j + 2]);
/* 431 */           this._nb.put(in++, uvw[k + 0]);
/* 432 */           this._nb.put(in++, uvw[k + 1]);
/* 433 */           this._nb.put(in++, uvw[k + 2]);
/*     */         } 
/* 435 */         if (this._cb != null) {
/* 436 */           this._cb.put(ic++, rgb[i + 0]);
/* 437 */           this._cb.put(ic++, rgb[i + 1]);
/* 438 */           this._cb.put(ic++, rgb[i + 2]);
/* 439 */           this._cb.put(ic++, rgb[j + 0]);
/* 440 */           this._cb.put(ic++, rgb[j + 1]);
/* 441 */           this._cb.put(ic++, rgb[j + 2]);
/* 442 */           this._cb.put(ic++, rgb[k + 0]);
/* 443 */           this._cb.put(ic++, rgb[k + 1]);
/* 444 */           this._cb.put(ic++, rgb[k + 2]);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     protected BoundingSphere computeBoundingSphere(boolean finite) {
/* 450 */       return this._bs;
/*     */     }
/*     */     
/*     */     protected void draw(DrawContext dc) {
/* 454 */       boolean selected = TriangleGroup.this.isSelected();
/* 455 */       Gl.glEnableClientState(32884);
/* 456 */       Gl.glVertexPointer(3, 5126, 0, this._vb);
/* 457 */       if (this._nb != null) {
/* 458 */         Gl.glEnableClientState(32885);
/* 459 */         Gl.glNormalPointer(5126, 0, this._nb);
/*     */       } 
/* 461 */       if (this._cb != null) {
/* 462 */         Gl.glEnableClientState(32886);
/* 463 */         Gl.glColorPointer(3, 5126, 0, this._cb);
/*     */       } 
/* 465 */       if (selected) {
/* 466 */         Gl.glEnable(32823);
/* 467 */         Gl.glPolygonOffset(1.0F, 1.0F);
/*     */       } 
/* 469 */       Gl.glDrawArrays(4, 0, 3 * this._nt);
/* 470 */       if (this._nb != null)
/* 471 */         Gl.glDisableClientState(32885); 
/* 472 */       if (this._cb != null)
/* 473 */         Gl.glDisableClientState(32886); 
/* 474 */       if (selected) {
/* 475 */         Gl.glPolygonMode(1032, 6913);
/* 476 */         Gl.glDisable(2896);
/* 477 */         Gl.glColor3d(1.0D, 1.0D, 1.0D);
/* 478 */         Gl.glDrawArrays(4, 0, 3 * this._nt);
/*     */       } 
/* 480 */       Gl.glDisableClientState(32884);
/*     */     }
/*     */     
/*     */     public void pick(PickContext pc) {
/* 484 */       Segment ps = pc.getPickSegment();
/* 485 */       for (int it = 0, jt = 0; it < this._nt; it++) {
/* 486 */         double xi = this._vb.get(jt++);
/* 487 */         double yi = this._vb.get(jt++);
/* 488 */         double zi = this._vb.get(jt++);
/* 489 */         double xj = this._vb.get(jt++);
/* 490 */         double yj = this._vb.get(jt++);
/* 491 */         double zj = this._vb.get(jt++);
/* 492 */         double xk = this._vb.get(jt++);
/* 493 */         double yk = this._vb.get(jt++);
/* 494 */         double zk = this._vb.get(jt++);
/* 495 */         Point3 p = ps.intersectWithTriangle(xi, yi, zi, xj, yj, zj, xk, yk, zk);
/* 496 */         if (p != null) {
/* 497 */           pc.addResult(p);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class Vertex
/*     */   {
/*     */     float x;
/*     */     float y;
/*     */     float z;
/*     */     
/*     */     Vertex(float x, float y, float z) {
/* 511 */       this.x = x;
/* 512 */       this.y = y;
/* 513 */       this.z = z;
/*     */     }
/*     */     public boolean equals(Object o) {
/* 516 */       Vertex v = (Vertex)o;
/* 517 */       return (this.x == v.x && this.y == v.y && this.z == v.z);
/*     */     }
/*     */     public int hashCode() {
/* 520 */       return Float.floatToIntBits(this.x) ^ 
/* 521 */         Float.floatToIntBits(this.y) ^ 
/* 522 */         Float.floatToIntBits(this.z);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float[] computeNormals(int[] ijk, float[] xyz) {
/* 550 */     int nv = xyz.length / 3;
/* 551 */     int nt = ijk.length / 3;
/* 552 */     float[] uvw = new float[3 * nv];
/*     */ 
/*     */     
/* 555 */     for (int it = 0, jt = 0; it < nt; it++) {
/*     */ 
/*     */       
/* 558 */       int i = 3 * ijk[jt++];
/* 559 */       int j = 3 * ijk[jt++];
/* 560 */       int k = 3 * ijk[jt++];
/* 561 */       float xi = xyz[i + 0];
/* 562 */       float yi = xyz[i + 1];
/* 563 */       float zi = xyz[i + 2];
/* 564 */       float xj = xyz[j + 0];
/* 565 */       float yj = xyz[j + 1];
/* 566 */       float zj = xyz[j + 2];
/* 567 */       float xk = xyz[k + 0];
/* 568 */       float yk = xyz[k + 1];
/* 569 */       float zk = xyz[k + 2];
/*     */ 
/*     */ 
/*     */       
/* 573 */       float xa = xj - xi;
/* 574 */       float ya = yj - yi;
/* 575 */       float za = zj - zi;
/* 576 */       float xb = xk - xi;
/* 577 */       float yb = yk - yi;
/* 578 */       float zb = zk - zi;
/* 579 */       float un = ya * zb - yb * za;
/* 580 */       float vn = za * xb - zb * xa;
/* 581 */       float wn = xa * yb - xb * ya;
/*     */ 
/*     */ 
/*     */       
/* 585 */       uvw[i + 0] = uvw[i + 0] + un;
/* 586 */       uvw[i + 1] = uvw[i + 1] + vn;
/* 587 */       uvw[i + 2] = uvw[i + 2] + wn;
/* 588 */       uvw[j + 0] = uvw[j + 0] + un;
/* 589 */       uvw[j + 1] = uvw[j + 1] + vn;
/* 590 */       uvw[j + 2] = uvw[j + 2] + wn;
/* 591 */       uvw[k + 0] = uvw[k + 0] + un;
/* 592 */       uvw[k + 1] = uvw[k + 1] + vn;
/* 593 */       uvw[k + 2] = uvw[k + 2] + wn;
/*     */     } 
/* 595 */     return uvw;
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
/*     */   private float[] computeCenters(int[] ijk, float[] xyz) {
/* 616 */     int nt = ijk.length / 3;
/* 617 */     float[] c = new float[3 * nt];
/* 618 */     float o3 = 0.33333334F;
/* 619 */     for (int it = 0, jt = 0, jc = 0; it < nt; it++) {
/* 620 */       int i = 3 * ijk[jt++];
/* 621 */       int j = 3 * ijk[jt++];
/* 622 */       int k = 3 * ijk[jt++];
/* 623 */       float xi = xyz[i + 0];
/* 624 */       float yi = xyz[i + 1];
/* 625 */       float zi = xyz[i + 2];
/* 626 */       float xj = xyz[j + 0];
/* 627 */       float yj = xyz[j + 1];
/* 628 */       float zj = xyz[j + 2];
/* 629 */       float xk = xyz[k + 0];
/* 630 */       float yk = xyz[k + 1];
/* 631 */       float zk = xyz[k + 2];
/* 632 */       c[jc++] = (xi + xj + xk) * o3;
/* 633 */       c[jc++] = (yi + yj + yk) * o3;
/* 634 */       c[jc++] = (zi + zj + zk) * o3;
/*     */     } 
/* 636 */     return c;
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
/*     */   private static StateSet defaultStateSet(Color color) {
/* 676 */     StateSet states = new StateSet();
/* 677 */     ColorState cs = new ColorState();
/* 678 */     cs.setColor(color);
/* 679 */     LightModelState lms = new LightModelState();
/* 680 */     lms.setTwoSide(true);
/* 681 */     MaterialState ms = new MaterialState();
/* 682 */     ms.setColorMaterial(5634);
/* 683 */     ms.setSpecular(Color.WHITE);
/* 684 */     ms.setShininess(100.0F);
/* 685 */     states.add(cs);
/* 686 */     states.add(lms);
/* 687 */     states.add(ms);
/* 688 */     return states;
/*     */   }
/*     */   
/*     */   private void setDefaultStates() {
/* 692 */     setStates(defaultStateSet(Color.LIGHT_GRAY));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/TriangleGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */