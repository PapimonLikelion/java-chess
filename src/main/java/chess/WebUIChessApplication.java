package chess;

import chess.controller.WebChessController;
import spark.Service;

public class WebUIChessApplication {
    public static void main(String[] args) {
        final Service ignite = Service.ignite();
        ignite.ipAddress("13.124.213.29");
        final WebChessController webChessController = new WebChessController();
        webChessController.run();
    }
}
