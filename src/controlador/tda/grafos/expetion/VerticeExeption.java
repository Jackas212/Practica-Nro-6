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
public class VerticeExeption extends Exception {

    /**
     * Creates a new instance of <code>VerticeExeption</code> without detail
     * message.
     */
    public VerticeExeption() {
    }

    /**
     * Constructs an instance of <code>VerticeExeption</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public VerticeExeption(String msg) {
        super(msg);
    }
}
