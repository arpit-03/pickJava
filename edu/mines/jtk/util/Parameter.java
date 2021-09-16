/*      */ package edu.mines.jtk.util;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Parameter
/*      */   implements Cloneable
/*      */ {
/*      */   public static final int NULL = 0;
/*      */   public static final int BOOLEAN = 1;
/*      */   public static final int INT = 2;
/*      */   public static final int LONG = 3;
/*      */   public static final int FLOAT = 4;
/*      */   public static final int DOUBLE = 5;
/*      */   public static final int STRING = 6;
/*      */   private String _name;
/*      */   private ParameterSet _parent;
/*      */   private String _units;
/*      */   private Object _values;
/*      */   
/*      */   public Parameter(String name) {
/*   82 */     setNameAndParent(name, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter(String name, boolean value) {
/*   91 */     setNameAndParent(name, null);
/*   92 */     setBoolean(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter(String name, int value) {
/*  101 */     setNameAndParent(name, null);
/*  102 */     setInt(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter(String name, long value) {
/*  111 */     setNameAndParent(name, null);
/*  112 */     setLong(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter(String name, float value) {
/*  121 */     setNameAndParent(name, null);
/*  122 */     setFloat(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter(String name, double value) {
/*  131 */     setNameAndParent(name, null);
/*  132 */     setDouble(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter(String name, String value) {
/*  141 */     setNameAndParent(name, null);
/*  142 */     setString(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter(String name, boolean[] values) {
/*  151 */     setNameAndParent(name, null);
/*  152 */     setBooleans(values);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter(String name, int[] values) {
/*  161 */     setNameAndParent(name, null);
/*  162 */     setInts(values);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter(String name, long[] values) {
/*  171 */     setNameAndParent(name, null);
/*  172 */     setLongs(values);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter(String name, float[] values) {
/*  181 */     setNameAndParent(name, null);
/*  182 */     setFloats(values);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter(String name, double[] values) {
/*  191 */     setNameAndParent(name, null);
/*  192 */     setDoubles(values);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter(String name, String[] values) {
/*  201 */     setNameAndParent(name, null);
/*  202 */     setStrings(values);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter(String name, boolean value, String units) {
/*  212 */     setNameAndParent(name, null);
/*  213 */     setBoolean(value);
/*  214 */     setUnits(units);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter(String name, int value, String units) {
/*  224 */     setNameAndParent(name, null);
/*  225 */     setInt(value);
/*  226 */     setUnits(units);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter(String name, long value, String units) {
/*  236 */     setNameAndParent(name, null);
/*  237 */     setLong(value);
/*  238 */     setUnits(units);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter(String name, float value, String units) {
/*  248 */     setNameAndParent(name, null);
/*  249 */     setFloat(value);
/*  250 */     setUnits(units);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter(String name, double value, String units) {
/*  260 */     setNameAndParent(name, null);
/*  261 */     setDouble(value);
/*  262 */     setUnits(units);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter(String name, String value, String units) {
/*  272 */     setNameAndParent(name, null);
/*  273 */     setString(value);
/*  274 */     setUnits(units);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter(String name, boolean[] values, String units) {
/*  284 */     setNameAndParent(name, null);
/*  285 */     setBooleans(values);
/*  286 */     setUnits(units);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter(String name, int[] values, String units) {
/*  296 */     setNameAndParent(name, null);
/*  297 */     setInts(values);
/*  298 */     setUnits(units);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter(String name, long[] values, String units) {
/*  308 */     setNameAndParent(name, null);
/*  309 */     setLongs(values);
/*  310 */     setUnits(units);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter(String name, float[] values, String units) {
/*  320 */     setNameAndParent(name, null);
/*  321 */     setFloats(values);
/*  322 */     setUnits(units);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter(String name, double[] values, String units) {
/*  332 */     setNameAndParent(name, null);
/*  333 */     setDoubles(values);
/*  334 */     setUnits(units);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter(String name, String[] values, String units) {
/*  344 */     setNameAndParent(name, null);
/*  345 */     setStrings(values);
/*  346 */     setUnits(units);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() throws CloneNotSupportedException {
/*      */     try {
/*  356 */       Parameter p = (Parameter)super.clone();
/*  357 */       p._parent = null;
/*  358 */       return p.replaceWith(this);
/*  359 */     } catch (CloneNotSupportedException e) {
/*  360 */       throw new InternalError();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter replaceWith(Parameter par) {
/*  373 */     if (par == this) return this; 
/*  374 */     setName(par.getName());
/*  375 */     setUnits(par.getUnits());
/*  376 */     setValues(par.getValues());
/*  377 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter copyTo(ParameterSet parent) {
/*  389 */     return copyTo(parent, getName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter copyTo(ParameterSet parent, String name) {
/*  402 */     if (this._parent == parent && this._name == name) return this; 
/*  403 */     Parameter p = (parent != null) ? parent.addParameter(name) : new Parameter(name);
/*  404 */     p.setUnits(getUnits());
/*  405 */     p.setValues(getValues());
/*  406 */     return p;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter moveTo(ParameterSet parent) {
/*  418 */     return moveTo(parent, getName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parameter moveTo(ParameterSet parent, String name) {
/*  431 */     if (this._parent == parent && this._name == name) return this; 
/*  432 */     if (this._parent != null) this._parent.remove(this); 
/*  433 */     if (parent != null) {
/*  434 */       parent.insert(name, this);
/*      */     } else {
/*  436 */       setNameAndParent(name, null);
/*      */     } 
/*  438 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void remove() {
/*  447 */     if (this._parent != null) this._parent.remove(this);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  455 */     return this._name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setName(String name) {
/*  464 */     moveTo(this._parent, name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUnits() {
/*  472 */     return this._units;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUnits(String units) {
/*  480 */     this._units = units;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getType() {
/*  488 */     if (this._values instanceof boolean[])
/*  489 */       return 1; 
/*  490 */     if (this._values instanceof int[])
/*  491 */       return 2; 
/*  492 */     if (this._values instanceof long[])
/*  493 */       return 3; 
/*  494 */     if (this._values instanceof float[])
/*  495 */       return 4; 
/*  496 */     if (this._values instanceof double[])
/*  497 */       return 5; 
/*  498 */     if (this._values instanceof String[]) {
/*  499 */       return 6;
/*      */     }
/*  501 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setType(int type) {
/*  513 */     if (type == 1) {
/*  514 */       this._values = valuesAsBooleans(this._values, false);
/*  515 */     } else if (type == 2) {
/*  516 */       this._values = valuesAsInts(this._values, false);
/*  517 */     } else if (type == 3) {
/*  518 */       this._values = valuesAsLongs(this._values, false);
/*  519 */     } else if (type == 4) {
/*  520 */       this._values = valuesAsFloats(this._values, false);
/*  521 */     } else if (type == 5) {
/*  522 */       this._values = valuesAsDoubles(this._values, false);
/*  523 */     } else if (type == 6) {
/*  524 */       this._values = valuesAsStrings(this._values, false);
/*      */     } else {
/*  526 */       this._values = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ParameterSet getParent() {
/*  536 */     return this._parent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBoolean() throws ParameterConvertException {
/*  548 */     boolean[] values = valuesAsBooleans(this._values, false);
/*  549 */     return values[values.length - 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getInt() throws ParameterConvertException {
/*  561 */     int[] values = valuesAsInts(this._values, false);
/*  562 */     return values[values.length - 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getLong() throws ParameterConvertException {
/*  574 */     long[] values = valuesAsLongs(this._values, false);
/*  575 */     return values[values.length - 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getFloat() throws ParameterConvertException {
/*  587 */     float[] values = valuesAsFloats(this._values, false);
/*  588 */     return values[values.length - 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDouble() throws ParameterConvertException {
/*  600 */     double[] values = valuesAsDoubles(this._values, false);
/*  601 */     return values[values.length - 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getString() throws ParameterConvertException {
/*  613 */     String[] values = valuesAsStrings(this._values, false);
/*  614 */     return values[values.length - 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean[] getBooleans() throws ParameterConvertException {
/*  625 */     return valuesAsBooleans(this._values, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] getInts() throws ParameterConvertException {
/*  636 */     return valuesAsInts(this._values, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long[] getLongs() throws ParameterConvertException {
/*  647 */     return valuesAsLongs(this._values, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float[] getFloats() throws ParameterConvertException {
/*  658 */     return valuesAsFloats(this._values, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double[] getDoubles() throws ParameterConvertException {
/*  669 */     return valuesAsDoubles(this._values, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getStrings() throws ParameterConvertException {
/*  680 */     return valuesAsStrings(this._values, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBoolean(boolean value) {
/*  688 */     this._values = new boolean[1];
/*  689 */     ((boolean[])this._values)[0] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInt(int value) {
/*  697 */     this._values = new int[1];
/*  698 */     ((int[])this._values)[0] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLong(long value) {
/*  706 */     this._values = new long[1];
/*  707 */     ((long[])this._values)[0] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFloat(float value) {
/*  715 */     this._values = new float[1];
/*  716 */     ((float[])this._values)[0] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDouble(double value) {
/*  724 */     this._values = new double[1];
/*  725 */     ((double[])this._values)[0] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setString(String value) {
/*  733 */     this._values = new String[1];
/*  734 */     ((String[])this._values)[0] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBooleans(boolean[] values) {
/*  742 */     int length = (values == null) ? 0 : values.length;
/*  743 */     this._values = new boolean[length];
/*  744 */     if (length > 0) System.arraycopy(values, 0, this._values, 0, length);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInts(int[] values) {
/*  752 */     int length = (values == null) ? 0 : values.length;
/*  753 */     this._values = new int[length];
/*  754 */     if (length > 0) System.arraycopy(values, 0, this._values, 0, length);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLongs(long[] values) {
/*  762 */     int length = (values == null) ? 0 : values.length;
/*  763 */     this._values = new long[length];
/*  764 */     if (length > 0) System.arraycopy(values, 0, this._values, 0, length);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFloats(float[] values) {
/*  772 */     int length = (values == null) ? 0 : values.length;
/*  773 */     this._values = new float[length];
/*  774 */     if (length > 0) System.arraycopy(values, 0, this._values, 0, length);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDoubles(double[] values) {
/*  782 */     int length = (values == null) ? 0 : values.length;
/*  783 */     this._values = new double[length];
/*  784 */     if (length > 0) System.arraycopy(values, 0, this._values, 0, length);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStrings(String[] values) {
/*  792 */     int length = (values == null) ? 0 : values.length;
/*  793 */     this._values = new String[length];
/*  794 */     if (length > 0) System.arraycopy(values, 0, this._values, 0, length);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNull() {
/*  802 */     return (getType() == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBoolean() {
/*  810 */     return (getType() == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInt() {
/*  818 */     return (getType() == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isLong() {
/*  826 */     return (getType() == 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFloat() {
/*  834 */     return (getType() == 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDouble() {
/*  842 */     return (getType() == 5);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isString() {
/*  850 */     return (getType() == 6);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  862 */     String indent = "";
/*  863 */     ParameterSet parent = getParent();
/*  864 */     for (; parent != null; 
/*  865 */       parent = parent.getParent()) {
/*  866 */       indent = indent + "  ";
/*      */     }
/*      */ 
/*      */     
/*  870 */     String name = XmlUtil.quoteAttributeValue(this._name);
/*  871 */     String units = XmlUtil.quoteAttributeValue(this._units);
/*  872 */     String type = XmlUtil.quoteAttributeValue(getTypeString());
/*  873 */     String[] values = valuesAsStrings(this._values, true);
/*  874 */     for (int i = 0; i < values.length; i++) {
/*  875 */       values[i] = XmlUtil.quoteCharacterData(values[i]);
/*      */     }
/*      */ 
/*      */     
/*  879 */     StringBuffer sb = new StringBuffer(256);
/*  880 */     sb.append(indent).append("<par name=").append(name);
/*  881 */     sb.append(" type=").append(type);
/*  882 */     if (units != null) sb.append(" units=").append(units); 
/*  883 */     if (isNull() || values.length == 0) {
/*  884 */       sb.append("/>\n");
/*  885 */     } else if (values.length == 1) {
/*  886 */       sb.append("> ").append(values[0]).append(" </par>\n");
/*      */     } else {
/*  888 */       sb.append(">\n");
/*  889 */       for (String value : values)
/*  890 */         sb.append(indent).append("  ").append(value).append("\n"); 
/*  891 */       sb.append(indent).append("</par>\n");
/*      */     } 
/*  893 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object o) {
/*  902 */     if (o == this)
/*  903 */       return true; 
/*  904 */     if (o == null || getClass() != o.getClass())
/*  905 */       return false; 
/*  906 */     Parameter other = (Parameter)o;
/*      */ 
/*      */     
/*  909 */     if (this._name == null) {
/*  910 */       if (other._name != null)
/*  911 */         return false; 
/*  912 */     } else if (!this._name.equals(other._name)) {
/*  913 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  917 */     if (this._units == null) {
/*  918 */       if (other._units != null)
/*  919 */         return false; 
/*  920 */     } else if (!this._units.equals(other._units)) {
/*  921 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  925 */     if (getType() != other.getType()) {
/*  926 */       return false;
/*      */     }
/*      */     
/*  929 */     int nvalues = countValues();
/*  930 */     if (nvalues != other.countValues()) {
/*  931 */       return false;
/*      */     }
/*      */     
/*  934 */     int type = getType();
/*  935 */     if (type == 1) {
/*  936 */       boolean[] values = (boolean[])this._values;
/*  937 */       boolean[] otherValues = (boolean[])other._values;
/*  938 */       for (int i = 0; i < nvalues; i++) {
/*  939 */         if (values[i] != otherValues[i])
/*  940 */           return false; 
/*      */       } 
/*  942 */     } else if (type == 2) {
/*  943 */       int[] values = (int[])this._values;
/*  944 */       int[] otherValues = (int[])other._values;
/*  945 */       for (int i = 0; i < nvalues; i++) {
/*  946 */         if (values[i] != otherValues[i])
/*  947 */           return false; 
/*      */       } 
/*  949 */     } else if (type == 3) {
/*  950 */       long[] values = (long[])this._values;
/*  951 */       long[] otherValues = (long[])other._values;
/*  952 */       for (int i = 0; i < nvalues; i++) {
/*  953 */         if (values[i] != otherValues[i])
/*  954 */           return false; 
/*      */       } 
/*  956 */     } else if (type == 4) {
/*  957 */       float[] values = (float[])this._values;
/*  958 */       float[] otherValues = (float[])other._values;
/*  959 */       for (int i = 0; i < nvalues; i++) {
/*  960 */         if (values[i] != otherValues[i])
/*  961 */           return false; 
/*      */       } 
/*  963 */     } else if (type == 5) {
/*  964 */       double[] values = (double[])this._values;
/*  965 */       double[] otherValues = (double[])other._values;
/*  966 */       for (int i = 0; i < nvalues; i++) {
/*  967 */         if (values[i] != otherValues[i])
/*  968 */           return false; 
/*      */       } 
/*  970 */     } else if (type == 6) {
/*  971 */       String[] values = (String[])this._values;
/*  972 */       String[] otherValues = (String[])other._values;
/*  973 */       for (int i = 0; i < nvalues; i++) {
/*  974 */         if (!values[i].equals(otherValues[i])) {
/*  975 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  980 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  989 */     String name = (this._name != null) ? this._name : "name";
/*  990 */     String units = (this._units != null) ? this._units : "units";
/*  991 */     int type = getType();
/*  992 */     int code = name.hashCode() ^ units.hashCode() ^ type;
/*  993 */     int nvalues = countValues();
/*  994 */     if (type == 1) {
/*  995 */       boolean[] values = (boolean[])this._values;
/*  996 */       for (int i = 0; i < nvalues; i++) {
/*  997 */         code ^= values[i] ? 1 : 0;
/*      */       }
/*  999 */     } else if (type == 2) {
/* 1000 */       int[] values = (int[])this._values;
/* 1001 */       for (int i = 0; i < nvalues; i++) {
/* 1002 */         code ^= values[i];
/*      */       }
/* 1004 */     } else if (type == 3) {
/* 1005 */       long[] values = (long[])this._values;
/* 1006 */       for (int i = 0; i < nvalues; i++) {
/* 1007 */         long bits = values[i];
/* 1008 */         code ^= (int)bits ^ (int)(bits >> 32L);
/*      */       } 
/* 1010 */     } else if (type == 4) {
/* 1011 */       float[] values = (float[])this._values;
/* 1012 */       for (int i = 0; i < nvalues; i++)
/* 1013 */         code ^= Float.floatToIntBits(values[i]); 
/* 1014 */     } else if (type == 5) {
/* 1015 */       double[] values = (double[])this._values;
/* 1016 */       for (int i = 0; i < nvalues; i++) {
/* 1017 */         long bits = Double.doubleToLongBits(values[i]);
/* 1018 */         code ^= (int)bits ^ (int)(bits >> 32L);
/*      */       } 
/* 1020 */     } else if (type == 6) {
/* 1021 */       String[] values = (String[])this._values;
/* 1022 */       for (int i = 0; i < nvalues; i++) {
/* 1023 */         code ^= values[i].hashCode();
/*      */       }
/*      */     } 
/* 1026 */     return code;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Parameter(String name, ParameterSet parent) {
/* 1034 */     setNameAndParent(name, parent);
/*      */   }
/*      */   
/*      */   void setParent(ParameterSet parent) {
/* 1038 */     this._parent = parent;
/*      */   }
/*      */   
/*      */   void setNameAndParent(String name, ParameterSet parent) {
/* 1042 */     this._name = name;
/* 1043 */     if (this._name != null && this._name.equals("")) this._name = null; 
/* 1044 */     this._parent = parent;
/*      */   }
/*      */   
/*      */   Object getValues() {
/* 1048 */     int n = 0;
/* 1049 */     Object values = null;
/* 1050 */     if (this._values instanceof boolean[]) {
/* 1051 */       n = ((boolean[])this._values).length;
/* 1052 */       values = new boolean[n];
/* 1053 */     } else if (this._values instanceof int[]) {
/* 1054 */       n = ((int[])this._values).length;
/* 1055 */       values = new int[n];
/* 1056 */     } else if (this._values instanceof long[]) {
/* 1057 */       n = ((long[])this._values).length;
/* 1058 */       values = new long[n];
/* 1059 */     } else if (this._values instanceof float[]) {
/* 1060 */       n = ((float[])this._values).length;
/* 1061 */       values = new float[n];
/* 1062 */     } else if (this._values instanceof double[]) {
/* 1063 */       n = ((double[])this._values).length;
/* 1064 */       values = new double[n];
/* 1065 */     } else if (this._values instanceof String[]) {
/* 1066 */       n = ((String[])this._values).length;
/* 1067 */       values = new String[n];
/*      */     } 
/* 1069 */     if (n > 0) System.arraycopy(this._values, 0, values, 0, n); 
/* 1070 */     return values;
/*      */   }
/*      */   
/*      */   void setValues(Object values) {
/* 1074 */     int n = 0;
/* 1075 */     this._values = null;
/* 1076 */     if (values instanceof boolean[]) {
/* 1077 */       n = ((boolean[])values).length;
/* 1078 */       this._values = new boolean[n];
/* 1079 */     } else if (values instanceof int[]) {
/* 1080 */       n = ((int[])values).length;
/* 1081 */       this._values = new int[n];
/* 1082 */     } else if (values instanceof long[]) {
/* 1083 */       n = ((long[])values).length;
/* 1084 */       this._values = new long[n];
/* 1085 */     } else if (values instanceof float[]) {
/* 1086 */       n = ((float[])values).length;
/* 1087 */       this._values = new float[n];
/* 1088 */     } else if (values instanceof double[]) {
/* 1089 */       n = ((double[])values).length;
/* 1090 */       this._values = new double[n];
/* 1091 */     } else if (values instanceof String[]) {
/* 1092 */       n = ((String[])values).length;
/* 1093 */       this._values = new String[n];
/*      */     } 
/* 1095 */     if (n > 0) System.arraycopy(values, 0, this._values, 0, n);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int countValues() {
/* 1108 */     if (getType() == 1)
/* 1109 */       return ((boolean[])this._values).length; 
/* 1110 */     if (getType() == 2)
/* 1111 */       return ((int[])this._values).length; 
/* 1112 */     if (getType() == 3)
/* 1113 */       return ((long[])this._values).length; 
/* 1114 */     if (getType() == 4)
/* 1115 */       return ((float[])this._values).length; 
/* 1116 */     if (getType() == 5)
/* 1117 */       return ((double[])this._values).length; 
/* 1118 */     if (getType() == 6) {
/* 1119 */       return ((String[])this._values).length;
/*      */     }
/* 1121 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean[] valuesAsBooleans(Object values, boolean copy) {
/*      */     boolean[] bvalues;
/* 1127 */     if (values instanceof boolean[]) {
/* 1128 */       bvalues = (boolean[])values;
/* 1129 */       if (copy) {
/* 1130 */         bvalues = new boolean[bvalues.length];
/* 1131 */         System.arraycopy(values, 0, bvalues, 0, bvalues.length);
/*      */       } 
/*      */     } else {
/*      */       try {
/* 1135 */         if (values instanceof String[]) {
/* 1136 */           String[] svalues = (String[])values;
/* 1137 */           bvalues = new boolean[svalues.length];
/* 1138 */           for (int i = 0; i < svalues.length; i++) {
/* 1139 */             String s = svalues[i].toLowerCase();
/* 1140 */             if (s.equals("true")) {
/* 1141 */               bvalues[i] = true;
/* 1142 */             } else if (s.equals("false")) {
/* 1143 */               bvalues[i] = false;
/*      */             } else {
/* 1145 */               throw new Exception();
/*      */             } 
/*      */           } 
/* 1148 */         } else if (values == null) {
/* 1149 */           bvalues = new boolean[0];
/*      */         } else {
/* 1151 */           throw new Exception();
/*      */         } 
/* 1153 */       } catch (Exception e) {
/*      */         
/* 1155 */         String msg = "Parameter cannot convert " + getTypeString(values) + " to boolean.";
/* 1156 */         throw new ParameterConvertException(msg);
/*      */       } 
/*      */     } 
/* 1159 */     return bvalues;
/*      */   }
/*      */   
/*      */   private static int[] valuesAsInts(Object values, boolean copy) {
/*      */     int[] ivalues;
/* 1164 */     if (values instanceof int[]) {
/* 1165 */       ivalues = (int[])values;
/* 1166 */       if (copy) {
/* 1167 */         ivalues = new int[ivalues.length];
/* 1168 */         System.arraycopy(values, 0, ivalues, 0, ivalues.length);
/*      */       } 
/*      */     } else {
/*      */       try {
/* 1172 */         if (values instanceof String[]) {
/* 1173 */           String[] svalues = (String[])values;
/* 1174 */           ivalues = new int[svalues.length];
/* 1175 */           for (int i = 0; i < svalues.length; i++) {
/* 1176 */             ivalues[i] = Integer.valueOf(svalues[i]).intValue();
/*      */           }
/* 1178 */         } else if (values == null) {
/* 1179 */           ivalues = new int[0];
/*      */         } else {
/* 1181 */           throw new Exception();
/*      */         } 
/* 1183 */       } catch (Exception e) {
/*      */         
/* 1185 */         String msg = "Parameter cannot convert " + getTypeString(values) + " to int.";
/* 1186 */         throw new ParameterConvertException(msg);
/*      */       } 
/*      */     } 
/* 1189 */     return ivalues;
/*      */   }
/*      */   
/*      */   private static long[] valuesAsLongs(Object values, boolean copy) {
/*      */     long[] lvalues;
/* 1194 */     if (values instanceof long[]) {
/* 1195 */       lvalues = (long[])values;
/* 1196 */       if (copy) {
/* 1197 */         lvalues = new long[lvalues.length];
/* 1198 */         System.arraycopy(values, 0, lvalues, 0, lvalues.length);
/*      */       } 
/*      */     } else {
/*      */       try {
/* 1202 */         if (values instanceof String[]) {
/* 1203 */           String[] svalues = (String[])values;
/* 1204 */           lvalues = new long[svalues.length];
/* 1205 */           for (int i = 0; i < svalues.length; i++) {
/* 1206 */             lvalues[i] = Long.valueOf(svalues[i]).longValue();
/*      */           }
/* 1208 */         } else if (values == null) {
/* 1209 */           lvalues = new long[0];
/*      */         } else {
/* 1211 */           throw new Exception();
/*      */         } 
/* 1213 */       } catch (Exception e) {
/*      */         
/* 1215 */         String msg = "Parameter cannot convert " + getTypeString(values) + " to long.";
/* 1216 */         throw new ParameterConvertException(msg);
/*      */       } 
/*      */     } 
/* 1219 */     return lvalues;
/*      */   }
/*      */   
/*      */   private static float[] valuesAsFloats(Object values, boolean copy) {
/*      */     float[] fvalues;
/* 1224 */     if (values instanceof float[]) {
/* 1225 */       fvalues = (float[])values;
/* 1226 */       if (copy) {
/* 1227 */         fvalues = new float[fvalues.length];
/* 1228 */         System.arraycopy(values, 0, fvalues, 0, fvalues.length);
/*      */       } 
/*      */     } else {
/*      */       try {
/* 1232 */         if (values instanceof int[]) {
/* 1233 */           int[] ivalues = (int[])values;
/* 1234 */           fvalues = new float[ivalues.length];
/* 1235 */           for (int i = 0; i < ivalues.length; i++) {
/* 1236 */             fvalues[i] = ivalues[i];
/*      */           }
/* 1238 */         } else if (values instanceof double[]) {
/* 1239 */           double[] dvalues = (double[])values;
/* 1240 */           fvalues = new float[dvalues.length];
/* 1241 */           for (int i = 0; i < dvalues.length; i++) {
/* 1242 */             fvalues[i] = (float)dvalues[i];
/*      */           }
/* 1244 */         } else if (values instanceof String[]) {
/* 1245 */           String[] svalues = (String[])values;
/* 1246 */           fvalues = new float[svalues.length];
/* 1247 */           for (int i = 0; i < svalues.length; i++) {
/* 1248 */             fvalues[i] = Float.valueOf(svalues[i]).floatValue();
/*      */           }
/* 1250 */         } else if (values == null) {
/* 1251 */           fvalues = new float[0];
/*      */         } else {
/* 1253 */           throw new Exception();
/*      */         } 
/* 1255 */       } catch (Exception e) {
/*      */         
/* 1257 */         String msg = "Parameter cannot convert " + getTypeString(values) + " to float.";
/* 1258 */         throw new ParameterConvertException(msg);
/*      */       } 
/*      */     } 
/* 1261 */     return fvalues;
/*      */   }
/*      */   
/*      */   private static double[] valuesAsDoubles(Object values, boolean copy) {
/*      */     double[] dvalues;
/* 1266 */     if (values instanceof double[]) {
/* 1267 */       dvalues = (double[])values;
/* 1268 */       if (copy) {
/* 1269 */         dvalues = new double[dvalues.length];
/* 1270 */         System.arraycopy(values, 0, dvalues, 0, dvalues.length);
/*      */       } 
/*      */     } else {
/*      */       try {
/* 1274 */         if (values instanceof int[]) {
/* 1275 */           int[] ivalues = (int[])values;
/* 1276 */           dvalues = new double[ivalues.length];
/* 1277 */           for (int i = 0; i < ivalues.length; i++) {
/* 1278 */             dvalues[i] = ivalues[i];
/*      */           }
/* 1280 */         } else if (values instanceof float[]) {
/* 1281 */           float[] fvalues = (float[])values;
/* 1282 */           dvalues = new double[fvalues.length];
/* 1283 */           for (int i = 0; i < fvalues.length; i++) {
/* 1284 */             dvalues[i] = fvalues[i];
/*      */           }
/* 1286 */         } else if (values instanceof String[]) {
/* 1287 */           String[] svalues = (String[])values;
/* 1288 */           dvalues = new double[svalues.length];
/* 1289 */           for (int i = 0; i < svalues.length; i++) {
/* 1290 */             dvalues[i] = Double.valueOf(svalues[i]).doubleValue();
/*      */           }
/* 1292 */         } else if (values == null) {
/* 1293 */           dvalues = new double[0];
/*      */         } else {
/* 1295 */           throw new Exception();
/*      */         } 
/* 1297 */       } catch (Exception e) {
/*      */         
/* 1299 */         String msg = "Parameter cannot convert " + getTypeString(values) + " to double.";
/* 1300 */         throw new ParameterConvertException(msg);
/*      */       } 
/*      */     } 
/* 1303 */     return dvalues;
/*      */   }
/*      */   
/*      */   private static String[] valuesAsStrings(Object values, boolean copy) {
/*      */     String[] svalues;
/* 1308 */     if (values instanceof String[]) {
/* 1309 */       svalues = (String[])values;
/* 1310 */       if (copy) {
/* 1311 */         svalues = new String[svalues.length];
/* 1312 */         System.arraycopy(values, 0, svalues, 0, svalues.length);
/*      */       } 
/*      */     } else {
/*      */       try {
/* 1316 */         if (values instanceof boolean[]) {
/* 1317 */           boolean[] bvalues = (boolean[])values;
/* 1318 */           svalues = new String[bvalues.length];
/* 1319 */           for (int i = 0; i < bvalues.length; i++) {
/* 1320 */             svalues[i] = bvalues[i] ? "true" : "false";
/*      */           }
/* 1322 */         } else if (values instanceof int[]) {
/* 1323 */           int[] ivalues = (int[])values;
/* 1324 */           svalues = new String[ivalues.length];
/* 1325 */           for (int i = 0; i < ivalues.length; i++) {
/* 1326 */             svalues[i] = Integer.toString(ivalues[i]);
/*      */           }
/* 1328 */         } else if (values instanceof long[]) {
/* 1329 */           long[] lvalues = (long[])values;
/* 1330 */           svalues = new String[lvalues.length];
/* 1331 */           for (int i = 0; i < lvalues.length; i++) {
/* 1332 */             svalues[i] = Long.toString(lvalues[i]);
/*      */           }
/* 1334 */         } else if (values instanceof float[]) {
/* 1335 */           float[] fvalues = (float[])values;
/* 1336 */           svalues = new String[fvalues.length];
/* 1337 */           for (int i = 0; i < fvalues.length; i++) {
/* 1338 */             svalues[i] = Float.toString(fvalues[i]);
/*      */           }
/* 1340 */         } else if (values instanceof double[]) {
/* 1341 */           double[] dvalues = (double[])values;
/* 1342 */           svalues = new String[dvalues.length];
/* 1343 */           for (int i = 0; i < dvalues.length; i++) {
/* 1344 */             svalues[i] = Double.toString(dvalues[i]);
/*      */           }
/* 1346 */         } else if (values == null) {
/* 1347 */           svalues = new String[0];
/*      */         } else {
/* 1349 */           throw new Exception();
/*      */         } 
/* 1351 */       } catch (Exception e) {
/*      */         
/* 1353 */         String msg = "Parameter cannot convert " + getTypeString(values) + " to String.";
/* 1354 */         throw new ParameterConvertException(msg);
/*      */       } 
/*      */     } 
/* 1357 */     return svalues;
/*      */   }
/*      */   
/*      */   private String getTypeString() {
/* 1361 */     return getTypeString(this._values);
/*      */   }
/*      */   
/*      */   private static String getTypeString(Object values) {
/* 1365 */     if (values instanceof boolean[])
/* 1366 */       return "boolean"; 
/* 1367 */     if (values instanceof int[])
/* 1368 */       return "int"; 
/* 1369 */     if (values instanceof long[])
/* 1370 */       return "long"; 
/* 1371 */     if (values instanceof float[])
/* 1372 */       return "float"; 
/* 1373 */     if (values instanceof double[])
/* 1374 */       return "double"; 
/* 1375 */     if (values instanceof String[]) {
/* 1376 */       return "string";
/*      */     }
/* 1378 */     return "null";
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/Parameter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */