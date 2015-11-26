package task.service.lvs.repository;

import task.service.lvs.domain.BaseObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Base repository interface
 *
 * @param <T> object type
 * @param <ID> object's identifier type
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseObject<ID>, ID extends Serializable> extends JpaRepository<T, ID>
{
}
