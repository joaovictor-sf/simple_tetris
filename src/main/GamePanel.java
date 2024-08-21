package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 660; //720 - Só fiz essa mudança pq a tela do meu note é pequena
    final int FPS = 60;
    Thread gameThread;
    PlayManager playManager;

    public GamePanel() {
        // Configurações do JPanel
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setLayout(null);// Desabilita o layout padrão do JPanel

        // Implementa o KeyHandler
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);

        // Inicializa o PlayManager
        playManager = new PlayManager();
    }

    public void LaunchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        // Game loop
        double drawInterval = 1000000000.0/FPS;
        double delta = 0;// Armazena o tempo que falta para atualizar o jogo
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    private void update() {
        if (!KeyHandler.pausePressed && !playManager.gameover) {
            playManager.update();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        playManager.draw(g2);
    }
}
