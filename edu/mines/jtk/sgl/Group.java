/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import edu.mines.jtk.util.Check;
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
/*     */ public class Group
/*     */   extends Node
/*     */ {
/*     */   public void addChild(Node child) {
/*  40 */     Check.argument(!(child instanceof World), "child is not a world");
/*  41 */     World worldChild = child.getWorld();
/*  42 */     World worldGroup = getWorld();
/*  43 */     Check.argument((worldChild == null || worldGroup == null || worldChild == worldGroup), "child is not already in a different world");
/*     */ 
/*     */     
/*  46 */     if (child.addParent(this)) {
/*  47 */       this._childList.add(child);
/*  48 */       dirtyBoundingSphere();
/*  49 */       dirtyDraw();
/*  50 */       if (worldGroup != null) {
/*  51 */         worldGroup.updateSelectedSet(child);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeChild(Node child) {
/*  61 */     if (child.removeParent(this)) {
/*  62 */       this._childList.remove(child);
/*  63 */       dirtyBoundingSphere();
/*  64 */       dirtyDraw();
/*  65 */       World worldGroup = getWorld();
/*  66 */       if (worldGroup != null) {
/*  67 */         worldGroup.updateSelectedSet(child);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int countChildren() {
/*  76 */     return this._childList.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<Node> getChildren() {
/*  84 */     return this._childList.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pick(PickContext pc) {
/*  93 */     for (Node child : this._childList) {
/*  94 */       child.pickApply(pc);
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
/*     */   protected void cull(CullContext cc) {
/* 106 */     for (Node child : this._childList) {
/* 107 */       child.cullApply(cc);
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
/*     */   protected void draw(DrawContext dc) {
/* 120 */     for (Node child : this._childList) {
/* 121 */       child.drawApply(dc);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BoundingSphere computeBoundingSphere(boolean finite) {
/* 130 */     if (countChildren() == 1) {
/* 131 */       return ((Node)this._childList.get(0)).getBoundingSphere(finite);
/*     */     }
/* 133 */     BoundingBox bb = new BoundingBox();
/* 134 */     for (Node child : this._childList)
/* 135 */       bb.expandBy(child.getBoundingSphere(finite)); 
/* 136 */     if (bb.isEmpty())
/* 137 */       return BoundingSphere.empty(); 
/* 138 */     if (bb.isInfinite())
/* 139 */       return BoundingSphere.infinite(); 
/* 140 */     BoundingSphere bs = new BoundingSphere(bb.getCenter(), 0.0D);
/* 141 */     for (Node child : this._childList)
/* 142 */       bs.expandRadiusBy(child.getBoundingSphere(finite)); 
/* 143 */     return bs;
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
/*     */   protected int getAttributeBits() {
/* 157 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 163 */   ArrayList<Node> _childList = new ArrayList<>(4);
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/Group.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */