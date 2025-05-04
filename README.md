# FastEat Backend API

API REST desarrollada con Spring Boot para la aplicación FastEat, un sistema de gestión de pedidos de comida a domicilio.

## Tecnologías Utilizadas

* Java 17
* Spring Boot 3.2.3
* PostgreSQL (Supabase)
* Maven
* JPA/Hibernate
* JUnit 5

## Requisitos Previos

* Java JDK 17 o superior
* Maven 3.6 o superior
* PostgreSQL
* IDE (recomendado: VS Code con extensiones de Java)

## Configuración del Entorno

1. Clona el repositorio:
```bash
git clone https://github.com/MarcosExp/fasteat-backend.git
cd fasteat-backend
```

2. Configura las variables de entorno:
   Crea un archivo `setenv.bat` en la raíz del proyecto:
```batch
@echo off
set DB_URL=your_supabase_url
set DB_USERNAME=your_username
set DB_PASSWORD=your_password
```

3. Ejecuta el archivo de variables de entorno:
```bash
.\setenv.bat
```

## Ejecutar la Aplicación

1. Compila el proyecto:
```bash
mvn clean install
```

2. Ejecuta la aplicación:
```bash
mvn spring-boot:run
```

La API estará disponible en `http://localhost:8080`

## Endpoints Principales

* `GET /usuarios` - Obtener todos los usuarios
* `GET /usuarios/{id}` - Obtener usuario por ID
* `POST /usuarios` - Crear nuevo usuario
* `PUT /usuarios/{id}` - Actualizar usuario
* `DELETE /usuarios/{id}` - Eliminar usuario

## Tests

* Ejecutar tests unitarios:
```bash
mvn test
```

* Ejecutar tests de integración:
```bash
mvn verify
```

## Estructura del Proyecto

```
backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/fasteat/fasteat_api/
│   │   │       ├── controller/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       └── FasteatApiApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/fasteat/fasteat_api/
├── .gitignore
├── pom.xml
└── README.md
```

## Contribuir

1. Fork el repositorio
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request


