package task.service.lvs.domain;

import java.io.Serializable;

/**
 * Interface indicates that a domain object is identifiable by identifier
 */
public interface IdentifiableDomainObject<ID extends Serializable>
{
    /**
     * Returns identifier for a domain object
     *
     * @return domain object's identifier
     */
    ID getId();
}
