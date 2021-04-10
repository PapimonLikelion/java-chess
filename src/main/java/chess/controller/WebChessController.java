package chess.controller;

import chess.service.ChessService;
import chess.webdto.*;
import com.google.gson.Gson;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static chess.controller.HTTPStatusCode.*;
import static spark.Spark.*;

public class WebChessController {
    private final ChessService chessService;
    private final Gson gson;

    public WebChessController() {
        this.chessService = new ChessService();
        this.gson = new Gson();
    }

    public void run() {
        staticFileLocation("/public");
        ipAddress("13.124.213.29");

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return render(model, "index.html");
        });

        get("/startNewGame", (req, res) -> {
            try {
                final ChessGameDTO chessGameDTO = chessService.startNewGame(false);
                res.status(SUCCESS.statusCode());
                return gson.toJson(chessGameDTO);
            } catch (SQLException e) {
                res.status(INTERNAL_SERVER_ERROR.statusCode());
                return gson.toJson(DBConnectionDTO.fail());
            } catch (IllegalArgumentException e) {
                res.status(BAD_REQUEST.statusCode());
                return gson.toJson(StartNewGameResponseDTO.unavailable());
            }
        });

        get("/forceNewGame", (req, res) -> {
            try {
                final ChessGameDTO chessGameDTO = chessService.startNewGame(true);
                res.status(SUCCESS.statusCode());
                return gson.toJson(chessGameDTO);
            } catch (SQLException e) {
                res.status(INTERNAL_SERVER_ERROR.statusCode());
                return gson.toJson(DBConnectionDTO.fail());
            }
        });

        get("/loadPrevGame", (req, res) -> {
            try {
                final ChessGameDTO chessGameDTO = chessService.loadPreviousGame();
                res.status(SUCCESS.statusCode());
                return gson.toJson(chessGameDTO);
            } catch (SQLException e) {
                res.status(INTERNAL_SERVER_ERROR.statusCode());
                return gson.toJson(DBConnectionDTO.fail());
            }
        });

        post("/move", (req, res) -> {
            final MoveRequestDTO moveRequestDTO = gson.fromJson(req.body(), MoveRequestDTO.class);
            final String start = moveRequestDTO.getStart();
            final String destination = moveRequestDTO.getDestination();
            try {
                final ChessGameDTO chessGameDTO = chessService.move(start, destination);
                res.status(SUCCESS.statusCode());
                return gson.toJson(chessGameDTO);
            } catch (SQLException e) {
                res.status(INTERNAL_SERVER_ERROR.statusCode());
                return gson.toJson(DBConnectionDTO.fail());
            } catch (IllegalArgumentException e) {
                res.status(BAD_REQUEST.statusCode());
                return gson.toJson(MoveResponseDTO.fail());
            }
        });
    }

    private String render(Map<String, Object> model, String templatePath) {
        return new HandlebarsTemplateEngine().render(new ModelAndView(model, templatePath));
    }
}
