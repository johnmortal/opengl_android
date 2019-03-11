package com.airhockey.android.com.airhockey.android.util;

import android.util.Log;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glGetShaderInfoLog;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glShaderSource;

public class ShaderHelper {
    private static final String TAG = "ShaderHelper";

    public static int compileVertexSHader(String shaderCode) {
        return compileShader(GL_VERTEX_SHADER, shaderCode);
    }

    public static int compileFragmentSHader(String shaderCode) {
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }

    private static int compileShader(int type, String shaderCode) {
        final int shaderObjectId = glCreateShader(type); // try and create a shader object on the graphics card of the given type
        if (shaderObjectId == 0 ) { // 0 means the graphics card failed to create the object
            if (LoggerConfig.ON) {
                Log.w(TAG, "Could not create new shader.");
            }
            return 0;
        }
        glShaderSource(shaderObjectId, shaderCode); // upload source code to the graphics card and associate it with the given ID
        final int[] compileStatus = new int[1];
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0); //

        if (LoggerConfig.ON) {
            Log.v(TAG, "Results of compiling source: " + '\n' + shaderCode + '\n' + glGetShaderInfoLog(shaderObjectId));
        }
        if ( compileStatus[0] == 0) {
            glDeleteShader(shaderObjectId); // compilation failed, so delete the opengl shader object
            if (LoggerConfig.ON) {
                Log.w(TAG, "Compilation of shader failed.");
            }
            return 0;
        }

        return shaderObjectId;
    }
}
