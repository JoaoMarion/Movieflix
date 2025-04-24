package br.com.movieflix.movieflix.controller;

import br.com.movieflix.movieflix.controller.request.RoleRequest;
import br.com.movieflix.movieflix.controller.response.RoleResponse;
import br.com.movieflix.movieflix.entity.Role;
import br.com.movieflix.movieflix.mapper.RoleMapper;
import br.com.movieflix.movieflix.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movieflix/roles")
public class RoleController {
    private final RoleService roleService;

    @PostMapping("/create")
    public ResponseEntity<RoleResponse> save(@Valid @RequestBody RoleRequest roleRequest){
        Role newRole = RoleMapper.toRole(roleRequest);
        Role savedRole = roleService.save(newRole);
        return ResponseEntity.status(HttpStatus.CREATED).body(RoleMapper.toRoleResponse(savedRole));
    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAll(){
        return ResponseEntity.ok(
                roleService.findAll().stream()
                        .map(RoleMapper::toRoleResponse)
                        .toList()
        );
    }
}
