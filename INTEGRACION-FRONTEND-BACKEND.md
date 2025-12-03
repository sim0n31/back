# 🔗 Guía de Integración Frontend-Backend

## ✅ **INTEGRACIÓN COMPLETADA**

He sincronizado ambos proyectos y corregido las inconsistencias entre frontend y backend.

---

## 📁 **UBICACIÓN DE LOS PROYECTOS**

- **Backend (Spring Boot):** `D:\TODOARQUI\MolinaChirinosTP (5)22\MolinaChirinosTP (5)\MolinaChirinosTP`
- **Frontend (Angular 17):** `D:\TODOARQUI\connected-frontend (2)22\connected-frontend`

---

## 🔧 **CAMBIOS REALIZADOS EN EL FRONTEND**

### 1. **Modelos Actualizados** ([mentoria.model.ts](D:\TODOARQUI\connected-frontend (2)22\connected-frontend\src\app\models\mentoria.model.ts))

**ANTES** (Incorrecto):
```typescript
export interface SolicitarMentoriaRequest {
  mentorId: number;      // ❌
  menteeId: number;      // ❌
  fechaInicio: string;
  fechaFin: string;      // ❌ No existe en backend
  tema: string;          // ❌ No existe en backend
}
```

**AHORA** (Correcto - coincide con `CrearSesionDTO.java`):
```typescript
export interface SolicitarMentoriaRequest {
  idMentor: number;      // ✅ Coincide con backend
  idAlumno: number;      // ✅ Coincide con backend
  fechaInicio: string;   // ✅ Formato: "YYYY-MM-DD"
  canal: string;         // ✅ NUEVO: "ZOOM", "PRESENCIAL", etc.
  estado: string;        // ✅ NUEVO: "PENDIENTE", "CONFIRMADA", etc.
  notas?: string;        // ✅ Opcional
}

// DTO de respuesta que coincide con SesionViewDTO del backend
export interface SesionMentoriaResponse {
  idSesion: number;
  fechaInicio: string;
  canal: string;
  estado: string;
  notas: string | null;
  idAlumno: number;
  alumnoNombre: string;
  idMentor: number;
  mentorNombre: string;
}
```

### 2. **Servicio Actualizado** ([mentoria.service.ts](D:\TODOARQUI\connected-frontend (2)22\connected-frontend\src\app\services\mentoria.service.ts))

```typescript
/**
 * Crear sesión de mentoría
 * Backend espera: { idAlumno, idMentor, fechaInicio, canal, estado, notas? }
 * Backend devuelve: SesionViewDTO
 */
crearSesion(request: SolicitarMentoriaRequest): Observable<SesionMentoriaResponse> {
  return this.http.post<SesionMentoriaResponse>(`${this.apiUrl}/sesiones`, request);
}
```

### 3. **Componente Actualizado** ([mentorias.component.ts](D:\TODOARQUI\connected-frontend (2)22\connected-frontend\src\app\pages\mentorias\mentorias.component.ts))

```typescript
solicitudForm = {
  fechaInicio: '',
  canal: 'ZOOM',      // Nuevo campo requerido
  estado: 'PENDIENTE', // Nuevo campo requerido
  notas: ''
};

solicitarMentoria() {
  const request: SolicitarMentoriaRequest = {
    idMentor: this.selectedMentor.idMentor,
    idAlumno: this.currentUserId,
    fechaInicio: this.solicitudForm.fechaInicio, // "YYYY-MM-DD"
    canal: this.solicitudForm.canal,
    estado: this.solicitudForm.estado,
    notas: this.solicitudForm.notas || undefined
  };

  // Ahora mostrará los mensajes de error del backend
  this.mentoriaService.crearSesion(request).subscribe({
    next: (sesion) => {
      console.log('Sesión creada:', sesion);
      alert('¡Sesión de mentoría solicitada exitosamente!');
    },
    error: (error) => {
      // Muestra el mensaje exacto del backend
      const errorMsg = error.error?.message || error.error || 'Error al solicitar mentoría';
      alert(errorMsg);
    }
  });
}
```

---

## 🎯 **ENDPOINTS DISPONIBLES**

### Backend: `http://localhost:8080`

| Método | Endpoint | Request | Response |
|--------|----------|---------|----------|
| POST | `/connected/sesiones` | `CrearSesionDTO` | `SesionViewDTO` |
| GET | `/connected/sesiones?idAlumno=1&fecha=2025-12-25` | Query params | `SesionViewDTO[]` |

### Request Body Ejemplo:
```json
{
  "idAlumno": 1,
  "idMentor": 1,
  "fechaInicio": "2025-12-25",
  "canal": "ZOOM",
  "estado": "PENDIENTE",
  "notas": "Primera sesión de mentoría sobre Spring Boot"
}
```

### Response Ejemplo:
```json
{
  "idSesion": 1,
  "fechaInicio": "2025-12-25",
  "canal": "ZOOM",
  "estado": "PENDIENTE",
  "notas": "Primera sesión de mentoría sobre Spring Boot",
  "idAlumno": 1,
  "alumnoNombre": "Juan Pérez",
  "idMentor": 1,
  "mentorNombre": "María García"
}
```

---

## 🔍 **MENSAJES DE ERROR MEJORADOS**

Ahora el backend devuelve mensajes claros cuando hay problemas:

| Error | Mensaje del Backend |
|-------|---------------------|
| ID de alumno nulo | `"El ID del alumno es obligatorio"` |
| ID de mentor nulo | `"El ID del mentor es obligatorio"` |
| Fecha vacía | `"La fecha de inicio es obligatoria"` |
| Formato de fecha incorrecto | `"Formato de fecha inválido. Use formato YYYY-MM-DD (ej: 2025-12-25)"` |
| Fecha en el pasado | `"La fecha debe ser hoy o futura"` |
| Canal vacío | `"El canal es obligatorio"` |
| Estado vacío | `"El estado es obligatorio"` |
| Alumno no existe | `"Alumno con ID 5 no encontrado"` |
| Mentor no existe | `"Mentor con ID 3 no encontrado"` |
| Mentor no disponible | `"El mentor seleccionado no está disponible actualmente"` |

---

## ⚡ **VALIDACIONES IMPLEMENTADAS**

### Frontend (TypeScript):
```typescript
if (!this.solicitudForm.fechaInicio || !this.solicitudForm.canal || !this.solicitudForm.estado) {
  alert('Por favor completa todos los campos requeridos');
  return;
}
```

### Backend (Java):
```java
// Validación de nulls
if (dto.idAlumno() == null) {
    throw new IllegalArgumentException("El ID del alumno es obligatorio");
}

// Validación de formato de fecha
try {
    f = LocalDate.parse(dto.fechaInicio());
} catch (Exception e) {
    throw new IllegalArgumentException("Formato de fecha inválido. Use formato YYYY-MM-DD");
}

// Validación de fecha futura
if (f.isBefore(LocalDate.now())) {
    throw new IllegalArgumentException("La fecha debe ser hoy o futura");
}

// Validación de disponibilidad del mentor
if (m.getDisponible() != null && !m.getDisponible()) {
    throw new IllegalArgumentException("El mentor seleccionado no está disponible actualmente");
}
```

---

## 🧪 **CÓMO PROBAR LA INTEGRACIÓN**

### 1. **Iniciar el Backend**
```bash
cd "D:\TODOARQUI\MolinaChirinosTP (5)22\MolinaChirinosTP (5)\MolinaChirinosTP"
mvn spring-boot:run
```

### 2. **Iniciar el Frontend**
```bash
cd "D:\TODOARQUI\connected-frontend (2)22\connected-frontend"
npm install  # Solo la primera vez
npm start
```

### 3. **Abrir la Aplicación**
```
http://localhost:4200
```

### 4. **Probar Crear Sesión**
1. Ir a la sección de Mentorías
2. Seleccionar un mentor
3. Completar el formulario:
   - **Fecha**: Seleccionar una fecha futura (formato: YYYY-MM-DD)
   - **Canal**: ZOOM, PRESENCIAL, TEAMS, etc.
   - **Estado**: PENDIENTE
   - **Notas**: (Opcional) Descripción de la sesión
4. Click en "Solicitar"

### 5. **Verificar en Consola del Navegador**
Si hay error, verás el mensaje exacto del backend:
```
Error al solicitar mentoría: {
  error: "Formato de fecha inválido. Use formato YYYY-MM-DD (ej: 2025-12-25)"
}
```

---

## 📊 **VALORES VÁLIDOS**

### Canal:
- `"ZOOM"`
- `"PRESENCIAL"`
- `"TEAMS"`
- `"GOOGLE_MEET"`
- O cualquier otro que desees usar

### Estado:
- `"PENDIENTE"` - Sesión solicitada pero no confirmada
- `"CONFIRMADA"` - Mentor confirmó la sesión
- `"COMPLETADA"` - Sesión ya realizada
- `"CANCELADA"` - Sesión cancelada

### Fecha:
- Formato: `"YYYY-MM-DD"` (ej: `"2025-12-25"`)
- Debe ser hoy o fecha futura
- NO fechas pasadas

---

## 🎨 **ACTUALIZAR EL HTML (OPCIONAL)**

Si quieres actualizar el formulario HTML para reflejar los nuevos campos, edita:
`D:\TODOARQUI\connected-frontend (2)22\connected-frontend\src\app\pages\mentorias\mentorias.component.html`

Agrega un selector para el canal:
```html
<div class="form-group">
  <label>Canal de Comunicación</label>
  <select [(ngModel)]="solicitudForm.canal" class="form-control" required>
    <option value="ZOOM">Zoom</option>
    <option value="PRESENCIAL">Presencial</option>
    <option value="TEAMS">Microsoft Teams</option>
    <option value="GOOGLE_MEET">Google Meet</option>
  </select>
</div>

<div class="form-group">
  <label>Notas (Opcional)</label>
  <textarea
    [(ngModel)]="solicitudForm.notas"
    class="form-control"
    rows="3"
    placeholder="Describe qué temas quieres tratar en la mentoría...">
  </textarea>
</div>
```

---

## 🐛 **SOLUCIÓN DE PROBLEMAS COMUNES**

### Error: CORS
Si ves error de CORS, verifica que el backend tenga configurado:
```java
// CorsConfig.java
configuration.setAllowedOrigins(Arrays.asList(
    "http://localhost:4200",
    "http://localhost:4280"
));
```

### Error: 403 Forbidden
Verifica que estés autenticado y el token JWT esté en el header:
```typescript
// El interceptor debería agregar automáticamente:
headers: { Authorization: `Bearer ${token}` }
```

### Error: Mentor/Alumno no encontrado
Verifica que existan registros en la base de datos:
```sql
SELECT * FROM alumno;
SELECT * FROM mentor;
```

---

## ✨ **SIGUIENTE PASO RECOMENDADO**

Actualizar el HTML del formulario para incluir:
1. Selector de canal (dropdown)
2. Campo de notas (textarea)
3. Validación visual de fecha (date picker)

¿Quieres que actualice el HTML también?

---

**Fecha de integración:** 2025-11-30
**Integrado por:** Claude Code
