package training.dynamite;

import com.softwire.dynamite.bot.Bot;
import com.softwire.dynamite.game.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MyBot implements Bot {
    public static int dynocount =0;
    public static int i = 0;
    public static int testCount =0;
    public static int opponentDynamiteCount = 0;
    public MyBot() {
        // Are you debugging?
        // Put a breakpoint on the line below to see when we start a new match

        System.out.println("Started new match");


    }

    @Override
    public Move makeMove(Gamestate gamestate) {
        // Are you debugging?
        // Put a breakpoint in this method to see when we make a move
        //enum

        try {
            int randomMove = Randint(5);
            Move move;

            List<ThreeState> winsLosses = creatWinLosses(gamestate);

            if (gamestate.getRounds().size() < 1) {
                move = Move.P;
                MyBot.dynocount =0;
                opponentDynamiteCount =0;

            } else {
                move = internalMover(gamestate.getRounds().get(gamestate.getRounds().size() - 1).getP1().ordinal());
            }
            if(MyBot.i>=4){
               MyBot.i =0;
            }



            move = internalMover( Randint(4));

//
//            if (shouldChangeStrat(winsLosses) && winsLosses.size() %10 ==0) {
//
//                System.out.println("strat change");
//
//
//
//                int mov = stratchanger(MyBot.i, gamestate, winsLosses, gamestate.getRounds().get(gamestate.getRounds().size() -1).getP1().ordinal());
//                move = internalMover(mov);
//                MyBot.i++;
//            }







            if(move == Move.D){
                dynocount++;
                //System.out.println(dynocount);
                if(dynocount >100 ){
                    move = internalMover(Randint(2));
                }
            }

            if(oppnentdynamite(gamestate) && move == Move.W ){
                move = internalMover(Randint(3));
            }

            testCount++;

            return move;
        }catch(Exception e){
            System.err.println(e.getMessage());
        }

        return Move.R;

    }



    public Move internalMover(int moveInt){
        switch (moveInt) {
            case 0:
                return Move.R;
            case 1:
                return Move.P;
            case 2:
                return Move.S;
            case 3:
                return Move.D;
            case 4:
                return Move.W;
            default:
                return Move.D;
        }
    }
    public int Randint(int upperLimit){
        Random rand = new Random();
        int upperBound = upperLimit;

       int move = rand.nextInt(upperBound);
       return move;
    }

    private static boolean xWinsAgainstY(Move x, Move y) {

        if (x != Move.R || y != Move.S && y != Move.W) {
            if (x != Move.P || y != Move.R && y != Move.W) {
                if (x == Move.S && (y == Move.P || y == Move.W)) {
                    return true;
                } else if (x != Move.D || y != Move.R && y != Move.P && y != Move.S) {
                    return x == Move.W && y == Move.D;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public boolean shouldChangeStrat(List<ThreeState> winsLosses){

        try {
           List<ThreeState> subList = winsLosses.subList((winsLosses.size()-10), (winsLosses.size()))
                   .stream().filter(listItem -> (listItem == ThreeState.LOSE)).collect(Collectors.toList());
            if (5 < subList.size()) {
                return true;
            } else{
                return false;
            }
        } catch(Exception e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }
        return false;
    }

    public int stratchanger(int stratChange, Gamestate gamestate, List<ThreeState> winsLosses, int lastMove){
        {
            switch (stratChange) {
                case 0:
                    return defeatLastMove(gamestate,winsLosses, lastMove);
                case 1:
                    return  beatAverage(gamestate);
                case 2:
                    return Randint(4);
                case 3:
                    return doubleDefeat(gamestate,winsLosses,lastMove);
                case 4:
                    return Randint(4);
                default:
                    return 1;
            }
        }
    }

    public int defeatLastMove(Gamestate gamestate, List<ThreeState> winsLosses,int lastmove )
    {

             return returnRevese(gamestate.getRounds().get(winsLosses.size()-1).getP2().ordinal());


    }
    public int doubleDefeat(Gamestate gamestate, List<ThreeState> winsLosses,int lastmove )
    {

         return returnPos(gamestate.getRounds().get(winsLosses.size()-1).getP1().ordinal());


    }



    public int returnRevese(int input){

            {
                switch (input) {
                    case 0:
                        return 1;
                    case 1:
                        return 2;
                    case 2:
                        return 0;
                    case 3:
                        return 4;
                    case 4:
                        return 1;
                    default:
                        return 4;
                }
            }
        }

    public int returnPos(int input){

        {
            switch (input) {
                case 0:
                    return 2;
                case 1:
                    return 0;
                case 2:
                    return 1;
                case 3:
                    return 4;
                case 4:
                    return 1;
                default:
                    return 4;
            }
        }
    }


    public enum ThreeState {
        WIN,
        LOSE,
        DRAW
    }

    public int noD(Gamestate gamestate, List<ThreeState> winsLosses,int lastmove )
    {
        try {
            if (gamestate.getRounds().subList((gamestate.getRounds().size() - 5), (gamestate.getRounds().size())).stream().allMatch(listItem -> (listItem.getP2() != Move.D))) {
                return 3;
            } else {
                return lastmove;
            }
        }catch(Exception e){
            System.out.println(e.getMessage()+"NOD");
        }
        return lastmove;

    }

    public Boolean oppnentdynamite(Gamestate gamesstate){
        if(gamesstate.getRounds().get((gamesstate.getRounds().size() -1)).getP2() == Move.D){
            opponentDynamiteCount++;
        }
        if(opponentDynamiteCount> 99){
            return true;
        } else{
            return false;
        }

    }


    public List<ThreeState> creatWinLosses(Gamestate gamestate) {
    return gamestate.getRounds().stream().map((round) -> {
            if (round.getP1() == round.getP2()) {
                return ThreeState.DRAW;
            }
            if (xWinsAgainstY(round.getP1(), round.getP2())) {
                return ThreeState.WIN;
            } else {
                return ThreeState.LOSE;
            }
        }).collect(Collectors.toList());
    }

    public int beatAverage(Gamestate gamestate){

        int move;
        long paper = gamestate.getRounds().stream().filter(round -> round.getP2().equals(Move.P)).count();
        long rock = gamestate.getRounds().stream().filter(round -> round.getP2().equals(Move.R)).count();
        long scissors = gamestate.getRounds().stream().filter(round -> round.getP2().equals(Move.S)).count();
        //long dynamite = gamestate.getRounds().stream().filter(round -> round.getP2().equals(Move.D)).count();
        if(paper>rock && paper > scissors){
            move = 1;
        } else if (rock >paper && rock > scissors) {
            move = 0;
        }else {
            move = 2;
        }
        return returnRevese(move);
    }
}





