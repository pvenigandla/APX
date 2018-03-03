package hackfsu.apx.utils;

/**
 * Created by pranathi on 3/3/18.
 */

public abstract class CallbackWithType <T>  {
    public abstract void onComplete (T t);

    public void onError () {}
}