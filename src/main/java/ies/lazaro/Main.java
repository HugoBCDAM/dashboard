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
        incidencias = new ArrayList<>();
        
        TextField campoBuscar = new TextField();
        campoBuscar.setPromptText("Escribe una incidencia");

        HBox.setHgrow(campoBuscar, Priority.ALWAYS);

        Button botonBuscar = new Button("Buscar");
        botonBuscar.setDefaultButton(true);

        comboEstado = new ComboBox<>();
        comboEstado.getItems().addAll("Abiertas", "En Proceso", "Resueltas");
        comboEstado.setPromptText("Filtrar");

        Button botonNuevaIncidencia = new Button("Nueva Incidencia");

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

        crearIncidenciasIniciales();
        
        listaIncidencias.getItems().setAll(this.incidencias);
        
        totalIncidencias = new Label("Total de incidencias: " + totalIncidenciasHechas);
        incidenciasAbiertas = new Label("Abiertas: " + totalIncidenciasAbiertas);
        incidenciasEnProceso = new Label("En Proceso: " + totalIncidenciasEnProceso);
        incidenciasResueltas = new Label("Resueltas: " + totalIncidenciasResueltas);

        HBox estadoIncidencias = new HBox(10,
            totalIncidencias, 
            incidenciasAbiertas, 
            incidenciasEnProceso, 
            incidenciasResueltas
        );

        estadoIncidencias.setAlignment(Pos.CENTER);

        mensaje = new Label(textoMensaje);
        mensaje.setWrapText(true);
        mensaje.setMaxWidth(Double.MAX_VALUE);
        mensaje.setAlignment(Pos.CENTER);

        VBox.setVgrow(listaIncidencias, Priority.ALWAYS);
        
        botonNuevaIncidencia.setOnAction(e -> nuevaIncidencia(stage));

        VBox raiz = new VBox(14, 
            incidencias,
            listaIncidencias,
            estadoIncidencias,
            mensaje
        );

        raiz.setPadding(new Insets(20));

        escenaIncidencias(stage, raiz);
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

        Button crearIncidencia = new Button("Guardar Incidencia");
        crearIncidencia.setDefaultButton(false);

        Button cancelar = new Button("Cerrar");
        cancelar.setDefaultButton(true);
        
        HBox botonNuevaIncidenciaBox = new HBox (10, 
            cancelar, 
            crearIncidencia
        );

        botonNuevaIncidenciaBox.setAlignment(Pos.CENTER);

        VBox nuevaIncidenciaBox = new VBox(14,
            new Label("Titulo:"),
            campoNuevaIncidencia,
            new Label("Descripción:"),
            campoDescripcion,
            new Label("Prioridad:"),
            comboNuevaPrioridad,
            new Label("Estado:"),
            comboNuevaEstado,
            botonNuevaIncidenciaBox
        );

        nuevaIncidenciaBox.setPadding(new Insets(20));

        crearIncidencia.setOnAction(e -> {
            totalIncidenciasHechas++;

            if (comboNuevaEstado.getValue().equals("Abierta")) {
                totalIncidenciasAbiertas++;
            } else if (comboNuevaEstado.getValue().equals("En Proceso")) {
                totalIncidenciasEnProceso++;
            } else if (comboNuevaEstado.getValue().equals("Resuelta")) {
                totalIncidenciasResueltas++;
            }
            
            mostrarMensaje(new Label("Incidencia creada correctamente"));
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