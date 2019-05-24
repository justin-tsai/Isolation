# Isolation
![Demo](https://i.imgur.com/vO4zM7k.jpg)

## Setup/Run
This project is played on a console/terminal. To play, run Main.java and follow the console instructions that are printed.

## About
Isolation is a two-player, time-based version of minimax with alpha-beta and a board evaluation function. The heuristic is calculated using both player's available moves. The AI attempts to maximize its own moves while minimizing the opponents available moves, prioritizing the former.

## How to Play
The game is played on an 8x8 board with both players starting at opposite corners. Each player is able to move in a straight line or in a diagonal line, much like a queen on a chess board. To enter a move input, type the coordinate you wish to move to (Example: D4). Once a move is made, that spot cannot be moved to or passed through for the remainder of the game. The game ends when a player no longer has an available move to make.

## Thoughts
This was my first time developing a game using an AI to make its moves rather than hard-coding moves. If I had more time to further develop this project, I would make a GUI for it so that it can be run with an .exe file. I would also experiment with the heuristic to make it more challenging to beat.
