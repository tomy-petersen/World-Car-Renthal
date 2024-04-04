import React, { useEffect, useState } from 'react'
import { json } from 'react-router-dom/dist/umd/react-router-dom.development'

const AddVehicle = () => {

  const url= "http://54.193.237.249:8090/vehiculo/registrar"
  const jwt= localStorage.getItem('token')


  const[datos , setDatos]=useState({
    
  modelo: "",
  marca: "",
  precio: 0,
  disponibilidad: true,
  tipo: "",
  pasajeros: 0,
  descripcion: "",
  motor: "",
  cilindrada: "",
  caja: "",
  patente: "",
  puntaje: 0,
  imagen: [],
  sucursal: {
    id: 0
  }
  })

    const handleImagenesChange = (e) => {
      const nuevasImagenes = e.target.value;
      setDatos({ ...datos, imagen: [...datos.imagen, nuevasImagenes] });
    };
  

    const config={
        method: 'POST',
        headers: {
          'Content-type': 'application/json; charset=UTF-8','Authorization': 'Bearer ' + jwt
      },
        body: JSON.stringify(datos)
    }


const handleSubmit=(e)=>{
    e.preventDefault()

    fetch(url,config)
    .then(response =>{
      if (response.ok === false) {
        return response.json()
        .then(data=>{
          alert(data.error)
          return data
        })
      }else {
        alert("creado con exito")
    }
   })
  }
    

    return(
      <section className='add-vehicle'>
        <form  method="post"  className='form-add'  onSubmit={(e)=>{handleSubmit(e)}}>
        <h2>Datos del Vehículo</h2>
        <div className="form-group">
          <label htmlFor="modelo">Modelo</label>
          <input type="text" className="form-control" id="modelo" name="modelo" required onChange={(event) =>  setDatos({ ...datos, modelo: event.target.value })}/>
        </div>
      
        <div className="form-group">
          <label htmlFor="marca">Marca</label>
          <input type="text" className="form-control" id="marca" name="marca" required  onChange={(event) =>  setDatos({ ...datos, marca: event.target.value })}/>
        </div>
      
        <div className="form-group">
          <label htmlFor="precio">Precio</label>
          <input type="number" className="form-control" id="precio" name="precio" required onChange={(event) =>  setDatos({ ...datos, precio: event.target.value })}/>
        </div>
      
        <div className="form-group">
          <label htmlFor="disponibilidad">Disponibilidad</label>
          <select className="form-control" id="disponibilidad" name="disponibilidad" onChange={(event) =>  setDatos({ ...datos, disponibilidad: event.target.value })}>
            <option value="true">Disponible</option>
            <option value="false">No disponible</option>
          </select>
        </div>
        <div className="form-group">
          <label htmlFor="tipo">Tipo o Categoría</label>
          <select className="form-control" id="tipo" name="tipo" onChange={(event) =>  setDatos({ ...datos, tipo: event.target.value })}>
            <option value="Selecciona">Selecciona</option>
            <option value="Autos">Autos</option>
            <option value="Motos">Motos</option>
            <option value="Camionetas">Camionetas</option>
            <option value="Van">Van</option>
          </select>
        </div>
      
        <div className="form-group">
          <label htmlFor="pasajeros">Número de Pasajeros</label>
          <input type="number" className="form-control" id="pasajeros" name="pasajeros" required onChange={(event) =>  setDatos({ ...datos, pasajeros: event.target.value })}/>
        </div>
      
        <div className="form-group">
          <label htmlFor="descripcion">Descripción del Vehículo</label>
          <textarea name="descripcion" className="form-control" id="descripcion" onChange={(event) =>  setDatos({ ...datos, descripcion: event.target.value })}></textarea>
        </div>
      
        <div className="form-group">
          <label htmlFor="motor">Tipo de Motor</label>
          <select className="form-control" id="motor" name="motor" onChange={(event) =>  setDatos({ ...datos, motor: event.target.value })}>
            <option value="Selecciona">Selecciona</option>
            <option value="Eléctrico">Eléctrico</option>
            <option value="Diésel">Diésel</option>
            <option value="Gas">Gas</option>
            <option value="Híbrido">Híbrido</option>
          </select>
        </div>
      
        <div className="form-group">
          <label htmlFor="cilindrada">Cilindrada</label>
          <input type="text" className="form-control" id="cilindrada" name="cilindrada" required  onChange={(event) =>  setDatos({ ...datos, cilindrada: event.target.value })}/>
        </div>
      
        <div className="form-group">
          <label htmlFor="caja">Tipo de Caja</label>
          <select className="form-control" id="caja" name="caja" onChange={(event) =>  setDatos({ ...datos, caja: event.target.value })}>
            <option value="Selecciona">Selecciona</option>
            <option value="Automática">Automática</option>
            <option value="Manual">Manual</option>
          </select>
        </div>
      
        <div className="form-group">
          <label htmlFor="patente">Patente</label>
          <input type="text" className="form-control" id="patente" name="patente" required onChange={(event) =>  setDatos({ ...datos, patente: event.target.value })}/>
        </div>
      
        <div className="form-group">
          <label htmlFor="puntaje">Puntaje</label>
          <input type="number" className="form-control" id="puntaje" name="puntaje" required onChange={(event) =>  setDatos({ ...datos, puntaje: event.target.value })}/>
        </div>
      
        <div className="form-group">
          <label htmlFor="imagenes">Imágenes</label>
          <input type="text" className="form-control" id="imagenes" name="imagenes" onChange={handleImagenesChange} />
        </div>
      
        <h2>Datos de la Sucursal</h2>
      
        <div className="form-group">
          <label htmlFor="sucursalId">ID de la Sucursal</label>
          <input type="number" className="form-control" id="sucursalId" name="sucursalId" required onChange={(event) =>  setDatos({ ...datos, sucursal:{ ...datos.sucursal,id: event.target.value }})} />
        </div>
      
        <button type="submit" className="btn btn-primary">Agregar Vehículo</button>
      </form>
    </section>
    
    )
}

export default AddVehicle