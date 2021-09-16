/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import edu.mines.jtk.ogl.Gl;
/*     */ import edu.mines.jtk.util.Direct;
/*     */ import java.awt.Color;
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
/*     */ public class HandleBox
/*     */   extends Handle
/*     */ {
/*     */   public HandleBox(Point3 p) {
/*  36 */     super(p);
/*  37 */     addChild(_box);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HandleBox(double x, double y, double z) {
/*  47 */     super(x, y, z);
/*  48 */     addChild(_box);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setColor(Color color) {
/*  56 */     _colorState.setColor(color);
/*  57 */     _box.dirtyDraw();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Box
/*     */     extends Node
/*     */   {
/*     */     Box() {
/*  66 */       StateSet states = new StateSet();
/*  67 */       HandleBox._materialState = new MaterialState();
/*  68 */       HandleBox._materialState.setColorMaterialFront(5634);
/*  69 */       HandleBox._materialState.setSpecularFront(Color.white);
/*  70 */       HandleBox._materialState.setShininessFront(100.0F);
/*  71 */       states.add(HandleBox._materialState);
/*  72 */       HandleBox._colorState = new ColorState();
/*  73 */       HandleBox._colorState.setColor(Color.YELLOW);
/*  74 */       states.add(HandleBox._colorState);
/*  75 */       setStates(states);
/*     */     }
/*     */     protected void draw(DrawContext dc) {
/*  78 */       Gl.glPushClientAttrib(2);
/*  79 */       Gl.glEnableClientState(32884);
/*  80 */       Gl.glEnableClientState(32885);
/*  81 */       Gl.glVertexPointer(3, 5126, 0, HandleBox._vb);
/*  82 */       Gl.glNormalPointer(5126, 0, HandleBox._nb);
/*  83 */       Gl.glDrawArrays(7, 0, 24);
/*  84 */       Gl.glPopClientAttrib();
/*     */     }
/*     */     public void pick(PickContext pc) {
/*  87 */       Segment ps = pc.getPickSegment();
/*  88 */       for (int iside = 0, index = 0; iside < 6; iside++, index += 12) {
/*  89 */         double xa = HandleBox._va[index + 0];
/*  90 */         double ya = HandleBox._va[index + 1];
/*  91 */         double za = HandleBox._va[index + 2];
/*  92 */         double xb = HandleBox._va[index + 3];
/*  93 */         double yb = HandleBox._va[index + 4];
/*  94 */         double zb = HandleBox._va[index + 5];
/*  95 */         double xc = HandleBox._va[index + 6];
/*  96 */         double yc = HandleBox._va[index + 7];
/*  97 */         double zc = HandleBox._va[index + 8];
/*  98 */         double xd = HandleBox._va[index + 9];
/*  99 */         double yd = HandleBox._va[index + 10];
/* 100 */         double zd = HandleBox._va[index + 11];
/* 101 */         Point3 p = ps.intersectWithTriangle(xa, ya, za, xb, yb, zb, xc, yc, zc);
/* 102 */         Point3 q = ps.intersectWithTriangle(xa, ya, za, xc, yc, zc, xd, yd, zd);
/* 103 */         if (p != null)
/* 104 */           pc.addResult(p); 
/* 105 */         if (q != null)
/* 106 */           pc.addResult(q); 
/*     */       } 
/*     */     }
/*     */     protected BoundingSphere computeBoundingSphere(boolean finite) {
/* 110 */       return new BoundingSphere(0.0D, 0.0D, 0.0D, Math.sqrt(3.0D));
/*     */     }
/*     */   }
/*     */   
/* 114 */   private static Box _box = new Box();
/* 115 */   private static ColorState _colorState = new ColorState();
/* 116 */   private static MaterialState _materialState = new MaterialState();
/*     */ 
/*     */   
/* 119 */   private static float[] _va = new float[] { -1.0F, -1.0F, -1.0F, -1.0F, -1.0F, 1.0F, -1.0F, 1.0F, 1.0F, -1.0F, 1.0F, -1.0F, -1.0F, -1.0F, -1.0F, 1.0F, -1.0F, -1.0F, 1.0F, -1.0F, 1.0F, -1.0F, -1.0F, 1.0F, -1.0F, -1.0F, -1.0F, -1.0F, 1.0F, -1.0F, 1.0F, 1.0F, -1.0F, 1.0F, -1.0F, -1.0F, 1.0F, -1.0F, -1.0F, 1.0F, 1.0F, -1.0F, 1.0F, 1.0F, 1.0F, 1.0F, -1.0F, 1.0F, -1.0F, 1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, -1.0F, -1.0F, -1.0F, 1.0F, 1.0F, -1.0F, 1.0F, 1.0F, 1.0F, 1.0F, -1.0F, 1.0F, 1.0F };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   private static float[] _na = new float[] { -1.0F, 0.0F, 0.0F, -1.0F, 0.0F, 0.0F, -1.0F, 0.0F, 0.0F, -1.0F, 0.0F, 0.0F, 0.0F, -1.0F, 0.0F, 0.0F, -1.0F, 0.0F, 0.0F, -1.0F, 0.0F, 0.0F, -1.0F, 0.0F, 0.0F, 0.0F, -1.0F, 0.0F, 0.0F, -1.0F, 0.0F, 0.0F, -1.0F, 0.0F, 0.0F, -1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 135 */   private static FloatBuffer _vb = Direct.newFloatBuffer(_va);
/* 136 */   private static FloatBuffer _nb = Direct.newFloatBuffer(_na);
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/HandleBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */