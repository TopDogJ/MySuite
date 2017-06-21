package com.mysuite.mytrade.api.entity.repository.security;


import com.mysuite.mytrade.api.entity.bean.security.Status;
import com.mysuite.mytrade.api.entity.repository.AbstractHibernateEntityRepository;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

/**
 * Created by jianl on 20/05/2017.
 */
@Repository
public class StatusEntityRepository extends AbstractHibernateEntityRepository<Status> {

    private static final String FIND_ALL_HQL = "from Status";
    private static final String FIND_BY_CODE_HQL = "from Status status where status.code = ?";

    public StatusEntityRepository() {
        super(LogFactory.getLog(StatusEntityRepository.class));
    }

    @Override
    protected Status doLoad(Long id) {
        return this.getCurrentSession().load(Status.class, id);
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
