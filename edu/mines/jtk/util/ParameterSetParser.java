/*     */ package edu.mines.jtk.util;
/*     */ 
/*     */ import java.io.CharArrayWriter;
/*     */ import java.io.Reader;
/*     */ import java.util.ArrayList;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.parsers.SAXParser;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ import org.xml.sax.helpers.DefaultHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ParameterSetParser
/*     */   extends DefaultHandler
/*     */ {
/*     */   public ParameterSetParser() {
/*  36 */     SAXParserFactory factory = SAXParserFactory.newInstance();
/*     */     try {
/*  38 */       this._parser = factory.newSAXParser();
/*  39 */     } catch (ParserConfigurationException e) {
/*  40 */       throw new RuntimeException("cannot create XML parser: " + e);
/*  41 */     } catch (SAXException e) {
/*  42 */       throw new RuntimeException("cannot create XML parser: " + e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void parse(Reader reader, ParameterSet ps) throws ParameterSetFormatException {
/*  49 */     this._ps_root = ps;
/*  50 */     this._ps = null;
/*  51 */     this._p = null;
/*  52 */     this._ptype = 0;
/*  53 */     this._pdata.reset();
/*     */     try {
/*  55 */       this._parser.parse(new InputSource(reader), this);
/*  56 */     } catch (ParameterSetFormatException e) {
/*  57 */       throw e;
/*  58 */     } catch (Exception e) {
/*  59 */       e.printStackTrace(System.err);
/*  60 */       throw new ParameterSetFormatException("ParameterSetParser.parse: unknown error: " + e
/*     */           
/*  62 */           .getMessage() + ":" + e.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(String uri, String localName, String qName, Attributes attributes) throws ParameterSetFormatException {
/*  73 */     if (qName.equals("parset")) {
/*  74 */       startParameterSet(attributes);
/*  75 */     } else if (qName.equals("par")) {
/*  76 */       startParameter(attributes);
/*     */     } else {
/*  78 */       throw new ParameterSetFormatException("ParameterSetParser.startElement: unrecognized XML element \"" + qName + "\".");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement(String uri, String localName, String qName) throws ParameterSetFormatException {
/*  87 */     if (qName.equals("parset")) {
/*  88 */       endParameterSet();
/*  89 */     } else if (qName.equals("par")) {
/*  90 */       endParameter();
/*     */     } else {
/*  92 */       throw new ParameterSetFormatException("ParameterSetParser.endElement: unrecognized XML element \"" + qName + "\".");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void characters(char[] ch, int start, int length) {
/*  99 */     if (this._p == null)
/* 100 */       return;  this._pdata.write(ch, start, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void error(SAXParseException e) throws ParameterSetFormatException {
/* 106 */     String message = e.getMessage();
/* 107 */     int line = e.getLineNumber();
/* 108 */     int column = e.getColumnNumber();
/* 109 */     throw new ParameterSetFormatException("ParameterSetParser.error: " + message + " at line " + line + ", column " + column + ".");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   private SAXParser _parser = null;
/* 119 */   private ParameterSet _ps_root = null;
/* 120 */   private ParameterSet _ps = null;
/* 121 */   private Parameter _p = null;
/* 122 */   private int _ptype = 0;
/* 123 */   private CharArrayWriter _pdata = new CharArrayWriter(256);
/*     */ 
/*     */   
/*     */   private void startParameter(Attributes attributes) throws ParameterSetFormatException {
/* 127 */     String name = attributes.getValue("name");
/* 128 */     if (name == null)
/* 129 */       throw new ParameterSetFormatException("parameter with no name"); 
/* 130 */     if (this._ps == null) {
/* 131 */       throw new ParameterSetFormatException("<par name=\"" + name + "\" ...> must be specified inside a parameter set.");
/*     */     }
/*     */     
/* 134 */     this._p = this._ps.addParameter(name);
/* 135 */     String units = attributes.getValue("units");
/* 136 */     this._p.setUnits(units);
/* 137 */     String type = attributes.getValue("type");
/* 138 */     if (type != null) {
/* 139 */       if (type.equals("boolean")) {
/* 140 */         this._ptype = 1;
/* 141 */       } else if (type.equals("int")) {
/* 142 */         this._ptype = 2;
/* 143 */       } else if (type.equals("long")) {
/* 144 */         this._ptype = 3;
/* 145 */       } else if (type.equals("float")) {
/* 146 */         this._ptype = 4;
/* 147 */       } else if (type.equals("double")) {
/* 148 */         this._ptype = 5;
/* 149 */       } else if (type.equals("string")) {
/* 150 */         this._ptype = 6;
/*     */       } else {
/* 152 */         this._ptype = 6;
/*     */       } 
/*     */     } else {
/* 155 */       this._ptype = 6;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void endParameter() {
/* 160 */     this._p.setStrings(parseValues());
/* 161 */     this._p.setType(this._ptype);
/* 162 */     this._p = null;
/* 163 */     this._ptype = 0;
/* 164 */     this._pdata.reset();
/*     */   }
/*     */   
/*     */   private void startParameterSet(Attributes attributes) {
/* 168 */     String name = attributes.getValue("name");
/* 169 */     if (name == null)
/* 170 */       throw new ParameterSetFormatException("parameter set with no name"); 
/* 171 */     if (this._ps == null) {
/* 172 */       this._ps_root.setName(name);
/* 173 */       this._ps = this._ps_root;
/*     */     } else {
/* 175 */       this._ps = this._ps.addParameterSet(name);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void endParameterSet() {
/* 180 */     if (this._ps != this._ps_root) {
/* 181 */       this._ps = this._ps.getParent();
/*     */     }
/*     */   }
/*     */   
/*     */   private String[] parseValues() {
/* 186 */     StringParser sp = new StringParser(this._pdata.toString());
/* 187 */     ArrayList<String> vtemp = new ArrayList<>(8);
/* 188 */     while (sp.hasMoreStrings())
/* 189 */       vtemp.add(sp.nextString()); 
/* 190 */     String[] values = new String[vtemp.size()];
/* 191 */     for (int i = 0; i < values.length; i++)
/* 192 */       values[i] = vtemp.get(i); 
/* 193 */     return values;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/ParameterSetParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */