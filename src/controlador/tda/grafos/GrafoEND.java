/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.tda.grafos;

import controlador.tda.grafos.expetion.VerticeExeption;

/**
 *
 * @author sebastian
 */
public class GrafoEND<E> extends GrafoDE<E> {

    public GrafoEND(Integer numVertice, Class clazz) {
        super(numVertice, clazz);
    }

    @Override
    public void insertarArista(Integer i, Integer j, Double peso) throws VerticeExeption {
        if (i > 0 && j > 0 && i <= numV && j <= numV) {
            Object[] existe = existeArista(i, j);
            if (!((Boolean) existe[0])) {
                numA++;
                listaAdycente[i].insertarCabecera(new Adycencia(j, peso));
                listaAdycente[j].insertarCabecera(new Adycencia(i, peso));
            }
        } else {
            throw new VerticeExeption("Algun vertice ingreado no existe GND");
        }
    }

    @Override
    public void insertarAristaE(E i, E j, Double peso) throws Exception {
        insertarArista(obtenerCodigo(i), obtenerCodigo(j), peso);
        insertarArista(obtenerCodigo(j), obtenerCodigo(i), peso);
    }

}
