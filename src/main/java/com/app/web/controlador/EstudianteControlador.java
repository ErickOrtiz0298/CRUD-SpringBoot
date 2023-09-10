package com.app.web.controlador;

import com.app.web.entidad.Estudiante;
import com.app.web.servicio.EstudianteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EstudianteControlador {
    @Autowired
    private EstudianteServicio servicio;

    /**
     * Este endpoint es donde se muestran los estudiantes y se tienen los botones:
     * Agregar Estudiante: llama al @GetMapping("/estudiantes/nuevo")
     * Editar: llama al @GetMapping("/estudiantes/editar/{id}")
     * Eliminar: llama al @GetMapping("/estudiantes/{id}")
     * @param modelo sirve para ser enviado al return (el modelo se envia al html)
     * @return nos muestra el html estudiantes y se muestra la lista de estudiantes.
     */
    @GetMapping({"/estudiantes","/"})
    public String listarEstudiantes(Model modelo){
        modelo.addAttribute("estudiantes",servicio.listarTodosLosEstudiantes());
        return "estudiantes"; //nos retorna al archivo estudiantes
    }

    /**
     * Este endpoint se ejecuta al presionar el boton agregar estudiante.
     * @param modelo
     * @return nos lleva al html "crear_estudiante"
     * El html tiene un formulario con lo siguiente:
     *  //<form th:action="@{/estudiantes}" th:object="${estudiante}" method="POST">
     * Se enviará un objeto estudiante con el metodo Post al endPoint localhost8080/estudiantes
     */
     @GetMapping("/estudiantes/nuevo")
    public String mostrarFormularioDeRegistrarEstudiante(Model modelo){
         Estudiante estudiante = new Estudiante();
         modelo.addAttribute("estudiante",estudiante);
         return "crear_estudiante";
     }

    /**
     * Este endpoint se ejecuta desde el html crear_estudiante al presionar el botón guardar.
     * *  //<form th:action="@{/estudiantes}" th:object="${estudiante}" method="POST">
     * @param estudiante proviene del html
     * @return nos regresa a la pagina principal localhost8080/estudiantes
     * y se visualiza el estudiante recien creado.
     */
     @PostMapping("/estudiantes")
    public String guardarEstudiante(@ModelAttribute("estudiante")Estudiante estudiante){
        servicio.guardarEstudiante(estudiante);
        return "redirect:/estudiantes";
     }

    /**
     * Este metodo se ejecuta desde estudiantes.html al seleccionar el boton editar.
     * @param id se obtiene desde estudiantes.html
     * @param modelo se envia a la pagina editar_estudiante
     * @return nos muestra la pagina editar_estudiante donde se captura la informacion del estudiante.
     * <form th:action="@{/estudiantes/{id}(id=${estudiante.id})}" th:object="${estudiante}" method="POST">
     * Se enviará un objeto estudiante con el metodo Post al endPoint localhost8080/estudiantes/id
     */

     @GetMapping("/estudiantes/editar/{id}")
    public String mostrarFormularioDeEditar(@PathVariable Long id, Model modelo){
        modelo.addAttribute("estudiante",servicio.obtenerEstudiantePorId(id));
        return "editar_estudiante";
     }

    /**
     * Este metodo se ejecuta desde editar_estudiante al presionar el boton guardar
     * La linea de abajo del html nos indica el endpoint al cual llegara la informacion,
     * el objeto que se paso desde editar_estudiante y el tipo de metodo Post.
     * <form th:action="@{/estudiantes/{id}(id=${estudiante.id})}" th:object="${estudiante}" method="POST">
     * @param id
     * @param estudiante
     * @return la pagina donde se captura la informacion del estudiante y se presenta
     */

     @PostMapping("/estudiantes/{id}")
    public String actualizarEstudiante(@PathVariable Long id,@ModelAttribute("estudiante")Estudiante estudiante){
        Estudiante estudianteExistente = servicio.obtenerEstudiantePorId(id);
        estudianteExistente.setId(id);
        estudianteExistente.setNombre(estudiante.getNombre());
        estudianteExistente.setApellido(estudiante.getApellido());
        estudianteExistente.setEmail(estudiante.getEmail());
        servicio.actualizarEstudiante(estudianteExistente);
        return "redirect:/estudiantes";
     }

    /**
     * Este metodo se ejecuta desde estudiantes.html al seleccionar el boton eliminar.
     * <a th:href="@{/estudiantes/{id}(id=${estudiante.id})}" class="btn btn-danger">Eliminar</a>
     * El endpoint que se manda a llamar es localhost:8080/estudiantes/id
     * @param id se obtiene desde estudiantes.html
     * @return actualiza la pagina y vuelve a mostrar la lista de estudiantes.
     */
     @GetMapping("/estudiantes/{id}")
    public String eliminarEstudiante(@PathVariable Long id){
        servicio.eliminarEstudiante(id);
        return "redirect:/estudiantes";
     }

}
