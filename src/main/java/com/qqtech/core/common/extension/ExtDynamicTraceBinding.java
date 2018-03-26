package com.qqtech.core.common.extension;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

public class ExtDynamicTraceBinding implements DynamicFeature {

    public void configure(ResourceInfo resourceInfo, FeatureContext context) {
        context.register(ExtDynamicTraceInterceptor.class);
    }
}