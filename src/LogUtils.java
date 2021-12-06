import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtils {

  public static void debug(Object obj, Object toBeLogged) {
    Logger logger = LogManager.getLogger(obj.getClass());
    logger.debug(toBeLogged);
  }

  public static void info(Object obj, Object toBeLogged) {
    Logger logger = LogManager.getLogger(obj.getClass());
    logger.info(toBeLogged);
  }

  public static void warn(Object obj, Object toBeLogged) {
    Logger logger = LogManager.getLogger(obj.getClass());
    logger.warn(toBeLogged);
  }

  public static void error(Object obj, Object toBeLogged) {
    Logger logger = LogManager.getLogger(obj.getClass());
    logger.error(toBeLogged);
  }

  public static void fatal(Object obj, Object toBeLogged) {
    Logger logger = LogManager.getLogger(obj.getClass());
    logger.fatal(toBeLogged);
  }

  public static void trace(Object obj, Object toBeLogged) {
    Logger logger = LogManager.getLogger(obj.getClass());
    logger.trace(toBeLogged);
  }
}
