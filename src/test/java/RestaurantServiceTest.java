import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantServiceTest {

    RestaurantService service = new RestaurantService();
    Restaurant restaurant;
    String restaurantName;
    List<String> mockItems;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void setup() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurantName = "Amelie's cafe";
        mockItems =  new ArrayList<String>();
    }


    //>>>>>>>>>>>>>>>>>>>>>>SEARCHING<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws restaurantNotFoundException {
        Restaurant expected = service.findRestaurantByName((restaurantName));
        assertNotNull(expected);
        assertEquals(restaurantName, expected.getName());
    }

    //You may watch the video by Muthukumaran on how to write exceptions in Course 3: Testing and Version control: Optional content
    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {
        restaurantName = "Test Restaurant";
        assertThrows(restaurantNotFoundException.class, () -> service.findRestaurantByName(restaurantName));
    }
    //<<<<<<<<<<<<<<<<<<<<SEARCHING>>>>>>>>>>>>>>>>>>>>>>>>>>




    //>>>>>>>>>>>>>>>>>>>>>>ADMIN: ADDING & REMOVING RESTAURANTS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant(restaurantName);
        assertEquals(initialNumberOfRestaurants-1, service.getRestaurants().size());
    }

    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {
        assertThrows(restaurantNotFoundException.class,()->service.removeRestaurant("Pantry d'or"));
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1(){
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales","Chennai",LocalTime.parse("12:00:00"),LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1,service.getRestaurants().size());
    }
    //<<<<<<<<<<<<<<<<<<<<ADMIN: ADDING & REMOVING RESTAURANTS>>>>>>>>>>>>>>>>>>>>>>>>>>


    //<<<<<<<<<<<<<<<<<<<<<<<GET_ORDER_VALUE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void if_restaurant_found_get_order_value_from_selected_restaurant_should_return_zero_for_empty_list() throws restaurantNotFoundException {
        assertEquals(0, service.getOrderValueFromSelectedRestaurant(restaurantName, mockItems));
    }

    @Test
    public void if_restaurant_found_get_order_value_from_selected_restaurant_should_return_the_sum_of_item_price() throws restaurantNotFoundException {
        mockItems.add("Vegetable lasagne");
        mockItems.add("Sweet corn soup");
        assertEquals(388, service.getOrderValueFromSelectedRestaurant(restaurantName, mockItems));
    }

    @Test
    public void if_restaurant_not_found_get_order_value_from_selected_restaurant_should_throw_restaurant_not_found_exception() throws restaurantNotFoundException {
        restaurantName = "Test Restaurant";
        mockItems.add("Vegetable lasagne");
        mockItems.add("Sweet corn soup");
        assertThrows(restaurantNotFoundException.class, () -> service.getOrderValueFromSelectedRestaurant(restaurantName ,mockItems));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<GET_ORDER_VALUE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}