/*     */ package edu.mines.jtk.opt;
/*     */ 
/*     */ import edu.mines.jtk.util.Almost;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransformQuadratic
/*     */   implements Quadratic
/*     */ {
/*  69 */   private static final Logger LOG = Logger.getLogger("edu.mines.jtk.opt");
/*     */ 
/*     */ 
/*     */   
/*     */   private VectConst _data;
/*     */ 
/*     */ 
/*     */   
/*     */   private VectConst _referenceModel;
/*     */ 
/*     */ 
/*     */   
/*     */   private VectConst _perturbModel;
/*     */ 
/*     */ 
/*     */   
/*     */   private Transform _transform;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean _dampOnlyPerturbation = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public TransformQuadratic(VectConst data, VectConst referenceModel, VectConst perturbModel, Transform transform, boolean dampOnlyPerturbation) {
/*  94 */     this._data = data;
/*  95 */     this._referenceModel = referenceModel;
/*  96 */     this._perturbModel = perturbModel;
/*  97 */     this._transform = transform;
/*  98 */     this._dampOnlyPerturbation = dampOnlyPerturbation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTransposePrecision() {
/* 108 */     VectConst d = this._data;
/* 109 */     Vect b = getB();
/* 110 */     double bb = b.dot(b);
/* 111 */     checkNaN(bb);
/* 112 */     assert !Almost.FLOAT.zero(bb) : "Cannot test with zero-magnitude b";
/*     */ 
/*     */     
/* 115 */     Vect Fb = VectUtil.cloneZero(d);
/* 116 */     Vect bSave = b.clone();
/* 117 */     this._transform.forwardLinearized(Fb, b, this._referenceModel);
/*     */ 
/*     */     
/* 120 */     assert VectUtil.areSame(b, bSave) : "model was changed by forward model";
/* 121 */     bSave.dispose();
/*     */ 
/*     */     
/* 124 */     Vect test = d.clone();
/* 125 */     this._transform.forwardLinearized(test, b, this._referenceModel);
/* 126 */     assert VectUtil.areSame(test, Fb) : "forwardLinearized should zero data";
/* 127 */     test.dispose();
/*     */ 
/*     */     
/* 130 */     Vect Ad = VectUtil.cloneZero(b);
/* 131 */     Vect dSave = d.clone();
/* 132 */     this._transform.addTranspose(d, Ad, this._referenceModel);
/* 133 */     double transposeMagnitude = Ad.dot(Ad);
/* 134 */     checkNaN(transposeMagnitude);
/*     */ 
/*     */     
/* 137 */     assert VectUtil.areSame(d, dSave) : "data was changed by transpose";
/* 138 */     dSave.dispose();
/*     */ 
/*     */     
/* 141 */     test = b.clone();
/* 142 */     double scaleTest = 1.1D * Math.sqrt(transposeMagnitude / bb);
/* 143 */     VectUtil.scale(test, scaleTest);
/* 144 */     this._transform.addTranspose(d, test, this._referenceModel);
/* 145 */     assert !VectUtil.areSame(Ad, test) : "Transpose should not zero model.  Magnitude: b=" + bb + "trans=" + transposeMagnitude + " test=" + test
/* 146 */       .dot(test);
/* 147 */     test.add(1.0D, -1.0D, Ad);
/* 148 */     VectUtil.scale(test, 1.0D / scaleTest);
/* 149 */     assert VectUtil.areSame(test, b) : "Transpose did not add to model vector";
/* 150 */     test.dispose();
/*     */ 
/*     */     
/* 153 */     double dFb = d.dot(Fb);
/* 154 */     double Adb = Ad.dot(b);
/* 155 */     assert !Almost.FLOAT.zero(dFb) : "zero magnitude test: dFb is zero";
/* 156 */     assert !Almost.FLOAT.zero(Adb) : "zero magnitude test: Adb is zero";
/* 157 */     checkNaN(dFb);
/* 158 */     checkNaN(Adb);
/*     */     
/* 160 */     int significantDigits = 10;
/* 161 */     boolean matches = false;
/* 162 */     while (!matches && significantDigits > 0) {
/* 163 */       Almost almost = new Almost(significantDigits);
/* 164 */       matches = almost.equal(dFb, Adb);
/* 165 */       if (!matches) {
/* 166 */         significantDigits--;
/*     */       }
/*     */     } 
/* 169 */     if (significantDigits < 3) {
/* 170 */       LOG.severe("Transpose precision is unacceptable: dFb=" + dFb + " Adb=" + Adb);
/*     */     }
/* 172 */     Ad.dispose();
/* 173 */     Fb.dispose();
/* 174 */     b.dispose();
/* 175 */     return significantDigits;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void multiplyHessian(Vect x) {
/* 183 */     Vect data = this._data.clone();
/* 184 */     this._transform.forwardLinearized(data, x, this._referenceModel);
/* 185 */     data.multiplyInverseCovariance();
/* 186 */     x.multiplyInverseCovariance();
/* 187 */     this._transform.addTranspose(data, x, this._referenceModel);
/* 188 */     data.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void inverseHessian(Vect x) {
/* 194 */     this._transform.inverseHessian(x, this._referenceModel);
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
/*     */   public Vect getB() {
/* 206 */     Vect b, data = VectUtil.cloneZero(this._data);
/* 207 */     this._transform.forwardNonlinear(data, this._referenceModel);
/* 208 */     data.add(1.0D, -1.0D, this._data);
/* 209 */     this._transform.adjustRobustErrors(data);
/* 210 */     data.multiplyInverseCovariance();
/*     */     
/* 212 */     if (this._dampOnlyPerturbation) {
/* 213 */       if (this._perturbModel != null) {
/* 214 */         b = VectUtil.cloneZero(this._perturbModel);
/*     */       } else {
/* 216 */         b = VectUtil.cloneZero(this._referenceModel);
/*     */       } 
/*     */     } else {
/* 219 */       if (this._perturbModel != null) {
/* 220 */         b = this._perturbModel.clone();
/* 221 */         b.project(0.0D, 1.0D, this._referenceModel);
/*     */       } else {
/* 223 */         b = this._referenceModel.clone();
/*     */       } 
/* 225 */       b.multiplyInverseCovariance();
/*     */     } 
/* 227 */     this._transform.addTranspose(data, b, this._referenceModel);
/* 228 */     data.dispose();
/* 229 */     return b;
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
/*     */   public double evalFullObjectiveFunction(VectConst m) {
/* 250 */     Vect data = VectUtil.cloneZero(this._data);
/*     */     
/* 252 */     Vect model = m.clone();
/* 253 */     model.constrain();
/*     */     
/* 255 */     this._transform.forwardNonlinear(data, model);
/* 256 */     data.add(1.0D, -1.0D, this._data);
/* 257 */     this._transform.adjustRobustErrors(data);
/* 258 */     double eNe = data.magnitude();
/* 259 */     checkNaN(eNe);
/* 260 */     data.dispose();
/*     */ 
/*     */     
/* 263 */     if (this._dampOnlyPerturbation) {
/* 264 */       model.add(1.0D, -1.0D, this._referenceModel);
/*     */     }
/*     */     
/* 267 */     double mMm = model.magnitude();
/* 268 */     checkNaN(mMm);
/* 269 */     model.dispose();
/* 270 */     return eNe + mMm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkNaN(double value) {
/* 277 */     if (value * 0.0D != 0.0D)
/* 278 */       throw new IllegalStateException("Value is a NaN"); 
/*     */   }
/*     */   
/*     */   public void dispose() {}
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/opt/TransformQuadratic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */