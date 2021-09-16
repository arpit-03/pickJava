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
/*     */ 
/*     */ 
/*     */ public class LineGroup
/*     */   extends Group
/*     */ {
/*     */   private static final int X = 0;
/*     */   private static final int Y = 1;
/*     */   private static final int Z = 2;
/*     */   private static final int R = 0;
/*     */   private static final int G = 1;
/*     */   private static final int B = 2;
/*     */   
/*     */   public LineGroup(float[] xyz) {
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
/*     */   public LineGroup(float[] xyz, float[] rgb) {
/*  52 */     addChild(new LineNode(xyz, rgb));
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
/*     */   public LineGroup(float[][] xyz) {
/*  64 */     this(xyz, (float[][])null);
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
/*     */   public LineGroup(float[][] xyz, float[][] rgb) {
/*  80 */     int ns = xyz.length;
/*  81 */     for (int is = 0; is < ns; is++) {
/*  82 */       if (rgb == null) {
/*  83 */         addChild(new LineNode(xyz[is], null));
/*     */       } else {
/*  85 */         addChild(new LineNode(xyz[is], rgb[is]));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private class LineNode
/*     */     extends Node
/*     */   {
/*     */     private BoundingSphere _bs;
/*     */     
/*     */     private int _np;
/*     */     
/*     */     private FloatBuffer _vb;
/*     */     
/*     */     private FloatBuffer _cb;
/*     */     
/*     */     public LineNode(float[] xyz, float[] rgb) {
/* 103 */       BoundingBox bb = new BoundingBox(xyz);
/* 104 */       this._bs = new BoundingSphere(bb);
/* 105 */       this._np = xyz.length / 3;
/* 106 */       int np = this._np;
/* 107 */       int nv = np;
/* 108 */       int nc = np;
/* 109 */       this._vb = Direct.newFloatBuffer(3 * nv);
/* 110 */       this._cb = (rgb != null) ? Direct.newFloatBuffer(3 * nc) : null;
/* 111 */       for (int ip = 0, iv = 0, ic = 0; ip < np; ip++) {
/* 112 */         int i = 3 * ip;
/* 113 */         this._vb.put(iv++, xyz[i + 0]);
/* 114 */         this._vb.put(iv++, xyz[i + 1]);
/* 115 */         this._vb.put(iv++, xyz[i + 2]);
/* 116 */         if (this._cb != null) {
/* 117 */           this._cb.put(ic++, rgb[i + 0]);
/* 118 */           this._cb.put(ic++, rgb[i + 1]);
/* 119 */           this._cb.put(ic++, rgb[i + 2]);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     protected BoundingSphere computeBoundingSphere(boolean finite) {
/* 125 */       return this._bs;
/*     */     }
/*     */     
/*     */     protected void draw(DrawContext dc) {
/* 129 */       Gl.glEnableClientState(32884);
/* 130 */       Gl.glVertexPointer(3, 5126, 0, this._vb);
/* 131 */       if (this._cb != null) {
/* 132 */         Gl.glEnableClientState(32886);
/* 133 */         Gl.glColorPointer(3, 5126, 0, this._cb);
/*     */       } 
/* 135 */       Gl.glDrawArrays(3, 0, this._np);
/* 136 */       if (this._cb != null)
/* 137 */         Gl.glDisableClientState(32886); 
/* 138 */       Gl.glDisableClientState(32884);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/LineGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */