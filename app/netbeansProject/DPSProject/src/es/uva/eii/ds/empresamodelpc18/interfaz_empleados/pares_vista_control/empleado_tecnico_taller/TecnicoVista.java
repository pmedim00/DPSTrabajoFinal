package es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.empleado_tecnico_taller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Vista de tecnico
 * 
 */
public class TecnicoVista extends javax.swing.JFrame {
    
    private TecnicoCtrlVista controlador;
    
    /**
     * Creates new form TecnicoVista
     */
    public TecnicoVista() {
        initComponents();
        controlador = new TecnicoCtrlVista(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        usuarioIdentificado = new javax.swing.JLabel();
        regMontaje = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        usuarioIdentificado.setText("Usuario identificado: Tecnico del taller");
        jPanel3.add(usuarioIdentificado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, -1));

        regMontaje.setText("Registrar montaje PC");
        regMontaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regMontajeActionPerformed(evt);
            }
        });
        jPanel3.add(regMontaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, 350, 71));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void regMontajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regMontajeActionPerformed
        try {
            controlador.procesarRegistrarMontajePc();
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            Logger.getLogger(TecnicoVista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_regMontajeActionPerformed

 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel3;
    private javax.swing.JButton regMontaje;
    private javax.swing.JLabel usuarioIdentificado;
    // End of variables declaration//GEN-END:variables
}
