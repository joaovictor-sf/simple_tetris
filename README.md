# Um Simples Jogo de Tetris
Este é o desenvolvimento de um simples jogo de Tetris, utilizando a linguagem de programação Java e a biblioteca gráfica Swing.

Meu objetivo com este projeto é aprender sobre a criação de jogos e aprimorar minhas habilidades de programação.

## Detalhes do Projeto
- As dimensões padrões do jogo são 1280x720 pixels.
- Game Loop com 60 FPS. Quer dizer que o jogo atualiza 60 vezes por segundo.

## Estudos

### Game Loop
O Game Loop é um padrão de projeto que é utilizado para controlar o tempo de execução de um jogo. Ele é responsável por <b>atualizar</b> a lógica do jogo</b> e <b>renderizar os gráficos</b>.

## Estrutura do projeto

### Main
```java
public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Simple Tetris");// Cria uma janela com o título "Simple Tetris"
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Fecha a aplicação quando a janela é fechada
        window.setResizable(false);// Impede que a janela seja redimensionada pelo usuário

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();// Redimensiona a janela para que caiba o JPanel

        window.setLocationRelativeTo(null);// Quer dizer que a não especificamos a posição da janela, então ela será centralizada na tela
        window.setVisible(true);
    }
}
```

A classe `Main` é responsável por iniciar a aplicação. Ela cria uma janela com o título "Simple Tetris" e adiciona um `GamePanel` à janela.

### GamePanel
```java
public class GamePanel extends JPanel implements Runnable {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    final int FPS = 60;
    Thread gameThread;
    PlayManager playManager;

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setLayout(null);// Desabilita o layout padrão do JPanel

        playManager = new PlayManager();
    }

    public void start() {
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

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        playManager.draw(g2);
    }
}
```

A classe `GamePanel` é responsável por gerenciar o jogo. Ela implementa a interface `Runnable` para criar um `game loop` com 60 FPS.

### PlayManager
```java
public class PlayManager {
    final int WIDTH=360;
    final  int HEIGHT=600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    public PlayManager() {
        // Define a posição do retângulo de jogo
        left_x = (GamePanel.WIDTH / 2) - (WIDTH / 2); // 1280/2 - 360/2 = 460
        right_x = left_x + WIDTH; // 460 + 360 = 820
        top_y = 50;
        bottom_y = top_y + HEIGHT;
    }

    public void update(){

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


    }
}
```

A classe `PlayManager` é responsável por gerenciar a lógica do jogo. Ela define a posição do retângulo de jogo e desenha o frame do jogo.

### Block
```java
public class Block extends Rectangle {
    public int x, y;
    public static final int SIZE = 30;
    public Color color;

    public Block(Color color) {
        this.color = color;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fillRect(x, y, SIZE, SIZE);
    }
}
```

A classe `Block` é responsável por representar um bloco do jogo. Ela estende a classe `Rectangle` e define a cor do bloco.

Cada peça do jogo é composta por 4 blocos. Cada bloco é representado por um objeto da classe `Block`.

## Ideias
- Adicionar get/set quando necessário
- Concertar os erros apontados pelo sonarlint
- Adicionar multiplayer(local e online)
- Fazer o javaDoc
- Melhorar certas partes do código
- Adicionar menus(Inicial, pause e game over)
- Adicionar opção de salvar pontuação