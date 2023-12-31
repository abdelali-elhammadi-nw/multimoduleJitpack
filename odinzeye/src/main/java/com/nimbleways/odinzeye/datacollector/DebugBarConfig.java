package com.nimbleways.odinzeye.datacollector;


import com.nimbleways.odinzeye.datacollector.databasequerycollector.DataBaseQueriesCollector;
import com.nimbleways.odinzeye.datacollector.databasequerycollector.DataBaseQueryEntity;
import com.nimbleways.odinzeye.datacollector.databasequerycollector.JpaMethodsCollector;
import com.nimbleways.odinzeye.datacollector.logscollector.LogsConfiguration;
import com.nimbleways.odinzeye.websocket.IWSDispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DebugBarConfig {

    @Bean
    public LogsConfiguration logsConfiguration(final IWSDispatcher wsDispatcher)
    {
        LogsConfiguration config = new LogsConfiguration(wsDispatcher);
        config.configure();
        return config;
    }

}