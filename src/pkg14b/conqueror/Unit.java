package pkg14b.conqueror;

public class Unit {
    int x = 0, y = 0, pl;
    boolean moved = false;
    Level gen;
    public Unit(Level gen, int x, int y, int pl){
        this.gen = gen;
        this.pl = pl;
        this.x = x; this.y = y;
    }
    public void move(int dir){
        if(!moved&&gen.edge(x,y,dir)){
            moved = true;
            switch (dir){
                case 0:y--;break;case 1:y++;break;
                case 2:x--;break;case 3:x++;break;
            }
        }
    }
}
