/*     */ package org.apache.commons.math3.ode.nonstiff;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import org.apache.commons.math3.ode.AbstractIntegrator;
/*     */ import org.apache.commons.math3.ode.EquationsMapper;
/*     */ import org.apache.commons.math3.ode.sampling.AbstractStepInterpolator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class RungeKuttaStepInterpolator
/*     */   extends AbstractStepInterpolator
/*     */ {
/*     */   protected double[] previousState;
/*     */   protected double[][] yDotK;
/*     */   protected AbstractIntegrator integrator;
/*     */   
/*     */   protected RungeKuttaStepInterpolator() {
/*  60 */     this.previousState = null;
/*  61 */     this.yDotK = (double[][])null;
/*  62 */     this.integrator = null;
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
/*     */   RungeKuttaStepInterpolator(RungeKuttaStepInterpolator interpolator) {
/*  84 */     super(interpolator);
/*     */     
/*  86 */     if (interpolator.currentState != null) {
/*     */       
/*  88 */       this.previousState = (double[])interpolator.previousState.clone();
/*     */       
/*  90 */       this.yDotK = new double[interpolator.yDotK.length][];
/*  91 */       for (int k = 0; k < interpolator.yDotK.length; k++) {
/*  92 */         this.yDotK[k] = (double[])interpolator.yDotK[k].clone();
/*     */       }
/*     */     } else {
/*     */       
/*  96 */       this.previousState = null;
/*  97 */       this.yDotK = (double[][])null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 102 */     this.integrator = null;
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
/*     */   public void reinitialize(AbstractIntegrator rkIntegrator, double[] y, double[][] yDotArray, boolean forward, EquationsMapper primaryMapper, EquationsMapper[] secondaryMappers) {
/* 133 */     reinitialize(y, forward, primaryMapper, secondaryMappers);
/* 134 */     this.previousState = null;
/* 135 */     this.yDotK = yDotArray;
/* 136 */     this.integrator = rkIntegrator;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void shift() {
/* 142 */     this.previousState = (double[])this.currentState.clone();
/* 143 */     super.shift();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeExternal(ObjectOutput out) throws IOException {
/* 152 */     writeBaseExternal(out);
/*     */ 
/*     */     
/* 155 */     int n = (this.currentState == null) ? -1 : this.currentState.length;
/* 156 */     for (int i = 0; i < n; i++) {
/* 157 */       out.writeDouble(this.previousState[i]);
/*     */     }
/*     */     
/* 160 */     int kMax = (this.yDotK == null) ? -1 : this.yDotK.length;
/* 161 */     out.writeInt(kMax);
/* 162 */     for (int k = 0; k < kMax; k++) {
/* 163 */       for (int j = 0; j < n; j++) {
/* 164 */         out.writeDouble(this.yDotK[k][j]);
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
/*     */ 
/*     */   
/*     */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
/* 178 */     double t = readBaseExternal(in);
/*     */ 
/*     */     
/* 181 */     int n = (this.currentState == null) ? -1 : this.currentState.length;
/* 182 */     if (n < 0) {
/* 183 */       this.previousState = null;
/*     */     } else {
/* 185 */       this.previousState = new double[n];
/* 186 */       for (int i = 0; i < n; i++) {
/* 187 */         this.previousState[i] = in.readDouble();
/*     */       }
/*     */     } 
/*     */     
/* 191 */     int kMax = in.readInt();
/* 192 */     this.yDotK = (kMax < 0) ? (double[][])null : new double[kMax][];
/* 193 */     for (int k = 0; k < kMax; k++) {
/* 194 */       this.yDotK[k] = (n < 0) ? null : new double[n];
/* 195 */       for (int i = 0; i < n; i++) {
/* 196 */         this.yDotK[k][i] = in.readDouble();
/*     */       }
/*     */     } 
/*     */     
/* 200 */     this.integrator = null;
/*     */     
/* 202 */     if (this.currentState != null) {
/*     */       
/* 204 */       setInterpolatedTime(t);
/*     */     } else {
/* 206 */       this.interpolatedTime = t;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/RungeKuttaStepInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */