###Test plan
In order to make our APIs more testable we will make our APIs throw
exceptions when incorrect inputs are given. We will also make them return
useful values for the board and piece states that we can look at and visually
identify. We can use reflection to get private values.

#### feature 1 creating pieces
If the user uploads a sim file with incorrect information
the parser will parse the sim file to locate keys that identify
properties. If none are located the parser will throw
and exception indicating as such.

If the user uploads a sim file with correct values then the
parser will parse the sim file to locate the keys that identify
properties. It creates a piece object with the given properties.

If the user uploads a sim file with the proper keys and inputs
but which contains an extra key and value field, our program will
succeed by only looking for defined keys and values, and ignoring the
extra input. It creates a piece object with the given properties.

#### feature 2 creating the board
If the user uploads a properties sim file for the board that has negative
values for the rows or columns we will throw an exception that the inputs
are invalid. It should prompt the user to upload a valid board file.

If the user uploads a properties sim file for the board with correct parameters
we will create a board object with the proper parameters. It returns a board object.

If the user uploads a sim file for the board that only contains partial information
we will make our API first parse the file to get all the given keys and values.
Other missing values are inferred from default values in the board constructor. It
creates and returns a board object.

####feature 3 initial state
The user uploaded sim file for each piece should define its initial states. If invalid
initial states are given the API should throw an error and indicate that one or more of
the states are invalid.

If all of the sim file initial states are correct then the API will create a new piece
object at each of the given states and populate the board with those pieces.

If the sim file contains initial states for a piece that is not described in a sim file
then our API will throw an exception that the piece is not defined and prompt the user
to upload a file implementing that piece.

####feature 4 resign and draw
Our game allows the user to resign and offer draws. If the user resigns and offers a draw on
their turn the engine should end their turn and prompt the next player to whether or not to 
accept the resign offer. If the next player accepts, we end the game and declare a winner. If not, 
we end that turn and continue the game where the first player left off.

If a player attempts to resign while it is not their turn it displays a message to the user
that they cannot do so, and resumes the game with the opponents turn.

If a player attempts to resign after they have been checkmated the game should
throw an error and tell them that they are unable to resign, and declare the opponent the winner.
