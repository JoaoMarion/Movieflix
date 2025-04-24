package br.com.movieflix.movieflix.service;

import br.com.movieflix.movieflix.controller.response.RoleResponse;
import br.com.movieflix.movieflix.entity.Role;
import br.com.movieflix.movieflix.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role save(Role role){
       return roleRepository.save(role);
    }
    public List<Role> findAll(){
        return roleRepository.findAll();
    }

}
