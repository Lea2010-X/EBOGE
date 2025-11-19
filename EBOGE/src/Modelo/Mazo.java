package Modelo;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

public class Mazo {
	
	private final int CAPACIDAD = 10;
	private Deque<Carta> pila;
	private TipoCarta tipoDeCartasParaMazo;
	
	public Mazo(TipoCarta tipo) {
		this.tipoDeCartasParaMazo = tipo;
		this.pila = new ArrayDeque<>();
		this.rellenar();
	}
	
	private void rellenar() {
		for (int i = 0; i < CAPACIDAD; i++) {
			Carta nuevaCarta = this.generarCartaAleatoria();
            this.pila.push(nuevaCarta);
        }
		
	}
	
	
	public Carta generarCartaAleatoria() {
	    TipoCarta tipo=getTipo();
	    Random random = new Random();
	    int selector = random.nextInt(2);
	    
	    switch (tipo) {
        case MAESTRA:
            if (selector == 0) {
                // Efecto: Convierte la casilla anterior en Trampa
                return new CartaCajaSorpresa("rutaNoDefinida"); 
            } else {
                // Efecto: Convierte la siguiente casilla en una casilla segura/propia
                return new CartaSantuarioDelProtector("rutaNoDefinida"); 
            }

        case GLOBAL:
            if (selector == 0) {
                // Efecto: Todos retroceden 5 casillas
                return new CartaRuptura("rutaNoDefinida");
            } else {
                // Efecto: Reubicación aleatoria de todos los jugadores
                return new CartaPortalDeReino("rutaNoDefinida");
            }
        
        case BLANCO: // (Target)
            if (selector == 0) {
                // Efecto: Inmoviliza al objetivo (Stun)
                return new CartaBroteLetal("rutaNoDefinida");
            } else {
                // Efecto: Intercambia posición con el objetivo
                return new CartaMiedoDeUltratumba("rutaNoDefinida");
            }  
            
        case PROPIA:
            if (selector == 0) {
                // Efecto: Lanza el dado de nuevo y avanza
                return new CartaHechizoOscuro("rutaNoDefinida");
            } else {
                // Efecto: Retrocede lo que acabas de avanzar
                return new CartaCronorruptura("rutaNoDefinida");
            }
            
        default:
            return null; // O lanzar una excepcion si el tipo no es valido
	    }
	}

	public TipoCarta getTipo() {
        return this.tipoDeCartasParaMazo;
    }
	
	public boolean vacio() {
        return pila.isEmpty();
    }
	
    public Carta robar() {    
        if (this.vacio()) {
            System.out.println("El mazo de tipo " + this.tipoDeCartasParaMazo.toString() + " se ha agotado. Rellenando...");
            this.rellenar(); 
        }

        return this.pila.poll(); 
    }
}
