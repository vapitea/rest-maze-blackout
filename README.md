# Maze solver application

## Running the app

Running the app requires JRE 11+.

### In commandline or terminal
```
git clone https://github.com/vapitea/rest-maze-blackout.git
cd rest-maze-blackout
java -jar target/rest-maze-blackout-0.0.1-SNAPSHOT.jar
```

After running the jar file, open a browser and visit: [http://localhost:8080/mazeSolution](http://localhost:8080/mazeSolution) to start solving a new random maze.

The page will load for 1-5 minutes, until the solution to the maze is found. In the meantime you can check the progress on the console screen, where every step is printed, a fontsize of 10 is recommended.

### Progress on the console

On top, the current Maze Cell is shown. 
The light rectangles are the visited cells, every cell has 4 neighbouring cells, they can be:  
* UNEXPLORED - 'U'
* BLOCKED - 'B'
* VISITED - 'W'

When the end is reached, the secret message will also be printed on the last line.


![Progress-on-console-windows](./documents/images/progress-on-console-windows.png)

![Progress-on-console](./documents/images/progress-on-console.png)


### Solution in browser

After the page is loaded, the secret message and the final state of the maze is displayed. A green line will indicate the chosen path, with an O
 at the starting cell and an X at the end.
 
Below is an example image of a run.


![Final-state-image](./documents/images/maze-solution-browser.png)







