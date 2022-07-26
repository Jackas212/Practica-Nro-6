/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.tda.grafos.expetion;

/**
 *
 * @author sebastian
 */
public class GrafoConexionException extends Exception {

    /**
     * Creates a new instance of <code>GrafoConexionException</code> without
     * detail message.
     */
    public GrafoConexionException() {
    }

    /**
     * Constructs an instance of <code>GrafoConexionException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public GrafoConexionException(String msg) {
        super(msg);
    }
}
