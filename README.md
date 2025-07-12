# 📚 Literalura CLI

---

**Literalura CLI** es una aplicación de línea de comandos desarrollada en **Java** con **Spring Shell**, que permite explorar libros del [Proyecto Gutenberg](https://gutendex.com) a través de su API pública (**Gutendex**). Fue desarrollada como parte del *Challenge Conversor de Monedas* de **Alura Latam**.

Esta herramienta te permite interactuar con la colección de libros clásicos más grande de dominio público desde la terminal de forma intuitiva, rápida y sin necesidad de una interfaz gráfica.

---

## 📋 Instrucciones de uso

### 🛠️ Requisitos previos

- **Java 23** (JDK)
- **Maven** (versión 3.6 o superior)
- **PostgreSQL** (local o en la nube)
- (Opcional) Herramienta para conectarte a la base de datos, como `psql`, **pgAdmin**, o **TablePlus**.

---

### 🔧 Configuración de la base de datos

1. **Local**:

    - Instala PostgreSQL y crea una base de datos, por ejemplo:
      ```bash
      createdb literalura
      ```
    - Asegúrate de tener usuario y contraseña válidos (por defecto: `postgres` / `root`, según configuraciones iniciales).

2. **En la nube**:

    - Si usas una instancia en un servicio como ElephantSQL, Railway, Heroku, Amazon RDS, etc., obtén tu cadena de conexión (`URL`), usuario y contraseña.
    - Reemplaza en el archivo `application.properties` la configuración por los valores de tu instancia remota.

---

### ⚙️ Configuración en `application.properties`

Ubicado en `src/main/resources`, incluye las siguientes propiedades:

```properties
# Conexión a PostgreSQL
spring.datasource.url=jdbc:postgresql://<HOST>:<PUERTO>/<DATABASE>
spring.datasource.username=<USUARIO>
spring.datasource.password=<PASSWORD>
spring.datasource.driver-class-name=org.postgresql.Driver

# Habilita Open‑In‑View (ajústalo según tu conveniencia)
spring.jpa.open-in-view=true
```

- Para **local**, podrías usar:
  ```properties
  spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
  spring.datasource.username=postgres
  spring.datasource.password=root
  ```
- Para **nube**, adapta `<HOST>`, `<PUERTO>`, `<DATABASE>`, `<USUARIO>` y `<PASSWORD>` según se te haya proporcionado en tu proveedor.

---

### 🧱 Compilar y ejecutar

Desde la raíz del proyecto:

```bash
# Compilar y descargar dependencias
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run
```

Asegúrate de que la base de datos esté funcionando antes de iniciar la aplicación.

---

### 🎮 Uso de Literalura CLI

Una vez en ejecución, usa el menú interactivo:

| Opcion | Descripción                               | Ejemplo                      |
| ------ | ----------------------------------------- | ---------------------------- |
| 1      | Buscar libro por título (API Gutendex)    | `menu 1 Orgullo y prejuicio` |
| 2      | Listar libros registrados localmente      | `menu 2`                     |
| 3      | Listar autores registrados                | `menu 3`                     |
| 4      | Buscar autores vivos en un año específico | `menu 4 1900`                |
| 5      | Filtrar libros por idioma                 | `menu 5 en`                  |
| 6      | Listar libros por autor                   | `menu 6 Jane Austen`         |
| 7      | Ver top 10 libros por descargas           | `menu 7`                     |
| 8      | Consultar estadísticas locales            | `menu 8`                     |
| 9      | Salir de la aplicación                    | `menu 9`                     |

---

### 🤐 Personalización y tips

- Si necesitas cambiar la base de datos (por ejemplo migrar de local a nube), solo edita las propiedades `spring.datasource.*`.
- Si tienes configuraciones más avanzadas (p. ej., SSL, esquemas, pool de conexiones), puedes añadirlas en `application.properties` o un perfil adicional:
  ```properties
  spring.datasource.hikari.maximum-pool-size=10
  spring.datasource.hikari.connection-timeout=20000
  spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
  ```
- Usa Maven profiles para soportar diferentes entornos (dev/prod) con distintos archivos `.properties`.

---

### ✅ Verifica tu entorno

- Comprueba que Java 23 está instalado:
  ```bash
  java --version
  ```
- Verifica Maven:
  ```bash
  mvn -version
  ```
- Asegúrate de que tu base de datos esté accesible:
  ```bash
  psql -h <HOST> -U <USUARIO> -d <DATABASE>
  ```
