/*     */ package org.jfree.chart.block;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.entity.StandardEntityCollection;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.ui.Size2D;
/*     */ import org.jfree.util.PublicCloneable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockContainer
/*     */   extends AbstractBlock
/*     */   implements Block, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8199508075695195293L;
/*     */   private List blocks;
/*     */   private Arrangement arrangement;
/*     */   
/*     */   public BlockContainer() {
/*  85 */     this(new BorderArrangement());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockContainer(Arrangement arrangement) {
/*  95 */     ParamChecks.nullNotPermitted(arrangement, "arrangement");
/*  96 */     this.arrangement = arrangement;
/*  97 */     this.blocks = new ArrayList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Arrangement getArrangement() {
/* 106 */     return this.arrangement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setArrangement(Arrangement arrangement) {
/* 115 */     ParamChecks.nullNotPermitted(arrangement, "arrangement");
/* 116 */     this.arrangement = arrangement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 126 */     return this.blocks.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getBlocks() {
/* 136 */     return Collections.unmodifiableList(this.blocks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(Block block) {
/* 145 */     add(block, (Object)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(Block block, Object key) {
/* 155 */     this.blocks.add(block);
/* 156 */     this.arrangement.add(block, key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 163 */     this.blocks.clear();
/* 164 */     this.arrangement.clear();
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
/*     */   public Size2D arrange(Graphics2D g2, RectangleConstraint constraint) {
/* 178 */     return this.arrangement.arrange(this, g2, constraint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(Graphics2D g2, Rectangle2D area) {
/* 189 */     draw(g2, area, (Object)null);
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
/*     */   public Object draw(Graphics2D g2, Rectangle2D area, Object params) {
/* 206 */     StandardEntityCollection sec = null;
/* 207 */     if (params instanceof EntityBlockParams) {
/* 208 */       EntityBlockParams ebp = (EntityBlockParams)params;
/* 209 */       if (ebp.getGenerateEntities()) {
/* 210 */         sec = new StandardEntityCollection();
/*     */       }
/*     */     } 
/* 213 */     Rectangle2D contentArea = (Rectangle2D)area.clone();
/* 214 */     contentArea = trimMargin(contentArea);
/* 215 */     drawBorder(g2, contentArea);
/* 216 */     contentArea = trimBorder(contentArea);
/* 217 */     contentArea = trimPadding(contentArea);
/* 218 */     Iterator<Block> iterator = this.blocks.iterator();
/* 219 */     while (iterator.hasNext()) {
/* 220 */       Block block = iterator.next();
/* 221 */       Rectangle2D bounds = block.getBounds();
/*     */ 
/*     */       
/* 224 */       Rectangle2D drawArea = new Rectangle2D.Double(bounds.getX() + area.getX(), bounds.getY() + area.getY(), bounds.getWidth(), bounds.getHeight());
/* 225 */       Object r = block.draw(g2, drawArea, params);
/* 226 */       if (sec != null && 
/* 227 */         r instanceof EntityBlockResult) {
/* 228 */         EntityBlockResult ebr = (EntityBlockResult)r;
/* 229 */         EntityCollection ec = ebr.getEntityCollection();
/* 230 */         sec.addAll(ec);
/*     */       } 
/*     */     } 
/*     */     
/* 234 */     BlockResult result = null;
/* 235 */     if (sec != null) {
/* 236 */       result = new BlockResult();
/* 237 */       result.setEntityCollection((EntityCollection)sec);
/*     */     } 
/* 239 */     return result;
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
/*     */   public boolean equals(Object obj) {
/* 251 */     if (obj == this) {
/* 252 */       return true;
/*     */     }
/* 254 */     if (!(obj instanceof BlockContainer)) {
/* 255 */       return false;
/*     */     }
/* 257 */     if (!super.equals(obj)) {
/* 258 */       return false;
/*     */     }
/* 260 */     BlockContainer that = (BlockContainer)obj;
/* 261 */     if (!this.arrangement.equals(that.arrangement)) {
/* 262 */       return false;
/*     */     }
/* 264 */     if (!this.blocks.equals(that.blocks)) {
/* 265 */       return false;
/*     */     }
/* 267 */     return true;
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 279 */     BlockContainer clone = (BlockContainer)super.clone();
/*     */     
/* 281 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/block/BlockContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */