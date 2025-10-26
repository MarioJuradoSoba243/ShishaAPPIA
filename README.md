# 🚀 Aplicación Web Full Stack (Java + Vue.js + SQLite)

Este proyecto es una aplicación web compuesta por un **backend en Java (Spring Boot)** con **base de datos embebida SQLite** y un **frontend en Vue.js**, ambos gestionados mediante **Maven**.  
Permite ejecutar una aplicación completa **sin depender de ningún servidor externo de base de datos**.

---

## 🏗️ Estructura del Proyecto

```
/project-root
│
├── backend/                # Backend Java (Spring Boot + SQLite)
│   ├── src/main/java/...   # Código fuente del backend
│   ├── src/main/resources/ # Configuración (application.yml, schema.sql, data.sql)
│   └── pom.xml             # Módulo Maven del backend
│
├── frontend/               # Frontend Vue.js
│   ├── src/                # Código fuente del frontend
│   ├── public/             # Archivos estáticos
│   ├── package.json        # Dependencias
│   └── vue.config.js       # Configuración de proxy hacia backend
│
└── pom.xml                 # POM raíz del proyecto Maven
```

---

## ⚙️ Tecnologías

### Backend
- **Java 17+**
- **Spring Boot 3.x**
- **Spring Web**
- **Spring Data JPA**
- **SQLite** (embebido)
- **Hibernate**
- **Lombok**
- **Maven**

### Frontend
- **Vue 3 + Vite o Vue CLI**
- **Vue Router**
- **Axios**
- **Pinia o Vuex**
- **TailwindCSS o Bootstrap** (opcional)

---

## 🧩 Configuración del Backend con SQLite

### 1️⃣ Dependencias Maven (`backend/pom.xml`)

```xml
<dependencies>
    <!-- Spring Boot Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- SQLite -->
    <dependency>
        <groupId>org.xerial</groupId>
        <artifactId>sqlite-jdbc</artifactId>
        <version>3.45.3.0</version>
    </dependency>

    <!-- Lombok (opcional) -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>

    <!-- Test -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

### 2️⃣ Configuración en `application.yml`

```yaml
spring:
  datasource:
    url: jdbc:sqlite:database/app.db
    driver-class-name: org.sqlite.JDBC
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    database-platform: org.hibernate.dialect.SQLiteDialect
```

📁 El archivo `app.db` se genera automáticamente en la carpeta `backend/database/`.

---

### 3️⃣ Ejemplo de Entidad y Repositorio

```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
}
```

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {}
```

---

## 🎨 Frontend Vue

### 1️⃣ Instalación
```bash
cd frontend
npm install
```

### 2️⃣ Servidor de desarrollo
```bash
npm run serve
```

### 3️⃣ Proxy a backend
En `vue.config.js`:
```js
module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
  },
};
```

---

## 🔧 Compilación unificada con Maven

`pom.xml` raíz:
```xml
<project>
  <modules>
    <module>backend</module>
    <module>frontend</module>
  </modules>
</project>
```

Plugin para empaquetar frontend dentro del backend:
```xml
<plugin>
  <groupId>com.github.eirslett</groupId>
  <artifactId>frontend-maven-plugin</artifactId>
  <version>1.12.1</version>
  <executions>
    <execution>
      <id>npm-install</id>
      <goals><goal>install-node-and-npm</goal></goals>
    </execution>
    <execution>
      <id>npm-build</id>
      <goals><goal>npm</goal></goals>
      <configuration>
        <arguments>run build</arguments>
      </configuration>
    </execution>
  </executions>
</plugin>
```

---

## 🚀 Ejecución Completa

### 🔹 Desarrollo
En dos terminales:
```bash
# Backend
cd backend
mvn spring-boot:run

# Frontend
cd frontend
npm run serve
```

### 🔹 Producción
```bash
mvn clean package
java -jar backend/target/backend-0.0.1-SNAPSHOT.jar
```

👉 Aplicación disponible en:  
`http://localhost:8080`

---

## 💾 Base de Datos

- El archivo `app.db` se crea automáticamente al iniciar el backend.
- No requiere instalación ni configuración externa.
- Se puede incluir en el `.gitignore` si se desea evitar su versionado.

---

## 🧠 Notas

- Ideal para entornos locales o demos.
- En producción se puede migrar fácilmente a PostgreSQL o MySQL.
- Compatible con Docker.

---

## 📄 Licencia

Este proyecto está bajo la licencia **MIT**.  
Hecho con ❤️ usando **Java**, **Vue**, **SQLite** y **Maven**.
