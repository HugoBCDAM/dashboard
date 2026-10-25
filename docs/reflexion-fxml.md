## **Cuestiones mínimas para la reflexión sobre FXML** 

## **1\. ¿Qué problema resuelve FXML en una aplicación JavaFX?**

Hasta ahora hemos hecho toda la interfaz con código: en Main.java creamos cada VBox, HBox, botón y tabla con new, les ponemos el estilo con setStyle y ahí mismo programamos qué hace cada botón. El resultado es una clase muy larga en la que cuesta encontrar las cosas, y para probar un cambio de diseño, como mover un botón o cambiar un color, hay que tocar el código y volver a ejecutar. FXML lo resuelve describiendo la interfaz en un archivo aparte, y en el código Java solo queda lo que hace la aplicación.

## **2\. ¿Qué información contiene un archivo .fxml?**

Un archivo .fxml es un documento XML, el mismo formato que usamos en Acceso a Datos, en el que se describe la ventana de arriba abajo:

* Los import de las clases que usa, igual que en un archivo Java.  
* El elemento raíz (por ejemplo un VBox o un BorderPane), que lleva xmlns:fx y, si hay controlador, fx:controller con el nombre de su clase.  
* Los nodos metidos unos dentro de otros, que forman el árbol de la pantalla: contenedores con controles dentro.  
* Las propiedades de cada nodo, como atributos (text, spacing, prefHeight) o como elementos hijos (\<padding\>).  
* Los fx:id, que dan nombre a los nodos, y los eventos, como onAction="\#buscar".  
* El enlace a la hoja de estilos CSS (stylesheets) y las clases de estilo (styleClass).

## **3\. ¿Qué relación existe entre FXML y las clases JavaFX?**

Cada etiqueta del FXML es una clase de JavaFX: \<Button\> crea un Button, igual que new Button() en nuestro código. Los atributos son las propiedades de esa clase, y text="Buscar" hace lo mismo que botonBuscar.setText("Buscar"). Lo que va dentro de un contenedor son sus hijos, que ahora le pasamos al constructor de VBox o a getChildren(). Por eso un FXML es otra forma de escribir lo mismo, y los import del principio sirven para que sepa a qué clase corresponde cada etiqueta.

## **4\. ¿Qué función cumple FXMLLoader?**

FXMLLoader es la clase que convierte el archivo en objetos. Con load() lee el .fxml, crea todos los nodos y nos devuelve el nodo raíz, que metemos en una Scene como hacemos ahora con nuestro VBox principal. Además crea el controlador que indica fx:controller y le pasa los nodos que tienen fx:id. Para que encuentre el archivo, este tiene que estar en src/main/resources, la carpeta de Maven para los recursos.

## **5\. ¿Qué utilidad tienen fx:id y la anotación @FXML?**

Con fx:id ponemos nombre a un nodo del FXML, por ejemplo fx:id="listaIncidencias". En el controlador declaramos un campo con ese mismo nombre y la anotación @FXML, y FXMLLoader lo rellena con el nodo. Así podemos usar desde Java los controles que dibujamos en Scene Builder, igual que ahora usamos listaIncidencias.getItems(). @FXML también marca los métodos que se llaman desde el FXML con onAction="\#nombre" y deja que los campos sigan siendo private. Si el nombre del campo no es exactamente el fx:id, el campo se queda en null y la aplicación falla al usarlo.

## **6\. ¿Qué papel tiene el controlador?**

El controlador es la clase donde va el comportamiento. Recibe los nodos con @FXML y define qué pasa al pulsar un botón, elegir un filtro o guardar un formulario. En nuestro proyecto, métodos como buscarIncidencias, los filtros por estado y actualizarContadores ya hacen justo eso, pero están en la misma clase que construye la pantalla. Con FXML pasarían al controlador y la interfaz quedaría en el archivo. Si el controlador tiene un método initialize(), se ejecuta solo cuando los nodos ya están cargados, y es el sitio para preparar las columnas y cargar los datos iniciales.

## **7\. ¿Qué aporta Scene Builder al proceso de diseño?**

Scene Builder es un programa para diseñar la interfaz arrastrando controles en vez de escribir código. A la izquierda se eligen contenedores y controles, en el centro se ve la ventana y a la derecha se cambian sus propiedades (texto, tamaño, márgenes, alineación, estilos), y todo se guarda en un archivo FXML. También permite poner el fx:id y enlazar eventos con los métodos del controlador. Nos ahorra escribir el XML a mano y los errores de sintaxis, pero hay que abrir el .fxml y entender lo que ha generado, porque tenemos que saber señalar qué elementos hemos creado con la herramienta.

## **8\. ¿Qué ha mejorado y qué no ha mejorado en nuestra aplicación después de la refactorización?**

