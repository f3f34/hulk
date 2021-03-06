package com.mtl.hulk.logger;

import com.mtl.hulk.AbstractHulk;
import com.mtl.hulk.BusinessActivityLogger;
import com.mtl.hulk.BusinessActivityLoggerFactory;
import com.mtl.hulk.HulkDataSource;
import com.mtl.hulk.configuration.HulkProperties;
import com.mtl.hulk.context.BusinessActivityContextHolder;
import com.mtl.hulk.context.HulkContext;
import com.mtl.hulk.context.RuntimeContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class BusinessActivityLoggerThread extends AbstractHulk implements Runnable {

    private Logger logger = LoggerFactory.getLogger(BusinessActivityLoggerThread.class);
    private HulkContext ctx;

    public BusinessActivityLoggerThread(HulkProperties properties, HulkDataSource ds, HulkContext ctx) {
        super(properties, ds);
        this.ctx = ctx;
    }

    @Override
    public void run() {
        RuntimeContextHolder.setContext(ctx.getRc());
        BusinessActivityContextHolder.setContext(ctx.getBac());
        BusinessActivityLogger bal = BusinessActivityLoggerFactory.getStorage(dataSource, properties);
        try {
            bal.write(RuntimeContextHolder.getContext(), BusinessActivityContextHolder.getContext());
        } catch (SQLException e) {
            logger.error("Hulk Log Write Exception", e);
        }
    }

}
