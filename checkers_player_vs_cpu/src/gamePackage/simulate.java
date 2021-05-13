package gamePackage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class simulate {

    public int [][] config = new int[8][8];
    public int turn,depth ;
    public Board env;
    public utility util;

    // debugging attributes
    public int test1=0;
    public void testPrint(int[][] node){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                System.out.print(node[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }
    // debugging area till here

    public int minimax(int[][] node,int depth,int curTurn,int player,int alpha,int beta){
        if(depth == 0 || util.isOver(node)){
            return util.heuristic(node , curTurn);
        }
        if(curTurn == player){
            int v = Integer.MIN_VALUE;
            for(int[][] arr : util.getAdjacents(util.cloneManual(node), curTurn )){
                v= Math.max(v,minimax(util.cloneManual(arr),depth-1,util.getOpp(curTurn) , player,alpha,beta));
                alpha = Math.max(alpha,v);
                if(alpha>beta)
                    break;
            }
            return v;
        }

        else{
            int v = Integer.MAX_VALUE;
            for(int[][] arr : util.getAdjacents(util.cloneManual(node), curTurn )){
                v= Math.min(v,minimax(util.cloneManual(arr),depth-1,util.getOpp(curTurn) , player,alpha,beta));
                beta = Math.min(beta,v);
                if(alpha > beta )
                    break;
            }
            return v;
        }

    }

    public void findMove(int turn){
        ArrayList<int[][] > children = util.getAdjacents(util.cloneManual(config),turn);
        if(children.size() == 1){
            config = util.cloneManual(children.get(0));
            return;
        }

        Iterator it = children.iterator();
        int mx=Integer.MIN_VALUE;
        int[][] res = new int[8][8];
        while(it.hasNext()){

            int[][] cur = (int[][]) it.next();
            int val = minimax(util.cloneManual(cur),depth,turn,turn,Integer.MIN_VALUE,Integer.MAX_VALUE);
            //System.out.print(val+" ");
            if(val>mx){
                val = mx;
                res = cur;
            }
            else if(val == mx){
                Random rand = new Random();
                int rand_int = rand.nextInt(2);
                if(rand_int == 1){
                    res = cur;
                }
            }
        }
        config = util.cloneManual(res);
    }

    boolean isvalid(int x1,int y1,int x2,int y2,int[][] config){
        if(x1<0 || x1>=8 || y1<0 || y1>=8)
            return false;
        if(Math.abs(x1-x2)!=Math.abs(y1-y2))
            return false;
        if(Math.abs(x1-x2)>2)
            return false;
        if(x1>x2 || x1==x2)
            return false;
        if(config[x1][y1] !=1 || config[x2][y2]!=0)
            return false;

        if(Math.abs(x1-x2) == 2 ){
            if(config[(x1+x2)/2][(y1+y2)/2]!=2)
                return false;
        }
        return true;
    }

    // start a player vs cpu match
    public void start(JFrame f) throws InterruptedException {
        while( !util.isOver(config) ){
            if(turn == 2){
                findMove(turn);

                turn = util.getOpp(turn);
                env.performMove(config);
                TimeUnit.SECONDS.sleep(1);
            }
            else{
                while(turn == 1){
                    int x1= Integer.parseInt(JOptionPane.showInputDialog(f,"Enter x1"));
                    int y1=Integer.parseInt(JOptionPane.showInputDialog(f,"Enter y1"));
                    int x2=Integer.parseInt(JOptionPane.showInputDialog(f,"Enter x2"));
                    int y2=Integer.parseInt(JOptionPane.showInputDialog(f,"Enter y2"));

                    if(isvalid(x1,y1,x2,y2,config)){
                        turn = util.getOpp(turn);
                        if(Math.abs(x1-x2)==1){
                            config[x2][y2] = config[x1][y1];
                            config[x1][y1] = 0;
                        }
                        else{
                            config[x2][y2] = config[x1][y1];
                            config[x1][y1] = 0;
                            config[(x1+x2)/2][(y1+y2)/2] = 0;
                        }

                        env.performMove(config);
                    }


                }
            }



            //TimeUnit.SECONDS.sleep(1);
        }
    }


    public static void main(String [] args) throws InterruptedException {
        simulate sim = new simulate();
         sim.env = new Board(sim.config);
         sim.util = new utility();
        JFrame f =  new JFrame("AI_Checkers");
        f.setBounds(10,10,800,800);
        f.setResizable(false);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(sim.env);
        sim.start(f);
        JOptionPane.showMessageDialog(f,"Game over");

    }

    simulate(){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                    this.config[i][j] = 0;
            }
        }

        for(int i=0;i<3;i++){
            for(int j=0;j<8;j++){
                if((i+j)%2==1){
                    this.config[i][j] = 1;
                }
            }
        }

        for(int i=5;i<8;i++){
            for(int j=0;j<8;j++){
                if((i+j)%2==1){
                    this.config[i][j] = 2;
                }
            }
        }

        this.turn=1;
        this.depth = 7;
    }
}
