package logica;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

public class EntidadGrafica {
	protected String[] lista_imgs= {"/datos/0.png","/datos/1.png","/datos/2.png","/datos/3.png","/datos/4.png","/datos/5.png","/datos/6.png","/datos/7.png","/datos/8.png","/datos/9.png","/datos/10.png"};
	protected ImageIcon grafico;
	protected ImageIcon imageIcon;
	protected JButton mi_boton;
	protected Border borde_general = new MatteBorder(1, 1, 1, 1, Color.black);;
	protected Border borde_inferior = new MatteBorder(1, 1, 4, 1, Color.black);
	protected Border borde_derecho = new MatteBorder(1, 1, 1, 4, Color.black);
	protected Border borde_inf_der = new MatteBorder(1, 1, 4, 4, Color.black);
	
	public EntidadGrafica() {
		grafico=new ImageIcon();
	}
	
	public EntidadGrafica(int indice) {
		grafico=new ImageIcon();
		imageIcon = new ImageIcon(this.getClass().getResource(this.lista_imgs[indice]));
		grafico.setImage(imageIcon.getImage());
	}
	
	public ImageIcon getGrafico() {
		return grafico;
	}
	
	/**
	 * Cambia el grafico de la entidad.
	 * @param indice
	 */
	public void actualizar(int indice) {
		imageIcon = new ImageIcon(this.getClass().getResource(this.lista_imgs[indice]));
		grafico.setImage(imageIcon.getImage());
	}
	
	public void agregarBoton(JButton b) {
		mi_boton=b;
	}
	
	/**
	 * Cambia el fondo del boton de la entidad.
	 * @param c Inicial del color.
	 */
	public void actualizarFondo(char c) {
		switch(c) {
		case 'r': mi_boton.setBackground(Color.RED); break;
		case 'g': mi_boton.setBackground(Color.GRAY); break;
		case 'o': mi_boton.setBackground(Color.DARK_GRAY); break;
		}
		
	}
	
	/**
	 * Agrega borde al boton de la entidad.
	 * @param i Fila del boton.
	 * @param j Columna del boton.
	 */
	public void agregarBorde(int i, int j) {
		if((i==2 || i==5) && (j==2 || j==5)) {
    		mi_boton.setBorder(borde_inf_der);
    	}else if(i==2 || i==5) {
    		mi_boton.setBorder(borde_inferior);
    	}else if(j==2 || j==5){
    		mi_boton.setBorder(borde_derecho);
    	}else {
    		mi_boton.setBorder(borde_general);
    	}
	}
	
	
}
