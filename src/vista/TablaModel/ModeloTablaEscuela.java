/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.TablaModel;

import controlador.tda.grafos.GrafoEND;
import javax.swing.table.AbstractTableModel;
import modelo.Escuela;

/**
 *
 * @author john
 */
public class ModeloTablaEscuela extends AbstractTableModel {

    private GrafoEND<Escuela> grafo;

    public GrafoEND<Escuela> getGrafo() {
        return grafo;
    }

    public void setGrafo(GrafoEND<Escuela> grafo) {
        this.grafo = grafo;
    }

    @Override
    public int getRowCount() {
        return grafo.numVertices();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Nombres";
            case 1:
                return "Ciudad";
            case 2:
                return "Direccion";
            default:
                return null;
        }
    }

    @Override
    public Object getValueAt(int arg0, int arg1) {
        try {
            Escuela esc = grafo.obtenerEtiqueta(arg0 + 1);
            switch (arg1) {
                case 0:
                    return (arg0 + 1);
                case 1:
                    return esc.getNombre();
                case 2:
                    return esc.getCiudad();
                case 3:
                    return esc.getDireccion();
                default:
                    return null;
            }
        } catch (Exception e) {
            System.out.println("Error en listado");
            return null;
        }
    }
}
