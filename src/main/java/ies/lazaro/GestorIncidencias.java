package ies.lazaro;

import java.util.ArrayList;

public class GestorIncidencias {

    private final ArrayList<Incidencia> listaIncidencias;

    public GestorIncidencias() {
        this.listaIncidencias = new ArrayList<>();
        cargarIncidenciasIniciales();
    }

    private void cargarIncidenciasIniciales() {
        listaIncidencias.add(new Incidencia(
            1,
            "Error en la página de inicio",
            "La página de inicio no carga correctamente.",
            "Abierta",
            "Alta"
        ));

        listaIncidencias.add(new Incidencia(
            2,
            "Problema con el formulario de contacto",
            "El formulario de contacto no envía los mensajes.",
            "En Proceso",
            "Media"
        ));

        listaIncidencias.add(new Incidencia(
            3,
            "Error en la base de datos",
            "Se ha producido un error al acceder a la base de datos.",
            "Resuelta",
            "Alta"
        ));
    }

    public ArrayList<Incidencia> obtenerTodas() {
        return new ArrayList<>(listaIncidencias);
    }

    public void agregarIncidencia(Incidencia nuevaIncidencia) {
        if (nuevaIncidencia != null) {
            listaIncidencias.add(nuevaIncidencia);
        }
    }

    public ArrayList<Incidencia> buscarPorTitulo(String texto) {
        ArrayList<Incidencia> incidenciasEncontradas = new ArrayList<>();
        if (texto == null) {
            return incidenciasEncontradas;
        }

        String textoNormalizado = texto.trim().toLowerCase();
        for (Incidencia incidencia : listaIncidencias) {
            if (incidencia.getTitulo().toLowerCase().contains(textoNormalizado)) {
                incidenciasEncontradas.add(incidencia);
            }
        }
        return incidenciasEncontradas;
    }

    public ArrayList<Incidencia> filtrarPorEstado(String estado) {
        if (estado == null || estado.equalsIgnoreCase("Todo")) {
            return obtenerTodas();
        }

        ArrayList<Incidencia> incidenciasFiltradas = new ArrayList<>();
        for (Incidencia incidencia : listaIncidencias) {
            if (incidencia.getEstado().equalsIgnoreCase(estado)) {
                incidenciasFiltradas.add(incidencia);
            } else if (estado.equalsIgnoreCase("Abiertas") && incidencia.getEstado().equalsIgnoreCase("Abierta")) {
                incidenciasFiltradas.add(incidencia);
            } else if (estado.equalsIgnoreCase("Resueltas") && incidencia.getEstado().equalsIgnoreCase("Resuelta")) {
                incidenciasFiltradas.add(incidencia);
            }
        }
        return incidenciasFiltradas;
    }

    public int obtenerTotalIncidencias() {
        return listaIncidencias.size();
    }

    public int obtenerTotalAbiertas() {
        int contador = 0;
        for (Incidencia incidencia : listaIncidencias) {
            if ("Abierta".equalsIgnoreCase(incidencia.getEstado())) {
                contador++;
            }
        }
        return contador;
    }

    public int obtenerTotalEnProceso() {
        int contador = 0;
        for (Incidencia incidencia : listaIncidencias) {
            if ("En Proceso".equalsIgnoreCase(incidencia.getEstado())) {
                contador++;
            }
        }
        return contador;
    }

    public int obtenerTotalResueltas() {
        int contador = 0;
        for (Incidencia incidencia : listaIncidencias) {
            if ("Resuelta".equalsIgnoreCase(incidencia.getEstado())) {
                contador++;
            }
        }
        return contador;
    }

    public int generarSiguienteId() {
        return listaIncidencias.size() + 1;
    }
}
