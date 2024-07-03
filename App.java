import java.util.Stack;

public class App {
    public static void main(String[] args) throws Exception {
        //String gameState = "lb,lp,lg,r;pg,,r,db,lb;r,y,o,gy;lb,dp,pk,db;lp,lp,dp,db;dg,y,o,lg;dp,dg,lg,o;pk,gy,o,lp;y,pk,lg,gy;pg,r,db,gy;pg,dg,dp,dg;pg,pk,y,lb;emp,emp,emp,emp;emp,emp,emp,emp";
        //String gameState = "lb,emp,emp,emp;lb,lb,lb,emp";
        //String gameState = "lg,lp,o,lg;lb,lp,lb,lp;r,lb,y,lg;gy,dp,dp,lg;lp,db,dp,db;gy,dp,o,y;db,o,gy,r;y,gy,r,db;r,o,y,lb;emp,emp,emp,emp;emp,emp,emp,emp";
        //String gameState = "db,dg,lb,pg;lp,r,lg,lb;dg,dp,pg,dp;lb,pg,y,o;dp,lp,gy,pg;db,r,o,dg;dg,lg,r,r;db,lg,db,y;lg,lp,gy,dp;o,pk,pk,lp;gy,pk,y,pk;o,lb,y,gy;emp,emp,emp,emp;emp,emp,emp,emp";
        String gameState = "pg,pg,lg,r;lb,gy,lp,lb;db,db,lg,r;gy,gy,db,dp;o,lp,o,dg;lp,y,pk,pg;pk,o,o,lp;y,lg,dg,y;pg,pk,y,lg;gy,dg,dp,r;dg,lb,r,db;dp,dp,pk,lb;emp,emp,emp,emp;emp,emp,emp,emp";
        Game game = new Game(gameState);
        Stack<Move> moves = game.solve();
        Stack<Move> reverseStack = new Stack<Move>();
        while(!moves.isEmpty()) {
            reverseStack.push(moves.pop());
        }
        int moveNum = 1;
        while(!reverseStack.isEmpty()) {
            System.out.println("MOVE " + moveNum + ": " + reverseStack.pop());
            moveNum++;
        }
    }
}
