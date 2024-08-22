package mino;

import java.awt.*;

/**
 * Classe que representa um dos blocos que compõem as peças do jogo.
 * @author joaovictor-sf
 */
public class Block extends Rectangle {
    /**
     * Coordenadas x e y do bloco.
     */
    public int x, y;
    /**
     * Tamanho do bloco. Os blocos possuem tamanho fixo de 30x30 pixels.
     */
    public static final int SIZE = 30;
    /**
     * Cor do bloco.
     */
    public Color color;

    /**
     * Construtor da classe Block.
     * @param color Cor do bloco.
     */
    public Block(Color color) {
        this.color = color;
    }

    /**
     * Método que desenha o bloco na tela.
     * @param g2 Objeto Graphics2D que será utilizado para desenhar o bloco.
     */
    public void draw(Graphics2D g2) {
        int margin = 2;
        g2.setColor(color);
        g2.fillRect(x + margin, y + margin, SIZE -(margin*2), SIZE - (margin*2));
    }
}
