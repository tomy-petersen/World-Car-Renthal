import React, { useEffect, useState } from 'react'

const MyReservation = () => {

  const idUser=localStorage.getItem('id')
  const jwt=localStorage.getItem('token')
  const [reservas,setReservas]=useState([]);

  
useEffect(()=>{ 

  const config={
    method: 'GET',
    headers: {
      'Content-type': 'application/json; charset=UTF-8','Authorization': 'Bearer ' + jwt
    },
}

fetch(`http://54.193.237.249:8090/reserva/reservasPorUsuario/${idUser}`, config)
.then((response)=>{
  return response.json()
})
.then((data)=>{
  setReservas(data)
  console.log(data);
}
)},[])

const calcularCantidadDias = (inicio, fin) => {
  const fechaInicio = new Date(inicio);
  const fechaFin = new Date(fin);
  const diferenciaEnMilisegundos = fechaFin - fechaInicio;
  return Math.floor(diferenciaEnMilisegundos / (1000 * 60 * 60 * 24));
};
    

  return(
    <section className='mis-reservas'>
    <table className="table table-striped">
        <thead>
            <tr>
            <th>Foto</th>
            <th>Nombre</th>
            <th>Fecha de Reserva</th>
            <th>Precio</th>
            </tr>
        </thead>
        <tbody>
          {reservas.map((reserva,index)=>{
            const cantidadDias = calcularCantidadDias(reserva.diaInicio, reserva.diaFinalizacion);
            const costoTotal = cantidadDias * reserva.vehiculo.precio; 
            return(
            <tr key={index}>
            <td><img src={reserva.vehiculo.imagen} alt="Foto del producto"/></td>
            <td>{reserva.vehiculo.modelo}</td>
            <td>{new Date(reserva.diaInicio.toString()).toLocaleDateString()}  |  
            {new Date(reserva.diaFinalizacion.toString()).toLocaleDateString()}</td>
            <td>$ {costoTotal}</td>
            </tr>
            )
          })}
        </tbody>
    </table>

</section>
  )
}

export default MyReservation