package logica;

public class Celda {
	private int numero,fila,col;
	private EntidadGrafica entidad_grafica;
	private boolean es_valida=false;
	private boolean es_fija=false;
	
	/**
	 * Constructor de la celda.
	 * @param f Fila de la celda.
	 * @param c Columna de la celda.
	 */
	public Celda(int f, int c) {
		fila=f;
		col=c;
		entidad_grafica=new EntidadGrafica();
		numero=-1;
	}
	
	public Celda() {
		entidad_grafica=new EntidadGrafica();
	}
	
	public int getNumero() {
		return numero;
	}
	
	public EntidadGrafica getEntidadGrafica() {
		return entidad_grafica;
	}
	
	public int getFila() {
		return fila;
	}
	
	public int getCol() {
		return col;
	}
	
	public boolean getEsValida() {
		return es_valida;
	}
	
	public boolean getEsFija() {
		return es_fija;
	}
	
	public void setEsFija(boolean b) {
		es_fija=b;
		es_valida=b;
	}
	
	public void setEsValida(boolean b) {
		es_valida=b;
		if(es_valida) {
			entidad_grafica.actualizarFondo('g');
		}else{
			entidad_grafica.actualizarFondo('r');
		}
	}
	
	/**
	 * Cambia el numero de la celda y actuliza su entidad grafica.
	 * @param n Numero.
	 */
	public void setNumero(int n) {
		numero=n;
		if(n!=-1)
			entidad_grafica.actualizar(n);
	}
	
	public void setEntidadGrafica(EntidadGrafica i) {
		entidad_grafica=i;
	}
	
	
	
	
}
