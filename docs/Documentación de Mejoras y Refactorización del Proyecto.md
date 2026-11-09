### **Documentación de Mejoras y Refactorización del Proyecto**

En esta fase del proyecto se han implementado mejoras significativas para el usuario y la limpieza del código:

#### **1\. Los estilos separados del main**

* Se han separado los estilos visuales que antes se encontraban dentro del main, trasladándose a una clase distinta llamada CSS.java.  
   Mejora notablemente la manera visual del código y facilita la modificación futura de la aplicación.

#### **2\. Funcionalidad de Cierre Rápido con la Tecla ESC**

* Se ha añadido una línea de código en el que el botón de cancelación se aplica al pulsar esc. Permite cerrar la ventana o cancelar la acción de forma intuitiva simplemente pulsando la tecla esc, mejorando en sí la interfaz.  
Esto se ha aplicado dos veces.

Linea de codigo puesta: 

- **botonCerrarDetalles.setCancelButton(true);**  
- **botonCancelar.setCancelButton(true);** 

#### **3\. El espaciado para ciertos elementos**

* Se han eliminado los espacios forzosos mediante tabuladores de texto , sustituyéndolo  con una línea de código en el que  hemos preguntado a la ia ya que desconocemos cómo hacerlo. Así de esta manera no se ha implementado el espacio de manera forzosa. Aclarando que lo hemos aplicado en distintos sitios dependiendo el espacio que necesitábamos   
  Linea de codigo puesta: **HBox.setMargin(“nombreVariable”, new Insets(0, 0, 0, 0));**      —\>(arriba, derecha,abajo,izquierda)


#### **4\. Limpieza de código y optimización**

* Tras esta optimización se han generado 4 clases más  por lo que el tamaño de código en el main se ha a disminuido

| Estructura de Clases Implementada  |  |
| :---- | :---- |

| Clase  | Responsabilidad  |

| GestorIncidencias.java  ---> Guarda la lista de incidencias y proporciona métodos para añadir, buscar por título, filtrar por estado y calcular los totales.  |

| VistaPrincipal.java ---> Construye el dashboard: barra de búsqueda y filtro, tabla con el botón Ver detalles y barra de contadores y mensajes.  |

| VistaNuevaIncidencia.java  ---> Construye el formulario de creaciónzxcvbnvbbnhgvbv y valida que el título y la descripción no estén vacíos.  |

| VistaDetallesIncidencia.java  ---> Y este construye la vista de detalles y cambio de estado: la información, selección de nuevo estado y guardar/cerrar.  |

| Main.java  ---> Mas simplificado.  |

| Incidencia.java  ---> No se ha cambiado. |

| CSS.java  ---> Los distintos tipos de estilos que se han usado |

#### **5\. Hemos añadido la parte Interfaces naturales en el Gestor de Incidencias**

El análisis sobre  qué acciones de la aplicación podrían mejorar con una interfaz natural en sí lo que se pedía de manera opcional para la entrega del trabajo

