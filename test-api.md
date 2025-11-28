# API Testing Guide - MolinaChirinosTP

Este documento contiene todas las pruebas realizadas en el proyecto para validar la funcionalidad completa.

## Usuarios de Prueba

Todos los usuarios tienen la contraseña: **Password123**

### Usuarios ROLE_USER:
- juan.perez@test.com
- carlos.rodriguez@test.com
- ana.martinez@test.com
- laura.sanchez@test.com

### Usuarios ROLE_MENTOR:
- maria.gonzalez@test.com
- pedro.lopez@test.com
- miguel.torres@test.com

### Usuario ROLE_ADMIN:
- admin@test.com

## 1. AUTENTICACIÓN

### 1.1 Registro de Usuario (POST /auth/register)
```bash
# Registrar usuario regular (ROLE_USER se asigna automáticamente)
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "primerNombre": "Juan",
    "primerApellido": "Pérez",
    "email": "juan.perez@test.com",
    "password": "Password123",
    "pais": "Peru",
    "ciudad": "Lima",
    "fechaNacimiento": "1995-03-15"
  }'
```

### 1.2 Login (POST /auth/login)
```bash
# Login con usuario ROLE_USER
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "juan.perez@test.com",
    "password": "Password123"
  }'

# Login con usuario ROLE_ADMIN
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@test.com",
    "password": "Password123"
  }'
```

**Respuesta esperada:**
```json
{
  "token": "eyJhbGc...",
  "type": "Bearer"
}
```

---

## 2. GESTIÓN DE USUARIOS

### 2.1 HU04 - Obtener Perfil de Usuario (GET /api/usuarios/{id})
```bash
curl -X GET http://localhost:8080/api/usuarios/1 \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### 2.2 HU04 - Editar Perfil (PUT /api/usuarios/{id})
```bash
curl -X PUT http://localhost:8080/api/usuarios/1 \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "primerNombre": "Juan Actualizado",
    "descripcion": "Desarrollador Full Stack con experiencia en Spring Boot"
  }'
```

### 2.3 HU08 - Buscar Usuarios (GET /api/usuarios/buscar)
```bash
curl -X GET "http://localhost:8080/api/usuarios/buscar?query=juan" \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### 2.4 Obtener Todos los Usuarios - Solo ADMIN (GET /api/usuarios)
```bash
curl -X GET http://localhost:8080/api/usuarios \
  -H "Authorization: Bearer ADMIN_TOKEN"
```

### 2.5 Eliminar Usuario - Solo ADMIN (DELETE /api/usuarios/{id})
```bash
curl -X DELETE http://localhost:8080/api/usuarios/5 \
  -H "Authorization: Bearer ADMIN_TOKEN"
```

---

## 3. NETWORKING / CONEXIONES (HU05-HU09)

### 3.1 HU05 - Enviar Solicitud de Conexión (POST /api/conexiones/enviar)
```bash
curl -X POST http://localhost:8080/api/conexiones/enviar \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "solicitanteId": 1,
    "receptorId": 2
  }'
```

### 3.2 HU06 - Aceptar Solicitud (PUT /api/conexiones/{id}/aceptar)
```bash
curl -X PUT http://localhost:8080/api/conexiones/1/aceptar \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### 3.3 HU06 - Rechazar Solicitud (PUT /api/conexiones/{id}/rechazar)
```bash
curl -X PUT http://localhost:8080/api/conexiones/1/rechazar \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### 3.4 HU07 - Ver Mis Contactos (GET /api/conexiones/contactos/{usuarioId})
```bash
curl -X GET http://localhost:8080/api/conexiones/contactos/1 \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### 3.5 HU09 - Ver Sugerencias de Conexión (GET /api/conexiones/sugerencias/{usuarioId})
```bash
curl -X GET http://localhost:8080/api/conexiones/sugerencias/1 \
  -H "Authorization: Bearer YOUR_TOKEN"
```

---

## 4. MENSAJERÍA (HU19)

### 4.1 Enviar Mensaje (POST /api/mensajes/enviar)
```bash
curl -X POST http://localhost:8080/api/mensajes/enviar \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "emisorId": 1,
    "receptorId": 2,
    "contenido": "Hola! Me gustaría conectar contigo"
  }'
```

### 4.2 Ver Conversación (GET /api/mensajes/conversacion)
```bash
curl -X GET "http://localhost:8080/api/mensajes/conversacion?usuario1Id=1&usuario2Id=2" \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### 4.3 Marcar Mensaje como Leído (PUT /api/mensajes/{id}/leer)
```bash
curl -X PUT http://localhost:8080/api/mensajes/1/leer \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### 4.4 Obtener Mensajes No Leídos (GET /api/mensajes/no-leidos/{usuarioId})
```bash
curl -X GET http://localhost:8080/api/mensajes/no-leidos/1 \
  -H "Authorization: Bearer YOUR_TOKEN"
```

---

## 5. FEED / PUBLICACIONES (HU20-HU22)

### 5.1 HU20 - Crear Publicación (POST /api/publicaciones)
```bash
curl -X POST http://localhost:8080/api/publicaciones \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "autorId": 1,
    "contenido": "¡Acabo de terminar mi primer proyecto en Spring Boot! #Java #SpringBoot",
    "imagen": "https://example.com/image.jpg"
  }'
```

### 5.2 Ver Feed Completo (GET /api/publicaciones/feed)
```bash
curl -X GET http://localhost:8080/api/publicaciones/feed \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### 5.3 Ver Publicaciones de un Usuario (GET /api/publicaciones/usuario/{usuarioId})
```bash
curl -X GET http://localhost:8080/api/publicaciones/usuario/1 \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### 5.4 HU21 - Agregar Comentario (POST /api/publicaciones/{publicacionId}/comentarios)
```bash
curl -X POST http://localhost:8080/api/publicaciones/1/comentarios \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "autorId": 2,
    "contenido": "¡Excelente trabajo! Felicitaciones"
  }'
```

### 5.5 HU22 - Dar Reacción (POST /api/publicaciones/{publicacionId}/reacciones)
```bash
curl -X POST http://localhost:8080/api/publicaciones/1/reacciones \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "usuarioId": 2,
    "tipo": "ME_GUSTA"
  }'
```

### 5.6 Quitar Reacción (DELETE /api/publicaciones/{publicacionId}/reacciones/{usuarioId})
```bash
curl -X DELETE http://localhost:8080/api/publicaciones/1/reacciones/2 \
  -H "Authorization: Bearer YOUR_TOKEN"
```

---

## 6. OPORTUNIDADES (HU15-HU18)

### 6.1 HU15 - Obtener Empleos (GET /api/oportunidades/empleos)
```bash
curl -X GET http://localhost:8080/api/oportunidades/empleos \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### 6.2 HU16 - Obtener Pasantías (GET /api/oportunidades/pasantias)
```bash
curl -X GET http://localhost:8080/api/oportunidades/pasantias \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### 6.3 HU17 - Obtener Talleres (GET /api/oportunidades/talleres)
```bash
curl -X GET http://localhost:8080/api/oportunidades/talleres \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### 6.4 HU18 - Obtener Eventos (GET /api/oportunidades/eventos)
```bash
curl -X GET http://localhost:8080/api/oportunidades/eventos \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### 6.5 Obtener Todas las Oportunidades (GET /api/oportunidades)
```bash
curl -X GET http://localhost:8080/api/oportunidades \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### 6.6 Crear Oportunidad - Solo ADMIN (POST /api/oportunidades)
```bash
curl -X POST http://localhost:8080/api/oportunidades \
  -H "Authorization: Bearer ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "tipo": "EMPLEO",
    "titulo": "Desarrollador Backend Senior",
    "descripcion": "Buscamos desarrollador con experiencia en Spring Boot",
    "empresa": "Tech Corp",
    "fechaInicio": "2025-03-01",
    "activo": true
  }'
```

### 6.7 Actualizar Oportunidad - Solo ADMIN (PUT /api/oportunidades/{id})
```bash
curl -X PUT http://localhost:8080/api/oportunidades/1 \
  -H "Authorization: Bearer ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Desarrollador Backend Senior - ACTUALIZADO",
    "activo": true
  }'
```

### 6.8 Desactivar Oportunidad - Solo ADMIN (DELETE /api/oportunidades/{id})
```bash
curl -X DELETE http://localhost:8080/api/oportunidades/1 \
  -H "Authorization: Bearer ADMIN_TOKEN"
```

---

## 7. GAMIFICACIÓN (HU25)

### 7.1 Obtener Puntuación de Usuario (GET /api/puntuaciones/{usuarioId})
```bash
curl -X GET http://localhost:8080/api/puntuaciones/1 \
  -H "Authorization: Bearer YOUR_TOKEN"
```

**Respuesta esperada:**
```json
{
  "idPuntuacion": 1,
  "usuario": { ... },
  "puntosPublicaciones": 10,
  "puntosConexiones": 5,
  "puntosMentorias": 0,
  "puntosComentarios": 5,
  "totalPuntos": 20
}
```

---

## 8. PRUEBAS DE SEGURIDAD

### 8.1 Acceso Sin Token (Debe Retornar 401 Unauthorized)
```bash
curl -X GET http://localhost:8080/api/usuarios/1
```

### 8.2 Acceso con Token Inválido (Debe Retornar 401 Unauthorized)
```bash
curl -X GET http://localhost:8080/api/usuarios/1 \
  -H "Authorization: Bearer invalid_token_here"
```

### 8.3 Acceso ADMIN con Usuario Regular (Debe Retornar 403 Forbidden)
```bash
# Login con usuario regular
TOKEN=$(curl -s -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"juan.perez@test.com","password":"Password123"}' \
  | grep -o '"token":"[^"]*' | cut -d'"' -f4)

# Intentar acceder a endpoint de ADMIN
curl -X GET http://localhost:8080/api/usuarios \
  -H "Authorization: Bearer $TOKEN"
```

---

## RESULTADOS ESPERADOS

### Funcionalidades Implementadas:
- ✅ HU01-HU03: Autenticación y registro
- ✅ HU04: Gestión de perfil de usuario
- ✅ HU05-HU09: Sistema de networking/conexiones
- ✅ HU08: Búsqueda de usuarios
- ✅ HU15-HU18: Recomendaciones de oportunidades
- ✅ HU19: Sistema de mensajería
- ✅ HU20-HU22: Feed de publicaciones con comentarios y reacciones
- ✅ HU25: Sistema de gamificación

### Seguridad:
- ✅ JWT con HS512 (512-bit key)
- ✅ BCrypt password hashing
- ✅ Role-Based Access Control (RBAC)
- ✅ @PreAuthorize en todos los endpoints protegidos
- ✅ STATELESS session management

### Base de Datos:
- ✅ PostgreSQL 17.6
- ✅ 14 tablas creadas automáticamente
- ✅ 16 JPA repositories
- ✅ Relaciones ManyToMany, OneToMany configuradas

---

## NOTAS PARA EL EQUIPO

1. **Puerto**: La aplicación corre en `http://localhost:8080`
2. **Base de Datos**: PostgreSQL en `jdbc:postgresql://localhost:5432/molinachirinosdb`
3. **Swagger UI**: Disponible en `http://localhost:8080/swagger-ui/index.html`
4. **Actuator**: Disponible en `http://localhost:8080/actuator`

### Para ejecutar el proyecto:
```bash
cd "C:\Users\Rafael\OneDrive - Universidad Peruana de Ciencias\Desktop\tf-simon\MolinaChirinosTP (5)\MolinaChirinosTP"
./mvnw spring-boot:run
```

### Para compilar:
```bash
./mvnw clean compile -DskipTests
```

### Crear usuarios de prueba vía API:
Usar los endpoints de registro para crear usuarios con diferentes roles. Por defecto, todos los usuarios registrados obtienen ROLE_USER.
