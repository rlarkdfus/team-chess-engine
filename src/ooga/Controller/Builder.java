package ooga.Controller;

import ooga.Model.Engine;
import ooga.view.BoardViewInterface;

public interface Builder {
  Engine buildEngine();
  BoardViewInterface buildView();
}
