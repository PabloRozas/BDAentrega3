package bdabackend.bda.Controller;

import bdabackend.bda.Entity.MongoTareaEntity;
import bdabackend.bda.Service.MongoTareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mongoTarea")
public class MongoTareaController {
    @Autowired
    private MongoTareaService mongoTareaService;

    // Endpoint para obtener todas las tareas
    @GetMapping
    public List<MongoTareaEntity> getAllTareas() {
        return mongoTareaService.findAll();
    }

    // Endpoint para obtener una tarea por ID
    @GetMapping("/{id}")
    public ResponseEntity<MongoTareaEntity> getTareaById(@PathVariable String id) {
        Optional<MongoTareaEntity> tarea = mongoTareaService.findById(id);
        return tarea.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para guardar una nueva tarea
    @PostMapping
    public ResponseEntity<MongoTareaEntity> createTarea(@RequestBody MongoTareaEntity tarea) {
        MongoTareaEntity savedTarea = mongoTareaService.save(tarea);
        return ResponseEntity.ok(savedTarea);
    }

    // Endpoint para actualizar una tarea existente
    @PutMapping("/{id}")
    public ResponseEntity<MongoTareaEntity> updateTarea(@PathVariable String id, @RequestBody MongoTareaEntity tareaDetails) {
        Optional<MongoTareaEntity> optionalTarea = mongoTareaService.findById(id);
        if (optionalTarea.isPresent()) {
            MongoTareaEntity tarea = optionalTarea.get();
            tarea.setNombre(tareaDetails.getNombre());
            tarea.setDescripcion(tareaDetails.getDescripcion());
            tarea.setTipo(tareaDetails.getTipo());
            tarea.setZona(tareaDetails.getZona());
            MongoTareaEntity updatedTarea = mongoTareaService.save(tarea);
            return ResponseEntity.ok(updatedTarea);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para eliminar una tarea por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarea(@PathVariable String id) {
        mongoTareaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
