package com.tony.user_service.service;

import com.tony.user_service.dto.PhoneDTO;
import com.tony.user_service.dto.UserRequestDTO;
import com.tony.user_service.dto.UserResponseDTO;
import com.tony.user_service.model.Phone;
import com.tony.user_service.model.User;
import com.tony.user_service.repository.UserRepository;
import com.tony.user_service.security.JwtService; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("UserService - Test UserService")
public class UserServiceTest {
    // 1. Mocks: Simulam as dependências externas da classe UserService
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService; // Nome atualizado para JwtService

    // 2. Injecao: Cria uma instância real de UserService e injeta os Mocks acima
    @InjectMocks
    private UserService userService;

    private UserRequestDTO validRequest;
    private User savedUser;
    private User anotherUser;
    private final String MOCK_ENCODED_PASSWORD = "hashedPasswordMock";
    private final String MOCK_JWT = "mock.jwt.token";
    private final UUID MOCK_USER_ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {

        validRequest = new UserRequestDTO();
        validRequest.setName("Tony Garcia");
        validRequest.setEmail("tony.garcia@test.com");
        validRequest.setPassword("securePassword123");
        validRequest.setPhones(List.of(new PhoneDTO("123456789", "91", "55")));

        savedUser = new User();
        savedUser.setId(MOCK_USER_ID);
        savedUser.setName(validRequest.getName());
        savedUser.setEmail(validRequest.getEmail());
        savedUser.setPassword(MOCK_ENCODED_PASSWORD);
        savedUser.setToken(MOCK_JWT);
        savedUser.setCreated(LocalDateTime.now().minusMinutes(5));
        savedUser.setModified(LocalDateTime.now().minusMinutes(5));
        savedUser.setLastLogin(LocalDateTime.now());
        savedUser.setActive(true);
         
        Phone phone = new Phone();
        phone.setCityCode("91");
        phone.setCountryCode("55");
        phone.setNumber("123456789");
        phone.setUser(savedUser);
        ArrayList<Phone> phones = new ArrayList<Phone>();
        phones.add(phone);
        savedUser.setPhones(phones);

        anotherUser = new User("Otro usuario", "otro@test.com", "otrapass");
        anotherUser.setId(UUID.randomUUID());
    }

  

    @Test
    @DisplayName("1. Creacion de usuario y generacion de token jwt exitoso")
    void save_Success_UserCreatedAndTokenGenerated() {
  
        when(userRepository.findByEmail(validRequest.getEmail())).thenReturn(Optional.empty());
      
        when(passwordEncoder.encode(validRequest.getPassword())).thenReturn(MOCK_ENCODED_PASSWORD);

        when(jwtService.generateJwtToken(any(UUID.class))).thenReturn(MOCK_JWT);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User resultUser = userService.save(validRequest);

        assertNotNull(resultUser);
        assertEquals(MOCK_ENCODED_PASSWORD, resultUser.getPassword());
        assertEquals(MOCK_JWT, resultUser.getToken());
        assertFalse(resultUser.getPhones().isEmpty());
        
        verify(userRepository, times(1)).findByEmail(validRequest.getEmail());
        verify(passwordEncoder, times(1)).encode(validRequest.getPassword());
        verify(jwtService, times(1)).generateJwtToken(any(UUID.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("2. Error, El correo ya está registrado")
    void save_Failure_EmailAlreadyExistsThrowsException() {
        when(userRepository.findByEmail(validRequest.getEmail())).thenReturn(Optional.of(new User()));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.save(validRequest));

        assertEquals("El correo ya está registrado", exception.getMessage());

        verify(passwordEncoder, never()).encode(anyString());
        verify(jwtService, never()).generateJwtToken(any(UUID.class));
        verify(userRepository, never()).save(any(User.class));
    }
    


    @Test
    @DisplayName("3. Busca usuario registrado por el id del usuario")
    void findById_Success_ReturnsUser() {
        when(userRepository.findById(MOCK_USER_ID)).thenReturn(Optional.of(savedUser));
        User result = userService.findById(MOCK_USER_ID);
        assertNotNull(result);
        assertEquals(MOCK_USER_ID, result.getId());
    }

    @Test
    @DisplayName("4. Id de Usuario no encontrado")
    void findById_Failure_ThrowsException() {
        when(userRepository.findById(MOCK_USER_ID)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.findById(MOCK_USER_ID));
        assertEquals("Usuario no encontrado con ID: " + MOCK_USER_ID, exception.getMessage());
    }

    @Test
    @DisplayName("5. Test de mapeo de json para datos de salida")
    void mapToResponseDTO_Success_MapsAllFields() {
        UserResponseDTO responseDTO = userService.mapToResponseDTO(savedUser);
        assertNotNull(responseDTO);
        assertEquals(savedUser.getId(), responseDTO.getId());
        assertEquals(savedUser.getToken(), responseDTO.getToken());
        assertEquals(savedUser.getEmail(), responseDTO.getEmail());
        assertEquals(savedUser.getCreated(), responseDTO.getCreado());
        assertEquals(savedUser.getLastLogin(), responseDTO.getUltimoLogin());
        assertTrue(responseDTO.getActivo());
    }


    @Test
    @DisplayName("6. Retorna lista todos los usuarios")
    void findAll_Success_ReturnsListOfUsers() {
        List<User> userList = Arrays.asList(savedUser, anotherUser);
        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(savedUser));
        verify(userRepository, times(1)).findAll();
    }



    @Test
    @DisplayName("7. Actualizacion de todos los datos del usuario")
    void update_Success_UpdatesAllFields() {
        User userDetails = new User("Otro Updated", "otro.new@test.com", "otrapassword");

        when(userRepository.findById(MOCK_USER_ID)).thenReturn(Optional.of(savedUser)); 
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.update(MOCK_USER_ID, userDetails);

        assertNotNull(result);
        assertEquals("Otro Updated", result.getName());
        assertEquals("otro.new@test.com", result.getEmail());
        assertEquals("otrapassword", result.getPassword()); 
        
        verify(userRepository, times(1)).findById(MOCK_USER_ID);
        verify(userRepository, times(1)).save(savedUser);
    }


    @Test
    @DisplayName("8. Actualiza solo el nombre del usuario")
    void patch_Success_UpdatesNameOnly() {
     
        User partialDetails = new User();
        partialDetails.setName("Otro nombre patch");
        
        when(userRepository.findById(MOCK_USER_ID)).thenReturn(Optional.of(savedUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.patch(MOCK_USER_ID, partialDetails);

        assertEquals("Otro nombre patch", result.getName());
        assertEquals(savedUser.getEmail(), result.getEmail()); 
        
        verify(userRepository, times(1)).save(savedUser);
    }
    
    @Test
    @DisplayName("9. Actualiza solo el email del usuario")
    void patch_Success_UpdatesEmailOnly() {
        User partialDetails = new User();
        partialDetails.setEmail("otro.correo@test.com");
        
        when(userRepository.findById(MOCK_USER_ID)).thenReturn(Optional.of(savedUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.patch(MOCK_USER_ID, partialDetails);


        assertEquals(savedUser.getName(), result.getName()); 
        assertEquals("otro.correo@test.com", result.getEmail());
        
        verify(userRepository, times(1)).save(savedUser);
    }

    @Test
    @DisplayName("10. Borrar usuario por id")
    void delete_Success_CallsRepositoryDelete() {
        userService.delete(MOCK_USER_ID);
        verify(userRepository, times(1)).deleteById(MOCK_USER_ID);
    }

}


