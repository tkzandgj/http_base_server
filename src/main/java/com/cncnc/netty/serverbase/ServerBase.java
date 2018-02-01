package com.cncnc.netty.serverbase;


import com.cncnc.netty.serverbase.config.ServerConfig;
import com.cncnc.netty.serverbase.initializer.ServerInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Administrator
 * HttpServer启动类的基类
 *
 */
@SpringBootApplication
public abstract class ServerBase implements CommandLineRunner{

    @Autowired
    private ServerConfig serverConfig;

    @Override
    public void run(String... strings) throws Exception {
        NettyServerContext.init(serverConfig);

        preInit(serverConfig);

        ServerInit serverInit = getServerInitializer(serverConfig);
        serverInit.init(strings);

        beforeClose(serverConfig);
    }


    /**
     * 子类实现该方法提供自定义的ServerInit实例
     * @param serverConfig
     * @return
     */
    protected abstract ServerInit getServerInitializer(ServerConfig serverConfig);


    /**
     * 子类实现该方法做自己的一些初始化工作
     * @param serverConfig
     */
    protected void preInit(ServerConfig serverConfig){

    }

    /**
     * 子类实现该方法做自己的一些资源释放工作
     * @param serverConfig
     */
    protected void beforeClose(ServerConfig serverConfig){

    }
}
