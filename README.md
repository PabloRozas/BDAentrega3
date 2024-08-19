# BDAentrega3

## Integrantes

- Pablo Macuada Rozas
- Nicolás Muñoz Reyes
- Nicolás Saavedra Charlin
- Nicolás Salinas Rodrigues
- Macarena Soto Herrera

## Antes de desplegar

1. Tener instalado Node.js
2. Tener instalado Java 17
3. Tener instalado una base de datos PostgreSQL con extensión PostGIS
4. Tener intalado MongoDB con alguna base de datos
5. Configurar las credenciales en el archivo `Backend\src\main\resources\application.properties` para la base de datos PostgreSQL y para la base de datos MongoDB

## Para desplegar Frontend

1. Clonar el repositorio
2. Entrar a la carpeta frontend
3. Ejecutar el comando `npm install`
4. Ejecutar el comando `npm run dev` para levantar en un ambiente de desarrollo

## Para desplegar Backend

1. Clonar el repositorio
2. Entrar a la carpeta backend
3. Para ejecutar se tienen dos opciones:
   - En caso de tener las extenciones de java y springboot, se puede ir al archivo de la aplicación en la ruta `Backend\src\main\java\bdabackend\bda\BackendApplication.java` y ejecutarlo con el boton run sobre el metodo main
   - Ejecutar desde la consola accediendo a la carpeta backend y ejecutar el comando `.\mvnw run`.
