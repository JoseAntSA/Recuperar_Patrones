package Patrones;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author anton
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;

public class Frame extends JFrame{
    
    private JPanel pnl;
    private JLabel etqFondo, etqMAXIMO, etqMAXIMO2, etqMINIMO, etqMINIMO2;
    private ImageIcon imgFondo;
    private JButton btnPatronOri, btnGuardar, btnLimpiarOrig;
    private JButton btnPatronRec, btnRecuperar, btnLimpiarRec;
    private JLabel etqPatron;
    
    int filas = 5;
    int columnas = 8;
    int tam = filas * columnas;
    
         
    ArrayList<JButton> btnPatronesOrig = new ArrayList<>();
    ArrayList<JButton> btnPatronesRecu = new ArrayList<>();
    ArrayList<JLabel> etqPatronesFinMAX = new ArrayList<>();
    ArrayList<JLabel> etqPatronesFinMIN = new ArrayList<>();
    ArrayList<int[]> patrones = new ArrayList<>();
    int[] patronOrig = new int[tam];
    int[] patronRecu = new int[tam];
    
    int[] patronRecuMAX = new int[tam];
    int[] patronRecuMIN = new int[tam];
    int[] patronRecuFin = new int[tam];
    
    ArrayList<int[][]> matricesP = new ArrayList<>();
    int[][] memoriaMAX = new int[tam][tam];
    int[][] memoriaMIN = new int[tam][tam];

    
    
    //Constructor
    public Frame(){
        setTitle("Algoritmo Genetico - Problema de la 8 Reinas");
        setSize(800,450);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        initComponets();
        setVisible(true);
    }
    
    public void initComponets(){
        
        Font fuenteTexto = new Font( "Calibri", 1, 20 );
        
        //CODIGO PANEL
        pnl = new JPanel();
        this.getContentPane().add(pnl);
        pnl.setLayout(null);
        
        int cont = 0;
        for(int i=0 ; i<columnas ; i++){
            for(int j=0 ; j<filas ; j++){
                btnPatronOri = new JButton();
                btnPatronOri.setBounds(25+(j*30),60+(i*30),30,30);
                btnPatronOri.setBackground(Color.WHITE);
                btnPatronOri.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
                btnPatronOri.setName(String.valueOf(cont));
                btnPatronOri.addActionListener(new EventoBoton(patronOrig));
                pnl.add(btnPatronOri);
                cont++;
                
                btnPatronesOrig.add(btnPatronOri);
            }
        }
        
        cont = 0;
        for(int i=0 ; i<columnas ; i++){
            for(int j=0 ; j<filas ; j++){
                btnPatronRec = new JButton();
                btnPatronRec.setBounds(225+(j*30),60+(i*30),30,30);
                btnPatronRec.setBackground(Color.WHITE);
                btnPatronRec.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
                btnPatronRec.setName(String.valueOf(cont));
                btnPatronRec.addActionListener(new EventoBoton(patronRecu));
                pnl.add(btnPatronRec);
                cont++;
                
                btnPatronesRecu.add(btnPatronRec);
            }
        }
        
        cont = 0;
        for(int i=0 ; i<columnas ; i++){
            for(int j=0 ; j<filas ; j++){
                etqPatron = new JLabel();
                //etqPatron.addActionListener(new EventoBoton(patronRecu));
                etqPatron.setOpaque(true);
                etqPatron.setBackground(Color.WHITE);
                etqPatron.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
                etqPatron.setBounds(425+(j*30),60+(i*30),30,30);
                etqPatron.setName(String.valueOf(cont));
                pnl.add(etqPatron);
                cont++;
                
                etqPatronesFinMIN.add(etqPatron);
            }
        }
        
        cont = 0;
        for(int i=0 ; i<columnas ; i++){
            for(int j=0 ; j<filas ; j++){
                etqPatron = new JLabel();
                //etqPatron.addActionListener(new EventoBoton(patronRecu));
                etqPatron.setOpaque(true);
                etqPatron.setBackground(Color.WHITE);
                etqPatron.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
                etqPatron.setBounds(625+(j*30),60+(i*30),30,30);
                etqPatron.setName(String.valueOf(cont));
                pnl.add(etqPatron);
                cont++;
                
                etqPatronesFinMAX.add(etqPatron);
            }
        }
        
        btnGuardar = new JButton("Guardar Patrón");
        btnGuardar.addActionListener(new EventoGuardar());
        btnGuardar.setBounds(25,320,150,30);
        pnl.add(btnGuardar);
        
        btnLimpiarOrig = new JButton("Limpiar Patrón");
        btnLimpiarOrig.addActionListener(new EventoLimpiar(btnPatronesOrig));
        btnLimpiarOrig.setBounds(25,360,150,30);
        pnl.add(btnLimpiarOrig);
        
        btnRecuperar = new JButton("Recuperar Patrón");
        btnRecuperar.addActionListener(new EventoRecuperar());
        btnRecuperar.setBounds(225,320,150,30);
        pnl.add(btnRecuperar);
        
        btnLimpiarRec = new JButton("Limpiar Patrón");
        btnLimpiarRec.addActionListener(new EventoLimpiar(btnPatronesRecu));
        btnLimpiarRec.setBounds(225,360,150,30);
        pnl.add(btnLimpiarRec);
        
        etqMAXIMO = new JLabel("Matriz de ");
        etqMAXIMO.setHorizontalAlignment(SwingConstants.CENTER);
        etqMAXIMO.setBounds(425,320,150,30);
        etqMAXIMO.setForeground(Color.WHITE);
        etqMAXIMO.setFont( fuenteTexto );
        pnl.add(etqMAXIMO);
        
        etqMAXIMO2 = new JLabel("Maximos");
        etqMAXIMO2.setHorizontalAlignment(SwingConstants.CENTER);
        etqMAXIMO2.setBounds(425,350,150,30);
        etqMAXIMO2.setForeground(Color.WHITE);
        etqMAXIMO2.setFont( fuenteTexto );
        pnl.add(etqMAXIMO2);
        
        etqMINIMO = new JLabel("Matriz de ");
        etqMINIMO.setHorizontalAlignment(SwingConstants.CENTER);
        etqMINIMO.setBounds(625,320,150,30);
        etqMINIMO.setForeground(Color.WHITE);
        etqMINIMO.setFont( fuenteTexto );
        pnl.add(etqMINIMO);
        
        etqMINIMO2 = new JLabel("Minimos");
        etqMINIMO2.setHorizontalAlignment(SwingConstants.CENTER);
        etqMINIMO2.setBounds(625,350,150,30);
        etqMINIMO2.setForeground(Color.WHITE);
        etqMINIMO2.setFont( fuenteTexto );
        pnl.add(etqMINIMO2);
        
        //CODIGO FONDO
        imgFondo = new ImageIcon("src/Patrones/Fondo_2.png");
        etqFondo = new JLabel();
        etqFondo.setBounds(0, 0, 800, 423);
        etqFondo.setIcon(new ImageIcon(imgFondo.getImage().getScaledInstance(etqFondo.getWidth(), etqFondo.getHeight(), Image.SCALE_SMOOTH)));
        pnl.add(etqFondo);
        
    }
    
    //CODIGO EVENTO ACTION
    public class EventoBoton implements ActionListener{
        int[] patron;
        
        EventoBoton(int[] patron){
            this.patron = patron;
        }
        
        @Override
        public void actionPerformed(ActionEvent ev){
            JButton btn = (JButton)ev.getSource();
            int num = Integer.parseInt(btn.getName());
            //System.out.println(name);
            if(patron[num] == 0){ 
                patron[num] = 1;
                btn.setBackground(Color.BLACK);
            }else{
                patron[num] = 0;
                btn.setBackground(Color.WHITE);
            }
        }//evento
    }//clase interna
    
    //CODIGO EVENTO ACTION
    public class EventoGuardar implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ev){    
            int[] patronO = patronOrig.clone();
            patrones.add(patronO);
            limpiarPatron(patronOrig, btnPatronesOrig);
            
            for(int i=0; i<patrones.size(); i++){
                int[][] matrizP = new int[tam][tam];
                for(int j=0; j<tam; j++){
                    for(int k=0; k<tam; k++){
                        matrizP[j][k] = operacionALFA(patrones.get(i)[j],patrones.get(i)[k]);
                    }    
                }
                matricesP.add(matrizP);
            }
            
            ArrayList<Integer> valorMem = new ArrayList<>();
            for(int j=0; j<tam; j++){
                for(int k=0; k<tam; k++){
                    for(int i=0; i<matricesP.size(); i++){
                        valorMem.add(matricesP.get(i)[j][k]);
                    }
                    memoriaMAX[j][k]= Collections.max(valorMem);
                    memoriaMIN[j][k]= Collections.min(valorMem);
                    valorMem.clear();
                }
            }
        }//evento
    }//clase interna
    
    //CODIGO EVENTO ACTION
    public class EventoRecuperar implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ev){
            int[] patronR = patronRecu.clone();
            
            int[][] matrizRMAX = new int[tam][tam];
            for(int j=0; j<tam; j++){
                for(int k=0; k<tam; k++){
                    matrizRMAX[j][k] = operacionBETA(memoriaMAX[j][k],patronR[k]);
                }    
            }
            
            int[][] matrizRMIN = new int[tam][tam];
            for(int j=0; j<tam; j++){
                for(int k=0; k<tam; k++){
                    matrizRMIN[j][k] = operacionBETA(memoriaMIN[j][k],patronR[k]);
                }    
            }
            
            int val = 0;
            int valPatron = 0;
            for(int j=0; j<tam; j++){
                val = matrizRMAX[j][0];
                for(int k=1; k<tam; k++){
                    if(val==1 && matrizRMAX[j][k]==1)
                        valPatron = 1;
                    else
                        valPatron = 0;
                    val = valPatron;
                }    
                patronRecuMAX[j] = valPatron;
            }
            
            for(int j=0; j<tam; j++){
                val = matrizRMIN[j][0];
                for(int k=1; k<tam; k++){
                    if(val==0 && matrizRMIN[j][k]==0)
                        valPatron = 0;
                    else
                        valPatron = 1;
                    val = valPatron;
                }    
                patronRecuMIN[j] = valPatron;
            }
            /*
            for(int i=0 ; i<patrones.size() ; i++){
                if(Arrays.equals(patrones.get(i), patronRecuMAX)){
                    patronRecuFin = patronRecuMAX;
                    break;
                }
                else if(Arrays.equals(patrones.get(i), patronRecuMIN)){
                    patronRecuFin = patronRecuMIN;
                    break;
                }
            }*/
            
            for(int i=0; i<tam; i++){
                if(patronRecuMIN[i]==0)
                    etqPatronesFinMIN.get(i).setBackground(Color.WHITE);
                else
                    etqPatronesFinMIN.get(i).setBackground(Color.BLACK);
                
                if(patronRecuMAX[i]==0)
                    etqPatronesFinMAX.get(i).setBackground(Color.WHITE);
                else
                    etqPatronesFinMAX.get(i).setBackground(Color.BLACK);
            }
            /*
            System.out.println("Patron Final");
            for(int j=0; j<columnas; j++){
                for(int k=0; k<filas; k++){
                    System.out.print(patronRecuFin[k+(j*5)]);
                }
                System.out.println("");
            }
            System.out.print("\n");
            
            */
            
        }//evento
    }//clase interna
    
    //CODIGO EVENTO ACTION
    public class EventoLimpiar implements ActionListener{
        ArrayList<JButton> btns;
        
        EventoLimpiar(ArrayList<JButton> btns){
            this.btns = btns;
        }
        
        @Override
        public void actionPerformed(ActionEvent ev){
            for(int i=0; i<btns.size() ; i++){
                btns.get(i).setBackground(Color.WHITE);
            }
        }//evento
    }//clase interna
    
    
    public void limpiarPatron(int[] patron, ArrayList<JButton> botones){
        for(int i=0; i<patron.length ; i++){
            patron[i] = 0;
            botones.get(i).setBackground(Color.WHITE);
        }
    }
    
    public int operacionALFA(int x, int y){
        int alfa=0;
        if(x==0 && y==0)
            alfa = 1;
        else if(x==0 && y==1)
            alfa = 0;
        else if(x==1 && y==0)
            alfa = 2;
        else if(x==1 && y==1)
            alfa = 1;
        
        return alfa;
    }
    
    public int operacionBETA(int x, int y){
        int beta = 0;
        if(x==0 && y==0)
            beta = 0;
        else if(x==0 && y==1)
            beta = 0;
        else if(x==1 && y==0)
            beta = 0;
        else if(x==1 && y==1)
            beta = 1;
        else if(x==2 && y==0)
            beta = 1;
        else if(x==2 && y==1)
            beta = 1;
        
        return beta;
    }
}
