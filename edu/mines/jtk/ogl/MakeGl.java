/*     */ package edu.mines.jtk.ogl;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MakeGl
/*     */ {
/*     */   public static void main(String[] args) {
/*  34 */     String[] inputFileNames = { "GL.html", "GLBase.html", "GLLightingFunc.html", "GLMatrixFunc.html", "GLPointerFunc.html", "GL2.html", "GL2ES1.html", "GL2ES2.html", "GL2ES3.html", "GL2GL3.html" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  46 */     String outputFileName = "Gl.java";
/*  47 */     HashSet<String> cs = new HashSet<>();
/*  48 */     HashSet<String> fs = new HashSet<>();
/*     */     try {
/*  50 */       trace("MakeGl begin ...");
/*  51 */       BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName));
/*  52 */       for (String line : PROLOG)
/*  53 */         bw.write(line + NEWLINE); 
/*  54 */       for (String inputFileName : inputFileNames) {
/*  55 */         trace("processing " + inputFileName);
/*  56 */         BufferedReader br = new BufferedReader(new FileReader(inputFileName));
/*  57 */         bw.write(NEWLINE + "  // Generated from " + inputFileName + NEWLINE + NEWLINE);
/*  58 */         guts(cs, fs, br, bw);
/*  59 */         br.close();
/*     */       } 
/*  61 */       for (String line : EPILOG)
/*  62 */         bw.write(line + NEWLINE); 
/*  63 */       bw.close();
/*  64 */       trace("... done");
/*  65 */     } catch (IOException ioe) {
/*  66 */       throw new RuntimeException(ioe);
/*     */     } 
/*     */   }
/*     */   
/*  70 */   private static final String NEWLINE = System.getProperty("line.separator");
/*     */   
/*  72 */   private static final String[] PROLOG = new String[] { "/****************************************************************************", "Copyright 2006, Colorado School of Mines and others.", "Licensed under the Apache License, Version 2.0 (the \"License\");", "you may not use this file except in compliance with the License.", "You may obtain a copy of the License at", "", "    http://www.apache.org/licenses/LICENSE-2.0", "", "Unless required by applicable law or agreed to in writing, software", "distributed under the License is distributed on an \"AS IS\" BASIS,", "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.", "See the License for the specific language governing permissions and", "limitations under the License.", "****************************************************************************/", "package edu.mines.jtk.ogl;", "", "import java.nio.*;", "import com.jogamp.opengl.*;", "import com.jogamp.common.nio.PointerBuffer;", "", "/**", " * OpenGL standard constants and functions.", " * @author Dave Hale, Colorado School of Mines", " * @version 2014.06.10", " */", "@SuppressWarnings(\"deprecation\")", "public class Gl {", "" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   private static final String[] EPILOG = new String[] { "  public static void setSwapInterval(int interval) {", "    gl().setSwapInterval(interval);", "  }", "", "  private static GL2 gl() {", "    return (GL2)GLContext.getCurrentGL();", "  }", "", "  ///////////////////////////////////////////////////////////////////////////", "  // private", "", "  private Gl() {", "  }", "}" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   private static final Pattern _conName = Pattern.compile("final&nbsp;int (GL_\\w*)</pre>");
/*     */   
/* 128 */   private static final Pattern _funType = Pattern.compile("(\\w+)(?:</a>)?((?:\\[\\])*)&nbsp;\\w+\\(");
/*     */   
/* 130 */   private static final Pattern _parType = Pattern.compile("(\\w+)(?:</a>)?((?:\\[\\])*)&nbsp;\\w+[,\\)]");
/*     */   
/* 132 */   private static final Pattern _funName = Pattern.compile("&nbsp;(\\w+)\\(");
/*     */   
/* 134 */   private static final Pattern _parName = Pattern.compile("(\\w+)(?:</a>)?((?:\\[\\])*)&nbsp;(\\w+)[,\\)]");
/*     */   private static boolean hasConstant(String input) {
/* 136 */     return input.contains("static final&nbsp;int GL_");
/*     */   }
/*     */   private static boolean hasFunction(String input) {
/* 139 */     return (input.startsWith("<pre>") && (input
/* 140 */       .contains("&nbsp;gl") || input
/* 141 */       .contains("&nbsp;is")));
/*     */   }
/*     */   private static boolean endFunction(String input) {
/* 144 */     return input.endsWith(")</pre>");
/*     */   }
/*     */   private static String getConName(String input) {
/* 147 */     Matcher m = _conName.matcher(input);
/* 148 */     return m.find() ? m.group(1) : null;
/*     */   }
/*     */   private static String getFunType(String input) {
/* 151 */     Matcher m = _funType.matcher(input);
/* 152 */     return m.find() ? (m.group(1) + m.group(2)) : null;
/*     */   }
/*     */   private static String getFunName(String input) {
/* 155 */     Matcher m = _funName.matcher(input);
/* 156 */     return m.find() ? m.group(1) : null;
/*     */   }
/*     */   private static String getParType(String input) {
/* 159 */     Matcher m = _parType.matcher(input);
/* 160 */     return m.find() ? (m.group(1) + m.group(2)) : null;
/*     */   }
/*     */   private static String getParName(String input) {
/* 163 */     Matcher m = _parName.matcher(input);
/* 164 */     return m.find() ? m.group(3) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void guts(HashSet<String> cs, HashSet<String> fs, BufferedReader br, BufferedWriter bw) throws IOException {
/* 171 */     ArrayList<String> parList = new ArrayList<>();
/* 172 */     for (String input = br.readLine(); input != null; input = br.readLine()) {
/* 173 */       if (hasConstant(input)) {
/* 174 */         String conName = getConName(input);
/* 175 */         if (!cs.add(conName))
/* 176 */         { trace("  duplicate: " + conName); }
/*     */         else
/*     */         
/* 179 */         { String output = "  public static final int " + conName + NEWLINE + "    = GL2." + conName + ";" + NEWLINE + NEWLINE;
/*     */ 
/*     */           
/* 182 */           bw.write(output); } 
/* 183 */       } else if (hasFunction(input)) {
/* 184 */         String funType = getFunType(input);
/* 185 */         String funName = getFunName(input);
/* 186 */         String funHash = funName + "(";
/* 187 */         String output = "  public static " + funType + " " + funName + "(" + NEWLINE + "    ";
/*     */ 
/*     */         
/* 190 */         String parType = getParType(input);
/* 191 */         String parName = getParName(input);
/* 192 */         parList.clear();
/* 193 */         if (parType != null && parName != null) {
/* 194 */           output = output + parType + " " + parName;
/* 195 */           parList.add(parName);
/*     */         } 
/* 197 */         while (!endFunction(input)) {
/* 198 */           output = output + "," + NEWLINE;
/* 199 */           input = br.readLine();
/* 200 */           parType = getParType(input);
/* 201 */           parName = getParName(input);
/* 202 */           parList.add(parName);
/* 203 */           funHash = funHash + parType;
/* 204 */           output = output + "    " + parType + " " + parName;
/*     */         } 
/* 206 */         output = output + ") {" + NEWLINE;
/* 207 */         funHash = funHash + ")";
/* 208 */         if (!fs.add(funHash)) {
/* 209 */           trace("  duplicate: " + funHash);
/*     */         } else {
/*     */           
/* 212 */           bw.write(output);
/* 213 */           if ("void".equals(funType)) {
/* 214 */             output = "    gl()." + funName + "(";
/*     */           } else {
/* 216 */             output = "    return gl()." + funName + "(";
/*     */           } 
/* 218 */           int n = parList.size();
/* 219 */           if (n == 0) {
/* 220 */             bw.write(output + ");" + NEWLINE);
/*     */           } else {
/* 222 */             bw.write(output + NEWLINE);
/* 223 */             for (int k = 0; k < n; k++) {
/* 224 */               if (k < n - 1) {
/* 225 */                 output = "      " + (String)parList.get(k) + ",";
/*     */               } else {
/* 227 */                 output = "      " + (String)parList.get(k) + ");";
/*     */               } 
/* 229 */               bw.write(output + NEWLINE);
/*     */             } 
/*     */           } 
/* 232 */           bw.write("  }" + NEWLINE + NEWLINE);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   } private static void trace(String s) {
/* 237 */     System.out.println(s);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/ogl/MakeGl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */