import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

class RestaurantTest {
    Restaurant restaurant;
    Restaurant restaurantSpy;
    List<String> mockItems;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void setup() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurantSpy = Mockito.spy(restaurant);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        mockItems = new ArrayList<String>();
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        LocalTime mockAfterOpeningTime = LocalTime.of(11, 00);
        doReturn(mockAfterOpeningTime).when(restaurantSpy).getCurrentTime();
        assertTrue(restaurantSpy.isRestaurantOpen());
        LocalTime mockBeforeClosingTime = LocalTime.of(21, 00);
        doReturn(mockBeforeClosingTime).when(restaurantSpy).getCurrentTime();
        assertTrue(restaurantSpy.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        LocalTime mockBeforeOpeningTime = LocalTime.of(9, 00);
        doReturn(mockBeforeOpeningTime).when(restaurantSpy).getCurrentTime();
        assertFalse(restaurantSpy.isRestaurantOpen());
        LocalTime mockAfterClosing = LocalTime.of(23, 00);
        doReturn(mockAfterClosing).when(restaurantSpy).getCurrentTime();
        assertFalse(restaurantSpy.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void calculate_order_value_should_return_zero_for_empty_list() {
        assertEquals(0, restaurant.calculateOrderValue(mockItems));
    }

    @Test
    public void calculate_order_value_should_return_the_sum_of_item_price() {
        mockItems.add("Vegetable lasagne");
        mockItems.add("Sweet corn soup");
        assertEquals(388, restaurant.calculateOrderValue(mockItems));
    }

    @Test
    public void calculate_order_value_should_not_includes_non_existing_items_and_return_the_sum_of_existing_item_price() {
        mockItems.add("Vegetable lasagne");
        mockItems.add("Sweet corn soup");
        mockItems.add("Burgers");
        assertEquals(388, restaurant.calculateOrderValue(mockItems));
    }
}