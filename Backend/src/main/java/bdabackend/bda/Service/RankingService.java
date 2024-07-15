package bdabackend.bda.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bdabackend.bda.Entity.RankingEntity;
import bdabackend.bda.Repository.RankingRepository;
import java.io.UnsupportedEncodingException;

import java.util.List;

@Service
public class RankingService {
    @Autowired
    private RankingRepository rankingRepository;

    // CREAR

    /**
     * Inserta un nuevo ranking en la base de datos.
     * 
     * @param nivel                     El nivel del voluntario.
     * @param tareaRanking              La tarea del voluntario.
     * @param nombreVoluntario          El nombre del voluntario.
     * @param numeroDocumentoVoluntario El número de documento del voluntario.
     * @param idTarea                   El id de la tarea.
     * @param idVoluntario              El id del voluntario.
     * 
     */
    public void insertarRanking(int nivel, String tareaRanking, String nombreVoluntario,
            String numeroDocumentoVoluntario, String idTarea, String idVoluntario) {

        RankingEntity ranking = new RankingEntity(nivel, tareaRanking, nombreVoluntario, numeroDocumentoVoluntario,
                idTarea, idVoluntario);
        rankingRepository.insert(ranking);
    }

    // LEER

    /**
     * Obtiene una lista con todos los rankings registrados en la base de datos.
     * 
     * @return Una lista con todos los rankings registrados en la base de datos.
     */
    public List<RankingEntity> listaRanking() {
        return rankingRepository.findAll();
    }

    /**
     * Busca un ranking en la base de datos por su id.
     * 
     * @param id El id del ranking a buscar.
     * @return Un RankingEntity con los datos del ranking encontrado.
     */
    public RankingEntity buscarRankingPorId(String id) {
        return rankingRepository.findByid(id);
    }

    // DELETE

    /**
     * Elimina un ranking de la base de datos por su id.
     * 
     * @param id El id del ranking a eliminar.
     */
    public void eliminarRankingPorId(String id) {
        rankingRepository.deleteById(id);
    }

    // ! REVISAR, SI SE USA O NO SE USA
    // public List<?> emergenciaZona(Long id) {
    // return rankingRepository.sacarZonaEmergencia(id);
    // }

    // public List<?> voluntarioZona(String id) {
    // return rankingRepository.sacarZonaVoluntario(id);
    // }

    // public double distanciaEntrePuntos(double latitudPunto1, double
    // longitudPunto1, double latitudPunto2,
    // double longitudPunto2) {
    // // Radio de la Tierra en metros

    // final double radioTierra = 6371000;

    // // Convertir las coordenadas de grados a radianes
    // double latitudPunto1Rad = Math.toRadians(latitudPunto1);
    // double longitudPunto1Rad = Math.toRadians(longitudPunto1);
    // double latitudPunto2Rad = Math.toRadians(latitudPunto2);
    // double longitudPunto2Rad = Math.toRadians(longitudPunto2);

    // // Calcular la diferencia entre las coordenadas
    // double diferenciaLatitud = latitudPunto2Rad - latitudPunto1Rad;
    // double diferenciaLongitud = longitudPunto2Rad - longitudPunto1Rad;

    // // Calcular la distancia utilizando la fórmula del haversine
    // double a = Math.pow(Math.sin(diferenciaLatitud / 2), 2) +
    // Math.cos(latitudPunto1Rad) * Math.cos(latitudPunto2Rad) *
    // Math.pow(Math.sin(diferenciaLongitud / 2), 2);
    // double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    // double distancia = radioTierra * c;

    // return distancia;
    // }

    public String bytesToString(byte[] bytes) {
        try {
            return new String(bytes, "UTF-8"); // Convertir los bytes a una cadena usando el charset especificado
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    // public double[] wkbToLatLong(byte[] wkbBytes) {
    // ByteBuffer buffer = ByteBuffer.wrap(wkbBytes);
    // buffer.order(ByteOrder.LITTLE_ENDIAN); // Orden de bytes para interpretar
    // como números de punto flotante
    // buffer.position(9); // Saltar los primeros nueve bytes (tipo de geometría y
    // orden de bytes)
    // double longitude = buffer.getDouble(); // Coordenada X (longitud)
    // double latitude = buffer.getDouble(); // Coordenada Y (latitud)
    // return new double[] { longitude, latitude };
    // }

    // public byte[] hexStringToByteArray(String hexString) {
    // int len = hexString.length();
    // byte[] data = new byte[len / 2];
    // for (int i = 0; i < len; i += 2) {
    // data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
    // + Character.digit(hexString.charAt(i + 1), 16));
    // }
    // return data;
    // }

    // public int puntajeRanking(Double distancia, String idVoluntario) {
    // int contador = 0;
    // String equipo = voluntarioService.eqipamientov(idVoluntario);
    // String[] elementos = equipo.split("\s,\s");
    // for (String elemento : elementos) {
    // List<String> resultadoFuncion = rankingRepository.matchEquipo(elemento);
    // if (!resultadoFuncion.isEmpty()) {
    // contador = contador + 1;
    // }
    // }
    // contador = contador + rankingRepository.matchHabilidad(idVoluntario);

    // if (distancia >= 0.0 && distancia <= 15000.0) {
    // contador = contador + 3;
    // } else if (distancia > 15000.0 && distancia <= 40000.0) {
    // contador = contador + 2;
    // } else if (distancia > 40000.0 && distancia <= 60000.0) {
    // contador = contador + 1;
    // }
    // return contador;
    // }
}
