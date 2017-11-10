package pkg14b.conqueror;
//Fixes: no cities on border for some reason
//Next Steps: AI, Balancing, Military/Explorers, Exploration, Dynamic Tiles
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Conqueror extends JFrame implements KeyListener, MouseListener {
    Level gen = new Level();
    Game game = new Game(gen);
    public BufferedImage[] img = new BufferedImage[79];//one more than highest
    private final int width = 45, height = 26;
    private final int scale = 2;
    private int mouseX = 0, mouseY = 0; 
    int phase = 0, action = 4, turn = 1;
    int tileheight = 16;
    int tilewidth = 16;
    boolean ownView = true, resView = false;
    @SuppressWarnings("LeakingThisInConstructor")
    public Conqueror() {
        
        //creating the window
        super("Conqueror");
        GamePane pane = new GamePane();
        add(pane);
        this.addKeyListener(this);
        this.addMouseListener(this);
        
        pane.setVisible(true);
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Conqueror frame = new Conqueror();
            frame.pack();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        });
    }
    public class GamePane extends JPanel {
        public GamePane() {
            loadImages();
            gen.gen(137, 1, 0, true);
            Timer timer = new Timer(50, (ActionEvent e) -> {
                repaint();
                game.update();
                updateMouse();
            });
            timer.start();
        }
        
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(width * tilewidth * scale, height * tileheight * scale);
        }

        public void loadImages() {
            for (int i = 0; i < 79; i++) {//one more than highest
                try {
                    img[i] = ImageIO.read(this.getClass().getResource("Con"+i+".png"));
                    Graphics2D g2d = img[i].createGraphics();
                    g2d.dispose();
                } catch (IOException ex) {
                    System.out.println("Missing Image");
                }
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setTransform(AffineTransform.getScaleInstance(scale, scale));
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    gen.u[x][y] = 0;
                    g2d.drawImage(img[gen.t[x][y]], x * tilewidth, y * tileheight, this);
                    if(game.dynamic(x, y)!=0)g2d.drawImage(img[63+game.dynamic(x, y)], x * tilewidth, y * tileheight, this);
                    if(gen.i[x][y] != 0 && gen.i[x][y] != 5){
                        g2d.drawImage(img[gen.i[x][y]+5], x * tilewidth, y * tileheight, this);
                    }else if(gen.i[x][y] == 5){
                        g2d.drawImage(img[42], x * tilewidth, y * tileheight, this);
                    }
                }
            }
            for(int i = 1; i < game.unit.size(); i++){
                gen.u[game.GeU(i).x][game.GeU(i).y] = i;
                if(gen.t[game.GeU(i).x][game.GeU(i).y]==0){
                    g2d.drawImage(img[50+game.GeU(i).pl], game.GeU(i).x*tilewidth, game.GeU(i).y*tileheight, this);
                }
                else{
                    g2d.drawImage(img[56+game.GeU(i).pl], game.GeU(i).x*tilewidth, game.GeU(i).y*tileheight, this);
                }
            }
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if(ownView && gen.o[x][y] == 1){
                        g2d.drawImage(img[10], x * tilewidth, y * tileheight, this);
                    }else if(ownView && gen.o[x][y] != 0){
                        g2d.drawImage(img[43+gen.o[x][y]], x * tilewidth, y * tileheight, this);
                    }
                    if(resView){
                        switch (gen.s[x][y]) {
                            case 0:
                                switch (gen.f[x][y]) {
                                    case 1:
                                        g2d.drawImage(img[11], x * tilewidth, y * tileheight, this);
                                        break;
                                    case 2:
                                        g2d.drawImage(img[12], x * tilewidth, y * tileheight, this);
                                        break;
                                    case 3:
                                        g2d.drawImage(img[13], x * tilewidth, y * tileheight, this);
                                        break;
                                    default:
                                        break;
                                }   break;
                            case 1:
                                switch (gen.f[x][y]) {
                                    case 0:
                                        g2d.drawImage(img[14], x * tilewidth, y * tileheight, this);
                                        break;
                                    case 1:
                                        g2d.drawImage(img[15], x * tilewidth, y * tileheight, this);
                                        break;
                                    case 2:
                                        g2d.drawImage(img[16], x * tilewidth, y * tileheight, this);
                                        break;
                                    case 3:
                                        g2d.drawImage(img[17], x * tilewidth, y * tileheight, this);
                                        break;
                                    default:
                                        break;
                                }   break;
                            case 2:
                                switch (gen.f[x][y]) {
                                    case 0:
                                        g2d.drawImage(img[18], x * tilewidth, y * tileheight, this);
                                        break;
                                    case 1:
                                        g2d.drawImage(img[19], x * tilewidth, y * tileheight, this);
                                        break;
                                    case 2:
                                        g2d.drawImage(img[20], x * tilewidth, y * tileheight, this);
                                        break;
                                    case 3:
                                        g2d.drawImage(img[21], x * tilewidth, y * tileheight, this);
                                        break;
                                    default:
                                        break;
                                }   break;
                            case 3:
                        switch (gen.f[x][y]) {
                            case 0:
                                g2d.drawImage(img[22], x * tilewidth, y * tileheight, this);
                                break;
                            case 1:
                                g2d.drawImage(img[23], x * tilewidth, y * tileheight, this);
                                break;
                            case 2:
                                g2d.drawImage(img[24], x * tilewidth, y * tileheight, this);
                                break;
                            case 3:
                                g2d.drawImage(img[25], x * tilewidth, y * tileheight, this);
                                break;
                            default:
                                break;
                        }
                        break;
                            default:
                                break;
                        }
                    }
                    if(gen.select)g2d.drawImage(img[63], gen.sx*tilewidth, gen.sy*tileheight, this);
                    if(action != 4){
                        g2d.drawImage(img[38+action], (width*tilewidth)-16, 0, this);
                    }else{
                        g2d.drawImage(img[43], (width*tilewidth)-16, 0, this);
                    }
                    
                    g2d.drawImage(img[26], 1, 0, this);
                    g2d.drawImage(img[27], 46, 0, this);
                    g2d.drawImage(img[44], 91, 0, this);
                    for(int i = 0; i < 5; i++){
                        if(gen.gold[1] >= 0){
                            g2d.drawImage(img[28+(int)((gen.gold[1]/(10000/Math.pow(10,i)))%10)], 24+(4*i), 0, this);
                        }else{
                            g2d.drawImage(img[28], 24+(4*i), 0, this);
                        }
                    }
                    for(int i = 0; i < 5; i++){
                        if(gen.gold[1] >= 0){
                            g2d.drawImage(img[28+(int)((gen.food[1]/(10000/Math.pow(10,i)))%10)], 69+(4*i), 0, this);
                        }else{
                            g2d.drawImage(img[28], 69+(4*i), 0, this);
                        }
                    }
                    for(int i = 0; i < 5; i++){
                        if(gen.gold[1] >= 0){
                            g2d.drawImage(img[28+(int)((gen.popu[1]/(10000/Math.pow(10,i)))%10)], 114+(4*i), 0, this);
                        }else{
                            g2d.drawImage(img[28], 114+(4*i), 0, this);
                        }
                    }
                    if(!gen.r[x][y]) g2d.drawImage(img[50], x*tilewidth, y*tileheight, this);
                }
            }
            g2d.dispose();
        }
        
        private void updateMouse(){
            mouseX=(MouseInfo.getPointerInfo().getLocation().x-getLocationOnScreen().x)/tilewidth/scale;
            mouseY=(MouseInfo.getPointerInfo().getLocation().y-getLocationOnScreen().y)/tileheight/scale;
        } 
        
    }
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(gen.select){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    game.GeU(gen.u[gen.sx][gen.sy]).move(0);
                    gen.select = false;break;
                case KeyEvent.VK_DOWN:
                    game.GeU(gen.u[gen.sx][gen.sy]).move(1);
                    gen.select = false;break;
                case KeyEvent.VK_LEFT:
                    game.GeU(gen.u[gen.sx][gen.sy]).move(2);
                    gen.select = false;break;
                case KeyEvent.VK_RIGHT:
                    game.GeU(gen.u[gen.sx][gen.sy]).move(3);
                    gen.select = false;break;
                default:break;
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_O){
            ownView = !ownView;
        }
        if(e.getKeyCode()==KeyEvent.VK_R){
            resView = !resView;
        }
        if(e.getKeyCode()==KeyEvent.VK_F){
            action = 1;
        }
        if(e.getKeyCode()==KeyEvent.VK_M){
            action = 2;
        }
        if(e.getKeyCode()==KeyEvent.VK_U){
            action = 3;
        }
        if(e.getKeyCode()==KeyEvent.VK_C){
            action = 4;
        }
        if(e.getKeyCode()==KeyEvent.VK_N){
            action = 5;
        }
        if(e.getKeyCode()==KeyEvent.VK_P){
            action = 0;
        }
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            game.turn();
        }
        if(e.getKeyCode()==KeyEvent.VK_E){
            for(int x = 0; x < gen.width; x++)
                for(int y = 0; y < gen.height; y++)
                    gen.r[x][y]=true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(phase == 0 && gen.t[mouseX][mouseY] != 0){
            game.city(mouseX, mouseY, 1);
            phase++;
            game.turn();
        }
        if(e.getButton()==MouseEvent.BUTTON1){
            if(action == 0 && gen.gold[1] >= 50){
                game.prop(mouseX, mouseY, 1);
            }
            if(action == 1 && gen.gold[1] >= 10){
                game.farm(mouseX, mouseY, 1);
            }
            if(action == 2 && gen.gold[1] >= 10){
                game.mine(mouseX, mouseY, 1);
            }
            if(action == 3 && gen.gold[1] >= 10){
                game.upgrade(mouseX, mouseY, 1);
            }
            if(action == 4 && gen.gold[1] >= 300){
                game.city(mouseX, mouseY, 1);
            }
            if(action == 5){
                game.makeUnit(mouseX, mouseY, 1);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}