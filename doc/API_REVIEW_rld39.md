### Part 1
- How does your API encapsulate your implementation decisions?
  - My ViewInterface API encapsulates my 
    implementation decisions because it provides a 
    template for creating a View for a board game without
    forcing the user to implement any functionalities in a
    specific way.
- What abstractions is your API built on?
  - Currently, our API does not use abstractions but to
    improve it in this area, we could make an interface
    or abstract class for Turn that would make Turns more
    flexible for different types of games.
- What about your API's design is intended to be flexible?
  - My API's methods are very general for any view that 
    may be used for a board game. For example, 
    changePieceStyle() is likely a common method that
    would be useful for any type of board game with pieces, and
    having that method in the API provides a useful
    method to override without specifying implementation 
    details.
- What exceptions (error cases) might occur in your API and how are they addressed?
  - Exceptions that might occur in my API would be due 
    user implementation details the current method 
    signatures within ViewInterface do not throw errors.
### Part 2
- How do you justify that your API is easy to learn?
  - My API is easy to learn because the methods have 
    very descriptive names and the types of variables 
    passed in are easy to learn how to use and work with.
- How do you justify that API leads to readable code?
  - My API leads to readable code because it forces 
    classes that implement it to override well-named
    methods. The overridden methods also help split up 
    a class' code into chunks that satisfy specific purposes,
    which overall keeps the code more organized and 
    readable.
- How do you justify that API is hard to misuse?
  - My API is hard to misuse because the methods have 
    parameters with specific types relevant to the specific
    method. For example, changeBoardColor() takes in 
    color parameters, so it's impossible to override the
    method and misuse it by passing in non-Color 
    parameters.
- Why do you think your API design is good (also define what your measure of good is)?
  - My API design is good because it's easy to 
    understand and can be generalized to many types
    of board games, which should be essential qualities of
    any solid API design. 