package training.dynamite;

import com.softwire.dynamite.bot.Bot;
import com.softwire.dynamite.game.*;
import jdk.nashorn.internal.runtime.Undefined;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MyBot implements Bot {
    public static int dynocount =0;
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
            int i;
            Move move;

            List<ThreeState> winsLosses = gamestate.getRounds().stream().map((round) -> {
                if (round.getP1() == round.getP2()) {
                    return ThreeState.DRAW;
                }
                if (xWinsAgainstY(round.getP1(), round.getP2())) {
                    return ThreeState.WIN;
                } else {
                    return ThreeState.LOSE;
                }
            }).collect(Collectors.toList());

            if (winsLosses.size() < 1) {
                move = Move.P;
                MyBot.dynocount =0;

            } else {
                move = internalMover(gamestate.getRounds().get(gamestate.getRounds().size() - 1).getP1().ordinal());
            }


            if (shouldChangeStrat(winsLosses)) {

                i = Randint(4);
                int mov = stratchanger(i, gamestate, winsLosses, gamestate.getRounds().get(gamestate.getRounds().size() -1).getP1().ordinal());
                move = internalMover(mov);
            }
            try {
                if (winsLosses.get(winsLosses.size() - 1) == ThreeState.DRAW) {
                    move = Move.D;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            if(move == Move.D){
                dynocount++;
                System.out.println(dynocount);
                if(dynocount >100 ){
                   move = internalMover(Randint(3));
                }
            }
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
            if (winsLosses.subList((winsLosses.size()-3), (winsLosses.size())).stream().allMatch(listItem -> (listItem == ThreeState.LOSE))) {
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
                    return Randint(6);
                case 1:
                    return defeatLastMove(gamestate,winsLosses, lastMove);
                case 2:
                    return noD(gamestate,winsLosses, lastMove);
                case 3:
                    return 1;
                case 4:
                    return 1;
                default:
                    return 1;
            }
        }
    }

    public int defeatLastMove(Gamestate gamestate, List<ThreeState> winsLosses,int lastmove )
    {
        if(winsLosses.get(winsLosses.size()-1) == ThreeState.LOSE){
             return returnRevese(gamestate.getRounds().get(winsLosses.size()-1).getP2().ordinal());
        } else {
            return lastmove;
        }

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
    }





