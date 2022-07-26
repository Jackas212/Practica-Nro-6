/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.tda.grafos;

import controlador.tda.grafos.expetion.VerticeExeption;
import controlador.tda.lista.ListaEnlazada;

/**
 *
 * @author sebastian
 */
public class GrafoD extends Grafo {
    protected Integer numV;
    protected Integer numA;
    protected ListaEnlazada<Adycencia> listaAdycente[];

    public GrafoD(Integer numV) {
        this.numV = numV;
        this.numA = 0;
        listaAdycente=new ListaEnlazada[numV + 1];
        for(int i = 1; i <= this.numV; i++) {
            listaAdycente[i] = new ListaEnlazada<>();
        }
    }

    @Override
    public Integer numVertices() {
        return this.numV;
    }

    @Override
    public Integer numAristas() {
        return this.numA;
    }
    /**
     * Permite verificar si existe una conexion entre vertices
     * @param i Vertice inicial
     * @param f Vertice final
     * @return Un arreglo de objetos. El la posicion 0 devuelve un Boolean, en la  posicion 1 devuelve el peso
     * @throws VerticeExeption Si existe un vertice fuera del grafo
     */
    @Override
    public Object[] existeArista(Integer i, Integer f) throws VerticeExeption {        
        
        Object[] resultado = {Boolean.FALSE, Double.NaN};
        if(i > 0 && f > 0 && i <= numV && f <= numV) {
            ListaEnlazada<Adycencia> lista = listaAdycente[i];
            for(int k = 0; k < lista.getSize(); k++) {
                try {
                    Adycencia aux = lista.obtenerDato(k);
                    if(aux.getDestino().intValue() == f.intValue()){                        
                        resultado[0] = Boolean.TRUE;
                        resultado[1] = aux.getPeso();
                        break;
                    }
                } catch (Exception e) {
                }
            }
            return resultado;
        } else
            throw new VerticeExeption("Algun vertice ingreado no existe");
        
    }

    @Override
    public Double pesoArista(Integer i, Integer f) throws VerticeExeption {
        Double peso = Double.NaN;
        Object [] existe = existeArista(i, f);
        if(((Boolean)existe[0])) {
            peso = (Double)existe[1];
        } 
        return peso;
    }

    @Override
    public void insertarArista(Integer i, Integer j) throws VerticeExeption {
        insertarArista(i, j, Double.NaN);
    }
    //V 4 ----- V 5 
    @Override
    public void insertarArista(Integer i, Integer j, Double peso) throws VerticeExeption {
        if(i > 0 && j > 0 && i <= numV && j <= numV) {
            Object [] existe = existeArista(i, j);
            if(!((Boolean)existe[0])) {
                numA++;
                listaAdycente[i].insertarCabecera(new Adycencia(j, peso));
            }
        } else 
            throw new VerticeExeption("Algun vertice ingreado no existe");
    }//creo grafo de 5 vertices

    @Override
    public ListaEnlazada<Adycencia> adycentes(Integer i) throws VerticeExeption {
        return listaAdycente[i];
    }
    
       
}
