package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepo userRepo;

    @GetMapping("/all")
    public ResponseEntity<List<Users>> getAllUserHandler(){
        List<Users> usersList = userRepo.findAll();
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Users> createUserHandler(@RequestBody Users users){
        Users user = userRepo.save(users);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserHandler(@RequestBody Users req, @PathVariable Long id) {
        Optional<Users> userOpt = userRepo.findById(id);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } else {
            Users user = userOpt.get();
            if (req.getFirstName() != null) {
                user.setFirstName(req.getFirstName());
            }
            if (req.getLastName() != null) {
                user.setLastName(req.getLastName());
            }
            if (req.getEmail() != null) {
                user.setEmail(req.getEmail());
            }
            if (req.getPassword() != null) {
                user.setPassword(req.getPassword());
            }
            userRepo.save(user);
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserHandler(@PathVariable Long id){
        Optional<Users> userOpt = userRepo.findById(id);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } else {
            userRepo.deleteById(id);
            return new ResponseEntity<>("delete user successfully", HttpStatus.OK);
        }
    }
}
