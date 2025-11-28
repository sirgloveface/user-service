package com.tony.user_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tony.user_service.dto.PhoneDTO;
import com.tony.user_service.dto.UserRequestDTO;
import com.tony.user_service.dto.UserResponseDTO;
import com.tony.user_service.model.Phone;
import com.tony.user_service.model.User;
import com.tony.user_service.repository.UserRepository;
import com.tony.user_service.security.JwtService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID; 

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired private JwtService jwtService;
    @Autowired private PasswordEncoder passwordEncoder; 

    public User save(UserRequestDTO userDto) {

        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        User user = new User(userDto.getName(), userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()));
        
       //String token = jwtService.generateToken(user.getId(), user.getEmail());
        String token = jwtService.generateJwtToken(user.getId());
        user.setToken(token);
        LocalDateTime now = LocalDateTime.now();
        user.setLastLogin(now); 

        if (userDto.getPhones() != null) {
            for (PhoneDTO phoneDTO : userDto.getPhones()) {
                Phone phone = new Phone(
                    phoneDTO.getNumber(), 
                    phoneDTO.getCityCode(), 
                    phoneDTO.getCountryCode()
                );
                user.addPhone(phone); 
            }
        }
        
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }


    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    public User update(UUID id, User userDetails) {
        User existingUser = findById(id);
        
        existingUser.setName(userDetails.getName());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setPassword(userDetails.getPassword());
        
        return userRepository.save(existingUser);
    }

    public User patch(UUID id, User partialDetails) {
        User existingUser = findById(id);
        
        if (partialDetails.getName() != null) {
            existingUser.setName(partialDetails.getName());
        }
        if (partialDetails.getEmail() != null) {
            existingUser.setEmail(partialDetails.getEmail());
        }

        return userRepository.save(existingUser);
    }
    

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    public UserResponseDTO mapToResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .creado(user.getCreated())
                .modificado(user.getModified())
                .ultimoLogin(user.getLastLogin())
                .token(user.getToken())
                .activo(user.getActive())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}