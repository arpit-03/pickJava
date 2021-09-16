/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CullContext
/*     */   extends TransformContext
/*     */ {
/*     */   private DrawList _drawList;
/*     */   private Plane[] _planes;
/*     */   private int _active;
/*     */   private ArrayStack<Plane> _planesStack;
/*     */   private IntStack _activeStack;
/*     */   
/*     */   public CullContext(ViewCanvas canvas) {
/*  35 */     super(canvas);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 150 */     this._drawList = new DrawList();
/* 151 */     this._planes = new Plane[6];
/*     */     
/* 153 */     this._planesStack = new ArrayStack<>();
/* 154 */     this._activeStack = new IntStack();
/*     */     initFrustum();
/*     */   }
/*     */   
/*     */   private void initFrustum() {
/* 159 */     this._planes[0] = new Plane(-1.0D, 0.0D, 0.0D, 1.0D);
/* 160 */     this._planes[1] = new Plane(1.0D, 0.0D, 0.0D, 1.0D);
/* 161 */     this._planes[2] = new Plane(0.0D, -1.0D, 0.0D, 1.0D);
/* 162 */     this._planes[3] = new Plane(0.0D, 1.0D, 0.0D, 1.0D);
/* 163 */     this._planes[4] = new Plane(0.0D, 0.0D, -1.0D, 1.0D);
/* 164 */     this._planes[5] = new Plane(0.0D, 0.0D, 1.0D, 1.0D);
/*     */ 
/*     */     
/* 167 */     this._active = 63;
/*     */     
/* 169 */     Matrix44 worldToCube = getWorldToCube();
/* 170 */     for (int i = 0; i < 6; i++)
/* 171 */       this._planes[i].transformWithInverse(worldToCube); 
/*     */   }
/*     */   
/*     */   public boolean frustumIntersectsSphereOf(Node node) {
/*     */     if (this._active != 0) {
/*     */       BoundingSphere bs = node.getBoundingSphere(false);
/*     */       if (bs.isEmpty())
/*     */         return false; 
/*     */       if (bs.isInfinite())
/*     */         return true; 
/*     */       Point3 c = bs.getCenter();
/*     */       double r = bs.getRadius();
/*     */       double s = -r;
/*     */       int plane;
/*     */       for (int i = 0; i < 6; i++, plane <<= 1) {
/*     */         if ((this._active & plane) != 0) {
/*     */           double d = this._planes[i].distanceTo(c);
/*     */           if (d < s)
/*     */             return false; 
/*     */           if (d > r)
/*     */             this._active ^= plane; 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     return true;
/*     */   }
/*     */   
/*     */   public void appendNodes() {
/*     */     this._drawList.append(getNodes());
/*     */   }
/*     */   
/*     */   public DrawList getDrawList() {
/*     */     return this._drawList;
/*     */   }
/*     */   
/*     */   public void pushNode(Node node) {
/*     */     super.pushNode(node);
/*     */     this._activeStack.push(this._active);
/*     */   }
/*     */   
/*     */   public void popNode() {
/*     */     super.popNode();
/*     */     this._active = this._activeStack.pop();
/*     */   }
/*     */   
/*     */   public void pushLocalToWorld(Matrix44 transform) {
/*     */     super.pushLocalToWorld(transform);
/*     */     for (int i = 0, plane = 1; i < 6; i++, plane <<= 1) {
/*     */       this._planesStack.push(new Plane(this._planes[i]));
/*     */       if ((this._active & plane) != 0)
/*     */         this._planes[i].transformWithInverse(transform); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void popLocalToWorld() {
/*     */     super.popLocalToWorld();
/*     */     for (int i = 5; i >= 0; i--)
/*     */       this._planes[i] = this._planesStack.pop(); 
/*     */   }
/*     */   
/*     */   private static class IntStack {
/*     */     void push(int active) {
/*     */       if (this._n == this._a.length) {
/*     */         int[] a = new int[2 * this._n];
/*     */         for (int i = 0; i < this._n; i++)
/*     */           a[i] = this._a[i]; 
/*     */         this._a = a;
/*     */       } 
/*     */       this._a[this._n++] = active;
/*     */     }
/*     */     
/*     */     int pop() {
/*     */       return this._a[--this._n];
/*     */     }
/*     */     
/*     */     private int _n = 0;
/*     */     private int[] _a = new int[8];
/*     */     
/*     */     private IntStack() {}
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/CullContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */