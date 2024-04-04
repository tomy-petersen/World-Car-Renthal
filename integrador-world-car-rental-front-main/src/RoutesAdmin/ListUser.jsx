import { useEffect, useState } from "react"
import { useParams, Link } from 'react-router-dom';


const ListUser=()=>{

    const [users,setUsers]=useState([])

    const jwt=localStorage.getItem("token")

    const url="http://54.193.237.249:8090/usuario/usuarios"

    const [userControler,setUserControler]= useState({})

    const email=localStorage.getItem('email')       
    
    const url2=`http://54.193.237.249:8090/usuario/buscarEmail?email=${email}`
    
    const config2={
        method: 'GET',
        headers: {
          'Content-type': 'application/json; charset=UTF-8','Authorization': 'Bearer ' + jwt
      },
    }
    
    useEffect(()=>{
        fetch(url2,config2)
      .then(response =>{
        return response.json();
      })
      .then(data => {
        setUserControler(data)
      })
      }, [])   


    const config={
        method: 'GET',
        headers: {
          'Content-type': 'application/json; charset=UTF-8','Authorization': 'Bearer ' + jwt
      },
    }

    useEffect(()=>{
        fetch(url,config)
      .then(response =>{
        return response.json();
      })
      .then(data => {    
        setUsers(data)
      })
      }, [])


    return(
        <>{userControler.rol==="ADMIN" ? 
            <table>
                <thead>
                    <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>DNI o Cedula</th>
                    <th>Telefono</th>
                    <th>Usuario</th>
                    <th>Email</th>
                    <th>Rol</th>
                    <th>Opciones</th>
                    </tr>
                </thead>
                {users.map((user, index) => (
                    <tbody key={index}>
                        <tr>
                        <td>{user.id}</td>
                        <td>{user.nombre}</td>
                        <td>{user.apellido}</td>
                        <td>{user.dni}</td>
                        <td>{user.telefono}</td>
                        <td>{user.usuario}</td>
                        <td>{user.email}</td>
                        <td>{user.rol}</td>
                        <td>
                      <Link to={`/editUser/${user.id}`}><button>Editar</button></Link>
                      <button>Eliminar</button>
                        </td>
                        </tr>
                    </tbody>
                ))}
            </table> 
            :
            <h1>NO TIENES ACCESO A ESTA PAGINA</h1>
            } 
        </>
    )

}
export default ListUser;