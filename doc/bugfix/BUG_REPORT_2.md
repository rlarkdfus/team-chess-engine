## Description

Teleport move allows a piece to teleport directly to a location if they are eligible to.

## Expected Behavior

The piece should teleport if the square is not occupied or if the square is occupied by 
the opposing team and the piece can take.

## Current Behavior

The piece can always teleport to the square, and can kill own team pieces.

## Steps to Reproduce

Provide detailed steps for reproducing the issue.

1. Make sure own team piece is on teleport square.
2. Teleport piece to square, killing own team piece.

## Failure Logs

N/A

## Hypothesis for Fixing the Bug

Test with own team piece on square, and attempt to take with the teleport move. The take should
not be a valid move.

Fix by checking the team of the taken piece in findAllEndLocations