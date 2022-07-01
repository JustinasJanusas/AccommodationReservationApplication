/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kursinis;

import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;
import ld.ontology.Apartment;
import ld.ontology.Reservation;

/**
 *
 * @author Justinas
 */
public class Gui extends javax.swing.JFrame {


    GuiAgent myAgent;
    public Gui(Buyer a) {
        myAgent = a;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        label1 = new java.awt.Label();
        label2 = new java.awt.Label();
        totalPriceText = new java.awt.Label();
        calendar = new com.toedter.calendar.JCalendar();
        label4 = new java.awt.Label();
        label5 = new java.awt.Label();
        durationTimeText = new javax.swing.JSpinner();
        label6 = new java.awt.Label();
        countryNameText = new java.awt.TextField();
        label7 = new java.awt.Label();
        cityNameText = new java.awt.TextField();
        searchButton = new java.awt.Button();
        reservationButton = new java.awt.Button();
        errorText = new javax.swing.JLabel();
        peopleCountField = new javax.swing.JSpinner();
        label8 = new java.awt.Label();
        cancelButton = new java.awt.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Pavadinimas", "Įmonė", "Aprašas", "Atvykimas", "Išvykimas", "1 nakties kaina"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table.setGridColor(new java.awt.Color(153, 153, 153));
        jScrollPane1.setViewportView(table);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 48, 612, 190));

        label1.setName(""); // NOI18N
        label1.setText("Rastas pigiausias variantas:");
        getContentPane().add(label1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 18, -1, -1));

        label2.setText("Kaina:");
        getContentPane().add(label2, new org.netbeans.lib.awtextra.AbsoluteConstraints(485, 18, -1, -1));

        totalPriceText.setName("totalPriceText"); // NOI18N
        getContentPane().add(totalPriceText, new org.netbeans.lib.awtextra.AbsoluteConstraints(534, 18, 80, -1));
        totalPriceText.getAccessibleContext().setAccessibleDescription("");

        getContentPane().add(calendar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, -1, -1));

        label4.setText("Pasirinkite data:");
        getContentPane().add(label4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, -1));

        label5.setText("Šalis");
        getContentPane().add(label5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 350, -1, -1));

        durationTimeText.setModel(new javax.swing.SpinnerNumberModel(1, 1, 365, 1));
        durationTimeText.setName(""); // NOI18N
        getContentPane().add(durationTimeText, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 270, 90, -1));

        label6.setText("Dienų skaičius");
        getContentPane().add(label6, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 250, -1, -1));
        getContentPane().add(countryNameText, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 370, 90, -1));

        label7.setText("Miestas");
        getContentPane().add(label7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 390, -1, -1));
        getContentPane().add(cityNameText, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 410, 90, -1));

        searchButton.setLabel("Ieškoti");
        searchButton.setName(""); // NOI18N
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });
        getContentPane().add(searchButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 260, 60, 30));

        reservationButton.setEnabled(false);
        reservationButton.setLabel("Užsakyti");
        reservationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reservationButtonActionPerformed(evt);
            }
        });
        getContentPane().add(reservationButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 260, 70, 30));

        errorText.setForeground(new java.awt.Color(255, 51, 51));
        errorText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(errorText, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 620, 20));

        peopleCountField.setModel(new javax.swing.SpinnerNumberModel(1, 1, 10, 1));
        peopleCountField.setName(""); // NOI18N
        getContentPane().add(peopleCountField, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 320, 90, -1));

        label8.setText("Žmonių skaičius");
        getContentPane().add(label8, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 300, -1, -1));

        cancelButton.setEnabled(false);
        cancelButton.setLabel("Atšaukti");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        getContentPane().add(cancelButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 300, 70, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
        if(!countryNameText.getText().equals("") && !cityNameText.getText().equals("")){
            errorText.setText("");
            reservationButton.setEnabled(false);
            cancelButton.setEnabled(false);
            GuiEvent ge = new GuiEvent(this, Buyer.SEARCH);
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            ge.addParameter(format.format(calendar.getDate()));
            ge.addParameter(durationTimeText.getValue());
            ge.addParameter(peopleCountField.getValue());
            ge.addParameter(cityNameText.getText());
            ge.addParameter(countryNameText.getText());
            myAgent.postGuiEvent(ge);
        }
        else{
            errorText.setText("Užpildykite visus laukus");
        }
    }//GEN-LAST:event_searchButtonActionPerformed

    private void reservationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reservationButtonActionPerformed
        // TODO add your handling code here:
        errorText.setText("");
        reservationButton.setEnabled(false);
        GuiEvent ge = new GuiEvent(this, Buyer.BOOK);
        myAgent.postGuiEvent(ge);
    }//GEN-LAST:event_reservationButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        errorText.setText("");
        reservationButton.setEnabled(false);
        cancelButton.setEnabled(false);
        GuiEvent ge = new GuiEvent(this, Buyer.CANCEL);
        myAgent.postGuiEvent(ge);
    }//GEN-LAST:event_cancelButtonActionPerformed
    public void showTable(jade.util.leap.List list){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for(Object o : list.toArray()){
            Apartment a = (Apartment) o;
            Reservation r = (Reservation) a.getReservations().get(0);
            model.addRow(new Object[]{a.getName(), a.getCompany(), a.getDescription(), r.getStartDate(), r.getEndDate(), a.getPrice()});
        }
        reservationButton.setEnabled(true);
    }
    public void setPrice(float price){
        totalPriceText.setText(price+"");
    }
    public void setError(String s){
        errorText.setText(s);
    }
    public void setCancel(boolean enabled){
        cancelButton.setEnabled(enabled);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JCalendar calendar;
    private java.awt.Button cancelButton;
    private java.awt.TextField cityNameText;
    private java.awt.TextField countryNameText;
    private javax.swing.JSpinner durationTimeText;
    private javax.swing.JLabel errorText;
    private javax.swing.JScrollPane jScrollPane1;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Label label4;
    private java.awt.Label label5;
    private java.awt.Label label6;
    private java.awt.Label label7;
    private java.awt.Label label8;
    private javax.swing.JSpinner peopleCountField;
    private java.awt.Button reservationButton;
    private java.awt.Button searchButton;
    private javax.swing.JTable table;
    private java.awt.Label totalPriceText;
    // End of variables declaration//GEN-END:variables
}
