import React from 'react'
import { useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';

const EditVehicle = () => {

  const { id } = useParams();    
  const [vehicleDetails, setVehicleDetails] = useState({
    modelo: '',
  marca: '',
  precio: 0,
  disponibilidad: false,
  tipo: 'Selecciona',
  pasajeros: 0,
  descripcion: '',
  motor: 'Selecciona',
  cilindrada: 0,
  caja: 'Selecciona',
  patente: '',
  stock: 0,
  imagenes: [],  
  });

  const jwt = localStorage.getItem("token");

  useEffect(() => {        
      fetch(`http://54.193.237.249:8090/vehiculo/buscar/${id}`)
        .then((response) => response.json())
        .then((data) => {
          setVehicleDetails(data);
        })
        .catch((error) => {
          console.error('Error al obtener detalles del vehículo:', error);
        });
    }, []); 
   

    const handleEdit = (e) => {
      
      e.preventDefault();

      fetch(`http://54.193.237.249:8090/vehiculo/modificar`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json', 'Authorization': 'Bearer ' + jwt 
        },
        body: JSON.stringify(vehicleDetails),
      })
        .then((response) => {
          if (response.ok) {
            // La solicitud de edición fue exitosa
            console.log('Vehículo editado con éxito');
            // Puedes redirigir o realizar otras acciones después de la edición
          } else {
            console.error('Error al editar el vehículo');
          }
        })
        .catch((error) => {
          console.error('Error en la solicitud de edición:', error);
        });
    };

  return(

    <form method="post"  className='form-add' onSubmit={(e)=>{handleEdit(e)}}>

      <h2>Datos del Vehículo</h2>

      <div className="form-group">
        <label htmlFor="modelo" >Modelo</label>
        <input 
         type="text" 
         class="form-control" 
         id="modelo"
         name="modelo" 
         value={vehicleDetails ? vehicleDetails.modelo : ''}
         onChange={(e) =>
          setVehicleDetails({
            ...vehicleDetails,
            modelo: e.target.value,
          })
        }         
         required />
      </div>
    
      <div className="form-group">
        <label htmlFor="marca">Marca</label>
        <input 
        type="text" 
        class="form-control" 
        id="marca" 
        name="marca" 
        value={vehicleDetails ? vehicleDetails.marca : ''}
        onChange={(e) =>
          setVehicleDetails({
            ...vehicleDetails,
            marca: e.target.value,
          })
        }
        required />
      </div>
    
      <div className="form-group">
        <label htmlFor="precio">Precio</label>
        <input 
        type="number" 
        class="form-control" 
        id="precio" 
        name="precio" 
        value={vehicleDetails ? vehicleDetails.precio : ''}
        onChange={(e) =>
          setVehicleDetails({
            ...vehicleDetails,
            precio: e.target.value,
          })
        }
        required />
      </div>
    
      <div className="form-group">
        <label htmlFor="disponibilidad">Disponibilidad</label>
        <input 
        type="checkbox" 
        id="disponibilidad"
        name="disponibilidad"
        checked={vehicleDetails.disponibilidad}
        onChange={(e) =>
          setVehicleDetails({
            ...vehicleDetails,
            disponibilidad: e.target.checked,
          })
        }
         />
      </div>
    
      <div className="form-group">
        <label htmlFor="tipo">Tipo o Categoría</label>
        <select 
        class="form-control" 
        id="tipo" 
        name="tipo" 
        value={vehicleDetails ? vehicleDetails.tipo : ''}
        onChange={(e) =>
          setVehicleDetails({
            ...vehicleDetails,
            tipo: e.target.value,
          })
        }>
        
          <option value="Selecciona">Selecciona</option>
          <option value="Autos">Autos</option>
          <option value="Motos">Motos</option>
          <option value="Camionetas">Camionetas</option>
          <option value="Van">Van</option>
        </select>
      </div>
    
      <div className="form-group">
        <label htmlFor="pasajeros">Número de Pasajeros</label>
        <input 
        type="number" 
        class="form-control" 
        id="pasajeros"
        name="pasajeros" 
        value={vehicleDetails ? vehicleDetails.pasajeros : ''}
        onChange={(e) =>
          setVehicleDetails({
            ...vehicleDetails,
            pasajeros: e.target.value,
          })
        }
        required />
      </div>
    
      <div className="form-group">
        <label htmlFor="descripcion">Descripción del Vehículo</label>
        <textarea 
        name="descripcion" 
        class="form-control" 
        id="descripcion"
        value={vehicleDetails ? vehicleDetails.descripcion : ''}
        onChange={(e) =>
          setVehicleDetails({
            ...vehicleDetails,
            descripcion: e.target.value,
          })
        }>        
        </textarea>
      </div>
    
      <div className="form-group">
        <label htmlFor="motor">Tipo de Motor</label>
        <select 
        class="form-control" 
        id="motor" 
        name="motor" 
        value={vehicleDetails ? vehicleDetails.motor : ''}
        onChange={(e) =>
          setVehicleDetails({
            ...vehicleDetails,
            motor: e.target.value,
          })
        }>
          <option value="Selecciona">Selecciona</option>
          <option value="Eléctrico">Eléctrico</option>
          <option value="Diésel">Diésel</option>
          <option value="Gas">Gas</option>
          <option value="Híbrido">Híbrido</option>
        </select>
      </div>
    
      <div className="form-group">
        <label htmlFor="cilindrada">Cilindrada</label>
        <input 
        type="text" 
        class="form-control" 
        id="cilindrada"
        name="cilindrada" 
        value={vehicleDetails ? vehicleDetails.cilindrada : ''}
        onChange={(e) =>
          setVehicleDetails({
            ...vehicleDetails,
            cilindrada: e.target.value,
          })
        }
        required />
      </div>
    
      <div className="form-group">
        <label htmlFor="caja">Tipo de Caja</label>
        <select 
        class="form-control" 
        id="caja" name="caja" 
        value={vehicleDetails ? vehicleDetails.caja : ''}
        onChange={(e) =>
          setVehicleDetails({
            ...vehicleDetails,
            caja: e.target.value,
          })
        }>
          <option value="Selecciona">Selecciona</option>
          <option value="Automática">Automática</option>
          <option value="Manual">Manual</option>
        </select>
      </div>
    
      <div className="form-group">
        <label htmlFor="patente">Patente</label>
        <input 
        type="text" 
        class="form-control" 
        id="patente"
        name="patente" 
        value={vehicleDetails ? vehicleDetails.patente : ''}
        onChange={(e) =>
          setVehicleDetails({
            ...vehicleDetails,
            patente: e.target.value,
          })
        }
        required />
      </div>
    
      <div className="form-group">
        <label htmlFor="puntaje">Puntaje</label>
        <input 
        type="text" 
        class="form-control" 
        id="puntaje"
        name="puntaje"
        value={vehicleDetails ? vehicleDetails.stock : ''}
        onChange={(e) =>
          setVehicleDetails({
            ...vehicleDetails,
            puntaje: e.target.value,
          })
        }
        required />
      </div>
    
      <div class="form-group">
        <label htmlFor="imagenes">Imágenes</label>
        <input 
        type="text" 
        class="form-control-file" 
        id="imagenes" 
        name="imagenes" 
        multiple />
      </div>   
          
      <button type="submit" class="btn">Editar Vehículo</button>
    </form>
  )
}

export default EditVehicle