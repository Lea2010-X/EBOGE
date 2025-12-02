package modelo;

import java.util.ArrayList;
import java.util.List;

import modelo.mapa.TipoCasilla;

public class CambioCasillaEventManager {

    private final List<CambioCasillaListener> listeners = new ArrayList<>();

    public void suscribir(CambioCasillaListener listener) {
        listeners.add(listener);
    }

    public void desuscribir(CambioCasillaListener listener) {
        listeners.remove(listener);
    }

    public void notificar(int indiceCasilla, TipoCasilla tipoAnterior, TipoCasilla nuevoTipo) {
        for (CambioCasillaListener l : listeners) {
            l.tipoCasillaCambiado(indiceCasilla, tipoAnterior, nuevoTipo);
        }
    }
}

