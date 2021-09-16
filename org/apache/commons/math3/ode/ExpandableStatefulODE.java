/*     */ package org.apache.commons.math3.ode;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExpandableStatefulODE
/*     */ {
/*     */   private final FirstOrderDifferentialEquations primary;
/*     */   private final EquationsMapper primaryMapper;
/*     */   private double time;
/*     */   private final double[] primaryState;
/*     */   private final double[] primaryStateDot;
/*     */   private List<SecondaryComponent> components;
/*     */   
/*     */   public ExpandableStatefulODE(FirstOrderDifferentialEquations primary) {
/*  73 */     int n = primary.getDimension();
/*  74 */     this.primary = primary;
/*  75 */     this.primaryMapper = new EquationsMapper(0, n);
/*  76 */     this.time = Double.NaN;
/*  77 */     this.primaryState = new double[n];
/*  78 */     this.primaryStateDot = new double[n];
/*  79 */     this.components = new ArrayList<SecondaryComponent>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FirstOrderDifferentialEquations getPrimary() {
/*  86 */     return this.primary;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTotalDimension() {
/*  96 */     if (this.components.isEmpty())
/*     */     {
/*  98 */       return this.primaryMapper.getDimension();
/*     */     }
/*     */     
/* 101 */     EquationsMapper lastMapper = (this.components.get(this.components.size() - 1)).mapper;
/* 102 */     return lastMapper.getFirstIndex() + lastMapper.getDimension();
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
/*     */   public void computeDerivatives(double t, double[] y, double[] yDot) throws MaxCountExceededException, DimensionMismatchException {
/* 117 */     this.primaryMapper.extractEquationData(y, this.primaryState);
/* 118 */     this.primary.computeDerivatives(t, this.primaryState, this.primaryStateDot);
/*     */ 
/*     */     
/* 121 */     for (SecondaryComponent component : this.components) {
/* 122 */       component.mapper.extractEquationData(y, component.state);
/* 123 */       component.equation.computeDerivatives(t, this.primaryState, this.primaryStateDot, component.state, component.stateDot);
/*     */       
/* 125 */       component.mapper.insertEquationData(component.stateDot, yDot);
/*     */     } 
/*     */     
/* 128 */     this.primaryMapper.insertEquationData(this.primaryStateDot, yDot);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int addSecondaryEquations(SecondaryEquations secondary) {
/*     */     int firstIndex;
/* 139 */     if (this.components.isEmpty()) {
/*     */       
/* 141 */       this.components = new ArrayList<SecondaryComponent>();
/* 142 */       firstIndex = this.primary.getDimension();
/*     */     } else {
/* 144 */       SecondaryComponent last = this.components.get(this.components.size() - 1);
/* 145 */       firstIndex = last.mapper.getFirstIndex() + last.mapper.getDimension();
/*     */     } 
/*     */     
/* 148 */     this.components.add(new SecondaryComponent(secondary, firstIndex));
/*     */     
/* 150 */     return this.components.size() - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EquationsMapper getPrimaryMapper() {
/* 159 */     return this.primaryMapper;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EquationsMapper[] getSecondaryMappers() {
/* 167 */     EquationsMapper[] mappers = new EquationsMapper[this.components.size()];
/* 168 */     for (int i = 0; i < mappers.length; i++) {
/* 169 */       mappers[i] = (this.components.get(i)).mapper;
/*     */     }
/* 171 */     return mappers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTime(double time) {
/* 178 */     this.time = time;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getTime() {
/* 185 */     return this.time;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPrimaryState(double[] primaryState) throws DimensionMismatchException {
/* 196 */     if (primaryState.length != this.primaryState.length) {
/* 197 */       throw new DimensionMismatchException(primaryState.length, this.primaryState.length);
/*     */     }
/*     */ 
/*     */     
/* 201 */     System.arraycopy(primaryState, 0, this.primaryState, 0, primaryState.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getPrimaryState() {
/* 209 */     return (double[])this.primaryState.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getPrimaryStateDot() {
/* 216 */     return (double[])this.primaryStateDot.clone();
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
/*     */   public void setSecondaryState(int index, double[] secondaryState) throws DimensionMismatchException {
/* 230 */     double[] localArray = (this.components.get(index)).state;
/*     */ 
/*     */     
/* 233 */     if (secondaryState.length != localArray.length) {
/* 234 */       throw new DimensionMismatchException(secondaryState.length, localArray.length);
/*     */     }
/*     */ 
/*     */     
/* 238 */     System.arraycopy(secondaryState, 0, localArray, 0, secondaryState.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getSecondaryState(int index) {
/* 248 */     return (double[])(this.components.get(index)).state.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getSecondaryStateDot(int index) {
/* 257 */     return (double[])(this.components.get(index)).stateDot.clone();
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
/*     */   public void setCompleteState(double[] completeState) throws DimensionMismatchException {
/* 269 */     if (completeState.length != getTotalDimension()) {
/* 270 */       throw new DimensionMismatchException(completeState.length, getTotalDimension());
/*     */     }
/*     */ 
/*     */     
/* 274 */     this.primaryMapper.extractEquationData(completeState, this.primaryState);
/* 275 */     for (SecondaryComponent component : this.components) {
/* 276 */       component.mapper.extractEquationData(completeState, component.state);
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
/*     */   public double[] getCompleteState() throws DimensionMismatchException {
/* 289 */     double[] completeState = new double[getTotalDimension()];
/*     */ 
/*     */     
/* 292 */     this.primaryMapper.insertEquationData(this.primaryState, completeState);
/* 293 */     for (SecondaryComponent component : this.components) {
/* 294 */       component.mapper.insertEquationData(component.state, completeState);
/*     */     }
/*     */     
/* 297 */     return completeState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SecondaryComponent
/*     */   {
/*     */     private final SecondaryEquations equation;
/*     */ 
/*     */ 
/*     */     
/*     */     private final EquationsMapper mapper;
/*     */ 
/*     */ 
/*     */     
/*     */     private final double[] state;
/*     */ 
/*     */     
/*     */     private final double[] stateDot;
/*     */ 
/*     */ 
/*     */     
/*     */     SecondaryComponent(SecondaryEquations equation, int firstIndex) {
/* 321 */       int n = equation.getDimension();
/* 322 */       this.equation = equation;
/* 323 */       this.mapper = new EquationsMapper(firstIndex, n);
/* 324 */       this.state = new double[n];
/* 325 */       this.stateDot = new double[n];
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/ExpandableStatefulODE.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */