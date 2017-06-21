package com.mysuite.mytrade.api.entity.repository;

import com.mysuite.commons.exception.entity.EntityNotFoundException;
import com.mysuite.mytrade.api.entity.bean.EntityBean;

import java.util.List;

/**
 * Created by jianl on 27/04/2017.
 */
public interface EntityRepository<E extends EntityBean> {
    public E save(final E e);
    public void update(final E e);
    public void delete(final E e);
    public E load(final Long id);
    public E findByDuplicateForReference(final List<String> params) throws EntityNotFoundException;
    public E findByDuplicate(final List<String> params) throws EntityNotFoundException;
    public E findByDuplicateForUpdate(final List<String> params) throws EntityNotFoundException;
    public List<E> findAllForReference() throws EntityNotFoundException;
    public List<E> findByNamedQueryForReference(final String query, final List<String> params) throws EntityNotFoundException;
    public E findSingleByNameQueryForReference(final String query, final List<String> params) throws EntityNotFoundException;
    public List<E> findByNamedQuery(final String query, final List<String> params) throws EntityNotFoundException;
    public E findSingleByNameQuery(final String query, final List<String> params) throws EntityNotFoundException;
    public List<E> findByNamedQueryForUpdate(final String query, final List<String> params) throws EntityNotFoundException;
    public E findSingleByNameQueryForUpdate(final String query, final List<String> params) throws EntityNotFoundException;
}
