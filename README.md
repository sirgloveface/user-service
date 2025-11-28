# API de Gestión de Usuarios (con JWT)

Esta es una API REST desarrollada en Spring Boot 3 y Java 21 para gestionar el registro y la autenticación de usuarios, utilizando JWT (JSON Web Token) y Spring Security, Cumpliendo con lo solicitado en el desafio para la entrevista 

La aplicación utiliza cifrado BCrypt para contraseñas y tiene Swagger (OpenAPI) integrado para la documentación y pruebas.

## Requisitos Previos

1.  Java 21 (JDK)
2.  Maven 3.x
3.  Docker (Opcional, pero recomendado para la ejecución en contenedores)

## 🚀 Cómo Ejecutar

### Opción 1: Ejecución Estándar (Sin Docker)

1.  **Clonar el repositorio:**
    ```bash
    git clone <URL_DE_TU_REPOSITORIO>
    cd user-service
    ```

2.  **Compilar el proyecto y ejecutar las pruebas:**
    ```bash
    mvn clean install
    ```
    *Este paso genera el archivo JAR ejecutable en el directorio target/*

3.  **Ejecutar la aplicación:**
    ```bash
    java -jar target/user-service-0.0.1-SNAPSHOT.jar
    ```
    *(La versión del JAR puede cambiar dependiendo de tu pom.xml)*

### Opción 2: Ejecución con Docker (Recomendado)

1.  **Compilar el proyecto y crear el archivo JAR:**
    ```bash
    mvn clean package -DskipTests
    ```
nota: se usa -DskipTests para saltar los test


2.  **Construir la imagen Docker:**
    ```bash
    docker build -t user-service:latest .
    ```

3.  **Ejecutar el contenedor:**
    ```bash
    docker run -p 8080:8080 --name user-service user-service:latest
    ```
    *La aplicación estará disponible en http://localhost:8080*

## 🔒 Endpoints Principales

| Método | URL | Descripción | Seguridad |
| :--- | :--- | :--- | :--- |
| **POST** | /api/users | **Registro** de nuevo usuario. Devuelve el JWT. | Pública (permitAll()) |
| **GET** | /api/users | Consulta la lista de usuarios. | **Protegida** (Requiere JWT) |
| **GET** | /api/users/{id} | Busca usuario por ID. | **Protegida** (Requiere JWT) |
| **PUT/PATCH/DELETE** | /api/users/{id} | Actualización y eliminación de usuario. | **Protegida** (Requiere JWT) |


## Ejemplos de curl


# Creacion de Usario

```bash 
curl --location 'http://localhost:8080/api/users' \
--header 'Content-Type: application/json' \
--data-raw '{
    "nombre": "João Silva tony",
    "correo": "joao.silva1@exemplo.com",
    "contrasena": "SenhaSegura123",
    "telefonos": [
        {
            "numero": "987654321",
            "codigoCiudad": "11",
            "codigoPais": "55"
        },
        {
            "numero": "55512345",
            "codigoCiudad": "21",
            "codigoPais": "55"
        }
    ]
}'
```

# Listar todos los Usarios

```bash 
curl --location 'http://localhost:8080/api/users' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMjg1ZDcyMi1jNTFmLTQ0MTktOWNhZi1iM2I4NmU1MTZiZDciLCJpYXQiOjE3NjQyNjg4MTEsImV4cCI6MTc2NDM1NTIxMX0.AGrhwT6wCxofv51mIqsx26K_3GxDI-XtkHXY887J_CSrmIsSAS9vyXeGXuC3twpydv6l9hkWLWmlhEJtED-fgQ'
```

# Obtener usuario por Id

```bash
curl --location 'http://localhost:8080/api/users/0285d722-c51f-4419-9caf-b3b86e516bd7' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMjg1ZDcyMi1jNTFmLTQ0MTktOWNhZi1iM2I4NmU1MTZiZDciLCJpYXQiOjE3NjQyNjg4MTEsImV4cCI6MTc2NDM1NTIxMX0.AGrhwT6wCxofv51mIqsx26K_3GxDI-XtkHXY887J_CSrmIsSAS9vyXeGXuC3twpydv6l9hkWLWmlhEJtED-fgQ'
```

# Actualizar campos total
```bash 
curl --location --request PUT 'http://localhost:8080/api/users/ddaa50d1-2984-4f10-b0dc-def04026ea66' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMjg1ZDcyMi1jNTFmLTQ0MTktOWNhZi1iM2I4NmU1MTZiZDciLCJpYXQiOjE3NjQyNjg4MTEsImV4cCI6MTc2NDM1NTIxMX0.AGrhwT6wCxofv51mIqsx26K_3GxDI-XtkHXY887J_CSrmIsSAS9vyXeGXuC3twpydv6l9hkWLWmlhEJtED-fgQ' \
--data-raw '{
    "firstName": "Elon",
    "lastName": "Musk",
    "email": "elon.nuevo@tesla.com",
    "password": "senhaAtualizadaForte",
    "phones": [
        {
            "ddi": "1",
            "ddd": "310",
            "number": "555-0101"
        },
        {
            "ddi": "81",
            "ddd": "20",
            "number": "9999-8888"
        }
    ]
}'
```

# Actualizar solo el correo
```bash 
curl --location --request PATCH 'http://localhost:8080/api/users/ddaa50d1-2984-4f10-b0dc-def04026ea66' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMjg1ZDcyMi1jNTFmLTQ0MTktOWNhZi1iM2I4NmU1MTZiZDciLCJpYXQiOjE3NjQyNjg4MTEsImV4cCI6MTc2NDM1NTIxMX0.AGrhwT6wCxofv51mIqsx26K_3GxDI-XtkHXY887J_CSrmIsSAS9vyXeGXuC3twpydv6l9hkWLWmlhEJtED-fgQ' \
--data-raw '{
    "email": "nuevo.email.actualizado@empresa.com",
    "phones": [
        {
            "ddi": "52",
            "ddd": "55",
            "number": "91234-5678"
        },
        {
            "ddi": "1",
            "ddd": "212",
            "number": "444-9999"
        }
    ]
}'
```

# Eliminar usuario

```bash
curl --location --request DELETE 'http://localhost:8080/api/users/ddaa50d1-2984-4f10-b0dc-def04026ea66' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMjg1ZDcyMi1jNTFmLTQ0MTktOWNhZi1iM2I4NmU1MTZiZDciLCJpYXQiOjE3NjQyNjg4MTEsImV4cCI6MTc2NDM1NTIxMX0.AGrhwT6wCxofv51mIqsx26K_3GxDI-XtkHXY887J_CSrmIsSAS9vyXeGXuC3twpydv6l9hkWLWmlhEJtED-fgQ'
```


## 📖 Documentación (Swagger UI)

Una vez que la aplicación esté en funcionamiento, accede a la documentación interactiva de la API:

➡️ [**http://localhost:8080/swagger-ui.html**](http://localhost:8080/swagger-ui.html)

### Cómo Probar Rutas Protegidas en Swagger

1.  Usa el endpoint **POST /api/users** para registrarte y obtener un token JWT.
2.  Haz clic en el botón **"Authorize"** (Autorizar) en la parte superior de la página de Swagger.
3.  Inserta el token JWT en el campo BearerAuth (formato: Bearer <TU_TOKEN>).
4.  Ahora, puedes probar las rutas **Protegidas** (aquellas que tienen la anotación BearerAuth).
````