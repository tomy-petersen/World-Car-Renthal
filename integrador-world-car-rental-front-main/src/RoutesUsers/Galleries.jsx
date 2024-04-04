import React, { useState, useEffect } from 'react'
import Gallery from '../Utils/Gallery'
import CarCards from '../Utils/CarCards'
import { Link } from 'react-router-dom'


const Galleries = () => {

  const [vehiculos,setVehiculos]=useState([]);
  const url= "http://54.193.237.249:8090/vehiculo/vehiculos"

  useEffect(()=>{
    fetch(url)
  .then(response =>{
    return response.json();
  })
  .then(data => {    
    setVehiculos(data) 
  })
  }, [])  

  return(
    <div className='galeries-cont'>
      <div><h2>Galeria de Imagenes</h2></div>
    <div className='galeries'>            
    {vehiculos.map((vehicle, index) => (
      <div className="card" key={index}>
        <img src={vehicle.imagen[0]} alt="Imagen del artículo" />
        <div className='car-details'>
          <h3><img src="../src/Img/distinciones/1.png" alt="" className='img-dis'/>Modelo: {vehicle.modelo}</h3>
          <p><img src="../src/Img/distinciones/2.png" alt="" className='img-dis'/>Marca: {vehicle.marca}</p>
          <p><img src="../src/Img/distinciones/8.png" alt="" className='img-dis'/>Precio: {vehicle.precio}</p>
          <p><img src="../src/Img/distinciones/9.png" alt="" className='img-dis'/>Valoracion: {vehicle.puntaje}</p>
        </div>
        <div className='card-buttons'>
          <button>Renta Ya</button>
          <Link to={`/cardetails/${vehicle.id}`}>
            <button>Más Detalles</button>
          </Link>
        </div>
      </div>
      
    ))}
    </div>
    </div>
  )
}

export default Galleries;