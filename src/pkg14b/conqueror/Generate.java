package pkg14b.conqueror;

public class Generate {
    final int width = 45, height = 26;
    int turn = 1;
    int food[] = new int[7];//50
    int gold[] = new int[7];//100
    int farmA[] = new int[7];
    int mineA[] = new int[7];
    int cityA[] = new int[7];
    int popu[] = new int[7];
    boolean a[] = new boolean[7];
    int t[][] = new int[width][height]; //terrain type
    int f[][] = new int[width][height]; //food[1]
    int s[][] = new int[width][height]; //gold[1]
    int o[][] = new int[width][height]; //owner
    int i[][] = new int[width][height]; //improvement
    boolean is0 = false;
    public void gen(int size, int toVal, int fromVal, boolean inland){
        for(int i = 1; i < 7; i++){
            food[i] = 53;
            gold[i] = 403;
            popu[i] = 3;
        }
        is0 = true;
        boolean w[][] = new boolean[width][height];
        boolean c[][] = new boolean[width][height];
        boolean m[][] = new boolean[width][height];
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                if(t[x][y] == fromVal){
                    if (Math.random() < .03){
                        w[x][y] = true;
                    }
                }
            }
        }
        for (int i = 0; i < size; i++){
            for (int x = 0; x < width; x++){
                for (int y = 0; y < height; y++){
                    if(t[x][y] == fromVal){
                        c[x][y] = false;
                        if(!w[x][y]){
                            if(x >= 1){
                                if (w[x-1][y]){
                                    if (Math.random() > .99){
                                        c[x][y] = true;
                                    }
                                }
                            }
                            if(x < width-1){
                                if (w[x+1][y]){
                                    if (Math.random() > .99){
                                        c[x][y] = true;
                                    }
                                }
                            }
                            if(y >= 1){
                                if (w[x][y-1]){
                                    if (Math.random() > .99){
                                        c[x][y] = true;
                                    }
                                }
                            }
                            if(y < height-1){
                                if (w[x][y+1]){
                                    if (Math.random() > .99){
                                        c[x][y] = true;
                                    }
                                }
                            }
                            if(inland && i < size/5){
                                if(c[x][y]){
                                    m[x][y] = true;
                                }
                            }
                            else if(c[x][y]){
                                w[x][y] = true;
                            }
                        }
                    }
                }
            }           
        }
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                if(inland && m[x][y]){
                    t[x][y] = 3;
                    s[x][y] = 2;
                    f[x][y] = 1;
                }
                else if (w[x][y]){
                    t[x][y] = toVal;
                    s[x][y] = 1;
                    f[x][y] = 1;
                }
            }
        }
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                if(t[x][y] == 1){
                    double rand = Math.random();
                    if(rand > .2 && rand < .4){
                        t[x][y] = 4;
                        s[x][y] = 1;
                        f[x][y] = 2;
                    }
                    else if(rand > .4 && rand < .6){
                        t[x][y] = 5;
                        s[x][y] = 2;
                        f[x][y] = 2;
                    }
                }
            }
        }
    }
    public boolean edge(int x, int y, int dir){
        boolean a = false;
            switch (dir) {
                case 0:
                    if(y >= 1){
                        a = true;
                    }       break;
                case 1:
                    if(y < height-1){
                        a = true;
                    }       break;
                case 2:
                    if(x >= 1){
                        a = true;
                    }       break;
                case 3:
                    if(x < width-1){
                        a = true;
                    }       break;
                default:
                    break;
            }
        return a;
    }
    public boolean[] neighbor(int x, int y, int testT){
        boolean[] k = new boolean[8];
        if(edge(x, y, 0) && t[x][y-1] == testT){
            k[0] = true;
        }
        if(edge(x, y, 1) && t[x][y+1] == testT){
            k[1] = true;
        }
        if(edge(x, y, 2) && t[x-1][y] == testT){
            k[2] = true;
        }
        if(edge(x, y, 3) && t[x+1][y] == testT){
            k[3] = true;
        }
        return k;
    }
    public boolean Oneighbor(int x, int y, int testT){
        boolean k = false;
        if(edge(x, y, 0) && o[x][y-1] == testT){
            k = true;
        }
        if(edge(x, y, 1) && o[x][y+1] == testT){
            k = true;
        }
        if(edge(x, y, 2) && o[x-1][y] == testT){
            k = true;
        }
        if(edge(x, y, 3) && o[x+1][y] == testT){
            k = true;
        }
        if(turn == 1){
            k = true;
        }
        return k;
    }
    public boolean surround(int x, int y){
        int k = 0;
        boolean b = false;
        if(edge(x, y, 0)){
            if(o[x][y-1] == 0){
                k++;
            }
        }
        if(edge(x, y, 1)){
            if(o[x][y+1] == 0){
                k++;
            }
        }
        if(edge(x, y, 2)){
            if(o[x-1][y] == 0){
                k++;
            }
        }
        if(edge(x, y, 3)){
            if(o[x+1][y] == 0){
                k++;
            }
        }
        if(edge(x, y, 0) && edge(x, y, 2)){
            if(o[x-1][y-1] == 0){
                k++;
            }
        }
        if(edge(x, y, 0) && edge(x, y, 3)){
            if(o[x+1][y-1] == 0){
                k++;
            }
        }
        if(edge(x, y, 1) && edge(x, y, 2)){
            if(o[x-1][y+1] == 0){
                k++;
            }
        }
        if(edge(x, y, 1) && edge(x, y, 3)){
            if(o[x+1][y+1] == 0){
                k++;
            }
        }
        if(k == 8){
            b = true;
        }
        return b;
    }
}
