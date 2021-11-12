package ooga.controller;

import ooga.model.Engine;
import ooga.view.BoardViewInterface;

public interface Builder {
  Engine buildEngine();
  BoardViewInterface buildView();
}
