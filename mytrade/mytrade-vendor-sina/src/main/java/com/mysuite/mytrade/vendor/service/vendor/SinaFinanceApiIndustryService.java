package com.mysuite.mytrade.vendor.service.vendor;

import com.mysuite.commons.exception.VendorDataNotFoundException;
import com.mysuite.commons.exception.service.ServiceException;
import com.mysuite.mytrade.message.api.bean.industry.IndustryMessage;

import java.util.List;

/**
 * Created by jianl on 19/05/2017.
 */
public interface SinaFinanceApiIndustryService {
    public IndustryMessage createIndustryMessageByCode(final String industryCode) throws ServiceException, VendorDataNotFoundException;

    public List<IndustryMessage> createAllIndustryMessage() throws ServiceException, VendorDataNotFoundException;
}
