package modelo;

import modelo.mapa.TipoCasilla;

public interface CambioCasillaListener {
    void tipoCasillaCambiado(int indiceCasilla, TipoCasilla tipoAnterior, TipoCasilla nuevoTipo);
}
