import React from 'react';
import { useParams } from 'react-router-dom';

const SearchResults = () => {

  const { diaInicio, diaEntrega, pais, ciudad} = useParams();
  
  return (
    
    <div className="resultado-busqueda">
      <h2>Resultados de tu búsqueda:</h2>
      <p>Día de Inicio: {diaInicio}</p>
      <p>Día de Entrega: {diaEntrega}</p>
      <p>País: {pais}</p>
      <p>Ciudad: {ciudad}</p>
     
    </div>
  );
};

export default SearchResults;