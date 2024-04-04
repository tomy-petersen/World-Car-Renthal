import { Link } from "react-router-dom/dist/umd/react-router-dom.development";
import { useEffect, useState } from "react";
import { Dropdown, DropdownItem,DropdownToggle, DropdownMenu } from 'react-bootstrap';
import Exit from "../RoutesUsers/Exit";

function Header (){
    
const [user,setUser]= useState({})
const jwt= localStorage.getItem('token')
const email=localStorage.getItem('email')
let inicial="";

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
    return response.json();
  })
  .then(data => {
    setUser(data)
    const idUser=data.id
    localStorage.setItem("id",idUser)
  })
  }, [jwt]) 
  if (user.nombre!=null) {
    inicial= (user.nombre[0]).toUpperCase()
  }

  const [isOpen, setIsOpen] = useState(false);

  const toggleMenu = () => {
    setIsOpen(!isOpen);
  }


    return(
        <section className="head">
                <div className="logo">
                   <Link to="/"><img src="../src/Redes/CAR 2@3x.png" alt="Logo" className="" /></Link>
                </div> 
                <nav className="navbar">
                    <ul className="nav-list">
                       <Link to="/"><li>Inicio</li></Link>
                       <Link to="/favorites"><li>Favoritos</li></Link>
                       <Link to="/galeries"><li>Galeria</li></Link>
                       <Link to="/myreservation"><li>Mis Reservas</li></Link> 
                       <Link to="/politics"><li>Politicas</li></Link>                       
                    </ul>
                </nav>
                {user.rol === "ADMIN" ? (
                    <ul className="admin">
                        <Link to="/homeAdmin"><li>Administrar</li></Link>
                    </ul>
                ) : (
                    <></>
                    )}
                {jwt ? (
                        <>
                          <Dropdown>
                                <Dropdown.Toggle variant="success" id="dropdown-basic">
                                  <h3 className="user-name" onClick={(e)=>{toggleMenu()}}>{inicial}</h3>
                                </Dropdown.Toggle>
                                {isOpen ?
                                <Dropdown.Menu className="menu-user">
                                    <h3 className="config">{user.usuario}</h3>
                                    <ul className="info-usuario">
                                        <li>Nombre:{user.nombre} {user.apellido}</li>                                        
                                        <li>Rol:{user.rol}</li>
                                        <Dropdown.Item className="item"><Link to='/perfil'><button className="exit">Editar perfil</button></Link></Dropdown.Item>
                                    </ul>
                                    <Dropdown.Item className="item"><button className="exit">Ayuda</button></Dropdown.Item>
                                    <Dropdown.Item className="item"><Exit/></Dropdown.Item>
                                </Dropdown.Menu>:<></>}
                            </Dropdown>
                        </>
                ) : (
                    <ul className="registro">
                    <Link to="/login"><li><button className="headerbutton">Iniciar Sesion</button></li></Link>
                    <Link to="/registration"><li><button className="headerbutton">Crear Cuenta</button></li></Link>
                    </ul>
                )}
                               
        </section>
        
    )
}

export default Header;