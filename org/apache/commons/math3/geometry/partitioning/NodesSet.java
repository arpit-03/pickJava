/*    */ package org.apache.commons.math3.geometry.partitioning;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.apache.commons.math3.geometry.Space;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NodesSet<S extends Space>
/*    */   implements Iterable<BSPTree<S>>
/*    */ {
/* 38 */   private List<BSPTree<S>> list = new ArrayList<BSPTree<S>>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void add(BSPTree<S> node) {
/* 46 */     for (BSPTree<S> existing : this.list) {
/* 47 */       if (node == existing) {
/*    */         return;
/*    */       }
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 54 */     this.list.add(node);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addAll(Iterable<BSPTree<S>> iterator) {
/* 62 */     for (BSPTree<S> node : iterator) {
/* 63 */       add(node);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<BSPTree<S>> iterator() {
/* 69 */     return this.list.iterator();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/partitioning/NodesSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */