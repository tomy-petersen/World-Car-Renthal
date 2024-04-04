import React, { useState } from 'react';
import { Link } from 'react-router-dom/dist/umd/react-router-dom.development'

const Login = () => {

      const jwt =localStorage.getItem('token')      


      const [data, setData] = useState({
        usuario: '',
        contraseña: '',
      });

    
      const config={
        method: 'POST',
        headers: {
            'Content-type': 'application/json; charset=UTF-8'},
        body: JSON.stringify(data)
      }
    
      const handleSubmit = (e) => {
        e.preventDefault();
        try {
           fetch('http://54.193.237.249:8090/login',config)
          .then((response)=>{
              if (response.ok) {
                return response.json();
              } else {
                alert("credenciales incorrectas")
                console.error('Credenciales incorrectas');
              }
          })
          .then((data)=>{
            console.log(data);
            const token = data.token
            const email=data.usuario;
            localStorage.setItem('token',token)
            localStorage.setItem('email',email)
            window.location.replace('/')            
          })

    
        } catch (error) {
          console.error('Error de red', error);
        }
    }

    return(
    <>
      {jwt ? 
        (
          <div className='bienvenida'>
            <h1>BIENVENIDO A WORLD RENT CAR</h1>
            <h3>Dirijase hacia la pagina principal</h3>
            <h2><Link to='/'>HOME</Link></h2>
          </div>
        )
      :(
        <div className='login-form'>
        <h2 className='titulo-login'>Iniciar sesión</h2>
        <form className='formLOGIN' onSubmit={(e)=>{handleSubmit(e)}}>
            <div class="form-group">
                <label for="email">Correo electrónico</label>                
                <input type="email" id="email" name="email" required onChange={(event) => setData({ ...data, usuario: event.target.value })} />
            </div>
            <div class="form-group">
                <label for="password">Contraseña</label>
                <input type="password" id="password" name="password" required onChange={(event) => setData({ ...data, contraseña: event.target.value })} />                
            </div>
            <button type="submit" className='headerbutton'>Iniciar sesión</button>
        </form>
        <p className='ruta-a-registro'>¿No tienes una cuenta? <Link to ="/registration"><p>Registrate Aqui</p></Link> </p>
        </div>) }
      </>
    )
};


export default Login;