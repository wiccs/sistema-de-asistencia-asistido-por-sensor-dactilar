import com.zkteco.biometric.FingerprintSensorErrorCode; //Importacion de las librerias basicas a utilizar.
import com.zkteco.biometric.FingerprintSensorEx;

public class CapturaHuella {

    public static int byteArrayToInt(byte[] bytes) { //Esta funcion se encarga de convertir un arreglo en bytes a tipo Int :)
        return ((bytes[0] & 0xFF)) |
                ((bytes[1] & 0xFF) << 8) |
                ((bytes[2] & 0xFF) << 16) |
                ((bytes[3] & 0xFF) << 24);
    }

    public static void main(String[] args) {

        // Aqui inicializamos el SDK:
        int encendido = FingerprintSensorEx.Init(); //--> Guardamos en una variable el retorno del metodo init()
        if (encendido != FingerprintSensorErrorCode.ZKFP_ERR_OK) {
            System.out.println("Error al inicializar el sensor: " + encendido); //-->Mostramos el tipo de error obtenido.
            return;
        }else{
            System.out.println("SDK inicializado correctamente!!"); //Si no obtenemos errores el SDK se ha inicializado! :)
        }

        // Abrir el dispositivo: ---------------------------------------------------------------------------------------
        long identificador = FingerprintSensorEx.OpenDevice(0); /*--> El metodo OpenDevice retorna 0 si no detecta el sensor.*/
        if (identificador == 0) {
            System.out.println("No se pudo abrir el dispositivo");
            return;
        }else{
            System.out.println("Dispositivo identificado!!"); //
        }


        //--> Obtener datos del sensor: --------------------------------------------------------------------------------
        int codeancho = 1; // variables para obtener el ancho y largo de la imagen
        int codealto = 2; //

        //Arreglos de bytes que contienen los bits para almacenar los datos a pedir.
        byte[] valueA = new byte[4];
        byte[] valueL = new byte[4];

        int[] size = new int[1]; //Inicializamos el array.Es para guardar el tamaño de valueA y ValueL
        size[0] = 4;

        // Obtener el ancho
        int retancho = FingerprintSensorEx.GetParameters(identificador, codeancho, valueA, size);
        if (retancho == 0) {
            int width = byteArrayToInt(valueA); //convertir array byte a tipo int.
            System.out.println("Ancho de la imagen: " + width + " pixeles");
        } else {
            System.out.println("Error al obtener el ancho de la imagen: " + retancho);
        }

        // Obtener el largo de la imagen
        int retlargo = FingerprintSensorEx.GetParameters(identificador, codealto, valueL, size);
        if (retlargo == 0) {
            int height = byteArrayToInt(valueL); //convertir array byte a tipo int.
            System.out.println("Alto de la imagen: " + height + " pixeles");
        } else {
            System.out.println("Error al obtener el ancho de la imagen: " + retlargo);
        }

        // Extraer una imagen y una plantilla de una huella: -----------------------------------------------------------


        // Calcular el tamaño del buffer de la imagen
       int tamañoImagen = byteArrayToInt(valueA) * byteArrayToInt(valueL);
        //int imgSize = (280 * 360);

        byte[] imgBuffer = new byte[tamañoImagen];
       // byte[] imgBuffer = ByteBuffer.allocate(4).putInt(imgSize).array();


        byte[] template = new byte[2048];
        int[] tamaño = new int[1];//Posible error a corregir


        System.out.println("imgSize = " + tamañoImagen );

        int rethuellita = FingerprintSensorEx.AcquireFingerprint(identificador,imgBuffer,template,tamaño);

        if(rethuellita == 0){
            System.out.println("Huella capturada!");
        }else{
            System.out.println("Error al capturar huella: " + rethuellita);
        }

        // Cerrar el dispositivo y terminar :---------------------------------------------------------------------------
        FingerprintSensorEx.CloseDevice(identificador); //Es muy importante cerrar el dispositivo para liberar recursos
        FingerprintSensorEx.Terminate(); //Y terminar la inicializacion del SDK para no tener errores inesperados :)
    }
}
