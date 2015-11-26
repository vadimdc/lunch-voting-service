package task.service.lvs.service;

import task.service.lvs.domain.Dish;
import task.service.lvs.repository.DishRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DishServiceUTest extends BaseServiceUTest<Dish, Integer, DishRepository, DishService>
{
    public DishServiceUTest()
    {
        super(new DishServiceImpl(), DishRepository.class, Dish.class);
    }

    @Test
    public void testFindByName() throws Exception
    {
        String anyName = RandomStringUtils.random(10);

        service.findByName(anyName);

        verify(repository).findByName(anyName);
    }

    @Test
    public void testFindOrCreateDishExists() throws Exception
    {
        Dish dish = mock(Dish.class);
        String anyName = RandomStringUtils.random(10);

        when(repository.findByName(anyName)).thenReturn(dish);

        service.findOrCreate(anyName);

        verify(repository).findByName(anyName);
        verify(repository, never()).save(dish);
    }

    @Test
    public void testFindOrCreateNewDish() throws Exception
    {
        String anyName = RandomStringUtils.random(10);

        when(repository.findByName(anyName)).thenReturn(null);

        Dish dish = service.findOrCreate(anyName);

        assertEquals(dish.getName(), anyName);
        verify(repository).findByName(anyName);
        verify(repository).save(dish);
    }
}