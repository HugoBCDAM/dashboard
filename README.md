# Dashboard de gestión de incidencias

Proyecto ABP de la UD1 de Desarrollo de Interfaces (DAM).

**Autores:** Hugo y Estrella

## Descripción

Aplicación de escritorio en JavaFX para gestionar incidencias internas de una empresa. Los datos se guardan en memoria, sin base de datos.

Funciones:

- Listado de incidencias en una tabla.
- Indicadores de total, abiertas, en proceso y resueltas.
- Búsqueda por título.
- Filtro por estado.
- Creación de nuevas incidencias con validación del formulario.
- Consulta del detalle y cambio de estado de una incidencia.

## Requisitos

- JDK 27
- Maven

## Cómo ejecutarla

Desde la carpeta del proyecto:

```bash
mvn clean javafx:run
```

## Estructura del código

El código está en `src/main/java/ies/lazaro` y se organiza en estas clases:

| Clase | Responsabilidad |
|---|---|

| `Main.java` | Punto de entrada. Crea el gestor de incidencias y cambia entre las vistas. |

| `GestorIncidencias.java` | Guarda la lista de incidencias y proporciona métodos para añadir, buscar, filtrar y calcular totales. |

| `VistaPrincipal.java` | Construye el dashboard: barra de búsqueda y filtro, tabla, contadores y mensajes. |

| `VistaNuevaIncidencia.java` | Construye el formulario de creación y valida los campos obligatorios. |

| `VistaDetallesIncidencia.java` | Muestra el detalle de una incidencia y permite cambiar su estado. |

| `Incidencia.java` | Modelo de datos de una incidencia. |

| `CSS.java` | Constantes con los estilos visuales de la aplicación. |

## Documentación

Carpeta `docs/`:

- [Decisiones de diseño](docs/decisiones-diseno.md)
- [Reflexión sobre FXML](docs/reflexion-fxml.md)
- [Documentación de mejoras y refactorización](<docs/Documentación de Mejoras y Refactorización del Proyecto.md>)
- [Interfaces naturales en el Gestor de Incidencias](<docs/Interfaces naturales en el Gestor de Incidencias.md>)
- [Registro de uso de IA](docs/IA.md)
- `wireframe.png`: diseño de las pantallas.