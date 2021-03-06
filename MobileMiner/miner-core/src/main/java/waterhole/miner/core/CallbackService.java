package waterhole.miner.core;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import waterhole.miner.core.analytics.AnalyticsWrapper;

import static waterhole.miner.core.utils.LogUtils.errorWithReport;

/**
 * @author huwwds on 2018/05/21
 */
public final class CallbackService extends Service {

    static StateObserver sStateObserver;

    public static void setCallBack(StateObserver stateObserver) {
        sStateObserver = stateObserver;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new CallbackBinder();
    }

    public class CallbackBinder extends MineCallback.Stub {

        @Override
        public void onConnectPoolBegin() throws RemoteException {
            sStateObserver.onConnectPoolBegin();
        }

        @Override
        public void onConnectPoolSuccess() {
            try {
                sStateObserver.onConnectPoolSuccess();
            } catch (RemoteException e) {
                errorWithReport(getApplicationContext(), "CallbackService|onConnectPoolSuccess: " + e.getMessage());
            }
        }

        @Override
        public void onConnectPoolFail(String error) {
            try {
                sStateObserver.onConnectPoolFail(error);
            } catch (RemoteException e) {
                errorWithReport(getApplicationContext(), "CallbackService|onConnectPoolFail: " + e.getMessage());
            }
        }

        @Override
        public void onPoolDisconnect(String error) {
            try {
                sStateObserver.onPoolDisconnect(error);
            } catch (RemoteException e) {
                errorWithReport(getApplicationContext(), "CallbackService|onPoolDisconnect: " + e.getMessage());
            }
        }

        @Override
        public void onMessageFromPool(String message) {
            try {
                sStateObserver.onMessageFromPool(message);
            } catch (RemoteException e) {
                errorWithReport(getApplicationContext(), "CallbackService|onMessageFromPool: " + e.getMessage());
            }
        }

        @Override
        public void onMiningError(String error) {
            try {
                sStateObserver.onMiningError(error);
            } catch (RemoteException e) {
                errorWithReport(getApplicationContext(), "CallbackService|onMiningError: " + e.getMessage());
            }
        }

        @Override
        public void onMiningStatus(double speed) {
            try {
                sStateObserver.onMiningStatus(speed);
                AnalyticsWrapper.onMiningEvent(getApplicationContext(), speed);
            } catch (RemoteException e) {
                errorWithReport(getApplicationContext(), "CallbackService|onMiningStatus: " + e.getMessage());
            }
        }
    }
}
