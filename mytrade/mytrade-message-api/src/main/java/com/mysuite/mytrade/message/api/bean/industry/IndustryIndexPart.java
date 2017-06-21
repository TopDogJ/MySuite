package com.mysuite.mytrade.message.api.bean.industry;

import java.io.Serializable;

/**
 * Created by jianl on 19/05/2017.
 */
public class IndustryIndexPart implements Serializable {
    private String numberOfSecurities;

    public String getNumberOfSecurities() {
        return numberOfSecurities;
    }

    public void setNumberOfSecurities(String numberOfSecurities) {
        this.numberOfSecurities = numberOfSecurities;
    }

    @Override
    public String toString() {
        return "IndustryIndexPart{" +
                "numberOfSecurities=" + numberOfSecurities +
                '}';
    }
}
