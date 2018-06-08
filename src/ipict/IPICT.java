/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipict;

/**
 *
 * @author karin
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class IPICT extends JFrame implements ActionListener {
    private JTextField field1, field2;
    private JButton browseButton1, browseButton2, compareButton;
    private JLabel header, files, info;
    private JTextArea area;
    private JScrollPane scroll;
    private JPanel panel;
    private Graphics g;
    private JFileChooser fileChooser;
    
    public static void main(String[] args) {
    IPICT analyse = new IPICT();
    analyse.setTitle("IPI Compare Tool");
    analyse.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    analyse.createGUI((analyse.getContentPane()));
    analyse.pack();
    analyse.setVisible(true);        
    }
    
    private void createGUI(Container pane) {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.CENTER;
        
	header = new JLabel("Voer twee IPI bestanden in om te vergelijken");
        c.gridx = 1;
        c.gridwidth = 3;   
        c.gridy = 0;
        c.insets = new Insets(10,10,4,4);
	pane.add(header, c);
        
        files = new JLabel("Bestand");
        c.gridx = 0;
        c.gridwidth = 1; 
        c.gridy = 1;
        pane.add(files,c);
        
        field1 = new JTextField(20);
        c.gridx = 1;
        pane.add(field1 ,c);
        
        browseButton1 = new JButton("Blader");
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 2;
        pane.add(browseButton1, c);
        browseButton1.addActionListener(this);
        
        field2 = new JTextField(20);
        c.gridx = 3; 
        pane.add(field2, c);
        
        browseButton2 = new JButton("Blader");
        c.fill = GridBagConstraints.CENTER;
        c.insets = new Insets(10,10,10,10);
        c.gridx = 4; 
        pane.add(browseButton2, c);
        browseButton2.addActionListener(this);
        
        compareButton = new JButton("Vergelijk");
        c.insets = new Insets(10,10,4,4);
        c.gridx = 1;
        c.gridy = 2;        
        pane.add(compareButton,c);
        compareButton.addActionListener(this);
        
        info = new JLabel("Informatie");
        c.gridx = 0;
        c.gridwidth = 1; 
        c.gridy = 3;        
        pane.add(info,c);
        
	area = new JTextArea(10, 50);
	area.setLineWrap(true);
	area.setWrapStyleWord(true);
	area.setEditable(false);
        c.gridx = 1;
        c.gridwidth = 3;
        pane.add(area, c);
        
        scroll = new JScrollPane (area);
	scroll.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
	scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pane.add(scroll,c);        
        
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(450, 100));
        panel.setBackground(Color.lightGray); 
        c.gridwidth = 3;
        c.insets = new Insets(10,10,10,10);
        c.gridy = 4;
        add(panel, c);
		
    }

    public void actionPerformed(ActionEvent event) {
        Object buttonPressed = event.getSource();
        File file;
	int reply;
        fileChooser = new JFileChooser();
        
        if (browseButton1 == buttonPressed){
            reply = fileChooser.showOpenDialog(this);
            String line;
            if(reply == JFileChooser.APPROVE_OPTION){
                file = fileChooser.getSelectedFile();
		field1.setText(file.getAbsolutePath());
            } 
        } else if (browseButton2 == buttonPressed){
            reply = fileChooser.showOpenDialog(this);
            String line;
            if(reply == JFileChooser.APPROVE_OPTION){
                file = fileChooser.getSelectedFile();
		field2.setText(file.getAbsolutePath());
            } 
        } else if (compareButton == buttonPressed){
            if (field1.getText() == null || field2.getText() == null){
                JOptionPane.showMessageDialog(null, "Voer twee bestanden in.");
            }else try{
                Comparison comp = new Comparison(field1.getText(), field2.getText());
                if(comp.getErrorMsg().equals("")){
                    area.setText("Bestanden zijn gelijk");
                }else {
                    area.setText(comp.getErrorMsg());
                    drawErrorGraph(comp);
                }                
            } catch (NotIPIException ex) {
                JOptionPane.showMessageDialog(null, "Corrupt bestand");
            }
        }
    }

    private void drawErrorGraph(Comparison comp){
        Graphics g = panel.getGraphics();
        int l = comp.getErrorLocations().length();
        for (int i = 0; i < l; i++){
            switch (comp.getErrorLocations().charAt(i)){
                case '0':
                    g.setColor(Color.black);
                    break;
                case '1':
                    g.setColor(Color.blue);
                    break;
                case '2':
                    g.setColor(Color.green);
                    break;
                case '3':
                     g.setColor(Color.red);
                    break;
                case '4':
                    g.setColor(Color.pink);
                    break;
                        }
                g.fillRect(25+i*(l), 20, l, 36);
        }
    }
}

