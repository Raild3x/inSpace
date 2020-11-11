package listeners;

import java.util.EventListener;

/**
 *
 * @author Logan
 */
public interface PostRenderListener extends EventListener {
    void postRender(double dt);
}
