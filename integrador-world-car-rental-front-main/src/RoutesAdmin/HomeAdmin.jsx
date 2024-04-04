import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'

const HomeAdmin = () => {

  
  const [user,setUser]= useState({})
  const jwt= localStorage.getItem('token')
  const email=localStorage.getItem('email')
  
  
  const url=`http://54.193.237.249:8090/usuario/buscarEmail?email=${email}`
  
  const config={
      method: 'GET',
      headers: {
        'Content-type': 'application/json; charset=UTF-8','Authorization': 'Bearer ' + jwt
    },
  }
  
  useEffect(()=>{
      fetch(url,config)
    .then(response =>{
      console.log(response);
      return response.json();
    })
    .then(data => {
      console.log(data);    
      setUser(data)
      console.log(user.rol);
    })
    }, []) 
  



  return (
    <section className='home-admin'>
      {jwt && (user.rol==="ADMIN") ?
      <>
        <h2>Administración</h2>
        <div>
          <ul>
            <Link to="/add"><li>AGREGAR VEHICULO</li></Link>
            {/*<Link to="/edit"><li>EDITAR VEHICULO</li></Link>*/}
            <Link to="/listuser"><li>ADMINISTRAR USUARIOS</li></Link>
            <Link to="/list">LISTA DE VEHICULOS</Link>            
          </ul>
        </div>
      </>
        :
        <h1>NO TIENES ACCESO A ESTA PAGINA</h1>
      }
    </section>
  )
}

export default HomeAdmin