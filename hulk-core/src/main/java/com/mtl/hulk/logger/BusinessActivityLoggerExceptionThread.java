package com.mtl.hulk.logger;

import com.mtl.hulk.AbstractHulk;
import com.mtl.hulk.BusinessActivityLogger;
import com.mtl.hulk.BusinessActivityLoggerFactory;
import com.mtl.hulk.HulkDataSource;
import com.mtl.hulk.configuration.HulkProperties;
import com.mtl.hulk.context.BusinessActivityContextHolder;
import com.mtl.hulk.context.HulkContext;
import com.mtl.hulk.context.RuntimeContextHolder;
import com.mtl.hulk.model.BusinessActivityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class BusinessActivityLoggerExceptionThread extends AbstractHulk implements Runnable {

    private Logger logger = LoggerFactory.getLogger(BusinessActivityLoggerExceptionThread.class);

    private BusinessActivityException ex;
    private HulkContext ctx;

    public BusinessActivityLoggerExceptionThread(HulkProperties properties, HulkDataSource ds, HulkContext ctx){
        super(properties, ds);
        this.ctx = ctx;
    }

    @Override
    public void run() {
        logger.info("Writing Exception log.");
        RuntimeContextHolder.setContext(ctx.getRc());
        BusinessActivityContextHolder.setContext(ctx.getBac());
        BusinessActivityLogger bal = BusinessActivityLoggerFactory.getStorage(dataSource, properties);
        if(null == bal) {
            logger.warn("businessActivityLogger获取为空，storage={}", properties.getLoggerStorage());
            return;
        }
        try {
            bal.writeEx(ex);
            logger.info("Written Exception log.");
        } catch (SQLException e) {
            logger.error("Hulk Log WriteEx Exception", e);
        }
    }

    public void setEx(BusinessActivityException ex) {
        this.ex = ex;
    }

}
