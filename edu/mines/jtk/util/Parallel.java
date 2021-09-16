/*     */ package edu.mines.jtk.util;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ForkJoinPool;
/*     */ import java.util.concurrent.RecursiveAction;
/*     */ import java.util.concurrent.RecursiveTask;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Parallel
/*     */ {
/*     */   private static final int NSQT = 6;
/*     */   
/*     */   public static interface LoopInt
/*     */   {
/*     */     void compute(int param1Int);
/*     */   }
/*     */   
/*     */   public static interface ReduceInt<V>
/*     */   {
/*     */     V compute(int param1Int);
/*     */     
/*     */     V combine(V param1V1, V param1V2);
/*     */   }
/*     */   
/*     */   public static class Unsafe<T>
/*     */   {
/*     */     private ConcurrentHashMap<Thread, T> _map;
/*     */     
/*     */     public Unsafe() {
/* 225 */       int initialCapacity = 16;
/* 226 */       float loadFactor = 0.5F;
/* 227 */       int concurrencyLevel = 2 * Parallel._pool.getParallelism();
/* 228 */       this._map = new ConcurrentHashMap<>(initialCapacity, loadFactor, concurrencyLevel);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public T get() {
/* 237 */       return this._map.get(Thread.currentThread());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void set(T object) {
/* 245 */       this._map.put(Thread.currentThread(), object);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Collection<T> getAll() {
/* 254 */       return this._map.values();
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
/*     */   public static void loop(int end, LoopInt body) {
/* 266 */     loop(0, end, 1, 1, body);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void loop(int begin, int end, LoopInt body) {
/* 276 */     loop(begin, end, 1, 1, body);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void loop(int begin, int end, int step, LoopInt body) {
/* 287 */     loop(begin, end, step, 1, body);
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
/*     */   public static void loop(int begin, int end, int step, int chunk, LoopInt body) {
/* 301 */     checkArgs(begin, end, step, chunk);
/* 302 */     if (_serial || end <= begin + chunk * step) {
/* 303 */       int i; for (i = begin; i < end; i += step) {
/* 304 */         body.compute(i);
/*     */       }
/*     */     } else {
/* 307 */       LoopIntAction task = new LoopIntAction(begin, end, step, chunk, body);
/* 308 */       if (LoopIntAction.inForkJoinPool()) {
/* 309 */         task.invoke();
/*     */       } else {
/* 311 */         _pool.invoke(task);
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
/*     */   public static <V> V reduce(int end, ReduceInt<V> body) {
/* 323 */     return reduce(0, end, 1, 1, body);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> V reduce(int begin, int end, ReduceInt<V> body) {
/* 334 */     return reduce(begin, end, 1, 1, body);
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
/*     */   public static <V> V reduce(int begin, int end, int step, ReduceInt<V> body) {
/* 348 */     return reduce(begin, end, step, 1, body);
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
/*     */   public static <V> V reduce(int begin, int end, int step, int chunk, ReduceInt<V> body) {
/* 363 */     checkArgs(begin, end, step, chunk);
/* 364 */     if (_serial || end <= begin + chunk * step) {
/* 365 */       V v = body.compute(begin); int i;
/* 366 */       for (i = begin + step; i < end; i += step) {
/* 367 */         V vi = body.compute(i);
/* 368 */         v = body.combine(v, vi);
/*     */       } 
/* 370 */       return v;
/*     */     } 
/* 372 */     ReduceIntTask<V> task = new ReduceIntTask<>(begin, end, step, chunk, body);
/* 373 */     if (ReduceIntTask.inForkJoinPool()) {
/* 374 */       return task.invoke();
/*     */     }
/* 376 */     return _pool.invoke(task);
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
/*     */   public static void setParallel(boolean parallel) {
/* 392 */     _serial = !parallel;
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
/* 413 */   private static ForkJoinPool _pool = new ForkJoinPool();
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean _serial = false;
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkArgs(int begin, int end, int step, int chunk) {
/* 422 */     Check.argument((begin < end), "begin<end");
/* 423 */     Check.argument((step > 0), "step>0");
/* 424 */     Check.argument((chunk > 0), "chunk>0");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int middle(int begin, int end, int step) {
/* 432 */     return begin + step + (end - begin - 1) / 2 / step * step;
/*     */   }
/*     */   
/*     */   private static class LoopIntAction extends RecursiveAction {
/*     */     private int _begin;
/*     */     private int _end;
/*     */     
/*     */     LoopIntAction(int begin, int end, int step, int chunk, Parallel.LoopInt body) {
/* 440 */       assert begin < end : "begin < end";
/* 441 */       this._begin = begin;
/* 442 */       this._end = end;
/* 443 */       this._step = step;
/* 444 */       this._chunk = chunk;
/* 445 */       this._body = body;
/*     */     } private int _step; private int _chunk; private Parallel.LoopInt _body;
/*     */     protected void compute() {
/* 448 */       if (this._end <= this._begin + this._chunk * this._step || getSurplusQueuedTaskCount() > 6) {
/* 449 */         for (int i = this._begin; i < this._end; i += this._step) {
/* 450 */           this._body.compute(i);
/*     */         }
/*     */       } else {
/* 453 */         int middle = Parallel.middle(this._begin, this._end, this._step);
/* 454 */         LoopIntAction l = new LoopIntAction(this._begin, middle, this._step, this._chunk, this._body);
/*     */         
/* 456 */         LoopIntAction r = (middle < this._end) ? new LoopIntAction(middle, this._end, this._step, this._chunk, this._body) : null;
/*     */ 
/*     */         
/* 459 */         if (r != null)
/* 460 */           r.fork(); 
/* 461 */         l.compute();
/* 462 */         if (r != null)
/* 463 */           r.join(); 
/*     */       } 
/*     */     } }
/*     */   
/*     */   private static class ReduceIntTask<V> extends RecursiveTask<V> {
/*     */     private int _begin;
/*     */     private int _end;
/*     */     private int _step;
/*     */     private int _chunk;
/*     */     private Parallel.ReduceInt<V> _body;
/*     */     
/*     */     ReduceIntTask(int begin, int end, int step, int chunk, Parallel.ReduceInt<V> body) {
/* 475 */       assert begin < end : "begin < end";
/* 476 */       this._begin = begin;
/* 477 */       this._end = end;
/* 478 */       this._step = step;
/* 479 */       this._chunk = chunk;
/* 480 */       this._body = body;
/*     */     }
/*     */     protected V compute() {
/* 483 */       if (this._end <= this._begin + this._chunk * this._step || getSurplusQueuedTaskCount() > 6) {
/* 484 */         V v1 = this._body.compute(this._begin);
/* 485 */         for (int i = this._begin + this._step; i < this._end; i += this._step) {
/* 486 */           V vi = this._body.compute(i);
/* 487 */           v1 = this._body.combine(v1, vi);
/*     */         } 
/* 489 */         return v1;
/*     */       } 
/* 491 */       int middle = Parallel.middle(this._begin, this._end, this._step);
/* 492 */       ReduceIntTask<V> l = new ReduceIntTask(this._begin, middle, this._step, this._chunk, this._body);
/*     */       
/* 494 */       ReduceIntTask<V> r = (middle < this._end) ? new ReduceIntTask(middle, this._end, this._step, this._chunk, this._body) : null;
/*     */ 
/*     */       
/* 497 */       if (r != null)
/* 498 */         r.fork(); 
/* 499 */       V v = l.compute();
/* 500 */       if (r != null)
/* 501 */         v = this._body.combine(v, r.join()); 
/* 502 */       return v;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/Parallel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */