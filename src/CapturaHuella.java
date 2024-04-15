import com.zkteco.biometric.FingerprintSensorErrorCode;
import com.zkteco.biometric.FingerprintSensorEx;

public class CapturaHuella {

    public static void main(String[] args) {
        // Inicializar el sensor
        int iniciar = FingerprintSensorEx.Init();
        if (iniciar != FingerprintSensorErrorCode.ZKFP_ERR_OK) {
            System.out.println("Error al inicializar el sensor: " + iniciar);
            return;
        }else{
            System.out.println("Sensor detectado atoscushi!!");
        }

        // Abrir el dispositivo
        long mhDevice = FingerprintSensorEx.OpenDevice(0);
        if (mhDevice == 0) {
            System.out.println("No se pudo abrir el dispositivo");
            return;
        }


        // Cerrar el dispositivo y terminar
        FingerprintSensorEx.CloseDevice(mhDevice);
        FingerprintSensorEx.Terminate();
    }
}
