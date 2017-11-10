package pkg14b.conqueror;

import java.util.ArrayList;

public class Game{
    Level gen;
    ArrayList<Unit> unit;
    public Game(Level gen){
        this.gen = gen;
        unit = new ArrayList<>();
        unit.add(new Unit(gen, 0, 0, 10));
    }
    public void city(int x, int y, int own){
        if(gen.gold[own] >= 300 && gen.t[x][y] != 0 && (gen.Oneighbor(x, y, own)||Uneighbor(x, y, own)) && gen.surround(x, y, own, true) && gen.i[x][y] != 5){
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
        if(gen.gold[own] >= 50 && gen.o[x][y] == 0 && gen.t[x][y]!=0){
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
    public void makeUnit(int x, int y, int pl){
        if(gen.u[x][y]==0){
            if(gen.gold[pl]>=50 && gen.o[x][y]==pl && gen.popu[pl]>=3){
                unit.add(new Unit(gen, x, y, pl));
                gen.gold[pl]-=50;
                gen.popu[pl]-=3;
            }
        }else{
            gen.select = !gen.select;
            gen.sx = x;
            gen.sy = y;
        }
    }  
    public void upgrade(int x, int y, int pl){
        if(gen.gold[pl]>=10&&gen.o[x][y]==pl){
            if(gen.i[x][y] == 1){
                gen.i[x][y]= 2;
                gen.gold[pl]-=10;
                gen.farmA[pl]++;
            }else if(gen.i[x][y] == 3){
                gen.i[x][y]= 4;
                gen.gold[pl]-=10;
                gen.mineA[pl]++;
            }
        }
    }
    public Unit GeU(int i){
        return ((Unit) unit.get(i));
    }
    public boolean Uneighbor(int x, int y, int own){
        if(gen.edge(x, y, 0) && gen.u[x][y-1] != 0 && GeU(gen.u[x][y-1]).pl == own){
            return true;
        }
        if(gen.edge(x, y, 1) && gen.u[x][y+1] != 0 && GeU(gen.u[x][y+1]).pl == own){
            return true;
        }
        if(gen.edge(x, y, 2) && gen.u[x-1][y] != 0 && GeU(gen.u[x-1][y]).pl == own){
            return true;
        }
        if(gen.edge(x, y, 3) && gen.u[x+1][y] != 0 && GeU(gen.u[x+1][y]).pl == own){
            return true;
        }
        if(gen.edge(x, y, 2) && gen.edge(x, y, 0) && gen.u[x-1][y-1] != 0 && GeU(gen.u[x-1][y-1]).pl == own){
            return true;
        }
        if(gen.edge(x, y, 3) && gen.edge(x, y, 1) && gen.u[x+1][y+1] != 0 && GeU(gen.u[x+1][y+1]).pl == own){
            return true;
        }
        if(gen.edge(x, y, 1) && gen.edge(x, y, 2) && gen.u[x-1][y+1] != 0 && GeU(gen.u[x-1][y+1]).pl == own){
            return true;
        }
        return gen.edge(x, y, 0) && gen.edge(x, y, 3) && gen.u[x+1][y-1] != 0 && GeU(gen.u[x+1][y-1]).pl == own;
    }
    public void AI(int pl, boolean first){
        if(first){
            int cx, cy;
            boolean k = true;
            while(k){
                cx = (int)(Math.random()*gen.width);
                cy = (int)(Math.random()*gen.height);
                if(gen.o[cx][cy] == 0 && gen.t[cx][cy] != 0 && gen.surround(cx, cy, pl, true)){
                    city(cx, cy, pl);
                    k = false;
                }
            }
        }else{
            boolean done = false;
            while(gen.gold[pl] >= 10&&!done){
                if(gen.mineA[pl] <= gen.farmA[pl]-(gen.popu[pl]/2)){
                    mainloop:
                    for(int cx = 0; cx < gen.width; cx++){
                        for(int cy = 0; cy < gen.height; cy++){
                            if(gen.o[cx][cy] == pl && gen.s[cx][cy] == 2 && gen.i[cx][cy] == 0){
                                mine(cx, cy, pl);
                                break mainloop;
                            }else if(gen.o[cx][cy] == pl && gen.s[cx][cy] == 1 && gen.i[cx][cy] == 0){
                                mine(cx, cy, pl);
                                break mainloop;
                            }
                        }
                    }
                }else if(gen.mineA[pl] > gen.farmA[pl]-(gen.popu[pl]/2)){
                    mainloop1:
                    for(int cx = 0; cx < gen.width; cx++){
                        for(int cy = 0; cy < gen.height; cy++){
                            if(gen.o[cx][cy] == pl && gen.f[cx][cy] == 2 && gen.i[cx][cy] == 0){
                                farm(cx, cy, pl);
                                break mainloop1;
                            }else if(gen.o[cx][cy] == pl && gen.f[cx][cy] == 1 && gen.i[cx][cy] == 0){
                                farm(cx, cy, pl);
                                break mainloop1;
                            }
                        }
                    }
                }
                for(int cx = 0; cx < gen.width; cx++){
                    for(int cy = 0; cy < gen.height; cy++){
                        upgrade(cx, cy, pl);
                    }
                }
                for(int x = 0; x < gen.width; x++){
                    for(int y = 0; y < gen.height; y++){
                        if(gen.o[x][y] == pl){
                            makeUnit(x, y, pl);
                        }
                    }
                }
                unit.stream().filter((unit1) -> (unit1.pl==pl)).forEachOrdered((unit1) -> {
                    unit1.move((int)(Math.random()*5));
                });
                mainloop:
                for(int cx = 0; cx < gen.width; cx++){
                    for(int cy = 0; cy < gen.height; cy++){
                        if(gen.o[cx][cy] == pl && gen.i[cx][cy] == 0){
                            done = true;
                            break mainloop;
                        }
                        done = false;
                    }
                }
            }
            if(gen.gold[pl] >= 300){
                int cx, cy;
                boolean k = true;
                while(k){//not this one
                    cx = (int)(Math.random()*gen.width);
                    cy = (int)(Math.random()*gen.height);
                    if(gen.t[cx][cy] != 0 && gen.checkI(cx, cy, 5) && (gen.surround(cx, cy, pl, false)||Uneighbor(cx, cy, pl))){
                        city(cx, cy, pl);
                        k = false;
                    }
                }
            }
        }
    }
    public void turn(){
        for(int i = 1; i < 7; i++){
            gen.food[i]+=gen.farmA[i];
            gen.gold[i]+=gen.mineA[i];//+(gen.popu[i]/2);//no popu income
            gen.food[i]-=gen.popu[i]/2;
            //gen.gold[i]-=gen.cityA[i]*2;
            if(gen.cityA[i] > 0 && gen.cityA[i]*Math.sqrt(gen.turn)%gen.cityA[i] == 0 && gen.food[i] >= 1){
                gen.popu[i]+=gen.cityA[i];
            }
            if(i != 1){
                if(gen.turn == 1){
                    AI(i, true);
                }else if(gen.a[i]){
                    /*System.out.println("----------("+i+")");
                    System.out.println("Money "+i+":"+gen.gold[i]);
                    System.out.println("Food "+i+":"+gen.food[i]);
                    System.out.println("Popu "+i+":"+gen.popu[i]);
                    System.out.println("Income "+i+":"+gen.mineA[i]);
                    System.out.println("Production "+i+":"+(gen.farmA[i]-(gen.popu[i]/2)));
                    if(gen.mineA[i] <= gen.farmA[i]-(gen.popu[i]/2))System.out.println("Priority: Mine");
                    else System.out.println("Priority: Farm");*/
                    AI(i, false);
                }
            }
        }
        for(int i = 0; i < unit.size(); i++)GeU(i).moved = false;
        gen.turn++;
    }
    public void reveal(int x, int y){
        if(gen.surround(x, y, 1, false)||Uneighbor(x, y, 1))
            gen.r[x][y] = true;
    }
    public void update(){
        //turn();
        for(int i = 0; i < 7; i++){
            gen.gold[i]=gen.gold[i]<=0?0:gen.gold[i];
            gen.popu[i]=gen.popu[i]<=0?0:gen.popu[i];
            gen.food[i]=gen.food[i]<=0?0:gen.food[i];
        }
        for(int x = 0; x < gen.width; x++)
            for(int y = 0; y < gen.height; y++)
                if(!gen.r[x][y]) reveal(x, y);
    }
    public boolean side(int x, int y, int dir){
        return gen.neighbor(x, y, 0)[dir];
    }
    public int dynamic(int x, int y){
        if(gen.t[x][y]!=0){
            if(side(x, y, 0)&&side(x, y, 1)&&side(x, y, 2)&&side(x, y, 3))return 15;
            if(side(x, y, 0)&&side(x, y, 1)&&side(x, y, 2))return 1;
            if(side(x, y, 0)&&side(x, y, 1)&&side(x, y, 3))return 2;
            if(side(x, y, 0)&&side(x, y, 2)&&side(x, y, 3))return 3;
            if(side(x, y, 1)&&side(x, y, 2)&&side(x, y, 3))return 4;
            if(side(x, y, 3)&&side(x, y, 0))return 9;
            if(side(x, y, 2)&&side(x, y, 0))return 10;
            if(side(x, y, 2)&&side(x, y, 1))return 11;
            if(side(x, y, 3)&&side(x, y, 1))return 12;
            if(side(x, y, 0)&&side(x, y, 1))return 13;
            if(side(x, y, 3)&&side(x, y, 2))return 14;
            if(side(x, y, 0))return 5;
            if(side(x, y, 2))return 6;
            if(side(x, y, 1))return 7;
            if(side(x, y, 3))return 8;
        }
        return 0;
    }
}
