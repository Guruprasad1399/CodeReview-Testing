package com.example.codeReview.todo;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(TodoRestController.class)
public class TodoRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoItemRepository repository;

    @Test
    public void testGetAll() throws Exception {
        TodoItem item1 = new TodoItem("Category 1", "Item 1");
        TodoItem item2 = new TodoItem("Category 2", "Item 2");
        List<TodoItem> todoList = Arrays.asList(item1, item2);

        given(repository.findAll()).willReturn(todoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/todo/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].category").value("Category 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Item 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].category").value("Category 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Item 2"));
    }

    @Test
    public void testAddItem() throws Exception {
        TodoItem savedItem = new TodoItem("Category 3", "Item 3");

        given(repository.save(any(TodoItem.class))).willReturn(savedItem);

        mockMvc.perform(MockMvcRequestBuilders.post("/todo/add")
                .param("name", "Item 3")
                .param("category", "Category 3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Added"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.item.category").value("Category 3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.item.name").value("Item 3"));

        verify(repository, times(1)).save(any(TodoItem.class));
    }

    @Test
    public void testUpdateItem() throws Exception {
        TodoItem requestItem = new TodoItem("Category 1", "Item 1");
        requestItem.setComplete(true);
        TodoItem savedItem = new TodoItem("Category 1", "Item 1");
        savedItem.setComplete(true);

        given(repository.save(any(TodoItem.class))).willReturn(savedItem);

        mockMvc.perform(MockMvcRequestBuilders.post("/todo/update")
                .param("id", "1")
                .param("name", "Item 1")
                .param("category", "Category 1")
                .param("isComplete", "true"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.item.category").value("Category 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.item.name").value("Item 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.item.isComplete").value(true));

        verify(repository, times(1)).save(any(TodoItem.class));
    }

}