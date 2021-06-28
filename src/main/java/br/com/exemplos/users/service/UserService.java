package br.com.exemplos.users.service;

import br.com.exemplos.users.exception.EntityNotFoundException;
import br.com.exemplos.users.exception.GenericException;
import br.com.exemplos.users.model.User;
import br.com.exemplos.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> userDataBase = userRepository.findById(id);
        if(!userDataBase.isPresent())
           throw new EntityNotFoundException(User.class);

        if(userDataBase.get().getId() == null || userDataBase.get().getName() == null || userDataBase.get().getBirthday() == null)
            throw new GenericException("Usuário Inválido");

        return userDataBase.get();
    }
}
