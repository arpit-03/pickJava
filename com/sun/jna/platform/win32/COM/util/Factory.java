/*     */ package com.sun.jna.platform.win32.COM.util;
/*     */ 
/*     */ import com.sun.jna.platform.win32.COM.IDispatch;
/*     */ import com.sun.jna.platform.win32.COM.IDispatchCallback;
/*     */ import com.sun.jna.platform.win32.COM.util.annotation.ComObject;
/*     */ import com.sun.jna.platform.win32.Guid;
/*     */ import com.sun.jna.platform.win32.OaIdl;
/*     */ import com.sun.jna.platform.win32.OleAuto;
/*     */ import com.sun.jna.platform.win32.Variant;
/*     */ import com.sun.jna.platform.win32.WinDef;
/*     */ import com.sun.jna.platform.win32.WinNT;
/*     */ import com.sun.jna.ptr.IntByReference;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Factory
/*     */   extends ObjectFactory
/*     */ {
/*     */   private ComThread comThread;
/*     */   
/*     */   public Factory() {
/*  59 */     this(new ComThread("Default Factory COM Thread", 5000L, new Thread.UncaughtExceptionHandler()
/*     */           {
/*     */             public void uncaughtException(Thread t, Throwable e) {}
/*     */           }));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Factory(ComThread comThread) {
/*  68 */     this.comThread = comThread;
/*     */   }
/*     */   
/*     */   private class ProxyObject2
/*     */     implements InvocationHandler {
/*     */     private final Object delegate;
/*     */     
/*     */     public ProxyObject2(Object delegate) {
/*  76 */       this.delegate = delegate;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
/*  81 */       if (args != null) {
/*  82 */         for (int i = 0; i < args.length; i++) {
/*  83 */           if (args[i] != null && 
/*  84 */             Proxy.isProxyClass(args[i].getClass())) {
/*  85 */             InvocationHandler ih = Proxy.getInvocationHandler(args[i]);
/*  86 */             if (ih instanceof ProxyObject2) {
/*  87 */               args[i] = ((ProxyObject2)ih).delegate;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/*  93 */       return Factory.this.comThread.execute(new Callable()
/*     */           {
/*     */             public Object call() throws Exception {
/*  96 */               return method.invoke(Factory.ProxyObject2.this.delegate, args);
/*     */             }
/*     */           });
/*     */     }
/*     */   }
/*     */   
/*     */   private class CallbackProxy2
/*     */     extends CallbackProxy {
/*     */     public CallbackProxy2(ObjectFactory factory, Class<?> comEventCallbackInterface, IComEventCallbackListener comEventCallbackListener) {
/* 105 */       super(factory, comEventCallbackInterface, comEventCallbackListener);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinNT.HRESULT Invoke(OaIdl.DISPID dispIdMember, Guid.REFIID riid, WinDef.LCID lcid, WinDef.WORD wFlags, OleAuto.DISPPARAMS.ByReference pDispParams, Variant.VARIANT.ByReference pVarResult, OaIdl.EXCEPINFO.ByReference pExcepInfo, IntByReference puArgErr) {
/* 112 */       ComThread.setComThread(true);
/*     */       try {
/* 114 */         return super.Invoke(dispIdMember, riid, lcid, wFlags, pDispParams, pVarResult, pExcepInfo, puArgErr);
/*     */       } finally {
/* 116 */         ComThread.setComThread(false);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T createProxy(Class<T> comInterface, IDispatch dispatch) {
/* 123 */     T result = super.createProxy(comInterface, dispatch);
/* 124 */     ProxyObject2 po2 = new ProxyObject2(result);
/* 125 */     Object proxy = Proxy.newProxyInstance(comInterface.getClassLoader(), new Class[] { comInterface }, po2);
/* 126 */     return (T)proxy;
/*     */   }
/*     */ 
/*     */   
/*     */   Guid.GUID discoverClsId(final ComObject annotation) {
/* 131 */     return runInComThread(new Callable<Guid.GUID>() {
/*     */           public Guid.GUID call() throws Exception {
/* 133 */             return Factory.this.discoverClsId(annotation);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T fetchObject(final Class<T> comInterface) {
/* 141 */     return runInComThread(new Callable<T>() {
/*     */           public T call() throws Exception {
/* 143 */             return Factory.this.fetchObject(comInterface);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T createObject(final Class<T> comInterface) {
/* 151 */     return runInComThread(new Callable<T>() {
/*     */           public T call() throws Exception {
/* 153 */             return Factory.this.createObject(comInterface);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   IDispatchCallback createDispatchCallback(Class<?> comEventCallbackInterface, IComEventCallbackListener comEventCallbackListener) {
/* 160 */     return new CallbackProxy2(this, comEventCallbackInterface, comEventCallbackListener);
/*     */   }
/*     */ 
/*     */   
/*     */   public IRunningObjectTable getRunningObjectTable() {
/* 165 */     return super.getRunningObjectTable();
/*     */   }
/*     */   
/*     */   private <T> T runInComThread(Callable<T> callable) {
/*     */     try {
/* 170 */       return this.comThread.execute(callable);
/* 171 */     } catch (TimeoutException ex) {
/* 172 */       throw new RuntimeException(ex);
/* 173 */     } catch (InterruptedException ex) {
/* 174 */       throw new RuntimeException(ex);
/* 175 */     } catch (ExecutionException ex) {
/* 176 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public ComThread getComThread() {
/* 181 */     return this.comThread;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/COM/util/Factory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */