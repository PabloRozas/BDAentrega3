package bdabackend.bda.Service;

import bdabackend.bda.Entity.RankingEntity;
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

import java.util.ArrayList;
import java.util.Arrays;
 

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.AggregateIterable;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import com.mongodb.client.MongoCollection;



@Service
public class VoluntarioService {
    @Autowired
    private VoluntarioRepository voluntarioRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoCollection<Document> tareaCollection;

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

    public void actualizarEquipamiento(String id, String nuevoEquipamiento) {
        VoluntarioEntity voluntarioExistente = voluntarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voluntario no encontrado con id: " + id));

        // Actualizar el campo equipamiento del voluntario existente
        voluntarioExistente.setEquipamiento(nuevoEquipamiento);

        // Guardar los cambios
        voluntarioRepository.save(voluntarioExistente);
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

      // Buscar voluntarios por tarea con agreggate y lookup

    /**
     * Obtiene todos los voluntarios registrados en una tarea específica.
     *
     * @param tareaId El ID de la tarea.
     * @return Lista de voluntarios registrados en la tarea.
     */

     public List<VoluntarioEntity> getVoluntariosByTarea(String tareaId) {
        List<VoluntarioEntity> voluntarios = new ArrayList<>();

        // Convertir tareaId a ObjectId
        ObjectId tareaObjectId = new ObjectId(tareaId);

        // Crear pipeline de agregación

        // 1. $match
        List<Document> matchStage = Arrays.asList(
            new Document("$match", new Document("_id", tareaObjectId))  // Buscar la tarea por _id
        );

        // 2. $addFields para convertir _id a String
        List<Document> addFieldsStage = Arrays.asList(
            new Document("$addFields", new Document("idTareaStr", new Document("$toString", "$_id")))
        );

        // 3. $lookup en ranking usando idTareaStr
        List<Document> lookupRankingStage = Arrays.asList(
            new Document("$lookup", new Document()
                .append("from", "ranking")  // Unir con la colección ranking
                .append("localField", "idTareaStr")  // Campo convertido en la colección de tareas
                .append("foreignField", "idTarea")  // Campo en la colección de rankings
                .append("as", "rankingDetalles"))
        );

        // 4. $addFields para convertir idVoluntario a ObjectId dentro del array
        List<Document> addFieldsVoluntarioStage = Arrays.asList(
            new Document("$addFields", new Document("rankingDetalles", 
                new Document("$map", new Document()
                    .append("input", "$rankingDetalles")
                    .append("as", "ranking")
                    .append("in", new Document()
                        .append("idVoluntarioObj", new Document("$toObjectId", "$$ranking.idVoluntario"))
                        .append("idTarea", "$$ranking.idTarea")
                        .append("nivel", "$$ranking.nivel")
                        .append("nombreTarea", "$$ranking.nombreTarea")
                        .append("nombreVoluntario", "$$ranking.nombreVoluntario")
                        .append("numeroDocumentoVoluntario", "$$ranking.numeroDocumentoVoluntario")
                    ))
            ))
        );

        // 5. $lookup en voluntario usando el array de idVoluntarioObj
        List<Document> lookupVoluntarioStage = Arrays.asList(
            new Document("$lookup", new Document()
                .append("from", "voluntario")  // Unir con la colección voluntario
                .append("localField", "rankingDetalles.idVoluntarioObj")  // Campo idVoluntarioObj en ranking
                .append("foreignField", "_id")  // Campo id en la colección de voluntarios
                .append("as", "voluntarioDetalles"))
        );

        // 6. Mantener los arrays y procesarlos en Java
        AggregateIterable<Document> results = tareaCollection.aggregate(Arrays.asList(
            matchStage.get(0),
            addFieldsStage.get(0),
            lookupRankingStage.get(0),
            addFieldsVoluntarioStage.get(0),
            lookupVoluntarioStage.get(0)
        ));

        // Mapear los resultados a una lista de voluntarios
        for (Document doc : results) {
            List<Document> voluntarioDetalles = doc.getList("voluntarioDetalles", Document.class);
            for (Document voluntarioDoc : voluntarioDetalles) {
                VoluntarioEntity voluntario = convertDocumentToVoluntario(voluntarioDoc);
                voluntarios.add(voluntario);
            }
        }

        return voluntarios;
    }

    private void printStageResult(String message, List<Document>... stages) {
        List<Document> pipeline = new ArrayList<>();
        for (List<Document> stage : stages) {
            pipeline.addAll(stage);
        }
        AggregateIterable<Document> results = tareaCollection.aggregate(pipeline);
        
        System.out.println(message);
        boolean hasResults = false;
        for (Document doc : results) {
            hasResults = true;
            System.out.println(doc.toJson());
        }
        if (!hasResults) {
            System.out.println("No se encontraron resultados para la consulta.");
        }
    }

    private VoluntarioEntity convertDocumentToVoluntario(Document doc) {
        VoluntarioEntity voluntario = new VoluntarioEntity();
    
        // Manejo del campo _id
        Object id = doc.get("_id");
        if (id instanceof ObjectId) {
            voluntario.setId(((ObjectId) id).toString());
        } else if (id instanceof String) {
            voluntario.setId((String) id);
        }
    
        // Asignar otros campos
        voluntario.setNombre(doc.getString("nombre"));
        voluntario.setCorreo(doc.getString("correo"));
        voluntario.setNumeroDocumento(doc.getString("numeroDocumento"));
    
        // Convertir el campo "zonaVivienda" a GeoJsonPoint
        Document zonaViviendaDoc = (Document) doc.get("zonaVivienda");
        if (zonaViviendaDoc != null) {
            double[] coordinates = zonaViviendaDoc.getList("coordinates", Double.class).stream().mapToDouble(Double::doubleValue).toArray();
            GeoJsonPoint zonaVivienda = new GeoJsonPoint(coordinates[0], coordinates[1]);
            voluntario.setZonaVivienda(zonaVivienda);
        }
    
        voluntario.setContrasena(doc.getString("contrasena"));
        voluntario.setEquipamiento(doc.getString("equipamiento"));
        voluntario.setRanking(null); // Para implementar
        voluntario.setVoluntarioHabilidad(null); // Para implementar
    
        return voluntario;
    }




    

}
