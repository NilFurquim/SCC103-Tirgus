package tirgus.net.message;

import tirgus.net.TirgusConnection;

@FunctionalInterface
public interface TirgusMessageCallback
{
    boolean callback(TirgusConnection connection, TirgusMessage message);
}
