/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
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
/*     */ public class World
/*     */   extends Group
/*     */ {
/*     */   public int countViews() {
/*  41 */     return this._viewList.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<View> getViews() {
/*  49 */     return this._viewList.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int countSelected() {
/*  57 */     return this._selectedSet.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<Node> getSelected() {
/*  65 */     return this._selectedSet.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearSelected() {
/*  72 */     for (Node node : new ArrayList(this._selectedSet)) {
/*  73 */       if (node.isSelected()) {
/*  74 */         node.setSelected(false);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearSelectedExcept(Selectable nodeToIgnore) {
/*  86 */     for (Node node : new ArrayList(this._selectedSet)) {
/*  87 */       if (node != nodeToIgnore && node.isSelected()) {
/*  88 */         node.setSelected(false);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dirtyDraw() {
/*  97 */     repaint();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dirtyBoundingSphere() {
/* 104 */     super.dirtyBoundingSphere();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void repaint() {
/* 111 */     for (View view : this._viewList) {
/* 112 */       view.repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public World getWorld() {
/* 121 */     return this;
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
/*     */   void updateSelectedSet(Node node) {
/* 139 */     if (node instanceof Selectable) {
/* 140 */       if (node.isSelected() && this == node.getWorld()) {
/* 141 */         this._selectedSet.add(node);
/*     */       } else {
/* 143 */         this._selectedSet.remove(node);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 149 */     if (node instanceof Group) {
/* 150 */       Group group = (Group)node;
/* 151 */       Iterator<Node> children = group.getChildren();
/* 152 */       while (children.hasNext()) {
/* 153 */         Node child = children.next();
/* 154 */         updateSelectedSet(child);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean addView(View view) {
/* 163 */     if (!this._viewList.contains(view)) {
/* 164 */       this._viewList.add(view);
/* 165 */       return true;
/*     */     } 
/* 167 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean removeView(View view) {
/* 175 */     return this._viewList.remove(view);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 181 */   private ArrayList<View> _viewList = new ArrayList<>();
/* 182 */   private HashSet<Node> _selectedSet = new HashSet<>();
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/World.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */