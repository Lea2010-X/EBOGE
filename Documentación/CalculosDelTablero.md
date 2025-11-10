# Cálculos para el tablero

El tablero se encuentra en un **panel** con medidas **W × H** y debe ser lo suficientemente grande para que el juego dure **R** rondas con un **dado de L lados**.

---

## Número de casillas

Para calcular el número de casillas tomamos en cuenta la **esperanza** de lanzar de un dado de $L$ lados (todos los lados con la misma probabilidad):

$$
E(\text{lanzamiento}) = \frac{1}{L}\sum_{k=1}^{L} k = \frac{L(L+1)}{2L} = \frac{L+1}{2}
$$

Como espero que una partida dure aproximadamente **R** rondas, el número total de casillas es:

$$
C = R \cdot \frac{L+1}{2}
$$

---

## Acomodar las casillas en el panel

Cada casilla tendrá medidas **wc × hc**. Por lo tanto, la cantidad de casillas por fila y por columna cumple:

$$
\frac{W}{wc} = n, \qquad \frac{H}{hc} = m, \qquad n,m \in \mathbb{N}
$$

$n$ y $m$ deberian cumplir:

$$
n \cdot m = C
$$

![espiral](espiral.png)



También podemos decir que los píxeles totales cubiertos por todas las casillas satisfacen:

$$
n \times m \times (wc \times hc) = W \times H
$$

De aquí:

$$
\frac{n}{m} \approx \frac{W}{H}
\quad \Longrightarrow \quad
n = \frac{W}{H}\, m
$$

Recordando que n * m = C :

$$
\frac{W}{H}\, m^2 = C
\quad \Longrightarrow \quad
m^2 = \frac{C\,H}{W}
\quad \Longrightarrow \quad
m = \sqrt{\frac{C\,H}{W}}
$$

despues despejamos n de la misma forma....

Conociendo n y m, se obtienen los tamaños de casilla:

$$
wc = \frac{W}{n},
\qquad
hc = \frac{H}{m}
$$
