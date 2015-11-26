package task.service.lvs.service;

import task.service.lvs.domain.Restaurant;
import task.service.lvs.repository.RestaurantRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import static org.mockito.Mockito.verify;

public class RestaurantServiceUTest extends BaseServiceUTest<Restaurant, Integer, RestaurantRepository, RestaurantService>
{
    public RestaurantServiceUTest()
    {
        super(new RestaurantServiceImpl(), RestaurantRepository.class, Restaurant.class);
    }

    @Test
    public void testFindByName() throws Exception
    {
        String anyName = RandomStringUtils.random(10);

        service.findByName(anyName);

        verify(repository).findByName(anyName);
    }
}