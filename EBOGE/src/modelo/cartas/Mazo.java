package modelo.cartas;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

import modelo.cartas.blanco.Plegaria;
import modelo.cartas.blanco.BoogieWoogie;
import modelo.cartas.global.DistorsionDelEntorno;
import modelo.cartas.global.Monzon;
import modelo.cartas.maestra.GrilletesDelAlma;
import modelo.cartas.maestra.CartaTemploDeLirios;
import modelo.cartas.propia.RegresoACasa;
import modelo.cartas.propia.Flash;

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

                return new GrilletesDelAlma(); 
            } else {

                return new CartaTemploDeLirios(); 
            }

        case GLOBAL:
            if (selector == 0) {

                return new Monzon();
            } else {

                return new DistorsionDelEntorno();
            }
        
        case BLANCO: // (Target)
            if (selector == 0) {

                return new Plegaria();
            } else {

                return new BoogieWoogie();
            }  
            
        case PROPIA:
            if (selector == 0) {

                return new Flash();
            } else {

                return new RegresoACasa();
            }
            
        default:
            return null; 
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
    
    public int getCantidadCartas() {
        return this.pila.size();
    }
}
