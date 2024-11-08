# Prueba W2M

## Despliegue

Para desplegar el proyecto, deberemos hacer lo siguiente:

### Backend

Para ejecutar el backend, abriremos el proyecto y ejecutaremos desde nuestro IDE la clase **BackendApplication**, esperaremos un momento hasta que tengamos un mensaje de: _"Data upload successfully completed"_, esto significa que los datos de inicio/prueba se han creado correctamente.

### Frontend

Abriremos el proyecto frontend y ejecutaremos los siguientes comandos:

Instalación de los modulos

```bash
  npm install
```

Ejecución en desarrollo:

```bash
  npm run dev
```

## Referencias API

#### Obtener todas las naves

```http
  GET /starship/
```

#### Obtener una nave por ID

```http
  GET /starship/id/${id}
```

| Parameter | Type     | Description                                       |
| :-------- | :------- | :------------------------------------------------ |
| `id`      | `number` | **Requerido**. Id de la nave que queremos obtener |

#### Obtener naves por nombre

```http
  GET /starship/name/${name}
```

| Parameter | Type     | Description                                                       |
| :-------- | :------- | :---------------------------------------------------------------- |
| `name`    | `string` | **Requerido**. Nombre de la nave o las naves que queremos obtener |

#### Crear nave

```http
  POST /starship/name/${name}
```

Body:

```json
{
  "name": "Nave prueba",
  "model": "PR01 Prueba",
  "starshipClass": "prueba",
  "manufacturer": "DevStylish Corporation",
  "costInCredits": 3500000,
  "length": 150,
  "crew": 30165,
  "passengers": 600,
  "maxAtmospheringSpeed": "950",
  "cargoCapacity": 3000000,
  "consumables": "1 year",
  "films": [],
  "pilots": [],
  "starshipImage": "https://starwars-visualguide.com/assets/img/starships/2.jpg",
  "created": "2024-10-30T17:55:06.604Z",
  "edited": "2024-10-30T17:55:06.604Z",
  "mglt": "60"
}
```

#### Actualizar nave

```http
  PATCH /starship/name/${name}
```

Body:

```json
{
  "name": "Nave prueba",
  "model": "PR01 Prueba",
  "starshipClass": "prueba",
  "manufacturer": "DevStylish Corporation",
  "costInCredits": 3500000,
  "length": 150,
  "crew": 30165,
  "passengers": 600,
  "maxAtmospheringSpeed": "950",
  "cargoCapacity": 3000000,
  "consumables": "1 year",
  "films": [],
  "pilots": [],
  "starshipImage": "https://starwars-visualguide.com/assets/img/starships/2.jpg",
  "created": "2024-10-30T17:55:06.604Z",
  "edited": "2024-10-30T17:55:06.604Z",
  "mglt": "60"
}
```

#### Eliminar nave

```http
  DELETE /starship/id/${id}
```

| Parameter | Type     | Description                                        |
| :-------- | :------- | :------------------------------------------------- |
| `id`      | `number` | **Requerido**. Id de la nave que queremos eliminar |
