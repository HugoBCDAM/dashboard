package ies.lazaro;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private TableView<String> listaIncidencias;
    private ComboBox<String> comboEstado;
    private Label totalIncidencias;
    private Label incidenciasAbiertas;
    private Label incidenciasEnProceso;
    private Label incidenciasResueltas;
    private ComboBox<String> comboNuevaEstado;
    private TextArea campoDescripcion;
    private int totalIncidenciasHechas = 0;
    private int totalIncidenciasAbiertas = 0;
    private int totalIncidenciasEnProceso = 0;
    private int totalIncidenciasResueltas = 0;

    @Override 
    public void start(Stage stage) {
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

        VBox raiz = new VBox(14, 
            incidencias,
            listaIncidencias,
            estadoIncidencias
        );

        raiz.setPadding(new Insets(20));

        VBox.setVgrow(listaIncidencias, Priority.ALWAYS);

        botonNuevaIncidencia.setOnAction(e -> nuevaIncidencia(stage));
        
        escenaIncidencias(stage, raiz);
    }

    private void escenaIncidencias(Stage stage, VBox raiz) {
        Scene scene = new Scene(raiz, 800, 500);
        stage.setTitle("Dashboard de Incidencias");
        stage.setScene(scene);
        stage.show();
    }

    public void nuevaIncidencia(Stage stage) {
        TextField campoNuevaIncidencia = new TextField();
        campoNuevaIncidencia.setPromptText("Escribe la nueva incidencia");

        HBox.setHgrow(campoNuevaIncidencia, Priority.ALWAYS);

        comboNuevaEstado = new ComboBox<>();
        comboNuevaEstado.getItems().addAll("Abierta", "En Proceso", "Resuelta");
        comboNuevaEstado.setValue("Abierta");

        campoDescripcion = new TextArea();
        campoDescripcion.setPrefHeight(300);
        campoDescripcion.setWrapText(true);

        totalIncidenciasHechas++;

        if (comboNuevaEstado.getValue().equals("Abierta")) {
            totalIncidenciasAbiertas++;
        } else if (comboNuevaEstado.getValue().equals("En Proceso")) {
            totalIncidenciasEnProceso++;
        } else if (comboNuevaEstado.getValue().equals("Resuelta")) {
            totalIncidenciasResueltas++;
        }

        Button crearIncidencia = new Button("Crear Incidencia");
        crearIncidencia.setDefaultButton(true);

        HBox botonNuevaIncidenciaBox = new HBox(crearIncidencia);
        botonNuevaIncidenciaBox.setAlignment(Pos.CENTER);

        VBox nuevaIncidenciaBox = new VBox(14,
            new Label("Nueva Incidencia:"),
            campoNuevaIncidencia,
            new Label("Estado de la Incidencia:"),
            comboNuevaEstado,
            new Label("Descripción de la Incidencia:"),
            campoDescripcion,
            botonNuevaIncidenciaBox
        );

        nuevaIncidenciaBox.setPadding(new Insets(20));

        crearIncidencia.setOnAction(e -> {
            start(stage);
        });

        Scene nuevaIncidenciaScene = new Scene(nuevaIncidenciaBox, 600, 500);
        stage.setTitle("Nueva Incidencia");
        stage.setScene(nuevaIncidenciaScene);
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}