/*     */ package edu.mines.jtk.util;
/*     */ 
/*     */ import java.util.Date;
/*     */ import java.util.logging.Level;
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
/*     */ public class LogMonitor
/*     */   implements Monitor
/*     */ {
/*  29 */   private Logger _log = null;
/*  30 */   private String _prefix = "";
/*  31 */   private volatile long _startTime = 0L;
/*  32 */   private volatile long _lastTime = 0L;
/*  33 */   private volatile long _currentTime = 0L;
/*  34 */   private volatile double _initFraction = 0.0D;
/*  35 */   private volatile double _lastFraction = 0.0D;
/*     */   private volatile boolean _canceled = false;
/*  37 */   private Thread _thread = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   private static final String NL = System.getProperty("line.separator");
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long SHORTEST_INTERVAL = 10000L;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long LONGEST_FIRST_INTERVAL = 60000L;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long LONGEST_INTERVAL = 900000L;
/*     */ 
/*     */ 
/*     */   
/*  58 */   private static final Logger LOG = Logger.getLogger(LogMonitor.class.getName(), LogMonitor.class
/*  59 */       .getName());
/*  60 */   private final boolean _debug = LOG.isLoggable(Level.FINE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LogMonitor(String prefix, Logger logger) {
/*  70 */     this._log = logger;
/*  71 */     if (prefix != null) {
/*  72 */       this._prefix = prefix;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initReport(double initFraction) {
/*  79 */     if (this._initFraction != 0.0D || initFraction < this._initFraction) {
/*  80 */       throw new IllegalStateException("initReport is being called twice, or with a bad value: new value of " + initFraction + " cannot replace previous value of " + this._initFraction);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     this._initFraction = Math.min(0.99999D, initFraction);
/*  87 */     report(initFraction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void report(double fraction) {
/*  94 */     fraction = (fraction - this._initFraction) / (1.0D - this._initFraction);
/*  95 */     synchronized (this) {
/*  96 */       if (fraction < this._lastFraction - 1.0E-4D) {
/*  97 */         IllegalStateException ex = new IllegalStateException("Progress cannot decrease from " + this._lastFraction + " to " + fraction);
/*     */         
/*  99 */         if (this._debug) {
/* 100 */           throw ex;
/*     */         }
/* 102 */         LOG.log(Level.WARNING, "", ex);
/* 103 */         this._lastFraction = fraction;
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 108 */       if (this._startTime == 0L) {
/* 109 */         this._currentTime = System.currentTimeMillis();
/* 110 */         this._startTime = this._currentTime;
/* 111 */         if (fraction < 1.0D) {
/* 112 */           this._thread = new UpdateTimeThread();
/* 113 */           this._thread.setDaemon(true);
/* 114 */           this._thread.start();
/*     */         } 
/* 116 */         print(fraction, this._currentTime);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 121 */       if (fraction >= 1.0D) {
/*     */         
/* 123 */         if (fraction > this._lastFraction) {
/* 124 */           this._currentTime = System.currentTimeMillis();
/* 125 */           print(fraction, this._currentTime);
/*     */         } 
/* 127 */         if (this._thread != null) {
/* 128 */           this._thread.interrupt();
/* 129 */           this._thread = null;
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 135 */       boolean print = false;
/* 136 */       if (this._currentTime > this._lastTime + 900000L) {
/* 137 */         this._currentTime = System.currentTimeMillis();
/*     */         
/* 139 */         print = true;
/*     */       } else {
/* 141 */         boolean significantProgress = (fraction > this._lastFraction + 0.02D || (fraction > this._lastFraction + 0.01D && this._lastFraction <= 0.02D));
/*     */ 
/*     */         
/* 144 */         boolean firstProgress = (this._lastFraction == 0.0D && fraction > this._lastFraction + 0.001D);
/*     */         
/* 146 */         if (significantProgress || firstProgress) {
/*     */           
/* 148 */           this._currentTime = System.currentTimeMillis();
/* 149 */           long interval = significantProgress ? 10000L : 60000L;
/*     */           
/* 151 */           if (this._currentTime >= this._lastTime + interval) {
/* 152 */             print = true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 158 */       if (print) {
/* 159 */         print(fraction, this._currentTime);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCanceled() {
/* 166 */     return this._canceled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancel() {
/* 173 */     this._canceled = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void print(double fraction, long currentTime) {
/* 184 */     synchronized (this) {
/* 185 */       if (this._log != null) {
/* 186 */         this._log.info(this._prefix + 
/* 187 */             getProgressReport(this._startTime, currentTime, fraction, this._initFraction));
/*     */       }
/* 189 */       this._lastFraction = fraction;
/* 190 */       this._lastTime = currentTime;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getProgressReport(long startTime, long currentTime, double fraction, double initFraction) {
/* 209 */     String progress = "";
/* 210 */     long secSoFar = (currentTime - startTime) / 1000L;
/* 211 */     if (secSoFar > 0L) {
/* 212 */       progress = Localize.timeWords(secSoFar) + " ${so_far}";
/* 213 */       long secRemaining = (fraction > 0.0D) ? (long)((1.0D / fraction - 1.0D) * secSoFar) : 0L;
/*     */ 
/*     */       
/* 216 */       if (secRemaining > 0L) {
/* 217 */         String remaining = Localize.timeWords(secRemaining) + " ${remaining}";
/* 218 */         if (progress.length() > 0) {
/* 219 */           progress = remaining + ", " + progress;
/*     */         } else {
/* 221 */           progress = remaining;
/*     */         } 
/*     */       } 
/* 224 */       long total = secSoFar + secRemaining;
/* 225 */       if (progress.length() > 0) {
/* 226 */         progress = NL + "  " + progress + ", " + Localize.timeWords(total) + " ${total}";
/*     */       }
/* 228 */       if (fraction >= 1.0D) {
/* 229 */         progress = NL + "  ${Finished_in} " + Localize.timeWords(total) + " ${total}";
/*     */       }
/*     */     } 
/*     */     
/* 233 */     int percent = (int)(100.0D * (initFraction + fraction * (1.0D - initFraction)) + 0.49D);
/*     */     
/* 235 */     String message = " ${progress}: " + percent + "% ${complete_at} " + new Date() + progress;
/*     */     
/* 237 */     message = Localize.filter(message, LogMonitor.class);
/* 238 */     return message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class UpdateTimeThread
/*     */     extends Thread
/*     */   {
/*     */     private UpdateTimeThread() {
/* 249 */       super("LogMonitor.UpdateTimeThread " + new Date());
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/*     */       try {
/* 255 */         while (LogMonitor.this._lastFraction < 0.9999D) {
/* 256 */           Thread.sleep(225000L);
/* 257 */           LogMonitor.this._currentTime = System.currentTimeMillis();
/* 258 */           if (LogMonitor.this._currentTime > LogMonitor.this._lastTime + 1800000L)
/*     */           {
/* 260 */             LogMonitor.this.print(LogMonitor.this._lastFraction, LogMonitor.this._currentTime);
/*     */           }
/*     */         } 
/* 263 */       } catch (InterruptedException interruptedException) {}
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
/*     */   public static void main(String[] argv) throws Exception {
/* 276 */     CleanHandler.setDefaultHandler();
/*     */     
/* 278 */     Monitor monitor = new LogMonitor("${Test}", LOG);
/* 279 */     monitor.report(0.0D);
/* 280 */     int pause = 25;
/* 281 */     Thread.sleep(25L);
/* 282 */     monitor.report(0.0D);
/* 283 */     int n = 25;
/* 284 */     for (int i = 0; i < 25; i++) {
/* 285 */       monitor.report(i / 24.0D);
/* 286 */       Thread.sleep(25L);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/LogMonitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */