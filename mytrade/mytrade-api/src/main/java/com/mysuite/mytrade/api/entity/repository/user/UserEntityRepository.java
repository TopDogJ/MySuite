package com.mysuite.mytrade.api.entity.repository.user;

import com.mysuite.mytrade.api.entity.bean.user.User;
import com.mysuite.mytrade.api.entity.repository.AbstractHibernateEntityRepository;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
 * Created by jianl on 7/06/2017.
 */
@Component
public class UserEntityRepository extends AbstractHibernateEntityRepository<User> {

    private static final String FIND_ALL_HQL = "from User";
    private static final String FIND_BY_USERNAME_HQL = "from User user where user.username= ?";

    public UserEntityRepository() {
        super(LogFactory.getLog(UserEntityRepository.class));
    }

    @Override
    protected User doLoad(Long id) {
        return this.getCurrentSession().load(User.class,id);
    }

    @Override
    protected String getFindAllQueryString() {
        return FIND_ALL_HQL;
    }

    @Override
    protected String getFindByDuplicateQueryString() {
        return FIND_BY_USERNAME_HQL;
    }
}
