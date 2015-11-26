package task.service.lvs.service;

import com.google.common.collect.Lists;
import task.service.lvs.domain.BaseObject;
import task.service.lvs.repository.BaseRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Service implements methods from interface {@code BaseEntityService}. All services which should
 * implement CRUD operations should be extended from this service
 *
 * @param <T>  entity type
 * @param <ID> entity identifier type
 * @param <R>  repository typr
 */
public abstract class AbstractService<T extends BaseObject<ID>, ID extends Serializable, R extends BaseRepository<T, ID>>
        implements BaseEntityService<T, ID, R>
{
    protected Log logger = LogFactory.getLog(AbstractService.class);

    protected R        repository;
    protected Class<T> clazz;

    protected AbstractService(Class<T> clazz)
    {
        this.clazz = clazz;
    }

    @Override
    public T createNew()
    {
        try
        {
            return clazz.newInstance();
        }
        catch (Exception e)
        {
            logger.error(e);
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public T findById(ID identifier)
    {
        return repository.findOne(identifier);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll()
    {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    @Transactional(readOnly = false)
    public <S extends T> S save(S entity)
    {
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(T entity)
    {
        repository.delete(entity);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteAll()
    {
        repository.deleteAllInBatch();
    }

    @Override
    public void flush()
    {
        repository.flush();
    }
}
