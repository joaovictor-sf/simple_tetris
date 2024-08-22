package main;

import mino.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Classe que gerencia o jogo.
 * Ela é responsável por criar os minos, checar se o mino está ativo, checar se a linha está completa, deletar a linha,
 * mover os blocos para baixo, calcular a pontuação, desenhar o frame do jogo, desenhar o mino, desenhar o próximo mino,
 * desenhar os blocos estáticos, desenhar o efeito, desenhar o pause e desenhar o título do jogo.
 * Ela também é responsável por definir a posição do retângulo de jogo, a posição inicial do mino, a posição do próximo mino,
 * a velocidade de queda do mino, se o jogo acabou, se o efeito está ativo, o contador do efeito, a lista de posições do efeito,
 * o nível, as linhas e a pontuação.
 *
 * @version 1.0
 * @since 1.0
 * @see Mino
 * @author joaovictor-sf
 */
public class PlayManager {
    /**
     * Largura do retângulo de jogo.
     */
    final int WIDTH=360;
    /**
     * Altura do retângulo de jogo.
     */
    final  int HEIGHT=600;
    /**
     * Posição x do retângulo de jogo.
     */
    public static int left_x;
    /**
     * Posição x do retângulo de jogo.
     */
    public static int right_x;
    /**
     * Posição y do retângulo de jogo.
     */
    public static int top_y;
    /**
     * Posição y do retângulo de jogo.
     */
    public static int bottom_y;

    /**
     * Mino atual.
     */
    Mino currentMino;
    /**
     * Posição x do mino atual.
     */
    final int MINO_START_X;
    /**
     * Posição y do mino atual.
     */
    final int MINO_START_Y;

    /**
     * Próximo mino.
     */
    Mino nextMino;
    /**
     * Posição x do próximo mino.
     */
    final int NEXT_MINO_X;
    /**
     * Posição y do próximo mino.
     */
    final int NEXT_MINO_Y;
    /**
     * Lista de blocos estáticos.
     */
    public static ArrayList<Block> staticBloks = new ArrayList<Block>();

    /**
     * Velocidade de queda do mino. A cada 60 frames o mino vai cair 1 bloco.
     */
    public static int dropInterval = 60; // A cada 60 frames o mino vai cair 1 bloco
    /**
     * Se o jogo acabou.
     */
    boolean gameover;


    /**
     * Se o efeito está ativo.
     */
    boolean effectCounterOn;
    /**
     * Contador do efeito.
     */
    int effectCounter;
    /**
     * Lista de posições do efeito.
     */
    ArrayList<Integer> effectY = new ArrayList<>();


    /**
     * Level atual
     */
    int level = 1;
    /**
     * Linhas completas.
     */
    int lines;
    /**
     * Pontuação.
     */
    int score;

    /**
     * Construtor da classe PlayManager.
     * Define a posição do retângulo de jogo, a posição inicial do mino, a posição do próximo mino e cria um novo mino.
     */
    public PlayManager() {
        // Define a posição do retângulo de jogo
        left_x = (GamePanel.WIDTH / 2) - (WIDTH / 2); // 1280/2 - 360/2 = 460
        right_x = left_x + WIDTH; // 460 + 360 = 820
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        // Define a posição inicial do mino
        MINO_START_X = left_x + (WIDTH/2) - Block.SIZE;// 460 + 180 - 30 = 610 Vai ser o centro do retângulo
        MINO_START_Y = top_y + Block.SIZE;// 50 + 30 = 80 Vai ser o topo do retângulo

        // Define a posição do próximo mino
        NEXT_MINO_X = right_x + 175;
        NEXT_MINO_Y = top_y + 505;

        // Cria um novo mino
        currentMino = getRandomMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);
        nextMino = getRandomMino();
        nextMino.setXY(NEXT_MINO_X, NEXT_MINO_Y);
    }

    /**
     * Método que retorna um mino aleatório.
     * @return Mino - mino aleatório
     */
    private Mino getRandomMino(){
        int random = new Random().nextInt(7);
        return switch (random) {
            case 0 -> new Bar_Mino();
            case 1 -> new Mino_Z1();
            case 2 -> new Mino_Square();
            case 3 -> new Mino_Z2();
            case 4 -> new Mino_L1();
            case 5 -> new Mino_T();
            case 6 -> new Mino_L2();
            default -> null;
        };
    }

    /**
     * Método que atualiza o jogo.<br>
     * Checa se o mino atual está ativo, se ele não estiver ativo, coloca-o no array de blocos estáticos, checa se o jogo acabou,
     * substitui o mino atual pelo próximo mino, checa se a linha está completa e chama o método checkDelete.<br>
     * Quando o mino se torna inativo, checa se a linha está completa.<br>
     * Calcula a pontuação.<br>
     * @see #checkDelete()
     */
    public void update(){
        // checa se o mino atual esta ativo
        if (!currentMino.active){
            // Se ele não estiver ativo, coloqueo no array de blocos estáticos
            staticBloks.add(currentMino.blocks[0]);
            staticBloks.add(currentMino.blocks[1]);
            staticBloks.add(currentMino.blocks[2]);
            staticBloks.add(currentMino.blocks[3]);

            // check if gameover
            if (currentMino.blocks[0].x == MINO_START_X && currentMino.blocks[0].y == MINO_START_Y) {
                gameover = true;
                GamePanel.soundEffect.play(2, false);
            }

            currentMino.deactivating = false;

            // Substitui o mino atual pelo próximo mino
            currentMino = nextMino;
            currentMino.setXY(MINO_START_X, MINO_START_Y);
            nextMino = getRandomMino();
            nextMino.setXY(NEXT_MINO_X, NEXT_MINO_Y);

            // Quando o mino se torna inativo, checa se a linha está completa
            checkDelete();
        }else {
            currentMino.update();
        }
    }

    /**
     * Método que checa se a linha está completa.<br>
     * Checa cada bloco do retângulo e para cada bloco, checa se ele está na lista de blocos estáticos.<br>
     * Se o bloco estiver na lista de blocos estáticos, incrementa o contador.<br>
     * Se o contador for igual a 12, deleta a linha, incrementa o contador de linhas e move os blocos para baixo.<br>
     * Calcula a pontuação.<br>
     */
    private void checkDelete() {
        int x = left_x;
        int y = top_y;
        int count = 0;
        int lineCount = 0;


        while (x < right_x && y < bottom_y) {// Checa cada bloco do retângulo
            for (int i = 0; i < staticBloks.size(); i++) {
                if (staticBloks.get(i).x == x && staticBloks.get(i).y == y) {
                    count++;
                }
            }
            x += Block.SIZE;
            if (x == right_x) {

                if (count == 12) {
                    effectCounterOn = true;
                    effectY.add(y);

                    for (int i = staticBloks.size() - 1; i > -1 ; i--) {
                        if (staticBloks.get(i).y == y) {
                            staticBloks.remove(i);
                        }
                    }

                    lineCount++;
                    lines++;

                    // Drop Speed
                    // if the line score hits a certain number, incrise the drop speed
                    // 1 is the fastest
                    if (lines % 10 == 0 && dropInterval > 1){
                        level++;
                        if (dropInterval > 10) {
                            dropInterval -= 10;
                        } else {
                            dropInterval--;
                        }
                    }

                    // Move os blocos para baixo
                    for (int i = 0; i < staticBloks.size(); i++) {
                        if (staticBloks.get(i).y < y) {
                            staticBloks.get(i).y += Block.SIZE;
                        }
                    }
                }

                count = 0;
                x = left_x;
                y += Block.SIZE;
            }
        }

        // Calcula a pontuação
        if (lineCount > 0){
            GamePanel.soundEffect.play(1, false);
            int sigleLineScore = 10 * level;
            score += sigleLineScore * lineCount;
        }
    }

    /**
     * Método que desenha o frame do jogo, o frame de next, o mino, o próximo mino, os blocos estáticos, o efeito, o pause e o título do jogo.
     * @param g2 - Graphics2D
     */
    public void draw(Graphics2D g2){
        // Desenha o frame do jogo
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x-4, top_y-4, WIDTH+8, HEIGHT+8);

        // Desenha o frame de next
        int x = right_x  + 100;
        int y = bottom_y - 200;
        g2.drawRect(x, y, 200, 200);
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("Next", x + 60, y + 60);

        g2.drawRect(x, top_y, 250, 300);
        x += 40;
        y = top_y + 90;
        g2.drawString("Level: " + level, x, y); y += 70;
        g2.drawString("Lines: " + lines, x, y); y += 70;
        g2.drawString("Score: " + score, x, y);

        // Desenha o mino
        if (currentMino != null) {
            currentMino.draw(g2);
        }

        // Desenha o próximo mino
        nextMino.draw(g2);

        // Desenha os blocos estáticos
        for (int i = 0; i < staticBloks.size(); i++) {
            staticBloks.get(i).draw(g2);
        }

        // Draw effect
        if (effectCounterOn){
            effectCounter++;

            g2.setColor(Color.white);
            for (int i = 0; i < effectY.size(); i++){
                g2.fillRect(left_x, effectY.get(i), WIDTH, Block.SIZE );
            }

            if (effectCounter == 10){
                effectCounterOn = false;
                effectCounter = 0;
                effectY.clear();
            }
        }

        // Desenha pause
        g2.setColor(Color.yellow);
        g2.setFont(g2.getFont().deriveFont(50f));
        if (gameover){
            x = left_x + 25;
            y = top_y + 320;
            g2.drawString("GAME OVER", x, y);
        }
        if (KeyHandler.pausePressed) {
            g2.drawString("PAUSE", left_x + 70, top_y + 320);
        }

        // Draw the Game Title
        x = 35;
        y = top_y + 320;
        g2.setColor(Color.white);
        g2.setFont(new Font("Times New Roman", Font.ITALIC, 60));
        g2.drawString("Simples Tetris", x, y);

    }
}
