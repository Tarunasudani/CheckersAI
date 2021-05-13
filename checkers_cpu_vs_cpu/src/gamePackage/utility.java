package gamePackage;

import java.util.ArrayList;



public class utility {

    public int getOpp(int x){
        if(x==1)
            return 2;

        return 1;
    }

    public int[][] cloneManual(int[][] node){
        int[][] arr = new int[8][8];
        for(int i=0;i<8;i++){
            arr[i] = node[i].clone();
        }
        return arr;
    }

    //check the adjacent positions
    boolean check(int x,int y,int [][] config){
        if(x>=0 && x<8 && y>0 && y<8 && config[x][y] == 0){
            return true;
        }
        return false;
    }
    boolean checkCut(int x,int y,int xp,int yp,int opp,int[][] config){
        if(x>=0 && x<8 && y>0 && y<8 && config[x][y] == 0 && config[(x+xp)/2][(y+yp)/2] == opp){
            return true;
        }
        return false;
    }

    // get adjacents of the current board state
    public ArrayList<int [][]> getAdjacents(int [][] supernode,int turn){
        ArrayList<int[][] > ans = new ArrayList<>();
        int[][] node = cloneManual(supernode);

        // scan for double jumps
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(turn == 1){
                    if(node[i][j] == turn){
                        if(checkCut(i+2,j+2,i,j,getOpp(turn),node)){
                            int [][] newNode = cloneManual(node);
                            newNode[i+2][j+2] = newNode[i][j];
                            newNode[i][j] = 0;
                            newNode[i+1][j+1] = 0;
                            ans.add(newNode);

                        }

                        if(checkCut(i+2,j-2,i,j,getOpp(turn),node)){
                            int [][] newNode = cloneManual(node);
                            newNode[i+2][j-2] = newNode[i][j];
                            newNode[i][j] = 0;
                            newNode[i+1][j-1] = 0;
                            ans.add(newNode);
                        }
                    }
                }
                else{
                    if(node[i][j] == turn){
                        if(checkCut(i-2,j+2,i,j,getOpp(turn),node)){
                            int [][] newNode = cloneManual(node);
                            newNode[i-2][j+2] = newNode[i][j];
                            newNode[i][j] = 0;
                            newNode[i-1][j+1] = 0;
                            ans.add(newNode);

                        }

                        if(checkCut(i-2,j-2,i,j,getOpp(turn),node)){
                            int [][] newNode = cloneManual(node);
                            newNode[i-2][j-2] = newNode[i][j];
                            newNode[i][j] = 0;
                            newNode[i-1][j-1] = 0;
                            ans.add(newNode);
                        }
                    }
                }

            }
        }

        //scan for single jumps
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(turn == 1){
                    if(node[i][j] == turn){
                        if(check(i+1,j+1,node)){
//                        System.out.println(i+","+j+" to " + (i+1) +","+(j+1));
                            int [][] newNode = cloneManual(node);
                            newNode[i+1][j+1] = newNode[i][j];
                            newNode[i][j] = 0;
                            ans.add(newNode);
                        }
                        if(check(i+1,j-1,node)){
//                        System.out.println(i+","+j+" to " + (i+1)+","+(j-1) );
                            int [][] newNode = cloneManual(node);
                            newNode[i+1][j-1] = newNode[i][j];
                            newNode[i][j] = 0;
                            ans.add(newNode);
                        }
                    }
                }
                else{
                    if(node[i][j] == turn){
                        if(check(i-1,j+1,node)){
//                        System.out.println(i+","+j+" to " + (i+1) +","+(j+1));
                            int [][] newNode = cloneManual(node);
                            newNode[i-1][j+1] = newNode[i][j];
                            newNode[i][j] = 0;
                            ans.add(newNode);
                        }
                        if(check(i-1,j-1,node)){
//                        System.out.println(i+","+j+" to " + (i+1)+","+(j-1) );
                            int [][] newNode = cloneManual(node);
                            newNode[i-1][j-1] = newNode[i][j];
                            newNode[i][j] = 0;
                            ans.add(newNode);
                        }
                    }
                }

            }
        }

        return ans;
    }

    // check for game over
    boolean isOver(int [][] node){
        int a=0,b=0;
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(node[i][j]==1)
                    a++;
                if(node[i][j]==2)
                    b++;

            }
        }
        if(a==0  || b==0){
            return true;
        }
        return false;
    }


    // calc heuristic for a particular state
    public int heuristic(int [][] node,int turn){
        int a = 0,b=0;
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(node[i][j] == turn)
                    a++;
                if(node[i][j] == getOpp(turn))
                    b++;
            }
        }
        return a-b;
    }
}
