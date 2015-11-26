package task.service.lvs.service;

import task.service.lvs.repository.BaseRepository;

/**
 * Base interface for all services
 * @param <R>
 */
public interface PersistentService<R extends BaseRepository>
{
    /**
     * Sets an internal BaseRepository object
     *
     * @param repository object
     */
    void setRepository(R repository);
}
