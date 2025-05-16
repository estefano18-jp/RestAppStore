# 📱 RestAppStore

**RestAppStore** es una aplicación Android que permite **buscar**, **listar**, **registrar**, y **editar softwares**, conectándose a una REST API pública desplegada en Railway. El objetivo es simular una pequeña tienda virtual de software, donde puedes interactuar con los datos de forma remota.

---

## 🌐 API utilizada

🔗 [https://rest-api-software-production-46c8.up.railway.app/api/softwares](https://rest-api-software-production-46c8.up.railway.app/api/softwares)

Esta API devuelve una lista de softwares en formato JSON.

### 🔄 Ejemplo de respuesta:

```json
[
  {
    "id": 1,
    "nombre": "Visual Studio Code",
    "categoria": "Editor de Código",
    "precio": 0.0
  },
  {
    "id": 2,
    "nombre": "Adobe Photoshop",
    "categoria": "Diseño Gráfico",
    "precio": 19.99
  }
]
```
```
📁 Estructura del Proyecto
Clases Java:
MainActivity.java: Pantalla principal de la app.

Buscar.java: Permite buscar un software por ID.

Listar.java: Muestra la lista de softwares disponibles.

Registrar.java: Permite agregar nuevos softwares a la base de datos.

Editar.java: Permite modificar softwares ya existentes.

Software.java: Clase modelo que representa un software.

SoftwareAdapter.java: Adaptador personalizado para mostrar datos en un RecyclerView.
```
```
Archivos XML (Diseño de interfaces):
activity_main.xml

activity_buscar.xml

activity_listar.xml

activity_registrar.xml

activity_editar.xml

item_software.xml

💡 Nota: Parte del diseño de las interfaces XML fue elaborado con la ayuda de ChatGPT, quien me apoyó a organizar visualmente mejor los layouts y mejorar su presentación en pantalla.
```
```

🛠 Tecnologías utilizadas
Java

Android Studio

Retrofit o Volley para consumo de API REST

REST API desplegada en Railway

Git & GitHub
```
```

🚀 Cómo usar este proyecto
Clona este repositorio:

bash
Copiar
Editar
git clone https://github.com/estefano18-jp/RestAppStore.git
Ábrelo en Android Studio.

Asegúrate de tener conexión a internet (para conectarte con la API).

Ejecuta la app en un emulador o dispositivo físico.
```
```

✍️ Autor
👤 Estefano
GitHub: @estefano18-jp
```


