/*     */ package edu.mines.jtk.mesh;
/*     */ import edu.mines.jtk.mosaic.Projector;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class TriMeshView extends TiledView {
/*     */   private TriMesh _mesh;
/*     */   private float _xmin;
/*     */   private float _xmax;
/*     */   private float _ymin;
/*     */   private float _ymax;
/*     */   private Color _markColor;
/*     */   private double _markWidth;
/*     */   private Color _triLineColor;
/*     */   private Color _polyLineColor;
/*     */   private double _lineWidth;
/*     */   private boolean _drawNodes;
/*     */   private boolean _drawTris;
/*     */   private boolean _drawPolys;
/*     */   private boolean _drawSubTris;
/*     */   private boolean _drawTriBounds;
/*     */   private boolean _drawPolyBounds;
/*     */   private TriPainter _triPainter;
/*     */   private Map<TriMesh.Edge, Float> _triEdgeWeights;
/*     */   private Map<TriMesh.Edge, Color> _triEdgeColors;
/*     */   private Orientation _orientation;
/*     */   
/*     */   public static interface TriPainter {
/*     */     void paint(Graphics2D param1Graphics2D, TriMesh.Tri param1Tri, TriMesh.Node param1Node1, int param1Int1, int param1Int2, TriMesh.Node param1Node2, int param1Int3, int param1Int4, TriMesh.Node param1Node3, int param1Int5, int param1Int6);
/*     */   }
/*     */   
/*     */   public enum Orientation {
/*  37 */     XRIGHT_YUP,
/*  38 */     XDOWN_YRIGHT;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TriMeshView(TriMesh mesh) {
/* 693 */     this._markColor = Color.red;
/* 694 */     this._markWidth = 6.0D;
/* 695 */     this._triLineColor = Color.yellow;
/* 696 */     this._polyLineColor = Color.yellow;
/* 697 */     this._lineWidth = 0.0D;
/* 698 */     this._drawNodes = true;
/* 699 */     this._drawTris = true;
/* 700 */     this._drawPolys = false;
/* 701 */     this._drawSubTris = false;
/* 702 */     this._drawTriBounds = false;
/* 703 */     this._drawPolyBounds = false;
/* 704 */     this._triPainter = null;
/* 705 */     this._triEdgeWeights = null;
/* 706 */     this._triEdgeColors = null;
/*     */     
/* 708 */     this._orientation = Orientation.XRIGHT_YUP; setMesh(mesh);
/*     */   } public void setMesh(TriMesh mesh) { this._mesh = mesh; updateMinMax(); updateBestProjectors(); repaint(); } public void setOrientation(Orientation orientation) { if (this._orientation != orientation) { this._orientation = orientation; updateBestProjectors(); repaint(); }  } public Orientation getOrientation() { return this._orientation; } public void setNodesVisible(boolean drawNodes) { if (this._drawNodes != drawNodes) { this._drawNodes = drawNodes; repaint(); }  } public void setTrisVisible(boolean drawTris) { if (this._drawTris != drawTris) { this._drawTris = drawTris; repaint(); }  } public void setSubTrisVisible(boolean drawSubTris) { if (this._drawSubTris != drawSubTris) { this._drawSubTris = drawSubTris; repaint(); }  } public void setPolysVisible(boolean drawPolys) { if (this._drawPolys != drawPolys) { this._drawPolys = drawPolys; repaint(); }  } public void setTriBoundsVisible(boolean drawTriBounds) { if (this._drawTriBounds != drawTriBounds) { this._drawTriBounds = drawTriBounds; repaint(); }  } public void setPolyBoundsVisible(boolean drawPolyBounds) { if (this._drawPolyBounds != drawPolyBounds) { this._drawPolyBounds = drawPolyBounds; repaint(); }  } public void showNodes() { setNodesVisible(true); } public void hideNodes() { setNodesVisible(false); } public void showTris() { setTrisVisible(true); } public void hideTris() { setTrisVisible(false); }
/*     */   public void showPolys() { setPolysVisible(true); }
/* 711 */   private float x(TriMesh.Node node) { if (this._orientation == Orientation.XRIGHT_YUP) {
/* 712 */       return node.x();
/*     */     }
/* 714 */     return node.y(); } public void hidePolys() { setPolysVisible(false); } public void showTriBounds() { setTriBoundsVisible(true); } public void hideTriBounds() { setTriBoundsVisible(false); }
/*     */   public void showPolyBounds() { setPolyBoundsVisible(true); }
/*     */   public void hidePolyBounds() { setPolyBoundsVisible(false); }
/*     */   public void setLineColor(Color lineColor) { this._triLineColor = lineColor; this._polyLineColor = lineColor; repaint(); }
/*     */   public void setTriColor(Color lineColor) { this._triLineColor = lineColor; repaint(); }
/* 719 */   private float y(TriMesh.Node node) { if (this._orientation == Orientation.XRIGHT_YUP) {
/* 720 */       return node.y();
/*     */     }
/* 722 */     return node.x(); } public void setTriEdgeWeights(Map<TriMesh.Edge, Float> triEdgeWeights) { this._triEdgeWeights = triEdgeWeights; this._triEdgeColors = null; repaint(); }
/*     */   public void setPolyColor(Color lineColor) { this._polyLineColor = lineColor;
/*     */     repaint(); }
/*     */   public void setLineWidth(int lineWidth) { this._lineWidth = lineWidth;
/*     */     updateBestProjectors();
/*     */     repaint(); }
/*     */   public void setMarkColor(Color markColor) { this._markColor = markColor;
/*     */     repaint(); }
/* 730 */   private void updateMinMax() { if (this._mesh == null)
/*     */       return; 
/* 732 */     this._xmin = Float.MAX_VALUE;
/* 733 */     this._ymin = Float.MAX_VALUE;
/* 734 */     this._xmax = -3.4028235E38F;
/* 735 */     this._ymax = -3.4028235E38F;
/* 736 */     TriMesh.NodeIterator ni = this._mesh.getNodes();
/* 737 */     while (ni.hasNext())
/* 738 */     { TriMesh.Node node = ni.next();
/* 739 */       float x = node.x();
/* 740 */       float y = node.y();
/* 741 */       if (x < this._xmin) this._xmin = x; 
/* 742 */       if (y < this._ymin) this._ymin = y; 
/* 743 */       if (x > this._xmax) this._xmax = x; 
/* 744 */       if (y > this._ymax) this._ymax = y;  }  }
/*     */   public void setMarkWidth(int markWidth) { this._markWidth = markWidth; updateBestProjectors(); repaint(); }
/*     */   public void setTriPainter(TriPainter triPainter) { this._triPainter = triPainter; }
/*     */   public void paint(Graphics2D g2d) { g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); Transcaler ts = getTranscaler(); Projector px = getHorizontalProjector(); double xv0 = px.v0(); double xv1 = px.v1(); double xu0 = px.u(xv0); double xu1 = px.u(xv1); double xd0 = ts.x(xu0); double xd1 = ts.x(xu1); double xscale = (xd1 - xd0) / (xv1 - xv0); double xshift = 0.5D + xd0 - xv0 * xscale; Projector py = getVerticalProjector(); double yv0 = py.v0(); double yv1 = py.v1(); double yu0 = py.u(yv0); double yu1 = py.u(yv1); double yd0 = ts.y(yu0); double yd1 = ts.y(yu1); double yscale = (yd1 - yd0) / (yv1 - yv0); double yshift = 0.5D + yd0 - yv0 * yscale; if (this._drawTris || this._drawPolys || this._drawTriBounds || this._drawPolyBounds) { Stroke defaultStroke = g2d.getStroke(); Stroke stroke = null; GeneralPath path = null; if (this._lineWidth > 1.0D) { stroke = new BasicStroke((float)this._lineWidth); path = new GeneralPath(); }  if (stroke != null) g2d.setStroke(stroke);  if (this._drawTris || this._drawTriBounds) { if (this._triEdgeWeights != null && this._triEdgeColors == null) this._triEdgeColors = convertEdgeWeightsToColors(this._triEdgeWeights);  boolean haveEdgeColors = (this._triEdgeColors != null); TriMesh.NodePropertyMap colorMap = null; if (this._mesh.hasNodePropertyMap("Color")) colorMap = this._mesh.getNodePropertyMap("Color");  g2d.setColor(this._triLineColor); TriMesh.TriIterator ti = this._mesh.getTris(); while (ti.hasNext()) { TriMesh.Tri tri = ti.next(); if (this._mesh.isOuter(tri)) continue;  TriMesh.Node na = tri.nodeA(); TriMesh.Node nb = tri.nodeB(); TriMesh.Node nc = tri.nodeC(); Color ca = Color.WHITE; Color cb = Color.WHITE; Color cc = Color.WHITE; if (colorMap != null) { ca = (Color)colorMap.get(na); cb = (Color)colorMap.get(nb); cc = (Color)colorMap.get(nc); }  Color cab = null; Color cbc = null; Color cca = null; if (this._drawTris && haveEdgeColors) { cab = this._triEdgeColors.get(this._mesh.findEdge(na, nb)); cbc = this._triEdgeColors.get(this._mesh.findEdge(nb, nc)); cca = this._triEdgeColors.get(this._mesh.findEdge(nc, na)); if (cab == null) cab = this._triLineColor;  if (cbc == null)
/*     */               cbc = this._triLineColor;  if (cca == null)
/*     */               cca = this._triLineColor;  }  double xaa = xshift + x(na) * xscale; double yaa = yshift + y(na) * yscale; double xbb = xshift + x(nb) * xscale; double ybb = yshift + y(nb) * yscale; double xcc = xshift + x(nc) * xscale; double ycc = yshift + y(nc) * yscale; int xa = (int)xaa; int ya = (int)yaa; int xb = (int)xbb; int yb = (int)ybb; int xc = (int)xcc; int yc = (int)ycc; int xd = 0; int yd = 0; int xe = 0; int ye = 0; int xf = 0; int yf = 0; int xg = 0; int yg = 0; if (this._drawSubTris || this._drawTriBounds) { double xab = 0.5D * (xaa + xbb); double yab = 0.5D * (yaa + ybb); double xbc = 0.5D * (xbb + xcc); double ybc = 0.5D * (ybb + ycc); double xca = 0.5D * (xcc + xaa); double yca = 0.5D * (ycc + yaa); double xabc = 0.333D * (xaa + xbb + xcc); double yabc = 0.333D * (yaa + ybb + ycc); xd = (int)xab; yd = (int)yab; xe = (int)xbc; ye = (int)ybc; xf = (int)xca; yf = (int)yca; xg = (int)xabc; yg = (int)yabc; }  if (this._drawTris) { if (this._triPainter != null)
/*     */               this._triPainter.paint(g2d, tri, na, xa, ya, nb, xb, yb, nc, xc, yc);  if (path == null) { if (haveEdgeColors) { if (cab != null)
/*     */                   g2d.setColor(cab);  g2d.drawLine(xa, ya, xb, yb); if (cbc != null)
/*     */                   g2d.setColor(cbc);  g2d.drawLine(xb, yb, xc, yc); if (cca != null)
/*     */                   g2d.setColor(cca);  g2d.drawLine(xc, yc, xa, ya); g2d.setColor(this._triLineColor); } else { g2d.drawLine(xa, ya, xb, yb); g2d.drawLine(xb, yb, xc, yc); g2d.drawLine(xc, yc, xa, ya); }  if (this._drawSubTris) { g2d.drawLine(xd, yd, xe, ye); g2d.drawLine(xe, ye, xf, yf); g2d.drawLine(xf, yf, xd, yd); }  } else { if (haveEdgeColors) { GeneralPath gp = new GeneralPath(); g2d.setColor(cab); gp.moveTo(xa, ya); gp.lineTo(xb, yb); g2d.draw(gp); gp.reset(); g2d.setColor(cbc); gp.moveTo(xb, yb); gp.lineTo(xc, yc); g2d.draw(gp); gp.reset(); g2d.setColor(cbc); gp.moveTo(xc, yc); gp.lineTo(xa, ya); g2d.draw(gp); gp.reset(); } else { path.moveTo(xa, ya); path.lineTo(xb, yb); path.lineTo(xc, yc); path.lineTo(xa, ya); }  if (this._drawSubTris) { path.moveTo(xd, yd); path.lineTo(xe, ye); path.lineTo(xf, yf); path.lineTo(xd, yd); }  }  }  if (this._drawTriBounds) { if (!ca.equals(cb) && !cb.equals(cc) && !cc.equals(ca)) { drawLine(g2d, path, xd, yd, xg, yg); drawLine(g2d, path, xe, ye, xg, yg); drawLine(g2d, path, xf, yf, xg, yg); continue; }  if (ca.equals(cb) && !cb.equals(cc)) { drawLine(g2d, path, xe, ye, xf, yf); continue; }  if (cb.equals(cc) && !cc.equals(ca)) { drawLine(g2d, path, xd, yd, xf, yf); continue; }  if (cc.equals(ca) && !ca.equals(cb))
/*     */               drawLine(g2d, path, xd, yd, xe, ye);  }  }  }  if (this._drawPolys || this._drawPolyBounds) { TriMesh.NodePropertyMap colorMap = null; if (this._mesh.hasNodePropertyMap("Color"))
/*     */           colorMap = this._mesh.getNodePropertyMap("Color");  g2d.setColor(this._polyLineColor); float[] pa = new float[2]; float[] pb = new float[2]; float[] pc = new float[2]; float[] pn = new float[2]; float[] qc = new float[2]; TriMesh.TriIterator ti = this._mesh.getTris(); while (ti.hasNext()) { TriMesh.Tri tri = ti.next(); if (this._mesh.isOuter(tri))
/*     */             continue;  TriMesh.Node na = tri.nodeA(); TriMesh.Node nb = tri.nodeB(); TriMesh.Node nc = tri.nodeC(); Color ca = Color.WHITE; Color cb = Color.WHITE; Color cc = Color.WHITE; if (colorMap != null) { ca = (Color)colorMap.get(na); cb = (Color)colorMap.get(nb); cc = (Color)colorMap.get(nc); }  pa[0] = x(na); pa[1] = y(na); pb[0] = x(nb); pb[1] = y(nb); pc[0] = x(nc); pc[1] = y(nc); circumcenter(pa, pb, pc, qc); int x1 = (int)(xshift + qc[0] * xscale); int y1 = (int)(yshift + qc[1] * yscale); if (this._drawPolys || !cb.equals(cc)) { TriMesh.Tri ta = tri.triNabor(na); if (ta != null && this._mesh.isInner(ta)) { TriMesh.Node nn = tri.nodeNabor(ta); pn[0] = x(nn); pn[1] = y(nn); circumcenter(pn, pc, pb, qc); int x2 = (int)(xshift + qc[0] * xscale); int y2 = (int)(yshift + qc[1] * yscale); drawLine(g2d, path, x1, y1, x2, y2); } else { float xb = pb[0]; float yb = pb[1]; float xc = pc[0]; float yc = pc[1]; int x2 = (int)(xshift + (0.5F * (xb + xc)) * xscale); int y2 = (int)(yshift + (0.5F * (yb + yc)) * yscale); drawLine(g2d, path, x1, y1, x2, y2); }  }  if (this._drawPolys || !ca.equals(cc)) { TriMesh.Tri tb = tri.triNabor(nb); if (tb != null && this._mesh.isInner(tb)) { TriMesh.Node nn = tri.nodeNabor(tb); pn[0] = x(nn); pn[1] = y(nn); circumcenter(pn, pa, pc, qc); int x2 = (int)(xshift + qc[0] * xscale); int y2 = (int)(yshift + qc[1] * yscale); drawLine(g2d, path, x1, y1, x2, y2); } else { float xa = pa[0]; float ya = pa[1]; float xc = pc[0]; float yc = pc[1]; int x2 = (int)(xshift + (0.5F * (xa + xc)) * xscale); int y2 = (int)(yshift + (0.5F * (ya + yc)) * yscale); drawLine(g2d, path, x1, y1, x2, y2); }  }  if (this._drawPolys || !ca.equals(cb)) { TriMesh.Tri tc = tri.triNabor(nc); if (tc != null && this._mesh.isInner(tc)) { TriMesh.Node nn = tri.nodeNabor(tc); pn[0] = x(nn); pn[1] = y(nn); circumcenter(pn, pb, pa, qc); int i = (int)(xshift + qc[0] * xscale); int j = (int)(yshift + qc[1] * yscale); drawLine(g2d, path, x1, y1, i, j); continue; }  float xa = pa[0]; float ya = pa[1]; float xb = pb[0]; float yb = pb[1]; int x2 = (int)(xshift + (0.5F * (xa + xb)) * xscale); int y2 = (int)(yshift + (0.5F * (ya + yb)) * yscale); drawLine(g2d, path, x1, y1, x2, y2); }  }  }  if (path != null)
/*     */         g2d.draw(path);  if (stroke != null)
/*     */         g2d.setStroke(defaultStroke);  }  if (this._drawNodes) { g2d.setColor(this._markColor); int halfWidth = (int)(this._markWidth / 2.0D + 0.51D); int markWidth = 1 + 2 * halfWidth; TriMesh.NodePropertyMap colorMap = null; if (this._mesh.hasNodePropertyMap("Color"))
/* 759 */         colorMap = this._mesh.getNodePropertyMap("Color");  TriMesh.NodeIterator ni = this._mesh.getNodes(); while (ni.hasNext()) { float xn, yn; TriMesh.Node node = ni.next(); if (this._orientation == Orientation.XRIGHT_YUP) { xn = node.x(); yn = node.y(); } else { xn = node.y(); yn = node.x(); }  int x = (int)(xshift + xn * xscale); int y = (int)(yshift + yn * yscale); if (colorMap != null) { Color color = (Color)colorMap.get(node); if (color != null) { g2d.setColor((Color)colorMap.get(node)); g2d.fillOval(x - halfWidth, y - halfWidth, markWidth, markWidth); continue; }  g2d.setColor(Color.white); g2d.drawOval(x - halfWidth, y - halfWidth, markWidth, markWidth); continue; }  g2d.fillOval(x - halfWidth, y - halfWidth, markWidth, markWidth); }  }  } private void updateBestProjectors() { double u0 = 0.0D;
/* 760 */     double u1 = 1.0D;
/* 761 */     if ((this._drawNodes && this._markWidth > 1.0D) || this._lineWidth > 1.0D) {
/* 762 */       u0 = 0.01D;
/* 763 */       u1 = 0.99D;
/*     */     } 
/*     */ 
/*     */     
/* 767 */     Projector bhp = null;
/* 768 */     Projector bvp = null;
/* 769 */     if (this._orientation == Orientation.XRIGHT_YUP) {
/* 770 */       bhp = (this._xmin < this._xmax) ? new Projector(this._xmin, this._xmax, u0, u1) : null;
/* 771 */       bvp = (this._ymin < this._ymax) ? new Projector(this._ymax, this._ymin, u0, u1) : null;
/* 772 */     } else if (this._orientation == Orientation.XDOWN_YRIGHT) {
/* 773 */       bhp = (this._ymin < this._ymax) ? new Projector(this._ymin, this._ymax, u0, u1) : null;
/* 774 */       bvp = (this._xmin < this._xmax) ? new Projector(this._xmin, this._xmax, u0, u1) : null;
/*     */     } 
/* 776 */     setBestProjectors(bhp, bvp); }
/*     */ 
/*     */ 
/*     */   
/*     */   private void circumcenter(float[] pa, float[] pb, float[] pc, float[] cc) {
/* 781 */     double xa = pa[0];
/* 782 */     double ya = pa[1];
/* 783 */     double xb = pb[0];
/* 784 */     double yb = pb[1];
/* 785 */     double xc = pc[0];
/* 786 */     double yc = pc[1];
/* 787 */     double xba = xb - xa;
/* 788 */     double yba = yb - ya;
/* 789 */     double xca = xc - xa;
/* 790 */     double yca = yc - ya;
/* 791 */     double dba = xba * xba + yba * yba;
/* 792 */     double dca = xca * xca + yca * yca;
/* 793 */     double scl = 0.5D / (xba * yca - yba * xca);
/* 794 */     double xcc = xa + scl * (yca * dba - yba * dca);
/* 795 */     double ycc = ya + scl * (xba * dca - xca * dba);
/* 796 */     cc[0] = (float)xcc;
/* 797 */     cc[1] = (float)ycc;
/*     */   } private static void drawLine(Graphics2D g2d, GeneralPath path, int x1, int y1, int x2, int y2) {
/*     */     if (path == null) {
/*     */       g2d.drawLine(x1, y1, x2, y2);
/*     */     } else {
/*     */       path.moveTo(x1, y1);
/*     */       path.lineTo(x2, y2);
/*     */     } 
/*     */   } private static Map<TriMesh.Edge, Color> convertEdgeWeightsToColors(Map<TriMesh.Edge, Float> edgeWeights) {
/* 806 */     float wmin = Float.MAX_VALUE;
/* 807 */     float wmax = -wmin;
/*     */     
/* 809 */     Iterator<Map.Entry<TriMesh.Edge, Float>> ei = edgeWeights.entrySet().iterator();
/* 810 */     while (ei.hasNext()) {
/* 811 */       Map.Entry<TriMesh.Edge, Float> entry = ei.next();
/* 812 */       float w = ((Float)entry.getValue()).floatValue();
/* 813 */       if (w < wmin) wmin = w; 
/* 814 */       if (w > wmax) wmax = w; 
/*     */     } 
/* 816 */     Map<TriMesh.Edge, Color> edgeColors = new HashMap<>();
/* 817 */     float hueMin = 0.0F;
/* 818 */     float hueMax = 0.5F;
/* 819 */     ei = edgeWeights.entrySet().iterator();
/* 820 */     while (ei.hasNext()) {
/* 821 */       Map.Entry<TriMesh.Edge, Float> entry = ei.next();
/* 822 */       TriMesh.Edge e = entry.getKey();
/* 823 */       float w = ((Float)entry.getValue()).floatValue();
/* 824 */       float hue = hueMin + (w - wmin) * (hueMax - hueMin) / (wmax - wmin);
/* 825 */       Color color = Color.getHSBColor(hue, 1.0F, 1.0F);
/* 826 */       edgeColors.put(e, color);
/*     */     } 
/* 828 */     return edgeColors;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/mesh/TriMeshView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */