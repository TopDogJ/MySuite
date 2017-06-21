package com.mysuite.mytrade.api.entity.repository.exchange;

import com.mysuite.mytrade.api.entity.bean.exchange.ExchangeType;
import com.mysuite.mytrade.api.entity.repository.AbstractHibernateEntityRepository;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

/**
 * Created by jianl on 19/05/2017.
 */
@Repository
public class ExchangeTypeEntityRepository extends AbstractHibernateEntityRepository<ExchangeType> {

    private static final String FIND_ALL_HQL = "from ExchangeType";
    private static final String FIND_BY_CODE_HQL = "from ExchangeType exchangeType where exchangeType.code = ? and exchangeType.exchange.code= ?";

    public ExchangeTypeEntityRepository() {
        super(LogFactory.getLog(ExchangeTypeEntityRepository.class));
    }

    @Override
    protected ExchangeType doLoad(Long id) {
        return this.getCurrentSession().load(ExchangeType.class, id);
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
