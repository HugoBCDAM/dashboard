package ies.lazaro;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VistaDetallesIncidencia {

    private final Main aplicacion;
    private final Incidencia incidencia;
    private ComboBox<String> comboCambiarEstado;

    public VistaDetallesIncidencia(Main aplicacion, Incidencia incidencia) {
        this.aplicacion = aplicacion;
        this.incidencia = incidencia;
    }

    public Scene crearEscena(Stage escenario) {
        HBox panelDetalles = crearPanelDetalles();
        HBox barraAcciones = crearBarraAcciones();

        VBox.setMargin(panelDetalles, new Insets(0, 0, 20, 0));
        VBox contenedorPrincipal = new VBox(10, panelDetalles, barraAcciones);
        contenedorPrincipal.setPadding(new Insets(20));

        escenario.setTitle("Detalles de la Incidencia");
        escenario.setResizable(false);

        return new Scene(contenedorPrincipal, 435, 230);
    }

    private HBox crearPanelDetalles() {
        Label etiquetaId = new Label("ID:");
        Label etiquetaIdValor = new Label(String.valueOf(incidencia.getId()));

        Label etiquetaTitulo = new Label("Titulo:");
        Label etiquetaTituloValor = new Label(incidencia.getTitulo());

        Label etiquetaDescripcion = new Label("Descripción:");
        Label etiquetaDescripcionValor = new Label(incidencia.getDescripcion());

        Label etiquetaPrioridad = new Label("Prioridad:");
        Label etiquetaPrioridadValor = new Label(incidencia.getPrioridad());

        Label etiquetaEstado = new Label("Estado:");
        Label etiquetaEstadoValor = new Label(incidencia.getEstado());

        VBox columnaCabeceras = new VBox(10,
            etiquetaId,
            etiquetaTitulo,
            etiquetaDescripcion,
            etiquetaPrioridad,
            etiquetaEstado
        );
        columnaCabeceras.setStyle(CSS.ESTILO_CABECERAS_DETALLES);

        VBox columnaValores = new VBox(10,
            etiquetaIdValor,
            etiquetaTituloValor,
            etiquetaDescripcionValor,
            etiquetaPrioridadValor,
            etiquetaEstadoValor
        );
        columnaValores.setStyle(CSS.ESTILO_VALORES_DETALLES);
        columnaValores.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(columnaValores, Priority.ALWAYS);

        HBox panelDetalles = new HBox(columnaCabeceras, columnaValores);
        panelDetalles.setMaxWidth(Double.MAX_VALUE);

        return panelDetalles;
    }

    private HBox crearBarraAcciones() {
        Label etiquetaCambiarEstado = new Label("Cambiar Estado: ");

        comboCambiarEstado = new ComboBox<>();
        comboCambiarEstado.getItems().addAll("Abierta", "En Proceso", "Resuelta");
        comboCambiarEstado.setValue(incidencia.getEstado());

        Button botonGuardarCambios = new Button("Guardar");
        botonGuardarCambios.setDefaultButton(true);
        botonGuardarCambios.setStyle(CSS.ESTILO_BOTON_GUARDAR_DETALLES);
        botonGuardarCambios.setOnMouseEntered(e -> botonGuardarCambios.setStyle(CSS.ESTILO_BOTON_GUARDAR_DETALLES_RATON_ENCIMA));
        botonGuardarCambios.setOnMouseExited(e -> botonGuardarCambios.setStyle(CSS.ESTILO_BOTON_GUARDAR_DETALLES_RATON_SALIDA));
        botonGuardarCambios.setOnAction(e -> guardarCambios());

        Button botonCerrarDetalles = new Button("Cerrar");
        botonCerrarDetalles.setCancelButton(true);
        botonCerrarDetalles.setStyle(CSS.ESTILO_BOTON_CERRAR_DETALLES);
        botonCerrarDetalles.setOnMouseEntered(e -> botonCerrarDetalles.setStyle(CSS.ESTILO_BOTON_CERRAR_DETALLES_RATON_ENCIMA));
        botonCerrarDetalles.setOnMouseExited(e -> botonCerrarDetalles.setStyle(CSS.ESTILO_BOTON_CERRAR_DETALLES_RATON_SALIDA));
        botonCerrarDetalles.setOnAction(e -> cerrarDetalles());

        HBox.setMargin(botonCerrarDetalles, new Insets(0, 0, 0, 30));

        return new HBox(10,
            etiquetaCambiarEstado,
            comboCambiarEstado,
            botonCerrarDetalles,
            botonGuardarCambios
        );
    }

    private void guardarCambios() {
        String estadoActual = incidencia.getEstado();
        String nuevoEstado = comboCambiarEstado.getValue();

        if (nuevoEstado != null && nuevoEstado.equalsIgnoreCase(estadoActual)) {
            aplicacion.mostrarVistaPrincipal("No se actualizó el estado porque has seleccionado el mismo estado.");
        } else {
            incidencia.setEstado(nuevoEstado);
            aplicacion.mostrarVistaPrincipal("El estado de la incidencia se actualizó correctamente.");
        }
    }

    private void cerrarDetalles() {
        aplicacion.mostrarVistaPrincipal("Los detalles de la incidencia se han cerrado correctamente");
    }
}
