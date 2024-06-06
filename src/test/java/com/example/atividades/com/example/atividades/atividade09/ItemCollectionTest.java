package com.example.atividades.atividade09;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ItemCollectionTest {

    private ItemCollection itemCollection;

    @BeforeEach
    public void setUp() {
        itemCollection = new ItemCollection();
    }

    @Test
    public void testAddItem_ValidItem() {
        Item item = new Item("Test Item");

        itemCollection.addItem(item);

        List<Item> items = itemCollection.getItems();
        assertEquals(1, items.size());
        assertTrue(items.contains(item));
    }

    @Test
    public void testAddItem_NullItem() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            itemCollection.addItem(null);
        });

        assertEquals("Item cannot be null", exception.getMessage());
    }

    @Test
    public void testRemoveItem_ValidItem() {
        Item item = new Item("Test Item");
        itemCollection.addItem(item);

        itemCollection.removeItem(item);

        List<Item> items = itemCollection.getItems();
        assertEquals(0, items.size());
        assertFalse(items.contains(item));
    }

    @Test
    public void testRemoveItem_ItemNotInCollection() {
        Item item = new Item("Test Item");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            itemCollection.removeItem(item);
        });

        assertEquals("Item not found in the collection", exception.getMessage());
    }

    @Test
    public void testGetItems_EmptyCollection() {
        List<Item> items = itemCollection.getItems();
        assertNotNull(items);
        assertEquals(0, items.size());
    }

    @Test
    public void testGetItems_NonEmptyCollection() {
        Item item1 = new Item("Item 1");
        Item item2 = new Item("Item 2");
        itemCollection.addItem(item1);
        itemCollection.addItem(item2);

        List<Item> items = itemCollection.getItems();
        assertNotNull(items);
        assertEquals(2, items.size());
        assertTrue(items.contains(item1));
        assertTrue(items.contains(item2));
    }
}
