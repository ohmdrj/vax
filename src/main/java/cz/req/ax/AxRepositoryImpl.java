/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.req.ax;

import cz.thickset.utils.IdObject;
import cz.thickset.utils.reflect.PrivateClassUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.PersistenceProvider;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;

/**
 * @author ondrej
 */
public class AxRepositoryImpl<T extends IdObject<Integer>>
        extends SimpleJpaRepository<T, Integer>
        implements AxRepository<T> {

    Class<T> entityClass;

    public AxRepositoryImpl(JpaEntityInformation<T, Integer> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        entityClass = entityInformation.getJavaType();
    }

    public AxRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        entityClass = domainClass;
    }

    EntityManager getEntityManager() {
        return (EntityManager) PrivateClassUtils.readField(this, SimpleJpaRepository.class, "em");
    }

    PersistenceProvider getPersistenceProvider() {
        return (PersistenceProvider) PrivateClassUtils.readField(this, SimpleJpaRepository.class, "provider");
    }

    @Override
    public Class<T> entityClass() {
        return entityClass;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public T entityInstance() {
        try {
            return entityClass.newInstance();
        } catch (Exception ex) {
            throw new IllegalStateException("Chyba instance " + entityClass, ex);
        }
    }

    @Override
    public T saveMerge(T entity) {
        Assert.notNull(entity);
        if (!getEntityManager().contains(entity)) {
            entity = getEntityManager().merge(entity);
        }
        return save(entity);
    }

    /*@Transactional
    public T create(T source, MappingContext mappingContext) {
        if (source == null || source.getId() == null) {
            return entityInstance();
        }
        T find = findOne(source.getId());
        if (find == null) {
            throw new IllegalArgumentException("Neni v databazi");
        }
        return find;
    }*/
   /* @Transactional
    @Override
    public T eagerize(T entity) {
        return (T) eagerizeImpl(entity);
    }

    public IdObject eagerizeImpl(IdObject entity) {
        if (entity == null || entity.getId() == null) {
            return entity;
        }
        try {
            entity = getEntityManager().getReference(entity.getClass(), entity.getId());
            for (Method method : entity.getClass().getMethods()) {
                if (IdObject.class.isAssignableFrom(method.getReturnType())) {
                    eagerizeImpl((IdObject) method.invoke(entity));
                }
                if (Collection.class.isAssignableFrom(method.getReturnType())) {
                    Collection collect = (Collection) method.invoke(entity);
                    if (collect != null)
                        for (Object object : collect) {
                            if (object instanceof IdObject)
                                eagerizeImpl((IdObject) object);
                        }
                }
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return entity;
    }*/
}
