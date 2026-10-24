package ies.lazaro;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private ArrayList<Incidencia> incidencias;
    private TableView<Incidencia> listaIncidencias;
    private TableColumn<Incidencia, Integer> colId;
    private TableColumn<Incidencia, String> colTitulo;
    private TableColumn<Incidencia, String> colPrioridad;
    private TableColumn<Incidencia, String> colEstado;
    private TableColumn<Incidencia, Void> colAcciones;
    private ComboBox<String> comboEstado;
    private Label totalIncidencias;
    private Label incidenciasAbiertas;
    private Label incidenciasEnProceso;
    private Label incidenciasResueltas;
    private ComboBox<String> comboNuevaEstado;
    private TextArea campoDescripcion;
    private int totalIncidenciasHechas = 3;
    private int totalIncidenciasAbiertas = 1;
    private int totalIncidenciasEnProceso = 1;
    private int totalIncidenciasResueltas = 1;
    private ComboBox<String> comboNuevaPrioridad;
    private Label mensaje;
    private String textoMensaje = "";

    @Override 
    public void start(Stage stage) {
        if (incidencias == null) {
            incidencias = new ArrayList<>();
            crearIncidenciasIniciales();
        }


        actualizarContadores();
        
        TextField campoBuscar = new TextField();
        campoBuscar.setPromptText("Escribe una incidencia");

        HBox.setHgrow(campoBuscar, Priority.ALWAYS);

        Button botonBuscar = new Button("Buscar");
        botonBuscar.setDefaultButton(true);
        botonBuscar.setStyle("-fx-background-color: #3775fc; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-cursor: hand;");
        botonBuscar.setOnMouseEntered(e -> botonBuscar.setStyle("-fx-background-color: rgb(0, 0, 255); -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-cursor: hand;"));
        botonBuscar.setOnMouseExited(e -> botonBuscar.setStyle("-fx-background-color: #3775fc; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-cursor: hand;"));

        comboEstado = new ComboBox<>();
        comboEstado.getItems().addAll("Abiertas", "En Proceso", "Resueltas", "Todo");
        comboEstado.setPromptText("Filtrar");

        Button botonNuevaIncidencia = new Button("Nueva Incidencia");
        botonNuevaIncidencia.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-cursor: hand;");
        botonNuevaIncidencia.setOnMouseEntered(e -> botonNuevaIncidencia.setStyle("-fx-background-color: rgb(41, 107, 43); -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-cursor: hand;"));
        botonNuevaIncidencia.setOnMouseExited(e -> botonNuevaIncidencia.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-cursor: hand;"));

        HBox incidencias = new HBox(10,
            campoBuscar,
            botonBuscar,
            comboEstado,
            botonNuevaIncidencia
        );
        
        incidencias.setAlignment(Pos.CENTER);

        listaIncidencias = new TableView<>();
        listaIncidencias.setPrefHeight(280);

        colId = new TableColumn<>("ID");
        colTitulo = new TableColumn<>("Titulo");
        colPrioridad = new TableColumn<>("Prioridad");
        colEstado = new TableColumn<>("Estado");
        colAcciones = new TableColumn<>("Acciones");

        listaIncidencias.getColumns().add(colId);
        listaIncidencias.getColumns().add(colTitulo);
        listaIncidencias.getColumns().add(colPrioridad);
        listaIncidencias.getColumns().add(colEstado);
        listaIncidencias.getColumns().add(colAcciones);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colPrioridad.setCellValueFactory(new PropertyValueFactory<>("prioridad"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colAcciones.setCellFactory(columna -> new TableCell<Incidencia, Void>() {
            private final Button botonDetalles = new Button("Ver detalles");

            {
                botonDetalles.setStyle(
                    "-fx-background-color: transparent;" +
                    "-fx-border-color: transparent;" +
                    "-fx-padding: 0;" +
                    "-fx-text-fill: #6A0DAD;" +
                    "-fx-cursor: hand;"
                );

                botonDetalles.setOnMouseEntered(e ->
                    botonDetalles.setStyle(
                        "-fx-background-color: transparent;" +
                        "-fx-border-color: transparent;" +
                        "-fx-padding: 0;" +
                        "-fx-text-fill: #4B0082;" +
                        "-fx-underline: true;" +
                        "-fx-cursor: hand;"
                    )
                );

                botonDetalles.setOnMouseExited(e ->
                    botonDetalles.setStyle(
                        "-fx-background-color: transparent;" +
                        "-fx-border-color: transparent;" +
                        "-fx-padding: 0;" +
                        "-fx-text-fill: #6A0DAD;" +
                        "-fx-cursor: hand;"
                    )
                );

                botonDetalles.setOnAction(e -> {
                    Incidencia incidenciaSeleccionada = getTableRow().getItem();

                    if (incidenciaSeleccionada != null) {
                        verDetalles(stage, incidenciaSeleccionada);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    setAlignment(Pos.CENTER);
                } else {
                    setGraphic(botonDetalles);
                    setAlignment(Pos.CENTER);
                }
            }
        });

        listaIncidencias.getColumns().forEach(columna ->
            columna.setStyle("-fx-alignment: CENTER;")
        );

        listaIncidencias.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        
        listaIncidencias.getItems().setAll(this.incidencias);
        
        totalIncidencias = new Label("Total de incidencias: " + totalIncidenciasHechas);
        totalIncidencias.setStyle("-fx-font-weight: bold;");

        incidenciasAbiertas = new Label("Abiertas: " + totalIncidenciasAbiertas);
        incidenciasAbiertas.setStyle("-fx-font-weight: bold;");

        incidenciasEnProceso = new Label("En Proceso: " + totalIncidenciasEnProceso);
        incidenciasEnProceso.setStyle("-fx-font-weight: bold;");

        incidenciasResueltas = new Label("Resueltas: " + totalIncidenciasResueltas);
        incidenciasResueltas.setStyle("-fx-font-weight: bold;");

        Label separacion1 = new Label(" | ");
        separacion1.setStyle("-fx-font-weight: bold;");

        Label separacion2 = new Label(" | ");
        separacion2.setStyle("-fx-font-weight: bold;");

        Label separacion3 = new Label(" | ");
        separacion3.setStyle("-fx-font-weight: bold;");
        
        HBox estadoIncidencias = new HBox(10,
            totalIncidencias, 
            separacion1,
            incidenciasAbiertas, 
            separacion2,
            incidenciasEnProceso, 
            separacion3,
            incidenciasResueltas
        );

        estadoIncidencias.setAlignment(Pos.CENTER);

        mensaje = new Label(textoMensaje);
        mensaje.setWrapText(true);
        mensaje.setMaxWidth(Double.MAX_VALUE);
        mensaje.setAlignment(Pos.CENTER);
        mensaje.setStyle("-fx-font-weight: bold;");
        
        VBox.setVgrow(listaIncidencias, Priority.ALWAYS);
        
        VBox registroIncidenciasMensaje = new VBox(14,
            estadoIncidencias,
            mensaje
        );

        registroIncidenciasMensaje.setStyle("-fx-background-color: #d2d3d2; -fx-padding: 5px;");

        botonNuevaIncidencia.setOnAction(e -> nuevaIncidencia(stage));

        botonBuscar.setOnAction(e -> buscarIncidencias(campoBuscar.getText()));

        


        comboEstado.setOnAction(e -> {
            String estado = comboEstado.getValue();

            switch (estado) {
            case "Abiertas":
                filtrarAbiertas();
                break;
            case "En Proceso":
                filtrarEnProceso();
                break;
            case "Resueltas":
                filtrarResueltas();
                break;
            default:
                filtrarTodas();
                break;
            }  
        });

        VBox raiz = new VBox(14, 
            incidencias,
            listaIncidencias,
            registroIncidenciasMensaje
        );

        raiz.setPadding(new Insets(20));

        escenaIncidencias(stage, raiz);
    }

    private void actualizarContadores() {
            totalIncidenciasHechas = incidencias.size();
            totalIncidenciasAbiertas = 0;
            totalIncidenciasEnProceso = 0;
            totalIncidenciasResueltas = 0;

            for (int i=0; i < incidencias.size(); i++) {
                String estado = this.incidencias.get(i).getEstado();
               if (estado.equals("Abierta")) {
                    totalIncidenciasAbiertas++;
                } else if (estado.equals("En Proceso")) {
                    totalIncidenciasEnProceso++;
                } else if (estado.equals("Resuelta")) {
                    totalIncidenciasResueltas++;
                }
            }
        }

    private void filtrarTodas() {
        listaIncidencias.getItems().setAll(this.incidencias);
    }

    private void filtrarResueltas() {
        ArrayList<Incidencia> indicenciasResueltas = new ArrayList<>();

        for (int i = 0; i < this.incidencias.size(); i++) {
            if (this.incidencias.get(i).getEstado().equals("Resuelta")) {
                indicenciasResueltas.add(this.incidencias.get(i));
            }
        }

        listaIncidencias.getItems().setAll(indicenciasResueltas);
    }
    private void filtrarEnProceso() {
        ArrayList<Incidencia> incidenciasEnProceso = new ArrayList<>();

        for (int i = 0; i < this.incidencias.size(); i++) {
            if (this.incidencias.get(i).getEstado().equals("En Proceso")) {
                incidenciasEnProceso.add(this.incidencias.get(i));
            }
        }

        listaIncidencias.getItems().setAll(incidenciasEnProceso);
    }

    private void filtrarAbiertas() {
        ArrayList<Incidencia> incidenciasAbiertas = new ArrayList<>();

        for (int i = 0; i < this.incidencias.size(); i++) {
            if (this.incidencias.get(i).getEstado().equals("Abierta")) {
                incidenciasAbiertas.add(this.incidencias.get(i));
            }
        }

        listaIncidencias.getItems().setAll(incidenciasAbiertas);
    }

    private void buscarIncidencias(String texto) {
        ArrayList<Incidencia> encontradas = new ArrayList<>();
        String textoBuscado = texto.trim().toLowerCase();

        for (int i = 0; i < this.incidencias.size(); i++) {
            Incidencia incidencia = this.incidencias.get(i);

            if (incidencia.getTitulo().toLowerCase().contains(textoBuscado)) {
                encontradas.add(incidencia);
            }
        }

        listaIncidencias.getItems().setAll(encontradas);

        if (encontradas.isEmpty()) {
            mensaje.setText("No se han encontrado incidencias con ese texto");
        } else {
            mensaje.setText("Incidencias encontradas: " + encontradas.size());
        }
    }

    private void verDetalles(Stage stage, Incidencia incidenciaSeleccionada) {
        Label idDetalles = new Label("ID:");
        Label idDetallesValor = new Label(String.valueOf(incidenciaSeleccionada.getId()));

        Label tituloDetalles = new Label("Titulo:");
        Label tituloDetallesValor = new Label(incidenciaSeleccionada.getTitulo());

        Label descripcionDetalles = new Label("Descripción:");
        Label descripcionDetallesValor = new Label(incidenciaSeleccionada.getDescripcion());

        Label prioridadDetalles = new Label("Prioridad:");
        Label prioridadDetallesValor = new Label(incidenciaSeleccionada.getPrioridad());

        Label estadoDetalles = new Label("Estado:");
        Label estadoDetallesValor = new Label(incidenciaSeleccionada.getEstado());

        VBox cabecerasDetalles = new VBox(10,
            idDetalles,
            tituloDetalles,
            descripcionDetalles,
            prioridadDetalles,
            estadoDetalles
        );

        cabecerasDetalles.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-font-weight: bold; -fx-padding: 5px; -fx-background-color: #bdb9b9;");
        
        VBox valoresDetalles = new VBox(10,
            idDetallesValor,
            tituloDetallesValor,
            descripcionDetallesValor,
            prioridadDetallesValor,
            estadoDetallesValor
        );

        valoresDetalles.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-font-weight: bold; -fx-padding: 5px; -fx-background-color: #e0dede;");
        
        HBox detallesHBox = new HBox(cabecerasDetalles, valoresDetalles);
        detallesHBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(valoresDetalles, Priority.ALWAYS);
        valoresDetalles.setMaxWidth(Double.MAX_VALUE);

        Label cambiarEstadoDetalles = new Label("Cambiar Estado: ");
        ComboBox<String> comboCambiarEstado = new ComboBox<>();
        comboCambiarEstado.getItems().addAll("Abierta", "En Proceso", "Resuelta");
        comboCambiarEstado.setValue(incidenciaSeleccionada.getEstado());

        Button botonCerrarDetalles = new Button("Cerrar");
        Button botonGuardarCambios = new Button("Guardar");

        botonGuardarCambios.setDefaultButton(false);
        botonGuardarCambios.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-cursor: hand;");
        botonGuardarCambios.setOnMouseEntered(e -> botonGuardarCambios.setStyle("-fx-background-color: rgb(42, 107, 44); -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-cursor: hand;"));
        botonGuardarCambios.setOnMouseExited(e -> botonGuardarCambios.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-cursor: hand;"));

        botonCerrarDetalles.setDefaultButton(true);
        botonCerrarDetalles.setStyle("-fx-background-color: #FF0000; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-cursor: hand;");
        botonCerrarDetalles.setOnMouseEntered(e -> botonCerrarDetalles.setStyle("-fx-background-color: #b40202; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-cursor: hand;"));
        botonCerrarDetalles.setOnMouseExited(e -> botonCerrarDetalles.setStyle("-fx-background-color: #FF0000; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-cursor: hand;"));
        
        HBox cambiarEstadoBox = new HBox(10, 
            cambiarEstadoDetalles,
            comboCambiarEstado,
            new Label("\t"),
            botonCerrarDetalles,
            botonGuardarCambios
        );

        VBox detallesBox = new VBox(10,
            detallesHBox,
            new Label(""),
            cambiarEstadoBox
        );
        
        detallesBox.setPadding(new Insets(20));

        botonGuardarCambios.setOnAction(e -> {
            String estadoActual = incidenciaSeleccionada.getEstado();
            String nuevoEstado = comboCambiarEstado.getValue();

            if (nuevoEstado.equalsIgnoreCase(estadoActual)) {
                mostrarMensaje(new Label("No se actualizó el estado porque has seleccionado el mismo estado."));
            } else {
                incidenciaSeleccionada.setEstado(nuevoEstado);

                mostrarMensaje(new Label("El estado de la incidencia se actualizó correctamente."));
            }

            start(stage);
        });

        botonCerrarDetalles.setOnAction(e -> {
            mostrarMensaje(new Label("Los detalles de la incidencia se han cerrado correctamente"));
            start(stage);
        });

        Scene verDetallesScene = new Scene(detallesBox, 435, 230);
        stage.setTitle("Detalles de la Incidencia");
        stage.setScene(verDetallesScene);
        stage.setResizable(false);
        stage.show();
    }

    private void crearIncidenciasIniciales() {
        incidencias.add(new Incidencia(1, 
            "Error en la página de inicio", 
            "La página de inicio no carga correctamente.", 
            "Abierta", 
            "Alta"
            )
        );

        incidencias.add(new Incidencia(2, 
            "Problema con el formulario de contacto", 
            "El formulario de contacto no envía los mensajes.", 
            "En Proceso", 
            "Media"
            )
        );

        incidencias.add(new Incidencia(3, 
            "Error en la base de datos", 
            "Se ha producido un error al acceder a la base de datos.", 
            "Resuelta", 
            "Alta"
            )
        );

        mostrarMensaje(new Label("Incidencias cargadas con éxito"));
    }

    private void escenaIncidencias(Stage stage, VBox raiz) {
        Scene scene = new Scene(raiz, 800, 500);
        stage.setTitle("Dashboard de Incidencias");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    private void nuevaIncidencia(Stage stage) {
        TextField campoNuevaIncidencia = new TextField();
        campoNuevaIncidencia.setPromptText("Escribe la nueva incidencia");

        HBox.setHgrow(campoNuevaIncidencia, Priority.ALWAYS);

        campoDescripcion = new TextArea();
        campoDescripcion.setPrefHeight(300);
        campoDescripcion.setWrapText(true);

        comboNuevaEstado = new ComboBox<>();
        comboNuevaEstado.getItems().addAll("Abierta", "En Proceso", "Resuelta");
        comboNuevaEstado.setValue("Abierta");

        comboNuevaPrioridad = new ComboBox<>();
        comboNuevaPrioridad.getItems().addAll("Alta", "Media", "Baja");
        comboNuevaPrioridad.setValue("Media");

        HBox estadoPrioridadNuevaIncidenciaBox = new HBox(10,
            new Label("Estado:"),
            comboNuevaEstado,
            new Label("\t\t"), 
            new Label("Prioridad:"),
            comboNuevaPrioridad
        );

        estadoPrioridadNuevaIncidenciaBox.setAlignment(Pos.CENTER);
        
        Button crearIncidencia = new Button("Guardar");
        crearIncidencia.setDefaultButton(true);
        crearIncidencia.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-cursor: hand;");
        crearIncidencia.setOnMouseEntered(e -> crearIncidencia.setStyle("-fx-background-color: rgb(42, 107, 44); -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-cursor: hand;"));
        crearIncidencia.setOnMouseExited(e -> crearIncidencia.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-cursor: hand;"));

        Button cancelar = new Button("Cerrar");
        cancelar.setDefaultButton(true);
        cancelar.setStyle("-fx-background-color: #FF0000; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-cursor: hand;");
        cancelar.setOnMouseEntered(e -> cancelar.setStyle("-fx-background-color: #b40202; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-cursor: hand;"));
        cancelar.setOnMouseExited(e -> cancelar.setStyle("-fx-background-color: #FF0000; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-cursor: hand;"));

        HBox botonNuevaIncidenciaBox = new HBox (10, 
            cancelar, 
            new Label("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"),
            crearIncidencia
        );

        botonNuevaIncidenciaBox.setAlignment(Pos.CENTER);

        Label avisoFormulario = new Label();
        avisoFormulario.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

        VBox nuevaIncidenciaBox = new VBox(14,
            new Label("Titulo:"),
            campoNuevaIncidencia,
            new Label("Descripción:"),
            campoDescripcion,
            estadoPrioridadNuevaIncidenciaBox,
            avisoFormulario, 
            botonNuevaIncidenciaBox
        );

        nuevaIncidenciaBox.setPadding(new Insets(20));

        crearIncidencia.setOnAction(e -> {
            String titulo = campoNuevaIncidencia.getText().trim();
            String descripcion = campoDescripcion.getText().trim();

              if (titulo.isEmpty() || descripcion.isEmpty()) {
                avisoFormulario.setText("Escriba el título y la descripción de la incidencia.");
                return;
              }
            int nuevoId = incidencias.size() + 1; 
                
            this.incidencias.add(new Incidencia(
                nuevoId,
                titulo,
                descripcion,
                comboNuevaEstado.getValue(),
                comboNuevaPrioridad.getValue()
            ));

                mostrarMensaje(new Label("Nueva incidencia creada con éxito"));
                start(stage);
            
        });
    

        cancelar.setOnAction(e -> {
            mostrarMensaje(new Label("Creación de incidencia cancelada"));
            start(stage);
        });

        Scene nuevaIncidenciaScene = new Scene(nuevaIncidenciaBox, 600, 500);
        stage.setTitle("Nueva Incidencia");
        stage.setScene(nuevaIncidenciaScene);
        stage.setResizable(false);
        stage.show();
    }

    private void mostrarMensaje(Label mensaje) {
        this.textoMensaje = mensaje.getText();
    }
    public static void main(String[] args) {
        launch();
    }

    public int getTotalIncidenciasHechas() {
        return totalIncidenciasHechas;
    }

    public void setTotalIncidenciasHechas(int totalIncidenciasHechas) {
        this.totalIncidenciasHechas = totalIncidenciasHechas;
    }

    public int getTotalIncidenciasAbiertas() {
        return totalIncidenciasAbiertas;
    }

    public void setTotalIncidenciasAbiertas(int totalIncidenciasAbiertas) {
        this.totalIncidenciasAbiertas = totalIncidenciasAbiertas;
    }

    public int getTotalIncidenciasEnProceso() {
        return totalIncidenciasEnProceso;
    }

    public void setTotalIncidenciasEnProceso(int totalIncidenciasEnProceso) {
        this.totalIncidenciasEnProceso = totalIncidenciasEnProceso;
    }

    public int getTotalIncidenciasResueltas() {
        return totalIncidenciasResueltas;
    }

    public void setTotalIncidenciasResueltas(int totalIncidenciasResueltas) {
        this.totalIncidenciasResueltas = totalIncidenciasResueltas;
    }

}   