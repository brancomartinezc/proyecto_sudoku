package logica;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class Juego {
	private final int cant=9,altura_bloque=3,ancho_bloque=3;
	private Celda[][] tablero;
	private Celda[] lista_opc;
	private BufferedReader br;
	private String linea;
	private Scanner scanner;
	private int num,valor,ultima_opc_elegida=1,cant_celdas_usadas=0;
	private Random rnd;
	private boolean ultima_accion_valida=true;
	boolean archivo_correcto,nums_validos=true;
	long tiempo_inicio;
	
	//
	
	/**
	 * Constructor del juego..
	 */
	public Juego() {
		try {
			tablero=new Celda[cant][cant];
			br =  new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/datos/inicio.txt")));
			rnd=new Random();
			
			//Leo el archivo y paso todo a la matriz del tablero 
			//mientras verifico que los numeros sean validos
			for(int i=0; i<cant && nums_validos; i++) {
				
				linea=br.readLine();
				
				if(linea!=null) {
					scanner=new Scanner(linea);
					
					for(int j=0; j<cant && nums_validos; j++) {
						num=scanner.nextInt();
						
						if(num<1 || num>9) {
							nums_validos=false;
						}else {
							tablero[i][j]=new Celda(i,j);
							tablero[i][j].setNumero(num);
						}

					}
				}else{
					nums_validos=false;
				}
			}
			
			br.close();
			
			if(nums_validos) {
				archivo_correcto=verificarArchivo();
				
				if(archivo_correcto) {
					
					//Preparo el tablero para que sea jugable de forma aleatoria.
					for(int i=0; i<cant; i++) {
						for(int j=0; j<cant; j++) {
							valor = rnd.nextInt(4);
							
							if(valor==0 /*|| valor==1 || valor==2*/) { //La celda es fija.
								tablero[i][j].setEsFija(true);
								cant_celdas_usadas++;
							}else { //Reseteo la celda para que sea jugable.
								tablero[i][j]=new Celda(i,j);
							}
							
						}
					}
					
					//Creo y cargo la lista de opciones
					lista_opc=new Celda[cant];
					for(int i=0; i<cant; i++) {
						lista_opc[i]=new Celda();
						lista_opc[i].setNumero(i+1);
					}
					
					tiempo_inicio = System.currentTimeMillis();
				}
				
				
			}else {
				archivo_correcto=false;
			}
			
		//Si hay algo que no es numero, el archvio no es correcto
		} catch (IOException | /*InputMismatchException |*/ NoSuchElementException e) {
			try {
				br.close();
			} catch (IOException e1) {
			}
			archivo_correcto=false;
		}
		
	}
	
	/**
	 * Devuelve el tiempo transcurrido desde el incio del juego en milisegundos.
	 * @return tiempo en milisegundos.
	 */
	public long tiempoActual(){
		return System.currentTimeMillis() - tiempo_inicio;
	}
	
	/**
	 * Verifica que la solucion de la matriz sea correcta 
	 * (aca la matriz esta con los datos directo del archivo, por lo tanto, lo que se verifica es que el archivo
	 * tenga una solucion correcta).
	 * @return Verdadero si la solucion es correcta, falso en caso contrario.
	 */
	private boolean verificarArchivo() {
		
		//Verifico que cada fila no tenga numeros repetidos.
		for(int f=0; f<cant; f++) {
			for(int c=0; c<cant-1; c++) {
				for(int c2=c+1; c2<cant; c2++) {
					if(tablero[f][c].getNumero()==tablero[f][c2].getNumero()) {
						return false;
					}
				}
			}
		}
		
		//Verifico que cada columna no tenga numeros repetidos.
		for(int c=0; c<cant; c++) {
			for(int f=0; f<cant-1; f++) {
				for(int f2=f+1; f2<cant; f2++) {
					if(tablero[f][c].getNumero()==tablero[f2][c].getNumero()) {
						return false;
					}
				}
			}
		}
		
		//Verifico que cada bloque no tenga numeros repetidos.
		if(verifBloques(0,2,0,2) && verifBloques(0,2,3,5) && verifBloques(0,2,6,8) &&
			verifBloques(3,5,0,2) && verifBloques(3,5,3,5) && verifBloques(3,5,6,8) &&
			verifBloques(6,8,0,2) && verifBloques(6,8,3,5) && verifBloques(6,8,6,8)) {
			
			return true;
		}else {
			return false;
		}
		
	}
	
	/**
	 * Verifica que un bloque de la matriz no tenga numeros repetidos.
	 * @param f_ini Fila de inicio del bloque.
	 * @param f_fin Fila de fin del bloque.
	 * @param c_ini Columna de inicio del bloque.
	 * @param c_fin Columna de fin del bloque.
	 * @return Verdadero si no hay numeros repetidos, falso en caso contrario.
	 */
	private boolean verifBloques(int f_ini,int f_fin, int c_ini, int c_fin) {
		int[] arreglo_bloque=new int[cant];
		int i=-1;
		
		//Paso todos los elementos del bloque a un arreglo.
		for(int f=f_ini; f<=f_fin; f++) {
			for(int c=c_ini; c<=c_fin; c++) {
				i++;
				arreglo_bloque[i]=tablero[f][c].getNumero();
			}
		}
		
		//Controlo que no haya numeros repetidos en el arreglo.
		for(int j=0; j<cant-1; j++) {
			for(int k=j+1; k<cant; k++) {
				if(arreglo_bloque[j]==arreglo_bloque[k]) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public boolean getArchivoCorrecto() {
		return archivo_correcto;
	}
	
	public int getNumeroEnCelda(int i, int j) {
		return tablero[i][j].getNumero();
	}
	
	public Celda getCelda(int i, int j) {
		return tablero[i][j];
	}
	
	public Celda getCelda(int i) {
		return lista_opc[i];
	}
	
	public void setUltimaOpcElegida(String s) {
		ultima_opc_elegida=Integer.parseInt(s); 
	}
	
	public int getUltimaOpcElegida() {
		return ultima_opc_elegida;
	}
	
	public int getCantCeldasUsadas() {
		return cant_celdas_usadas;
	}
	
	public boolean getUltimaAccionValida() {
		return ultima_accion_valida;
	}
	
	/**
	 * Actualiza el numero y la validez de una celda.
	 * @param i Fila de la celda.
	 * @param j Columna de la celda.
	 * @param validez Verdadero si es valida, falso en caso contrario.
	 */
	public void actualizarCelda(int i, int j, boolean validez) {
		if(tablero[i][j].getNumero()==-1) {
			cant_celdas_usadas++;
		}
		tablero[i][j].setNumero(ultima_opc_elegida);
		tablero[i][j].setEsValida(validez);
	}
	
	/**
	 * Verifica si la solucion del jugador es valida.
	 * @return Verdadero si es valida, falso en caso contrario.
	 */
	public boolean verificarSolucion() {
		boolean cumple=true;
		
		//Si todas las celdas estan usadas y no hay ninguna invalida, cumple.
		if(cant_celdas_usadas==81) {
			for(int i=0; i<cant; i++) {
				for(int j=0; j<cant; j++) {
					if(!tablero[i][j].getEsValida()) {
						cumple=false;
					}
				}
			}
		}else {
			cumple=false;
		}
		
		return cumple;
	}
	
	/**
	 * Comprueba si un numero colocado es valido.
	 * @param f Fila donde se coloco.
	 * @param c Columna donde se coloco.
	 * @param opc Numero colocado.
	 * @param revalidar Verdadero si se esta revalidando, falso en caso contrario.
	 * @return Verdadero si la accion fue valida, falso en caso contrario.
	 */
	public boolean comprobar(int f, int c, int opc, boolean revalidar) {
		boolean cumple;
		int cantFila=comprobarFila(f,opc,revalidar);
		int cantCol=comprobarCol(c,opc,revalidar);
		int cantBloque=comprobarBloque(f,c,opc,revalidar);
		
		if(!revalidar) {
			if(cantFila==0 && cantCol==0 && cantBloque==0) {
				cumple=true;
			}else {
				cumple=false;
			}
		}else {
			if(!tablero[f][c].getEsFija() && cantFila==1 && cantCol==1 && cantBloque==1) {
				cumple=true;
			}else {
				cumple=false;
			}
		}
		
		return cumple;
	}
	
	/**
	 * Vuelve a verificar la validez de todas las celdas.
	 * @param f Fila de la celda.
	 * @param c Columna de la celda.
	 */
	public void revalidar() {
		boolean cumple;
		
		for(int i=0; i<cant; i++) {
			for(int j=0; j<cant; j++) {
				cumple=comprobar(i,j,tablero[i][j].getNumero(),true);
				if(cumple) {
					tablero[i][j].setEsValida(true);
				}
			}
		}
		
	}
	
	/**
	 * Cuenta la cantidad de veces que aparece un numero en una fila. 
	 * (Por las reglas del juego, como maximo puede encontrar 2 y corta).
	 * @param f Fila.
	 * @param opc Numero elegido.
	 * @param revalidar Verdadero si se esta revalidando, falso si es una comprobacion normal.
	 * @return Cantidad de apariciones.
	 */
	private int comprobarFila(int f, int opc, boolean revalidar) {
		int encontrados=0;
		
		for(int j=0; j<cant && encontrados<2; j++) {
			if(tablero[f][j].getNumero()==opc) {
				encontrados++;
				if(!tablero[f][j].getEsFija() && !revalidar) {
					tablero[f][j].setEsValida(false);
				}
				
			}
		}
		
		return encontrados;
	}
	
	/**
	 * Cuenta la cantidad de veces que aparece un numero en una columna. 
	 * (Por las reglas del juego, como maximo puede encontrar 2 y corta).
	 * @param c Columna.
	 * @param opc Numero elegido.
	 * @param revalidar Verdadero si se esta revalidando, falso si es una comprobacion normal.
	 * @return Cantidad de apariciones.
	 */
	private int comprobarCol(int c,int opc, boolean revalidar) {
		int encontrados=0;
		
		for(int i=0; i<cant && encontrados<2; i++) {
			if(tablero[i][c].getNumero()==opc) {
				encontrados++;
				if(!tablero[i][c].getEsFija() && !revalidar) {
					tablero[i][c].setEsValida(false);
				}
				
			}
		}
		
		return encontrados;
	}
	
	/**
	 * Cuenta la cantidad de veces que aparece un numero en un bloque (es decir, el subPanel de 3x3). 
	 * (Por las reglas del juego, como maximo puede encontrar 2 y corta).
	 * @param f Fila.
	 * @param c Columna.
	 * @param opc Numero elegido.
	 * @param revalidar Verdadero si se esta revalidando, falso si es una comprobacion normal.
	 * @return Cantidad de apariciones.
	 */
	private int comprobarBloque(int f, int c,int opc, boolean revalidar) {
		int f_inicio=(f/altura_bloque)*altura_bloque;
		int f_final=f_inicio+altura_bloque;
		int c_inicio=(c/ancho_bloque)*ancho_bloque;
		int c_final=c_inicio+ancho_bloque;
		int encontrados=0;
		
		for(int i=f_inicio; i<f_final && encontrados<2; i++) {
			for(int j=c_inicio; j<c_final && encontrados<2; j++) {
				if(tablero[i][j].getNumero()==opc) {
					encontrados++;
					if(!tablero[i][j].getEsFija() && !revalidar) {
						tablero[i][j].setEsValida(false);
					}
					
				}
			}
		}
		
		return encontrados;
	}
	
}




