import React, { useEffect } from 'react'
import { useState} from 'react';
import { useParams, Link } from 'react-router-dom';



const ListVehicle = () => {

  const [vehiculos,setVehiculos]=useState([]);
  const url= "http://54.193.237.249:8090/vehiculo/vehiculos" 
  

  useEffect(()=>{
    fetch(url)
  .then(response =>{
    return response.json();
  })
  .then(data => {    
    setVehiculos(data) 
    {vehiculos.map((vehicle, index) => (
      console.log(vehicle.id))
    )}
    
  })
  }, []);
  
 
  const handleDelete = (idToDelete) => {  

    const jwt = localStorage.getItem("token");  

    const url = `http://54.193.237.249:8090/vehiculo/eliminar/${idToDelete}`

    const config = {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json','Authorization': 'Bearer ' + jwt
      }
    }

        fetch(url,config)
        .then(response => {          
          if (response.ok === false) {
            throw new Error(`Error al eliminar: ${response.status} - ${response.statusText}`);
           
          } else {
            console.log(response.json);
            return response;
          }
        })
        .then(data => {
          alert("Desea eliminar este vehiculo con ID: " + idToDelete)
          console.log('Eliminado con éxito:', data);  
          setVehiculos(prevVehiculos => prevVehiculos.filter(vehicle => vehicle.id !== idToDelete));  
          window.location.reload()
        })
        .catch(error => {
         console.error('Error al eliminar:', error.message);
        }
        )
        .finally(idToDelete='');
    }  

  return (
    <div className='table-list'>

<table>
        <tr>
            <th>ID DEL VEHICULO</th>
            <th>MODELO</th>
            <th>MARCA</th>
            <th>PUNTAJE</th>
            <th>OPCIONES</th>
        </tr>
        
        {vehiculos.map((vehicle, index) => ( 
      <tr key={index}>   
      <td>{vehicle.id}</td>
      <td>{vehicle.modelo}</td>
      <td>{vehicle.marca}</td>
      <td>{vehicle.puntaje}</td>
      <td>
        <Link to={`/edit/${vehicle.id}`}>
        <button>EDITAR</button>
        </Link>
        <button onClick={() => {handleDelete(vehicle.id)}}>ELIMINAR</button></td>
      </tr>
      ))}
        
        
    </table>
    
    </div>
  )
}

export default ListVehicle;