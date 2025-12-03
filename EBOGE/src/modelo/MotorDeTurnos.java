package modelo;

import java.util.HashMap;
import java.util.Map;

import modelo.cartas.Carta;
import modelo.cartas.EfectoConObjetivo;
import modelo.cartas.EfectoSinObjetivo;
import modelo.cartas.Mazo;
import modelo.cartas.TipoCarta;
import modelo.jugador.Jugador;
import modelo.mapa.TipoCasilla;


public class MotorDeTurnos {
	
    private Partida partida;
    private Jugador jugadorEnTurno;

    private Map<TipoCasilla, AccionCasilla> accionesPorCasilla;

    // Estado temporal del turno actual
    private TipoCarta mazoHabilitado;  
    private Carta cartaPendiente;      

    public MotorDeTurnos(Partida partida) {
        this.partida = partida;
        this.accionesPorCasilla = new HashMap<>();
        inicializarAcciones();
    }

    private void inicializarAcciones() {
        accionesPorCasilla.put(TipoCasilla.TRAMPA, new AccionCasillaTrampa());
        accionesPorCasilla.put(TipoCasilla.FINAL,  new AccionCasillaVictoria());
    }

    public Jugador iniciarTurno() {
        this.jugadorEnTurno = partida.getJugadorActual();
        
        cartaPendiente = null;
        mazoHabilitado = null;
        return jugadorEnTurno;
    }

    public int lanzarDado() {
        return partida.jugadorActualLanzaDado();
    }

    public int calcularNuevaPosicion(int pasos) {
        partida.moverPorDado(jugadorEnTurno, pasos);
        return jugadorEnTurno.getPosicion();
    }

    // Determina si el turno requiere interacción del usuario (robar carta) o es automático (trampa/nada)
    public void ejecutarAccionEnCasilla() {
    	if (jugadorEnTurno == null || !jugadorEnTurno.requiereActivacion()) return;

        int posicion = jugadorEnTurno.getPosicion();
        TipoCasilla tipo = partida.getMapa().identificarTipoDeCasilla(posicion);
        
        TipoCarta tipoMazo = mapearCasillaACarta(tipo);
        
        if(tipoMazo != null) {
        	this.mazoHabilitado = tipoMazo;
        } else {
        	ejecutarEfectoInstantaneo(tipo);
        }               
        jugadorEnTurno.setRequiereActivacion(false);
    }
    
    private TipoCarta mapearCasillaACarta(TipoCasilla tipo) {
        return switch (tipo) {
            case PROPIA  -> TipoCarta.PROPIA;
            case BLANCO  -> TipoCarta.BLANCO;
            case GLOBAL  -> TipoCarta.GLOBAL;
            case MAESTRA -> TipoCarta.MAESTRA;
            default      -> null;
        };
    }
    
    private void ejecutarEfectoInstantaneo(TipoCasilla tipo) {
        AccionCasilla accion = accionesPorCasilla.get(tipo);
        if (accion != null) {
            accion.ejecutar(partida, jugadorEnTurno);
        }
    }

	public boolean requiereOtroTurno() {
		return jugadorEnTurno != null && jugadorEnTurno.requiereActivacion();
	}

	public void terminarTurno() {
		if (partida.getJuegoTerminado()) return;

        partida.pasarAlSiguienteJugador();
        Jugador siguienteJugador = partida.getJugadorActual();

        siguienteJugador.actualizarEfectos();

        // Funcion recursiva: Si el siguiente jugador está inmovilizado, lo saltamos y buscamos al próximo
        if (siguienteJugador.estaInmovilizado()) {           
            terminarTurno();             
        } else {
            this.jugadorEnTurno = siguienteJugador;            
        }
	}
		
	public Carta robarCartaDeMazo(TipoCarta tipo) {
        if (!puedeRobarDeMazo(tipo)) {
            return null;
        }

        Mazo mazo = partida.getMazo(tipo);
        if (mazo == null) {
            return null;
        }

        Carta carta = mazo.robar();
        this.cartaPendiente = carta;

        return carta;
    }


    public void usarCartaPendienteSinObjetivo() {
        if (cartaPendiente == null) return;

        if (cartaPendiente instanceof EfectoSinObjetivo efecto) {
            efecto.aplicar(partida);
        } else {
            System.out.println("La carta pendiente tiene un tipo de efecto no manejado todavía.");
        }

        cartaPendiente = null;
        mazoHabilitado = null;
    }
   
    public void usarCartaPendienteConObjetivo(Jugador jugadorObjetivo) {
        if (cartaPendiente == null) return;

        if (cartaPendiente instanceof EfectoConObjetivo efecto) {
            efecto.aplicar(jugadorObjetivo, partida);
        } else {
            System.out.println("Intentaste usar un objetivo en una carta que no lo pide.");
        }

        cartaPendiente = null;
        mazoHabilitado = null;
    }

    // Validaciones de estado
    public boolean puedeRobarDeMazo(TipoCarta tipo) {
        return mazoHabilitado == tipo && cartaPendiente == null;
    }

    public boolean hayCartaPendiente() {
        return cartaPendiente != null;
    }

    // Getters publicos
    public Jugador getJugadorActual() {
        return this.jugadorEnTurno;
    }

    public Partida getPartida() {
        return partida;
    }
    
    public TipoCarta getMazoHabilitado() {
        return mazoHabilitado;
    }

    public Carta getCartaPendiente() {
        return cartaPendiente;
    }    
}
