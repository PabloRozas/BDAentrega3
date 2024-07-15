package bdabackend.bda.Service;

import bdabackend.bda.Entity.TareaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import bdabackend.bda.Entity.VoluntarioEntity;
import bdabackend.bda.Repository.VoluntarioRepository;

import java.util.List;



@Service
public class VoluntarioService {
    @Autowired
    private VoluntarioRepository voluntarioRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    // CREAR

    /**
     * Inserta un nuevo voluntario en la base de datos.
     *
     * @param voluntario Un VoluntarioEntity con los datos del voluntario a
     *                   insertar.
     */
    public void insertarVoluntario(VoluntarioEntity voluntario) {
        voluntarioRepository.save(voluntario);
    }

    // LEER

    /**
     * Obtiene una lista con todos los voluntarios registrados en la base de datos.
     */
    public List<VoluntarioEntity> listaVoluntario() {
        return voluntarioRepository.findAll();
    }

    /**
     * Busca un voluntario en la base de datos por su id.
     * 
     * @param id El id del voluntario a buscar.
     * @return Un VoluntarioEntity con los datos del voluntario encontrado.
     */
    public VoluntarioEntity buscarVoluntarioPorId(String id) {
        return voluntarioRepository.findById(id).orElse(null);
    }

    /**
     * Busca un voluntario en la base de datos por su correo.
     * 
     * @param correo El correo del voluntario a buscar.
     * @return Un VoluntarioEntity con los datos del voluntario encontrado.
     */
    public VoluntarioEntity buscarPorCorreo(String correo) {
        VoluntarioEntity voluntarioData = voluntarioRepository.findByCorreo(correo);
        return voluntarioData;
    }

    /**
     * Busca un voluntario en la base de datos por su id y devuelve su nombre.
     * 
     * @param id El id del voluntario a buscar.
     * @return El nombre del voluntario encontrado.
     */
    public String nombrev(String id) {
        return voluntarioRepository.findById(id).get().getNombre();
    }

    /**
     * Busca un voluntario en la base de datos por su id y devuelve numero de
     * documento.
     * 
     * @param id El id del voluntario a buscar.
     * @return El numero de documento del voluntario encontrado.
     */
    public String numerov(String id) {
        return voluntarioRepository.findById(id).get().getNumeroDocumento();
    }

    /**
     * Busca un voluntario en la base de datos por su id y devuelve el equipamiento.
     * 
     * @param id El id del voluntario a buscar.
     * @return El equipamiento del voluntario encontrado.
     */
    public String eqipamientov(String id) {
        return voluntarioRepository.findById(id).get().getEquipamiento();
    }

    // ACTUALIZAR

    // ELIMINAR

    /**
     * Elimina a un voluntario de la base de datos según su id, solo en caso de
     * encontrarlo.
     * 
     * @param id
     */
    public void eliminarVoluntarioPorId(String id) {
        if (voluntarioRepository.existsById(id)) {
            voluntarioRepository.deleteById(id);
        } else {
            System.out.println("No se encontró el voluntario con el id: " + id);
        }
    }

    public List<TareaEntity> getTareasConVoluntarios(String idTarea) {
        // Define la operación de lookup para unir con la colección "voluntario"
        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("voluntario")
                .localField("idTarea")
                .foreignField("_id")
                .as("voluntariosDetalles");

        // Construye la agregación
        Aggregation aggregation = Aggregation.newAggregation(
                match(Criteria.where("_id").is(idTarea)),  // Filtra por el id de la tarea
                lookupOperation  // Realiza la operación de lookup con la colección "voluntario"
        );

        // Ejecuta la agregación
        AggregationResults<TareaEntity> results = mongoTemplate.aggregate(aggregation, "Tarea", TareaEntity.class);

        // Retorna los resultados mapeados
        return results.getMappedResults();
    }

    // ! REVISAR
    // @Autowired
    // private JdbcTemplate jdbcTemplate;

    // public void crearVoluntario(String nombreVoluntario, String correoVoluntario,
    // String numeroDocumentoVoluntario,
    // Double latitud, Double longitud, String contrasenaVoluntario, String
    // equipamientoVoluntario) {

    // // Convierte las coordenadas a un formato adecuado para PostgreSQL, como WKT
    // DecimalFormat df = new DecimalFormat("#.######", new
    // DecimalFormatSymbols(Locale.US));
    // String zonaViviendaWKT = String.format("POINT(%s %s)", df.format(longitud),
    // df.format(latitud));

    // // Ejecuta la consulta SQL parametrizada para insertar el nuevo voluntario
    // jdbcTemplate.update("INSERT INTO voluntario (nombre, correo, "
    // + "numero_documento, zona_vivienda, contrasena, "
    // + "equipamiento) VALUES (?, ?, ?, ST_GeomFromText(?), ?, ?)",
    // nombreVoluntario,
    // correoVoluntario, numeroDocumentoVoluntario, zonaViviendaWKT,
    // contrasenaVoluntario,
    // equipamientoVoluntario);
    // }

}
