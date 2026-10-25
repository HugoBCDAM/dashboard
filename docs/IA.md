# **Registro de Uso de Inteligencia Artificial**

## **1\. Estructura inicial de las columnas del TableView**

* **Fecha:** 22/10/2026  
* **Problema que queríamos resolver:** Definir las columnas de la tabla (ID, Título, Prioridad, Estado y Acciones) en el orden correcto.  
* **Herramienta utilizada:** ChatGPT  
* **Consulta realizada:** «si la cabecera es ID, TITULO, PRIORIDAD, ESTADO, ACCIONES, como harias la cabecera de un tableview en javafx»  
* **Resumen de la respuesta:** Nos propuso crear un objeto TableColumn por cada cabecera y añadirlos a la tabla.  
* **Qué parte utilizamos:** La estructura base para crear las columnas.  
* **Qué modificamos:** La adaptamos a nuestra clase Incidencia.  
* **Cómo lo validamos:** Compilamos y comprobamos que las cabeceras aparecían en el orden previsto.  
* **Qué hemos aprendido:** Cómo se inicializa la estructura de una tabla en JavaFX.

  ## **2\. Ajuste del ancho de las columnas**

* **Fecha:** 22/10/2026  
* **Problema que queríamos resolver:** Las columnas aparecían estrechas y no ocupaban todo el ancho de la tabla.  
* **Herramienta utilizada:** ChatGPT  
* **Consulta realizada:** «me salen las columnas como pegadas y no ocupan todo el espacio, que podría hacer para que ocupen todo el espacio de la tabla...»  
* **Resumen de la respuesta:** Nos explicó la política de redimensionado de columnas (ColumnResizePolicy) y recomendó la variante que ajusta la última columna.  
* **Qué parte utilizamos:** La línea de la política de redimensionado.  
* **Qué modificamos:** Elegimos TableView.CONSTRAINED\_RESIZE\_POLICY\_FLEX\_LAST\_COLUMN.  
* **Cómo lo validamos:** Agrandamos y reducimos la ventana para comprobar que la tabla se adaptaba.  
* **Qué hemos aprendido:** Cómo se ajustan automáticamente las columnas de una tabla en JavaFX.

  ## **3\. Enlace de los datos del ArrayList con PropertyValueFactory**

* **Fecha:** 22/10/2026  
* **Problema que queríamos resolver:** Conectar las incidencias guardadas en el ArrayList\<Incidencia\> con las columnas de la tabla.  
* **Herramienta utilizada:** ChatGPT  
* **Consulta realizada:** Preguntamos dónde colocar PropertyValueFactory y cómo cargar los elementos en la tabla.  
* **Resumen de la respuesta:** Nos explicó que PropertyValueFactory usa los getters de la clase del modelo y que los datos se cargan con getItems().setAll(...).  
* **Qué parte utilizamos:** La sintaxis para enlazar cada columna con su dato.  
* **Qué modificamos:** Ajustamos los nombres de las propiedades a los getters de Incidencia.java.  
* **Cómo lo validamos:** Comprobamos que las tres incidencias iniciales se cargaban al arrancar.  
* **Qué hemos aprendido:** Cómo se enlazan los datos del modelo con la tabla.

  ## **4\. Botón «Ver detalles» en cada fila**

* **Fecha:** 22/10/2026  
* **Problema que queríamos resolver:** Queríamos un botón en la columna de acciones, uno por cada fila.  
* **Herramienta utilizada:** ChatGPT  
* **Consulta realizada:** Preguntamos cómo añadir botones funcionales con setCellFactory y pedimos una explicación detallada de TableCell.  
* **Resumen de la respuesta:** Nos propuso sobrescribir updateItem en un TableCell personalizado para colocar un Button dentro de la celda.  
* **Qué parte utilizamos:** La estructura de la fábrica de celdas para el botón.  
* **Qué modificamos:** Pusimos el texto «Ver detalles» y le asociamos nuestra acción.  
* **Cómo lo validamos:** Comprobamos que aparecía un botón en cada fila de la tabla.  
* **Qué hemos aprendido:** Cómo funcionan las fábricas de celdas y cómo poner componentes dentro de una tabla.


  ## **5\. Centrado del contenido de las celdas**

* **Fecha:** 22/10/2026  
* **Problema que queríamos resolver:** Centrar horizontalmente los textos y elementos de todas las columnas.  
* **Herramienta utilizada:** ChatGPT  
* **Consulta realizada:** «como podría hacer para centrar todas las celdas de la tabla»  
* **Resumen de la respuesta:** Nos sugirió recorrer las columnas con un bucle y aplicar columna.setStyle("-fx-alignment: CENTER;"), además de setAlignment(Pos.CENTER) en las celdas.  
* **Qué parte utilizamos:** El bucle que recorre las columnas.  
* **Qué modificamos:** Lo aplicamos a toda la TableView.  
* **Cómo lo validamos:** Comprobamos visualmente que los datos de la tabla quedaban centrados.  
* **Qué hemos aprendido:** Cómo aplicar estilos CSS en línea a los componentes de JavaFX.

## **6\. Estilo del botón de acciones y contraste al seleccionar una fila**

* **Fecha:** 22/10/2026  
* **Problema que queríamos resolver:** Al seleccionar una fila, el fondo se ponía azul y el texto del botón de acciones dejaba de verse.  
* **Herramienta utilizada:** ChatGPT  
* **Consulta realizada:** Preguntamos cómo quitar el recuadro del botón para que pareciera un enlace y cómo solucionar el contraste con el fondo azul de la selección.  
* **Resumen de la respuesta:** Nos sugirió quitar bordes y fondo (-fx-background-color: transparent) y elegir un color de texto con contraste.  
* **Qué parte utilizamos:** Las propiedades de estilo del botón y los eventos setOnMouseEntered y setOnMouseExited.  
* **Qué modificamos:** Elegimos un morado oscuro (\#6A0DAD) para que se lea tanto sobre fondo blanco como sobre el azul de la selección.  
* **Cómo lo validamos:** Seleccionamos filas y pasamos el ratón por encima para comprobar que el enlace se veía y respondía.  
* **Qué hemos aprendido:** Cómo gestionar estados visuales, efectos al pasar el ratón y problemas de contraste.

  ## **7\. Filtrado de incidencias por estado con un ComboBox**

* **Fecha:** 22/10/2026  
* **Problema que queríamos resolver:** Que al elegir una opción del desplegable (Abiertas, En Proceso, Resueltas o Todo) la tabla mostrara solo las incidencias de ese estado.  
* **Herramienta utilizada:** ChatGPT  
* **Consulta realizada:** Preguntamos cómo capturar la selección de un ComboBox y filtrar un ArrayList para actualizar la TableView.  
* **Resumen de la respuesta:** Nos explicó el uso de un switch dentro del evento setOnAction del ComboBox y la creación de listas auxiliares filtradas con bucles sobre la lista original.  
* **Qué parte utilizamos:** La lógica de filtrado por estados y la actualización de la tabla con getItems().setAll(...).  
* **Qué modificamos:** Adaptamos los nombres de los estados («Abierta», «En Proceso», «Resuelta») para que coincidieran exactamente con los de nuestras incidencias.  
* **Cómo lo validamos:** Probamos cada opción del desplegable y comprobamos que la tabla se actualizaba con los datos filtrados.  
* **Qué hemos aprendido:** Cómo asociar la selección de una lista desplegable con la manipulación de una colección de datos.

  ## **8\. Celda sin botón**  

* ## **Fecha: 23/10/2026**

* **Problema que queríamos resolver:** Que la columna de acciones mostrara un botón «Ver detalles» en cada fila capaz de abrir la pantalla correspondiente.  
* **Herramienta utilizada:** ChatGPT y documentación de JavaFX  
* **Consulta realizada:** Preguntamos cómo sobrescribir TableCell y cómo gestionar la creación de los botones según el estado de cada fila.  
* **Resumen de la respuesta:** Nos explicó una fábrica de celdas con una clase anónima que sobrescribe updateItem() para poner el botón solo en las celdas no vacías.  
* **Qué parte utilizamos:** La estructura de TableCell\<Incidencia, Void\> y la condición de updateItem con setGraphic().  
* **Qué modificamos:** Hicimos que el botón obtuviera la incidencia con getTableRow().getItem() y llamara a verDetalles().  
* **Cómo lo validamos:** Añadimos y filtramos incidencias y comprobamos que solo las filas con datos mostraban el botón.  
* **Qué hemos aprendido:** Cómo se reutilizan las celdas en JavaFX y cómo colocar componentes gráficos dentro de una tabla.

  ## **9\. Cambio de pantalla sobre la misma ventana (Stage)**

* **Fecha:** 23/10/2026  
* **Problema que queríamos resolver:** Mostrar las pantallas «Ver detalles» y «Nueva Incidencia» sin abrir varias ventanas independientes.  
* **Herramienta utilizada:** ChatGPT y documentación de JavaFX  
* **Consulta realizada:** Preguntamos cuál era la mejor forma de cambiar de pantalla dentro de una misma aplicación JavaFX.  
* **Resumen de la respuesta:** Nos explicó cómo crear una Scene nueva con sus contenedores (VBox, HBox) y asignarla a la ventana principal con stage.setScene().  
* **Qué parte utilizamos:** La lógica para sustituir la escena activa y volver a la principal al pulsar «Cerrar» o «Guardar».  
* **Qué modificamos:** Al guardar o cancelar volvemos a llamar a start(stage) para redibujar el dashboard con los datos actualizados.  
* **Cómo lo validamos:** Navegamos entre el dashboard, el formulario y los detalles, comprobando que la transición funcionaba y que la ventana seguía siendo la misma.  
* **Qué hemos aprendido:** Cómo funciona la ventana (Stage) y cómo se cambia de escena en una aplicación JavaFX.

  ## **10\. Crecimiento dinámico de los controles (Priority.ALWAYS)**

* **Fecha:** 23/10/2026  
* **Problema que queríamos resolver:** Que el campo de búsqueda y la tabla se expandieran al cambiar el tamaño de la ventana sin descolocar los botones.  
* **Herramienta utilizada:** ChatGPT y documentación de JavaFX  
* **Consulta realizada:** Preguntamos cómo hacer que ciertos controles ocupen todo el espacio sobrante en un HBox y un VBox.  
* **Resumen de la respuesta:** Nos sugirió HBox.setHgrow() y VBox.setVgrow() con la prioridad Priority.ALWAYS.  
* **Qué parte utilizamos:** Las líneas de crecimiento para el campo de búsqueda y la tabla.  
* **Qué modificamos:** Aplicamos HBox.setHgrow(campoBuscar, Priority.ALWAYS) y VBox.setVgrow(listaIncidencias, Priority.ALWAYS).  
* **Cómo lo validamos:** Maximizamos y redimensionamos la ventana y comprobamos que esos elementos se estiraban.  
* **Qué hemos aprendido:** Cómo controlar el reparto del espacio en los contenedores de JavaFX para que la interfaz se adapte.

  ## **11\. Efecto visual al pasar el ratón por los botones**

* **Fecha:** 23/10/2026  
* **Problema que queríamos resolver:** Dar una respuesta visual al pasar el ratón por un botón, sin usar todavía un archivo CSS externo.  
* **Herramienta utilizada:** ChatGPT y documentación de JavaFX  
* **Consulta realizada:** Preguntamos cómo cambiar el estilo de un botón al entrar y salir el puntero.  
* **Resumen de la respuesta:** Nos propuso expresiones lambda con setOnMouseEntered y setOnMouseExited que vuelven a definir setStyle().  
* **Qué parte utilizamos:** Los escuchadores de ratón aplicados a los botones.  
* **Qué modificamos:** Elegimos un color para cada tipo de botón: verde para guardar y crear, azul para buscar, rojo para cerrar y morado para detalles.  
* **Cómo lo validamos:** Pasamos el cursor por todos los botones y comprobamos que cambiaban de tono y que el cursor se convertía en una mano.  
* **Qué hemos aprendido:** Cómo capturar la entrada y salida del puntero para mejorar la experiencia de uso.

