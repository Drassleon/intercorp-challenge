# eCommerce Backend Test

Este es un proyecto base para demostrar un microservido restful ficticio.

Se deberá implementar un registro de orden para lo cual se le proporcionará un servicio externo
con una lista de productos. Usted deberá almacenar una order en la que se registre el usuario, los productos seleccionados, y las cantidades.

Se le proporsionará un proyecto con las siguientes caracteristicas:

- Spring Boot 2.4.5
- Maven 3.6.1
- Java 11
- Restful API ecommerce

## Tareas

### 1. Integración con ecommerce-rest-api

Usaremos la siguiente API para poder extraer una lista de productos:
[ecommerce-rest-api][ecommerce-rest-api]

La aplicación ya cuenta con un ejemplo de integración basica para poder consumir esta api.
Algunos datos adicionales sobre la API es que usa una autenticación basica para el consumo del API (ver ejemplo)

Por favor, sientete libre de implementar los patrones adecuados para poder integrarnos con el servicio.

> **Tips:**
> - Se recomienda utilizar programación reactiva.
> - Se recomienda utilizar patrones de diseño para la integración con el API externa.
> - Utilice las librerias que sean necesarias para la integración, sin embargo, deberá justificar el uso de las librerias.

### 2. Data Storage

No hay restricciones para escoger la base de datos, aunque se recomienda utilizar MySql y PostgreSQL.
Tambien puede utilizar bases de datos no relacionales o en memoria.

> **Tips:**
> - Se recomienda utilizar configuraciones reactivas

### 3. Tolerancia a fallos

Se debe implementar los patrones de diseño relevantes para soportar una posible caida en el consumo API externo.

Tambien, debe validar los datos de entrada al momento de registrar la orden.

### 4. Implementación de Servicios

Deberá implenmentar las siguientes interfaces para poder resolver los siguientes casos de uso:


Deberá implementar un servicio que devuelva un producto por SkuId 
```java
public interface GetProductBySkuIdService {
    
    public Product getBySkuId(String skuId);
    
}

```
Deberá implementar un servicio que devuelva la lista de productos
```java
public interface GetProductListService {

    public List<Product> getAll();

}

```

Deberá implementar un servicio que almacene la orden
```java
public interface SaveOrderService {

    public void save(Order order);

}

```

**Notas Adicionales**:

Estamos particularmente interesados en cómo se diseña y prueba el sistema utilizando flujos reactivos, prestando especial atención a los siguientes aspectos:
* **Simplicidad**: No sobre-diseñar la solución, simple es mejor.
* **Non-Blocking APIs**: Cómo maximizar el uso de los recursos disponibles.
* **Concurrencia**: Cómo se controlan las solicitudes simultáneas para maximizar el rendimiento.
* **Testing**: Cómo diseñar las pruebas con el fin de aumentar el code coverage.
* **Fault tolerance**: Cómo el sistema maneja, aísla y reacciona a los errores.

> **Ten en cuenta:**
> * Como la implementación está pensada para atender varias solicitudes simultáneas!!!
>> El hecho de que un registro pueda tardar 5 segundos no debe impedir que ocurran otros cálculos durante ese tiempo.
> * Cómo modelar/controlar los errores.

[ecommerce-rest-api]: https://github.com/eternalfool/ecommerce-rest-api