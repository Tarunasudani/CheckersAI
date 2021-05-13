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
            int val = minimax(util.cloneManual(cur),depth,2,2,Integer.MIN_VALUE,Integer.MAX_VALUE);
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

    // start a cpu vs cpu match
    public void start() throws InterruptedException {
        while( !util.isOver(config) ){
            findMove(turn);
            env.performMove(config);
            turn = util.getOpp(turn);
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
        sim.start();
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
        this.depth = 10;
    }
}
