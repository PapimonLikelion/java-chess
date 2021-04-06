let startBtn = document.getElementById("startBtn");
let loadBtn = document.getElementById("loadBtn");

import {chessBoard} from "./initialize.js";
import {gameResultWindow} from "./initialize.js";
import {initChessBoard} from "./initialize.js";
import {player1} from "./movement.js";
import {player2} from "./movement.js";
import {addChessBoardEvent} from "./movement.js";
import {checkIsPlaying} from "./movement.js";
import {enableSaveBtn} from "./movement.js";

export let saveGameBtn = document.getElementById('saveGameBtn');

loadFirstPage();

function loadFirstPage() {
    chessBoard.style.display = "none";
    gameResultWindow.style.display = "none";
    player1.style.display = "none";
    player2.style.display = "none";
    saveGameBtn.style.display = "none";
}

function startNewGame() {
    fetch("/startNewGame")
        .then(response => {
            if(!response.ok) {
                throw new Error(response.status);
            }
            return response.json();
        })
        .then(data => {
            initializeChessBoard(data);
        })
        .catch(error => {
            console.log(error)
            alert("서버와의 통신이 실패하였습니다.");
        })
}

function loadPrevGame() {
    fetch("/loadPrevGame")
        .then(response => {
            if(!response.ok) {
                throw new Error(response.status);
            }
            return response.json();
        })
        .then(data => {
            initializeChessBoard(data);
            checkIsPlaying(data);
        })
        .catch(error => {
            console.log(error)
            alert("서버와의 통신이 실패하였습니다.");
        })
}

function initializeChessBoard(data) {
    console.log(data);
    initChessBoard(data);
    addChessBoardEvent();
    saveGameBtn.style.display = "block";
    enableSaveBtn();
    startBtn.style.display = "none";
    loadBtn.style.display = "none";
    chessBoard.style.display = "flex";
    player1.style.display = "flex";
    player2.style.display = "flex";
}

startBtn.addEventListener("click", startNewGame);
loadBtn.addEventListener("click", loadPrevGame);