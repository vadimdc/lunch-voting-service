package task.service.lvs.service;

import com.google.common.collect.Lists;
import task.service.lvs.domain.BaseObject;
import task.service.lvs.repository.BaseRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.Serializable;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public abstract class BaseServiceUTest<T extends BaseObject<ID>, ID extends Serializable, R extends BaseRepository<T, ID>, S extends BaseEntityService<T, ID, R>>
{
    @InjectMocks
    protected S service;
    protected R repository;
    protected Class<T> entityClass;

    protected BaseServiceUTest(S service, Class<R> repositoryClass, Class<T> entityClass)
    {
        this.service = service;
        this.repository = mock(repositoryClass);
        this.entityClass = entityClass;
    }

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        service.setRepository(repository);
    }

    @Test
    public void testFindById() throws Exception
    {
        T entity = mock(entityClass);
        when(repository.findOne(entity.getId())).thenReturn(entity);

        service.findById(entity.getId());

        verify(repository).findOne(entity.getId());
    }

    @Test
    public void testFindAll() throws Exception
    {
        T entity = mock(entityClass);
        List<T> list = Lists.<T>newArrayList(entity);
        list.add(entity);
        when(repository.findAll()).thenReturn(list);

        List<T> result = service.findAll();

        assertEquals(list, result);
    }

    @Test
    public void testSave() throws Exception
    {
        T entity = mock(entityClass);
        when(repository.save(entity)).thenReturn(entity);

        service.save(entity);

        verify(repository).save(entity);
    }

    @Test
    public void testDelete() throws Exception
    {
        T entity = mock(entityClass);

        service.delete(entity);

        verify(repository).delete(entity);
    }

    @Test
    public void testDeleteAll() throws Exception
    {
        service.deleteAll();

        verify(repository).deleteAllInBatch();
    }

    @Test
    public void testFlush() throws Exception
    {
        service.flush();

        verify(repository).flush();
    }
}
