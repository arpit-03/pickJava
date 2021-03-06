package org.apache.http.nio;

import org.apache.http.config.MessageConstraints;
import org.apache.http.nio.reactor.SessionInputBuffer;

public interface NHttpMessageParserFactory<T extends org.apache.http.HttpMessage> {
  NHttpMessageParser<T> create(SessionInputBuffer paramSessionInputBuffer, MessageConstraints paramMessageConstraints);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/nio/NHttpMessageParserFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */