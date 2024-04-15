import com.zkteco.biometric.FingerprintSensorErrorCode; //Importacion de las librerias basicas a utilizar.
import com.zkteco.biometric.FingerprintSensorEx;

public class CapturaHuella {

    public static void main(String[] args) {

        // Aqui inicializamos el SDK:
        int encendido = FingerprintSensorEx.Init(); //--> Guardamos en una variable el retorno del metodo init()
        if (encendido != FingerprintSensorErrorCode.ZKFP_ERR_OK) {
            System.out.println("Error al inicializar el sensor: " + encendido); //-->Mostramos el tipo de error obtenido.
            return;
        }else{
            System.out.println("SDK inicializado correctamente!!"); //Si no obtenemos errores el SDK se ha inicializado! :)
        }

        // Abrir el dispositivo:
        long abertura = FingerprintSensorEx.OpenDevice(0); /*--> El metodo OpenDevice retorna 0 si no detecta el sensor.*/
        if (abertura == 0) {
            System.out.println("No se pudo abrir el dispositivo");
            return;
        }else{
            System.out.println("Dispositivo Listo para ser usado!!"); //
        }


        // Cerrar el dispositivo y terminar
        FingerprintSensorEx.CloseDevice(abertura); //Es muy importante cerrar el dispositivo para liberar recursos
        FingerprintSensorEx.Terminate(); //Y terminar la inicializacion del SDK para no tener errores inesperados :)
    }
}
