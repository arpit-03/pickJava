/*     */ package edu.mines.jtk.util;
/*     */ 
/*     */ import java.util.Hashtable;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Units
/*     */   implements Cloneable
/*     */ {
/*     */   private static final int NPOWERS = 16;
/*     */   
/*     */   public Units(String definition) throws UnitsFormatException {
/*  76 */     Units units = unitsFromDefinition(definition);
/*  77 */     this._scale = units._scale;
/*  78 */     this._shift = units._shift;
/*  79 */     for (int i = 0; i < this._power.length; i++) {
/*  80 */       this._power[i] = units._power[i];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/*  90 */       Units units = (Units)super.clone();
/*  91 */       units._power = (byte[])this._power.clone();
/*  92 */       return units;
/*  93 */     } catch (CloneNotSupportedException e) {
/*  94 */       throw new InternalError();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object object) {
/* 104 */     if (object instanceof Units) return equals((Units)object); 
/* 105 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Units units) {
/* 114 */     if (this._scale != units._scale) return false; 
/* 115 */     if (this._shift != units._shift) return false; 
/* 116 */     for (int i = 0; i < this._power.length; i++) {
/* 117 */       if (this._power[i] != units._power[i]) return false; 
/*     */     } 
/* 119 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float toSI(float value) {
/* 130 */     return (float)((value - this._shift) * this._scale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double toSI(double value) {
/* 141 */     return (value - this._shift) * this._scale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float fromSI(float value) {
/* 152 */     return (float)(this._shift + value / this._scale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double fromSI(double value) {
/* 163 */     return this._shift + value / this._scale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float floatShiftFrom(Units units) {
/* 174 */     return (float)doubleShiftFrom(units);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double doubleShiftFrom(Units units) {
/* 185 */     Check.argument(units.haveDimensionsOf(this), "same dimensions");
/* 186 */     return fromSI(units.toSI(0.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float floatScaleFrom(Units units) {
/* 197 */     return (float)doubleScaleFrom(units);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double doubleScaleFrom(Units units) {
/* 208 */     Check.argument(units.haveDimensionsOf(this), "same dimensions");
/* 209 */     return fromSI(units.toSI(1.0D)) - doubleShiftFrom(units);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean haveDimensions() {
/* 217 */     for (int power : this._power) {
/* 218 */       if (power != 0) return true; 
/* 219 */     }  return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean haveDimensionsOf(Units units) {
/* 228 */     for (int i = 0; i < this._power.length; i++) {
/* 229 */       if (this._power[i] != units._power[i]) return false; 
/*     */     } 
/* 231 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String standardDefinition() {
/* 240 */     String sd = "";
/* 241 */     boolean appending = false;
/* 242 */     if (this._scale != 1.0D) {
/* 243 */       sd = sd + this._scale;
/* 244 */       appending = true;
/*     */     } 
/* 246 */     for (int i = 0; i < _nbase; i++) {
/* 247 */       if (this._power[i] != 0) {
/* 248 */         if (appending) sd = sd + " "; 
/* 249 */         sd = sd + _bases[i];
/* 250 */         if (this._power[i] != 1) sd = sd + "^" + this._power[i]; 
/* 251 */         appending = true;
/*     */       } 
/*     */     } 
/* 254 */     if (this._shift != 0.0D) {
/* 255 */       double abs_shift = (this._shift > 0.0D) ? this._shift : -this._shift;
/* 256 */       if (appending) sd = sd + " "; 
/* 257 */       sd = sd + ((this._shift > 0.0D) ? "+ " : "- ");
/* 258 */       sd = sd + abs_shift;
/*     */     } 
/* 260 */     return sd;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Units add(Units units, double s) {
/* 270 */     return ((Units)units.clone()).shift(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Units sub(Units units, double s) {
/* 280 */     return ((Units)units.clone()).shift(-s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Units mul(Units units, double s) {
/* 290 */     return ((Units)units.clone()).scale(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Units div(Units units, double s) {
/* 300 */     return ((Units)units.clone()).scale(1.0D / s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Units mul(Units units1, Units units2) {
/* 310 */     return ((Units)units1.clone()).mul(units2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Units div(Units units1, Units units2) {
/* 320 */     return ((Units)units1.clone()).div(units2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Units inv(Units units) {
/* 329 */     return ((Units)units.clone()).inv();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Units pow(Units units, int p) {
/* 339 */     return ((Units)units.clone()).pow(p);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized boolean define(String name, boolean plural, String definition) throws UnitsFormatException {
/* 375 */     return addDefinition(name, plural, definition);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized boolean isValidDefinition(String definition) {
/*     */     try {
/* 387 */       unitsFromDefinition(definition);
/* 388 */     } catch (UnitsFormatException e) {
/* 389 */       return false;
/*     */     } 
/* 391 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized boolean isDefined(String name) {
/* 400 */     return _table.containsKey(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Units scale(double s) {
/* 408 */     this._scale *= s;
/* 409 */     this._shift /= s;
/* 410 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   Units shift(double s) {
/* 415 */     this._shift += s;
/* 416 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   Units mul(Units u) {
/* 421 */     this._shift = (this._shift != 0.0D) ? (this._shift / u._scale) : (u._shift / this._scale);
/* 422 */     this._scale *= u._scale;
/* 423 */     for (int i = 0; i < this._power.length; i++) {
/* 424 */       this._power[i] = (byte)(this._power[i] + u._power[i]);
/*     */     }
/* 426 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   Units div(Units u) {
/* 431 */     this._shift *= u._scale;
/* 432 */     this._scale /= u._scale;
/* 433 */     for (int i = 0; i < this._power.length; i++) {
/* 434 */       this._power[i] = (byte)(this._power[i] - u._power[i]);
/*     */     }
/* 436 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   Units inv() {
/* 441 */     this._scale = 1.0D / this._scale;
/* 442 */     this._shift = 0.0D;
/* 443 */     for (int i = 0; i < this._power.length; i++) {
/* 444 */       this._power[i] = (byte)-this._power[i];
/*     */     }
/* 446 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   Units pow(int p) {
/* 451 */     this._scale = Math.pow(this._scale, p);
/* 452 */     this._shift = 0.0D;
/* 453 */     for (int i = 0; i < this._power.length; i++) {
/* 454 */       this._power[i] = (byte)(this._power[i] * (byte)p);
/*     */     }
/* 456 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static synchronized Units unitsFromName(String name) {
/* 464 */     if (name == null || name.equals("")) return new Units();
/*     */ 
/*     */     
/* 467 */     UnitsTableEntry entry = _table.get(name);
/* 468 */     if (entry != null) {
/* 469 */       return (Units)entry._units.clone();
/*     */     }
/*     */ 
/*     */     
/* 473 */     double factor = 1.0D;
/* 474 */     int index = findPrefix(name);
/* 475 */     boolean prefix = (index >= 0);
/* 476 */     if (prefix) {
/* 477 */       factor = _prefix_factor[index];
/* 478 */       String temp = name.substring(_prefix_string[index].length());
/* 479 */       entry = _table.get(temp);
/* 480 */       if (entry != null) {
/* 481 */         Units units = (Units)entry._units.clone();
/* 482 */         units.scale(factor);
/* 483 */         return units;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 488 */     boolean suffix = (name.length() > 0 && name.charAt(name.length() - 1) == 's');
/* 489 */     if (suffix) {
/* 490 */       name = name.substring(0, name.length() - 1);
/* 491 */       entry = _table.get(name);
/* 492 */       if (entry != null && entry._plural) {
/* 493 */         Units units = (Units)entry._units.clone();
/* 494 */         return units;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 499 */     if (prefix && suffix) {
/* 500 */       name = name.substring(_prefix_string[index].length());
/* 501 */       entry = _table.get(name);
/* 502 */       if (entry != null && entry._plural) {
/* 503 */         Units units = (Units)entry._units.clone();
/* 504 */         units.scale(factor);
/* 505 */         return units;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 510 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class UnitsTableEntry
/*     */   {
/*     */     String _name;
/*     */     
/*     */     boolean _plural;
/*     */     
/*     */     Units _units;
/*     */     
/*     */     UnitsTableEntry(String name, boolean plural, Units units) {
/* 523 */       this._name = null;
/* 524 */       this._plural = false;
/* 525 */       this._units = null;
/*     */       this._name = name;
/*     */       this._plural = plural;
/*     */       this._units = units;
/* 529 */     } } private static Hashtable<String, UnitsTableEntry> _table = null;
/* 530 */   private static String[] _bases = null;
/* 531 */   private static int _nbase = 0;
/* 532 */   private static final String[] _prefix_string = new String[] { "E", "G", "M", "P", "T", "Y", "Z", "a", "atto", "c", "centi", "d", "da", "deca", "deci", "deka", "exa", "f", "femto", "giga", "h", "hecto", "k", "kilo", "m", "mega", "micro", "milli", "n", "nano", "p", "peta", "pico", "tera", "u", "y", "yocto", "yotta", "z", "zepto", "zetta" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 539 */   private static final double[] _prefix_factor = new double[] { 1.0E18D, 1.0E9D, 1000000.0D, 1.0E15D, 1.0E12D, 1.0E24D, 1.0E21D, 1.0E-18D, 1.0E-18D, 0.01D, 0.01D, 0.1D, 10.0D, 10.0D, 0.1D, 10.0D, 1.0E18D, 1.0E-15D, 1.0E-15D, 1.0E9D, 100.0D, 100.0D, 1000.0D, 1000.0D, 0.001D, 1000000.0D, 1.0E-6D, 0.001D, 1.0E-9D, 1.0E-9D, 1.0E-12D, 1.0E15D, 1.0E-12D, 1.0E12D, 1.0E-6D, 1.0E-24D, 1.0E-24D, 1.0E24D, 1.0E-21D, 1.0E-21D, 1.0E21D };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 547 */     _table = new Hashtable<>();
/* 548 */     _bases = new String[16];
/* 549 */     loadTable();
/*     */   }
/*     */   
/* 552 */   private double _scale = 1.0D;
/* 553 */   private double _shift = 0.0D;
/* 554 */   private byte[] _power = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized Units unitsFromDefinition(String definition) throws UnitsFormatException {
/* 562 */     Units units = unitsFromName(definition);
/* 563 */     if (units != null) return units;
/*     */ 
/*     */     
/*     */     try {
/* 567 */       return UnitsParser.parse(definition);
/* 568 */     } catch (Exception e) {
/* 569 */       throw new UnitsFormatException(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int findPrefix(String name) {
/* 577 */     if (name.length() < 1) return -1; 
/* 578 */     char name0 = name.charAt(0);
/* 579 */     int length = 0;
/* 580 */     int index = -1;
/* 581 */     for (int i = 0; i < _prefix_string.length; i++) {
/* 582 */       String prefix = _prefix_string[i];
/* 583 */       char prefix0 = prefix.charAt(0);
/* 584 */       if (name0 <= prefix0) {
/* 585 */         if (name0 < prefix0)
/* 586 */           break;  if (name.startsWith(prefix) && length < prefix.length()) {
/* 587 */           length = prefix.length();
/* 588 */           index = i;
/*     */         } 
/*     */       } 
/* 591 */     }  return index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized boolean addDefinition(String name, boolean plural, String definition) throws UnitsFormatException {
/* 601 */     if (_table.containsKey(name)) return false;
/*     */ 
/*     */     
/* 604 */     if (definition == null || definition.equals("")) {
/* 605 */       Units units1 = new Units();
/* 606 */       if (_nbase >= units1._power.length - 1) return false; 
/* 607 */       units1._power[_nbase] = (byte)(units1._power[_nbase] + 1);
/* 608 */       _bases[_nbase++] = name;
/* 609 */       _table.put(name, new UnitsTableEntry(name, plural, units1));
/* 610 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 614 */     Units units = unitsFromDefinition(definition);
/*     */ 
/*     */     
/* 617 */     _table.put(name, new UnitsTableEntry(name, plural, units));
/* 618 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private static synchronized void loadTable() {
/* 623 */     String[] specs = UnitsSpecs.specs;
/* 624 */     for (String spec : specs) {
/* 625 */       if (!spec.startsWith("#")) {
/* 626 */         StringTokenizer st = new StringTokenizer(spec);
/* 627 */         if (st.countTokens() >= 2) {
/* 628 */           String name = st.nextToken();
/* 629 */           String plural_str = st.nextToken();
/* 630 */           if (plural_str.equals("S") || plural_str.equals("P")) {
/* 631 */             boolean plural = plural_str.equals("P");
/* 632 */             String definition = st.hasMoreTokens() ? st.nextToken("") : null;
/* 633 */             if (definition != null) {
/* 634 */               int index = definition.indexOf("#");
/* 635 */               if (index >= 0) definition = definition.substring(0, index); 
/* 636 */               definition = definition.trim();
/*     */             } 
/* 638 */             if (definition != null && definition.equals("")) definition = null; 
/*     */             try {
/* 640 */               boolean defined = addDefinition(name, plural, definition);
/* 641 */               if (!defined) {
/* 642 */                 System.err.println("Units.loadTable: failed to define " + name + " = " + definition);
/*     */               }
/*     */             }
/* 645 */             catch (UnitsFormatException e) {
/* 646 */               System.err.println("Units.loadTable: failed to define " + name + " = " + definition + " because " + e
/* 647 */                   .getMessage());
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Units() {}
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/Units.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */