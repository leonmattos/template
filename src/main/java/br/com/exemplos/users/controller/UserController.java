package br.com.exemplos.users.controller;

import br.com.exemplos.users.exception.EntityNotFoundException;
import br.com.exemplos.users.model.User;
import br.com.exemplos.users.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
@Api(tags = "Usuários")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "Listar usuários", nickname = "getAllUsers", notes = "Resgata todos os usuários")
    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @ApiOperation(value = "Busca usuário pelo ID", nickname = "getById", notes = "Busca usuário pelo ID")
    @GetMapping("/{id}")
    public User findById(
            @ApiParam(value = "ID do usuário", required = true) @PathVariable Long id) {
        return userService.findById(id);
    }

    @ApiOperation(value = "Salva usuário", nickname = "saveUsers", notes = "Persiste usuário")
    @PostMapping
    public User save(@RequestBody @Valid User user) {
        return userService.save(user);
    }
}
