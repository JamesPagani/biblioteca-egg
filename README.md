# Biblioteca (Egg) - VERSION WEB

Como parte del proceso de aprendizaje del curso "Backend con Java", de Egg, se esta creando una aplicacion web escrita en Java con Spring y el patron Modelo-Vista-Controlador (MVC) para gestionar los libros de una bilbioteca.

## Temas abordados
### Capa de Acceso a Datos
- Creacion de las entidades del proyecto:
    - Autor
    - Editorial
    - Libro
- Creacion de los repositorios para cada entidad.
    - Estos repositorios son interfaces que extienden la interfaz de Spring de JpaRepository, permitiendo que el framework se encarge de gestionar los repositorios segun se necesiten.
    - Para la entidad Libro, se añadieron dos consultas extra escritas en JPQL (Java Persistence Query Language).

### Capa de Servicios
- Creacion de los servicios para cada entidad.
    - Operaciones basicas de Creacion, Lectura, y Actualizacion de cada tipo de entidad.

### Capa de Controladores (Parte 1)
- Creacion de los controladores para cada entidad, junto con las vistas.
    - Libros quedo parcialmente implementado. Si bien el controlador esta funcionando, la vista no permite seleccionar un los atributos mandatorios que faltan.

### Capa de Controladores (Parte 2)
- Controlador de Libros funcional.
- Añadido las paginas para listar y modificar cada entidad.
- Implementado en los servicios y controladores los metodos necesarios para listar todas las entidades y modificarlas.

### Spring Security: Roles y Registro de Usuario
- Entidad, Repositorio, y Controlador de Usuario implementados.
- Enumerador para Roles añadido.
- Funcionalidad de Registro de usuarios creado.

### Spring Security: Login y restricciones de accesos
- Funcionalidad de inicio de sesion implementado.
- Autenticacion para cada pagina implementada
- Separacion de informacion para Usuarios y Administradores (no implementado en el front-end)

### Sesión - HttpSession
- Implementacion de la clase HttpSession para mandar informacion del usuario al frontend.
- Validacion de seguridad en el frontend con la integracion de Spring Security para Thymeleaf, permitiendo el uso de las propiedades `sec:authorize`.
- Los admin ahora tienen acceso a un listado de los usuarios y el poder de modificar sus nombres y roles.
    - Se ha creado una vista para la funcion respectiva.

## Informacion tecnica
- Version de Java: 17
- Gestor de software/builds: Maven
- Editor de Texto/IDE usado: VSCode
- Sistema de Gestion de Base de Datos: MySQL 8.0.4
