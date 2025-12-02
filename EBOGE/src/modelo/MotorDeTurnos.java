package modelo;

import java.util.HashMap;
import java.util.Map;

import modelo.cartas.Carta;
import modelo.cartas.EfectoSinObjetivo;
import modelo.cartas.Mazo;
import modelo.cartas.TipoCarta;
import modelo.jugador.Jugador;
import modelo.mapa.TipoCasilla;


public class MotorDeTurnos {

    private Partida partida;
    private Jugador jugadorEnTurno;

    private Map<TipoCasilla, AccionCasilla> accionesPorCasilla;


    private TipoCarta mazoHabilitado;  
    private Carta cartaPendiente;      

    public MotorDeTurnos(Partida partida) {
        this.partida = partida;
        this.accionesPorCasilla = new HashMap<>();
        inicializarAcciones();
    }

    private void inicializarAcciones() {

        accionesPorCasilla.put(TipoCasilla.TRAMPA, new AccionCasillaTrampa());
        accionesPorCasilla.put(TipoCasilla.NORMAL, new AccionCasillaSegura());
        accionesPorCasilla.put(TipoCasilla.INICIO, new AccionCasillaSegura());
        accionesPorCasilla.put(TipoCasilla.FINAL,  new AccionCasillaVictoria());

    }

    public Jugador iniciarTurno() {
        this.jugadorEnTurno = partida.getJugadorActual();

        cartaPendiente = null;
        mazoHabilitado = null;
        return jugadorEnTurno;
    }

    public int lanzarDado() {
    	jugadorEnTurno.actualizarEfectos();
    	if(jugadorEnTurno.estaInmovilizado())
    	{
    		System.out.println("El Jugador "+ jugadorEnTurno+ " esta estuneado se salta el turno");
    		terminarTurno();
    		return 0;
    	}
        return partida.jugadorActualLanzaDado();
    }

    public int calcularNuevaPosicion(int pasos) {
        partida.moverPorDado(jugadorEnTurno, pasos);
        return jugadorEnTurno.getPosicion();
    }


    public void ejecutarAccionEnCasilla() {

        if (jugadorEnTurno == null)
            return;

        if (!jugadorEnTurno.requiereActivacion())
            return;

        int posicion = jugadorEnTurno.getPosicion();
        TipoCasilla tipo = partida.getMapa().identificarTipoDeCasilla(posicion);


        switch (tipo) {
            case PROPIA  -> mazoHabilitado = TipoCarta.PROPIA;
            case BLANCO  -> mazoHabilitado = TipoCarta.BLANCO;
            case GLOBAL  -> mazoHabilitado = TipoCarta.GLOBAL;
            case MAESTRA -> mazoHabilitado = TipoCarta.MAESTRA;
            default -> {

                AccionCasilla accion = accionesPorCasilla.get(tipo);
                if (accion != null) {
                    accion.ejecutar(partida, jugadorEnTurno);
                }
            }
        }

        jugadorEnTurno.setRequiereActivacion(false);
    }


	public boolean requiereOtroTurno() {
		return jugadorEnTurno != null && jugadorEnTurno.requiereActivacion();
	}

	public void terminarTurno() {
		if (!partida.getJuegoTerminado()) {
			partida.pasarAlSiguienteJugador();
		}
	}

	public Jugador getJugadorActual() {
		return this.jugadorEnTurno;
	}


	public TipoCarta getMazoDisponibleEnCasillaActual() {
		TipoCasilla tipoCasilla = getTipoCasillaActual();
		if (tipoCasilla == null)
			return null;

		switch (tipoCasilla) {
		case PROPIA:
			return TipoCarta.PROPIA;
		case GLOBAL:
			return TipoCarta.GLOBAL;
		case BLANCO:
			return TipoCarta.BLANCO;
		case MAESTRA:
			return TipoCarta.MAESTRA;
		default:
			return null; 
		}
	}
	public Partida getPartida() {
		return partida;
	}


	private TipoCasilla getTipoCasillaActual() {
		if (jugadorEnTurno == null)
			return null;

		int pos = jugadorEnTurno.getPosicion();
		return partida.getMapa().identificarTipoDeCasilla(pos);
	}
	
	 public TipoCarta getMazoHabilitado() {
	        return mazoHabilitado;
	    }

	    public boolean puedeRobarDeMazo(TipoCarta tipo) {
	        return mazoHabilitado == tipo && cartaPendiente == null;
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



	    public boolean hayCartaPendiente() {
	        return cartaPendiente != null;
	    }

	    public Carta getCartaPendiente() {
	        return cartaPendiente;
	    }

	    public void usarCartaPendiente() {
	        if (cartaPendiente == null) return;

	        if (cartaPendiente instanceof EfectoSinObjetivo efecto) {
	            efecto.aplicar(partida);
	        } else {

	            System.out.println("La carta pendiente tiene un tipo de efecto no manejado todavía.");
	        }


	        cartaPendiente = null;
	        mazoHabilitado = null;
	    }
}
