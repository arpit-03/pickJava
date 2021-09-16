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
/*     */ public class QuadGroup
/*     */   extends Group
/*     */   implements Selectable
/*     */ {
/*     */   private static final int I = 0;
/*     */   private static final int J = 1;
/*     */   private static final int K = 2;
/*     */   private static final int L = 3;
/*     */   private static final int X = 0;
/*     */   private static final int Y = 1;
/*     */   private static final int Z = 2;
/*     */   private static final int U = 0;
/*     */   private static final int V = 1;
/*     */   private static final int W = 2;
/*     */   private static final int R = 0;
/*     */   private static final int G = 1;
/*     */   private static final int B = 2;
/*     */   private static final int MIN_QUAD_PER_NODE = 1024;
/*     */   
/*     */   public QuadGroup(boolean vn, float[] xyz) {
/*  63 */     this(vn, xyz, (float[])null);
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
/*     */   public QuadGroup(boolean vn, float[] xyz, float[] rgb) {
/*  88 */     int[] ijkl = indexVertices(!vn, xyz);
/*  89 */     float[] uvw = computeNormals(ijkl, xyz);
/*  90 */     buildTree(ijkl, xyz, uvw, rgb);
/*  91 */     setDefaultStates();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuadGroup(boolean vn, Sampling sx, Sampling sy, float[][] z) {
/* 102 */     this(vn, makeVertices(sx, sy, z));
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
/*     */   public QuadGroup(boolean vn, Sampling sx, Sampling sy, float[][] z, float[][] r, float[][] g, float[][] b) {
/* 119 */     this(vn, makeVertices(sx, sy, z), makeColors(r, g, b));
/*     */   }
/*     */   private static float[] makeVertices(Sampling sx, Sampling sy, float[][] z) {
/* 122 */     int nx = sx.getCount() - 1;
/* 123 */     int ny = sy.getCount() - 1;
/* 124 */     float[] xyz = new float[18 * nx * ny];
/* 125 */     for (int ix = 0, i = 0; ix < nx; ix++) {
/* 126 */       float x0 = (float)sx.getValue(ix);
/* 127 */       float x1 = (float)sx.getValue(ix + 1);
/* 128 */       for (int iy = 0; iy < ny; iy++) {
/* 129 */         float y0 = (float)sy.getValue(iy);
/* 130 */         float y1 = (float)sy.getValue(iy + 1);
/* 131 */         xyz[i++] = x0; xyz[i++] = y0; xyz[i++] = z[ix][iy];
/* 132 */         xyz[i++] = x0; xyz[i++] = y1; xyz[i++] = z[ix][iy + 1];
/* 133 */         xyz[i++] = x1; xyz[i++] = y0; xyz[i++] = z[ix + 1][iy];
/* 134 */         xyz[i++] = x1; xyz[i++] = y0; xyz[i++] = z[ix + 1][iy];
/* 135 */         xyz[i++] = x0; xyz[i++] = y1; xyz[i++] = z[ix][iy + 1];
/* 136 */         xyz[i++] = x1; xyz[i++] = y1; xyz[i++] = z[ix + 1][iy + 1];
/*     */       } 
/*     */     } 
/* 139 */     return xyz;
/*     */   }
/*     */   private static float[] makeColors(float[][] r, float[][] g, float[][] b) {
/* 142 */     int nx = r.length - 1;
/* 143 */     int ny = (r[0]).length - 1;
/* 144 */     float[] rgb = new float[18 * nx * ny];
/* 145 */     for (int ix = 0, i = 0; ix < nx; ix++) {
/* 146 */       for (int iy = 0; iy < ny; iy++) {
/* 147 */         rgb[i++] = r[ix][iy];
/* 148 */         rgb[i++] = g[ix][iy];
/* 149 */         rgb[i++] = b[ix][iy];
/* 150 */         rgb[i++] = r[ix][iy + 1];
/* 151 */         rgb[i++] = g[ix][iy + 1];
/* 152 */         rgb[i++] = b[ix][iy + 1];
/* 153 */         rgb[i++] = r[ix + 1][iy];
/* 154 */         rgb[i++] = g[ix + 1][iy];
/* 155 */         rgb[i++] = b[ix + 1][iy];
/* 156 */         rgb[i++] = r[ix + 1][iy];
/* 157 */         rgb[i++] = g[ix + 1][iy];
/* 158 */         rgb[i++] = b[ix + 1][iy];
/* 159 */         rgb[i++] = r[ix][iy + 1];
/* 160 */         rgb[i++] = g[ix][iy + 1];
/* 161 */         rgb[i++] = b[ix][iy + 1];
/* 162 */         rgb[i++] = r[ix + 1][iy + 1];
/* 163 */         rgb[i++] = g[ix + 1][iy + 1];
/* 164 */         rgb[i++] = b[ix + 1][iy + 1];
/*     */       } 
/*     */     } 
/* 167 */     return rgb;
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
/*     */   public QuadGroup(int[] ijkl, float[] xyz) {
/* 187 */     this(ijkl, xyz, (float[])null);
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
/*     */   public QuadGroup(float[] xyz, float[] uvw) {
/* 202 */     this(indexVertices(true, xyz), xyz, uvw, (float[])null);
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
/*     */   public QuadGroup(int[] ijkl, float[] xyz, float[] uvw) {
/* 222 */     this(ijkl, xyz, uvw, (float[])null);
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
/*     */   public QuadGroup(float[] xyz, float[] uvw, float[] rgb) {
/* 242 */     this(indexVertices(true, xyz), xyz, uvw, rgb);
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
/*     */   public QuadGroup(int[] ijkl, float[] xyz, float[] uvw, float[] rgb) {
/* 268 */     if (uvw == null)
/* 269 */       uvw = computeNormals(ijkl, xyz); 
/* 270 */     buildTree(ijkl, xyz, uvw, rgb);
/* 271 */     setDefaultStates();
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
/* 307 */     int nv = xyz.length / 3;
/* 308 */     int nq = nv / 4;
/*     */ 
/*     */     
/* 311 */     int[] ijkl = new int[nv];
/*     */ 
/*     */ 
/*     */     
/* 315 */     if (sequential) {
/* 316 */       for (int iv = 0; iv < nv; iv++)
/* 317 */         ijkl[iv] = iv; 
/*     */     } else {
/* 319 */       HashMap<Vertex, Integer> vimap = new HashMap<>(nv);
/* 320 */       for (int iq = 0; iq < nq; iq++) {
/* 321 */         for (int iv = 0, jv = 4 * iq, kv = 3 * jv; iv < 4; iv++, jv++, kv += 3) {
/* 322 */           Vertex v = new Vertex(xyz[kv + 0], xyz[kv + 1], xyz[kv + 2]);
/* 323 */           Integer i = vimap.get(v);
/* 324 */           if (i == null) {
/* 325 */             i = Integer.valueOf(jv);
/* 326 */             vimap.put(v, i);
/*     */           } 
/* 328 */           ijkl[jv] = i.intValue();
/*     */         } 
/*     */       } 
/*     */     } 
/* 332 */     return ijkl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor(Color color) {
/* 342 */     StateSet states = getStates();
/* 343 */     ColorState cs = (ColorState)states.find(ColorState.class);
/* 344 */     if (cs == null)
/* 345 */       cs = new ColorState(); 
/* 346 */     cs.setColor(color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void selectedChanged() {
/* 353 */     System.out.println("QuadGroup: " + this + " selected=" + isSelected());
/* 354 */     dirtyDraw();
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
/*     */   private void buildTree(int[] ijkl, float[] xyz, float[] uvw, float[] rgb) {
/* 372 */     float[] c = computeCenters(ijkl, xyz);
/* 373 */     BoundingBoxTree bbt = new BoundingBoxTree(1024, c);
/* 374 */     buildTree(this, bbt.getRoot(), ijkl, xyz, uvw, rgb);
/*     */   }
/*     */ 
/*     */   
/*     */   private void buildTree(Group parent, BoundingBoxTree.Node bbtNode, int[] ijkl, float[] xyz, float[] uvw, float[] rgb) {
/* 379 */     if (bbtNode.isLeaf()) {
/* 380 */       QuadNode qn = new QuadNode(bbtNode, ijkl, xyz, uvw, rgb);
/* 381 */       parent.addChild(qn);
/*     */     } else {
/* 383 */       Group group = new Group();
/* 384 */       parent.addChild(group);
/* 385 */       buildTree(group, bbtNode.getLeft(), ijkl, xyz, uvw, rgb);
/* 386 */       buildTree(group, bbtNode.getRight(), ijkl, xyz, uvw, rgb);
/*     */     } 
/*     */   }
/*     */   
/*     */   private class QuadNode extends Node {
/*     */     private BoundingSphere _bs;
/*     */     private int _nq;
/*     */     private FloatBuffer _vb;
/*     */     private FloatBuffer _nb;
/*     */     private FloatBuffer _cb;
/*     */     
/*     */     public QuadNode(BoundingBoxTree.Node bbtNode, int[] ijkl, float[] xyz, float[] uvw, float[] rgb) {
/* 398 */       BoundingBox bb = bbtNode.getBoundingBox();
/* 399 */       this._bs = new BoundingSphere(bb);
/* 400 */       this._nq = bbtNode.getSize();
/* 401 */       int nq = this._nq;
/* 402 */       int nv = 4 * nq;
/* 403 */       int nn = 4 * nq;
/* 404 */       int nc = 4 * nq;
/* 405 */       int[] index = bbtNode.getIndices();
/* 406 */       this._vb = Direct.newFloatBuffer(3 * nv);
/* 407 */       this._nb = (uvw != null) ? Direct.newFloatBuffer(3 * nn) : null;
/* 408 */       this._cb = (rgb != null) ? Direct.newFloatBuffer(3 * nc) : null;
/* 409 */       for (int iq = 0, iv = 0, in = 0, ic = 0; iq < nq; iq++) {
/* 410 */         int jq = 4 * index[iq];
/* 411 */         int i = 3 * ijkl[jq + 0];
/* 412 */         int j = 3 * ijkl[jq + 1];
/* 413 */         int k = 3 * ijkl[jq + 2];
/* 414 */         int l = 3 * ijkl[jq + 3];
/* 415 */         this._vb.put(iv++, xyz[i + 0]);
/* 416 */         this._vb.put(iv++, xyz[i + 1]);
/* 417 */         this._vb.put(iv++, xyz[i + 2]);
/* 418 */         this._vb.put(iv++, xyz[j + 0]);
/* 419 */         this._vb.put(iv++, xyz[j + 1]);
/* 420 */         this._vb.put(iv++, xyz[j + 2]);
/* 421 */         this._vb.put(iv++, xyz[k + 0]);
/* 422 */         this._vb.put(iv++, xyz[k + 1]);
/* 423 */         this._vb.put(iv++, xyz[k + 2]);
/* 424 */         this._vb.put(iv++, xyz[l + 0]);
/* 425 */         this._vb.put(iv++, xyz[l + 1]);
/* 426 */         this._vb.put(iv++, xyz[l + 2]);
/* 427 */         if (this._nb != null) {
/* 428 */           this._nb.put(in++, uvw[i + 0]);
/* 429 */           this._nb.put(in++, uvw[i + 1]);
/* 430 */           this._nb.put(in++, uvw[i + 2]);
/* 431 */           this._nb.put(in++, uvw[j + 0]);
/* 432 */           this._nb.put(in++, uvw[j + 1]);
/* 433 */           this._nb.put(in++, uvw[j + 2]);
/* 434 */           this._nb.put(in++, uvw[k + 0]);
/* 435 */           this._nb.put(in++, uvw[k + 1]);
/* 436 */           this._nb.put(in++, uvw[k + 2]);
/* 437 */           this._nb.put(in++, uvw[l + 0]);
/* 438 */           this._nb.put(in++, uvw[l + 1]);
/* 439 */           this._nb.put(in++, uvw[l + 2]);
/*     */         } 
/* 441 */         if (this._cb != null) {
/* 442 */           this._cb.put(ic++, rgb[i + 0]);
/* 443 */           this._cb.put(ic++, rgb[i + 1]);
/* 444 */           this._cb.put(ic++, rgb[i + 2]);
/* 445 */           this._cb.put(ic++, rgb[j + 0]);
/* 446 */           this._cb.put(ic++, rgb[j + 1]);
/* 447 */           this._cb.put(ic++, rgb[j + 2]);
/* 448 */           this._cb.put(ic++, rgb[k + 0]);
/* 449 */           this._cb.put(ic++, rgb[k + 1]);
/* 450 */           this._cb.put(ic++, rgb[k + 2]);
/* 451 */           this._cb.put(ic++, rgb[l + 0]);
/* 452 */           this._cb.put(ic++, rgb[l + 1]);
/* 453 */           this._cb.put(ic++, rgb[l + 2]);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     protected BoundingSphere computeBoundingSphere(boolean finite) {
/* 459 */       return this._bs;
/*     */     }
/*     */     
/*     */     protected void draw(DrawContext dc) {
/* 463 */       boolean selected = QuadGroup.this.isSelected();
/* 464 */       Gl.glEnableClientState(32884);
/* 465 */       Gl.glVertexPointer(3, 5126, 0, this._vb);
/* 466 */       if (this._nb != null) {
/* 467 */         Gl.glEnableClientState(32885);
/* 468 */         Gl.glNormalPointer(5126, 0, this._nb);
/*     */       } 
/* 470 */       if (this._cb != null) {
/* 471 */         Gl.glEnableClientState(32886);
/* 472 */         Gl.glColorPointer(3, 5126, 0, this._cb);
/*     */       } 
/* 474 */       if (selected) {
/* 475 */         Gl.glEnable(32823);
/* 476 */         Gl.glPolygonOffset(1.0F, 1.0F);
/*     */       } 
/* 478 */       Gl.glDrawArrays(7, 0, 4 * this._nq);
/* 479 */       if (this._nb != null)
/* 480 */         Gl.glDisableClientState(32885); 
/* 481 */       if (this._cb != null)
/* 482 */         Gl.glDisableClientState(32886); 
/* 483 */       if (selected) {
/* 484 */         Gl.glPolygonMode(1032, 6913);
/* 485 */         Gl.glDisable(2896);
/* 486 */         Gl.glColor3d(1.0D, 1.0D, 1.0D);
/* 487 */         Gl.glDrawArrays(7, 0, 4 * this._nq);
/*     */       } 
/* 489 */       Gl.glDisableClientState(32884);
/*     */     }
/*     */     
/*     */     public void pick(PickContext pc) {
/* 493 */       Segment ps = pc.getPickSegment();
/* 494 */       for (int iq = 0, j = 0; iq < this._nq; iq++) {
/* 495 */         double xi = this._vb.get(j++);
/* 496 */         double yi = this._vb.get(j++);
/* 497 */         double zi = this._vb.get(j++);
/* 498 */         double xj = this._vb.get(j++);
/* 499 */         double yj = this._vb.get(j++);
/* 500 */         double zj = this._vb.get(j++);
/* 501 */         double xk = this._vb.get(j++);
/* 502 */         double yk = this._vb.get(j++);
/* 503 */         double zk = this._vb.get(j++);
/* 504 */         double xl = this._vb.get(j++);
/* 505 */         double yl = this._vb.get(j++);
/* 506 */         double zl = this._vb.get(j++);
/* 507 */         Point3 p = ps.intersectWithTriangle(xi, yi, zi, xj, yj, zj, xk, yk, zk);
/* 508 */         if (p == null)
/* 509 */           p = ps.intersectWithTriangle(xk, yk, zk, xl, yl, zl, xi, yi, zi); 
/* 510 */         if (p != null) {
/* 511 */           pc.addResult(p);
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
/* 525 */       this.x = x;
/* 526 */       this.y = y;
/* 527 */       this.z = z;
/*     */     }
/*     */     public boolean equals(Object o) {
/* 530 */       Vertex v = (Vertex)o;
/* 531 */       return (this.x == v.x && this.y == v.y && this.z == v.z);
/*     */     }
/*     */     public int hashCode() {
/* 534 */       return Float.floatToIntBits(this.x) ^ 
/* 535 */         Float.floatToIntBits(this.y) ^ 
/* 536 */         Float.floatToIntBits(this.z);
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
/*     */   private static float[] computeNormals(int[] ijkl, float[] xyz) {
/* 564 */     int nv = xyz.length / 3;
/* 565 */     int nq = ijkl.length / 4;
/* 566 */     float[] uvw = new float[3 * nv];
/*     */ 
/*     */     
/* 569 */     for (int iq = 0, jq = 0; iq < nq; iq++) {
/*     */ 
/*     */       
/* 572 */       int i = 3 * ijkl[jq++];
/* 573 */       int j = 3 * ijkl[jq++];
/* 574 */       int k = 3 * ijkl[jq++];
/* 575 */       int l = 3 * ijkl[jq++];
/* 576 */       float xi = xyz[i + 0];
/* 577 */       float yi = xyz[i + 1];
/* 578 */       float zi = xyz[i + 2];
/* 579 */       float xj = xyz[j + 0];
/* 580 */       float yj = xyz[j + 1];
/* 581 */       float zj = xyz[j + 2];
/* 582 */       float xk = xyz[k + 0];
/* 583 */       float yk = xyz[k + 1];
/* 584 */       float zk = xyz[k + 2];
/* 585 */       float xl = xyz[l + 0];
/* 586 */       float yl = xyz[l + 1];
/* 587 */       float zl = xyz[l + 2];
/*     */ 
/*     */ 
/*     */       
/* 591 */       float xa = xk - xi;
/* 592 */       float ya = yk - yi;
/* 593 */       float za = zk - zi;
/* 594 */       float xb = xl - xj;
/* 595 */       float yb = yl - yj;
/* 596 */       float zb = zl - zj;
/* 597 */       float un = ya * zb - yb * za;
/* 598 */       float vn = za * xb - zb * xa;
/* 599 */       float wn = xa * yb - xb * ya;
/*     */ 
/*     */ 
/*     */       
/* 603 */       uvw[i + 0] = uvw[i + 0] + un;
/* 604 */       uvw[i + 1] = uvw[i + 1] + vn;
/* 605 */       uvw[i + 2] = uvw[i + 2] + wn;
/* 606 */       uvw[j + 0] = uvw[j + 0] + un;
/* 607 */       uvw[j + 1] = uvw[j + 1] + vn;
/* 608 */       uvw[j + 2] = uvw[j + 2] + wn;
/* 609 */       uvw[k + 0] = uvw[k + 0] + un;
/* 610 */       uvw[k + 1] = uvw[k + 1] + vn;
/* 611 */       uvw[k + 2] = uvw[k + 2] + wn;
/* 612 */       uvw[l + 0] = uvw[l + 0] + un;
/* 613 */       uvw[l + 1] = uvw[l + 1] + vn;
/* 614 */       uvw[l + 2] = uvw[l + 2] + wn;
/*     */     } 
/* 616 */     return uvw;
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
/*     */   private float[] computeCenters(int[] ijkl, float[] xyz) {
/* 637 */     int nq = ijkl.length / 4;
/* 638 */     float[] c = new float[3 * nq];
/* 639 */     float o4 = 0.25F;
/* 640 */     for (int iq = 0, jq = 0, jc = 0; iq < nq; iq++) {
/* 641 */       int i = 3 * ijkl[jq++];
/* 642 */       int j = 3 * ijkl[jq++];
/* 643 */       int k = 3 * ijkl[jq++];
/* 644 */       int l = 3 * ijkl[jq++];
/* 645 */       float xi = xyz[i + 0];
/* 646 */       float yi = xyz[i + 1];
/* 647 */       float zi = xyz[i + 2];
/* 648 */       float xj = xyz[j + 0];
/* 649 */       float yj = xyz[j + 1];
/* 650 */       float zj = xyz[j + 2];
/* 651 */       float xk = xyz[k + 0];
/* 652 */       float yk = xyz[k + 1];
/* 653 */       float zk = xyz[k + 2];
/* 654 */       float xl = xyz[l + 0];
/* 655 */       float yl = xyz[l + 1];
/* 656 */       float zl = xyz[l + 2];
/* 657 */       c[jc++] = (xi + xj + xk + xl) * o4;
/* 658 */       c[jc++] = (yi + yj + yk + yl) * o4;
/* 659 */       c[jc++] = (zi + zj + zk + zl) * o4;
/*     */     } 
/* 661 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static StateSet defaultStateSet(Color color) {
/* 668 */     StateSet states = new StateSet();
/* 669 */     ColorState cs = new ColorState();
/* 670 */     cs.setColor(color);
/* 671 */     LightModelState lms = new LightModelState();
/* 672 */     lms.setTwoSide(true);
/* 673 */     MaterialState ms = new MaterialState();
/* 674 */     ms.setColorMaterial(5634);
/* 675 */     ms.setSpecular(Color.WHITE);
/* 676 */     ms.setShininess(100.0F);
/* 677 */     states.add(cs);
/* 678 */     states.add(lms);
/* 679 */     states.add(ms);
/* 680 */     return states;
/*     */   }
/*     */   
/*     */   private void setDefaultStates() {
/* 684 */     setStates(defaultStateSet(Color.LIGHT_GRAY));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/QuadGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */