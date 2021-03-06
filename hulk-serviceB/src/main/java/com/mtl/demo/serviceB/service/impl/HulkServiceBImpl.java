package com.mtl.demo.serviceB.service.impl;

import com.mtl.demo.serviceB.service.HulkServiceB;
import com.mtl.hulk.annotation.MTLTwoPhaseAction;
import com.mtl.hulk.context.BusinessActivityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HulkServiceBImpl implements HulkServiceB {

    private final static Logger logger = LoggerFactory.getLogger(HulkServiceBImpl.class);

    @MTLTwoPhaseAction(confirmMethod = "confirmMysqlSaveAssetBCard", cancelMethod = "cancelMysqlSaveAssetBCard")
    @Override
    public String getHulkServiceB(int a, int b) throws InterruptedException {
//        Thread.sleep(3000);
        return "HulkServiceB/////////////";
    }
    public boolean confirmMysqlSaveAssetBCard(BusinessActivityContext context) {
        logger.info("confirm B params: {} {}", context.getParams().get("getHulkServiceB"));
        return false;
    }

    public boolean cancelMysqlSaveAssetBCard(BusinessActivityContext context) {
        logger.info("cancel B params: {} {}", context.getParams().get("getHulkServiceB"));
        return true;
    }
}
