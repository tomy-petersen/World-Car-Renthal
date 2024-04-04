import React from 'react'
import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';



const SearchBar = () => {  

  const ciudadesPorPais = {
    Argentina: [
      'Buenos Aires',
      'Córdoba',
      'Rosario',
      'Mendoza',
      'La Plata',
      'Mar del Plata',
      'Santa Fe',
      'San Juan',
      'Salta',
      'Tucumán',
      'Neuquén',
      'Bariloche',
      'San Miguel de Tucumán',
      'Corrientes',
      'Resistencia',
      'Posadas',
      'Bahía Blanca',
      'San Salvador de Jujuy',
      'Misiones',
    ],
    Colombia: [
      'Bogotá',
      'Medellín',
      'Cali',
      'Barranquilla',
      'Cartagena',
      'Cúcuta',
      'Bucaramanga',
      'Pereira',
      'Santa Marta',
      'Ibagué',
      'Manizales',
      'Pasto',
      'Neiva',
      'Soledad',
      'Villavicencio',
      'Armenia',
      'Soacha',
      'Valledupar',
      'Montería',
      'Popayán',
    ],
    Uruguay: [
      'Montevideo',
      'Punta del Este',
      'Colonia del Sacramento',
      'Salto',
      'Mercedes',
      'Maldonado',
      'Tacuarembó',
      'Treinta y Tres',
      'Rocha',
      'Durazno',
      'Artigas',
      'Fray Bentos',
      'Trinidad',
      'San José de Mayo',
      'Canelones',
      'Las Piedras',
      'Melilla',
      'La Paz',
      'Minas',
      'Florida',
    ],
    Chile: [
  'Santiago',
  'Valparaíso',
  'Concepción',
  'Viña del Mar',
  'Antofagasta',
  'Valdivia',
  'La Serena',
  'Puerto Montt',
  'Temuco',
  'Iquique',
    ],

    Ecuador: [
      'Quito',
      'Guayaquil',
      'Cuenca',
      'Ambato',
      'Loja',
      'Manta',
      'Esmeraldas',
      'Portoviejo',
      'Santo Domingo',
      'Machala',
    ],

    Peru: [
  'Lima',
  'Arequipa',
  'Trujillo',
  'Chiclayo',
  'Piura',
  'Iquitos',
  'Cusco',
  'Huancayo',
  'Tacna',
  'Chimbote',
    ]
  };

  const [diaInicio, setDiaInicio] = useState('');
  const [diaEntrega, setDiaEntrega] = useState('');  
  const [pais, setPais] = useState('');
  const [ciudad, setCiudad] = useState('');
  const [ciudadesDisponibles, setCiudadesDisponibles] = useState(ciudadesPorPais[pais] || []);
  const [resultadosAutos, setResultadosAutos] = useState([]);
  const [mostrarResultados, setMostrarResultados] = useState(false);

  useEffect(() => {
    setCiudadesDisponibles(ciudadesPorPais[pais] || []);
  }, [pais]);
 
  const handlePaisChange = (e) => {
    const selectedPais = e.target.value;
    setPais(selectedPais);
    setCiudad('');
    setCiudadesDisponibles(ciudadesPorPais[selectedPais] || []);
  };

  useEffect(() => {    
    const fechaActual = new Date();
    const formatoFecha = fechaActual.toISOString().split('T')[0];
    setDiaInicio(formatoFecha);    
  }, []); 

  const mostrarPais = (nombrePais) => {
    setPais(nombrePais);
  };

  const limpiar = ()=>{
    setMostrarResultados(false)
  }

  const estaEnRango = (fechas, rangoInicio, rangoFin) => {
    return fechas.some(fecha => {
      const fechaFiltrada = new Date(fecha);
      return fechaFiltrada >= rangoInicio && fechaFiltrada <= rangoFin;
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    fetch('http://54.193.237.249:8090/vehiculo/vehiculos')
    .then(response => {
      if (!response.ok) {
        throw new Error(`Error en la solicitud: ${response.status}`);
      }
      return response.json();
    })
    .then(data => {
      
      const autosFiltrados = data.filter(auto =>
        auto.sucursal.direccion.provincia.toLowerCase() === ciudad.toLowerCase() &&
        !estaEnRango(auto.fechasNoDisponibles, diaInicio, diaEntrega) 
      );

      setResultadosAutos(autosFiltrados);
      setMostrarResultados(true);
    })
    .catch(error => {
      console.error('Error en la solicitud:', error);
    });     
    
  };

  const isValidDate = (dateString) => {
    const regex = /^\d{4}-\d{2}-\d{2}$/;
    return dateString.match(regex) !== null;
  };


  return (
    <section className='buscador'> 

    <div>      
    <h1>Busca tu mejor opción</h1>
    </div>    

    <form onSubmit={handleSubmit} className='form-search'>

        <label htmlFor="dia-inicio">Día de Inicio:</label>
        <input 
        type="date" 
        name="dia-inicio" 
        id="dia-inicio"
        value={diaInicio}
            onChange={(e) => setDiaInicio(e.target.value)}
        min={diaInicio}
        />

        <label htmlFor="dia-entrega">Día de Entrega:</label>
        <input 
        type="date" 
        name="dia-entrega" 
        id="dia-entrega"
        value={diaEntrega}
            onChange={(e) => setDiaEntrega(e.target.value)}
        min={diaInicio}
        />        

        <label htmlFor="pais">País:</label>
        <select 
        name="pais" 
        id="pais"
        value={pais} 
        placeholder='Selecciona Un Pais'
        onChange={handlePaisChange}
        >   
            <option value="null">Selecciona Un Pais</option>
            <option value="Argentina">Argentina</option>
            <option value="Colombia">Colombia</option>
            <option value="Uruguay">Uruguay</option>
            <option value="Ecuador">Ecuador</option>
            <option value="Chile">Chile</option>
            <option value="Peru">Perú</option>

        </select>

        <label htmlFor="ciudad">Ciudad:</label>
        <input
            type="text"
            name="ciudad"
            id="ciudad"
            value={ciudad}
            onChange={(e) => setCiudad(e.target.value)}
            placeholder="Selecciona una ciudad"
            list="ciudades-list"
            className='ciudad'
          />
          <datalist id="ciudades-list">
            {ciudadesDisponibles.map((ciudad, index) => (
              <option key={index} value={ciudad} />
            ))}
          </datalist>

        
          <input type="submit" value="Buscar" className='buscarbtn'/>
           
      </form>
    

    <div className='flags'>
      <img src="../src/Img/Flags/1.png" alt="" className='img-flag' onClick={() => mostrarPais('Argentina')}/>
      <img src="../src/Img/Flags/2.png" alt="" className='img-flag' onClick={() => mostrarPais('Colombia')}/>
      <img src="../src/Img/Flags/3.png" alt="" className='img-flag' onClick={() => mostrarPais('Uruguay')}/>
      <img src="../src/Img/Flags/4.png" alt="" className='img-flag' onClick={() => mostrarPais('Ecuador')}/>
      <img src="../src/Img/Flags/5.png" alt="" className='img-flag' onClick={() => mostrarPais('Chile')}/>
      <img src="../src/Img/Flags/6.png" alt="" className='img-flag' onClick={() => mostrarPais('Peru')}/>
    </div>

    {mostrarResultados && (

        <div className="resultado-busqueda">

          <h2>Resultados de tu búsqueda:</h2> 

          <div className='info-busqueda'>          
          <p>Día de Inicio: {diaInicio}</p>
          <p>Día de Entrega: {diaEntrega}</p>
          <p>País: {pais}</p>
          <p>Ciudad: {ciudad}</p>
          <button onClick={limpiar} className='btn'>Limpiar</button>
          </div>
          <h3>Autos Disponibles:</h3>
          {resultadosAutos.length > 0 ? (
            
            <ul className='result-content'>
              {resultadosAutos.map(auto => (
                <div className='card-result'>
                <li key={auto.id}>
                  <p><img src="../src/Img/distinciones/1.png" alt="" className='img-dis'/>
                     {auto.modelo}
                  </p>
                  <p><img src="../src/Img/distinciones/2.png" alt="" className='img-dis'/>
                     {auto.marca}
                  </p>
                  <p><img src="../src/Img/distinciones/8.png" alt="" className='img-dis'/>
                     ${auto.precio}
                  </p>
                  <p><img src="../src/Img/distinciones/9.png" alt="" className='img-dis'/>
                     {auto.descripcion}
                  </p>
                  <img src={auto.imagen[0]} alt="Imagen del auto" />
                  
                </li> 
                <button className='btn'>Reservar</button>
                </div>               
              ))}
              
            </ul>
            
          ) : (
            <p>No hay autos disponibles en la ciudad especificada.</p>
          )}
        </div>
      )}     
    


    </section>
  )
}

export default SearchBar;
