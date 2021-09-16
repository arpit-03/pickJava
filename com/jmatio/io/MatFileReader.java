/*      */ package com.jmatio.io;
/*      */ 
/*      */ import com.jmatio.types.ByteStorageSupport;
/*      */ import com.jmatio.types.MLArray;
/*      */ import com.jmatio.types.MLCell;
/*      */ import com.jmatio.types.MLChar;
/*      */ import com.jmatio.types.MLDouble;
/*      */ import com.jmatio.types.MLEmptyArray;
/*      */ import com.jmatio.types.MLInt16;
/*      */ import com.jmatio.types.MLInt32;
/*      */ import com.jmatio.types.MLInt64;
/*      */ import com.jmatio.types.MLInt8;
/*      */ import com.jmatio.types.MLJavaObject;
/*      */ import com.jmatio.types.MLNumericArray;
/*      */ import com.jmatio.types.MLObject;
/*      */ import com.jmatio.types.MLSingle;
/*      */ import com.jmatio.types.MLSparse;
/*      */ import com.jmatio.types.MLStructure;
/*      */ import com.jmatio.types.MLUInt32;
/*      */ import com.jmatio.types.MLUInt64;
/*      */ import com.jmatio.types.MLUInt8;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.RandomAccessFile;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.lang.reflect.Method;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.nio.MappedByteBuffer;
/*      */ import java.nio.channels.FileChannel;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.Map;
/*      */ import sun.misc.Cleaner;
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
/*      */ public class MatFileReader
/*      */ {
/*      */   private static final int DIRECT_BUFFER_LIMIT = 33554432;
/*      */   
/*      */   public MatFileReader(String fileName) throws FileNotFoundException, IOException {
/*  102 */     this(new File(fileName), new MatFileFilter());
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
/*      */ 
/*      */   
/*      */   public MatFileReader(String fileName, MatFileFilter filter) throws IOException {
/*  117 */     this(new File(fileName), filter);
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
/*      */   public MatFileReader(File file) throws IOException {
/*  130 */     this(file, new MatFileFilter());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatFileReader(File file, MatFileFilter filter) throws IOException {
/*  152 */     this();
/*      */     
/*  154 */     read(file, filter, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  159 */   private MatFileFilter filter = new MatFileFilter(); private ByteOrder byteOrder;
/*  160 */   private Map<String, MLArray> data = new LinkedHashMap<String, MLArray>();
/*      */ 
/*      */   
/*      */   private MatFileHeader matFileHeader;
/*      */ 
/*      */   
/*      */   public static final int HEAP_BYTE_BUFFER = 4;
/*      */   
/*      */   public static final int DIRECT_BYTE_BUFFER = 2;
/*      */   
/*      */   public static final int MEMORY_MAPPED_FILE = 1;
/*      */ 
/*      */   
/*      */   public MatFileReader() {}
/*      */ 
/*      */   
/*      */   public synchronized Map<String, MLArray> read(File file) throws IOException {
/*  177 */     return read(file, new MatFileFilter(), 1);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Map<String, MLArray> read(File file, int policy) throws IOException {
/*  195 */     return read(file, new MatFileFilter(), policy);
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
/*      */   public synchronized Map<String, MLArray> read(File file, MatFileFilter filter, int policy) throws IOException {
/*  231 */     this.filter = filter;
/*      */ 
/*      */     
/*  234 */     for (String key : this.data.keySet())
/*      */     {
/*  236 */       this.data.remove(key);
/*      */     }
/*      */     
/*  239 */     FileChannel roChannel = null;
/*  240 */     RandomAccessFile raFile = null;
/*  241 */     ByteBuffer buf = null;
/*  242 */     WeakReference<MappedByteBuffer> bufferWeakRef = null;
/*      */     
/*      */     try {
/*      */       int filesize, numberOfBlocks;
/*  246 */       raFile = new RandomAccessFile(file, "r");
/*  247 */       roChannel = raFile.getChannel();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  252 */       switch (policy) {
/*      */         
/*      */         case 2:
/*  255 */           buf = ByteBuffer.allocateDirect((int)roChannel.size());
/*  256 */           roChannel.read(buf, 0L);
/*  257 */           buf.rewind();
/*      */           break;
/*      */         case 4:
/*  260 */           filesize = (int)roChannel.size();
/*  261 */           System.gc();
/*  262 */           buf = ByteBuffer.allocate(filesize);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  271 */           numberOfBlocks = filesize / 33554432 + ((filesize % 33554432 > 0) ? 1 : 0);
/*  272 */           if (numberOfBlocks > 1) {
/*  273 */             ByteBuffer tempByteBuffer = ByteBuffer.allocateDirect(33554432);
/*  274 */             for (int block = 0; block < numberOfBlocks; block++) {
/*  275 */               tempByteBuffer.clear();
/*  276 */               roChannel.read(tempByteBuffer, (block * 33554432));
/*  277 */               tempByteBuffer.flip();
/*  278 */               buf.put(tempByteBuffer);
/*      */             } 
/*  280 */             tempByteBuffer = null;
/*      */           } else {
/*  282 */             roChannel.read(buf, 0L);
/*      */           } 
/*  284 */           buf.rewind();
/*      */           break;
/*      */         case 1:
/*  287 */           buf = roChannel.map(FileChannel.MapMode.READ_ONLY, 0L, (int)roChannel.size());
/*  288 */           bufferWeakRef = new WeakReference<MappedByteBuffer>((MappedByteBuffer)buf);
/*      */           break;
/*      */         default:
/*  291 */           throw new IllegalArgumentException("Unknown file allocation policy");
/*      */       } 
/*      */       
/*  294 */       readHeader(buf);
/*      */       
/*  296 */       while (buf.remaining() > 0)
/*      */       {
/*  298 */         readData(buf);
/*      */       }
/*      */       
/*  301 */       return getContent();
/*      */     }
/*  303 */     catch (IOException e) {
/*      */       
/*  305 */       throw e;
/*      */     }
/*      */     finally {
/*      */       
/*  309 */       if (roChannel != null)
/*      */       {
/*  311 */         roChannel.close();
/*      */       }
/*  313 */       if (raFile != null)
/*      */       {
/*  315 */         raFile.close();
/*      */       }
/*  317 */       if (buf != null && bufferWeakRef != null && policy == 1) {
/*      */         
/*      */         try {
/*      */           
/*  321 */           clean(buf);
/*      */         }
/*  323 */         catch (Exception e) {
/*      */           
/*  325 */           int GC_TIMEOUT_MS = 1000;
/*  326 */           buf = null;
/*  327 */           long start = System.currentTimeMillis();
/*  328 */           while (bufferWeakRef.get() != null) {
/*      */             
/*  330 */             if (System.currentTimeMillis() - start > GC_TIMEOUT_MS) {
/*      */               break;
/*      */             }
/*      */ 
/*      */             
/*  335 */             System.gc();
/*  336 */             Thread.yield();
/*      */           } 
/*      */         } 
/*      */       }
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
/*      */   private void clean(final Object buffer) throws Exception {
/*  366 */     AccessController.doPrivileged(new PrivilegedAction()
/*      */         {
/*      */           
/*      */           public Object run()
/*      */           {
/*      */             try {
/*  372 */               Method getCleanerMethod = buffer.getClass().getMethod("cleaner", new Class[0]);
/*      */               
/*  374 */               getCleanerMethod.setAccessible(true);
/*  375 */               Cleaner cleaner = (Cleaner)getCleanerMethod.invoke(buffer, new Object[0]);
/*      */               
/*  377 */               cleaner.clean();
/*      */             }
/*  379 */             catch (Exception e) {
/*      */               
/*  381 */               e.printStackTrace();
/*      */             } 
/*  383 */             return null;
/*      */           }
/*      */         });
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
/*      */   public MatFileHeader getMatFileHeader() {
/*  397 */     return this.matFileHeader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayList<MLArray> getData() {
/*  408 */     return new ArrayList<MLArray>(this.data.values());
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
/*      */   public MLArray getMLArray(String name) {
/*  421 */     return this.data.get(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, MLArray> getContent() {
/*  432 */     return this.data;
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
/*      */   private void readData(ByteBuffer buf) throws IOException {
/*      */     // Byte code:
/*      */     //   0: new com/jmatio/io/MatFileReader$ISMatTag
/*      */     //   3: dup
/*      */     //   4: aload_1
/*      */     //   5: invokespecial <init> : (Ljava/nio/ByteBuffer;)V
/*      */     //   8: astore_2
/*      */     //   9: aload_2
/*      */     //   10: getfield type : I
/*      */     //   13: lookupswitch default -> 387, 14 -> 245, 15 -> 40
/*      */     //   40: aload_2
/*      */     //   41: getfield size : I
/*      */     //   44: istore_3
/*      */     //   45: aload_1
/*      */     //   46: invokevirtual remaining : ()I
/*      */     //   49: iload_3
/*      */     //   50: if_icmpge -> 63
/*      */     //   53: new com/jmatio/io/MatlabIOException
/*      */     //   56: dup
/*      */     //   57: ldc 'Compressed buffer length miscalculated!'
/*      */     //   59: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   62: athrow
/*      */     //   63: new java/util/zip/InflaterInputStream
/*      */     //   66: dup
/*      */     //   67: new com/jmatio/io/ByteBufferInputStream
/*      */     //   70: dup
/*      */     //   71: aload_1
/*      */     //   72: iload_3
/*      */     //   73: invokespecial <init> : (Ljava/nio/ByteBuffer;I)V
/*      */     //   76: invokespecial <init> : (Ljava/io/InputStream;)V
/*      */     //   79: astore #4
/*      */     //   81: sipush #1024
/*      */     //   84: newarray byte
/*      */     //   86: astore #5
/*      */     //   88: new com/jmatio/io/HeapBufferDataOutputStream
/*      */     //   91: dup
/*      */     //   92: invokespecial <init> : ()V
/*      */     //   95: astore #6
/*      */     //   97: aload #4
/*      */     //   99: aload #5
/*      */     //   101: iconst_0
/*      */     //   102: aload #5
/*      */     //   104: arraylength
/*      */     //   105: invokevirtual read : ([BII)I
/*      */     //   108: istore #7
/*      */     //   110: iconst_0
/*      */     //   111: iload #7
/*      */     //   113: invokestatic max : (II)I
/*      */     //   116: istore #8
/*      */     //   118: aload #6
/*      */     //   120: aload #5
/*      */     //   122: iconst_0
/*      */     //   123: iload #8
/*      */     //   125: invokevirtual write : ([BII)V
/*      */     //   128: iload #7
/*      */     //   130: ifgt -> 97
/*      */     //   133: jsr -> 177
/*      */     //   136: goto -> 191
/*      */     //   139: astore #8
/*      */     //   141: new com/jmatio/io/MatlabIOException
/*      */     //   144: dup
/*      */     //   145: new java/lang/StringBuilder
/*      */     //   148: dup
/*      */     //   149: invokespecial <init> : ()V
/*      */     //   152: ldc 'Could not decompress data: '
/*      */     //   154: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   157: aload #8
/*      */     //   159: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*      */     //   162: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   165: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   168: athrow
/*      */     //   169: astore #9
/*      */     //   171: jsr -> 177
/*      */     //   174: aload #9
/*      */     //   176: athrow
/*      */     //   177: astore #10
/*      */     //   179: aload #4
/*      */     //   181: invokevirtual close : ()V
/*      */     //   184: aload #6
/*      */     //   186: invokevirtual flush : ()V
/*      */     //   189: ret #10
/*      */     //   191: aload #6
/*      */     //   193: invokevirtual getByteBuffer : ()Ljava/nio/ByteBuffer;
/*      */     //   196: astore #8
/*      */     //   198: aload #8
/*      */     //   200: aload_0
/*      */     //   201: getfield byteOrder : Ljava/nio/ByteOrder;
/*      */     //   204: invokevirtual order : (Ljava/nio/ByteOrder;)Ljava/nio/ByteBuffer;
/*      */     //   207: pop
/*      */     //   208: aload_0
/*      */     //   209: aload #8
/*      */     //   211: invokespecial readData : (Ljava/nio/ByteBuffer;)V
/*      */     //   214: jsr -> 233
/*      */     //   217: goto -> 242
/*      */     //   220: astore #9
/*      */     //   222: aload #9
/*      */     //   224: athrow
/*      */     //   225: astore #11
/*      */     //   227: jsr -> 233
/*      */     //   230: aload #11
/*      */     //   232: athrow
/*      */     //   233: astore #12
/*      */     //   235: aload #6
/*      */     //   237: invokevirtual close : ()V
/*      */     //   240: ret #12
/*      */     //   242: goto -> 414
/*      */     //   245: aload_1
/*      */     //   246: invokevirtual position : ()I
/*      */     //   249: istore #9
/*      */     //   251: aload_0
/*      */     //   252: aload_1
/*      */     //   253: iconst_1
/*      */     //   254: invokespecial readMatrix : (Ljava/nio/ByteBuffer;Z)Lcom/jmatio/types/MLArray;
/*      */     //   257: astore #10
/*      */     //   259: aload #10
/*      */     //   261: ifnull -> 301
/*      */     //   264: aload_0
/*      */     //   265: getfield data : Ljava/util/Map;
/*      */     //   268: aload #10
/*      */     //   270: invokevirtual getName : ()Ljava/lang/String;
/*      */     //   273: invokeinterface containsKey : (Ljava/lang/Object;)Z
/*      */     //   278: ifne -> 301
/*      */     //   281: aload_0
/*      */     //   282: getfield data : Ljava/util/Map;
/*      */     //   285: aload #10
/*      */     //   287: invokevirtual getName : ()Ljava/lang/String;
/*      */     //   290: aload #10
/*      */     //   292: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   297: pop
/*      */     //   298: goto -> 331
/*      */     //   301: aload_1
/*      */     //   302: invokevirtual position : ()I
/*      */     //   305: iload #9
/*      */     //   307: isub
/*      */     //   308: istore #11
/*      */     //   310: aload_2
/*      */     //   311: getfield size : I
/*      */     //   314: iload #11
/*      */     //   316: isub
/*      */     //   317: istore #12
/*      */     //   319: aload_1
/*      */     //   320: aload_1
/*      */     //   321: invokevirtual position : ()I
/*      */     //   324: iload #12
/*      */     //   326: iadd
/*      */     //   327: invokevirtual position : (I)Ljava/nio/Buffer;
/*      */     //   330: pop
/*      */     //   331: aload_1
/*      */     //   332: invokevirtual position : ()I
/*      */     //   335: iload #9
/*      */     //   337: isub
/*      */     //   338: istore #11
/*      */     //   340: aload_2
/*      */     //   341: getfield size : I
/*      */     //   344: iload #11
/*      */     //   346: isub
/*      */     //   347: istore #12
/*      */     //   349: iload #12
/*      */     //   351: ifeq -> 414
/*      */     //   354: new com/jmatio/io/MatlabIOException
/*      */     //   357: dup
/*      */     //   358: new java/lang/StringBuilder
/*      */     //   361: dup
/*      */     //   362: invokespecial <init> : ()V
/*      */     //   365: ldc 'Matrix was not red fully! '
/*      */     //   367: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   370: iload #12
/*      */     //   372: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*      */     //   375: ldc ' remaining in the buffer.'
/*      */     //   377: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   380: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   383: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   386: athrow
/*      */     //   387: new com/jmatio/io/MatlabIOException
/*      */     //   390: dup
/*      */     //   391: new java/lang/StringBuilder
/*      */     //   394: dup
/*      */     //   395: invokespecial <init> : ()V
/*      */     //   398: ldc 'Incorrect data tag: '
/*      */     //   400: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   403: aload_2
/*      */     //   404: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*      */     //   407: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   410: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   413: athrow
/*      */     //   414: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #452	-> 0
/*      */     //   #453	-> 9
/*      */     //   #456	-> 40
/*      */     //   #458	-> 45
/*      */     //   #460	-> 53
/*      */     //   #464	-> 63
/*      */     //   #467	-> 81
/*      */     //   #469	-> 88
/*      */     //   #475	-> 97
/*      */     //   #476	-> 110
/*      */     //   #477	-> 118
/*      */     //   #479	-> 128
/*      */     //   #480	-> 133
/*      */     //   #489	-> 136
/*      */     //   #481	-> 139
/*      */     //   #483	-> 141
/*      */     //   #487	-> 169
/*      */     //   #488	-> 184
/*      */     //   #491	-> 191
/*      */     //   #494	-> 198
/*      */     //   #498	-> 208
/*      */     //   #500	-> 214
/*      */     //   #508	-> 217
/*      */     //   #501	-> 220
/*      */     //   #503	-> 222
/*      */     //   #507	-> 225
/*      */     //   #509	-> 242
/*      */     //   #513	-> 245
/*      */     //   #515	-> 251
/*      */     //   #517	-> 259
/*      */     //   #519	-> 281
/*      */     //   #523	-> 301
/*      */     //   #524	-> 310
/*      */     //   #525	-> 319
/*      */     //   #527	-> 331
/*      */     //   #529	-> 340
/*      */     //   #531	-> 349
/*      */     //   #533	-> 354
/*      */     //   #537	-> 387
/*      */     //   #540	-> 414
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   118	10	8	len	I
/*      */     //   141	28	8	e	Ljava/io/IOException;
/*      */     //   222	3	9	e	Ljava/io/IOException;
/*      */     //   310	21	11	red	I
/*      */     //   319	12	12	toread	I
/*      */     //   45	369	3	numOfBytes	I
/*      */     //   81	333	4	iis	Ljava/util/zip/InflaterInputStream;
/*      */     //   88	326	5	result	[B
/*      */     //   97	317	6	dos	Lcom/jmatio/io/HeapBufferDataOutputStream;
/*      */     //   110	304	7	i	I
/*      */     //   198	216	8	out	Ljava/nio/ByteBuffer;
/*      */     //   251	163	9	pos	I
/*      */     //   259	155	10	element	Lcom/jmatio/types/MLArray;
/*      */     //   340	74	11	red	I
/*      */     //   349	65	12	toread	I
/*      */     //   0	415	0	this	Lcom/jmatio/io/MatFileReader;
/*      */     //   0	415	1	buf	Ljava/nio/ByteBuffer;
/*      */     //   9	406	2	tag	Lcom/jmatio/io/MatFileReader$ISMatTag;
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   97	133	139	java/io/IOException
/*      */     //   97	136	169	finally
/*      */     //   139	174	169	finally
/*      */     //   208	214	220	java/io/IOException
/*      */     //   208	217	225	finally
/*      */     //   220	230	225	finally
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
/*      */   private MLArray readMatrix(ByteBuffer buf, boolean isRoot) throws IOException {
/*      */     MLStructure mLStructure1;
/*      */     MLCell mLCell1;
/*      */     MLDouble mLDouble;
/*      */     MLSingle mLSingle;
/*      */     MLUInt8 mLUInt8;
/*      */     MLInt8 mLInt8;
/*      */     MLInt16 mLInt16;
/*      */     MLInt32 mLInt32;
/*      */     MLUInt32 mLUInt32;
/*      */     MLInt64 mLInt64;
/*      */     MLUInt64 mLUInt64;
/*      */     MLChar mLChar1;
/*      */     MLSparse mLSparse1;
/*      */     MLJavaObject mLJavaObject;
/*      */     ISMatTag tag;
/*      */     MLStructure struct;
/*      */     int maxlen, numOfFields;
/*      */     String[] fieldNames;
/*      */     int i, index;
/*      */     MLCell cell;
/*      */     int j;
/*      */     MLChar mlchar;
/*      */     String str;
/*      */     int k;
/*      */     MLSparse sparse;
/*      */     int[] ir, jc;
/*      */     double[] ad1;
/*      */     int count, column;
/*      */     String className;
/*      */     byte[] nn;
/*      */     int m;
/*      */     String arrName;
/*      */     ISMatTag contentTag;
/*  568 */     int i1, n, flags[] = readFlags(buf);
/*  569 */     int attributes = (flags.length != 0) ? flags[0] : 0;
/*  570 */     int nzmax = (flags.length != 0) ? flags[1] : 0;
/*  571 */     int type = attributes & 0xFF;
/*      */ 
/*      */     
/*  574 */     int[] dims = readDimension(buf);
/*      */ 
/*      */     
/*  577 */     String name = readName(buf);
/*      */ 
/*      */     
/*  580 */     if (isRoot && !this.filter.matches(name))
/*      */     {
/*  582 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  587 */     switch (type) {
/*      */ 
/*      */       
/*      */       case 2:
/*  591 */         struct = new MLStructure(name, dims, type, attributes);
/*      */ 
/*      */         
/*  594 */         tag = new ISMatTag(buf);
/*  595 */         maxlen = buf.getInt();
/*      */ 
/*      */         
/*  598 */         tag = new ISMatTag(buf);
/*      */         
/*  600 */         numOfFields = tag.size / maxlen;
/*      */         
/*  602 */         fieldNames = new String[numOfFields];
/*  603 */         for (i = 0; i < numOfFields; i++) {
/*      */           
/*  605 */           byte[] names = new byte[maxlen];
/*  606 */           buf.get(names);
/*  607 */           fieldNames[i] = zeroEndByteArrayToString(names);
/*      */         } 
/*  609 */         buf.position(buf.position() + tag.padding);
/*      */         
/*  611 */         for (index = 0; index < struct.getM() * struct.getN(); index++) {
/*      */           
/*  613 */           for (int i2 = 0; i2 < numOfFields; i2++) {
/*      */ 
/*      */             
/*  616 */             tag = new ISMatTag(buf);
/*      */             
/*  618 */             if (tag.size > 0) {
/*      */               
/*  620 */               MLArray fieldValue = readMatrix(buf, false);
/*  621 */               struct.setField(fieldNames[i2], fieldValue, index);
/*      */             }
/*      */             else {
/*      */               
/*  625 */               struct.setField(fieldNames[i2], (MLArray)new MLEmptyArray(), index);
/*      */             } 
/*      */           } 
/*      */         } 
/*  629 */         return (MLArray)struct;
/*      */       
/*      */       case 1:
/*  632 */         cell = new MLCell(name, dims, type, attributes);
/*  633 */         for (j = 0; j < cell.getM() * cell.getN(); j++) {
/*      */           
/*  635 */           tag = new ISMatTag(buf);
/*  636 */           if (tag.size > 0) {
/*      */ 
/*      */             
/*  639 */             MLArray cellmatrix = readMatrix(buf, false);
/*  640 */             cell.set(cellmatrix, j);
/*      */           }
/*      */           else {
/*      */             
/*  644 */             cell.set((MLArray)new MLEmptyArray(), j);
/*      */           } 
/*      */         } 
/*  647 */         return (MLArray)cell;
/*      */       
/*      */       case 6:
/*  650 */         mLDouble = new MLDouble(name, dims, type, attributes);
/*      */         
/*  652 */         tag = new ISMatTag(buf);
/*  653 */         tag.readToByteBuffer(((MLNumericArray)mLDouble).getRealByteBuffer(), (ByteStorageSupport<?>)mLDouble);
/*      */ 
/*      */         
/*  656 */         if (mLDouble.isComplex()) {
/*      */           
/*  658 */           tag = new ISMatTag(buf);
/*  659 */           tag.readToByteBuffer(((MLNumericArray)mLDouble).getImaginaryByteBuffer(), (ByteStorageSupport<?>)mLDouble);
/*      */         } 
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
/*  932 */         return (MLArray)mLDouble;case 7: mLSingle = new MLSingle(name, dims, type, attributes); tag = new ISMatTag(buf); tag.readToByteBuffer(((MLNumericArray)mLSingle).getRealByteBuffer(), (ByteStorageSupport<?>)mLSingle); if (mLSingle.isComplex()) { tag = new ISMatTag(buf); tag.readToByteBuffer(((MLNumericArray)mLSingle).getImaginaryByteBuffer(), (ByteStorageSupport<?>)mLSingle); }  return (MLArray)mLSingle;case 9: mLUInt8 = new MLUInt8(name, dims, type, attributes); tag = new ISMatTag(buf); tag.readToByteBuffer(((MLNumericArray)mLUInt8).getRealByteBuffer(), (ByteStorageSupport<?>)mLUInt8); if (mLUInt8.isComplex()) { tag = new ISMatTag(buf); tag.readToByteBuffer(((MLNumericArray)mLUInt8).getImaginaryByteBuffer(), (ByteStorageSupport<?>)mLUInt8); }  return (MLArray)mLUInt8;case 8: mLInt8 = new MLInt8(name, dims, type, attributes); tag = new ISMatTag(buf); tag.readToByteBuffer(((MLNumericArray)mLInt8).getRealByteBuffer(), (ByteStorageSupport<?>)mLInt8); if (mLInt8.isComplex()) { tag = new ISMatTag(buf); tag.readToByteBuffer(((MLNumericArray)mLInt8).getImaginaryByteBuffer(), (ByteStorageSupport<?>)mLInt8); }  return (MLArray)mLInt8;case 10: mLInt16 = new MLInt16(name, dims, type, attributes); tag = new ISMatTag(buf); tag.readToByteBuffer(((MLNumericArray)mLInt16).getRealByteBuffer(), (ByteStorageSupport<?>)mLInt16); if (mLInt16.isComplex()) { tag = new ISMatTag(buf); tag.readToByteBuffer(((MLNumericArray)mLInt16).getImaginaryByteBuffer(), (ByteStorageSupport<?>)mLInt16); }  return (MLArray)mLInt16;case 12: mLInt32 = new MLInt32(name, dims, type, attributes); tag = new ISMatTag(buf); tag.readToByteBuffer(((MLNumericArray)mLInt32).getRealByteBuffer(), (ByteStorageSupport<?>)mLInt32); if (mLInt32.isComplex()) { tag = new ISMatTag(buf); tag.readToByteBuffer(((MLNumericArray)mLInt32).getImaginaryByteBuffer(), (ByteStorageSupport<?>)mLInt32); }  return (MLArray)mLInt32;case 13: mLUInt32 = new MLUInt32(name, dims, type, attributes); tag = new ISMatTag(buf); tag.readToByteBuffer(((MLNumericArray)mLUInt32).getRealByteBuffer(), (ByteStorageSupport<?>)mLUInt32); if (mLUInt32.isComplex()) { tag = new ISMatTag(buf); tag.readToByteBuffer(((MLNumericArray)mLUInt32).getImaginaryByteBuffer(), (ByteStorageSupport<?>)mLUInt32); }  return (MLArray)mLUInt32;case 14: mLInt64 = new MLInt64(name, dims, type, attributes); tag = new ISMatTag(buf); tag.readToByteBuffer(((MLNumericArray)mLInt64).getRealByteBuffer(), (ByteStorageSupport<?>)mLInt64); if (mLInt64.isComplex()) { tag = new ISMatTag(buf); tag.readToByteBuffer(((MLNumericArray)mLInt64).getImaginaryByteBuffer(), (ByteStorageSupport<?>)mLInt64); }  return (MLArray)mLInt64;case 15: mLUInt64 = new MLUInt64(name, dims, type, attributes); tag = new ISMatTag(buf); tag.readToByteBuffer(((MLNumericArray)mLUInt64).getRealByteBuffer(), (ByteStorageSupport<?>)mLUInt64); if (mLUInt64.isComplex()) { tag = new ISMatTag(buf); tag.readToByteBuffer(((MLNumericArray)mLUInt64).getImaginaryByteBuffer(), (ByteStorageSupport<?>)mLUInt64); }  return (MLArray)mLUInt64;case 4: mlchar = new MLChar(name, dims, type, attributes); tag = new ISMatTag(buf); str = tag.readToString(); for (k = 0; k < str.length(); k++) mlchar.setChar(str.charAt(k), k);  return (MLArray)mlchar;case 5: sparse = new MLSparse(name, dims, attributes, nzmax); tag = new ISMatTag(buf); ir = tag.readToIntArray(); tag = new ISMatTag(buf); jc = tag.readToIntArray(); tag = new ISMatTag(buf); ad1 = tag.readToDoubleArray(); count = 0; for (column = 0; column < sparse.getN(); column++) { while (count < jc[column + 1]) { sparse.setReal(Double.valueOf(ad1[count]), ir[count], column); count++; }  }  if (sparse.isComplex()) { tag = new ISMatTag(buf); double[] ad2 = tag.readToDoubleArray(); count = 0; for (int i2 = 0; i2 < sparse.getN(); i2++) { while (count < jc[i2 + 1]) { sparse.setImaginary(Double.valueOf(ad2[count]), ir[count], i2); count++; }  }  }  return (MLArray)sparse;case 17: tag = new ISMatTag(buf); className = tag.readToString(); nn = new byte[dims.length]; for (m = 0; m < dims.length; m++) nn[m] = (byte)dims[m];  arrName = new String(nn); contentTag = new ISMatTag(buf); if (contentTag.type == 14) { MLUInt8 content = (MLUInt8)readMatrix(buf, false); ObjectInputStream ois = new ObjectInputStream(new ByteBufferInputStream(content.getRealByteBuffer(), content.getRealByteBuffer().limit())); try { Object o = ois.readObject(); mLJavaObject = new MLJavaObject(arrName, className, o); } catch (Exception e) { throw new IOException(e); } finally { ois.close(); }  } else { throw new IOException("Unexpected java object content"); }  return (MLArray)mLJavaObject;
/*      */       case 3:
/*      */         tag = new ISMatTag(buf); className = tag.readToString(); struct = new MLStructure(name, dims, type, attributes); tag = new ISMatTag(buf); maxlen = buf.getInt(); tag = new ISMatTag(buf); numOfFields = tag.size / maxlen; fieldNames = new String[numOfFields]; for (i1 = 0; i1 < numOfFields; i1++) {
/*      */           byte[] names = new byte[maxlen]; buf.get(names); fieldNames[i1] = zeroEndByteArrayToString(names);
/*      */         }  buf.position(buf.position() + tag.padding); for (n = 0; n < 1; n++) {
/*      */           for (int i2 = 0; i2 < numOfFields; i2++) {
/*      */             tag = new ISMatTag(buf); if (tag.size > 0) {
/*      */               MLArray fieldValue = readMatrix(buf, false); struct.setField(fieldNames[i2], fieldValue, n);
/*      */             } else {
/*      */               struct.setField(fieldNames[i2], (MLArray)new MLEmptyArray(), n);
/*      */             } 
/*      */           } 
/*      */         }  return (MLArray)new MLObject(name, className, struct);
/*      */     } 
/*  946 */     throw new MatlabIOException("Incorrect matlab array class: " + MLArray.typeToString(type)); } private String zeroEndByteArrayToString(byte[] bytes) throws IOException { int i = 0;
/*      */     
/*  948 */     for (i = 0; i < bytes.length && bytes[i] != 0; i++);
/*      */     
/*  950 */     return new String(bytes, 0, i); }
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
/*      */   private int[] readFlags(ByteBuffer buf) throws IOException {
/*  964 */     ISMatTag tag = new ISMatTag(buf);
/*      */     
/*  966 */     int[] flags = tag.readToIntArray();
/*      */     
/*  968 */     return flags;
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
/*      */   
/*      */   private int[] readDimension(ByteBuffer buf) throws IOException {
/*  982 */     ISMatTag tag = new ISMatTag(buf);
/*  983 */     int[] dims = tag.readToIntArray();
/*  984 */     return dims;
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
/*      */   
/*      */   private String readName(ByteBuffer buf) throws IOException {
/*  998 */     ISMatTag tag = new ISMatTag(buf);
/*      */     
/* 1000 */     return tag.readToString();
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readHeader(ByteBuffer buf) throws IOException {
/*      */     int version;
/* 1018 */     byte[] endianIndicator = new byte[2];
/*      */ 
/*      */     
/* 1021 */     byte[] descriptionBuffer = new byte[116];
/* 1022 */     buf.get(descriptionBuffer);
/*      */     
/* 1024 */     String description = zeroEndByteArrayToString(descriptionBuffer);
/*      */     
/* 1026 */     if (!description.matches("MATLAB 5.0 MAT-file.*"))
/*      */     {
/* 1028 */       throw new MatlabIOException("This is not a valid MATLAB 5.0 MAT-file.");
/*      */     }
/*      */ 
/*      */     
/* 1032 */     buf.position(buf.position() + 8);
/*      */     
/* 1034 */     byte[] bversion = new byte[2];
/*      */     
/* 1036 */     buf.get(bversion);
/*      */ 
/*      */     
/* 1039 */     buf.get(endianIndicator);
/*      */ 
/*      */ 
/*      */     
/* 1043 */     if ((char)endianIndicator[0] == 'I' && (char)endianIndicator[1] == 'M') {
/*      */       
/* 1045 */       this.byteOrder = ByteOrder.LITTLE_ENDIAN;
/* 1046 */       version = bversion[1] & 0xFF | bversion[0] << 8;
/*      */     }
/*      */     else {
/*      */       
/* 1050 */       this.byteOrder = ByteOrder.BIG_ENDIAN;
/* 1051 */       version = bversion[0] & 0xFF | bversion[1] << 8;
/*      */     } 
/*      */     
/* 1054 */     buf.order(this.byteOrder);
/*      */     
/* 1056 */     this.matFileHeader = new MatFileHeader(description, version, endianIndicator);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class ISMatTag
/*      */     extends MatTag
/*      */   {
/*      */     private final MatFileInputStream mfis;
/*      */ 
/*      */     
/*      */     private final int padding;
/*      */ 
/*      */     
/*      */     private final boolean compressed;
/*      */ 
/*      */     
/*      */     public ISMatTag(ByteBuffer buf) throws IOException {
/* 1074 */       super(0, 0);
/* 1075 */       int tmp = buf.getInt();
/*      */ 
/*      */       
/* 1078 */       if (tmp >> 16 == 0) {
/*      */         
/* 1080 */         this.type = tmp;
/* 1081 */         this.size = buf.getInt();
/* 1082 */         this.compressed = false;
/*      */       }
/*      */       else {
/*      */         
/* 1086 */         this.size = tmp >> 16;
/* 1087 */         this.type = tmp & 0xFFFF;
/* 1088 */         this.compressed = true;
/*      */       } 
/* 1090 */       this.padding = getPadding(this.size, this.compressed);
/* 1091 */       this.mfis = new MatFileInputStream(buf, this.type);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void readToByteBuffer(ByteBuffer buff, ByteStorageSupport<?> storage) throws IOException {
/* 1097 */       int elements = this.size / sizeOf();
/* 1098 */       this.mfis.readToByteBuffer(buff, elements, storage);
/* 1099 */       this.mfis.skip(this.padding);
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] readToByteArray() throws IOException {
/* 1104 */       int elements = this.size / sizeOf();
/* 1105 */       byte[] ab = new byte[elements];
/*      */       
/* 1107 */       for (int i = 0; i < elements; i++)
/*      */       {
/* 1109 */         ab[i] = this.mfis.readByte();
/*      */       }
/*      */ 
/*      */       
/* 1113 */       this.mfis.skip(this.padding);
/* 1114 */       return ab;
/*      */     }
/*      */ 
/*      */     
/*      */     public double[] readToDoubleArray() throws IOException {
/* 1119 */       int elements = this.size / sizeOf();
/* 1120 */       double[] ad = new double[elements];
/*      */       
/* 1122 */       for (int i = 0; i < elements; i++)
/*      */       {
/* 1124 */         ad[i] = this.mfis.readDouble();
/*      */       }
/*      */ 
/*      */       
/* 1128 */       this.mfis.skip(this.padding);
/* 1129 */       return ad;
/*      */     }
/*      */ 
/*      */     
/*      */     public int[] readToIntArray() throws IOException {
/* 1134 */       int elements = this.size / sizeOf();
/* 1135 */       int[] ai = new int[elements];
/*      */       
/* 1137 */       for (int i = 0; i < elements; i++)
/*      */       {
/* 1139 */         ai[i] = this.mfis.readInt();
/*      */       }
/*      */ 
/*      */       
/* 1143 */       this.mfis.skip(this.padding);
/* 1144 */       return ai;
/*      */     }
/*      */ 
/*      */     
/*      */     public String readToString() throws IOException {
/* 1149 */       byte[] bytes = readToByteArray();
/*      */       
/* 1151 */       return new String(bytes, "UTF-8");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public char[] readToCharArray() throws IOException {
/* 1158 */       int elements = this.size / sizeOf();
/* 1159 */       char[] ac = new char[elements];
/*      */       
/* 1161 */       for (int i = 0; i < elements; i++)
/*      */       {
/* 1163 */         ac[i] = this.mfis.readChar();
/*      */       }
/*      */ 
/*      */       
/* 1167 */       this.mfis.skip(this.padding);
/* 1168 */       return ac;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/io/MatFileReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */