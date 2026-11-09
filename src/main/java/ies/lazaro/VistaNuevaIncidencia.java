package ies.lazaro;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VistaNuevaIncidencia {

    private final Main aplicacion;
    private final GestorIncidencias gestor;

    private TextField campoTitulo;
    private TextArea campoDescripcion;
    private ComboBox<String> comboEstado;
    private ComboBox<String> comboPrioridad;
    private Label etiquetaAviso;

    public VistaNuevaIncidencia(Main aplicacion, GestorIncidencias gestor) {
        this.aplicacion = aplicacion;
        this.gestor = gestor;
    }

    public Scene crearEscena(Stage escenario) {
        campoTitulo = new TextField();
        campoTitulo.setPromptText("Escribe la nueva incidencia");
        HBox.setHgrow(campoTitulo, Priority.ALWAYS);

        campoDescripcion = new TextArea();
        campoDescripcion.setPrefHeight(300);
        campoDescripcion.setWrapText(true);

        HBox selectoresEstadoPrioridad = crearSelectoresEstadoPrioridad();
        HBox barraBotones = crearBotonesAccion();

        etiquetaAviso = new Label();
        etiquetaAviso.setStyle(CSS.ESTILO_LABEL_AVISO_FORMULARIO);

        VBox contenedorFormulario = new VBox(14,
            new Label("Titulo:"),
            campoTitulo,
            new Label("Descripción:"),
            campoDescripcion,
            selectoresEstadoPrioridad,
            etiquetaAviso,
            barraBotones
        );
        contenedorFormulario.setPadding(new Insets(20));

        escenario.setTitle("Nueva Incidencia");
        escenario.setResizable(false);

        return new Scene(contenedorFormulario, 600, 500);
    }

    private HBox crearSelectoresEstadoPrioridad() {
        comboEstado = new ComboBox<>();
        comboEstado.getItems().addAll("Abierta", "En Proceso", "Resuelta");
        comboEstado.setValue("Abierta");

        comboPrioridad = new ComboBox<>();
        comboPrioridad.getItems().addAll("Alta", "Media", "Baja");
        comboPrioridad.setValue("Media");

        HBox.setMargin(comboEstado, new Insets(0, 60, 0, 0));

        HBox selectores = new HBox(10,
            new Label("Estado:"),
            comboEstado,
            new Label("Prioridad:"),
            comboPrioridad
        );
        selectores.setAlignment(Pos.CENTER);
        return selectores;
    }

    private HBox crearBotonesAccion() {
        Button botonGuardar = new Button("Guardar");
        botonGuardar.setDefaultButton(true);
        botonGuardar.setStyle(CSS.ESTILO_BOTON_CREAR_INCIDENCIA_FORMULARIO);
        botonGuardar.setOnMouseEntered(e -> botonGuardar.setStyle(CSS.ESTILO_BOTON_CREAR_INCIDENCIA_FORMULARIO_RATON_ENCIMA));
        botonGuardar.setOnMouseExited(e -> botonGuardar.setStyle(CSS.ESTILO_BOTON_CREAR_INCIDENCIA_FORMULARIO_RATON_SALIDA));
        botonGuardar.setOnAction(e -> guardarIncidencia());

        Button botonCancelar = new Button("Cerrar");
        botonCancelar.setStyle(CSS.ESTILO_BOTON_CANCELAR_INCIDENCIA);
        botonCancelar.setOnMouseEntered(e -> botonCancelar.setStyle(CSS.ESTILO_BOTON_CANCELAR_INCIDENCIA_RATON_ENCIMA));
        botonCancelar.setOnMouseExited(e -> botonCancelar.setStyle(CSS.ESTILO_BOTON_CANCELAR_INCIDENCIA_RATON_SALIDA));
        botonCancelar.setOnAction(e -> cancelar());

        HBox.setMargin(botonCancelar, new Insets(0, 425, 0, 0));

        HBox cajaBotones = new HBox(10, botonCancelar, botonGuardar);
        cajaBotones.setAlignment(Pos.CENTER);
        return cajaBotones;
    }

    private void guardarIncidencia() {
        String titulo = campoTitulo.getText().trim();
        String descripcion = campoDescripcion.getText().trim();

        if (titulo.isEmpty() || descripcion.isEmpty()) {
            etiquetaAviso.setText("Escriba el título y la descripción de la incidencia.");
            return;
        }

        int nuevoId = gestor.generarSiguienteId();
        Incidencia nuevaIncidencia = new Incidencia(
            nuevoId,
            titulo,
            descripcion,
            comboEstado.getValue(),
            comboPrioridad.getValue()
        );

        gestor.agregarIncidencia(nuevaIncidencia);
        aplicacion.mostrarVistaPrincipal("Nueva incidencia creada con éxito");
    }

    private void cancelar() {
        aplicacion.mostrarVistaPrincipal("Creación de incidencia cancelada");
    }
}
