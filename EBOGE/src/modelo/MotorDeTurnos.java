package modelo;

import java.util.HashMap;
import java.util.Map;
import modelo.jugador.Jugador;
import modelo.mapa.TipoCasilla;
// Asegúrate de importar tus acciones...

public class MotorDeTurnos {

    private Partida partida; 
    private Jugador jugadorEnTurno; 
    
    // Strategy Pattern
    private Map<TipoCasilla, AccionCasilla> accionesPorCasilla; 

    public MotorDeTurnos(Partida partida) {
        this.partida = partida;
        this.accionesPorCasilla = new HashMap<>();
        inicializarAcciones();
    }

    private void inicializarAcciones() {
        
        accionesPorCasilla.put(TipoCasilla.PROPIA,   new AccionCasillaPropia());
        accionesPorCasilla.put(TipoCasilla.GLOBAL,   new AccionCasillaGlobal());
        accionesPorCasilla.put(TipoCasilla.MAESTRA,  new AccionCasillaMaestra());
        accionesPorCasilla.put(TipoCasilla.BLANCO,   new AccionCasillaBlanco());  
        accionesPorCasilla.put(TipoCasilla.TRAMPA,   new AccionCasillaTrampa());
        
        accionesPorCasilla.put(TipoCasilla.NORMAL,   new AccionCasillaSegura());
        accionesPorCasilla.put(TipoCasilla.INICIO,   new AccionCasillaSegura());
        
        accionesPorCasilla.put(TipoCasilla.FINAL,    new AccionCasillaVictoria()); 
    }


    public Jugador iniciarTurno() {
        this.jugadorEnTurno = partida.getJugadorActual();
        
        // Aquí validarías si está aturdido, etc.
        // if (jugadorEnTurno.estaAturdido()) return null;
        
        return jugadorEnTurno;
    }

    public int lanzarDado() {
        return partida.getDado().lanzar();
    }

    // Este método solo actualiza el dato lógico. La animación la hace el Controller.
    public int calcularNuevaPosicion(int pasos) {
        int totalCasillas = partida.getMapa().getTotalCasillas();
        jugadorEnTurno.avanzar(pasos, totalCasillas);
        return jugadorEnTurno.getPosicion();
    }

    public TipoCasilla obtenerTipoCasillaActual() {
        return partida.getMapa().identificarTipoDeCasilla(jugadorEnTurno.getPosicion());
    }

    public void ejecutarAccionEnCasilla() {
        int posicion = jugadorEnTurno.getPosicion();
        TipoCasilla tipo = partida.getMapa().identificarTipoDeCasilla(posicion);
        
        AccionCasilla accion = accionesPorCasilla.get(tipo);
        if (accion != null) {
            accion.ejecutar(partida, jugadorEnTurno); 
        }
        
    }
    
    //Para turnos dobles, el controlador debe manejar esto
    public boolean requiereOtroTurno() {
    	return jugadorEnTurno.requiereActivacion();
    }

    public void terminarTurno() {
        // Verificar si alguien ganó (generalmente lo maneja AccionCasillaVictoria, 
        // pero aquí es el cambio de turno)
        if (!partida.getJuegoTerminado()) {
            partida.pasarAlSiguienteJugador();
        }
    }
    
    public Jugador getJugadorActual() {
    	return this.jugadorEnTurno;
    }
}