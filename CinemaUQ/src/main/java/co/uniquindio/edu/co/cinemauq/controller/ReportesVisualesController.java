package co.uniquindio.edu.co.cinemauq.controller;

import co.uniquindio.edu.co.cinemauq.Creacionales.CinemaUQ;
import co.uniquindio.edu.co.cinemauq.Comportamentales.StatePagada;
import co.uniquindio.edu.co.cinemauq.Model.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReportesVisualesController {
    @FXML private ComboBox<String> cbTipoGrafico,cbTipoVisualizacion;
    @FXML private DatePicker dpFechaInicio,dpFechaFin;
    @FXML private Label lblSinGrafico;
    @FXML private StackPane panelGrafico;
    @FXML public void initialize() {
        cbTipoGrafico.setItems(FXCollections.observableArrayList("Ventas por fecha", "Entradas por película", "Productos vendidos", "Recargas por fecha", "Ocupación por sala"));
        cbTipoVisualizacion.setItems(FXCollections.observableArrayList("Barras", "Pastel", "Líneas"));
        cbTipoGrafico.getSelectionModel().selectFirst();
        cbTipoVisualizacion.getSelectionModel().selectFirst();
        dpFechaInicio.setValue(LocalDate.now().minusMonths(1));
        dpFechaFin.setValue(LocalDate.now());
    }
    @FXML private void generarGrafico() {
        LocalDate inicio = dpFechaInicio.getValue(), fin = dpFechaFin.getValue();
        if (inicio == null || fin == null || inicio.isAfter(fin)) { Navegacion.mensaje("Selecciona un periodo válido."); return; }
        Map<String,Double> datos = new LinkedHashMap<>();
        String tipo = cbTipoGrafico.getValue();
        if (tipo.equals("Ocupación por sala")) {
            Map<String,Integer> capacidad = new LinkedHashMap<>();
            Map<String,Integer> ocupados = new LinkedHashMap<>();
            for (Funcion f : CinemaUQ.getInstance().getFunciones()) {
                if (!f.getFecha().isBefore(inicio) && !f.getFecha().isAfter(fin)) {
                    String sala = "Sala " + f.getSala().getNumSala();
                    capacidad.merge(sala,f.getSala().getAsientos().size(),Integer::sum);
                    ocupados.merge(sala,f.getAsientosReservados().size(),Integer::sum);
                }
            }
            for (String sala : capacidad.keySet()) {
                int total = capacidad.get(sala);
                if (total > 0) datos.put(sala, 100.0 * ocupados.getOrDefault(sala,0) / total);
            }
        } else if (tipo.equals("Recargas por fecha")) {
            for (Cliente c : CinemaUQ.getInstance().getClientes()) {
                for (Movimiento m : c.getTarjeta().getMovimientos()) {
                    LocalDate d = m.fecha().toLocalDate();
                    if (m.tipo() == TipoMovimiento.RECARGA && !d.isBefore(inicio) && !d.isAfter(fin))
                        datos.merge(d.toString(),m.valor(),Double::sum);
                }
            }
        } else {
            for (Compra c : CinemaUQ.getInstance().getCompras()) {
                LocalDate d = c.getFechaHora().toLocalDate();
                if (!(c.getEstado() instanceof StatePagada) || d.isBefore(inicio) || d.isAfter(fin)) continue;
                if (tipo.equals("Ventas por fecha")) datos.merge(d.toString(), c.getTotal(),Double::sum);
                else if (tipo.equals("Entradas por película")) {
                    for (Entrada entrada : c.getEntradas()) datos.merge(entrada.getFuncion().getPelicula().getNombre(),1.0,Double::sum);
                } else if (tipo.equals("Productos vendidos")) {
                    for (var producto : c.getProductos()) {
                        String nombre = producto instanceof co.uniquindio.edu.co.cinemauq.Estructurales.ProductoIndividual p ? p.getNombre()
                            : producto instanceof co.uniquindio.edu.co.cinemauq.Estructurales.Combo combo ? combo.getNombre() : "Producto";
                        datos.merge(nombre,1.0,Double::sum);
                    }
                }
            }
        }
        panelGrafico.getChildren().clear();
        if (datos.isEmpty()) { lblSinGrafico.setText("No hay datos para ese reporte."); lblSinGrafico.setVisible(true); return; }
        lblSinGrafico.setVisible(false);
        String visualizacion = cbTipoVisualizacion.getValue();
        if ("Pastel".equals(visualizacion)) {
            var lista = FXCollections.<PieChart.Data>observableArrayList();
            datos.forEach((nombre,valor) -> lista.add(new PieChart.Data(nombre,valor)));
            PieChart pastel = new PieChart(lista); pastel.setTitle(tipo); panelGrafico.getChildren().add(pastel);
        } else {
            CategoryAxis ejeX = new CategoryAxis(); NumberAxis ejeY = new NumberAxis();
            XYChart<String,Number> grafico = "Líneas".equals(visualizacion) ? new LineChart<>(ejeX,ejeY) : new BarChart<>(ejeX,ejeY);
            XYChart.Series<String,Number> serie = new XYChart.Series<>(); serie.setName(tipo);
            datos.forEach((nombre,valor) -> serie.getData().add(new XYChart.Data<>(nombre,valor)));
            grafico.getData().add(serie); grafico.setTitle(tipo); panelGrafico.getChildren().add(grafico);
        }
    }
    @FXML private void volverMenu(ActionEvent e) { Navegacion.abrir(e,"menu-administrador-view.fxml"); }
}
