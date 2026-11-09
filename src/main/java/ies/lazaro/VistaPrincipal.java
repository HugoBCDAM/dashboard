package ies.lazaro;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VistaPrincipal {

    private final Main aplicacion;
    private final GestorIncidencias gestor;

    private TableView<Incidencia> tablaIncidencias;
    private TextField campoBuscar;
    private ComboBox<String> comboFiltroEstado;
    private Label etiquetaTotal;
    private Label etiquetaAbiertas;
    private Label etiquetaEnProceso;
    private Label etiquetaResueltas;
    private Label etiquetaMensaje;

    public VistaPrincipal(Main aplicacion, GestorIncidencias gestor) {
        this.aplicacion = aplicacion;
        this.gestor = gestor;
    }

    public Scene crearEscena(Stage escenario, String mensajeInicial) {
        HBox barraSuperior = crearBarraSuperior();
        tablaIncidencias = crearTablaIncidencias();
        VBox panelInferior = crearBarraEstadoYMensaje(mensajeInicial);

        VBox raiz = new VBox(14, barraSuperior, tablaIncidencias, panelInferior);
        raiz.setPadding(new Insets(20));

        escenario.setTitle("Dashboard de Incidencias");
        escenario.setResizable(true);

        return new Scene(raiz, 800, 500);
    }

    private HBox crearBarraSuperior() {
        campoBuscar = new TextField();
        campoBuscar.setPromptText("Escribe una incidencia");
        HBox.setHgrow(campoBuscar, Priority.ALWAYS);

        Button botonBuscar = new Button("Buscar");
        botonBuscar.setDefaultButton(true);
        botonBuscar.setStyle(CSS.ESTILO_BOTON_BUSCAR);
        botonBuscar.setOnMouseEntered(e -> botonBuscar.setStyle(CSS.ESTILO_BOTON_BUSCAR_RATON_ENCIMA));
        botonBuscar.setOnMouseExited(e -> botonBuscar.setStyle(CSS.ESTILO_BOTON_BUSCAR_RATON_SALIDA));
        botonBuscar.setOnAction(e -> ejecutarBusqueda());

        comboFiltroEstado = new ComboBox<>();
        comboFiltroEstado.getItems().addAll("Abiertas", "En Proceso", "Resueltas", "Todo");
        comboFiltroEstado.setPromptText("Filtrar");
        comboFiltroEstado.setOnAction(e -> aplicarFiltro(comboFiltroEstado.getValue()));

        Button botonNuevaIncidencia = new Button("Nueva Incidencia");
        botonNuevaIncidencia.setStyle(CSS.ESTILO_BOTON_CREAR_INCIDENCIA);
        botonNuevaIncidencia.setOnMouseEntered(e -> botonNuevaIncidencia.setStyle(CSS.ESTILO_BOTON_CREAR_INCIDENCIA_RATON_ENCIMA));
        botonNuevaIncidencia.setOnMouseExited(e -> botonNuevaIncidencia.setStyle(CSS.ESTILO_BOTON_CREAR_INCIDENCIA_RATON_SALIDA));
        botonNuevaIncidencia.setOnAction(e -> aplicacion.mostrarVistaNuevaIncidencia());

        HBox barra = new HBox(10, campoBuscar, botonBuscar, comboFiltroEstado, botonNuevaIncidencia);
        barra.setAlignment(Pos.CENTER);
        return barra;
    }

    private TableView<Incidencia> crearTablaIncidencias() {
        TableView<Incidencia> tabla = new TableView<>();
        tabla.setPrefHeight(280);

        TableColumn<Incidencia, Integer> colId = new TableColumn<>("ID");
        TableColumn<Incidencia, String> colTitulo = new TableColumn<>("Titulo");
        TableColumn<Incidencia, String> colPrioridad = new TableColumn<>("Prioridad");
        TableColumn<Incidencia, String> colEstado = new TableColumn<>("Estado");
        TableColumn<Incidencia, Void> colAcciones = crearColumnaAcciones();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colPrioridad.setCellValueFactory(new PropertyValueFactory<>("prioridad"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        tabla.getColumns().add(colId);
        tabla.getColumns().add(colTitulo);
        tabla.getColumns().add(colPrioridad);
        tabla.getColumns().add(colEstado);
        tabla.getColumns().add(colAcciones);

        tabla.getColumns().forEach(columna -> columna.setStyle("-fx-alignment: CENTER;"));
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        VBox.setVgrow(tabla, Priority.ALWAYS);

        tabla.getItems().setAll(gestor.obtenerTodas());
        return tabla;
    }

    private TableColumn<Incidencia, Void> crearColumnaAcciones() {
        TableColumn<Incidencia, Void> columna = new TableColumn<>("Acciones");
        columna.setCellFactory(param -> new TableCell<Incidencia, Void>() {
            private final Button botonDetalles = new Button("Ver detalles");

            {
                botonDetalles.setStyle(CSS.ESTILO_BOTON_VER_DETALLES);
                botonDetalles.setOnMouseEntered(e -> botonDetalles.setStyle(CSS.ESTILO_BOTON_VER_DETALLES_RATON_ENCIMA));
                botonDetalles.setOnMouseExited(e -> botonDetalles.setStyle(CSS.ESTILO_BOTON_VER_DETALLES_RATON_SALIDA));
                botonDetalles.setOnAction(e -> {
                    Incidencia seleccionada = getTableRow().getItem();
                    if (seleccionada != null) {
                        aplicacion.mostrarVistaDetalles(seleccionada);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(botonDetalles);
                }
                setAlignment(Pos.CENTER);
            }
        });
        return columna;
    }

    private VBox crearBarraEstadoYMensaje(String mensajeInicial) {
        etiquetaTotal = new Label("Total de incidencias: " + gestor.obtenerTotalIncidencias());
        etiquetaTotal.setStyle(CSS.ESTILO_LABEL_TOTAL_INCIDENCIAS);

        etiquetaAbiertas = new Label("Abiertas: " + gestor.obtenerTotalAbiertas());
        etiquetaAbiertas.setStyle(CSS.ESTILO_LABEL_INCIDENCIAS_ABIERTAS);

        etiquetaEnProceso = new Label("En Proceso: " + gestor.obtenerTotalEnProceso());
        etiquetaEnProceso.setStyle(CSS.ESTILO_LABEL_INCIDENCIAS_EN_PROCESO);

        etiquetaResueltas = new Label("Resueltas: " + gestor.obtenerTotalResueltas());
        etiquetaResueltas.setStyle(CSS.ESTILO_LABEL_INCIDENCIAS_RESUELTAS);

        Label separacion1 = new Label(" | ");
        separacion1.setStyle(CSS.ESTILO_LABEL_SEPARACIONES);
        Label separacion2 = new Label(" | ");
        separacion2.setStyle(CSS.ESTILO_LABEL_SEPARACIONES);
        Label separacion3 = new Label(" | ");
        separacion3.setStyle(CSS.ESTILO_LABEL_SEPARACIONES);

        HBox barraContadores = new HBox(10,
            etiquetaTotal, separacion1,
            etiquetaAbiertas, separacion2,
            etiquetaEnProceso, separacion3,
            etiquetaResueltas
        );
        barraContadores.setAlignment(Pos.CENTER);

        etiquetaMensaje = new Label(mensajeInicial != null ? mensajeInicial : "");
        etiquetaMensaje.setWrapText(true);
        etiquetaMensaje.setMaxWidth(Double.MAX_VALUE);
        etiquetaMensaje.setAlignment(Pos.CENTER);
        etiquetaMensaje.setStyle(CSS.ESTILO_LABEL_MENSAJE);

        VBox panel = new VBox(14, barraContadores, etiquetaMensaje);
        panel.setStyle(CSS.ESTILO_REGISTRO_INCIDENCIAS_MENSAJE);
        return panel;
    }

    private void ejecutarBusqueda() {
        String texto = campoBuscar.getText();
        ArrayList<Incidencia> encontradas = gestor.buscarPorTitulo(texto);
        tablaIncidencias.getItems().setAll(encontradas);

        if (encontradas.isEmpty()) {
            etiquetaMensaje.setText("No se han encontrado incidencias con ese texto");
        } else {
            etiquetaMensaje.setText("Incidencias encontradas: " + encontradas.size());
        }
    }

    private void aplicarFiltro(String estado) {
        ArrayList<Incidencia> filtradas = gestor.filtrarPorEstado(estado);
        tablaIncidencias.getItems().setAll(filtradas);
    }

    public void actualizarContadores() {
        etiquetaTotal.setText("Total de incidencias: " + gestor.obtenerTotalIncidencias());
        etiquetaAbiertas.setText("Abiertas: " + gestor.obtenerTotalAbiertas());
        etiquetaEnProceso.setText("En Proceso: " + gestor.obtenerTotalEnProceso());
        etiquetaResueltas.setText("Resueltas: " + gestor.obtenerTotalResueltas());
    }
}
