// package com.tony.user_service.control;

// // import com.fasterxml.jackson.databind.ObjectMapper;
// // import com.tony.user_service.dto.UserRequestDTO;
// // import com.tony.user_service.dto.UserResponseDTO;
// // import com.tony.user_service.model.User;
// // import com.tony.user_service.service.UserService;
// // import org.junit.jupiter.api.BeforeEach;
// // import org.junit.jupiter.api.DisplayName;
// // import org.junit.jupiter.api.Test;
// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
// // import org.springframework.http.MediaType;
// // import org.springframework.test.context.bean.override.mockito.MockitoBean;
// // import org.springframework.test.web.servlet.MockMvc;

// // import java.time.LocalDateTime;
// // import java.util.Arrays;
// // import java.util.List;
// // import java.util.UUID;

// // import static org.mockito.ArgumentMatchers.*;
// // import java.util.UUID;

// // import static org.mockito.ArgumentMatchers.any;
// // import static org.mockito.Mockito.*;
// // import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// // import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


// // @WebMvcTest(UserController.class)
// // @DisplayName("UserController - Test UserController")
// // public class UserControllerTest {

// //     @Autowired
// //     private MockMvc mockMvc;

// //     @Autowired
// //     private ObjectMapper objectMapper;

// //     @MockitoBean
// //     private UserService userService;

// //     private User validUser;
// //     private UserRequestDTO validRequestDTO;
// //     private UserResponseDTO expectedResponseDTO;
// //     private final UUID MOCK_USER_ID = UUID.randomUUID();

// //     @BeforeEach
// //     void setUp() {
// //         validRequestDTO = new UserRequestDTO();
// //         validRequestDTO.setName("Test User");
// //         validRequestDTO.setEmail("test@example.com");
// //         validRequestDTO.setPassword("123");
// //         validUser = new User();
// //         validUser.setId(MOCK_USER_ID);
// //         validUser.setName(validRequestDTO.getName());
// //         validUser.setEmail(validRequestDTO.getEmail());
// //         validUser.setPassword("1234");
// //         validUser.setToken("tokentoken");
// //         validUser.setCreated(LocalDateTime.now());
// //         validUser.setLastLogin(LocalDateTime.now());

// //         expectedResponseDTO = UserResponseDTO.builder()
// //                 .id(validUser.getId())
// //                 .name(validUser.getName())
// //                 .email(validUser.getEmail())
// //                 .token(validUser.getToken())
// //                 .creado(validUser.getCreated())
// //                 .ultimoLogin(validUser.getLastLogin())
// //                 .activo(true)
// //                 .build();
// //     }


// //     @Test
// //     @DisplayName("1. POST /api/users: Creacion de usuario, retorna 201 CREATED")
// //     void createUser_Success_Returns201() throws Exception {
  
// //         when(userService.save(any(UserRequestDTO.class))).thenReturn(validUser);
// //         when(userService.mapToResponseDTO(any(User.class))).thenReturn(expectedResponseDTO);
// //         mockMvc.perform(post("/api/users")
// //                 .contentType(MediaType.APPLICATION_JSON)
// //                 .content(objectMapper.writeValueAsString(validRequestDTO))) 
                
// //                 .andExpect(status().isCreated())
// //                 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
// //                 .andExpect(jsonPath("$.id").value(MOCK_USER_ID.toString()))
// //                 .andExpect(jsonPath("$.email").value("test@example.com"));

// //         verify(userService, times(1)).save(any(UserRequestDTO.class));
// //         verify(userService, times(1)).mapToResponseDTO(any(User.class));
// //     }


// //     @Test
// //     @DisplayName("2. GET /api/users: Retorna lista de usuarios, status 200 OK")
// //     void getAllUsers_Success_Returns200() throws Exception {
// //         User user2 = new User();
// //         user2.setId(UUID.randomUUID());
// //         user2.setName("Usuariotest");

// //         List<User> userList = Arrays.asList(validUser, user2);
        
// //         when(userService.findAll()).thenReturn(userList);

// //         mockMvc.perform(get("/api/users")
// //                 .contentType(MediaType.APPLICATION_JSON))
                
// //                 .andExpect(status().isOk())
// //                 .andExpect(jsonPath("$.length()").value(2))
// //                 .andExpect(jsonPath("$[0].name").value(validUser.getName()));

// //         verify(userService, times(1)).findAll();
// //     }


// //     @Test
// //     @DisplayName("3. GET /api/users/{id}: Retorna el usuario por el id , status 200 OK")
// //     void getUserById_Success_Returns200() throws Exception {
// //         when(userService.findById(MOCK_USER_ID)).thenReturn(validUser);

// //         mockMvc.perform(get("/api/users/{id}", MOCK_USER_ID)
// //                 .contentType(MediaType.APPLICATION_JSON))
                
// //                 .andExpect(status().isOk())
// //                 .andExpect(jsonPath("$.id").value(MOCK_USER_ID.toString()))
// //                 .andExpect(jsonPath("$.email").value(validUser.getEmail()));

// //         verify(userService, times(1)).findById(MOCK_USER_ID);
// //     }
    

// //     @Test
// //     @DisplayName("4. GET /api/users/{id}: Id no existe, retorna 404 NOT FOUND")
// //     void getUserById_NotFound_Returns404() throws Exception {
// //         when(userService.findById(any(UUID.class))).thenThrow(new RuntimeException("Usuario no encontrado"));

// //         mockMvc.perform(get("/api/users/{id}", UUID.randomUUID())
// //                 .contentType(MediaType.APPLICATION_JSON))
                
// //                 .andExpect(status().isNotFound()); 
// //         verify(userService, times(1)).findById(any(UUID.class));
// //     }


// //     @Test
// //     @DisplayName("5. PUT /api/users/{id}: Actualiza todos los datos del usuario, status 200 OK")
// //     void updateUser_Success_Returns200() throws Exception {
// //         User updatedDetails = new User("Nombre test", "testtest@email.com", "testtestnew");
        
// //         when(userService.update(eq(MOCK_USER_ID), any(User.class))).thenReturn(updatedDetails);

// //         mockMvc.perform(put("/api/users/{id}", MOCK_USER_ID)
// //                 .contentType(MediaType.APPLICATION_JSON)
// //                 .content(objectMapper.writeValueAsString(updatedDetails)))
                
// //                 .andExpect(status().isOk())
// //                 .andExpect(jsonPath("$.name").value("Nombre test"));

// //         verify(userService, times(1)).update(eq(MOCK_USER_ID), any(User.class));
// //     }



// //     @Test
// //     @DisplayName("6. PATCH /api/users/{id}: Actualizacion de solo el nombre, status 200 OK")
// //     void patchUser_Success_Returns200() throws Exception {
// //         User partialDetails = new User();
// //         partialDetails.setName("Nuevo nombre");
        
// //         User patchedUser = new User("Nuevo nombre", validUser.getEmail(), validUser.getPassword());
        
// //         when(userService.patch(eq(MOCK_USER_ID), any(User.class))).thenReturn(patchedUser);

// //         mockMvc.perform(patch("/api/users/{id}", MOCK_USER_ID)
// //                 .contentType(MediaType.APPLICATION_JSON)
// //                 .content(objectMapper.writeValueAsString(partialDetails)))  
// //                 .andExpect(status().isOk())
// //                 .andExpect(jsonPath("$.name").value("Nuevo nombre"));

// //         verify(userService, times(1)).patch(eq(MOCK_USER_ID), any(User.class));
// //     }

// //     @Test
// //     @DisplayName("7. DELETE /api/users/{id}: Borrar Usuario, status 204 NO CONTENT")
// //     void deleteUser_Success_Returns204() throws Exception {
// //         doNothing().when(userService).delete(MOCK_USER_ID);

// //         mockMvc.perform(delete("/api/users/{id}", MOCK_USER_ID))
// //                 .andExpect(status().isNoContent()) 
// //                 .andExpect(content().string("")); 
                
// //         verify(userService, times(1)).delete(MOCK_USER_ID);
// //     }
// // }









// // package com.tony.user_service.control;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.tony.user_service.dto.UserRequestDTO;
// import com.tony.user_service.dto.UserResponseDTO;
// import com.tony.user_service.model.User;
// import com.tony.user_service.service.UserService;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith; 
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;

// import java.time.LocalDateTime;
// import java.util.Arrays;
// import java.util.List;
// import java.util.UUID;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


// @ExtendWith(MockitoExtension.class)
// @DisplayName("UserController - Testes de Endpoints REST (Setup Manual)")
// public class UserControllerTest {

//     // MockMvc é configurado manualmente no método setUp()
//     private MockMvc mockMvc;

//     // 1. Mocks: Simula o Service
//     @Mock
//     private UserService userService;

//     // 2. Injeção: Cria uma instância real de UserController e injeta o Mock do UserService
//     @InjectMocks
//     private UserController userController;

//     // ObjectMapper para converter objetos Java em JSON
//     private ObjectMapper objectMapper = new ObjectMapper();

//     private User validUser;
//     private UserRequestDTO validRequestDTO;
//     private UserResponseDTO expectedResponseDTO;
//     private final UUID MOCK_USER_ID = UUID.randomUUID();

//     @BeforeEach
//     void setUp() {

//         mockMvc = MockMvcBuilders.standaloneSetup(userController)
//                 .build();
        
//         validRequestDTO = new UserRequestDTO();
//         validRequestDTO.setName("Test User");
//         validRequestDTO.setEmail("test@example.com");
//         validRequestDTO.setPassword("P@sswOrd123");

//         validUser = new User();
//         validUser.setId(MOCK_USER_ID);
//         validUser.setName(validRequestDTO.getName());
//         validUser.setEmail(validRequestDTO.getEmail());
//         validUser.setPassword("encodedPassword");
//         validUser.setToken("mock.jwt.token");
//         validUser.setCreated(LocalDateTime.now());
//         validUser.setLastLogin(LocalDateTime.now());

//         expectedResponseDTO = UserResponseDTO.builder()
//                 .id(validUser.getId())
//                 .name(validUser.getName())
//                 .email(validUser.getEmail())
//                 .token(validUser.getToken())
//                 .creado(validUser.getCreated())
//                 .ultimoLogin(validUser.getLastLogin())
//                 .activo(true)
//                 .build();
//     }


//     @Test
//     @DisplayName("1. POST /api/users: Crear usuario, retorna 201 CREATED")
//     void createUser_Success_Returns201() throws Exception {
//         when(userService.save(any(UserRequestDTO.class))).thenReturn(validUser);
//         when(userService.mapToResponseDTO(any(User.class))).thenReturn(expectedResponseDTO);


//         mockMvc.perform(post("/api/users")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(validRequestDTO))) 
//                 .andExpect(status().isCreated()) 
//                 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(jsonPath("$.id").value(MOCK_USER_ID.toString()))
//                 .andExpect(jsonPath("$.email").value("test@example.com"));

//         verify(userService, times(1)).save(any(UserRequestDTO.class));
//         verify(userService, times(1)).mapToResponseDTO(any(User.class));
//     }


//     // @Test
//     // @DisplayName("2. GET /api/users: Retorna lista de usuários, status 200 OK")
//     // void getAllUsers_Success_Returns200() throws Exception {
//     //     User user2 = new User();
//     //     user2.setId(UUID.randomUUID());
//     //     user2.setName("Jane");

//     //     List<User> userList = Arrays.asList(validUser, user2);
        
//     //     when(userService.findAll()).thenReturn(userList);

//     //     mockMvc.perform(get("/api/users")
//     //             .contentType(MediaType.APPLICATION_JSON))
                
//     //             .andExpect(status().isOk())
//     //             .andExpect(jsonPath("$.length()").value(2))
//     //             .andExpect(jsonPath("$[0].name").value(validUser.getName()));

//     //     verify(userService, times(1)).findAll();
//     // }

//     // // --- Testes para GET /api/users/{id} (Busca por ID) ---

//     // @Test
//     // @DisplayName("3. GET /api/users/{id}: Busca por ID bem-sucedida, status 200 OK")
//     // void getUserById_Success_Returns200() throws Exception {
//     //     when(userService.findById(MOCK_USER_ID)).thenReturn(validUser);

//     //     mockMvc.perform(get("/api/users/{id}", MOCK_USER_ID)
//     //             .contentType(MediaType.APPLICATION_JSON))
                
//     //             .andExpect(status().isOk())
//     //             .andExpect(jsonPath("$.id").value(MOCK_USER_ID.toString()))
//     //             .andExpect(jsonPath("$.email").value(validUser.getEmail()));

//     //     verify(userService, times(1)).findById(MOCK_USER_ID);
//     // }
    
//     // // Teste de falha (Assumindo que o Controller não trata, o que resulta em um 500 ou 404)
//     // @Test
//     // @DisplayName("4. GET /api/users/{id}: ID não encontrado, retorna 404 NOT FOUND (ou similar)")
//     // void getUserById_NotFound_Returns404() throws Exception {
//     //     when(userService.findById(any(UUID.class))).thenThrow(new RuntimeException("Usuario no encontrado"));

//     //     mockMvc.perform(get("/api/users/{id}", UUID.randomUUID())
//     //             .contentType(MediaType.APPLICATION_JSON))
                
//     //             .andExpect(status().isNotFound()); 

//     //     verify(userService, times(1)).findById(any(UUID.class));
//     // }


//     // // --- Testes para PUT /api/users/{id} (Atualização Completa) ---

//     // @Test
//     // @DisplayName("5. PUT /api/users/{id}: Atualização bem-sucedida, status 200 OK")
//     // void updateUser_Success_Returns200() throws Exception {
//     //     User updatedDetails = new User("Updated Name", "updated@email.com", "newPassword");
        
//     //     when(userService.update(eq(MOCK_USER_ID), any(User.class))).thenReturn(updatedDetails);

//     //     mockMvc.perform(put("/api/users/{id}", MOCK_USER_ID)
//     //             .contentType(MediaType.APPLICATION_JSON)
//     //             .content(objectMapper.writeValueAsString(updatedDetails)))
                
//     //             .andExpect(status().isOk())
//     //             .andExpect(jsonPath("$.name").value("Updated Name"));

//     //     verify(userService, times(1)).update(eq(MOCK_USER_ID), any(User.class));
//     // }

//     // // --- Testes para PATCH /api/users/{id} (Atualização Parcial) ---

//     // @Test
//     // @DisplayName("6. PATCH /api/users/{id}: Atualização parcial bem-sucedida, status 200 OK")
//     // void patchUser_Success_Returns200() throws Exception {
//     //     User partialDetails = new User();
//     //     partialDetails.setName("Patched Name");
        
//     //     User patchedUser = new User("Patched Name", validUser.getEmail(), validUser.getPassword());
        
//     //     when(userService.patch(eq(MOCK_USER_ID), any(User.class))).thenReturn(patchedUser);

//     //     mockMvc.perform(patch("/api/users/{id}", MOCK_USER_ID)
//     //             .contentType(MediaType.APPLICATION_JSON)
//     //             .content(objectMapper.writeValueAsString(partialDetails)))
                
//     //             .andExpect(status().isOk())
//     //             .andExpect(jsonPath("$.name").value("Patched Name"));

//     //     verify(userService, times(1)).patch(eq(MOCK_USER_ID), any(User.class));
//     // }

//     // // --- Testes para DELETE /api/users/{id} (Exclusão) ---

//     // @Test
//     // @DisplayName("7. DELETE /api/users/{id}: Exclusão bem-sucedida, status 204 NO CONTENT")
//     // void deleteUser_Success_Returns204() throws Exception {
//     //     doNothing().when(userService).delete(MOCK_USER_ID);

//     //     mockMvc.perform(delete("/api/users/{id}", MOCK_USER_ID))
                
//     //             .andExpect(status().isNoContent()) 

//     //             .andExpect(content().string("")); 

//     //     verify(userService, times(1)).delete(MOCK_USER_ID);
//     // }
// }

