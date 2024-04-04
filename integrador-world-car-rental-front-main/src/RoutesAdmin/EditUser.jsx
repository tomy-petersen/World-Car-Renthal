import React, { useEffect } from 'react'
import { useParams} from 'react-router-dom'
import { useState } from 'react';

const EditUser = () => {

    const {id} = useParams();

    const jwt = localStorage.getItem("token")

    const [usuarios, setUsuario] = useState({

        nombre:'',
        apellido:'',
        dni:'',
        telefono:'',
        usuario:'',
        email:'',
        contraseña:'',
        rol:'',
        direccion:''

    })     
    
    useEffect(() => {        
        fetch(`http://54.193.237.249:8090/usuario/buscar/${id}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json', 'Authorization': 'Bearer ' + jwt 
        },        
      })
          .then((response) => response.json())
          .then((data) => {
            setUsuario(data);
          })
          .catch((error) => {
            console.error('Error al obtener detalles del vehículo:', error);
          });
      }, []); 


      const handleEdit = (e) => {
      
        e.preventDefault();
  
        fetch(`http://54.193.237.249:8090/usuario/${id}/modificar-rol`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json', 'Authorization': 'Bearer ' + jwt 
          },
          body: JSON.stringify(usuarios),
        })
          .then((response) => {
            if (response.ok) {
              alert("Usuario ha cambiado de rol a " + usuarios.rol)
              console.log(usuarios.rol);
              window.location.replace("/listUser")
              
            } else {
              console.error('Error al editar el rol del usuario');
            }
          })
          .catch((error) => {
            console.error('Error en la solicitud de edición:', error);
          });
      };

      



  return (
    <div>
        <div className='tabla-rol'>
            <h2>EDITA EL ROL DEL USUARIO</h2>

            <table>

                <thead>
                    <tr>
                    <th>ID</th>
                    <th>Nombre Completo</th>
                    <th>Rol</th> 
                    </tr>                   
                </thead>
                
                <tbody>
                    <tr>
                    <td>{`${usuarios.id}`}</td>
                    <td>{`${usuarios.nombre} ${usuarios.apellido}`}</td>
                    <td>{`${usuarios.rol}`}</td>
                    </tr>
                </tbody>


            </table>
        </div>
        <div className='form-rol' >

            <form className='editar-rol' onSubmit={(e)=>{handleEdit(e)}}>

                <label htmlFor="rol">ROL</label>
                  <select 
                  name="rol" 
                  id="rol"
                  className='rol'
                  value={usuarios ? usuarios.rol : ''}
                  onChange={(e) =>
                    setUsuario({
                      ...usuarios,
                      rol: e.target.value,
                    })}
                  >
                  <option value="ADMIN">ADMIN</option>
                  <option value="USER">USER</option>
                  </select>
                
                  <button type="submit" class="btn">Editar Rol del Usuario</button>

            </form>
        </div>

    </div>
  )
}

export default EditUser