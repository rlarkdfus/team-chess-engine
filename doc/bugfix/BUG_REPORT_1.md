## Description

Summary of the feature's bug (without describing implementation details)
    * On the board editor, when two different pieces are placed from the menu onto the board and deselected, and the user attempts
      to move one of the pieces again to an empty spot, there's an exception thrown.
## Expected Behavior
    * The piece should be able to be moved again to the empty spot.
## Current Behavior
    * The piece is not moved to the new spot and there's an exception thrown.

## Steps to Reproduce
 1. Log into the game (can simply click "play as guest")
 2. Select "Board Editor" from the Variation dropdown on the top right menu to open the Board Editor in a
    new window.
 3. Place two pieces on the board with a deselection in between (place one piece form the bottom menu onto the board,
    deselect by clicking on the icon in the menu again, place a different second piece in the same manner,
    and deselect the second one in the same manner)
 4. Try moving one of the pieces again by clicking on that piece, then clicking on any open square on the board.
    The destination square should not have any pieces in it. There should be an exception thrown

## Failure Logs
    * Exception in thread "JavaFX Application Thread" java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because "this.moves" is null
      	at ooga.model.Piece.updateMoves(Piece.java:203)
      	at ooga.model.Board.updateLegalMoves(Board.java:119)
      	at ooga.model.Board.movePiece(Board.java:68)
      	at ooga.controller.Controller.movePiece(Controller.java:130)
      	at ooga.view.boardview.BoardView.movePiece(BoardView.java:243)
      	at ooga.view.boardview.EditorBoardView.clickBoard(EditorBoardView.java:49)
      	at ooga.view.boardview.BoardView.lambda$initializeBoardView$0(BoardView.java:61)
      	at javafx.base/com.sun.javafx.event.CompositeEventHandler.dispatchBubblingEvent(CompositeEventHandler.java:86)
      	at javafx.base/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:234)
      	at javafx.base/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:191)
      	at javafx.base/com.sun.javafx.event.CompositeEventDispatcher.dispatchBubblingEvent(CompositeEventDispatcher.java:59)
      	at javafx.base/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:58)
      	at javafx.base/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
      	at javafx.base/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
      	at javafx.base/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
      	at javafx.base/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
      	at javafx.base/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
      	at javafx.base/com.sun.javafx.event.EventUtil.fireEventImpl(EventUtil.java:74)
      	at javafx.base/com.sun.javafx.event.EventUtil.fireEvent(EventUtil.java:54)
      	at javafx.base/javafx.event.Event.fireEvent(Event.java:198)
      	at javafx.graphics/javafx.scene.Scene$ClickGenerator.postProcess(Scene.java:3566)
      	at javafx.graphics/javafx.scene.Scene$MouseHandler.process(Scene.java:3868)
      	at javafx.graphics/javafx.scene.Scene.processMouseEvent(Scene.java:1854)
      	at javafx.graphics/javafx.scene.Scene$ScenePeerListener.mouseEvent(Scene.java:2587)
      	at javafx.graphics/com.sun.javafx.tk.quantum.GlassViewEventHandler$MouseEventNotification.run(GlassViewEventHandler.java:409)
      	at javafx.graphics/com.sun.javafx.tk.quantum.GlassViewEventHandler$MouseEventNotification.run(GlassViewEventHandler.java:299)
      	at java.base/java.security.AccessController.doPrivileged(AccessController.java:391)
      	at javafx.graphics/com.sun.javafx.tk.quantum.GlassViewEventHandler.lambda$handleMouseEvent$2(GlassViewEventHandler.java:447)
      	at javafx.graphics/com.sun.javafx.tk.quantum.QuantumToolkit.runWithoutRenderLock(QuantumToolkit.java:413)
      	at javafx.graphics/com.sun.javafx.tk.quantum.GlassViewEventHandler.handleMouseEvent(GlassViewEventHandler.java:446)
      	at javafx.graphics/com.sun.glass.ui.View.handleMouseEvent(View.java:556)
      	at javafx.graphics/com.sun.glass.ui.View.notifyMouse(View.java:942)
      	at javafx.graphics/com.sun.glass.ui.win.WinApplication._runLoop(Native Method)
      	at javafx.graphics/com.sun.glass.ui.win.WinApplication.lambda$runLoop$3(WinApplication.java:174)
      	at java.base/java.lang.Thread.run(Thread.java:831)
      Exception in thread "JavaFX Application Thread" java.lang.NullPointerException: Cannot invoke "ooga.model.PieceInterface.getLocation()" because "piece" is null
      	at ooga.model.Moves.Move.movePiece(Move.java:124)
      	at ooga.model.Moves.TranslationMove.executeMove(TranslationMove.java:41)
      	at ooga.model.Board.executeMove(Board.java:73)
      	at ooga.model.Board.movePiece(Board.java:66)
      	at ooga.controller.Controller.movePiece(Controller.java:130)
      	at ooga.view.boardview.BoardView.movePiece(BoardView.java:243)
      	at ooga.view.boardview.EditorBoardView.clickBoard(EditorBoardView.java:49)
      	at ooga.view.boardview.BoardView.lambda$initializeBoardView$0(BoardView.java:61)
      	at javafx.base/com.sun.javafx.event.CompositeEventHandler.dispatchBubblingEvent(CompositeEventHandler.java:86)
      	at javafx.base/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:234)
      	at javafx.base/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:191)
      	at javafx.base/com.sun.javafx.event.CompositeEventDispatcher.dispatchBubblingEvent(CompositeEventDispatcher.java:59)
      	at javafx.base/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:58)
      	at javafx.base/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
      	at javafx.base/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
      	at javafx.base/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
      	at javafx.base/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
      	at javafx.base/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
      	at javafx.base/com.sun.javafx.event.EventUtil.fireEventImpl(EventUtil.java:74)
      	at javafx.base/com.sun.javafx.event.EventUtil.fireEvent(EventUtil.java:54)
      	at javafx.base/javafx.event.Event.fireEvent(Event.java:198)
      	at javafx.graphics/javafx.scene.Scene$ClickGenerator.postProcess(Scene.java:3566)
      	at javafx.graphics/javafx.scene.Scene$MouseHandler.process(Scene.java:3868)
      	at javafx.graphics/javafx.scene.Scene.processMouseEvent(Scene.java:1854)
      	at javafx.graphics/javafx.scene.Scene$ScenePeerListener.mouseEvent(Scene.java:2587)
      	at javafx.graphics/com.sun.javafx.tk.quantum.GlassViewEventHandler$MouseEventNotification.run(GlassViewEventHandler.java:409)
      	at javafx.graphics/com.sun.javafx.tk.quantum.GlassViewEventHandler$MouseEventNotification.run(GlassViewEventHandler.java:299)
      	at java.base/java.security.AccessController.doPrivileged(AccessController.java:391)
      	at javafx.graphics/com.sun.javafx.tk.quantum.GlassViewEventHandler.lambda$handleMouseEvent$2(GlassViewEventHandler.java:447)
      	at javafx.graphics/com.sun.javafx.tk.quantum.QuantumToolkit.runWithoutRenderLock(QuantumToolkit.java:413)
      	at javafx.graphics/com.sun.javafx.tk.quantum.GlassViewEventHandler.handleMouseEvent(GlassViewEventHandler.java:446)
      	at javafx.graphics/com.sun.glass.ui.View.handleMouseEvent(View.java:556)
      	at javafx.graphics/com.sun.glass.ui.View.notifyMouse(View.java:942)
      	at javafx.graphics/com.sun.glass.ui.win.WinApplication._runLoop(Native Method)
      	at javafx.graphics/com.sun.glass.ui.win.WinApplication.lambda$runLoop$3(WinApplication.java:174)
      	at java.base/java.lang.Thread.run(Thread.java:831)


## Hypothesis for Fixing the Bug

Describe the test you think will verify this bug and the code fix you believe will solve this issue.
    * A test that will verify this bug is a test in EditorViewTest that uses TestFX to move pieces in a way
      according to the steps described above that will replicate the issue. The code that will solve this issue
      would be in EditorBoard because that's where we passed in "null" for Moves for a piece and the first
      line in the stack trace is a NullPointerException for this.moves. We could pass in an empty ArrayList
      and the location parameter in the function when creating the piece to fix this issue.