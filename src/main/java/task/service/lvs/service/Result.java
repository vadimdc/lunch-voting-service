package task.service.lvs.service;

import java.util.List;

public interface Result<T>
{
    /**
     * Get result
     */
    List<T> getResult();
}
