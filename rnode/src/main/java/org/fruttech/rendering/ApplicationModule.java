package org.fruttech.rendering;

import org.fruttech.rendering.services.RenderStateService;
import org.fruttech.rendering.services.RenderStateServiceImpl;

public class ApplicationModule extends BaseModule {
    @Override protected void configure() {
        bind(RenderStateService.class).to(RenderStateServiceImpl.class);
    }
}

