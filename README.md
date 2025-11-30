# MolinaChirinosTP - Plataforma de Networking y Mentoría

## 📋 Descripción

Plataforma web completa de networking profesional y mentoría que permite a usuarios conectarse, compartir publicaciones, buscar oportunidades laborales y participar en un sistema de gamificación.

## ✨ Características Principales

- 🔐 **Autenticación JWT** con roles (USER, MENTOR, ADMIN)
- 👥 **Sistema de Networking** - Conexiones entre usuarios
- 💬 **Mensajería Privada** - Chat entre contactos
- 📰 **Feed de Publicaciones** - Con comentarios y reacciones
- 🏢 **Oportunidades** - Empleos, pasantías, talleres y eventos
- 🏆 **Gamificación** - Sistema de puntos por actividades
- 🔍 **Búsqueda de Usuarios** - Por nombre y email

## 🛠️ Tecnologías

- **Backend**: Spring Boot 3.5.6
- **Seguridad**: Spring Security 6.5.5 + JWT
- **Base de Datos**: PostgreSQL 17.6
- **ORM**: Hibernate / JPA
- **Java**: JDK 21

## 📁 Estructura del Proyecto

```
src/main/java/com/upc/molinachirinostp/
├── controller/       # Controllers REST
├── service/          # Lógica de negocio
├── repository/       # JPA Repositories
├── entity/           # Entidades JPA
├── security/         # Configuración de seguridad
├── dto/              # Data Transfer Objects
└── util/             # Utilidades
```

## 🚀 Inicio Rápido

### Prerrequisitos

- Java 21+
- PostgreSQL 17+
- Maven 3+

### Configuración

1. **Crear base de datos**:
```sql
CREATE DATABASE molinachirinosdb;
```

2. **Configurar credenciales** en `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/molinachirinosdb
spring.datasource.username=postgres
spring.datasource.password=admin
```

3. **Ejecutar aplicación**:
```bash
./mvnw spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## 📚 Documentación

- **[test-api.md](test-api.md)** - Guía completa de endpoints y ejemplos de uso
- **[RESULTADOS-PRUEBAS.md](RESULTADOS-PRUEBAS.md)** - Resultados de pruebas y validaciones
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **OpenAPI**: http://localhost:8080/v3/api-docs

## 🔑 Usuarios de Prueba

Puedes crear usuarios mediante el endpoint `/auth/register`. Todos los usuarios nuevos reciben automáticamente el rol `ROLE_USER`.

### Contraseña para pruebas:
```
Password123
```

## 📝 Endpoints Principales

### Autenticación
- `POST /auth/register` - Registro de usuario
- `POST /auth/login` - Inicio de sesión (retorna JWT)

### Usuarios
- `GET /api/usuarios/{id}` - Obtener perfil
- `PUT /api/usuarios/{id}` - Actualizar perfil
- `GET /api/usuarios/buscar?query=...` - Buscar usuarios

### Conexiones
- `POST /api/conexiones/enviar` - Enviar solicitud
- `PUT /api/conexiones/{id}/aceptar` - Aceptar conexión
- `GET /api/conexiones/contactos/{usuarioId}` - Ver contactos

### Publicaciones
- `POST /api/publicaciones` - Crear publicación
- `GET /api/publicaciones/feed` - Ver feed
- `POST /api/publicaciones/{id}/comentarios` - Comentar
- `POST /api/publicaciones/{id}/reacciones` - Reaccionar

### Mensajes
- `POST /api/mensajes/enviar` - Enviar mensaje
- `GET /api/mensajes/conversacion` - Ver conversación

### Oportunidades
- `GET /api/oportunidades/empleos` - Listar empleos
- `GET /api/oportunidades/pasantias` - Listar pasantías
- `GET /api/oportunidades/talleres` - Listar talleres
- `GET /api/oportunidades/eventos` - Listar eventos

### Gamificación
- `GET /api/puntuaciones/{usuarioId}` - Ver puntos

## 🔒 Seguridad

### JWT
- **Algoritmo**: HS512 (512 bits)
- **Expiración**: 24 horas
- **Header**: `Authorization: Bearer <token>`

### Roles

| Rol | Permisos |
|-----|----------|
| **USER** | Networking, mensajes, publicaciones, ver oportunidades |
| **MENTOR** | Todo lo anterior + sesiones de mentoría |
| **ADMIN** | Control total + gestión de usuarios y oportunidades |

## 🎮 Sistema de Gamificación

Los usuarios ganan puntos automáticamente por:
- ✅ **10 puntos** - Crear una publicación
- ✅ **5 puntos** - Aceptar/enviar conexión
- ✅ **5 puntos** - Comentar en publicaciones
- ✅ **Puntos variables** - Participar en mentorías

## 📊 Base de Datos

### Tablas (14 total)
- `role` - Roles del sistema
- `usuario` - Usuarios
- `usuario_roles` - Relación usuario-roles
- `conexion` - Conexiones entre usuarios
- `mensaje` - Mensajes privados
- `publicacion` - Publicaciones
- `comentario` - Comentarios
- `reaccion` - Reacciones
- `oportunidad` - Oportunidades laborales
- `puntuacion` - Puntos de gamificación
- `mentor` - Perfiles de mentores
- `sesion_mentoria` - Sesiones de mentoría
- `resena_mentor` - Reseñas de mentores

## 🧪 Pruebas

### Test Rápido
```bash
# 1. Registrarse
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "primerNombre": "Juan",
    "primerApellido": "Pérez",
    "email": "juan@test.com",
    "password": "Password123",
    "pais": "Peru",
    "ciudad": "Lima",
    "fechaNacimiento": "1995-01-01"
  }'

# 2. Login (guardar el token)
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "juan@test.com",
    "password": "Password123"
  }'

# 3. Usar el token
curl -X GET http://localhost:8080/api/usuarios/1 \
  -H "Authorization: Bearer <TU_TOKEN_AQUI>"
```

Ver **[test-api.md](test-api.md)** para ejemplos completos de todos los endpoints.

## 📦 Compilación y Empaquetado

```bash
# Compilar
./mvnw clean compile

# Ejecutar tests
./mvnw test

# Empaquetar JAR
./mvnw clean package

# Ejecutar JAR
java -jar target/MolinaChirinosTP-0.0.1-SNAPSHOT.jar
```

## 🤝 Contribuir

Este proyecto fue desarrollado como parte del Trabajo Final.

## 📄 Licencia

Proyecto académico - Universidad Peruana de Ciencias Aplicadas (UPC)

## 👨‍💻 Autores

- **Molina Chirinos** - Desarrollo Backend
- **Equipo de Desarrollo** - Frontend y Testing

## 📞 Soporte

Para dudas sobre el proyecto, consultar la documentación en:
- [test-api.md](test-api.md) - Guía de endpoints
- [RESULTADOS-PRUEBAS.md](RESULTADOS-PRUEBAS.md) - Resultados de pruebas

---

**Versión**: 1.0.0
**Fecha**: Noviembre 2025
**Estado**: ✅ Producción Ready
