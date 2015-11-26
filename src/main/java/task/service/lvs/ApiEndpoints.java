package task.service.lvs;

public final class ApiEndpoints
{
    public static final String API_VERSION = "/v1";
    public static final String API_ROOT    = "/api" + API_VERSION;

    public static final String RESTAURANTS            = "/restaurants";
    public static final String RESTAURANTS_RESTAURANT = RESTAURANTS + "/{restaurantId}";

    public static final String DISHES      = "/dishes";
    public static final String DISHES_DISH = DISHES + "/{dishId}";

    public static final String MENU_ITEMS_RESTAURANT = "/menu-items/restaurants/{restaurantId}";
    public static final String MENU_ITEMS_ITEM       = "/menu-items/{itemId}";
    public static final String MENU_ITEMS            = "/menu-items";

    public static final String LUNCH_MENU     = "/lunch-menu";
    public static final String VOTING         = "/voting";
    public static final String VOTING_RESULTS = "/voting/results";

    public static String apiEndpoint(String endpoint)
    {
        return API_ROOT + endpoint;
    }

    private ApiEndpoints()
    {
    }
}
