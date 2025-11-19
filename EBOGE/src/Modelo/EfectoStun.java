package Modelo;
<<<<<<< HEAD
=======

>>>>>>> 047dcec6e5d36a161280f8dd98b378e3f8089b6b

public class EfectoStun implements EfectoJugador {

    
    private int turnosRestantes;
    private boolean haExpirado;

    
    public EfectoStun(int turnos) {
        
        this.turnosRestantes = Math.max(1, Math.min(turnos, 3));
        this.haExpirado = false;
    }

    
    @Override
    public void aplicarEfectoInicial(Jugador jugador) {
        jugador.setEstado(EstadoJugador.INMOVILIZADO);
    }

    
    @Override
    public void actualizarEfectoPorTurno(Jugador jugador) {
        if (this.haExpirado) {
            return;
        }

        this.turnosRestantes--;

        if (this.turnosRestantes <= 0) {
            this.haExpirado = true;
        }
    }

    
    @Override
    public void removerEfecto(Jugador jugador) {
        
        long otrosStunsActivos = jugador.getEfectosActivos().stream()
                .filter(efecto -> efecto instanceof EfectoStun)
                .filter(efecto -> !efecto.equals(this))
                .count();

        
        if (otrosStunsActivos == 0) {
            jugador.setEstado(EstadoJugador.NORMAL);
        }
    }

    
    @Override
    public boolean haExpirado() {
        return this.haExpirado;
    }

    @Override
    public String getNombre() {
        return "Inmovilizado";
    }
}