/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import edu.mines.jtk.ogl.Gl;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Node
/*     */ {
/*     */   private boolean _selected;
/*     */   
/*     */   public final boolean isSelected() {
/*  64 */     return this._selected;
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
/*     */   public final void setSelected(boolean selected) {
/*  78 */     if (this instanceof Selectable && this._selected != selected) {
/*     */ 
/*     */       
/*  81 */       this._selected = selected;
/*  82 */       selectedChanged();
/*     */ 
/*     */       
/*  85 */       World world = getWorld();
/*  86 */       if (world != null) {
/*  87 */         world.updateSelectedSet(this);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int countParents() {
/*  96 */     return this._parentList.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<Group> getParents() {
/* 104 */     return this._parentList.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public World getWorld() {
/* 112 */     for (Group parent : this._parentList) {
/* 113 */       World world = parent.getWorld();
/* 114 */       if (world != null)
/* 115 */         return world; 
/*     */     } 
/* 117 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dirtyDraw() {
/* 125 */     for (Group parent : this._parentList) {
/* 126 */       parent.dirtyDraw();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BoundingSphere getBoundingSphere(boolean finite) {
/* 137 */     if (this._boundingSphere == null || this._boundingSphereFinite != finite) {
/* 138 */       this._boundingSphere = computeBoundingSphere(finite);
/* 139 */       this._boundingSphereFinite = finite;
/*     */     } 
/* 141 */     return this._boundingSphere;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dirtyBoundingSphere() {
/* 150 */     if (this._boundingSphere != null) {
/* 151 */       this._boundingSphere = null;
/* 152 */       for (Group parent : this._parentList) {
/* 153 */         parent.dirtyBoundingSphere();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StateSet getStates() {
/* 162 */     return this._states;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStates(StateSet states) {
/* 170 */     this._states = states;
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
/*     */   public void pick(PickContext pc) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void selectedChanged() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BoundingSphere computeBoundingSphere(boolean finite) {
/* 212 */     return finite ? BoundingSphere.empty() : BoundingSphere.infinite();
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
/*     */   protected int getAttributeBits() {
/* 234 */     return 1048575;
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
/*     */   protected void cullApply(CullContext cc) {
/* 250 */     if (cc.frustumIntersectsSphereOf(this)) {
/* 251 */       cullBegin(cc);
/* 252 */       cull(cc);
/* 253 */       cullEnd(cc);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void cullBegin(CullContext cc) {
/* 263 */     cc.pushNode(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void cull(CullContext cc) {
/* 272 */     cc.appendNodes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void cullEnd(CullContext cc) {
/* 280 */     cc.popNode();
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
/*     */   protected void drawApply(DrawContext dc) {
/* 294 */     drawBegin(dc);
/* 295 */     draw(dc);
/* 296 */     drawEnd(dc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawBegin(DrawContext dc) {
/* 306 */     dc.pushNode(this);
/* 307 */     int bits = getAttributeBits();
/* 308 */     if (bits != 1048575 && this._states != null)
/* 309 */       bits |= this._states.getAttributeBits(); 
/* 310 */     Gl.glPushAttrib(bits);
/* 311 */     if (this._states != null) {
/* 312 */       this._states.apply();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void draw(DrawContext dc) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawEnd(DrawContext dc) {
/* 329 */     Gl.glPopAttrib();
/* 330 */     dc.popNode();
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
/*     */   protected void pickApply(PickContext pc) {
/* 346 */     if (pc.segmentIntersectsSphereOf(this)) {
/* 347 */       pickBegin(pc);
/* 348 */       pick(pc);
/* 349 */       pickEnd(pc);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void pickBegin(PickContext pc) {
/* 359 */     pc.pushNode(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void pickEnd(PickContext pc) {
/* 367 */     pc.popNode();
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
/*     */   boolean addParent(Group parent) {
/* 380 */     if (!this._parentList.contains(parent)) {
/* 381 */       this._parentList.add(parent);
/* 382 */       return true;
/*     */     } 
/* 384 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean removeParent(Group parent) {
/* 395 */     return this._parentList.remove(parent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 402 */   private BoundingSphere _boundingSphere = null;
/*     */   private boolean _boundingSphereFinite = false;
/* 404 */   private ArrayList<Group> _parentList = new ArrayList<>(2);
/*     */   private StateSet _states;
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/Node.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */