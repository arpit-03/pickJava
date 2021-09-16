/*     */ package edu.mines.jtk.util;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.io.StringReader;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParameterSet
/*     */   implements Cloneable, Externalizable
/*     */ {
/*     */   private String _name;
/*     */   private ParameterSet _parent;
/*     */   
/*     */   public ParameterSet() {}
/*     */   
/*     */   public ParameterSet(String name) {
/*  47 */     setNameAndParent(name, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/*     */     try {
/*  57 */       ParameterSet ps = (ParameterSet)super.clone();
/*  58 */       ps._parent = null;
/*  59 */       ps._pars = new LinkedHashMap<>();
/*  60 */       ps._parsets = new LinkedHashMap<>();
/*  61 */       return ps.replaceWith(this);
/*  62 */     } catch (CloneNotSupportedException e) {
/*  63 */       throw new InternalError();
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
/*     */   public ParameterSet replaceWith(ParameterSet parset) {
/*  76 */     if (parset == this) return this; 
/*  77 */     setName(parset.getName());
/*  78 */     clear();
/*  79 */     Iterator<Parameter> pi = parset.getParameters();
/*  80 */     while (pi.hasNext()) {
/*  81 */       ((Parameter)pi.next()).copyTo(this);
/*     */     }
/*  83 */     Iterator<ParameterSet> psi = parset.getParameterSets();
/*  84 */     while (psi.hasNext()) {
/*  85 */       ((ParameterSet)psi.next()).copyTo(this);
/*     */     }
/*  87 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  95 */     return this._name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setName(String name) {
/* 104 */     moveTo(this._parent, name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Parameter getParameter(String name) {
/* 114 */     return this._pars.get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParameterSet getParameterSet(String name) {
/* 124 */     return this._parsets.get(name);
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
/*     */   public Parameter addParameter(String name) {
/* 136 */     if (name == null) return null; 
/* 137 */     Parameter par = new Parameter(name);
/* 138 */     insert(name, par);
/* 139 */     return par;
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
/*     */   public ParameterSet addParameterSet(String name) {
/* 151 */     if (name == null) return null; 
/* 152 */     ParameterSet parset = new ParameterSet(name, null);
/* 153 */     insert(name, parset);
/* 154 */     return parset;
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
/*     */   public boolean getBoolean(String name, boolean defaultValue) throws ParameterConvertException {
/* 168 */     Parameter par = getParameter(name);
/* 169 */     return (par != null) ? par.getBoolean() : defaultValue;
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
/*     */   public int getInt(String name, int defaultValue) throws ParameterConvertException {
/* 183 */     Parameter par = getParameter(name);
/* 184 */     return (par != null) ? par.getInt() : defaultValue;
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
/*     */   public long getLong(String name, long defaultValue) throws ParameterConvertException {
/* 198 */     Parameter par = getParameter(name);
/* 199 */     return (par != null) ? par.getLong() : defaultValue;
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
/*     */   public float getFloat(String name, float defaultValue) throws ParameterConvertException {
/* 213 */     Parameter par = getParameter(name);
/* 214 */     return (par != null) ? par.getFloat() : defaultValue;
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
/*     */   public double getDouble(String name, double defaultValue) throws ParameterConvertException {
/* 228 */     Parameter par = getParameter(name);
/* 229 */     return (par != null) ? par.getDouble() : defaultValue;
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
/*     */   public String getString(String name, String defaultValue) throws ParameterConvertException {
/* 243 */     Parameter par = getParameter(name);
/* 244 */     return (par != null) ? par.getString() : defaultValue;
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
/*     */   public boolean[] getBooleans(String name, boolean[] defaultValues) throws ParameterConvertException {
/* 258 */     Parameter par = getParameter(name);
/* 259 */     return (par != null) ? par.getBooleans() : defaultValues;
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
/*     */   public int[] getInts(String name, int[] defaultValues) throws ParameterConvertException {
/* 273 */     Parameter par = getParameter(name);
/* 274 */     return (par != null) ? par.getInts() : defaultValues;
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
/*     */   public long[] getLongs(String name, long[] defaultValues) throws ParameterConvertException {
/* 288 */     Parameter par = getParameter(name);
/* 289 */     return (par != null) ? par.getLongs() : defaultValues;
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
/*     */   public float[] getFloats(String name, float[] defaultValues) throws ParameterConvertException {
/* 303 */     Parameter par = getParameter(name);
/* 304 */     return (par != null) ? par.getFloats() : defaultValues;
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
/*     */   public double[] getDoubles(String name, double[] defaultValues) throws ParameterConvertException {
/* 318 */     Parameter par = getParameter(name);
/* 319 */     return (par != null) ? par.getDoubles() : defaultValues;
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
/*     */   public String[] getStrings(String name, String[] defaultValues) throws ParameterConvertException {
/* 333 */     Parameter par = getParameter(name);
/* 334 */     return (par != null) ? par.getStrings() : defaultValues;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnits(String name, String defaultUnits) {
/* 345 */     Parameter par = getParameter(name);
/* 346 */     return (par != null) ? par.getUnits() : defaultUnits;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBoolean(String name, boolean value) {
/* 357 */     Parameter par = getParameter(name);
/* 358 */     if (par == null) par = addParameter(name); 
/* 359 */     par.setBoolean(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInt(String name, int value) {
/* 370 */     Parameter par = getParameter(name);
/* 371 */     if (par == null) par = addParameter(name); 
/* 372 */     par.setInt(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLong(String name, long value) {
/* 383 */     Parameter par = getParameter(name);
/* 384 */     if (par == null) par = addParameter(name); 
/* 385 */     par.setLong(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFloat(String name, float value) {
/* 396 */     Parameter par = getParameter(name);
/* 397 */     if (par == null) par = addParameter(name); 
/* 398 */     par.setFloat(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDouble(String name, double value) {
/* 409 */     Parameter par = getParameter(name);
/* 410 */     if (par == null) par = addParameter(name); 
/* 411 */     par.setDouble(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setString(String name, String value) {
/* 422 */     Parameter par = getParameter(name);
/* 423 */     if (par == null) par = addParameter(name); 
/* 424 */     par.setString(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBooleans(String name, boolean[] values) {
/* 435 */     Parameter par = getParameter(name);
/* 436 */     if (par == null) par = addParameter(name); 
/* 437 */     par.setBooleans(values);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInts(String name, int[] values) {
/* 448 */     Parameter par = getParameter(name);
/* 449 */     if (par == null) par = addParameter(name); 
/* 450 */     par.setInts(values);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLongs(String name, long[] values) {
/* 461 */     Parameter par = getParameter(name);
/* 462 */     if (par == null) par = addParameter(name); 
/* 463 */     par.setLongs(values);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFloats(String name, float[] values) {
/* 474 */     Parameter par = getParameter(name);
/* 475 */     if (par == null) par = addParameter(name); 
/* 476 */     par.setFloats(values);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDoubles(String name, double[] values) {
/* 487 */     Parameter par = getParameter(name);
/* 488 */     if (par == null) par = addParameter(name); 
/* 489 */     par.setDoubles(values);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStrings(String name, String[] values) {
/* 500 */     Parameter par = getParameter(name);
/* 501 */     if (par == null) par = addParameter(name); 
/* 502 */     par.setStrings(values);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUnits(String name, String units) {
/* 513 */     Parameter par = getParameter(name);
/* 514 */     if (par == null) par = addParameter(name); 
/* 515 */     par.setUnits(units);
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
/*     */   public ParameterSet copyTo(ParameterSet parent) {
/* 527 */     return copyTo(parent, getName());
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
/*     */   public ParameterSet copyTo(ParameterSet parent, String name) {
/* 540 */     if (this._parent == parent && this._name == name) return this;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 546 */     ParameterSet ps = new ParameterSet(name);
/*     */ 
/*     */     
/* 549 */     Iterator<Parameter> pi = getParameters();
/* 550 */     while (pi.hasNext()) {
/* 551 */       ((Parameter)pi.next()).copyTo(ps);
/*     */     }
/*     */ 
/*     */     
/* 555 */     Iterator<ParameterSet> psi = getParameterSets();
/* 556 */     while (psi.hasNext()) {
/* 557 */       ((ParameterSet)psi.next()).copyTo(ps);
/*     */     }
/*     */ 
/*     */     
/* 561 */     if (parent != null) parent.insert(name, ps); 
/* 562 */     return ps;
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
/*     */   public ParameterSet moveTo(ParameterSet parent) {
/* 574 */     return moveTo(parent, getName());
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
/*     */   public ParameterSet moveTo(ParameterSet parent, String name) {
/* 588 */     if (this._parent == parent && this._name == name) return this;
/*     */ 
/*     */     
/* 591 */     for (ParameterSet ps = parent; ps != null; ps = ps.getParent()) {
/* 592 */       if (ps == this) {
/* 593 */         throw new IllegalArgumentException("ParameterSet.moveTo: specified parent \"" + parent
/* 594 */             .getName() + "\" cannot be this parameter set \"" + 
/* 595 */             getName() + "\" or a subset of this parameter set.");
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 601 */     if (this._parent != null) this._parent.remove(this); 
/* 602 */     if (parent != null) {
/* 603 */       parent.insert(name, this);
/*     */     } else {
/* 605 */       setNameAndParent(name, null);
/*     */     } 
/*     */     
/* 608 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove() {
/* 617 */     if (this._parent != null) this._parent.remove(this);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(String name) {
/*     */     Parameter par;
/* 629 */     if ((par = getParameter(name)) != null)
/* 630 */     { par.remove(); }
/* 631 */     else { ParameterSet parset; if ((parset = getParameterSet(name)) != null) {
/* 632 */         parset.remove();
/*     */       } }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int countParameters() {
/* 641 */     return this._pars.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int countParameterSets() {
/* 649 */     return this._parsets.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 659 */     Iterator<Parameter> pi = getParameters();
/* 660 */     while (pi.hasNext()) {
/* 661 */       ((Parameter)pi.next()).setParent(null);
/*     */     }
/* 663 */     Iterator<ParameterSet> psi = getParameterSets();
/* 664 */     while (psi.hasNext()) {
/* 665 */       ((ParameterSet)psi.next()).setParent(null);
/*     */     }
/*     */ 
/*     */     
/* 669 */     this._pars.clear();
/* 670 */     this._parsets.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParameterSet getParent() {
/* 680 */     return this._parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<Parameter> getParameters() {
/* 688 */     return this._pars.values().iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<ParameterSet> getParameterSets() {
/* 696 */     return this._parsets.values().iterator();
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
/*     */   public void fromString(String s) throws ParameterSetFormatException {
/* 708 */     clear();
/* 709 */     ParameterSetParser psp = new ParameterSetParser();
/* 710 */     psp.parse(new BufferedReader(new StringReader(s)), this);
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
/*     */   public String toString() {
/* 722 */     String indent = "";
/* 723 */     ParameterSet parent = getParent();
/* 724 */     for (; parent != null; 
/* 725 */       parent = parent.getParent()) {
/* 726 */       indent = indent + "  ";
/*     */     }
/*     */ 
/*     */     
/* 730 */     StringBuffer sb = new StringBuffer(256);
/* 731 */     String name = XmlUtil.quoteAttributeValue((this._name != null) ? this._name : "");
/* 732 */     sb.append(indent).append("<parset name=").append(name).append(">\n");
/*     */ 
/*     */     
/* 735 */     Iterator<Parameter> pi = getParameters();
/* 736 */     while (pi.hasNext()) {
/* 737 */       Parameter p = pi.next();
/* 738 */       sb.append(p.toString());
/*     */     } 
/*     */ 
/*     */     
/* 742 */     Iterator<ParameterSet> psi = getParameterSets();
/* 743 */     while (psi.hasNext()) {
/* 744 */       ParameterSet ps = psi.next();
/* 745 */       sb.append(ps.toString());
/*     */     } 
/*     */ 
/*     */     
/* 749 */     sb.append(indent).append("</parset>\n");
/* 750 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 760 */     if (o == this)
/* 761 */       return true; 
/* 762 */     if (o == null || getClass() != o.getClass())
/* 763 */       return false; 
/* 764 */     ParameterSet other = (ParameterSet)o;
/*     */ 
/*     */     
/* 767 */     if (this._name == null) {
/* 768 */       if (other._name != null)
/* 769 */         return false; 
/* 770 */     } else if (!this._name.equals(other._name)) {
/* 771 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 775 */     int npars = countParameters();
/* 776 */     if (npars != other.countParameters()) {
/* 777 */       return false;
/*     */     }
/*     */     
/* 780 */     int nparsets = countParameterSets();
/* 781 */     if (nparsets != other.countParameterSets()) {
/* 782 */       return false;
/*     */     }
/*     */     
/* 785 */     if (npars > 0) {
/* 786 */       Parameter[] these = new Parameter[npars];
/* 787 */       Parameter[] those = new Parameter[npars];
/* 788 */       int i = 0;
/* 789 */       Iterator<Parameter> pi = getParameters();
/* 790 */       while (pi.hasNext()) {
/* 791 */         these[i++] = pi.next();
/*     */       }
/* 793 */       i = 0;
/* 794 */       pi = other.getParameters();
/* 795 */       while (pi.hasNext()) {
/* 796 */         those[i++] = pi.next();
/*     */       }
/* 798 */       sortParametersByName(these);
/* 799 */       sortParametersByName(those);
/* 800 */       for (i = 0; i < npars; i++) {
/* 801 */         if (!these[i].equals(those[i])) {
/* 802 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 807 */     if (nparsets > 0) {
/* 808 */       ParameterSet[] these = new ParameterSet[nparsets];
/* 809 */       ParameterSet[] those = new ParameterSet[nparsets];
/* 810 */       int i = 0;
/* 811 */       Iterator<ParameterSet> psi = getParameterSets();
/* 812 */       while (psi.hasNext()) {
/* 813 */         these[i++] = psi.next();
/*     */       }
/* 815 */       i = 0;
/* 816 */       psi = other.getParameterSets();
/* 817 */       while (psi.hasNext()) {
/* 818 */         those[i++] = psi.next();
/*     */       }
/* 820 */       sortParameterSetsByName(these);
/* 821 */       sortParameterSetsByName(those);
/* 822 */       for (i = 0; i < nparsets; i++) {
/* 823 */         if (!these[i].equals(those[i])) {
/* 824 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 829 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 837 */     String name = (this._name != null) ? this._name : "name";
/* 838 */     int code = name.hashCode();
/* 839 */     Iterator<Parameter> pars = getParameters();
/* 840 */     while (pars.hasNext()) {
/* 841 */       code ^= ((Parameter)pars.next()).hashCode();
/*     */     }
/* 843 */     Iterator<ParameterSet> parsets = getParameterSets();
/* 844 */     while (parsets.hasNext()) {
/* 845 */       code ^= ((ParameterSet)parsets.next()).hashCode();
/*     */     }
/* 847 */     return code;
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
/* 858 */     out.writeUTF(toString());
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
/*     */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException, ParameterSetFormatException {
/* 874 */     String s = in.readUTF();
/* 875 */     fromString(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void remove(Parameter par) {
/* 882 */     if (par == null)
/* 883 */       return;  this._pars.remove(par.getName());
/* 884 */     par.setParent(null);
/*     */   }
/*     */   
/*     */   void insert(String name, Parameter par) {
/* 888 */     if (name == null || par == null)
/* 889 */       return;  par.remove();
/* 890 */     remove(name);
/* 891 */     this._pars.put(name, par);
/* 892 */     par.setNameAndParent(name, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 900 */   private LinkedHashMap<String, Parameter> _pars = new LinkedHashMap<>(8);
/*     */   
/* 902 */   private LinkedHashMap<String, ParameterSet> _parsets = new LinkedHashMap<>(8);
/*     */ 
/*     */   
/*     */   private ParameterSet(String name, ParameterSet parent) {
/* 906 */     setNameAndParent(name, parent);
/*     */   }
/*     */   
/*     */   private void setParent(ParameterSet parent) {
/* 910 */     this._parent = parent;
/*     */   }
/*     */   
/*     */   private void setNameAndParent(String name, ParameterSet parent) {
/* 914 */     this._name = name;
/* 915 */     if (this._name != null && this._name.equals("")) this._name = null; 
/* 916 */     this._parent = parent;
/*     */   }
/*     */   
/*     */   private void remove(ParameterSet parset) {
/* 920 */     if (parset == null)
/* 921 */       return;  this._parsets.remove(parset.getName());
/* 922 */     parset.setParent(null);
/*     */   }
/*     */   
/*     */   private void insert(String name, ParameterSet parset) {
/* 926 */     if (name == null || parset == null)
/* 927 */       return;  parset.remove();
/* 928 */     remove(name);
/* 929 */     this._parsets.put(name, parset);
/* 930 */     parset.setNameAndParent(name, this);
/*     */   }
/*     */   
/*     */   private static void swap(Object[] v, int i, int j) {
/* 934 */     Object temp = v[i];
/* 935 */     v[i] = v[j];
/* 936 */     v[j] = temp;
/*     */   }
/*     */   
/*     */   private static void qsortParameters(Parameter[] v, int left, int right) {
/* 940 */     if (left >= right)
/* 941 */       return;  swap((Object[])v, left, (left + right) / 2);
/* 942 */     int last = left;
/* 943 */     for (int i = left + 1; i <= right; i++) {
/* 944 */       if (v[i].getName().compareTo(v[left].getName()) < 0)
/* 945 */         swap((Object[])v, ++last, i); 
/* 946 */     }  swap((Object[])v, left, last);
/* 947 */     qsortParameters(v, left, last - 1);
/* 948 */     qsortParameters(v, last + 1, right);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void qsortParameterSets(ParameterSet[] v, int left, int right) {
/* 953 */     if (left >= right)
/* 954 */       return;  swap((Object[])v, left, (left + right) / 2);
/* 955 */     int last = left;
/* 956 */     for (int i = left + 1; i <= right; i++) {
/* 957 */       if (v[i].getName().compareTo(v[left].getName()) < 0)
/* 958 */         swap((Object[])v, ++last, i); 
/* 959 */     }  swap((Object[])v, left, last);
/* 960 */     qsortParameterSets(v, left, last - 1);
/* 961 */     qsortParameterSets(v, last + 1, right);
/*     */   }
/*     */   
/*     */   private static void sortParametersByName(Parameter[] v) {
/* 965 */     qsortParameters(v, 0, v.length - 1);
/*     */   }
/*     */   
/*     */   private static void sortParameterSetsByName(ParameterSet[] v) {
/* 969 */     qsortParameterSets(v, 0, v.length - 1);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/ParameterSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */