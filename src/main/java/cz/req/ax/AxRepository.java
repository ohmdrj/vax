package cz.req.ax;

import cz.thickset.utils.IdObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author ondrej
 */
@NoRepositoryBean
public interface AxRepository<T extends IdObject<Integer>> extends JpaRepository<T, Integer> {

    Class<T> entityClass();

    T entityInstance();

    T saveMerge(T entity);

    /*AxContainer<T> newContainer();

    AxEditor<T> newEditor();*/

    /* @Deprecated
    T eagerize(T entity);
    */
}
