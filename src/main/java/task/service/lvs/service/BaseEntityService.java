package task.service.lvs.service;

import task.service.lvs.domain.BaseObject;
import task.service.lvs.repository.BaseRepository;

import java.io.Serializable;
import java.util.List;

public interface BaseEntityService<T extends BaseObject<ID>, ID extends Serializable, R extends BaseRepository<T, ID>> extends PersistentService<R>
{
    /**
     * Service method for creating new entity.
     * All new domain entities should be created using this method
     *
     * @return new entity of type T
     */
    T createNew();

    /**
     * Retrieves an entity by its identifier.
     *
     * @param identifier must not be {@literal null}.
     * @return the entity with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    T findById(ID identifier);

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    List<T> findAll();

    /**
     * Saves a given entity. Use the returned instance for further operations
     * as the save operation might have changed the entity instance completely
     *
     * @param entity entity to save
     * @return the saved entity
     */
    <S extends T> S save(S entity);

    /**
     * Deletes a given entity. In case the given entity is {@literal null}
     * does nothing
     *
     * @param entity entity to delete
     * @throws IllegalArgumentException in case the given entity is {@literal null}
     */
    void delete(T entity);

    /**
     * Deletes all entites
     */
    void deleteAll();

    /**
     * Flushes all pending changes to the database.
     */
    void flush();
}
