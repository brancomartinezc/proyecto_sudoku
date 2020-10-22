##### INFORMACION

El juego se encarga de verificar que en el archivo solo haya numeros entre 1 y 9 (inclusive) y la solucion de sudoku sea valida.
Si el archivo tiene mas de 9 filas y/o columnas, todas aquellas que esten de mas seran ignoradas por el juego y se utilizaran las primeras 9 filas y columnas. Esta hecho de esa manera para que, por ejemplo, si el archivo tiene una solucion valida pero hay cosas extras afuera de las filas y columnas, igualmente el juego puedo comenzar utilizando lo que sirve.

Instrucciones de juego:
- Primero tenes que elegir el numero que quieras desde el menu de OPCIONES y despues tocar la celda donde quieras ponerlo (Por defecto arranca en 1).
- Si una celda esta incumpliendo alguna regla, se va a poner en ROJO, excepto las celdas fijas.
- Cuando quieras verificar si tu solucion es correcta apreta en el boton correspondiente.

<ins>Por que las celdas fijas nunca se marcan en rojo?</ins> Decidi hacerlo asi porque mi idea es que en todo momento el jugador sepa cual celda es fija y cual no (por ello tambien tienen un color diferente), sin importar si se esta rompiendo alguna regla ya que realmente la que va a tener que corregirse es la celda jugable, una celda fija ya es correcta por defecto. La utilidad de esto comienza a verse cuando hay muchas celdas ocupadas y resulta de ayuda saber cual es fija y cual no.

Jar exportado con jdk 14.0.1
