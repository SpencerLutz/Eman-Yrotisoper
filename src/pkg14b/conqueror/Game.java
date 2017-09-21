package pkg14b.conqueror;

public class Game {
    Generate gen = new Generate();
    public void city(int x, int y, int own){
        if(gen.gold[own] >= 300 && gen.o[x][y] == 0 && gen.t[x][y] != 0 && gen.Oneighbor(x, y, own)){
            gen.i[x][y] = 5;
            gen.o[x][y] = own;
            if(gen.edge(x, y, 0)){
                gen.o[x][y-1] = own;
                if(gen.edge(x, y, 2)){
                    gen.o[x-1][y-1] = own;
                }
                if(gen.edge(x, y, 3)){
                    gen.o[x+1][y-1] = own;
                }
            }
            if(gen.edge(x, y, 1)){
                gen.o[x][y+1] = own;
                if(gen.edge(x, y, 2)){
                    gen.o[x-1][y+1] = own;
                }
                if(gen.edge(x, y, 3)){
                    gen.o[x+1][y+1] = own;
                }
            }
            if(gen.edge(x, y, 2)){
                gen.o[x-1][y] = own;
            }
            if(gen.edge(x, y, 3)){
                gen.o[x+1][y] = own;
            }
            gen.gold[own]-=300;
            gen.cityA[own]++;
        }
    }
    public void farm(int x, int y, int own){
        if(gen.gold[own] >= 10 && gen.i[x][y] == 0 && gen.o[x][y] == own){
            if(gen.t[x][y] == 4 || gen.t[x][y] == 5){
                gen.i[x][y] = 2;
                gen.gold[own]-=10;
                gen.farmA[own]+=2;
            }else if(gen.t[x][y] == 1 || gen.t[x][y] == 3){
                gen.i[x][y] = 1;
                gen.gold[own]-=10;
                gen.farmA[own]++;
            }
        }
    }
    public void mine(int x, int y, int own){
        if(gen.gold[own] >= 10 && gen.i[x][y] == 0 && gen.o[x][y] == own){
            if(gen.t[x][y] == 3 || gen.t[x][y] == 5){
                gen.i[x][y] = 4;
                gen.gold[own]-=10;
                gen.farmA[own]+=2;
            }else if(gen.t[x][y] == 1 || gen.t[x][y] == 4){
                gen.i[x][y] = 3;
                gen.gold[own]-=10;
                gen.farmA[own]++;
            }
        }
    }
    public void prop(int x, int y, int own){
        if(gen.gold[own] >= 50 && gen.o[x][y] != own){
            if(y >= 1 && gen.o[x][y-1] == own){
                gen.o[x][y] = 1;
                gen.gold[own]-=50;
                gen.cityA[own]++;
            }else if(y < gen.width-1 && gen.o[x][y+1] == own){
                gen.o[x][y] = 1;
                gen.gold[own]-=50;
                gen.cityA[own]++;
            }else if(x >= 1 && gen.o[x-1][y] == own){
                gen.o[x][y] = 1;
                gen.gold[own]-=50;
                gen.cityA[own]++;
            }else if(x < gen.width-1 && gen.o[x+1][y] == own){
                gen.o[x][y] = 1;
                gen.gold[own]-=50;
                gen.cityA[own]++;
            }
        }
    }
    public void AI(int pl, boolean first){
        if(first){
            int cx = 0, cy = 0;
            boolean k = true;
            while(k){
                cx = (int)(Math.random()*gen.width);
                cy = (int)(Math.random()*gen.height);
                if(gen.o[cx][cy] == 0 && gen.t[cx][cy] != 0 && gen.surround(cx, cy)){
                    city(cx, cy, pl);
                    k = false;
                }
            }
        }else{
            if(gen.gold[pl] >= 10){
                if((gen.mineA[pl]+(gen.popu[pl]/2))-(gen.cityA[pl]*2) <= gen.farmA[pl]-gen.popu[pl]){
                    boolean k = false;
                    for(int cx = 0; cx < gen.width; cx++){
                        for(int cy = 0; cy < gen.height; cy++){
                            if(gen.o[cx][cy] == pl && !k && gen.s[cx][cy] == 2 && gen.i[cx][cy] == 0){
                                mine(cx, cy, pl);
                                k = true;
                            }
                        }
                    }
                    if(k = false){
                        for(int cx = 0; cx < gen.width; cx++){
                            for(int cy = 0; cy < gen.height; cy++){
                                if(gen.o[cx][cy] == pl && !k && gen.s[cx][cy] == 1 && gen.i[cx][cy] == 0){
                                    mine(cx, cy, pl);
                                    k = true;
                                }
                            }
                        }
                    }
                }
                else if((gen.mineA[pl]+(gen.popu[pl]/2))-(gen.cityA[pl]*2) > gen.farmA[pl]-gen.popu[pl]){
                    boolean k = false;
                    for(int cx = 0; cx < gen.width; cx++){
                        for(int cy = 0; cy < gen.height; cy++){
                            if(gen.o[cx][cy] == pl && !k && gen.f[cx][cy] == 2 && gen.i[cx][cy] == 0){
                                farm(cx, cy, pl);
                                k = true;
                            }
                        }
                    }
                    if(k = false){
                        for(int cx = 0; cx < gen.width; cx++){
                            for(int cy = 0; cy < gen.height; cy++){
                                if(gen.o[cx][cy] == pl && !k && gen.f[cx][cy] == 1 && gen.i[cx][cy] == 0){
                                    farm(cx, cy, pl);
                                    k = true;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public void turn(){
        for(int i = 1; i < 7; i++){
            gen.food[i]+=gen.farmA[i];
            gen.gold[i]+=gen.mineA[i]+(gen.popu[i]/2);
            gen.food[i]-=gen.popu[i];
            gen.gold[i]-=gen.cityA[i]*2;
            if(gen.turn%2 == 0 && gen.farmA[i]-gen.popu[i] >= 0 && gen.food[i] >= 10){
                gen.popu[i]+=gen.cityA[i];
            }
            if(gen.food[i] <=0){
                gen.popu[i]--;
                gen.farmA[i]--;
            }
            if(i != 1){
                if(gen.turn == 1){
                    AI(i, true);
                }else if(gen.a[i]){
                    AI(i, false);
                }
            }
        }
        gen.turn++;
    }
    public void update(){
        for(int i = 1; i < 7; i++){
            if(gen.popu[i] <= 0){
                if(gen.is0&&i == 1){
                    System.out.println("You couldn't feed your people. So sad.");
                    //System.exit(0);
                }else{
                    gen.a[i] = false;
                    for(int x = 0; x < gen.width; x++){
                        for(int y = 0; y < gen.height; y++){
                            if(gen.o[x][y] == i){
                                gen.o[x][y] = 0;
                            }
                        }
                    }
                }
            }
            gen.gold[i]=gen.gold[i]<0?0:gen.gold[i];
            gen.popu[i]=gen.popu[i]<0?0:gen.popu[i];
        }
        
    }
}
