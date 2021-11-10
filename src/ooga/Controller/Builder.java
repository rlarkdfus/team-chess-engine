package ooga.Controller;

import ooga.Model.Engine;
import ooga.View.BoardViewInterface;

public interface Builder {
  Engine buildEngine();
  BoardViewInterface buildView();
}
