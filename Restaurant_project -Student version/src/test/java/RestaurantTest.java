import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class RestaurantTest {
    Restaurant restaurant;

    @BeforeEach
    public void BeforeEach() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        LocalTime mockTime = LocalTime.parse("12:00:00");
        LocalTime currentTime = mockTime;
        restaurant = Mockito.spy(restaurant);
        when(restaurant.getCurrentTime()).thenReturn(currentTime);

        assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        LocalTime mockTime = LocalTime.parse("09:00:00");
        LocalTime currentTime = mockTime;
        restaurant = Mockito.spy(restaurant);
        when(restaurant.getCurrentTime()).thenReturn(currentTime);

        assertFalse(restaurant.isRestaurantOpen());

    }

    @Test
    public void calculate_order_value_should_return_zero_for_empty_order() {
        int orderValue = restaurant.calculateOrderValue();
        assertEquals(0, orderValue);
    }

    @Test
    public void calculateOrderValue_should_return_correct_value_for_single_item() {
        restaurant.addToOrder("Sweet corn soup"); // Price: 119
        assertEquals(119, restaurant.calculateOrderValue());
    }

    @Test
    public void calculateOrderValue_should_return_correct_value_for_multiple_items() {
        restaurant.addToOrder("Sweet corn soup"); // Price: 119
        restaurant.addToOrder("Vegetable lasagne"); // Price: 269
        assertEquals(119 + 269, restaurant.calculateOrderValue());
    }

    @Test
    public void calculateOrderValue_should_ignore_items_not_in_menu() {
        restaurant.addToOrder("Sweet corn soup"); // Price: 119
        restaurant.addToOrder("Invalid item"); // Not in menu
        assertEquals(119, restaurant.calculateOrderValue());
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
}