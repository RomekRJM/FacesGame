package rjm.romek.facegame.ui.manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameUtils;

import rjm.romek.facegame.R;
import rjm.romek.facegame.common.GooglePlayable;

public class GooglePlayManager implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GooglePlayable googlePlayable;

    private GoogleApiClient googleApiClient;

    public static final int RC_UNUSED = 5001;
    public static int CODE_OK = 1;
    private static int RC_SIGN_IN = 9001;
    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInFlow = true;
    private boolean shareButtonActive = false;
    private boolean connectionFailed = false;

    static final String TAG = "GooglePlayManager";


    public GooglePlayManager(GooglePlayable googlePlayable) {
        this.googlePlayable = googlePlayable;
    }
    public void init() {
        // Create the Google API Client with access to Plus and Games
        try {
            // Create the Google Api Client with access to the Play Game services
            googleApiClient = new GoogleApiClient.Builder(googlePlayable.getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                    .build();

        } catch (Exception exc) {
            connectionFailed = true;
        }
    }

    public void start() {
        Log.d(TAG, "onStart(): connecting");
        try {
            googleApiClient.connect();
        } catch (Exception exc) {
        }
    }

    public void stop() {
        Log.d(TAG, "onStop(): disconnecting");
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    public boolean isSignedIn() {
        return (googleApiClient != null && googleApiClient.isConnected());
    }

    @Override
    public void onConnected(Bundle bundle) {
        updateShareButton();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        connectionFailed = true;

        if (mResolvingConnectionFailure) {
            // already resolving
            return;
        }

        // if the sign-in button was clicked or if auto sign-in is enabled,
        // launch the sign-in flow
        if (mAutoStartSignInFlow) {
            mAutoStartSignInFlow = false;
            mResolvingConnectionFailure = true;

            // Attempt to resolve the connection failure using BaseGameUtils.
            // The R.string.signin_other_error value should reference a generic
            // error string in your strings.xml file, such as "There was
            // an issue with sign-in, please try again later."
            if (!BaseGameUtils.resolveConnectionFailure(googlePlayable.getActivity(),
                    googleApiClient, connectionResult, RC_SIGN_IN,
                    googlePlayable.getActivity().getString(R.string.signin_other_error))) {
                mResolvingConnectionFailure = false;
            }
        }

        updateShareButton();
        // Put code here to display the sign-in button
    }

    @Override
    public void onConnectionSuspended(int i) {
        // Attempt to reconnect
        googleApiClient.connect();
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            mResolvingConnectionFailure = false;
            if (resultCode == CODE_OK) {
                googleApiClient.connect();
            } else {
                // Bring up an error dialog to alert the user that sign-in
                // failed. The R.string.signin_failure should reference an error
                // string in your strings.xml file that tells the user they
                // could not be signed in, such as "Unable to sign in."
                BaseGameUtils.showActivityResultError(googlePlayable.getActivity(),
                        requestCode, resultCode, R.string.signin_failure);
                connectionFailed = true;
            }
        }
        updateShareButton();
    }

    public void updateShareButton() {
        Button shareButton = googlePlayable.getShareButton();
        Activity activity = googlePlayable.getActivity();

        if (googlePlayable.isEmpty()) {
            shareButton.setText(activity
                    .getText(R.string.share_button_nothing_to_publish));
            shareButtonActive = false;
        } else if (connectionFailed) {
            shareButtonActive = true;
            shareButton.setText(activity
                    .getText(R.string.share_button_retry_connection));
        } else if (googleApiClient != null && googleApiClient.isConnected()) {

            shareButtonActive = true;
            if (googlePlayable.needsPublishing()) {
                shareButton.setText(activity
                        .getText(R.string.share_button_connected));
            } else {
                shareButton.setText(activity
                        .getText(R.string.share_button_all_published));
            }
        } else {
            shareButton.setText(activity
                    .getText(R.string.share_button_connecting));
            shareButtonActive = false;
        }
    }

    public GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }

    public boolean isConnectionFailed() {
        return connectionFailed;
    }

    public void setConnectionFailed(boolean connectionFailed) {
        this.connectionFailed = connectionFailed;
    }

    public boolean isShareButtonActive() {
        return shareButtonActive;
    }
}
