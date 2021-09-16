/*     */ package org.apache.commons.math3.ode.sampling;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.linear.Array2DRowRealMatrix;
/*     */ import org.apache.commons.math3.ode.EquationsMapper;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NordsieckStepInterpolator
/*     */   extends AbstractStepInterpolator
/*     */ {
/*     */   private static final long serialVersionUID = -7179861704951334960L;
/*     */   protected double[] stateVariation;
/*     */   private double scalingH;
/*     */   private double referenceTime;
/*     */   private double[] scaled;
/*     */   private Array2DRowRealMatrix nordsieck;
/*     */   
/*     */   public NordsieckStepInterpolator() {}
/*     */   
/*     */   public NordsieckStepInterpolator(NordsieckStepInterpolator interpolator) {
/*  82 */     super(interpolator);
/*  83 */     this.scalingH = interpolator.scalingH;
/*  84 */     this.referenceTime = interpolator.referenceTime;
/*  85 */     if (interpolator.scaled != null) {
/*  86 */       this.scaled = (double[])interpolator.scaled.clone();
/*     */     }
/*  88 */     if (interpolator.nordsieck != null) {
/*  89 */       this.nordsieck = new Array2DRowRealMatrix(interpolator.nordsieck.getDataRef(), true);
/*     */     }
/*  91 */     if (interpolator.stateVariation != null) {
/*  92 */       this.stateVariation = (double[])interpolator.stateVariation.clone();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected StepInterpolator doCopy() {
/*  99 */     return new NordsieckStepInterpolator(this);
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
/*     */   public void reinitialize(double[] y, boolean forward, EquationsMapper primaryMapper, EquationsMapper[] secondaryMappers) {
/* 115 */     super.reinitialize(y, forward, primaryMapper, secondaryMappers);
/* 116 */     this.stateVariation = new double[y.length];
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
/*     */   public void reinitialize(double time, double stepSize, double[] scaledDerivative, Array2DRowRealMatrix nordsieckVector) {
/* 132 */     this.referenceTime = time;
/* 133 */     this.scalingH = stepSize;
/* 134 */     this.scaled = scaledDerivative;
/* 135 */     this.nordsieck = nordsieckVector;
/*     */ 
/*     */     
/* 138 */     setInterpolatedTime(getInterpolatedTime());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rescale(double stepSize) {
/* 149 */     double ratio = stepSize / this.scalingH;
/* 150 */     for (int i = 0; i < this.scaled.length; i++) {
/* 151 */       this.scaled[i] = this.scaled[i] * ratio;
/*     */     }
/*     */     
/* 154 */     double[][] nData = this.nordsieck.getDataRef();
/* 155 */     double power = ratio;
/* 156 */     for (int j = 0; j < nData.length; j++) {
/* 157 */       power *= ratio;
/* 158 */       double[] nDataI = nData[j];
/* 159 */       for (int k = 0; k < nDataI.length; k++) {
/* 160 */         nDataI[k] = nDataI[k] * power;
/*     */       }
/*     */     } 
/*     */     
/* 164 */     this.scalingH = stepSize;
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
/*     */   public double[] getInterpolatedStateVariation() throws MaxCountExceededException {
/* 183 */     getInterpolatedState();
/* 184 */     return this.stateVariation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void computeInterpolatedStateAndDerivatives(double theta, double oneMinusThetaH) {
/* 191 */     double x = this.interpolatedTime - this.referenceTime;
/* 192 */     double normalizedAbscissa = x / this.scalingH;
/*     */     
/* 194 */     Arrays.fill(this.stateVariation, 0.0D);
/* 195 */     Arrays.fill(this.interpolatedDerivatives, 0.0D);
/*     */ 
/*     */ 
/*     */     
/* 199 */     double[][] nData = this.nordsieck.getDataRef();
/* 200 */     for (int i = nData.length - 1; i >= 0; i--) {
/* 201 */       int order = i + 2;
/* 202 */       double[] nDataI = nData[i];
/* 203 */       double power = FastMath.pow(normalizedAbscissa, order);
/* 204 */       for (int k = 0; k < nDataI.length; k++) {
/* 205 */         double d = nDataI[k] * power;
/* 206 */         this.stateVariation[k] = this.stateVariation[k] + d;
/* 207 */         this.interpolatedDerivatives[k] = this.interpolatedDerivatives[k] + order * d;
/*     */       } 
/*     */     } 
/*     */     
/* 211 */     for (int j = 0; j < this.currentState.length; j++) {
/* 212 */       this.stateVariation[j] = this.stateVariation[j] + this.scaled[j] * normalizedAbscissa;
/* 213 */       this.interpolatedState[j] = this.currentState[j] + this.stateVariation[j];
/* 214 */       this.interpolatedDerivatives[j] = (this.interpolatedDerivatives[j] + this.scaled[j] * normalizedAbscissa) / x;
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
/*     */   public void writeExternal(ObjectOutput out) throws IOException {
/* 226 */     writeBaseExternal(out);
/*     */ 
/*     */     
/* 229 */     out.writeDouble(this.scalingH);
/* 230 */     out.writeDouble(this.referenceTime);
/*     */     
/* 232 */     int n = (this.currentState == null) ? -1 : this.currentState.length;
/* 233 */     if (this.scaled == null) {
/* 234 */       out.writeBoolean(false);
/*     */     } else {
/* 236 */       out.writeBoolean(true);
/* 237 */       for (int j = 0; j < n; j++) {
/* 238 */         out.writeDouble(this.scaled[j]);
/*     */       }
/*     */     } 
/*     */     
/* 242 */     if (this.nordsieck == null) {
/* 243 */       out.writeBoolean(false);
/*     */     } else {
/* 245 */       out.writeBoolean(true);
/* 246 */       out.writeObject(this.nordsieck);
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
/* 259 */     double t = readBaseExternal(in);
/*     */ 
/*     */     
/* 262 */     this.scalingH = in.readDouble();
/* 263 */     this.referenceTime = in.readDouble();
/*     */     
/* 265 */     int n = (this.currentState == null) ? -1 : this.currentState.length;
/* 266 */     boolean hasScaled = in.readBoolean();
/* 267 */     if (hasScaled) {
/* 268 */       this.scaled = new double[n];
/* 269 */       for (int j = 0; j < n; j++) {
/* 270 */         this.scaled[j] = in.readDouble();
/*     */       }
/*     */     } else {
/* 273 */       this.scaled = null;
/*     */     } 
/*     */     
/* 276 */     boolean hasNordsieck = in.readBoolean();
/* 277 */     if (hasNordsieck) {
/* 278 */       this.nordsieck = (Array2DRowRealMatrix)in.readObject();
/*     */     } else {
/* 280 */       this.nordsieck = null;
/*     */     } 
/*     */     
/* 283 */     if (hasScaled && hasNordsieck) {
/*     */       
/* 285 */       this.stateVariation = new double[n];
/* 286 */       setInterpolatedTime(t);
/*     */     } else {
/* 288 */       this.stateVariation = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/sampling/NordsieckStepInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */