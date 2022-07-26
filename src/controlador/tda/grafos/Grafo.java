/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.tda.grafos;

import controlador.tda.grafos.expetion.GrafoConexionException;
import controlador.tda.grafos.expetion.VerticeExeption;
import controlador.tda.lista.ListaEnlazada;
import java.util.HashMap;

/**
 *
 * @author sebastian
 */
public abstract class Grafo {

    public abstract Integer numVertices();

    public abstract Integer numAristas();

    public abstract Object[] existeArista(Integer i, Integer f) throws VerticeExeption;

    public abstract Double pesoArista(Integer i, Integer f) throws VerticeExeption;

    public abstract void insertarArista(Integer i, Integer j) throws VerticeExeption;

    public abstract void insertarArista(Integer i, Integer j, Double peso) throws VerticeExeption;

    public abstract ListaEnlazada<Adycencia> adycentes(Integer i) throws VerticeExeption;

    @Override
    public String toString() {
        StringBuilder grafo = new StringBuilder();
        for (int i = 1; i <= numVertices(); i++) {
            grafo.append("VERTICE " + i);
            try {
                ListaEnlazada<Adycencia> lista = adycentes(i);

                for (int j = 0; j < lista.getSize(); j++) {
                    Adycencia aux = lista.obtenerDato(j);
                    if (aux.getPeso().toString().equalsIgnoreCase(String.valueOf(Double.NaN))) {
                        grafo.append(" --- VERTICE DESTINO " + aux.getDestino());
                    } else {
                        grafo.append(" --- VERTICE DESTINO " + aux.getDestino() + " --peso-- " + aux.getPeso());
                    }
                }
                grafo.append("\n");
            } catch (Exception e) {
                System.out.println("Error en toString " + e);

            }
        }
        return grafo.toString(); //To change body of generated methods, choose Tools | Templates.
    }

    private Boolean estaConectado() {
        Boolean bad = true;
        for (int i = 1; i <= numVertices(); i++) {
            try {
                ListaEnlazada<Adycencia> lista = adycentes(i);
                if (lista.getSize() == 0) {
                    bad = false;
                    break;
                }
            } catch (Exception e) {
                System.out.println("Error en toString " + e);
                bad = false;
            }
        }
        return bad;
    }

    private Boolean estaPintado(ListaEnlazada<Integer> lista, Integer vertice) throws Exception {
        Boolean band = false;
        for (int i = 0; i < lista.getSize(); i++) {
            if (lista.obtenerDato(i).intValue() == vertice.intValue()) {
                band = true;
                break;
            }
        }
        return band;
    }

    public ListaEnlazada caminoMinimo(Integer verticeInicial, Integer verticeFinal) throws Exception {
        ListaEnlazada<Integer> camino = new ListaEnlazada();
        if (estaConectado()) {
            ListaEnlazada pesos = new ListaEnlazada();
            Boolean finalizar = false;
            Integer inicial = verticeInicial;
            camino.insertarCabecera(inicial);
            while (!finalizar) {
                System.out.println("***** " + inicial);
                ListaEnlazada<Adycencia> adycencias = adycentes(inicial);
                Integer T = -1;
                Double peso = 100000000.0;
                for (int i = 0; i < adycencias.getSize(); i++) {
                    Adycencia ad = adycencias.obtenerDato(i);
                    if (!estaPintado(camino, ad.getDestino())) {
                        Double pesoArista = ad.getPeso();
                        if (verticeFinal.intValue() == ad.getDestino()) {
                            T = ad.getDestino();
                            peso = ad.getPeso();
                            break;
                        } else if (pesoArista < peso) {
                            T = ad.getDestino();
                            peso = pesoArista;
                        }
                    }
                }
                if (T > -1) {
                    pesos.insertarCabecera(peso);
                    camino.insertarCabecera(T);
                    inicial = T;
                } else {
                    throw new GrafoConexionException("NO SE ENCUENTRA EL CAMINO");
                }
                if (verticeFinal.intValue() == inicial.intValue()) {
                    finalizar = true;
                }
            }
        } else {
            throw new GrafoConexionException("LE GRAFO NO ESTA CONECTADO");
        }

        return camino;
    }

}
