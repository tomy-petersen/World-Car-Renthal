import React, { useState } from 'react'
import Loader from '../Utils/Loader'



const RegistrationForm = () => {

    const [mensajeDeAyuda, setMensajeDeAyuda] = useState('');
    const [isLoading, setIsLoading] = useState(false);

    const url= "http://54.193.237.249:8090/usuario/registrar"

    const[datos , setDatos]=useState({
        nombre: "",
        apellido: "",
        dni: "",
        telefono: "",
        usuario: "",
        email: "",
        contrasena: "",
        reservas:[]
      })

      const [validacion, setValidacion] = useState({
        nombre: true,
        apellido: true,
        dni: true,
        telefono: true,
        usuario: true,
        email: true,
        contrasena: true
      });

      const mensajesAyuda = {
        nombre: "El nombre debe tener al menos 4 caracteres.",
        apellido: "El apellido debe tener al menos 4 caracteres.",
        dni: "El DNI debe contener solo números.",
        telefono: "El teléfono debe tener al menos 5 caracteres y contener solo números.",
        usuario: "El usuario debe tener más de 4 letras y al menos una mayúscula.",
        email: "El email debe contener un '@' y un '.' antes de la terminación.",
        contrasena: "La contraseña debe tener más de 8 caracteres, incluir al menos 1 número y 1 letra mayúscula."
      };

      const validarCampo = (campo) => {
        switch (campo) {
          case 'nombre':
            return datos.nombre.length >= 4;
      
          case 'apellido':
            return datos.apellido.length >= 4;
      
          case 'dni':
            return /^\d+$/.test(datos.dni);
      
          case 'telefono':
            return /^\d{5,}$/.test(datos.telefono);
      
          case 'usuario':
            return datos.usuario.length > 4 && /[A-Z]/.test(datos.usuario);
      
          case 'email':
            
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            return emailRegex.test(datos.email);
      
          case 'contrasena':
            
            const longitudValida = datos.contrasena.length > 8;
      
            
            const tieneDigito = /\d/.test(datos.contrasena);
      
            
            const tieneMayuscula = /[A-Z]/.test(datos.contrasena);
      
            
            return longitudValida && tieneDigito && tieneMayuscula;
      
          default:
            return true;
        }
      };
      

      const handleInputChange = (campo, valor) => {
        setDatos({ ...datos, [campo]: valor });
        setValidacion({ ...validacion, [campo]: validarCampo(campo) });
      };

      const handleInputFocus = (campo) => {
        setMensajeDeAyuda(mensajesAyuda[campo]);
      };
      
  
  
  const handleSubmit=(e)=>{

      e.preventDefault()

      setIsLoading(true);

      const camposValidos = Object.values(validacion).every((campo) => campo);

      if (camposValidos) {        
        const config = {
          method: 'POST',
          body: JSON.stringify(datos),
          headers: {
            'Content-type': 'application/json; charset=UTF-8',
          }
        };


      fetch(url,config)
      .then(response =>{
        if (response.ok === false) {
            alert("El mail o usario ingresado ya esta en uso")
        }else {
          alert("creado con exito")
          window.location.replace('/login')

      }
     })
    }}

    return(
      <div className='regis-form'>

        <h1 className='titulo-form'>Formulario de Registro</h1>
            
        <form method="post"  onSubmit={(e)=>{handleSubmit(e)}}>

            <label htmlFor="nombre">Nombre:</label>
            <input 
            type="text" 
            id="nombre" 
            name="nombre" 
            style={{ borderColor: validacion.nombre ? 'green' : 'red' }}
            required 
            onFocus={() => handleInputFocus('nombre')}
            onChange={(event) => handleInputChange('nombre', event.target.value)}/>
             <br />
            
            <label htmlFor="apellido">Apellido:</label>
            <input 
            type="text" 
            id="apellido" 
            name="apellido" 
            style={{ borderColor: validacion.apellido ? 'green' : 'red' }}
            required 
            onFocus={() => handleInputFocus('apellido')}
            onChange={(event) => handleInputChange('apellido', event.target.value)}/> <br/>

            <label htmlFor="dni">Dni:</label>
            <input 
            type="text" 
            id="dni" 
            name="dni" 
            required 
            onFocus={() => handleInputFocus('dni')}
            onChange={(event) => handleInputChange('dni', event.target.value)}
            style={{ borderColor: validacion.dni ? 'green' : 'red' }}/>            
            <br />

            <label htmlFor="telefono">Telefono:</label>
            <input 
            type="text" 
            id="telefono" 
            name="telefono" 
            required 
            onFocus={() => handleInputFocus('telefono')}
            onChange={(event) => handleInputChange('telefono', event.target.value)}
            style={{ borderColor: validacion.telefono ? 'green' : 'red' }} 
            /><br />
            
            <label htmlFor="usuario">Usuario:</label>
            <input 
            type="text" 
            id="usuario" 
            name="usuario" 
            required 
            onFocus={() => handleInputFocus('usuario')}
            onChange={(event) => handleInputChange('usuario', event.target.value)}
            style={{ borderColor: validacion.usuario ? 'green' : 'red' }}
            /><br />
            
            <label htmlFor="email">Email:</label>
            <input 
            type="email" 
            id="email" 
            name="email" 
            required 
            onFocus={() => handleInputFocus('email')}
            onChange={(event) => handleInputChange('email', event.target.value)}
            style={{ borderColor: validacion.email ? 'green' : 'red' }}
            /><br />
    
            <label htmlFor="contrasena">Contraseña:</label>
            <input 
            type="password" 
            id="contrasena" 
            name="contrasena" 
            required 
            onFocus={() => handleInputFocus('contrasena')}
            onChange={(event) => handleInputChange('contrasena', event.target.value)}
            style={{ borderColor: validacion.contrasena ? 'green' : 'red' }}
            /><br />   
             
            <div className='envio'>
              <input type="submit" value="Registrarse" className='headerbutton'/>
              {isLoading ? <Loader/> : <></>}
           </div>         
        </form>

            <div>
          <p>{mensajeDeAyuda}</p>
            </div>
  </div>
    )  
}

export default RegistrationForm;