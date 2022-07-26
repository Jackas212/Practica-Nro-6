/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package vista;

import controlador.escuela.EscuelaController;
import controlador.tda.lista.ListaEnlazada;
import controlador.tda.lista.exception.PosicionException;
import java.util.Arrays;
import javax.swing.JOptionPane;
import vista.TablaModel.ModeloTablaEscuela;

/**
 *
 * @author john
 */
public class Frm_Principal extends javax.swing.JDialog {

    private EscuelaController escuelaCont;
    private ModeloTablaEscuela modEscuela;

    /**
     * Creates new form Frm_Principal
     */
    public Frm_Principal(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        jPanel3.setVisible(false);
        jPanel4.setVisible(false);
        jPanel5.setVisible(false);
    }

    private void limpiar() {
        jTNro.setText("");
        jTKm.setText("");
        jTDireccion.setText("");
        jTCiudad.setText("");
        jTNombre.setText("");
        escuelaCont.setEscuela(null);
        jButton1.setEnabled(false);
        cargarTabla();
    }

    private void crear() {

        Integer nro = Integer.parseInt(jTNro.getText());
        escuelaCont = new EscuelaController(nro);
        jPanel3.setVisible(true);
        jPanel4.setVisible(true);
        jPanel5.setVisible(true);
        String[] aux = {""};
        jlista.setListData(aux);
        cargarTabla();
        cargarComboVertice();
    }

    private void cargarTabla() {
        modEscuela.setGrafo(escuelaCont.getGend());
        modEscuela.fireTableStructureChanged();
        modEscuela.fireTableDataChanged();
        jTable1.setModel(modEscuela);
        jTable1.updateUI();
    }

    private void cargarVista() {
        Integer fila = -1;
        fila = jTable1.getSelectedRow();
        if (fila >= 0) {
            try {
                escuelaCont.setEscuela(escuelaCont.getGend().obtenerEtiqueta(fila + 1));
                jTNombre.setText(escuelaCont.getEscuela().getNombre());
                jTCiudad.setText(String.valueOf(escuelaCont.getEscuela().getCiudad()));
                jTDireccion.setText(String.valueOf(escuelaCont.getEscuela().getDireccion()));
                jButton1.setEnabled(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Escoja una fila de la tabla", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificar() {
        if (jTNombre.getText().trim().length() == 0
                || jTCiudad.getText().trim().length() == 0
                || jTDireccion.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Datos incompletos", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                Integer pos = escuelaCont.getGend().obtenerCodigo(escuelaCont.getEscuela());
                escuelaCont.getEscuela().setNombre(jTNombre.getText());
                escuelaCont.getEscuela().setCiudad(jTCiudad.getText());
                escuelaCont.getEscuela().setDireccion(jTDireccion.getText());
                if (escuelaCont.getGend().modificar(escuelaCont.getGend().obtenerEtiqueta(pos), escuelaCont.getEscuela())) {
                    cargarComboVertice();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "SE HA MODIFICADO CORRECTAMENTE!!", "OK", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "NO SE PUDO MODIFICAR", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cargarComboVertice() {
        jCOrigen.removeAllItems();
        jCDestino.removeAllItems();
        try {
            for (int i = 1; i <= escuelaCont.getGend().numVertices(); i++) {
                jCOrigen.addItem(escuelaCont.getGend().obtenerEtiqueta(i).toString());
                jCDestino.addItem(escuelaCont.getGend().obtenerEtiqueta(i).toString());
            }
        } catch (Exception e) {
            System.out.println("ERROR EN CARGAR COMBO");
        }
    }

    private void adyacencia() {
        Integer origen = (jCOrigen.getSelectedIndex() + 1);
        Integer destino = (jCDestino.getSelectedIndex() + 1);
        Integer distancia = Integer.parseInt(jTKm.getText());
        if (origen == destino) {
            JOptionPane.showMessageDialog(null, "ESCOJA CLIENTES DIFERENTES", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                escuelaCont.getGend().insertarAristaE(escuelaCont.getGend().obtenerEtiqueta(origen), escuelaCont.getGend().obtenerEtiqueta(destino), (double) distancia);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public ListaEnlazada<Object> recorridoAnchura(int nodoI) {
//Lista donde guardo los nodos recorridos
        ListaEnlazada<Object> recorridos = new ListaEnlazada<>();
//El nodo inicial ya está visitado
        ListaEnlazada<Boolean> visitadoAnchura = new ListaEnlazada<>();
        visitadoAnchura.insertarCabecera(true);
//Cola de visitas de los nodos adyacentes
        ListaEnlazada<Integer> cola = new ListaEnlazada<Integer>();
//Se lista el nodo como ya recorrido
        recorridos.insertarCabecera(nodoI);
//Se agrega el nodo a la cola de visitas
        cola.insertarCabecera(nodoI);
//Hasta que visite todos los nodos
        try {
            while (!cola.estaVacia()) {
                int j = cola.eliminarDato(0); //Se saca el primero nodo de la cola
//Se busca en la matriz que representa el grafo los nodos adyacentes
                for (int i = 0; i < escuelaCont.getGend().numVertices(); i++) {
//Si es un nodo adyacente y no está visitado entonces
                    Object obj = 1;
                    if (escuelaCont.getGend().adycentes(i) == obj) {
                        cola.insertarCabecera(i);//Se agrega a la cola de visitas
                        recorridos.insertarCabecera(i);//Se marca como recorrido
                        visitadoAnchura.insertarCabecera(true);//Se marca como visitado
                    }
                }
            }
        } catch (Exception e) {
        }

        return recorridos;//Devuelvo el recorrido del grafo en anchura
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTNro = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTDireccion = new javax.swing.JTextArea();
        jTCiudad = new javax.swing.JTextField();
        jTNombre = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jCOrigen = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jCDestino = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jTKm = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jlista = new javax.swing.JList<>();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        jPanel1.setLayout(null);

        jLabel1.setText("Ingreso Escuela");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(10, 70, 120, 18);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(null);

        jLabel2.setText("Nro.Escuelas:");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(10, 10, 110, 20);
        jPanel2.add(jTNro);
        jTNro.setBounds(110, 10, 190, 24);

        jButton1.setText("Generar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);
        jButton1.setBounds(350, 10, 83, 24);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(10, 10, 680, 50);

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setLayout(null);

        jLabel3.setText("Nombre:");
        jPanel3.add(jLabel3);
        jLabel3.setBounds(10, 20, 70, 18);

        jLabel4.setText("Ciudad:");
        jPanel3.add(jLabel4);
        jLabel4.setBounds(10, 60, 70, 18);

        jLabel5.setText("Direccion:");
        jPanel3.add(jLabel5);
        jLabel5.setBounds(10, 100, 90, 18);

        jTDireccion.setColumns(20);
        jTDireccion.setRows(5);
        jScrollPane1.setViewportView(jTDireccion);

        jPanel3.add(jScrollPane1);
        jScrollPane1.setBounds(80, 100, 274, 96);
        jPanel3.add(jTCiudad);
        jTCiudad.setBounds(80, 60, 270, 24);
        jPanel3.add(jTNombre);
        jTNombre.setBounds(80, 20, 270, 24);

        jButton2.setText("Editar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2);
        jButton2.setBounds(140, 210, 72, 24);

        jPanel1.add(jPanel3);
        jPanel3.setBounds(10, 90, 380, 250);

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setLayout(null);

        jLabel6.setText("Origen:");
        jPanel4.add(jLabel6);
        jLabel6.setBounds(10, 10, 60, 18);

        jCOrigen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel4.add(jCOrigen);
        jCOrigen.setBounds(60, 10, 120, 24);

        jLabel7.setText("Destino:");
        jPanel4.add(jLabel7);
        jLabel7.setBounds(10, 40, 70, 18);

        jCDestino.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel4.add(jCDestino);
        jCDestino.setBounds(70, 40, 120, 24);

        jLabel8.setText("Distancia (Km):");
        jPanel4.add(jLabel8);
        jLabel8.setBounds(10, 80, 110, 18);
        jPanel4.add(jTKm);
        jTKm.setBounds(120, 80, 64, 24);

        jButton3.setText("Agregar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton3);
        jButton3.setBounds(230, 40, 81, 24);

        jPanel1.add(jPanel4);
        jPanel4.setBounds(10, 350, 380, 120);

        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel5.setLayout(null);

        jlista.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jlista);

        jPanel5.add(jScrollPane3);
        jScrollPane3.setBounds(10, 20, 242, 130);

        jButton4.setText("Anchura");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton4);
        jButton4.setBounds(270, 30, 90, 24);

        jButton5.setText("Profundidad");
        jPanel5.add(jButton5);
        jButton5.setBounds(260, 70, 110, 24);

        jButton6.setText("Floyd");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton6);
        jButton6.setBounds(270, 110, 83, 24);

        jPanel1.add(jPanel5);
        jPanel5.setBounds(10, 480, 380, 160);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(400, 90, 300, 550);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 700, 650);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        crear();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        modificar();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        adyacencia();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        recorridoAnchura(jCOrigen.getSelectedIndex());
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frm_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Frm_Principal dialog = new Frm_Principal(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jCDestino;
    private javax.swing.JComboBox<String> jCOrigen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTCiudad;
    private javax.swing.JTextArea jTDireccion;
    private javax.swing.JTextField jTKm;
    private javax.swing.JTextField jTNombre;
    private javax.swing.JTextField jTNro;
    private javax.swing.JTable jTable1;
    private javax.swing.JList<String> jlista;
    // End of variables declaration//GEN-END:variables
}
