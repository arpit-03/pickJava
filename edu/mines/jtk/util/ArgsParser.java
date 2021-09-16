/*     */ package edu.mines.jtk.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ArgsParser
/*     */ {
/*     */   private String[] _args;
/*     */   private String _shortOpts;
/*     */   private String[] _longOpts;
/*     */   private String _longOpt;
/*     */   private ArrayList<String> _optList;
/*     */   private ArrayList<String> _valList;
/*     */   private ArrayList<String> _argList;
/*     */   
/*     */   public static class OptionException
/*     */     extends Exception
/*     */   {
/*     */     private OptionException(String msg) {
/*  89 */       super(msg);
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
/*     */   public ArgsParser(String[] args, String shortOpts) throws OptionException {
/* 104 */     this._args = args;
/* 105 */     this._shortOpts = shortOpts;
/* 106 */     this._longOpts = new String[0];
/* 107 */     init();
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
/*     */   public ArgsParser(String[] args, String shortOpts, String[] longOpts) throws OptionException {
/* 128 */     this._args = args;
/* 129 */     this._shortOpts = shortOpts;
/* 130 */     this._longOpts = longOpts;
/* 131 */     init();
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
/*     */   public String[] getOptions() {
/* 143 */     return getStrings(this._optList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getValues() {
/* 152 */     return getStrings(this._valList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getOtherArgs() {
/* 160 */     return getStrings(this._argList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean toBoolean(String s) throws OptionException {
/* 170 */     s = s.toLowerCase();
/* 171 */     if (s.equals("true"))
/* 172 */       return true; 
/* 173 */     if (s.equals("false"))
/* 174 */       return false; 
/* 175 */     throw new OptionException("the value " + s + " is not a valid boolean");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double toDouble(String s) throws OptionException {
/*     */     try {
/* 186 */       return Double.valueOf(s).doubleValue();
/* 187 */     } catch (NumberFormatException e) {
/* 188 */       throw new OptionException("the value " + s + " is not a valid double");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float toFloat(String s) throws OptionException {
/*     */     try {
/* 200 */       return Float.valueOf(s).floatValue();
/* 201 */     } catch (NumberFormatException e) {
/* 202 */       throw new OptionException("the value " + s + " is not a valid float");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int toInt(String s) throws OptionException {
/*     */     try {
/* 214 */       return Integer.parseInt(s);
/* 215 */     } catch (NumberFormatException e) {
/* 216 */       throw new OptionException("the value " + s + " is not a valid int");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long toLong(String s) throws OptionException {
/*     */     try {
/* 228 */       return Long.parseLong(s);
/* 229 */     } catch (NumberFormatException e) {
/* 230 */       throw new OptionException("the value " + s + " is not a valid long");
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
/*     */   private void init() throws OptionException {
/* 249 */     int n = this._args.length;
/* 250 */     this._optList = new ArrayList<>(n);
/* 251 */     this._valList = new ArrayList<>(n);
/* 252 */     this._argList = new ArrayList<>(n);
/* 253 */     for (int i = 0; i < n; i++)
/* 254 */       this._argList.add(this._args[i]); 
/* 255 */     while (!this._argList.isEmpty()) {
/* 256 */       String arg = this._argList.get(0);
/* 257 */       if (arg.charAt(0) != '-' || arg.equals("-"))
/*     */         break; 
/* 259 */       this._argList.remove(0);
/* 260 */       if (arg.equals("--"))
/*     */         break; 
/* 262 */       if (arg.charAt(1) == '-') {
/* 263 */         doLongOption(arg); continue;
/*     */       } 
/* 265 */       doShortOption(arg);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void doShortOption(String arg) throws OptionException {
/* 271 */     String argString = arg.substring(1);
/* 272 */     while (!argString.equals("")) {
/* 273 */       char opt = argString.charAt(0);
/* 274 */       argString = argString.substring(1);
/* 275 */       String val = "";
/* 276 */       if (shortOptionHasValue(opt)) {
/* 277 */         if (argString.equals("")) {
/* 278 */           if (this._argList.isEmpty())
/* 279 */             throw new OptionException("option -" + opt + " requires a value"); 
/* 280 */           argString = this._argList.remove(0);
/*     */         } 
/* 282 */         val = argString;
/* 283 */         argString = "";
/*     */       } 
/* 285 */       this._optList.add("-" + opt);
/* 286 */       this._valList.add(val);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean shortOptionHasValue(char opt) throws OptionException {
/* 291 */     for (int i = 0; i < this._shortOpts.length(); i++) {
/* 292 */       if (opt == this._shortOpts.charAt(i) && opt != ':')
/* 293 */         return (i + 1 < this._shortOpts.length() && this._shortOpts.charAt(i + 1) == ':'); 
/*     */     } 
/* 295 */     throw new OptionException("option -" + opt + " not recognized");
/*     */   }
/*     */   
/*     */   private void doLongOption(String arg) throws OptionException {
/* 299 */     String argString = arg.substring(2);
/* 300 */     String opt = argString;
/* 301 */     String val = null;
/* 302 */     int i = argString.indexOf('=');
/* 303 */     if (i >= 0) {
/* 304 */       opt = argString.substring(0, i);
/* 305 */       val = (i + 1 < argString.length()) ? argString.substring(i + 1) : "";
/*     */     } 
/* 307 */     boolean hasValue = longOptionHasValue(opt);
/* 308 */     opt = this._longOpt;
/* 309 */     if (hasValue) {
/* 310 */       if (val == null) {
/* 311 */         if (this._argList.isEmpty())
/* 312 */           throw new OptionException("option --" + opt + " requires a value"); 
/* 313 */         val = this._argList.remove(0);
/*     */       } 
/* 315 */     } else if (val != null) {
/* 316 */       throw new OptionException("option --" + opt + " must not have a value");
/*     */     } 
/* 318 */     this._optList.add("--" + opt);
/* 319 */     this._valList.add(val);
/*     */   }
/*     */   
/*     */   private boolean longOptionHasValue(String opt) throws OptionException {
/* 323 */     int lenOpt = opt.length();
/* 324 */     for (int i = 0; i < this._longOpts.length; i++) {
/* 325 */       String longOpt = this._longOpts[i];
/* 326 */       int lenLongOpt = longOpt.length();
/* 327 */       if (lenOpt <= lenLongOpt) {
/*     */         
/* 329 */         String x = longOpt.substring(0, lenOpt);
/* 330 */         if (x.equals(opt))
/*     */         
/* 332 */         { String y = longOpt.substring(lenOpt, longOpt.length());
/* 333 */           if (!y.equals("") && !y.equals("=") && i + 1 < this._longOpts.length && 
/* 334 */             opt.equals(this._longOpts[i + 1].substring(0, lenOpt)))
/* 335 */             throw new OptionException("option --" + opt + " not a unique prefix"); 
/* 336 */           int last = longOpt.length() - 1;
/* 337 */           if (longOpt.charAt(last) == '=') {
/* 338 */             this._longOpt = longOpt.substring(0, last);
/* 339 */             return true;
/*     */           } 
/* 341 */           this._longOpt = longOpt;
/* 342 */           return false; } 
/*     */       } 
/* 344 */     }  throw new OptionException("option --" + opt + " not recognized");
/*     */   }
/*     */   
/*     */   private static String[] getStrings(ArrayList<String> list) {
/* 348 */     int n = list.size();
/* 349 */     String[] a = new String[n];
/* 350 */     for (int i = 0; i < n; i++)
/* 351 */       a[i] = list.get(i); 
/* 352 */     return a;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/ArgsParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */