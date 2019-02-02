package cc.urowks.ulibrary.util;

import android.hardware.Camera;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;


public class PermissionTest {
    private static PermissionTest instance;

    private PermissionTest() {
    }

    public static PermissionTest getInstance() {
        return PermissionTestHolder.instance;
    }

    private static class PermissionTestHolder {
        private static final PermissionTest instance = new PermissionTest();
    }

    /**
     * 作用：用户是否同意打开相机权限
     *
     * @return true 同意 false 拒绝
     */
    public boolean isCameraPermission() {

        try {
            Camera.open().release();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 作用：用户是否同意录音权限
     *
     * @return true 同意 false 拒绝
     */
    public boolean isVoicePermission() {

        try {
            AudioRecord record = new AudioRecord(MediaRecorder.AudioSource.MIC, 22050, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                    AudioFormat.ENCODING_PCM_16BIT, AudioRecord.getMinBufferSize(22050, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                    AudioFormat.ENCODING_PCM_16BIT));
            record.startRecording();
            int recordingState = record.getRecordingState();
            if (recordingState == AudioRecord.RECORDSTATE_STOPPED) {
                return false;
            }
            record.release();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

}  