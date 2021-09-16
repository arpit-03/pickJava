/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StateSet
/*     */   implements State
/*     */ {
/*     */   public static StateSet forTwoSidedShinySurface(Color color) {
/*  53 */     StateSet ss = new StateSet();
/*  54 */     if (color != null) {
/*  55 */       ColorState cs = new ColorState();
/*  56 */       cs.setColor(color);
/*  57 */       ss.add(cs);
/*     */     } 
/*  59 */     LightModelState lms = new LightModelState();
/*  60 */     lms.setTwoSide(true);
/*  61 */     ss.add(lms);
/*  62 */     MaterialState ms = new MaterialState();
/*  63 */     ms.setColorMaterial(5634);
/*  64 */     ms.setSpecular(Color.white);
/*  65 */     ms.setShininess(100.0F);
/*  66 */     ss.add(ms);
/*  67 */     return ss;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(State state) {
/*  75 */     this._states.add(state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(State state) {
/*  83 */     this._states.remove(state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(Class<?> stateClass) {
/*  92 */     return (find(stateClass) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public State find(Class<?> stateClass) {
/* 101 */     for (State s : this._states) {
/* 102 */       if (s.getClass().equals(stateClass))
/* 103 */         return s; 
/*     */     } 
/* 105 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<State> getStates() {
/* 113 */     return this._states.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlendState getBlendState() {
/* 121 */     return (BlendState)find(BlendState.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ColorState getColorState() {
/* 129 */     return (ColorState)find(ColorState.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LightModelState getLightModelState() {
/* 137 */     return (LightModelState)find(LightModelState.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LineState getLineState() {
/* 145 */     return (LineState)find(LineState.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MaterialState getMaterialState() {
/* 153 */     return (MaterialState)find(MaterialState.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PointState getPointState() {
/* 161 */     return (PointState)find(PointState.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolygonState getPolygonState() {
/* 169 */     return (PolygonState)find(PolygonState.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply() {
/* 176 */     for (State s : this._states) {
/* 177 */       s.apply();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAttributeBits() {
/* 185 */     int attributeBits = 0;
/* 186 */     for (State state : this._states)
/* 187 */       attributeBits |= state.getAttributeBits(); 
/* 188 */     return attributeBits;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 194 */   private Set<State> _states = new HashSet<>();
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/StateSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */