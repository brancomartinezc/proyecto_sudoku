package gui;
import logica.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class GUI extends JFrame {

	private JPanel contentPane;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setTitle("Sudoku");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		
		Juego juego= new Juego();
		
		if(!juego.getArchivoCorrecto()) { //Si el archivo no es correcto se muestra el mensaje de error y sale del juego.
			JOptionPane.showMessageDialog(null, "ERROR en el archivo, no se puede jugar.");
			System.exit(0);
		}else {
			//Preparacion grafica
			EntidadGrafica entidad_graf_seg1=new EntidadGrafica(0);
			EntidadGrafica entidad_graf_seg2=new EntidadGrafica(0);
			EntidadGrafica entidad_graf_min1=new EntidadGrafica(0);
			EntidadGrafica entidad_graf_min2=new EntidadGrafica(0);
			
			setBackground(Color.WHITE);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 725, 718);
			contentPane = new JPanel();
			contentPane.setBackground(Color.WHITE);
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(new BorderLayout());
			
			JPanel panelTablero = new JPanel();
			panelTablero.setBackground(Color.BLACK);
			contentPane.add(panelTablero);
			panelTablero.setLayout(new GridLayout(9, 9));
			
			JPanel panelSuperior = new JPanel();
			panelSuperior.setBackground(Color.GRAY);
			contentPane.add(panelSuperior, BorderLayout.NORTH);
			
			JLabel labelTitulo = new JLabel("SUDOKU");
			labelTitulo.setForeground(Color.BLACK);
			labelTitulo.setFont(new Font("Arial", Font.BOLD, 50));
			panelSuperior.add(labelTitulo);
			
			JLabel labelTimer = new JLabel("       |       ");
			labelTimer.setForeground(Color.BLACK);
			labelTimer.setFont(new Font("Arial", Font.BOLD, 50));
			panelSuperior.add(labelTimer);
			
			JLabel labelMin2 = new JLabel(entidad_graf_min2.getGrafico(), JLabel.CENTER);
			panelSuperior.add(labelMin2);
			
			JLabel labelMin1 = new JLabel(entidad_graf_min1.getGrafico(), JLabel.CENTER);
			panelSuperior.add(labelMin1);
			
			JLabel labelDosPuntos = new JLabel(":");
			labelDosPuntos.setForeground(Color.BLACK);
			labelDosPuntos.setFont(new Font("Arial", Font.BOLD, 70));
			panelSuperior.add(labelDosPuntos);
			
			JLabel labelSeg2 = new JLabel(entidad_graf_seg2.getGrafico(), JLabel.CENTER);
			panelSuperior.add(labelSeg2);
			
			JLabel labelSeg1 = new JLabel(entidad_graf_seg1.getGrafico(), JLabel.CENTER);
			labelSeg1.setForeground(Color.GRAY);
			panelSuperior.add(labelSeg1);
			
			JPanel panelInferior = new JPanel();
			panelInferior.setBackground(Color.GRAY);
			panelInferior.setForeground(Color.BLACK);
			contentPane.add(panelInferior, BorderLayout.SOUTH);
			panelInferior.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			JButton botonVerificar = new JButton("Verificar solucion");
			botonVerificar.setFont(new Font("Arial", Font.BOLD, 14));
			panelInferior.add(botonVerificar);
			
			JPanel panelOpciones = new JPanel();
			panelOpciones.setBackground(Color.GRAY);
			contentPane.add(panelOpciones, BorderLayout.EAST);
			panelOpciones.setLayout(new GridLayout(10, 1, 0, 0));
			
			JLabel labelOpciones = new JLabel("OPCIONES");
			labelOpciones.setForeground(Color.BLACK);
			labelOpciones.setBackground(Color.GRAY);
			labelOpciones.setFont(new Font("Arial", Font.BOLD, 14));
			panelOpciones.add(labelOpciones);
			
			Timer timer;
			int cant=9;
			JButton boton;
			JButton[][] matriz_botones=new JButton[cant][cant];
			EntidadGrafica ent_graf;
			int update_rate=1000;
			
			//Reloj
			class timerListener implements ActionListener {
				public void actionPerformed(ActionEvent e) {
					long tiempo = juego.tiempoActual();
					
					//Formateo del tiempo
					int segs_totales = (int) (tiempo / 1000);
					int segs_mod60 = segs_totales % 60;
					int segs_mostrados1=segs_mod60 % 10;
					int segs_mostrados2=segs_mod60 / 10;
					int mins_totales = segs_totales / 60;
					int mins_mostrados1=mins_totales % 10;
					int mins_mostrados2=mins_totales / 10;
					
					//Actualizacion grafica del timer
					entidad_graf_seg1.actualizar(segs_mostrados1);
					entidad_graf_seg2.actualizar(segs_mostrados2);
					entidad_graf_min1.actualizar(mins_mostrados1);
					entidad_graf_min2.actualizar(mins_mostrados2);
					panelSuperior.repaint();
					
				}
			}
			
			timer=new Timer(update_rate, new timerListener());
			timer.start();
			
			//Accion del boton de verificar.
			botonVerificar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					boolean cumple=juego.verificarSolucion();
					
					if(cumple) {
						JOptionPane.showMessageDialog(null,"GANASTE!");
						timer.stop();
					}else {
						JOptionPane.showMessageDialog(null,"Todavia no ganaste.");
					}
				}
			});
			
			//Panel de OPCIONES.
			for(int i=0; i<9; i++) {
				//Creo cada celda de opciones y les doy el estilo.
				ent_graf=juego.getCelda(i).getEntidadGrafica();
				boton=new JButton(ent_graf.getGrafico());
				
				ent_graf.agregarBoton(boton);
				ent_graf.actualizarFondo('g');
				ent_graf.agregarBorde(0,0);
				boton.setName((i+1)+"");
				panelOpciones.add(boton);
				
				//Accion del boton.
				boton.addActionListener(new ActionListener(){
		        	public void actionPerformed(ActionEvent e) {
		        		JButton opcion_actual= (JButton) e.getSource();
		        		juego.setUltimaOpcElegida(opcion_actual.getName());
					}
		        });
			}
			
			//Panel del JUEGO.
			for(int i=0; i<cant; i++) {
				for(int j=0; j<cant; j++) {
					
					ent_graf=juego.getCelda(i,j).getEntidadGrafica();
					
					//Creo cada boton, lo asigno a su entidad grafica y le doy el estilo.
		        	boton = new JButton(ent_graf.getGrafico());
		        	ent_graf.agregarBoton(boton);
		        	ent_graf.agregarBorde(i,j);
		        	panelTablero.add(boton);
		        	
			        if(juego.getNumeroEnCelda(i,j)!=-1) { //Celda fija.
			        	ent_graf.actualizarFondo('o');
			        	boton.setFocusPainted(false);
			        	boton.setEnabled(false);
			        	
			        }else { //Celda jugable.
			        	matriz_botones[i][j]=boton;
			        	ent_graf.actualizarFondo('g');
			        	
			        	//Accion del boton.
				        boton.addActionListener(new ActionListener(){
				        	public void actionPerformed(ActionEvent e) {
				        		boolean accion_valida;
				        		int num_anterior;
				        		
				        		//Busco el boton seleccionado.
								for (int i = 0; i < cant; i++) {
									for (int j = 0; j < cant; j++){
										if(matriz_botones[i][j]==e.getSource()) {
											
											num_anterior=juego.getNumeroEnCelda(i,j);
											
											//Si no eligio el mismo numero que ya estaba antes
											if(num_anterior!=juego.getUltimaOpcElegida()) {
												
												//Verifico si fue una accion valida y actualizo la cela.
												accion_valida=juego.comprobar(i,j,juego.getUltimaOpcElegida(),false);
												juego.actualizarCelda(i, j, accion_valida);
												
												//vuelvo a controlar todas las celdas por posibles
												//cambios de validez despues de la ultima accion
												juego.revalidar();
											}
											
											
										}
									}
								}
							}
				        });
			        }

				}
			}
		}
		
		
	}

}


//IGNORAR
//Actualizo el color del boton.
/*ent_graf_actual=juego.getCelda(i,j).getEntidadGrafica();
if(!accion_valida) {
	ent_graf_actual.actualizarFondo('r');
}else {
	ent_graf_actual.actualizarFondo('g');	
}*/


