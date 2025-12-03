package modelo.jugador;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import modelo.Dado;

public class Jugador {

    private final String nombre;
    private final ColorJugador color;
    private int posicion;
    private int ultimoMovimiento;
    private boolean requiereActivacion;
    private EstadoJugador estado;
    private final List<EfectoJugador> efectosActivos;
    private int turnosEfecto; 

    public Jugador(String nombre, ColorJugador color) {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del jugador no puede ser nulo o vacío.");
        }
        this.requiereActivacion = false;
        this.nombre = nombre.trim();
        this.color = Objects.requireNonNull(color, "El color no puede ser nulo.");
        this.posicion = 0;
        this.estado = EstadoJugador.NORMAL;
        this.efectosActivos = new ArrayList<>();
        this.ultimoMovimiento = 0;
        this.turnosEfecto = 0;
    }



    public void aplicarEfecto(EfectoJugador efecto) {
        Objects.requireNonNull(efecto, "El efecto no puede ser nulo.");
        this.efectosActivos.add(efecto);
        efecto.aplicarEfectoInicial(this);
    }

    public void actualizarEfectos() {

        Iterator<EfectoJugador> iterador = this.efectosActivos.iterator();

        while (iterador.hasNext()) {
            EfectoJugador efecto = iterador.next();

            efecto.actualizarEfectoPorTurno(this);

            if (efecto.haExpirado()) {
                efecto.removerEfecto(this);
                iterador.remove();
            }
        }
    }



    public void mover(int pasos, int totalCasillas) {
        this.ultimoMovimiento = pasos;

        int nuevaPos = this.posicion + pasos;

        if (nuevaPos < 0) {
            nuevaPos = 0;
        } else if (nuevaPos >= totalCasillas) {
            nuevaPos = totalCasillas - 1;
        }

        this.posicion = nuevaPos;
    }

    public int lanzarDado(Dado dado) {
        Objects.requireNonNull(dado, "El dado no puede ser nulo.");

        boolean tieneMovimientoDoble = this.efectosActivos.stream()
                .anyMatch(efecto -> efecto.getNombre().equals("Movimiento Doble"));

        int resultadoBase = dado.lanzar();

        return tieneMovimientoDoble ? (resultadoBase * 2) : resultadoBase;
    }


    public EstadoJugador getEstado() {
        return estado;
    }

    public void setEstado(EstadoJugador estado) {
        this.estado = estado;
    }

    public boolean estaInmovilizado() {
        return this.estado == EstadoJugador.INMOVILIZADO;
    }

    public String getNombre() {
        return nombre;
    }

    public String getColor() {
        return color.getCodigoHex();
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public int getTurnosEfecto() {
        return turnosEfecto;
    }
    
    public void setTurnosEfecto(int duracion) {
        this.turnosEfecto = duracion;
    }

    public int getUltimoMovimiento() {
        return ultimoMovimiento;
    }

    public boolean requiereActivacion() {
        return requiereActivacion;
    }

    public void setRequiereActivacion(boolean requiereActivacion) {
        this.requiereActivacion = requiereActivacion;
    }

    public List<EfectoJugador> getEfectosActivos() {
        return new ArrayList<>(this.efectosActivos);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Jugador jugador = (Jugador) obj;

        return nombre.equalsIgnoreCase(jugador.nombre);
    }

    @Override
    public String toString() {
        return "Jugador{" + "nombre='" + nombre + '\'' + ", color=" + color + ", posicion=" + posicion + ", estado="
                + estado + '}';
    }
}
