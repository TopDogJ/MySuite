package com.mysuite.mytrade.api.entity.repository.exchange;

import com.mysuite.mytrade.api.entity.bean.exchange.Exchange;
import com.mysuite.mytrade.api.entity.repository.AbstractHibernateEntityRepository;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

/**
 * Created by jianl on 19/05/2017.
 */
@Repository
public class ExchangeEntityRepository extends AbstractHibernateEntityRepository<Exchange> {

    private static final String FIND_ALL_HQL = "from Exchange";
    private static final String FIND_BY_CODE_HQL = "from Exchange exchange where exchange.code = ?";

    public ExchangeEntityRepository() {
        super(LogFactory.getLog(ExchangeEntityRepository.class));
    }

    @Override
    protected Exchange doLoad(Long id) {
        return this.getCurrentSession().load(Exchange.class, id);
    }

    @Override
    protected String getFindAllQueryString() {
        return FIND_ALL_HQL;
    }

    @Override
    protected String getFindByDuplicateQueryString() {
        return FIND_BY_CODE_HQL;
    }
}
