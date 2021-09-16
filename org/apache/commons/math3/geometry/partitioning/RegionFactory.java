/*     */ package org.apache.commons.math3.geometry.partitioning;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.geometry.Point;
/*     */ import org.apache.commons.math3.geometry.Space;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RegionFactory<S extends Space>
/*     */ {
/*  44 */   private final NodesCleaner nodeCleaner = new NodesCleaner();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Region<S> buildConvex(Hyperplane<S>... hyperplanes) {
/*  52 */     if (hyperplanes == null || hyperplanes.length == 0) {
/*  53 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  57 */     Region<S> region = hyperplanes[0].wholeSpace();
/*     */ 
/*     */     
/*  60 */     BSPTree<S> node = region.getTree(false);
/*  61 */     node.setAttribute(Boolean.TRUE);
/*  62 */     for (Hyperplane<S> hyperplane : hyperplanes) {
/*  63 */       if (node.insertCut(hyperplane)) {
/*  64 */         node.setAttribute(null);
/*  65 */         node.getPlus().setAttribute(Boolean.FALSE);
/*  66 */         node = node.getMinus();
/*  67 */         node.setAttribute(Boolean.TRUE);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/*  72 */         SubHyperplane<S> s = hyperplane.wholeHyperplane();
/*  73 */         for (BSPTree<S> tree = node; tree.getParent() != null && s != null; tree = tree.getParent()) {
/*  74 */           Hyperplane<S> other = tree.getParent().getCut().getHyperplane();
/*  75 */           SubHyperplane.SplitSubHyperplane<S> split = s.split(other);
/*  76 */           switch (split.getSide()) {
/*     */             
/*     */             case HYPER:
/*  79 */               if (!hyperplane.sameOrientationAs(other))
/*     */               {
/*     */                 
/*  82 */                 return getComplement(hyperplanes[0].wholeSpace());
/*     */               }
/*     */               break;
/*     */ 
/*     */ 
/*     */             
/*     */             case PLUS:
/*  89 */               throw new MathIllegalArgumentException(LocalizedFormats.NOT_CONVEX_HYPERPLANES, new Object[0]);
/*     */             default:
/*  91 */               s = split.getMinus();
/*     */               break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*  97 */     return region;
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
/*     */   public Region<S> union(Region<S> region1, Region<S> region2) {
/* 109 */     BSPTree<S> tree = region1.getTree(false).merge(region2.getTree(false), new UnionMerger());
/*     */     
/* 111 */     tree.visit(this.nodeCleaner);
/* 112 */     return region1.buildNew(tree);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Region<S> intersection(Region<S> region1, Region<S> region2) {
/* 123 */     BSPTree<S> tree = region1.getTree(false).merge(region2.getTree(false), new IntersectionMerger());
/*     */     
/* 125 */     tree.visit(this.nodeCleaner);
/* 126 */     return region1.buildNew(tree);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Region<S> xor(Region<S> region1, Region<S> region2) {
/* 137 */     BSPTree<S> tree = region1.getTree(false).merge(region2.getTree(false), new XorMerger());
/*     */     
/* 139 */     tree.visit(this.nodeCleaner);
/* 140 */     return region1.buildNew(tree);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Region<S> difference(Region<S> region1, Region<S> region2) {
/* 151 */     BSPTree<S> tree = region1.getTree(false).merge(region2.getTree(false), new DifferenceMerger(region1, region2));
/*     */     
/* 153 */     tree.visit(this.nodeCleaner);
/* 154 */     return region1.buildNew(tree);
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
/*     */   public Region<S> getComplement(Region<S> region) {
/* 168 */     return region.buildNew(recurseComplement(region.getTree(false)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BSPTree<S> recurseComplement(BSPTree<S> node) {
/* 178 */     Map<BSPTree<S>, BSPTree<S>> map = new HashMap<BSPTree<S>, BSPTree<S>>();
/* 179 */     BSPTree<S> transformedTree = recurseComplement(node, map);
/*     */ 
/*     */     
/* 182 */     for (Map.Entry<BSPTree<S>, BSPTree<S>> entry : map.entrySet()) {
/* 183 */       if (((BSPTree)entry.getKey()).getCut() != null) {
/*     */         
/* 185 */         BoundaryAttribute<S> original = (BoundaryAttribute<S>)((BSPTree)entry.getKey()).getAttribute();
/* 186 */         if (original != null) {
/*     */           
/* 188 */           BoundaryAttribute<S> transformed = (BoundaryAttribute<S>)((BSPTree)entry.getValue()).getAttribute();
/* 189 */           for (BSPTree<S> splitter : original.getSplitters()) {
/* 190 */             transformed.getSplitters().add(map.get(splitter));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 196 */     return transformedTree;
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
/*     */   private BSPTree<S> recurseComplement(BSPTree<S> node, Map<BSPTree<S>, BSPTree<S>> map) {
/*     */     BSPTree<S> transformedNode;
/* 209 */     if (node.getCut() == null) {
/* 210 */       transformedNode = new BSPTree<S>(((Boolean)node.getAttribute()).booleanValue() ? Boolean.FALSE : Boolean.TRUE);
/*     */     }
/*     */     else {
/*     */       
/* 214 */       BoundaryAttribute<S> attribute = (BoundaryAttribute<S>)node.getAttribute();
/* 215 */       if (attribute != null) {
/* 216 */         SubHyperplane<S> plusOutside = (attribute.getPlusInside() == null) ? null : attribute.getPlusInside().copySelf();
/*     */         
/* 218 */         SubHyperplane<S> plusInside = (attribute.getPlusOutside() == null) ? null : attribute.getPlusOutside().copySelf();
/*     */ 
/*     */         
/* 221 */         attribute = new BoundaryAttribute<S>(plusOutside, plusInside, new NodesSet<S>());
/*     */       } 
/*     */       
/* 224 */       transformedNode = new BSPTree<S>(node.getCut().copySelf(), recurseComplement(node.getPlus(), map), recurseComplement(node.getMinus(), map), attribute);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 230 */     map.put(node, transformedNode);
/* 231 */     return transformedNode;
/*     */   }
/*     */ 
/*     */   
/*     */   private class UnionMerger
/*     */     implements BSPTree.LeafMerger<S>
/*     */   {
/*     */     private UnionMerger() {}
/*     */     
/*     */     public BSPTree<S> merge(BSPTree<S> leaf, BSPTree<S> tree, BSPTree<S> parentTree, boolean isPlusChild, boolean leafFromInstance) {
/* 241 */       if (((Boolean)leaf.getAttribute()).booleanValue()) {
/*     */         
/* 243 */         leaf.insertInTree(parentTree, isPlusChild, new RegionFactory.VanishingToLeaf(true));
/* 244 */         return leaf;
/*     */       } 
/*     */       
/* 247 */       tree.insertInTree(parentTree, isPlusChild, new RegionFactory.VanishingToLeaf(false));
/* 248 */       return tree;
/*     */     }
/*     */   }
/*     */   
/*     */   private class IntersectionMerger
/*     */     implements BSPTree.LeafMerger<S>
/*     */   {
/*     */     private IntersectionMerger() {}
/*     */     
/*     */     public BSPTree<S> merge(BSPTree<S> leaf, BSPTree<S> tree, BSPTree<S> parentTree, boolean isPlusChild, boolean leafFromInstance) {
/* 258 */       if (((Boolean)leaf.getAttribute()).booleanValue()) {
/*     */         
/* 260 */         tree.insertInTree(parentTree, isPlusChild, new RegionFactory.VanishingToLeaf(true));
/* 261 */         return tree;
/*     */       } 
/*     */       
/* 264 */       leaf.insertInTree(parentTree, isPlusChild, new RegionFactory.VanishingToLeaf(false));
/* 265 */       return leaf;
/*     */     }
/*     */   }
/*     */   
/*     */   private class XorMerger
/*     */     implements BSPTree.LeafMerger<S>
/*     */   {
/*     */     private XorMerger() {}
/*     */     
/*     */     public BSPTree<S> merge(BSPTree<S> leaf, BSPTree<S> tree, BSPTree<S> parentTree, boolean isPlusChild, boolean leafFromInstance) {
/* 275 */       BSPTree<S> t = tree;
/* 276 */       if (((Boolean)leaf.getAttribute()).booleanValue())
/*     */       {
/* 278 */         t = RegionFactory.this.recurseComplement(t);
/*     */       }
/* 280 */       t.insertInTree(parentTree, isPlusChild, new RegionFactory.VanishingToLeaf(true));
/* 281 */       return t;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class DifferenceMerger
/*     */     implements BSPTree.LeafMerger<S>, BSPTree.VanishingCutHandler<S>
/*     */   {
/*     */     private final Region<S> region1;
/*     */ 
/*     */     
/*     */     private final Region<S> region2;
/*     */ 
/*     */ 
/*     */     
/*     */     DifferenceMerger(Region<S> region1, Region<S> region2) {
/* 299 */       this.region1 = region1.copySelf();
/* 300 */       this.region2 = region2.copySelf();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BSPTree<S> merge(BSPTree<S> leaf, BSPTree<S> tree, BSPTree<S> parentTree, boolean isPlusChild, boolean leafFromInstance) {
/* 307 */       if (((Boolean)leaf.getAttribute()).booleanValue()) {
/*     */         
/* 309 */         BSPTree<S> argTree = RegionFactory.this.recurseComplement(leafFromInstance ? tree : leaf);
/*     */         
/* 311 */         argTree.insertInTree(parentTree, isPlusChild, this);
/* 312 */         return argTree;
/*     */       } 
/*     */       
/* 315 */       BSPTree<S> instanceTree = leafFromInstance ? leaf : tree;
/*     */       
/* 317 */       instanceTree.insertInTree(parentTree, isPlusChild, this);
/* 318 */       return instanceTree;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public BSPTree<S> fixNode(BSPTree<S> node) {
/* 324 */       BSPTree<S> cell = node.pruneAroundConvexCell(Boolean.TRUE, Boolean.FALSE, null);
/* 325 */       Region<S> r = this.region1.buildNew(cell);
/* 326 */       Point<S> p = r.getBarycenter();
/* 327 */       return new BSPTree<S>(Boolean.valueOf((this.region1.checkPoint(p) == Region.Location.INSIDE && this.region2.checkPoint(p) == Region.Location.OUTSIDE)));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private class NodesCleaner
/*     */     implements BSPTreeVisitor<S>
/*     */   {
/*     */     private NodesCleaner() {}
/*     */     
/*     */     public BSPTreeVisitor.Order visitOrder(BSPTree<S> node) {
/* 338 */       return BSPTreeVisitor.Order.PLUS_SUB_MINUS;
/*     */     }
/*     */ 
/*     */     
/*     */     public void visitInternalNode(BSPTree<S> node) {
/* 343 */       node.setAttribute(null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void visitLeafNode(BSPTree<S> node) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class VanishingToLeaf
/*     */     implements BSPTree.VanishingCutHandler<S>
/*     */   {
/*     */     private final boolean inside;
/*     */ 
/*     */ 
/*     */     
/*     */     VanishingToLeaf(boolean inside) {
/* 362 */       this.inside = inside;
/*     */     }
/*     */ 
/*     */     
/*     */     public BSPTree<S> fixNode(BSPTree<S> node) {
/* 367 */       if (node.getPlus().getAttribute().equals(node.getMinus().getAttribute()))
/*     */       {
/* 369 */         return new BSPTree<S>(node.getPlus().getAttribute());
/*     */       }
/*     */       
/* 372 */       return new BSPTree<S>(Boolean.valueOf(this.inside));
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/partitioning/RegionFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */