package com.mysuite.mytrade.api.entity.repository.industry;

import com.mysuite.mytrade.api.entity.bean.industry.Industry;
import com.mysuite.mytrade.api.entity.repository.AbstractHibernateEntityRepository;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

/**
 * Created by jianl on 19/05/2017.
 */
@Repository
public class IndustryEntityRepository extends AbstractHibernateEntityRepository<Industry> {

    private static final String FIND_ALL_HQL = "from Industry";
    private static final String FIND_BY_CODE_HQL = "from Industry industry where industry.vendorCode = ?";

    public IndustryEntityRepository() {
        super(LogFactory.getLog(IndustryEntityRepository.class));
    }

    @Override
    protected Industry doLoad(Long id) {
        return this.getCurrentSession().load(Industry.class, id);
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
