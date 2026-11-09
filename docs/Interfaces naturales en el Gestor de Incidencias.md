# Interfaces naturales en el Gestor de Incidencias

**Autores:** Hugo y Estrella · Desarrollo de Interfaces (DAM) · Actividad de interfaces naturales (NUI)

## Paso 1. Acciones de nuestra aplicación

| Acción | Cómo se realiza actualmente |
|---|---|
| Buscar una incidencia ---> Se escribe texto en un `TextField` y se pulsa el botón "Buscar". La búsqueda compara con el título, sin distinguir mayúsculas. |

| Filtrar por estado ---> Se elige una opción (Abiertas, En Proceso, Resueltas, Todo) en un `ComboBox` y la tabla se actualiza. |

| Crear una incidencia ---> Se pulsa "Nueva Incidencia", se abre otra pantalla y se escribe el título y la descripción. Se eligen estado y prioridad en dos `ComboBox` y se pulsa "Guardar". Si falta título o descripción, aparece un aviso en rojo. |

| Consultar los detalles ---> Se pulsa el enlace "Ver detalles" de la fila correspondiente en la tabla y se abre la pantalla de detalles. |

| Cambiar el estado ---> En la pantalla de detalles se elige el nuevo estado en un `ComboBox` y se pulsa "Guardar". La aplicación avisa si se elige el mismo estado. |

## Paso 2. Alternativas con interfaces naturales

| Acción | Interacción actual | Posible interfaz natural | Valoración |
|---|---|---|---|

| Buscar ---> Campo de texto + botón ---> Voz: "Buscar error en la base de datos" ---> Funciona, pero con teclado y ratón ya es rápido. No aporta mucho. |


| Filtrar por estado ---> `ComboBox` de 4 opciones ---> Voz: "Mostrar incidencias abiertas" ---> Es viable, porque hay pocos comandos y son fáciles de reconocer. La mejora es pequeña: el desplegable ya son dos clics. |


| Crear incidencia ---> Escribir título y descripción con teclado ---> Voz: "Nueva incidencia. Título: la impresora no imprime. Descripción: …" ---> Es la que más mejora, porque es la tarea que más tecleo exige. |


| Consultar detalles ---> Botón "Ver detalles" en la fila ---> Voz: "Abrir la incidencia 2". También sería posible un gesto con la mano. ---> Podría usarse la voz, pero hacer clic en un botón visible es más directo. Los gestos no compensan por falta de precisión. |


| Cambiar estado ---> `ComboBox` + "Guardar" ---> Voz: "Marcar como resuelta" ---> Es útil si el técnico tiene las manos ocupadas. En una mesa de oficina, no. |


**Conclusión:** en buscar, filtrar y consultar detalles, la interfaz gráfica actual es igual o mejor. Los gestos y el movimiento corporal los descartamos porque obligan a estar frente a una cámara y se equivocan más que un clic. La voz solo compensa donde hay mucho texto que introducir o donde el usuario no puede usar las manos.

## Paso 3. Propuesta elegida: dictado por voz para crear incidencias

- **Problema que resuelve:** crear una incidencia es la acción más lenta de la aplicación. Hay que escribir un título y una descripción completa, y la descripción es la parte más larga.
- **Usuario beneficiado:** el técnico o empleado que detecta un fallo y lo registra mientras lo tiene delante (un equipo que no arranca, una impresora atascada). También ayuda a quien tiene dificultad para teclear.
- **Contexto de uso:** un técnico que va por la oficina o la planta y tiene las manos ocupadas con cables o piezas, y quiere registrar el fallo al momento, sin esperar a sentarse al ordenador.
- **Por qué mejora lo actual:** hablar es más rápido que escribir. El usuario no tiene que dejar lo que está haciendo y no se pierden detalles por dejar el registro para más tarde.

## Paso 4. Esquema de funcionamiento

```
Usuario
 ↓
Dice: "Nueva incidencia. Título: la impresora no imprime.
       Descripción: sale un error de atasco. Prioridad alta"
 ↓
Micrófono
 ↓
Reconocimiento de voz
 ↓
Interpretación del comando
 ↓
Acción de la aplicación
 ↓
Respuesta de la interfaz
 - campoNuevaIncidencia ← título
 - campoDescripcion     ← descripción
 - comboNuevaPrioridad  ← "Alta"
 ↓
El usuario revisa el texto y confirma con "Guardar"
(botón o voz: "guardar").
```

## Paso 5. Qué sería necesario

- **Hardware:** un micrófono (el del portátil sirve, aunque un auricular con micrófono reduce el ruido).
- **Interpretación del comando:** código que detecte palabras clave ("título", "descripción", "prioridad") y reparta el texto en los campos.
- **Cambios en la interfaz actual:** un botón con icono de micrófono en la pantalla de nueva incidencia y otro en el dashboard; un indicador visual de "escuchando…"; mostrar en pantalla lo que se ha entendido antes de guardar.
- **Nuevos eventos:** un manejador para el botón del micrófono y un método que reciba el texto reconocido y rellene `campoNuevaIncidencia`, `campoDescripcion` y `comboNuevaPrioridad`.
- **Mensajes de estado:** reutilizar el recuadro de mensajes inferior para avisar de errores ("No se ha entendido, repítelo").

## Paso 6. Ventajas y limitaciones

**Ventajas**

1. **Rapidez y comodidad:** es más rápido que teclear, sobre todo en descripciones largas.
2. **Manos libres:** el técnico puede registrar el fallo sin soltar lo que está haciendo.
3. **Accesibilidad:** ayuda a personas con dificultades motrices o que teclean con dificultad.

**Limitaciones**

1. **Errores de reconocimiento:** hay que corregir palabras técnicas ("SQL", "router", nombres de equipos) y los números mal reconocidos. Por eso es imprescindible mostrar el texto antes de guardar.
2. **Ruido ambiental:** en una planta ruidosa, la precisión baja mucho.

## Paso 7. Decisión final

Parcialmente. Lo incorporaríamos como complemento a la interfaz gráfica, no como sustituto:

- El teclado y el ratón se mantienen como forma principal. Son más fiables y silenciosos, y sirven para corregir errores.
- La voz se usaría solo para dictar el título y la descripción. Nunca guardaría sola: el usuario revisa y pulsa "Guardar", lo que además mantiene la validación que ya tenemos.
- No usaríamos voz para buscar, filtrar ni consultar detalles, porque ahí la interfaz actual ya es suficientemente buena.
