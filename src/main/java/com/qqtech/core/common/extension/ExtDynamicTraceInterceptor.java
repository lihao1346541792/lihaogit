package com.qqtech.core.common.extension;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.IOException;

@Priority(Priorities.USER)
public class ExtDynamicTraceInterceptor implements ReaderInterceptor, WriterInterceptor {

    public Object aroundReadFrom(ReaderInterceptorContext readerInterceptorContext) throws IOException, WebApplicationException {
        System.out.println("Dynamic reader interceptor invoked");
        return readerInterceptorContext.proceed();
    }

    public void aroundWriteTo(WriterInterceptorContext writerInterceptorContext) throws IOException, WebApplicationException {
        System.out.println("Dynamic writer interceptor invoked");
        writerInterceptorContext.proceed();
    }
}
