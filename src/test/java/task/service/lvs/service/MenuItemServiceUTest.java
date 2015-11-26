package task.service.lvs.service;

import task.service.lvs.repository.MenuItemRepository;
import task.service.lvs.domain.Dish;
import task.service.lvs.domain.MenuItem;
import task.service.lvs.domain.Restaurant;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MenuItemServiceUTest extends BaseServiceUTest<MenuItem, Integer, MenuItemRepository, MenuItemService>
{
    public MenuItemServiceUTest()
    {
        super(new MenuItemServiceImpl(), MenuItemRepository.class, MenuItem.class);
    }

    @Test
    public void testFindByRestaurantAndDish() throws Exception
    {
        Restaurant restaurant = mock(Restaurant.class);
        Dish       dish = mock(Dish.class);

        service.findByRestaurantAndDish(restaurant, dish);

        verify(repository).findByRestaurantAndDish(restaurant, dish);
    }

    @Test
    public void testCreateOrUpdateMenuItemExists() throws Exception
    {
        Restaurant restaurant = mock(Restaurant.class);
        Dish dish = mock(Dish.class);
        BigDecimal price = BigDecimal.TEN;

        MenuItem menuItem = new MenuItem();
        menuItem.setPrice(BigDecimal.ONE);

        when(repository.findByRestaurantAndDish(restaurant, dish)).thenReturn(menuItem);
        when(repository.save(menuItem)).thenReturn(menuItem);

        MenuItem result = service.createOrUpdate(restaurant, dish, price);

        assertEquals(result.getPrice(), price);
        verify(repository).findByRestaurantAndDish(restaurant, dish);
        verify(repository).save(menuItem);
    }

    @Test
    public void testCreateOrUpdateNewMenuItem() throws Exception
    {
        Restaurant restaurant = mock(Restaurant.class);
        Dish dish = mock(Dish.class);
        BigDecimal price = BigDecimal.TEN;

        when(repository.findByRestaurantAndDish(restaurant, dish)).thenReturn(null);
        ArgumentCaptor<MenuItem> captor = ArgumentCaptor.forClass(MenuItem.class);

        service.createOrUpdate(restaurant, dish, price);

        verify(repository).save(captor.capture());
        MenuItem menuItem = captor.getValue();

        assertNotNull(menuItem);
        assertEquals(menuItem.getRestaurant(), restaurant);
        assertEquals(menuItem.getDish(), dish);
        assertEquals(menuItem.getPrice(), price);
        verify(repository).findByRestaurantAndDish(restaurant, dish);
    }
}