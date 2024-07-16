# Desafío Técnico Walmart Chile - Android MVP

## Descripción del Proyecto

Este proyecto es un MVP (Producto Mínimo Viable) de una aplicación móvil Android para un pequeño emprendimiento de venta de artículos de uso diario. La aplicación permite a los usuarios listar productos, ver detalles y agregarlos al carrito de compras.

## Características Principales

- Listado de productos con un producto destacado
- Navegación por categorías
- Detalles del producto
- Carrito de compras
- Persistencia local de datos del carrito

## Tecnologías Utilizadas

- Kotlin
- Android Jetpack (ViewModel, LiveData, Room)
- Retrofit para llamadas a la API
- Coil para carga de imágenes
- Navigation Component para la navegación entre fragmentos
- Material Design para la interfaz de usuario

## Arquitectura

El proyecto sigue el patrón de arquitectura MVVM (Model-View-ViewModel) para una clara separación de responsabilidades y una mejor mantenibilidad del código.

## Configuración del Proyecto

1. Clone el repositorio:
   ```
   git clone https://github.com/leotorrealba/DesafioTecnicoWalmart.git
   ```

2. Abra el proyecto en Android Studio.

3. Sincronice el proyecto con los archivos Gradle.

4. Ejecute la aplicación en un emulador o dispositivo físico.

## Estructura del Proyecto

```
## app/
- **manifests/**
  - `AndroidManifest.xml`

- **kotlin+java/**
  - **com.lectorrealba.desafiotecnico/**
    - **data/**
      - **local/**
        - **entity/**
          - `CartItemEntity`
          - `AppDatabase`
          - `CartDao`
      - **mapper/**
        - `CategoryMapper`
        - `ProductMapper`
      - **remote/**
        - `FakeStoreApi`
        - **api/**
          - `CategoryDto`
          - `ProductDto`
          - `RatingDto`
      - **repository/**
        - `CartRepositoryImpl`
        - `ProductRepositoryImpl`
      - **di/**
        - `AppModule`
        - `RepositoryModule`
    - **domain/**
      - **model/**
        - `CartItemData`
        - `Category`
        - `Product`
        - `Rating`
      - **repository/**
        - `CartRepository`
        - `ProductRepository`
      - **usecase/**
        - `AddToCartUseCase`
        - `ClearCartUseCase`
        - `GetCartItemsUseCase`
        - `GetCategoriesUseCase`
        - `GetProductDetailUseCase`
        - `GetProductsByCategoryUseCase`
        - `GetProductsInCartUseCase`
        - `GetProductsUseCase`
        - `RemoveFromCartUseCase`
        - `UpdateItemQuantityInCartUseCase`
    - **presentation/**
      - **navigation/**
        - `NavGraph.kt`
      - **ui/**
        - **cart/**
          - `CartScreen.kt`
        - **common/**
          - `ComposableElements.kt`
        - **detail/**
          - `ProductDetailScreen.kt`
        - **home/**
          - `HomeScreen.kt`
        - **pbc/**
          - `ProductsByCategoryScreen.kt`
        - **theme/**
          - `Color.kt`
          - `Shape.kt`
          - `Type.kt`
      - `MainActivity`
    - **viewmodel/**
      - `CartViewModel`
      - `ProductsViewModel`
    - **utilities/**
      - `UIState`
      - `DesafioTecnicoApplication`
  - **java/**
    - `generated`
  - **res/**
    - `generated`
  - **gradle Scripts**

```

## Funcionalidades Detalladas

### Pantalla Principal (Home)

- Muestra una lista de productos obtenidos de la API FakeStore.
- El primer producto de la lista es el "Destacado", con un diseño diferente.
- El producto destacado se determina por la fórmula: MAX(rate * count).
- Cada producto muestra su título y precio.
- Botón "+" para agregar productos al carrito.
- Indicador numérico en el botón del carrito que se actualiza al agregar productos.

### Navegación por Categorías

- Drawer de navegación que muestra las categorías disponibles.
- Al seleccionar una categoría, se cargan los productos correspondientes.

### Detalle del Producto

- Se accede al tocar un producto en la lista principal.
- Muestra información detallada del producto, incluyendo descripción y calificación.
- Permite agregar el producto al carrito desde esta vista.

### Carrito de Compras

- Gestión local del carrito (sin sincronización con la nube).
- Muestra los productos agregados, sus cantidades y el total a pagar.
- Permite modificar las cantidades de los productos.
- Botón "Purchase" (sin funcionalidad en este MVP).

## API Utilizada

La aplicación consume datos de [FakeStore API](https://fakestoreapi.com/). Los endpoints principales son:

- `GET /products`: Lista todos los productos.
- `GET /products/categories`: Obtiene las categorías disponibles.
- `GET /products/category/{categoryName}`: Lista productos por categoría.
- `GET /products/{id}`: Obtiene detalles de un producto específico.

## Consideraciones y Decisiones de Diseño

1. **Persistencia de Datos**: Se utiliza Room para almacenar localmente los datos del carrito, asegurando que la información persista entre sesiones de la aplicación.

2. **Manejo de Estado**: Se implementa ViewModel con LiveData para manejar el estado de la UI y los datos, facilitando la comunicación entre la capa de datos y la interfaz de usuario.

3. **Inyección de Dependencias**: Se utiliza Hilt para la inyección de dependencias, mejorando la modularidad y testabilidad del código.

4. **Manejo de Errores**: Se implementa un sistema robusto de manejo de errores para proporcionar feedback al usuario en caso de fallos en la red o en la carga de datos.

5. **Diseño Responsivo**: La interfaz de usuario se diseña para ser responsiva y adaptarse a diferentes tamaños de pantalla y orientaciones.

6. **Optimización de Rendimiento**: Se implementa paginación para la carga eficiente de grandes listas de productos.

7. **Accesibilidad**: Se consideran las pautas de accesibilidad de Android para asegurar que la aplicación sea utilizable por un amplio rango de usuarios.

## Pruebas

El proyecto incluye pruebas unitarias para la lógica de negocio y pruebas de integración para los componentes de la UI. Para ejecutar las pruebas:

1. Para pruebas unitarias: `./gradlew test`
2. Para pruebas de instrumentación: `./gradlew connectedAndroidTest`

## Contribuciones

Las contribuciones son bienvenidas. Por favor, abra un issue para discutir cambios mayores antes de enviar un pull request.

## Licencia

Este proyecto está bajo la licencia MIT. Ver el archivo [LICENSE](LICENSE) para más detalles.

Citations:
[1] https://ppl-ai-file-upload.s3.amazonaws.com/web/direct-files/14330561/50f2a50a-e797-43e1-aeea-7f7168463727/Test.pdf