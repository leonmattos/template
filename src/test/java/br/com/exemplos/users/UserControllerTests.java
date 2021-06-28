package br.com.exemplos.users;

import br.com.exemplos.users.controller.UserController;
import br.com.exemplos.users.model.User;
import br.com.exemplos.users.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAll() {
        ArrayList<User> users = new ArrayList<>();
        var user = new User(1L, "Leon", LocalDate.of(1994, Month.MARCH, 19));
        users.add(user);
        Mockito.when(userService.getAll()).thenReturn(users);

        MockHttpServletResponse response = makeGetRequest("/user");
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void findById() {
        var user = new User(1L, "Leon", LocalDate.of(1994, Month.MARCH, 19));
        Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(user);

        MockHttpServletResponse response = makeGetRequest("/user/1");
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }


    @Test
    void save() throws Exception {
        var user = new User(1L, "Leon", LocalDate.of(1994, Month.MARCH, 19));
        Mockito.when(userService.save(user)).thenReturn(user);

        MockHttpServletResponse response = makePostRequest("/user", objectMapper.writeValueAsString(user));
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    private MockHttpServletResponse makeGetRequest(String mapping) {
        MvcResult result = null;
        try {
            RequestBuilder requestBuilder = MockMvcRequestBuilders.get(mapping).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE);
            result = mockMvc.perform(requestBuilder).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MockHttpServletResponse response = result.getResponse();
        response.setCharacterEncoding("UTF-8");
        return response;
    }

    private MockHttpServletResponse makePostRequest(String mapping, String data) {
        MvcResult result = null;
        try {
            RequestBuilder requestBuilder = MockMvcRequestBuilders.post(mapping).content(data).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE);
            result = mockMvc.perform(requestBuilder).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.getResponse();
    }


}

