/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import edu.mines.jtk.awt.Mode;
/*     */ import edu.mines.jtk.awt.ModeManager;
/*     */ import java.awt.Component;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.awt.event.MouseMotionAdapter;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ import javax.swing.KeyStroke;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SelectDragMode
/*     */   extends Mode
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private ViewCanvas _canvas;
/*     */   private View _view;
/*     */   private World _world;
/*     */   private PickResult _pickResult;
/*     */   private Selectable _selectable;
/*     */   private Dragable _dragable;
/*     */   private DragContext _dragContext;
/*     */   private boolean _selecting;
/*     */   private MouseListener _ml;
/*     */   private MouseMotionListener _mml;
/*     */   
/*     */   public SelectDragMode(ModeManager modeManager) {
/*  37 */     super(modeManager);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  76 */     this._ml = new MouseAdapter()
/*     */       {
/*     */         
/*     */         public void mousePressed(MouseEvent e)
/*     */         {
/*  81 */           SelectDragMode.this._selecting = true;
/*     */ 
/*     */           
/*  84 */           SelectDragMode.this._pickResult = SelectDragMode.this.pick(e);
/*  85 */           if (SelectDragMode.this._pickResult != null) {
/*  86 */             SelectDragMode.this._selectable = SelectDragMode.this._pickResult.getSelectableNode();
/*  87 */             SelectDragMode.this._dragable = SelectDragMode.this._pickResult.getDragableNode();
/*     */           } 
/*     */ 
/*     */           
/*  91 */           SelectDragMode.this._canvas = (ViewCanvas)e.getSource();
/*  92 */           SelectDragMode.this._view = SelectDragMode.this._canvas.getView();
/*  93 */           if (SelectDragMode.this._view != null) {
/*  94 */             SelectDragMode.this._world = SelectDragMode.this._view.getWorld();
/*     */           }
/*     */           
/*  97 */           SelectDragMode.this._canvas.addMouseMotionListener(SelectDragMode.this._mml);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public void mouseReleased(MouseEvent e) {
/* 103 */           if (SelectDragMode.this._dragable != null && SelectDragMode.this._dragContext != null) {
/* 104 */             SelectDragMode.this._dragable.dragEnd(SelectDragMode.this._dragContext);
/*     */ 
/*     */           
/*     */           }
/* 108 */           else if (SelectDragMode.this._selecting) {
/*     */ 
/*     */             
/* 111 */             if (e.isControlDown() || e.isAltDown()) {
/* 112 */               if (SelectDragMode.this._selectable != null) {
/* 113 */                 SelectDragMode.this._selectable.setSelected(!SelectDragMode.this._selectable.isSelected());
/*     */               
/*     */               }
/*     */             }
/* 117 */             else if (e.isShiftDown()) {
/* 118 */               if (SelectDragMode.this._selectable != null) {
/* 119 */                 SelectDragMode.this._selectable.setSelected(true);
/*     */               
/*     */               }
/*     */             
/*     */             }
/* 124 */             else if (SelectDragMode.this._selectable != null) {
/* 125 */               SelectDragMode.this._world.clearSelectedExcept(SelectDragMode.this._selectable);
/* 126 */               SelectDragMode.this._selectable.setSelected(true);
/*     */             }
/* 128 */             else if (SelectDragMode.this._world != null) {
/* 129 */               SelectDragMode.this._world.clearSelected();
/*     */             } 
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 135 */           SelectDragMode.this._dragable = null;
/* 136 */           SelectDragMode.this._dragContext = null;
/* 137 */           SelectDragMode.this._selectable = null;
/* 138 */           SelectDragMode.this._selecting = false;
/*     */ 
/*     */           
/* 141 */           SelectDragMode.this._canvas.removeMouseMotionListener(SelectDragMode.this._mml);
/*     */         }
/*     */       };
/*     */     
/* 145 */     this._mml = new MouseMotionAdapter()
/*     */       {
/*     */         
/*     */         public void mouseDragged(MouseEvent e)
/*     */         {
/* 150 */           SelectDragMode.this._selecting = (SelectDragMode.this._dragContext == null);
/* 151 */           if (SelectDragMode.this._selecting && SelectDragMode.this._pickResult != null) {
/* 152 */             Point3 pp = SelectDragMode.this._pickResult.getPointPixel();
/* 153 */             Point3 pd = new Point3(e.getX(), e.getY(), pp.z);
/* 154 */             if (pp.distanceTo(pd) >= 2.0D) {
/* 155 */               SelectDragMode.this._selecting = false;
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 160 */           if (!SelectDragMode.this._selecting && SelectDragMode.this._dragable != null && SelectDragMode.this._dragContext == null) {
/* 161 */             SelectDragMode.this._dragContext = new DragContext(SelectDragMode.this._pickResult);
/* 162 */             SelectDragMode.this._dragable.dragBegin(SelectDragMode.this._dragContext);
/*     */           } 
/*     */ 
/*     */           
/* 166 */           if (SelectDragMode.this._dragable != null && SelectDragMode.this._dragContext != null) {
/* 167 */             SelectDragMode.this._dragContext.update(e);
/* 168 */             SelectDragMode.this._dragable.drag(SelectDragMode.this._dragContext);
/*     */           }  } }; setName("Select"); Class<SelectDragMode> cls = SelectDragMode.class;
/*     */     setIcon(loadIcon(cls, "SelectDragIcon16.png"));
/*     */     setCursor(loadCursor(cls, "SelectDragCursor16.png", 1, 1));
/*     */     setMnemonicKey(83);
/*     */     setAcceleratorKey(KeyStroke.getKeyStroke(83, 0));
/* 174 */     setShortDescription("Select/drag"); } private PickResult pick(MouseEvent event) { ViewCanvas canvas = (ViewCanvas)event.getSource();
/*     */     
/* 176 */     View view = canvas.getView();
/* 177 */     if (view == null)
/* 178 */       return null; 
/* 179 */     World world = view.getWorld();
/* 180 */     if (world == null)
/* 181 */       return null; 
/* 182 */     PickContext pc = new PickContext(event);
/* 183 */     world.pickApply(pc);
/* 184 */     PickResult pickResult = pc.getClosest();
/* 185 */     if (pickResult != null) {
/* 186 */       Point3 pointLocal = pickResult.getPointLocal();
/* 187 */       Point3 pointWorld = pickResult.getPointWorld();
/* 188 */       System.out.println("Pick");
/* 189 */       System.out.println("  local=" + pointLocal);
/* 190 */       System.out.println("  world=" + pointWorld);
/*     */     } else {
/* 192 */       System.out.println("Pick nothing");
/*     */     } 
/* 194 */     return pickResult; }
/*     */ 
/*     */   
/*     */   protected void setActive(Component component, boolean active) {
/*     */     if (component instanceof ViewCanvas)
/*     */       if (active) {
/*     */         component.addMouseListener(this._ml);
/*     */       } else {
/*     */         component.removeMouseListener(this._ml);
/*     */         this._selecting = false;
/*     */         this._selectable = null;
/*     */         this._dragable = null;
/*     */         this._dragContext = null;
/*     */       }  
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/SelectDragMode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */