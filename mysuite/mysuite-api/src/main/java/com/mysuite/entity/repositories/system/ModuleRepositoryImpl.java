package com.mysuite.entity.repositories.system;

import com.mysuite.entity.beans.system.Module;
import com.mysuite.entity.support.EntityRepository;
import com.mysuite.service.internal.logging.SystemLogging;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jianl on 30/03/2017.
 */
@Repository
public class ModuleRepositoryImpl extends EntityRepository implements ModuleRepository {

    @Transactional(propagation = Propagation.REQUIRED)
    @SystemLogging
    public void save( final Module module) {
        this.getSessionFactory().getCurrentSession().save(module);
    }
}
