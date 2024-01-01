package com.example.codeReview.todo;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
@WebMvcTest(TodoAppController.class)
public class TodoAppControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoItemRepository repository;

    @Test
    public void testIndex() throws Exception {
        TodoItem item1 = new TodoItem("Category 1", "Item 1");
        TodoItem item2 = new TodoItem("Category 2", "Item 2");
        List<TodoItem> todoList = Arrays.asList(item1, item2);

        given(repository.findAll()).willReturn(todoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("items"))
                .andExpect(model().attributeExists("newitem"))
                .andExpect(model().attribute("items", new TodoListViewModel(todoList)));
    }

    @Test
    public void testAdd() throws Exception {
        TodoItem requestItem = new TodoItem("Category 3", "Item 3");

        mockMvc.perform(MockMvcRequestBuilders.post("/add")
                .flashAttr("requestItem", requestItem))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(repository).save(any(TodoItem.class));
    }

    @Test
    public void testUpdate() throws Exception {
        TodoItem item1 = new TodoItem("Category 1", "Item 1");
        TodoItem item2 = new TodoItem("Category 2", "Item 2");
        List<TodoItem> requestItems = Arrays.asList(item1, item2);

        mockMvc.perform(MockMvcRequestBuilders.post("/update")
                .flashAttr("requestItems", new TodoListViewModel(requestItems)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(repository, times(2)).save(any(TodoItem.class));
    }
}