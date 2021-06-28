package br.com.exemplos.users;


import br.com.exemplos.users.exception.EntityNotFoundException;
import br.com.exemplos.users.exception.GenericException;
import br.com.exemplos.users.model.User;
import br.com.exemplos.users.repository.UserRepository;
import br.com.exemplos.users.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTests {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Test
    void save() {
        User user = new User(null, "Leon", LocalDate.of(1994, Month.MARCH, 19));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        User userSaved = userService.save(user);
        Assertions.assertEquals(user, userSaved);
    }

    @Test
    void getAll() {
        ArrayList<User> users = new ArrayList<>();
        User user = new User(null, "Leon", LocalDate.of(1994, Month.MARCH, 19));
        users.add(user);
        Mockito.when(userRepository.findAll()).thenReturn(users);

        List<User> all = userService.getAll();
        Assertions.assertTrue(!all.isEmpty());
    }

    @Test
    void findById() {
        User user = new User(1L, "Leon", LocalDate.of(1994, Month.MARCH, 19));
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        User byId = userService.findById(1L);
        Assertions.assertEquals(user, byId);
    }

    @Test
    void userNotFound() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class,
                () -> {
                    userService.findById(1L);
                });
        Assertions.assertTrue(exception.getMessage().contains("não encontrado"));
    }

    @Test
    void invalidUser() {
        User user = new User(null, "Leon", LocalDate.of(1994, Month.MARCH, 19));
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        GenericException exception = Assertions.assertThrows(GenericException.class,
                () -> {
                    userService.findById(1L);
                });
        Assertions.assertTrue(exception.getMessage().contains("Usuário Inválido"));
    }

}
