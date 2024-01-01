package com.example.codeReview.todo;

import org.junit.Test;
import static org.junit.Assert.*;

public class TodoItemTest {

    @Test
    public void testGettersAndSetters() {
        // Create a TodoItem object
        TodoItem todoItem = new TodoItem();

        // Set values using setters
        todoItem.setCategory("Category 1");
        todoItem.setName("Item 1");
        todoItem.setComplete(true);

        // Verify values using getters
        assertEquals(1L, todoItem.getId().longValue());
        assertEquals("Category 1", todoItem.getCategory());
        assertEquals("Item 1", todoItem.getName());
        assertTrue(todoItem.isComplete());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Create two TodoItem objects with the same ID
        TodoItem todoItem1 = new TodoItem();

        TodoItem todoItem2 = new TodoItem();

        // Verify that the objects are equal and have the same hash code
        assertEquals(todoItem1, todoItem2);
        assertEquals(todoItem1.hashCode(), todoItem2.hashCode());
    }

    @Test
    public void testToString() {
        // Create a TodoItem object
        TodoItem todoItem = new TodoItem("Category 1", "Item 1");
        todoItem.setComplete(true);

        // Verify the string representation
        String expectedString = "TodoItem[id=1, category='Category 1', name='Item 1', complete='true']";
        assertEquals(expectedString, todoItem.toString());
    }
}