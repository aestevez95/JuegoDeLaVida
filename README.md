# JuegoDeLaVida

This project is a basic version of "the game of life". The game consists of creating a board with a matrix of
cells, which can be alive or dead (depending on their color). For each iteration of the main program, the state
of each cell is updated with the following rules:

If a cell is in an overpopulated area (4 or more neighbours) ...  it dies
If a cell is in a deserted area (0 or 1 neighbour)           ...  it dies
If a live cell has 2 or 3 neighbours                         ...  it continues alive
If a dead cell has 3 neighbours                              ...  it revives (or it is born)
