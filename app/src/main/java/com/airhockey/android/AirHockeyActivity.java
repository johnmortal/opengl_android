package com.airhockey.android;


import android.os.Bundle;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.pm.ConfigurationInfo;
import android.widget.Toast;

//import android.support.v7.app.AppCompatActivity;

public class AirHockeyActivity extends Activity {
    private GLSurfaceView glSurfaceView;
    private boolean rendererSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glSurfaceView = new GLSurfaceView(this);
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

//        Toast.makeText(this, "Build Version SDK_INT " + Build.VERSION.SDK_INT, Toast.LENGTH_LONG ).show();

        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000
                || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                && (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")));
        if (supportsEs2) {
//            glSurfaceView.setDebugFlags(GLSurfaceView.DEBUG_CHECK_GL_ERROR);
            glSurfaceView.setEGLContextClientVersion(2);
            glSurfaceView.setRenderer(new AirHockeyRenderer(this));
            rendererSet = true;
        } else {
            Toast.makeText(this, "This device does not support OpenGl ES 2.0.", Toast.LENGTH_LONG).show();
            return;
        }
        setContentView(glSurfaceView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (rendererSet) glSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (rendererSet) glSurfaceView.onResume();
    }

}
