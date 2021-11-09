package ooga.Controller;

import ooga.Model.Engine;
import ooga.View.BoardView;

public interface Builder {
  Engine buildEngine();
  BoardView buildView();
}
