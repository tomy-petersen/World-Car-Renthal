import React from 'react'
import { Link } from 'react-router-dom';
import { useParams } from 'react-router-dom';
import { useState,useEffect } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import Loader from '../Utils/Loader';


const CarDetails = () => {

    const { id } = useParams();    
    const jwt= localStorage.getItem('token')
    const idUser=localStorage.getItem('id')
    const [vehicleDetails, setVehicleDetails] = useState();
    const [isLoading, setIsLoading] = useState(false);
    const [dias,setDias] = useState(0);



    useEffect(() => {        
        fetch(`http://54.193.237.249:8090/vehiculo/buscar/${id}`)
          .then((response) => response.json())
          .then((data) => {
            setVehicleDetails(data);
            console.log(data);
          })
          .catch((error) => {
            console.error('Error al obtener detalles del vehículo:', error);
          });
      }, [id]);           
            

      const [selectedDates, setSelectedDates] = useState([]);
      const today = new Date();
      const unavailableDates = [new Date(2023, 0, 5), new Date(2023, 0, 10)]; // Fechas no disponibles
    
      const isDateDisabled = (date) => {
        // Bloquea fechas anteriores a la fecha actual
        if (date < today) {
          return true;
        }
    
        // Bloquea fechas no disponibles
        return unavailableDates.some(unavailableDate => date.toDateString() === unavailableDate.toDateString());
      };
    
      const handleDateChange = (date) => {
        // Verifica si la fecha ya está seleccionada y quítala del array, o agrégala si no está presente
        setSelectedDates(prevSelectedDates => {
          const dateIndex = prevSelectedDates.findIndex(selectedDate => selectedDate.toDateString() === date.toDateString());
    
          if (dateIndex !== -1) {
            return [...prevSelectedDates.slice(0, dateIndex), ...prevSelectedDates.slice(dateIndex + 1)];
          } else {
            return [...prevSelectedDates, date];
          }
        }); 
      };

      const calculateDaysDifference = () => {
        const firstSelectedDate = selectedDates.length > 0 ? selectedDates[0] : null;
        const lastSelectedDate = selectedDates.length > 0 ? selectedDates[selectedDates.length - 1] : null;
    
        if (firstSelectedDate && lastSelectedDate) {
          // Calcula la diferencia en milisegundos
          const differenceInMilliseconds = lastSelectedDate - firstSelectedDate;
    
          // Convierte la diferencia de milisegundos a días
          const newDaysDifference = Math.floor(differenceInMilliseconds / (1000 * 60 * 60 * 24));
    
          // Actualiza el estado con la nueva cantidad de días
          setDias(newDaysDifference);
        } else {
          // Si no hay fechas seleccionadas, establece la diferencia en 0
          setDias(0);
        }
      };

      const handleCLick=()=>{
        calculateDaysDifference()  
      }
    
    
      const handleSaveDates = () => {
        setIsLoading(true)
        
        const firstSelectedDate = selectedDates.length > 0 ? selectedDates[0] : null;
        const lastSelectedDate = selectedDates.length > 0 ? selectedDates[selectedDates.length - 1] : null;

        const data = {
            diaInicio: firstSelectedDate,
            diaFinalizacion: lastSelectedDate,
            usuario: {
              id: idUser
            },
            vehiculo: {
              id: id
            },
            metodoDePago: "MERCADOPAGO",
            politicas: true
          
        };
        const config={
          method: 'POST',
          headers: {
            'Content-type': 'application/json; charset=UTF-8','Authorization': 'Bearer ' + jwt
          },
          body: JSON.stringify(data)
      }

        fetch("http://54.193.237.249:8090/reserva/reservar",config)
        .then((response)=>{
          return response
        })
        .then((data)=>{
          console.log(data);
            alert("Reserva realizada")
            window.location.replace('/myreservation')

          
        })
        .catch((error)=>{
          console.log(error)
          if(alert(error)){
            isLoading(false)
          }
        }
        )

        console.log('Fechas seleccionadas:', selectedDates);
        // Lógica para enviar a la base de datos...
      };

        return(

          <section className='details-section'>
        <div className='dates'>
          <h2>Selecciona fechas de reserva:</h2>
          <Calendar
            onChange={handleDateChange}
            value={selectedDates}
            tileDisabled={({ date }) => isDateDisabled(date)}
          />
          <button onClick={handleCLick} className='btn'>Calcular</button>
          <button onClick={handleSaveDates} className='btn'>Reservar</button>  
          {isLoading ? <Loader/> : <></>}
          {vehicleDetails ?
          <h2>Precio: ${vehicleDetails.precio*dias}</h2>:<></>}
        </div>
        <div className="car-details-container">
        <h2>Detalles del vehículo</h2>
        <div className='detalles'>
        {vehicleDetails ? ( 
          <>
            <h3><img src="../src/Img/distinciones/1.png" alt="" className='img-dis'/>{vehicleDetails.modelo}</h3>
            <p><img src="../src/Img/distinciones/2.png" alt="" className='img-dis'/>Marca: {vehicleDetails.marca}</p>
            <p><img src="../src/Img/distinciones/3.png" alt="" className='img-dis'/>Tipo de Auto: {vehicleDetails.tipo}</p>
            <p><img src="../src/Img/distinciones/4.png" alt="" className='img-dis'/>Tipo de Motor: {vehicleDetails.motor}</p>
            <p><img src="../src/Img/distinciones/5.png" alt="" className='img-dis'/>Cilindraje: {vehicleDetails.cilindrada}</p>
            <p><img src="../src/Img/distinciones/6.png" alt="" className='img-dis'/>Tipo de Caja: {vehicleDetails.caja}</p>
            {vehicleDetails.imagen && vehicleDetails.imagen.length > 0 ? (
              <img src={vehicleDetails.imagen[0]} alt="imagen vehiculo" /> 
            ) : (
              <p>No hay imagen disponible</p>
            )}              
            <p><img src="../src/Img/distinciones/8.png" alt="" className='img-dis'/>Precio: {vehicleDetails.precio}</p>
            <p><img src="../src/Img/distinciones/9.png" alt="" className='img-dis'/>{vehicleDetails.descripcion}</p>
            <Link to={`/carimg/${vehicleDetails.id}`}>
                  <button className='btn'>Más Imagenes</button>
            </Link>
          </>
        ) : (
          <p>Cargando detalles...</p>
        )}    
        </div> 
        </div>
        <div className='titulo'>          
          <Link to="/"><img src="../src/Img/1.png" alt="imagen de volver"
          className='volver'/></Link>
        </div>
      </section>
    );
      
  };


export default CarDetails;