package ies.lazaro;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage escenarioPrincipal;
    private GestorIncidencias gestorIncidencias;

    @Override
    public void start(Stage stage) {
        this.escenarioPrincipal = stage;
        this.gestorIncidencias = new GestorIncidencias();

        mostrarVistaPrincipal("Incidencias cargadas con éxito");
    }

    public void mostrarVistaPrincipal(String mensaje) {
        VistaPrincipal vistaPrincipal = new VistaPrincipal(this, gestorIncidencias);
        Scene escena = vistaPrincipal.crearEscena(escenarioPrincipal, mensaje);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();
    }

    public void mostrarVistaNuevaIncidencia() {
        VistaNuevaIncidencia vistaNuevaIncidencia = new VistaNuevaIncidencia(this, gestorIncidencias);
        Scene escena = vistaNuevaIncidencia.crearEscena(escenarioPrincipal);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();
    }

    public void mostrarVistaDetalles(Incidencia incidencia) {
        VistaDetallesIncidencia vistaDetalles = new VistaDetallesIncidencia(this, incidencia);
        Scene escena = vistaDetalles.crearEscena(escenarioPrincipal);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
