package main;

import mino.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class PlayManager {
    final int WIDTH=360;
    final  int HEIGHT=600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    // Mino
    Mino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;

    //Next Mino
    Mino nextMino;
    final int NEXT_MINO_X;
    final int NEXT_MINO_Y;
    public static ArrayList<Block> staticBloks = new ArrayList<Block>();

    // Others
    public static int dropInterval = 60; // A cada 60 frames o mino vai cair 1 bloco

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

    public void update(){
        // checa se o mino atual esta ativo
        if (!currentMino.active){
            // Se ele não estiver ativo, coloqueo no array de blocos estáticos
            staticBloks.add(currentMino.blocks[0]);
            staticBloks.add(currentMino.blocks[1]);
            staticBloks.add(currentMino.blocks[2]);
            staticBloks.add(currentMino.blocks[3]);

            // Substitui o mino atual pelo próximo mino
            currentMino = nextMino;
            currentMino.setXY(MINO_START_X, MINO_START_Y);
            nextMino = getRandomMino();
            nextMino.setXY(NEXT_MINO_X, NEXT_MINO_Y);
        }else {
            currentMino.update();
        }
    }

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

        // Desenha pause
        g2.setColor(Color.yellow);
        g2.setFont(g2.getFont().deriveFont(50f));
        if (KeyHandler.pausePressed) {
            g2.drawString("PAUSE", left_x + 70, top_y + 320);
        }
    }
}
