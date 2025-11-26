// Callback.aidl
package org.qiyi.video.svg;
import android.os.Bundle;
import org.qiyi.video.svg.event.Event;
// Declare any non-default types here with import statements

interface IRemoteTransfer {

  oneway void registerDispatcher(IBinder dispatcherBinder);

  oneway void unregisterRemoteService(String serviceCanonicalName);

  oneway void notify(in Event event);
}
