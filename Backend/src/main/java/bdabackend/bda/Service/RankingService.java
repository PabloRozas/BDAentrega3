package bdabackend.bda.Service;

import bdabackend.bda.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bdabackend.bda.Repository.RankingRepository;
import bdabackend.bda.Utils.GeoUtils;

import java.io.UnsupportedEncodingException;

import java.util.List;
import java.util.Objects;

@Service
public class RankingService {
    @Autowired
    private RankingRepository rankingRepository;

    @Autowired
    private VoluntarioService voluntarioService;

    @Autowired
    private VoluntarioHabilidadService voluntarioHabilidadService;

    @Autowired
    private EmergenciaHabilidadSevice emergenciaHabilidadSevice;

    @Autowired
    private TareaService tareaService;

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
    public void insertarRanking(Double nivel, String tareaRanking, String nombreVoluntario,
            String numeroDocumentoVoluntario, String idTarea, String idVoluntario) {

        RankingEntity ranking = new RankingEntity(nivel, tareaRanking, nombreVoluntario, numeroDocumentoVoluntario,
                idTarea, idVoluntario);
        rankingRepository.insert(ranking);
    }

    /**
     * Crear un ranking cuando se ingresa un voluntario
     * 
     * @param idVoluntario
     * 
     */
    public void crearRankingVoluntario(String idVoluntario) {
        // Se buscan todas las tareas
        List<TareaEntity> tareas = tareaService.listaTarea();

        // Se busca el voluntario
        VoluntarioEntity voluntario = voluntarioService.buscarVoluntarioPorId(idVoluntario);

        // Se busca la voluntariohabilidad
        List<VoluntarioHabilidadEntity> voluntarioHabilidades = voluntarioHabilidadService.listaVoluntarioHabilidad();

        // Se busca la emergenciahabilidad
        List<EmergenciaHabilidadEntity> emergenciaHabilidades = emergenciaHabilidadSevice.listaEmergenciaHabilidad();


        // Se bucan todos los rankings
        List<RankingEntity> rankings = listaRanking();
        // Total de rankings
        /*
        int totalRankings = rankings.size();

        if (totalRankings == 0){
            totalRankings = 1;
        }
         */

        // Si no hay tareas, no se crea el ranking
        if (tareas.isEmpty()) {
            return;
        }

        // Se recorren todas las tareas
        for (TareaEntity tarea : tareas) {
            Double puntaje = 0.0;
            Double puntaje_2 = 0.0;
            Double puntaje_3 = 0.0;
            // Por cada una de las tareas se crea un ranking según la distancia entre el
            // voluntario y la tarea
            double distancia = GeoUtils.calcularDistancia(voluntario.getZonaVivienda(), tarea.getZona());
            System.out.println("Distancia: " + distancia);
            // Si la distancia es menor o igual a 20 km el ranking al puntaje se le suma 2
            if (distancia >= 0.0 && distancia <= 15000.0) {
                puntaje += 3;
            } else if (distancia > 15000.0 && distancia <= 40000.0) {
                puntaje += 2;
            } else if (distancia > 40000.0 && distancia <= 60000.0) {
                puntaje += 1;
            }
            // Se le agrega el peso elegido por todos
            puntaje *= 0.6;

            // Si el voluntario tiene el equipamiento necesario para la tarea, al puntaje se
            // le suma 2

            String equipo = voluntario.getEquipamiento();
            String[] elementos = equipo.split(",");
            String equipamientoTarea = tarea.getRequerimientos();
            String[] elementosTarea = equipamientoTarea.split(",");
            for (String elemento : elementos) {
                for (String elementoTarea : elementosTarea) {
                    if (elemento.contains(elementoTarea)) {
                        puntaje_2 += 1;
                    }
                }
            }
            // Se le agrega el peso elegido por todos
            puntaje_2 *= 0.1;

            String id = voluntario.getId();
            for (VoluntarioHabilidadEntity voluntarioHabilidad : voluntarioHabilidades){
                String idvoluntario = voluntarioHabilidad.getVoluntario();
                if (Objects.equals(id, idvoluntario)){
                    Long idhabilidad = voluntarioHabilidad.getHabilidad().getId();
                    for (EmergenciaHabilidadEntity emergenciaHabilidad : emergenciaHabilidades){
                        Long idhabilidadcomparar = emergenciaHabilidad.getHabilidad().getId();
                        if (Objects.equals(idhabilidad, idhabilidadcomparar)){
                            Long idemergencia = emergenciaHabilidad.getEmergencia().getId();
                            Long idemergenciacompara = tarea.getEmergencia();
                            if (Objects.equals(idemergencia, idemergenciacompara)){
                                puntaje_3 += 1;
                                break;
                            }
                        }
                    }
                }
            }
            // Se le agrega el peso elegido por todos
            puntaje_3 *= 0.3;

            // Se calcula el nivel del voluntario
            Double nivel = (puntaje + puntaje_2 + puntaje_3) ;

            // Se crea el ranking
            insertarRanking(nivel, tarea.getNombre(), voluntario.getNombre(), voluntario.getNumeroDocumento(),
                    tarea.getId(), voluntario.getId());
        }
    }

    /**
     * Crear un ranking cuando se ingresa una tarea
     * 
     * @param idTarea
     */
    public void crearRankingTarea(String idTarea) {
        // Se buscan todos los voluntarios
        List<VoluntarioEntity> voluntarios = voluntarioService.listaVoluntario();

        // Se busca la tarea
        TareaEntity tarea = tareaService.buscarTareaPorId(idTarea);

        // Se busca la voluntariohabilidad
        List<VoluntarioHabilidadEntity> voluntarioHabilidades = voluntarioHabilidadService.listaVoluntarioHabilidad();

        // Se busca la emergenciahabilidad
        List<EmergenciaHabilidadEntity> emergenciaHabilidades = emergenciaHabilidadSevice.listaEmergenciaHabilidad();

        // Se bucan todos los rankings
        List<RankingEntity> rankings = listaRanking();
        // Total de rankings
        /*
        int totalRankings = rankings.size();
        if (totalRankings == 0){
            totalRankings = 1;
        }
         */

        // Si no hay voluntarios, no se crea el ranking
        if (voluntarios.isEmpty()) {
            return;
        }

        // Se recorren todos los voluntarios
        for (VoluntarioEntity voluntario : voluntarios) {
            Double puntaje = 0.0;
            Double puntaje_2 = 0.0;
            Double puntaje_3 = 0.0;
            // Por cada uno de los voluntarios se crea un ranking según la distancia entre
            // el
            // voluntario y la tarea
            double distancia = GeoUtils.calcularDistancia(voluntario.getZonaVivienda(), tarea.getZona());
            System.out.println("Distancia: " + distancia);
            // Si la distancia es menor o igual a 20 km el ranking al puntaje se le suma 2
            if (distancia >= 0.0 && distancia <= 15000.0) {
                puntaje += 3;
            } else if (distancia > 15000.0 && distancia <= 40000.0) {
                puntaje += 2;
            } else if (distancia > 40000.0 && distancia <= 60000.0) {
                puntaje += 1;
            }
            // Se le agrega el peso elegido por todos
            puntaje *= 0.6;
            // Si el voluntario tiene el equipamiento necesario para la tarea, al puntaje se
            // le suma 2

            String equipo = voluntario.getEquipamiento();
            String[] elementos = equipo.split(",");
            String equipamientoTarea = tarea.getRequerimientos();
            String[] elementosTarea = equipamientoTarea.split(",");
            for (String elemento : elementos) {
                for (String elementoTarea : elementosTarea) {
                    if (elemento.contains(elementoTarea)) {
                        puntaje_2 += 1;
                    }
                }
            }
            // Se le agrega el peso elegido por todos
            puntaje_2 *= 0.1;

            String id = voluntario.getId();
            for (VoluntarioHabilidadEntity voluntarioHabilidad : voluntarioHabilidades){
                String idvoluntario = voluntarioHabilidad.getVoluntario();
                if (Objects.equals(id, idvoluntario)){
                    Long idhabilidad = voluntarioHabilidad.getHabilidad().getId();
                    for (EmergenciaHabilidadEntity emergenciaHabilidad : emergenciaHabilidades){
                        Long idhabilidadcomparar = emergenciaHabilidad.getHabilidad().getId();
                        if (Objects.equals(idhabilidad, idhabilidadcomparar)){
                            Long idemergencia = emergenciaHabilidad.getEmergencia().getId();
                            Long idemergenciacompara = tarea.getEmergencia();
                            if (Objects.equals(idemergencia, idemergenciacompara)){
                                puntaje_3 += 1;
                                break;
                            }
                        }
                    }
                }
            }
            // Se le agrega el peso elegido por todos
            puntaje_3 *= 0.3;

            // Se calcula el nivel del voluntario
            Double nivel = (puntaje + puntaje_2 + puntaje_3);

            // Se crea el ranking
            insertarRanking(nivel, tarea.getNombre(), voluntario.getNombre(), voluntario.getNumeroDocumento(),
                    tarea.getId(), voluntario.getId());
        }
    }


    public void modificarRankingVoluntario(String idVoluntario) {

        // Se buscan todas las tareas
        List<TareaEntity> tareas = tareaService.listaTarea();

        // Se busca el voluntario
        VoluntarioEntity voluntario = voluntarioService.buscarVoluntarioPorId(idVoluntario);

        // Se busca la voluntariohabilidad
        List<VoluntarioHabilidadEntity> voluntarioHabilidades = voluntarioHabilidadService.listaVoluntarioHabilidad();

        // Se busca la emergenciahabilidad
        List<EmergenciaHabilidadEntity> emergenciaHabilidades = emergenciaHabilidadSevice.listaEmergenciaHabilidad();


        // Se bucan todos los rankings
        List<RankingEntity> rankings = listaRanking();
        // Total de rankings
        /*
        int totalRankings = rankings.size();

        if (totalRankings == 0){
            totalRankings = 1;
        }
         */

        // Si no hay tareas, no se crea el ranking
        if (tareas.isEmpty()) {
            return;
        }

        // Se recorren todas las tareas
        for (TareaEntity tarea : tareas) {
            Double puntaje = 0.0;
            Double puntaje_2 = 0.0;
            Double puntaje_3 = 0.0;
            // Por cada una de las tareas se crea un ranking según la distancia entre el
            // voluntario y la tarea
            double distancia = GeoUtils.calcularDistancia(voluntario.getZonaVivienda(), tarea.getZona());
            System.out.println("Distancia: " + distancia);
            // Si la distancia es menor o igual a 20 km el ranking al puntaje se le suma 2
            if (distancia >= 0.0 && distancia <= 15000.0) {
                puntaje += 3;
            } else if (distancia > 15000.0 && distancia <= 40000.0) {
                puntaje += 2;
            } else if (distancia > 40000.0 && distancia <= 60000.0) {
                puntaje += 1;
            }
            // Se le agrega el peso elegido por todos
            puntaje *= 0.6;

            // Si el voluntario tiene el equipamiento necesario para la tarea, al puntaje se
            // le suma 2

            String equipo = voluntario.getEquipamiento();
            String[] elementos = equipo.split(",");
            String equipamientoTarea = tarea.getRequerimientos();
            String[] elementosTarea = equipamientoTarea.split(",");
            for (String elemento : elementos) {
                for (String elementoTarea : elementosTarea) {
                    System.out.println(elemento + "         " + elementoTarea);
                    if (elemento.contains(elementoTarea)) {
                        System.out.println(puntaje_2);
                        puntaje_2 += 1;
                    }
                }
            }
            // Se le agrega el peso elegido por todos
            puntaje_2 *= 0.1;

            String id = voluntario.getId();
            for (VoluntarioHabilidadEntity voluntarioHabilidad : voluntarioHabilidades){
                String idvoluntario = voluntarioHabilidad.getVoluntario();
                if (Objects.equals(id, idvoluntario)){
                    Long idhabilidad = voluntarioHabilidad.getHabilidad().getId();
                    for (EmergenciaHabilidadEntity emergenciaHabilidad : emergenciaHabilidades){
                        Long idhabilidadcomparar = emergenciaHabilidad.getHabilidad().getId();
                        if (Objects.equals(idhabilidad, idhabilidadcomparar)){
                            Long idemergencia = emergenciaHabilidad.getEmergencia().getId();
                            Long idemergenciacompara = tarea.getEmergencia();
                            if (Objects.equals(idemergencia, idemergenciacompara)){
                                puntaje_3 += 1;
                                break;
                            }
                        }
                    }
                }
            }
            // Se le agrega el peso elegido por todos
            puntaje_3 *= 0.3;

            // Se calcula el nivel del voluntario
            Double nivel = (puntaje + puntaje_2 + puntaje_3) ;

            String idtarea = tarea.getId();
            for (RankingEntity ranking : rankings){
                String idvoluntariocomprar = ranking.getVoluntario();
                String idtareacomparar = ranking.getTarea();
                if (Objects.equals(idvoluntariocomprar, idVoluntario) && Objects.equals(idtareacomparar, idtarea)){
                    ranking.setNivel(nivel);

                    // Guardar los cambios
                    rankingRepository.save(ranking);
                }
            }
        }
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
//