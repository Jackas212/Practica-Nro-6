/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.tda.grafos;

/**
 *
 * @author sebastian
 */
public class Adycencia {
    private Integer destino;
    private Double peso;

    public Adycencia(Integer destino, Double peso) {
        this.destino = destino;
        this.peso = peso;
    }

    public Integer getDestino() {
        return destino;
    }

    public void setDestino(Integer destino) {
        this.destino = destino;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    @Override
    public String toString() {
        return "destino "+destino + " peso "+peso; //To change body of generated methods, choose Tools | Templates.
    }
    
}
