import React, { useEffect } from 'react';
import { useState} from 'react';


const Favorites = () => {

  const [favorito, setFavorito] = useState(true);

  const [fav, setFav] = useState([]);
  const userId = localStorage.getItem('id');
  const jwt = localStorage.getItem('token');
  const urlFav = `http://54.193.237.249:8090/usuario/${userId}/vehiculosFavoritos`;
  const configFav ={
    method: 'GET',
  headers: {
    'Content-Type': 'application/json', 'Authorization': 'Bearer ' + jwt
  }}

  useEffect(()=>{
    fetch(urlFav,configFav)
  .then(response =>{
    return response.json();
  })
  .then(data => {    
    setFav(data) 
    {fav.map((favorites, index) => (
      console.log(favorites.id))
    )}
    
  })
  }, [fav]);


  const handleAgregarQuitarFavorito = (vehiculoId) => {

    const urlFav = `http://localhost:8080/usuario/${userId}/vehiculoAddFav`;

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

  function favsRender(){
    if (jwt) {
      return (
        <div className='favs'>
          <h2>Tus Favoritos</h2>
          {fav.map((favoritos, index) => (
            <div className='gallery' key={`fav-${index}`}>
              <div className='card'>
                <h3>
                  <img src="../src/Img/distinciones/1.png" alt="" className='img-dis' />
                  Modelo: {favoritos.modelo}
                </h3>
                <p>
                  <img src="../src/Img/distinciones/2.png" alt="" className='img-dis' />
                  Marca: {favoritos.marca}
                </p>
                <p>
                  <img src="../src/Img/distinciones/9.png" alt="" className='img-dis' />
                  {favoritos.descripcion}
                </p>
                {favoritos.imagen && favoritos.imagen.length > 0 ? (
                  <img src={favoritos.imagen[0]} alt="imagen vehiculo" />
                ) : (
                  <p>No hay imagen disponible</p>
                )}
                <button onClick={() => handleAgregarQuitarFavorito(favoritos.id)}>Quitar de Favoritos</button>
              </div>
            </div>
          ))}
        </div>
      );
    } else {
      
      return (
        <div className='favs'>
          <h2>Inicia sesión para ver tus favoritos.</h2>
        </div>
      );
    }}
  

  return (
    <div>
   {favsRender()}
   </div>
  )
   
} 
export default Favorites;