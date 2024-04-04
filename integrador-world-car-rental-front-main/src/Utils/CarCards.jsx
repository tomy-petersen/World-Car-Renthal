
import React,{ useEffect, useState } from 'react'
import CarDetails from '../RoutesUsers/CarDetail';
import { Link } from 'react-router-dom';

const CarCards = () => {

  const [vehiculo,setVehiculo]=useState([]) ;
  const [vehiculo2,setVehiculo2]=useState([]);  
  const [favorito, setFavorito] = useState(false);
  const userId = localStorage.getItem('id');
  const jwt = localStorage.getItem('token')
 
  
  
  const url= "http://54.193.237.249:8090/vehiculo/vehiculos"  

  useEffect(()=>{
    fetch(url)
  .then(response =>{
    return response.json();
  })
  .then(data => {  

    let v1, v2;
    let ve1, ve2;

     do {
          v1 = new Set(obtenerElementosAleatorios(data, 2));
          v2 = new Set(obtenerElementosAleatorios(data, 2));
        } while (JSON.stringify([...v1]) === JSON.stringify([...v2]));

    ve1 = [...v1];
    ve2 = [...v2];

    setVehiculo(ve1);
    setVehiculo2(ve2);
    
    
  }  
       
  )
  }, []); 
  

  function obtenerElementosAleatorios(array, cantidad) {
    const elementosAleatorios = [];    
  
    while (elementosAleatorios.length < cantidad && array.length > 0) {
      const indiceAleatorio = Math.floor(Math.random() * array.length);
      const elementoAleatorio = array.splice(indiceAleatorio, 1)[0];
      elementosAleatorios.push(elementoAleatorio);
    }
  
    return elementosAleatorios;
  }   

  

  const handleAgregarQuitarFavorito = (vehiculoId) => {

    const urlFav = `http://54.193.237.249:8090/usuario/${userId}/vehiculoAddFav`;

    const configFav = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json', 'Authorization': 'Bearer ' + jwt
      },
      body: JSON.stringify({ vehiculoId }),
    }  

    fetch(urlFav, configFav)
      .then(response => {
        if (response.ok) {          
          setFavorito((prevFavorito) => !prevFavorito)          
        } else {
          console.error('Error al agregar/quitar vehículo a favoritos');
          alert("No se ha podido realizar la acción con éxito, inicia sesión para poder agregar a favoritos")
        }
      })
      .catch(error => console.error('Error al agregar/quitar vehículo a favoritos:', error));
  };

  

 
  function cardRender (){    

    if (vehiculo.length > 0 && vehiculo2.length > 0) {
      return (
        <section>
          <div className="car-cards">
            {vehiculo.map((vehicle, index) => (
              <div className="card" key={`vehiculo-${index}`}>
                <img src={vehicle.imagen[0]} alt="Imagen del vehículo" />
                <div className='car-details'>
                  <h3><img src="../src/Img/distinciones/1.png" alt="" className='img-dis'/> Modelo: {vehicle.modelo}</h3>
                  <p><img src="../src/Img/distinciones/2.png" alt="" className='img-dis'/> Marca: {vehicle.marca}</p>
                  <p><img src="../src/Img/distinciones/8.png" alt="" className='img-dis'/> Precio: {vehicle.precio}</p>
                  <p>Valoracion: {vehicle.puntaje}</p>
                </div>
                <div className='card-buttons'>
                  <Link to={`/cardetails/${vehicle.id}`}>
                    <button className='btn'>Más Detalles</button>
                  </Link>
                  <button onClick={() => handleAgregarQuitarFavorito(vehicle.id)} className='btn'>
                    {favorito ? 'Quitar de Favoritos' : 'Agregar a Favoritos'}
                  </button>
                </div>
              </div>
            ))}
  
            {vehiculo2.map((vehicle, index) => (
              <div className="card" key={`vehiculo2-${index}`}>
                <img src={vehicle.imagen[0]} alt="Imagen del vehículo" />
                <div className='car-details'>
                  <h3><img src="../src/Img/distinciones/1.png" alt="" className='img-dis'/> Modelo: {vehicle.modelo}</h3>
                  <p><img src="../src/Img/distinciones/2.png" alt="" className='img-dis'/> Marca: {vehicle.marca}</p>
                  <p><img src="../src/Img/distinciones/8.png" alt="" className='img-dis'/> Precio: {vehicle.precio}</p>
                  <p>Valoracion: {vehicle.puntaje}</p>
                </div>
                <div className='card-buttons'>
                  <Link to={`/cardetails/${vehicle.id}`}>
                    <button className='btn'>Más Detalles</button>
                  </Link>
                  <button onClick={() => handleAgregarQuitarFavorito(vehicle.id)} className='btn'>                    
                    {favorito ? 'Quitar de Favoritos' : 'Agregar a Favoritos'}
                  </button>
                </div>
              </div>
            ))}
          </div>
        </section>
      );
    } else {
      return null;
    }
  }
  
   
    
  return (
    <div>

      {cardRender()}     

    </div>
);}
export default CarCards;
