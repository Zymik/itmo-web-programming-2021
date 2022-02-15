package ru.itmo.wp.web.page;


import javax.servlet.http.HttpServletRequest;

import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {
    public static class State {
        private static final int SIZE = 3;
        private final int size;
        private String phase;
        private boolean crossesMove;
        private int empty;
        private final String[][] cells;

        public State() {
            size = SIZE;
            phase = "RUNNING";
            crossesMove = true;
            empty = size * size;
            cells = new String[size][size];
        }

        public int getSize() {
            return size;
        }

        public String getPhase() {
            return phase;
        }

        public String[][] getCells() {
            return cells;
        }

        public boolean getCrossesMove() {return crossesMove;}

        private String getCurrentMoveString() {
            return crossesMove ? "X" : "O";
        }

        private boolean goodCoordinates(int x, int y) {
            return 0 <= x && 0 <= y && x < size && y < size;
        }

        private boolean check(int x, int y, int dx, int dy) {
            int counter = 0;
            String c = getCurrentMoveString();
            for (int i = x, j = y; goodCoordinates(i, j); i += dx, j += dy) {
                if (c.equals(cells[i][j])) {
                    counter++;
                }
            }
            return counter == size;
        }

        private boolean checkWin() {
            for (int i = 0; i < size; i++) {
                if (check(i, 0, 0, 1) || check(0, i, 1, 0)) {
                    return true;
                }
            }
            return check(size - 1, 0, -1, 1) || check(0, 0, 1, 1);
        }

        public void makeMove(int x, int y) {
            if (cells[x][y] != null || !phase.equalsIgnoreCase("RUNNING")) {
                return;
            }
            cells[x][y] = getCurrentMoveString();
            empty--;
            boolean win = checkWin();
            if (win) {
                phase = "WON" + "_" + getCurrentMoveString();
            } else if (empty == 0) {
                phase = "DRAW";
            }
            crossesMove = !crossesMove;
        }

    }



    private void action(Map<String, Object> view, HttpServletRequest request) {
        view.put("state", getState(request));
    }

    private State getState(HttpServletRequest request) {
        if (request.getSession().getAttribute("state") == null) {
            request.getSession().setAttribute("state", new State());
        }
        return (State) request.getSession().getAttribute("state");
    }

    private void onMove(Map<String, Object> view, HttpServletRequest request) {
        State state = getState(request);
        loop:
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (request.getParameter("cell_" + i + j) != null) {
                    state.makeMove(i, j);
                    break loop;
                }
            }
        }
        request.setAttribute("state", state);
        action(view, request);
    }

    private void newGame(Map<String, Object> view, HttpServletRequest request) {
        request.getSession().setAttribute("state", new State());
        action(view, request);
    }
}
