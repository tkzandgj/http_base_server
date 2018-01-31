package com.cncnc.netty.serverbase.initializer;

import com.cncnc.netty.serverbase.config.RequestMapping;
import com.cncnc.netty.serverbase.config.RequestMappingsConfig;
import com.cncnc.netty.serverbase.handler.DispatcherHandler;
import com.cncnc.netty.serverbase.handler.EntryHandler;
import com.cncnc.netty.serverbase.utils.CollectionUtil;
import io.netty.channel.ChannelHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tukangzheng
 */
@Component
public class HandlerManager {

    @Autowired
    private RequestMappingsConfig requestMappingsConfig;

    @Autowired
    private ApplicationContext appContext;

    public List<ChannelHandler> getHandles() throws ClassNotFoundException {
        List<ChannelHandler> handlers = new ArrayList<>();

        DispatcherHandler dispatcherHandler = appContext.getBean(DispatcherHandler.class);
        handlers.add(dispatcherHandler);

        List<RequestMapping> requestMappings = requestMappingsConfig.getRequestMappings();

        for (RequestMapping requestMapping : requestMappings) {
            String url = requestMapping.getUrl();
            if (!CollectionUtil.isEmpty(requestMapping.getHandlers())) {

                // 没一组handler都需要一个EntryHandler做为入口，那这个handler就不需要通过
                // 配置文件去制定了，自动加入
                EntryHandler entryHandler = appContext.getBean(EntryHandler.class);
                entryHandler.setDispatcherHandler(dispatcherHandler);
                entryHandler.setUrl(url);
                handlers.add(entryHandler);

                // 加入配置文件制定的handler
                for (int i = 0; i < requestMapping.getHandlers().size(); i++) {
                    Class handlerClass = Class.forName(requestMapping.getHandlers().get(i));
                    ChannelHandler handler = (ChannelHandler) appContext.getBean(handlerClass);
                    handlers.add(handler);
                }
            }
        }

        return handlers;
    }
}
