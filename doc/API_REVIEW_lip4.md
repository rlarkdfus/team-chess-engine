# Partner: Alex Desbans ard59
## Part 1
1. How does your API encapsulate your implementation decisions?

The Writer interface that I will soon build encapsulated the unique decisions involved in saving a
specific kind of file to the user's machine. Any class implementing this interface will have the
saveFile() method allowing other classes to download a file to the user's computer without concern 
for how it works under the hood. 

2. What abstractions is your API built on?

My API is built on the abstraction of saving a file to a user's computer. To a user of my API the 
only concern they will have is that the file is saved properly, and they do not want to know 
how it is the file is saved.

3. What about your API's design is intended to be flexible?

My API is intended to be flexible across any sort of file type, so any file that may need to be
saved can be with a simple call to this API.

4. What exceptions (error cases) might occur in your API and how are they addressed?

The main exception that could be a concern is if an invalid file path is passed in to the
saveFile method, which should result in an alert that the file cannot be saved to this path. 

## Part 2 
1. How do you justify that your API is easy to learn?

My API is very easy to learn because it is essentially built only on one method, saveFile(). This
will be simple as it will require only the parameter of the file path and then will save the data 
accordingly. 

2. How do you justify that API leads to readable code?

This API leads to readable code because we will have one reliable method allowing files to be 
written to the user's machine without worry of implementation or any of the unique details required
for each file writer. 

3. How do you justify that API is hard to misuse?

This method will work correctly as long as a valid file path is passed in as a parameter. It is hard
to misuse as this is not a true concern in the program as this will not be a common error. 

4. Why do you think your API design is good (also define what your measure of good is)?

I think it is good because it is relatively simple and clear to use. There is only one method with 
which to be concerned, and this simplicity seems to lend itself well to not introducing bugs. 