
package co.uniquindio.edu.co.cinemauq.controller;

import co.uniquindio.edu.co.cinemauq.Reportes.GeneradorReportes;
import co.uniquindio.edu.co.cinemauq.Reportes.Reporte;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class ReportesController {

    @FXML private ComboBox<String> cbTipoReporte;
    @FXML private DatePicker dpFechaInicio;
    @FXML private DatePicker dpFechaFin;
    @FXML private TextArea txtReporte;

    private GeneradorReportes generadorReportes;
    private Reporte reporteActual;

    @FXML
    public void initialize() {
        generadorReportes = new GeneradorReportes();

        cbTipoReporte.getItems().addAll("Ventas", "Entradas", "Ocupacion", "Productos y combos", "Recargas", "Cancelaciones y reembolsos", "Puntos");
        cbTipoReporte.getSelectionModel().selectFirst();
    }

    @FXML
    private void generarReporte() {
        String tipoReporte = cbTipoReporte.getValue();
        LocalDate fechaInicio = dpFechaInicio.getValue();
        LocalDate fechaFin = dpFechaFin.getValue();

        if (tipoReporte == null || fechaInicio == null || fechaFin == null) {
            mostrarMensaje("Complete todos los campos para generar el reporte.");
            return;
        }

        if (fechaInicio.isAfter(fechaFin)) {
            mostrarMensaje("La fecha inicial no puede ser posterior a la fecha final.");
            return;
        }

        switch (tipoReporte) {
            case "Ventas" -> reporteActual = generadorReportes.generarReporteVentas(fechaInicio,fechaFin);
            case "Entradas" -> reporteActual = generadorReportes.generarReporteEntradas(fechaInicio,fechaFin);
            case "Ocupacion" -> reporteActual = generadorReportes.generarReporteOcupacion(fechaInicio,fechaFin);
            case "Productos y combos" -> reporteActual = generadorReportes.generarReporteProductos(fechaInicio,fechaFin);
            case "Recargas" -> reporteActual = generadorReportes.generarReporteRecargas(fechaInicio,fechaFin);
            case "Cancelaciones y reembolsos" -> reporteActual = generadorReportes.generarReporteCancelaciones(fechaInicio,fechaFin);
            case "Puntos" -> reporteActual = generadorReportes.generarReportePuntos(fechaInicio,fechaFin);
            default -> { mostrarMensaje("Selecciona un tipo de reporte."); return; }
        }
        txtReporte.setText(reporteActual.generarTexto());
    }

    @FXML
    private void guardarReporte(ActionEvent event) {
        if (reporteActual == null) {
            mostrarMensaje("Primero debe generar un reporte.");
            return;
        }

        FileChooser selector = new FileChooser();
        selector.setTitle("Guardar reporte");
        selector.setInitialFileName("Reporte_CinemaUQ.txt");

        selector.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Archivos de texto", "*.txt"
                )
        );

        Stage stage = (Stage) ((Node) event.getSource())
                .getScene().getWindow();

        File archivo = selector.showSaveDialog(stage);

        if (archivo != null) {
            try {
                generadorReportes.guardarReporte(
                        reporteActual,
                        archivo.getAbsolutePath()
                );

                mostrarMensaje("Reporte guardado correctamente.");

            } catch (IOException e) {
                mostrarMensaje("No se pudo guardar el reporte.");
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void volverMenuPrincipal(ActionEvent event) {
        Navegacion.abrir(event, "menu-administrador-view.fxml");
    }

    private void mostrarMensaje(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("CinemaUQ");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
