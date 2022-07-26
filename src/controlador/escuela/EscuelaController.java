/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.escuela;

import controlador.tda.grafos.GrafoEND;
import modelo.Escuela;

/**
 *
 * @author john
 */
public class EscuelaController {

    private GrafoEND<Escuela> gend;
    private Escuela escuela;

    public EscuelaController(Integer nro_vertices) {
        gend = new GrafoEND<>(nro_vertices, Escuela.class);
        for (int i = 1; i <= nro_vertices; i++) {
            Escuela esc = new Escuela();
            esc.setId(i);
            esc.setNombre("Escuela " + i);
            esc.setCiudad("Ciudad " + i);
            esc.setDireccion("Direccion " + i);
            gend.etiquetarVertice(i, esc);
        }
    }

    public GrafoEND<Escuela> getGend() {
        return gend;
    }

    public void setGend(GrafoEND<Escuela> gend) {
        this.gend = gend;
    }

    public Escuela getEscuela() {
        if (escuela == null) {
            escuela = new Escuela();
        }
        return escuela;
    }

    public void setEscuela(Escuela escuela) {
        this.escuela = escuela;
    }

}
