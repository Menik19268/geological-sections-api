package com.testing.geologicalsectionsapi.services.impl;

import org.springframework.stereotype.Component;


@Component
public abstract class CommonService {
    public abstract String getStatusByIdAndType(String id);
}
