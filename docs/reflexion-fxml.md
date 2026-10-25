## **Cuestiones mínimas para la reflexión sobre FXML** 

## **1\. ¿Qué problema resuelve FXML en una aplicación JavaFX?**

Hasta ahora hemos hecho toda la interfaz con código: en Main.java creamos cada VBox, HBox, botón y tabla con new, les ponemos el estilo con setStyle y ahí mismo programamos qué hace cada botón. El resultado es una clase muy larga por lo que costaria encontrar las cosas, y para probar un cambio de diseño, como mover un botón o cambiar un color, hay que tocar el código y volver a ejecutar. FXML lo resuelve describiendo la interfaz en un archivo aparte, y en el código Java solo queda lo que hace la aplicación.

## **2\. ¿Qué información contiene un archivo .fxml?**

Que un archivo .fxml es un archivo de texto en formato XML donde se describe cómo es la ventana: qué hay en ella y cómo está colocado. Empieza indicando qué clases de JavaFX usa (botones, tablas, cajas...). Después va una caja principal y, dentro de ella, el resto de elementos, metidos unos dentro de otros, cada uno con sus datos, como el texto de un botón o su tamaño. Además puede llevar un nombre para cada elemento  qué método se ejecuta al pulsar un botón, el archivo CSS con los estilos y la clase Java que se encarga de lo que hace la pantalla.

## **3\. ¿Qué relación existe entre FXML y las clases JavaFX?**

Cada etiqueta del FXML es una clase de JavaFX: \<Button\> crea un Button, igual que new Button() en nuestro código. Los atributos son las propiedades de esa clase, y text="Buscar" hace lo mismo que botonBuscar.setText("Buscar"). Lo que va dentro de un contenedor son sus hijos, que ahora le pasamos al constructor de VBox o a getChildren(). Por eso un FXML es otra forma de escribir lo mismo, y los import del principio sirven para que sepa a qué clase corresponde cada etiqueta.

## **4\. ¿Qué función cumple FXMLLoader?**

Hemos visto que FXMLLoader es la clase que lee el archivo .fxml y lo convierte en los objetos de JavaFX. Al llamar a load() crea todos los elementos de la pantalla y nos devuelve el principal, que metemos en una Scene como hacemos ahora con nuestro VBox. También se encarga de crear el controlador que pone en fx:controller y de pasarle los elementos que tienen fx:id el cual sirve para ponerle nombre a un elemento del FXML. Para que encuentre el archivo, tiene que estar en la carpeta src/main/resources

## **5\. ¿Qué utilidad tienen fx:id y la anotación @FXML?**

Con fx:id  ponemos nombre a un nodo del FXML y con el controlador creamos un campo con ese mismo nombre y le ponemos @FXML encima, y así FXMLLoader mete ahí el elemento de la pantalla. Gracias a eso podemos usarlo desde Java, igual que ahora usamos listaIncidencias.getItems(). @FXML también se pone en los métodos que llamamos desde el FXML con onAction="#nombre". Si el nombre del campo no es exactamente igual que el fx:id, el campo se queda en null y da error al usarlo.

## **6\. ¿Qué papel tiene el controlador?**

El controlador es la clase de Java donde va lo que hace la aplicación. Recibe los elementos de la pantalla que marcamos con @FXML y decide qué pasa al pulsar un botón, elegir un filtro o guardar un formulario. En nuestro proyecto eso ya lo hacen métodos como buscarIncidencias, los filtros por estado y actualizarContadores, pero ahora están en Main, mezclados con el código que construye la pantalla. Con FXML pasarían al controlador y la pantalla se quedaría en el archivo .fxml

## **7\. ¿Qué aporta Scene Builder al proceso de diseño?**

Scene Builder es un programa donde diseñas la ventana arrastrando botones, tablas y contenedores, en vez de escribirlo todo en código. Según vas cambiando cosas ves cómo queda, y por debajo va generando el archivo FXML, así que no tenemos que escribir el XML a mano.

## **8\. ¿Qué ha mejorado y qué no ha mejorado en nuestra aplicación después de la refactorización?**

Todavía no hemos hecho la refactorización a FXML, así que no podemos decir con seguridad qué ha mejorado y qué no en nuestra aplicación. Lo que sí sabemos es cómo está ahora:los estilos están dentro del código con setStyle y la lógica está en la misma clase que construye la pantalla. Por lo que sabemos de FXML, esperamos que con esto y Scene Builder la estructura visual quede separada del código y que Main.java sea más corto.