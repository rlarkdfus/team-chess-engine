#### Kyle White (Kgw47), Tarun Amasa (tra23)

## Part 1

**1.  How does your API encapsulate your implementation decisions?**


For our game, we resolved to build a chess engine. Our API is based on the implementation design of utilizing a board, move classes, and a player that work synergistically for the board to work with each. We follow an MVC implementation, and our APIS are split into these buckets accordingly to hold classes to a contract such that the class interaction flows without errors.

**2.  What abstractions is your API built on?**


Our abstractions are based on inheritance structures for specific subclasses that implement the same APIS. The interface for the Move class and their subclasses to be interacted with, within a broader Board class and the contracts allow this flexibility

**3.  What about your API's design is intended to be flexible?**


Our APIS are built such that if subclasses or classes with similar functionality need to be made, the interfaces can be implemented for them, and the subclasses will be contractually obligated to do so. This allows our code to be extended without further implementation.

**4.  What exceptions (error cases) might occur in your API and how are they addressed?**


Error cases can occur if the wrong parameters are passed to the public method or if the output by the method is an edge case. These are handled with custom exceptions that are written such that the user can determine which input was incorrect.

## Part 2

**1.  How do you justify that your API is easy to learn?**


Our APIs are easy to learn because they follow a generalized format with javadocs, we outline exactly which classes use which APIS, and they are easily open for extension.

**2.  How do you justify that API leads to readable code?**


API leads to readable code, as the user only has to interpret when to use specific methods, not their implementation. Our API documentation allows the user to see the specific inputs, what methods do, and what outputs are provided.

This also allows for extension, as the classes that use an API are contracted to use them, which means that you would be able to guarantee a method/implementation will be called for them.



**3.  How do you justify that API is hard to misuse?**


Our APIS are hard to misuse as there are unique exceptions that are built into each of the method skeletons. Moreover, the APIS are only implemented in the classes that follow the outline for them,

**4.  Why do you think your API design is good (also define what your measure of good is)?**


I think that our APIS could be better if they followed Single Responsibility principles. Currently, each class only implements a single API, but I feel that if we have multiple APIS that classes implement and each of these APIS only handle a specific set of instructions. For example, the PlayerInterface has several methods that pertain to the player’s attributes including their pieces, their time, and their turn. Instead of putting all these methods under the player interface, we can split this into a timer interface and a piece interface etc .