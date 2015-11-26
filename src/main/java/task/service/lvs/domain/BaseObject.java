package task.service.lvs.domain;

import java.io.Serializable;

public abstract class BaseObject<ID extends Serializable> implements IdentifiableDomainObject<ID>
{
    /**
     * Overrides method in compliance with business logic
     *
     * @see Object#equals(Object)
     */
    @Override
    public abstract boolean equals(Object obj);

    /**
     * Overrides method in compliance with business logic
     *
     * @see Object#hashCode()
     */
    @Override
    public abstract int hashCode();

    /**
     * Overrides method in compliance with business logic
     *
     * @see Object#toString()
     */
    @Override
    public abstract String toString();

}
