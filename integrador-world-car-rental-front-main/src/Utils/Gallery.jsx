import CarCards from "./CarCards";
import React,{ useEffect, useState } from 'react'
import Galleries from "../RoutesUsers/Galleries";
import { Link } from "react-router-dom";

function Gallery() {
  const numeroDePaginas = 5;
  const [paginaActual, setPaginaActual] = useState(1);

  const carCardsPorPagina = Array.from({ length: numeroDePaginas }, (_, index) => {
    // Crea un array para cada página, puedes llenar este array con los CarCards específicos para esa página
    return Array.from({ length: 2 }, (_, cardIndex) => (
      // Puedes personalizar esta parte según tus necesidades
      <CarCards key={`page${index + 1}_card${cardIndex + 1}`} />
    ));
  });

  const handleCambioDePagina = (newPage) => {
    if (newPage >= 1 && newPage <= numeroDePaginas) {
      setPaginaActual(newPage);
    }
  };

  return (
    <section className="gallery">
      <h2>TE RECOMENDAMOS VER</h2>

      <div className="gallery-container">
        {carCardsPorPagina[paginaActual - 1] && (
          <div className="gallery-container">{carCardsPorPagina[paginaActual - 1]}</div>
        )}
      </div>

      <div className="btn-gallery">
        <button className="btn" onClick={() => handleCambioDePagina(1)} disabled={paginaActual === 1}>
          Inicio
        </button>
        <button className="btn" onClick={() => handleCambioDePagina(paginaActual - 1)} disabled={paginaActual === 1}>
          Anterior
        </button>
        <button className="btn" onClick={() => handleCambioDePagina(paginaActual + 1)} disabled={paginaActual === numeroDePaginas}>
          Siguiente
        </button>
        <button className="btn" onClick={() => handleCambioDePagina(numeroDePaginas)} disabled={paginaActual === numeroDePaginas}>
          Final
        </button>
        <p>Pag. {paginaActual}</p>
      </div>
      <Link to="/galeries">
        <h2>VER MÁS</h2>
      </Link>
    </section>
  );
}

export default Gallery;