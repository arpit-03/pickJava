/*     */ package com.sun.jna.platform.win32.COM;
/*     */ 
/*     */ import com.sun.jna.platform.win32.Guid;
/*     */ import com.sun.jna.platform.win32.OleAuto;
/*     */ import com.sun.jna.platform.win32.Variant;
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class COMLateBindingObject
/*     */   extends COMBindingBaseObject
/*     */ {
/*     */   public COMLateBindingObject(IDispatch iDispatch) {
/*  47 */     super(iDispatch);
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
/*     */   public COMLateBindingObject(Guid.CLSID clsid, boolean useActiveInstance) {
/*  59 */     super(clsid, useActiveInstance);
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
/*     */   public COMLateBindingObject(String progId, boolean useActiveInstance) throws COMException {
/*  74 */     super(progId, useActiveInstance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected IDispatch getAutomationProperty(String propertyName) {
/*  85 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/*  86 */     oleMethod(2, result, 
/*  87 */         getIDispatch(), propertyName);
/*     */     
/*  89 */     return (IDispatch)result.getValue();
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
/*     */   protected IDispatch getAutomationProperty(String propertyName, COMLateBindingObject comObject) {
/* 103 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 104 */     oleMethod(2, result, comObject
/* 105 */         .getIDispatch(), propertyName);
/*     */     
/* 107 */     return (IDispatch)result.getValue();
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
/*     */   protected IDispatch getAutomationProperty(String propertyName, COMLateBindingObject comObject, Variant.VARIANT value) {
/* 123 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 124 */     oleMethod(2, result, comObject
/* 125 */         .getIDispatch(), propertyName, value);
/*     */     
/* 127 */     return (IDispatch)result.getValue();
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
/*     */   protected IDispatch getAutomationProperty(String propertyName, IDispatch iDispatch) {
/* 141 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 142 */     oleMethod(2, result, iDispatch, propertyName);
/*     */ 
/*     */     
/* 145 */     return (IDispatch)result.getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean getBooleanProperty(String propertyName) {
/* 156 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 157 */     oleMethod(2, result, 
/* 158 */         getIDispatch(), propertyName);
/*     */     
/* 160 */     return result.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Date getDateProperty(String propertyName) {
/* 171 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 172 */     oleMethod(2, result, 
/* 173 */         getIDispatch(), propertyName);
/*     */     
/* 175 */     return result.dateValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getIntProperty(String propertyName) {
/* 186 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 187 */     oleMethod(2, result, 
/* 188 */         getIDispatch(), propertyName);
/*     */     
/* 190 */     return result.intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected short getShortProperty(String propertyName) {
/* 201 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 202 */     oleMethod(2, result, 
/* 203 */         getIDispatch(), propertyName);
/*     */     
/* 205 */     return result.shortValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getStringProperty(String propertyName) {
/* 216 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 217 */     oleMethod(2, result, 
/* 218 */         getIDispatch(), propertyName);
/*     */     
/* 220 */     String res = result.stringValue();
/*     */     
/* 222 */     OleAuto.INSTANCE.VariantClear((Variant.VARIANT)result);
/*     */     
/* 224 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Variant.VARIANT invoke(String methodName) {
/* 235 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 236 */     oleMethod(1, result, getIDispatch(), methodName);
/*     */ 
/*     */     
/* 239 */     return (Variant.VARIANT)result;
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
/*     */   protected Variant.VARIANT invoke(String methodName, Variant.VARIANT arg) {
/* 252 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 253 */     oleMethod(1, result, getIDispatch(), methodName, arg);
/*     */ 
/*     */     
/* 256 */     return (Variant.VARIANT)result;
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
/*     */   protected Variant.VARIANT invoke(String methodName, Variant.VARIANT[] args) {
/* 269 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 270 */     oleMethod(1, result, getIDispatch(), methodName, args);
/*     */ 
/*     */     
/* 273 */     return (Variant.VARIANT)result;
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
/*     */   protected Variant.VARIANT invoke(String methodName, Variant.VARIANT arg1, Variant.VARIANT arg2) {
/* 288 */     return invoke(methodName, new Variant.VARIANT[] { arg1, arg2 });
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
/*     */   protected Variant.VARIANT invoke(String methodName, Variant.VARIANT arg1, Variant.VARIANT arg2, Variant.VARIANT arg3) {
/* 306 */     return invoke(methodName, new Variant.VARIANT[] { arg1, arg2, arg3 });
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
/*     */   protected Variant.VARIANT invoke(String methodName, Variant.VARIANT arg1, Variant.VARIANT arg2, Variant.VARIANT arg3, Variant.VARIANT arg4) {
/* 326 */     return invoke(methodName, new Variant.VARIANT[] { arg1, arg2, arg3, arg4 });
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
/*     */   protected void invokeNoReply(String methodName, IDispatch dispatch) {
/* 338 */     oleMethod(1, (Variant.VARIANT.ByReference)null, dispatch, methodName);
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
/*     */   protected void invokeNoReply(String methodName, COMLateBindingObject comObject) {
/* 351 */     oleMethod(1, (Variant.VARIANT.ByReference)null, comObject.getIDispatch(), methodName);
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
/*     */   protected void invokeNoReply(String methodName, IDispatch dispatch, Variant.VARIANT arg) {
/* 367 */     oleMethod(1, (Variant.VARIANT.ByReference)null, dispatch, methodName, arg);
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
/*     */   protected void invokeNoReply(String methodName, IDispatch dispatch, Variant.VARIANT arg1, Variant.VARIANT arg2) {
/* 384 */     oleMethod(1, (Variant.VARIANT.ByReference)null, dispatch, methodName, new Variant.VARIANT[] { arg1, arg2 });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void invokeNoReply(String methodName, COMLateBindingObject comObject, Variant.VARIANT arg1, Variant.VARIANT arg2) {
/* 390 */     oleMethod(1, (Variant.VARIANT.ByReference)null, comObject.getIDispatch(), methodName, new Variant.VARIANT[] { arg1, arg2 });
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
/*     */   protected void invokeNoReply(String methodName, COMLateBindingObject comObject, Variant.VARIANT arg) {
/* 406 */     oleMethod(1, (Variant.VARIANT.ByReference)null, comObject.getIDispatch(), methodName, arg);
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
/*     */   protected void invokeNoReply(String methodName, IDispatch dispatch, Variant.VARIANT[] args) {
/* 422 */     oleMethod(1, (Variant.VARIANT.ByReference)null, dispatch, methodName, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void invokeNoReply(String methodName) {
/* 433 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 434 */     oleMethod(1, result, getIDispatch(), methodName);
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
/*     */   protected void invokeNoReply(String methodName, Variant.VARIANT arg) {
/* 447 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 448 */     oleMethod(1, result, getIDispatch(), methodName, arg);
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
/*     */   protected void invokeNoReply(String methodName, Variant.VARIANT[] args) {
/* 461 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 462 */     oleMethod(1, result, getIDispatch(), methodName, args);
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
/*     */   protected void invokeNoReply(String methodName, Variant.VARIANT arg1, Variant.VARIANT arg2) {
/* 477 */     invokeNoReply(methodName, new Variant.VARIANT[] { arg1, arg2 });
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
/*     */   protected void invokeNoReply(String methodName, Variant.VARIANT arg1, Variant.VARIANT arg2, Variant.VARIANT arg3) {
/* 494 */     invokeNoReply(methodName, new Variant.VARIANT[] { arg1, arg2, arg3 });
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
/*     */   protected void invokeNoReply(String methodName, Variant.VARIANT arg1, Variant.VARIANT arg2, Variant.VARIANT arg3, Variant.VARIANT arg4) {
/* 513 */     invokeNoReply(methodName, new Variant.VARIANT[] { arg1, arg2, arg3, arg4 });
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
/*     */   protected void setProperty(String propertyName, boolean value) {
/* 525 */     oleMethod(4, (Variant.VARIANT.ByReference)null, getIDispatch(), propertyName, new Variant.VARIANT(value));
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
/*     */   protected void setProperty(String propertyName, Date value) {
/* 538 */     oleMethod(4, (Variant.VARIANT.ByReference)null, getIDispatch(), propertyName, new Variant.VARIANT(value));
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
/*     */   protected void setProperty(String propertyName, IDispatch value) {
/* 551 */     oleMethod(4, (Variant.VARIANT.ByReference)null, getIDispatch(), propertyName, new Variant.VARIANT(value));
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
/*     */   protected void setProperty(String propertyName, int value) {
/* 564 */     oleMethod(4, (Variant.VARIANT.ByReference)null, getIDispatch(), propertyName, new Variant.VARIANT(value));
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
/*     */   protected void setProperty(String propertyName, short value) {
/* 577 */     oleMethod(4, (Variant.VARIANT.ByReference)null, getIDispatch(), propertyName, new Variant.VARIANT(value));
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
/*     */   protected void setProperty(String propertyName, String value) {
/* 590 */     oleMethod(4, (Variant.VARIANT.ByReference)null, getIDispatch(), propertyName, new Variant.VARIANT(value));
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
/*     */   protected void setProperty(String propertyName, IDispatch iDispatch, Variant.VARIANT value) {
/* 606 */     oleMethod(4, (Variant.VARIANT.ByReference)null, iDispatch, propertyName, value);
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
/*     */   protected void setProperty(String propertyName, COMLateBindingObject comObject, Variant.VARIANT value) {
/* 622 */     oleMethod(4, (Variant.VARIANT.ByReference)null, comObject
/* 623 */         .getIDispatch(), propertyName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Variant.VARIANT toVariant() {
/* 632 */     return new Variant.VARIANT(getIDispatch());
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/COM/COMLateBindingObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */